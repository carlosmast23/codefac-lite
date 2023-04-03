/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.factura;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Carlos
 */
@XmlType(propOrder = {"formaPago","total","plazo","unidadTiempo"})
public class FormaPagoComprobante implements Serializable{    
    
    
    private String nombreTmp;
    
    private String formaPago;
    
    private BigDecimal total;
    
    private BigDecimal plazo;
    
    private String unidadTiempo;

    public FormaPagoComprobante() {
        System.out.println("creando Forma Pago");
    }

    @XmlElement(name = "formaPago")
    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    @XmlElement(name = "total")
    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
    
    @XmlElement(name = "plazo")
    public BigDecimal getPlazo() {
        return plazo;
    }

    public void setPlazo(BigDecimal plazo) {
        this.plazo = plazo;
    }

    @XmlElement(name = "unidadTiempo")
    public String getUnidadTiempo() {
        return unidadTiempo;
    }

    public void setUnidadTiempo(String unidadTiempo) {
        this.unidadTiempo = unidadTiempo;
    }

    @XmlTransient
    public String getNombreTmp() {
        return nombreTmp;
    }

    public void setNombreTmp(String nombreTmp) {
        this.nombreTmp = nombreTmp;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.formaPago);
        hash = 23 * hash + Objects.hashCode(this.total);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FormaPagoComprobante other = (FormaPagoComprobante) obj;
        if (!Objects.equals(this.formaPago, other.formaPago)) {
            return false;
        }
        if (!Objects.equals(this.total, other.total)) {
            return false;
        }
        return true;
    }
    
}
