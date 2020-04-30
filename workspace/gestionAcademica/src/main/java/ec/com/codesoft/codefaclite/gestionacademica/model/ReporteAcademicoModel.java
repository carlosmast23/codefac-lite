/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.gestionacademica.model;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.excel.Excel;
import ec.com.codesoft.codefaclite.controlador.model.ReporteDialogListener;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
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
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
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
import java.util.TreeMap;
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

    /**
     * Referencia que guardar el periodo por defecto todos
     */
    private Periodo periodoActivo;
    private NivelAcademico defaultTodos;
    private DefaultTableModel modeloTablaEstudiantes;
    private List<EstudianteInscrito> dataEstudiantes;

    List<ReporteAcademicoData> dataReporte;

    Map parameters = new HashMap();

    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        iniciarValores();
        listenerBotones();
        cargarDefecto();
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

    public void generarConsulta() {
        try {

            EstudianteInscritoServiceIf na = ServiceFactory.getFactory().getEstudianteInscritoServiceIf();

            NivelAcademico nivelAcademico = (NivelAcademico) getCmbNivelAcademico().getSelectedItem();

            Periodo periodo = (Periodo) getCmbPeriodo().getSelectedItem();
            dataEstudiantes = na.obtenerEstudiantesInscritos(nivelAcademico.getNombre().equals("TODOS") ? null : nivelAcademico, periodo);
            
            dataReporte=new ArrayList<ReporteAcademicoData>();
            for (EstudianteInscrito est : dataEstudiantes) {
                String nombreRepresentante = "s/n";

                if (est.getEstudiante().getRepresentante() != null) {
                    nombreRepresentante = est.getEstudiante().getRepresentante().getNombresCompletos();
                }

                String telefono = (est.getEstudiante().getRepresentante() != null) ? est.getEstudiante().getRepresentante().obtenerTodosTelefonos() : "";
                telefono = (telefono == null) ? "" : telefono;
                
                String email = (est.getEstudiante().getRepresentante() != null) ? est.getEstudiante().getRepresentante().getCorreoElectronico() : "";
                email=(email==null)?"":email;
                
                dataReporte.add(new ReporteAcademicoData(
                        est.getEstudiante().getCedula(),
                        est.getEstudiante().getNombres(),
                        est.getEstudiante().getApellidos(),
                        email,
                        telefono,
                        nombreRepresentante,
                        est.getNivelAcademico().getNombre()
                ));

            }

            getTblEstudiantes().setModel(modeloTablaEstudiantes);
        } catch (RemoteException ex) {
            Logger.getLogger(ReporteAcademicoModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void imprimir() {
        InputStream path = RecursoCodefac.JASPER_ACADEMICO.getResourceInputStream("reporteMatriculados.jrxml");
        Periodo periodo = (Periodo) getCmbPeriodo().getSelectedItem();
        
        //Periodo periodo = (Periodo) getCmbPeriodo().getSelectedItem();
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
        
        DialogoCodefac.dialogoReporteOpciones(new ReporteDialogListener() {
            @Override
            public void excel() {
                try {
                    Excel excel = new Excel();
                    String[] nombreCabeceras = {" Nivel ", " Cédula ", " Apellidos ", " Nombres ", " Email ", " Telefono ", " Representante "};
                    //List<ReporteAcademicoData> dat = ordenarDetallesEnFuncionDeCliente(data);
                    excel.gestionarIngresoInformacionExcel(nombreCabeceras, dataReporte);
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

                ReporteCodefac.generarReporteInternalFramePlantilla(path, parameters, dataReporte, panelPadre, "Estudiantes Matriculados");
            }
        });
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
        return "Estudiantes Matriculados";
    }

    @Override
    public String getURLAyuda() {
        return "https://docs.google.com/document/d/e/2PACX-1vRxHiHd5vpEu1In25BKtCXigpl4m1phGAZwNR7Rh2Jm-Xqe7ffQpivlYJsMAWHFBS0BOnYxj4dpUi7H/pub?embedded=true#h.dc7l4ak532l6";
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
            //Map<String, Object> mapBusqueda = new HashMap<String, Object>();
            //mapBusqueda.put("periodo", periodo);
            //mapBusqueda.put("estado", GeneralEnumEstado.ACTIVO.getEstado());

            List<NivelAcademico> resultados = servicio.buscarPorPeriodo(periodo);
            comboNivel.removeAllItems();

            comboNivel.addItem(defaultTodos);
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

    private void listenerBotones() {
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
                Vector<String> titulo = new Vector<>();
                titulo.add("Cedula");
                titulo.add("Apellidos");
                titulo.add("Nombres");
                titulo.add("Email");
                titulo.add("Telefono");
                titulo.add("Representante");
                titulo.add("Nivel Academico");
                modeloTablaEstudiantes = new DefaultTableModel(titulo, 0);
                generarConsulta();
                for (ReporteAcademicoData est : dataReporte) {
                    Vector<String> fila = new Vector<String>();
                    
                    fila.add(est.getCedulaEstudiante());
                    fila.add(est.getApellidosEstudiante());
                    fila.add(est.getNombresEstudiante());
                    fila.add(est.getEmailEstudiante());
                    fila.add(est.getTelefonoEstudiante());
                    fila.add(est.getRepresentanteEstudiante());
                    fila.add(est.getNivelAcademicoEstudiante());
                    
                    modeloTablaEstudiantes.addRow(fila);
                    
                }
                getTblEstudiantes().setModel(modeloTablaEstudiantes);

            }
        });
    }

    private void cargarDefecto() {

        try {
            List<Periodo> periodos = ServiceFactory.getFactory().getPeriodoServiceIf().obtenerPeriodosSinEliminar();
            getCmbPeriodo().removeAllItems();

            for (Periodo periodo : periodos) {
                getCmbPeriodo().addItem(periodo);
            }
            getCmbPeriodo().setSelectedItem(periodoActivo);

        } catch (RemoteException ex) {
            Logger.getLogger(ReporteAcademicoModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void iniciarValores() {
        try {
            defaultTodos = new NivelAcademico();
            defaultTodos.setNombre("TODOS");

            periodoActivo = ServiceFactory.getFactory().getPeriodoServiceIf().obtenerUnicoPeriodoActivo();
        } catch (RemoteException ex) {
            Logger.getLogger(ReporteAcademicoModel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public List<ReporteAcademicoData> ordenarDetallesEnFuncionDeCliente(List<ReporteAcademicoData> data) {
        List<ReporteAcademicoData> dataTemp = new ArrayList<>();
        Map<String, List<ReporteAcademicoData>> dataEstudiantesCurso;
        //Metodo que permite ordentar los maps por las proveedores
        dataEstudiantesCurso = new TreeMap<String, List<ReporteAcademicoData>>(new Comparator<String>() {
            @Override
            public int compare(String c1, String c2) {
                return c1.compareTo(c2);
            }
        });

        for (ReporteAcademicoData rd : data) {
            if (dataEstudiantesCurso.get(rd.getNivelAcademicoEstudiante()) == null) {
                List<ReporteAcademicoData> estudiantesCurso = new ArrayList<ReporteAcademicoData>();
                estudiantesCurso.add(rd); //Agrego el dato a la nueva lista
                dataEstudiantesCurso.put(rd.getNivelAcademicoEstudiante(), estudiantesCurso); //Agredo el proveedor, con el detalle
            } else {
                dataEstudiantesCurso.get(rd.getNivelAcademicoEstudiante()).add(rd);
            }
        }
        boolean b = true;
        for (Map.Entry<String, List<ReporteAcademicoData>> entry : dataEstudiantesCurso.entrySet()) {
            String key = entry.getKey();
            List<ReporteAcademicoData> value = entry.getValue();
            for (ReporteAcademicoData reporteAcademicoData : value) {
                dataTemp.add(reporteAcademicoData);
            }
            b = true;
        }

        return dataTemp;
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
