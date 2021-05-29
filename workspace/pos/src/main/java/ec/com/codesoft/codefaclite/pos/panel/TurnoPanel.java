/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.pos.panel;

import ec.com.codesoft.codefaclite.controlador.aplicacion.ControladorCodefacInterface;
import ec.com.codesoft.codefaclite.controlador.vistas.converter.StringToBigDecimalConverter;
import ec.com.codesoft.codefaclite.controlador.vistas.core.TextFieldBinding;
import ec.com.codesoft.codefaclite.corecodefaclite.util.LimpiarAnotacion;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

/**
 *
 * @author Robert
 */
public abstract class TurnoPanel extends ControladorCodefacInterface {

    /**
     * Creates new form TurnoPanel
     */
    public TurnoPanel() {
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
        jSpinner1 = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextNombre = new javax.swing.JTextField();
        Date dateHoraInicial = new Date();
        SpinnerDateModel sm1 = new SpinnerDateModel(dateHoraInicial, null, null, Calendar.HOUR_OF_DAY);
        jSpinnerHoraInicial = new javax.swing.JSpinner(sm1);
        Date dateHoraFinal = new Date();
        SpinnerDateModel sm2 = new SpinnerDateModel(dateHoraFinal, null, null, Calendar.HOUR_OF_DAY);
        jSpinnerHoraFinal = new javax.swing.JSpinner(sm2);
        jLabel5 = new javax.swing.JLabel();

        jLabel1.setText("jLabel1");

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Turno");
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jLabel2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel2.setText("Nombre:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jLabel2, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel3.setText("Hora Inicial:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jLabel3, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel4.setText("Hora Final:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jLabel4, gridBagConstraints);

        jTextNombre.setText("          ");
        jTextNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextNombreActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jTextNombre, gridBagConstraints);

        JSpinner.DateEditor de1 = new javax.swing.JSpinner.DateEditor(jSpinnerHoraInicial, "HH:mm:ss");
        jSpinnerHoraInicial.setEditor(de1);
        jSpinnerHoraInicial.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jSpinnerHoraInicial, gridBagConstraints);

        JSpinner.DateEditor de2 = new javax.swing.JSpinner.DateEditor(jSpinnerHoraFinal, "HH:mm:ss");
        jSpinnerHoraFinal.setEditor(de2);
        jSpinnerHoraFinal.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jSpinnerHoraFinal, gridBagConstraints);

        jLabel5.setText("          ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        getContentPane().add(jLabel5, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextNombreActionPerformed

    public JSpinner getjSpinnerHoraFinal() {
        return jSpinnerHoraFinal;
    }

    public void setjSpinnerHoraFinal(JSpinner jSpinnerHoraFinal) {
        this.jSpinnerHoraFinal = jSpinnerHoraFinal;
    }

    public JSpinner getjSpinnerHoraInicial() {
        return jSpinnerHoraInicial;
    }

    public void setjSpinnerHoraInicial(JSpinner jSpinnerHoraInicial) {
        this.jSpinnerHoraInicial = jSpinnerHoraInicial;
    }

    @LimpiarAnotacion
    @TextFieldBinding(value = "controlador.turno.nombre")
    public JTextField getjTextNombre() {
        return jTextNombre;
    }

    public void setjTextNombre(JTextField jTextNombre) {
        this.jTextNombre = jTextNombre;
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JSpinner jSpinnerHoraFinal;
    private javax.swing.JSpinner jSpinnerHoraInicial;
    private javax.swing.JTextField jTextNombre;
    // End of variables declaration//GEN-END:variables
}