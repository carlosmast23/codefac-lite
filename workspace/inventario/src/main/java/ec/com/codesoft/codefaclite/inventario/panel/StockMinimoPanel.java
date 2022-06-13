/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.inventario.panel;

import ec.com.codesoft.codefaclite.controlador.aplicacion.ControladorCodefacInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Bodega;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import org.jdesktop.swingx.JXDatePicker;

/**
 *
 * @author Carlos
 */
public abstract class StockMinimoPanel extends ControladorCodefacInterface {

    /**
     * Creates new form StockMinimoPanel
     */
    public StockMinimoPanel() {
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

        jPanel1 = new javax.swing.JPanel();
        lblMostrarDetalle = new javax.swing.JLabel();
        cmbBodega = new javax.swing.JComboBox<>();
        chkTodasBodega = new javax.swing.JCheckBox();
        btnBuscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDato = new javax.swing.JTable();
        lblDiasCaducidad = new javax.swing.JLabel();
        txtCategoria = new javax.swing.JTextField();
        chkTodasCategoria = new javax.swing.JCheckBox();
        lblEspacio = new javax.swing.JLabel();
        btnBuscarCategoria = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        cmbFechaFinal = new org.jdesktop.swingx.JXDatePicker();
        lblEspacio12 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cmbFechaInicial = new org.jdesktop.swingx.JXDatePicker();
        jLabel5 = new javax.swing.JLabel();
        cmbMostrarDetalle = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        txtDiasCaducidad = new javax.swing.JSpinner();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cmbSegmento = new javax.swing.JComboBox<>();
        cmbTipo = new javax.swing.JComboBox<>();
        chkTodosSegmento = new javax.swing.JCheckBox();
        chkTodosTipo = new javax.swing.JCheckBox();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtNombreProducto = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Stock minimo productos");

        jPanel1.setLayout(new java.awt.GridBagLayout());

        lblMostrarDetalle.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblMostrarDetalle.setText("Mostrar Detalle:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblMostrarDetalle, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(cmbBodega, gridBagConstraints);

        chkTodasBodega.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        chkTodasBodega.setText("Todos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(chkTodasBodega, gridBagConstraints);

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/find2-ico.png"))); // NOI18N
        btnBuscar.setText("Buscar");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 11;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(btnBuscar, gridBagConstraints);

        jScrollPane1.setViewportView(tblDato);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jScrollPane1, gridBagConstraints);

        lblDiasCaducidad.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblDiasCaducidad.setText("Días Caducidad Tolerancia:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblDiasCaducidad, gridBagConstraints);

        txtCategoria.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtCategoria, gridBagConstraints);

        chkTodasCategoria.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        chkTodasCategoria.setSelected(true);
        chkTodasCategoria.setText("Todos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(chkTodasCategoria, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        jPanel1.add(lblEspacio, gridBagConstraints);

        btnBuscarCategoria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/find2-ico.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(btnBuscarCategoria, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel3.setText("Bodega:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel3, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(cmbFechaFinal, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        jPanel1.add(lblEspacio12, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel4.setText("Fecha Inicial:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel4, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(cmbFechaInicial, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel5.setText("Categoria:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel5, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(cmbMostrarDetalle, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel6.setText("Tipo:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel6, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtDiasCaducidad, gridBagConstraints);

        jLabel7.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel7.setText("Nombre:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel7, gridBagConstraints);

        jLabel8.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel8.setText("Fecha Final:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel8, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(cmbSegmento, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(cmbTipo, gridBagConstraints);

        chkTodosSegmento.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        chkTodosSegmento.setSelected(true);
        chkTodosSegmento.setText("Todos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(chkTodosSegmento, gridBagConstraints);

        chkTodosTipo.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        chkTodosTipo.setSelected(true);
        chkTodosTipo.setText("Todos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(chkTodosTipo, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        jPanel1.add(jLabel9, gridBagConstraints);

        jLabel10.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel10.setText("Segmento:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel10, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtNombreProducto, gridBagConstraints);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnBuscarCategoria;
    private javax.swing.JCheckBox chkTodasBodega;
    private javax.swing.JCheckBox chkTodasCategoria;
    private javax.swing.JCheckBox chkTodosSegmento;
    private javax.swing.JCheckBox chkTodosTipo;
    private javax.swing.JComboBox<Bodega> cmbBodega;
    private org.jdesktop.swingx.JXDatePicker cmbFechaFinal;
    private org.jdesktop.swingx.JXDatePicker cmbFechaInicial;
    private javax.swing.JComboBox<EnumSiNo> cmbMostrarDetalle;
    private javax.swing.JComboBox<Bodega> cmbSegmento;
    private javax.swing.JComboBox<Bodega> cmbTipo;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDiasCaducidad;
    private javax.swing.JLabel lblEspacio;
    private javax.swing.JLabel lblEspacio12;
    private javax.swing.JLabel lblMostrarDetalle;
    private javax.swing.JTable tblDato;
    private javax.swing.JTextField txtCategoria;
    private javax.swing.JSpinner txtDiasCaducidad;
    private javax.swing.JTextField txtNombreProducto;
    // End of variables declaration//GEN-END:variables

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    public JCheckBox getChkTodasBodega() {
        return chkTodasBodega;
    }

    public void setChkTodasBodega(JCheckBox chkTodasBodega) {
        this.chkTodasBodega = chkTodasBodega;
    }

    public JComboBox<Bodega> getCmbBodega() {
        return cmbBodega;
    }

    public void setCmbBodega(JComboBox<Bodega> cmbBodega) {
        this.cmbBodega = cmbBodega;
    }

    public JTable getTblDato() {
        return tblDato;
    }

    public void setTblDato(JTable tblDato) {
        this.tblDato = tblDato;
    }

    public JButton getBtnBuscarCategoria() {
        return btnBuscarCategoria;
    }

    public void setBtnBuscarCategoria(JButton btnBuscarCategoria) {
        this.btnBuscarCategoria = btnBuscarCategoria;
    }

    public JCheckBox getChkTodasCategoria() {
        return chkTodasCategoria;
    }

    public void setChkTodasCategoria(JCheckBox chkTodasCategoria) {
        this.chkTodasCategoria = chkTodasCategoria;
    }

    public JTextField getTxtCategoria() {
        return txtCategoria;
    }

    public void setTxtCategoria(JTextField txtCategoria) {
        this.txtCategoria = txtCategoria;
    }

    public JXDatePicker getCmbFechaFinal() {
        return cmbFechaFinal;
    }

    public void setCmbFechaFinal(JXDatePicker cmbFechaFinal) {
        this.cmbFechaFinal = cmbFechaFinal;
    }

    public JXDatePicker getCmbFechaInicial() {
        return cmbFechaInicial;
    }

    public void setCmbFechaInicial(JXDatePicker cmbFechaInicial) {
        this.cmbFechaInicial = cmbFechaInicial;
    }

    public JComboBox<EnumSiNo> getCmbMostrarDetalle() {
        return cmbMostrarDetalle;
    }

    public void setCmbMostrarDetalle(JComboBox<EnumSiNo> cmbMostrarDetalle) {
        this.cmbMostrarDetalle = cmbMostrarDetalle;
    }

    public JSpinner getTxtDiasCaducidad() {
        return txtDiasCaducidad;
    }

    public void setTxtDiasCaducidad(JSpinner txtDiasCaducidad) {
        this.txtDiasCaducidad = txtDiasCaducidad;
    }

    public JCheckBox getChkTodosSegmento() {
        return chkTodosSegmento;
    }

    public void setChkTodosSegmento(JCheckBox chkTodosSegmento) {
        this.chkTodosSegmento = chkTodosSegmento;
    }

    public JCheckBox getChkTodosTipo() {
        return chkTodosTipo;
    }

    public void setChkTodosTipo(JCheckBox chkTodosTipo) {
        this.chkTodosTipo = chkTodosTipo;
    }

    public JComboBox<Bodega> getCmbSegmento() {
        return cmbSegmento;
    }

    public void setCmbSegmento(JComboBox<Bodega> cmbSegmento) {
        this.cmbSegmento = cmbSegmento;
    }

    public JComboBox<Bodega> getCmbTipo() {
        return cmbTipo;
    }

    public void setCmbTipo(JComboBox<Bodega> cmbTipo) {
        this.cmbTipo = cmbTipo;
    }

    public JTextField getTxtNombreProducto() {
        return txtNombreProducto;
    }

    public void setTxtNombreProducto(JTextField txtNombreProducto) {
        this.txtNombreProducto = txtNombreProducto;
    }

    public JLabel getLblDiasCaducidad() {
        return lblDiasCaducidad;
    }

    public void setLblDiasCaducidad(JLabel lblDiasCaducidad) {
        this.lblDiasCaducidad = lblDiasCaducidad;
    }

    public JLabel getLblMostrarDetalle() {
        return lblMostrarDetalle;
    }

    public void setLblMostrarDetalle(JLabel lblMostrarDetalle) {
        this.lblMostrarDetalle = lblMostrarDetalle;
    }
    
    
    
    
    
}
