/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.configuraciones.panel;

import ec.com.codesoft.codefaclite.controlador.aplicacion.ControladorCodefacInterface;
import javax.swing.JButton;
import javax.swing.JTextField;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public abstract class RespaldarInformacionPanel extends ControladorCodefacInterface
{

    /**
     * Creates new form RespaldarInformacion
     */
    public RespaldarInformacionPanel() {
        initComponents();
    }
    
    public JButton getBtnRespaldar() {
        return btnRespaldar;
    }

    public void setBtnRespaldar(JButton btnRespaldar) {
        this.btnRespaldar = btnRespaldar;
    }

    public JButton getBtnGuardarLocalizacion() {
        return btnGuardarLocalizacion;
    }

    public void setBtnGuardarLocalizacion(JButton btnGuardarLocalizacion) {
        this.btnGuardarLocalizacion = btnGuardarLocalizacion;
    }

    public JTextField getTxtUbicacionRespaldo() {
        return txtUbicacionRespaldo;
    }

    public void setTxtUbicacionRespaldo(JTextField txtUbicacionRespaldo) {
        this.txtUbicacionRespaldo = txtUbicacionRespaldo;
    }

    public JButton getBtnRespaldarCorreo() {
        return btnRespaldarCorreo;
    }

    public void setBtnRespaldarCorreo(JButton btnRespaldarCorreo) {
        this.btnRespaldarCorreo = btnRespaldarCorreo;
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

        btnRespaldar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btnGuardarLocalizacion = new javax.swing.JButton();
        txtUbicacionRespaldo = new javax.swing.JTextField();
        lblEspacio1 = new javax.swing.JLabel();
        lblEspacio2 = new javax.swing.JLabel();
        btnRespaldarCorreo = new javax.swing.JButton();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Respaldar BD");
        getContentPane().setLayout(new java.awt.GridBagLayout());

        btnRespaldar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/guardar.png"))); // NOI18N
        btnRespaldar.setText("Respaldar en mi PC");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(btnRespaldar, gridBagConstraints);

        jLabel1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel1.setText("Seleccionar ubicación para respaldos:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jLabel1, gridBagConstraints);

        btnGuardarLocalizacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/buscar-ico.png"))); // NOI18N
        btnGuardarLocalizacion.setText("Guardar");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        getContentPane().add(btnGuardarLocalizacion, gridBagConstraints);

        txtUbicacionRespaldo.setEditable(false);
        txtUbicacionRespaldo.setText("                                   ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(txtUbicacionRespaldo, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.4;
        getContentPane().add(lblEspacio1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 0.1;
        getContentPane().add(lblEspacio2, gridBagConstraints);

        btnRespaldarCorreo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/32Pixeles/email.png"))); // NOI18N
        btnRespaldarCorreo.setText("Respaldo Correo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(btnRespaldarCorreo, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardarLocalizacion;
    private javax.swing.JButton btnRespaldar;
    private javax.swing.JButton btnRespaldarCorreo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblEspacio1;
    private javax.swing.JLabel lblEspacio2;
    private javax.swing.JTextField txtUbicacionRespaldo;
    // End of variables declaration//GEN-END:variables
}
