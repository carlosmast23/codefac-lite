/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.factura;

import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general.InformacionAdicional;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general.InformacionTributaria;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.notacredito.DetalleNotaCreditoComprobante;
import java.sql.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;



/**
 *
 * @author Carlos
 */
@XmlRootElement(name = ComprobanteElectronico.FACTURA)
@XmlType(propOrder = {"informacionTributaria","informacionFactura","detalles","informacionAdicional"})
public class FacturaComprobante extends ComprobanteElectronico{

    private InformacionFactura informacionFactura;
    private List<DetalleFacturaComprobante> detalles;
    
    @Override
    public String getTipoDocumento() {
        return ComprobanteElectronico.FACTURA;
    }
    

    @XmlElementWrapper(name = "detalles")
    @XmlElement(name = "detalle")
    public List<DetalleFacturaComprobante> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleFacturaComprobante> detalles) {
        this.detalles = detalles;
    }

    @XmlElement(name = "infoFactura")
    public InformacionFactura getInformacionFactura() {
        return informacionFactura;
    }

    @Override
    public String getFechaEmision() {
        return this.informacionFactura.getFechaEmision();
    }

    public void setInformacionFactura(InformacionFactura informacionFactura) {
        this.informacionFactura = informacionFactura;
    }
/*
    @XmlElement(name="infoTributaria")
    public InformacionTributaria getInformacionTributaria() {
        return informacionTributaria;
    }
    
    @XmlElementWrapper(name = "infoAdicional")
    @XmlElement(name = "campoAdicional")
    public List<InformacionAdicional> getInformacionAdicional() {
        return informacionAdicional;
    }
*/

    @XmlAttribute(name = "version")
    //@Override
    public String getVersionAttribute() {
        return "1.0.0";
    }

    @Override
    public String getRazonSocialComprador() {
        return getInformacionFactura().getRazonSocialComprador();
    }
    
    
}
