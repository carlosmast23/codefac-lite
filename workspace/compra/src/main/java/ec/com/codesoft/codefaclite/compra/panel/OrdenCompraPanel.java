/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.compra.panel;

import com.toedter.calendar.JDateChooser;
import ec.com.codesoft.codefaclite.controlador.aplicacion.ControladorCodefacInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.util.LimpiarAnotacion;
import ec.com.codesoft.codefaclite.corecodefaclite.validation.ValidacionCodefacAnotacion;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencionIva;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencionRenta;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author Carlos
 */
public abstract class OrdenCompraPanel extends ControladorCodefacInterface {

    public JButton getBtnEditarItem() {
        return btnEditarItem;
    }

    public void setBtnEditarItem(JButton btnEditarItem) {
        this.btnEditarItem = btnEditarItem;
    }

    public JButton getBtnEliminarItem() {
        return btnEliminarItem;
    }

    public void setBtnEliminarItem(JButton btnEliminarItem) {
        this.btnEliminarItem = btnEliminarItem;
    }

    /**
     * Creates new form CompraPanel
     */
    public OrdenCompraPanel() {
        initComponents();
    }


    public JLabel getLblSubtotalImpuestos() {
        return lblSubtotalImpuestos;
    }

    public void setLblSubtotalImpuestos(JLabel lblSubtotalImpuestos) {
        this.lblSubtotalImpuestos = lblSubtotalImpuestos;
    }

    public JLabel getLblSubtotalSinImpuestos() {
        return lblSubtotalSinImpuestos;
    }

