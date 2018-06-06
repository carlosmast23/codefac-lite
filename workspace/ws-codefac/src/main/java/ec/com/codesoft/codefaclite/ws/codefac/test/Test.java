/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.ws.codefac.test;


import com.sun.xml.internal.ws.client.ClientTransportException;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.ActualizarlicenciaRequestType;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.ActualizarlicenciaResponseType;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.ActualizartipolicenciaRequestType;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.ActualizartipolicenciaResponseType;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.ComprobarRequestType;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.ComprobarResponseType;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.DevolverlicenciaRequestType;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.DevolverlicenciaResponseType;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.EstadousuarioRequestType;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.EstadousuarioResponseType;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.NumaquinasRequestType;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.NumaquinasResponseType;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.ObtenerlicenciaRequestType;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.ObtenerlicenciaResponseType;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.SOAPServer;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.SOAPServerPortType;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.VerificarmoduloRequestType;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.VerificarmoduloResponseType;

/**
 *
 * @author Carlos
 */
public class Test {
    public static void main(String[] args) {
        //obtenerLicencia();
        //setearLicencia();
        //obtenerNumeroMaquinasLicencia();
        //setearTipoLicencia();
        //obtenerTipoLicencia();
        //verificar();
        //devolverEstado();
        System.out.println(getModulo("carlosmast2302@gmail.com","f"));
    }
    
    public static void obtenerTipoLicencia()
    {
        SOAPServer soapServer=new SOAPServer();
        SOAPServerPortType soapServerPort=soapServer.getSOAPServerPort();        
        DevolverlicenciaRequestType parametros=new DevolverlicenciaRequestType();
        parametros.setEmail("carlosmast2301@hotmail.es");
        DevolverlicenciaResponseType respuesta=soapServerPort.devolverlicencia(parametros);
        System.out.println(respuesta.getReturn());   
    }
    
        public static void obtenerNumeroMaquinasLicencia()
    {
        SOAPServer soapServer=new SOAPServer();
        SOAPServerPortType soapServerPort=soapServer.getSOAPServerPort();        
        NumaquinasRequestType parametros=new NumaquinasRequestType();
        parametros.setEmail("carlosmast2301@hotmail.es");
        NumaquinasResponseType respuesta=soapServerPort.numaquinas(parametros);
        System.out.println(respuesta.getReturn());   
    }
    
    public static void obtenerLicencia()
    {
        SOAPServer soapServer=new SOAPServer();
        SOAPServerPortType soapServerPort=soapServer.getSOAPServerPort();        
        ObtenerlicenciaRequestType parametros=new ObtenerlicenciaRequestType();
        parametros.setEmail("carlosmast2301@hotmail.es");
        ObtenerlicenciaResponseType respuesta=soapServerPort.obtenerlicencia(parametros);
        System.out.println(respuesta.getReturn());        
    }
    
    public static void setearLicencia()
    {
        SOAPServer soapServer=new SOAPServer();
        SOAPServerPortType soapServerPort=soapServer.getSOAPServerPort();        
       
        ActualizarlicenciaRequestType parametros=new ActualizarlicenciaRequestType();
        //parametros.setEmail("carlosmast2301@hotmail.es");
        //parametros.setEmail("treborvtc@outlook.com");
        parametros.setEmail("trebortc@outlook.com");
        parametros.setLicencia("");

        ActualizarlicenciaResponseType respuesta=soapServerPort.actualizarlicencia(parametros);
        System.out.println(respuesta.getReturn());
    }
    
    public static void setearTipoLicencia()
    {
        SOAPServer soapServer=new SOAPServer();
        SOAPServerPortType soapServerPort=soapServer.getSOAPServerPort();        
       
        ActualizartipolicenciaRequestType parametros=new ActualizartipolicenciaRequestType();
        //parametros.setEmail("carlosmast2301@hotmail.es");
        parametros.setEmail("trebortc@hotmail.coms");
        parametros.setTipo("p");
        ActualizartipolicenciaResponseType respuesta=soapServerPort.actualizartipolicencia(parametros);
        System.out.println(respuesta.getReturn());
    }
    
    public static void verificar()
    {
        SOAPServer soapServer=new SOAPServer();
        SOAPServerPortType soapServerPort=soapServer.getSOAPServerPort();
        ComprobarRequestType parametros=new ComprobarRequestType();
        parametros.setC("Codesoft2301");
        parametros.setU("carlosmast2301@hotmail.es");
        ComprobarResponseType respuesta= soapServerPort.comprobar(parametros);
        //respuesta.get
        System.out.println(respuesta.getReturn());

    }
    
    public static void devolverEstado()
    {
        SOAPServer soapServer=new SOAPServer();
        SOAPServerPortType soapServerPort=soapServer.getSOAPServerPort();
        
        EstadousuarioRequestType parametros=new EstadousuarioRequestType();
        parametros.setEmail("carlosmast2302@gmail.com");
        EstadousuarioResponseType respuesta= soapServerPort.estadousuario(parametros);
        //respuesta.get
        System.out.println(respuesta.getReturn());

    }
    
        public static String getModulo(String email,String tipo) throws ClientTransportException {
        try {
            SOAPServer soapServer = new SOAPServer();
            SOAPServerPortType soapServerPort = soapServer.getSOAPServerPort();
            VerificarmoduloRequestType parametrosLicencia = new VerificarmoduloRequestType();
            parametrosLicencia.setEmail(email);
            parametrosLicencia.setTipo(tipo);
            VerificarmoduloResponseType respuestaLicencia = soapServerPort.verificarmodulo(parametrosLicencia);
            return respuestaLicencia.getReturn();
        } catch (com.sun.xml.internal.ws.client.ClientTransportException cte) {
            throw cte;
        }
    }

}
