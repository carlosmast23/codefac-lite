/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte;

import java.io.Serializable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author Carlos
 */
public class GuiaRemisionAdicional implements Serializable{
    
    @JoinColumn(name = "GUIA_REMISION_ID")
    @ManyToOne
    private GuiaRemision guiaRemision;

    public GuiaRemision getGuiaRemision() {
        return guiaRemision;
    }

    public void setGuiaRemision(GuiaRemision guiaRemision) {
        this.guiaRemision = guiaRemision;
    }
    
    
    
}
