/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.guiaRetencion;

import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteEnum;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Carlos
 */
@XmlRootElement(name = ComprobanteElectronico.GUIA_REMISION)
@XmlType(propOrder = {
    "informacionTributaria",
    "infoNotaCredito",
    "detalles",
    "informacionAdicional"})
public class GuiaRetencionComprobante extends ComprobanteElectronico{
    
    private 

    @Override
    public String getTipoDocumento() {
        return ComprobanteEnum.GUIA_REMISION.getCodigo();
    }

    @Override
    public String getFechaEmision() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getRazonSocialComprador() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
