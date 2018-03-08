/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.gestionacademica.model;

import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.report.ReporteCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.gestionacademica.panel.ReporteDeudasEstudiantePanel;
import ec.com.codesoft.codefaclite.gestionacademica.reportdata.ReporteDeudasEstudianteData;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.EstudianteInscrito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.NivelAcademico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroEstudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.EstudianteInscritoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.NivelAcademicoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RubroEstudianteServiceIf;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
public class ReporteDeudasEstudianteModel extends ReporteDeudasEstudiantePanel {

    private DefaultTableModel modeloTablaDeudas;
    private List<RubroEstudiante> dataRubroEstudiante;
    Map parameters = new HashMap();

    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        try {
            List<Periodo> periodos = ServiceFactory.getFactory().getPeriodoServiceIf().obtenerTodos();
            getCmbNivelAcademico().removeAllItems();
            for (Periodo periodo : periodos) {
                getCmbPeriodo().addItem(periodo);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(ReporteAcademicoModel.class.getName()).log(Level.SEVERE, null, ex);
        }

        getCmbPeriodo().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Periodo periodo = (Periodo) getCmbPeriodo().getSelectedItem();
                if (periodo != null) {
                    cargarNivelesPeriodo(periodo, getCmbNivelAcademico());
                }
            }
        });

        getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Vector<String> titulo = new Vector<>();
                    titulo.add("Identificación");
                    titulo.add("Estudiante");
                    titulo.add("Nivel Académico");
                    titulo.add("Rubro");
                    titulo.add("Valor");

                    modeloTablaDeudas = new DefaultTableModel(titulo, 0);

                    RubroEstudianteServiceIf na = ServiceFactory.getFactory().getRubroEstudianteServiceIf();
                    dataRubroEstudiante = na.obtenerDeudasEstudiante((NivelAcademico) getCmbNivelAcademico().getSelectedItem());
                    for (RubroEstudiante rubro : dataRubroEstudiante) {
                        Vector<String> fila = new Vector<String>();
                        if (rubro.getEstudianteInscrito().getEstudiante() != null) {
                            fila.add(rubro.getEstudianteInscrito().getEstudiante().getCedula());
                            fila.add(rubro.getEstudianteInscrito().getEstudiante().getNombres() + " " + rubro.getEstudianteInscrito().getEstudiante().getApellidos());
                            fila.add(rubro.getEstudianteInscrito().getNivelAcademico().getNombre());
                        }
                        if (rubro.getRubroNivel() != null) {
                            fila.add(rubro.getRubroNivel().getNombre());
                            fila.add(rubro.getRubroNivel().getValor().toString());
                        }

                        modeloTablaDeudas.addRow(fila);

                    }

                    getTblDeudas().setModel(modeloTablaDeudas);
                } catch (RemoteException ex) {
                    Logger.getLogger(ReporteAcademicoModel.class.getName()).log(Level.SEVERE, null, ex);
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
            InputStream path = RecursoCodefac.JASPER_ACADEMICO.getResourceInputStream("reporte_deudas.jrxml");

            RubroEstudianteServiceIf na = ServiceFactory.getFactory().getRubroEstudianteServiceIf();
            dataRubroEstudiante = na.obtenerDeudasEstudiante((NivelAcademico) getCmbNivelAcademico().getSelectedItem());
            List<ReporteDeudasEstudianteData> data = new ArrayList<ReporteDeudasEstudianteData>();

            for (RubroEstudiante rubro : dataRubroEstudiante) {
                
                data.add(new ReporteDeudasEstudianteData(
                        rubro.getEstudianteInscrito().getNivelAcademico().getNombre(),
                        rubro.getEstudianteInscrito().getEstudiante().getCedula(),
                        rubro.getEstudianteInscrito().getEstudiante().getNombres() + " " + rubro.getEstudianteInscrito().getEstudiante().getApellidos(),
                        rubro.getRubroNivel().getNombre(),
                        rubro.getRubroNivel().getValor().toString()
                ));

            }

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
            for (NivelAcademico resultado : resultados) {
                comboNivel.addItem(resultado);

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
