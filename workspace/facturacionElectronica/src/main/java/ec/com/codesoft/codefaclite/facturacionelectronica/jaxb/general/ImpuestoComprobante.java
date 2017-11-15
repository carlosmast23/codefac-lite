/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general;

import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author Carlos
 */
public class ImpuestoComprobante {
    private String codigo;
    private String codigoPorcentaje;
    private String tarifa;
    private String baseImponible;
    private String valor;

    public ImpuestoComprobante() {
    }

    @XmlElement(name="codigo")
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @XmlElement(name="codigoPorcentaje")
    public String getCodigoPorcentaje() {
        return codigoPorcentaje;
    }

    public void setCodigoPorcentaje(String codigoPorcentaje) {
        this.codigoPorcentaje = codigoPorcentaje;
    }

    @XmlElement(name="tarifa")
    public String getTarifa() {
        return tarifa;
    }

    public void setTarifa(String tarifa) {
        this.tarifa = tarifa;
    }

    @XmlElement(name="baseImponible")
    public String getBaseImponible() {
        return baseImponible;
    }

    public void setBaseImponible(String baseImponible) {
        this.baseImponible = baseImponible;
    }

    @XmlElement(name="valor")
    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
    

}
