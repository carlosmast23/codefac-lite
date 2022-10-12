/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servicios.panel;

import com.toedter.calendar.JDateChooser;
import ec.com.codesoft.codefaclite.controlador.aplicacion.ControladorCodefacInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.util.LimpiarAnotacion;
import ec.com.codesoft.codefaclite.corecodefaclite.validation.ValidacionCodefacAnotacion;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Departamento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empleado;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ObjetoMantenimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.PrioridadEnumEstado;
import ec.com.codesoft.codefaclite.utilidades.validadores.ExpresionRegular;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Carlos
 */
public abstract class OrdenTrabajoPanel extends ControladorCodefacInterface{

    /**
     * Creates new form OrdenTrabajoPanel
     */
    public OrdenTrabajoPanel() {
        initComponents();
        txtDescripcion.setVisible(false);
        jLabel3.setVisible(false);      
        
    }

    public JButton getBtnActualizarDetalle() {
        return btnActualizarDetalle;
    }

    public void setBtnActualizarDetalle(JButton btnActualizarDetalle) {
        this.btnActualizarDetalle = btnActualizarDetalle;
    }

    public JButton getBtnAgregarDetalle() {
        return btnAgregarDetalle;
    }

    public void setBtnAgregarDetalle(JButton btnAgregarDetalle) {
        this.btnAgregarDetalle = btnAgregarDetalle;
    }

    public JButton getBtnCliente() {
        return btnCliente;
    }

    public void setBtnCliente(JButton btnCliente) {
        this.btnCliente = btnCliente;
    }

    public JButton getBtnEliminarDetalle() {
        return btnEliminarDetalle;
    }

    public void setBtnEliminarDetalle(JButton btnEliminarDetalle) {
        this.btnEliminarDetalle = btnEliminarDetalle;
    }

    public JComboBox<Empleado> getCmbAsignadoADetalle() {
        return cmbAsignadoADetalle;
    }

    public void setCmbAsignadoADetalle(JComboBox<Empleado> cmbAsignadoADetalle) {
        this.cmbAsignadoADetalle = cmbAsignadoADetalle;
    }

    public JDateChooser getCmbDateFechaIngreso() {
        return cmbDateFechaIngreso;
    }

    public void setCmbDateFechaIngreso(JDateChooser cmbDateFechaIngreso) {
        this.cmbDateFechaIngreso = cmbDateFechaIngreso;
    }

    public JComboBox<OrdenTrabajoDetalle.EstadoEnum> getCmbEstadoDetalle() {
        return cmbEstadoDetalle;
    }

    public void setCmbEstadoDetalle(JComboBox<OrdenTrabajoDetalle.EstadoEnum> cmbEstadoDetalle) {
        this.cmbEstadoDetalle = cmbEstadoDetalle;
    }

    public JComboBox<OrdenTrabajo.EstadoEnum> getCmbEstadoOrdenTrabajo() {
        return cmbEstadoOrdenTrabajo;
    }

    public JComboBox<OrdenTrabajo.EstadoEnum> getCmbEstadoDetallesOrdenTrabajo() {
        return cmbEstadoDetallesOrdenTrabajo;
    }

    public void setCmbEstadoDetallesOrdenTrabajo(JComboBox<OrdenTrabajo.EstadoEnum> cmbEstadoDetallesOrdenTrabajo) {
        this.cmbEstadoDetallesOrdenTrabajo = cmbEstadoDetallesOrdenTrabajo;
    }
    
    public JComboBox<PrioridadEnumEstado> getCmbPrioridadDetalle() {
        return cmbPrioridadDetalle;
    }

    public void setCmbPrioridadDetalle(JComboBox<PrioridadEnumEstado> cmbPrioridadDetalle) {
        this.cmbPrioridadDetalle = cmbPrioridadDetalle;
    }

    public JComboBox<Departamento> getCmbTipoOrdenDetalle() {
        return cmbTipoOrdenDetalle;
    }

