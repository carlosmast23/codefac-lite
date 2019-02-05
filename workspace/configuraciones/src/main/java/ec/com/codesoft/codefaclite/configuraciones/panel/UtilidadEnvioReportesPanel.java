/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.configuraciones.panel;

import ec.com.codesoft.codefaclite.controlador.aplicacion.ControladorCodefacInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.FormatoReporteEnum;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import org.jdesktop.swingx.JXDatePicker;

/**
 *
 * @author Carlos
 */
public abstract class UtilidadEnvioReportesPanel extends ControladorCodefacInterface {

    /**
     * Creates new form UtilidadEnvioReportesPanel
     */
    public UtilidadEnvioReportesPanel() {
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
        chkNotaCredito = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        chkRetencion = new javax.swing.JCheckBox();
        chkGuiaRemision = new javax.swing.JCheckBox();
        chkVentas = new javax.swing.JCheckBox();
        cmbTipoEstadoReporte = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        txtEmpleadoDatos = new javax.swing.JTextField();
        btnBuscarEmpleado = new javax.swing.JButton();
        btnEnviarCorreo = new javax.swing.JButton();
        lblEspacio1 = new javax.swing.JLabel();
        lblEspacio2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cmbFechaFinal = new org.jdesktop.swingx.JXDatePicker();
        cmbFechaInicial = new org.jdesktop.swingx.JXDatePicker();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        chkPdf = new javax.swing.JCheckBox();
        chkExcel = new javax.swing.JCheckBox();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Utilidad Envio Reportes");
        setPreferredSize(new java.awt.Dimension(635, 451));

        jPanel1.setLayout(new java.awt.GridBagLayout());

        chkNotaCredito.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        chkNotaCredito.setSelected(true);
        chkNotaCredito.setText("Nota de Crédito:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(chkNotaCredito, gridBagConstraints);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setText("Seleccione el empleado a enviar el reporte:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(20, 5, 5, 5);
        jPanel1.add(jLabel1, gridBagConstraints);

        chkRetencion.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        chkRetencion.setSelected(true);
        chkRetencion.setText("Retenciones:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(chkRetencion, gridBagConstraints);

        chkGuiaRemision.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        chkGuiaRemision.setSelected(true);
        chkGuiaRemision.setText("Guía Remisión:");
        chkGuiaRemision.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkGuiaRemisionActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(chkGuiaRemision, gridBagConstraints);

        chkVentas.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        chkVentas.setSelected(true);
        chkVentas.setText("Ventas:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(chkVentas, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(cmbTipoEstadoReporte, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setText("Seleccione las configuraciones para los reportes:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(20, 5, 5, 5);
        jPanel1.add(jLabel2, gridBagConstraints);

        txtEmpleadoDatos.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtEmpleadoDatos, gridBagConstraints);

        btnBuscarEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/find2-ico.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel1.add(btnBuscarEmpleado, gridBagConstraints);

        btnEnviarCorreo.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        btnEnviarCorreo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/32Pixeles/email.png"))); // NOI18N
        btnEnviarCorreo.setText("Enviar Correo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(btnEnviarCorreo, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        jPanel1.add(lblEspacio1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jPanel1.add(lblEspacio2, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel3.setText("Seleccione los reportes que desea enviar:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(20, 5, 5, 5);
        jPanel1.add(jLabel3, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(cmbFechaFinal, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(cmbFechaInicial, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel4.setText("Formato Reporte:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel4, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel5.setText("Tipo de Reporte:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel5, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel6.setText("Seleccione el intervalo de Fechas para los reportes:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel6, gridBagConstraints);

        jLabel7.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel7.setText("Fecha Inicial:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel7, gridBagConstraints);

        jLabel8.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel8.setText("Fecha Final:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel8, gridBagConstraints);

        chkPdf.setSelected(true);
        chkPdf.setText("pdf");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel1.add(chkPdf, gridBagConstraints);

        chkExcel.setSelected(true);
        chkExcel.setText("excel");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel1.add(chkExcel, gridBagConstraints);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void chkGuiaRemisionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkGuiaRemisionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkGuiaRemisionActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscarEmpleado;
    private javax.swing.JButton btnEnviarCorreo;
    private javax.swing.JCheckBox chkExcel;
    private javax.swing.JCheckBox chkGuiaRemision;
    private javax.swing.JCheckBox chkNotaCredito;
    private javax.swing.JCheckBox chkPdf;
    private javax.swing.JCheckBox chkRetencion;
    private javax.swing.JCheckBox chkVentas;
    private org.jdesktop.swingx.JXDatePicker cmbFechaFinal;
    private org.jdesktop.swingx.JXDatePicker cmbFechaInicial;
    private javax.swing.JComboBox<ComprobanteEntity.ComprobanteEnumEstado> cmbTipoEstadoReporte;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblEspacio1;
    private javax.swing.JLabel lblEspacio2;
    private javax.swing.JTextField txtEmpleadoDatos;
    // End of variables declaration//GEN-END:variables

    public JCheckBox getChkGuiaRemision() {
        return chkGuiaRemision;
    }

    public void setChkGuiaRemision(JCheckBox chkGuiaRemision) {
        this.chkGuiaRemision = chkGuiaRemision;
    }

    public JCheckBox getChkNotaCredito() {
        return chkNotaCredito;
    }

    public void setChkNotaCredito(JCheckBox chkNotaCredito) {
        this.chkNotaCredito = chkNotaCredito;
    }

    public JCheckBox getChkRetencion() {
        return chkRetencion;
    }

    public void setChkRetencion(JCheckBox chkRetencion) {
        this.chkRetencion = chkRetencion;
    }

    public JCheckBox getChkVentas() {
        return chkVentas;
    }

    public void setChkVentas(JCheckBox chkVentas) {
        this.chkVentas = chkVentas;
    }

    public JButton getBtnBuscarEmpleado() {
        return btnBuscarEmpleado;
    }

    public JButton getBtnEnviarCorreo() {
        return btnEnviarCorreo;
    }

    public JTextField getTxtEmpleadoDatos() {
        return txtEmpleadoDatos;
    }

    public JXDatePicker getCmbFechaFinal() {
        return cmbFechaFinal;
    }

    public JXDatePicker getCmbFechaInicial() {
        return cmbFechaInicial;
    }

    /*public JComboBox<FormatoReporteEnum> getCmbFormatoReporte() {
        return cmbFormatoReporte;
    }*/

    public JComboBox<ComprobanteEntity.ComprobanteEnumEstado> getCmbTipoEstadoReporte() {
        return cmbTipoEstadoReporte;
    }

    public JCheckBox getChkExcel() {
        return chkExcel;
    }

    public JCheckBox getChkPdf() {
        return chkPdf;
    }

    
    
    

    
    
}

