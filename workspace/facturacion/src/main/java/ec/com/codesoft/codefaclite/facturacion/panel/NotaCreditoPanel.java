/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.panel;

import com.toedter.calendar.JDateChooser;
import ec.com.codesoft.codefaclite.controlador.aplicacion.ControladorCodefacInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.util.LimpiarAnotacion;
import ec.com.codesoft.codefaclite.corecodefaclite.validation.ValidacionCodefacAnotacion;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author Carlos
 */
public abstract class NotaCreditoPanel extends ControladorCodefacInterface {

    /**
     * Creates new form NotaCreditoPanel
     */
    public NotaCreditoPanel() {
        initComponents();
        java.util.Date fecha = new java.util.Date();
        jDateFechaEmision.setDate(fecha);
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

        panelDatosEmpresa = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblRuc = new javax.swing.JLabel();
        lblNombreComercial = new javax.swing.JLabel();
        lblDireccion = new javax.swing.JLabel();
        lblTelefonos = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        panelDatosGenerales = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        lblSecuencial = new javax.swing.JLabel();
        panelFechaEmision = new javax.swing.JPanel();
        jDateFechaEmision = new com.toedter.calendar.JDateChooser();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        PanelDatosNotaCredito = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtReferenciaFactura = new javax.swing.JTextField();
        btnBuscarFactura = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblDireccionCliente = new javax.swing.JLabel();
        lblNombreCliente = new javax.swing.JLabel();
        lblTelefonoCliente = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        PanelValores = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        lblValorTotal = new javax.swing.JLabel();
        lblSubtotal12 = new javax.swing.JLabel();
        lblSubtotal0 = new javax.swing.JLabel();
        lblSubtotalNoObjetoDeIva = new javax.swing.JLabel();
        lblSubtotalExentoDeIva = new javax.swing.JLabel();
        lblSubtotalSinImpuesto = new javax.swing.JLabel();
        lblTotalDescuento = new javax.swing.JLabel();
        lblValorIce = new javax.swing.JLabel();
        lblIva12 = new javax.swing.JLabel();
        lblValorIRBPNR = new javax.swing.JLabel();
        lblPropina10 = new javax.swing.JLabel();
        txtValorTotal = new javax.swing.JTextField();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel19 = new javax.swing.JLabel();
        txtMotivoAnulacion = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDetalleNotaCredito = new javax.swing.JTable();
        jLabel21 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("NotaCredito");
        setAutoscrolls(true);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        panelDatosEmpresa.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos Empresa", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        panelDatosEmpresa.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Ruc:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        panelDatosEmpresa.add(jLabel1, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Nombre Comercial:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        panelDatosEmpresa.add(jLabel2, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("Direccion:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        panelDatosEmpresa.add(jLabel3, gridBagConstraints);

        lblRuc.setBackground(new java.awt.Color(255, 255, 255));
        lblRuc.setText("jLabel5");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 47;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        panelDatosEmpresa.add(lblRuc, gridBagConstraints);

        lblNombreComercial.setBackground(new java.awt.Color(255, 255, 255));
        lblNombreComercial.setText("jLabel6");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 49;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        panelDatosEmpresa.add(lblNombreComercial, gridBagConstraints);

        lblDireccion.setBackground(new java.awt.Color(255, 255, 255));
        lblDireccion.setText("jLabel7");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 49;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        panelDatosEmpresa.add(lblDireccion, gridBagConstraints);

        lblTelefonos.setBackground(new java.awt.Color(255, 255, 255));
        lblTelefonos.setText("jLabel8");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 49;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        panelDatosEmpresa.add(lblTelefonos, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("Telefonos:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        panelDatosEmpresa.add(jLabel5, gridBagConstraints);

        jLabel20.setText("             ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 0.1;
        panelDatosEmpresa.add(jLabel20, gridBagConstraints);

        jLabel32.setText("             ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 0.1;
        panelDatosEmpresa.add(jLabel32, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        getContentPane().add(panelDatosEmpresa, gridBagConstraints);

        panelDatosGenerales.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos Generales", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        panelDatosGenerales.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        panelDatosGenerales.setLayout(new java.awt.GridBagLayout());

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel35.setText("Secuencial:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        panelDatosGenerales.add(jLabel35, gridBagConstraints);

        jLabel36.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel36.setText("Fecha Emision:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        panelDatosGenerales.add(jLabel36, gridBagConstraints);

        lblSecuencial.setText("001-002-9213912939");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        panelDatosGenerales.add(lblSecuencial, gridBagConstraints);

        panelFechaEmision.setBackground(new java.awt.Color(255, 255, 255));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        panelDatosGenerales.add(panelFechaEmision, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        panelDatosGenerales.add(jDateFechaEmision, gridBagConstraints);

        jLabel38.setText("             ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        panelDatosGenerales.add(jLabel38, gridBagConstraints);

        jLabel39.setText("             ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        panelDatosGenerales.add(jLabel39, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        getContentPane().add(panelDatosGenerales, gridBagConstraints);

        PanelDatosNotaCredito.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos Cliente", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        PanelDatosNotaCredito.setLayout(new java.awt.GridBagLayout());

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("Factura:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        PanelDatosNotaCredito.add(jLabel4, gridBagConstraints);

        txtReferenciaFactura.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtReferenciaFactura.setText("...");
        txtReferenciaFactura.setToolTipText("");
        txtReferenciaFactura.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        PanelDatosNotaCredito.add(txtReferenciaFactura, gridBagConstraints);

        btnBuscarFactura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/find2-ico.png"))); // NOI18N
        btnBuscarFactura.setText("Buscar");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        PanelDatosNotaCredito.add(btnBuscarFactura, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("Nombres:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        PanelDatosNotaCredito.add(jLabel6, gridBagConstraints);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("Telefono:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        PanelDatosNotaCredito.add(jLabel7, gridBagConstraints);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText("Direccion");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        PanelDatosNotaCredito.add(jLabel8, gridBagConstraints);

        lblDireccionCliente.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDireccionCliente.setText("...");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        PanelDatosNotaCredito.add(lblDireccionCliente, gridBagConstraints);

        lblNombreCliente.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNombreCliente.setText("...");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        PanelDatosNotaCredito.add(lblNombreCliente, gridBagConstraints);

        lblTelefonoCliente.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTelefonoCliente.setText("...");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        PanelDatosNotaCredito.add(lblTelefonoCliente, gridBagConstraints);

        jLabel33.setText("             ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 0.1;
        PanelDatosNotaCredito.add(jLabel33, gridBagConstraints);

        jLabel34.setText("             ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 0.1;
        PanelDatosNotaCredito.add(jLabel34, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        getContentPane().add(PanelDatosNotaCredito, gridBagConstraints);

        PanelValores.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        PanelValores.setLayout(new java.awt.GridBagLayout());

        jLabel9.setText("SUBTOTAL SIN IMPUESTO");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(1, 2, 1, 2);
        PanelValores.add(jLabel9, gridBagConstraints);

        jLabel10.setText("SUBTOTAL 12.0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(1, 2, 1, 2);
        PanelValores.add(jLabel10, gridBagConstraints);

        jLabel11.setText("SUBTOTAL 0%");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(1, 2, 1, 2);
        PanelValores.add(jLabel11, gridBagConstraints);

        jLabel12.setText("SUBTOTAL NO OBJETO DE IVA");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(1, 2, 1, 2);
        PanelValores.add(jLabel12, gridBagConstraints);

        jLabel13.setText("SUBTOTAL EXENTO DE IVA");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(1, 2, 1, 2);
        PanelValores.add(jLabel13, gridBagConstraints);

        jLabel14.setText("TOTAL DESCUENTO");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(1, 2, 1, 2);
        PanelValores.add(jLabel14, gridBagConstraints);

        jLabel15.setText("VALOR ICE");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(1, 2, 1, 2);
        PanelValores.add(jLabel15, gridBagConstraints);

        jLabel16.setText("IVA 12.0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(1, 2, 1, 2);
        PanelValores.add(jLabel16, gridBagConstraints);

        jLabel17.setText("VALOR IRBPNR");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(1, 2, 1, 2);
        PanelValores.add(jLabel17, gridBagConstraints);

        jLabel18.setText("PROPINA 10%");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(1, 2, 1, 2);
        PanelValores.add(jLabel18, gridBagConstraints);

        lblValorTotal.setText("VALOR TOTAL");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(1, 2, 1, 2);
        PanelValores.add(lblValorTotal, gridBagConstraints);

        lblSubtotal12.setText("0.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(1, 2, 1, 2);
        PanelValores.add(lblSubtotal12, gridBagConstraints);

        lblSubtotal0.setText("0.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(1, 2, 1, 2);
        PanelValores.add(lblSubtotal0, gridBagConstraints);

        lblSubtotalNoObjetoDeIva.setText("0.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(1, 2, 1, 2);
        PanelValores.add(lblSubtotalNoObjetoDeIva, gridBagConstraints);

        lblSubtotalExentoDeIva.setText("0.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(1, 2, 1, 2);
        PanelValores.add(lblSubtotalExentoDeIva, gridBagConstraints);

        lblSubtotalSinImpuesto.setText("0.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(1, 2, 1, 2);
        PanelValores.add(lblSubtotalSinImpuesto, gridBagConstraints);

        lblTotalDescuento.setText("0.00");
        lblTotalDescuento.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 51, 255), null));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(1, 2, 1, 2);
        PanelValores.add(lblTotalDescuento, gridBagConstraints);

        lblValorIce.setText("0.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(1, 2, 1, 2);
        PanelValores.add(lblValorIce, gridBagConstraints);

        lblIva12.setText("0.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(1, 2, 1, 2);
        PanelValores.add(lblIva12, gridBagConstraints);

        lblValorIRBPNR.setText("0.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(1, 2, 1, 2);
        PanelValores.add(lblValorIRBPNR, gridBagConstraints);

        lblPropina10.setText("0.00");
        lblPropina10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 255)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(1, 2, 1, 2);
        PanelValores.add(lblPropina10, gridBagConstraints);

        txtValorTotal.setText("0.00");
        txtValorTotal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 255)));
        txtValorTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtValorTotalActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(1, 2, 1, 2);
        PanelValores.add(txtValorTotal, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        PanelValores.add(jCheckBox1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        getContentPane().add(PanelValores, gridBagConstraints);

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel19.setText("Motivo Anulacion:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        getContentPane().add(jLabel19, gridBagConstraints);

        txtMotivoAnulacion.setText("                                                         ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        getContentPane().add(txtMotivoAnulacion, gridBagConstraints);

        tblDetalleNotaCredito.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblDetalleNotaCredito);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        getContentPane().add(jScrollPane1, gridBagConstraints);

        jLabel21.setText("             ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        getContentPane().add(jLabel21, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtValorTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtValorTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtValorTotalActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelDatosNotaCredito;
    private javax.swing.JPanel PanelValores;
    private javax.swing.JButton btnBuscarFactura;
    private javax.swing.JCheckBox jCheckBox1;
    private com.toedter.calendar.JDateChooser jDateFechaEmision;
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
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDireccion;
    private javax.swing.JLabel lblDireccionCliente;
    private javax.swing.JLabel lblIva12;
    private javax.swing.JLabel lblNombreCliente;
    private javax.swing.JLabel lblNombreComercial;
    private javax.swing.JLabel lblPropina10;
    private javax.swing.JLabel lblRuc;
    private javax.swing.JLabel lblSecuencial;
    private javax.swing.JLabel lblSubtotal0;
    private javax.swing.JLabel lblSubtotal12;
    private javax.swing.JLabel lblSubtotalExentoDeIva;
    private javax.swing.JLabel lblSubtotalNoObjetoDeIva;
    private javax.swing.JLabel lblSubtotalSinImpuesto;
    private javax.swing.JLabel lblTelefonoCliente;
    private javax.swing.JLabel lblTelefonos;
    private javax.swing.JLabel lblTotalDescuento;
    private javax.swing.JLabel lblValorIRBPNR;
    private javax.swing.JLabel lblValorIce;
    private javax.swing.JLabel lblValorTotal;
    private javax.swing.JPanel panelDatosEmpresa;
    private javax.swing.JPanel panelDatosGenerales;
    private javax.swing.JPanel panelFechaEmision;
    private javax.swing.JTable tblDetalleNotaCredito;
    private javax.swing.JTextField txtMotivoAnulacion;
    private javax.swing.JTextField txtReferenciaFactura;
    private javax.swing.JTextField txtValorTotal;
    // End of variables declaration//GEN-END:variables

    public JLabel getLblDireccion() {
        return lblDireccion;
    }

    public void setLblDireccion(JLabel lblDireccion) {
        this.lblDireccion = lblDireccion;
    }

    public JLabel getLblRuc() {
        return lblRuc;
    }

    public void setLblRuc(JLabel lblRuc) {
        this.lblRuc = lblRuc;
    }

    public JLabel getLblNombreComercial() {
        return lblNombreComercial;
    }

    public void setLblNombreComercial(JLabel lblNombreComercial) {
        this.lblNombreComercial = lblNombreComercial;
    }

    public JLabel getLblTelefonos() {
        return lblTelefonos;
    }

    public void setLblTelefonos(JLabel lblTelefonos) {
        this.lblTelefonos = lblTelefonos;
    }

    public JButton getBtnBuscarFactura() {
        return btnBuscarFactura;
    }

    public JTextField getTxtReferenciaFactura() {
        return txtReferenciaFactura;
    }

    public void setTxtReferenciaFactura(JTextField txtReferenciaFactura) {
        this.txtReferenciaFactura = txtReferenciaFactura;
    }

    public JLabel getLblDireccionCliente() {
        return lblDireccionCliente;
    }

    public JLabel getLblNombreCliente() {
        return lblNombreCliente;
    }

    public JLabel getLblTelefonoCliente() {
        return lblTelefonoCliente;
    }

    public JTable getTblDetalleNotaCredito() {
        return tblDetalleNotaCredito;
    }

    public JLabel getLblIva12() {
        return lblIva12;
    }

    public JLabel getLblPropina10() {
        return lblPropina10;
    }

    public JLabel getLblSecuencial() {
        return lblSecuencial;
    }

    public JLabel getLblSubtotal0() {
        return lblSubtotal0;
    }

    public JLabel getLblSubtotal12() {
        return lblSubtotal12;
    }

    public JLabel getLblSubtotalExentoDeIva() {
        return lblSubtotalExentoDeIva;
    }

    public JLabel getLblSubtotalNoObjetoDeIva() {
        return lblSubtotalNoObjetoDeIva;
    }

    public JLabel getLblSubtotalSinImpuesto() {
        return lblSubtotalSinImpuesto;
    }

    public JLabel getLblTotalDescuento() {
        return lblTotalDescuento;
    }

    public JLabel getLblValorIRBPNR() {
        return lblValorIRBPNR;
    }

    public JLabel getLblValorIce() {
        return lblValorIce;
    }

    public JLabel getLblValorTotal() {
        return lblValorTotal;
    }

    public JTextField getTxtValorTotal() {
        return txtValorTotal;
    }

    @LimpiarAnotacion
    @ValidacionCodefacAnotacion(requerido = true)
    public JTextField getTxtMotivoAnulacion() {
        return txtMotivoAnulacion;
    }

    public void setTxtMotivoAnulacion(JTextField txtMotivoAnulacion) {
        this.txtMotivoAnulacion = txtMotivoAnulacion;
    }

    public JDateChooser getjDateFechaEmision() {
        return jDateFechaEmision;
    }

    public void setjDateFechaEmision(JDateChooser jDateFechaEmision) {
        this.jDateFechaEmision = jDateFechaEmision;
    }

}
