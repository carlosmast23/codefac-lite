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
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.servidor.entity.ImpuestoDetalle;
import java.util.Map;
import javax.swing.JComboBox;
import javax.swing.JTextField;

/**
 *
 * @author PC
 */
public abstract class ProductoForm extends ControladorCodefacInterface{

    public JComboBox<String> getComboTipoProducto() {
        return comboTipoProducto;
    }

    public void setComboTipoProducto(JComboBox<String> jComboTipoProducto) {
        this.comboTipoProducto = jComboTipoProducto;
    }
    
    @LimpiarAnotacion
    @AyudaCodefacAnotacion(recurso = "ayudaHtml.html")
    @ValidacionCodefacAnotacion(requerido=true ,expresionRegular = "^[A-Za-z0-9.\\_\\-\\\\s]*$",nombre = "Codigo Auxiliar")
    public JTextField getTextCodigoAuxiliar() {
        return textCodigoAuxiliar;
    }

    public void setTextCodigoAuxiliar(JTextField jTextCodigoAuxiliar) {
        this.textCodigoAuxiliar = jTextCodigoAuxiliar;
    }
    
    @LimpiarAnotacion
    @AyudaCodefacAnotacion(recurso = "ayudaHtml.html")
    @ValidacionCodefacAnotacion(requerido=true ,expresionRegular = "^[A-Za-z0-9.\\_\\-\\\\s]*$",nombre = "Codigo Principal")
    public JTextField getTextCodigoPrincipal() {
        return textCodigoPrincipal;
    }

    public void setTextCodigoPrincipal(JTextField jTextCodigoPrincipal) {
        this.textCodigoPrincipal = jTextCodigoPrincipal;
    }
    
    @LimpiarAnotacion
    //@AyudaCodefacAnotacion(recurso = "ayudaHtml.html")
    @ValidacionCodefacAnotacion(requerido=true, expresionRegular = "^[a-zA-Z\\s0-9.\\_\\-]*$",nombre = "Nombre")
    public JTextField getTextNombre() {
        return textNombre;
    }

    public void setTextNombre(JTextField jTextNombre) {
        this.textNombre = jTextNombre;
    }
    
    @LimpiarAnotacion
    @AyudaCodefacAnotacion(recurso = "ayudaHtml.html")
    @ValidacionCodefacAnotacion(requerido=true, expresionRegular = "^[0-9]+([.][0-9]+)?$")
    public JTextField getTextValorUnitario() {
        return textValorUnitario;
    }

    public void setTextValorUnitario(JTextField jTextValorUnitario) {
        this.textValorUnitario = jTextValorUnitario;
    }

    public JComboBox<ImpuestoDetalle> getComboIce() {
        return comboIce;
    }

    public void setComboIce(JComboBox<ImpuestoDetalle> comboIce) {
        this.comboIce = comboIce;
    }

    public JComboBox<ImpuestoDetalle> getComboIrbpnr() {
        return comboIrbpnr;
    }

    public void setComboIrbpnr(JComboBox<ImpuestoDetalle> comboIrbpnr) {
        this.comboIrbpnr = comboIrbpnr;
    }

    public JComboBox<ImpuestoDetalle> getComboIva() {
        return comboIva;
    }

    public void setComboIva(JComboBox<ImpuestoDetalle> comboIva) {
        this.comboIva = comboIva;
    }
    
    

    /**
     * Creates new form ProductoForm
     */
    public ProductoForm() {
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
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        textCodigoPrincipal = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        textCodigoAuxiliar = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        comboTipoProducto = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        textNombre = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        textValorUnitario = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        comboIva = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        comboIce = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        comboIrbpnr = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Producto");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jLabel1.setText("DETALLE DEL PRODUCTO / SERVICIO");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(jLabel1, gridBagConstraints);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Características Generales"));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel2.setText("Código Principal: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LAST_LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        jPanel1.add(jLabel2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        jPanel1.add(textCodigoPrincipal, gridBagConstraints);

        jLabel3.setText("Código Auxiliar: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        jPanel1.add(jLabel3, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        jPanel1.add(textCodigoAuxiliar, gridBagConstraints);

        jLabel4.setText("Tipo de Producto: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        jPanel1.add(jLabel4, gridBagConstraints);

        comboTipoProducto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "BIEN", "SERVICIO" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        jPanel1.add(comboTipoProducto, gridBagConstraints);

        jLabel5.setText("Nombre: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        jPanel1.add(jLabel5, gridBagConstraints);

        textNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textNombreActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        jPanel1.add(textNombre, gridBagConstraints);

        jLabel6.setText("Valor Unitario: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        jPanel1.add(jLabel6, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        jPanel1.add(textValorUnitario, gridBagConstraints);

        jLabel9.setText("                                           ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        jPanel1.add(jLabel9, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Impuestos Aplicables"));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel10.setText("IVA:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        jPanel2.add(jLabel10, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 339;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel2.add(comboIva, gridBagConstraints);

        jLabel11.setText("ICE:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        jPanel2.add(jLabel11, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 339;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel2.add(comboIce, gridBagConstraints);

        jLabel12.setText("IRBPNR:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        jPanel2.add(jLabel12, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 339;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel2.add(comboIrbpnr, gridBagConstraints);

        jLabel14.setText("                              ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(13, 158, 0, 0);
        jPanel2.add(jLabel14, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jPanel2, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void textNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textNombreActionPerformed
    }//GEN-LAST:event_textNombreActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<ImpuestoDetalle> comboIce;
    private javax.swing.JComboBox<ImpuestoDetalle> comboIrbpnr;
    private javax.swing.JComboBox<ImpuestoDetalle> comboIva;
    private javax.swing.JComboBox<String> comboTipoProducto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField textCodigoAuxiliar;
    private javax.swing.JTextField textCodigoPrincipal;
    private javax.swing.JTextField textNombre;
    private javax.swing.JTextField textValorUnitario;
    // End of variables declaration//GEN-END:variables

    
}
