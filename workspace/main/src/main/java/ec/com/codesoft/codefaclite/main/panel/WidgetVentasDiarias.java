/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.panel;

import ec.com.codesoft.codefaclite.controlador.aplicacion.ControladorCodefacInterface;
import ec.com.codesoft.codefaclite.main.model.ObjetoEscritorioAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesSwingX;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author PC
 */
public abstract class WidgetVentasDiarias extends ObjetoEscritorioAbstract{

    /**
     * Creates new form ejemplo
     * @param parentPanel
     */
    public WidgetVentasDiarias(JDesktopPane parentPanel) {
        super(parentPanel);
        initComponents();
        UtilidadesSwingX.placeHolder("Descripción",txtDescripcionProducto);
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

        panelTitulo = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jTabbedPanel = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        txtDescripcionProducto = new javax.swing.JTextField();
        txtCantidadProdiucto = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtValorUnitarioProducto = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        btnFacturar = new javax.swing.JButton();
        btnAgregarProducto = new javax.swing.JButton();
        btnBuscarProducto = new javax.swing.JButton();
        cmbTipoDocumento = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        btnEliminarProducto = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TblDetalleFactura = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        lblSubtotal12 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblSubtotal0 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        lblValorTotal = new javax.swing.JLabel();
        lblIva12 = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        setLayout(new java.awt.BorderLayout());

        panelTitulo.setBackground(new java.awt.Color(40, 161, 207));
        panelTitulo.setForeground(new java.awt.Color(255, 255, 51));
        panelTitulo.setLayout(new javax.swing.BoxLayout(panelTitulo, javax.swing.BoxLayout.LINE_AXIS));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("  Ventas Diarias");
        panelTitulo.add(jLabel1);

        add(panelTitulo, java.awt.BorderLayout.PAGE_START);

        jPanel2.setLayout(new java.awt.GridBagLayout());

        jTabbedPanel.setMinimumSize(new java.awt.Dimension(100, 100));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        txtDescripcionProducto.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_END;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 5);
        jPanel1.add(txtDescripcionProducto, gridBagConstraints);