    public void setCmbTipoOrdenDetalle(JComboBox<Departamento> cmbTipoOrdenDetalle) {
        this.cmbTipoOrdenDetalle = cmbTipoOrdenDetalle;
    }

    public JTable getTableDetallesOrdenTrabajo() {
        return tableDetallesOrdenTrabajo;
    }

    public void setTableDetallesOrdenTrabajo(JTable tableDetallesOrdenTrabajo) {
        this.tableDetallesOrdenTrabajo = tableDetallesOrdenTrabajo;
    }
    
    @LimpiarAnotacion
    @ValidacionCodefacAnotacion(requerido=true, nombre = "Descripción detalle Orden Trabajo", grupo = "detalles")
    public JTextArea getTxtAreaDescripcion() {
        return txtAreaDescripcion;
    }

    public void setTxtAreaDescripcion(JTextArea txtAreaDescripcion) {
        this.txtAreaDescripcion = txtAreaDescripcion;
    }
    
    @LimpiarAnotacion
    @ValidacionCodefacAnotacion(requerido=false ,nombre = "Nota detalle Orden Trabajo", grupo = "detalles")
    public JTextArea getTxtAreaNotas() {
        return txtAreaNotas;
    }

    public void setTxtAreaNotas(JTextArea txtAreaNotas) {
        this.txtAreaNotas = txtAreaNotas;
    }

    public JTextField getTxtCliente() {
        return txtCliente;
    }

    public void setTxtCliente(JTextField txtCliente) {
        this.txtCliente = txtCliente;
    }

    public JLabel getLblCodigo() {
        return lblCodigo;
    }

    public void setLblCodigo(JLabel lblCodigo) {
        this.lblCodigo = lblCodigo;
    }
    
    
    
    @LimpiarAnotacion
    @ValidacionCodefacAnotacion(requerido = false, nombre = "Descripción Orden Trabajo")
    public JTextField getTxtDescripcion() {
        return txtDescripcion;
    }

    public void setTxtDescripcion(JTextField txtDescripcion) {
        this.txtDescripcion = txtDescripcion;
    }

    public JLabel getLblTotalDetalles() {
        return lblTotalDetalles;
    }

    public void setLblTotalDetalles(JLabel lblTotalDetalles) {
        this.lblTotalDetalles = lblTotalDetalles;
    }

    public JDateChooser getCmbDateFechaEntrega() {
        return cmbDateFechaEntrega;
    }

    public void setCmbDateFechaEntrega(JDateChooser cmbDateFechaEntrega) {
        this.cmbDateFechaEntrega = cmbDateFechaEntrega;
    }
    
    @LimpiarAnotacion
    @ValidacionCodefacAnotacion(requerido = true, nombre = "Título detalle Orden Trabajo", grupo = "detalles")
    public JTextField getTxtCategoria() {
        return txtCategoria;
    }

    public void setTxtCategoria(JTextField txtCategoria) {
        this.txtCategoria = txtCategoria;
    }

    public JLabel getLblNombreLegal() {
        return lblNombreLegal;
    }

    public void setLblNombreLegal(JLabel lblNombreLegal) {
        this.lblNombreLegal = lblNombreLegal;
    }

    public JLabel getLblRazonSocial() {
        return lblRazonSocial;
    }

    public void setLblRazonSocial(JLabel lblRazonSocial) {
        this.lblRazonSocial = lblRazonSocial;
    }
    
    public JComboBox<ObjetoMantenimiento> getCmbObjetoMantenimiento() {
        return cmbObjetoMantenimiento;
    }

    public void setCmbObjetoMantenimiento(JComboBox<ObjetoMantenimiento> cmbObjetoMantenimiento) {
        this.cmbObjetoMantenimiento = cmbObjetoMantenimiento;
    }

    public JButton getBtnAgregarObjecto() {
        return btnAgregarObjecto;
    }

