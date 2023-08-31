/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.mb.servicios;

import ec.com.codesoft.codefaclite.codefacweb.core.GeneralAbstractMb;
import ec.com.codesoft.codefaclite.codefacweb.mb.utilidades.MensajeMb;
import ec.com.codesoft.codefaclite.codefacweb.mb.utilidades.UtilidadesDialogo;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ClienteEstablecimientoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ObjetoMantenimientoBusqueda;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Mantenimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ObjetoMantenimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Taller;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.TallerTarea;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.MensajeCodefacSistema;
import java.io.IOException;
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
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

/**
 *
 * @author CARLOS_CODESOFT
 */
@ManagedBean 
@ViewScoped
public class MantenimientosPendientesMb extends GeneralAbstractMb implements Serializable{

    List<Mantenimiento> mantenimientoPendienteList;     
    private List<Taller> tallerList; 
    private List<TallerTarea> subtareaList; 
    private List<TallerTarea> subtareaSeleccionadaList;
    private Mantenimiento mantenimiento;
    
    @PostConstruct
    public void init()
    {
        try {
            this.mantenimiento=new Mantenimiento();
            
            //Cargar los talleres disponibles
            tallerList=ServiceFactory.getFactory().getTallerServiceIf().obtenerActivos();
            //subtareaList=ServiceFactory.getFactory().getTallerServiceIf().obtenerTareasPorTaller(mant)
            System.out.println("Talleres encontrados: "+tallerList.size());
            
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(MantenimientosPendientesMb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(MantenimientosPendientesMb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
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
            mantenimientoPendienteList=ServiceFactory.getFactory().getMantenimientoServiceIf().obtenerPendientes(sessionMb.getSession().getEmpresa());
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
    
    public void agregarTarea(Mantenimiento mantenimiento)
    {
        System.out.println("Agregar Tarea "+mantenimiento.getId());
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        try {
            externalContext.redirect("tarea_mantenimiento.xhtml?mantenimientoId="+mantenimiento.getId());
        } catch (IOException e) {
            // Manejar cualquier excepción
            e.printStackTrace();
        }
    }
    
    public void eliminarMantenimiento(Mantenimiento matenimiento)
    {
        try {
            System.out.println("Eliminar Mantenimiento ...");  
            ServiceFactory.getFactory().getMantenimientoServiceIf().eliminar(matenimiento);
            MensajeMb.mensaje(MensajeCodefacSistema.AccionesFormulario.GUARDADO);
            iniciar();
        } catch (ServicioCodefacException ex) {
            MensajeMb.mostrarMensajeDialogo("Error", ex.getMessage(), FacesMessage.SEVERITY_ERROR);
            Logger.getLogger(MantenimientosPendientesMb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(MantenimientosPendientesMb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExcepcionCodefacLite ex) {
            Logger.getLogger(MantenimientosPendientesMb.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void onItemUnselect(UnselectEvent event) {
        FacesMessage msg = new FacesMessage();
        msg.setSummary("Item unselected: " + event.getObject().toString());
        msg.setSeverity(FacesMessage.SEVERITY_INFO);

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void seleccionarSubtareDesdeTaller()
    {
        System.out.println("Sub EVENTO NUEVO ..."); 
        try {
            System.out.println("seleccionarSubtareDesdeTaller ...");
            subtareaList=ServiceFactory.getFactory().getTallerServiceIf().obtenerTareasPorTaller(mantenimiento.getTaller());
            //ServiceFactory.getFactory().getTallerServiceIf().obtenerActivos();
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(MantenimientosPendientesMb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(MantenimientosPendientesMb.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public void abrirDialogoBuscarVehiculo() {
        System.out.println("Abriendo dialogo init");
        ObjetoMantenimientoBusqueda vehiculoDialogo=new ObjetoMantenimientoBusqueda(sessionMb.getSession().getEmpresa());
        //ClienteEstablecimientoBusquedaDialogo clienteBusquedaDialogo = new ClienteEstablecimientoBusquedaDialogo(sessionMb.getSession());
        //abrirDialogoBusqueda(clienteBusquedaDialogo);
        UtilidadesDialogo.abrirDialogoBusqueda(vehiculoDialogo);
        System.out.println("Abriendo dialogo fin");
    }
    
    public void seleccionarVehiculo(SelectEvent event) {
        ObjetoMantenimiento vehiculo=(ObjetoMantenimiento) event.getObject();
        mantenimiento.setVehiculo(vehiculo);
    }

    /*
    =======================/ METODOS GET AND SET /=====================
    */

    public List<Mantenimiento> getMantenimientoPendienteList() {
        return mantenimientoPendienteList;
    }

    public void setMantenimientoPendienteList(List<Mantenimiento> mantenimientoPendienteList) {
        this.mantenimientoPendienteList = mantenimientoPendienteList;
    }

    public Mantenimiento getMantenimiento() {
        return mantenimiento;
    }

    public void setMantenimiento(Mantenimiento mantenimiento) {
        this.mantenimiento = mantenimiento;
    }

    public List<Taller> getTallerList() {
        return tallerList;
    }

    public void setTallerList(List<Taller> tallerList) {
        this.tallerList = tallerList;
    }

    public List<TallerTarea> getSubtareaList() {
        return subtareaList;
    }

    public void setSubtareaList(List<TallerTarea> subtareaList) {
        this.subtareaList = subtareaList;
    }

    public List<TallerTarea> getSubtareaSeleccionadaList() {
        return subtareaSeleccionadaList;
    }

    public void setSubtareaSeleccionadaList(List<TallerTarea> subtareaSeleccionadaList) {
        this.subtareaSeleccionadaList = subtareaSeleccionadaList;
    }
    
    
    
}
