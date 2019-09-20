/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.configuraciones.panel;

import ec.com.codesoft.codefaclite.controlador.aplicacion.ControladorCodefacInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencionIva;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencionRenta;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.FormatoHojaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Carlos
 */
public abstract class ConfiguracionDefectoPanel extends ControladorCodefacInterface {

    /**
     * Creates new form ConfiguracionDefectoPanel
     */
    public ConfiguracionDefectoPanel() {
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

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cmbTipoDocumento = new javax.swing.JComboBox<>();
        lblEspacio1 = new javax.swing.JLabel();
        lblEspacio2 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        cmbTipoDocumentoCompra = new javax.swing.JComboBox<>();
        lblEspacio3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtOrdenTrabajoReporte = new javax.swing.JTextArea();
        lblEspacioBlanco2 = new javax.swing.JLabel();
        lblEspacioBlanco = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cmbFormatoHojas = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        cmbActivarModuloCartera = new javax.swing.JComboBox<>();
        lblEspacio6 = new javax.swing.JLabel();
        lblEspacio4 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        lblEspacioVertical = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cmbActivarComprobanteVenta = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        cmbCargarProductoIvaFactura = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        chkImpresoraTickets = new javax.swing.JCheckBox();
        jLabel13 = new javax.swing.JLabel();
        txtVariableGeneralComprobantes = new javax.swing.JTextField();
        lblEspacioBlanco123 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        cmbEditarPrecioUnitFactura = new javax.swing.JComboBox<>();
        cmbEditarDescripcionFactura = new javax.swing.JComboBox<>();
        cmbEditarDescuentoFactura = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        cmbActivarNotaVenta = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        lblEspacioVertical1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cmbActivarReporteSimpleGuiaRemision = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        txtMotivoTrasladoGuiaRemision = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        cmbRetencionIva = new javax.swing.JComboBox<>();
        lblEspacio42 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        cmbRetencionRenta = new javax.swing.JComboBox<>();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtCodigoHtml = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        jEditorPanelVistaPrevia = new javax.swing.JEditorPane();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Configuraciones por Defecto");

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel1.setText("Tipo Documento Defecto Factura:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(cmbTipoDocumento, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        jPanel1.add(lblEspacio1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblEspacio2, gridBagConstraints);

        jLabel12.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel12.setText("Tipo Documento:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel12, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(cmbTipoDocumentoCompra, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weighty = 0.1;
        jPanel1.add(lblEspacio3, gridBagConstraints);

        jTabbedPane1.addTab("Documentos", jPanel1);

        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel2.setText("Detalles Reporte Ordenes de Trabajo:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel2.add(jLabel2, gridBagConstraints);

        txtOrdenTrabajoReporte.setColumns(20);
        txtOrdenTrabajoReporte.setRows(5);
        jScrollPane1.setViewportView(txtOrdenTrabajoReporte);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel2.add(jScrollPane1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weighty = 0.1;
        jPanel2.add(lblEspacioBlanco2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        jPanel2.add(lblEspacioBlanco, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel3.setText("Formato Reportes:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel2.add(jLabel3, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel2.add(cmbFormatoHojas, gridBagConstraints);

        jTabbedPane1.addTab("Ordenes Trabajo", jPanel2);

        jPanel3.setLayout(new java.awt.GridBagLayout());

        jLabel4.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel4.setText("Activar Módulo:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(jLabel4, gridBagConstraints);

        cmbActivarModuloCartera.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(cmbActivarModuloCartera, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weighty = 0.1;
        jPanel3.add(lblEspacio6, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        jPanel3.add(lblEspacio4, gridBagConstraints);

        jTabbedPane1.addTab("Cartera", jPanel3);

        jPanel4.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        jPanel4.add(lblEspacioVertical, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel5.setText("Cargar forma de cargar productos con iva:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(jLabel5, gridBagConstraints);

        cmbActivarComprobanteVenta.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(cmbActivarComprobanteVenta, gridBagConstraints);

        jLabel7.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel7.setText("Variable General Comprobantes:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(jLabel7, gridBagConstraints);

        cmbCargarProductoIvaFactura.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(cmbCargarProductoIvaFactura, gridBagConstraints);

        jLabel11.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel11.setText("Editar Precio Unitario:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(jLabel11, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        jPanel4.add(chkImpresoraTickets, gridBagConstraints);

        jLabel13.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel13.setText("Impresora con tickets:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(jLabel13, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtVariableGeneralComprobantes, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 0.1;
        jPanel4.add(lblEspacioBlanco123, gridBagConstraints);

        jLabel16.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel16.setText("Editar Descripción:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(jLabel16, gridBagConstraints);

        jLabel17.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel17.setText("Editar Descuento:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(jLabel17, gridBagConstraints);

        jLabel18.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel18.setText("Activar Documento Nota de  Venta:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(jLabel18, gridBagConstraints);

        cmbEditarPrecioUnitFactura.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(cmbEditarPrecioUnitFactura, gridBagConstraints);

        cmbEditarDescripcionFactura.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(cmbEditarDescripcionFactura, gridBagConstraints);

        cmbEditarDescuentoFactura.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(cmbEditarDescuentoFactura, gridBagConstraints);

        jLabel19.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel19.setText("Activar Comprobante Venta:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(jLabel19, gridBagConstraints);

        cmbActivarNotaVenta.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(cmbActivarNotaVenta, gridBagConstraints);

        jTabbedPane1.addTab("Facturas", jPanel4);

        jPanel5.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        jPanel5.add(lblEspacioVertical1, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel6.setText("Motivo Traslado Por Defecto:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jLabel6, gridBagConstraints);

        cmbActivarReporteSimpleGuiaRemision.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(cmbActivarReporteSimpleGuiaRemision, gridBagConstraints);

        jLabel8.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel8.setText("Reporte Simplificado Guia Retención:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jLabel8, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 300;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(txtMotivoTrasladoGuiaRemision, gridBagConstraints);

        jTabbedPane1.addTab("Transporte", jPanel5);

        jPanel6.setLayout(new java.awt.GridBagLayout());

        jLabel9.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel9.setText("Valor por defecto retención renta:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(jLabel9, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(cmbRetencionIva, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        jPanel6.add(lblEspacio42, gridBagConstraints);

        jLabel10.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel10.setText("Valor por defecto retención iva:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(jLabel10, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(cmbRetencionRenta, gridBagConstraints);

        jTabbedPane1.addTab("Retenciones", jPanel6);

        jPanel7.setLayout(new java.awt.GridBagLayout());

        txtCodigoHtml.setColumns(20);
        txtCodigoHtml.setRows(5);
        jScrollPane2.setViewportView(txtCodigoHtml);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.1;
        jPanel7.add(jScrollPane2, gridBagConstraints);

        jScrollPane3.setViewportView(jEditorPanelVistaPrevia);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.1;
        jPanel7.add(jScrollPane3, gridBagConstraints);

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setText("Código HTML");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel7.add(jLabel14, gridBagConstraints);

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setText("Vista Previa");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel7.add(jLabel15, gridBagConstraints);

        jTabbedPane1.addTab("Comprobantes Electrónicos", jPanel7);

        getContentPane().add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox chkImpresoraTickets;
    private javax.swing.JComboBox<EnumSiNo> cmbActivarComprobanteVenta;
    private javax.swing.JComboBox<EnumSiNo> cmbActivarModuloCartera;
    private javax.swing.JComboBox<EnumSiNo> cmbActivarNotaVenta;
    private javax.swing.JComboBox<EnumSiNo> cmbActivarReporteSimpleGuiaRemision;
    private javax.swing.JComboBox<EnumSiNo> cmbCargarProductoIvaFactura;
    private javax.swing.JComboBox<EnumSiNo> cmbEditarDescripcionFactura;
    private javax.swing.JComboBox<EnumSiNo> cmbEditarDescuentoFactura;
    private javax.swing.JComboBox<EnumSiNo> cmbEditarPrecioUnitFactura;
    private javax.swing.JComboBox<FormatoHojaEnum> cmbFormatoHojas;
    private javax.swing.JComboBox<SriRetencionIva> cmbRetencionIva;
    private javax.swing.JComboBox<SriRetencionRenta> cmbRetencionRenta;
    private javax.swing.JComboBox<TipoDocumentoEnum> cmbTipoDocumento;
    private javax.swing.JComboBox<TipoDocumentoEnum> cmbTipoDocumentoCompra;
    private javax.swing.JEditorPane jEditorPanelVistaPrevia;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblEspacio1;
    private javax.swing.JLabel lblEspacio2;
    private javax.swing.JLabel lblEspacio3;
    private javax.swing.JLabel lblEspacio4;
    private javax.swing.JLabel lblEspacio42;
    private javax.swing.JLabel lblEspacio6;
    private javax.swing.JLabel lblEspacioBlanco;
    private javax.swing.JLabel lblEspacioBlanco123;
    private javax.swing.JLabel lblEspacioBlanco2;
    private javax.swing.JLabel lblEspacioVertical;
    private javax.swing.JLabel lblEspacioVertical1;
    private javax.swing.JTextArea txtCodigoHtml;
    private javax.swing.JTextField txtMotivoTrasladoGuiaRemision;
    private javax.swing.JTextArea txtOrdenTrabajoReporte;
    private javax.swing.JTextField txtVariableGeneralComprobantes;
    // End of variables declaration//GEN-END:variables

    public JComboBox<TipoDocumentoEnum> getCmbTipoDocumento() {
        return cmbTipoDocumento;
    }

    public void setCmbTipoDocumento(JComboBox<TipoDocumentoEnum> cmbTipoDocumento) {
        this.cmbTipoDocumento = cmbTipoDocumento;
    }

    public JComboBox<TipoDocumentoEnum> getCmbTipoDocumentoCompra() {
        return cmbTipoDocumentoCompra;
    }

    public void setCmbTipoDocumentoCompra(JComboBox<TipoDocumentoEnum> cmbTipoDocumentoCompra) {
        this.cmbTipoDocumentoCompra = cmbTipoDocumentoCompra;
    }

    public JTextArea getTxtOrdenTrabajoReporte() {
        return txtOrdenTrabajoReporte;
    }

    public void setTxtOrdenTrabajoReporte(JTextArea txtOrdenTrabajoReporte) {
        this.txtOrdenTrabajoReporte = txtOrdenTrabajoReporte;
    }

    public JComboBox<FormatoHojaEnum> getCmbFormatoHojas() {
        return cmbFormatoHojas;
    }

    public void setCmbFormatoHojas(JComboBox<FormatoHojaEnum> cmbFormatoHojas) {
        this.cmbFormatoHojas = cmbFormatoHojas;
    }

    public JComboBox<EnumSiNo> getCmbActivarModuloCartera() {
        return cmbActivarModuloCartera;
    }

    public void setCmbActivarModuloCartera(JComboBox<EnumSiNo> cmbActivarModuloCartera) {
        this.cmbActivarModuloCartera = cmbActivarModuloCartera;
    }

    public JComboBox<EnumSiNo> getCmbActivarComprobanteVenta() {
        return cmbActivarComprobanteVenta;
    }

    public void setCmbActivarComprobanteVenta(JComboBox<EnumSiNo> cmbActivarComprobanteVenta) {
        this.cmbActivarComprobanteVenta = cmbActivarComprobanteVenta;
    }

    public JComboBox<EnumSiNo> getCmbActivarReporteSimpleGuiaRemision() {
        return cmbActivarReporteSimpleGuiaRemision;
    }

    public void setCmbActivarReporteSimpleGuiaRemision(JComboBox<EnumSiNo> cmbActivarReporteSimpleGuiaRemision) {
        this.cmbActivarReporteSimpleGuiaRemision = cmbActivarReporteSimpleGuiaRemision;
    }

    public JComboBox<EnumSiNo> getCmbCargarProductoIvaFactura() {
        return cmbCargarProductoIvaFactura;
    }

    public JTextField getTxtMotivoTrasladoGuiaRemision() {
        return txtMotivoTrasladoGuiaRemision;
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

    public JCheckBox getChkImpresoraTickets() {
        return chkImpresoraTickets;
    }

    public JTextField getTxtVariableGeneralComprobantes() {
        return txtVariableGeneralComprobantes;
    }

    public JEditorPane getjEditorPanelVistaPrevia() {
        return jEditorPanelVistaPrevia;
    }

    public JTextArea getTxtCodigoHtml() {
        return txtCodigoHtml;
    }

    public JComboBox<EnumSiNo> getCmbEditarDescripcionFactura() {
        return cmbEditarDescripcionFactura;
    }

    public JComboBox<EnumSiNo> getCmbEditarDescuentoFactura() {
        return cmbEditarDescuentoFactura;
    }

    public JComboBox<EnumSiNo> getCmbEditarPrecioUnitFactura() {
        return cmbEditarPrecioUnitFactura;
    }

    public JComboBox<EnumSiNo> getCmbActivarNotaVenta() {
        return cmbActivarNotaVenta;
    }

    public void setCmbActivarNotaVenta(JComboBox<EnumSiNo> cmbActivarNotaVenta) {
        this.cmbActivarNotaVenta = cmbActivarNotaVenta;
    }
    
    
    
    
    
    
}
