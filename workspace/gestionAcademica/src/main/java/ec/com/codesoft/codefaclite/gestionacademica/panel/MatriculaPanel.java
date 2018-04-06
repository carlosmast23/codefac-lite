/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.gestionacademica.panel;

import ec.com.codesoft.codefaclite.controlador.aplicacion.ControladorCodefacInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Estudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.NivelAcademico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;

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
        java.awt.GridBagConstraints gridBagConstraints;

        btnPasar = new javax.swing.JButton();
        btnRegresar = new javax.swing.JButton();
        cmbNivel = new javax.swing.JComboBox<>();
        lblNivelAnterior = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cmbPeriodoAnterior = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cmbNivelMatricula = new javax.swing.JComboBox<>();
        cmbPeriodoSiguiente = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblAlumnosSinMatricula = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblAlumnosConMatricula = new javax.swing.JTable();
        btnAgregarEstudiante = new javax.swing.JButton();
        chkSeleccionarTodoTblSinMatricula = new javax.swing.JCheckBox();
        chkSeleccionarTodoTblConMatricula = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Matricula por grupo");
        getContentPane().setLayout(new java.awt.GridBagLayout());

        btnPasar.setBackground(new java.awt.Color(255, 255, 255));
        btnPasar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/pequenos/flechaDer.png"))); // NOI18N
        btnPasar.setToolTipText("Matricular");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(90, 10, 0, 0);
        getContentPane().add(btnPasar, gridBagConstraints);

        btnRegresar.setBackground(new java.awt.Color(255, 255, 255));
        btnRegresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/pequenos/flechaIzq.png"))); // NOI18N
        btnRegresar.setToolTipText("Regresar");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 10, 0, 0);
        getContentPane().add(btnRegresar, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        getContentPane().add(cmbNivel, gridBagConstraints);

        lblNivelAnterior.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblNivelAnterior.setText("Cursos:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        getContentPane().add(lblNivelAnterior, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel2.setText("Buscar Por:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        getContentPane().add(jLabel2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        getContentPane().add(cmbPeriodoAnterior, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel3.setText("Periodo Matricula:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        getContentPane().add(jLabel3, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel4.setText("Cursos:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        getContentPane().add(jLabel4, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 11;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        getContentPane().add(cmbNivelMatricula, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 11;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        getContentPane().add(cmbPeriodoSiguiente, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel5.setText("Lista de Alumnos disponibles");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.ipadx = 28;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        getContentPane().add(jLabel5, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel6.setText("Lista de Alumnos Matriculados");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.ipadx = 28;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        getContentPane().add(jLabel6, gridBagConstraints);

        tblAlumnosSinMatricula.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Opcion", "Alumno"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tblAlumnosSinMatricula);
        if (tblAlumnosSinMatricula.getColumnModel().getColumnCount() > 0) {
            tblAlumnosSinMatricula.getColumnModel().getColumn(0).setMinWidth(60);
            tblAlumnosSinMatricula.getColumnModel().getColumn(0).setPreferredWidth(30);
            tblAlumnosSinMatricula.getColumnModel().getColumn(0).setMaxWidth(30);
        }

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 9;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 10, 0, 0);
        getContentPane().add(jScrollPane3, gridBagConstraints);

        tblAlumnosConMatricula.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Opcion", "Grabado", "Alumno"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.String.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane4.setViewportView(tblAlumnosConMatricula);
        if (tblAlumnosConMatricula.getColumnModel().getColumnCount() > 0) {
            tblAlumnosConMatricula.getColumnModel().getColumn(0).setMinWidth(60);
            tblAlumnosConMatricula.getColumnModel().getColumn(0).setPreferredWidth(30);
            tblAlumnosConMatricula.getColumnModel().getColumn(0).setMaxWidth(30);
        }

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 10;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 16, 0, 0);
        getContentPane().add(jScrollPane4, gridBagConstraints);

        btnAgregarEstudiante.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/pequenos/add2.png"))); // NOI18N
        btnAgregarEstudiante.setText("Estudiante");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 19;
        gridBagConstraints.gridy = 2;
        getContentPane().add(btnAgregarEstudiante, gridBagConstraints);

        chkSeleccionarTodoTblSinMatricula.setText("Seleccionar Todo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        getContentPane().add(chkSeleccionarTodoTblSinMatricula, gridBagConstraints);

        chkSeleccionarTodoTblConMatricula.setText("Seleccionar Todo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 20, 8, 20);
        getContentPane().add(chkSeleccionarTodoTblConMatricula, gridBagConstraints);

        jLabel1.setText("                              ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 2;
        getContentPane().add(jLabel1, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarEstudiante;
    private javax.swing.JButton btnPasar;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JCheckBox chkSeleccionarTodoTblConMatricula;
    private javax.swing.JCheckBox chkSeleccionarTodoTblSinMatricula;
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
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblNivelAnterior;
    private javax.swing.JTable tblAlumnosConMatricula;
    private javax.swing.JTable tblAlumnosSinMatricula;
    // End of variables declaration//GEN-END:variables

    public JButton getBtnPasar() {
        return btnPasar;
    }

    public void setBtnPasar(JButton btnPasar) {
        this.btnPasar = btnPasar;
    }


    public JButton getBtnRegresar() {
        return btnRegresar;
    }

    public void setBtnRegresar(JButton btnRegresar) {
        this.btnRegresar = btnRegresar;
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

    public JTable getTblAlumnosConMatricula() {
        return tblAlumnosConMatricula;
    }

    public void setTblAlumnosConMatricula(JTable tblAlumnosConMatricula) {
        this.tblAlumnosConMatricula = tblAlumnosConMatricula;
    }

    public JTable getTblAlumnosSinMatricula() {
        return tblAlumnosSinMatricula;
    }

    public void setTblAlumnosSinMatricula(JTable tblAlumnosSinMatricula) {
        this.tblAlumnosSinMatricula = tblAlumnosSinMatricula;
    }

    public JButton getBtnAgregarEstudiante() {
        return btnAgregarEstudiante;
    }

    public void setBtnAgregarEstudiante(JButton btnAgregarEstudiante) {
        this.btnAgregarEstudiante = btnAgregarEstudiante;
    }

    public JLabel getLblNivelAnterior() {
        return lblNivelAnterior;
    }

    public JCheckBox getChkSeleccionarTodoTblConMatricula() {
        return chkSeleccionarTodoTblConMatricula;
    }

    public void setChkSeleccionarTodoTblConMatricula(JCheckBox chkSeleccionarTodoTblConMatricula) {
        this.chkSeleccionarTodoTblConMatricula = chkSeleccionarTodoTblConMatricula;
    }

    public JCheckBox getChkSeleccionarTodoTblSinMatricula() {
        return chkSeleccionarTodoTblSinMatricula;
    }

    public void setChkSeleccionarTodoTblSinMatricula(JCheckBox chkSeleccionarTodoTblSinMatricula) {
        this.chkSeleccionarTodoTblSinMatricula = chkSeleccionarTodoTblSinMatricula;
    }

    
    
    
    
}
