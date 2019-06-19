/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.core;

import ec.com.codesoft.codefaclite.controlador.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfaceModelFind;
import java.io.Serializable;
import org.primefaces.PrimeFaces;

/**
 *
 * @author Carlos
 */
public abstract class GeneralAbstractMb implements Serializable {

    public abstract void nuevo() throws ExcepcionCodefacLite, UnsupportedOperationException;

    public abstract void grabar() throws ExcepcionCodefacLite, UnsupportedOperationException;

    public abstract void editar() throws ExcepcionCodefacLite, UnsupportedOperationException;

    public abstract void imprimir() throws ExcepcionCodefacLite, UnsupportedOperationException;

    public abstract void eliminar() throws ExcepcionCodefacLite, UnsupportedOperationException;

    public abstract void buscar() throws ExcepcionCodefacLite, UnsupportedOperationException;

    public abstract void cargarBusqueda(Object obj) throws ExcepcionCodefacLite, UnsupportedOperationException;

    public abstract String titulo() throws ExcepcionCodefacLite, UnsupportedOperationException;

    public abstract InterfaceModelFind obtenerDialogoBusqueda() throws ExcepcionCodefacLite;

    private ResultadoDialogo resultadoDialogo = new ResultadoDialogo();

    public String linkAyuda() {
        System.out.println("ayuda presionada");
        return "hola";
    }

    public ResultadoDialogo getResultadoDialogo() {
        return resultadoDialogo;
    }

    public void setResultadoDialogo(ResultadoDialogo resultadoDialogo) {
        this.resultadoDialogo = resultadoDialogo;
    }

    protected void mostrarDialogoResultado(CodefacMsj codefacMensaje) {
        resultadoDialogo.setMensaje(codefacMensaje.mensaje);
        resultadoDialogo.setTitulo(codefacMensaje.titulo);
        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('dialogResultado').show();"); //Todo: Parametrizar y poner en una funcion aparte este dialogo
    }
    
    public void aceptarResultado()
    {
        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('dialogResultado').hide();"); //Todo: Parametrizar y poner en una funcion aparte este dialogo        
    }
    
    
}
