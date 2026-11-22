/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.dialog;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FormaPago;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriFormaPago;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SriServiceIf;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * Ventana de cobro de Venta Rapida: reemplaza al flujo previo "Cobrar -> Esta seguro? -> dialogo
 * generico de Forma de Pago -> impresion" por una sola ventana (Cobrar -> esta ventana -> Confirmar
 * venta -> resultado). No se extiende/toca FormaPagoDialog (compartido con Facturacion, Nota de
 * Credito, etc.): es una presentacion nueva, exclusiva de Venta Rapida, que reutiliza las mismas
 * entidades (FormaPago, SriFormaPago) y el mismo registro de venta (VentaRapidaModel.grabar()/
 * registrarVentaConfirmada, inalterados en su logica de negocio).
 *
 * No tiene .form de NetBeans a proposito: el contenido cambia de tarjeta (formulario/resultado) y
 * el panel de efectivo se muestra/oculta y genera botones de monto rapido en tiempo de ejecucion,
 * algo que el editor grafico de formularios no modela bien.
 *
 * @author Carlos
 */
public class VentaRapidaCobroDialog extends JDialog {

    private static final Color COLOR_VERDE_PRIMARIO = new Color(46, 139, 87);
    private static final Color COLOR_GRIS_TEXTO = new Color(130, 130, 130);
    private static final Color COLOR_ROJO_FALTA = new Color(178, 34, 34);
    private static final String CODIGO_SRI_EFECTIVO = "01";
    private static final String TARJETA_FORMULARIO = "FORMULARIO";
    private static final String TARJETA_RESULTADO = "RESULTADO";

    /**
     * Resultado de registrar la venta: la factura ya grabada (puede ser la misma instancia con el
     * id/secuencial asignados por el servidor) y una advertencia opcional cuando la venta SI quedo
     * registrada pero algo posterior fallo (impresion de la NVI, o generacion del comprobante
     * electronico) -nunca se usa esta clase para representar un fallo de registro: eso se
     * sigue manejando con las excepciones ya existentes (ServicioCodefacException/RemoteException).
     */
    public static final class ResultadoCobro {
        public final Factura facturaRegistrada;
        public final String advertencia;

        public ResultadoCobro(Factura facturaRegistrada, String advertencia) {
            this.facturaRegistrada = facturaRegistrada;
            this.advertencia = advertencia;
        }
    }

    public interface Callback {
        /**
         * Hace todo lo que ya hacia VentaRapidaModel.grabar() desde "setear forma de pago" en
         * adelante (datos por defecto, servicio.grabar, impresion/firma electronica). Solo debe
         * lanzar ServicioCodefacException/RemoteException para los 2 casos de fallo de
         * REGISTRO (rechazo de negocio confirmado / resultado incierto); un fallo de impresion o de
         * firma electronica POSTERIOR a un registro exitoso se reporta como advertencia dentro de
         * ResultadoCobro, no como excepcion.
         */
        ResultadoCobro confirmarVenta(FormaPago formaPagoElegida, BigDecimal valorRecibido) throws RemoteException, ServicioCodefacException;

        /**
         * Reimprime el comprobante YA registrado (sin volver a llamar grabar/firmar electronicamente).
         */
        void reimprimir(Factura facturaRegistrada);

        /**
         * Deja el pedido listo para una venta nueva (equivalente a limpiar() en VentaRapidaModel).
         */
        void nuevaVenta();
    }

    private final Factura factura;
    private final DocumentoEnum documentoSeleccionado;
    private final Callback callback;

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel panelCards = new JPanel(cardLayout);

    private JComboBox<SriFormaPago> cmbFormaPago;
    private JPanel panelEfectivo;
    private JTextField txtRecibido;
    private JLabel lblVuelto;
    private JPanel panelMontosRapidos;
    private JButton btnCancelar;
    private JButton btnConfirmar;

