/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.factura;

import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.DetalleComprobanteAbstract;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general.ImpuestoComprobante;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Carlos
 */
@XmlType(propOrder = {"codigoPrincipal","descripcion","cantidad","precioUnitario","descuento","precioTotalSinImpuesto","impuestos"})
public class DetalleFacturaComprobante extends DetalleComprobanteAbstract{
    private String descripcion;
    //private BigDecimal cantidad;
    //private BigDecimal precioUnitario;
    //private BigDecimal descuento;
    //Precio Unitario*Cantidad-Descuento
    private BigDecimal precioTotalSinImpuesto;
    
    private String codigoPrincipal;
    
    //@XmlElementWrapper(name = "formasDePago")
    //@XmlElement(name = "formaPago")
    private List<ImpuestoComprobante> impuestos; 

    public DetalleFacturaComprobante() {
    }

    @XmlElement(name="descripcion")
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    
    @XmlElement(name="precioTotalSinImpuesto")
    public BigDecimal getPrecioTotalSinImpuesto() {
        return precioTotalSinImpuesto;
    }

    public void setPrecioTotalSinImpuesto(BigDecimal precioTotalSinImpuesto) {
        this.precioTotalSinImpuesto = precioTotalSinImpuesto;
    }

    @XmlElementWrapper(name = "impuestos")
    @XmlElement(name = "impuesto")
    public List<ImpuestoComprobante> getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(List<ImpuestoComprobante> impuestos) {
        this.impuestos = impuestos;
    }

    @XmlElement(name="codigoPrincipal")
    public String getCodigoPrincipal() {
        return codigoPrincipal;
    }

    public void setCodigoPrincipal(String codigoPrincipal) {
        this.codigoPrincipal = codigoPrincipal;
    }
    
    
    
    
}
