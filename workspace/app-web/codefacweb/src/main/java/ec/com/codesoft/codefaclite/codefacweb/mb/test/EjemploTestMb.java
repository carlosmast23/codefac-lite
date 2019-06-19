/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.mb.test;

import ec.com.codesoft.codefaclite.codefacweb.core.GeneralAbstractMb;
import ec.com.codesoft.codefaclite.codefacweb.mb.utilidades.MensajeMb;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ProformaBusqueda;
import ec.com.codesoft.codefaclite.controlador.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfaceModelFind;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.PrimeFaces;

/**
 *
 * @author Carlos
 */
@ManagedBean
@ViewScoped
public class EjemploTestMb  extends GeneralAbstractMb implements Serializable {

    private String texto;
    
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
        return new ProformaBusqueda();
    }
    
    
    public void imprimirOtro()
    {
        texto="seteado mas nuevo";
        PrimeFaces.current().ajax().update("formulario:txtEjemplo");
        PrimeFaces.current().ajax().update("formulario:txtTituloPagina"); //Actualizar un componente desde la vista
        System.out.println("otro nuevo ejemplo actualizar");
        
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
