/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.factura;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author CARLOS_CODESOFT
 */
@XmlType(propOrder = {
    "codigo",
    "codigoPorcentaje",
    "tarifa",
    "baseImponibleReembolso",
    "impuestoReembolso"
})
public class DetalleImpuestoReembolsoComprobante implements Serializable{
    
    private String codigo;
    private String codigoPorcentaje;
    private String tarifa;
    private String baseImponibleReembolso;
    private String impuestoReembolso;

    public DetalleImpuestoReembolsoComprobante() {
    }

    @XmlElement(name = "codigo")
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @XmlElement(name = "codigoPorcentaje")
    public String getCodigoPorcentaje() {
        return codigoPorcentaje;
    }

    public void setCodigoPorcentaje(String codigoPorcentaje) {
        this.codigoPorcentaje = codigoPorcentaje;
    }

    @XmlElement(name = "tarifa")
    public String getTarifa() {
        return tarifa;
    }

    public void setTarifa(String tarifa) {
        this.tarifa = tarifa;
    }

    @XmlElement(name = "baseImponibleReembolso")
    public String getBaseImponibleReembolso() {
        return baseImponibleReembolso;
    }

    public void setBaseImponibleReembolso(String baseImponibleReembolso) {
        this.baseImponibleReembolso = baseImponibleReembolso;
    }

    @XmlElement(name = "impuestoReembolso")
    public String getImpuestoReembolso() {
        return impuestoReembolso;
    }

    public void setImpuestoReembolso(String impuestoReembolso) {
        this.impuestoReembolso = impuestoReembolso;
    }
    
    
    
}