    public void setLblSubtotalSinImpuestos(JLabel lblSubtotalSinImpuestos) {
        this.lblSubtotalSinImpuestos = lblSubtotalSinImpuestos;
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

        jScrollBar1 = new javax.swing.JScrollBar();
        jLabel1 = new javax.swing.JLabel();
        txtProveedor = new javax.swing.JTextField();
        btnProveedorBuscar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtObservacion = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        cmbFechaIngreso = new com.toedter.calendar.JDateChooser();
        jLabel12 = new javax.swing.JLabel();
        cmbTipoDocumento = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        btnBuscarProductoProveedor = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        txtDescripcionItem = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtCantidadItem = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtPrecionUnitarioItem = new javax.swing.JTextField();
        btnAgregarItem = new javax.swing.JButton();
        btnEditarItem = new javax.swing.JButton();
        btnEliminarItem = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        cmbCobraIva = new javax.swing.JComboBox<>();
        btnCrearProducto = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        txtDescuentoImpuestos = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        lblSubtotalImpuesto = new javax.swing.JLabel();
        lblSubtotalSinImpuesto = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txtDescuentoSinImpuestos = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        lblSubtotalImpuestos = new javax.swing.JLabel();
        lblSubtotalSinImpuestos = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        lblIva = new javax.swing.JLabel();
        lblEspacio15 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDetalleProductos = new javax.swing.JTable();
        lblEspacio10 = new javax.swing.JLabel();
        lblEspacio12 = new javax.swing.JLabel();
        btnAgregarProveedor = new javax.swing.JButton();
        lblEspacio2 = new javax.swing.JLabel();
        lblEspacio4 = new javax.swing.JLabel();
        lblEspacio6 = new javax.swing.JLabel();
        lblCodigoOrdenCompra = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Orden de Compra");
        setMinimumSize(new java.awt.Dimension(853, 642));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel1.setText("Proveedor:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jLabel1, gridBagConstraints);

        txtProveedor.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 0.1;
        getContentPane().add(txtProveedor, gridBagConstraints);

        btnProveedorBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/pequenos/Zoom.png"))); // NOI18N
        btnProveedorBuscar.setText("Buscar");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 13;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(btnProveedorBuscar, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel2.setText("Codigo:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jLabel2, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel3.setText("Observación:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jLabel3, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 13;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        getContentPane().add(txtObservacion, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel4.setText("Fecha Ingreso:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 17;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jLabel4, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 18;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(cmbFechaIngreso, gridBagConstraints);

        jLabel12.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel12.setText("Tipo Documento:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 17;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jLabel12, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 18;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(cmbTipoDocumento, gridBagConstraints);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Detalles"));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        btnBuscarProductoProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/pequenos/list.png"))); // NOI18N
        btnBuscarProductoProveedor.setText("Producto");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(btnBuscarProductoProveedor, gridBagConstraints);

        jLabel8.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel8.setText("Descripcion:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel8, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 0.1;
        jPanel2.add(txtDescripcionItem, gridBagConstraints);

        jLabel9.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel9.setText("Cantidad");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel9, gridBagConstraints);

        txtCantidadItem.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 0.1;
        jPanel2.add(txtCantidadItem, gridBagConstraints);

        jLabel10.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel10.setText("P.Unitario:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel10, gridBagConstraints);

        txtPrecionUnitarioItem.setText("0.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 0.1;
        jPanel2.add(txtPrecionUnitarioItem, gridBagConstraints);

        btnAgregarItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/pequenos/add2.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 17;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jPanel2.add(btnAgregarItem, gridBagConstraints);

        btnEditarItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/pequenos/edit_icon.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 18;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jPanel2.add(btnEditarItem, gridBagConstraints);

        btnEliminarItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/pequenos/cerrar-ico.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 19;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel2.add(btnEliminarItem, gridBagConstraints);

        jLabel17.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel17.setText("Cobra Iva:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel17, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 0.1;
        jPanel2.add(cmbCobraIva, gridBagConstraints);

        btnCrearProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/documento-icon.png"))); // NOI18N
        btnCrearProducto.setText("Crear");
        btnCrearProducto.setToolTipText("Crear un nuevo producto");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(btnCrearProducto, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 18;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.8;
        gridBagConstraints.insets = new java.awt.Insets(20, 5, 5, 5);
        getContentPane().add(jPanel2, gridBagConstraints);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Totales"));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel11.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel11.setText("Descuento 12%");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(1, 2, 1, 2);
        jPanel1.add(jLabel11, gridBagConstraints);

        txtDescuentoImpuestos.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtDescuentoImpuestos.setText("0.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(1, 2, 1, 10);
        jPanel1.add(txtDescuentoImpuestos, gridBagConstraints);

        jLabel14.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel14.setText("Subtotal 12%");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(1, 2, 1, 2);
        jPanel1.add(jLabel14, gridBagConstraints);

        jLabel15.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel15.setText("Subtotal 0%");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(1, 2, 1, 2);
        jPanel1.add(jLabel15, gridBagConstraints);

        jLabel16.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel16.setText("Iva:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(1, 2, 1, 2);
        jPanel1.add(jLabel16, gridBagConstraints);

        lblSubtotalImpuesto.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblSubtotalImpuesto.setText("0.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(1, 2, 1, 10);
        jPanel1.add(lblSubtotalImpuesto, gridBagConstraints);

        lblSubtotalSinImpuesto.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblSubtotalSinImpuesto.setText("0.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(1, 2, 1, 10);
        jPanel1.add(lblSubtotalSinImpuesto, gridBagConstraints);

        jLabel19.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel19.setText("Descuento 0%");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(1, 2, 1, 2);
        jPanel1.add(jLabel19, gridBagConstraints);

        txtDescuentoSinImpuestos.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtDescuentoSinImpuestos.setText("0.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(1, 2, 1, 10);
        jPanel1.add(txtDescuentoSinImpuestos, gridBagConstraints);

        jLabel20.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel20.setText("Subtotal Inpuestos:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(1, 2, 1, 2);
        jPanel1.add(jLabel20, gridBagConstraints);

        jLabel21.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel21.setText("Subtotal Sin Impuesto:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(1, 2, 1, 2);
        jPanel1.add(jLabel21, gridBagConstraints);

        lblSubtotalImpuestos.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblSubtotalImpuestos.setText("0.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(1, 2, 1, 10);
        jPanel1.add(lblSubtotalImpuestos, gridBagConstraints);

        lblSubtotalSinImpuestos.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblSubtotalSinImpuestos.setText("0.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(1, 2, 1, 10);
        jPanel1.add(lblSubtotalSinImpuestos, gridBagConstraints);

        jLabel24.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel24.setText("TOTAL:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(1, 2, 1, 2);
        jPanel1.add(jLabel24, gridBagConstraints);

        lblTotal.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblTotal.setText("0.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(1, 2, 1, 10);
        jPanel1.add(lblTotal, gridBagConstraints);

        lblIva.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblIva.setText("0.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(1, 2, 1, 10);
        jPanel1.add(lblIva, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weighty = 0.1;
        jPanel1.add(lblEspacio15, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 19;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 0.1;
        getContentPane().add(jPanel1, gridBagConstraints);

        tblDetalleProductos.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblDetalleProductos);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 20;
        gridBagConstraints.gridheight = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jScrollPane1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.3;
        getContentPane().add(lblEspacio10, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.3;
        getContentPane().add(lblEspacio12, gridBagConstraints);

        btnAgregarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/persona-ico.png"))); // NOI18N
        btnAgregarProveedor.setToolTipText("Agregar Proveedor");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 14;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(btnAgregarProveedor, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 15;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        getContentPane().add(lblEspacio2, gridBagConstraints);
        getContentPane().add(lblEspacio4, new java.awt.GridBagConstraints());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 12;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.3;
        getContentPane().add(lblEspacio6, gridBagConstraints);

        lblCodigoOrdenCompra.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblCodigoOrdenCompra.setText("00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(lblCodigoOrdenCompra, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarItem;
    private javax.swing.JButton btnAgregarProveedor;
    private javax.swing.JButton btnBuscarProductoProveedor;
    private javax.swing.JButton btnCrearProducto;
    private javax.swing.JButton btnEditarItem;
    private javax.swing.JButton btnEliminarItem;
    private javax.swing.JButton btnProveedorBuscar;
    private javax.swing.JComboBox<EnumSiNo> cmbCobraIva;
    private com.toedter.calendar.JDateChooser cmbFechaIngreso;
    private javax.swing.JComboBox<TipoDocumentoEnum> cmbTipoDocumento;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCodigoOrdenCompra;
    private javax.swing.JLabel lblEspacio10;
    private javax.swing.JLabel lblEspacio12;
    private javax.swing.JLabel lblEspacio15;
    private javax.swing.JLabel lblEspacio2;
    private javax.swing.JLabel lblEspacio4;
    private javax.swing.JLabel lblEspacio6;
    private javax.swing.JLabel lblIva;
    private javax.swing.JLabel lblSubtotalImpuesto;
    private javax.swing.JLabel lblSubtotalImpuestos;
    private javax.swing.JLabel lblSubtotalSinImpuesto;
    private javax.swing.JLabel lblSubtotalSinImpuestos;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JTable tblDetalleProductos;
    private javax.swing.JTextField txtCantidadItem;
    private javax.swing.JTextField txtDescripcionItem;
    private javax.swing.JTextField txtDescuentoImpuestos;
    private javax.swing.JTextField txtDescuentoSinImpuestos;
    private javax.swing.JTextField txtObservacion;
    private javax.swing.JTextField txtPrecionUnitarioItem;
    private javax.swing.JTextField txtProveedor;
    // End of variables declaration//GEN-END:variables


    public JComboBox<TipoDocumentoEnum> getCmbTipoDocumento() {
        return cmbTipoDocumento;
    }

    public void setCmbTipoDocumento(JComboBox<TipoDocumentoEnum> cmbTipoDocumento) {
        this.cmbTipoDocumento = cmbTipoDocumento;
    }

    public JButton getBtnProveedorBuscar() {
        return btnProveedorBuscar;
    }

    public void setBtnProveedorBuscar(JButton btnProveedorBuscar) {
        this.btnProveedorBuscar = btnProveedorBuscar;
    }


    public JTextField getTxtProveedor() {
        return txtProveedor;
    }

    public void setTxtProveedor(JTextField txtProveedor) {
        this.txtProveedor = txtProveedor;
    }

    public JButton getBtnBuscarProductoProveedor() {
        return btnBuscarProductoProveedor;
    }

    public void setBtnBuscarProductoProveedor(JButton btnBuscarProductoProveedor) {
        this.btnBuscarProductoProveedor = btnBuscarProductoProveedor;
    }

    @LimpiarAnotacion
    @ValidacionCodefacAnotacion(requerido=true ,expresionRegular = "^[A-Za-z0-9\\s.\\_\\-\\ ]*$",nombre = "Descripción",grupo = "detalles")
    public JTextField getTxtDescripcionItem() {
        return txtDescripcionItem;
    }

    
    public void setTxtDescripcionItem(JTextField txtDescripcionItem) {
        this.txtDescripcionItem = txtDescripcionItem;
    }

    @LimpiarAnotacion
    @ValidacionCodefacAnotacion(requerido=true ,expresionRegular = "^[0-9]+([.][0-9]+)?$", nombre = "Precio Unitario",grupo = "detalles")
    public JTextField getTxtPrecionUnitarioItem() {
        return txtPrecionUnitarioItem;
    }

    public void setTxtPrecionUnitarioItem(JTextField txtPrecionUnitarioItem) {
        this.txtPrecionUnitarioItem = txtPrecionUnitarioItem;
    }

    public JButton getBtnAgregarItem() {
        return btnAgregarItem;
    }

    public void setBtnAgregarItem(JButton btnAgregarItem) {
        this.btnAgregarItem = btnAgregarItem;
    }
    
    
    @LimpiarAnotacion
    @ValidacionCodefacAnotacion(requerido=true ,expresionRegular = "^[0-9]+$",nombre = "Cantidad",grupo = "detalles")
    public JTextField getTxtCantidadItem() {
        return txtCantidadItem;
    }

    public void setTxtCantidadItem(JTextField txtCantidadItem) {
        this.txtCantidadItem = txtCantidadItem;
    }
    
    @LimpiarAnotacion
    @ValidacionCodefacAnotacion(requerido=true ,expresionRegular = "^[A-Za-z0-9\\s.\\_\\-\\ ]*$",nombre = "Observación")
    public JTextField getTxtObservacion() {
        return txtObservacion;
    }

    public void setTxtObservacion(JTextField txtObservacion) {
        this.txtObservacion = txtObservacion;
    }


//    @LimpiarAnotacion
//    @ValidacionCodefacAnotacion(requerido=true ,expresionRegular = "^[0-9]+$",nombre = "Preimpreso")
//    public JTextField getTxtPreimpreso() {
//        return txtEstablecimiento;
//    }
//
//    public void setTxtPreimpreso(JTextField txtPreimpreso) {
//        this.txtEstablecimiento = txtPreimpreso;
//    }

    @LimpiarAnotacion
    @ValidacionCodefacAnotacion(requerido=false, expresionRegular = "^[0-9]+([.][0-9]+)?$", nombre = "DescuentoImpuestos")
    public JTextField getTxtDescuentoImpuestos() {
        return txtDescuentoImpuestos;
    }

    public void setTxtDescuentoImpuestos(JTextField txtDescuentoImpuestos) {
        this.txtDescuentoImpuestos = txtDescuentoImpuestos;
    }
    
    @LimpiarAnotacion
    @ValidacionCodefacAnotacion(requerido = false, expresionRegular = "^[0-9]+([.][0-9]+)?$", nombre = "DescuentoSinImpuesto")
    public JTextField getTxtDescuentoSinImpuestos() {
        return txtDescuentoSinImpuestos;
    }

    public void setTxtDescuentoSinImpuestos(JTextField txtDescuentoSinImpuestos) {
        this.txtDescuentoSinImpuestos = txtDescuentoSinImpuestos;
    }

    public JLabel getLblIva() {
        return lblIva;
    }

    public void setLblIva(JLabel lblIva) {
        this.lblIva = lblIva;
    }

    public JLabel getLblSubtotalImpuesto() {
        return lblSubtotalImpuesto;
    }

    public void setLblSubtotalImpuesto(JLabel lblSubtotalImpuesto) {
        this.lblSubtotalImpuesto = lblSubtotalImpuesto;
    }

    public JLabel getLblSubtotalSinImpuesto() {
        return lblSubtotalSinImpuesto;
    }

    public void setLblSubtotalSinImpuesto(JLabel lblSubtotalSinImpuesto) {
        this.lblSubtotalSinImpuesto = lblSubtotalSinImpuesto;
    }

    public JLabel getLblTotal() {
        return lblTotal;
    }

    public void setLblTotal(JLabel lblTotal) {
        this.lblTotal = lblTotal;
    }

    public JTable getTblDetalleProductos() {
        return tblDetalleProductos;
    }

    public void setTblDetalleProductos(JTable tblDetalleProductos) {
        this.tblDetalleProductos = tblDetalleProductos;
    }

    public JDateChooser getCmbFechaIngreso() {
        return cmbFechaIngreso;
    }

    public void setCmbFechaIngreso(JDateChooser cmbFechaIngreso) {
        this.cmbFechaIngreso = cmbFechaIngreso;
    }

    

    public JComboBox<EnumSiNo> getCmbCobraIva() {
        return cmbCobraIva;
    }

    public void setCmbCobraIva(JComboBox<EnumSiNo> cmbCobraIva) {
        this.cmbCobraIva = cmbCobraIva;
    }

    public JButton getBtnAgregarProveedor() {
        return btnAgregarProveedor;
    }

    public void setBtnAgregarProveedor(JButton btnAgregarProveedor) {
        this.btnAgregarProveedor = btnAgregarProveedor;
    }

    public JButton getBtnCrearProducto() {
        return btnCrearProducto;
    }

    public void setBtnCrearProducto(JButton btnCrearProducto) {
        this.btnCrearProducto = btnCrearProducto;
    }

    public JLabel getLblCodigoOrdenCompra() {
        return lblCodigoOrdenCompra;
    }

    public void setLblCodigoOrdenCompra(JLabel lblCodigoOrdenCompra) {
        this.lblCodigoOrdenCompra = lblCodigoOrdenCompra;
    }
    
    
    
    
}
