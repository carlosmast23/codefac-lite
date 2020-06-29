/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.callback;

import ec.com.codesoft.codefaclite.facturacionelectronica.ClaveAcceso;
import ec.com.codesoft.codefaclite.facturacionelectronica.exception.ComprobanteElectronicoException;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.ComprobanteData;
 
 ;
import java.util.List;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author Carlos
 */
public interface ClienteInterfaceComprobanteLote   {
    
    public void termino(List<ComprobanteData> comprobantes);    
    
    public void iniciado();    
    
    public void procesando(int etapa);    
    
    public void error(ComprobanteElectronicoException cee);    
    
}
