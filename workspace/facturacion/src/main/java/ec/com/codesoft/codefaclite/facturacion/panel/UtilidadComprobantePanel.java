/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.panel;

import ec.com.codesoft.codefaclite.controlador.aplicacion.ControladorCodefacInterface;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JTable;

/**
 *
 * @author Carlos
 */
public abstract class UtilidadComprobantePanel extends ControladorCodefacInterface {

    /**
     * Creates new form UtilidadaComprobantePanel
     */
    public UtilidadComprobantePanel() {
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

        jProgressBar1 = new javax.swing.JProgressBar();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblComprobantes = new javax.swing.JTable();
        btnSiguienteEtapa = new javax.swing.JButton();
        cmbCarpetaComprobante = new javax.swing.JComboBox<>();
        cmbEstadoLimiteProcesar = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListCorreos = new javax.swing.JList<>();
        btnAgregarCorreo = new javax.swing.JButton();
        btnEliminarCorreo = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        tblComprobantes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tblComprobantes);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jScrollPane2, gridBagConstraints);

        btnSiguienteEtapa.setText("Procesar");
        btnSiguienteEtapa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSiguienteEtapaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        getContentPane().add(btnSiguienteEtapa, gridBagConstraints);

        cmbCarpetaComprobante.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        getContentPane().add(cmbCarpetaComprobante, gridBagConstraints);

        cmbEstadoLimiteProcesar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        getContentPane().add(cmbEstadoLimiteProcesar, gridBagConstraints);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Carpetas Comprobantes:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        getContentPane().add(jLabel1, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Etapa Final Procesar:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        getContentPane().add(jLabel2, gridBagConstraints);

        jScrollPane1.setViewportView(jListCorreos);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jScrollPane1, gridBagConstraints);

        btnAgregarCorreo.setText("+");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        getContentPane().add(btnAgregarCorreo, gridBagConstraints);

        btnEliminarCorreo.setText("X");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        getContentPane().add(btnEliminarCorreo, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("Correos Electronicos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        getContentPane().add(jLabel3, gridBagConstraints);

        jLabel4.setText("           ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 5;
        getContentPane().add(jLabel4, gridBagConstraints);

        jLabel5.setText("          ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        getContentPane().add(jLabel5, gridBagConstraints);

        jLabel6.setText("          ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 7;
        getContentPane().add(jLabel6, gridBagConstraints);

        jLabel7.setText("          ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        getContentPane().add(jLabel7, gridBagConstraints);

        jLabel8.setText("          ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        getContentPane().add(jLabel8, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSiguienteEtapaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiguienteEtapaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSiguienteEtapaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarCorreo;
    private javax.swing.JButton btnEliminarCorreo;
    private javax.swing.JButton btnSiguienteEtapa;
    private javax.swing.JComboBox<String> cmbCarpetaComprobante;
    private javax.swing.JComboBox<String> cmbEstadoLimiteProcesar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JList<String> jListCorreos;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblComprobantes;
    // End of variables declaration//GEN-END:variables


    public JButton getBtnSiguienteEtapa() {
        return btnSiguienteEtapa;
    }

    public void setBtnSiguienteEtapa(JButton btnSiguienteEtapa) {
        this.btnSiguienteEtapa = btnSiguienteEtapa;
    }

    public JComboBox<String> getCmbCarpetaComprobante() {
        return cmbCarpetaComprobante;
    }

    public void setCmbCarpetaComprobante(JComboBox<String> cmbCarpetaComprobante) {
        this.cmbCarpetaComprobante = cmbCarpetaComprobante;
    }

    public JTable getTblComprobantes() {
        return tblComprobantes;
    }

    public void setTblComprobantes(JTable tblComprobantes) {
        this.tblComprobantes = tblComprobantes;
    }

    public JComboBox<String> getCmbEstadoLimiteProcesar() {
        return cmbEstadoLimiteProcesar;
    }

    public void setCmbEstadoLimiteProcesar(JComboBox<String> cmbEstadoLimiteProcesar) {
        this.cmbEstadoLimiteProcesar = cmbEstadoLimiteProcesar;
    }

    public JList<String> getjListCorreos() {
        return jListCorreos;
    }

    public void setjListCorreos(JList<String> jListCorreos) {
        this.jListCorreos = jListCorreos;
    }

    public JButton getBtnAgregarCorreo() {
        return btnAgregarCorreo;
    }

    public JButton getBtnEliminarCorreo() {
        return btnEliminarCorreo;
    }
    
    
    
    

}
