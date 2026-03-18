/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade.pos;

import ec.com.codesoft.codefaclite.servidor.facade.AbstractFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.Caja;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.CajaSession;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.IngresoCaja;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CajaEnum;
import jakarta.persistence.EntityManager;
import java.util.List;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

/**
 *
 * @author Robert
 */
public class IngresoCajaFacade extends AbstractFacade<IngresoCaja>
{

    public IngresoCajaFacade() {
        super(IngresoCaja.class);
    }
    
    public List<IngresoCaja> consultarPorCajaSession(CajaSession cajaSession,EntityManager em)
    {
        //IngresoCaja i;
        //i.getCajaSession().
        String queryString=" SELECT i FROM IngresoCaja i WHERE i.cajaSessionId=?1 ";
        Query query = em.createQuery(queryString);
        query.setParameter(1, cajaSession.getId());
        List resultadoList = query.getResultList();
        return resultadoList;
    }
    
    public Long consultarPorCajaSessionCount(CajaSession cajaSession, EntityManager em) {
        //String jpql = "SELECT COUNT(i) FROM IngresoCaja i WHERE i.cajaSession.id = :id";
        String jpql = "SELECT COUNT(i) FROM IngresoCaja i WHERE i.cajaSessionId = :id";

        TypedQuery<Long> query = em.createQuery(jpql, Long.class);
        query.setParameter("id", cajaSession.getId());

        return query.getSingleResult();
    }
    
}
