/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.gestionacademica.panel;

import ec.com.codesoft.codefaclite.controlador.aplicacion.ControladorCodefacInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author CodesoftDesarrollo
 */
public abstract class ReporteDeudasEstudiantePanel extends ControladorCodefacInterface {

    /**
     * Creates new form ReporteDeudasEstudiantePanel
     */
    public ReporteDeudasEstudiantePanel() {
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tblDeudas = new javax.swing.JTable();
        lblEstudiante = new javax.swing.JLabel();
        txtEstudiante = new javax.swing.JTextField();
        btnBuscarEstudiante = new javax.swing.JButton();
        lblDeuda = new javax.swing.JLabel();
        lblTotalDeuda = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblperiodo = new javax.swing.JLabel();
        cmbPeriodo = new javax.swing.JComboBox<>();
        lblEspacio1 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Reporte Deudas por Estudiante");
        getContentPane().setLayout(new java.awt.GridBagLayout());

        tblDeudas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Rubro", "Valor"
            }
        ));
        jScrollPane1.setViewportView(tblDeudas);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(jScrollPane1, gridBagConstraints);

        lblEstudiante.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        lblEstudiante.setText("Estudiante");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(lblEstudiante, gridBagConstraints);

        txtEstudiante.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtEstudiante.setText("...");
        txtEstudiante.setEnabled(false);
        txtEstudiante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEstudianteActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(txtEstudiante, gridBagConstraints);

        btnBuscarEstudiante.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/find2-ico.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(btnBuscarEstudiante, gridBagConstraints);

        lblDeuda.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblDeuda.setText("Total Deuda:");
        lblDeuda.setToolTipText("");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(23, 78, 0, 0);
        getContentPane().add(lblDeuda, gridBagConstraints);

        lblTotalDeuda.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblTotalDeuda.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTotalDeuda.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 94;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(16, 10, 16, 20);
        getContentPane().add(lblTotalDeuda, gridBagConstraints);

        jLabel1.setText("                                                                                          ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        getContentPane().add(jLabel1, gridBagConstraints);

        lblperiodo.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        lblperiodo.setText("Periodo:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(lblperiodo, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(cmbPeriodo, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        getContentPane().add(lblEspacio1, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtEstudianteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEstudianteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEstudianteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscarEstudiante;
    private javax.swing.JComboBox<Periodo> cmbPeriodo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDeuda;
    private javax.swing.JLabel lblEspacio1;
    private javax.swing.JLabel lblEstudiante;
    private javax.swing.JLabel lblTotalDeuda;
    private javax.swing.JLabel lblperiodo;
    private javax.swing.JTable tblDeudas;
    private javax.swing.JTextField txtEstudiante;
    // End of variables declaration//GEN-END:variables


    public JButton getBtnBuscarEstudiante() {
        return btnBuscarEstudiante;
    }

    public void setBtnBuscarEstudiante(JButton btnBuscarEstudiante) {
        this.btnBuscarEstudiante = btnBuscarEstudiante;
    }

    public JTable getTblDeudas() {
        return tblDeudas;
    }

    public void setTblDeudas(JTable tblDeudas) {
        this.tblDeudas = tblDeudas;
    }

    public JTextField getTxtEstudiante() {
        return txtEstudiante;
    }

    public void setTxtEstudiante(JTextField txtEstudiante) {
        this.txtEstudiante = txtEstudiante;
    }

    public JLabel getLblTotalDeuda() {
        return lblTotalDeuda;
    }

    public void setLblTotalDeuda(JLabel lblTotalDeuda) {
        this.lblTotalDeuda = lblTotalDeuda;
    }

    public JComboBox<Periodo> getCmbPeriodo() {
        return cmbPeriodo;
    }

    public void setCmbPeriodo(JComboBox<Periodo> cmbPeriodo) {
        this.cmbPeriodo = cmbPeriodo;
    }
    
}
