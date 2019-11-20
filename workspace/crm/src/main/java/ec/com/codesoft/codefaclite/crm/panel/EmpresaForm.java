/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.crm.panel;

import ec.com.codesoft.codefaclite.controlador.aplicacion.ControladorCodefacInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.ayuda.AyudaCodefacAnotacion;
import ec.com.codesoft.codefaclite.corecodefaclite.util.LimpiarAnotacion;
import ec.com.codesoft.codefaclite.corecodefaclite.validation.ValidacionCodefacAnotacion;
import ec.com.codesoft.codefaclite.utilidades.validadores.ExpresionRegular;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Robert
 */
public abstract class EmpresaForm extends ControladorCodefacInterface{
    /**
     * Creates new form ConfigurarEmisor
     */
    public EmpresaForm() {
        initComponents();
        this.jTextLogo.setEnabled(false);
    }
    
    /*@LimpiarAnotacion
    @ValidacionCodefacAnotacion(requerido= false, min=7, max=10,expresionRegularMensaje = "Ingrese un numero valido", expresionRegular = "\\d+")
    public JTextField getjTextTelefono() {
        return jTextTelefono;
    }*/
    
    /*
    public void setjTextTelefono(JTextField jTextTelefono) {
        this.jTextTelefono = jTextTelefono;
    }*/
    
    public JCheckBox getjCheckBLlevaContabilidad() {
        return jCheckBLlevaContabilidad;
    }

    public void setjCheckBLlevaContabilidad(JCheckBox jCheckBLlevaContabilidad) {
        this.jCheckBLlevaContabilidad = jCheckBLlevaContabilidad;
    }

    public JPanel getjPanelConfiguracionEmisor() {
        return jPanelConfiguracionEmisor;
    }

    public void setjPanelConfiguracionEmisor(JPanel jPanelConfiguracionEmisor) {
        this.jPanelConfiguracionEmisor = jPanelConfiguracionEmisor;
    }
/*
    public JTextField getjTextActividadComercial() {
        return jTextTelefono;
    }

    public void setjTextActividadComercial(JTextField jTextActividadComercial) {
        this.jTextTelefono = jTextActividadComercial;
    }
    
    
    @LimpiarAnotacion
    @ValidacionCodefacAnotacion(requerido=true , min=5 ,max = 1024,expresionRegular = "^[A-Za-z0-9\\s.\\_\\-\\,\\ ]*$",nombre = "Direccion Matriz")
    public JTextArea getTxtDireccion() {
        return txtDireccion;
    }

    public void setTxtDireccion(JTextArea jTxtDireccion) {
        this.txtDireccion = jTxtDireccion;
    }*/
    
    
    @LimpiarAnotacion
    @ValidacionCodefacAnotacion(requerido=true , min=1 ,max = 300,expresionRegular = ExpresionRegular.textoSinSaltosLinea,nombre = "Nombre Comercial")
    public JTextField getjTextNombreComercial() {
        return jTextNombreComercial;
    }

    public void setjTextNombreComercial(JTextField jTextNombreComercial) {
        this.jTextNombreComercial = jTextNombreComercial;
    }
    
    @LimpiarAnotacion
    @ValidacionCodefacAnotacion(requerido=true , min=1 ,max = 300,expresionRegular = ExpresionRegular.textoSinSaltosLinea,nombre = "Apellidos y Nombres | Razon Social")
    public JTextField getjTextNombreSocial() {
        return jTextNombreSocial;
    }

    public void setjTextNombreSocial(JTextField jTextNombreSocial) {
        this.jTextNombreSocial = jTextNombreSocial;
    }
    
    @LimpiarAnotacion
    @ValidacionCodefacAnotacion(requerido = false,min=2 ,max = 1024,expresionRegular = "^[A-Za-z0-9\\s]*$",nombre = "Contribuyente especial Nro. Resolucion")
    public JTextField getjTextNumContribuyente() {
        return jTextNumContribuyente;
    }

    public void setjTextNumContribuyente(JTextField jTextNumContribuyente) {
        this.jTextNumContribuyente = jTextNumContribuyente;
    }
    
    @LimpiarAnotacion
    @ValidacionCodefacAnotacion(personalizado = {"validarRuc"} , requerido=true , min=0 ,max = 13, nombre = "RUC")
    public JTextField getjTextRuc() {
        return jTextRuc;
    }

