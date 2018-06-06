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
import ec.com.codesoft.codefaclite.ws.codefac.webservice.VerificarmoduloaRequestType;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.VerificarmoduloaResponseType;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.VerificarmodulocRequestType;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.VerificarmodulocResponseType;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.VerificarmodulofRequestType;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.VerificarmodulofResponseType;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.VerificarmoduloiRequestType;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.VerificarmoduloiResponseType;

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
        try
        {
            SOAPServer soapServer = new SOAPServer();
            SOAPServerPortType soapServerPort = soapServer.getSOAPServerPort();
            VerificarmoduloiRequestType parametrosLicencia = new VerificarmoduloiRequestType();
            parametrosLicencia.setEmail(email);
            VerificarmoduloiResponseType respuestaLicencia = soapServerPort.verificarmoduloi(parametrosLicencia);
            return respuestaLicencia.getReturn();
        }
        catch(com.sun.xml.internal.ws.client.ClientTransportException cte)
        {
            throw cte;
        }
        
    }

    public static String getModuloGestionAcademica(String email) throws ClientTransportException {
        try {
            SOAPServer soapServer = new SOAPServer();
            SOAPServerPortType soapServerPort = soapServer.getSOAPServerPort();
            VerificarmoduloaRequestType parametrosLicencia = new VerificarmoduloaRequestType();
            parametrosLicencia.setEmail(email);
            VerificarmoduloaResponseType respuestaLicencia = soapServerPort.verificarmoduloa(parametrosLicencia);
            return respuestaLicencia.getReturn();
        } catch (com.sun.xml.internal.ws.client.ClientTransportException cte) {
            throw cte;
        }
    }

    public static String getModuloFacturacion(String email) throws ClientTransportException {
        try {
            SOAPServer soapServer = new SOAPServer();
            SOAPServerPortType soapServerPort = soapServer.getSOAPServerPort();
            VerificarmodulofRequestType parametrosLicencia = new VerificarmodulofRequestType();
            parametrosLicencia.setEmail(email);
            VerificarmodulofResponseType respuestaLicencia = soapServerPort.verificarmodulof(parametrosLicencia);
            return respuestaLicencia.getReturn();
        } catch (com.sun.xml.internal.ws.client.ClientTransportException cte) {
            throw cte;
        }
    }

    public static String getModuloCrm(String email) throws ClientTransportException {
        try {
            SOAPServer soapServer = new SOAPServer();
            SOAPServerPortType soapServerPort = soapServer.getSOAPServerPort();
            VerificarmodulocRequestType parametrosLicencia = new VerificarmodulocRequestType();
            parametrosLicencia.setEmail(email);
            VerificarmodulocResponseType respuestaLicencia = soapServerPort.verificarmoduloc(parametrosLicencia);
            return respuestaLicencia.getReturn();
        } catch (com.sun.xml.internal.ws.client.ClientTransportException cte) {
            throw cte;
        }
    }

}
