/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.model;

import ec.com.codesoft.codefaclite.controlador.componentes.CarritoLineaPanel;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ClienteEstablecimientoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.componentes.ComponenteDatosComprobanteElectronicosInterface;
import ec.com.codesoft.codefaclite.controlador.componentes.IconosVentaRapida;
import ec.com.codesoft.codefaclite.controlador.componentes.ProductoTileCard;
import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.controlador.core.swing.InterfazComunicacionPanel;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.utilidades.UtilidadesImagenesCodefac;
import ec.com.codesoft.codefaclite.controlador.interfaces.ControladorVistaIf;
import ec.com.codesoft.codefaclite.controlador.vista.factura.FacturaModelControlador;
import ec.com.codesoft.codefaclite.controlador.vista.factura.ModelControladorAbstract;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ObserverUpdateInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.general.ParametrosClienteEscritorio;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfazPostConstructPanel;
import ec.com.codesoft.codefaclite.facturacion.callback.ClienteFacturaImplComprobante;
import ec.com.codesoft.codefaclite.facturacion.dialog.VentaRapidaCobroDialog;
import ec.com.codesoft.codefaclite.facturacion.nocallback.FacturaRespuestaNoCallBack;
import ec.com.codesoft.codefaclite.facturacion.panel.VentaRapidaPanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.callback.ClienteInterfaceComprobante;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.ComprobanteDataInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CategoriaProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity.TipoEmisionEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FormaPago;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Kardex;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PersonaEstablecimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Presupuesto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Estudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroEstudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModoProcesarEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.OperadorNegocioEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.VentanaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.servidorinterfaz.parameros.CarteraParametro;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ComprobanteServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.FacturacionServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ProductoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.ParametroUtilidades;
import ec.com.codesoft.codefaclite.utilidades.imagen.UtilidadImagen;
import ec.com.codesoft.codefaclite.utilidades.rmi.UtilidadesRmi;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JasperPrint;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JToggleButton;

/**
 * Pantalla de venta rapida (grid de productos por categoria, tipo heladeria/cafeteria).
 * No hereda FacturacionModel/FacturacionPanel: implementa FacturaModelControlador.FacturaModelInterface
 * directamente para compartir el mismo motor de calculo/kardex/cartera sin cargar los ~80 componentes
 * de la pantalla de Facturacion completa (ver analisis de viabilidad y Fase 0/1 previas).
 *
 * Alcance de esta fase (Fase 2): cargar categorias/productos reales y permitir agregar al carrito
 * tocando un tile. El cobro completo (forma de pago, comprobante electronico, impresion) queda para
 * la Fase 4, una vez probado este flujo base.
 *
 * @author Carlos
 */
