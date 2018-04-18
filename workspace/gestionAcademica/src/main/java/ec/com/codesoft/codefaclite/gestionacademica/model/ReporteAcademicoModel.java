/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.gestionacademica.model;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.excel.Excel;
import ec.com.codesoft.codefaclite.controlador.model.ReporteDialogListener;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.report.ReporteCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.gestionacademica.panel.ReporteAcademicoPanel;
import ec.com.codesoft.codefaclite.gestionacademica.reportdata.ReporteAcademicoData;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Estudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.EstudianteInscrito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.NivelAcademico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.EstudianteInscritoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.NivelAcademicoServiceIf;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
public class ReporteAcademicoModel extends ReporteAcademicoPanel {

    private DefaultTableModel modeloTablaEstudiantes;
    private List<EstudianteInscrito> dataEstudiantes;
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
                    titulo.add("Cedula");
                    titulo.add("Nombres");
                    titulo.add("Apellidos");
                    titulo.add("Email");
                    titulo.add("Telefono");
                    titulo.add("Representante");
                    titulo.add("Nivel Academico");

                    modeloTablaEstudiantes = new DefaultTableModel(titulo, 0);

                    EstudianteInscritoServiceIf na = ServiceFactory.getFactory().getEstudianteInscritoServiceIf();
                    dataEstudiantes = na.obtenerEstudiantesInscritos((NivelAcademico) getCmbNivelAcademico().getSelectedItem());
                    for (EstudianteInscrito est : dataEstudiantes) {
                        Vector<String> fila = new Vector<String>();
                        fila.add(est.getEstudiante().getCedula());
                        fila.add(est.getEstudiante().getNombres());
                        fila.add(est.getEstudiante().getApellidos());
                        fila.add(est.getEstudiante().getEmail());
                        fila.add(est.getEstudiante().getTelefono());
                        if (est.getEstudiante().getRepresentante() != null) {
                            fila.add(est.getEstudiante().getRepresentante().getNombres() + " " + est.getEstudiante().getRepresentante().getApellidos());
                        } else {
                            fila.add("s/n");
                        }
                        fila.add(est.getNivelAcademico().getNombre());
                        modeloTablaEstudiantes.addRow(fila);

                    }

                    getTblEstudiantes().setModel(modeloTablaEstudiantes);
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
            InputStream path = RecursoCodefac.JASPER_ACADEMICO.getResourceInputStream("reporte_academico.jrxml");

            EstudianteInscritoServiceIf na = ServiceFactory.getFactory().getEstudianteInscritoServiceIf();
            dataEstudiantes = na.obtenerEstudiantesInscritos((NivelAcademico) getCmbNivelAcademico().getSelectedItem());
            List<ReporteAcademicoData> data = new ArrayList<ReporteAcademicoData>();

            for (EstudianteInscrito est : dataEstudiantes) {
                
                String nombreRepresentante="s/n";
                
                if(est.getEstudiante().getRepresentante()!=null)
                    nombreRepresentante=est.getEstudiante().getRepresentante().getNombresCompletos();
                
                data.add(new ReporteAcademicoData(
                        est.getEstudiante().getCedula(),
                        est.getEstudiante().getNombres(),
                        est.getEstudiante().getApellidos(),
                        est.getEstudiante().getEmail(),
                        est.getEstudiante().getTelefono(),
                        nombreRepresentante,
                        est.getNivelAcademico().getNombre()
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
            
            //Ordenar Datos en base a un parametro establecido
            Collections.sort(data, new Comparator<ReporteAcademicoData>(){
                public int compare(ReporteAcademicoData obj1, ReporteAcademicoData obj2)
                {
                    return obj1.getNombresEstudiante().compareTo(obj2.getNombresEstudiante());
                }
            });
            //---
            
            DialogoCodefac.dialogoReporteOpciones(new ReporteDialogListener() {
                @Override
                public void excel() {
                    try {
                        Excel excel = new Excel();
                        String[] nombreCabeceras = {" Cédula ", " Nombres ", " Apellidos "," Email "," Telefono "," Representante "};
                        excel.gestionarIngresoInformacionExcel(nombreCabeceras, data);
                        excel.abrirDocumento();
                    } catch (IOException ex) {
                        Logger.getLogger(ReporteAcademicoData.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalArgumentException ex) {
                        Logger.getLogger(ReporteAcademicoData.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(ReporteAcademicoData.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                @Override
                public void pdf() {
                    ReporteCodefac.generarReporteInternalFramePlantilla(path, parameters, data, panelPadre, "Reporte Academico");
                }
            });
            
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
        return "Reporte Estudiantes Matriculados"; 
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
