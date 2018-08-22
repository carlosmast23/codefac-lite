/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.gestionacademica.panel;

import ec.com.codesoft.codefaclite.controlador.aplicacion.ControladorCodefacInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.NivelAcademico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroPlantillaMes;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubrosNivel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.JTable;

/**
 *
 * @author CodesoftDesarrollo
 */
public abstract class ReporteDeudasPanel extends ControladorCodefacInterface {

    /**
     * Creates new form ReporteDeudasEstudiantePanel
     */
    public ReporteDeudasPanel() {
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

        lblperiodo = new javax.swing.JLabel();
        cmbPeriodo = new javax.swing.JComboBox<>();
        cmbNivelAcademico = new javax.swing.JComboBox<>();
        lblBuscar = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDeudas = new javax.swing.JTable();
        chkTodosNiveles = new javax.swing.JCheckBox();
        chkTodosRubros = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        cmbTipoRubroPorMes = new javax.swing.JComboBox<>();
        lblEspacio2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        lstFiltrosMes = new javax.swing.JList<>();
        btnAgregarMes = new javax.swing.JButton();
        btnEliminarMes = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        cmbMesFiltro = new javax.swing.JComboBox<>();
        btnLimpiar = new javax.swing.JButton();
        lblEspacio = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Deudas por Curso");
        setPreferredSize(new java.awt.Dimension(900, 600));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        lblperiodo.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblperiodo.setText("Periodo:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(lblperiodo, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(cmbPeriodo, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(cmbNivelAcademico, gridBagConstraints);

        lblBuscar.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblBuscar.setText("Nivel:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(lblBuscar, gridBagConstraints);

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/find2-ico.png"))); // NOI18N
        btnBuscar.setText("Buscar");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(btnBuscar, gridBagConstraints);

        tblDeudas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Identificación", "Estudiante", "Nivel Academico", "Rubro", "Valor"
            }
        ));
        jScrollPane1.setViewportView(tblDeudas);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 10;
        gridBagConstraints.gridheight = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(20, 15, 20, 15);
        getContentPane().add(jScrollPane1, gridBagConstraints);

        chkTodosNiveles.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        chkTodosNiveles.setText("Todos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(chkTodosNiveles, gridBagConstraints);

        chkTodosRubros.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        chkTodosRubros.setText("Todos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(chkTodosRubros, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setText("Tipo Rubro:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jLabel4, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(cmbTipoRubroPorMes, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.3;
        getContentPane().add(lblEspacio2, gridBagConstraints);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jScrollPane2.setViewportView(lstFiltrosMes);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        jPanel1.add(jScrollPane2, gridBagConstraints);

        btnAgregarMes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/mas-ico.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(btnAgregarMes, gridBagConstraints);

        btnEliminarMes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/cancel-ico.png"))); // NOI18N
        jPanel1.add(btnEliminarMes, new java.awt.GridBagConstraints());

        jLabel1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel1.setText("Filtro Por Mes:");
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
        jPanel1.add(cmbMesFiltro, gridBagConstraints);

        btnLimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/pequenos/clear.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        jPanel1.add(btnLimpiar, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 10, 10);
        getContentPane().add(jPanel1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        getContentPane().add(lblEspacio, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarMes;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEliminarMes;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JCheckBox chkTodosNiveles;
    private javax.swing.JCheckBox chkTodosRubros;
    private javax.swing.JComboBox<RubroPlantillaMes> cmbMesFiltro;
    private javax.swing.JComboBox<NivelAcademico> cmbNivelAcademico;
    private javax.swing.JComboBox<Periodo> cmbPeriodo;
    private javax.swing.JComboBox<CatalogoProducto> cmbTipoRubroPorMes;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblBuscar;
    private javax.swing.JLabel lblEspacio;
    private javax.swing.JLabel lblEspacio2;
    private javax.swing.JLabel lblperiodo;
    private javax.swing.JList<RubroPlantillaMes> lstFiltrosMes;
    private javax.swing.JTable tblDeudas;
    // End of variables declaration//GEN-END:variables

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    public JComboBox<NivelAcademico> getCmbNivelAcademico() {
        return cmbNivelAcademico;
    }

    public void setCmbNivelAcademico(JComboBox<NivelAcademico> cmbNivelAcademico) {
        this.cmbNivelAcademico = cmbNivelAcademico;
    }

    public JComboBox<Periodo> getCmbPeriodo() {
        return cmbPeriodo;
    }

    public void setCmbPeriodo(JComboBox<Periodo> cmbPeriodo) {
        this.cmbPeriodo = cmbPeriodo;
    }

    public JTable getTblDeudas() {
        return tblDeudas;
    }

    public void setTblDeudas(JTable tblDeudas) {
        this.tblDeudas = tblDeudas;
    }

    public JCheckBox getChkTodosNiveles() {
        return chkTodosNiveles;
    }

    public void setChkTodosNiveles(JCheckBox chkTodosNiveles) {
        this.chkTodosNiveles = chkTodosNiveles;
    }

    public JCheckBox getChkTodosRubros() {
        return chkTodosRubros;
    }

    public void setChkTodosRubros(JCheckBox chkTodosRubros) {
        this.chkTodosRubros = chkTodosRubros;
    }

    public JComboBox<CatalogoProducto> getCmbTipoRubroPorMes() {
        return cmbTipoRubroPorMes;
    }

    public void setCmbTipoRubroPorMes(JComboBox<CatalogoProducto> cmbTipoRubroPorMes) {
        this.cmbTipoRubroPorMes = cmbTipoRubroPorMes;
    }

    public JButton getBtnAgregarMes() {
        return btnAgregarMes;
    }

    public void setBtnAgregarMes(JButton btnAgregarMes) {
        this.btnAgregarMes = btnAgregarMes;
    }

    public JList<RubroPlantillaMes> getLstFiltrosMes() {
        return lstFiltrosMes;
    }

    public void setLstFiltrosMes(JList<RubroPlantillaMes> lstFiltrosMes) {
        this.lstFiltrosMes = lstFiltrosMes;
    }

    public JButton getBtnEliminarMes() {
        return btnEliminarMes;
    }

    public void setBtnEliminarMes(JButton btnEliminarMes) {
        this.btnEliminarMes = btnEliminarMes;
    }

    public JComboBox<RubroPlantillaMes> getCmbMesFiltro() {
        return cmbMesFiltro;
    }

    public void setCmbMesFiltro(JComboBox<RubroPlantillaMes> cmbMesFiltro) {
        this.cmbMesFiltro = cmbMesFiltro;
    }

    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }

    public void setBtnLimpiar(JButton btnLimpiar) {
        this.btnLimpiar = btnLimpiar;
    }
    
    
    
}
