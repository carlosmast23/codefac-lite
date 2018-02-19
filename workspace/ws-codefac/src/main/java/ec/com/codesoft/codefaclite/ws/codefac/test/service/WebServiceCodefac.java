/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.ws.codefac.test.service;

import com.sun.xml.internal.ws.client.ClientTransportException;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.DevolverlicenciaRequestType;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.DevolverlicenciaResponseType;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.NumaquinasRequestType;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.NumaquinasResponseType;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.ObtenerlicenciaRequestType;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.ObtenerlicenciaResponseType;
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
    public static String getTipoLicencia(String email) throws ClientTransportException {
        /**
         * Obtener el tipo de licencia del usuario
         */
        try
        {
            SOAPServer soapServer = new SOAPServer();
            SOAPServerPortType soapServerPort = soapServer.getSOAPServerPort();
            DevolverlicenciaRequestType parametrosLicencia = new DevolverlicenciaRequestType();
            parametrosLicencia.setEmail(email);
            DevolverlicenciaResponseType respuestaLicencia = soapServerPort.devolverlicencia(parametrosLicencia);
            return respuestaLicencia.getReturn();
        }
        catch(com.sun.xml.internal.ws.client.ClientTransportException cte)
        {
            throw cte;
        }

    
    }
    
        /**
     * Obtiene la licencia del servidor
     * @param email
     * @return 
     */
    public static String getLicencia(String email)throws ClientTransportException{
        /**
         * Obtener el tipo de licencia del usuario
         */
        try
        {
            SOAPServer soapServer = new SOAPServer();
            SOAPServerPortType soapServerPort = soapServer.getSOAPServerPort();
            ObtenerlicenciaRequestType parametrosLicencia = new ObtenerlicenciaRequestType();
            parametrosLicencia.setEmail(email);        
            ObtenerlicenciaResponseType respuestaLicencia = soapServerPort.obtenerlicencia(parametrosLicencia);
            return respuestaLicencia.getReturn();
        }
        catch(com.sun.xml.internal.ws.client.ClientTransportException cte)
        {
            throw cte;
        }

    
    }
    
    
    /**
     * Obtiene la licencia del servidor
     *
     * @param email
     * @return
     */
    public static Integer getCantidadClientes(String email) throws ClientTransportException {
        /**
         * Obtener el tipo de licencia del usuario
         */
        try {
            SOAPServer soapServer = new SOAPServer();
            SOAPServerPortType soapServerPort = soapServer.getSOAPServerPort();
            NumaquinasRequestType parametrosLicencia = new NumaquinasRequestType();
            parametrosLicencia.setEmail(email);
            NumaquinasResponseType respuestaLicencia = soapServerPort.numaquinas(parametrosLicencia);
            //return respuestaLicencia.getReturn();
            return Integer.parseInt(respuestaLicencia.getReturn());
        } catch (com.sun.xml.internal.ws.client.ClientTransportException cte) {
            throw cte;
        }

    }
    
    
        /**
     * Obtiene la licencia del servidor
     *
     * @param email
     * @return
     */
    public static String getModuloInventario(String email) throws ClientTransportException {
        return "s";
    }

    public static String getModuloGestionAcademica(String email) throws ClientTransportException {
        return "s";
    }

    public static String getModuloFacturacion(String email) throws ClientTransportException {
        return "s";
    }

    public static String getModuloCrm(String email) throws ClientTransportException {
        return "n";
    }

}
