package ec.com.codesoft.codefaclite.main.panel;

import ec.com.codesoft.codefaclite.main.model.changelog.ChangelogCambioData;
import ec.com.codesoft.codefaclite.main.model.changelog.ChangelogVersionData;
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
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.SwingConstants;

/**
 * Dialogo para mostrar las novedades del sistema y permitir consultar versiones anteriores.
 */
public class ChangelogDialog extends JDialog {

    private final List<ChangelogVersionData> historialVersiones;
    private ChangelogVersionData versionSeleccionada;
    private JLabel lblVersionValor;
    private JLabel lblFechaValor;
    private JLabel lblTituloValor;
    private JTextArea txtCambios;
    private JList<ChangelogVersionData> listaVersiones;
    private boolean detalleInicializado;

    public ChangelogDialog(Frame parent, List<ChangelogVersionData> historialVersiones, ChangelogVersionData versionInicial) {
        super(parent, "Novedades del sistema", true);
        this.historialVersiones = (historialVersiones != null) ? historialVersiones : new ArrayList<ChangelogVersionData>();
        this.versionSeleccionada = (versionInicial != null) ? versionInicial : obtenerPrimeraVersion();
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
                BorderFactory.createLineBorder(new Color(210, 220, 232)),
                new EmptyBorder(12, 14, 12, 14)));

        JLabel lblTituloPrincipal = new JLabel("Novedades disponibles");
        lblTituloPrincipal.setFont(new Font("Arial", Font.BOLD, 20));
        lblTituloPrincipal.setForeground(new Color(46, 78, 125));
        panelCabecera.add(lblTituloPrincipal, BorderLayout.NORTH);

        JLabel lblSubtitulo = new JLabel("Consulte los cambios incorporados en cada version del sistema.");
        lblSubtitulo.setBorder(new EmptyBorder(6, 0, 0, 0));
        panelCabecera.add(lblSubtitulo, BorderLayout.CENTER);

        panelPrincipal.add(panelCabecera, BorderLayout.NORTH);

        JPanel panelDetalle = crearPanelDetalle();
        JPanel panelVersiones = crearPanelVersiones();

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelVersiones, panelDetalle);
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

        if (versionSeleccionada != null) {
            listaVersiones.setSelectedValue(versionSeleccionada, true);
        } else if (!historialVersiones.isEmpty()) {
            listaVersiones.setSelectedIndex(0);
        } else {
            actualizarDetalle(null);
        }
        pack();
        setMinimumSize(new Dimension(860, 540));
    }

    private JPanel crearPanelVersiones() {
        JPanel panelVersiones = new JPanel(new BorderLayout(0, 8));
        panelVersiones.setBackground(Color.WHITE);
        panelVersiones.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(210, 220, 232)),
                "Versiones disponibles",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 12),
                new Color(70, 70, 70)));

        listaVersiones = new JList<ChangelogVersionData>(historialVersiones.toArray(new ChangelogVersionData[0]));
        listaVersiones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaVersiones.setFont(new Font("Arial", Font.PLAIN, 12));
        listaVersiones.setBackground(Color.WHITE);
        listaVersiones.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof ChangelogVersionData) {
                    ChangelogVersionData versionData = (ChangelogVersionData) value;
                    label.setText(obtenerTexto(versionData.getVersion()) + " - " + obtenerTexto(versionData.getFecha()));
                    label.setBorder(new EmptyBorder(6, 8, 6, 8));
                }
                return label;
            }
        });
        listaVersiones.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                actualizarDetalle(listaVersiones.getSelectedValue());
            }
        });

        JScrollPane scrollVersiones = new JScrollPane(listaVersiones);
        scrollVersiones.setBorder(BorderFactory.createEmptyBorder());
        scrollVersiones.setPreferredSize(new Dimension(240, 340));
        panelVersiones.add(scrollVersiones, BorderLayout.CENTER);

        return panelVersiones;
    }

    private JPanel crearPanelDetalle() {
        JPanel panelDetalle = new JPanel(new BorderLayout(0, 12));
        panelDetalle.setBackground(Color.WHITE);

        JPanel panelInformacion = new JPanel(new GridBagLayout());
        panelInformacion.setBackground(Color.WHITE);
        panelInformacion.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(210, 220, 232)),
                "Informacion de la version",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 12),
                new Color(70, 70, 70)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelInformacion.add(crearEtiquetaTitulo("Version:"), gbc);

        gbc.gridx = 1;
        lblVersionValor = crearEtiquetaValor("");
        panelInformacion.add(lblVersionValor, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelInformacion.add(crearEtiquetaTitulo("Fecha:"), gbc);

        gbc.gridx = 1;
        lblFechaValor = crearEtiquetaValor("");
        panelInformacion.add(lblFechaValor, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panelInformacion.add(crearEtiquetaTitulo("Titulo:"), gbc);

        gbc.gridx = 1;
        lblTituloValor = crearEtiquetaValor("");
        panelInformacion.add(lblTituloValor, gbc);

        panelDetalle.add(panelInformacion, BorderLayout.NORTH);

        JPanel panelCambios = new JPanel(new BorderLayout());
        panelCambios.setBackground(Color.WHITE);
        panelCambios.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(210, 220, 232)),
                "Cambios incluidos",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 12),
                new Color(70, 70, 70)));

        txtCambios = new JTextArea();
        txtCambios.setEditable(false);
        txtCambios.setLineWrap(true);
        txtCambios.setWrapStyleWord(true);
        txtCambios.setFont(new Font("Arial", Font.PLAIN, 13));
        txtCambios.setBackground(new Color(252, 252, 252));
        txtCambios.setBorder(new EmptyBorder(10, 10, 10, 10));

        JScrollPane scrollCambios = new JScrollPane(txtCambios);
        scrollCambios.setBorder(BorderFactory.createEmptyBorder());
        scrollCambios.setPreferredSize(new Dimension(540, 320));
        panelCambios.add(scrollCambios, BorderLayout.CENTER);

        panelDetalle.add(panelCambios, BorderLayout.CENTER);
        detalleInicializado = true;
        return panelDetalle;
    }

    private void actualizarDetalle(ChangelogVersionData versionData) {
        if (!detalleInicializado) {
            versionSeleccionada = versionData;
            return;
        }

        versionSeleccionada = versionData;
        if (versionData == null) {
            lblVersionValor.setText("");
            lblFechaValor.setText("");
            lblTituloValor.setText("");
            txtCambios.setText("No existen cambios registrados.");
            return;
        }

        lblVersionValor.setText(obtenerTexto(versionData.getVersion()));
        lblFechaValor.setText(obtenerTexto(versionData.getFecha()));
        lblTituloValor.setText(obtenerTexto(versionData.getTitulo()));
        txtCambios.setText(construirTextoCambios(versionData));
        txtCambios.setCaretPosition(0);
    }

    private String construirTextoCambios(ChangelogVersionData versionData) {
        StringBuilder texto = new StringBuilder();

        if (versionData != null && versionData.getCambios() != null) {
            for (ChangelogCambioData cambioData : versionData.getCambios()) {
                texto.append("- [")
                        .append(obtenerTexto(cambioData.getTipo()))
                        .append("] ")
                        .append(obtenerTexto(cambioData.getDetalle()))
                        .append("\n\n");
            }
        }

        return texto.toString().trim();
    }

    private ChangelogVersionData obtenerPrimeraVersion() {
        return historialVersiones.isEmpty() ? null : historialVersiones.get(0);
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