public class VentaRapidaModel extends VentaRapidaPanel implements
        InterfazPostConstructPanel, ComponenteDatosComprobanteElectronicosInterface,
        FacturaModelControlador.FacturaModelInterface, ControladorVistaIf {

    private FacturaModelControlador controlador;
    private Factura factura;
    private Producto productoSeleccionado;
    private FacturaDetalle facturaDetalleSeleccionado;

    //Campos "staging" que respaldan el detalle que se agrega al carrito. No tienen widget visible propio:
    //Venta Rapida no necesita que el cajero edite cantidad/descuento/valor manualmente en el caso normal.
    private String txtCantidad = "1";
    private String txtDescuento = "";
    private String txtDescripcion = "";
    private String txtValorUnitario = "";
    private EnumSiNo comboIva = EnumSiNo.NO;
    private Boolean checkPorcentajeSeleccion = false;
    private Boolean modoEdicionDetalle = false;

    private List<CategoriaProducto> categoriaList;
    private List<Producto> productosCategoriaActual = new ArrayList<Producto>();
    private boolean procesandoCobro = false;

    //"Deshacer" de la ultima linea eliminada (no interrumpe con un dialogo de confirmacion por
    //cada click en la papelera; en vez de eso, ofrece revertir en una ventana corta de tiempo).
    private FacturaDetalle detalleEliminadoPendiente;
    private int indiceEliminadoPendiente;
    private javax.swing.Timer timerDeshacer;

    /**
     * Tipo de comprobante a grabar. Solo se ofrecen FACTURA y NOTA_VENTA_INTERNA (no NOTA_VENTA a
     * secas): el dispatcher del servidor que arma el XML para firma electronica
     * (ComprobantesService.convertirComprobanteEntityToDataFacturacionElectronica) solo sabe construir
     * datos para NOTA_VENTA_INTERNA, no para NOTA_VENTA -ofrecer esa ultima generaria un documento que
     * fallaria al grabar-. NOTA_VENTA_INTERNA NO pasa por firma electronica SRI (ver grabar(): se salta
     * facturarElectronicamente(...) e imprime localmente), igual que ya hace FacturacionModel.grabar()
     * para este tipo de documento.
     */
    private DocumentoEnum documentoSeleccionado = DocumentoEnum.FACTURA;

    /**
     * Catalogo completo de productos activos de la empresa, cargado UNA vez en iniciar() (igual que
     * categoriaList). Sirve de fuente para la busqueda por texto/codigo (aplicarFiltroBusqueda /
     * procesarBusquedaPorCodigo) para que no quede limitada a la categoria/favoritos abiertos en
     * pantalla -un cajero escaneando un codigo de barras, o escribiendo un nombre, no deberia tener que
     * saber de antemano en que pestaña vive ese producto-. Si se edita/crea un producto en otra pantalla
     * mientras Venta Rapida sigue abierta, esta lista queda desactualizada hasta reabrir la pantalla
     * (mismo tradeoff ya aceptado para categoriaList).
     */
    private List<Producto> productosTodosActivos = new ArrayList<Producto>();

    public VentaRapidaModel() {
    }

    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        controlador = new FacturaModelControlador(session, this, DialogoCodefac.intefaceMensaje);
        controlador.iniciar();
        limpiar();
        cargarCatalogoCompleto();
        cargarCategorias();
        configurarBuscador();

        getBtnCobrar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    grabar();
                } catch (Exception ex) {
                    Logger.getLogger(VentaRapidaModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        getBtnEditarCliente().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarMenuCliente();
            }
        });

        getBtnVaciarPedido().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vaciarPedido();
            }
        });

        getBtnTipoFactura().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seleccionarTipoDocumento(DocumentoEnum.FACTURA);
            }
        });
        getBtnTipoNotaVenta().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seleccionarTipoDocumento(DocumentoEnum.NOTA_VENTA_INTERNA);
            }
        });
        estilizarBotonTipoDocumento(getBtnTipoFactura());
        estilizarBotonTipoDocumento(getBtnTipoNotaVenta());
    }

    /**
     * Cambia el tipo de comprobante a grabar (ver javadoc de documentoSeleccionado). Reutiliza la misma
     * conversion de IVA que ya usa Facturacion completa al cambiar su combo de documento (ver
     * FacturacionModel.cambiarDocumentoVenta/cargarFacturaDesdeProforma): Nota de Venta Interna puede
     * mostrar el precio "todo incluido" (segun el parametro NVI_TOTAL_CON_IVA/NOTA_VENTA_INTERNA_IVA),
     * asi que si ya hay productos en el carrito hay que convertir los detalles, no solo el encabezado.
     * No se copian esos metodos (son privados y cargan de mas: merge remoto de proforma, descuentos de
     * factura que esta pantalla no usa); se llama directo a los 2 metodos ya compartidos en
     * FacturaModelControlador (verificarFacturaConNotaVentaInterna / invertirCalculoDetallesAFactura).
     */
    private void seleccionarTipoDocumento(DocumentoEnum documento) {
        DocumentoEnum documentoAnterior = this.documentoSeleccionado;
        this.documentoSeleccionado = documento;
        estilizarBotonTipoDocumento(getBtnTipoFactura());
        estilizarBotonTipoDocumento(getBtnTipoNotaVenta());

        if (documento.equals(documentoAnterior) || factura.getDetalles() == null || factura.getDetalles().isEmpty()) {
            return;
        }
        if (DocumentoEnum.NOTA_VENTA_INTERNA.equals(documento)) {
            controlador.verificarFacturaConNotaVentaInterna(factura);
        } else if (DocumentoEnum.NOTA_VENTA_INTERNA.equals(documentoAnterior)) {
            boolean agregarValorIva = ParametroUtilidades.comparar(session.getEmpresa(), ParametroCodefac.NVI_TOTAL_CON_IVA, EnumSiNo.NO);
            controlador.invertirCalculoDetallesAFactura(factura, agregarValorIva);
        }
        cargarDatosDetalles();
        controlador.cargarTotales();
    }

    /**
     * Misma fuente de verdad que Facturacion completa (ver FacturacionModel.iniciarValoresIniciales),
     * acotada a los 2 tipos que esta pantalla ofrece: si el parametro esta configurado con otro valor
     * (ej. PROFORMA, que no aplica a una venta rapida), se usa FACTURA como respaldo seguro.
     */
    private DocumentoEnum obtenerDocumentoDefectoVentaRapida() {
        DocumentoEnum configurado = ParametroUtilidades.obtenerValorBaseDatos(session.getEmpresa(), ParametroCodefac.DOCUMENTO_DEFECTO_VISTA_FACTURA, DocumentoEnum.FACTURA);
        if (DocumentoEnum.FACTURA.equals(configurado) || DocumentoEnum.NOTA_VENTA_INTERNA.equals(configurado)) {
            return configurado;
        }
        return DocumentoEnum.FACTURA;
    }

    private void estilizarBotonTipoDocumento(javax.swing.JToggleButton boton) {
        if (boton.isSelected()) {
            boton.setBackground(COLOR_VERDE_PRIMARIO);
            boton.setForeground(Color.WHITE);
            boton.setOpaque(true);
        } else {
            boton.setBackground(null);
            boton.setForeground(new Color(90, 90, 90));
            boton.setOpaque(false);
        }
    }

    private void vaciarPedido() {
        if (factura.getDetalles().isEmpty()) {
            return;
        }
        if (!DialogoCodefac.dialogoPregunta("Vaciar pedido", "¿Seguro que desea quitar todos los productos del pedido actual?", DialogoCodefac.MENSAJE_ADVERTENCIA)) {
            return;
        }
        descartarDeshacerPendiente();
        factura.getDetalles().clear();
        cargarDatosDetalles();
        controlador.cargarTotales();
    }

    /**
     * Placeholder "Buscar producto o escanear codigo..." (Swing no tiene placeholder nativo en
     * JTextField), filtro en vivo sobre la categoria/favoritos activos, y Enter funciona como
     * lector de codigo de barras: si el texto calza exacto con un codigo, agrega ese producto
     * directo al carrito (igual que pasaria un lector fisico) en vez de solo filtrar.
     */
    private void configurarBuscador() {
        final javax.swing.JTextField txtBuscar = getTxtBuscar();
        txtBuscar.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtBuscar.getText().equals(VentaRapidaPanel.PLACEHOLDER_BUSCADOR)) {
                    txtBuscar.setText("");
                    txtBuscar.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtBuscar.getText().trim().isEmpty()) {
                    txtBuscar.setForeground(Color.GRAY);
                    txtBuscar.setText(VentaRapidaPanel.PLACEHOLDER_BUSCADOR);
                }
            }
        });

        txtBuscar.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                aplicarFiltroBusqueda();
                actualizarVisibilidadBotonLimpiar();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                aplicarFiltroBusqueda();
                actualizarVisibilidadBotonLimpiar();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                //No aplica a JTextField plano.
            }
        });

        txtBuscar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    procesarBusquedaPorCodigo(txtBuscar.getText().trim());
                }
            }
        });

        getBtnLimpiarBusqueda().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtBuscar.setText("");
                txtBuscar.requestFocusInWindow();
            }
        });
    }

    /**
     * El boton "x" de limpiar busqueda solo tiene sentido mientras hay texto real escrito por el
     * usuario (no el placeholder gris, que no cuenta como busqueda activa).
     */
    private void actualizarVisibilidadBotonLimpiar() {
        String texto = getTxtBuscar().getText();
        boolean hayTextoReal = !texto.isEmpty() && !texto.equals(VentaRapidaPanel.PLACEHOLDER_BUSCADOR);
        getBtnLimpiarBusqueda().setVisible(hayTextoReal);
    }

    /**
     * Filtra sobre TODO el catalogo activo (productosTodosActivos), no solo la categoria/favoritos
     * abiertos: un cajero escribiendo un nombre no deberia tener que saber de antemano en que pestaña
     * vive ese producto. Al limpiar el texto se vuelve a mostrar solo la categoria actual, como antes.
     */
    private void aplicarFiltroBusqueda() {
        String texto = getTxtBuscar().getText().trim();
        if (texto.isEmpty() || texto.equals(VentaRapidaPanel.PLACEHOLDER_BUSCADOR)) {
            renderizarGridProductos(productosCategoriaActual);
            return;
        }
        String textoBusqueda = texto.toLowerCase();
        List<Producto> filtrados = new ArrayList<Producto>();
        for (Producto producto : productosTodosActivos) {
            boolean coincideNombre = producto.getNombre() != null && producto.getNombre().toLowerCase().contains(textoBusqueda);
            boolean coincideCodigo = producto.getCodigoPersonalizado() != null && producto.getCodigoPersonalizado().toLowerCase().contains(textoBusqueda);
            if (coincideNombre || coincideCodigo) {
                filtrados.add(producto);
            }
        }
        renderizarGridProductos(filtrados);
    }

    /**
     * Busca tambien en TODO el catalogo (productosTodosActivos), igual que aplicarFiltroBusqueda: un
     * lector de codigo de barras fisico no sabe (ni le importa) que categoria esta abierta en pantalla.
     */
    private void procesarBusquedaPorCodigo(String texto) {
        if (texto.isEmpty() || texto.equals(VentaRapidaPanel.PLACEHOLDER_BUSCADOR)) {
            return;
        }
        for (Producto producto : productosTodosActivos) {
            if (texto.equalsIgnoreCase(producto.getCodigoPersonalizado())) {
                agregarProductoAlCarrito(producto);
                getTxtBuscar().setText("");
                getTxtBuscar().requestFocusInWindow();
                return;
            }
        }
    }

    /**
     * Guia visual discreta (no modal, para no interrumpir un escaneo rapido de varios productos
     * seguidos): reutiliza la nota de "Precios finales..." bajo las categorias para avisar que el
     * producto agregado no pertenece a la categoria/favoritos actualmente visibles en pantalla.
     */
    private void avisarProductoAgregadoFueraDeCategoria(Producto producto) {
        String categoriaNombre = null;
        if (producto.getCatalogoProducto() != null && producto.getCatalogoProducto().getCategoriaProducto() != null) {
            categoriaNombre = producto.getCatalogoProducto().getCategoriaProducto().getNombre();
        }
        String mensaje = "Agregado \"" + producto.getNombre() + "\""
                + (categoriaNombre != null ? " (categoria: " + categoriaNombre + ") - fuera de esta vista" : " - fuera de esta categoria/favoritos");
        getLblNotaPrecios().setText(mensaje);
        getLblNotaPrecios().setForeground(COLOR_VERDE_PRIMARIO);
        javax.swing.Timer timer = new javax.swing.Timer(3500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getLblNotaPrecios().setText("Precios finales • Impuestos incluidos");
                getLblNotaPrecios().setForeground(new Color(150, 150, 150));
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    /**
     * Busca el indice actual de un detalle dentro de factura.getDetalles() (en vez de guardar un
     * indice de fila fijo): como las filas del carrito se reconstruyen completas en cada refresco,
     * el indice puede cambiar si se agrega o quita otra linea mientras tanto.
     */
    private void quitarDetalle(FacturaDetalle detalle) {
        //IMPORTANTE: FacturaDetalle.equals() compara solo por id, y TODAS las lineas sin grabar
        //comparten id=null -son "iguales" entre si para equals()-. indexOf(Object)/remove(Object)
        //usan equals() por dentro, asi que con mas de una linea en el carrito podian ubicar/borrar la
        //linea equivocada (siempre la primera "igual" encontrada) en vez de la que el usuario toco en
        //la papelera. Se busca y se quita por IDENTIDAD (==) para evitar eso.
        int indice = indiceDeDetallePorIdentidad(detalle);
        if (indice < 0) {
            return;
        }
        factura.getDetalles().remove(indice);
        ofrecerDeshacerEliminacion(detalle, indice);
        cargarDatosDetalles();
        controlador.cargarTotales();
    }

    private int indiceDeDetallePorIdentidad(FacturaDetalle detalle) {
        List<FacturaDetalle> detalles = factura.getDetalles();
        for (int i = 0; i < detalles.size(); i++) {
            if (detalles.get(i) == detalle) {
                return i;
            }
        }
        return -1;
    }

    /**
     * En vez de interrumpir cada eliminacion con un dialogo de confirmacion, se ofrece revertir
     * durante una ventana corta (igual que Gmail/Android): la fila eliminada se recuerda por unos
     * segundos y aparece un banner "Deshacer" al tope del carrito (ver cargarDatosDetalles).
     */
    private void ofrecerDeshacerEliminacion(FacturaDetalle detalle, int indiceOriginal) {
        if (timerDeshacer != null) {
            timerDeshacer.stop();
        }
        detalleEliminadoPendiente = detalle;
        indiceEliminadoPendiente = indiceOriginal;
        timerDeshacer = new javax.swing.Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                detalleEliminadoPendiente = null;
                cargarDatosDetalles();
            }
        });
        timerDeshacer.setRepeats(false);
        timerDeshacer.start();
    }

    private void deshacerEliminacion() {
        if (detalleEliminadoPendiente == null) {
            return;
        }
        if (timerDeshacer != null) {
            timerDeshacer.stop();
        }
        int indice = Math.max(0, Math.min(indiceEliminadoPendiente, factura.getDetalles().size()));
        factura.getDetalles().add(indice, detalleEliminadoPendiente);
        detalleEliminadoPendiente = null;
        cargarDatosDetalles();
        controlador.cargarTotales();
    }

    /**
     * Descarta cualquier "deshacer" pendiente sin mostrarlo. Se llama cuando el carrito cambia de
     * forma mas drastica (vaciar, limpiar tras cobrar) y resucitar esa linea ya no tendria sentido.
     */
    private void descartarDeshacerPendiente() {
        if (timerDeshacer != null) {
            timerDeshacer.stop();
        }
        detalleEliminadoPendiente = null;
    }

    private void cambiarCantidadDetalle(FacturaDetalle detalle, BigDecimal nuevaCantidad) {
        if (nuevaCantidad == null || nuevaCantidad.compareTo(BigDecimal.ZERO) <= 0) {
            DialogoCodefac.mensaje(new CodefacMsj("La cantidad debe ser mayor a cero.", CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
            cargarDatosDetalles();
            return;
        }
        detalle.setCantidad(nuevaCantidad);
        detalle.calcularTotalesDetallesFactura();
        cargarDatosDetalles();
        controlador.cargarTotales();
    }

    /**
     * Si la ultima linea agregada (siempre la mas reciente en factura.getDetalles()) corresponde al
     * mismo producto/precio que una linea anterior del carrito, las fusiona sumando la cantidad en la
     * linea anterior en vez de dejar dos filas separadas -asi se comporta como un POS estandar
     * (Square/Odoo/Toast): tocar el mismo producto incrementa cantidad, no duplica fila-.
     * No fusiona items con KardexItemEspecifico (series/garantia: cada unidad debe quedar identificable
     * por separado) ni lineas cuyo precio unitario difiera (p.ej. el PVP cambio a mitad de venta por un
     * cambio de cliente), para no alterar silenciosamente el precio ya cobrado de la linea anterior.
     */
    private void fusionarLineaRepetida() {
        List<FacturaDetalle> detalles = factura.getDetalles();
        if (detalles == null || detalles.size() < 2) {
            return;
        }
        int indiceNueva = detalles.size() - 1;
        FacturaDetalle nueva = detalles.get(indiceNueva);
        if (nueva.getKardexItemEspecifico() != null) {
            return;
        }
        for (int i = 0; i < indiceNueva; i++) {
            FacturaDetalle existente = detalles.get(i);
            if (!coincideParaFusionarLinea(existente, nueva)) {
                continue;
            }
            BigDecimal cantidadFusionada = existente.getCantidad().add(nueva.getCantidad());
            //IMPORTANTE: FacturaDetalle.equals() compara solo por id, y dos detalles nuevos sin
            //grabar comparten id=null -quedan "iguales" entre si para equals()-, asi que hay que
            //quitar por INDICE (no por objeto/List.remove(Object), que usa equals() por dentro):
            //de lo contrario se arriesga a borrar la linea existente en vez de la recien agregada.
            detalles.remove(indiceNueva);
            cambiarCantidadDetalle(existente, cantidadFusionada);
            return;
        }
    }

    private boolean coincideParaFusionarLinea(FacturaDetalle existente, FacturaDetalle nueva) {
        if (existente.getKardexItemEspecifico() != null) {
            return false;
        }
        if (existente.getReferenciaId() == null || nueva.getReferenciaId() == null
                || !existente.getReferenciaId().equals(nueva.getReferenciaId())) {
            return false;
        }
        if (existente.getPrecioUnitario() == null || nueva.getPrecioUnitario() == null
                || existente.getPrecioUnitario().compareTo(nueva.getPrecioUnitario()) != 0) {
            return false;
        }
        Long loteExistente = (existente.getLote() != null) ? existente.getLote().getId() : null;
        Long loteNueva = (nueva.getLote() != null) ? nueva.getLote().getId() : null;
        return (loteExistente == null) ? (loteNueva == null) : loteExistente.equals(loteNueva);
    }

    @Override
    public void nuevo() {
        limpiar();
        controlador.nuevo();
    }

    /**
     * NOTA (2026-11-20): si servicio.grabar(...) lanza RemoteException, el resultado es incierto -no
     * sabemos si el servidor ya aplico la venta y solo se perdio la respuesta-. Hoy solo se evita
     * invitar a reintentar a ciegas (ver el catch de RemoteException en registrarVentaConfirmada);
     * resolverlo de verdad requiere un identificador de operacion estable + proteccion de duplicados
     * en servidor. Diseno completo documentado en docs/codex-context.md ("Pendiente de diseño:
     * idempotencia de FacturacionServiceIf.grabar").
     *
     * Flujo (requerimiento 2026-11-21, "Cobrar -> Ventana de cobro -> Confirmar venta -> Resultado"):
     * grabar() solo valida las precondiciones que YA eran necesarias antes (carrito, matriz, punto de
     * emision, tipo electronico) y abre VentaRapidaCobroDialog. La venta ya NO se registra aqui ni
     * detras de la pregunta "Esta seguro?" (eliminada, solo para esta pantalla): se registra
     * unicamente cuando el cajero pulsa "Confirmar venta" dentro de esa ventana, via
     * registrarVentaConfirmada(...). Cancelar o cerrar la ventana de cobro no llama a ese metodo, asi
     * que el pedido (productos, cantidades, cliente, tipo de comprobante) queda intacto.
     */
    @Override
    public void grabar() {
        if (procesandoCobro) {
            return;
        }
        if (factura.getDetalles().isEmpty()) {
            DialogoCodefac.mensaje(new CodefacMsj("No se puede cobrar sin productos en el carrito.", CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
            return;
        }
        if (session.getMatriz() == null) {
            DialogoCodefac.mensaje(new CodefacMsj("No tiene configurado un establecimiento principal o matriz.", CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
            return;
        }
        final PuntoEmision puntoEmision = obtenerPuntoEmisionVentaRapida();
        if (puntoEmision == null) {
            DialogoCodefac.mensaje(new CodefacMsj("No se puede cobrar sin un punto de venta configurado para esta sucursal.", CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
            return;
        }
        if (!puntoEmision.getTipoFacturacionEnum().equals(TipoEmisionEnum.ELECTRONICA)) {
            //TODO Fase 4 (siguiente iteracion): el Punto de Emision esta configurado como facturacion manual/lote
            //(TipoEmisionEnum.NORMAL), que en Facturacion completa pasa por FacturacionServiceIf.grabarLote(...)
            //con numeracion preimpresa. Venta Rapida todavia no replica esa variante.
            DialogoCodefac.mensaje(new CodefacMsj("Este punto de emision esta configurado como facturacion manual (no electronica); esa variante todavia no esta soportada en Venta Rapida.", CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
            return;
        }

        VentaRapidaCobroDialog dialogoCobro = new VentaRapidaCobroDialog(null, factura, documentoSeleccionado, getLblCliente().getText(),
                new VentaRapidaCobroDialog.Callback() {
            @Override
            public VentaRapidaCobroDialog.ResultadoCobro confirmarVenta(FormaPago formaPagoElegida, BigDecimal valorRecibido) throws RemoteException, ServicioCodefacException {
                return registrarVentaConfirmada(puntoEmision, formaPagoElegida, valorRecibido);
            }

            @Override
            public void reimprimir(Factura facturaRegistrada) {
                reimprimirVentaRegistrada(facturaRegistrada);
            }

            @Override
            public void nuevaVenta() {
                limpiar();
            }
        });
        dialogoCobro.setLocationRelativeTo(null);
        dialogoCobro.setVisible(true);
    }

    /**
     * Registra la venta confirmada desde VentaRapidaCobroDialog (boton "Confirmar venta"). Mismo
     * contenido que antes tenia grabar() desde "setear forma de pago" en adelante, mas la separacion
     * explicita entre PAGO APLICADO y EFECTIVO RECIBIDO (requerimiento 2026-11-21 punto 4):
     *
     * - formaPagoElegida.getTotal() SIEMPRE es factura.getTotal() (lo arma asi
     *   VentaRapidaCobroDialog.confirmarVentaClick(), no es editable por el cajero) -antes de este
     *   cambio, el dialogo generico de Forma de Pago dejaba escribir libremente ese "Valor", y si el
     *   cajero ponia ahi el efectivo entregado (ej. $10.00 para cubrir $6.90) ese MISMO numero se
     *   registraba como pago aplicado/entrada de caja, inflandolo-.
     * - valorRecibido (puede ser null si la forma de pago no es Efectivo) es el efectivo que entrego
     *   el cliente; se guarda en Factura.valorRecibido, el mismo campo que YA usaba FacturacionModel
     *   para su "Valor Recibido" de Comanda/restaurante (ver getTxtValorRecibido() ahi) y que el
     *   ticket impreso YA consume como "pl_recibido"/"pl_vuelto" (FacturaModelControlador.
     *   getMapParametrosReporte) -no son campos nuevos, solo no estaban conectados en Venta Rapida-.
     *   El vuelto no se persiste aparte: se deriva siempre de Factura.calcularVuelto() (recibido -
     *   total), igual que en el resto del sistema.
     *
     * Solo lanza ServicioCodefacException/RemoteException para los 2 casos en que el registro mismo
     * (servicio.grabar) no se complete con certeza; un fallo de impresion (NVI) o de firma
     * electronica DESPUES de un registro exitoso se devuelve como advertencia en ResultadoCobro, no
     * como excepcion -la venta ya quedo grabada en ambos casos-.
     */
    private VentaRapidaCobroDialog.ResultadoCobro registrarVentaConfirmada(PuntoEmision puntoEmision, FormaPago formaPagoElegida, BigDecimal valorRecibido) throws RemoteException, ServicioCodefacException {
        procesandoCobro = true;
        try {
            factura.getFormaPagos().clear();
            factura.addFormaPago(formaPagoElegida);
            factura.setValorRecibido((valorRecibido != null) ? valorRecibido.setScale(2, RoundingMode.HALF_UP) : null);

            //Datos obligatorios de la factura (equivalente reducido de FacturacionModel.setearValoresDefaultFactura,
            //sin los campos especificos de Comanda/transporte/credito que esta pantalla no maneja).
            factura.setEmpresa(session.getEmpresa());
            factura.setUsuario(session.getUsuario());
            factura.setSucursalEmpresa(session.getSucursal());
            factura.setContribuyenteEspecial(session.getEmpresa().getContribuyenteEspecial());
            factura.setObligadoLlevarContabilidad(session.getEmpresa().getObligadoLlevarContabilidad());
            factura.setDireccionEstablecimiento(session.getSucursal().getDirecccion());
            if (!UtilidadesTextos.verificarNullOVacio(session.getSucursal().getDireccionMatriz())) {
                factura.setDireccionMatriz(session.getSucursal().getDireccionMatriz());
            } else {
                factura.setDireccionMatriz(session.getMatriz().getDirecccion());
            }
            factura.setVentaCreditoEnum(EnumSiNo.NO);
            factura.setDiasCredito(0);
            factura.setFechaEmision(new java.util.Date());
            factura.setPuntoEmision(puntoEmision.getPuntoEmision());
            factura.setPuntoEmisionId(puntoEmision.getId());
            factura.setPuntoEstablecimiento(new BigDecimal(session.getSucursal().getCodigoSucursal().toString()));
            factura.setNombreTipoDocumento(TipoDocumentoEnum.INVENTARIO.getNombre());
            factura.setCodigoTipoDocumento(TipoDocumentoEnum.INVENTARIO.getCodigo());
            factura.setCodigoDocumento(documentoSeleccionado.getCodigo());
            factura.setTipoFacturacion(puntoEmision.getTipoFacturacion());

            FacturacionServiceIf servicio = ServiceFactory.getFactory().getFacturacionServiceIf();
            CarteraParametro carteraParametro = new CarteraParametro(false, 0, 0);
            factura = servicio.grabar(factura, null, carteraParametro, ModoProcesarEnum.NORMAL);

            String advertencia = null;
            //Nota de Venta Interna NO es un comprobante electronico del SRI (ver DocumentoEnum.NOTA_VENTA_INTERNA:
            //comprobanteElectronico=false) y NO debe pasar por facturarElectronicamente(...) -eso dispara la
            //firma/autorizacion real ante el SRI-. Esto replica exactamente la rama que ya usa
            //FacturacionModel.grabar() (buscar "documentoEnum.equals(DocumentoEnum.NOTA_VENTA_INTERNA)" ahi):
            //para NVI solo se imprime localmente el "Comprobante de Venta Interna", nunca se firma.
            if (DocumentoEnum.NOTA_VENTA_INTERNA.equals(documentoSeleccionado)) {
                try {
                    FacturaModelControlador.imprimirComprobanteVenta(factura, FacturacionModel.NOMBRE_REPORTE_FACTURA_INTERNA, true, session, panelPadre, getEstadoFormularioEnum());
                } catch (Exception exImpresion) {
                    //La venta YA quedo registrada (servicio.grabar() ya retorno); un fallo de
                    //impresora aqui NO debe reportarse como si la venta no se hubiera guardado
                    //(requerimiento 2026-11-21 punto 7: "Venta registrada; impresion pendiente").
                    Logger.getLogger(VentaRapidaModel.class.getName()).log(Level.SEVERE, null, exImpresion);
                    advertencia = "La venta se registro correctamente, pero hubo un problema al imprimir. Use \"Reimprimir\".";
                }
            } else {
                try {
                    facturarElectronicamente(factura);
                } catch (RemoteException ex) {
                    Logger.getLogger(VentaRapidaModel.class.getName()).log(Level.SEVERE, null, ex);
                    advertencia = "La venta se grabo correctamente, pero hubo un problema generando el comprobante electronico: " + ex.getMessage();
                }
            }

            return new VentaRapidaCobroDialog.ResultadoCobro(factura, advertencia);
        } finally {
            procesandoCobro = false;
        }
    }

    /**
     * Reimprime el comprobante de una venta YA registrada (boton "Reimprimir" de
     * VentaRapidaCobroDialog) sin volver a llamar grabar()/facturarElectronicamente(): para NVI
     * repite el mismo reporte de ticket usado al grabar; para comprobantes electronicos reutiliza el
     * RIDE ya autorizado por su claveAcceso -mismo patron que FacturacionModel.imprimir(), opcion
     * "Ride": NUNCA se vuelve a firmar/autorizar, solo se vuelve a renderizar el PDF ya generado-.
     *
     * Limitacion conocida (no resuelta en esta iteracion): para comprobantes electronicos la firma y
     * la impresion automatica ocurren en un callback ASINCRONO (ClienteFacturaImplComprobante, con su
     * propio panel "Monitor de comprobantes"); por eso registrarVentaConfirmada no puede saber en el
     * momento de la confirmacion si ESA impresion automatica especifica tuvo exito o no -solo puede
     * saber si la venta en si quedo grabada-. El estado real de firma/impresion de ese comprobante se
     * sigue viendo en el Monitor de comprobantes, como ya pasaba antes de este cambio.
     */
    private void reimprimirVentaRegistrada(Factura facturaRegistrada) {
        try {
            if (DocumentoEnum.NOTA_VENTA_INTERNA.equals(facturaRegistrada.getCodigoDocumentoEnum())) {
                FacturaModelControlador.imprimirComprobanteVenta(facturaRegistrada, FacturacionModel.NOMBRE_REPORTE_FACTURA_INTERNA, true, session, panelPadre, getEstadoFormularioEnum());
                return;
            }
            String claveAcceso = facturaRegistrada.getClaveAcceso();
            if (claveAcceso == null || claveAcceso.isEmpty()) {
                DialogoCodefac.mensaje(new CodefacMsj("El comprobante electronico todavia se esta procesando (ver el Monitor de comprobantes); intente reimprimir en unos segundos.", CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
                return;
            }
            byte[] byteReporte = ServiceFactory.getFactory().getComprobanteServiceIf().getReporteComprobante(facturaRegistrada, claveAcceso, facturaRegistrada.getEmpresa());
            JasperPrint jasperPrint = (JasperPrint) UtilidadesRmi.deserializar(byteReporte);
            panelPadre.crearReportePantalla(jasperPrint, facturaRegistrada.getPreimpreso());
        } catch (RemoteException ex) {
            Logger.getLogger(VentaRapidaModel.class.getName()).log(Level.SEVERE, null, ex);
            DialogoCodefac.mensaje(new CodefacMsj("No se pudo reimprimir: " + ex.getMessage(), CodefacMsj.TipoMensajeEnum.ERROR));
        } catch (IOException ex) {
            Logger.getLogger(VentaRapidaModel.class.getName()).log(Level.SEVERE, null, ex);
            DialogoCodefac.mensaje(new CodefacMsj("No se pudo reimprimir: " + ex.getMessage(), CodefacMsj.TipoMensajeEnum.ERROR));
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(VentaRapidaModel.class.getName()).log(Level.SEVERE, null, ex);
            DialogoCodefac.mensaje(new CodefacMsj("No se pudo reimprimir: " + ex.getMessage(), CodefacMsj.TipoMensajeEnum.ERROR));
        }
    }

    /**
     * Firma/autoriza el comprobante electronico y dispara la impresion automatica del RIDE al terminar
     * (via ClienteFacturaImplComprobante, generalizada en esta misma fase para no depender de FacturacionModel).
     */
    private void facturarElectronicamente(Factura facturaProcesando) throws RemoteException {
        ComprobanteDataInterface comprobanteData = FacturaModelControlador.obtenerComprobanteData(facturaProcesando);
        ClienteInterfaceComprobante cic = new ClienteFacturaImplComprobante(this, facturaProcesando, false);
        ComprobanteServiceIf comprobanteServiceIf = ServiceFactory.getFactory().getComprobanteServiceIf();

        if (ParametrosClienteEscritorio.tipoClienteEnum.equals(ParametrosClienteEscritorio.TipoClienteSwingEnum.REMOTO)) {
            cic = null;
        }

        comprobanteServiceIf.procesarComprobante(comprobanteData, facturaProcesando, session.getUsuario(), cic);

        if (ParametrosClienteEscritorio.tipoClienteEnum.equals(ParametrosClienteEscritorio.TipoClienteSwingEnum.REMOTO)) {
            FacturaRespuestaNoCallBack respuestaNoCallBack = new FacturaRespuestaNoCallBack(factura, this, true);
            respuestaNoCallBack.iniciar();
        }
    }

    private PuntoEmision obtenerPuntoEmisionVentaRapida() {
        try {
            List<PuntoEmision> puntos = ServiceFactory.getFactory().getPuntoVentaServiceIf().obtenerActivosPorSucursal(session.getSucursal());
            return (puntos != null && !puntos.isEmpty()) ? puntos.get(0) : null;
        } catch (RemoteException ex) {
            Logger.getLogger(VentaRapidaModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(VentaRapidaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void editar() {
        throw new UnsupportedOperationException("Venta Rapida no permite editar una venta ya realizada desde esta pantalla.");
    }

    @Override
    public void eliminar() {
        throw new UnsupportedOperationException("Venta Rapida no permite eliminar desde esta pantalla.");
    }

    @Override
    public void imprimir() {
        //El ticket/RIDE de la ultima venta cobrada ya se imprime automaticamente al autorizar el
        //comprobante electronico (ver facturarElectronicamente -> ClienteFacturaImplComprobante).
        //Esto es para RE-imprimir manualmente desde el boton de la barra, pendiente para una iteracion futura.
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void actualizar() {
        throw new UnsupportedOperationException("Venta Rapida no maneja edicion de ventas ya grabadas.");
    }

    /**
     * Reutiliza el hook generico de cierre de GeneralPanelModel.internalFrameClosing (todo panel
     * ya arranca con estadoFormulario=ESTADO_GRABAR al crearse, ver GeneralPanelModel linea ~1739):
     * si esto devuelve false, esa ventana pide confirmacion antes de cerrar. La implementacion por
     * defecto (reflexion sobre JTextField/JTextArea) no aplica aqui porque el pedido vive en
     * factura.getDetalles(), no en campos de texto del formulario.
     */
    @Override
    public boolean salirSinGrabar() {
        return factura == null || factura.getDetalles() == null || factura.getDetalles().isEmpty();
    }

    @Override
    public void limpiar() {
        descartarDeshacerPendiente();
        this.factura = new Factura();
        this.factura.setDetalles(new ArrayList<FacturaDetalle>());
        limpiarIngresoDetalleVista();
        asignarClienteConsumidorFinal();
        //Por seguridad, cada venta nueva arranca en el tipo configurado por defecto (mismo parametro
        //que usa Facturacion completa) en vez de arrastrar silenciosamente la seleccion de la venta
        //anterior -un cajero no deberia quedar "atascado" en Nota de venta por olvido-.
        DocumentoEnum documentoDefecto = obtenerDocumentoDefectoVentaRapida();
        getBtnTipoFactura().setSelected(DocumentoEnum.FACTURA.equals(documentoDefecto));
        getBtnTipoNotaVenta().setSelected(DocumentoEnum.NOTA_VENTA_INTERNA.equals(documentoDefecto));
        seleccionarTipoDocumento(documentoDefecto);
        cargarDatosDetalles();
        cargarTotalesVista();
    }

    /**
     * Venta Rapida arranca siempre con "Consumidor Final" asignado -igual al checkbox homonimo de
     * Facturacion- porque el servidor exige un cliente para poder grabar. El cajero puede cambiarlo
     * desde el lapiz junto al nombre del cliente (mostrarMenuCliente), que reutiliza los mismos
     * dialogos de busqueda/creacion de cliente que usa Facturacion completa.
     */
    private void asignarClienteConsumidorFinal() {
        try {
            List<PersonaEstablecimiento> resultados = ServiceFactory.getFactory().getPersonaEstablecimientoServiceIf()
                    .buscarActivoPorIdentificacion(Persona.IDENTIFICACION_CONSUMIDOR_FINAL, session.getEmpresa());
            PersonaEstablecimiento establecimiento;
            if (!resultados.isEmpty()) {
                establecimiento = resultados.get(0);
            } else {
                Persona consumidorFinal = ServiceFactory.getFactory().getPersonaServiceIf().crearConsumidorFinal(session.getEmpresa());
                establecimiento = consumidorFinal.getEstablecimientos().get(0);
                //Por seguridad: al crear el Consumidor Final por primera vez, la referencia de vuelta
                //establecimiento->persona a veces no viene poblada desde el servidor.
                if (establecimiento.getPersona() == null) {
                    establecimiento.setPersona(consumidorFinal);
                }
            }
            cargarCliente(establecimiento);
        } catch (RemoteException ex) {
            Logger.getLogger(VentaRapidaModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(VentaRapidaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Menu con las 3 formas de resolver el cliente de la venta, todas reutilizando flujos que ya
     * existen en el resto del sistema (no se construye ningun dialogo nuevo):
     * - Volver a Consumidor Final (el estado por defecto).
     * - Buscar un cliente existente (ClienteEstablecimientoBusquedaDialogo, igual que Facturacion).
     * - Crear un cliente nuevo (pantalla VentanaEnum.CLIENTE como dialogo, igual que Facturacion).
     */
    private void mostrarMenuCliente() {
        javax.swing.JPopupMenu menu = new javax.swing.JPopupMenu();

        javax.swing.JMenuItem itemConsumidorFinal = new javax.swing.JMenuItem("Consumidor final");
        itemConsumidorFinal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                asignarClienteConsumidorFinal();
            }
        });
        menu.add(itemConsumidorFinal);

        javax.swing.JMenuItem itemBuscar = new javax.swing.JMenuItem("Buscar cliente...");
        itemBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirBusquedaCliente();
            }
        });
        menu.add(itemBuscar);

        javax.swing.JMenuItem itemCrear = new javax.swing.JMenuItem("Crear cliente nuevo...");
        itemCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirCrearCliente();
            }
        });
        menu.add(itemCrear);

        menu.show(getBtnEditarCliente(), 0, getBtnEditarCliente().getHeight());
    }

    private void abrirBusquedaCliente() {
        ClienteEstablecimientoBusquedaDialogo clienteBusquedaDialogo = new ClienteEstablecimientoBusquedaDialogo(session);
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(clienteBusquedaDialogo);
        buscarDialogoModel.setVisible(true);
        PersonaEstablecimiento resultado = (PersonaEstablecimiento) buscarDialogoModel.getResultado();
        if (resultado != null) {
            cargarCliente(resultado);
        }
    }

    private void abrirCrearCliente() {
        Object[] parametros = {OperadorNegocioEnum.CLIENTE, ""};
        panelPadre.crearDialogoCodefac(new ObserverUpdateInterface<Persona>() {
            @Override
            public void updateInterface(Persona entity) {
                if (entity != null && entity.getEstablecimientos() != null && !entity.getEstablecimientos().isEmpty()) {
                    cargarCliente(entity.getEstablecimientos().get(0));
                }
            }
        }, VentanaEnum.CLIENTE, false, parametros, this);
    }

    @Override
    public String getURLAyuda() {
        return null;
    }

    @Override
    public List<String> getPerfilesPermisos() {
        return null;
    }

    @Override
    public InterfaceModelFind obtenerDialogoBusqueda() {
        return null;
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        //No aplica: Venta Rapida siempre inicia una venta nueva, no consulta ventas anteriores desde aqui.
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        Map<Integer, Boolean> permisos = new HashMap<Integer, Boolean>();
        permisos.put(GeneralPanelInterface.BOTON_NUEVO, true);
        permisos.put(GeneralPanelInterface.BOTON_GRABAR, true);
        //Deshabilitado a proposito: imprimir() lanza UnsupportedOperationException (no hay reimpresion
        //implementada todavia). Antes quedaba en true y el boton de la barra general parecia funcionar
        //pero no hacia nada (la excepcion se traga en un log, GeneralPanelModel.java linea ~1238).
        permisos.put(GeneralPanelInterface.BOTON_IMPRIMIR, false);
        permisos.put(GeneralPanelInterface.BOTON_BUSCAR, false);
        permisos.put(GeneralPanelInterface.BOTON_ELIMINAR, false);
        permisos.put(GeneralPanelInterface.BOTON_AYUDA, false);
        permisos.put(GeneralPanelInterface.BOTON_REFRESCAR, false);
        return permisos;
    }

    @Override
    public ModelControladorAbstract getControladorVista() {
        return controlador;
    }

    //===================================================================================
    // Carga de categorias/productos (grid)
    //===================================================================================
    private void cargarCatalogoCompleto() {
        try {
            productosTodosActivos = ServiceFactory.getFactory().getProductoServiceIf().obtenerTodosActivos(session.getEmpresa());
            if (productosTodosActivos == null) {
                productosTodosActivos = new ArrayList<Producto>();
            }
        } catch (RemoteException ex) {
            Logger.getLogger(VentaRapidaModel.class.getName()).log(Level.SEVERE, null, ex);
            productosTodosActivos = new ArrayList<Producto>();
        }
    }

    private void cargarCategorias() {
        try {
            List<CategoriaProducto> todasLasCategorias = ServiceFactory.getFactory().getCategoriaProductoServiceIf().obtenerTodosPorEmpresa(session.getEmpresa());
            //Filtro defensivo: categorias sin nombre no generan un boton usable en la barra de categorias.
            categoriaList = new ArrayList<CategoriaProducto>();
            for (CategoriaProducto categoria : todasLasCategorias) {
                if (!UtilidadesTextos.verificarNullOVacio(categoria.getNombre())) {
                    categoriaList.add(categoria);
                }
            }
        } catch (RemoteException ex) {
            Logger.getLogger(VentaRapidaModel.class.getName()).log(Level.SEVERE, null, ex);
            categoriaList = new ArrayList<CategoriaProducto>();
        }

        getPanelCategorias().removeAll();
        ButtonGroup grupoCategorias = new ButtonGroup();

        //Pestaña fija de favoritos (Producto.FAVORITO), siempre primero y seleccionada por defecto.
        final JToggleButton botonFavoritos = new JToggleButton("★ Favoritos");
        estilizarBotonCategoria(botonFavoritos, "Favoritos");
        grupoCategorias.add(botonFavoritos);
        botonFavoritos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarProductosFavoritos();
            }
        });
        getPanelCategorias().add(botonFavoritos);

        for (final CategoriaProducto categoria : categoriaList) {
            final JToggleButton boton = new JToggleButton();
            estilizarBotonCategoria(boton, categoria.getNombre());
            if (!UtilidadesTextos.verificarNullOVacio(categoria.getImagenPath())) {
                try {
                    BufferedImage iconoCategoria = UtilidadImagen.resizeImage(
                            UtilidadesImagenesCodefac.buscarImagenServidorInputStream(session.getEmpresa(), categoria.getImagenPath()), 20, 20);
                    if (iconoCategoria != null) {
                        boton.setIcon(new ImageIcon(iconoCategoria));
                    }
                } catch (Exception ex) {
                    Logger.getLogger(VentaRapidaModel.class.getName()).log(Level.WARNING, "No se pudo cargar el icono de la categoria " + categoria.getNombre(), ex);
                }
            }
            grupoCategorias.add(boton);
            boton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    cargarProductosPorCategoria(categoria);
                }
            });
            getPanelCategorias().add(boton);
        }
        getPanelCategorias().revalidate();
        getPanelCategorias().repaint();

        botonFavoritos.setSelected(true);
        cargarProductosFavoritos();
    }

    private static final int MAX_CARACTERES_CATEGORIA = 16;

    /**
     * Da a los botones de categoria un tamaño comodo (altura ~44px para uso tactil), trunca
     * nombres largos con "..." mostrando el nombre completo en el tooltip (en vez de empujar a
     * las demas categorias fuera de vista), y resalta la categoria activa con el verde
     * principal en vez de solo el estado por defecto del Look&amp;Feel.
     */
    private void estilizarBotonCategoria(final JToggleButton boton, String nombreCompleto) {
        String textoVisible = nombreCompleto;
        if (textoVisible.length() > MAX_CARACTERES_CATEGORIA) {
            textoVisible = textoVisible.substring(0, MAX_CARACTERES_CATEGORIA - 1) + "…";
        }
        boton.setText(textoVisible);
        boton.setToolTipText(nombreCompleto);
        boton.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
        boton.setFocusPainted(false);
        //El padding va en el BORDE (EmptyBorder), no solo en el margin: algunos Look&Feel con
        //skin propio (ej. JTattoo) calculan su propio preferredSize para JToggleButton sin sumar
        //el margin de AbstractButton, asi que con solo setMargin el boton seguia quedando chico
        //y con el texto pegado al borde. El border.getBorderInsets() si se respeta siempre.
        boton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        boton.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(VentaRapidaPanel.COLOR_BORDE, 1, true),
                javax.swing.BorderFactory.createEmptyBorder(12, 18, 12, 18)));
        boton.setBackground(java.awt.Color.WHITE);

        boton.addItemListener(new java.awt.event.ItemListener() {
            @Override
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                aplicarEstiloSeleccionCategoria(boton);
            }
        });
        aplicarEstiloSeleccionCategoria(boton);
    }

    private void aplicarEstiloSeleccionCategoria(JToggleButton boton) {
        if (boton.isSelected()) {
            boton.setBackground(VentaRapidaPanel.COLOR_VERDE_PRIMARIO);
            boton.setForeground(java.awt.Color.WHITE);
            boton.setFont(boton.getFont().deriveFont(java.awt.Font.BOLD));
        } else {
            boton.setBackground(java.awt.Color.WHITE);
            boton.setForeground(java.awt.Color.BLACK);
            boton.setFont(boton.getFont().deriveFont(java.awt.Font.PLAIN));
        }
    }

    /**
     * Filtra sobre productosTodosActivos (ya cargado en iniciar() via cargarCatalogoCompleto) en vez de
     * volver a pedir el catalogo completo al servidor cada vez que se toca la pestaña "Favoritos".
     */
    private void cargarProductosFavoritos() {
        List<Producto> productos = new ArrayList<Producto>();
        for (Producto producto : productosTodosActivos) {
            if (EnumSiNo.SI.equals(producto.getFavoritoEnum())) {
                productos.add(producto);
            }
        }
        productosCategoriaActual = productos;
        renderizarGridProductos(productos);
    }

    private void cargarProductosPorCategoria(CategoriaProducto categoria) {
        List<Producto> productos;
        try {
            productos = ServiceFactory.getFactory().getProductoServiceIf().buscarPorCategoria(categoria);
        } catch (RemoteException ex) {
            Logger.getLogger(VentaRapidaModel.class.getName()).log(Level.SEVERE, null, ex);
            return;
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(VentaRapidaModel.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        productosCategoriaActual = productos;
        renderizarGridProductos(productos);
    }

    private void renderizarGridProductos(List<Producto> productos) {
        getPanelGridProductos().removeAll();
        for (final Producto producto : productos) {
            final ProductoTileCard tile = new ProductoTileCard();
            tile.setNombre(producto.getNombre());
            //Precio final con impuestos incluidos: es lo que el cliente realmente paga por unidad,
            //coherente con el importe que luego se ve en el carrito (que tambien incluye IVA).
            tile.setPrecio("$" + controlador.obtenerPrecioVentaConImpuestos(producto).setScale(2, RoundingMode.HALF_UP));
            tile.setDatoAsociado(producto);
            cargarImagenTile(tile, producto);
            tile.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    agregarProductoAlCarrito(producto);
                }
            });
            getPanelGridProductos().add(tile);
        }
        getPanelGridProductos().revalidate();
        getPanelGridProductos().repaint();
    }

    /**
     * Precio final (con IVA e ICE incluidos si aplica) que paga el cliente por una unidad del
     * producto. Se usa tanto en el tile como base de referencia para el precio unitario mostrado
     * en el carrito, para que nunca se vean dos cifras distintas para "lo mismo".
     */
    /**
     * Carga la imagen del tile de forma sincrona; si el catalogo de productos crece mucho,
     * esta carga deberia pasar a asincrona para no congelar la UI al cambiar de categoria.
     * Si el producto no tiene foto propia, usa el icono configurado en su categoria (si tiene).
     * Si tampoco hay icono de categoria, usa un icono neutro de producto generico (no deja la
     * tarjeta vacia ni un texto feo de "sin imagen").
     */
    private void cargarImagenTile(ProductoTileCard tile, Producto producto) {
        String archivo = producto.getImagen();
        if (UtilidadesTextos.verificarNullOVacio(archivo)) {
            archivo = obtenerImagenCategoriaDeProducto(producto);
        }
        if (UtilidadesTextos.verificarNullOVacio(archivo)) {
            tile.setImagen(IconosVentaRapida.productoGenerico(new Color(190, 190, 190), 64));
            return;
        }
        try {
            BufferedImage imagen = UtilidadImagen.resizeImage(
                    UtilidadesImagenesCodefac.buscarImagenServidorInputStream(session.getEmpresa(), archivo), 130, 100);
            if (imagen != null) {
                tile.setImagen(new ImageIcon(imagen));
            } else {
                tile.setImagen(IconosVentaRapida.productoGenerico(new Color(190, 190, 190), 64));
            }
        } catch (Exception ex) {
            Logger.getLogger(VentaRapidaModel.class.getName()).log(Level.WARNING, "No se pudo cargar la imagen del producto " + producto.getCodigoPersonalizado(), ex);
            tile.setImagen(IconosVentaRapida.productoGenerico(new Color(190, 190, 190), 64));
        }
    }

    private String obtenerImagenCategoriaDeProducto(Producto producto) {
        if (producto.getCatalogoProducto() != null && producto.getCatalogoProducto().getCategoriaProducto() != null) {
            return producto.getCatalogoProducto().getCategoriaProducto().getImagenPath();
        }
        return null;
    }

    //===================================================================================
    // Flujo de agregar producto al carrito (el punto central de reutilizacion del motor)
    //===================================================================================
    private void agregarProductoAlCarrito(Producto producto) {
        try {
            Kardex kardex = controlador.obtenerKardexDesdeProducto(producto);
            controlador.resolverYAgregarProducto(EnumSiNo.SI, kardex, producto);
            controlador.agregarDetallesFactura(facturaDetalleSeleccionado, null, documentoSeleccionado, kardex, EnumSiNo.NO, BigDecimal.ONE, null, null);
            fusionarLineaRepetida();
            //La busqueda por texto/codigo ahora cubre todo el catalogo (ver aplicarFiltroBusqueda /
            //procesarBusquedaPorCodigo), asi que el producto agregado puede no pertenecer a la
            //categoria/favoritos actualmente visibles en pantalla -avisar para que no parezca magia-.
            if (!productosCategoriaActual.contains(producto)) {
                avisarProductoAgregadoFueraDeCategoria(producto);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(VentaRapidaModel.class.getName()).log(Level.SEVERE, null, ex);
            DialogoCodefac.mensaje(new CodefacMsj(ex.getMessage(), CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(VentaRapidaModel.class.getName()).log(Level.SEVERE, null, ex);
            DialogoCodefac.mensaje(new CodefacMsj(ex.getMessage(), CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
        }
    }

    //===================================================================================
    // ComponenteDatosComprobanteElectronicosInterface (necesario para reusar FacturaModelControlador;
    // el llenado real de comprobante electronico/impresion queda para la Fase 4)
    //===================================================================================
    @Override
    public ComprobanteEntity getComprobante() {
        return factura;
    }

    @Override
    public Empresa getEmpresa() {
        return session.getEmpresa();
    }

    @Override
    public InterfazComunicacionPanel getPanelPadre() {
        return panelPadre;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ComprobanteAdicional> getDatosAdicionales() {
        return (List<ComprobanteAdicional>) (Object) factura.getDatosAdicionales();
    }

    @Override
    public ClienteInterfaceComprobante getInterfaceComprobante() throws RemoteException {
        return new ClienteFacturaImplComprobante(this, factura, false);
    }

    @Override
    public ComprobanteDataInterface obtenerComprobanteData() {
        return FacturaModelControlador.obtenerComprobanteData(factura);
    }

    //===================================================================================
    // InterfazPostConstructPanel
    //===================================================================================
    @Override
    public void postConstructorExterno(Object[] parametros) {
        //No aplica: Venta Rapida no se abre desde otra pantalla enviando parametros.
    }

    //===================================================================================
    // FacturaModelControlador.FacturaModelInterface
    //===================================================================================
    @Override
    public DocumentoEnum obtenerDocumentoSeleccionado() {
        return documentoSeleccionado;
    }

    @Override
    public TipoDocumentoEnum obtenerTipoDocumentoSeleccionado() {
        return TipoDocumentoEnum.INVENTARIO;
    }

    @Override
    public Producto obtenerProductoSeleccionado() {
        return productoSeleccionado;
    }

    @Override
    public Presupuesto obtenerPresupuestoSeleccionado() {
        return null;
    }

    @Override
    public RubroEstudiante obtenerRubroSeleccionado() {
        return null;
    }

    @Override
    public void setProductoSeleccionado(Producto producto) {
        this.productoSeleccionado = producto;
    }

    @Override
    public void cargarPrecios(Producto producto) {
        //No aplica: no hay combo de PVP visible en Venta Rapida, se usa siempre el precio por defecto del producto.
    }

    @Override
    public void cargarPreciosPorcentaje(List<BigDecimal> descuentos, Boolean porcentajes) {
        //No aplica: sin combo de descuentos visible en esta pantalla.
    }

    @Override
    public void cargarPresentaciones(Producto producto) {
        //No aplica: sin combo de presentaciones visible en esta pantalla.
    }

    @Override
    public void seleccionarPresentacion(String codigoPresentacion) {
        //No aplica.
    }

    @Override
    public void cargarEtiquetaStock(BigDecimal stock) {
        //TODO: se podria mostrar como badge sobre el tile en una iteracion futura.
    }

    @Override
    public void setearValoresProducto(BigDecimal valorUnitario, String descripcion, String codigo, CatalogoProducto catologoProducto) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void cargarDatosDetalleVista(BigDecimal valorUnitario, BigDecimal descuento, String descripcion, String codigo) {
        this.txtValorUnitario = (valorUnitario != null) ? valorUnitario.toString() : "";
        this.txtDescuento = (descuento != null) ? descuento.toString() : "";
        this.txtDescripcion = descripcion;
    }

    @Override
    public void habilitarComboIva(Boolean opcion) {
        //No aplica: sin combo de IVA visible en esta pantalla.
    }

    @Override
    public void setComboIva(EnumSiNo enumSiNo) {
        this.comboIva = enumSiNo;
    }

    @Override
    public void setTxtValorUnitario(String valorUnitario) {
        this.txtValorUnitario = valorUnitario;
    }

    @Override
    public String obtenerTxtDescuento() {
        return (txtDescuento != null) ? txtDescuento : "";
    }

    @Override
    public String obtenerTxtCantidad() {
        return txtCantidad;
    }

    @Override
    public String obtenerTxtDescripcion() {
        return txtDescripcion;
    }

    @Override
    public String obtenerTxtValorUnitario() {
        return txtValorUnitario;
    }

    @Override
    public Boolean obtenerChkReservado() {
        return false;
    }

    @Override
    public EnumSiNo obtenerComboIva() {
        return comboIva;
    }

    @Override
    public Factura obtenerFactura() {
        return factura;
    }

    @Override
    public Boolean obtenerCheckPorcentajeSeleccion() {
        return checkPorcentajeSeleccion;
    }

    @Override
    public void setCheckPorcentajeSeleccion(Boolean seleccion) {
        this.checkPorcentajeSeleccion = seleccion;
    }

    @Override
    public Long obtenerKardexId() {
        return null;
    }

    @Override
    public void limpiarComboPrecioVenta() {
        //No aplica.
    }

    @Override
    public void limpiarComboPresentaciones() {
        //No aplica.
    }

    @Override
    public void focoTxtCodigoDetalle() {
        //No aplica: sin campo de codigo manual en esta pantalla.
    }

    @Override
    public void setearCantidadTxt(String cantidad) {
        this.txtCantidad = cantidad;
    }

    @Override
    public void setearDescripcionTxt(String descripcion) {
        this.txtDescripcion = descripcion;
    }

    @Override
    public void setearValorUnitarioTxt(String valorUnitario) {
        this.txtValorUnitario = valorUnitario;
    }

    @Override
    public void setearDescuentoTxt(String descuento) {
        this.txtDescuento = descuento;
    }

    @Override
    public void setearCodigoDetalleTxt(String codigoDetalle) {
        //No aplica.
    }

    @Override
    public void cargarTotalesVista() {
        //factura.getTotal() queda null hasta que se llame calcularTotalesDesdeDetalles() al menos una vez
        //(ej. una factura recien creada en limpiar()/nuevo()), por eso el null-check explicito aqui.
        BigDecimal total = (factura != null) ? factura.getTotal() : null;
        if (total == null) {
            total = BigDecimal.ZERO;
        }
        total = total.setScale(2, RoundingMode.HALF_UP);
        getLblTotalValor().setText("$" + total);

        //factura.getSubtotalSinImpuestos() SOLO es el subtotal de los detalles con ivaPorcentaje=0 (ver
        //ComprobanteVentaNotaCreditoAbstract.calcularTotalesConPrecioUnitario/calcularTotalesDesdeDetalles);
        //el subtotal pre-impuestos de los detalles QUE SI llevan IVA vive en getSubtotalImpuestos(). Sumando
        //ambos se obtiene el subtotal general (todos los detalles, antes de impuestos) que se quiere mostrar aqui.
        BigDecimal subtotalCero = (factura != null) ? factura.getSubtotalSinImpuestos() : null;
        BigDecimal subtotalConIva = (factura != null) ? factura.getSubtotalImpuestos() : null;
        BigDecimal subtotal = (subtotalCero != null ? subtotalCero : BigDecimal.ZERO)
                .add(subtotalConIva != null ? subtotalConIva : BigDecimal.ZERO);
        getLblSubtotalValor().setText("$" + subtotal.setScale(2, RoundingMode.HALF_UP));

        BigDecimal iva = (factura != null) ? factura.getIva() : null;
        if (iva == null) {
            iva = BigDecimal.ZERO;
        }
        getLblIvaValor().setText("$" + iva.setScale(2, RoundingMode.HALF_UP));

        BigDecimal unidades = BigDecimal.ZERO;
        boolean hayProductos = factura != null && factura.getDetalles() != null && !factura.getDetalles().isEmpty();
        if (hayProductos) {
            for (FacturaDetalle detalle : factura.getDetalles()) {
                if (detalle.getCantidad() != null) {
                    unidades = unidades.add(detalle.getCantidad());
                }
            }
        }
        BigDecimal unidadesSinDecimales = unidades.setScale(0, RoundingMode.HALF_UP);
        getLblUnidades().setText(unidadesSinDecimales + (unidadesSinDecimales.compareTo(BigDecimal.ONE) == 0 ? " unidad" : " unidades"));

        getBtnCobrar().setText("Cobrar $" + total);
        getBtnCobrar().setEnabled(hayProductos && !procesandoCobro);
        getBtnVaciarPedido().setEnabled(hayProductos);
    }

    @Override
    public void cargarFormasPagoTabla() {
        //TODO Fase 4: tabla/panel de formas de pago para el cobro completo.
    }

    /**
     * Banner transitorio ("Producto eliminado  Deshacer") insertado como primera fila del carrito
     * mientras detalleEliminadoPendiente no sea null. Se reconstruye en cada cargarDatosDetalles()
     * en vez de ser un componente fijo del .form para no reservar espacio vacio cuando no aplica.
     */
    private javax.swing.JPanel construirBannerDeshacer() {
        javax.swing.JPanel banner = new javax.swing.JPanel(new java.awt.BorderLayout());
        banner.setBackground(new Color(255, 248, 225));
        banner.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)),
                javax.swing.BorderFactory.createEmptyBorder(10, 14, 10, 10)));

        JLabel lblTexto = new JLabel("Producto eliminado");
        lblTexto.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 13));
        banner.add(lblTexto, java.awt.BorderLayout.CENTER);

        JButton btnDeshacer = new JButton("DESHACER");
        btnDeshacer.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 12));
        btnDeshacer.setForeground(VentaRapidaPanel.COLOR_VERDE_PRIMARIO);
        btnDeshacer.setFocusPainted(false);
        btnDeshacer.setBorderPainted(false);
        btnDeshacer.setContentAreaFilled(false);
        btnDeshacer.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDeshacer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deshacerEliminacion();
            }
        });
        banner.add(btnDeshacer, java.awt.BorderLayout.EAST);
        return banner;
    }

    @Override
    public void cargarDatosDetalles() {
        getCarritoLista().removeAll();
        if (detalleEliminadoPendiente != null) {
            getCarritoLista().add(construirBannerDeshacer());
        }
        if (factura != null && factura.getDetalles() != null) {
            //No se agrupan lineas del mismo producto entre si (cada click en el tile crea su propia
            //linea): asi nunca se mezclan por accidente lineas que a futuro puedan diferir en
            //variante/nota/precio especial, aunque hoy sea el mismo producto repetido.
            for (final FacturaDetalle detalle : factura.getDetalles()) {
                final CarritoLineaPanel fila = new CarritoLineaPanel();
                fila.setNombre(detalle.getDescripcion());
                BigDecimal cantidad = (detalle.getCantidad() != null) ? detalle.getCantidad() : BigDecimal.ONE;
                BigDecimal totalFinal = (detalle.getTotalFinal() != null) ? detalle.getTotalFinal() : BigDecimal.ZERO;
                BigDecimal precioUnitarioConIva = (cantidad.compareTo(BigDecimal.ZERO) != 0)
                        ? totalFinal.divide(cantidad, 2, RoundingMode.HALF_UP)
                        : BigDecimal.ZERO;
                fila.setPrecioUnitarioTexto("$" + precioUnitarioConIva + " c/u");
                fila.setCantidadTexto(cantidad.stripTrailingZeros().toPlainString());
                fila.setImporteTexto("$" + totalFinal.setScale(2, RoundingMode.HALF_UP));
                fila.setDatoAsociado(detalle);

                fila.setOnMenos(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        BigDecimal actual = (detalle.getCantidad() != null) ? detalle.getCantidad() : BigDecimal.ONE;
                        if (actual.compareTo(BigDecimal.ONE) <= 0) {
                            quitarDetalle(detalle);
                        } else {
                            cambiarCantidadDetalle(detalle, actual.subtract(BigDecimal.ONE));
                        }
                    }
                });
                fila.setOnMas(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        BigDecimal actual = (detalle.getCantidad() != null) ? detalle.getCantidad() : BigDecimal.ONE;
                        cambiarCantidadDetalle(detalle, actual.add(BigDecimal.ONE));
                    }
                });
                fila.setOnCantidadCambiada(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            cambiarCantidadDetalle(detalle, new BigDecimal(e.getActionCommand().trim()));
                        } catch (NumberFormatException ex) {
                            cargarDatosDetalles(); //Texto invalido: redibuja con el valor original
                        }
                    }
                });
                fila.setOnEliminar(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        quitarDetalle(detalle);
                    }
                });

                getCarritoLista().add(fila);
            }
        }
        getCarritoLista().revalidate();
        getCarritoLista().repaint();
    }

    @Override
    public Boolean validarIngresoDetalle() {
        return true;
    }

    @Override
    public Integer filaSeleccionadaTablaDetalle() {
        return -1;
    }

    @Override
    public void seleccionarFilaTablaDetalle(int filaSeleccionada) {
        //No aplica: sin edicion de filas en el carrito por ahora.
    }

    @Override
    public void setFacturaDetalleSeleccionado(FacturaDetalle facturaDetalle) {
        this.facturaDetalleSeleccionado = facturaDetalle;
    }

    @Override
    public Boolean getModoEdicionDetalle() {
        return modoEdicionDetalle;
    }

    @Override
    public void setModoEdicionDetalle(Boolean modoEdicionDetalle) {
        this.modoEdicionDetalle = modoEdicionDetalle;
    }

    @Override
    public void limpiarIngresoDetalleVista() {
        this.productoSeleccionado = null;
        this.facturaDetalleSeleccionado = null;
        this.txtCantidad = "1";
        this.txtDescuento = "";
        this.txtDescripcion = "";
        this.txtValorUnitario = "";
        this.comboIva = EnumSiNo.NO;
        this.checkPorcentajeSeleccion = false;
        this.modoEdicionDetalle = false;
    }

    @Override
    public Boolean isPagoConCartera() {
        return false;
    }

    @Override
    public Estudiante getEStudiante() {
        return null;
    }

    @Override
    public void setearCostoDetalleTxt(String cantidad) {
        //No aplica: sin campo de costo visible en esta pantalla.
    }

    @Override
    public void setearFechaCaducidadTxt(String fechaCaducidad) {
        //No aplica: sin campo de fecha de caducidad visible en esta pantalla.
    }

    @Override
    public void cargarCliente(PersonaEstablecimiento cliente) {
        if (cliente == null) {
            return;
        }
        Persona persona = cliente.getPersona();
        factura.setCliente(persona);
        //factura.getSucursal() aqui es (a pesar del nombre) el establecimiento del CLIENTE, no el
        //de la empresa; el SRI exige su direccion (FacturacionService.validacionInicialFacturar).
        factura.setSucursal(cliente);
        if (persona != null) {
            Persona.TipoIdentificacionEnum tipoIdentificacionEnum = persona.getTipoIdentificacionEnum();
            factura.setTipoIdentificacionCodigoSri((tipoIdentificacionEnum != null) ? tipoIdentificacionEnum.getCodigoSriVenta() : "");
            factura.setRazonSocial(persona.getRazonSocial());
            factura.setIdentificacion(persona.getIdentificacion());
        }
        //BUG real encontrado 2026-11-20: cargarCliente nunca actualizaba lblCliente, asi que la
        //etiqueta se quedaba fija en "Consumidor final" (el texto puesto en el .form) sin importar
        //que factura.setCliente(...) si cambiara correctamente por dentro -el cliente buscado/creado
        //SI quedaba asignado a la factura, pero visualmente no se notaba ningun cambio-.
        getLblCliente().setText((persona != null && !UtilidadesTextos.verificarNullOVacio(persona.getRazonSocial()))
                ? persona.getRazonSocial() : "Consumidor final");
        //Igual que FacturacionModel.cargarCliente: si el cliente tiene un pvp por defecto distinto
        //(lista de precios especial), usarlo para los PRODUCTOS NUEVOS que se agreguen de ahora en
        //adelante. Las lineas que ya estaban en el pedido NO se recalculan retroactivamente -ese es
        //el mismo comportamiento que tiene Facturacion completa hoy, no algo nuevo de esta pantalla-
        //asi que tampoco cambia el total ni el boton Cobrar. Lo que si se refresca es el PRECIO DE
        //LAS TARJETAS del grid (si cambio el pvp activo), sin volver a consultar el servidor: usa la
        //misma lista de productos ya cargada (productosCategoriaActual).
        controlador.seleccionarPvpDefecto(persona);
        renderizarGridProductos(productosCategoriaActual);
    }

    @Override
    public void setPresupuestoSeleccionado(Presupuesto presupuestoSeleccionado) {
        //No aplica.
    }

    @Override
    public void seleccionarPvpPorNombre(String nombrePvp) {
        //No aplica: sin combo de PVP visible en esta pantalla.
    }

    @Override
    public void mostrarEtiquetasAhorro(Boolean activar) {
        //No aplica.
    }

    @Override
    public FacturaDetalle obtenerFacturaDetalle() {
        return facturaDetalleSeleccionado;
    }

}
