/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade;

import ec.com.codesoft.codefaclite.servidor.service.ProductoService;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ProductoProveedor;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.util.List;
import jakarta.persistence.Query;

/**
 *
 * @author Carlos
 */
public class ProductoProveedorFacade extends AbstractFacade<ProductoProveedor>{

    public ProductoProveedorFacade() {
        super(ProductoProveedor.class);
    }
    
    
    public List<ProductoProveedor> buscarProductoProveedorActivoFacade(Producto producto, Persona proveedor) throws ServicioCodefacException, java.rmi.RemoteException
    {
        //ProductoProveedor pp;
        //pp.getProducto();
        //pp.getProveedor();
        String queryStr=" SELECT pp FROM ProductoProveedor pp WHERE pp.producto=?1 AND pp.proveedor=?2 ";        
        Query query=getEntityManager().createQuery(queryStr);
        query.setParameter(1, producto);
        query.setParameter(2, proveedor);
        
        List<ProductoProveedor> productoProveedorList=query.getResultList();
        return productoProveedorList;
    }
    
    
}
