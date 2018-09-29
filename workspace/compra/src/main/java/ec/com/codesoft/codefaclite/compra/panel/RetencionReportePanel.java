/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.compra.panel;

import com.toedter.calendar.JDateChooser;
import ec.com.codesoft.codefaclite.controlador.aplicacion.ControladorCodefacInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencion;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencionIva;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencionRenta;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author CodesoftDesarrollo
 */
public abstract class RetencionReportePanel extends ControladorCodefacInterface {

    /**
     * Creates new form RetencionReportePanel
     */
    public RetencionReportePanel() {
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

        cmbRetencionIva = new javax.swing.JComboBox<>();
        cmbRetencionRenta = new javax.swing.JComboBox<>();
        dateFechaInicio = new com.toedter.calendar.JDateChooser();
        dateFechaFin = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnBuscarProveedor = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtProveedor = new javax.swing.JTextField();
        chkTodos = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblRetenciones = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblRetIva = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblRetRenta = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        chkTodosIva = new javax.swing.JCheckBox();
        chkTodosRenta = new javax.swing.JCheckBox();
        cmbTipo = new javax.swing.JComboBox<>();
        chkTodosTipo = new javax.swing.JCheckBox();
        lblEspacio1 = new javax.swing.JLabel();
        lblEspacio3 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        btnLimpiarFechaInicial = new javax.swing.JButton();
        btnLimpiarFechaFinal = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Reporte Retenciones");
        getContentPane().setLayout(new java.awt.GridBagLayout());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(cmbRetencionIva, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(cmbRetencionRenta, gridBagConstraints);

        dateFechaInicio.setDateFormatString("yyyy-MM-dd");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(dateFechaInicio, gridBagConstraints);

        dateFechaFin.setDateFormatString("yyyy-MM-dd");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(dateFechaFin, gridBagConstraints);

        jLabel1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel1.setText("Fecha inicial:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jLabel1, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel2.setText("Proveedor:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jLabel2, gridBagConstraints);

        btnBuscarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/pequenos/Zoom.png"))); // NOI18N
        btnBuscarProveedor.setToolTipText("Buscar Proveedor");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(btnBuscarProveedor, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel3.setText("Fecha final:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jLabel3, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(txtProveedor, gridBagConstraints);

        chkTodos.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        chkTodos.setText("Todos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        getContentPane().add(chkTodos, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel4.setText("IVA:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jLabel4, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel5.setText("Tipo:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jLabel5, gridBagConstraints);

        btnBuscar.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/find2-ico.png"))); // NOI18N
        btnBuscar.setText("Buscar Retenciones");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(btnBuscar, gridBagConstraints);

        jScrollPane1.setViewportView(tblRetenciones);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 9;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 5;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LAST_LINE_START;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jScrollPane1, gridBagConstraints);

        jScrollPane2.setViewportView(tblRetIva);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 70;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jScrollPane2, gridBagConstraints);

        jScrollPane3.setViewportView(tblRetRenta);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jScrollPane3, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Iva");
        jLabel6.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jLabel6, gridBagConstraints);

        jLabel7.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Retención");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jLabel7, gridBagConstraints);

        chkTodosIva.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        chkTodosIva.setText("Todos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        getContentPane().add(chkTodosIva, gridBagConstraints);

        chkTodosRenta.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        chkTodosRenta.setText("Todos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        getContentPane().add(chkTodosRenta, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(cmbTipo, gridBagConstraints);

        chkTodosTipo.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        chkTodosTipo.setText("Todos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        getContentPane().add(chkTodosTipo, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        getContentPane().add(lblEspacio1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.weightx = 0.1;
        getContentPane().add(lblEspacio3, gridBagConstraints);

        jLabel8.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel8.setText("Renta:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jLabel8, gridBagConstraints);

        btnLimpiarFechaInicial.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/pequenos/clear.png"))); // NOI18N
        btnLimpiarFechaInicial.setToolTipText("limpiar fecha");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 11;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(btnLimpiarFechaInicial, gridBagConstraints);

        btnLimpiarFechaFinal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/pequenos/clear.png"))); // NOI18N
        btnLimpiarFechaFinal.setToolTipText("limpiar fecha");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 11;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(btnLimpiarFechaFinal, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    public JButton getBtnBuscarProveedor() {
        return btnBuscarProveedor;
    }

    public void setBtnBuscarProveedor(JButton btnBuscarProveedor) {
        this.btnBuscarProveedor = btnBuscarProveedor;
    }

    public JCheckBox getChkTodos() {
        return chkTodos;
    }

    public void setChkTodos(JCheckBox chkTodos) {
        this.chkTodos = chkTodos;
    }

    public JComboBox<SriRetencionRenta> getCmbRetencionRenta() {
        return cmbRetencionRenta;
    }

    public void setCmbRetencionRenta(JComboBox<SriRetencionRenta> cmbRetencionRenta) {
        this.cmbRetencionRenta = cmbRetencionRenta;
    }

    public JDateChooser getDateFechaFin() {
        return dateFechaFin;
    }

    public void setDateFechaFin(JDateChooser dateFechaFin) {
        this.dateFechaFin = dateFechaFin;
    }

    public JDateChooser getDateFechaInicio() {
        return dateFechaInicio;
    }

    public void setDateFechaInicio(JDateChooser dateFechaInicio) {
        this.dateFechaInicio = dateFechaInicio;
    }

    public JTextField getTxtProveedor() {
        return txtProveedor;
    }

    public void setTxtProveedor(JTextField txtProveedor) {
        this.txtProveedor = txtProveedor;
    }

    public JComboBox<SriRetencionIva> getCmbRetencionIva() {
        return cmbRetencionIva;
    }

    public void setCmbRetencionIva(JComboBox<SriRetencionIva> cmbRetencionIva) {
        this.cmbRetencionIva = cmbRetencionIva;
    }

    public JTable getTblRetenciones() {
        return tblRetenciones;
    }

    public void setTblRetenciones(JTable tblRetenciones) {
        this.tblRetenciones = tblRetenciones;
    }

    public JTable getTblRetIva() {
        return tblRetIva;
    }

    public void setTblRetIva(JTable tblRetIva) {
        this.tblRetIva = tblRetIva;
    }

    public JTable getTblRetRenta() {
        return tblRetRenta;
    }

    public void setTblRetRenta(JTable tblRetRenta) {
        this.tblRetRenta = tblRetRenta;
    }

    public JCheckBox getChkTodosIva() {
        return chkTodosIva;
    }

    public void setChkTodosIva(JCheckBox chkTodosIva) {
        this.chkTodosIva = chkTodosIva;
    }

    public JCheckBox getChkTodosRenta() {
        return chkTodosRenta;
    }

    public void setChkTodosRenta(JCheckBox chkTodosRenta) {
        this.chkTodosRenta = chkTodosRenta;
    }

    public JCheckBox getChkTodosTipo() {
        return chkTodosTipo;
    }

    public void setChkTodosTipo(JCheckBox chkTodosTipo) {
        this.chkTodosTipo = chkTodosTipo;
    }

    public JComboBox<SriRetencion> getCmbTipo() {
        return cmbTipo;
    }

    public void setCmbTipo(JComboBox<SriRetencion> cmbTipo) {
        this.cmbTipo = cmbTipo;
    }

    public JButton getBtnLimpiarFechaFinal() {
        return btnLimpiarFechaFinal;
    }

    public void setBtnLimpiarFechaFinal(JButton btnLimpiarFechaFinal) {
        this.btnLimpiarFechaFinal = btnLimpiarFechaFinal;
    }

    public JButton getBtnLimpiarFechaInicial() {
        return btnLimpiarFechaInicial;
    }

    public void setBtnLimpiarFechaInicial(JButton btnLimpiarFechaInicial) {
        this.btnLimpiarFechaInicial = btnLimpiarFechaInicial;
    }

    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnBuscarProveedor;
    private javax.swing.JButton btnLimpiarFechaFinal;
    private javax.swing.JButton btnLimpiarFechaInicial;
    private javax.swing.JCheckBox chkTodos;
    private javax.swing.JCheckBox chkTodosIva;
    private javax.swing.JCheckBox chkTodosRenta;
    private javax.swing.JCheckBox chkTodosTipo;
    private javax.swing.JComboBox<SriRetencionIva> cmbRetencionIva;
    private javax.swing.JComboBox<SriRetencionRenta> cmbRetencionRenta;
    private javax.swing.JComboBox<SriRetencion> cmbTipo;
    private com.toedter.calendar.JDateChooser dateFechaFin;
    private com.toedter.calendar.JDateChooser dateFechaInicio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblEspacio1;
    private javax.swing.JLabel lblEspacio3;
    private javax.swing.JTable tblRetIva;
    private javax.swing.JTable tblRetRenta;
    private javax.swing.JTable tblRetenciones;
    private javax.swing.JTextField txtProveedor;
    // End of variables declaration//GEN-END:variables
}
