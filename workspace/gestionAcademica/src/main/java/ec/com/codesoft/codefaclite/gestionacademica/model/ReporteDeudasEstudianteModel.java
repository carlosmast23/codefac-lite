/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.gestionacademica.model;

import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.report.ReporteCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.gestionacademica.panel.ReporteDeudasPanel;
import ec.com.codesoft.codefaclite.gestionacademica.reportdata.ReporteDeudasData;
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
public class ReporteDeudasEstudianteModel extends ReporteDeudasPanel {

    Map parameters = new HashMap();
    private boolean banderaNiveles = false;
    private boolean banderaRubros = false;
    Estudiante estudiante=null;

    @Override
    public void iniciar() throws ExcepcionCodefacLite {

        getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Vector<String> titulo = new Vector<>();
                    titulo.add("Rubro");
                    titulo.add("Valor");

                    DefaultTableModel modeloTablaDeudas = new DefaultTableModel(titulo, 0);

                    Map<String, Object> mapParametros2 = new HashMap<String, Object>();
                    mapParametros2.put("estudianteInscrito", estudiante);
                    if (banderaRubros == false) {
                        mapParametros2.put("rubroNivel", (RubrosNivel) getCmbRubrosNivel().getSelectedItem());
                    }
                    RubroEstudianteServiceIf rs = ServiceFactory.getFactory().getRubroEstudianteServiceIf();
                    List<RubroEstudiante> dataRubro = rs.obtenerPorMap(mapParametros2);
                    // comparamos si el estudiante tiene rubros
                    if (!dataRubro.isEmpty()) {
                        for (RubroEstudiante re : dataRubro) {
                            Vector<String> fila2 = new Vector<String>();
                            fila2.add("-");
                            fila2.add("-");
                            fila2.add("-");
                            fila2.add(re.getRubroNivel().getNombre());
                            fila2.add(re.getRubroNivel().getValor().toString());
                            modeloTablaDeudas.addRow(fila2);
                        }
                        getTblDeudas().setModel(modeloTablaDeudas);

                    }

                } catch (RemoteException ex) {
                    Logger.getLogger(ReporteAcademicoModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ServicioCodefacException ex) {
                    Logger.getLogger(ReporteDeudasEstudianteModel.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

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
            InputStream path = RecursoCodefac.JASPER_ACADEMICO.getResourceInputStream("reporte_deudas2.jrxml");

            Map<String, Object> mapParametros = new HashMap<String, Object>();
            if (banderaNiveles == false) {
                mapParametros.put("nivelAcademico", (NivelAcademico) getCmbNivelAcademico().getSelectedItem());
            }
            EstudianteInscritoServiceIf na = ServiceFactory.getFactory().getEstudianteInscritoServiceIf();
            List<EstudianteInscrito> dataEstudiante = na.obtenerPorMap(mapParametros);

            List<ReporteDeudasData> data = new ArrayList<ReporteDeudasData>();
            for (EstudianteInscrito estudiante : dataEstudiante) {

                Map<String, Object> mapParametros2 = new HashMap<String, Object>();
                mapParametros2.put("estudianteInscrito", estudiante);
                if (banderaRubros == false) {
                    mapParametros2.put("rubroNivel", (RubrosNivel) getCmbRubrosNivel().getSelectedItem());
                }
                RubroEstudianteServiceIf rs = ServiceFactory.getFactory().getRubroEstudianteServiceIf();
                List<RubroEstudiante> dataRubro = rs.obtenerPorMap(mapParametros2);
                // comparamos si el estudiante tiene rubros
                if (!dataRubro.isEmpty()) {
                    for (RubroEstudiante re : dataRubro) {
                        data.add(new ReporteDeudasData(
                                estudiante.getNivelAcademico().getNombre(),
                                estudiante.getEstudiante().getCedula(),
                                estudiante.getEstudiante().getNombreCompleto(),
                                re.getRubroNivel().getNombre(),
                                re.getRubroNivel().getValor().toString()
                        ));
                    }
                }

            }
//            List<ReporteDeudasEstudianteData> data = new ArrayList<ReporteDeudasEstudianteData>();

            Periodo periodo = (Periodo) getCmbPeriodo().getSelectedItem();
            NivelAcademico nivela = (NivelAcademico) getCmbNivelAcademico().getSelectedItem();
            if (periodo != null) {
                parameters.put("periodo", periodo.getNombre());
            } else {
                parameters.put("periodo", "TODOS");
            }

            if (nivela != null) {
                parameters.put("nivelacademico", nivela.getNombre());
            } else {
                parameters.put("nivelacademico", "TODOS");
            }

            ReporteCodefac.generarReporteInternalFramePlantilla(path, parameters, data, panelPadre, "Reporte Deudas");
        } catch (RemoteException ex) {
            Logger.getLogger(ReporteAcademicoModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ReporteDeudasEstudianteModel.class.getName()).log(Level.SEVERE, null, ex);
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

    @Override
    public String getNombre() {
        return "Reporte Deudas por Estudiante";
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
            List<NivelAcademico> resultados = servicio.obtenerPorMap(mapBusqueda);

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

}
