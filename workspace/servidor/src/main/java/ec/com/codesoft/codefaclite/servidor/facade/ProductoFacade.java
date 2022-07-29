/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.OperadorNegocioEnum;
import java.rmi.RemoteException;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author PC
 */
public class ProductoFacade extends AbstractFacade<Producto>
{ 
    public ProductoFacade() 
    {
        super(Producto.class);
    }
    
    public List<Producto> reporteProductoFacade(Producto producto) throws RemoteException,ServicioCodefacException
    {
        //producto.getIdProducto()
        
        String whereProducto="";
        if(producto!=null)
        {
            whereProducto=" AND p.idProducto=?2 ";
        }
        
        String queryString = "SELECT p FROM Producto p WHERE p.estado=?1 "+whereProducto ;
        
        Query query = getEntityManager().createQuery(queryString);
        
        query.setParameter(1,GeneralEnumEstado.ACTIVO.getEstado());
        
        if(producto!=null)
        {
            query.setParameter(2,producto.getIdProducto());
        }
        
        
        return query.getResultList();
    }
      
}
