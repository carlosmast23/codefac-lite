/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vista.factura;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefacInterface;
import ec.com.codesoft.codefaclite.controlador.vistas.core.ControladorCampoTextoAnot;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesReflexion;


/**
 *TODO: Hacer un metodo generico que me permita saber el estado si creando o editando
 * @author CARLOS_CODESOFT
 */
public abstract class ModelControladorAbstract <T>
{
    protected TipoVista tipoVista;
    /**
     * Interfaz me va a permitir mandar mensajes de forma estandar
     */
    protected MensajeVistaInterface mensajeVista;
    
    protected SessionCodefacInterface session;
    
    protected T interfaz;
    
    

    public ModelControladorAbstract(MensajeVistaInterface mensajeVista) {
        this.mensajeVista = mensajeVista;
    }
    
    public ModelControladorAbstract(MensajeVistaInterface mensajeVista,SessionCodefacInterface session) {
        this.mensajeVista = mensajeVista;
        this.session=session;
    }

    public ModelControladorAbstract(MensajeVistaInterface mensajeVista, SessionCodefacInterface session, T interfaz) {
        this.mensajeVista = mensajeVista;
        this.session = session;
        this.interfaz = interfaz;
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
     * Metodo que me permite funcionar de medio de comunicación para activar los listener de la pantalla
     * @param value 
     */
    public void setProperty(Object value,Object newValue)
    {
        if(value instanceof ControladorCampoTextoAnot)
        {
            value=newValue;
            //Avisar al componente que sucedio una modificación en los datos
            UtilidadesReflexion.ejecutarAnotacionEnPropiedades(Class.class,ControladorCampoTextoAnot.class,new UtilidadesReflexion.AnotacionIf<ControladorCampoTextoAnot>() {
                @Override
                public void ejecutar(ControladorCampoTextoAnot anotacion) {
                    
                }
            });
        }
    }
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Enum que me permite clasificar con que vista estoy trabajando especialmente util para poder mostrar los mensajes
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
