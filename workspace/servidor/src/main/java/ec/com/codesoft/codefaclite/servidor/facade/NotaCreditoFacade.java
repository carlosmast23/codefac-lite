/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade;

import ec.com.codesoft.codefaclite.servidor.entity.NotaCredito;
import ec.com.codesoft.codefaclite.servidor.entity.Persona;
import java.sql.Date;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author PC
 */
public class NotaCreditoFacade extends AbstractFacade<NotaCredito> {

    public NotaCreditoFacade() {
        super(NotaCredito.class);
    }

    public List<NotaCredito> lista(Persona persona, Date fi, Date ff) {

        String cliente = "", fecha = "";
        if (persona != null) {
            cliente = "u.cliente=?1";
        } else {
            cliente = "1=1";
        }

        if (fi == null && ff != null) {
            fecha = " AND u.fechaNotaCredito <= ?3";
        } else if (fi != null && ff == null) {
            fecha = " AND u.fechaNotaCredito <= ?2";
        } else if (fi == null && ff == null) {
            fecha = "";
        } else {
            fecha = " AND (u.fechaNotaCredito BETWEEN ?2 AND ?3)";
        }

        try {
            String queryString = "SELECT u FROM NotaCredito u WHERE " + cliente + fecha;
            Query query = getEntityManager().createQuery(queryString);
            if (persona != null) {
                query.setParameter(1, persona);
            }
            if (fi != null) {
                query.setParameter(2, fi);
            }
            if (ff != null) {
                query.setParameter(3, ff);
            }

            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

}
