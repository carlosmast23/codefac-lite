/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.gestionacademica.model;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.gestionacademica.panel.NivelPanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Nivel;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.NivelEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.NivelServiceIf;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class NivelModel extends NivelPanel {
    
    private Nivel nivel;
    private NivelServiceIf nivelService;
    
    public NivelModel() {
    }
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        //combo para niveles
        try {
            getCmbNivelPosterior().removeAllItems();
            List<Nivel> nivelList = nivelService.obtenerTodos();
            for (Nivel niv : nivelList) {
                getCmbNivelPosterior().addItem(niv);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(NivelModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void grabar() throws ExcepcionCodefacLite {
        try {
            setearValoresNivel(nivel);
            nivel = nivelService.grabar(nivel);
            DialogoCodefac.mensaje("Datos correctos", "El nivel se guardo correctamente", DialogoCodefac.MENSAJE_CORRECTO);
        } catch (ServicioCodefacException ex) {
            DialogoCodefac.mensaje("Error", ex.getMessage(), DialogoCodefac.MENSAJE_INCORRECTO);
            throw new ExcepcionCodefacLite("Error al grabar nivel modelo");
        } catch (RemoteException ex) {
            Logger.getLogger(AulaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void setearValoresNivel(Nivel nivel) {
        nivel.setNombre(getTxtNombre().getText());
        nivel.setOrden(Integer.parseInt(getTxtOrden().getText()));
        nivel.setDescripcion(getTxtDescripcion().getText());
        nivel.setEstado(((NivelEnumEstado) getCmbEstado().getSelectedItem()).getEstado());
        
        nivel = (Nivel) getCmbNivelPosterior().getSelectedItem();
        nivel.setNivelPosterior(nivel);
        
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
        nivel = new Nivel();
        nivelService = ServiceFactory.getFactory().getNivelServiceIf();
    }
    
    @Override
    public String getNombre() {
        return "Nivel";
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
    
}
