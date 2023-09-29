package ec.com.codesoft.codefaclite.codefacweb.mb.servicios;

import ec.com.codesoft.codefaclite.codefacweb.core.GeneralAbstractMb;
import ec.com.codesoft.codefaclite.codefacweb.mb.utilidades.MensajeMb;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Mantenimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.MantenimientoInformeDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.MantenimientoTareaDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Taller;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ParteVehiculoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoNoConformidadEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.utilidades.list.UtilidadesLista;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author CARLOS_CODESOFT 
 */
@ManagedBean
@ViewScoped
public class TareasPendientesMb extends GeneralAbstractMb implements Serializable 
{
    private List<MantenimientoTareaDetalle> mantenimientoTareaList;   
    private List<Taller> tallerList;   
    
    private List<TipoNoConformidadEnum> tipoNoConfirmidadList;
    private List<ParteVehiculoEnum> parteVehiculoList;
    
    private TipoNoConformidadEnum tipoNoConformidadSeleccionada;
    private ParteVehiculoEnum parteVehiculoSeleccionada;
    
    private MantenimientoTareaDetalle detalleSeleccionado;
    

    @Override
    public void nuevo() throws ExcepcionCodefacLite, UnsupportedOperationException { 
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
            cargarDatosIniciales();
            
            tallerList=ServiceFactory.getFactory().getTallerServiceIf().obtenerActivos();
            parteVehiculoList=UtilidadesLista.arrayToList(ParteVehiculoEnum.values());
            tipoNoConfirmidadList=UtilidadesLista.arrayToList(TipoNoConformidadEnum.values());
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(TareasPendientesMb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void cargarDatosIniciales()
    {
        try {
            mantenimientoTareaList=ServiceFactory.getFactory().getMantenimientoTareaDetalleServiceIf().obtenerTareasPendientesPorEmpleado(sessionMb.getSession().getUsuario().getEmpleado());
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(TareasPendientesMb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(TareasPendientesMb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void iniciarTarea()
    {
        
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

    public List<MantenimientoTareaDetalle> getMantenimientoTareaList() {
        return mantenimientoTareaList; 
    }

    public void setMantenimientoTareaList(List<MantenimientoTareaDetalle> mantenimientoTareaList) {
        this.mantenimientoTareaList = mantenimientoTareaList;
    }
    
    public List<Taller> getTallerList() {
        return tallerList;
    }

    public void setTallerList(List<Taller> tallerList) {
        this.tallerList = tallerList;
    }

    public List<TipoNoConformidadEnum> getTipoNoConfirmidadList() {
        return tipoNoConfirmidadList;
    }

    public void setTipoNoConfirmidadList(List<TipoNoConformidadEnum> tipoNoConfirmidadList) {
        this.tipoNoConfirmidadList = tipoNoConfirmidadList;
    }

    public List<ParteVehiculoEnum> getParteVehiculoList() {
        return parteVehiculoList;
    }

    public void setParteVehiculoList(List<ParteVehiculoEnum> parteVehiculoList) {
        this.parteVehiculoList = parteVehiculoList;
    }

    public TipoNoConformidadEnum getTipoNoConformidadSeleccionada() {
        return tipoNoConformidadSeleccionada;
    }

    public void setTipoNoConformidadSeleccionada(TipoNoConformidadEnum tipoNoConformidadSeleccionada) {
        this.tipoNoConformidadSeleccionada = tipoNoConformidadSeleccionada;
    }

    public ParteVehiculoEnum getParteVehiculoSeleccionada() {
        return parteVehiculoSeleccionada;
    }

    public void setParteVehiculoSeleccionada(ParteVehiculoEnum parteVehiculoSeleccionada) {
        this.parteVehiculoSeleccionada = parteVehiculoSeleccionada;
    }

    public MantenimientoTareaDetalle getDetalleSeleccionado() {
        return detalleSeleccionado;
    }

    public void setDetalleSeleccionado(MantenimientoTareaDetalle detalleSeleccionado) {
        this.detalleSeleccionado = detalleSeleccionado;
    }
    
    
    public void abrirNovedades(MantenimientoTareaDetalle tarea)
    {
        System.out.println("Abriendo Dialogo para abrir las NOVEDADES ...");
        detalleSeleccionado=tarea;
    }
    
    public void agregarNovedadTabla()
    {
        System.out.println("Agregando NOVEDADES ...");
        MantenimientoInformeDetalle detalle=new MantenimientoInformeDetalle();
        detalle.setCodigoTipoNoConformidad(tipoNoConformidadSeleccionada.getCodigo());    
        detalle.setParteVehiculo(parteVehiculoSeleccionada.getNombre());
        detalle.setMantenimientoTareaDetalle(detalleSeleccionado);
        if(!detalleSeleccionado.verificarInformeDuplicado(detalle))
        {
            try {                
                ServiceFactory.getFactory().getMantenimientoTareaDetalleServiceIf().grabarInformeDetalle(detalle,detalleSeleccionado);
                detalleSeleccionado.agregarInforme(detalle);
            } catch (ServicioCodefacException ex) {
                Logger.getLogger(TareasPendientesMb.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RemoteException ex) {
                Logger.getLogger(TareasPendientesMb.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

    
    
    public void abrirPantallaModificarTarea(MantenimientoTareaDetalle mantenimiento)
    {
        System.out.println("abriendo pantalla para modificar el mantenimiento ...");
        //UtilidadesWeb.redirigirPaginaInterna("tarea_mantenimiento"); 
        
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        try {
            externalContext.redirect("tarea_mantenimiento.xhtml?tareaMantenimientoId="+mantenimiento.getId());
        } catch (IOException e) {
            // Manejar cualquier excepción
            e.printStackTrace();
        }
    }
    
    public void iniciarTarea(MantenimientoTareaDetalle tarea)
    {
        System.out.println(" iniciarTarea ...");
        try {
            ServiceFactory.getFactory().getMantenimientoServiceIf().iniciarTarea(tarea,sessionMb.getSession().getUsuario().getEmpleado());
            MensajeMb.mensaje(MensajeCodefacSistema.AccionesFormulario.GUARDADO);
            cargarDatosIniciales();
            
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(TareasPendientesMb.class.getName()).log(Level.SEVERE, null, ex);
            MensajeMb.mostrarMensajeDialogo("Error", ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        } catch (RemoteException ex) {
            Logger.getLogger(TareasPendientesMb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void finalizarTarea(MantenimientoTareaDetalle tarea)
    {
        System.out.println(" finalizarTarea ..."); 
        try {
            ServiceFactory.getFactory().getMantenimientoServiceIf().finalizarTarea(tarea,sessionMb.getSession().getUsuario().getEmpleado());
            MensajeMb.mensaje(MensajeCodefacSistema.AccionesFormulario.GUARDADO);
            cargarDatosIniciales();
            
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(TareasPendientesMb.class.getName()).log(Level.SEVERE, null, ex);
            MensajeMb.mostrarMensajeDialogo("Error", ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        } catch (RemoteException ex) {
            Logger.getLogger(TareasPendientesMb.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    
    public void eliminarNovedad(MantenimientoInformeDetalle detalle)
    {
        try {
            System.out.println(" Eliminando novedad ...");            
            ServiceFactory.getFactory().getMantenimientoTareaDetalleServiceIf().eliminarInformeDetalle(detalle,detalleSeleccionado);
            detalleSeleccionado.getInformeList().remove(detalle);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(TareasPendientesMb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(TareasPendientesMb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
