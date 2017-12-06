/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.jaxb;

import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general.InformacionTributaria;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general.InformacionAdicional;
import java.sql.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Carlos
 */
@XmlTransient
public abstract class ComprobanteElectronico {    
    public static final String FACTURA="factura";
    public static final String NOTA_CREDITO="notaCredito";
    
    
    public static final String MODO_FACTURACION_NORMAL="1";
    protected InformacionTributaria informacionTributaria;
    protected List<InformacionAdicional> informacionAdicional;
    public abstract String getTipoDocumento();
    public abstract String getFechaEmision();
    public abstract String getIdentificacion();

    public ComprobanteElectronico() {
    }
    
    /**
     * Compobante electronico
     * @return 
     */
    @XmlAttribute(name = "id")
    public String getIdAttribute()
    {
        return "comprobante";
    }
    
    @XmlAttribute(name = "version")
    public String getVersionAttribute()
    {
        return "1.0.0";
    }
    
    @XmlAttribute(name = "xmlns:ds")
    public String getXmlnsdsAttribute()
    {
        return "http://www.w3.org/2000/09/xmldsig#";
    }
    
    @XmlAttribute(name = "xmlns:xsi")
    public String getXmlnsxsiAttribute() {
        return "http://www.w3.org/2001/XMLSchema-instance";
    }



    @XmlElement(name="infoTributaria")
    public InformacionTributaria getInformacionTributaria() {
        return informacionTributaria;
    }

    public void setInformacionTributaria(InformacionTributaria informacionTributaria) {
        this.informacionTributaria = informacionTributaria;
    }

    @XmlElementWrapper(name = "infoAdicional")
    @XmlElement(name = "campoAdicional")
    public List<InformacionAdicional> getInformacionAdicional() {
        return informacionAdicional;
    }

    public void setInformacionAdicional(List<InformacionAdicional> informacionAdicional) {
        this.informacionAdicional = informacionAdicional;
    }
    
   
    
}
