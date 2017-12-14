/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade;

import ec.com.codesoft.codefaclite.servidor.entity.Factura;
import java.sql.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    public List<Factura> lista(Integer cli_id, String estado, Date fi, Date ff) {
        //System.out.println("CLIENTE "+cli_id);
       // System.out.println("ESTADO "+estado);
        String cliente = "", tipo = "";
        if (cli_id != null) {
            cliente = "u.cliente.idCliente=?1";
        } else {
            cliente = "1=1";
        }
        if (estado == "T") {
            tipo = "1=1";
        } else {
            tipo = "u.estado=?2";
        }

        try {
            String queryString = "SELECT u FROM Factura u WHERE " + cliente + " AND (u.fechaFactura BETWEEN ?3 AND ?4) AND " + tipo;
            Query query = getEntityManager().createQuery(queryString);
            if (cli_id != 0) {
                query.setParameter(1, cli_id);
            }
            if (estado != "T") {
                query.setParameter(2, estado);
            }
            query.setParameter(3, fi);
            query.setParameter(4, ff);

            Logger.getLogger(getClass().getName()).log(Level.INFO, "Mensaje informativo..." + queryString);

            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

}
