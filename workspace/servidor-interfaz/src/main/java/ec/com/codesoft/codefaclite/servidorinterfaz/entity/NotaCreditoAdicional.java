/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "NOTA_CREDITO_ADICIONAL")
public class NotaCreditoAdicional extends ComprobanteAdicional implements Serializable{
    
    
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
