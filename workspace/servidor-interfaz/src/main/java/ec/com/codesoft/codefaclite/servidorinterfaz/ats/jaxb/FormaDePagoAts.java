/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.ats.jaxb;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author Carlos
 */
public class FormaDePagoAts implements Serializable {
    private String formaPago;

    public FormaDePagoAts() {
    }
    
    @XmlElement(name = "TipoIDInformante")
    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }
    
    
}
