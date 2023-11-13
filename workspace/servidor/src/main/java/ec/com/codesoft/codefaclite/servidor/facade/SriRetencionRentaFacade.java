/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencionRenta;
import java.rmi.RemoteException;
import java.util.List;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

/**
 *
 * @author Carlos
 */
public class SriRetencionRentaFacade extends AbstractFacade<SriRetencionRenta>{

    public SriRetencionRentaFacade() {
        super(SriRetencionRenta.class);
    }
    
    public List<SriRetencionRenta> obtenerTodosOrdenadoPorCodigoFacade() throws RemoteException
    {
        SriRetencionRenta srr;
        //srr.getFechaFin()
        //srr.getCodigo().get
        try {
            
            String queryString = "SELECT u FROM SriRetencionRenta u WHERE ( u.fechaFin >= CURRENT_DATE OR u.fechaFin is NULL ) order by u.codigo desc";
            Query query = getEntityManager().createQuery(queryString);
            return (List<SriRetencionRenta>) query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
    
}
