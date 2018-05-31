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
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author Carlos
 */
public abstract class RetencionPanel extends ControladorCodefacInterface {

    /**
     * Creates new form NotaCreditoPanel
     */
    public RetencionPanel() {
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

        panelDatosGenerales = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        lblSecuencial = new javax.swing.JLabel();
        jDateFechaEmision = new com.toedter.calendar.JDateChooser();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        lblEspacio1 = new javax.swing.JLabel();
        lblEspacio2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblRuc = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblDireccion = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblNombreComercial = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblTelefonos = new javax.swing.JLabel();
        PanelDatosNotaCredito = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtReferenciaFactura = new javax.swing.JTextField();
        btnBuscarFacturaCompra = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblDireccionCliente = new javax.swing.JLabel();
        lblNombreCliente = new javax.swing.JLabel();
        lblTelefonoCliente = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        lblEspacio3 = new javax.swing.JLabel();
        PanelValores = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lblValorTotal = new javax.swing.JLabel();
        lblSubtotalRetencionRenta = new javax.swing.JLabel();
        lblSubtotalRetencionIva = new javax.swing.JLabel();
        lblRetencionTotal = new javax.swing.JTextField();
        lblEspacio4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDetalleRetenciones = new javax.swing.JTable();
        PanelDatosAdicionales = new javax.swing.JPanel();
        btnAgregarDatosAdicionales = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblDatosAdicionales = new javax.swing.JTable();
        btnQuitarDatosAdicionales = new javax.swing.JButton();
        PanelDatosAdicionales1 = new javax.swing.JPanel();
        cmbRetencionIva = new javax.swing.JComboBox<>();
        jLabel22 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        cmbRetencionRenta = new javax.swing.JComboBox<>();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Retención");
        setAutoscrolls(true);
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/facturaError.png"))); // NOI18N
        getContentPane().setLayout(new java.awt.GridBagLayout());

        panelDatosGenerales.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos Generales", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 11))); // NOI18N
        panelDatosGenerales.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        panelDatosGenerales.setLayout(new java.awt.GridBagLayout());

        jLabel35.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel35.setText("Secuencial:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelDatosGenerales.add(jLabel35, gridBagConstraints);

        jLabel36.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel36.setText("Fecha Emision:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelDatosGenerales.add(jLabel36, gridBagConstraints);

        lblSecuencial.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblSecuencial.setText("001-002-9213912939");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelDatosGenerales.add(lblSecuencial, gridBagConstraints);

        jDateFechaEmision.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.05;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
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
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelDatosGenerales.add(jLabel39, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 0.1;
        panelDatosGenerales.add(lblEspacio1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 0.1;
        panelDatosGenerales.add(lblEspacio2, gridBagConstraints);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setText("Ruc:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelDatosGenerales.add(jLabel1, gridBagConstraints);

        lblRuc.setBackground(new java.awt.Color(255, 255, 255));
        lblRuc.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblRuc.setText("jLabel5");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelDatosGenerales.add(lblRuc, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel3.setText("Direccion:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelDatosGenerales.add(jLabel3, gridBagConstraints);

        lblDireccion.setBackground(new java.awt.Color(255, 255, 255));
        lblDireccion.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblDireccion.setText("jLabel7");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        panelDatosGenerales.add(lblDireccion, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setText("Nombre Comercial:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelDatosGenerales.add(jLabel2, gridBagConstraints);

        lblNombreComercial.setBackground(new java.awt.Color(255, 255, 255));
        lblNombreComercial.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblNombreComercial.setText("jLabel6");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelDatosGenerales.add(lblNombreComercial, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel5.setText("Telefonos:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelDatosGenerales.add(jLabel5, gridBagConstraints);

        lblTelefonos.setBackground(new java.awt.Color(255, 255, 255));
        lblTelefonos.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblTelefonos.setText("jLabel8");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelDatosGenerales.add(lblTelefonos, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        getContentPane().add(panelDatosGenerales, gridBagConstraints);

        PanelDatosNotaCredito.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos Empresa", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 11))); // NOI18N
        PanelDatosNotaCredito.setLayout(new java.awt.GridBagLayout());

        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setText("Factura Compra:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        PanelDatosNotaCredito.add(jLabel4, gridBagConstraints);

        txtReferenciaFactura.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
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
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        PanelDatosNotaCredito.add(txtReferenciaFactura, gridBagConstraints);

        btnBuscarFacturaCompra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/find2-ico.png"))); // NOI18N
        btnBuscarFacturaCompra.setText("Buscar");
        btnBuscarFacturaCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarFacturaCompraActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        PanelDatosNotaCredito.add(btnBuscarFacturaCompra, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel6.setText("Proveedores:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        PanelDatosNotaCredito.add(jLabel6, gridBagConstraints);

        jLabel7.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel7.setText("Telefono:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        PanelDatosNotaCredito.add(jLabel7, gridBagConstraints);

        jLabel8.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel8.setText("Direccion");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        PanelDatosNotaCredito.add(jLabel8, gridBagConstraints);

        lblDireccionCliente.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblDireccionCliente.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDireccionCliente.setText("LblDireccionCompleta");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        PanelDatosNotaCredito.add(lblDireccionCliente, gridBagConstraints);

        lblNombreCliente.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblNombreCliente.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNombreCliente.setText("lblNombresCompletos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        PanelDatosNotaCredito.add(lblNombreCliente, gridBagConstraints);

        lblTelefonoCliente.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblTelefonoCliente.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTelefonoCliente.setText("lblTelefonos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        PanelDatosNotaCredito.add(lblTelefonoCliente, gridBagConstraints);

        jLabel34.setText("             ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 0.1;
        PanelDatosNotaCredito.add(jLabel34, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        PanelDatosNotaCredito.add(lblEspacio3, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        getContentPane().add(PanelDatosNotaCredito, gridBagConstraints);

        PanelValores.setBorder(javax.swing.BorderFactory.createTitledBorder("Total"));
        PanelValores.setLayout(new java.awt.GridBagLayout());

        jLabel9.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel9.setText("SUBTOTAL RET IVA:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 0.8;
        gridBagConstraints.insets = new java.awt.Insets(1, 2, 1, 2);
        PanelValores.add(jLabel9, gridBagConstraints);

        jLabel10.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel10.setText("SUBTOTAL RET RENTA:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 0.8;
        gridBagConstraints.insets = new java.awt.Insets(1, 2, 1, 2);
        PanelValores.add(jLabel10, gridBagConstraints);

        lblValorTotal.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblValorTotal.setText("VALOR TOTAL");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 0.8;
        gridBagConstraints.insets = new java.awt.Insets(1, 2, 1, 2);
        PanelValores.add(lblValorTotal, gridBagConstraints);

        lblSubtotalRetencionRenta.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblSubtotalRetencionRenta.setText("0.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(1, 2, 1, 2);
        PanelValores.add(lblSubtotalRetencionRenta, gridBagConstraints);

        lblSubtotalRetencionIva.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblSubtotalRetencionIva.setText("0.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(1, 2, 1, 2);
        PanelValores.add(lblSubtotalRetencionIva, gridBagConstraints);

        lblRetencionTotal.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblRetencionTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        lblRetencionTotal.setText("0.00");
        lblRetencionTotal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 255)));
        lblRetencionTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblRetencionTotalActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(1, 2, 1, 2);
        PanelValores.add(lblRetencionTotal, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weighty = 0.1;
        PanelValores.add(lblEspacio4, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.4;
        gridBagConstraints.insets = new java.awt.Insets(1, 2, 1, 2);
        getContentPane().add(PanelValores, gridBagConstraints);

        tblDetalleRetenciones.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        tblDetalleRetenciones.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblDetalleRetenciones);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.4;
        getContentPane().add(jScrollPane1, gridBagConstraints);

        PanelDatosAdicionales.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos Adicionales", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 11))); // NOI18N
        PanelDatosAdicionales.setLayout(new java.awt.GridBagLayout());

        btnAgregarDatosAdicionales.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/pequenos/add2.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        PanelDatosAdicionales.add(btnAgregarDatosAdicionales, gridBagConstraints);

        tblDatosAdicionales.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane5.setViewportView(tblDatosAdicionales);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        PanelDatosAdicionales.add(jScrollPane5, gridBagConstraints);

        btnQuitarDatosAdicionales.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/pequenos/cerrar-ico.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        PanelDatosAdicionales.add(btnQuitarDatosAdicionales, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(1, 2, 1, 2);
        getContentPane().add(PanelDatosAdicionales, gridBagConstraints);

        PanelDatosAdicionales1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Retenciones", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 11))); // NOI18N
        PanelDatosAdicionales1.setEnabled(false);
        PanelDatosAdicionales1.setLayout(new java.awt.GridBagLayout());

        cmbRetencionIva.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        PanelDatosAdicionales1.add(cmbRetencionIva, gridBagConstraints);

        jLabel22.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel22.setText("IVA:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        PanelDatosAdicionales1.add(jLabel22, gridBagConstraints);

        jLabel19.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel19.setText("RENTA:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        PanelDatosAdicionales1.add(jLabel19, gridBagConstraints);

        cmbRetencionRenta.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        PanelDatosAdicionales1.add(cmbRetencionRenta, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        getContentPane().add(PanelDatosAdicionales1, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblRetencionTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblRetencionTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblRetencionTotalActionPerformed

    private void btnBuscarFacturaCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarFacturaCompraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBuscarFacturaCompraActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelDatosAdicionales;
    private javax.swing.JPanel PanelDatosAdicionales1;
    private javax.swing.JPanel PanelDatosNotaCredito;
    private javax.swing.JPanel PanelValores;
    private javax.swing.JButton btnAgregarDatosAdicionales;
    private javax.swing.JButton btnBuscarFacturaCompra;
    private javax.swing.JButton btnQuitarDatosAdicionales;
    private javax.swing.JComboBox<SriRetencionIva> cmbRetencionIva;
    private javax.swing.JComboBox<SriRetencionRenta> cmbRetencionRenta;
    private com.toedter.calendar.JDateChooser jDateFechaEmision;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
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
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lblDireccion;
    private javax.swing.JLabel lblDireccionCliente;
    private javax.swing.JLabel lblEspacio1;
    private javax.swing.JLabel lblEspacio2;
    private javax.swing.JLabel lblEspacio3;
    private javax.swing.JLabel lblEspacio4;
    private javax.swing.JLabel lblNombreCliente;
    private javax.swing.JLabel lblNombreComercial;
    private javax.swing.JTextField lblRetencionTotal;
    private javax.swing.JLabel lblRuc;
    private javax.swing.JLabel lblSecuencial;
    private javax.swing.JLabel lblSubtotalRetencionIva;
    private javax.swing.JLabel lblSubtotalRetencionRenta;
    private javax.swing.JLabel lblTelefonoCliente;
    private javax.swing.JLabel lblTelefonos;
    private javax.swing.JLabel lblValorTotal;
    private javax.swing.JPanel panelDatosGenerales;
    private javax.swing.JTable tblDatosAdicionales;
    private javax.swing.JTable tblDetalleRetenciones;
    private javax.swing.JTextField txtReferenciaFactura;
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


    public JLabel getLblSecuencial() {
        return lblSecuencial;
    }


    public JTextField getTxtValorTotal() {
        return lblRetencionTotal;
    }


    public JDateChooser getjDateFechaEmision() {
        return jDateFechaEmision;
    }

    public void setjDateFechaEmision(JDateChooser jDateFechaEmision) {
        this.jDateFechaEmision = jDateFechaEmision;
    }

    public JTable getTblDatosAdicionales() {
        return tblDatosAdicionales;
    }

    public void setTblDatosAdicionales(JTable tblDatosAdicionales) {
        this.tblDatosAdicionales = tblDatosAdicionales;
    }

    public JButton getBtnAgregarDatosAdicionales() {
        return btnAgregarDatosAdicionales;
    }

    public void setBtnAgregarDatosAdicionales(JButton btnAgregarDatosAdicionales) {
        this.btnAgregarDatosAdicionales = btnAgregarDatosAdicionales;
    }

    public JButton getBtnQuitarDatosAdicionales() {
        return btnQuitarDatosAdicionales;
    }

    public void setBtnQuitarDatosAdicionales(JButton btnQuitarDatosAdicionales) {
        this.btnQuitarDatosAdicionales = btnQuitarDatosAdicionales;
    }

    public JButton getBtnBuscarFacturaCompra() {
        return btnBuscarFacturaCompra;
    }

    public void setBtnBuscarFacturaCompra(JButton btnBuscarFacturaCompra) {
        this.btnBuscarFacturaCompra = btnBuscarFacturaCompra;
    }

    public JComboBox<SriRetencionIva> getCmbRetencionIva() {
        return cmbRetencionIva;
    }

    public void setCmbRetencionIva(JComboBox<SriRetencionIva> cmbRetencionIva) {
        this.cmbRetencionIva = cmbRetencionIva;
    }

    public JComboBox<SriRetencionRenta> getCmbRetencionRenta() {
        return cmbRetencionRenta;
    }

    public void setCmbRetencionRenta(JComboBox<SriRetencionRenta> cmbRetencionRenta) {
        this.cmbRetencionRenta = cmbRetencionRenta;
    }

    public JTable getTblDetalleRetenciones() {
        return tblDetalleRetenciones;
    }

    public void setTblDetalleRetenciones(JTable tblDetalleRetenciones) {
        this.tblDetalleRetenciones = tblDetalleRetenciones;
    }

    public JTextField getLblRetencionTotal() {
        return lblRetencionTotal;
    }

    public JLabel getLblSubtotalRetencionIva() {
        return lblSubtotalRetencionIva;
    }

    public JLabel getLblSubtotalRetencionRenta() {
        return lblSubtotalRetencionRenta;
    }
    
    
    
    
    

}
