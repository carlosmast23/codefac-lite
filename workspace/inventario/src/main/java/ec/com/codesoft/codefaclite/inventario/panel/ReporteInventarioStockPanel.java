/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.inventario.panel;

import ec.com.codesoft.codefaclite.controlador.aplicacion.ControladorCodefacInterface;
import ec.com.codesoft.codefaclite.controlador.vistas.core.components.ButtonBinding;
import ec.com.codesoft.codefaclite.controlador.vistas.core.components.ComboBoxBinding;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Bodega;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author DesarrolloSoftware
 */
public abstract class ReporteInventarioStockPanel extends ControladorCodefacInterface {

    /**
     * Creates new form ReporteInventarioPanel
     */
    public ReporteInventarioStockPanel() {
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

        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cmbTipoReporte = new javax.swing.JComboBox<>();
        txtNombre = new javax.swing.JTextField();
        checkTodos = new javax.swing.JCheckBox();
        btnBuscarGenerica = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblInformacionReporte = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        cmbBodega = new javax.swing.JComboBox<>();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Inventario Stock");
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jLabel3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel3.setText("Reporte:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        getContentPane().add(jLabel3, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel4.setText("Nombre:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        getContentPane().add(jLabel4, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(cmbTipoReporte, gridBagConstraints);

        txtNombre.setText("                                                                                       ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(txtNombre, gridBagConstraints);

        checkTodos.setText("Todos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        getContentPane().add(checkTodos, gridBagConstraints);

        btnBuscarGenerica.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/pequenos/Zoom.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        getContentPane().add(btnBuscarGenerica, gridBagConstraints);

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/pequenos/icon1.png"))); // NOI18N
        btnBuscar.setText("Buscar");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 8;
        getContentPane().add(btnBuscar, gridBagConstraints);

        tblInformacionReporte.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblInformacionReporte);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        getContentPane().add(jScrollPane1, gridBagConstraints);

        jLabel1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel1.setText("Bodega:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        getContentPane().add(jLabel1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(cmbBodega, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    @ButtonBinding(actionListener ="controlador.listenerBotonBuscarFiltroPorCategoria" )
    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }
    
    @ButtonBinding(actionListener = "controlador.listenerBotonBuscar")
    public JButton getBtnBuscarGenerica() {
        return btnBuscarGenerica;
    }

    public void setBtnBuscarGenerica(JButton btnBuscarGenerica) {
        this.btnBuscarGenerica = btnBuscarGenerica;
    }

    public JCheckBox getCheckTodos() {
        return checkTodos;
    }

    public void setCheckTodos(JCheckBox checkTodos) {
        this.checkTodos = checkTodos;
    }

    @ComboBoxBinding(source = "controlador.bodegasList", valueSelect = "controlador.bodega")
    public JComboBox<Bodega> getCmbBodega() {
        return cmbBodega;
    }
    
    
    public void setCmbBodega(JComboBox<Bodega> cmbBodega) {
        this.cmbBodega = cmbBodega;
    }
    
    //@ComboBoxBinding(source = "controlador.filtroPorCategoria", valueSelect = "controlador.elementoSeleccionadoFiltroPorCategoria")
    public JComboBox<String> getCmbTipoReporte() {
        return cmbTipoReporte;
    }

    public void setCmbTipoReporte(JComboBox<String> cmbTipoReporte) {
        this.cmbTipoReporte = cmbTipoReporte;
    }

    public JTable getTblInformacionReporte() {
        return tblInformacionReporte;
    }

    public void setTblInformacionReporte(JTable tblInformacionReporte) {
        this.tblInformacionReporte = tblInformacionReporte;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public void setTxtNombre(JTextField txtNombre) {
        this.txtNombre = txtNombre;
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnBuscarGenerica;
    private javax.swing.JCheckBox checkTodos;
    private javax.swing.JComboBox<Bodega> cmbBodega;
    private javax.swing.JComboBox<String> cmbTipoReporte;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblInformacionReporte;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
