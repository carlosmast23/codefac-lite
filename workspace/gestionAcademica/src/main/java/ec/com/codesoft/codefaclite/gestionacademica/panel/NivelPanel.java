/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.gestionacademica.panel;

import ec.com.codesoft.codefaclite.controlador.aplicacion.ControladorCodefacInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.util.LimpiarAnotacion;
import ec.com.codesoft.codefaclite.corecodefaclite.validation.ValidacionCodefacAnotacion;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Nivel;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Carlos
 */
public abstract class NivelPanel extends ControladorCodefacInterface {

    /**
     * Creates new form NivelAcademicoPanel
     */
    public NivelPanel() {
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
        txtNombre = new javax.swing.JTextField();
        lblDescripcion = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        lblOrden = new javax.swing.JLabel();
        lblPosterior = new javax.swing.JLabel();
        txtOrden = new javax.swing.JTextField();
        cmbNivelPosterior = new javax.swing.JComboBox<>();
        lblEstado = new javax.swing.JLabel();
        cmbEstado = new javax.swing.JComboBox<>();
        lblEspacio2 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Nivel");
        getContentPane().setLayout(new java.awt.GridBagLayout());

        lblNombre.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblNombre.setText("Nombre:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        getContentPane().add(lblNombre, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(txtNombre, gridBagConstraints);

        lblDescripcion.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblDescripcion.setText("Descripcion:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        getContentPane().add(lblDescripcion, gridBagConstraints);

        txtDescripcion.setColumns(20);
        txtDescripcion.setRows(5);
        jScrollPane1.setViewportView(txtDescripcion);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jScrollPane1, gridBagConstraints);

        lblOrden.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblOrden.setText("Orden:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        getContentPane().add(lblOrden, gridBagConstraints);

        lblPosterior.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblPosterior.setText("Nivel Posterior:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        getContentPane().add(lblPosterior, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(txtOrden, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(cmbNivelPosterior, gridBagConstraints);

        lblEstado.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblEstado.setText("Estado:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        getContentPane().add(lblEstado, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(cmbEstado, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weighty = 2.5;
        getContentPane().add(lblEspacio2, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<GeneralEnumEstado> cmbEstado;
    private javax.swing.JComboBox<Nivel> cmbNivelPosterior;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDescripcion;
    private javax.swing.JLabel lblEspacio2;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblOrden;
    private javax.swing.JLabel lblPosterior;
    private javax.swing.JTextArea txtDescripcion;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtOrden;
    // End of variables declaration//GEN-END:variables

    @LimpiarAnotacion
    @ValidacionCodefacAnotacion(requerido = false, expresionRegular = "^[a-zA-Z\\s0-9.\\_\\-]*$", nombre = "Descripcion", expresionRegularMensaje = "No se permiten caracteres especiales")
    public JTextArea getTxtDescripcion() {
        return txtDescripcion;
    }

    public void setTxtDescripcion(JTextArea txtDescripcion) {
        this.txtDescripcion = txtDescripcion;
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
    @ValidacionCodefacAnotacion(requerido = true, expresionRegular = "^[0-9]+$", nombre = "Orden")
    public JTextField getTxtOrden() {
        return txtOrden;
    }

    public void setTxtOrden(JTextField txtOrden) {
        this.txtOrden = txtOrden;
    }

    public JComboBox<GeneralEnumEstado> getCmbEstado() {
        return cmbEstado;
    }

    public void setCmbEstado(JComboBox<GeneralEnumEstado> cmbEstado) {
        this.cmbEstado = cmbEstado;
    }

 
    public JComboBox<Nivel> getCmbNivelPosterior() {
        return cmbNivelPosterior;
    }

    public void setCmbNivelPosterior(JComboBox<Nivel> cmbNivelPosterior) {
        this.cmbNivelPosterior = cmbNivelPosterior;
    }

}
