/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.gestionacademica.model;

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
    private Periodo periodoNinguno;
    
    /**
     * Nivel por defecto para cargar todos los alumnos sin nivel
     */
    private NivelAcademico nivelNinguno;
    
    private DefaultTableModel modeloTablaSinMatricula;
    
    /**
     * Lista que va a contener los nuevos estudiantes agregado para grabar y matricular
     */
    private List<Estudiante> estudiantesAgregados;

    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        crearVariablesPorDefecto();
        iniciarListener();
        cargarValoresIniciales();
        
    }
    
   private void crearVariablesPorDefecto()
   {
       periodoNinguno=new Periodo();
       periodoNinguno.setNombre("Ninguno");
       
       nivelNinguno=new NivelAcademico();
       nivelNinguno.setNombre("Ninguno");
       
       getTblAlumnosConMatricula().setDefaultRenderer(Object.class,new RowsRenderer(1));
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
        estudiantesAgregados=new ArrayList<Estudiante>();
    }

    @Override
    public String getNombre() {
        return "Matricula";
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
        getBtnPasar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Obtener todos los datos Seleccionados
                DefaultTableModel modeloDatos= (DefaultTableModel) getTblAlumnosSinMatricula().getModel();
                for (int i = 0; i < modeloDatos.getRowCount(); i++) {
                    Boolean seleccionado=(Boolean) modeloDatos.getValueAt(i,0);
                    if(seleccionado)
                    {
                        Estudiante estudiante=(Estudiante) modeloDatos.getValueAt(i,2);
                        estudiantesAgregados.add(estudiante);
                        actualizarTablaMatricula();
                    }
                }
            }
        });
    }
    
    private void actualizarTablaMatricula()
    {
        DefaultTableModel modeloTabla= (DefaultTableModel) getTblAlumnosConMatricula().getModel();
        for (Estudiante estudiantesAgregado : estudiantesAgregados) {
            
            modeloTabla.insertRow(0,new Object[]{false,"No",estudiantesAgregado});       
            //getTblAlumnosConMatricula().get
        }
        
        
    }

    private void cargarValoresIniciales() {
        try {
            //Cargar los combos de los niveles
            List<Periodo> niveles=ServiceFactory.getFactory().getPeriodoServiceIf().obtenerTodos();
            getCmbPeriodoAnterior().removeAllItems();
            getCmbPeriodoSiguiente().removeAllItems();
            
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
            //Agregar valor por defecto al combo de nivel anterior cuando los alumnos no estan inscritos en ninguna curso anterior
           
            getCmbPeriodoAnterior().addItem(periodoNinguno);
            
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
                    if(periodoNinguno.equals(periodo))
                    {
                        getCmbNivel().removeAllItems();
                        getCmbNivel().addItem(nivelNinguno);
                    }
                    else
                    {
                        cargarNivelesPeriodo(periodo,getCmbNivel());
                    }
                }
                
            }
        });
        
        
        getCmbNivel().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NivelAcademico nivelAcademico=(NivelAcademico) getCmbNivel().getSelectedItem();
                if(nivelAcademico.equals(nivelNinguno))
                {
                    try {
                        List<Estudiante> estudiantes=ServiceFactory.getFactory().getEstudianteServiceIf().estudianteSinMatriculaPorPeriodo(nivelAcademico.getPeriodo());
                        cargarTablaSinMatricula(estudiantes);
                    } catch (RemoteException ex) {
                        Logger.getLogger(MatriculaModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        
        
        getCmbNivelMatricula().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NivelAcademico nivelAcademico=(NivelAcademico) getCmbNivelMatricula().getSelectedItem();
                if(nivelAcademico.equals(nivelNinguno))
                {
                    try {
                        Map<String,Object> mapParametros=new HashMap<String,Object>();
                        mapParametros.put("NivelAcademico",nivelAcademico);
                        List<EstudianteInscrito> estudiantesInscritos= ServiceFactory.getFactory().getEstudianteInscritoServiceIf().obtenerPorMap(mapParametros);
                        cargarTablaConMatricula(estudiantesInscritos);
                    } catch (RemoteException ex) {
                        Logger.getLogger(MatriculaModel.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ServicioCodefacException ex) {
                        Logger.getLogger(MatriculaModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        
    }
    
   
    private void cargarTablaSinMatricula(List<Estudiante> estudiantes)
    {
        String[] titulos={"Opcion","Grabado","Estudiante"};
        this.modeloTablaSinMatricula=crearModeloTabla(titulos,new Class[]{Boolean.class,String.class,Estudiante.class});
        
        DefaultListModel<Estudiante> listaEstudiantes=new DefaultListModel<Estudiante>();
        for (Estudiante estudiante : estudiantes) {
            Object[] fila={new Boolean(false),"Si",estudiante};
            modeloTablaSinMatricula.addRow(fila);
       }
       getTblAlumnosSinMatricula().setModel(modeloTablaSinMatricula);
    }
    
    private void cargarTablaConMatricula(List<EstudianteInscrito> estudiantesInscritos)
    {
        String[] titulos={"Opcion","Estudiante"};
        this.modeloTablaSinMatricula=crearModeloTabla(titulos,new Class[]{Boolean.class,Estudiante.class});
        
        DefaultListModel<Estudiante> listaEstudiantes=new DefaultListModel<Estudiante>();
        for (EstudianteInscrito estudiante : estudiantesInscritos) {
            Object[] fila={new Boolean(false),"Si",estudiante.getEstudiante()};
            modeloTablaSinMatricula.addRow(fila);
       }
       getTblAlumnosSinMatricula().setModel(modeloTablaSinMatricula);
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
            NivelAcademico na=new NivelAcademico();
            na.getPeriodo();
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
