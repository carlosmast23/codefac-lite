/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Bodega;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CategoriaProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CompraDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Kardex;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.KardexDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ProductoEnsamble;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.auxiliar.KardexDetalleTmp;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import java.math.BigDecimal;
import java.rmi.Remote;
import java.sql.Date;
import java.util.List;
import java.util.Map;


/**
 *
 * @author CodesoftDesarrollo 1
 */
public interface KardexServiceIf extends ServiceAbstractIf<Kardex>
{
    public Kardex buscarKardexPorProductoyBodega(Bodega bodega,Producto producto) throws java.rmi.RemoteException;
    public void ingresoEgresoInventarioEnsamble(Bodega bodega, Producto productoEnsamble,Integer cantidad,ProductoEnsamble.EnsambleAccionEnum accion) throws java.rmi.RemoteException,ServicioCodefacException;
    public void ingresarInventario(Map<KardexDetalle,CompraDetalle> detalles,Bodega bodega) throws java.rmi.RemoteException,ServicioCodefacException;
    public void ingresarInventario(List<KardexDetalle> detalles) throws java.rmi.RemoteException,ServicioCodefacException;
    public void ingresarInventario(KardexDetalle detalle) throws java.rmi.RemoteException,ServicioCodefacException;
    public List<KardexDetalle> obtenerConsultaPorFecha(Date fechaInicial , Date fechaFinal,Producto producto,Bodega bodega,Integer cantidadMovimientos) throws java.rmi.RemoteException;
    public List<Object[]> consultarStockMinimo(Bodega bodega,CategoriaProducto categoria) throws java.rmi.RemoteException;    
    public List<Kardex> buscarPorProducto(Producto producto) throws java.rmi.RemoteException,ServicioCodefacException;
    public List<Kardex> buscarPorBodega(Bodega bodega) throws java.rmi.RemoteException,ServicioCodefacException;
    public List<Object[]> consultarStock(Bodega bodega,CategoriaProducto categoria) throws java.rmi.RemoteException;
    public List<Kardex> buscarPorProductoYBodega(Producto producto,Bodega bodega) throws java.rmi.RemoteException,ServicioCodefacException;
    public void transferirProductoBodegas(Producto producto,Bodega bodegaOrigen,Bodega bodegaDestino, String descripcion,Integer cantidad,BigDecimal precio,Date fechaTransaccion) throws java.rmi.RemoteException,ServicioCodefacException;
    public  KardexDetalle crearKardexDetalleSinPersistencia(Kardex kardex,TipoDocumentoEnum tipoDocumentoEnum,BigDecimal precioUnitario,Integer cantidad) throws java.rmi.RemoteException,ServicioCodefacException;
    public void recalcularValoresKardex(Kardex kardex,KardexDetalle kardexDetalle) throws java.rmi.RemoteException,ServicioCodefacException;
    public boolean obtenerSiNoExisteStockProducto(Bodega bodega, Producto producto, int cantidad) throws java.rmi.RemoteException;
    
    public List<Kardex> getKardexModificados(Producto productoEnsamble,Integer cantidadEnsamble,Bodega bodega,ProductoEnsamble.EnsambleAccionEnum accion) throws java.rmi.RemoteException,ServicioCodefacException;
    public void ingresoEgresoInventarioEnsambleSinTransaccion(Bodega bodega, Producto productoEnsamble,Integer cantidad,ProductoEnsamble.EnsambleAccionEnum accion) throws java.rmi.RemoteException,ServicioCodefacException;
    
    
    
}
