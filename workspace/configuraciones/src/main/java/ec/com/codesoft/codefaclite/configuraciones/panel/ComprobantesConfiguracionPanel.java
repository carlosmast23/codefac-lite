/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.configuraciones.panel;

import ec.com.codesoft.codefaclite.controlador.aplicacion.ControladorCodefacInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.validation.ValidacionCodefacAnotacion;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ImpuestoDetalle;
import ec.com.codesoft.codefaclite.utilidades.validadores.ExpresionRegular;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextField;

/**
 *
 * @author Carlos
 */
public abstract class ComprobantesConfiguracionPanel extends ControladorCodefacInterface {

    /**
     * Creates new form ComprobantesConfiguracion
     */
    
    public ComprobantesConfiguracionPanel() {
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

        panelFacturacionElectronica = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        cmbModoFacturacion = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtNombreFirma = new javax.swing.JTextField();
        txtClaveFirma = new javax.swing.JPasswordField();
        btnFirmaElectronica = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        panelConfiguracionesGenerales = new javax.swing.JPanel();
        cmbIvaDefault = new javax.swing.JComboBox<>();
        txtDirectorioRecurso = new javax.swing.JTextField();
        txtCorreoElectronico = new javax.swing.JTextField();
        txtPasswordCorreo = new javax.swing.JPasswordField();
        jLabel20 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtFondoEscritorio = new javax.swing.JTextField();
        btnBuscarImagen = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        cmbTipoFacturacion = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        txtSmtpHost = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtSmtpPuerto = new javax.swing.JSpinner();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        lblEspacio1 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Configuraciones");
        setToolTipText("");
        getContentPane().setLayout(new java.awt.GridBagLayout());

        panelFacturacionElectronica.setBorder(javax.swing.BorderFactory.createTitledBorder("Comprobantes Electrónicos"));
        panelFacturacionElectronica.setLayout(new java.awt.GridBagLayout());

        jLabel6.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(204, 51, 0));
        jLabel6.setText("Modo Facturación: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelFacturacionElectronica.add(jLabel6, gridBagConstraints);

        cmbModoFacturacion.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        panelFacturacionElectronica.add(cmbModoFacturacion, gridBagConstraints);

        jLabel8.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel8.setText("Nombre Archivo Firma:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelFacturacionElectronica.add(jLabel8, gridBagConstraints);

        jLabel9.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel9.setText("Contraseña Firma:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelFacturacionElectronica.add(jLabel9, gridBagConstraints);

        txtNombreFirma.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 101;
        gridBagConstraints.weightx = 0.1;
        panelFacturacionElectronica.add(txtNombreFirma, gridBagConstraints);

        txtClaveFirma.setMaximumSize(new java.awt.Dimension(100, 100));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        panelFacturacionElectronica.add(txtClaveFirma, gridBagConstraints);

        btnFirmaElectronica.setText("Cargar Firma");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelFacturacionElectronica.add(btnFirmaElectronica, gridBagConstraints);

        jLabel28.setText("                              ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        panelFacturacionElectronica.add(jLabel28, gridBagConstraints);

        jLabel29.setText("                              ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        panelFacturacionElectronica.add(jLabel29, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        panelFacturacionElectronica.add(jLabel22, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(panelFacturacionElectronica, gridBagConstraints);

        panelConfiguracionesGenerales.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuraciones Generales"));
        panelConfiguracionesGenerales.setLayout(new java.awt.GridBagLayout());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        panelConfiguracionesGenerales.add(cmbIvaDefault, gridBagConstraints);

        txtDirectorioRecurso.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        panelConfiguracionesGenerales.add(txtDirectorioRecurso, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_END;
        gridBagConstraints.weightx = 0.1;
        panelConfiguracionesGenerales.add(txtCorreoElectronico, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        panelConfiguracionesGenerales.add(txtPasswordCorreo, gridBagConstraints);

        jLabel20.setText("                              ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 3;
        panelConfiguracionesGenerales.add(jLabel20, gridBagConstraints);

        jLabel11.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel11.setText("IVA Defecto:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelConfiguracionesGenerales.add(jLabel11, gridBagConstraints);

        jLabel12.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel12.setText("Directorio Recurso:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelConfiguracionesGenerales.add(jLabel12, gridBagConstraints);

        jLabel13.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel13.setText("Puerto (Smtp):");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelConfiguracionesGenerales.add(jLabel13, gridBagConstraints);

        jLabel23.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel23.setText("Contraseña Correo:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelConfiguracionesGenerales.add(jLabel23, gridBagConstraints);

        jLabel24.setText("                              ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        panelConfiguracionesGenerales.add(jLabel24, gridBagConstraints);

        jLabel10.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel10.setText("Imagen Fondo:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelConfiguracionesGenerales.add(jLabel10, gridBagConstraints);

        txtFondoEscritorio.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        panelConfiguracionesGenerales.add(txtFondoEscritorio, gridBagConstraints);

        btnBuscarImagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/find2-ico.png"))); // NOI18N
        btnBuscarImagen.setToolTipText("Busca Imagen");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelConfiguracionesGenerales.add(btnBuscarImagen, gridBagConstraints);

        jLabel19.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel19.setText("Tipo de Facturación:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelConfiguracionesGenerales.add(jLabel19, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panelConfiguracionesGenerales.add(cmbTipoFacturacion, gridBagConstraints);

        jLabel14.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel14.setText("Correo Electrónico: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(30, 5, 5, 5);
        panelConfiguracionesGenerales.add(jLabel14, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        panelConfiguracionesGenerales.add(txtSmtpHost, gridBagConstraints);

        jLabel15.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel15.setText("Host (Smtp):");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelConfiguracionesGenerales.add(jLabel15, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panelConfiguracionesGenerales.add(txtSmtpPuerto, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(panelConfiguracionesGenerales, gridBagConstraints);

        jLabel16.setText("               ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        getContentPane().add(jLabel16, gridBagConstraints);

        jLabel17.setText("              ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        getContentPane().add(jLabel17, gridBagConstraints);

        jLabel18.setText("              ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        getContentPane().add(jLabel18, gridBagConstraints);

        jLabel21.setText("              ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        getContentPane().add(jLabel21, gridBagConstraints);

        jLabel26.setText("             ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        getContentPane().add(jLabel26, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        getContentPane().add(lblEspacio1, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscarImagen;
    private javax.swing.JButton btnFirmaElectronica;
    private javax.swing.JComboBox<ImpuestoDetalle> cmbIvaDefault;
    private javax.swing.JComboBox<String> cmbModoFacturacion;
    private javax.swing.JComboBox<ComprobanteEntity.TipoEmisionEnum> cmbTipoFacturacion;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel lblEspacio1;
    private javax.swing.JPanel panelConfiguracionesGenerales;
    private javax.swing.JPanel panelFacturacionElectronica;
    private javax.swing.JPasswordField txtClaveFirma;
    private javax.swing.JTextField txtCorreoElectronico;
    private javax.swing.JTextField txtDirectorioRecurso;
    private javax.swing.JTextField txtFondoEscritorio;
    private javax.swing.JTextField txtNombreFirma;
    private javax.swing.JPasswordField txtPasswordCorreo;
    private javax.swing.JTextField txtSmtpHost;
    private javax.swing.JSpinner txtSmtpPuerto;
    // End of variables declaration//GEN-END:variables

   


    public JComboBox<String> getCmbModoFacturacion() {
        return cmbModoFacturacion;
    }

    //@ValidacionCodefacAnotacion(requerido = true, min = 0, nombre = "Contraseña de firma electrónica")
    public JPasswordField getTxtClaveFirma() {
        return txtClaveFirma;
    }
    
    public void setTxtClaveFirma(JPasswordField txtClaveFirma) {
        this.txtClaveFirma = txtClaveFirma;
    }

    @ValidacionCodefacAnotacion(requerido = true, expresionRegular = ExpresionRegular.email, nombre = "Correo Electronico")
    public JTextField getTxtCorreoElectronico() {
        return txtCorreoElectronico;
    }

    public void setTxtCorreoElectronico(JTextField txtCorreoElectronico) {
        this.txtCorreoElectronico = txtCorreoElectronico;
    }

    public JTextField getTxtDirectorioRecurso() {
        return txtDirectorioRecurso;
    }

    public void setTxtDirectorioRecurso(JTextField txtDirectorioRecurso) {
        this.txtDirectorioRecurso = txtDirectorioRecurso;
    }

    public JTextField getTxtNombreFirma() {
        return txtNombreFirma;
    }

    public void setTxtNombreFirma(JTextField txtNombreFirma) {
        this.txtNombreFirma = txtNombreFirma;
    }

    @ValidacionCodefacAnotacion(requerido = true, min = 0, nombre = "Contraseña del correo")
    public JPasswordField getTxtPasswordCorreo() {
        return txtPasswordCorreo;
    }

    public void setTxtPasswordCorreo(JPasswordField txtPasswordCorreo) {
        this.txtPasswordCorreo = txtPasswordCorreo;
    }

    public JComboBox<ImpuestoDetalle> getCmbIvaDefault() {
        return cmbIvaDefault;
    }

    public void setCmbIvaDefault(JComboBox<ImpuestoDetalle> cmbIvaDefault) {
        this.cmbIvaDefault = cmbIvaDefault;
    }
    
    public JButton getBtnFirmaElectronica() {
        return btnFirmaElectronica;
    }

    public void setBtnFirmaElectronica(JButton btnFirmaElectronica) {
        this.btnFirmaElectronica = btnFirmaElectronica;
    }

    public JButton getBtnBuscarImagen() {
        return btnBuscarImagen;
    }

    public void setBtnBuscarImagen(JButton btnBuscarImagen) {
        this.btnBuscarImagen = btnBuscarImagen;
    }

    public JTextField getTxtFondoEscritorio() {
        return txtFondoEscritorio;
    }

    public void setTxtFondoEscritorio(JTextField txtFondoEscritorio) {
        this.txtFondoEscritorio = txtFondoEscritorio;
    }

    public JComboBox<ComprobanteEntity.TipoEmisionEnum> getCmbTipoFacturacion() {
        return cmbTipoFacturacion;
    }

    public void setCmbTipoFacturacion(JComboBox<ComprobanteEntity.TipoEmisionEnum> cmbTipoFacturacion) {
        this.cmbTipoFacturacion = cmbTipoFacturacion;
    }
    
    public JPanel getPanelFacturacionElectronica() {
        return panelFacturacionElectronica;
    }

    public void setPanelFacturacionElectronica(JPanel panelFacturacionElectronica) {
        this.panelFacturacionElectronica = panelFacturacionElectronica;
    }

    public JTextField getTxtSmtpHost() {
        return txtSmtpHost;
    }

    public JSpinner getTxtSmtpPuerto() {
        return txtSmtpPuerto;
    }

    
   
    
    

}
