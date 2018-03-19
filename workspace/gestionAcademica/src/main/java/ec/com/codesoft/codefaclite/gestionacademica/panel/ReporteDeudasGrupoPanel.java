/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.gestionacademica.panel;

import ec.com.codesoft.codefaclite.controlador.aplicacion.ControladorCodefacInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.NivelAcademico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubrosNivel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JTable;

/**
 *
 * @author CodesoftDesarrollo
 */
public abstract class ReporteDeudasGrupoPanel extends ControladorCodefacInterface {

    /**
     * Creates new form ReporteDeudasEstudiantePanel
     */
    public ReporteDeudasGrupoPanel() {
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

        lblperiodo = new javax.swing.JLabel();
        cmbPeriodo = new javax.swing.JComboBox<>();
        cmbNivelAcademico = new javax.swing.JComboBox<>();
        lblBuscar = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDeudas = new javax.swing.JTable();
        cmbRubrosNivel = new javax.swing.JComboBox<>();
        lblperiodo1 = new javax.swing.JLabel();
        chkTodosNiveles = new javax.swing.JCheckBox();
        chkTodosRubros = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        lstRubrosFacturar = new javax.swing.JList<>();
        btnAgregarRubrosNivel = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Reporte Deudas por Grupo");

        lblperiodo.setText("Periodo:");

        lblBuscar.setText("Nivel:");

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/find2-ico.png"))); // NOI18N
        btnBuscar.setText("Buscar");

        tblDeudas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Identificación", "Estudiante", "Nivel Academico"
            }
        ));
        jScrollPane1.setViewportView(tblDeudas);

        lblperiodo1.setText("Rubros de Nivel:");

        chkTodosNiveles.setText("Todos");

        chkTodosRubros.setText("Todos");

        jLabel3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel3.setText("Rubros a Facturar:");

        jScrollPane2.setViewportView(lstRubrosFacturar);

        btnAgregarRubrosNivel.setText("Add");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(182, 182, 182)
                                .addComponent(lblperiodo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmbPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblBuscar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmbNivelAcademico, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(chkTodosNiveles)
                                .addGap(18, 18, 18)
                                .addComponent(lblperiodo1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbRubrosNivel, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAgregarRubrosNivel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(chkTodosRubros))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(286, 286, 286)
                        .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 635, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(22, 22, 22))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblperiodo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblBuscar)
                            .addComponent(cmbNivelAcademico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(chkTodosNiveles)
                            .addComponent(lblperiodo1)
                            .addComponent(cmbRubrosNivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAgregarRubrosNivel)
                            .addComponent(chkTodosRubros))
                        .addGap(18, 18, 18)
                        .addComponent(btnBuscar))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(78, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarRubrosNivel;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JCheckBox chkTodosNiveles;
    private javax.swing.JCheckBox chkTodosRubros;
    private javax.swing.JComboBox<NivelAcademico> cmbNivelAcademico;
    private javax.swing.JComboBox<Periodo> cmbPeriodo;
    private javax.swing.JComboBox<RubrosNivel> cmbRubrosNivel;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblBuscar;
    private javax.swing.JLabel lblperiodo;
    private javax.swing.JLabel lblperiodo1;
    private javax.swing.JList<RubrosNivel> lstRubrosFacturar;
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

    public JComboBox<RubrosNivel> getCmbRubrosNivel() {
        return cmbRubrosNivel;
    }

    public void setCmbRubrosNivel(JComboBox<RubrosNivel> cmbRubrosNivel) {
        this.cmbRubrosNivel = cmbRubrosNivel;
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

    public JButton getBtnAgregarRubrosNivel() {
        return btnAgregarRubrosNivel;
    }

    public void setBtnAgregarRubrosNivel(JButton btnAgregarRubrosNivel) {
        this.btnAgregarRubrosNivel = btnAgregarRubrosNivel;
    }

    public JList<RubrosNivel> getLstRubrosFacturar() {
        return lstRubrosFacturar;
    }

    public void setLstRubrosFacturar(JList<RubrosNivel> lstRubrosFacturar) {
        this.lstRubrosFacturar = lstRubrosFacturar;
    }

    
}
