/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Compra;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ProductoProveedor;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import es.mityc.firmaJava.ocsp.config.ProveedorInfo;
 
import java.util.List;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public interface ProductoProveedorServiceIf extends ServiceAbstractIf<ProductoProveedor>
{
    public List<ProductoProveedor> buscarProductoCompraActivo(Producto producto,Compra compra) throws ServicioCodefacException   ;
    
    public List<ProductoProveedor> buscarProductoProveedorActivo(Producto producto,Persona proveedor) throws ServicioCodefacException   ;
    
    public List<ProductoProveedor> buscarPorProveedorActivo(Persona proveedor) throws ServicioCodefacException   ;
    
    public List<ProductoProveedor> buscarPorProductoActivo(Producto producto) throws ServicioCodefacException   ;
}
