/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.core;

import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.EmpleadoBusquedaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfaceModelFind;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Carlos
 */
@ManagedBean
@ViewScoped
public class ControllerCodefacMb implements Serializable{
    private String indiceTabSecundario;
    private GeneralAbstractMb generalAbstractMb;
    

    @PostConstruct
    public void init()
    {
        indiceTabSecundario="2";
    }
    
    /**
     * Metodo superior que contrala la forma como van a grabar
     */
    public void save()
    {
        //Metodo save desde el controlador
        System.out.println("Metodo save desde el controlador");
        generalAbstractMb.grabar();
    }
    
     
    public void abrirDialogoBusqueda()
    {
        //find();
        System.out.println("Abriendo dialogo busqueda");
        
        //Establecer objeto de la clase que tiene la implemetacion del dialogo de busqueda que necesito para construir el dialogo web
        //TODO: Solucion temporal porque es una gasto innesario de memoria , buscar otra forma
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        sessionMap.put("busquedaClase", new EmpleadoBusquedaDialogo());
        
        //Esstablecer porpiedades que se van a enviar al dialogo en map
        Map<String,Object> options = new HashMap<String, Object>();
        options.put("resizable", false);
        options.put("draggable", false);
        options.put("modal", true);
        //options.put("busquedaClase", new EmpleadoBusquedaDialogo() ); //TODO: Mando por defecto un dialogo por defecto
        String nombreDialogoBusqueda="dialogo_busqueda";
        //PrimeFaces.current().dialog()
        PrimeFaces.current().dialog().openDynamic(nombreDialogoBusqueda, options, null);
    }
    
    //Metodo que permite recibir el dato seleccionado
    public void onObjectChosen(SelectEvent event)
    {        
        Object objetoSeleccionado = (Object) event.getObject();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Dato Seleccionado", "");         
        FacesContext.getCurrentInstance().addMessage(null, message);
        generalAbstractMb.cargarBusqueda(objetoSeleccionado);
    }
    
    public String getIndiceTabSecundario() {
        return indiceTabSecundario;
    }

    public void setIndiceTabSecundario(String indiceTabSecundario) {
        this.indiceTabSecundario = indiceTabSecundario;
    }
    
    public void mostrarAyuda()
    {
        System.out.println("Cambiando indice panel");
        indiceTabSecundario="1";
    }

    public GeneralAbstractMb getGeneralAbstractMb() {
        return generalAbstractMb;
    }

    public void setGeneralAbstractMb(GeneralAbstractMb generalAbstractMb) {
        this.generalAbstractMb = generalAbstractMb;
    }
    
    public void agregarVista(GeneralAbstractMb vista)
    {
        generalAbstractMb=vista;
        System.out.println("iniciando el metodo que setea la vista");
    }

    
}
