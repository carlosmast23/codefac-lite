/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.retencion;

import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general.ImpuestoComprobante;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author DellWin10
 */
@XmlType(propOrder = {"codImpuestoDocSustento","codigoPorcentaje","tarifa","baseImponible","valorImpuesto"})
public class ImpuestoRetencion extends ImpuestoComprobante implements Serializable{

    @XmlElement(name="valorImpuesto")
    @Override
    public BigDecimal getValor() {
        return super.getValor(); //To change body of generated methods, choose Tools | Templates.
    }

    @XmlElement(name="codImpuestoDocSustento")
    @Override
    public String getCodigo() {
        return super.getCodigo(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
