/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Retencion;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.RetencionDetalle;
import java.sql.Date;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author Carlos
 */
public class RetencionFacade extends AbstractFacade<Retencion> {

    public RetencionFacade() {
        super(Retencion.class);
    }

    public List<RetencionDetalle> lista(Persona persona, Date fi, Date ff, String estado) {
        String proveedor = "", fecha = "", estadoFactura = "";
        if (persona != null) {
            proveedor = "u.cliente=?1";
        } else {
            proveedor = "1=1";
        }
        if (fi == null && ff != null) {
            fecha = " AND u.fechaFactura <= ?3";
        } else if (fi != null && ff == null) {
            fecha = " AND u.fechaFactura <= ?2";
        } else if (fi == null && ff == null) {
            fecha = "";
        } else {
            fecha = " AND (u.fechaFactura BETWEEN ?2 AND ?3)";
        }
     
        try {//INNER JOIN Retencion r ON d.retencion=r.id  
            String queryString = "SELECT u FROM RetencionDetalle u ";// + proveedor + fecha ;
            Query query = getEntityManager().createQuery(queryString);
            System.err.println("QUERY--->"+query.toString());
            if (persona != null) {
                query.setParameter(1, persona);
            }
            if (fi != null) {
                query.setParameter(2, fi);
            }
            if (ff != null) {
                query.setParameter(3, ff);
            }
            if (estado != null) {
                query.setParameter(4, estado);
            }
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

}
