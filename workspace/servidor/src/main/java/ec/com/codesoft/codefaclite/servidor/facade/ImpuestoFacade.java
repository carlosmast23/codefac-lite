/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade;

import ec.com.codesoft.codefaclite.servidor.entity.Impuesto;
import ec.com.codesoft.codefaclite.servidor.entity.ImpuestoDetalle;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    
    public Impuesto getByImpuestoVigente(String nombre) throws ParseException
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fechaControl = "2017-06-01";
        Date fecha = sdf.parse(fechaControl);
        List<ImpuestoDetalle> impuestoDetallesAEliminar = new ArrayList<>();
        
        String queryString = "SELECT i FROM Impuesto i WHERE i.nombre=?1";
        Query query = getEntityManager().createQuery(queryString);
        query.setParameter(1,nombre); 
        Impuesto impuestos = (Impuesto) query.getSingleResult();
            for(ImpuestoDetalle id : impuestos.getDetalleImpuestos())
            {
                if(id.getFechaFin() == null)
                {
                    if(!(id.getFechaInicio().before(fecha) || id.getFechaInicio().equals(fecha)))
                    {
                       impuestoDetallesAEliminar.add(id);
                    }
                }
                else
                {
                    impuestoDetallesAEliminar.add(id);
                }
            }
            
            for(ImpuestoDetalle id : impuestoDetallesAEliminar)
            {
                impuestos.getDetalleImpuestos().remove(id);
            }
            
        return (Impuesto) impuestos;
    }

}
