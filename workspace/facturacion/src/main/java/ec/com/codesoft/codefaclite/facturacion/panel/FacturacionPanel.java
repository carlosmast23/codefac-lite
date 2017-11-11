/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.panel;

import ec.com.codesoft.codefaclite.controlador.aplicacion.ControladorCodefacInterface;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author Carlos
 */
public abstract class FacturacionPanel extends ControladorCodefacInterface {

    /**
     * Creates new form FacturacionPanel
     */
    public FacturacionPanel() {
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

        panelDatosEmpresa = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblRuc = new javax.swing.JLabel();
        lblNombreComercial = new javax.swing.JLabel();
        lblDireccion = new javax.swing.JLabel();
        lblTelefonos = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        PanelCliente = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtCliente = new javax.swing.JTextField();
        btnBuscarCliente = new javax.swing.JButton();
        bntAgregarCliente = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblDireccionCliente = new javax.swing.JLabel();
        lblNombreCliente = new javax.swing.JLabel();
        lblTelefonoCliente = new javax.swing.JLabel();

        getContentPane().setLayout(new java.awt.GridBagLayout());

        panelDatosEmpresa.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos Empresa"));
        panelDatosEmpresa.setLayout(new java.awt.GridBagLayout());

        jLabel1.setText("Ruc:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelDatosEmpresa.add(jLabel1, gridBagConstraints);

        jLabel2.setText("Nombre Comercial:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelDatosEmpresa.add(jLabel2, gridBagConstraints);

        jLabel3.setText("Direccion:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelDatosEmpresa.add(jLabel3, gridBagConstraints);

        lblRuc.setBackground(new java.awt.Color(255, 255, 255));
        lblRuc.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblRuc.setText("jLabel5");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 47;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelDatosEmpresa.add(lblRuc, gridBagConstraints);

        lblNombreComercial.setBackground(new java.awt.Color(255, 255, 255));
        lblNombreComercial.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblNombreComercial.setText("jLabel6");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 49;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelDatosEmpresa.add(lblNombreComercial, gridBagConstraints);

        lblDireccion.setBackground(new java.awt.Color(255, 255, 255));
        lblDireccion.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblDireccion.setText("jLabel7");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 49;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelDatosEmpresa.add(lblDireccion, gridBagConstraints);

        lblTelefonos.setBackground(new java.awt.Color(255, 255, 255));
        lblTelefonos.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblTelefonos.setText("jLabel8");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 49;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelDatosEmpresa.add(lblTelefonos, gridBagConstraints);

        jLabel5.setText("Telefonos:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        panelDatosEmpresa.add(jLabel5, gridBagConstraints);

        getContentPane().add(panelDatosEmpresa, new java.awt.GridBagConstraints());

        PanelCliente.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos Cliente"));
        PanelCliente.setLayout(new java.awt.GridBagLayout());

        jLabel4.setText("Cliente:");
        PanelCliente.add(jLabel4, new java.awt.GridBagConstraints());

        txtCliente.setText("jTextField1");
        txtCliente.setEnabled(false);
        PanelCliente.add(txtCliente, new java.awt.GridBagConstraints());

        btnBuscarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/find2-ico.png"))); // NOI18N
        btnBuscarCliente.setText("Buscar");
        PanelCliente.add(btnBuscarCliente, new java.awt.GridBagConstraints());

        bntAgregarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/mas-ico.png"))); // NOI18N
        bntAgregarCliente.setText("Agregar");
        PanelCliente.add(bntAgregarCliente, new java.awt.GridBagConstraints());

        jLabel6.setText("Nombres:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        PanelCliente.add(jLabel6, gridBagConstraints);

        jLabel7.setText("Telefono:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        PanelCliente.add(jLabel7, gridBagConstraints);

        jLabel8.setText("Direccion");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        PanelCliente.add(jLabel8, gridBagConstraints);

        lblDireccionCliente.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblDireccionCliente.setText("jLabel9");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        PanelCliente.add(lblDireccionCliente, gridBagConstraints);

        lblNombreCliente.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblNombreCliente.setText("jLabel10");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        PanelCliente.add(lblNombreCliente, gridBagConstraints);

        lblTelefonoCliente.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblTelefonoCliente.setText("jLabel11");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        PanelCliente.add(lblTelefonoCliente, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        getContentPane().add(PanelCliente, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelCliente;
    private javax.swing.JButton bntAgregarCliente;
    private javax.swing.JButton btnBuscarCliente;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel lblDireccion;
    private javax.swing.JLabel lblDireccionCliente;
    private javax.swing.JLabel lblNombreCliente;
    private javax.swing.JLabel lblNombreComercial;
    private javax.swing.JLabel lblRuc;
    private javax.swing.JLabel lblTelefonoCliente;
    private javax.swing.JLabel lblTelefonos;
    private javax.swing.JPanel panelDatosEmpresa;
    private javax.swing.JTextField txtCliente;
    // End of variables declaration//GEN-END:variables

    public JButton getBntAgregarCliente() {
        return bntAgregarCliente;
    }

    public void setBntAgregarCliente(JButton bntAgregarCliente) {
        this.bntAgregarCliente = bntAgregarCliente;
    }

    public JButton getBtnBuscarCliente() {
        return btnBuscarCliente;
    }

    public void setBtnBuscarCliente(JButton btnBuscarCliente) {
        this.btnBuscarCliente = btnBuscarCliente;
    }

    public JLabel getLblDireccionCliente() {
        return lblDireccionCliente;
    }

    public JLabel getLblNombreCliente() {
        return lblNombreCliente;
    }

    public JLabel getLblTelefonoCliente() {
        return lblTelefonoCliente;
    }

    public JTextField getTxtCliente() {
        return txtCliente;
    }

    public JLabel getLblDireccion() {
        return lblDireccion;
    }

    public JLabel getLblNombreComercial() {
        return lblNombreComercial;
    }

    public JLabel getLblRuc() {
        return lblRuc;
    }

    public JLabel getLblTelefonos() {
        return lblTelefonos;
    }
    
    
    
}
