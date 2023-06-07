/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.mb.servicios;

import ec.com.codesoft.codefaclite.codefacweb.core.GeneralAbstractMb;
import ec.com.codesoft.codefaclite.codefacweb.mb.utilidades.MensajeMb;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Mantenimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.utilidades.list.UtilidadesLista;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author CARLOS_CODESOFT
 */
@ManagedBean
@ViewScoped 
public class ClasificarVehiculosNuevosMb extends GeneralAbstractMb implements  Serializable{
    
    private List<Mantenimiento> mantenimientoList;
    private List<Mantenimiento> mantenimientoSeleccionList;
    private List<Mantenimiento.UbicacionEnum> ubicacionList;

    @Override
    public void nuevo() throws ExcepcionCodefacLite, UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private List<Mantenimiento> setearEstadoVehiculos(List<Mantenimiento> mantenimientoSeleccionados,List<Mantenimiento> mantenimientoList)
    {
        List<Mantenimiento> resultadoList=new ArrayList<Mantenimiento>();
        for (Mantenimiento mantenimiento : mantenimientoList) 
        {
            if(mantenimientoSeleccionados.contains(mantenimiento))
            {
                mantenimiento.setUbicacionEnum(Mantenimiento.UbicacionEnum.TALLER);            
            }
            else
            {
                mantenimiento.setUbicacion(null);
            }
            
            resultadoList.add(mantenimiento);
        }
        return resultadoList;
    }
    
    public void procesarTodosVehiculosDirecto()
    {
        System.out.println("procesando resto de vehiculos de forma DIRECTA ..");
        try {
            for (Mantenimiento mantenimiento : mantenimientoList) {
                mantenimiento.setUbicacionEnum(Mantenimiento.UbicacionEnum.DIRECTO);
            }
            ServiceFactory.getFactory().getMantenimientoServiceIf().editarLote(mantenimientoList, sessionMb.getSession().getUsuario());
            iniciar();
            //ServiceFactory.getFactory().getMantenimientoServiceIf().editarLote(mantenimientoList,sessionMb.getSession().getUsuario());
            MensajeMb.mensaje(MensajeCodefacSistema.AccionesFormulario.GUARDADO);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ClasificarVehiculosNuevosMb.class.getName()).log(Level.SEVERE, null, ex);
            MensajeMb.mensaje(new CodefacMsj(ex.getMessage(), CodefacMsj.TipoMensajeEnum.ERROR));
        } catch (RemoteException ex) {
            Logger.getLogger(ClasificarVehiculosNuevosMb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExcepcionCodefacLite ex) {
            Logger.getLogger(ClasificarVehiculosNuevosMb.class.getName()).log(Level.SEVERE, null, ex);
            MensajeMb.mensaje(new CodefacMsj(ex.getMessage(), CodefacMsj.TipoMensajeEnum.ERROR));
        }
        
    }
 
    @Override
    public void grabar() throws ExcepcionCodefacLite, UnsupportedOperationException {
        
        try {
            List<Mantenimiento> mantenimientoProcesarList= setearEstadoVehiculos(mantenimientoSeleccionList, mantenimientoList);
            ServiceFactory.getFactory().getMantenimientoServiceIf().editarLote(mantenimientoProcesarList,sessionMb.getSession().getUsuario());
            iniciar();
            //ServiceFactory.getFactory().getMantenimientoServiceIf().editarLote(mantenimientoList,sessionMb.getSession().getUsuario());
            MensajeMb.mensaje(MensajeCodefacSistema.AccionesFormulario.GUARDADO);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ClasificarVehiculosNuevosMb.class.getName()).log(Level.SEVERE, null, ex);
            MensajeMb.mensaje(new CodefacMsj(ex.getMessage(), CodefacMsj.TipoMensajeEnum.ERROR));
        } catch (RemoteException ex) {
            Logger.getLogger(ClasificarVehiculosNuevosMb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void editar() throws ExcepcionCodefacLite, UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void imprimir() throws ExcepcionCodefacLite, UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite, UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buscar() throws ExcepcionCodefacLite, UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String titulo() throws ExcepcionCodefacLite, UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public InterfaceModelFind obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        try 
        {
            mantenimientoList = ServiceFactory.getFactory().getMantenimientoServiceIf().obtenerPendientesClasificarUbicacion(sessionMb.getSession().getEmpresa());
            for (Mantenimiento mantenimiento : mantenimientoList) 
            {
                mantenimiento.setUbicacionEnum(Mantenimiento.UbicacionEnum.DIRECTO);
            }
            ubicacionList=UtilidadesLista.arrayToList(Mantenimiento.UbicacionEnum.values());
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(MantenimientosPendientesMb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    @Override
    public void actualizar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void limpiar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getURLAyuda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Mantenimiento> getMantenimientoList() {     
        return mantenimientoList;          
    }

    public void setMantenimientoList(List<Mantenimiento> mantenimientoList) {   
        this.mantenimientoList = mantenimientoList;           
    }

    public List<Mantenimiento.UbicacionEnum> getUbicacionList() {
        return ubicacionList;
    }

    public void setUbicacionList(List<Mantenimiento.UbicacionEnum> ubicacionList) {
        this.ubicacionList = ubicacionList;
    }

    public List<Mantenimiento> getMantenimientoSeleccionList() {
        return mantenimientoSeleccionList;
    }

    public void setMantenimientoSeleccionList(List<Mantenimiento> mantenimientoSeleccionList) {
        this.mantenimientoSeleccionList = mantenimientoSeleccionList;
    }

    
    
    
}
