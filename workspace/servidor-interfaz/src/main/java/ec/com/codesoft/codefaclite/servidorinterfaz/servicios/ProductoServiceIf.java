/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.dataExport.ProductoExportar;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CategoriaProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.KardexDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PresentacionProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ProductoPresentacionDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModoProcesarEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.reportData.ProductoPrecioDataTable;
import ec.com.codesoft.codefaclite.servidorinterfaz.respuesta.ProductoConversionPresentacionRespuesta;
import ec.com.codesoft.codefaclite.servidorinterfaz.respuesta.TopProductoRespuesta;
import java.math.BigDecimal;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Carlos
 */
public interface ProductoServiceIf extends ServiceAbstractIf<Producto> {

       
    public Producto grabar(Producto p,Boolean generarCodigo) throws RemoteException, ServicioCodefacException;
    
    public void editarProducto(Producto p) throws java.rmi.RemoteException,ServicioCodefacException;
    
    //public void eliminar(Producto p) throws java.rmi.RemoteException;
    
    public List<Producto> buscar(Empresa empresa) throws java.rmi.RemoteException;
    
    public Producto buscarPorNombreyEstado(String nombre,GeneralEnumEstado estadoEnum,Empresa empresa) throws RemoteException;
    
    public Producto buscarProductoActivoPorCodigo(String codigo,Empresa empresa) throws ServicioCodefacException,RemoteException;
    
    public List<Producto> obtenerTodosActivos(Empresa empresa) throws java.rmi.RemoteException;
        
    public List<Producto> buscarGenerarCodigoBarras(EnumSiNo enumSiNo,Empresa empresa ) throws ServicioCodefacException,RemoteException;
    
    public void grabarConInventario(Producto p,KardexDetalle kardexDetalle) throws ServicioCodefacException,java.rmi.RemoteException;
    
    public void eliminarProducto(Producto p,ModoProcesarEnum modoProcesar) throws ServicioCodefacException, RemoteException;
    
    public List<Producto> buscarProductoActivo(Producto producto,Empresa empresa) throws ServicioCodefacException,RemoteException;
    
    public void actualizarPrecios(List<ProductoPrecioDataTable> productos ) throws RemoteException, ServicioCodefacException;
    
    public List<Producto> reporteProducto(Producto producto,Boolean pendienteActualizarPrecio) throws RemoteException,ServicioCodefacException;
    
    public List<PresentacionProducto> obtenerPresentacionesProducto(Producto producto) throws RemoteException,ServicioCodefacException;
    
    public Producto buscarProductoEmpaquePrincipal(Producto producto) throws RemoteException,ServicioCodefacException;
    
    public ProductoPresentacionDetalle buscarProductoPorPresentacion(PresentacionProducto presentacion,Producto producto) throws RemoteException,ServicioCodefacException;
    
    public List<TopProductoRespuesta> topProductosMasVendidosService() throws ServicioCodefacException, RemoteException;
    
    public ProductoConversionPresentacionRespuesta convertirProductoEmpaqueSecundarioEnPrincipal(Producto productoEmpaqueSecundario,BigDecimal cantidad,BigDecimal precioUnitario) throws RemoteException,ServicioCodefacException;
    
    public String actualizarProductoExportados(ProductoExportar productoExportar,Empresa empresa) throws  ServicioCodefacException, RemoteException;
    
    public List<Producto> buscarPorCategoria(CategoriaProducto categoria) throws RemoteException,ServicioCodefacException;
    
    public Producto buscarProductoDefectoCompras( Producto producto)throws RemoteException,ServicioCodefacException;
    
    public void actualizarPreciosPresentaciones(Producto producto) throws RemoteException, ServicioCodefacException;
    
    public ProductoPresentacionDetalle buscarProductoPorPresentacionCodigo(String presentacionCodigo,Producto producto) throws RemoteException,ServicioCodefacException;
}
