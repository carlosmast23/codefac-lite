 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.factura;

import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general.TotalImpuesto;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Carlos
 */
@XmlType(propOrder = {"fechaEmision",
    "obligadoContabilidad",
    "tipoIdentificacionComprador",
    "razonSocialComprador",
    "identificacionComprador",
    "totalSinImpuestos",
    "totalDescuento",
    "totalImpuestos",
    "importeTotal",
    "formaPagos"})
public class InformacionFactura implements Serializable{
    private String fechaEmision;
    private String tipoIdentificacionComprador;
    private String razonSocialComprador;
    private String identificacionComprador;
    private BigDecimal totalSinImpuestos;
    private BigDecimal totalDescuento;
    private BigDecimal importeTotal;
    
    private String obligadoContabilidad;
    
    private List<TotalImpuesto> totalImpuestos;
    private List<FormaPagoComprobante> formaPagos;

    public InformacionFactura() {
    }

    @XmlElement(name = "fechaEmision")
    public String getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
    }
    
    @XmlElement(name = "tipoIdentificacionComprador")
    public String getTipoIdentificacionComprador() {
        return tipoIdentificacionComprador;
    }

    public void setTipoIdentificacionComprador(String tipoIdentificacionComprador) {
        this.tipoIdentificacionComprador = tipoIdentificacionComprador;
    }

    @XmlElement(name = "razonSocialComprador")
    public String getRazonSocialComprador() {
        return razonSocialComprador;
    }

    public void setRazonSocialComprador(String razonSocialComprador) {
        this.razonSocialComprador = razonSocialComprador;
    }

    @XmlElement(name = "identificacionComprador")
    public String getIdentificacionComprador() {
        return identificacionComprador;
    }

    public void setIdentificacionComprador(String identificacionComprador) {
        this.identificacionComprador = identificacionComprador;
    }

    @XmlElement(name = "totalSinImpuestos")
    public BigDecimal getTotalSinImpuestos() {
        return totalSinImpuestos;
    }


    public void setTotalSinImpuestos(BigDecimal totalSinImpuestos) {
        this.totalSinImpuestos = totalSinImpuestos;
    }

    @XmlElement(name = "totalDescuento")
    public BigDecimal getTotalDescuento() {
        return totalDescuento;
    }

    public void setTotalDescuento(BigDecimal totalDescuento) {
        this.totalDescuento = totalDescuento;
    }

    @XmlElement(name = "importeTotal")
    public BigDecimal getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(BigDecimal importeTotal) {
        this.importeTotal = importeTotal;
    }
    
    

    @XmlElementWrapper(name = "totalConImpuestos")
    @XmlElement(name = "totalImpuesto")
    public List<TotalImpuesto> getTotalImpuestos() {
        return totalImpuestos;
    }

    @XmlElementWrapper(name = "pagos")
    @XmlElement(name = "pago")
    public List<FormaPagoComprobante> getFormaPagos() {
        return formaPagos;
    }

    public void setFormaPagos(List<FormaPagoComprobante> formaPagos) {
        this.formaPagos = formaPagos;
    }
    
    

    public void setTotalImpuestos(List<TotalImpuesto> totalImpuestos) {
        this.totalImpuestos = totalImpuestos;
    }

    @XmlElement(name = "obligadoContabilidad")    
    public String getObligadoContabilidad() {
        return obligadoContabilidad;
    }

    public void setObligadoContabilidad(String obligadoContabilidad) {
        this.obligadoContabilidad = obligadoContabilidad;
    }
    
    
    
    

}
