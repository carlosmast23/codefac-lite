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
import java.util.Map;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Robert
 */
public abstract class ConfigurarEmisorForm extends ControladorCodefacInterface{
    /**
     * Creates new form ConfigurarEmisor
     */
    public ConfigurarEmisorForm() {
        initComponents();
    }
    
    public JCheckBox getjCheckBLlevaContabilidad() {
        return jCheckBLlevaContabilidad;
    }

    public void setjCheckBLlevaContabilidad(JCheckBox jCheckBLlevaContabilidad) {
        this.jCheckBLlevaContabilidad = jCheckBLlevaContabilidad;
    }

    public JComboBox<String> getjComboTipoAmbiente() {
        return jComboTipoAmbiente;
    }

    public void setjComboTipoAmbiente(JComboBox<String> jComboTipoAmbiente) {
        this.jComboTipoAmbiente = jComboTipoAmbiente;
    }

    public JComboBox<String> getjComboToken() {
        return jComboToken;
    }

    public void setjComboToken(JComboBox<String> jComboToken) {
        this.jComboToken = jComboToken;
    }

    public JPanel getjPanelConfiguracionEmisor() {
        return jPanelConfiguracionEmisor;
    }

    public void setjPanelConfiguracionEmisor(JPanel jPanelConfiguracionEmisor) {
        this.jPanelConfiguracionEmisor = jPanelConfiguracionEmisor;
    }
    
    @LimpiarAnotacion
    public JSpinner getjSpinnerTiempoEspera() {
        return jSpinnerTiempoEspera;
    }

    public void setjSpinnerTiempoEspera(JSpinner jSpinnerTiempoEspera) {
        this.jSpinnerTiempoEspera = jSpinnerTiempoEspera;
    }
    
    @LimpiarAnotacion
    @AyudaCodefacAnotacion(recurso = "ayudaHtml.html")
    @ValidacionCodefacAnotacion(requerido=true , min=5 ,max = 1024,expresionRegular = "^[A-Za-z0-9\\s]*$",nombre = "Direccion Establecimento")
    public JTextArea getjTextADirEstablecimiento() {
        return jTextADirEstablecimiento;
    }

    public void setjTextADirEstablecimiento(JTextArea jTextADirEstablecimiento) {
        this.jTextADirEstablecimiento = jTextADirEstablecimiento;
    }
    
    @LimpiarAnotacion
    @AyudaCodefacAnotacion(recurso = "ayudaHtml.html")
    @ValidacionCodefacAnotacion(requerido=true , min=5 ,max = 1024,expresionRegular = "^[A-Za-z0-9\\s]*$",nombre = "Direccion Matriz")
    public JTextArea getjTextADirMatriz() {
        return jTextADirMatriz;
    }

    public void setjTextADirMatriz(JTextArea jTextADirMatriz) {
        this.jTextADirMatriz = jTextADirMatriz;
    }
    
    @LimpiarAnotacion
    @AyudaCodefacAnotacion(recurso = "ayudaHtml.html")
    @ValidacionCodefacAnotacion(requerido=true , min=5 ,max = 1024,expresionRegular = "^[A-Za-z0-9\\s]*$",nombre = "Codigo Estableciento")
    public JTextField getjTextCodEstablecimiento() {
        return jTextCodEstablecimiento;
    }

    public void setjTextCodEstablecimiento(JTextField jTextCodEstablecimiento) {
        this.jTextCodEstablecimiento = jTextCodEstablecimiento;
    }
    
    @LimpiarAnotacion
    @AyudaCodefacAnotacion(recurso = "ayudaHtml.html")
    @ValidacionCodefacAnotacion(requerido=true , min=5 ,max = 1024,expresionRegular = "^[A-Za-z0-9\\s]*$",nombre = "Codigo del punto de Emision")
    public JTextField getjTextCodPuntoEmision() {
        return jTextCodPuntoEmision;
    }

    public void setjTextCodPuntoEmision(JTextField jTextCodPuntoEmision) {
        this.jTextCodPuntoEmision = jTextCodPuntoEmision;
    }
    
    @LimpiarAnotacion
    @AyudaCodefacAnotacion(recurso = "ayudaHtml.html")
    @ValidacionCodefacAnotacion(requerido=true , min=5 ,max = 1024,expresionRegular = "^[A-Za-z0-9\\s]*$",nombre = "Nombre Comercial")
    public JTextField getjTextNombreComercial() {
        return jTextNombreComercial;
    }

    public void setjTextNombreComercial(JTextField jTextNombreComercial) {
        this.jTextNombreComercial = jTextNombreComercial;
    }
    
    @LimpiarAnotacion
    @AyudaCodefacAnotacion(recurso = "ayudaHtml.html")
    @ValidacionCodefacAnotacion(requerido=true , min=5 ,max = 1024,expresionRegular = "^[A-Za-z0-9\\s]*$",nombre = "Apellidos y Nombres | Razon Social")
    public JTextField getjTextNombreSocial() {
        return jTextNombreSocial;
    }

    public void setjTextNombreSocial(JTextField jTextNombreSocial) {
        this.jTextNombreSocial = jTextNombreSocial;
    }
    
    @LimpiarAnotacion
    @AyudaCodefacAnotacion(recurso = "ayudaHtml.html")
    @ValidacionCodefacAnotacion(requerido=true , min=5 ,max = 1024,expresionRegular = "^[A-Za-z0-9\\s]*$",nombre = "Contribuyente especial Nro. Resolucion")
    public JTextField getjTextNumContribuyente() {
        return jTextNumContribuyente;
    }

    public void setjTextNumContribuyente(JTextField jTextNumContribuyente) {
        this.jTextNumContribuyente = jTextNumContribuyente;
    }
    
