/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.dialog;

import ec.com.codesoft.codefaclite.controlador.componentes.IconosAviso;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

/**
 * Dialogo de aviso reutilizable, pensado para reemplazar gradualmente a DialogoCodefac.mensaje()
 * (hoy un JOptionPane.showMessageDialog sin estilo propio) empezando por un piloto puntual -ver
 * LoginModel.advertirCajaSessionAbiertaDiaAnterior()-, SIN tocar el comportamiento de los demas
 * dialogos de la aplicacion.
 *
 * Diseño (ver conversacion 2026-11-26 "mejora visual de dialogos de aviso"):
 * - Conserva la barra de titulo nativa de la ventana (solo se moderniza el contenido interior).
 * - Icono de severidad (circulo de color + simbolo blanco, ver IconosAviso) + titulo grande en
 *   negrita, descripcion mas pequeña en gris oscuro, "datos" opcionales en una tarjeta clara,
 *   "detalles" tecnicos opcionales colapsados detras de un enlace "Ver detalles".
 * - Ancho fijo de texto (no la ventana completa, el texto hace wrap dentro de ese ancho) para que
 *   mensajes largos generen mas lineas en vez de una ventana mas ancha.
 * - Si el contenido no cabe en pantalla, el area central hace scroll pero los botones de accion
 *   quedan siempre visibles (estan fuera del area con scroll).
 * - Boton principal solido (verde Codefac, igual que el resto de la app) y boton secundario con
 *   fondo claro + borde visible (no texto plano gris, para que no parezca deshabilitado).
 * - Cerrar con la X o Escape SIEMPRE equivale a la accion secundaria, nunca a la principal.
 *
 * Uso:
 * <pre>
 * new AvisoDialog.Builder(parentWindow)
 *     .tipo(AvisoDialog.TipoAviso.ADVERTENCIA)
 *     .titulo("Caja pendiente de cierre")
 *     .dato("CAJA 1")
 *     .dato("Abierta desde el 25/11/2026")
 *     .descripcion("Las nuevas ventas se agregaran a esta misma sesion. Revisa el cierre para separar los movimientos de cada jornada.")
 *     .accionSecundaria("Más tarde", null)
 *     .accionPrincipal("Revisar cierre", () -&gt; panelPrincipal.crearVentanaCodefac(VentanaEnum.ABRIR_CAJA, false, null))
 *     .build()
 *     .mostrar();
 * </pre>
 *
 * @author Carlos
 */
public class AvisoDialog extends JDialog {

    public enum TipoAviso {
        INFORMACION, ADVERTENCIA, ERROR, EXITO
    }

    private static final Color COLOR_INFORMACION = new Color(41, 128, 185);
    private static final Color COLOR_ADVERTENCIA = new Color(243, 156, 18);
    private static final Color COLOR_ERROR = new Color(192, 57, 43);
    private static final Color COLOR_EXITO = new Color(46, 139, 87);
    private static final Color COLOR_VERDE_PRIMARIO = new Color(46, 139, 87);
    private static final Color COLOR_TEXTO_DESCRIPCION = new Color(68, 68, 68);
    private static final Color COLOR_FONDO_DATOS = new Color(245, 246, 248);
    private static final Color COLOR_BORDE_DATOS = new Color(224, 224, 224);
    private static final Color COLOR_BOTON_SECUNDARIO_FONDO = new Color(241, 242, 244);
    private static final Color COLOR_BOTON_SECUNDARIO_BORDE = new Color(200, 203, 208);
    private static final Color COLOR_BOTON_SECUNDARIO_TEXTO = new Color(51, 51, 51);
    private static final Color COLOR_ENLACE_DETALLES = new Color(41, 128, 185);

