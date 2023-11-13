/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteAdicional;
import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "GUIA_REMISION_ADICIONAL")
public class GuiaRemisionAdicional extends ComprobanteAdicional implements Serializable{
    
    @JoinColumn(name = "NOTA_CREDITO_ID") //Todo: Esta valor esta pendiente de modificar el nombre
    @ManyToOne
    private GuiaRemision guiaRemision;

    public GuiaRemision getGuiaRemision() {
        return guiaRemision;
    }

    public void setGuiaRemision(GuiaRemision guiaRemision) {
        this.guiaRemision = guiaRemision;
    }
    
    
    
}
