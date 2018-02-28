/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.model;

import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.facturacion.panel.FacturaAcademicoLotePanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Estudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.EstudianteInscrito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.NivelAcademico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroEstudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubrosNivel;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Carlos
 */
public class FacturaAcademicoLoteModel extends FacturaAcademicoLotePanel{

    /**
     * Map que permite almacenar todos los datos por curso y luego por estudiante y rubros a facturar
     */
    private Map<NivelAcademico,Map<EstudianteInscrito,List<RubroEstudiante>>> mapDatosFacturar;
    
    /**
     * Informacion del map por defecto cargado
     */
    private Map<EstudianteInscrito,List<RubroEstudiante>> mapEstudianteRubros;
    
   
    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        cargarValoresIniciales();
        listenerCombos();
        listenerBotones();
        listenerListas();
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
        mapDatosFacturar=new HashMap<NivelAcademico,Map<EstudianteInscrito, List<RubroEstudiante>>>();
        mapEstudianteRubros=new HashMap<EstudianteInscrito, List<RubroEstudiante>>();
    }

    @Override
    public String getNombre() {
        return "Factura Académica por Lote";
    }

    @Override
    public String getURLAyuda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void cargarValoresIniciales() {
        //cargar los periodos actuales
        try {
            List<Periodo> periodos = ServiceFactory.getFactory().getPeriodoServiceIf().obtenerPeriodoActivo();
            for (Periodo periodo : periodos) {
                getCmbPeriodo().addItem(periodo);
            }
        } 
        catch (RemoteException ex) {
            Logger.getLogger(FacturaAcademicoLoteModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void listenerCombos() 
    {
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
                    Logger.getLogger(FacturaAcademicoLoteModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ServicioCodefacException ex) {
                    Logger.getLogger(FacturaAcademicoLoteModel.class.getName()).log(Level.SEVERE, null, ex);
                }
               
            }
        });
        
        getCmbNivelAcademico().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              
                try {
                    getCmbRubrosNivel().removeAllItems();
                    NivelAcademico nivelSeleccionado=(NivelAcademico) getCmbNivelAcademico().getSelectedItem();
                    Map<String, Object> mapParametros = new HashMap<String, Object>();
                    //Cargar rubros generales para todos los niveles
                    mapParametros.put("nivel",null);
                    mapParametros.put("periodo",nivelSeleccionado.getPeriodo());
                    List<RubrosNivel> rubrosSinNivel=ServiceFactory.getFactory().getRubrosNivelServiceIf().obtenerPorMap(mapParametros);
                    for (RubrosNivel rubrosNivel : rubrosSinNivel) {
                        getCmbRubrosNivel().addItem(rubrosNivel);
                    }
                    
                    //Cargar rubros exclusivos de los niveles actuales
                    mapParametros.clear();
                    mapParametros.put("nivel",nivelSeleccionado.getNivel());
                    mapParametros.put("periodo",nivelSeleccionado.getPeriodo());
                    List<RubrosNivel> rubros=ServiceFactory.getFactory().getRubrosNivelServiceIf().obtenerPorMap(mapParametros);
                    
                    //Agregar todos los rubros disponibles para el nivels
                    for (RubrosNivel rubro : rubros) {
                        getCmbRubrosNivel().addItem(rubro);
                    } 
                    
                    //Agregar los estudiantes del nivel a la tabla
                    cargarEstudiantesNuevos();
                    cargarTabla();
                    
                } catch (RemoteException ex) {
                    Logger.getLogger(FacturaAcademicoLoteModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ServicioCodefacException ex) {
                    Logger.getLogger(FacturaAcademicoLoteModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        getCmbRubrosNivel().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //cargarEstudiantesNuevos();
                //cargarTabla();
                
            }
        });
        
    }
    
    
    private void cargarEstudiantesNuevos() {
        mapEstudianteRubros=new HashMap<EstudianteInscrito, List<RubroEstudiante>>();
        
        NivelAcademico nivelAcademico = (NivelAcademico) getCmbNivelAcademico().getSelectedItem();
        //RubrosNivel rubroNivel = (RubrosNivel) getCmbRubrosNivel().getSelectedItem();
        
        if (nivelAcademico != null) {
            try {
                Map<String, Object> mapParametros = new HashMap<String, Object>();
                mapParametros.put("nivelAcademico", nivelAcademico);
                List<EstudianteInscrito> estudiantesInscritos = ServiceFactory.getFactory().getEstudianteInscritoServiceIf().obtenerPorMap(mapParametros);
                
                for (EstudianteInscrito estudiantesInscrito : estudiantesInscritos) {
                    mapEstudianteRubros.put(estudiantesInscrito,new ArrayList<RubroEstudiante>());
                }
            } catch (RemoteException ex) {
                Logger.getLogger(FacturaAcademicoLoteModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ServicioCodefacException ex) {
                Logger.getLogger(FacturaAcademicoLoteModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        

    }
    
    
    private void cargarTabla() {
        
        DefaultTableModel modeloTabla = crearModeloTabla(crearTituloTabla(),crearColumnasTabla());
        
        for (Map.Entry<EstudianteInscrito, List<RubroEstudiante>> entry : mapEstudianteRubros.entrySet()) {
            EstudianteInscrito estudianteInscrito = entry.getKey();
            List<RubroEstudiante> listaRubrosEstudiante = entry.getValue();
            
            Vector<Object> datos=new Vector<Object>();
            datos.add(true);
            datos.add(estudianteInscrito);
            
            ListModel<RubrosNivel> rubrosList = getLstRubrosFacturar().getModel();

            for (int i = 0; i < rubrosList.getSize(); i++) 
            {
                boolean rubroEncontrado=false;
                for (RubroEstudiante rubroEstudiante : listaRubrosEstudiante) {
                    if(rubroEstudiante.getRubroNivel().equals(rubrosList.getElementAt(i)))
                    {
                        rubroEncontrado=true;
                        break;
                    }
                }
                
                if(rubroEncontrado)
                {
                    datos.add(true);
                }
                else
                {
                    datos.add(false);
                }                

            }
            
            modeloTabla.addRow(datos);
            
        }
        
        //Setear los datos creados en la tabla
        getTblEstudiantesFacturar().setModel(modeloTabla);
        
    }
    
    private Class[] crearColumnasTabla()
    {
        Vector<Class> columnas=new Vector<Class>();
        columnas.add(Boolean.class);
        columnas.add(EstudianteInscrito.class);
        
        ListModel<RubrosNivel> rubrosList=getLstRubrosFacturar().getModel();
        
        for (int i = 0; i < rubrosList.getSize(); i++) {
            columnas.add(Boolean.class);
        }
        
        return columnas.toArray(new Class[columnas.size()]);
    
    }
    
    private String[] crearTituloTabla()
    {
        Vector<String> titulo=new Vector<String>();
        titulo.add("Opcion");
        titulo.add("Alumno");
        
        ListModel<RubrosNivel> rubrosList=getLstRubrosFacturar().getModel();
        
        for (int i = 0; i < rubrosList.getSize(); i++) {
            titulo.add(rubrosList.getElementAt(i).getNombre());
        }
        
        return titulo.toArray(new String[titulo.size()]);
        
    }
    
    private DefaultTableModel crearModeloTabla(String titulos[], Class[] tipoDatoFilas) {
        DefaultTableModel defaultTableModel = new javax.swing.table.DefaultTableModel(titulos, 0) {
            public Class getColumnClass(int columnIndex) {
                return tipoDatoFilas[columnIndex];
            }
        };
        return defaultTableModel;
    }

    private void listenerBotones() {
        getBtnAgregarRubrosNivel().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RubrosNivel rubrosNivel=(RubrosNivel) getCmbRubrosNivel().getSelectedItem();               
                agregarRubroLista(rubrosNivel);
                cargarRubrosLista();
                cargarTabla();
            }
        });
        
        getBtnAgregarCurso().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NivelAcademico nivelAcademico=(NivelAcademico) getCmbNivelAcademico().getSelectedItem();
                
                if(mapEstudianteRubros!=null)
                {
                    Map<EstudianteInscrito,List<RubroEstudiante>> mapTemporal= mapDatosFacturar.get(nivelAcademico);
                    if(mapTemporal==null)
                    {
                        mapDatosFacturar.put(nivelAcademico, mapEstudianteRubros);
                    }
                }
                cargarCursosLista();
            }
        });
       
    }
    
    private void cargarRubrosLista()
    {
        DefaultListModel<RubrosNivel> modelList=new DefaultListModel<RubrosNivel>();
        
        for (Map.Entry<EstudianteInscrito, List<RubroEstudiante>> entry1 : mapEstudianteRubros.entrySet()) {
            EstudianteInscrito estudianteInscrito = entry1.getKey();
            List<RubroEstudiante> lista = entry1.getValue();
            
            for (RubroEstudiante rubroEstudiante : lista) {
                //Agregar solo si no existe el rubro en la lista
                if(!modelList.contains(rubroEstudiante.getRubroNivel()))
                {
                    modelList.addElement(rubroEstudiante.getRubroNivel());
                }
            }

        }
        
        getLstRubrosFacturar().setModel(modelList);
        
    }
    
    private void cargarCursosLista()
    {
        DefaultListModel<NivelAcademico> modelList=new DefaultListModel<NivelAcademico>();
        for (Map.Entry<NivelAcademico, Map<EstudianteInscrito, List<RubroEstudiante>>> entry : mapDatosFacturar.entrySet()) {
            NivelAcademico nivelAcademico = entry.getKey();
            Map<EstudianteInscrito, List<RubroEstudiante>> datosCursos = entry.getValue();
            
            modelList.addElement(nivelAcademico);            
        }
        getLstCursosFacturar().setModel(modelList);
    }
   
    
    private void agregarRubroLista(RubrosNivel rubroNivel)
    {
       
        for ( Map.Entry<EstudianteInscrito,List<RubroEstudiante>> entry : mapEstudianteRubros.entrySet()) 
        {
            try {
                EstudianteInscrito estudianteInscrito = entry.getKey();
                List<RubroEstudiante> rubrosLista = entry.getValue();
                
                Map<String,Object> mapParametro=new HashMap<String,Object>();
                mapParametro.put("estudianteInscrito",estudianteInscrito);
                mapParametro.put("rubroNivel",rubroNivel);
                
                List<RubroEstudiante> rubrosEstudiante = ServiceFactory.getFactory().getRubroEstudianteServiceIf().obtenerPorMap(mapParametro);
                
                if(rubrosEstudiante.size()>0)
                {
                    RubroEstudiante rubroEstudiante=rubrosEstudiante.get(0);
                    rubrosLista.add(rubroEstudiante);
                }
                               
            } catch (RemoteException ex) {
                Logger.getLogger(FacturaAcademicoLoteModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ServicioCodefacException ex) {
                Logger.getLogger(FacturaAcademicoLoteModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void listenerListas() {
        getLstCursosFacturar().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                //int indiceSeleccionado=e.getFirstIndex();
                NivelAcademico nivelAcademico= getLstCursosFacturar().getSelectedValue();
                mapEstudianteRubros=mapDatosFacturar.get(nivelAcademico);
                cargarRubrosLista();
                cargarTabla();
            }
        });
    }
    
}
