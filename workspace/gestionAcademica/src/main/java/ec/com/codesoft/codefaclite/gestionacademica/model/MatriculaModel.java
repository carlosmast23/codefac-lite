/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.gestionacademica.model;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.gestionacademica.other.RowsRenderer;
import ec.com.codesoft.codefaclite.gestionacademica.panel.MatriculaPanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Estudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.EstudianteInscrito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Nivel;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.NivelAcademico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.NivelEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.EstudianteInscritoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.NivelAcademicoServiceIf;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Carlos
 */
public class MatriculaModel extends MatriculaPanel{
    
    /**
     * Periodo por defecto para manejar cuando el estudiante no esta inscrito o es nuevo
     */
    private Periodo periodoNuevosEstudiantes;
    
    
    private Periodo periodoTodosSinMatricular;
    
    
    private DefaultTableModel modeloTablaSinMatricula;
    
    /**
     * Lista que va a contener los nuevos estudiantes agregado para grabar y matricular
     */
    private Map<NivelAcademico,List<Estudiante>> mapEstudiantesAgregadosMatricula;
    
    private List<Estudiante> estudiantesSinMatricular;
    
    private List<Estudiante> estudiantesMatriculados;
    

    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        limpiarVariables();
        crearVariablesPorDefecto();
        iniciarListener();
        cargarValoresIniciales();
        
    }
    
   private void crearVariablesPorDefecto()
   {
       periodoNuevosEstudiantes=new Periodo();
       periodoNuevosEstudiantes.setNombre("Nuevos Estudiante");
       
       periodoTodosSinMatricular=new Periodo();
       periodoTodosSinMatricular.setNombre("Sin Matricular");
       
       getTblAlumnosConMatricula().setDefaultRenderer(Object.class,new RowsRenderer(1));
   }

    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        if(mapEstudiantesAgregadosMatricula.size()==0)
        {
            DialogoCodefac.mensaje("Advertencia","No existen datos para matricular",DialogoCodefac.MENSAJE_ADVERTENCIA);
            throw new ExcepcionCodefacLite("Cancelado metodo grabar");
        }
        else
        {
            try {
                ServiceFactory.getFactory().getEstudianteInscritoServiceIf().matricularEstudiantesByMap(mapEstudiantesAgregadosMatricula);
                DialogoCodefac.mensaje("Correcto","Los estudiantes fueron grabados correctamente",DialogoCodefac.MENSAJE_ADVERTENCIA);
            } catch (RemoteException ex) {
                DialogoCodefac.mensaje("Error","No existe comunicación con el servidor",DialogoCodefac.MENSAJE_ADVERTENCIA);
                Logger.getLogger(MatriculaModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        limpiarVariables();
        //System.out.println("Tam:" + getCmbPeriodoAnterior().getModel().getSize());
        getCmbPeriodoAnterior().setSelectedIndex(0);
        cargarTablaSinMatricula();
        cargarTablaSinMatricula();
    }
    
    private void limpiarVariables()
    {
        mapEstudiantesAgregadosMatricula = new HashMap<NivelAcademico,List<Estudiante>>();
        estudiantesMatriculados = new ArrayList<Estudiante>();
        estudiantesSinMatricular = new ArrayList<Estudiante>();
    }

    @Override
    public String getNombre() {
        return "Matricula por grupo";
    }

    @Override
    public String getURLAyuda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        Map<Integer, Boolean> permisos = new HashMap<Integer, Boolean>();
        permisos.put(GeneralPanelInterface.BOTON_NUEVO, true);
        permisos.put(GeneralPanelInterface.BOTON_GRABAR, true);
        permisos.put(GeneralPanelInterface.BOTON_BUSCAR, true);
        permisos.put(GeneralPanelInterface.BOTON_ELIMINAR, true);
        permisos.put(GeneralPanelInterface.BOTON_IMPRIMIR, true);
        permisos.put(GeneralPanelInterface.BOTON_AYUDA, true);
        return permisos;
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void iniciarListener() {
        iniciarListenerBotones();
        iniciarListenerCombos();
       
    }

    private void iniciarListenerBotones() {
        
        getBtnRegresar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel modeloTabla = (DefaultTableModel) getTblAlumnosConMatricula().getModel();
                for (int i = 0; i < modeloTabla.getRowCount(); i++) {
                    Boolean seleccionado = (Boolean) modeloTabla.getValueAt(i, 0);

                    if (seleccionado) {
                        Estudiante estudiante = (Estudiante) modeloTabla.getValueAt(i,2);
                        List<Estudiante> estudiantesAgregadosMatricula=obtenerTodosEstudiantesAgregados();
                        if (estudiantesAgregadosMatricula.contains(estudiante)) {
                            quitarEstudianteParaMatricular(estudiante);
                        }
                        else
                        {
                            DialogoCodefac.mensaje("Advertencia","Solo se pueden quitar estudiantes sin grabar",DialogoCodefac.MENSAJE_INCORRECTO);
                        }
                    }
                }
                cargarTablaMatriculados();
                cargarTablaSinMatricula();
            }
        });
        
        getBtnPasar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Obtener todos los datos Seleccionados
                NivelAcademico nivelAcademicoParaMatricular= (NivelAcademico) getCmbNivelMatricula().getSelectedItem();
                DefaultTableModel modeloDatos= (DefaultTableModel) getTblAlumnosSinMatricula().getModel();
                for (int i = 0; i < modeloDatos.getRowCount(); i++) {
                    Boolean seleccionado=(Boolean) modeloDatos.getValueAt(i,0);
                    if(seleccionado)
                    {
                        Estudiante estudiante=(Estudiante) modeloDatos.getValueAt(i,1);
                        agregarEstudiantesMatricula(estudiante,nivelAcademicoParaMatricular);                        
                    }
                }
                
                cargarTablaMatriculados();
                cargarTablaSinMatricula();
            }
        });
    }
    
   
    private void agregarEstudiantesMatricula(Estudiante estudiante, NivelAcademico nivelAcademico)
    {
        List<Estudiante> listaEstudiantes=mapEstudiantesAgregadosMatricula.get(nivelAcademico);
        if(listaEstudiantes==null)
        {
            listaEstudiantes=new ArrayList<Estudiante>();
            listaEstudiantes.add(estudiante);
            mapEstudiantesAgregadosMatricula.put(nivelAcademico, listaEstudiantes);
        }
        else
        {
            listaEstudiantes.add(estudiante);
        }
    }
    
    
    public void quitarEstudianteParaMatricular(Estudiante estudiante)
    {
        for (Map.Entry<NivelAcademico, List<Estudiante>> entry : mapEstudiantesAgregadosMatricula.entrySet()) {
            NivelAcademico key = entry.getKey();
            List<Estudiante> lista = entry.getValue();
            
            for (Estudiante estudianteTemp : lista) {
                if(estudianteTemp.equals(estudiante))
                {
                    lista.remove(estudiante);
                }
            }
        }
    }

    private void cargarValoresIniciales() {
        try {
            //Cargar los combos de los niveles
            List<Periodo> niveles=ServiceFactory.getFactory().getPeriodoServiceIf().obtenerTodos();
            getCmbPeriodoAnterior().removeAllItems();
            getCmbPeriodoSiguiente().removeAllItems();
            
            getCmbPeriodoAnterior().addItem(periodoNuevosEstudiantes);
            getCmbPeriodoAnterior().addItem(periodoTodosSinMatricular);
            for (Periodo nivel : niveles) 
            {
                if(NivelEnumEstado.getEnum(nivel.getEstado()).equals(NivelEnumEstado.INACTIVO))
                {
                    getCmbPeriodoAnterior().addItem(nivel);
                }
                
                //Solo cargar los niveles activos en este combo
                if(NivelEnumEstado.getEnum(nivel.getEstado()).equals(NivelEnumEstado.ACTIVO))
                {
                    getCmbPeriodoSiguiente().addItem(nivel);
                }
            }
            
           
                        
        } catch (RemoteException ex) {
            Logger.getLogger(MatriculaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void iniciarListenerCombos() {
        getCmbPeriodoSiguiente().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Periodo periodo= (Periodo) getCmbPeriodoSiguiente().getSelectedItem();
                if(periodo!=null)
                {
                    cargarNivelesPeriodo(periodo,getCmbNivelMatricula());
                }
            }
        });
        
        
        getCmbPeriodoAnterior().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Periodo periodo= (Periodo) getCmbPeriodoAnterior().getSelectedItem();
                if(periodo!=null)
                {
                    //Cuando no sea busqueda por periodo descativo el combo de los niveles
                    if(periodoNuevosEstudiantes.equals(periodo) || periodoTodosSinMatricular.equals(periodo))
                    {
                        if(periodoNuevosEstudiantes.equals(periodo))
                        {
                            cargarNuevosEstudiantes();
                        }
                        
                        if(periodoTodosSinMatricular.equals(periodo))
                        {
                            cargarEstudiantesSinMatricula();
                        }
                    }
                    else
                    {
                        getCmbNivel().setEnabled(true);
                        cargarNivelesPeriodo(periodo,getCmbNivel());
                    }
                }
                
            }

        });
        
        
        getCmbNivel().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        
        
        getCmbNivelMatricula().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NivelAcademico nivelAcademico = (NivelAcademico) getCmbNivelMatricula().getSelectedItem();
                if (nivelAcademico != null) {
                    try {
                        Map<String, Object> mapParametros = new HashMap<String, Object>();
                        mapParametros.put("nivelAcademico", nivelAcademico);
                        EstudianteInscritoServiceIf servicio= ServiceFactory.getFactory().getEstudianteInscritoServiceIf();
                        List<EstudianteInscrito> estudiantesInscritos = servicio.obtenerPorMap(mapParametros);
                        estudiantesMatriculados.clear();
                        for (EstudianteInscrito estudiantesInscrito : estudiantesInscritos) {
                            estudiantesMatriculados.add(estudiantesInscrito.getEstudiante());
                        }
                        cargarTablaMatriculados();
                        cargarTablaSinMatricula();
                        
                    } catch (RemoteException ex) {
                        Logger.getLogger(MatriculaModel.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ServicioCodefacException ex) {
                        Logger.getLogger(MatriculaModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        
    }
    
    private void cargarEstudiantesSinMatricula() {
        Periodo periodo = (Periodo) getCmbPeriodoAnterior().getSelectedItem();
        if (periodo.equals(periodoNuevosEstudiantes)) {
            try {
                estudiantesSinMatricular = ServiceFactory.getFactory().getEstudianteServiceIf().estudianteSinMatriculaPorPeriodo(periodo);
                cargarTablaMatriculados();
                cargarTablaSinMatricula();
                
            } catch (RemoteException ex) {
                Logger.getLogger(MatriculaModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void cargarNuevosEstudiantes()
    {
        Periodo periodo = (Periodo) getCmbPeriodoAnterior().getSelectedItem();
        if (periodo.equals(periodoNuevosEstudiantes)) {
            try {
                estudiantesSinMatricular = ServiceFactory.getFactory().getEstudianteServiceIf().estudianteNuevosSinMatricula();
                cargarTablaMatriculados();
                cargarTablaSinMatricula();
                
            } catch (RemoteException ex) {
                Logger.getLogger(MatriculaModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private List<Estudiante> obtenerTodosEstudiantesAgregados()
    {
        List<Estudiante> listaEstudiantes=new ArrayList<Estudiante>();
        for (Map.Entry<NivelAcademico, List<Estudiante>> entry : mapEstudiantesAgregadosMatricula.entrySet()) {
            NivelAcademico key = entry.getKey();
            List<Estudiante> value = entry.getValue();
            
            for (Estudiante estudiante : value) {
                listaEstudiantes.add(estudiante);
            }
        }
        return listaEstudiantes;
    }
   
    private void cargarTablaSinMatricula()
    {
        String[] titulos={"Opcion","Estudiante"};
        this.modeloTablaSinMatricula=crearModeloTabla(titulos,new Class[]{Boolean.class,Estudiante.class});
        
        DefaultListModel<Estudiante> listaEstudiantes=new DefaultListModel<Estudiante>();
        List<Estudiante> estudiantesAgregadosMatricula=obtenerTodosEstudiantesAgregados();
        for (Estudiante estudiante : estudiantesSinMatricular) {
            //Agregar los estudiantes a la tabla sin matricula solo si no estan en la lista de estudiante sin matricular
            if(!estudiantesAgregadosMatricula.contains(estudiante))
            {
                Object[] fila={new Boolean(false),estudiante};
                modeloTablaSinMatricula.addRow(fila);
            }
       }
       getTblAlumnosSinMatricula().setModel(modeloTablaSinMatricula);
    }
    
    private void cargarTablaMatriculados()
    {
        String[] titulos={"Opcion","Grabado","Estudiante"};
        this.modeloTablaSinMatricula=crearModeloTabla(titulos,new Class[]{Boolean.class,String.class,Estudiante.class});
        
        DefaultListModel<Estudiante> listaEstudiantes=new DefaultListModel<Estudiante>();
        
        //Agregar la lista de los estudiantes por matricular
        NivelAcademico nivelAcademico=(NivelAcademico) getCmbNivelMatricula().getSelectedItem();
        List<Estudiante> estudiantesAgregadosMatricula=mapEstudiantesAgregadosMatricula.get(nivelAcademico);
        
        //Si no existen datos agregados no carga nada
        if(estudiantesAgregadosMatricula!=null)
        {
            for (Estudiante estudiante : estudiantesAgregadosMatricula) {
                Object[] fila = {new Boolean(false), "No", estudiante};
                modeloTablaSinMatricula.addRow(fila);
            }
        }
        
        //Agregar la lista de los estudiantes matriculados
        for (Estudiante estudiante : estudiantesMatriculados) {
            //Agregar los estudiantes a la tabla sin matricula solo si no estan en la lista de estudiante sin matricular
            Object[] fila = {new Boolean(false), "Si", estudiante};
            modeloTablaSinMatricula.addRow(fila);
       }
       getTblAlumnosConMatricula().setModel(modeloTablaSinMatricula);
        
    
    }

    private DefaultTableModel crearModeloTabla(String titulos[],Class[] tipoDatoFilas)
    {
         DefaultTableModel defaultTableModel=new javax.swing.table.DefaultTableModel(titulos,0) 
         {
             public Class getColumnClass(int columnIndex) {
                return tipoDatoFilas [columnIndex];
            }
        };    
         return defaultTableModel;
    }
    
    private void cargarNivelesPeriodo(Periodo periodo,JComboBox<NivelAcademico> comboNivel)
    {
        try {

            NivelAcademicoServiceIf servicio= ServiceFactory.getFactory().getNivelAcademicoServiceIf();
            Map<String,Object> mapBusqueda=new HashMap<String,Object>();
            mapBusqueda.put("periodo",periodo);
            List<NivelAcademico> resultados=servicio.obtenerPorMap(mapBusqueda);
            
            comboNivel.removeAllItems();
            for (NivelAcademico resultado : resultados) {
                comboNivel.addItem(resultado);
            }
            
        } catch (RemoteException ex) {
            Logger.getLogger(MatriculaModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(MatriculaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
