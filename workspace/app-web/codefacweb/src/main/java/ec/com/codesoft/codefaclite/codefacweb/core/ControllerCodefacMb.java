/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.core;

import ec.com.codesoft.codefaclite.codefacweb.mb.utilidades.MensajeMb;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.EmpleadoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EstadoFormEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
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
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;

/**
 *
 * @author Carlos
 */
@ManagedBean
@ViewScoped
public class ControllerCodefacMb implements Serializable {

    private String indiceTabSecundario;
    private GeneralAbstractMb generalAbstractMb;
    private EstadoFormEnum estadoEnum;
    private Boolean modoDialogo;
    //private String titulo;

    @PostConstruct
    public void init() {
        System.out.println("Iniciando el postConstruct del controlador");
        modoDialogo=false;
        indiceTabSecundario = "0";
        this.estadoEnum = EstadoFormEnum.GRABAR;
        //titulo="Sin titulo";
        
        //Leer datos de configuraciones si fue abierta la ventana como map
          System.out.println("Leyendo datos del dialogo");
          String dialogo = FacesContext.getCurrentInstance()
                                 .getExternalContext()
                                 .getRequestParameterMap()
                                 .get("isDialog");
          
          System.out.println("Map:"+FacesContext.getCurrentInstance()
                                 .getExternalContext()
                                 .getRequestParameterMap()
                           );
          
          if(dialogo!=null)
          {
              modoDialogo=true;
          }
          
          
    }
    
    public void aceptar()
    {
    
    }

