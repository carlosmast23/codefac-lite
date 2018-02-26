/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.gestionacademica.model;

import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.gestionacademica.panel.GestionarDeudasPanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.EstudianteInscrito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.NivelAcademico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubrosNivel;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Carlos
 */
public class GestionarDeudasModel extends GestionarDeudasPanel{

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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
                    NivelAcademico nivelSeleccionado=(NivelAcademico) getCmbNivelAcademico().getSelectedItem();
                    Map<String, Object> mapParametros = new HashMap<String, Object>();
                    mapParametros.put("nivel",nivelSeleccionado.getNivel());
                    mapParametros.put("periodo",nivelSeleccionado.getPeriodo());
                    List<RubrosNivel> rubros=ServiceFactory.getFactory().getRubrosNivelServiceIf().obtenerPorMap(mapParametros);
                    
                    //Agregar todos los rubros disponibles para el nivels
                    getCmbRubrosNivel().removeAllItems();
                    for (RubrosNivel rubro : rubros) {
                        getCmbRubrosNivel().addItem(rubro);
                    }                    
                    
                } catch (RemoteException ex) {
                    Logger.getLogger(GestionarDeudasModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ServicioCodefacException ex) {
                    Logger.getLogger(GestionarDeudasModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
    }
    
    private void cargarTabla()
    {
        DefaultTableModel modeloTabla=crearModeloTabla(new String[]{"Opcion","Alumno"},new Class[]{Boolean.class,EstudianteInscrito.class});
        NivelAcademico nivelAcademico= (NivelAcademico) getCmbNivelAcademico().getSelectedItem();
        RubrosNivel rubroNivel=(RubrosNivel) getCmbRubrosNivel().getSelectedItem();
        
        if(nivelAcademico!=null && rubroNivel!=null)
        {
            //Crear servicio que me devuelva los objetos de estudiante inscrito y rubro selecccionado para cargar el tipo de dato boolean
        }
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

}
