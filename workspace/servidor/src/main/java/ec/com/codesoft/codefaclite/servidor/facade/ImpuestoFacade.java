/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade;

import ec.com.codesoft.codefaclite.servidor.entity.Impuesto;
import javax.persistence.Query;

/**
 *
 * @author PC
 */
public class ImpuestoFacade extends AbstractFacade<Impuesto> {

    public ImpuestoFacade() {
        super(Impuesto.class);
    }

    public Impuesto getByName(String nombre) {

        String queryString = "SELECT i FROM Impuesto i WHERE i.nombre=?1";
        Query query = getEntityManager().createQuery(queryString);
        query.setParameter(1,nombre);
        return (Impuesto) query.getSingleResult();
    }

}
