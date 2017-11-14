/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Carlos
 */
@XmlType
public class TotalImpuesto {
    
    private String codigo;
    private String codigoPorcentaje;
    private String baseImponible;
    private String valor;

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
    public String getBaseImponible() {
        return baseImponible;
    }

    @XmlElement(name = "valor")
    public String getValor() {
        return valor;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setCodigoPorcentaje(String codigoPorcentaje) {
        this.codigoPorcentaje = codigoPorcentaje;
    }

    public void setBaseImponible(String baseImponible) {
        this.baseImponible = baseImponible;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
    
    

    
}
