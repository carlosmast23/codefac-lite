/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.mb.servicios;

import ec.com.codesoft.codefaclite.codefacweb.core.GeneralAbstractMb;
import ec.com.codesoft.codefaclite.codefacweb.mb.sistema.UtilidadesWeb;
import ec.com.codesoft.codefaclite.codefacweb.mb.utilidades.MensajeMb;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empleado;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Mantenimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.MantenimientoTareaDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.TareaMantenimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import es.mityc.firmaJava.libreria.utilidades.Utilidades;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author CARLOS_CODESOFT
 */
@ManagedBean
@ViewScoped
public class TareaMantenimientoMb extends GeneralAbstractMb implements Serializable
{
    private List<TareaMantenimiento> tareaList;
    private List<Empleado> operadorList;
    private List<Mantenimiento> mantenimientoList;
    private MantenimientoTareaDetalle tareaMantenimieto;
    
    @PostConstruct
    private void init()
    {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, String> requestParams = externalContext.getRequestParameterMap();
        String mantemientoIdStr = requestParams.get("mantenimientoId");
        System.out.println("leyendo mantenimientoId: "+mantemientoIdStr);
        
        //Por defecto crear el objeto de forma vacia
        tareaMantenimieto=new MantenimientoTareaDetalle();
        
        if(!UtilidadesTextos.verificarNullOVacio(mantemientoIdStr))
        {
            try {
                tareaMantenimieto=ServiceFactory.getFactory().getMantenimientoTareaDetalleServiceIf().buscarPorId(Long.parseLong(mantemientoIdStr));
            } catch (RemoteException ex) {
                Logger.getLogger(TareaMantenimientoMb.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite, UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, UnsupportedOperationException {
        try {
            ServiceFactory.getFactory().getMantenimientoTareaDetalleServiceIf().grabar(tareaMantenimieto, sessionMb.getSession().getEmpresa(),sessionMb.getSession().getUsuario());
            MensajeMb.mensaje(MensajeCodefacSistema.AccionesFormulario.GUARDADO);
        } catch (ServicioCodefacException ex) {
            MensajeMb.mostrarMensajeDialogo("Error", ex.getMessage(), FacesMessage.SEVERITY_ERROR);
            Logger.getLogger(TareaMantenimientoMb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(TareaMantenimientoMb.class.getName()).log(Level.SEVERE, null, ex);
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
        try {
            //tareaMantenimieto=new MantenimientoTareaDetalle();
            tareaList=ServiceFactory.getFactory().getTareaMantenimientoServiceIf().obtenerTodosActivos(sessionMb.getSession().getEmpresa());
            operadorList=ServiceFactory.getFactory().getEmpleadoServiceIf().buscarActivosPorEmpresa(sessionMb.getSession().getEmpresa());
            mantenimientoList=ServiceFactory.getFactory().getMantenimientoServiceIf().obtenerPendientes(sessionMb.getSession().getEmpresa());
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(TareaMantenimientoMb.class.getName()).log(Level.SEVERE, null, ex);
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

    public List<TareaMantenimiento> getTareaList() {
        return tareaList;
    }

    public void setTareaList(List<TareaMantenimiento> tareaList) {
        this.tareaList = tareaList;
    }

    public List<Empleado> getOperadorList() {
        return operadorList;
    }

    public void setOperadorList(List<Empleado> operadorList) {
        this.operadorList = operadorList;
    }

    public MantenimientoTareaDetalle getTareaMantenimieto() {
        return tareaMantenimieto;
    }

    public void setTareaMantenimieto(MantenimientoTareaDetalle tareaMantenimieto) {
        this.tareaMantenimieto = tareaMantenimieto;
    }

    public List<Mantenimiento> getMantenimientoList() {
        return mantenimientoList;
    }

    public void setMantenimientoList(List<Mantenimiento> mantenimientoList) {
        this.mantenimientoList = mantenimientoList;
    }
    
    
    //////////////////////////////////////////////////////////////////////
    ////                METODOS ADICIONALES
    /////////////////////////////////////////////////////////////////////   
    
    public void crearNuevaTarea()
    {
        try {
            ServiceFactory.getFactory().getMantenimientoTareaDetalleServiceIf().grabar(tareaMantenimieto,sessionMb.getSession().getEmpresa(),sessionMb.getSession().getUsuario());
            System.out.println("Creando nueva tarea ...");
            MensajeMb.mensaje(MensajeCodefacSistema.AccionesFormulario.GUARDADO);
            UtilidadesWeb.redirigirPaginaInterna("mantenimientos_pendientes");
        } catch (ServicioCodefacException ex) {
            MensajeMb.mostrarMensajeDialogo("Error", ex.getMessage(), FacesMessage.SEVERITY_ERROR);
            Logger.getLogger(TareaMantenimientoMb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(TareaMantenimientoMb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void finalizarMantenimiento()
    {
        try {
            ServiceFactory.getFactory().getMantenimientoTareaDetalleServiceIf().finalizarTarea(tareaMantenimieto,true);
            MensajeMb.mensaje(MensajeCodefacSistema.AccionesFormulario.GUARDADO);
             UtilidadesWeb.redirigirPaginaInterna("tarea_mantenimiento");
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(TareaMantenimientoMb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(TareaMantenimientoMb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void finalizarTareaYCrearOtra()
    {
        try {
            ServiceFactory.getFactory().getMantenimientoTareaDetalleServiceIf().finalizarTarea(tareaMantenimieto,false);
            MensajeMb.mensaje(MensajeCodefacSistema.AccionesFormulario.GUARDADO);
            UtilidadesWeb.redirigirPaginaInterna("tarea_mantenimiento");
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(TareaMantenimientoMb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(TareaMantenimientoMb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