    //Ancho de wrap del texto (titulo/descripcion/detalles), no de la ventana completa: el texto
    //adentro hace salto de linea dentro de este ancho en vez de ensanchar el dialogo. Con el icono
    //(44px) + separacion (12px) + margenes (24px x2) esto da un ancho total de ventana de ~440+44+
    //12+48=544px, dentro del rango 480-560 pedido.
    private static final int ANCHO_TEXTO = 440;
    private static final int ANCHO_ICONO = 44;
    private static final int MARGEN = 24;
    //Alto maximo del area con scroll antes de que aparezca una barra de desplazamiento (los botones
    //quedan siempre visibles porque estan fuera de esta area).
    private static final int ALTO_MAXIMO_CONTENIDO = 420;

    private final Runnable accionSecundariaRunnable;
    private boolean detallesExpandidos = false;
    private JPanel panelDetallesContenedor;
    private JLabel enlaceDetalles;
    private JScrollPane scrollContenido;
    private JPanel panelContenido;

    private AvisoDialog(Builder builder) {
        super(builder.parent, ModalityType.APPLICATION_MODAL);
        this.accionSecundariaRunnable = builder.accionSecundariaRunnable;

        setTitle((builder.titulo != null && !builder.titulo.isEmpty()) ? builder.titulo : "Codefac");
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        panelContenido = construirPanelContenido(builder);
        scrollContenido = new JScrollPane(panelContenido);
        scrollContenido.setBorder(BorderFactory.createEmptyBorder());
        scrollContenido.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollContenido.getViewport().setBackground(Color.WHITE);
        scrollContenido.getVerticalScrollBar().setUnitIncrement(16);
        getContentPane().add(scrollContenido, BorderLayout.CENTER);

        JPanel panelBotones = construirPanelBotones(builder);
        getContentPane().add(panelBotones, BorderLayout.SOUTH);

        configurarCierreYTeclado();

        //Calcula el alto real del contenido ya armado y lo limita a ALTO_MAXIMO_CONTENIDO: si el
        //contenido es mas corto no se nota (el scrollpane simplemente no muestra barra), si es mas
        //largo (ej. detalles expandidos) aparece scroll en vez de seguir creciendo la ventana.
        Dimension preferido = panelContenido.getPreferredSize();
        int altoVisible = Math.min(preferido.height, ALTO_MAXIMO_CONTENIDO);
        scrollContenido.setPreferredSize(new Dimension(preferido.width, altoVisible));

        pack();
        setLocationRelativeTo(builder.parent);
    }

