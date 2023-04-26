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
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Robert
 */
public class IngresoCajaFacade extends AbstractFacade<IngresoCaja>
{

    public IngresoCajaFacade() {
        super(IngresoCaja.class);
    }
    
    public List<IngresoCaja> consultarPorCajaSession(CajaSession cajaSession)
    {
        //IngresoCaja i;
        //i.getCajaSession().
        String queryString=" SELECT i FROM IngresoCaja i WHERE i.cajaSession=?1 ";
        Query query = getEntityManager().createQuery(queryString);
        query.setParameter(1, cajaSession);
        List resultadoList = query.getResultList();
        return resultadoList;
    }
    
}
