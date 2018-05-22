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
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencionIva;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencionRenta;
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

    public List<RetencionDetalle> lista(Persona persona, Date fi, Date ff, SriRetencionIva iva, SriRetencionRenta renta, String tipo) {
        String proveedor = "", fecha = "", retiva = "", retrenta = "", tipor = "";
        if (persona != null) {
            proveedor = "r.proveedor=?1";
        } else {
            proveedor = "1=1";
        }
        if (fi == null && ff != null) {
            fecha = " AND r.fechaEmision <= ?3";
        } else if (fi != null && ff == null) {
            fecha = " AND r.fechaEmision <= ?2";
        } else if (fi == null && ff == null) {
            fecha = "";
        } else {
            fecha = " AND (r.fechaEmision BETWEEN ?2 AND ?3)";
        }

        if (iva != null) {
            retiva = "e.sriRetencionIva=?4";
        } else {
            retiva = "1=1";
        }

        if (renta != null) {
            retrenta = "e.sriRetencionRenta=?5";
        } else {
            retrenta = "1=1";
        }
        if (tipo != null) {
            if (tipo.compareTo("IVA")==0) {
                tipo = "2";
            }
            if (tipo.compareTo("RENTA")==0) {
                tipo = "1";
            }
            tipor = "d.codigoSri=?6";
        } else {
            tipor = "1=1";
        }

        try {//INNER JOIN Retencion r ON d.retencion=r.id  
            String queryString = "SELECT d FROM RetencionDetalle d WHERE " + tipor + " AND d.retencion.id IN(SELECT r.id FROM Retencion r WHERE " + proveedor + fecha + " AND r.compra.id IN(SELECT e.compra.id FROM CompraDetalle e WHERE " + retiva + " AND " + retrenta + ") )";
            Query query = getEntityManager().createQuery(queryString);
            //System.err.println("QUERY--->" + query.toString());
            if (persona != null) {
                query.setParameter(1, persona);
            }
            if (fi != null) {
                query.setParameter(2, fi);
            }
            if (ff != null) {
                query.setParameter(3, ff);
            }
            if (iva != null) {
                query.setParameter(4, iva);
            }
            if (renta != null) {
                query.setParameter(5, renta);
            }
            if (tipo != null) {
                query.setParameter(6, tipo);
            }
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Object[]> retencionesCodigo(Persona persona, Date fi, Date ff, SriRetencionIva iva, SriRetencionRenta renta, String tipo) {
        String proveedor = "", fecha = "", retiva = "", retrenta = "", tipoc = "";
        if (persona != null) {
            proveedor = "r.proveedor=?1";
        } else {
            proveedor = "1=1";
        }
        if (fi == null && ff != null) {
            fecha = " AND r.fechaEmision <= ?3";
        } else if (fi != null && ff == null) {
            fecha = " AND r.fechaEmision <= ?2";
        } else if (fi == null && ff == null) {
            fecha = "";
        } else {
            fecha = " AND (r.fechaEmision BETWEEN ?2 AND ?3)";
        }

        if (iva != null) {
            retiva = "e.sriRetencionIva=?4";
        } else {
            retiva = "1=1";
        }

        if (renta != null) {
            retrenta = "e.sriRetencionRenta=?5";
        } else {
            retrenta = "1=1";
        }
        if (tipo != null) {
            tipoc = "d.codigoSri=?6";
        } else {
            tipoc = "1=1";
        }

        try {
            String queryString = "SELECT d.codigoRetencionSri,SUM(d.valorRetenido) FROM RetencionDetalle d WHERE " + tipoc + " AND d.retencion.id IN(SELECT r.id FROM Retencion r WHERE " + proveedor + fecha + " AND r.compra.id IN(SELECT e.compra.id FROM CompraDetalle e WHERE " + retiva + " AND " + retrenta + ")) GROUP BY d.codigoRetencionSri";
            //String queryString = "SELECT d.codigoSri,SUM(d.valorRetenido) FROM RetencionDetalle d GROUP BY d.codigoSri";
            Query query = getEntityManager().createQuery(queryString);
            System.err.println("QUERY 2 --->" + query.toString());
            if (persona != null) {
                query.setParameter(1, persona);
            }
            if (fi != null) {
                query.setParameter(2, fi);
            }
            if (ff != null) {
                query.setParameter(3, ff);
            }
            if (iva != null) {
                query.setParameter(4, iva);
            }
            if (renta != null) {
                query.setParameter(5, renta);
            }
            if (tipo != null) {
                query.setParameter(6, tipo);
            }
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

}