    private JPanel construirPanelContenido(Builder builder) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(MARGEN, MARGEN, MARGEN, MARGEN));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        //--- Fila 1: icono + titulo ---
        JPanel filaTitulo = new JPanel(new GridBagLayout());
        filaTitulo.setBackground(Color.WHITE);
        filaTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(0, 0, 0, 12);
        JLabel lblIcono = new JLabel(obtenerIcono(builder.tipo));
        filaTitulo.add(lblIcono, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 0, 0);
        JLabel lblTitulo = new JLabel(envolverHtml(builder.titulo, ANCHO_TEXTO, 21, true, Color.BLACK));
        filaTitulo.add(lblTitulo, gbc);

        panel.add(filaTitulo);

        //--- Datos opcionales (tarjeta clara) ---
        if (!builder.datos.isEmpty()) {
            panel.add(Box.createVerticalStrut(14));
            panel.add(construirPanelDatos(builder.datos));
        }

        //--- Descripcion ---
        if (builder.descripcion != null && !builder.descripcion.isEmpty()) {
            panel.add(Box.createVerticalStrut(14));
            JLabel lblDescripcion = new JLabel(envolverHtml(builder.descripcion, ANCHO_TEXTO + ANCHO_ICONO + 12, 14, false, COLOR_TEXTO_DESCRIPCION));
            lblDescripcion.setAlignmentX(Component.LEFT_ALIGNMENT);
            panel.add(lblDescripcion);
        }

        //--- Detalles opcionales (colapsable) ---
        if (builder.detalles != null && !builder.detalles.isEmpty()) {
            panel.add(Box.createVerticalStrut(10));
            enlaceDetalles = new JLabel("Ver detalles ▸");
            enlaceDetalles.setFont(new Font("Arial", Font.PLAIN, 13));
            enlaceDetalles.setForeground(COLOR_ENLACE_DETALLES);
            enlaceDetalles.setCursor(new Cursor(Cursor.HAND_CURSOR));
            enlaceDetalles.setAlignmentX(Component.LEFT_ALIGNMENT);
            enlaceDetalles.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    alternarDetalles(builder.detalles);
                }
            });
            panel.add(enlaceDetalles);

            panelDetallesContenedor = new JPanel(new BorderLayout());
            panelDetallesContenedor.setBackground(Color.WHITE);
            panelDetallesContenedor.setAlignmentX(Component.LEFT_ALIGNMENT);
            panelDetallesContenedor.setVisible(false);
            panel.add(panelDetallesContenedor);
        }

        return panel;
    }

    private void alternarDetalles(String textoDetalles) {
        detallesExpandidos = !detallesExpandidos;
        if (detallesExpandidos) {
            enlaceDetalles.setText("Ocultar detalles ▾");
            if (panelDetallesContenedor.getComponentCount() == 0) {
                JTextArea areaTexto = new JTextArea(textoDetalles);
                areaTexto.setEditable(false);
                areaTexto.setLineWrap(true);
                areaTexto.setWrapStyleWord(true);
                areaTexto.setFont(new Font("Monospaced", Font.PLAIN, 12));
                areaTexto.setBackground(COLOR_FONDO_DATOS);
                areaTexto.setBorder(new EmptyBorder(10, 10, 10, 10));
                JScrollPane scrollDetalles = new JScrollPane(areaTexto);
                scrollDetalles.setBorder(BorderFactory.createLineBorder(COLOR_BORDE_DATOS));
                scrollDetalles.setPreferredSize(new Dimension(ANCHO_TEXTO + ANCHO_ICONO + 12, 140));
                panelDetallesContenedor.add(scrollDetalles, BorderLayout.CENTER);
                panelDetallesContenedor.setBorder(new EmptyBorder(8, 0, 0, 0));
            }
            panelDetallesContenedor.setVisible(true);
        } else {
            enlaceDetalles.setText("Ver detalles ▸");
            panelDetallesContenedor.setVisible(false);
        }

        //Recalcula el alto visible del scroll de contenido tras expandir/colapsar detalles.
        panelContenido.revalidate();
        Dimension preferido = panelContenido.getPreferredSize();
        int altoVisible = Math.min(preferido.height, ALTO_MAXIMO_CONTENIDO);
        scrollContenido.setPreferredSize(new Dimension(preferido.width, altoVisible));
        pack();
    }

    private static final Color COLOR_DATO_PRINCIPAL = new Color(30, 30, 30);
    private static final Color COLOR_DATO_SECUNDARIO = new Color(95, 98, 102);

    private JPanel construirPanelDatos(List<String> datos) {
        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBackground(COLOR_FONDO_DATOS);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE_DATOS, 1, true),
                new EmptyBorder(10, 14, 10, 14)));
        tarjeta.setAlignmentX(Component.LEFT_ALIGNMENT);
        //Ancho maximo SIN limite propio (a diferencia de antes, que fijaba un numero de pixeles que
        //podia desalinearse del resto del contenido): BoxLayout.Y_AXIS estira cada hijo hasta el
        //ancho real del panel contenedor, que ya esta determinado por el titulo/descripcion en HTML.
        //Asi la tarjeta siempre llega exactamente al mismo margen derecho que el resto del contenido.
        tarjeta.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        int anchoTextoDato = ANCHO_TEXTO + ANCHO_ICONO + 12 - 28; //menos el padding horizontal de la tarjeta (14+14)
        for (int i = 0; i < datos.size(); i++) {
            boolean esPrincipal = (i == 0);
            JLabel linea = new JLabel(envolverHtml(datos.get(i), anchoTextoDato, 13, esPrincipal,
                    esPrincipal ? COLOR_DATO_PRINCIPAL : COLOR_DATO_SECUNDARIO));
            linea.setAlignmentX(Component.LEFT_ALIGNMENT);
            tarjeta.add(linea);
            if (i < datos.size() - 1) {
                tarjeta.add(Box.createVerticalStrut(2));
            }
        }
        return tarjeta;
    }

    private JPanel construirPanelBotones(Builder builder) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, COLOR_BORDE_DATOS),
                new EmptyBorder(14, MARGEN, 14, MARGEN)));

        String textoSecundaria = (builder.textoSecundaria != null && !builder.textoSecundaria.isEmpty()) ? builder.textoSecundaria : "Cerrar";
        JButton botonSecundario = crearBotonSecundario(textoSecundaria);
        botonSecundario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ejecutarSecundariaYCerrar();
            }
        });
        panel.add(botonSecundario);

        if (builder.textoPrincipal != null && !builder.textoPrincipal.isEmpty()) {
            JButton botonPrincipal = crearBotonPrincipal(builder.textoPrincipal);
            botonPrincipal.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    if (builder.accionPrincipalRunnable != null) {
                        builder.accionPrincipalRunnable.run();
                    }
                }
            });
            panel.add(botonPrincipal);
            getRootPane().setDefaultButton(botonPrincipal);
        } else {
            getRootPane().setDefaultButton(botonSecundario);
        }

        return panel;
    }

    private JButton crearBotonPrincipal(String texto) {
        JButton boton = new BotonRedondeado(texto, COLOR_VERDE_PRIMARIO, Color.WHITE, null);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setPreferredSize(new Dimension(Math.max(140, boton.getPreferredSize().width + 24), 38));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return boton;
    }

    private JButton crearBotonSecundario(String texto) {
        //A proposito NO es un boton "plano/fantasma" de solo texto (ver nota de la conversacion:
        //"evita que la accion secundaria parezca deshabilitada"): fondo gris claro + borde visible,
        //para que se note que es clickeable aunque pese menos visualmente que el boton principal.
        JButton boton = new BotonRedondeado(texto, COLOR_BOTON_SECUNDARIO_FONDO, COLOR_BOTON_SECUNDARIO_TEXTO, COLOR_BOTON_SECUNDARIO_BORDE);
        boton.setFont(new Font("Arial", Font.PLAIN, 14));
        boton.setPreferredSize(new Dimension(Math.max(110, boton.getPreferredSize().width + 24), 38));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return boton;
    }

    private void ejecutarSecundariaYCerrar() {
        dispose();
        if (accionSecundariaRunnable != null) {
            accionSecundariaRunnable.run();
        }
    }

    private void configurarCierreYTeclado() {
        //La X y Escape SIEMPRE equivalen a la accion secundaria, nunca a la principal (punto
        //explicito del requerimiento): se enrutan al mismo metodo que el boton "Mas tarde"/"Cerrar".
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ejecutarSecundariaYCerrar();
            }
        });

        JRootPane root = getRootPane();
        root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "cerrarConSecundaria");
        root.getActionMap().put("cerrarConSecundaria", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ejecutarSecundariaYCerrar();
            }
        });
    }

    private Icon obtenerIcono(TipoAviso tipo) {
        int tamanoIcono = ANCHO_ICONO;
        switch (tipo) {
            case ADVERTENCIA:
                return IconosAviso.advertencia(COLOR_ADVERTENCIA, tamanoIcono);
            case ERROR:
                return IconosAviso.error(COLOR_ERROR, tamanoIcono);
            case EXITO:
                return IconosAviso.exito(COLOR_EXITO, tamanoIcono);
            case INFORMACION:
            default:
                return IconosAviso.informacion(COLOR_INFORMACION, tamanoIcono);
        }
    }

    /**
     * Envuelve texto plano en HTML con un ancho fijo de wrap -asi el texto hace salto de linea
     * dentro de ese ancho en vez de ensanchar la ventana completa- escapando los caracteres HTML
     * especiales del texto original para no interpretar nada que venga del mensaje como markup.
     */
    private String envolverHtml(String texto, int anchoPx, int tamanoFuente, boolean negrita, Color color) {
        String escapado = escaparHtml(texto != null ? texto : "");
        String estiloPeso = negrita ? "bold" : "normal";
        String colorHex = String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
        return "<html><body style='width:" + anchoPx + "px; font-family:Arial; font-size:" + tamanoFuente
                + "px; font-weight:" + estiloPeso + "; color:" + colorHex + ";'>" + escapado + "</body></html>";
    }

    private String escaparHtml(String texto) {
        return texto.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\n", "<br>");
    }

    public void mostrar() {
        setVisible(true);
    }

    /**
     * Boton con esquinas ligeramente redondeadas (Swing no trae esto "de fabrica"): se pinta a mano
     * en vez de usar el JButton plano de siempre, para lograr el aspecto "profesional" pedido sin
     * depender de una libreria de UI externa nueva.
     */
    private static class BotonRedondeado extends JButton {

        private final Color colorFondo;
        private final Color colorBorde;

        BotonRedondeado(String texto, Color colorFondo, Color colorTexto, Color colorBorde) {
            super(texto);
            this.colorFondo = colorFondo;
            this.colorBorde = colorBorde;
            setForeground(colorTexto);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setOpaque(false);
        }

        @Override
        protected void paintComponent(java.awt.Graphics g) {
            java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
            g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
            Color fondo = colorFondo;
            if (getModel().isPressed()) {
                fondo = fondo.darker();
            } else if (getModel().isRollover()) {
                fondo = new Color(
                        Math.max(0, fondo.getRed() - 12),
                        Math.max(0, fondo.getGreen() - 12),
                        Math.max(0, fondo.getBlue() - 12));
            }
            g2.setColor(fondo);
            g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
            if (colorBorde != null) {
                g2.setColor(colorBorde);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
            }
            g2.dispose();
            super.paintComponent(g);
        }
    }

    public static class Builder {

        private final Window parent;
        private TipoAviso tipo = TipoAviso.INFORMACION;
        private String titulo = "";
        private String descripcion = "";
        private final List<String> datos = new ArrayList<String>();
        private String detalles;
        private String textoPrincipal;
        private Runnable accionPrincipalRunnable;
        private String textoSecundaria = "Cerrar";
        private Runnable accionSecundariaRunnable;

        public Builder(Window parent) {
            this.parent = parent;
        }

        public Builder tipo(TipoAviso tipo) {
            this.tipo = tipo;
            return this;
        }

        public Builder titulo(String titulo) {
            this.titulo = titulo;
            return this;
        }

        public Builder descripcion(String descripcion) {
            this.descripcion = descripcion;
            return this;
        }

        /**
         * Agrega una linea de datos opcionales (ej. nombre de caja, fecha, responsable). La primera
         * linea agregada se muestra en negrita (pensada para el dato mas importante, ej. el nombre
         * de la caja); el resto en texto normal.
         */
        public Builder dato(String linea) {
            if (linea != null && !linea.isEmpty()) {
                this.datos.add(linea);
            }
            return this;
        }

        /**
         * Texto tecnico/extenso opcional, oculto detras de un enlace "Ver detalles" en vez de
         * mostrarse siempre (y en vez de reducir la letra o ensanchar la ventana para que quepa).
         */
        public Builder detalles(String detalles) {
            this.detalles = detalles;
            return this;
        }

        public Builder accionPrincipal(String texto, Runnable accion) {
            this.textoPrincipal = texto;
            this.accionPrincipalRunnable = accion;
            return this;
        }

        public Builder accionSecundaria(String texto, Runnable accion) {
            this.textoSecundaria = texto;
            this.accionSecundariaRunnable = accion;
            return this;
        }

        public AvisoDialog build() {
            return new AvisoDialog(this);
        }
    }
}