    public void setjTextRuc(JTextField jTextRuc) {
        this.jTextRuc = jTextRuc;
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

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanelConfiguracionEmisor = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextRuc = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextNombreSocial = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextNombreComercial = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTextNumContribuyente = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jCheckBLlevaContabilidad = new javax.swing.JCheckBox();
        jLabel10 = new javax.swing.JLabel();
        jTextLogo = new javax.swing.JTextField();
        lblEspacio1 = new javax.swing.JLabel();
        lblEspacio2 = new javax.swing.JLabel();
        btnCargarImagen = new javax.swing.JButton();
        lblEspacio3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lblCelular = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtCelular = new javax.swing.JTextField();
        txtFacebook = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAdicional = new javax.swing.JTextArea();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Empresa");
        setToolTipText("");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/edit-icon.png"))); // NOI18N

        jPanelConfiguracionEmisor.setBorder(javax.swing.BorderFactory.createTitledBorder("Características Generales"));
        jPanelConfiguracionEmisor.setName(""); // NOI18N
        jPanelConfiguracionEmisor.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel1.setText("RUC:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        jPanelConfiguracionEmisor.add(jLabel1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        jPanelConfiguracionEmisor.add(jTextRuc, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel2.setText("Apellidos y Nombres | Razón Social:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        jPanelConfiguracionEmisor.add(jLabel2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        jPanelConfiguracionEmisor.add(jTextNombreSocial, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel3.setText("Nombre Comercial:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        jPanelConfiguracionEmisor.add(jLabel3, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        jPanelConfiguracionEmisor.add(jTextNombreComercial, gridBagConstraints);

        jLabel8.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel8.setText("Contribuyente Especial | Nro. Resolución:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        jPanelConfiguracionEmisor.add(jLabel8, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        jPanelConfiguracionEmisor.add(jTextNumContribuyente, gridBagConstraints);

        jLabel9.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel9.setText("Obligado a Llevar Contabilidad:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        jPanelConfiguracionEmisor.add(jLabel9, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jPanelConfiguracionEmisor.add(jCheckBLlevaContabilidad, gridBagConstraints);

        jLabel10.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel10.setText("Logo(Imagen):");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        jPanelConfiguracionEmisor.add(jLabel10, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        jPanelConfiguracionEmisor.add(jTextLogo, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        jPanelConfiguracionEmisor.add(lblEspacio1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        jPanelConfiguracionEmisor.add(lblEspacio2, gridBagConstraints);

        btnCargarImagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/find2-ico.png"))); // NOI18N
        btnCargarImagen.setToolTipText("Buscar imagen en su computador");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jPanelConfiguracionEmisor.add(btnCargarImagen, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        jPanelConfiguracionEmisor.add(lblEspacio3, gridBagConstraints);

        jTabbedPane1.addTab("Datos generales", jPanelConfiguracionEmisor);

        lblCelular.setText("WhatsApp:");

        jLabel5.setText("Facebook:");

        txtCelular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelularActionPerformed(evt);
            }
        });

        jLabel11.setText("Texto Adicional:");

        txtAdicional.setColumns(20);
        txtAdicional.setRows(5);
        jScrollPane2.setViewportView(txtAdicional);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel5)
                    .addComponent(lblCelular))
                .addGap(36, 36, 36)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtCelular)
                        .addComponent(txtFacebook, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCelular)
                    .addComponent(txtCelular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(txtFacebook, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jTabbedPane1.addTab("Datos adicionales", jPanel1);

        getContentPane().add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCelularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelularActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelularActionPerformed

    public JTextField getjTextLogo() {
        return jTextLogo;
    }

    public void setjTextLogo(JTextField jTextLogo) {
        this.jTextLogo = jTextLogo;
    }
    @ValidacionCodefacAnotacion(requerido= false, min=7, max=10,expresionRegularMensaje = "Ingrese un numero valido", expresionRegular = "\\d+")
    public JTextField getTxtCelular() {
        return txtCelular;
    }

    public void setTxtCelular(JTextField txtCelular) {
        this.txtCelular = txtCelular;
    }
    @ValidacionCodefacAnotacion(requerido=false , min=5 ,max = 50,expresionRegular = "^[A-Za-z0-9\\s.\\_\\-\\,\\ ]*$",nombre = "Direccion Matriz")
    public JTextField getTxtFacebook() {
        return txtFacebook;
    }

    public void setTxtFacebook(JTextField txtFacebook) {
        this.txtFacebook = txtFacebook;
    }
    @ValidacionCodefacAnotacion(requerido=false , min=5 ,max = 100,nombre = "Direccion Matriz")
    public JTextArea getTxtAdicional() {
        return txtAdicional;
    }

    public void setTxtAdicional(JTextArea txtAdicional) {
        this.txtAdicional = txtAdicional;
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCargarImagen;
    private javax.swing.JCheckBox jCheckBLlevaContabilidad;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelConfiguracionEmisor;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextLogo;
    private javax.swing.JTextField jTextNombreComercial;
    private javax.swing.JTextField jTextNombreSocial;
    private javax.swing.JTextField jTextNumContribuyente;
    private javax.swing.JTextField jTextRuc;
    private javax.swing.JLabel lblCelular;
    private javax.swing.JLabel lblEspacio1;
    private javax.swing.JLabel lblEspacio2;
    private javax.swing.JLabel lblEspacio3;
    private javax.swing.JTextArea txtAdicional;
    private javax.swing.JTextField txtCelular;
    private javax.swing.JTextField txtFacebook;
    // End of variables declaration//GEN-END:variables

    public JButton getBtnCargarImagen() {
        return btnCargarImagen;
    }

    public void setBtnCargarImagen(JButton btnCargarImagen) {
        this.btnCargarImagen = btnCargarImagen;
    }

    public JLabel getLblCelular() {
        return lblCelular;
    }

    
    
}
