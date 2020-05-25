/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.ws.codefac.test.service;

import ec.com.codesoft.codefaclite.ws.codefac.webservice.ActualizarlicenciaRequestType;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.ActualizarlicenciaResponseType;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.ComprobarRequestType;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.ComprobarResponseType;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.DevolverlicenciaRequestType;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.DevolverlicenciaResponseType;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.NumaquinasRequestType;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.NumaquinasResponseType;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.ObtenerDiasFechaPagoRequestType;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.ObtenerDiasFechaPagoResponseType;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.ObtenerlicenciaRequestType;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.ObtenerlicenciaResponseType;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.SOAPServer;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.SOAPServerPortType;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.VerificarmoduloRequestType;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.VerificarmoduloResponseType;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.VerificarsoporteRequestType;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.VerificarsoporteResponseType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        //try
        //{
            SOAPServer soapServer = new SOAPServer();
            SOAPServerPortType soapServerPort = soapServer.getSOAPServerPort();
            DevolverlicenciaRequestType parametrosLicencia = new DevolverlicenciaRequestType();
            parametrosLicencia.setEmail(email);
            DevolverlicenciaResponseType respuestaLicencia = soapServerPort.devolverlicencia(parametrosLicencia);
            return respuestaLicencia.getReturn();
        //}
        //catch(com.sun.xml.internal.ws.client.ClientTransportException cte)
        //{
        //    throw cte;
        //}

    
    }
    
        /**
     * Obtiene la licencia del servidor
     * @param email
     * @return 
     */
    public static String getLicencia(String email) throws Exception{
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
        catch(com.sun.xml.ws.client.ClientTransportException cte)
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
    public static Integer getCantidadClientes(String email) {
        /**
         * Obtener el tipo de licencia del usuario
         */
        //try {
            SOAPServer soapServer = new SOAPServer();
            SOAPServerPortType soapServerPort = soapServer.getSOAPServerPort();
            NumaquinasRequestType parametrosLicencia = new NumaquinasRequestType();
            parametrosLicencia.setEmail(email);
            NumaquinasResponseType respuestaLicencia = soapServerPort.numaquinas(parametrosLicencia);
            //return respuestaLicencia.getReturn();
            return Integer.parseInt(respuestaLicencia.getReturn());
        //} catch (com.sun.xml.internal.ws.client.ClientTransportException cte) {
        //    throw cte;
        //}

    }
    
    
    /**
     * 
     * @param email email a consultar el modulo
     * @param letra letra asignada al modulo para verificar si esta habilitado
     * @return
     * @throws ClientTransportException 
     */
    public static String getModuloCodefac(String email,String letra) //throws ClientTransportException
    {
        //try {
            SOAPServer soapServer = new SOAPServer();
            SOAPServerPortType soapServerPort = soapServer.getSOAPServerPort();
            VerificarmoduloRequestType parametrosLicencia = new VerificarmoduloRequestType();
            parametrosLicencia.setEmail(email);
            parametrosLicencia.setTipo(letra);
            VerificarmoduloResponseType respuestaLicencia = soapServerPort.verificarmodulo(parametrosLicencia);
            return respuestaLicencia.getReturn();
        //} catch (com.sun.xml.internal.ws.client.ClientTransportException cte) {
        //    throw cte;
        //}    
    }
    
    public static boolean verificarCredenciales(String usuario, String clave)
    {
        SOAPServer soapServer=new SOAPServer();
        SOAPServerPortType soapServerPort=soapServer.getSOAPServerPort();
        ComprobarRequestType parametros=new ComprobarRequestType();
        parametros.setC(clave);
        parametros.setU(usuario);
        ComprobarResponseType respuesta= soapServerPort.comprobar(parametros);
        if("success".equals(respuesta.getReturn()))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public static void actualizarLicencia(String usuario,String licencia) 
    {
        SOAPServer soapServer = new SOAPServer();
        SOAPServerPortType soapServerPort = soapServer.getSOAPServerPort();

        ActualizarlicenciaRequestType parametros = new ActualizarlicenciaRequestType();
        parametros.setEmail(usuario);
        parametros.setLicencia(licencia);

        ActualizarlicenciaResponseType respuesta = soapServerPort.actualizarlicencia(parametros);
        respuesta.getReturn();
    }
    
    public static Boolean getVerificarSoporte(String usuario, String clave){
        //try {
            SOAPServer soapServer = new SOAPServer();
            SOAPServerPortType soapServerPort = soapServer.getSOAPServerPort();
            VerificarsoporteRequestType parametrosLicencia = new VerificarsoporteRequestType();
            parametrosLicencia.setUsuario(usuario);
            parametrosLicencia.setClave(clave);
            VerificarsoporteResponseType respuestaLicencia = soapServerPort.verificarsoporte(parametrosLicencia);
            return respuestaLicencia.getReturn().equals("success");
        //} catch (com.sun.xml.internal.ws.client.ClientTransportException cte) {
        //    throw cte;
        //}
    }
    
    
    public static Date obtenerFechaLimitePago(String usuario)
    {
        try
        {
            long startTime = System.nanoTime();
            SOAPServer soapServer=new SOAPServer();
            SOAPServerPortType soapServerPort=soapServer.getSOAPServerPort();
            ObtenerDiasFechaPagoRequestType parametros=new ObtenerDiasFechaPagoRequestType();
            parametros.setUsuario(usuario);

            ObtenerDiasFechaPagoResponseType respuesta= soapServerPort.obtenerDiasFechaPago(parametros);
            String respuestaStr=respuesta.getReturn();
            if("null".equals(respuestaStr))
            {
                return null;
            }
            else
            {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date fechaPago=format.parse(respuestaStr);
                    return fechaPago;
                } catch (ParseException ex) {
                    Logger.getLogger(WebServiceCodefac.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            long endTime = System.nanoTime() - startTime; // tiempo en que se ejecuta su método
            System.out.println("tiempo obtenerFechaLimitePago: "+endTime);
        }catch(com.sun.xml.ws.client.ClientTransportException e)
        {            
            e.printStackTrace();
        }
        
        return null;
        
    }

}