    private JLabel lblResultadoTitulo;
    private JLabel lblResultadoDetalle;
    private JLabel lblResultadoVuelto;
    private JButton btnReimprimir;
    private JButton btnNuevaVenta;

    private boolean procesando = false;
    private boolean ventaRegistrada = false;
    private BigDecimal ultimoValorRecibido;

    public VentaRapidaCobroDialog(Frame parent, Factura factura, DocumentoEnum documentoSeleccionado, String resumenCliente, Callback callback) {
        super(parent, true);
        this.factura = factura;
        this.documentoSeleccionado = documentoSeleccionado;
        this.callback = callback;

        setTitle("Cobrar venta");
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        panelCards.add(construirPanelFormulario(resumenCliente), TARJETA_FORMULARIO);
        panelCards.add(construirPanelResultado(), TARJETA_RESULTADO);
        getContentPane().add(panelCards);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                cerrarVentana();
            }

            @Override
            public void windowOpened(WindowEvent e) {
                if (esEfectivoSeleccionado()) {
                    txtRecibido.requestFocusInWindow();
                }
            }
        });

        pack();
    }

    /**
     * X o Cancelar: si la venta ya se registro (estamos en la tarjeta de resultado) ya no hay nada
     * que "cancelar" -se interpreta igual que Nueva Venta, para no dejar el pedido ya cobrado dando
     * vueltas en la pantalla-. Si todavia no se confirma nada, cierra sin tocar el pedido (punto 1).
     */
    private void cerrarVentana() {
        if (ventaRegistrada) {
            callback.nuevaVenta();
        }
        dispose();
    }

    private boolean esEfectivoSeleccionado() {
        SriFormaPago seleccionada = (SriFormaPago) cmbFormaPago.getSelectedItem();
        return seleccionada != null && CODIGO_SRI_EFECTIVO.equals(seleccionada.getCodigo());
    }

    // ===================================================================================
    // Tarjeta FORMULARIO
    // ===================================================================================
    private JPanel construirPanelFormulario(String resumenCliente) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(16, 20, 16, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridy = 0;

        JLabel lblResumen = new JLabel(documentoSeleccionado.getNombre() + " · " + resumenCliente);
        lblResumen.setFont(new Font("Arial", Font.PLAIN, 13));
        lblResumen.setForeground(COLOR_GRIS_TEXTO);
        gbc.insets = new Insets(0, 0, 14, 0);
        panel.add(lblResumen, gbc);

        gbc.gridy++;
        JLabel lblTotalTitulo = new JLabel("Total a pagar");
        lblTotalTitulo.setFont(new Font("Arial", Font.PLAIN, 13));
        lblTotalTitulo.setForeground(COLOR_GRIS_TEXTO);
        gbc.insets = new Insets(0, 0, 2, 0);
        panel.add(lblTotalTitulo, gbc);

        gbc.gridy++;
        JLabel lblTotalValor = new JLabel("$" + factura.getTotal().setScale(2, RoundingMode.HALF_UP));
        lblTotalValor.setFont(new Font("Arial", Font.BOLD, 32));
        gbc.insets = new Insets(0, 0, 16, 0);
        panel.add(lblTotalValor, gbc);

        gbc.gridy++;
        JLabel lblFormaPagoTitulo = new JLabel("Forma de pago");
        lblFormaPagoTitulo.setFont(new Font("Arial", Font.BOLD, 13));
        gbc.insets = new Insets(0, 0, 4, 0);
        panel.add(lblFormaPagoTitulo, gbc);

        gbc.gridy++;
        cmbFormaPago = new JComboBox<SriFormaPago>();
        cmbFormaPago.setFont(new Font("Arial", Font.PLAIN, 14));
        cmbFormaPago.setPreferredSize(new Dimension(380, 30));
        cargarFormasPagoActivas();
        gbc.insets = new Insets(0, 0, 14, 0);
        panel.add(cmbFormaPago, gbc);

        gbc.gridy++;
        panelEfectivo = construirPanelEfectivo();
        gbc.insets = new Insets(0, 0, 10, 0);
        panel.add(panelEfectivo, gbc);

        gbc.gridy++;
        JPanel panelBotones = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 10, 0));
        panelBotones.setBackground(Color.WHITE);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setForeground(COLOR_GRIS_TEXTO);
        btnCancelar.setFocusPainted(false);
        btnCancelar.setBorderPainted(false);
        btnCancelar.setContentAreaFilled(false);
        btnCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cerrarVentana();
            }
        });
        panelBotones.add(btnCancelar);

        btnConfirmar = new JButton();
        btnConfirmar.setFont(new Font("Arial", Font.BOLD, 15));
        btnConfirmar.setBackground(COLOR_VERDE_PRIMARIO);
        btnConfirmar.setForeground(Color.WHITE);
        btnConfirmar.setOpaque(true);
        btnConfirmar.setBorderPainted(false);
        btnConfirmar.setPreferredSize(new Dimension(220, 40));
        btnConfirmar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmarVentaClick();
            }
        });
        panelBotones.add(btnConfirmar);

        gbc.insets = new Insets(6, 0, 0, 0);
        panel.add(panelBotones, gbc);

        getRootPane().setDefaultButton(btnConfirmar);

        cmbFormaPago.addItemListener(new java.awt.event.ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    actualizarVisibilidadEfectivo();
                }
            }
        });
        actualizarVisibilidadEfectivo();
        actualizarTextoBotonConfirmar();

        return panel;
    }

    private void cargarFormasPagoActivas() {
        try {
            SriServiceIf service = ServiceFactory.getFactory().getSriServiceIf();
            List<SriFormaPago> formasPago = service.obtenerFormasPagoActivo();
            SriFormaPago efectivo = null;
            for (SriFormaPago formaPago : formasPago) {
                cmbFormaPago.addItem(formaPago);
                if (CODIGO_SRI_EFECTIVO.equals(formaPago.getCodigo())) {
                    efectivo = formaPago;
                }
            }
            //Efectivo siempre arranca seleccionado (punto 3 del requerimiento), sin importar el
            //orden en que venga el catalogo configurado en el sistema.
            if (efectivo != null) {
                cmbFormaPago.setSelectedItem(efectivo);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(VentaRapidaCobroDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private JPanel construirPanelEfectivo() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JLabel lblRecibidoTitulo = new JLabel("Efectivo recibido");
        lblRecibidoTitulo.setFont(new Font("Arial", Font.BOLD, 13));
        gbc.insets = new Insets(0, 0, 4, 0);
        panel.add(lblRecibidoTitulo, gbc);

        gbc.gridy++;
        txtRecibido = new JTextField();
        txtRecibido.setFont(new Font("Arial", Font.BOLD, 20));
        txtRecibido.setHorizontalAlignment(SwingConstants.RIGHT);
        txtRecibido.setPreferredSize(new Dimension(380, 38));
        txtRecibido.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                actualizarVuelto();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                actualizarVuelto();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                //No aplica a JTextField plano.
            }
        });
        gbc.insets = new Insets(0, 0, 6, 0);
        panel.add(txtRecibido, gbc);

        gbc.gridy++;
        panelMontosRapidos = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 6, 0));
        panelMontosRapidos.setBackground(Color.WHITE);
        gbc.insets = new Insets(0, 0, 8, 0);
        panel.add(panelMontosRapidos, gbc);

        gbc.gridy++;
        lblVuelto = new JLabel(" ");
        lblVuelto.setFont(new Font("Arial", Font.BOLD, 15));
        gbc.insets = new Insets(0, 0, 0, 0);
        panel.add(lblVuelto, gbc);

        return panel;
    }

    private void construirBotonesMontosRapidos() {
        panelMontosRapidos.removeAll();

        JButton btnExacto = new JButton("Exacto");
        estilizarBotonMonto(btnExacto);
        btnExacto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtRecibido.setText(factura.getTotal().setScale(2, RoundingMode.HALF_UP).toPlainString());
            }
        });
        panelMontosRapidos.add(btnExacto);

        for (final BigDecimal monto : calcularMontosRapidos(factura.getTotal())) {
            JButton btnMonto = new JButton("$" + monto.toPlainString());
            estilizarBotonMonto(btnMonto);
            btnMonto.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //Los accesos rapidos REEMPLAZAN el monto recibido, nunca lo acumulan (punto 3).
                    txtRecibido.setText(monto.toPlainString());
                }
            });
            panelMontosRapidos.add(btnMonto);
        }

        panelMontosRapidos.revalidate();
        panelMontosRapidos.repaint();
    }

    private void estilizarBotonMonto(JButton boton) {
        boton.setFont(new Font("Arial", Font.PLAIN, 12));
        boton.setFocusPainted(false);
        boton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        boton.setBackground(new Color(240, 240, 240));
        boton.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));
    }

    /**
     * Sugerencias de billetes comunes (Ecuador: $1/$5/$10/$20/$50/$100) que alcanzan para cubrir el
     * total, hasta 3 para no saturar la fila. No existe un catalogo de "denominaciones" en el
     * sistema; es una heuristica simple y no un dato de configuracion.
     */
    private List<BigDecimal> calcularMontosRapidos(BigDecimal total) {
        BigDecimal[] denominaciones = {
            new BigDecimal(5), new BigDecimal(10), new BigDecimal(20), new BigDecimal(50), new BigDecimal(100)
        };
        List<BigDecimal> sugeridos = new ArrayList<BigDecimal>();
        for (BigDecimal denominacion : denominaciones) {
            if (denominacion.compareTo(total) >= 0) {
                sugeridos.add(denominacion);
            }
            if (sugeridos.size() == 3) {
                break;
            }
        }
        return sugeridos;
    }

    private void actualizarVisibilidadEfectivo() {
        boolean efectivo = esEfectivoSeleccionado();
        panelEfectivo.setVisible(efectivo);
        if (efectivo) {
            //Precargado con el total (= pago exacto, vuelto $0.00) en vez de vacio: cubre sin
            //friccion el caso mas comun (el cliente entrega el monto justo) y Confirmar queda
            //habilitado de inmediato; el cajero solo edita este campo si el cliente entrega otro
            //monto. Sigue siendo obligatorio tener un monto valido (nunca se puede confirmar con
            //el campo en blanco o en $0), solo que el valor por defecto ya no es "nada".
            txtRecibido.setText(factura.getTotal().setScale(2, RoundingMode.HALF_UP).toPlainString());
            construirBotonesMontosRapidos();
            txtRecibido.requestFocusInWindow();
            txtRecibido.selectAll();
        }
        actualizarEstadoConfirmar();
        pack();
    }

    /**
     * Interpreta el texto del campo Recibido sin lanzar nunca una excepcion visible al usuario
     * (punto 3): vacio, letras o numeros negativos se tratan todos como "todavia no hay un monto
     * valido ingresado", no como un error que interrumpa el flujo.
     */
    private BigDecimal parsearRecibido(String texto) {
        if (texto == null) {
            return null;
        }
        String limpio = texto.trim();
        if (limpio.isEmpty()) {
            return null;
        }
        try {
            BigDecimal valor = new BigDecimal(limpio);
            if (valor.compareTo(BigDecimal.ZERO) < 0) {
                return null;
            }
            return valor;
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    private void actualizarVuelto() {
        if (!esEfectivoSeleccionado()) {
            return;
        }
        BigDecimal recibido = parsearRecibido(txtRecibido.getText());
        if (recibido == null) {
            lblVuelto.setForeground(COLOR_GRIS_TEXTO);
            lblVuelto.setText("Ingrese el efectivo recibido");
        } else {
            BigDecimal vuelto = recibido.subtract(factura.getTotal()).setScale(2, RoundingMode.HALF_UP);
            if (vuelto.compareTo(BigDecimal.ZERO) < 0) {
                lblVuelto.setForeground(COLOR_ROJO_FALTA);
                lblVuelto.setText("Faltan $" + vuelto.negate());
            } else {
                lblVuelto.setForeground(COLOR_VERDE_PRIMARIO);
                lblVuelto.setText("Vuelto: $" + vuelto);
            }
        }
        actualizarEstadoConfirmar();
    }

    private void actualizarEstadoConfirmar() {
        if (!esEfectivoSeleccionado()) {
            btnConfirmar.setEnabled(true);
            return;
        }
        BigDecimal recibido = parsearRecibido(txtRecibido.getText());
        boolean alcanza = recibido != null && recibido.compareTo(factura.getTotal()) >= 0;
        btnConfirmar.setEnabled(alcanza);
    }

    private void actualizarTextoBotonConfirmar() {
        btnConfirmar.setText("Confirmar venta · $" + factura.getTotal().setScale(2, RoundingMode.HALF_UP));
    }

    /**
     * Bloquea el formulario y lanza el registro. Sincrono sobre el EDT (igual que el resto de la
     * app): mientras dure, Swing no puede despachar un segundo click/Enter sobre este mismo boton,
     * pero ademas se deja deshabilitado explicitamente como segunda barrera (punto 6).
     */
    private void confirmarVentaClick() {
        if (procesando) {
            return;
        }
        procesando = true;

        BigDecimal valorRecibido = null;
        if (esEfectivoSeleccionado()) {
            valorRecibido = parsearRecibido(txtRecibido.getText());
            if (valorRecibido == null || valorRecibido.compareTo(factura.getTotal()) < 0) {
                procesando = false;
                return; //El boton deberia estar deshabilitado en este caso; defensivo.
            }
        }
        ultimoValorRecibido = valorRecibido;

        SriFormaPago sriSeleccionada = (SriFormaPago) cmbFormaPago.getSelectedItem();
        FormaPago formaPagoElegida = new FormaPago(factura.getTotal().setScale(2, RoundingMode.HALF_UP), sriSeleccionada);

        habilitarFormulario(false);
        btnConfirmar.setText("Procesando...");

        try {
            ResultadoCobro resultado = callback.confirmarVenta(formaPagoElegida, valorRecibido);
            ventaRegistrada = true;
            mostrarResultado(resultado);
        } catch (ServicioCodefacException ex) {
            //Rechazo de negocio CONFIRMADO: no se registro nada, es seguro corregir y reintentar
            //sin cerrar esta ventana (el pedido tampoco se toco).
            Logger.getLogger(VentaRapidaCobroDialog.class.getName()).log(Level.SEVERE, null, ex);
            DialogoCodefac.mensaje(new CodefacMsj(ex.getMessage(), CodefacMsj.TipoMensajeEnum.ERROR));
            habilitarFormulario(true);
            actualizarTextoBotonConfirmar();
        } catch (RemoteException ex) {
            //Resultado INCIERTO (igual que ya documentaba VentaRapidaModel.grabar()): no se invita a
            //reintentar desde aqui, se cierra la ventana dejando el pedido intacto para que se
            //verifique manualmente antes de volver a intentar cobrar.
            Logger.getLogger(VentaRapidaCobroDialog.class.getName()).log(Level.SEVERE, null, ex);
            DialogoCodefac.mensaje(new CodefacMsj("No se pudo confirmar si la venta se registro (fallo de comunicacion con el servidor). "
                    + "El pedido se mantiene intacto. ANTES de volver a presionar Cobrar, verifique en el listado de ventas o con un supervisor "
                    + "si esta venta ya quedo registrada, para evitar duplicarla. Detalle tecnico: " + ex.getMessage(),
                    CodefacMsj.TipoMensajeEnum.ERROR));
            dispose();
        } finally {
            procesando = false;
        }
    }

    private void habilitarFormulario(boolean habilitado) {
        cmbFormaPago.setEnabled(habilitado);
        txtRecibido.setEnabled(habilitado);
        btnCancelar.setEnabled(habilitado);
        btnConfirmar.setEnabled(habilitado);
        for (java.awt.Component boton : panelMontosRapidos.getComponents()) {
            boton.setEnabled(habilitado);
        }
    }

    // ===================================================================================
    // Tarjeta RESULTADO
    // ===================================================================================
    private JPanel construirPanelResultado() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(16, 20, 16, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        lblResultadoTitulo = new JLabel("Venta registrada");
        lblResultadoTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblResultadoTitulo.setForeground(COLOR_VERDE_PRIMARIO);
        gbc.insets = new Insets(0, 0, 10, 0);
        panel.add(lblResultadoTitulo, gbc);

        gbc.gridy++;
        lblResultadoDetalle = new JLabel(" ");
        lblResultadoDetalle.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.insets = new Insets(0, 0, 4, 0);
        panel.add(lblResultadoDetalle, gbc);

        gbc.gridy++;
        lblResultadoVuelto = new JLabel(" ");
        lblResultadoVuelto.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.insets = new Insets(4, 0, 16, 0);
        panel.add(lblResultadoVuelto, gbc);

        gbc.gridy++;
        JPanel panelBotones = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 10, 0));
        panelBotones.setBackground(Color.WHITE);

        btnReimprimir = new JButton("Reimprimir");
        btnReimprimir.setFont(new Font("Arial", Font.PLAIN, 13));
        btnReimprimir.setFocusPainted(false);
        btnReimprimir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnReimprimir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                callback.reimprimir(factura);
            }
        });
        panelBotones.add(btnReimprimir);

        btnNuevaVenta = new JButton("Nueva venta");
        btnNuevaVenta.setFont(new Font("Arial", Font.BOLD, 14));
        btnNuevaVenta.setBackground(COLOR_VERDE_PRIMARIO);
        btnNuevaVenta.setForeground(Color.WHITE);
        btnNuevaVenta.setOpaque(true);
        btnNuevaVenta.setBorderPainted(false);
        btnNuevaVenta.setPreferredSize(new Dimension(160, 38));
        btnNuevaVenta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNuevaVenta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                callback.nuevaVenta();
                dispose();
            }
        });
        panelBotones.add(btnNuevaVenta);

        gbc.insets = new Insets(0, 0, 0, 0);
        panel.add(panelBotones, gbc);

        getRootPane().setDefaultButton(btnNuevaVenta);

        return panel;
    }

    private void mostrarResultado(ResultadoCobro resultado) {
        boolean esEfectivo = ultimoValorRecibido != null;
        if (resultado.advertencia != null) {
            lblResultadoTitulo.setText("Venta registrada; impresion pendiente");
            lblResultadoTitulo.setForeground(new Color(180, 120, 0));
        } else {
            lblResultadoTitulo.setText("Venta registrada");
            lblResultadoTitulo.setForeground(COLOR_VERDE_PRIMARIO);
        }

        StringBuilder detalle = new StringBuilder();
        detalle.append("Total: $").append(factura.getTotal().setScale(2, RoundingMode.HALF_UP));
        if (esEfectivo) {
            detalle.append("   ·   Recibido: $").append(ultimoValorRecibido.setScale(2, RoundingMode.HALF_UP));
        }
        if (resultado.advertencia != null) {
            detalle.append("<br>").append(resultado.advertencia);
        }
        lblResultadoDetalle.setText("<html>" + detalle + "</html>");

        if (esEfectivo) {
            BigDecimal vuelto = ultimoValorRecibido.subtract(factura.getTotal()).setScale(2, RoundingMode.HALF_UP);
            lblResultadoVuelto.setText("Entregar vuelto: $" + vuelto);
            lblResultadoVuelto.setVisible(true);
        } else {
            lblResultadoVuelto.setVisible(false);
        }

        cardLayout.show(panelCards, TARJETA_RESULTADO);
        pack();
    }
}
