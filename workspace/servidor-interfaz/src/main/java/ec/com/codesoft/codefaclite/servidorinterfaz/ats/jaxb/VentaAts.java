/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.ats.jaxb;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Carlos
 */
@XmlRootElement(name = "ventas")
@XmlType(propOrder = {"tpIdCliente","idCliente","numeroComprobantes","baseImponible","montoIva","formasDePago"})
public class VentaAts implements Serializable{
    private String tpIdCliente;
    private String idCliente;
    private int numeroComprobantes;
    private BigDecimal baseImponible;
    private BigDecimal montoIva;
    
    private List<FormaDePagoAts> formasDePago;

    public VentaAts() {
    }

    public List<FormaDePagoAts> getFormasDePago() {
        return formasDePago;
    }
    

    @XmlAttribute(name = "tpIdCliente")
    public String getTpIdCliente() {
        return tpIdCliente;
    }

    @XmlAttribute(name = "idCliente")
    public String getIdCliente() {
        return idCliente;
    }

    @XmlAttribute(name = "numeroComprobantes")
    public int getNumeroComprobantes() {
        return numeroComprobantes;
    }

    @XmlAttribute(name = "baseImponible")
    public BigDecimal getBaseImponible() {
        return baseImponible;
    }

    @XmlAttribute(name = "montoIva")
    public BigDecimal getMontoIva() {
        return montoIva;
    }

    public void setTpIdCliente(String tpIdCliente) {
        this.tpIdCliente = tpIdCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public void setNumeroComprobantes(int numeroComprobantes) {
        this.numeroComprobantes = numeroComprobantes;
    }

    public void setBaseImponible(BigDecimal baseImponible) {
        this.baseImponible = baseImponible;
    }

    public void setMontoIva(BigDecimal montoIva) {
        this.montoIva = montoIva;
    }

    public void setFormasDePago(List<FormaDePagoAts> formasDePago) {
        this.formasDePago = formasDePago;
    }

    
    
}
