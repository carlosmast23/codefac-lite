/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.inventario.panel;

import ec.com.codesoft.codefaclite.controlador.aplicacion.ControladorCodefacInterface;
import javax.swing.JButton;
import javax.swing.JTable;

/**
 *
 * @author Carlos
 */
public abstract class ImprimirCodigoBarrasPanel extends ControladorCodefacInterface {

    /**
     * Creates new form ImprimirCodigoBarrasPanel
     */
    public ImprimirCodigoBarrasPanel() {
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDatos = new javax.swing.JTable();
        jToolBar1 = new javax.swing.JToolBar();
        btnAgregarTodos = new javax.swing.JButton();
        btnAgregarProducto = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Imprimir Códigos de Barras");

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jScrollPane1.setViewportView(tblDatos);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jScrollPane1, gridBagConstraints);

        jToolBar1.setBorder(null);

        btnAgregarTodos.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        btnAgregarTodos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/32Pixeles/documentos_icono.png"))); // NOI18N
        btnAgregarTodos.setText("  Agregar Todos  ");
        btnAgregarTodos.setFocusable(false);
        btnAgregarTodos.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAgregarTodos.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnAgregarTodos);

        btnAgregarProducto.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        btnAgregarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/32Pixeles/Agregar.png"))); // NOI18N
        btnAgregarProducto.setText("  Agregar Producto");
        btnAgregarProducto.setFocusable(false);
        btnAgregarProducto.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAgregarProducto.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnAgregarProducto);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jPanel1.add(jToolBar1, gridBagConstraints);

        jLabel1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel1.setText("Nota: Ingresar las cantidades que desea generar en los códigos de barras");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        jPanel1.add(jLabel1, gridBagConstraints);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarProducto;
    private javax.swing.JButton btnAgregarTodos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTable tblDatos;
    // End of variables declaration//GEN-END:variables

    public JButton getBtnAgregarProducto() {
        return btnAgregarProducto;
    }

    public void setBtnAgregarProducto(JButton btnAgregarProducto) {
        this.btnAgregarProducto = btnAgregarProducto;
    }

    public JButton getBtnAgregarTodos() {
        return btnAgregarTodos;
    }

    public void setBtnAgregarTodos(JButton btnAgregarTodos) {
        this.btnAgregarTodos = btnAgregarTodos;
    }

    public JTable getTblDatos() {
        return tblDatos;
    }

    public void setTblDatos(JTable tblDatos) {
        this.tblDatos = tblDatos;
    }

    
}
