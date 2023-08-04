/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.factura;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author CARLOS_CODESOFT
 */

@XmlType(propOrder = {
    "tipoIdentificacionProveedorReembolso",
    "identificacionProveedorReembolso",
    "codPaisPagoProveedorReembolso",
    "tipoProveedorReembolso",
    "codDocReembolso",
    "estabDocReembolso",
    "ptoEmiDocReembolso",
    "secuencialDocReembolso",    
    "fechaEmisionDocReembolso",
    "numeroautorizacionDocReemb",
    "detalleImpuestoList"
})
public class ReembolsoDetalleComprobante implements Serializable{
    
    private String tipoIdentificacionProveedorReembolso;
    private String identificacionProveedorReembolso;
    private String codPaisPagoProveedorReembolso;
    private String tipoProveedorReembolso;
    private String codDocReembolso;
    private String estabDocReembolso;
    private String ptoEmiDocReembolso;
    private String secuencialDocReembolso;
    private String fechaEmisionDocReembolso;
    private String numeroautorizacionDocReemb;
    
    private List<DetalleImpuestoReembolsoComprobante> detalleImpuestoList;

    public ReembolsoDetalleComprobante() {
    }

    @XmlElementWrapper(name = "detalleImpuestos")
    @XmlElement(name = "detalleImpuesto")
    public List<DetalleImpuestoReembolsoComprobante> getDetalleImpuestoList() {
        return detalleImpuestoList;
    }

    public void setDetalleImpuestoList(List<DetalleImpuestoReembolsoComprobante> detalleImpuestoList) {
        this.detalleImpuestoList = detalleImpuestoList;
    }
    
    

    @XmlElement(name = "tipoIdentificacionProveedorReembolso")
    public String getTipoIdentificacionProveedorReembolso() {
        return tipoIdentificacionProveedorReembolso;
    }

    public void setTipoIdentificacionProveedorReembolso(String tipoIdentificacionProveedorReembolso) {
        this.tipoIdentificacionProveedorReembolso = tipoIdentificacionProveedorReembolso;
    }

    @XmlElement(name = "identificacionProveedorReembolso")
    public String getIdentificacionProveedorReembolso() {
        return identificacionProveedorReembolso;
    }

    public void setIdentificacionProveedorReembolso(String identificacionProveedorReembolso) {
        this.identificacionProveedorReembolso = identificacionProveedorReembolso;
    }

    @XmlElement(name = "codPaisPagoProveedorReembolso")
    public String getCodPaisPagoProveedorReembolso() {
        return codPaisPagoProveedorReembolso;
    }

    public void setCodPaisPagoProveedorReembolso(String codPaisPagoProveedorReembolso) {
        this.codPaisPagoProveedorReembolso = codPaisPagoProveedorReembolso;
    }

    @XmlElement(name = "tipoProveedorReembolso")
    public String getTipoProveedorReembolso() {
        return tipoProveedorReembolso;
    }

    public void setTipoProveedorReembolso(String tipoProveedorReembolso) {
        this.tipoProveedorReembolso = tipoProveedorReembolso;
    }

    @XmlElement(name = "codDocReembolso")
    public String getCodDocReembolso() {
        return codDocReembolso;
    }

    public void setCodDocReembolso(String codDocReembolso) {
        this.codDocReembolso = codDocReembolso;
    }

    @XmlElement(name = "estabDocReembolso")
    public String getEstabDocReembolso() {
        return estabDocReembolso;
    }

    public void setEstabDocReembolso(String estabDocReembolso) {
        this.estabDocReembolso = estabDocReembolso;
    }

    @XmlElement(name = "ptoEmiDocReembolso")
    public String getPtoEmiDocReembolso() {
        return ptoEmiDocReembolso;
    }

    public void setPtoEmiDocReembolso(String ptoEmiDocReembolso) {
        this.ptoEmiDocReembolso = ptoEmiDocReembolso;
    }

    @XmlElement(name = "secuencialDocReembolso")
    public String getSecuencialDocReembolso() {
        return secuencialDocReembolso;
    }

    public void setSecuencialDocReembolso(String secuencialDocReembolso) {
        this.secuencialDocReembolso = secuencialDocReembolso;
    }

    @XmlElement(name = "fechaEmisionDocReembolso")
    public String getFechaEmisionDocReembolso() {
        return fechaEmisionDocReembolso;
    }

    public void setFechaEmisionDocReembolso(String fechaEmisionDocReembolso) {
        this.fechaEmisionDocReembolso = fechaEmisionDocReembolso;
    }

    @XmlElement(name = "numeroautorizacionDocReemb")
    public String getNumeroautorizacionDocReemb() {
        return numeroautorizacionDocReemb;
    }

    public void setNumeroautorizacionDocReemb(String numeroautorizacionDocReemb) {
        this.numeroautorizacionDocReemb = numeroautorizacionDocReemb;
    }
    
    
    
    
}
