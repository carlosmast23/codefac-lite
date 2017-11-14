/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.factura;

import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.notacredito.DetalleNotaCreditoComprobante;
import java.sql.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;



/**
 *
 * @author Carlos
 */
@XmlRootElement(name = ComprobanteElectronico.FACTURA)
public class FacturaComprobante extends ComprobanteElectronico{

    private InformacionFactura informacionFactura;
    private List<DetalleNotaCreditoComprobante> detalles;
    
    @Override
    public String getTipoDocumento() {
        return ComprobanteElectronico.FACTURA;
    }

    @XmlElementWrapper(name = "detalles")
    @XmlElement(name = "detalle")
    public List<DetalleNotaCreditoComprobante> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleNotaCreditoComprobante> detalles) {
        this.detalles = detalles;
    }

    @XmlElement(name = "infoFactura")
    public InformacionFactura getInformacionFactura() {
        return informacionFactura;
    }

    @Override
    public Date getFechaEmision() {
        return this.informacionFactura.getFechaEmision();
    }

    @Override
    public String getIdentificacion() {
        return this.informacionFactura.getIdentificacionComprador();
    }

    public void setInformacionFactura(InformacionFactura informacionFactura) {
        this.informacionFactura = informacionFactura;
    }

    
    
}