    @LimpiarAnotacion
    @AyudaCodefacAnotacion(recurso = "ayudaHtml.html")
    @ValidacionCodefacAnotacion(requerido=true , min=5 ,max = 1024,expresionRegular = "^[A-Za-z0-9\\s]*$",nombre = "RUC")
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

        jPanelConfiguracionEmisor = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextRuc = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextNombreSocial = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextNombreComercial = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextADirMatriz = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextADirEstablecimiento = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        jTextCodEstablecimiento = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextCodPuntoEmision = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTextNumContribuyente = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jCheckBLlevaContabilidad = new javax.swing.JCheckBox();
        jLabel10 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jSpinnerTiempoEspera = new javax.swing.JSpinner();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jComboTipoAmbiente = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        jComboToken = new javax.swing.JComboBox<>();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Emisor");
        setToolTipText("");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/edit-icon.png"))); // NOI18N
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanelConfiguracionEmisor.setBorder(javax.swing.BorderFactory.createTitledBorder("Características Generales"));
        jPanelConfiguracionEmisor.setName(""); // NOI18N
        jPanelConfiguracionEmisor.setLayout(new java.awt.GridBagLayout());

        jLabel1.setText("RUC:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        jPanelConfiguracionEmisor.add(jLabel1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        jPanelConfiguracionEmisor.add(jTextRuc, gridBagConstraints);

        jLabel2.setText("Apellidos y Nombres | Razón Social:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        jPanelConfiguracionEmisor.add(jLabel2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        jPanelConfiguracionEmisor.add(jTextNombreSocial, gridBagConstraints);

        jLabel3.setText("Nombre Comercial:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        jPanelConfiguracionEmisor.add(jLabel3, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        jPanelConfiguracionEmisor.add(jTextNombreComercial, gridBagConstraints);

        jLabel4.setText("Dirección Matríz:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        jPanelConfiguracionEmisor.add(jLabel4, gridBagConstraints);

        jTextADirMatriz.setColumns(20);
        jTextADirMatriz.setRows(5);
        jScrollPane1.setViewportView(jTextADirMatriz);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 8, 0);
        jPanelConfiguracionEmisor.add(jScrollPane1, gridBagConstraints);

        jLabel5.setText("Dirección Establecimiento:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        jPanelConfiguracionEmisor.add(jLabel5, gridBagConstraints);

        jTextADirEstablecimiento.setColumns(20);
        jTextADirEstablecimiento.setRows(5);
        jScrollPane2.setViewportView(jTextADirEstablecimiento);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 8, 0);
        jPanelConfiguracionEmisor.add(jScrollPane2, gridBagConstraints);

        jLabel6.setText("Código Establecimiento:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        jPanelConfiguracionEmisor.add(jLabel6, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        jPanelConfiguracionEmisor.add(jTextCodEstablecimiento, gridBagConstraints);

        jLabel7.setText("Código del punto de Emisión:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        jPanelConfiguracionEmisor.add(jLabel7, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        jPanelConfiguracionEmisor.add(jTextCodPuntoEmision, gridBagConstraints);

        jLabel8.setText("Contribuyente Especial | Nro. Resolución:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        jPanelConfiguracionEmisor.add(jLabel8, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        jPanelConfiguracionEmisor.add(jTextNumContribuyente, gridBagConstraints);

        jLabel9.setText("Obligado a llevar contabilidad:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        jPanelConfiguracionEmisor.add(jLabel9, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jPanelConfiguracionEmisor.add(jCheckBLlevaContabilidad, gridBagConstraints);

        jLabel10.setText("Logo(Imagen):");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        jPanelConfiguracionEmisor.add(jLabel10, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        jPanelConfiguracionEmisor.add(jTextField7, gridBagConstraints);

        jLabel11.setText("Tiempo máximo de espera para respuesta de Autorización:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        jPanelConfiguracionEmisor.add(jLabel11, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        jPanelConfiguracionEmisor.add(jSpinnerTiempoEspera, gridBagConstraints);

        jLabel12.setText("Segundos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        jPanelConfiguracionEmisor.add(jLabel12, gridBagConstraints);

        jLabel13.setText("Tipo de ambiente:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        jPanelConfiguracionEmisor.add(jLabel13, gridBagConstraints);

        jComboTipoAmbiente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "PRODUCCIÓN", "PRUEBAS" }));
        jComboTipoAmbiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboTipoAmbienteActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        jPanelConfiguracionEmisor.add(jComboTipoAmbiente, gridBagConstraints);

        jLabel14.setText("Token para firmar:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        jPanelConfiguracionEmisor.add(jLabel14, gridBagConstraints);

        jComboToken.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ANF - Certificado Exportado", "ANF - Plug & Sign", "BCE - iKey2032", "BCE - Aladdin eToken Pro", "SD - ePass3003 auto", "SD - BioPass3000", "KEY4 - Consejo Judicatura" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        jPanelConfiguracionEmisor.add(jComboToken, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(jPanelConfiguracionEmisor, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboTipoAmbienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboTipoAmbienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboTipoAmbienteActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox jCheckBLlevaContabilidad;
    private javax.swing.JComboBox<String> jComboTipoAmbiente;
    private javax.swing.JComboBox<String> jComboToken;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanelConfiguracionEmisor;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSpinner jSpinnerTiempoEspera;
    private javax.swing.JTextArea jTextADirEstablecimiento;
    private javax.swing.JTextArea jTextADirMatriz;
    private javax.swing.JTextField jTextCodEstablecimiento;
    private javax.swing.JTextField jTextCodPuntoEmision;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextNombreComercial;
    private javax.swing.JTextField jTextNombreSocial;
    private javax.swing.JTextField jTextNumContribuyente;
    private javax.swing.JTextField jTextRuc;
    // End of variables declaration//GEN-END:variables
}
