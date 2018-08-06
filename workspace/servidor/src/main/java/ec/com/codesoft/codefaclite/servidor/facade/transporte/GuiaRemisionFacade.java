/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade.transporte;

import ec.com.codesoft.codefaclite.servidor.facade.AbstractFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.GuiaRemision;
import java.sql.Date;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Carlos
 */
public class GuiaRemisionFacade extends AbstractFacade<GuiaRemision>{
    
    public GuiaRemisionFacade() {
        super(GuiaRemision.class);
    }
    
    public List<GuiaRemision> obtenerConsultaFacade(Date fechaInicial,Date fechaFinal)
    {
        GuiaRemision guia;
        //guia.getFechaIniciaTransporte();
        String queryString="select u from GuiaRemision u ";
        Query query = getEntityManager().createQuery(queryString);
        return query.getResultList();        
    }
    
}
