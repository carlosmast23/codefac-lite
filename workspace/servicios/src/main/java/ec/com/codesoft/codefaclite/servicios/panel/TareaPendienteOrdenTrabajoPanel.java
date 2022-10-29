/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servicios.panel;

import ec.com.codesoft.codefaclite.controlador.aplicacion.ControladorCodefacInterface;
import ec.com.codesoft.codefaclite.controlador.vistas.core.TableBinding;
import ec.com.codesoft.codefaclite.controlador.vistas.core.TextFieldBinding;
import ec.com.codesoft.codefaclite.controlador.vistas.core.components.ButtonBinding;
import ec.com.codesoft.codefaclite.controlador.vistas.core.components.ComboBoxBinding;
import ec.com.codesoft.codefaclite.corecodefaclite.util.LimpiarAnotacion;
import ec.com.codesoft.codefaclite.corecodefaclite.validation.ValidacionCodefacAnotacion;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoObjetoMantenimientoEnum;
import ec.com.codesoft.codefaclite.utilidades.validadores.ExpresionRegular;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Carlos
 */
public abstract class TareaPendienteOrdenTrabajoPanel extends ControladorCodefacInterface {

    /**
     * Creates new form CategoriaPanel
     */
    public TareaPendienteOrdenTrabajoPanel() {
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
        btnFiltrar = new javax.swing.JButton();
        cmbTipo = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDatos = new javax.swing.JTable();
        cmbSeleccionarTodo = new javax.swing.JCheckBox();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Objecto de Mantenimiento");
        setToolTipText("");
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel1.setText("Estado:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        getContentPane().add(jLabel1, gridBagConstraints);

        btnFiltrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/find2-ico.png"))); // NOI18N
        btnFiltrar.setText("Filtrar");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(btnFiltrar, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(cmbTipo, gridBagConstraints);

        jScrollPane1.setViewportView(tblDatos);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jScrollPane1, gridBagConstraints);

        cmbSeleccionarTodo.setFont(new java.awt.Font("Arial", 2, 11)); // NOI18N
        cmbSeleccionarTodo.setText("Seleccionar Todo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(cmbSeleccionarTodo, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFiltrar;
    private javax.swing.JCheckBox cmbSeleccionarTodo;
    private javax.swing.JComboBox<TipoObjetoMantenimientoEnum> cmbTipo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblDatos;
    // End of variables declaration//GEN-END:variables

    //@LimpiarAnotacion
    //@ValidacionCodefacAnotacion(requerido = true, expresionRegular = ExpresionRegular.textoSimple, nombre = "Descripcion", expresionRegularMensaje = "No se permiten caracteres especiales")
    /*@TexT(value = "controlador.marcaProducto.descripcion")
    public JTextArea getTxtDescripcion() {
        return txtDescripcion;
    }

    public void setTxtDescripcion(JTextArea txtDescripcion) {
        this.txtDescripcion = txtDescripcion;
    }*/
    
    


    //@ComboBoxBinding(source ="controlador.tipoList",valueSelect = "controlador.objetoMantenimiento.tipoEnum" )
    public JComboBox<TipoObjetoMantenimientoEnum> getCmbTipo() {
        return cmbTipo;
    }

    public void setCmbTipo(JComboBox<TipoObjetoMantenimientoEnum> cmbTipo) {
        this.cmbTipo = cmbTipo;
    }

    @TableBinding(
            source = "controlador.actividadList",
            tableAddDataInterface = "tableBindingAddData",
            selectValue = "controlador.ordenDetalleSeleccionada",
            listSelected = "controlador.actividadListSeleccionada",
            componenteCheckSelect = "cmbSeleccionarTodo",
            modeCheck = true,
            controlador = "controlador.tableBindingControlador")    
    public JTable getTblDatos() {
        return tblDatos;
    }

    public void setTblDatos(JTable tblDatos) {
        this.tblDatos = tblDatos;
    }

    public JCheckBox getCmbSeleccionarTodo() {
        return cmbSeleccionarTodo;
    }

    public void setCmbSeleccionarTodo(JCheckBox cmbSeleccionarTodo) {
        this.cmbSeleccionarTodo = cmbSeleccionarTodo;
    }

    @ButtonBinding(actionListener = "controlador.listenerFiltrar")
    public JButton getBtnFiltrar() {
        return btnFiltrar;
    }

    public void setBtnFiltrar(JButton btnFiltrar) {
        this.btnFiltrar = btnFiltrar;
    }

    
    
    
    

}
