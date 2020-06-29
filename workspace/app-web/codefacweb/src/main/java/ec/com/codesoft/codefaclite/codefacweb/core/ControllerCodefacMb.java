/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.core;

import ec.com.codesoft.codefaclite.codefacweb.mb.utilidades.MensajeMb;
import ec.com.codesoft.codefaclite.codefacweb.mb.utilidades.UtilidadesDialogo;
import ec.com.codesoft.codefaclite.controlador.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.controlador.model.ReporteDialogListener;
import ec.com.codesoft.codefaclite.controlador.utilidades.UtilidadesCoreCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EstadoFormEnum;
import java.io.Serializable;
 ;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import ec.com.codesoft.codefaclite.corecodefaclite.interfaces.VistaCodefacIf;

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

    /**
     * Variable para saber si la vista que se esta controlando fue abierta en modo dialogo
     */
    private Boolean modoDialogo;
    //private String titulo;
    
    private Boolean visiblePanelComprobantes;

    @PostConstruct
    public void init() {
        System.out.println("Iniciando el postConstruct del controlador");
        modoDialogo=false;
        visiblePanelComprobantes=false;
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
    
    public void iniciar()
    {
        System.out.println("ejecutando metodo iniciar()");    
        try {
            UtilidadesCoreCodefac.ejecutarIniciar(generalAbstractMb);
            UtilidadesCoreCodefac.ejecutarIniciar(UtilidadesCoreCodefac.getControladorTodoVista(generalAbstractMb));    
            limpiar();
            
        } catch (ExcepcionCodefacLite ex) {
            Logger.getLogger(ControllerCodefacMb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void limpiar()
    {
        UtilidadesCoreCodefac.ejecutarLimpiar(generalAbstractMb);
        UtilidadesCoreCodefac.ejecutarLimpiar(UtilidadesCoreCodefac.getControladorTodoVista(generalAbstractMb));
    }
    
    public void aceptar()
    {
    
    }

    public void nuevo() {
        System.out.println("presionado boton nuevo");
        try {
            generalAbstractMb.nuevo();
            VistaCodefacIf controladorVista=UtilidadesCoreCodefac.getControladorTodoVista(generalAbstractMb);
            if(controladorVista!=null)
            {
                controladorVista.nuevo();
            }
            generalAbstractMb.mostrarResultadoGrabar=false;
            limpiar();            
            estadoEnum = EstadoFormEnum.GRABAR;
        } catch (ExcepcionCodefacLite ex) {
            Logger.getLogger(ControllerCodefacMb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedOperationException ex) {
            MensajeMb.mensaje(MensajeCodefacSistema.ErroresComunes.METODO_SIN_IMPLEMENTAR);
        } catch (Exception ex) {
            Logger.getLogger(ControllerCodefacMb.class.getName()).log(Level.SEVERE, null, ex);
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
                VistaCodefacIf vistaControlador=UtilidadesCoreCodefac.getControladorTodoVista(generalAbstractMb);
                if(vistaControlador!=null)
                {
                    vistaControlador.grabar();
                }
                else
                {
                    generalAbstractMb.grabar();
                }
                //generalAbstractMb.nuevo();
                
                //Programacion para retornar el valor cuando se seleccione un dialogo
                if(modoDialogo)
                {
                    if(generalAbstractMb instanceof DialogoWeb)
                    {
                        DialogoWeb dialogoWeb=(DialogoWeb) generalAbstractMb;
                        PrimeFaces.current().dialog().closeDynamic(dialogoWeb.getResultDialogo());
                        limpiar();
                    }
                    
                }
                else
                {                
                    //Solo limpiar si no va a mostrar un dialogo de resultado por que en ese caso
                    //todavia necesita las variables temporales
                    if(!generalAbstractMb.mostrarResultadoGrabar)
                    {
                        limpiar();
                    }
                }
                
            } catch (ExcepcionCodefacLite ex) {
                Logger.getLogger(ControllerCodefacMb.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedOperationException ex) {
                MensajeMb.mensaje(MensajeCodefacSistema.ErroresComunes.METODO_SIN_IMPLEMENTAR);
            } catch (Exception ex) {
                Logger.getLogger(ControllerCodefacMb.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (estadoEnum.equals(EstadoFormEnum.EDITAR)) {
            try {
                VistaCodefacIf vistaControlador=UtilidadesCoreCodefac.getControladorTodoVista(generalAbstractMb);
                if(vistaControlador!=null)
                {
                    vistaControlador.editar();
                }
                else
                {
                    generalAbstractMb.editar();
                }
                limpiar();
                estadoEnum = EstadoFormEnum.GRABAR;
            } catch (ExcepcionCodefacLite ex) {
                Logger.getLogger(ControllerCodefacMb.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedOperationException ex) {
                MensajeMb.mensaje(MensajeCodefacSistema.ErroresComunes.METODO_SIN_IMPLEMENTAR);
            } catch (Exception ex) {
                Logger.getLogger(ControllerCodefacMb.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

    public void delete() {
        try {
            System.err.println("Metodo para eliminar desde el controlador");
            generalAbstractMb.eliminar();
            estadoEnum=EstadoFormEnum.GRABAR;
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
            
            VistaCodefacIf vistaControlador = UtilidadesCoreCodefac.getControladorTodoVista(generalAbstractMb);
            if (vistaControlador != null) {
               UtilidadesDialogo.abrirDialogoBusqueda(vistaControlador.obtenerDialogoBusqueda());
            }
            else
            {
                UtilidadesDialogo.abrirDialogoBusqueda(generalAbstractMb.obtenerDialogoBusqueda());
            }
            //abrirDialogoBusqueda(generalAbstractMb.obtenerDialogoBusqueda());
        //} catch (ExcepcionCodefacLite ex) {
        //    Logger.getLogger(ControllerCodefacMb.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (UnsupportedOperationException ex) {
            MensajeMb.mensaje(MensajeCodefacSistema.ErroresComunes.METODO_SIN_IMPLEMENTAR);
        }
    }

    

    //Metodo que permite recibir el dato seleccionado
    public void onObjectChosen(SelectEvent event) {
        Object objetoSeleccionado = (Object) event.getObject();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Dato Seleccionado", "");
        FacesContext.getCurrentInstance().addMessage(null, message);
        
        VistaCodefacIf vistaControlador = UtilidadesCoreCodefac.getControladorTodoVista(generalAbstractMb);
        if(vistaControlador!=null)
        {
            try
            {
                vistaControlador.cargarDatosPantalla(objetoSeleccionado);
                //System.out.println("catrgando datos pantalla controlador ...");
            }catch(java.lang.UnsupportedOperationException u){}
        }
        
        try
        {
            generalAbstractMb.cargarDatosPantalla(objetoSeleccionado);   
            //System.out.println("catrgando datos pantalla controlador normal ...");
        }catch(java.lang.UnsupportedOperationException u){}
        
        estadoEnum = EstadoFormEnum.EDITAR;
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
    
    public void mostrarPanelComprobantes()
    {
        
        //System.out.println("Activando o desactivando el panel de los comprobantes");
        visiblePanelComprobantes=!visiblePanelComprobantes;
        //System.out.println(visiblePanelComprobantes);
        //MensajeMb.mensaje(new CodefacMsj("Actualizando panel",":)",1));
    }
    
    public void activarPanelComprobante()
    {
        visiblePanelComprobantes=true;
    }

    public void agregarVista(GeneralAbstractMb vista) {
        try {
            generalAbstractMb = vista;     
            System.out.println("===== >AGREGANDO EL CONTROLADOR A LA VISTA  ");
            PrimeFaces.current().ajax().update("formulario:pnlDatos"); //Actualizar un componente desde la vista
            //System.out.println("actualizando el titulo en la pagina:"+titulo);
            iniciar();
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

    public Boolean getVisiblePanelComprobantes() {
        return visiblePanelComprobantes;
    }

    public void setVisiblePanelComprobantes(Boolean visiblePanelComprobantes) {
        this.visiblePanelComprobantes = visiblePanelComprobantes;
    }
    
    public void imprimirPdf()
    {
        generalAbstractMb.getDialogoReporte().pdf();
    }
    
    public void imprimirExcel()
    {
        generalAbstractMb.getDialogoReporte().excel();
    }
    
    ///////////////////////////////////////////////////////////////////////////
    //              CLASES E INTERFACES ADICIONALES 
    ///////////////////////////////////////////////////////////////////////////
    
    
    
}
