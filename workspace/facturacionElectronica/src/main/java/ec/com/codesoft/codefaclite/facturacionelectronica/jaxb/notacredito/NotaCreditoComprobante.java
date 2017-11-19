/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.notacredito;

import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import java.sql.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos
 */
@XmlRootElement(name = ComprobanteElectronico.NOTA_CREDITO)
public class NotaCreditoComprobante extends ComprobanteElectronico {
    
    private List<DetalleNotaCreditoComprobante> detalles;
    
    @Override
    public String getTipoDocumento() {
        return ComprobanteElectronico.NOTA_CREDITO;
    }

    @XmlElementWrapper(name = "detalles")
    @XmlElement(name = "detalle")
    public List<DetalleNotaCreditoComprobante> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleNotaCreditoComprobante> detalles) {
        this.detalles = detalles;
    }

    @Override
    public String getFechaEmision() {
        return null;
    }

    @Override
    public String getIdentificacion() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
}
