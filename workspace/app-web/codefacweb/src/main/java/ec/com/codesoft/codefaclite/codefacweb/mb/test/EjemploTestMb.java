/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.mb.test;

import ec.com.codesoft.codefaclite.codefacweb.core.GeneralAbstractMb;
import ec.com.codesoft.codefaclite.codefacweb.mb.utilidades.MensajeMb;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ClienteEstablecimientoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.EjemploDialogo;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.EmpleadoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ProformaBusqueda;
import ec.com.codesoft.codefaclite.controlador.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.PrimeFaces;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfacesPropertisFindWeb;

/**
 *
 * @author Carlos
 */
@ManagedBean
@ViewScoped
public class EjemploTestMb  extends GeneralAbstractMb implements Serializable {

    private String texto;
    
    @PostConstruct
    public void init()
    {
        System.out.println("Iniciando la aplicacion por primera vez");
    }
    
    @Override
    public void grabar() throws ExcepcionCodefacLite {
        System.out.println("grabar creado desde ejemplo");
        MensajeMb.mostrarMensajeDialogo(MensajeCodefacSistema.AccionesFormulario.GUARDADO);
    }

    @Override
    public void editar() throws ExcepcionCodefacLite {
        System.out.println("editar creado desde ejemplo");
        MensajeMb.mostrarMensajeDialogo(MensajeCodefacSistema.AccionesFormulario.EDITADO);
    }

    @Override
    public void imprimir() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite {
        
    }

    @Override
    public void buscar() throws ExcepcionCodefacLite {
        
    }

    @Override
    public void cargarBusqueda(Object obj) throws ExcepcionCodefacLite {
        
    }

    @Override
    public String titulo() throws ExcepcionCodefacLite,UnsupportedOperationException{
        return "Pantalla Ejemplo";
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public InterfaceModelFind obtenerDialogoBusqueda() throws ExcepcionCodefacLite {
        return new ClienteEstablecimientoBusquedaDialogo(sessionMb.getSession().getEmpresa());
    }
    
    
    public void imprimirOtro()
    {
        /*texto="seteado mas nuevo";
        PrimeFaces.current().ajax().update("formulario:txtEjemplo");
        PrimeFaces.current().ajax().update("formulario:txtTituloPagina"); //Actualizar un componente desde la vista
        System.out.println("otro nuevo ejemplo actualizar");*/
        //ProformaBusqueda p=new  ProformaBusqueda();
        
        EjemploDialogo p=new EjemploDialogo();
        DialogTest p2=new DialogTest();
        InterfaceModelFind i=p;
        InterfaceModelFind i2=(InterfaceModelFind)p;
        System.out.println("Columnas 2:"+p.getColumnas());
        System.out.println("Columnas 3:"+p2.getColumnas());
        
        
        if(p2 instanceof InterfacesPropertisFindWeb)
        {
            System.out.println("WEB SI implementa la interfaz");
        }
        else
        {
            System.out.println("WEB No implementa la interfaz");
        }
        
        if(p instanceof InterfacesPropertisFindWeb)
        {
            System.out.println("LIB SI implementa la interfaz");
        }
        else
        {
            System.out.println("LIB No implementa la interfaz");
        }
        
        //System.out.println("Columnas 1:"+i.getColumnas());        
        //System.out.println("Columnas 2"+i.getColumnas());        */
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        
    }
    
    
}
