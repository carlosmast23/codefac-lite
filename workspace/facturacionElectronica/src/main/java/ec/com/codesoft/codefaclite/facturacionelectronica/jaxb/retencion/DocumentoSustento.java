/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.retencion;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author DellWin10
 */
@XmlType(propOrder = {
    "codSustento", //REQUERIDO
    "codDocSustento", //REQUERIDO
    "numDocSustento", //REQUERIDO
    "fechaEmisionDocSustento", //REQUERIDO
    "fechaRegistroContable", //REQUERIDO
    "numAutDocSustento", //REQUERIDO
    "pagoLocExt", //REQUERIDO
    "tipoRegi", 
    "paisEfecPago",
    "aplicConvDobTrib",
    "pagExtSujRetNorLeg",
    "pagoRegFisM",
    "totalComprobantesReembolso",
    "totalBaseImponibleReembolso",
    "totalImpuestoReembolso",
    "totalSinImpuestos", //REQUERIDO
    "importeTotal", //REQUERIDO
    "impuestosDocSustento",
    "retenciones",
    "pagos",
})
public class DocumentoSustento implements Serializable{
    
    private String codSustento;
    private String codDocSustento;
    private String numDocSustento;
    private String fechaEmisionDocSustento;
    private String fechaRegistroContable;
    private String numAutDocSustento;
    private String pagoLocExt;
    private String tipoRegi;
    private String paisEfecPago;
    private String aplicConvDobTrib;
    private String pagExtSujRetNorLeg;
    private String pagoRegFisM;
    private BigDecimal totalComprobantesReembolso;
    private BigDecimal totalBaseImponibleReembolso;
    private BigDecimal totalImpuestoReembolso;
    private BigDecimal totalSinImpuestos;
    private BigDecimal importeTotal;
    
    private List<ImpuestoDocSustento> impuestosDocSustento;
    private List<DetalleRetencionComprobante> retenciones;
    private List<Pago> pagos;

    public DocumentoSustento() {
    }

    @XmlElement(name="codSustento")  
    public String getCodSustento() {
        return codSustento;
    }

    public void setCodSustento(String codSustento) {
        this.codSustento = codSustento;
    }

    @XmlElement(name="codDocSustento")  
    public String getCodDocSustento() {
        return codDocSustento;
    }

    public void setCodDocSustento(String codDocSustento) {
        this.codDocSustento = codDocSustento;
    }

    @XmlElement(name="numDocSustento")  
    public String getNumDocSustento() {
        return numDocSustento;
    }

    public void setNumDocSustento(String numDocSustento) {
        this.numDocSustento = numDocSustento;
    }
    

    @XmlElement(name="fechaEmisionDocSustento")  
    public String getFechaEmisionDocSustento() {
        return fechaEmisionDocSustento;
    }

    public void setFechaEmisionDocSustento(String fechaEmisionDocSustento) {
        this.fechaEmisionDocSustento = fechaEmisionDocSustento;
    }

    @XmlElement(name="fechaRegistroContable")  
    public String getFechaRegistroContable() {
        return fechaRegistroContable;
    }

    public void setFechaRegistroContable(String fechaRegistroContable) {
        this.fechaRegistroContable = fechaRegistroContable;
    }

    @XmlElement(name="numAutDocSustento")  
    public String getNumAutDocSustento() {
        return numAutDocSustento;
    }

    public void setNumAutDocSustento(String numAutDocSustento) {
        this.numAutDocSustento = numAutDocSustento;
    }

    @XmlElement(name="pagoLocExt")  
    public String getPagoLocExt() {
        return pagoLocExt;
    }

    public void setPagoLocExt(String pagoLocExt) {
        this.pagoLocExt = pagoLocExt;
    }

    @XmlElement(name="tipoRegi")  
    public String getTipoRegi() {
        return tipoRegi;
    }

    public void setTipoRegi(String tipoRegi) {
        this.tipoRegi = tipoRegi;
    }

    @XmlElement(name="paisEfecPago")  
    public String getPaisEfecPago() {
        return paisEfecPago;
    }

    public void setPaisEfecPago(String paisEfecPago) {
        this.paisEfecPago = paisEfecPago;
    }

    @XmlElement(name="aplicConvDobTrib")  
    public String getAplicConvDobTrib() {
        return aplicConvDobTrib;
    }

    public void setAplicConvDobTrib(String aplicConvDobTrib) {
        this.aplicConvDobTrib = aplicConvDobTrib;
    }

    @XmlElement(name="pagExtSujRetNorLeg")  
    public String getPagExtSujRetNorLeg() {
        return pagExtSujRetNorLeg;
    }

    public void setPagExtSujRetNorLeg(String pagExtSujRetNorLeg) {
        this.pagExtSujRetNorLeg = pagExtSujRetNorLeg;
    }

    @XmlElement(name="pagoRegFisM")  
    public String getPagoRegFisM() {
        return pagoRegFisM;
    }

    public void setPagoRegFisM(String pagoRegFisM) {
        this.pagoRegFisM = pagoRegFisM;
    }

    @XmlElement(name="totalComprobantesReembolso")  
    public BigDecimal getTotalComprobantesReembolso() {
        return totalComprobantesReembolso;
    }

    public void setTotalComprobantesReembolso(BigDecimal totalComprobantesReembolso) {
        this.totalComprobantesReembolso = totalComprobantesReembolso;
    }

    @XmlElement(name="totalBaseImponibleReembolso")  
    public BigDecimal getTotalBaseImponibleReembolso() {
        return totalBaseImponibleReembolso;
    }

    public void setTotalBaseImponibleReembolso(BigDecimal totalBaseImponibleReembolso) {
        this.totalBaseImponibleReembolso = totalBaseImponibleReembolso;
    }

    @XmlElement(name="totalImpuestoReembolso")  
    public BigDecimal getTotalImpuestoReembolso() {
        return totalImpuestoReembolso;
    }

    public void setTotalImpuestoReembolso(BigDecimal totalImpuestoReembolso) {
        this.totalImpuestoReembolso = totalImpuestoReembolso;
    }

    @XmlElement(name="totalSinImpuestos")  
    public BigDecimal getTotalSinImpuestos() {
        return totalSinImpuestos;
    }

    public void setTotalSinImpuestos(BigDecimal totalSinImpuestos) {
        this.totalSinImpuestos = totalSinImpuestos;
    }

    @XmlElement(name="importeTotal")  
    public BigDecimal getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(BigDecimal importeTotal) {
        this.importeTotal = importeTotal;
    }
    
    @XmlElementWrapper(name = "retenciones")
    @XmlElement(name = "retencion")
    public List<DetalleRetencionComprobante> getRetenciones() {
        return retenciones;
    }

    public void setRetenciones(List<DetalleRetencionComprobante> retenciones) {
        this.retenciones = retenciones;
    }
    
    @XmlElementWrapper(name = "impuestosDocSustento")
    @XmlElement(name = "impuestoDocSustento")
    public List<ImpuestoDocSustento> getImpuestosDocSustento() {
        return impuestosDocSustento;
    }

    public void setImpuestosDocSustento(List<ImpuestoDocSustento> impuestosDocSustento) {
        this.impuestosDocSustento = impuestosDocSustento;
    }

    @XmlElementWrapper(name = "pagos")
    @XmlElement(name = "pago")
    public List<Pago> getPagos() {
        return pagos;
    }

    public void setPagos(List<Pago> pagos) {
        this.pagos = pagos;
    }
    
    
    
    
    
}
