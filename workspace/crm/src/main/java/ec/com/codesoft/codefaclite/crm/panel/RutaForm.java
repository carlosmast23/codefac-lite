/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.crm.panel;

import ec.com.codesoft.codefaclite.controlador.aplicacion.ControladorCodefacInterface;
import ec.com.codesoft.codefaclite.controlador.vistas.core.SpinnerBinding;
import ec.com.codesoft.codefaclite.controlador.vistas.core.TableBinding;
import ec.com.codesoft.codefaclite.controlador.vistas.core.TextFieldBinding;
import ec.com.codesoft.codefaclite.controlador.vistas.core.components.ButtonBinding;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author CARLOS_CODESOFT
 */
public abstract class RutaForm extends ControladorCodefacInterface {

    /**
     * Creates new form RutaForm
     */
    public RutaForm() {
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtDiaVisita = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        txtEmpleado = new javax.swing.JTextField();
        btnBuscarEmpleado = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDatos = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        txtClienteDetalle = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        lblEspacio123 = new javax.swing.JLabel();
        btnBuscarClienteDetalle = new javax.swing.JButton();
        jToolBar1 = new javax.swing.JToolBar();
        btnAgregarDetalle = new javax.swing.JButton();
        btnEditarDetalle = new javax.swing.JButton();
        btnEliminarDetalle = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        txtOrdenDetalle = new javax.swing.JSpinner();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel1.setText("Código:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel1, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel2.setText("Vendedor:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtCodigo, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.2;
        jPanel1.add(jLabel3, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtNombre, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel4.setText("Nombre:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel4, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 25;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtDiaVisita, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel5.setText("Día Visita:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel5, gridBagConstraints);

        txtEmpleado.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtEmpleado, gridBagConstraints);

        btnBuscarEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/find2-ico.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        jPanel1.add(btnBuscarEmpleado, gridBagConstraints);

        jScrollPane1.setViewportView(tblDatos);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jScrollPane1, gridBagConstraints);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Detalle", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 2, 11))); // NOI18N
        jPanel2.setLayout(new java.awt.GridBagLayout());

        txtClienteDetalle.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 53;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtClienteDetalle, gridBagConstraints);

        jLabel6.setText("Orden:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 25, 5, 5);
        jPanel2.add(jLabel6, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 0.2;
        jPanel2.add(lblEspacio123, gridBagConstraints);

        btnBuscarClienteDetalle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/find2-ico.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel2.add(btnBuscarClienteDetalle, gridBagConstraints);

        jToolBar1.setFloatable(false);

        btnAgregarDetalle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/mas-ico.png"))); // NOI18N
        btnAgregarDetalle.setFocusable(false);
        btnAgregarDetalle.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAgregarDetalle.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnAgregarDetalle);

        btnEditarDetalle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/pequenos/edit_icon.png"))); // NOI18N
        btnEditarDetalle.setFocusable(false);
        btnEditarDetalle.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEditarDetalle.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnEditarDetalle);

