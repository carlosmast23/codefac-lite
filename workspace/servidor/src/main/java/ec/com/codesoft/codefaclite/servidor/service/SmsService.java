/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servicios.ServidorSMS;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SmsServiceIf;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Carlos
 */
public class SmsService extends UnicastRemoteObject implements SmsServiceIf{ 

    public SmsService() throws RemoteException {
    }
    
    public void enviarMensaje(String numero , String mensaje)throws RemoteException,ServicioCodefacException
    {
        ServidorSMS servidorsms=ServidorSMS.getInstance();
        mensaje=UtilidadesTextos.quitaDiacriticos(mensaje); //Funcion que permite quitar acentuasiones y simbolo especiales que pueden generar problemas
        
        //Si no se puede enviar el mensaje manda una excecion al cliente
        if(!servidorsms.enviarMensaje(numero, mensaje))
        {            
            throw new ServicioCodefacException("No se puedo enviar el mensaje, ocurrio un error con el servidor de mensajeria");
        }
    }
    
    public boolean isServicioDisponible()throws RemoteException
    {
        ServidorSMS servidorsms=ServidorSMS.getInstance();
        return servidorsms.servicioDisponible();
    }
    
}
