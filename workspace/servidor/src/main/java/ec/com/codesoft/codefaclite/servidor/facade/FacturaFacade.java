/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade;

import ec.com.codesoft.codefaclite.servidor.entity.Factura;
import ec.com.codesoft.codefaclite.servidor.entity.Usuario;
import ec.com.codesoft.codefaclite.servidor.entity.enumerados.FacturaEnumEstado;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author Carlos
 */
public class FacturaFacade extends AbstractFacade<Factura>
{
    
    public FacturaFacade() 
    {
        super(Factura.class);
    }
    
    public List<Factura> getFacturaEnable()
    {
        try
        {

            String queryString = "SELECT u FROM Factura u WHERE u.estado<>?1 AND u.estado<>?2 AND u.estado<>?3";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter(1,FacturaEnumEstado.ELIMINADO.getEstado());
            query.setParameter(2,FacturaEnumEstado.ANULADO_TOTAL.getEstado());
            query.setParameter(3,FacturaEnumEstado.SIN_AUTORIZAR.getEstado());
            return (List<Factura>) query.getResultList();
        }
        catch(NoResultException e)
        {
            return null;
        }
    }
}