/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "FACTURA_ADICIONAL")
public class FacturaAdicional extends ComprobanteAdicional implements Serializable,Cloneable {
    
    public static final String NUMERO_ORDEN ="numero orden";
    /**
     * Nombre de los campos para grabar correos
     */
    //public static final String NOMBRE_CORREO="correo";
    @JoinColumn(name = "FACTURA_ID")
    @ManyToOne
    private Factura factura;

    public FacturaAdicional() {
    }
    
    public FacturaAdicional(ComprobanteAdicional comprobanteAdicional)
    {
        super(comprobanteAdicional.getCampo(),comprobanteAdicional.getValor(),comprobanteAdicional.getTipoEnum());
    }
    
    public FacturaAdicional(String campo, String valor, Tipo tipo) {
        super(campo, valor, tipo);
        //this.factura = factura;
    }

    public FacturaAdicional(Factura factura, String campo, String valor, Tipo tipo) {
        super(campo, valor, tipo);
        this.factura = factura;
    }

    public FacturaAdicional(String correo, Tipo tipoCorreo, CampoDefectoEnum campoDefecto) {
        super(correo, tipoCorreo, campoDefecto);
        //this.factura = factura;
    }
    
    

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }

    
        
}
