/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import java.sql.Date;
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

    public List<Factura> lista(Persona persona, Date fi, Date ff, String estado,Boolean consultarReferidos,Persona referido,Boolean agrupadoReferido) {
        //Factura factura;
        //factura.getReferido();
        String cliente = "", fecha = "", estadoFactura = "",filtrarReferidos="",ordenarAgrupado="";
        if (persona != null) {
            cliente = "u.cliente=?1";
        } else {
            cliente = "1=1";
        }
        if (fi == null && ff != null) {
            fecha = " AND u.fechaEmision <= ?3";
        } else if (fi != null && ff == null) {
            fecha = " AND u.fechaEmision >= ?2";
        } else if (fi == null && ff == null) {
            fecha = "";
        } else {
            fecha = " AND (u.fechaEmision BETWEEN ?2 AND ?3)";
        }
        if (estado != null) {
            estadoFactura = " AND u.estado=?4";
        }
        
        if(agrupadoReferido)
        {
            ordenarAgrupado=" u.referido ,";
        }
        
        if(consultarReferidos)
        {
            filtrarReferidos=" AND u.referido IS NOT NULL ";
            if(referido!=null)
            {            
                filtrarReferidos+=" AND u.referido=?5 ";
            }
        }

        try {
            String queryString = "SELECT u FROM Factura u WHERE " + cliente + fecha + estadoFactura +filtrarReferidos+" ORDER BY"+ ordenarAgrupado+" u.secuencial+0 asc";
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
            
            if (consultarReferidos) 
            {
                if (referido != null) 
                {
                    query.setParameter(5, referido);
                }
            }
            
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Factura> getFacturaEnable() {
        try {
            
            String queryString = "SELECT u FROM Factura u WHERE u.estado<>?1 AND u.estadoNotaCredito<>?2 AND u.estado<>?3";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter(1, ComprobanteEntity.ComprobanteEnumEstado.ELIMINADO.getEstado());
            query.setParameter(2, Factura.EstadoNotaCreditoEnum.ANULADO_PARCIAL.getEstado());
            query.setParameter(3, ComprobanteEntity.ComprobanteEnumEstado.SIN_AUTORIZAR.getEstado());
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
    
      public Long getSecuencialProforma() {
        try {

            String queryString="SELECT MAX(CAST (F.SECUENCIAL AS BIGINT)) FROM FACTURA F WHERE  F.CODIGO_DOCUMENTO=?1"; //TODO: Por el momento dejo una consulta nativa porque tengo un problema al evaluar el secuencial que en la base de datos esta como string pero esta mapeado como entero y al hacer casting en jpql el compilador se confunde
            //String queryString = "SELECT max( CAST(u.secuencial CHAR(64))  ) FROM Factura u WHERE  u.codigoDocumento=?1";
            Query query = getEntityManager().createNativeQuery(queryString);
            query.setParameter(1, DocumentoEnum.PROFORMA.getCodigo());

            return (Long) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    //public Long obtenerSecuencialPresupuestos
}
