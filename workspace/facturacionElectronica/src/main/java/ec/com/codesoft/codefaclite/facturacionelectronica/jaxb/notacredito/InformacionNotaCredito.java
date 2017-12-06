/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.notacredito;

import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general.Compensacion;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general.TotalImpuesto;
import java.math.BigDecimal;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Carlos
 */
@XmlType(propOrder = {"fechaEmision",
    "dirEstablecimiento",
    "tipoIdentificacionComprador",
    "razonSocialComprador",
    "identificacionComprador",
    "contribuyenteEspecial",
    "obligadoContabilidad",
    "rise",
    "codDocModificado",
    "numDocModificado",
    "fechaEmisionDocSustento",
    "totalSinImpuestos",
    "compensaciones",
    "valorModificacion",
    "moneda",
    "totalModificacion",
    "motivo",})
public class InformacionNotaCredito {
    @XmlElement(name = "fechaEmision")
    private String fechaEmision;
    @XmlElement(name = "dirEstablecimiento")
    private String dirEstablecimiento;
    @XmlElement(name = "tipoIdentificacionComprador")
    private String tipoIdentificacionComprador;
    @XmlElement(name = "razonSocialComprador")
    private String razonSocialComprador;
    @XmlElement(name = "identificacionComprador")
    private String identificacionComprador;
    @XmlElement(name = "contribuyenteEspecial")
    private String contribuyenteEspecial;
    @XmlElement(name = "obligadoContabilidad")
    private String obligadoContabilidad;
    @XmlElement(name = "rise")
    private String rise;
    @XmlElement(name = "codDocModificado")
    private String codDocModificado;
    @XmlElement(name = "numDocModificado")
    private String numDocModificado;
    @XmlElement(name = "fechaEmisionDocSustento")
    private String fechaEmisionDocSustento;
    @XmlElement(name = "totalSinImpuestos")
    private BigDecimal totalSinImpuestos;
    @XmlElement(name = "valorModificacion")
    private String valorModificacion;
    @XmlElement(name = "moneda")
    private String moneda;
    @XmlElement(name = "totalModificacion")
    private BigDecimal totalModificacion;
    @XmlElement(name = "motivo")
    private String motivo;
    
    private List<TotalImpuesto> totalImpuestos;
    private List<Compensacion> compensaciones;

    public InformacionNotaCredito() {
    }
    
    
    public String getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getDirEstablecimiento() {
        return dirEstablecimiento;
    }

    public void setDirEstablecimiento(String dirEstablecimiento) {
        this.dirEstablecimiento = dirEstablecimiento;
    }

    public String getTipoIdentificacionComprador() {
        return tipoIdentificacionComprador;
    }

    public void setTipoIdentificacionComprador(String tipoIdentificacionComprador) {
        this.tipoIdentificacionComprador = tipoIdentificacionComprador;
    }

    public String getRazonSocialComprador() {
        return razonSocialComprador;
    }

    public void setRazonSocialComprador(String razonSocialComprador) {
        this.razonSocialComprador = razonSocialComprador;
    }

    public String getIdentificacionComprador() {
        return identificacionComprador;
    }

    public void setIdentificacionComprador(String identificacionComprador) {
        this.identificacionComprador = identificacionComprador;
    }

    public String getContribuyenteEspecial() {
        return contribuyenteEspecial;
    }

    public void setContribuyenteEspecial(String contribuyenteEspecial) {
        this.contribuyenteEspecial = contribuyenteEspecial;
    }

    public String getObligadoContabilidad() {
        return obligadoContabilidad;
    }

    public void setObligadoContabilidad(String obligadoContabilidad) {
        this.obligadoContabilidad = obligadoContabilidad;
    }

    public String getRise() {
        return rise;
    }

    public void setRise(String rise) {
        this.rise = rise;
    }

    public String getCodDocModificado() {
        return codDocModificado;
    }

    public void setCodDocModificado(String codDocModificado) {
        this.codDocModificado = codDocModificado;
    }

    public String getNumDocModificado() {
        return numDocModificado;
    }

    public void setNumDocModificado(String numDocModificado) {
        this.numDocModificado = numDocModificado;
    }

    public String getFechaEmisionDocSustento() {
        return fechaEmisionDocSustento;
    }

    public void setFechaEmisionDocSustento(String fechaEmisionDocSustento) {
        this.fechaEmisionDocSustento = fechaEmisionDocSustento;
    }

    public BigDecimal getTotalSinImpuestos() {
        return totalSinImpuestos;
    }

    public void setTotalSinImpuestos(BigDecimal totalSinImpuestos) {
        this.totalSinImpuestos = totalSinImpuestos;
    }

    public String getValorModificacion() {
        return valorModificacion;
    }

    public void setValorModificacion(String valorModificacion) {
        this.valorModificacion = valorModificacion;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    @XmlElementWrapper(name = "totalConImpuestos")
    @XmlElement(name = "totalImpuesto")
    public List<TotalImpuesto> getTotalImpuestos() {
        return totalImpuestos;
    }

    public void setTotalImpuestos(List<TotalImpuesto> totalImpuestos) {
        this.totalImpuestos = totalImpuestos;
    }

    @XmlElementWrapper(name = "compensaciones")
    @XmlElement(name = "compensacion")
    public List<Compensacion> getCompensaciones() {
        return compensaciones;
    }

    public void setCompensaciones(List<Compensacion> compensaciones) {
        this.compensaciones = compensaciones;
    }


    
    
}