        txtCantidadProdiucto.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_END;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 5);
        jPanel1.add(txtCantidadProdiucto, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel3.setText("Cantidad:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 5);
        jPanel1.add(jLabel3, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setText("Valor Unitario:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 5);
        jPanel1.add(jLabel2, gridBagConstraints);

        txtValorUnitarioProducto.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_END;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 5);
        jPanel1.add(txtValorUnitarioProducto, gridBagConstraints);

        jLabel6.setText("         ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        jPanel1.add(jLabel6, gridBagConstraints);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel1.add(jPanel5, gridBagConstraints);

        btnFacturar.setBackground(new java.awt.Color(255, 255, 255));
        btnFacturar.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        btnFacturar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/guardar.png"))); // NOI18N
        btnFacturar.setText("Facturar");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 5);
        jPanel1.add(btnFacturar, gridBagConstraints);

        btnAgregarProducto.setBackground(new java.awt.Color(255, 255, 255));
        btnAgregarProducto.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        btnAgregarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/pequenos/add2.png"))); // NOI18N
        btnAgregarProducto.setText("Agregar");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 5);
        jPanel1.add(btnAgregarProducto, gridBagConstraints);

        btnBuscarProducto.setBackground(new java.awt.Color(255, 255, 255));
        btnBuscarProducto.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        btnBuscarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/pequenos/Zoom.png"))); // NOI18N
        btnBuscarProducto.setText("Buscar");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 5);
        jPanel1.add(btnBuscarProducto, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 5);
        jPanel1.add(cmbTipoDocumento, gridBagConstraints);

        jTabbedPanel.addTab("Producto", new javax.swing.ImageIcon(getClass().getResource("/img/iconos/pequenos/camion.png")), jPanel1); // NOI18N

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.GridBagLayout());

        btnEliminarProducto.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        btnEliminarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/cancel-ico.png"))); // NOI18N
        btnEliminarProducto.setText("Eliminar");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        jPanel4.add(btnEliminarProducto, gridBagConstraints);

        jPanel6.setLayout(new java.awt.GridBagLayout());

        jScrollPane1.setViewportView(TblDetalleFactura);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        jPanel6.add(jScrollPane1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        jPanel4.add(jPanel6, gridBagConstraints);

        jPanel3.setBackground(new java.awt.Color(248, 244, 244));
        jPanel3.setLayout(new java.awt.GridBagLayout());

        jLabel11.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel11.setText("SUBTOTAL 12%");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(1, 20, 1, 2);
        jPanel3.add(jLabel11, gridBagConstraints);

        lblSubtotal12.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        lblSubtotal12.setText("0.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel3.add(lblSubtotal12, gridBagConstraints);

        jLabel8.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel8.setText("SUBTOTAL 0%");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(1, 20, 1, 2);
        jPanel3.add(jLabel8, gridBagConstraints);

        lblSubtotal0.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        lblSubtotal0.setText("0.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel3.add(lblSubtotal0, gridBagConstraints);

        jLabel15.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel15.setText("IVA 12%");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(1, 20, 1, 2);
        jPanel3.add(jLabel15, gridBagConstraints);

        jLabel16.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        jLabel16.setText("VALOR TOTAL");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(1, 20, 1, 2);
        jPanel3.add(jLabel16, gridBagConstraints);

        lblValorTotal.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        lblValorTotal.setText("0.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel3.add(lblValorTotal, gridBagConstraints);

        lblIva12.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        lblIva12.setText("0.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel3.add(lblIva12, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 10);
        jPanel4.add(jPanel3, gridBagConstraints);

        jTabbedPanel.addTab("Detalles", new javax.swing.ImageIcon(getClass().getResource("/img/iconos/pequenos/detalle.png")), jPanel4); // NOI18N

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        jPanel2.add(jTabbedPanel, gridBagConstraints);

        add(jPanel2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    public JButton getBtnAgregarProducto() {
        return btnAgregarProducto;
    }

    public void setBtnAgregarProducto(JButton btnAgregarProducto) {
        this.btnAgregarProducto = btnAgregarProducto;
    }

    public JButton getBtnBuscarProducto() {
        return btnBuscarProducto;
    }

    public void setBtnBuscarProducto(JButton btnBuscarProducto) {
        this.btnBuscarProducto = btnBuscarProducto;
    }

    public JTable getTblDetalleFactura() {
        return TblDetalleFactura;
    }

    public void setTblDetalleFactura(JTable TblDetalleFactura) {
        this.TblDetalleFactura = TblDetalleFactura;
    }

    public JTabbedPane getjTabbedPanel() {
        return jTabbedPanel;
    }

    public void setjTabbedPanel(JTabbedPane jTabbedPanel) {
        this.jTabbedPanel = jTabbedPanel;
    }

    public JButton getBtnFacturar() {
        return btnFacturar;
    }

    public void setBtnFacturar(JButton btnFacturar) {
        this.btnFacturar = btnFacturar;
    }

    public JButton getBtnEliminarProducto() {
        return btnEliminarProducto;
    }

    public void setBtnEliminarProducto(JButton btnEliminarProducto) {
        this.btnEliminarProducto = btnEliminarProducto;
    }

    public JPanel getPanelTitulo() {
        return panelTitulo;
    }

    public void setPanelTitulo(JPanel panelTitulo) {
        this.panelTitulo = panelTitulo;
    }
    
    public JTextField getTxtCantidadProducto() {
        return txtCantidadProdiucto;
    }

    public void setTxtCantidadProducto(JTextField txtCantidadProducto) {
        this.txtCantidadProdiucto = txtCantidadProducto;
    }

    public JTextField getTxtDescripcionProducto() {
        return txtDescripcionProducto;
    }

    public void setTxtDescripcionProducto(JTextField txtDescripcionProducto) {
        this.txtDescripcionProducto = txtDescripcionProducto;
    }

    public JTextField getTxtValorUnitarioProducto() {
        return txtValorUnitarioProducto;
    }

    public void setTxtValorUnitarioProducto(JTextField txtValorUnitarioProducto) {
        this.txtValorUnitarioProducto = txtValorUnitarioProducto;
    }

    public JComboBox<TipoDocumentoEnum> getCmbTipoDocumento() {
        return cmbTipoDocumento;
    }

    public void setCmbTipoDocumento(JComboBox<TipoDocumentoEnum> cmbTipoDocumento) {
        this.cmbTipoDocumento = cmbTipoDocumento;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TblDetalleFactura;
    private javax.swing.JButton btnAgregarProducto;
    private javax.swing.JButton btnBuscarProducto;
    private javax.swing.JButton btnEliminarProducto;
    private javax.swing.JButton btnFacturar;
    private javax.swing.JComboBox<TipoDocumentoEnum> cmbTipoDocumento;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPanel;
    private javax.swing.JLabel lblIva12;
    private javax.swing.JLabel lblSubtotal0;
    private javax.swing.JLabel lblSubtotal12;
    private javax.swing.JLabel lblValorTotal;
    private javax.swing.JPanel panelTitulo;
    private javax.swing.JTextField txtCantidadProdiucto;
    private javax.swing.JTextField txtDescripcionProducto;
    private javax.swing.JTextField txtValorUnitarioProducto;
    // End of variables declaration//GEN-END:variables

    public JLabel getLblIva12() {
        return lblIva12;
    }

    public void setLblIva12(JLabel lblIva12) {
        this.lblIva12 = lblIva12;
    }

    public JLabel getLblSubtotal0() {
        return lblSubtotal0;
    }

    public void setLblSubtotal0(JLabel lblSubtotal0) {
        this.lblSubtotal0 = lblSubtotal0;
    }

    public JLabel getLblSubtotal12() {
        return lblSubtotal12;
    }

    public void setLblSubtotal12(JLabel lblSubtotal12) {
        this.lblSubtotal12 = lblSubtotal12;
    }

    public JLabel getLblValorTotal() {
        return lblValorTotal;
    }

    public void setLblValorTotal(JLabel lblValorTotal) {
        this.lblValorTotal = lblValorTotal;
    }

}
