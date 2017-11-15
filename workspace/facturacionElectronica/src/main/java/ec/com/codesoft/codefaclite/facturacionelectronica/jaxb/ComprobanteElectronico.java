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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

/**
 *
 * @author Carlos
 */
public abstract class ComprobanteElectronico {    
    public static final String FACTURA="factura";
    public static final String NOTA_CREDITO="notaCredito";
    public static final String MODO_FACTURACION_NORMAL="1";

    
    private InformacionTributaria informacionTributaria;
    private List<InformacionAdicional> informacionAdicional;
    public abstract String getTipoDocumento();
    public abstract Date getFechaEmision();
    public abstract String getIdentificacion();

    public ComprobanteElectronico() {
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
