/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade.pos;

import ec.com.codesoft.codefaclite.servidor.facade.AbstractFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.Caja;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.CajaSession;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CajaSessionEnum;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author Robert
 */
public class CajaSesionFacade extends AbstractFacade<CajaSession> {
    
    public CajaSesionFacade() {
        super(CajaSession.class);
    }
    
    public CajaSession obtenerUltimaCajaSession(Caja caja) 
    {
        try
        {
            String stringQuery = "Select cs from CajaSession cs where cs.caja = ?1 and cs.estadoCierreCaja = ?2 order by cs.fechaHoraCierre desc";

            Query query = getEntityManager().createQuery(stringQuery);
            query.setParameter(1, caja);
            query.setParameter(2, CajaSessionEnum.FINALIZADO.getEstado());
            
            List<CajaSession> cajasSession = query.getResultList();
            
            if(cajasSession.size() > 0)
            {
                return cajasSession.get(0);
            }
        } 
        catch (NoResultException e) 
        {
            return null;
        }
        
        return null;
    }
    
}
