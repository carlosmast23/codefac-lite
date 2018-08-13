/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.corecodefaclite.sms;

import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.PlantillaSmsEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.PlantillaSmsEnum.EtiquetaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SmsServiceIf;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public abstract class ControladorPlantillaSms {
    
    public static Boolean enviarMensajeConPlantilla(String numero,String mensaje,Map<PlantillaSmsEnum.EtiquetaEnum,String> mapParametros) throws ServicioCodefacException
    {
        try {
            //buscar etiquetas para remplazar
            for (EtiquetaEnum etiquetaEnum : PlantillaSmsEnum.EtiquetaEnum.values())
            {
                if(mensaje.contains(etiquetaEnum.getTag()))
                {
                    String valor=(mapParametros.get(etiquetaEnum)!=null)?mapParametros.get(etiquetaEnum):"";
                    mensaje=mensaje.replace(etiquetaEnum.getTag(),valor);
                }
            }
            
            //Quitar saltos de linea porque no funciona
            mensaje=mensaje.replace("\n"," "); //TODO: revisar cual es el codigo ascci de los mensajes de texto para saltos de linea 
            
            /**
             * Enviar el mensaje con el servidor de correo
             */
            SmsServiceIf servidorSms=ServiceFactory.getFactory().getSmsServiceIf();
            if (servidorSms.isServicioDisponible()) {
                servidorSms.enviarMensaje(numero, mensaje);
                return true;
            } else {
                return false;
            }
        } catch (RemoteException ex) {
            Logger.getLogger(ControladorPlantillaSms.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ControladorPlantillaSms.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
        return false;
        
    }
}
