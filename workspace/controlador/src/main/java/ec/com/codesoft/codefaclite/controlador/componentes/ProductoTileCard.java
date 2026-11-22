/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.componentes;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Tile reutilizable (imagen + nombre + precio) para pantallas de venta rapida tipo grid
 * (heladerias, cafeterias, etc). Deliberadamente agnostico de Producto/Kardex/servidor: solo
 * sabe mostrar un icono, dos textos y disparar un click. Quien lo usa (ej. VentaRapidaModel)
 * resuelve los datos de negocio y los setea via setImagen/setNombre/setPrecio, y puede guardar
 * su propia referencia (Producto, Kardex, etc.) en setDatoAsociado para recuperarla en el click.
 *
 * @author Carlos
 */
public class ProductoTileCard extends javax.swing.JPanel {

    private Object datoAsociado;
    private final List<ActionListener> actionListeners = new ArrayList<ActionListener>();
    private boolean destacado = false;

    /**
     * Creates new form ProductoTileCard
     */
    private static final int ARCO = 16;
    private static final int SOMBRA = 2;
    private static final Color COLOR_FONDO = Color.WHITE;
    private static final Color COLOR_FONDO_DESTACADO = new Color(224, 246, 233);
    private static final Color COLOR_BORDE = new Color(234, 234, 234);
    private static final Color COLOR_BORDE_DESTACADO = new Color(70, 160, 110);

    public ProductoTileCard() {
        initComponents();
        setOpaque(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        MouseAdapter clickAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                destacarBreve();
                dispararClick();
            }
        };
        addMouseListener(clickAdapter);
        lblImagen.addMouseListener(clickAdapter);
        lblNombre.addMouseListener(clickAdapter);
        lblPrecio.addMouseListener(clickAdapter);
    }

    /**
     * Pinta brevemente la tarjeta con fondo/borde verdes como confirmacion visual de que el
     * producto se agrego al carrito, y vuelve al estado normal solo. No bloquea la pantalla ni
     * necesita un sistema de "toast" aparte.
     */
    private void destacarBreve() {
        destacado = true;
        repaint();
        Timer temporizador = new Timer(180, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                destacado = false;
                repaint();
            }
        });
        temporizador.setRepeats(false);
        temporizador.start();
    }

    /**
     * Tarjeta con esquinas redondeadas y una sombra sutil, en vez del borde plano de
     * 1px que se ve como una celda de tabla. java.awt.FlowLayout en el panel contenedor
     * deja la transparencia de fondo intacta porque el tile es setOpaque(false).
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int ancho = getWidth() - SOMBRA;
        int alto = getHeight() - SOMBRA;

        g2.setColor(new Color(0, 0, 0, 16));
        g2.fillRoundRect(SOMBRA, SOMBRA, ancho, alto, ARCO, ARCO);

        g2.setColor(destacado ? COLOR_FONDO_DESTACADO : COLOR_FONDO);
        g2.fillRoundRect(0, 0, ancho, alto, ARCO, ARCO);

        g2.setColor(destacado ? COLOR_BORDE_DESTACADO : COLOR_BORDE);
        g2.drawRoundRect(0, 0, ancho - 1, alto - 1, ARCO, ARCO);

        g2.dispose();
        super.paintComponent(g);
    }

    private void dispararClick() {
        ActionEvent evento = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "click");
        for (ActionListener listener : new ArrayList<ActionListener>(actionListeners)) {
            listener.actionPerformed(evento);
        }
    }

    public void addActionListener(ActionListener listener) {
        actionListeners.add(listener);
    }

    public void removeActionListener(ActionListener listener) {
        actionListeners.remove(listener);
    }

    public void setImagen(Icon icono) {
        lblImagen.setIcon(icono);
    }

    public void setNombre(String nombre) {
        lblNombre.setText("<html><center>" + nombre + "</center></html>");
    }

    public void setPrecio(String precioTexto) {
        lblPrecio.setText(precioTexto);
    }

    public Object getDatoAsociado() {
        return datoAsociado;
    }

    public void setDatoAsociado(Object datoAsociado) {
        this.datoAsociado = datoAsociado;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        lblImagen = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        lblPrecio = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(168, 188));
        setLayout(new java.awt.GridBagLayout());

        lblImagen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblImagen.setPreferredSize(new java.awt.Dimension(134, 104));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(8, 8, 0, 8);
        add(lblImagen, gridBagConstraints);

        lblNombre.setFont(new java.awt.Font("Arial", 1, 14));
        lblNombre.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNombre.setText("<html><center>Producto</center></html>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 8, 0, 8);
        add(lblNombre, gridBagConstraints);

        lblPrecio.setFont(new java.awt.Font("Arial", 1, 16));
        lblPrecio.setForeground(new java.awt.Color(0, 102, 51));
        lblPrecio.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPrecio.setText("$0.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 8, 8, 8);
        add(lblPrecio, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblImagen;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblPrecio;
    // End of variables declaration//GEN-END:variables
}
