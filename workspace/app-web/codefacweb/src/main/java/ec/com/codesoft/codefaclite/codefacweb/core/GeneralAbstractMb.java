/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.core;

import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfaceModelFind;
import java.io.Serializable;

/**
 *
 * @author Carlos
 */
public abstract class GeneralAbstractMb implements Serializable{
    public abstract void grabar();
    /*public abstract void editar();
    public abstract void imprimir();*/
    public abstract void eliminar();
    public abstract void buscar();
    public abstract void cargarBusqueda(Object obj);
    public abstract InterfaceModelFind obtenerDialogoBusqueda();
    public String linkAyuda()
    {
        System.out.println("ayuda presionada");
        return "hola";
    }
   
    
    
}
