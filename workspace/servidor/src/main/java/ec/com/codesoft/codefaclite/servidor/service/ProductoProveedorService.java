/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ProductoProveedor;
import ec.com.codesoft.codefaclite.servidor.facade.ProductoProveedorFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Compra;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ProductoProveedorServiceIf;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Carlos
 */
public class ProductoProveedorService extends ServiceAbstract<ProductoProveedor,ProductoProveedorFacade> implements  ProductoProveedorServiceIf
{
    
    public ProductoProveedorService() throws RemoteException {
        super(ProductoProveedorFacade.class);
    }

    @Override
    public List<ProductoProveedor> buscarProductoCompraActivo(Producto producto,Compra compra) throws ServicioCodefacException,java.rmi.RemoteException
    {
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("producto", producto);
        mapParametros.put("proveedor", compra.getProveedor());
        return getFacade().findByMap(mapParametros);
    }
    
    @Override
    public List<ProductoProveedor> buscarProductoProveedorActivo(Producto producto,Persona proveedor) throws ServicioCodefacException,java.rmi.RemoteException
    {
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("producto", producto);
        mapParametros.put("proveedor", proveedor);
        return getFacade().findByMap(mapParametros);
    }
    
    public List<ProductoProveedor> buscarPorProveedorActivo(Persona proveedor) throws ServicioCodefacException,java.rmi.RemoteException
    {
        //ProductoProveedor pp;
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        //mapParametros.put("producto", producto);
        mapParametros.put("proveedor", proveedor);
        return getFacade().findByMap(mapParametros);
    }
    
    public List<ProductoProveedor> buscarPorProductoActivo(Producto producto) throws ServicioCodefacException,java.rmi.RemoteException
    {
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("producto", producto);
        //mapParametros.put("proveedor", proveedor);
        return getFacade().findByMap(mapParametros);
    }
    
}
