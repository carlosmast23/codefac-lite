/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.virtuallmall;

import ec.com.codesoft.codefaclite.virtuallmall.ws.BuscarvmRequestType;
import ec.com.codesoft.codefaclite.virtuallmall.ws.BuscarvmResponseType;
import ec.com.codesoft.codefaclite.virtuallmall.ws.SOAPServer;
import ec.com.codesoft.codefaclite.virtuallmall.ws.SOAPServerPortType;

/**
 *
 * @author Carlos
 */
public class WebServiceVirtuallMall {
    public static boolean enviarBusqueda(String buscar,String celular,String tiempo)
    {
        SOAPServer soapsServer=new SOAPServer();
        SOAPServerPortType puerto=soapsServer.getSOAPServerPort();
        BuscarvmRequestType parametros=new BuscarvmRequestType();
        parametros.setBuscar(buscar);
        parametros.setCelular(celular);
        parametros.setTime(tiempo);
       
        BuscarvmResponseType respuesta=puerto.buscarvm(parametros);
        String respuestaString= respuesta.getReturn();
        if(respuestaString.equals("success"))
        {
            return true;
        }
        return false;
    }
}
