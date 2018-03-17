/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.gestionacademica.panel;

import ec.com.codesoft.codefaclite.controlador.aplicacion.ControladorCodefacInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.util.LimpiarAnotacion;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.NivelAcademico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author Carlos
 */
public abstract class RubroPlantillaPanel extends ControladorCodefacInterface {

    /**
     * Creates new form RubroPlantillaPanel
     */
    public RubroPlantillaPanel() {
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

        jLabel3 = new javax.swing.JLabel();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cmbPeriodo = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cmbRubro = new javax.swing.JComboBox<>();
        txtValor = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtDiasCredito = new javax.swing.JTextField();
        btnAgregarRubro = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        cmbCursoSinRegistrar = new javax.swing.JComboBox<>();
        btnBuscarEstudianteSinRegistro = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDatosSinRegistrar = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        cmbCursosRegistrados = new javax.swing.JComboBox<>();
        btnAgregarEstudiante = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDatosRegistrados = new javax.swing.JTable();
        btnPasar = new javax.swing.JButton();
        btnPasarTodo = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
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
        jLabel10 = new javax.swing.JLabel();
        txtNombreMes = new javax.swing.JTextField();
        btnGenerar = new javax.swing.JButton();

        jLabel3.setText("jLabel3");

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Rubro Plantilla");

        jLabel1.setText("Periodo Activo:");

        jLabel2.setText("Nombre:");

        jLabel5.setText("Rubro:");

        jLabel4.setText("Valor:");

        txtValor.setText("0.00");

        jLabel6.setText("Días Credito:");

        btnAgregarRubro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/pequenos/add2.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbRubro, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtDiasCredito, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtValor, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(3, 3, 3))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(44, 44, 44)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addComponent(btnAgregarRubro)
                .addContainerGap(527, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cmbPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(28, 28, 28)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cmbRubro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5))
                    .addComponent(btnAgregarRubro))
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDiasCredito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addContainerGap(207, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Datos Generales", jPanel2);

        jLabel8.setText("Cursos Sin Registrar:");

        btnBuscarEstudianteSinRegistro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/find2-ico.png"))); // NOI18N

        tblDatosSinRegistrar.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblDatosSinRegistrar);

        jLabel9.setText("Cursos Registrados:");

        btnAgregarEstudiante.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/pequenos/add2.png"))); // NOI18N

        tblDatosRegistrados.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblDatosRegistrados);

        btnPasar.setText(">");

        btnPasarTodo.setText(">>");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(cmbCursoSinRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnBuscarEstudianteSinRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnPasar, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPasarTodo))))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(cmbCursosRegistrados, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(btnAgregarEstudiante))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 405, Short.MAX_VALUE))
                .addGap(27, 27, 27))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel8)
                                .addComponent(cmbCursoSinRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(btnAgregarEstudiante)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel9)
                                    .addComponent(cmbCursosRegistrados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(btnBuscarEstudianteSinRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(131, 131, 131)
                        .addComponent(btnPasar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnPasarTodo)))
                .addContainerGap(69, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Registrar Estudiantes", jPanel3);

        jToolBar1.setBorder(javax.swing.BorderFactory.createTitledBorder("Meses"));

        chkEnero.setText("Enero");
        chkEnero.setFocusable(false);
        chkEnero.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        chkEnero.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(chkEnero);

        chkFebrero.setText("Febrero");
        chkFebrero.setFocusable(false);
        chkFebrero.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        chkFebrero.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(chkFebrero);

        chkMarzo.setText("Marzo");
        chkMarzo.setFocusable(false);
        chkMarzo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        chkMarzo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(chkMarzo);

        chkAbril.setText("Abril");
        chkAbril.setFocusable(false);
        chkAbril.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        chkAbril.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(chkAbril);

        chkMayo.setText("Mayo");
        chkMayo.setFocusable(false);
        chkMayo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        chkMayo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(chkMayo);

        chkJunio.setText("Junio");
        chkJunio.setFocusable(false);
        chkJunio.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        chkJunio.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(chkJunio);

        chkJulio.setText("Julio");
        chkJulio.setFocusable(false);
        chkJulio.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        chkJulio.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(chkJulio);

        chkAgosto.setText("Agosto");
        chkAgosto.setFocusable(false);
        chkAgosto.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        chkAgosto.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(chkAgosto);

        chkSeptiembre.setText("Septiembre");
        chkSeptiembre.setFocusable(false);
        chkSeptiembre.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        chkSeptiembre.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(chkSeptiembre);

        chkOctubre.setText("Octubre");
        chkOctubre.setFocusable(false);
        chkOctubre.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        chkOctubre.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(chkOctubre);

        chkNoviembre.setText("Noviembre");
        chkNoviembre.setFocusable(false);
        chkNoviembre.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        chkNoviembre.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(chkNoviembre);

        chkDiciembre.setText("Diciembre");
        chkDiciembre.setFocusable(false);
        chkDiciembre.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        chkDiciembre.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(chkDiciembre);

        jLabel10.setText("Nombre Mes:");

        btnGenerar.setText("Generar");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 630, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(txtNombreMes, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnGenerar, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(277, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtNombreMes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGenerar))
                .addContainerGap(318, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Generar Rubros", jPanel4);

        getContentPane().add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarEstudiante;
    private javax.swing.JButton btnAgregarRubro;
    private javax.swing.JButton btnBuscarEstudianteSinRegistro;
    private javax.swing.JButton btnGenerar;
    private javax.swing.JButton btnPasar;
    private javax.swing.JButton btnPasarTodo;
    private javax.swing.ButtonGroup buttonGroup1;
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
    private javax.swing.JComboBox<NivelAcademico> cmbCursoSinRegistrar;
    private javax.swing.JComboBox<NivelAcademico> cmbCursosRegistrados;
    private javax.swing.JComboBox<Periodo> cmbPeriodo;
    private javax.swing.JComboBox<CatalogoProducto> cmbRubro;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTable tblDatosRegistrados;
    private javax.swing.JTable tblDatosSinRegistrar;
    private javax.swing.JTextField txtDiasCredito;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNombreMes;
    private javax.swing.JTextField txtValor;
    // End of variables declaration//GEN-END:variables

    public JButton getBtnAgregarEstudiante() {
        return btnAgregarEstudiante;
    }

    public void setBtnAgregarEstudiante(JButton btnAgregarEstudiante) {
        this.btnAgregarEstudiante = btnAgregarEstudiante;
    }

    public JButton getBtnBuscarEstudianteSinRegistro() {
        return btnBuscarEstudianteSinRegistro;
    }

    public void setBtnBuscarEstudianteSinRegistro(JButton btnBuscarEstudianteSinRegistro) {
        this.btnBuscarEstudianteSinRegistro = btnBuscarEstudianteSinRegistro;
    }

    public JButton getBtnGenerar() {
        return btnGenerar;
    }

    public void setBtnGenerar(JButton btnGenerar) {
        this.btnGenerar = btnGenerar;
    }

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

    public ButtonGroup getButtonGroup1() {
        return buttonGroup1;
    }

    public void setButtonGroup1(ButtonGroup buttonGroup1) {
        this.buttonGroup1 = buttonGroup1;
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

    public JComboBox<NivelAcademico> getCmbCursoSinRegistrar() {
        return cmbCursoSinRegistrar;
    }

    public void setCmbCursoSinRegistrar(JComboBox<NivelAcademico> cmbCursoSinRegistrar) {
        this.cmbCursoSinRegistrar = cmbCursoSinRegistrar;
    }

    public JComboBox<NivelAcademico> getCmbCursosRegistrados() {
        return cmbCursosRegistrados;
    }

    public void setCmbCursosRegistrados(JComboBox<NivelAcademico> cmbCursosRegistrados) {
        this.cmbCursosRegistrados = cmbCursosRegistrados;
    }

    public JComboBox<Periodo> getCmbPeriodo() {
        return cmbPeriodo;
    }

    public void setCmbPeriodo(JComboBox<Periodo> cmbPeriodo) {
        this.cmbPeriodo = cmbPeriodo;
    }

    public JComboBox<CatalogoProducto> getCmbRubro() {
        return cmbRubro;
    }

    public void setCmbRubro(JComboBox<CatalogoProducto> cmbRubro) {
        this.cmbRubro = cmbRubro;
    }

    public JTable getTblDatosRegistrados() {
        return tblDatosRegistrados;
    }

    public void setTblDatosRegistrados(JTable tblDatosRegistrados) {
        this.tblDatosRegistrados = tblDatosRegistrados;
    }

    public JTable getTblDatosSinRegistrar() {
        return tblDatosSinRegistrar;
    }

    public void setTblDatosSinRegistrar(JTable tblDatosSinRegistrar) {
        this.tblDatosSinRegistrar = tblDatosSinRegistrar;
    }

    @LimpiarAnotacion
    public JTextField getTxtDiasCredito() {
        return txtDiasCredito;
    }

    public void setTxtDiasCredito(JTextField txtDiasCredito) {
        this.txtDiasCredito = txtDiasCredito;
    }

    @LimpiarAnotacion
    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public void setTxtNombre(JTextField txtNombre) {
        this.txtNombre = txtNombre;
    }

    public JTextField getTxtNombreMes() {
        return txtNombreMes;
    }

    public void setTxtNombreMes(JTextField txtNombreMes) {
        this.txtNombreMes = txtNombreMes;
    }

    @LimpiarAnotacion
    public JTextField getTxtValor() {
        return txtValor;
    }

    public void setTxtValor(JTextField txtValor) {
        this.txtValor = txtValor;
    }

    public JButton getBtnAgregarRubro() {
        return btnAgregarRubro;
    }

    public void setBtnAgregarRubro(JButton btnAgregarRubro) {
        this.btnAgregarRubro = btnAgregarRubro;
    }

    
    
}
