/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.gestionacademica.panel;

import com.toedter.calendar.JDateChooser;
import ec.com.codesoft.codefaclite.controlador.aplicacion.ControladorCodefacInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.util.LimpiarAnotacion;
import ec.com.codesoft.codefaclite.corecodefaclite.validation.ValidacionCodefacAnotacion;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Carlos
 */
public abstract class PeriodoPanel extends ControladorCodefacInterface {

    /**
     * Creates new form PeriodoPanel
     */
    public PeriodoPanel() {
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

        lblNombre = new javax.swing.JLabel();
        lblFechaInicio = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        lblFechaFin = new javax.swing.JLabel();
        dateFechaInicio = new com.toedter.calendar.JDateChooser();
        dateFechaFin = new com.toedter.calendar.JDateChooser();
        lblEstado = new javax.swing.JLabel();
        cmbEstado = new javax.swing.JComboBox<>();
        lblObservacion = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtObservacion = new javax.swing.JTextArea();
        lblEspacio3 = new javax.swing.JLabel();
        lblEspacio2 = new javax.swing.JLabel();
        lblEspacio1 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Periodo");
        getContentPane().setLayout(new java.awt.GridBagLayout());

        lblNombre.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblNombre.setText("Nombre:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(lblNombre, gridBagConstraints);

        lblFechaInicio.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblFechaInicio.setText("Fecha Inicio:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(lblFechaInicio, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 420;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(txtNombre, gridBagConstraints);

        lblFechaFin.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblFechaFin.setText("Fecha Fin:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(lblFechaFin, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 89;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(dateFechaInicio, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 81;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(dateFechaFin, gridBagConstraints);

        lblEstado.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblEstado.setText("Estado:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(lblEstado, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(cmbEstado, gridBagConstraints);

        lblObservacion.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblObservacion.setText("Observacion:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(lblObservacion, gridBagConstraints);

        txtObservacion.setColumns(20);
        txtObservacion.setRows(5);
        jScrollPane1.setViewportView(txtObservacion);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 222;
        gridBagConstraints.ipady = 63;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jScrollPane1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weighty = 1.5;
        getContentPane().add(lblEspacio3, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.7;
        getContentPane().add(lblEspacio2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.6;
        getContentPane().add(lblEspacio1, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<GeneralEnumEstado> cmbEstado;
    private com.toedter.calendar.JDateChooser dateFechaFin;
    private com.toedter.calendar.JDateChooser dateFechaInicio;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblEspacio1;
    private javax.swing.JLabel lblEspacio2;
    private javax.swing.JLabel lblEspacio3;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblFechaFin;
    private javax.swing.JLabel lblFechaInicio;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblObservacion;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextArea txtObservacion;
    // End of variables declaration//GEN-END:variables

    public JComboBox<GeneralEnumEstado> getCmbEstado() {
        return cmbEstado;
    }

    public void setCmbEstado(JComboBox<GeneralEnumEstado> cmbEstado) {
        this.cmbEstado = cmbEstado;
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

    @LimpiarAnotacion
    @ValidacionCodefacAnotacion(requerido = true, expresionRegular = "^[a-zA-Z\\s0-9.\\_\\-]*$", nombre = "Nombre", expresionRegularMensaje = "No se permiten caracteres especiales")
    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public void setTxtNombre(JTextField txtNombre) {
        this.txtNombre = txtNombre;
    }

    @LimpiarAnotacion
    @ValidacionCodefacAnotacion(requerido = false, expresionRegular = "^[a-zA-Z\\s0-9.\\_\\-]*$", nombre = "Observaciones", expresionRegularMensaje = "No se permiten caracteres especiales")
    public JTextArea getTxtObservacion() {
        return txtObservacion;
    }

    public void setTxtObservacion(JTextArea txtObservacion) {
        this.txtObservacion = txtObservacion;
    }

}
