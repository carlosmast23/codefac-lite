/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos;

import ec.com.codesoft.codefaclite.facturacionelectronica.evento.ListenerComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Carlos
 */
public class ComprobanteDataGenerico implements ComprobanteDataInterface,Serializable {
    
    private List<String> correos;

    public ComprobanteDataGenerico(List<String> correos) {
        this.correos = correos;
    }
    
    

    @Override
    public String getCodigoComprobante() {
        return "";
    }

    @Override
    public String getSecuencial() {
        return "";
    }

    @Override
    public Map<String, String> getMapAdicional() {
        return new HashMap<String,String>();
    }

    @Override
    public List<String> getCorreos() {
        return correos;
    }

    @Override
    public ComprobanteElectronico getComprobante() {
        return null;
    }

    @Override
    public ListenerComprobanteElectronico getListenerComprobanteElectronico() {
        return null;
    }
    
}
