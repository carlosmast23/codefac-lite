/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.configuraciones.panel;

import ec.com.codesoft.codefaclite.controlador.aplicacion.ControladorCodefacInterface;
import ec.com.codesoft.codefaclite.servidor.entity.ImpuestoDetalle;
import javax.swing.JComboBox;
import javax.swing.JPasswordField;
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

        panelSecuenciales = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtFacturaSecuencial = new javax.swing.JTextField();
        txtNotaCreditoSecuencial = new javax.swing.JTextField();
        txtNotaDebitoSecuencial = new javax.swing.JTextField();
        txtGuiaRemisionSecuencial = new javax.swing.JTextField();
        txtRetencionesSecuencial = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        panelFacturacionElectronica = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        cmbModoFacturacion = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtNombreFirma = new javax.swing.JTextField();
        txtClaveFirma = new javax.swing.JPasswordField();
        txtClaveFirmaRepetir = new javax.swing.JPasswordField();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        panelConfiguracionesGenerales = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        cmbIvaDefault = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        txtDirectorioRecurso = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtCorreoElectronico = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtPasswordCorreo = new javax.swing.JPasswordField();
        lblEstablecimiento = new javax.swing.JLabel();
        txtEstablecimiento = new javax.swing.JTextField();
        lblPuntoEmision = new javax.swing.JLabel();
        txtPuntoEmision = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        panelSecuenciales.setBorder(javax.swing.BorderFactory.createTitledBorder("Secuenciales comprobantes"));
        panelSecuenciales.setLayout(new java.awt.GridBagLayout());

        jLabel1.setText("Factura:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        panelSecuenciales.add(jLabel1, gridBagConstraints);

        jLabel2.setText("Nota Credito:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        panelSecuenciales.add(jLabel2, gridBagConstraints);

        jLabel3.setText("Nota de Debito:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        panelSecuenciales.add(jLabel3, gridBagConstraints);

        jLabel4.setText("Guia Remision:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        panelSecuenciales.add(jLabel4, gridBagConstraints);

        jLabel5.setText("Retenciones:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        panelSecuenciales.add(jLabel5, gridBagConstraints);

        txtFacturaSecuencial.setText("                                    ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        panelSecuenciales.add(txtFacturaSecuencial, gridBagConstraints);

        txtNotaCreditoSecuencial.setText("                                    ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        panelSecuenciales.add(txtNotaCreditoSecuencial, gridBagConstraints);

        txtNotaDebitoSecuencial.setText("                                    ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        panelSecuenciales.add(txtNotaDebitoSecuencial, gridBagConstraints);

        txtGuiaRemisionSecuencial.setText("                                    ");
        txtGuiaRemisionSecuencial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGuiaRemisionSecuencialActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        panelSecuenciales.add(txtGuiaRemisionSecuencial, gridBagConstraints);

        txtRetencionesSecuencial.setText("                                    ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        panelSecuenciales.add(txtRetencionesSecuencial, gridBagConstraints);

        jLabel14.setText("               ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        panelSecuenciales.add(jLabel14, gridBagConstraints);

        jLabel15.setText("               ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        panelSecuenciales.add(jLabel15, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(panelSecuenciales, gridBagConstraints);

        panelFacturacionElectronica.setBorder(javax.swing.BorderFactory.createTitledBorder("Comprobantes Electronicas"));
        panelFacturacionElectronica.setLayout(new java.awt.GridBagLayout());

        jLabel6.setText("Modo facturacion: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        panelFacturacionElectronica.add(jLabel6, gridBagConstraints);

        cmbModoFacturacion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Prueba", "Producción" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 45;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        panelFacturacionElectronica.add(cmbModoFacturacion, gridBagConstraints);

        jLabel8.setText("Nombre Archivo Firma:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        panelFacturacionElectronica.add(jLabel8, gridBagConstraints);

        jLabel9.setText("Clave Fima:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        panelFacturacionElectronica.add(jLabel9, gridBagConstraints);

        jLabel10.setText("Repetir Clave Firma:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        panelFacturacionElectronica.add(jLabel10, gridBagConstraints);

        txtNombreFirma.setText("jTextField1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 101;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        panelFacturacionElectronica.add(txtNombreFirma, gridBagConstraints);

        txtClaveFirma.setText("jPasswordField1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 105;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        panelFacturacionElectronica.add(txtClaveFirma, gridBagConstraints);

        txtClaveFirmaRepetir.setText("jPasswordField1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 105;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        panelFacturacionElectronica.add(txtClaveFirmaRepetir, gridBagConstraints);

        jLabel18.setText("               ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        panelFacturacionElectronica.add(jLabel18, gridBagConstraints);

        jLabel19.setText("               ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        panelFacturacionElectronica.add(jLabel19, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(panelFacturacionElectronica, gridBagConstraints);

        panelConfiguracionesGenerales.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuraciones generales"));
        panelConfiguracionesGenerales.setLayout(new java.awt.GridBagLayout());

        jLabel7.setText("Iva defecto:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        panelConfiguracionesGenerales.add(jLabel7, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        panelConfiguracionesGenerales.add(cmbIvaDefault, gridBagConstraints);

        jLabel11.setText("Directorio Recurso:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        panelConfiguracionesGenerales.add(jLabel11, gridBagConstraints);

        txtDirectorioRecurso.setText("jTextField1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        panelConfiguracionesGenerales.add(txtDirectorioRecurso, gridBagConstraints);

        jLabel12.setText("Correo Electronico:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        panelConfiguracionesGenerales.add(jLabel12, gridBagConstraints);

        txtCorreoElectronico.setText("jTextField1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        panelConfiguracionesGenerales.add(txtCorreoElectronico, gridBagConstraints);

        jLabel13.setText("Password Correo:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        panelConfiguracionesGenerales.add(jLabel13, gridBagConstraints);

        txtPasswordCorreo.setText("jPasswordField1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        panelConfiguracionesGenerales.add(txtPasswordCorreo, gridBagConstraints);

        lblEstablecimiento.setText("Establecimiento:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        panelConfiguracionesGenerales.add(lblEstablecimiento, gridBagConstraints);

        txtEstablecimiento.setText("                                    ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        panelConfiguracionesGenerales.add(txtEstablecimiento, gridBagConstraints);

        lblPuntoEmision.setText("Punto Emision:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        panelConfiguracionesGenerales.add(lblPuntoEmision, gridBagConstraints);

        txtPuntoEmision.setText("                                    ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        panelConfiguracionesGenerales.add(txtPuntoEmision, gridBagConstraints);

        jLabel17.setText("               ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        panelConfiguracionesGenerales.add(jLabel17, gridBagConstraints);

        jLabel20.setText("               ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        panelConfiguracionesGenerales.add(jLabel20, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(panelConfiguracionesGenerales, gridBagConstraints);

        jLabel16.setText("               ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        getContentPane().add(jLabel16, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtGuiaRemisionSecuencialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGuiaRemisionSecuencialActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGuiaRemisionSecuencialActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<ImpuestoDetalle> cmbIvaDefault;
    private javax.swing.JComboBox<String> cmbModoFacturacion;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel lblEstablecimiento;
    private javax.swing.JLabel lblPuntoEmision;
    private javax.swing.JPanel panelConfiguracionesGenerales;
    private javax.swing.JPanel panelFacturacionElectronica;
    private javax.swing.JPanel panelSecuenciales;
    private javax.swing.JPasswordField txtClaveFirma;
    private javax.swing.JPasswordField txtClaveFirmaRepetir;
    private javax.swing.JTextField txtCorreoElectronico;
    private javax.swing.JTextField txtDirectorioRecurso;
    private javax.swing.JTextField txtEstablecimiento;
    private javax.swing.JTextField txtFacturaSecuencial;
    private javax.swing.JTextField txtGuiaRemisionSecuencial;
    private javax.swing.JTextField txtNombreFirma;
    private javax.swing.JTextField txtNotaCreditoSecuencial;
    private javax.swing.JTextField txtNotaDebitoSecuencial;
    private javax.swing.JPasswordField txtPasswordCorreo;
    private javax.swing.JTextField txtPuntoEmision;
    private javax.swing.JTextField txtRetencionesSecuencial;
    // End of variables declaration//GEN-END:variables

    public JTextField getTxtFacturaSecuencial() {
        return txtFacturaSecuencial;
    }

    public JTextField getTxtGuiaRemisionSecuencial() {
        return txtGuiaRemisionSecuencial;
    }

    public JTextField getTxtNotaCreditoSecuencial() {
        return txtNotaCreditoSecuencial;
    }

    public JTextField getTxtNotaDebitoSecuencial() {
        return txtNotaDebitoSecuencial;
    }

    public JTextField getTxtRetencionesSecuencial() {
        return txtRetencionesSecuencial;
    }



    public JComboBox<String> getCmbModoFacturacion() {
        return cmbModoFacturacion;
    }


    public JPasswordField getTxtClaveFirma() {
        return txtClaveFirma;
    }


    public JPasswordField getTxtClaveFirmaRepetir() {
        return txtClaveFirmaRepetir;
    }

    public void setTxtClaveFirma(JPasswordField txtClaveFirma) {
        this.txtClaveFirma = txtClaveFirma;
    }

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

    public JTextField getTxtEstablecimiento() {
        return txtEstablecimiento;
    }

    public void setTxtEstablecimiento(JTextField txtEstablecimiento) {
        this.txtEstablecimiento = txtEstablecimiento;
    }

    public JTextField getTxtPuntoEmision() {
        return txtPuntoEmision;
    }

    public void setTxtPuntoEmision(JTextField txtPuntoEmision) {
        this.txtPuntoEmision = txtPuntoEmision;
    }

    
    

    
}
