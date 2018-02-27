/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.crm.panel;

import com.toedter.calendar.JDateChooser;
import ec.com.codesoft.codefaclite.controlador.aplicacion.ControladorCodefacInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.util.LimpiarAnotacion;
import ec.com.codesoft.codefaclite.corecodefaclite.validation.ValidacionCodefacAnotacion;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author Carlos
 */
public abstract class CompraPanel extends ControladorCodefacInterface {

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
    public CompraPanel() {
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

        jLabel1 = new javax.swing.JLabel();
        txtProveedor = new javax.swing.JTextField();
        btnProveedorBuscar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtOrdenCompra = new javax.swing.JTextField();
        btnOrdenCompraBuscar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtObservacion = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtEstablecimiento = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtAutorizacion = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        cmbFechaCompra = new com.toedter.calendar.JDateChooser();
        jLabel12 = new javax.swing.JLabel();
        cmbTipoDocumento = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtProductoItem = new javax.swing.JTextField();
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDetalleProductos = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        cmbDocumento = new javax.swing.JComboBox<>();
        txtPuntoEmision = new javax.swing.JTextField();
        txtSecuencial = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Compra");
        setMinimumSize(new java.awt.Dimension(853, 642));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jLabel1.setText("Proveedor:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        getContentPane().add(jLabel1, gridBagConstraints);

        txtProveedor.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 0.1;
        getContentPane().add(txtProveedor, gridBagConstraints);

        btnProveedorBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/pequenos/Zoom.png"))); // NOI18N
        btnProveedorBuscar.setText("Buscar");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        getContentPane().add(btnProveedorBuscar, gridBagConstraints);

        jLabel2.setText("OrdenCompra:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        getContentPane().add(jLabel2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 0.1;
        getContentPane().add(txtOrdenCompra, gridBagConstraints);

        btnOrdenCompraBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/pequenos/Zoom.png"))); // NOI18N
        btnOrdenCompraBuscar.setText("Buscar");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        getContentPane().add(btnOrdenCompraBuscar, gridBagConstraints);

        jLabel3.setText("Observacion");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        getContentPane().add(jLabel3, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 0.1;
        getContentPane().add(txtObservacion, gridBagConstraints);

        jLabel5.setText("Preimpreso:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        getContentPane().add(jLabel5, gridBagConstraints);

        txtEstablecimiento.setText("001");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        getContentPane().add(txtEstablecimiento, gridBagConstraints);

        jLabel6.setText("Autorizacion");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        getContentPane().add(jLabel6, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 0.1;
        getContentPane().add(txtAutorizacion, gridBagConstraints);

        jLabel4.setText("Fecha Compra:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        getContentPane().add(jLabel4, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 0.1;
        getContentPane().add(cmbFechaCompra, gridBagConstraints);

        jLabel12.setText("Tipo Documento:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        getContentPane().add(jLabel12, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        getContentPane().add(cmbTipoDocumento, gridBagConstraints);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Detalles"));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel7.setText("Producto:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        jPanel2.add(jLabel7, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        jPanel2.add(txtProductoItem, gridBagConstraints);

        btnBuscarProductoProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/pequenos/list.png"))); // NOI18N
        btnBuscarProductoProveedor.setText("Productos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        jPanel2.add(btnBuscarProductoProveedor, gridBagConstraints);

        jLabel8.setText("Descripcion:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        jPanel2.add(jLabel8, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        jPanel2.add(txtDescripcionItem, gridBagConstraints);

        jLabel9.setText("Cantidad");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        jPanel2.add(jLabel9, gridBagConstraints);

        txtCantidadItem.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 0.1;
        jPanel2.add(txtCantidadItem, gridBagConstraints);

        jLabel10.setText("P.Unitario:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        jPanel2.add(jLabel10, gridBagConstraints);

        txtPrecionUnitarioItem.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 0.1;
        jPanel2.add(txtPrecionUnitarioItem, gridBagConstraints);

        btnAgregarItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/pequenos/add2.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jPanel2.add(btnAgregarItem, gridBagConstraints);

        btnEditarItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/pequenos/edit_icon.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jPanel2.add(btnEditarItem, gridBagConstraints);

        btnEliminarItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/pequenos/cerrar-ico.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 11;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jPanel2.add(btnEliminarItem, gridBagConstraints);

        jLabel17.setText("Cobra Iva:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        jPanel2.add(jLabel17, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 0.1;
        jPanel2.add(cmbCobraIva, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        getContentPane().add(jPanel2, gridBagConstraints);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Totales"));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel11.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel11.setText("Descuento 12%");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(1, 2, 1, 2);
        jPanel1.add(jLabel11, gridBagConstraints);

        txtDescuentoImpuestos.setText("0.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(1, 2, 1, 10);
        jPanel1.add(txtDescuentoImpuestos, gridBagConstraints);

        jLabel14.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel14.setText("Subtotal 12%");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(1, 2, 1, 2);
        jPanel1.add(jLabel14, gridBagConstraints);

        jLabel15.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel15.setText("Subtotal 0%");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(1, 2, 1, 2);
        jPanel1.add(jLabel15, gridBagConstraints);

        jLabel16.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel16.setText("Iva:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(1, 2, 1, 2);
        jPanel1.add(jLabel16, gridBagConstraints);

        lblSubtotalImpuesto.setText("0.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(1, 2, 1, 10);
        jPanel1.add(lblSubtotalImpuesto, gridBagConstraints);

        lblSubtotalSinImpuesto.setText("0.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(1, 2, 1, 10);
        jPanel1.add(lblSubtotalSinImpuesto, gridBagConstraints);

        jLabel19.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel19.setText("Descuento 0%");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(1, 2, 1, 2);
        jPanel1.add(jLabel19, gridBagConstraints);

        txtDescuentoSinImpuestos.setText("0.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(1, 2, 1, 10);
        jPanel1.add(txtDescuentoSinImpuestos, gridBagConstraints);

        jLabel20.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel20.setText("Subtotal Inpuestos:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(1, 2, 1, 2);
        jPanel1.add(jLabel20, gridBagConstraints);

        jLabel21.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel21.setText("Subtotal Sin Impuesto:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(1, 2, 1, 2);
        jPanel1.add(jLabel21, gridBagConstraints);

        lblSubtotalImpuestos.setText("0.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(1, 2, 1, 10);
        jPanel1.add(lblSubtotalImpuestos, gridBagConstraints);

        lblSubtotalSinImpuestos.setText("0.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(1, 2, 1, 10);
        jPanel1.add(lblSubtotalSinImpuestos, gridBagConstraints);

        jLabel24.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        jLabel24.setText("TOTAL:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(1, 2, 1, 2);
        jPanel1.add(jLabel24, gridBagConstraints);

        lblTotal.setText("0.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(1, 2, 1, 10);
        jPanel1.add(lblTotal, gridBagConstraints);

        lblIva.setText("0.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(1, 2, 1, 10);
        jPanel1.add(lblIva, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
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
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.gridheight = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jScrollPane1, gridBagConstraints);

        jLabel13.setText("Documento:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        getContentPane().add(jLabel13, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        getContentPane().add(cmbDocumento, gridBagConstraints);

        txtPuntoEmision.setText("001");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        getContentPane().add(txtPuntoEmision, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        getContentPane().add(txtSecuencial, gridBagConstraints);

        jLabel26.setText("                    ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 8;
        getContentPane().add(jLabel26, gridBagConstraints);

        jLabel28.setText("                              ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 8;
        getContentPane().add(jLabel28, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarItem;
    private javax.swing.JButton btnBuscarProductoProveedor;
    private javax.swing.JButton btnEditarItem;
    private javax.swing.JButton btnEliminarItem;
    private javax.swing.JButton btnOrdenCompraBuscar;
    private javax.swing.JButton btnProveedorBuscar;
    private javax.swing.JComboBox<EnumSiNo> cmbCobraIva;
    private javax.swing.JComboBox<DocumentoEnum> cmbDocumento;
    private com.toedter.calendar.JDateChooser cmbFechaCompra;
    private javax.swing.JComboBox<TipoDocumentoEnum> cmbTipoDocumento;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblIva;
    private javax.swing.JLabel lblSubtotalImpuesto;
    private javax.swing.JLabel lblSubtotalImpuestos;
    private javax.swing.JLabel lblSubtotalSinImpuesto;
    private javax.swing.JLabel lblSubtotalSinImpuestos;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JTable tblDetalleProductos;
    private javax.swing.JTextField txtAutorizacion;
    private javax.swing.JTextField txtCantidadItem;
    private javax.swing.JTextField txtDescripcionItem;
    private javax.swing.JTextField txtDescuentoImpuestos;
    private javax.swing.JTextField txtDescuentoSinImpuestos;
    private javax.swing.JTextField txtEstablecimiento;
    private javax.swing.JTextField txtObservacion;
    private javax.swing.JTextField txtOrdenCompra;
    private javax.swing.JTextField txtPrecionUnitarioItem;
    private javax.swing.JTextField txtProductoItem;
    private javax.swing.JTextField txtProveedor;
    private javax.swing.JTextField txtPuntoEmision;
    private javax.swing.JTextField txtSecuencial;
    // End of variables declaration//GEN-END:variables

    public JComboBox<DocumentoEnum> getCmbDocumento() {
        return cmbDocumento;
    }

    public void setCmbDocumento(JComboBox<DocumentoEnum> cmbDocumento) {
        this.cmbDocumento = cmbDocumento;
    }

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

    public JButton getBtnOrdenCompraBuscar() {
        return btnOrdenCompraBuscar;
    }

    public void setBtnOrdenCompraBuscar(JButton btnOrdenCompraBuscar) {
        this.btnOrdenCompraBuscar = btnOrdenCompraBuscar;
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
    @ValidacionCodefacAnotacion(requerido=true ,expresionRegular = "^[A-Za-z0-9\\s.\\_\\-\\ ]*$",nombre = "Producto",grupo = "detalles")
    public JTextField getTxtProductoItem() {
        return txtProductoItem;
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
    @ValidacionCodefacAnotacion(requerido=false ,expresionRegular = "^[A-Za-z0-9\\s.\\_\\-\\ ]*$",nombre = "Autorización")
    public JTextField getTxtAutorizacion() {
        return txtAutorizacion;
    }

    public void setTxtAutorizacion(JTextField txtAutorizacion) {
        this.txtAutorizacion = txtAutorizacion;
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

    public JTextField getTxtOrdenCompra() {
        return txtOrdenCompra;
    }

    public void setTxtOrdenCompra(JTextField txtOrdenCompra) {
        this.txtOrdenCompra = txtOrdenCompra;
    }

    @LimpiarAnotacion
    @ValidacionCodefacAnotacion(requerido=true ,expresionRegular = "^[0-9]+$",nombre = "Preimpreso")
    public JTextField getTxtPreimpreso() {
        return txtEstablecimiento;
    }

    public void setTxtPreimpreso(JTextField txtPreimpreso) {
        this.txtEstablecimiento = txtPreimpreso;
    }

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

    public JDateChooser getCmbFechaCompra() {
        return cmbFechaCompra;
    }

    public void setCmbFechaCompra(JDateChooser cmbFechaCompra) {
        this.cmbFechaCompra = cmbFechaCompra;
    }
    
    @LimpiarAnotacion
    @ValidacionCodefacAnotacion(requerido = true, expresionRegular = "^[0-9]+$", nombre = "Codigo establecimiento")
    public JTextField getTxtEstablecimiento() {
        return txtEstablecimiento;
    }

    public void setTxtEstablecimiento(JTextField txtEstablecimiento) {
        this.txtEstablecimiento = txtEstablecimiento;
    }
    
    @LimpiarAnotacion
    @ValidacionCodefacAnotacion(requerido=true ,expresionRegular = "^[0-9]+$",nombre = "Codigo punto emisión")
    public JTextField getTxtPuntoEmision() {
        return txtPuntoEmision;
    }

    public void setTxtPuntoEmision(JTextField txtPuntoEmision) {
        this.txtPuntoEmision = txtPuntoEmision;
    }
    
    @LimpiarAnotacion
    @ValidacionCodefacAnotacion(requerido=true ,expresionRegular = "^[0-9]+$",nombre = "Codigo secuencial")
    public JTextField getTxtSecuencial() {
        return txtSecuencial;
    }

    public void setTxtSecuencial(JTextField txtSecuencial) {
        this.txtSecuencial = txtSecuencial;
    }

    public JComboBox<EnumSiNo> getCmbCobraIva() {
        return cmbCobraIva;
    }

    public void setCmbCobraIva(JComboBox<EnumSiNo> cmbCobraIva) {
        this.cmbCobraIva = cmbCobraIva;
    }
    
    
    
    
    
    

    
}
