/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.pos.panel;

import ec.com.codesoft.codefaclite.controlador.aplicacion.ControladorCodefacInterface;
import ec.com.codesoft.codefaclite.controlador.vistas.core.TextFieldBinding;
import ec.com.codesoft.codefaclite.controlador.vistas.core.components.ButtonBinding;
import ec.com.codesoft.codefaclite.controlador.vistas.core.components.ComboBoxBinding;
import ec.com.codesoft.codefaclite.corecodefaclite.util.LimpiarAnotacion;
import ec.com.codesoft.codefaclite.corecodefaclite.validation.ValidacionCodefacAnotacion;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.utilidades.validadores.ExpresionRegular;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Robert
 */
public abstract class CajaPermisoPanel extends ControladorCodefacInterface {

    /**
     * Creates new form CajaPermisoPanel
     */
    public CajaPermisoPanel() {
        initComponents();
    }
    
    @ButtonBinding(actionListener = "controlador.listenerBotonBuscarCaja")
    public JButton getjButtonCaja() {
        return jButtonCaja;
    }

    public void setjButtonCaja(JButton jButtonCaja) {
        this.jButtonCaja = jButtonCaja;
    }

    @ButtonBinding(actionListener = "controlador.listenerBotonBuscarUsuario")
    public JButton getjButtonUsuario() {
        return jButtonUsuario;
    }

    public void setjButtonUsuario(JButton jButtonUsuario) {
        this.jButtonUsuario = jButtonUsuario;
    }

    @ComboBoxBinding(source = "controlador.estadosLista", valueSelect = "controlador.cajaPermiso.estadoEnum")
    public JComboBox<GeneralEnumEstado> getjComboEstado() {
        return jComboEstado;
    }

    public void setjComboEstado(JComboBox<GeneralEnumEstado> jComboEstado) {
        this.jComboEstado = jComboEstado;
    }

    @LimpiarAnotacion
    @ValidacionCodefacAnotacion(requerido = true, expresionRegular = ExpresionRegular.textoSimple, nombre = "Descripcion", expresionRegularMensaje = "No se permiten caracteres especiales")
    public JTextArea getjTextAreaDescripcion() {
        return jTextAreaDescripcion;
    }

    public void setjTextAreaDescripcion(JTextArea jTextAreaDescripcion) {
        this.jTextAreaDescripcion = jTextAreaDescripcion;
    }

    @LimpiarAnotacion
    @TextFieldBinding(value = "controlador.cajaPermiso.Caja.nombre")
    public JTextField getjTextCaja() {
        return jTextCaja;
    }

    public void setjTextCaja(JTextField jTextCaja) {
        this.jTextCaja = jTextCaja;
    }

    @LimpiarAnotacion
    @TextFieldBinding(value = "controlador.cajaPermiso.Usuario.nick")
    public JTextField getjTextUsuario() {
        return jTextUsuario;
    }

    public void setjTextUsuario(JTextField jTextUsuario) {
        this.jTextUsuario = jTextUsuario;
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
        jLabel2 = new javax.swing.JLabel();
        jTextCaja = new javax.swing.JTextField();
        jTextUsuario = new javax.swing.JTextField();
        jButtonCaja = new javax.swing.JButton();
        jButtonUsuario = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jComboEstado = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaDescripcion = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Caja Permiso");
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jLabel1.setText("Caja:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jLabel1, gridBagConstraints);

        jLabel2.setText("Usuario:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jLabel2, gridBagConstraints);

        jTextCaja.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jTextCaja, gridBagConstraints);

        jTextUsuario.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jTextUsuario, gridBagConstraints);

        jButtonCaja.setText("Ver");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jButtonCaja, gridBagConstraints);

        jButtonUsuario.setText("Ver");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jButtonUsuario, gridBagConstraints);

        jLabel3.setText("Estado:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jLabel3, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jComboEstado, gridBagConstraints);

        jLabel4.setText("Descripción:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jLabel4, gridBagConstraints);

        jTextAreaDescripcion.setColumns(20);
        jTextAreaDescripcion.setRows(5);
        jScrollPane1.setViewportView(jTextAreaDescripcion);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jScrollPane1, gridBagConstraints);

        jLabel5.setText("      ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        getContentPane().add(jLabel5, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCaja;
    private javax.swing.JButton jButtonUsuario;
    private javax.swing.JComboBox<GeneralEnumEstado> jComboEstado;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextAreaDescripcion;
    private javax.swing.JTextField jTextCaja;
    private javax.swing.JTextField jTextUsuario;
    // End of variables declaration//GEN-END:variables
}