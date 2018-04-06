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
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubrosNivel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
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
        jToolBar1 = new javax.swing.JToolBar();
        chkEnero = new javax.swing.JRadioButton();
        chkFebrero = new javax.swing.JRadioButton();
        chkMarzo = new javax.swing.JRadioButton();
        chkAbril = new javax.swing.JRadioButton();
        chkMayo = new javax.swing.JRadioButton();
        chkJunio = new javax.swing.JRadioButton();
        chkJulio = new javax.swing.JRadioButton();
        chkAgosto = new javax.swing.JRadioButton();
        chkSeptiembre = new javax.swing.JRadioButton();
        chkOctubre = new javax.swing.JRadioButton();
        chkNoviembre = new javax.swing.JRadioButton();
        chkDiciembre = new javax.swing.JRadioButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Reporte Deudas por Nivel Académico");
        setPreferredSize(new java.awt.Dimension(900, 600));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        lblperiodo.setText("Periodo:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(30, 20, 8, 20);
        getContentPane().add(lblperiodo, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 156;
        gridBagConstraints.insets = new java.awt.Insets(24, 0, 0, 0);
        getContentPane().add(cmbPeriodo, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 156;
        getContentPane().add(cmbNivelAcademico, gridBagConstraints);

        lblBuscar.setText("Nivel:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        getContentPane().add(lblBuscar, gridBagConstraints);

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/find2-ico.png"))); // NOI18N
        btnBuscar.setText("Buscar");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.ipadx = 15;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 5, 5);
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
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 612;
        gridBagConstraints.ipady = 150;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(20, 15, 20, 15);
        getContentPane().add(jScrollPane1, gridBagConstraints);

        chkTodosNiveles.setText("Todos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        getContentPane().add(chkTodosNiveles, gridBagConstraints);

        chkTodosRubros.setText("Todos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        getContentPane().add(chkTodosRubros, gridBagConstraints);

        jLabel4.setText("Tipo Rubro:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        getContentPane().add(jLabel4, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 156;
        getContentPane().add(cmbTipoRubroPorMes, gridBagConstraints);

        jToolBar1.setBorder(javax.swing.BorderFactory.createTitledBorder("Meses Generar"));

        chkEnero.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        chkEnero.setText("Enero");
        chkEnero.setFocusable(false);
        chkEnero.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        chkEnero.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(chkEnero);

        chkFebrero.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        chkFebrero.setText("Febrero");
        chkFebrero.setFocusable(false);
        chkFebrero.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        chkFebrero.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(chkFebrero);

        chkMarzo.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        chkMarzo.setText("Marzo");
        chkMarzo.setFocusable(false);
        chkMarzo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        chkMarzo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(chkMarzo);

        chkAbril.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        chkAbril.setText("Abril");
        chkAbril.setFocusable(false);
        chkAbril.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        chkAbril.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(chkAbril);

        chkMayo.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        chkMayo.setText("Mayo");
        chkMayo.setFocusable(false);
        chkMayo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        chkMayo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(chkMayo);

        chkJunio.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        chkJunio.setText("Junio");
        chkJunio.setFocusable(false);
        chkJunio.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        chkJunio.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(chkJunio);

        chkJulio.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        chkJulio.setText("Julio");
        chkJulio.setFocusable(false);
        chkJulio.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        chkJulio.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(chkJulio);

        chkAgosto.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        chkAgosto.setText("Agosto");
        chkAgosto.setFocusable(false);
        chkAgosto.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        chkAgosto.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(chkAgosto);

        chkSeptiembre.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        chkSeptiembre.setText("Septiembre");
        chkSeptiembre.setFocusable(false);
        chkSeptiembre.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        chkSeptiembre.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(chkSeptiembre);

        chkOctubre.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        chkOctubre.setText("Octubre");
        chkOctubre.setFocusable(false);
        chkOctubre.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        chkOctubre.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(chkOctubre);

        chkNoviembre.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        chkNoviembre.setText("Noviembre");
        chkNoviembre.setFocusable(false);
        chkNoviembre.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        chkNoviembre.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(chkNoviembre);

        chkDiciembre.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        chkDiciembre.setText("Diciembre");
        chkDiciembre.setFocusable(false);
        chkDiciembre.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        chkDiciembre.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(chkDiciembre);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(jToolBar1, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JRadioButton chkAbril;
    private javax.swing.JRadioButton chkAgosto;
    private javax.swing.JRadioButton chkDiciembre;
    private javax.swing.JRadioButton chkEnero;
    private javax.swing.JRadioButton chkFebrero;
    private javax.swing.JRadioButton chkJulio;
    private javax.swing.JRadioButton chkJunio;
    private javax.swing.JRadioButton chkMarzo;
    private javax.swing.JRadioButton chkMayo;
    private javax.swing.JRadioButton chkNoviembre;
    private javax.swing.JRadioButton chkOctubre;
    private javax.swing.JRadioButton chkSeptiembre;
    private javax.swing.JCheckBox chkTodosNiveles;
    private javax.swing.JCheckBox chkTodosRubros;
    private javax.swing.JComboBox<NivelAcademico> cmbNivelAcademico;
    private javax.swing.JComboBox<Periodo> cmbPeriodo;
    private javax.swing.JComboBox<CatalogoProducto> cmbTipoRubroPorMes;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lblBuscar;
    private javax.swing.JLabel lblperiodo;
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

    public JRadioButton getChkAbril() {
        return chkAbril;
    }

    public void setChkAbril(JRadioButton chkAbril) {
        this.chkAbril = chkAbril;
    }

    public JRadioButton getChkAgosto() {
        return chkAgosto;
    }

    public void setChkAgosto(JRadioButton chkAgosto) {
        this.chkAgosto = chkAgosto;
    }

    public JRadioButton getChkDiciembre() {
        return chkDiciembre;
    }

    public void setChkDiciembre(JRadioButton chkDiciembre) {
        this.chkDiciembre = chkDiciembre;
    }

    public JRadioButton getChkJunio() {
        return chkJunio;
    }

    public void setChkJunio(JRadioButton chkJunio) {
        this.chkJunio = chkJunio;
    }

    public JRadioButton getChkMarzo() {
        return chkMarzo;
    }

    public void setChkMarzo(JRadioButton chkMarzo) {
        this.chkMarzo = chkMarzo;
    }

    public JRadioButton getChkMayo() {
        return chkMayo;
    }

    public void setChkMayo(JRadioButton chkMayo) {
        this.chkMayo = chkMayo;
    }

    public JRadioButton getChkNoviembre() {
        return chkNoviembre;
    }

    public void setChkNoviembre(JRadioButton chkNoviembre) {
        this.chkNoviembre = chkNoviembre;
    }

    public JRadioButton getChkOctubre() {
        return chkOctubre;
    }

    public void setChkOctubre(JRadioButton chkOctubre) {
        this.chkOctubre = chkOctubre;
    }

    public JRadioButton getChkSeptiembre() {
        return chkSeptiembre;
    }

    public void setChkSeptiembre(JRadioButton chkSeptiembre) {
        this.chkSeptiembre = chkSeptiembre;
    }

    public JRadioButton getChkEnero() {
        return chkEnero;
    }

    public void setChkEnero(JRadioButton chkEnero) {
        this.chkEnero = chkEnero;
    }

    public JRadioButton getChkFebrero() {
        return chkFebrero;
    }

    public void setChkFebrero(JRadioButton chkFebrero) {
        this.chkFebrero = chkFebrero;
    }

    public JRadioButton getChkJulio() {
        return chkJulio;
    }

    public void setChkJulio(JRadioButton chkJulio) {
        this.chkJulio = chkJulio;
    }

}
