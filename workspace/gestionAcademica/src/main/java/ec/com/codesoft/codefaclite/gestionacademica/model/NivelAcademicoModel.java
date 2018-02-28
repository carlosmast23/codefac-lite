/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.gestionacademica.model;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.gestionacademica.busqueda.NivelAcademicoDialogo;
import ec.com.codesoft.codefaclite.gestionacademica.panel.NivelAcademicoPanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Aula;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Nivel;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.NivelAcademico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.NivelAcademicoServiceIf;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class NivelAcademicoModel extends NivelAcademicoPanel implements Serializable{
    
    /**
     * Referencia para mejar los cruds
     */
    private NivelAcademico nivelAcademico;

    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        cargarValoresIniciales();
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        try {
            NivelAcademicoServiceIf servicio= ServiceFactory.getFactory().getNivelAcademicoServiceIf();
            setearVariablesPantalla();
            servicio.grabar(nivelAcademico);
            DialogoCodefac.mensaje("Correcto","El nivel academico fue creado correctamente",DialogoCodefac.MENSAJE_CORRECTO);
        } catch (ServicioCodefacException ex) {
            DialogoCodefac.mensaje("Error","Ocurrio un error al grabar los datos",DialogoCodefac.MENSAJE_CORRECTO);
            Logger.getLogger(NivelAcademicoModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            DialogoCodefac.mensaje("Error","Error de comunicación con el servidor",DialogoCodefac.MENSAJE_CORRECTO);
            Logger.getLogger(NivelAcademicoModel.class.getName()).log(Level.SEVERE, null, ex);
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
        NivelAcademicoDialogo clienteBusquedaDialogo = new NivelAcademicoDialogo();
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(clienteBusquedaDialogo);
        buscarDialogoModel.setVisible(true);
        NivelAcademico nivelAcademicoTemp = (NivelAcademico) buscarDialogoModel.getResultado();
        
        if(nivelAcademicoTemp==null)
        {
            throw new ExcepcionCodefacLite("Excepcion lanzada desde buscar");
        }
        nivelAcademico=nivelAcademicoTemp;
        setearDatosPantalla();

    }
    
    private void setearDatosPantalla()
    {
        getTxtNombre().setText(nivelAcademico.getNombre());
        getCmbAula().setSelectedItem(nivelAcademico.getAula());
        getCmbNivel().setSelectedItem(nivelAcademico.getNivel());
        getCmbPeriodo().setSelectedItem(nivelAcademico.getPeriodo());
        
    }

    @Override
    public void limpiar() {
        limpiarVariables();
    }

    @Override
    public String getNombre() {
        return "Nivel Academico";
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

    private void cargarValoresIniciales() {
        cargarCombos();
    }

    private void cargarCombos() {
      
        try {
            List<Aula> aulas= ServiceFactory.getFactory().getAulaServiceIf().obtenerTodos();
            getCmbAula().removeAllItems();
            for (Aula aula : aulas) {
                getCmbAula().addItem(aula);
            }
            
            List<Periodo> periodos= ServiceFactory.getFactory().getPeriodoServiceIf().obtenerTodos();
            getCmbPeriodo().removeAllItems();
            for (Periodo periodo : periodos) {
                getCmbPeriodo().addItem(periodo);
            }
            
            List<Nivel> niveles= ServiceFactory.getFactory().getNivelServiceIf().obtenerTodos();
            getCmbNivel().removeAllItems();
            for (Nivel nivel : niveles) {
                getCmbNivel().addItem(nivel);
            }
            
            
        } catch (RemoteException ex) {
            Logger.getLogger(NivelAcademicoModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void limpiarVariables() {
        nivelAcademico=new NivelAcademico();
    }

    private void setearVariablesPantalla() {
        nivelAcademico.setNombre(getTxtNombre().getText());
        
        nivelAcademico.setAula((Aula) getCmbAula().getSelectedItem());
        nivelAcademico.setNivel((Nivel) getCmbNivel().getSelectedItem());
        nivelAcademico.setPeriodo((Periodo) getCmbPeriodo().getSelectedItem());
    }
    
}
