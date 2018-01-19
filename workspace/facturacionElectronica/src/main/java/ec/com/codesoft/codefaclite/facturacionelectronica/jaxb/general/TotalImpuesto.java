/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Carlos
 */
@XmlType(propOrder = {"codigo","codigoPorcentaje","descuentoAdicional","baseImponible","valor"})
public class TotalImpuesto {
    
    private String codigo;
    private String codigoPorcentaje;
    private String descuentoAdicional;
    private BigDecimal baseImponible;
    private BigDecimal valor;

    public TotalImpuesto() {
    }

    @XmlElement(name = "codigo")
    public String getCodigo() {
        return codigo;
    }

    @XmlElement(name = "codigoPorcentaje")
    public String getCodigoPorcentaje() {
        return codigoPorcentaje;
    }

    @XmlElement(name = "baseImponible")
    public BigDecimal getBaseImponible() {
        return baseImponible;
    }

    @XmlElement(name = "valor")
    public BigDecimal getValor() {
        return valor;
    }

    @XmlElement(name = "descuentoAdicional")
    public String getDescuentoAdicional() {
        return descuentoAdicional;
    }
    
    
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setCodigoPorcentaje(String codigoPorcentaje) {
        this.codigoPorcentaje = codigoPorcentaje;
    }

    public void setBaseImponible(BigDecimal baseImponible) {
        this.baseImponible = baseImponible;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public void setDescuentoAdicional(String descuentoAdicional) {
        this.descuentoAdicional = descuentoAdicional;
    }
    
    

    
}