    public void nuevo() {
        System.out.println("presionado boton nuevo");
        try {
            generalAbstractMb.nuevo();
            estadoEnum = EstadoFormEnum.GRABAR;
        } catch (ExcepcionCodefacLite ex) {
            Logger.getLogger(ControllerCodefacMb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedOperationException ex) {
            MensajeMb.mostrarMensajeDialogo(MensajeCodefacSistema.ErroresComunes.METODO_SIN_IMPLEMENTAR);
        }
    }

    /**
     * Metodo superior que contrala la forma como van a grabar
     */
    public void save() {
        System.out.println("Metodo save desde el controlador");
        //Metodo save desde el controlador
        if (estadoEnum.equals(EstadoFormEnum.GRABAR)) {
            try {
                generalAbstractMb.grabar();
                //generalAbstractMb.nuevo();
                
                //Programacion para retornar el valor cuando se seleccione un dialogo
                if(modoDialogo)
                {
                    if(generalAbstractMb instanceof DialogoWeb)
                    {
                        DialogoWeb dialogoWeb=(DialogoWeb) generalAbstractMb;
                        PrimeFaces.current().dialog().closeDynamic(dialogoWeb.getResultDialogo());
                    }
                    
                }
                
                
            } catch (ExcepcionCodefacLite ex) {
                Logger.getLogger(ControllerCodefacMb.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedOperationException ex) {
                MensajeMb.mostrarMensajeDialogo(MensajeCodefacSistema.ErroresComunes.METODO_SIN_IMPLEMENTAR);
            }
        } else if (estadoEnum.equals(EstadoFormEnum.EDITAR)) {
            try {
                generalAbstractMb.editar();
                estadoEnum = EstadoFormEnum.GRABAR;
            } catch (ExcepcionCodefacLite ex) {
                Logger.getLogger(ControllerCodefacMb.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedOperationException ex) {
                MensajeMb.mostrarMensajeDialogo(MensajeCodefacSistema.ErroresComunes.METODO_SIN_IMPLEMENTAR);
            }
        }
        
        
        
    }

    public void delete() {
        try {
            System.err.println("Metodo para eliminar desde el controlador");
            generalAbstractMb.eliminar();
        } catch (ExcepcionCodefacLite ex) {
            Logger.getLogger(ControllerCodefacMb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void imprimir() {
        try {
            System.out.println("Metodo para imprimir");
            generalAbstractMb.imprimir();
        } catch (ExcepcionCodefacLite ex) {
            Logger.getLogger(ControllerCodefacMb.class.getName()).log(Level.SEVERE, null, ex);

        } catch (UnsupportedOperationException ex) {
            System.out.println("Metodo no implementado");
            MensajeMb.mostrarMensajeDialogo("Error", "Metodo imprimir no disponible", FacesMessage.SEVERITY_WARN);
        }
    }

    public void abrirDialogoBusqueda() {
        try {
            System.out.println("ejecutar abrir dialogo controlador");
            abrirDialogoBusqueda(generalAbstractMb.obtenerDialogoBusqueda());
        } catch (ExcepcionCodefacLite ex) {
            Logger.getLogger(ControllerCodefacMb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedOperationException ex) {
            MensajeMb.mostrarMensajeDialogo(MensajeCodefacSistema.ErroresComunes.METODO_SIN_IMPLEMENTAR);
        }
    }

    public void abrirDialogoBusqueda(InterfaceModelFind modeloBusqueda) {
        //find();
        //System.out.println("Abriendo dialogo busqueda");

        //Establecer objeto de la clase que tiene la implemetacion del dialogo de busqueda que necesito para construir el dialogo web
        //TODO: Solucion temporal porque es una gasto innesario de memoria , buscar otra forma
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        sessionMap.put("busquedaClase", modeloBusqueda); 

        //Esstablecer porpiedades que se van a enviar al dialogo en map
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("resizable", false);
        options.put("draggable", false);
        options.put("modal", true);
        //options.put("busquedaClase", new EmpleadoBusquedaDialogo() ); //TODO: Mando por defecto un dialogo por defecto
        String nombreDialogoBusqueda = "dialogo_busqueda";
        //PrimeFaces.current().dialog()
        PrimeFaces.current().dialog().openDynamic(nombreDialogoBusqueda, options, null);
    }

    //Metodo que permite recibir el dato seleccionado
    public void onObjectChosen(SelectEvent event) {
        try {
            Object objetoSeleccionado = (Object) event.getObject();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Dato Seleccionado", "");
            FacesContext.getCurrentInstance().addMessage(null, message);
            generalAbstractMb.cargarBusqueda(objetoSeleccionado);
            estadoEnum = EstadoFormEnum.EDITAR;
        } catch (ExcepcionCodefacLite ex) {
            Logger.getLogger(ControllerCodefacMb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getIndiceTabSecundario() {
        return indiceTabSecundario;
    }

    public void setIndiceTabSecundario(String indiceTabSecundario) {
        this.indiceTabSecundario = indiceTabSecundario;
    }

    public void mostrarAyuda() {
        System.out.println("Cambiando indice panel");
        indiceTabSecundario = "1";
    }

    public GeneralAbstractMb getGeneralAbstractMb() {
        return generalAbstractMb;
    }

    public void setGeneralAbstractMb(GeneralAbstractMb generalAbstractMb) {
        this.generalAbstractMb = generalAbstractMb;
    }

    public void agregarVista(GeneralAbstractMb vista) {
        try {
            generalAbstractMb = vista; 
            System.out.println("Agregando Vista"+vista.titulo());
            System.out.println("Estó agregando oficial "+estadoEnum.getLetra()+" "+estadoEnum.getLetra());
            PrimeFaces.current().ajax().update("formulario:txtTituloPagina"); //Actualizar un componente desde la vista
            //System.out.println("actualizando el titulo en la pagina:"+titulo);
        } catch (ExcepcionCodefacLite ex) {
            Logger.getLogger(ControllerCodefacMb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedOperationException ex) {
            Logger.getLogger(ControllerCodefacMb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getTituloPagina() {
        try {
            String tituloPagina = this.generalAbstractMb.titulo();
            return estadoEnum.construirFormato(tituloPagina);
            //return "Errro Nombre";
        } catch (ExcepcionCodefacLite ex) {
            Logger.getLogger(ControllerCodefacMb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedOperationException ex) {
            System.out.println("Metodo no implementado");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Successful", "Your message: PRUEBA"));
            context.addMessage(null, new FacesMessage("Second Message", "Additional Message Detail"));
        }
        return "error nombre pagina ";
    }
    
    public void cerrarDialogoResultados(){
        System.out.println("Ocultando el dialogo de respuestas");
        //generalAbstractMb.aceptarResultado();
        nuevo();
        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('dialogResultado').hide();"); //Todo: Parametrizar y poner en una funcion aparte este dialogo        
    
    }

    /*
    public String setTituloPagina(String titulo) {
        return estadoEnum.construirFormato(this.generalAbstractMb.titulo());
        //return "Errro Nombre";
    }*/

 /*public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }*/

    public Boolean getModoDialogo() {
        return modoDialogo;
    }

    public void setModoDialogo(Boolean modoDialogo) {
        this.modoDialogo = modoDialogo;
    }
    
    
}
