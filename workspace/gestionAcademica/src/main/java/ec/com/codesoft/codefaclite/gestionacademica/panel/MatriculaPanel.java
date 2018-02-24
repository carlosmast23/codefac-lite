/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.gestionacademica.panel;

import ec.com.codesoft.codefaclite.controlador.aplicacion.ControladorCodefacInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.NivelAcademico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;

/**
 *
 * @author Carlos
 */
public abstract class MatriculaPanel extends ControladorCodefacInterface {

    /**
     * Creates new form MatriculaPanel
     */
    public MatriculaPanel() {
        initComponents();
        Periodo p;
        NivelAcademico na;
                
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        lstAlumnosDisponibles = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        lstAlumnosMatriculados = new javax.swing.JList<>();
        btnPasar = new javax.swing.JButton();
        btnPasarTodo = new javax.swing.JButton();
        btnRegresar = new javax.swing.JButton();
        btnRegresarTodo = new javax.swing.JButton();
        cmbNivel = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cmbPeriodoAnterior = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cmbNivelMatricula = new javax.swing.JComboBox<>();
        cmbPeriodoSiguiente = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        jScrollPane1.setViewportView(lstAlumnosDisponibles);

        jScrollPane2.setViewportView(lstAlumnosMatriculados);

        btnPasar.setText(">");
        btnPasar.setToolTipText("Matricular");

        btnPasarTodo.setText(">>");
        btnPasarTodo.setToolTipText("Matricular Todos");

        btnRegresar.setText("<");
        btnRegresar.setToolTipText("Regresar");

        btnRegresarTodo.setText("<<");
        btnRegresarTodo.setToolTipText("Regresar Todos");

        jLabel1.setText("Nivel:");

        jLabel2.setText("Periodo Anterior:");

        jLabel3.setText("Periodo Matricula:");

        jLabel4.setText("Nivel:");

        jLabel5.setText("Lista de Alumnos disponibles");

        jLabel6.setText("Lista de Alumnos Matriculados");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cmbNivel, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmbPeriodoAnterior, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 88, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnPasarTodo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnPasar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnRegresar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnRegresarTodo))
                        .addGap(54, 54, 54)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(cmbNivelMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(cmbPeriodoSiguiente, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(28, 28, 28)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(cmbPeriodoAnterior, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cmbNivel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbPeriodoSiguiente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(cmbNivelMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
                            .addComponent(jScrollPane1))
                        .addGap(28, 28, 28))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnPasar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPasarTodo)
                        .addGap(54, 54, 54)
                        .addComponent(btnRegresar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRegresarTodo)
                        .addGap(98, 98, 98))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPasar;
    private javax.swing.JButton btnPasarTodo;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JButton btnRegresarTodo;
    private javax.swing.JComboBox<NivelAcademico> cmbNivel;
    private javax.swing.JComboBox<NivelAcademico> cmbNivelMatricula;
    private javax.swing.JComboBox<Periodo> cmbPeriodoAnterior;
    private javax.swing.JComboBox<Periodo> cmbPeriodoSiguiente;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList<String> lstAlumnosDisponibles;
    private javax.swing.JList<String> lstAlumnosMatriculados;
    // End of variables declaration//GEN-END:variables

    public JButton getBtnPasar() {
        return btnPasar;
    }

    public void setBtnPasar(JButton btnPasar) {
        this.btnPasar = btnPasar;
    }

    public JButton getBtnPasarTodo() {
        return btnPasarTodo;
    }

    public void setBtnPasarTodo(JButton btnPasarTodo) {
        this.btnPasarTodo = btnPasarTodo;
    }

    public JButton getBtnRegresar() {
        return btnRegresar;
    }

    public void setBtnRegresar(JButton btnRegresar) {
        this.btnRegresar = btnRegresar;
    }

    public JButton getBtnRegresarTodo() {
        return btnRegresarTodo;
    }

    public void setBtnRegresarTodo(JButton btnRegresarTodo) {
        this.btnRegresarTodo = btnRegresarTodo;
    }

    public JComboBox<Periodo> getCmbPeriodoAnterior() {
        return cmbPeriodoAnterior;
    }

    public void setCmbPeriodoAnterior(JComboBox<Periodo> cmbPeriodoAnterior) {
        this.cmbPeriodoAnterior = cmbPeriodoAnterior;
    }

    public JComboBox<Periodo> getCmbPeriodoSiguiente() {
        return cmbPeriodoSiguiente;
    }

    public void setCmbPeriodoSiguiente(JComboBox<Periodo> cmbPeriodoSiguiente) {
        this.cmbPeriodoSiguiente = cmbPeriodoSiguiente;
    }

    public JList<String> getLstAlumnosDisponibles() {
        return lstAlumnosDisponibles;
    }

    public void setLstAlumnosDisponibles(JList<String> lstAlumnosDisponibles) {
        this.lstAlumnosDisponibles = lstAlumnosDisponibles;
    }

    public JList<String> getLstAlumnosMatriculados() {
        return lstAlumnosMatriculados;
    }

    public void setLstAlumnosMatriculados(JList<String> lstAlumnosMatriculados) {
        this.lstAlumnosMatriculados = lstAlumnosMatriculados;
    }

    public JComboBox<NivelAcademico> getCmbNivel() {
        return cmbNivel;
    }

    public void setCmbNivel(JComboBox<NivelAcademico> cmbNivel) {
        this.cmbNivel = cmbNivel;
    }

    public JComboBox<NivelAcademico> getCmbNivelMatricula() {
        return cmbNivelMatricula;
    }

    public void setCmbNivelMatricula(JComboBox<NivelAcademico> cmbNivelMatricula) {
        this.cmbNivelMatricula = cmbNivelMatricula;
    }

    
}
