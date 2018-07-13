/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajo;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public class OrdenTrabajoFacade extends AbstractFacade<OrdenTrabajo>
{
    
    public OrdenTrabajoFacade()
    {
        super(OrdenTrabajo.class);
    }
    
    public List<OrdenTrabajo> consultaReporteFacade(Date fechaInicial, Date fechaFinal)
    {
        OrdenTrabajo ot;
       // ot.getFechaIngreso();        
        String queryStr="SELECT u FROM OrdenTrabajo u WHERE 1=1 ";
        
        if (fechaInicial != null) 
        {
            queryStr = queryStr + " AND u.fechaIngreso>=?1 ";
        }

        if (fechaFinal != null) 
        {
            queryStr = queryStr + " AND u.fechaIngreso<=?2 ";
        }        
        
        
        Query query = getEntityManager().createQuery(queryStr);
        
        
        if(fechaInicial!=null)
            query.setParameter(1,fechaInicial);
        
        if(fechaFinal!=null)
            query.setParameter(2,fechaFinal);
        
        return query.getResultList();
    }
    
}
