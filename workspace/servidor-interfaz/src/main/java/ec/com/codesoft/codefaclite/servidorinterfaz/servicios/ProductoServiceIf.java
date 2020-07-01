/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.KardexDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
 
 ;
import java.util.List;

/**
 *
 * @author Carlos
 */
public interface ProductoServiceIf extends ServiceAbstractIf<Producto> {

       
    public Producto grabar(Producto p) throws ServicioCodefacException ;
    
    public void editarProducto(Producto p) throws ServicioCodefacException;
    
    //public void eliminar(Producto p)   ;
    
    public List<Producto> buscar(Empresa empresa)   ;
    
    public Producto buscarPorNombreyEstado(String nombre,GeneralEnumEstado estadoEnum,Empresa empresa);    
    
    public Producto buscarProductoActivoPorCodigo(String codigo,Empresa empresa) throws ServicioCodefacException   ;
    
    public List<Producto> obtenerTodosActivos(Empresa empresa)   ;
    
    public Producto buscarGenerarCodigoBarras(EnumSiNo enumSiNo,Empresa empresa ) throws ServicioCodefacException   ;
    
    public void grabarConInventario(Producto p,KardexDetalle kardexDetalle) throws ServicioCodefacException   ;
    
    public void eliminarProducto(Producto p) throws ServicioCodefacException;
    
}
