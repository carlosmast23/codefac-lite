/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.ws.codefac.test.service;

import ec.com.codesoft.codefaclite.ws.codefac.webservice.DevolverlicenciaRequestType;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.DevolverlicenciaResponseType;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.SOAPServer;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.SOAPServerPortType;

/**
 *
 * @author Carlos
 */
public abstract class WebServiceCodefac {
    
    /**
     * Obtiene el tipo de licencia del servidor
     * @param email
     * @return 
     */
    public static String getTipoLicencia(String email) {
        /**
         * Obtener el tipo de licencia del usuario
         */
        SOAPServer soapServer = new SOAPServer();
        SOAPServerPortType soapServerPort = soapServer.getSOAPServerPort();
        DevolverlicenciaRequestType parametrosLicencia = new DevolverlicenciaRequestType();
        parametrosLicencia.setEmail(email);
        DevolverlicenciaResponseType respuestaLicencia = soapServerPort.devolverlicencia(parametrosLicencia);
        return respuestaLicencia.getReturn();

    
    }
}
