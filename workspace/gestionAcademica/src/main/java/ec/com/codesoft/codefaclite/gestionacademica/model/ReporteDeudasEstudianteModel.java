/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.gestionacademica.model;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.report.ReporteCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.gestionacademica.busqueda.EstudianteBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.EstudianteInscritoBusquedaDialogo;
import ec.com.codesoft.codefaclite.gestionacademica.panel.ReporteDeudasEstudiantePanel;
import ec.com.codesoft.codefaclite.gestionacademica.panel.ReporteDeudasPanel;
import ec.com.codesoft.codefaclite.gestionacademica.reportdata.ReporteDeudasData;
import ec.com.codesoft.codefaclite.gestionacademica.reportdata.ReporteDeudasEstudianteData;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Estudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.EstudianteInscrito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.NivelAcademico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroEstudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubrosNivel;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.EstudianteInscritoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.NivelAcademicoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RubroEstudianteServiceIf;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.InputStream;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author CodesoftDesarrollo
 */
public class ReporteDeudasEstudianteModel extends ReporteDeudasEstudiantePanel {

    Map parameters = new HashMap();
    private boolean banderaNiveles = false;
    private boolean banderaRubros = false;
    Estudiante estudiante = null;

    public ReporteDeudasEstudianteModel() {
        validacionDatosIngresados=false;
    }
    
    

    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        
        iniciarComponentes();
        listener();
        
    }
    
    private void iniciarComponentes() {
        try {
            Periodo p1 = new Periodo();
            p1.setNombre("Seleccione:");
            List<Periodo> periodos = ServiceFactory.getFactory().getPeriodoServiceIf().obtenerPeriodosSinEliminar();
            getCmbPeriodo().removeAllItems();
            getCmbPeriodo().addItem(p1);
            for (Periodo periodo : periodos) {
                getCmbPeriodo().addItem(periodo);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(ReporteAcademicoModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    private void listener()
    {
        iniciarBotones();
    }
    
    private void iniciarBotones()
    {
        getBtnBuscarEstudiante().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Periodo periodoSeleccionado=(Periodo) getCmbPeriodo().getSelectedItem();
                EstudianteInscritoBusquedaDialogo aulaBusquedaDialogo = new EstudianteInscritoBusquedaDialogo(periodoSeleccionado);
                BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(aulaBusquedaDialogo);
                buscarDialogoModel.setVisible(true);
                estudiante = ((EstudianteInscrito) buscarDialogoModel.getResultado()).getEstudiante();
                if (estudiante != null) {
                    getTxtEstudiante().setText(estudiante.getNombreCompleto());
                    buscarDeudas();
                }
            }
        });


    }
    
    private void buscarDeudas()
    {
        try {
            BigDecimal acum = BigDecimal.ZERO;
            Vector<String> titulo = new Vector<>();
            titulo.add("Rubro");
            titulo.add("Valor");
            titulo.add("Saldo");

            DefaultTableModel modeloTablaDeudas = new DefaultTableModel(titulo, 0);
            RubroEstudianteServiceIf rs = ServiceFactory.getFactory().getRubroEstudianteServiceIf();

            Periodo periodoSeleccionado = (Periodo) getCmbPeriodo().getSelectedItem();

            List<RubroEstudiante> dataRubro = rs.obtenerDeudasEstudiante(estudiante, periodoSeleccionado);
            // comparamos si el estudiante tiene rubros
            if (!dataRubro.isEmpty()) {
                for (RubroEstudiante re : dataRubro) {
                    Vector<String> fila2 = new Vector<String>();
                    fila2.add(re.getRubroNivel().getNombre());
                    fila2.add(re.getValor().toString());
                    fila2.add(re.getSaldo().toString());

                    acum = acum.add(re.getSaldo());
                    modeloTablaDeudas.addRow(fila2);
                }
            }
            getTblDeudas().setModel(modeloTablaDeudas);
            getLblTotalDeuda().setText(acum.toString());
        } catch (RemoteException ex) {
            Logger.getLogger(ReporteAcademicoModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void imprimir() {

        try {
            BigDecimal acum = BigDecimal.ZERO;

            InputStream path = RecursoCodefac.JASPER_ACADEMICO.getResourceInputStream("reporte_deudasestudiante.jrxml");

            Map<String, Object> mapParametros2 = new HashMap<String, Object>();
            mapParametros2.put("estudianteInscrito", estudiante);

            RubroEstudianteServiceIf rs = ServiceFactory.getFactory().getRubroEstudianteServiceIf();
            
            Periodo periodoSeleccionado=(Periodo) getCmbPeriodo().getSelectedItem();
            List<RubroEstudiante> dataRubro = rs.obtenerDeudasEstudiante(estudiante,periodoSeleccionado);
            List<ReporteDeudasEstudianteData> data = new ArrayList<ReporteDeudasEstudianteData>();

            // comparamos si el estudiante tiene rubros
            if (!dataRubro.isEmpty()) {
                for (RubroEstudiante re : dataRubro) {
                    data.add(new ReporteDeudasEstudianteData(
                            re.getRubroNivel().getNombre(),
                            re.getSaldo().toString(),
                            re.getEstudianteInscrito().getNivelAcademico().getPeriodo().getNombre(),
                            re.getValor().toString()
                    ));

                    acum = acum.add(re.getSaldo());
                }
            }

//           
            parameters.put("cedulaEstudiante", estudiante.getCedula());
            parameters.put("estudiante", estudiante.getNombreCompleto());
            parameters.put("valorDeuda", acum.toString());

            ReporteCodefac.generarReporteInternalFramePlantilla(path, parameters, data, panelPadre, "Deuda Estudiante");
        } catch (RemoteException ex) {
            Logger.getLogger(ReporteAcademicoModel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void actualizar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buscar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void limpiar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

//    @Override
    public String getNombre() {
        return "Deudas por Estudiante";
    }

    @Override
    public String getURLAyuda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        Map<Integer, Boolean> permisos = new HashMap<Integer, Boolean>();
        permisos.put(GeneralPanelInterface.BOTON_IMPRIMIR, true);
        permisos.put(GeneralPanelInterface.BOTON_AYUDA, true);
        return permisos;
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void cargarNivelesPeriodo(Periodo periodo, JComboBox<NivelAcademico> comboNivel) {
        try {

            NivelAcademicoServiceIf servicio = ServiceFactory.getFactory().getNivelAcademicoServiceIf();
            Map<String, Object> mapBusqueda = new HashMap<String, Object>();
            mapBusqueda.put("periodo", periodo);
            List<NivelAcademico> resultados = servicio.buscarPorPeriodo(periodo);

            comboNivel.removeAllItems();
            if (!resultados.isEmpty()) {
                for (NivelAcademico resultado : resultados) {
                    comboNivel.addItem(resultado);

                }
            }

        } catch (RemoteException ex) {
            Logger.getLogger(MatriculaModel.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (ServicioCodefacException ex) {
            Logger.getLogger(MatriculaModel.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public BuscarDialogoModel obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
