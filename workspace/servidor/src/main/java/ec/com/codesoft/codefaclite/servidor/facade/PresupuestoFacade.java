/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Presupuesto;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public class PresupuestoFacade extends AbstractFacade<Presupuesto>
{
    public PresupuestoFacade()
    {
        super(Presupuesto.class);
    }
    
    public List<OrdenTrabajoDetalle> listarOrdenTrabajo()
    {
        //String queryString = "SELECT otd FROM Presupuesto p INNER JOIN p.ordenTrabajoDetalle otd";
        String queryString = "SELECT otd FROM Presupuesto p INNER JOIN p.ordenTrabajoDetalle otd where otd.id = ?1";
        //String queryString = "Select p from OrdenTrabajoDetalle otd INNER JOIN otd.presupuesto where otd.id=?1";
        //String queryString = "SELECT p FROM PerfilUsuario pu INNER JOIN pu.perfil p WHERE pu.usuario=?1";
        Query query = getEntityManager().createQuery(queryString);
        query.setParameter(1,8);
        return query.getResultList();
    }
}
