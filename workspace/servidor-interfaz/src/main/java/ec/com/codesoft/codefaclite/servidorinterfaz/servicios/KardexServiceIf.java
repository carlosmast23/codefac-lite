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
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import java.math.BigDecimal;
 
import java.sql.Date;
import java.util.List;
import java.util.Map;


/**
 *
 * @author CodesoftDesarrollo 1
 */
public interface KardexServiceIf extends ServiceAbstractIf<Kardex>
{
    public Kardex buscarKardexPorProductoyBodega(Bodega bodega,Producto producto)   ;
    public void ingresoEgresoInventarioEnsamble(Bodega bodega, Producto productoEnsamble,Integer cantidad,ProductoEnsamble.EnsambleAccionEnum accion,Boolean validarStockComponentes) throws ServicioCodefacException;
    public void ingresarInventario(Map<KardexDetalle,CompraDetalle> detalles,Bodega bodega) throws  ServicioCodefacException;
    public void ingresarInventario(List<KardexDetalle> detalles)   throws ServicioCodefacException;
    public void ingresarInventario(KardexDetalle detalle)    throws ServicioCodefacException;
    public List<KardexDetalle> obtenerConsultaPorFecha(Date fechaInicial , Date fechaFinal,Producto producto,Bodega bodega,Integer cantidadMovimientos)   ;
    public List<Object[]> consultarStockMinimo(Bodega bodega,CategoriaProducto categoria)   ;    
    public List<Kardex> buscarPorProducto(Producto producto)   throws ServicioCodefacException;
    public List<Kardex> buscarPorBodega(Bodega bodega)   throws ServicioCodefacException;
    public List<Object[]> consultarStock(Bodega bodega,CategoriaProducto categoria)   ;
    public List<Kardex> buscarPorProductoYBodega(Producto producto,Bodega bodega)   throws ServicioCodefacException;
    public void transferirProductoBodegas(Producto producto,Bodega bodegaOrigen,Bodega bodegaDestino, String descripcion,Integer cantidad,BigDecimal precio,Date fechaTransaccion)   throws ServicioCodefacException;
    public  KardexDetalle crearKardexDetalleSinPersistencia(Kardex kardex,TipoDocumentoEnum tipoDocumentoEnum,BigDecimal precioUnitario,Integer cantidad)   throws ServicioCodefacException;
    public void recalcularValoresKardex(Kardex kardex,KardexDetalle kardexDetalle)   throws ServicioCodefacException;
    public boolean obtenerSiNoExisteStockProducto(Bodega bodega, Producto producto, int cantidad)   ;
    
    public List<Kardex> getKardexModificados(Producto productoEnsamble,Integer cantidadEnsamble,Bodega bodega,ProductoEnsamble.EnsambleAccionEnum accion)   throws ServicioCodefacException;
    public void ingresoEgresoInventarioEnsambleSinTransaccion(Bodega bodega, Producto productoEnsamble,Integer cantidad,ProductoEnsamble.EnsambleAccionEnum accion,Boolean validarStockComponentes)   throws ServicioCodefacException;
    
    public Kardex construirKardexVacioSinPersistencia()   throws ServicioCodefacException;
    
    public List<Kardex> buscarPorProducto(Producto producto,GeneralEnumEstado generalEnumEstado)   throws ServicioCodefacException;
    /**
     * Este metodo permite crear un movimiento que permite dejar en 0 el Stock actual
     * @param kardex
     * @  
     * @throws ServicioCodefacException 
     */
    public void anularInventario(Kardex kardex)    throws ServicioCodefacException;
    
    public Kardex buscarKardexPorProducto(Producto producto)   ;
    
}
