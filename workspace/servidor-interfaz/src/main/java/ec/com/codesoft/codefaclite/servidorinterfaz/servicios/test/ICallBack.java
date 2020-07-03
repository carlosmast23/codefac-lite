/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios.test;

import ec.com.codesoft.codefaclite.facturacionelectronica.AlertaComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.ClaveAcceso;
import ec.com.codesoft.codefaclite.facturacionelectronica.exception.ComprobanteElectronicoException;
import ec.com.codesoft.codefaclite.servidorinterfaz.callback.ClienteInterfaceComprobante;
import java.io.Serializable;
import java.util.List;


/**
 *
 * @author CARLOS_CODESOFT
 */
public class ICallBack implements ClienteInterfaceComprobante , Serializable{

    public void termino(byte[] byteJasperPrint, List<AlertaComprobanteElectronico> alertas) {
        System.out.println("termino");
    }

    public void iniciado() {
        System.out.println("iniciando");
    }

    public void procesando(int etapa, ClaveAcceso clave) {
        System.out.println("procesando");
    }

    public void error(ComprobanteElectronicoException cee, String claveAcceso) {
        System.out.println("error");
    }
    
    
}
