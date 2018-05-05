/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.RetencionFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Retencion;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.RetencionDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RetencionServiceIf;
import java.rmi.RemoteException;
import javax.persistence.EntityTransaction;

/**
 *
 * @author Carlos
 */
public class RetencionService extends ServiceAbstract<Retencion,RetencionFacade> implements RetencionServiceIf{

    public RetencionService() throws RemoteException {
        super(RetencionFacade.class);
    }

    
    public Retencion grabar(Retencion entity) throws ServicioCodefacException, RemoteException {
        EntityTransaction transaction=getTransaccion();
        transaction.begin();
        
        for (RetencionDetalle retencionDetalle : entity.getDetalles()) {
            entityManager.persist(retencionDetalle);
        }
        
        entityManager.persist(entity);        
        
        transaction.commit();
        return entity;
    }
    
    
    
}
