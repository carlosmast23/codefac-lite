/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.retencion;

import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general.ImpuestoComprobante;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * //TODO: Ver si luego se puede unir con ImpuestoComprobante
 * @author CARLOS_CODESOFT
 */
@XmlType(propOrder = {"codigo","codigoPorcentaje","baseImponible","tarifa","valor"})
public class ImpuestoDocSustento implements Serializable {

    public ImpuestoDocSustento() 
    {
        
    }
    
    public ImpuestoDocSustento(ImpuestoComprobante impuesto) 
    {
        this.setCodigo(impuesto.getCodigo());
        this.setCodigoPorcentaje(impuesto.getCodigoPorcentaje());
        this.setTarifa(impuesto.getTarifa());
        this.setBaseImponible(impuesto.getBaseImponible());
        this.setValor(impuesto.getValor());        
    }
    
    //Este codigo esta relacionado con IMPUESTO GENERAL (IVA,ICE...)
    private String codigo;
    
    //Este codigo esta relacionado con IMPUESTO_DETALLE (IVA 0, IVA 12 , IVA 14)
    private String codigoPorcentaje;
    private BigDecimal tarifa;
    private BigDecimal baseImponible;
    private BigDecimal valor;


    @XmlElement(name="codImpuestoDocSustento")
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
    public BigDecimal getTarifa() {
        return tarifa;
    }

    public void setTarifa(BigDecimal tarifa) {
        this.tarifa = tarifa;
    }

    @XmlElement(name="baseImponible")
    public BigDecimal getBaseImponible() {
        return baseImponible;
    }

    public void setBaseImponible(BigDecimal baseImponible) {
        this.baseImponible = baseImponible;
    }

    @XmlElement(name="valorImpuesto")
    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
            
    public static List<ImpuestoDocSustento> castList(List<ImpuestoComprobante> impuestoComprobanteList)
    {
        List<ImpuestoDocSustento> resultadoList=new ArrayList<ImpuestoDocSustento>();
        for (ImpuestoComprobante impuestoComprobante : impuestoComprobanteList) 
        {
            ImpuestoDocSustento impuesto=new ImpuestoDocSustento(impuestoComprobante);
            resultadoList.add(impuesto);
        }
        return resultadoList;
    }
    
    
}
