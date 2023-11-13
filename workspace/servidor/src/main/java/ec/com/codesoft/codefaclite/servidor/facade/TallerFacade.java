/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Taller;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.TallerTarea;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Zona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import java.rmi.RemoteException;
import java.util.List;
import jakarta.persistence.Query;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class TallerFacade extends AbstractFacade<Taller> {

    public TallerFacade() {
        super(Taller.class);
    }
    
    public List<TallerTarea> obtenerTareasPorTallerFacade(Taller taller)
    {
        //TallerTarea t;
        //t.getTaller();
        //t.getTareaMantenimiento();
        
        String queryString = "SELECT u FROM TallerTarea u WHERE u.taller=?1";
        
        Query query = getEntityManager().createQuery(queryString);
        query.setParameter(1, taller);
        return query.getResultList();
    }
    
}
