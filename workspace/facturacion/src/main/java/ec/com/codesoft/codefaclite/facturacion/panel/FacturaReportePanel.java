/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.panel;

import com.toedter.calendar.JDateChooser;
import ec.com.codesoft.codefaclite.controlador.aplicacion.ControladorCodefacInterface;
import ec.com.codesoft.codefaclite.controlador.comprobante.reporte.DocumentosConsultarEnum;
import ec.com.codesoft.codefaclite.corecodefaclite.validation.ValidacionCodefacAnotacion;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author CodesoftDesarrollo
 */
public abstract class FacturaReportePanel extends ControladorCodefacInterface {

    /**
     * Creates new form FacturaReportePanel
     */
    public FacturaReportePanel() {
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

        lblCliente = new javax.swing.JLabel();
        txtCliente = new javax.swing.JTextField();
        btnBuscarCliente = new javax.swing.JButton();
        dateFechaInicio = new com.toedter.calendar.JDateChooser();
        lblFechaInicio = new javax.swing.JLabel();
        lblFechaFin = new javax.swing.JLabel();
        dateFechaFin = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDocumentos = new javax.swing.JTable();
        btnBuscar = new javax.swing.JButton();
        chkTodos = new javax.swing.JCheckBox();
        btnLimpiarFechaInicio = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        btnLimpiarFechaFin = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        PanelValores = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        lblValorTotal = new javax.swing.JLabel();
        lblSubtotal12 = new javax.swing.JLabel();
        lblSubtotal0 = new javax.swing.JLabel();
        lblSubtotalSinImpuesto = new javax.swing.JLabel();
        lblTotalDescuento = new javax.swing.JLabel();
        lblIva12 = new javax.swing.JLabel();
        txtValorTotal = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cmbEstado = new javax.swing.JComboBox<>();
        lblCliente1 = new javax.swing.JLabel();
        cmbDocumento = new javax.swing.JComboBox<>();
        panelOpciones = new javax.swing.JPanel();
        chkAfectaNotaDebito = new javax.swing.JCheckBox();
        chkAfectaNotaCredito = new javax.swing.JCheckBox();
        lblReferido = new javax.swing.JLabel();
        txtReferido = new javax.swing.JTextField();
        btnBuscarReferido = new javax.swing.JButton();
        chkTodosReferidos = new javax.swing.JCheckBox();
        chkReporteAgrupadoReferido = new javax.swing.JCheckBox();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Reporte Facturas");
        getContentPane().setLayout(new java.awt.GridBagLayout());

        lblCliente.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblCliente.setText("Cliente:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(lblCliente, gridBagConstraints);

        txtCliente.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCliente.setText("...");
        txtCliente.setEnabled(false);
        txtCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtClienteActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(txtCliente, gridBagConstraints);

        btnBuscarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/find2-ico.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(btnBuscarCliente, gridBagConstraints);

        dateFechaInicio.setDateFormatString("yyyy-MM-dd");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(dateFechaInicio, gridBagConstraints);

        lblFechaInicio.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblFechaInicio.setText("Fecha inicial:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(lblFechaInicio, gridBagConstraints);

        lblFechaFin.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblFechaFin.setText("Fecha final:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(lblFechaFin, gridBagConstraints);

        dateFechaFin.setDateFormatString("yyyy-MM-dd");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(dateFechaFin, gridBagConstraints);

        jScrollPane1.setViewportView(tblDocumentos);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(jScrollPane1, gridBagConstraints);

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/find2-ico.png"))); // NOI18N
        btnBuscar.setText("Consultar");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(btnBuscar, gridBagConstraints);

        chkTodos.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        chkTodos.setText("Todos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(chkTodos, gridBagConstraints);

        btnLimpiarFechaInicio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/pequenos/clear.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(btnLimpiarFechaInicio, gridBagConstraints);

        jLabel1.setText("           ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        getContentPane().add(jLabel1, gridBagConstraints);

        jLabel12.setText("           ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 6;
        getContentPane().add(jLabel12, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 15;
        gridBagConstraints.gridy = 9;
        getContentPane().add(jLabel21, gridBagConstraints);

        btnLimpiarFechaFin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/pequenos/clear.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(btnLimpiarFechaFin, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        getContentPane().add(jLabel6, gridBagConstraints);

        PanelValores.setLayout(new java.awt.GridBagLayout());

        jLabel9.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel9.setText("SUBTOTAL:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 2, 5);
        PanelValores.add(jLabel9, gridBagConstraints);

        jLabel10.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel10.setText("SUBTOTAL 12%:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 2, 5);
        PanelValores.add(jLabel10, gridBagConstraints);

        jLabel11.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel11.setText("SUBTOTAL 0%:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 2, 5);
        PanelValores.add(jLabel11, gridBagConstraints);

        jLabel17.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel17.setText("TOTAL DESCUENTO:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 2, 5);
        PanelValores.add(jLabel17, gridBagConstraints);

        jLabel19.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel19.setText("IVA 12%:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 2, 5);
        PanelValores.add(jLabel19, gridBagConstraints);

        lblValorTotal.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblValorTotal.setText("VALOR TOTAL:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 2, 5);
        PanelValores.add(lblValorTotal, gridBagConstraints);

        lblSubtotal12.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblSubtotal12.setText("0.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 2, 5);
        PanelValores.add(lblSubtotal12, gridBagConstraints);

        lblSubtotal0.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblSubtotal0.setText("0.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 2, 5);
        PanelValores.add(lblSubtotal0, gridBagConstraints);

        lblSubtotalSinImpuesto.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblSubtotalSinImpuesto.setText("0.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 2, 5);
        PanelValores.add(lblSubtotalSinImpuesto, gridBagConstraints);

        lblTotalDescuento.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblTotalDescuento.setText("0.00");
        lblTotalDescuento.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 51, 255), null));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 2, 5);
        PanelValores.add(lblTotalDescuento, gridBagConstraints);

        lblIva12.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblIva12.setText("0.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 2, 5);
        PanelValores.add(lblIva12, gridBagConstraints);

        txtValorTotal.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtValorTotal.setText("0.00");
        txtValorTotal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 255)));
        txtValorTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtValorTotalActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 2, 5);
        PanelValores.add(txtValorTotal, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.001;
        PanelValores.add(jLabel2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(PanelValores, gridBagConstraints);

        jLabel8.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel8.setText("Estado:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jLabel8, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(cmbEstado, gridBagConstraints);

        lblCliente1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblCliente1.setText("Documento:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(lblCliente1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(cmbDocumento, gridBagConstraints);

        panelOpciones.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Opciones Venta", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 12))); // NOI18N
        panelOpciones.setLayout(new java.awt.GridBagLayout());

        chkAfectaNotaDebito.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        chkAfectaNotaDebito.setText("Afectar Notas de Debito");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        panelOpciones.add(chkAfectaNotaDebito, gridBagConstraints);

        chkAfectaNotaCredito.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        chkAfectaNotaCredito.setText("Afectar Notas de Crédito");
        panelOpciones.add(chkAfectaNotaCredito, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(panelOpciones, gridBagConstraints);

        lblReferido.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblReferido.setText("Referido:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(lblReferido, gridBagConstraints);

        txtReferido.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtReferido.setText("...");
        txtReferido.setEnabled(false);
        txtReferido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtReferidoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(txtReferido, gridBagConstraints);

        btnBuscarReferido.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/find2-ico.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(btnBuscarReferido, gridBagConstraints);

        chkTodosReferidos.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        chkTodosReferidos.setText("Todos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(chkTodosReferidos, gridBagConstraints);

        chkReporteAgrupadoReferido.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        chkReporteAgrupadoReferido.setText("Reporte Agrupado Referido");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 4;
        getContentPane().add(chkReporteAgrupadoReferido, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtClienteActionPerformed

    private void txtValorTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtValorTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtValorTotalActionPerformed

    private void txtReferidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtReferidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtReferidoActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelValores;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnBuscarCliente;
    private javax.swing.JButton btnBuscarReferido;
    private javax.swing.JButton btnLimpiarFechaFin;
    private javax.swing.JButton btnLimpiarFechaInicio;
    private javax.swing.JCheckBox chkAfectaNotaCredito;
    private javax.swing.JCheckBox chkAfectaNotaDebito;
    private javax.swing.JCheckBox chkReporteAgrupadoReferido;
    private javax.swing.JCheckBox chkTodos;
    private javax.swing.JCheckBox chkTodosReferidos;
    private javax.swing.JComboBox<DocumentosConsultarEnum> cmbDocumento;
    private javax.swing.JComboBox<ComprobanteEntity.ComprobanteEnumEstado> cmbEstado;
    private com.toedter.calendar.JDateChooser dateFechaFin;
    private com.toedter.calendar.JDateChooser dateFechaInicio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCliente;
    private javax.swing.JLabel lblCliente1;
    private javax.swing.JLabel lblFechaFin;
    private javax.swing.JLabel lblFechaInicio;
    private javax.swing.JLabel lblIva12;
    private javax.swing.JLabel lblReferido;
    private javax.swing.JLabel lblSubtotal0;
    private javax.swing.JLabel lblSubtotal12;
    private javax.swing.JLabel lblSubtotalSinImpuesto;
    private javax.swing.JLabel lblTotalDescuento;
    private javax.swing.JLabel lblValorTotal;
    private javax.swing.JPanel panelOpciones;
    private javax.swing.JTable tblDocumentos;
    private javax.swing.JTextField txtCliente;
    private javax.swing.JTextField txtReferido;
    private javax.swing.JTextField txtValorTotal;
    // End of variables declaration//GEN-END:variables

    public JButton getBtnBuscarCliente() {
        return btnBuscarCliente;
    }

    public void setBtnBuscarCliente(JButton btnBuscarCliente) {
        this.btnBuscarCliente = btnBuscarCliente;
    }


    public JDateChooser getDateFechaFin() {
        return dateFechaFin;
    }

    public void setDateFechaFin(JDateChooser dateFechaFin) {
        this.dateFechaFin = dateFechaFin;
    }

    public JDateChooser getDateFechaInicio() {
        return dateFechaInicio;
    }

    public void setDateFechaInicio(JDateChooser dateFechaInicio) {
        this.dateFechaInicio = dateFechaInicio;
    }



    public JTable getTblDocumentos() {
        return tblDocumentos;
    }

    public void setTblDocumentos(JTable tblDocumentos) {
        this.tblDocumentos = tblDocumentos;
    }

    public JTextField getTxtCliente() {
        return txtCliente;
    }

    public void setTxtCliente(JTextField txtCliente) {
        this.txtCliente = txtCliente;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    public JCheckBox getChkTodos() {
        return chkTodos;
    }

    public void setChkTodos(JCheckBox chkTodos) {
        this.chkTodos = chkTodos;
    }

    public JButton getBtnLimpiarFecha() {
        return btnLimpiarFechaInicio;
    }

    public void setBtnLimpiarFecha(JButton btnLimpiarFecha) {
        this.btnLimpiarFechaInicio = btnLimpiarFecha;
    }

    public JButton getBtnLimpiarFechaFin() {
        return btnLimpiarFechaFin;
    }

    public void setBtnLimpiarFechaFin(JButton btnLimpiarFechaFin) {
        this.btnLimpiarFechaFin = btnLimpiarFechaFin;
    }

    public JButton getBtnLimpiarFechaInicio() {
        return btnLimpiarFechaInicio;
    }

    public void setBtnLimpiarFechaInicio(JButton btnLimpiarFechaInicio) {
        this.btnLimpiarFechaInicio = btnLimpiarFechaInicio;
    }

    public JLabel getLblIva12() {
        return lblIva12;
    }

    public JLabel getLblSubtotal0() {
        return lblSubtotal0;
    }

    public JLabel getLblSubtotal12() {
        return lblSubtotal12;
    }

    public JLabel getLblSubtotalSinImpuesto() {
        return lblSubtotalSinImpuesto;
    }

    public JLabel getLblTotalDescuento() {
        return lblTotalDescuento;
    }

    public JLabel getLblValorTotal() {
        return lblValorTotal;
    }

    public JTextField getTxtValorTotal() {
        return txtValorTotal;
    }

    public void setTxtValorTotal(JTextField txtValorTotal) {
        this.txtValorTotal = txtValorTotal;
    }

    public JComboBox<ComprobanteEntity.ComprobanteEnumEstado> getCmbEstado() {
        return cmbEstado;
    }

    public void setCmbEstado(JComboBox<ComprobanteEntity.ComprobanteEnumEstado> cmbEstado) {
        this.cmbEstado = cmbEstado;
    }

    public JComboBox<DocumentosConsultarEnum> getCmbDocumento() {
        return cmbDocumento;
    }

    public void setCmbDocumento(JComboBox<DocumentosConsultarEnum> cmbDocumento) {
        this.cmbDocumento = cmbDocumento;
    }

    public JPanel getPanelOpciones() {
        return panelOpciones;
    }

    public void setPanelOpciones(JPanel panelOpciones) {
        this.panelOpciones = panelOpciones;
    }

    public JCheckBox getChkAfectaNotaCredito() {
        return chkAfectaNotaCredito;
    }

    public JCheckBox getChkAfectaNotaDebito() {
        return chkAfectaNotaDebito;
    }

    public JButton getBtnBuscarReferido() {
        return btnBuscarReferido;
    }

    public JCheckBox getChkTodosReferidos() {
        return chkTodosReferidos;
    }

    public JTextField getTxtReferido() {
        return txtReferido;
    }

    public JLabel getLblReferido() {
        return lblReferido;
    }

    public JCheckBox getChkReporteAgrupadoReferido() {
        return chkReporteAgrupadoReferido;
    }

    
    
    
    
    

}
