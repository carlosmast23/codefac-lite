/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.gestionacademica.model;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.gestionacademica.other.RubroEstudianteData;
import ec.com.codesoft.codefaclite.gestionacademica.panel.GestionarDeudasPanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.EstudianteInscrito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.NivelAcademico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroEstudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubrosNivel;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Carlos
 */
public class GestionarDeudasModel extends GestionarDeudasPanel{

    private List<RubroEstudianteData> listaEstudiantes;
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        iniciarListener();
        cargarValoresIniciales();
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        try {
            List<EstudianteInscrito> lista=getListaCrearRubrosEstudiantes();
            RubrosNivel rubroNivel=(RubrosNivel) getCmbRubrosNivel().getSelectedItem();            
            ServiceFactory.getFactory().getRubroEstudianteServiceIf().crearRubrosEstudiantes(lista,rubroNivel);
            DialogoCodefac.mensaje("Correcto","Los estudiantes se grabaron correctamente",DialogoCodefac.MENSAJE_CORRECTO);
        } catch (RemoteException ex) {      
            DialogoCodefac.mensaje("Error","No existe comunicación con el servidor",DialogoCodefac.MENSAJE_INCORRECTO);
            Logger.getLogger(GestionarDeudasModel.class.getName()).log(Level.SEVERE, null, ex);
            throw new ExcepcionCodefacLite("Cancelar grabar");
        }
        
    }
    
    private List<EstudianteInscrito> getListaCrearRubrosEstudiantes()
    {
        List<EstudianteInscrito> lista=new ArrayList<EstudianteInscrito>();
        if(listaEstudiantes!=null)
        {
            for (RubroEstudianteData estudiante : listaEstudiantes) {
                if(estudiante.isDatoModificado())
                {
                    //Solo agregar Estudiantes que agregaron rubros
                    if(estudiante.getEstadoFinal())
                    {
                        lista.add(estudiante.getEstudianteInscrito());
                    }
                }
            }
        }
        return lista;
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
        
    }

    @Override
    public void limpiar() {
        
    }

    @Override
    public String getNombre() {
        return "Gestionar Deudas";
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
        listenerCombos();
        listenerCheckBox();
    }

    private void cargarValoresIniciales() {
        try {
            List<Periodo> periodos= ServiceFactory.getFactory().getPeriodoServiceIf().obtenerPeriodoActivo();
            for (Periodo periodo : periodos) {
                getCmbPeriodo().addItem(periodo);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(GestionarDeudasModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private void listenerCombos() {
        
       
        getCmbPeriodo().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Periodo periodoSeleccionado = (Periodo) getCmbPeriodo().getSelectedItem();
                    Map<String, Object> mapParametros = new HashMap<String, Object>();
                    mapParametros.put("periodo", periodoSeleccionado);
                    List<NivelAcademico> niveles= ServiceFactory.getFactory().getNivelAcademicoServiceIf().obtenerPorMap(mapParametros);
                    
                    //Cargar todos los niveles disponibles para ese periodo activo
                    getCmbNivelAcademico().removeAllItems();
                    for (NivelAcademico nivel : niveles) {
                        getCmbNivelAcademico().addItem(nivel);
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(GestionarDeudasModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ServicioCodefacException ex) {
                    Logger.getLogger(GestionarDeudasModel.class.getName()).log(Level.SEVERE, null, ex);
                }
               
            }
        });
        
        getCmbNivelAcademico().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              
                try {
                    getCmbRubrosNivel().removeAllItems();
                    NivelAcademico nivelSeleccionado=(NivelAcademico) getCmbNivelAcademico().getSelectedItem();
                    if(nivelSeleccionado!=null)
                    {
                        Map<String, Object> mapParametros = new HashMap<String, Object>();
                        mapParametros.put("nivel", null);
                        mapParametros.put("periodo", nivelSeleccionado.getPeriodo());
                        List<RubrosNivel> rubrosSinNivel = ServiceFactory.getFactory().getRubrosNivelServiceIf().obtenerPorMap(mapParametros);
                        for (RubrosNivel rubrosNivel : rubrosSinNivel) {
                            getCmbRubrosNivel().addItem(rubrosNivel);
                        }

                        mapParametros.clear();
                        mapParametros.put("nivel", nivelSeleccionado.getNivel());
                        mapParametros.put("periodo", nivelSeleccionado.getPeriodo());
                        List<RubrosNivel> rubros = ServiceFactory.getFactory().getRubrosNivelServiceIf().obtenerPorMap(mapParametros);

                        //Agregar todos los rubros disponibles para el nivels
                        for (RubrosNivel rubro : rubros) {
                            getCmbRubrosNivel().addItem(rubro);
                        }
                    }                    
                    
                } catch (RemoteException ex) {
                    Logger.getLogger(GestionarDeudasModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ServicioCodefacException ex) {
                    Logger.getLogger(GestionarDeudasModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        getCmbRubrosNivel().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarTabla();
            }
        });
        
    }
    
    private void cargarTabla() {
        DefaultTableModel modeloTabla = crearModeloTabla(new String[]{"Opcion", "Alumno"}, new Class[]{Boolean.class, RubroEstudianteData.class});
        listaEstudiantes=new ArrayList<RubroEstudianteData>();
        try {            
            NivelAcademico nivelAcademico = (NivelAcademico) getCmbNivelAcademico().getSelectedItem();
            RubrosNivel rubroNivel = (RubrosNivel) getCmbRubrosNivel().getSelectedItem();

            if (nivelAcademico != null && rubroNivel != null) {
                Map<String, Object> mapParametros = new HashMap<String, Object>();
                mapParametros.put("nivelAcademico", nivelAcademico);
                List<EstudianteInscrito> estudiantesInscritos = ServiceFactory.getFactory().getEstudianteInscritoServiceIf().obtenerPorMap(mapParametros);
               
                for (EstudianteInscrito estudiantesInscrito : estudiantesInscritos) {
                    Map<String, Object> mapParametroNivelAcademico = new HashMap<String, Object>();
                    mapParametroNivelAcademico.put("estudianteInscrito", estudiantesInscrito);
                    mapParametroNivelAcademico.put("rubroNivel",rubroNivel);

                    List<RubroEstudiante> rubrosEstudiantes= ServiceFactory.getFactory().getRubroEstudianteServiceIf().obtenerPorMap(mapParametroNivelAcademico);
                    
                    if(rubrosEstudiantes.size()==0)
                    {
                        RubroEstudianteData rubroEstudianteData=new RubroEstudianteData(estudiantesInscrito,false);
                        listaEstudiantes.add(rubroEstudianteData);
                        //modeloTabla.addRow(new Object[]{false,estudiantesInscrito});
                    }
                    else
                    {
                        RubroEstudianteData rubroEstudianteData = new RubroEstudianteData(estudiantesInscrito,true);
                        listaEstudiantes.add(rubroEstudianteData);
                        //modeloTabla.addRow(new Object[]{true,estudiantesInscrito});
                    }
                }
                
                /**
                 * Agregar lista de los estudiantes y los rubros a la tabla
                 */
                for (RubroEstudianteData estudianteRubroData : listaEstudiantes) {
                    if(estudianteRubroData.getEstadoOriginal())
                    {
                        modeloTabla.addRow(new Object[]{true,estudianteRubroData});
                    }
                    else
                    {
                        modeloTabla.addRow(new Object[]{false,estudianteRubroData});
                    }
                }
                
            }
        } catch (RemoteException ex) {
            Logger.getLogger(GestionarDeudasModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(GestionarDeudasModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        getTblEstudiantes().setModel(modeloTabla);
        
        getTblEstudiantes().getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int fila = e.getFirstRow();
                int columna = e.getColumn();

                //Solo verificar si se modifico la columna del option listener
                if (columna == 0) {
                    Boolean opcion = (Boolean) getTblEstudiantes().getValueAt(fila,0);
                    RubroEstudianteData rubroEstudianteData=(RubroEstudianteData) getTblEstudiantes().getValueAt(fila,1);
                    if(opcion)
                    {
                        rubroEstudianteData.setSeleccionado(true);
                    }
                    else
                    {
                        rubroEstudianteData.setSeleccionado(false);
                    }
                    
                }
            }
        });
        
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
    
    private void cambiarSeleccionaTabla(JTable tabla, Boolean seleccion) 
    {
        DefaultTableModel tablaModelo = (DefaultTableModel) tabla.getModel();

        for (int i = 0; i < tablaModelo.getRowCount(); i++) {
            //Boolean opcion=(Boolean) tablaModelo.getValueAt(i,1);
            tablaModelo.setValueAt(seleccion, i, 0);
        }
    }

    private void listenerCheckBox() {
        
        getChkSeleccionarTodo().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Boolean opcion= getChkSeleccionarTodo().isSelected();
                cambiarSeleccionaTabla(getTblEstudiantes(),opcion);
            }
        });

    }

}
