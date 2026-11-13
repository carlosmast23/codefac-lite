package ec.com.codesoft.codefaclite.main.panel;

import ec.com.codesoft.codefaclite.main.model.noticias.NoticiaData;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

/**
 * Dialogo que se muestra una sola vez al arrancar cuando hay noticias nuevas sin ver
 * (ver NoticiasInicioUtil). Lista todas las noticias nuevas a la vez, con detalle al costado.
 */
public class NoticiaDialog extends JDialog {

    private static final Color COLOR_BORDE = new Color(210, 220, 232);
    private static final Color COLOR_TITULO = new Color(46, 78, 125);
    private static final Color COLOR_URGENTE = new Color(178, 42, 42);

    private final List<NoticiaData> noticias;
    private NoticiaData noticiaSeleccionada;
    private JLabel lblTituloValor;
    private JLabel lblFechaValor;
    private JLabel lblVigenciaValor;
    private JTextArea txtMensaje;
    private JList<NoticiaData> listaNoticias;
    private boolean detalleInicializado;

    public NoticiaDialog(Frame parent, List<NoticiaData> noticias) {
        super(parent, "Noticias Codefac", true);
        this.noticias = (noticias != null) ? noticias : new ArrayList<NoticiaData>();
        this.noticiaSeleccionada = this.noticias.isEmpty() ? null : this.noticias.get(0);
        initComponents();
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        setLayout(new BorderLayout(0, 0));
        getContentPane().setBackground(Color.WHITE);

        JPanel panelPrincipal = new JPanel(new BorderLayout(0, 12));
        panelPrincipal.setBackground(Color.WHITE);
        panelPrincipal.setBorder(new EmptyBorder(14, 14, 14, 14));

        JPanel panelCabecera = new JPanel(new BorderLayout());
        panelCabecera.setBackground(new Color(245, 248, 252));
        panelCabecera.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE),
                new EmptyBorder(12, 14, 12, 14)));

        JLabel lblTituloPrincipal = new JLabel("Tienes noticias nuevas");
        lblTituloPrincipal.setFont(new Font("Arial", Font.BOLD, 20));
        lblTituloPrincipal.setForeground(COLOR_TITULO);
        panelCabecera.add(lblTituloPrincipal, BorderLayout.NORTH);

        JLabel lblSubtitulo = new JLabel("Revisa las novedades publicadas por Codefac.");
        lblSubtitulo.setBorder(new EmptyBorder(6, 0, 0, 0));
        panelCabecera.add(lblSubtitulo, BorderLayout.CENTER);

        panelPrincipal.add(panelCabecera, BorderLayout.NORTH);

        JPanel panelDetalle = crearPanelDetalle();
        JPanel panelLista = crearPanelLista();

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelLista, panelDetalle);
        splitPane.setBorder(BorderFactory.createEmptyBorder());
        splitPane.setDividerSize(6);
        splitPane.setResizeWeight(0.32d);
        splitPane.setBackground(Color.WHITE);
        panelPrincipal.add(splitPane, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        panelBotones.setBackground(Color.WHITE);
        panelBotones.setBorder(new EmptyBorder(4, 0, 0, 0));
        JButton btnContinuar = new JButton("Continuar");
        btnContinuar.addActionListener(e -> dispose());
        getRootPane().setDefaultButton(btnContinuar);
        getRootPane().registerKeyboardAction(
                e -> dispose(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JPanel.WHEN_IN_FOCUSED_WINDOW);
        panelBotones.add(btnContinuar);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        add(panelPrincipal, BorderLayout.CENTER);

        if (noticiaSeleccionada != null) {
            listaNoticias.setSelectedValue(noticiaSeleccionada, true);
        } else {
            actualizarDetalle(null);
        }
        pack();
        setMinimumSize(new Dimension(820, 480));
    }

    private JPanel crearPanelLista() {
        JPanel panelLista = new JPanel(new BorderLayout(0, 8));
        panelLista.setBackground(Color.WHITE);
        panelLista.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(COLOR_BORDE),
                "Noticias",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 12),
                new Color(70, 70, 70)));

        listaNoticias = new JList<NoticiaData>(noticias.toArray(new NoticiaData[0]));
        listaNoticias.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaNoticias.setFont(new Font("Arial", Font.PLAIN, 12));
        listaNoticias.setBackground(Color.WHITE);
        listaNoticias.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof NoticiaData) {
                    NoticiaData noticiaData = (NoticiaData) value;
                    String prefijo = noticiaData.esUrgente() ? "[URGENTE] " : "";
                    label.setText(prefijo + obtenerTexto(noticiaData.getTitulo()));
                    if (!isSelected && noticiaData.esUrgente()) {
                        label.setForeground(COLOR_URGENTE);
                    }
                    label.setBorder(new EmptyBorder(6, 8, 6, 8));
                }
                return label;
            }
        });
        listaNoticias.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                actualizarDetalle(listaNoticias.getSelectedValue());
            }
        });

        JScrollPane scrollLista = new JScrollPane(listaNoticias);
        scrollLista.setBorder(BorderFactory.createEmptyBorder());
        scrollLista.setPreferredSize(new Dimension(230, 320));
        panelLista.add(scrollLista, BorderLayout.CENTER);

        return panelLista;
    }

    private JPanel crearPanelDetalle() {
        JPanel panelDetalle = new JPanel(new BorderLayout(0, 12));
        panelDetalle.setBackground(Color.WHITE);

        JPanel panelInformacion = new JPanel(new GridBagLayout());
        panelInformacion.setBackground(Color.WHITE);
        panelInformacion.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(COLOR_BORDE),
                "Informacion",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 12),
                new Color(70, 70, 70)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelInformacion.add(crearEtiquetaTitulo("Titulo:"), gbc);

        gbc.gridx = 1;
        lblTituloValor = crearEtiquetaValor("");
        panelInformacion.add(lblTituloValor, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelInformacion.add(crearEtiquetaTitulo("Fecha:"), gbc);

        gbc.gridx = 1;
        lblFechaValor = crearEtiquetaValor("");
        panelInformacion.add(lblFechaValor, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panelInformacion.add(crearEtiquetaTitulo("Valido hasta:"), gbc);

        gbc.gridx = 1;
        lblVigenciaValor = crearEtiquetaValor("");
        panelInformacion.add(lblVigenciaValor, gbc);

        panelDetalle.add(panelInformacion, BorderLayout.NORTH);

        JPanel panelMensaje = new JPanel(new BorderLayout());
        panelMensaje.setBackground(Color.WHITE);
        panelMensaje.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(COLOR_BORDE),
                "Mensaje",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 12),
                new Color(70, 70, 70)));

        txtMensaje = new JTextArea();
        txtMensaje.setEditable(false);
        txtMensaje.setLineWrap(true);
        txtMensaje.setWrapStyleWord(true);
        txtMensaje.setFont(new Font("Arial", Font.PLAIN, 14));
        txtMensaje.setBackground(new Color(252, 252, 252));
        txtMensaje.setBorder(new EmptyBorder(10, 10, 10, 10));

        JScrollPane scrollMensaje = new JScrollPane(txtMensaje);
        scrollMensaje.setBorder(BorderFactory.createEmptyBorder());
        scrollMensaje.setPreferredSize(new Dimension(520, 280));
        panelMensaje.add(scrollMensaje, BorderLayout.CENTER);

        panelDetalle.add(panelMensaje, BorderLayout.CENTER);
        detalleInicializado = true;
        return panelDetalle;
    }

    private void actualizarDetalle(NoticiaData noticiaData) {
        if (!detalleInicializado) {
            noticiaSeleccionada = noticiaData;
            return;
        }

        noticiaSeleccionada = noticiaData;
        if (noticiaData == null) {
            lblTituloValor.setText("");
            lblFechaValor.setText("");
            lblVigenciaValor.setText("");
            txtMensaje.setText("");
            return;
        }

        lblTituloValor.setText(obtenerTexto(noticiaData.getTitulo()));
        lblTituloValor.setForeground(noticiaData.esUrgente() ? COLOR_URGENTE : Color.BLACK);
        lblFechaValor.setText(obtenerTexto(noticiaData.getFecha()));
        String vigencia = noticiaData.getValidoHasta();
        lblVigenciaValor.setText((vigencia != null && !vigencia.trim().isEmpty()) ? vigencia : "Sin vencimiento");
        txtMensaje.setText(obtenerTexto(noticiaData.getMensaje()));
        txtMensaje.setCaretPosition(0);
    }

    private JLabel crearEtiquetaTitulo(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.BOLD, 12));
        label.setHorizontalAlignment(SwingConstants.LEFT);
        return label;
    }

    private JLabel crearEtiquetaValor(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.PLAIN, 12));
        label.setHorizontalAlignment(SwingConstants.LEFT);
        return label;
    }

    private String obtenerTexto(String valor) {
        return (valor != null) ? valor : "";
    }
}
