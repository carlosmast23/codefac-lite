/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import java.sql.Date;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.FacturaEnumEstado;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author Carlos
 */
public class FacturaFacade extends AbstractFacade<Factura> {

    //aca consuultas con la BBDD
//compara objetos no strings
    public FacturaFacade() {
        super(Factura.class);
    }

    public List<Factura> lista(Persona persona, Date fi, Date ff, String estado) {
        String cliente = "", fecha = "", estadoFactura = "";
        if (persona != null) {
            cliente = "u.cliente=?1";
        } else {
            cliente = "1=1";
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
        if (estado != null) {
            estadoFactura = " AND u.estado=?4";
        }

        try {
            String queryString = "SELECT u FROM Factura u WHERE " + cliente + fecha + estadoFactura;
            Query query = getEntityManager().createQuery(queryString);
            //System.err.println("QUERY--->"+query.toString());
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

    public List<Factura> getFacturaEnable() {
        try {
            String queryString = "SELECT u FROM Factura u WHERE u.estado<>?1 AND u.estado<>?2 AND u.estado<>?3";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter(1, FacturaEnumEstado.ELIMINADO.getEstado());
            query.setParameter(2, FacturaEnumEstado.ANULADO_TOTAL.getEstado());
            query.setParameter(3, FacturaEnumEstado.SIN_AUTORIZAR.getEstado());
            return (List<Factura>) query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Factura> queryDialog(String param, int limiteMinimo, int limiteMaximo) {
        String queryString = "SELECT u FROM Factura u WHERE u.estado<>?1 AND u.estado<>?2 AND u.estado<>?3 ";
        queryString += "AND ( f.cliente.razonSocial like ?1 )";

        Query query = getEntityManager().createQuery(queryString);
        query.setParameter(1, param);
        query.setMaxResults(limiteMaximo);
        query.setFirstResult(limiteMinimo);
        return query.getResultList();

    }
}
