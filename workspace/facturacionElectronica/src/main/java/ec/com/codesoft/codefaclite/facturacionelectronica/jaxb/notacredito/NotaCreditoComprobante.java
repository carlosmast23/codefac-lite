/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.notacredito;

import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteEnum;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
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
@XmlRootElement(name = ComprobanteElectronico.NOTA_CREDITO)
@XmlType(propOrder = {
    "informacionTributaria",
    "infoNotaCredito",
    "detalles",
    "informacionAdicional"})
public class NotaCreditoComprobante extends ComprobanteElectronico {
    
    private InformacionNotaCredito infoNotaCredito;
    private List<DetalleNotaCreditoComprobante> detalles;
    
    @Override
    public String getTipoDocumento() {
        return ComprobanteEnum.NOTA_CREDITO.getCodigo();
    }

    @XmlElement(name = "infoNotaCredito")
    public InformacionNotaCredito getInfoNotaCredito() {
        return infoNotaCredito;
    }

    public void setInfoNotaCredito(InformacionNotaCredito infoNotaCredito) {
        this.infoNotaCredito = infoNotaCredito;
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
        return this.infoNotaCredito.getFechaEmision();
    }

    @XmlAttribute(name = "version")
    //@Override
    public String getVersionAttribute() {
        return "1.1.0";
    }

    @Override
    public String getRazonSocialComprador() {
        return this.infoNotaCredito.getRazonSocialComprador();
    }

    @Override
    public String getDireccionEstablecimiento() {
        return this.infoNotaCredito.getDirEstablecimiento();
    }

    

    
    
}
