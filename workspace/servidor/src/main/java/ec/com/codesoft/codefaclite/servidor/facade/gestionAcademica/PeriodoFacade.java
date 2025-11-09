/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade.gestionAcademica;

import ec.com.codesoft.codefaclite.servidor.facade.AbstractFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import jakarta.persistence.EntityManager;
import java.util.List;
import jakarta.persistence.Query;

/**
 *
 * @author Carlos
 */
public class PeriodoFacade extends AbstractFacade<Periodo>{

    public PeriodoFacade() {
        super(Periodo.class);
    }
    
    public List<Periodo> getPeriodosSinEliminar(EntityManager em) {
        String queryString = "SELECT u FROM Periodo u WHERE u.estado<>?1 ";
        Query query = em.createQuery(queryString);
        query.setParameter(1, GeneralEnumEstado.ELIMINADO.getEstado());

        return (List<Periodo>) query.getResultList();
    }
}
