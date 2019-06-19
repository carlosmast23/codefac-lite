/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.core;

import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfaceModelFind;
import java.io.Serializable;

/**
 *
 * @author Carlos
 */
public abstract class GeneralAbstractMb implements Serializable{
    public abstract void nuevo() throws ExcepcionCodefacLite;
    public abstract void grabar() throws ExcepcionCodefacLite;
    public abstract void editar() throws ExcepcionCodefacLite;
    public abstract void imprimir()throws ExcepcionCodefacLite,UnsupportedOperationException;
    public abstract void eliminar() throws ExcepcionCodefacLite;
    public abstract void buscar() throws ExcepcionCodefacLite;
    public abstract void cargarBusqueda(Object obj) throws ExcepcionCodefacLite;
    public abstract String titulo() throws ExcepcionCodefacLite,UnsupportedOperationException;
    public abstract InterfaceModelFind obtenerDialogoBusqueda() throws ExcepcionCodefacLite;
    
    
    
    public String linkAyuda()
    {
        System.out.println("ayuda presionada");
        return "hola";
    }
   
    
    
}
