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
@Table(name = "NOTA_CREDITO_ADICIONAL")
public class NotaCreditoAdicional extends ComprobanteAdicional implements Serializable{

    public NotaCreditoAdicional() {
    }
    
    public NotaCreditoAdicional(ComprobanteAdicional comprobanteAdicional)
    {
        super(comprobanteAdicional.getCampo(),comprobanteAdicional.getValor(),comprobanteAdicional.getTipoEnum());
    }
    
    public NotaCreditoAdicional(String correo, Tipo tipoCorreo, CampoDefectoEnum campoDefecto) {
        super(correo, tipoCorreo, campoDefecto);
        //this.factura = factura;
    }
    
    @JoinColumn(name = "NOTA_CREDITO_ID")
    @ManyToOne
    private NotaCredito notaCredito;


    public NotaCredito getNotaCredito() {
        return notaCredito;
    }

    public void setNotaCredito(NotaCredito notaCredito) {
        this.notaCredito = notaCredito;
    }
    
}