    public void setBtnAgregarObjecto(JButton btnAgregarObjecto) {
        this.btnAgregarObjecto = btnAgregarObjecto;
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
        jLabel2 = new javax.swing.JLabel();
        cmbEstadoOrdenTrabajo = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        txtCliente = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnCliente = new javax.swing.JButton();
        txtDescripcion = new javax.swing.JTextField();
        cmbDateFechaIngreso = new com.toedter.calendar.JDateChooser();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableDetallesOrdenTrabajo = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        cmbTipoOrdenDetalle = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtAreaDescripcion = new javax.swing.JTextArea();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtAreaNotas = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        cmbAsignadoADetalle = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        cmbEstadoDetalle = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        cmbPrioridadDetalle = new javax.swing.JComboBox<>();
        jToolBar1 = new javax.swing.JToolBar();
        btnAgregarDetalle = new javax.swing.JButton();
        btnActualizarDetalle = new javax.swing.JButton();
        btnEliminarDetalle = new javax.swing.JButton();
        cmbDateFechaEntrega = new com.toedter.calendar.JDateChooser();
        txtCategoria = new javax.swing.JTextField();
        lblEspacio1 = new javax.swing.JLabel();
        lblEspacio2 = new javax.swing.JLabel();
        lblTotalDetalles = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblCodigo = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        lblRazonSocial = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        lblNombreLegal = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        cmbEstadoDetallesOrdenTrabajo = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        cmbObjetoMantenimiento = new javax.swing.JComboBox<>();
        btnAgregarObjecto = new javax.swing.JButton();
        btnBuscarUltimoMantenimiento = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Orden de Trabajo");
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel1.setText("Código:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jLabel1, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel2.setText("Objecto:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jLabel2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.05;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(cmbEstadoOrdenTrabajo, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel3.setText("Descripción:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jLabel3, gridBagConstraints);

        txtCliente.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(txtCliente, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel5.setText("Estado:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jLabel5, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel6.setText("Fecha Ingreso:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jLabel6, gridBagConstraints);

        btnCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/persona-ico.png"))); // NOI18N
        btnCliente.setToolTipText("Agregar Cliente");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(btnCliente, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(txtDescripcion, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(cmbDateFechaIngreso, gridBagConstraints);

        tableDetallesOrdenTrabajo.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tableDetallesOrdenTrabajo);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jScrollPane2, gridBagConstraints);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Detalles"));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel9.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel9.setText("Asignado A:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel9, gridBagConstraints);

        jLabel10.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel10.setText("Fecha Entrega:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel10, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(cmbTipoOrdenDetalle, gridBagConstraints);

        jLabel11.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel11.setText("Descripción:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel11, gridBagConstraints);

        txtAreaDescripcion.setColumns(20);
        txtAreaDescripcion.setRows(5);
        jScrollPane3.setViewportView(txtAreaDescripcion);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jScrollPane3, gridBagConstraints);

        jLabel12.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel12.setText("Notas:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel12, gridBagConstraints);

        txtAreaNotas.setColumns(20);
        txtAreaNotas.setRows(5);
        jScrollPane4.setViewportView(txtAreaNotas);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jScrollPane4, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel4.setText("Departamento:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel4, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(cmbAsignadoADetalle, gridBagConstraints);

        jLabel13.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel13.setText("Estado:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel13, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(cmbEstadoDetalle, gridBagConstraints);

        jLabel8.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel8.setText("Prioridad:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel8, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(cmbPrioridadDetalle, gridBagConstraints);

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btnAgregarDetalle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/nuevo-icono.png"))); // NOI18N
        btnAgregarDetalle.setFocusable(false);
        btnAgregarDetalle.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAgregarDetalle.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnAgregarDetalle);

        btnActualizarDetalle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/edit-icon.png"))); // NOI18N
        btnActualizarDetalle.setFocusable(false);
        btnActualizarDetalle.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnActualizarDetalle.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnActualizarDetalle);

        btnEliminarDetalle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/delete-icon.png"))); // NOI18N
        btnEliminarDetalle.setFocusable(false);
        btnEliminarDetalle.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEliminarDetalle.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnEliminarDetalle);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jPanel1.add(jToolBar1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        jPanel1.add(cmbDateFechaEntrega, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 0.1;
        jPanel1.add(txtCategoria, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 5, 5, 5);
        getContentPane().add(jPanel1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.4;
        getContentPane().add(lblEspacio1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 0.2;
        getContentPane().add(lblEspacio2, gridBagConstraints);

        lblTotalDetalles.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblTotalDetalles.setText("0.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 8;
        getContentPane().add(lblTotalDetalles, gridBagConstraints);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("TOTAL:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 8;
        getContentPane().add(jLabel7, gridBagConstraints);

        lblCodigo.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblCodigo.setText("jLabel14");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(lblCodigo, gridBagConstraints);

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel14.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel14.setText("Razón Social:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel14, gridBagConstraints);

        lblRazonSocial.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(lblRazonSocial, gridBagConstraints);

        jLabel16.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel16.setText("Nombre Legal:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel16, gridBagConstraints);

        lblNombreLegal.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(lblNombreLegal, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jPanel2, gridBagConstraints);

        jLabel15.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel15.setText("Detalles");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jLabel15, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.05;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(cmbEstadoDetallesOrdenTrabajo, gridBagConstraints);

        jLabel17.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel17.setText("Identificación:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jLabel17, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(cmbObjetoMantenimiento, gridBagConstraints);

        btnAgregarObjecto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/pequenos/mas-ico.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(btnAgregarObjecto, gridBagConstraints);

        btnBuscarUltimoMantenimiento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/pequenos/informe-ico.png"))); // NOI18N
        btnBuscarUltimoMantenimiento.setToolTipText("Último mantenimiento");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(btnBuscarUltimoMantenimiento, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizarDetalle;
    private javax.swing.JButton btnAgregarDetalle;
    private javax.swing.JButton btnAgregarObjecto;
    private javax.swing.JButton btnBuscarUltimoMantenimiento;
    private javax.swing.JButton btnCliente;
    private javax.swing.JButton btnEliminarDetalle;
    private javax.swing.JComboBox<Empleado> cmbAsignadoADetalle;
    private com.toedter.calendar.JDateChooser cmbDateFechaEntrega;
    private com.toedter.calendar.JDateChooser cmbDateFechaIngreso;
    private javax.swing.JComboBox<OrdenTrabajoDetalle.EstadoEnum> cmbEstadoDetalle;
    private javax.swing.JComboBox<OrdenTrabajo.EstadoEnum> cmbEstadoDetallesOrdenTrabajo;
    private javax.swing.JComboBox<OrdenTrabajo.EstadoEnum> cmbEstadoOrdenTrabajo;
    private javax.swing.JComboBox<ObjetoMantenimiento> cmbObjetoMantenimiento;
    private javax.swing.JComboBox<PrioridadEnumEstado> cmbPrioridadDetalle;
    private javax.swing.JComboBox<Departamento> cmbTipoOrdenDetalle;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
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
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblEspacio1;
    private javax.swing.JLabel lblEspacio2;
    private javax.swing.JLabel lblNombreLegal;
    private javax.swing.JLabel lblRazonSocial;
    private javax.swing.JLabel lblTotalDetalles;
    private javax.swing.JTable tableDetallesOrdenTrabajo;
    private javax.swing.JTextArea txtAreaDescripcion;
    private javax.swing.JTextArea txtAreaNotas;
    private javax.swing.JTextField txtCategoria;
    private javax.swing.JTextField txtCliente;
    private javax.swing.JTextField txtDescripcion;
    // End of variables declaration//GEN-END:variables

    public JButton getBtnBuscarUltimoMantenimiento() {
        return btnBuscarUltimoMantenimiento;
    }

    public void setBtnBuscarUltimoMantenimiento(JButton btnBuscarUltimoMantenimiento) {
        this.btnBuscarUltimoMantenimiento = btnBuscarUltimoMantenimiento;
    }

    


    
}
