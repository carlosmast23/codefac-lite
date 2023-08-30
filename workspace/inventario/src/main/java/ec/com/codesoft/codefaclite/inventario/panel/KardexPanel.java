/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.inventario.panel;

import com.toedter.calendar.JDateChooser;
import ec.com.codesoft.codefaclite.controlador.aplicacion.ControladorCodefacInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.util.LimpiarAnotacion;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Bodega;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author Carlos
 */
public abstract class KardexPanel extends ControladorCodefacInterface {

    /**
     * Creates new form KardexPanel
     */
    public KardexPanel() {
        initComponents();
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

        jLabel1 = new javax.swing.JLabel();
        txtProducto = new javax.swing.JTextField();
        btnProductoBuscar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cmbFechaInicial = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        cmbFechaFinal = new com.toedter.calendar.JDateChooser();
        btnConsultar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblKardexDetalle = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        lblEspacioBlanco2 = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        jLabel5 = new javax.swing.JLabel();
        lblCantidad = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblPrecioPromedio = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lblPrecioUltimo = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cmbBodega = new javax.swing.JComboBox<>();
        txtMovimientos = new javax.swing.JSpinner();
        chkTodosMovimientos = new javax.swing.JCheckBox();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtCostoPromedio = new javax.swing.JTextField();
        txtUltimoCosto = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtLoteNombre = new javax.swing.JTextField();
        btnBuscarLote = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        txtReserva = new javax.swing.JSpinner();
        chkPsicotropicos = new javax.swing.JCheckBox();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Kardex");
        setToolTipText("");
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel1.setText("Producto:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 28;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jLabel1, gridBagConstraints);

        txtProducto.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(txtProducto, gridBagConstraints);

        btnProductoBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/find2-ico.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(btnProductoBuscar, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel2.setText("Movimientos:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 35;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jLabel2, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel3.setText("Último Costo:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jLabel3, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(cmbFechaInicial, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel4.setText("Fecha Final:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jLabel4, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(cmbFechaFinal, gridBagConstraints);

        btnConsultar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/find2-ico.png"))); // NOI18N
        btnConsultar.setText("Consultar");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(btnConsultar, gridBagConstraints);

        jScrollPane1.setViewportView(tblKardexDetalle);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jScrollPane1, gridBagConstraints);

        jLabel11.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        jLabel11.setText("Total:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jLabel11, gridBagConstraints);

        lblTotal.setFont(new java.awt.Font("Arial", 0, 15)); // NOI18N
        lblTotal.setText("0.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(lblTotal, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        getContentPane().add(lblEspacioBlanco2, gridBagConstraints);

        jToolBar1.setRollover(true);

        jLabel5.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        jLabel5.setText("Stock:");
        jToolBar1.add(jLabel5);

        lblCantidad.setFont(new java.awt.Font("Arial", 0, 15)); // NOI18N
        lblCantidad.setText("0");
        lblCantidad.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 10));
        jToolBar1.add(lblCantidad);

        jLabel7.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        jLabel7.setText("Precio Promedio:");
        jLabel7.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 50, 1, 1));
        jToolBar1.add(jLabel7);

        lblPrecioPromedio.setFont(new java.awt.Font("Arial", 0, 15)); // NOI18N
        lblPrecioPromedio.setText("00.00");
        lblPrecioPromedio.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 10));
        jToolBar1.add(lblPrecioPromedio);

        jLabel9.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        jLabel9.setText("Ultimo Precio:");
        jLabel9.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 50, 1, 1));
        jToolBar1.add(jLabel9);

        lblPrecioUltimo.setFont(new java.awt.Font("Arial", 0, 15)); // NOI18N
        lblPrecioUltimo.setText("00.00");
        lblPrecioUltimo.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 10));
        jToolBar1.add(lblPrecioUltimo);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 5;
        getContentPane().add(jToolBar1, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel6.setText("Lote:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 35;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jLabel6, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(cmbBodega, gridBagConstraints);

        txtMovimientos.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtMovimientos.setToolTipText("Cantidad de últimos movimientos a mostrar");
        txtMovimientos.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(txtMovimientos, gridBagConstraints);

        chkTodosMovimientos.setSelected(true);
        chkTodosMovimientos.setText("Todos");
        chkTodosMovimientos.setFocusable(false);
        chkTodosMovimientos.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        chkTodosMovimientos.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        chkTodosMovimientos.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(chkTodosMovimientos, gridBagConstraints);

        jLabel8.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel8.setText("Reserva:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jLabel8, gridBagConstraints);

        jLabel10.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel10.setText("Fecha Inicial:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jLabel10, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(txtCostoPromedio, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(txtUltimoCosto, gridBagConstraints);

        jLabel12.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel12.setText("Bodega:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 35;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jLabel12, gridBagConstraints);

        txtLoteNombre.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(txtLoteNombre, gridBagConstraints);

        btnBuscarLote.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/find2-ico.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(btnBuscarLote, gridBagConstraints);

        jLabel13.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel13.setText("Costo Promedio:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jLabel13, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(txtReserva, gridBagConstraints);

        chkPsicotropicos.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        chkPsicotropicos.setText("Psicotrópicos");
        chkPsicotropicos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkPsicotropicosActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(chkPsicotropicos, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void chkPsicotropicosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkPsicotropicosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkPsicotropicosActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscarLote;
    private javax.swing.JButton btnConsultar;
    private javax.swing.JButton btnProductoBuscar;
    private javax.swing.JCheckBox chkPsicotropicos;
    private javax.swing.JCheckBox chkTodosMovimientos;
    private javax.swing.JComboBox<Bodega> cmbBodega;
    private com.toedter.calendar.JDateChooser cmbFechaFinal;
    private com.toedter.calendar.JDateChooser cmbFechaInicial;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lblCantidad;
    private javax.swing.JLabel lblEspacioBlanco2;
    private javax.swing.JLabel lblPrecioPromedio;
    private javax.swing.JLabel lblPrecioUltimo;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JTable tblKardexDetalle;
    private javax.swing.JTextField txtCostoPromedio;
    private javax.swing.JTextField txtLoteNombre;
    private javax.swing.JSpinner txtMovimientos;
    private javax.swing.JTextField txtProducto;
    private javax.swing.JSpinner txtReserva;
    private javax.swing.JTextField txtUltimoCosto;
    // End of variables declaration//GEN-END:variables

    public JButton getBtnConsultar() {
        return btnConsultar;
    }

    public void setBtnConsultar(JButton btnConsultar) {
        this.btnConsultar = btnConsultar;
    }

    public JButton getBtnProductoBuscar() {
        return btnProductoBuscar;
    }

    public void setBtnProductoBuscar(JButton btnProductoBuscar) {
        this.btnProductoBuscar = btnProductoBuscar;
    }

    //@LimpiarAnotacion
    public JDateChooser getCmbFechaFinal() {
        return cmbFechaFinal;
    }

    public void setCmbFechaFinal(JDateChooser cmbFechaFinal) {
        this.cmbFechaFinal = cmbFechaFinal;
    }

    //@LimpiarAnotacion
    public JDateChooser getCmbFechaInicial() {
        return cmbFechaInicial;
    }

    public void setCmbFechaInicial(JDateChooser cmbFechaInicial) {
        this.cmbFechaInicial = cmbFechaInicial;
    }

    public JTable getTblKardexDetalle() {
        return tblKardexDetalle;
    }

    public void setTblKardexDetalle(JTable tblKardexDetalle) {
        this.tblKardexDetalle = tblKardexDetalle;
    }

    public JComboBox<Bodega> getCmbBodega() {
        return cmbBodega;
    }

    public void setCmbBodega(JComboBox<Bodega> cmbBodega) {
        this.cmbBodega = cmbBodega;
    }

    public JTextField getTxtProducto() {
        return txtProducto;
    }

    public void setTxtProducto(JTextField txtProducto) {
        this.txtProducto = txtProducto;
    }

    public JLabel getLblCantidad() {
        return lblCantidad;
    }

    public void setLblCantidad(JLabel lblCantidad) {
        this.lblCantidad = lblCantidad;
    }

    public JLabel getLblPrecioPromedio() {
        return lblPrecioPromedio;
    }

    public void setLblPrecioPromedio(JLabel lblPrecioPromedio) {
        this.lblPrecioPromedio = lblPrecioPromedio;
    }

    public JLabel getLblPrecioUltimo() {
        return lblPrecioUltimo;
    }

    public void setLblPrecioUltimo(JLabel lblPrecioUltimo) {
        this.lblPrecioUltimo = lblPrecioUltimo;
    }

    public JLabel getLblTotal() {
        return lblTotal;
    }

    public void setLblTotal(JLabel lblTotal) {
        this.lblTotal = lblTotal;
    }

    public JCheckBox getChkTodosMovimientos() {
        return chkTodosMovimientos;
    }

    public void setChkTodosMovimientos(JCheckBox chkTodosMovimientos) {
        this.chkTodosMovimientos = chkTodosMovimientos;
    }

    public JSpinner getTxtMovimientos() {
        return txtMovimientos;
    }

    public void setTxtMovimientos(JSpinner txtMovimientos) {
        this.txtMovimientos = txtMovimientos;
    }

    public JTextField getTxtCostoPromedio() {
        return txtCostoPromedio;
    }

    public void setTxtCostoPromedio(JTextField txtCostoPromedio) {
        this.txtCostoPromedio = txtCostoPromedio;
    }

    public JTextField getTxtUltimoCosto() {
        return txtUltimoCosto;
    }

    public void setTxtUltimoCosto(JTextField txtUltimoCosto) {
        this.txtUltimoCosto = txtUltimoCosto;
    }

    public JTextField getTxtLoteNombre() {
        return txtLoteNombre;
    }

    public void setTxtLoteNombre(JTextField txtLoteNombre) {
        this.txtLoteNombre = txtLoteNombre;
    }

    public JButton getBtnBuscarLote() {
        return btnBuscarLote;
    }

    public void setBtnBuscarLote(JButton btnBuscarLote) {
        this.btnBuscarLote = btnBuscarLote;
    }

    public JSpinner getTxtReserva() {
        return txtReserva;
    }

    public void setTxtReserva(JSpinner txtReserva) {
        this.txtReserva = txtReserva;
    }

    public JCheckBox getChkPsicotropicos() {
        return chkPsicotropicos;
    }

    public void setChkPsicotropicos(JCheckBox chkPsicotropicos) {
        this.chkPsicotropicos = chkPsicotropicos;
    }
    
    
}
