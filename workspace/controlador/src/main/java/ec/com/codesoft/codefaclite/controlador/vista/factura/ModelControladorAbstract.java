/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vista.factura;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.mensajes.CodefacMsj;


/**
 *
 * @author CARLOS_CODESOFT
 */
public abstract class ModelControladorAbstract 
{
    protected TipoVista tipoVista;
    /**
     * Interfaz me va a permitir mandar mensajes de forma estandar
     */
    protected MensajeVistaInterface mensajeVista;

    public ModelControladorAbstract(MensajeVistaInterface mensajeVista) {
        this.mensajeVista = mensajeVista;
    }
    
    
    
    /**
     * Metodo que me permite enviar un mensaje para mostrar en las vistas dependiendo cada caso
     * @param mensaje 
     */
    public void mostrarMensaje(CodefacMsj mensaje)
    {
        if(mensajeVista!=null)
        {
            mensajeVista.mensaje(mensaje);
        }
    }
    
    /**
     * Enum que me permite clasificar con que vista estoy trabajando especialmente util para poder mostrar los mensajes
     */
    public enum TipoVista
    {
        ESCRITORIO,
        WEB;
    }
    
    public interface MensajeVistaInterface
    {
        public void mensaje(CodefacMsj codefacMensaje);
    }
}
