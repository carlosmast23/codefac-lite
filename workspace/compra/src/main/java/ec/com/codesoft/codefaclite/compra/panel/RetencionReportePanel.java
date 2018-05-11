/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.compra.panel;

import com.toedter.calendar.JDateChooser;
import ec.com.codesoft.codefaclite.controlador.aplicacion.ControladorCodefacInterface;
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

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Reporte Retenciones");
        getContentPane().setLayout(new java.awt.GridBagLayout());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.ipadx = 256;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(14, 13, 0, 0);
        getContentPane().add(cmbRetencionIva, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 256;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 13, 0, 0);
        getContentPane().add(cmbRetencionRenta, gridBagConstraints);

        dateFechaInicio.setDateFormatString("yyyy-MM-dd");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.ipadx = 147;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 10, 0, 18);
        getContentPane().add(dateFechaInicio, gridBagConstraints);

        dateFechaFin.setDateFormatString("yyyy-MM-dd");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 147;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 10, 0, 18);
        getContentPane().add(dateFechaFin, gridBagConstraints);

        jLabel1.setText("Fecha inicial:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 34, 0, 0);
        getContentPane().add(jLabel1, gridBagConstraints);

        jLabel2.setText("Proveedor:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 23;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(16, 10, 0, 0);
        getContentPane().add(jLabel2, gridBagConstraints);

        btnBuscarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/pequenos/Zoom.png"))); // NOI18N
        btnBuscarProveedor.setText("Buscar");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 10, 0, 0);
        getContentPane().add(btnBuscarProveedor, gridBagConstraints);

        jLabel3.setText("Fecha final:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.ipadx = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(14, 37, 0, 0);
        getContentPane().add(jLabel3, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.ipadx = 168;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(13, 10, 0, 0);
        getContentPane().add(txtProveedor, gridBagConstraints);

        chkTodos.setText("Todos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 11, 0, 0);
        getContentPane().add(chkTodos, gridBagConstraints);

        jLabel4.setText("IVA:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 56;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(17, 10, 0, 0);
        getContentPane().add(jLabel4, gridBagConstraints);

        jLabel5.setText("Renta:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.ipadx = 44;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 10, 0, 0);
        getContentPane().add(jLabel5, gridBagConstraints);

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/find2-ico.png"))); // NOI18N
        btnBuscar.setText("Buscar Compra");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 37, 0, 0);
        getContentPane().add(btnBuscar, gridBagConstraints);

        tblRetenciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Proveedor", "Preimpreso", "Base imponible", "Porcentaje", "Código", "Valor"
            }
        ));
        jScrollPane1.setViewportView(tblRetenciones);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 678;
        gridBagConstraints.ipady = 148;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(18, 10, 291, 18);
        getContentPane().add(jScrollPane1, gridBagConstraints);

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
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnBuscarProveedor;
    private javax.swing.JCheckBox chkTodos;
    private javax.swing.JComboBox<SriRetencionIva> cmbRetencionIva;
    private javax.swing.JComboBox<SriRetencionRenta> cmbRetencionRenta;
    private com.toedter.calendar.JDateChooser dateFechaFin;
    private com.toedter.calendar.JDateChooser dateFechaInicio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblRetenciones;
    private javax.swing.JTextField txtProveedor;
    // End of variables declaration//GEN-END:variables
}