        btnEliminarDetalle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/pequenos/cerrar-ico.png"))); // NOI18N
        btnEliminarDetalle.setFocusable(false);
        btnEliminarDetalle.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEliminarDetalle.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnEliminarDetalle);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        jPanel2.add(jToolBar1, gridBagConstraints);

        jLabel7.setText("Cliente:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel2.add(jLabel7, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 20;
        jPanel2.add(txtOrdenDetalle, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(25, 5, 5, 5);
        jPanel1.add(jPanel2, gridBagConstraints);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarDetalle;
    private javax.swing.JButton btnBuscarClienteDetalle;
    private javax.swing.JButton btnBuscarEmpleado;
    private javax.swing.JButton btnEditarDetalle;
    private javax.swing.JButton btnEliminarDetalle;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lblEspacio123;
    private javax.swing.JTable tblDatos;
    private javax.swing.JTextField txtClienteDetalle;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JSpinner txtDiaVisita;
    private javax.swing.JTextField txtEmpleado;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JSpinner txtOrdenDetalle;
    // End of variables declaration//GEN-END:variables

    @ButtonBinding(actionListener ="controlador.listenerAddDetalle" )
    public JButton getBtnAgregarDetalle() {
        return btnAgregarDetalle;
    }

    public void setBtnAgregarDetalle(JButton btnAgregarDetalle) {
        this.btnAgregarDetalle = btnAgregarDetalle;
    }

    @ButtonBinding(actionListener ="controlador.listenerBotonBuscarCliente" )
    public JButton getBtnBuscarClienteDetalle() {
        return btnBuscarClienteDetalle;
    }

    public void setBtnBuscarClienteDetalle(JButton btnBuscarClienteDetalle) {
        this.btnBuscarClienteDetalle = btnBuscarClienteDetalle;
    }

    @ButtonBinding(actionListener ="controlador.listenerBotonBuscarVendedor" )
    public JButton getBtnBuscarEmpleado() {
        return btnBuscarEmpleado;
    }

    public void setBtnBuscarEmpleado(JButton btnBuscarEmpleado) {
        this.btnBuscarEmpleado = btnBuscarEmpleado;
    }

    @ButtonBinding(actionListener = "controlador.listenerEditarDetalle")
    public JButton getBtnEditarDetalle() {
        return btnEditarDetalle;
    }

    public void setBtnEditarDetalle(JButton btnEditarDetalle) {
        this.btnEditarDetalle = btnEditarDetalle;
    }

    @ButtonBinding(actionListener = "controlador.listenerEliminarDetalle")
    public JButton getBtnEliminarDetalle() {
        return btnEliminarDetalle;
    }

    public void setBtnEliminarDetalle(JButton btnEliminarDetalle) {
        this.btnEliminarDetalle = btnEliminarDetalle;
    }

    @TextFieldBinding(value = "controlador.rutaDetalle.establecimiento.persona.nombresCompletos")
    public JTextField getTxtClienteDetalle() {
        return txtClienteDetalle;
    }

    public void setTxtClienteDetalle(JTextField txtClienteDetalle) {
        this.txtClienteDetalle = txtClienteDetalle;
    }

    @TextFieldBinding(value = "controlador.ruta.codigo")
    public JTextField getTxtCodigo() {
        return txtCodigo;
    }
    
    public void setTxtCodigo(JTextField txtCodigo) {
        this.txtCodigo = txtCodigo;
    }


    @SpinnerBinding(value = "controlador.ruta.diaVisita")    
    public JSpinner getTxtDiaVisita() {
        return txtDiaVisita;
    }

    public void setTxtDiaVisita(JSpinner txtDiaVisita) {
        this.txtDiaVisita = txtDiaVisita;
    }

    @TextFieldBinding(value = "controlador.ruta.vendedor.nombresCompletos")
    public JTextField getTxtEmpleado() {
        return txtEmpleado;
    }

    public void setTxtEmpleado(JTextField txtEmpleado) {
        this.txtEmpleado = txtEmpleado;
    }

    @TextFieldBinding(value = "controlador.ruta.nombre")
    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public void setTxtNombre(JTextField txtNombre) {
        this.txtNombre = txtNombre;
    }

    @SpinnerBinding(value = "controlador.rutaDetalle.orden")
    public JSpinner getTxtOrdenDetalle() {
        return txtOrdenDetalle;
    }

    public void setTxtOrdenDetalle(JSpinner txtOrdenDetalle) {
        this.txtOrdenDetalle = txtOrdenDetalle;
    }

    @TableBinding(source = "controlador.ruta.detallesOrdenadoPorOrden",tableAddDataInterface = "tableBindingAddData",selectValue = "controlador.rutaDetalleSeleccionado")
    public JTable getTblDatos() {
        return tblDatos;
    }

    public void setTblDatos(JTable tblDatos) {
        this.tblDatos = tblDatos;
    }

    
    
}
