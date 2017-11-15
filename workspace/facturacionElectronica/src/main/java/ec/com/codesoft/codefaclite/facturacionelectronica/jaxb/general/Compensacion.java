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
public class Compensacion {
    private String codigo;
    private String tarifa;
    private String valor;
    
    public Compensacion() {
    }

    @XmlElement(name = "codigo")
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @XmlElement(name = "tarifa")
    public String getTarifa() {
        return tarifa;
    }

    public void setTarifa(String tarifa) {
        this.tarifa = tarifa;
    }

    @XmlElement(name = "valor")
    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

}
