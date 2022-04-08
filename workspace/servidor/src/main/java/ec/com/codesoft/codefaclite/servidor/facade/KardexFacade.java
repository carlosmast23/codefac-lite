/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Bodega;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CategoriaProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Kardex;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.KardexDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.utilidades.list.UtilidadesMap;
import ec.com.codesoft.codefaclite.utilidades.seguridad.UtilidadesHash;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author Carlos
 */
public class KardexFacade extends AbstractFacade<Kardex> {

    public KardexFacade() {
        super(Kardex.class);
    }

    public List<Object[]> obtenerConsultaSaldoAnterior(Date fechaCorte, Producto producto, Bodega bodega) {
        //KardexDetalle kd;
        //kd.getKardex().getBodega();
        //kd.getKardex().getProducto();
        //kd.getPrecioTotal();
        String queryString = "SELECT kd.codigoTipoDocumento, SUM(kd.cantidad),SUM(kd.precioUnitario),SUM(kd.precioTotal) "
                + "FROM KardexDetalle kd "
                + "WHERE kd.fechaIngreso<?1 and kd.kardex.bodega=?2 and kd.kardex.producto=?3 "
                + "group by kd.codigoTipoDocumento ";

        Query query = getEntityManager().createQuery(queryString);
        query.setParameter(1, fechaCorte);
        query.setParameter(2, bodega);
        query.setParameter(3, producto);

        return query.getResultList();

    }

    public List<KardexDetalle> obtenerConsultaPorFechaFacade(Date fechaInicial, Date fechaFinal, Producto producto, Bodega bodega, Integer cantidadMovimientos) {
        try {
            //KardexDetalle kd;
            //kd.getFechaCreacion();

            //kd.getFechaIngreso();
            String queryString = "SELECT kd FROM KardexDetalle kd WHERE kd.kardex.bodega=?3 and kd.kardex.producto=?4 ";

            if (fechaInicial != null) {
                queryString += " and kd.fechaIngreso>=?1 ";
            }

            if (fechaFinal != null) {
                queryString += " and kd.fechaIngreso<=?2 ";
            }
            
            //ordenar kardex por fechas de los movimientos
            queryString+=" order by kd.fechaIngreso ";

            //Agregar orden y un limite de la consulta
            //queryString+=" order by kd.id desc ";
            System.out.println(queryString);
            Query query = getEntityManager().createQuery(queryString);

            //if (cantidadMovimientos != null) {
            //    query.setMaxResults(cantidadMovimientos);
            //}
            query.setParameter(3, bodega);
            query.setParameter(4, producto);

            if (fechaInicial != null) {
                query.setParameter(1, fechaInicial);
            }

            if (fechaFinal != null) {
                query.setParameter(2, fechaFinal);
            }

            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    public Long consultarStockMinimoCantidadFacade(Empresa empresa)  throws java.rmi.RemoteException
    {
        //Producto producto;
        //producto.getCantidadMinima()
        //Kardex kardex;
        String queryString = " SELECT COUNT(k) FROM Kardex k WHERE (k.producto.estado<>?4 ) AND k.stock<k.producto.cantidadMinima ";               
        Query query = getEntityManager().createQuery(queryString);
        query.setParameter(4,GeneralEnumEstado.ELIMINADO.getEstado());
        return (Long) query.getSingleResult();
    }

    public List<Object[]> consultarStockMinimoFacade(Bodega bodega,CategoriaProducto categoria) throws java.rmi.RemoteException {

        String whereBodega="";
        if(bodega!=null)
        {
            whereBodega=" and k.bodega=?1 ";
        }
        
        String whereCategoria="";
        if(categoria!=null)
        {
            whereCategoria=" and k.producto.catalogoProducto.categoriaProducto=?2 ";
        }
        
        String queryString = "SELECT k.producto,max(k.stock) FROM Kardex k WHERE 1=1 AND k.producto IS NOT NULL AND (k.producto.estado<>?4 )  "+whereBodega+whereCategoria+" "
                + " group by k.producto having max(k.stock)<=k.producto.cantidadMinima  ";
        Query query = getEntityManager().createQuery(queryString);
        
        if(bodega!=null)
        {
            query.setParameter(1,bodega);
        }
        
        if(categoria!=null)
        {
            query.setParameter(2,categoria);
        }
        
        query.setParameter(4,GeneralEnumEstado.ELIMINADO.getEstado());
        //query.setParameter(1,producto);
        return query.getResultList();

    }

    /**
     * TODO: Falta filtrar por empresa cuando sea vacia la bodega
     * @param bodega
     * @return
     * @throws java.rmi.RemoteException 
     */
    public List<Object[]> consultarStockFacade(Bodega bodega,CategoriaProducto categoria,Empresa empresa) throws java.rmi.RemoteException {
        //Kardex k;k.getBodega().getEmpresa();
        //k.getProducto().getCatalogoProducto().getCategoriaProducto();
        //k.getProducto().getNombre()
        
        
        String whereBodega="";
        if(bodega!=null)
        {
            whereBodega=" and k.bodega=?1 ";
        }
        
        String whereCategoria="";
        if(categoria!=null)
        {
            whereCategoria=" and k.producto.catalogoProducto.categoriaProducto=?2 ";
        }
        
        //Kardex kardex;
        //kardex.getBodega().getEstado()
        //Talvez agregar condicion para buscar solo por kardex activos
        String queryString = "SELECT k.producto,k.stock,k.costoPromedio,k.bodega,k.lote FROM Kardex k WHERE k.bodega.empresa=?5 AND k.bodega.estado=?6  AND k.producto IS NOT NULL AND (k.producto.estado<>?4 ) "+whereBodega+whereCategoria+" ORDER BY k.producto.nombre asc";
        Query query = getEntityManager().createQuery(queryString);
        
        query.setParameter(5,empresa);
        query.setParameter(6,GeneralEnumEstado.ACTIVO.getEstado());
        
        if(bodega!=null)
        {
            query.setParameter(1,bodega);
        }        
        
        if(categoria!=null)
        {
            query.setParameter(2,categoria);
        }
        
        //query.setParameter(3,GeneralEnumEstado.ELIMINADO.getEstado());
        query.setParameter(4,GeneralEnumEstado.ELIMINADO.getEstado());
        
        return query.getResultList();

    }
    
    public List<KardexDetalle> consultarMovimientosTransferenciaFacade(java.util.Date fechaInicial, java.util.Date fechaFinal) throws java.rmi.RemoteException,ServicioCodefacException
    {
        //KardexDetalle kd;
        //kd.getKardex().getProducto().getNombre();
        //kd.getFechaCreacion()
        //kd.getKardex().getFechaCreacion()
        //kd.setCodigoTipoDocumento(usuarioDb);
        //TipoDocumentoEnum.TRANSFERENCIA_MERCADERIA_ORIGEN;
        //kd.getFechaIngreso()
        //kd.getKardex().getEstado();
        
        String fechaInicialWhere="";
        if(fechaInicial!=null)
        {
            fechaInicialWhere=" AND kd.fechaCreacion>=?2  ";
        }
        
        String fechaFinalWhere="";
        if(fechaFinal!=null)
        {
            fechaFinalWhere=" AND kd.fechaCreacion<=?3  ";
        }
        
        
        
        String queryString=" SELECT kd FROM KardexDetalle kd WHERE kd.kardex.estado=?1 AND kd.codigoTipoDocumento=?4 "+fechaInicialWhere+fechaFinalWhere;
        //Ordenar por nombre de producto
        queryString+=" ORDER BY kd.kardex.producto.nombre ";
        
        Query query = getEntityManager().createQuery(queryString);
        
        query.setParameter(1, GeneralEnumEstado.ACTIVO.getEstado());
        
        query.setParameter(4,TipoDocumentoEnum.TRANSFERENCIA_MERCADERIA_ORIGEN.getCodigo());
        
        if(fechaInicial!=null)
        {
            query.setParameter(2,fechaInicial);
        }
        
        if(fechaFinal!=null)
        {
            query.setParameter(3,fechaFinal);
        }
    
        return query.getResultList();
    }
    
    /**
     * Obtiene el total de ventas en el inventario desde una fecha dado hasta una fecha final
     * @param fechaInicio
     * @param fechaFinal
     * @return 
     */
    public Map<Producto,BigDecimal> obtenerCantidadVentas(Date fechaInicio,Date fechaFinal,Sucursal sucursal)
    {
        //KardexDetalle kd;
        //kd.getKardex().getProducto();
        //kd.getCantidad();
        //kd.getCodigoTipoDocumento();
        //kd.getCodigoTipoDocumentoEnum().VENTA_INVENTARIO;
        
        /*kd.getCodigoTipoDocumentoEnum().VENTA
        kd.;*/
        String queryString="SELECT kd.kardex.producto,sum(kd.cantidad) FROM KardexDetalle kd WHERE kd.codigoTipoDocumento=?1 and kd.fechaIngreso>=?2 and kd.fechaIngreso<=?3 and kd.kardex.bodega.sucursal=?4 group by kd.kardex.producto ";        
        Query query = getEntityManager().createQuery(queryString);
               
        query.setParameter(1,TipoDocumentoEnum.VENTA_INVENTARIO);
        query.setParameter(2,fechaInicio);
        query.setParameter(3,fechaFinal);
        query.setParameter(4,sucursal);
        
        List<Object[]> resultadoList= query.getResultList();
        
        Map<Producto,BigDecimal> mapResultado= UtilidadesMap.crearMapDesdeList(resultadoList, new UtilidadesMap.MapCastListIf<Producto,BigDecimal,Object[]>() 
        {
            @Override
            public Producto getClave(Object[] dato) {
                return (Producto) dato[0];
            }

            @Override
            public BigDecimal getValor(Object[] dato) {
                return (BigDecimal) dato[1];
            }            
        });
        
        return mapResultado;
    }
    
    /**
     * Obtiene solo los movimientos en el kardes de los movimientos de las compras , que son las que generan movimientos
     * @param fechaInicio
     * @param fechaFinal
     * @param sucursal
     * @return 
     */
    public Map<Producto,StockPromedioYCantidadRespuesta> obtenerStockComprasPromedioYCantidad(Date fechaInicio,Date fechaFinal,Sucursal sucursal)
    {
        String queryString="SELECT kd.kardex.producto,avg(kd.cantidad),count(kd.id) FROM KardexDetalle kd WHERE kd.codigoTipoDocumento=?1 and kd.fechaIngreso>=?2 and kd.fechaIngreso<=?3 and kd.kardex.bodega.sucursal=?4 group by kd.kardex.producto ";        
        Query query = getEntityManager().createQuery(queryString);
               
        query.setParameter(1,TipoDocumentoEnum.COMPRA_INVENTARIO);
        query.setParameter(2,fechaInicio);
        query.setParameter(3,fechaFinal);
        query.setParameter(4,sucursal);
        
        List<Object[]> resultadoList= query.getResultList();
        
        Map<Producto,StockPromedioYCantidadRespuesta> mapResultado= UtilidadesMap.crearMapDesdeList(resultadoList, new UtilidadesMap.MapCastListIf<Producto,StockPromedioYCantidadRespuesta,Object[]>() 
        {
            @Override
            public Producto getClave(Object[] dato) {
                return (Producto) dato[0];
            }

            @Override
            public StockPromedioYCantidadRespuesta getValor(Object[] dato) {
                return new StockPromedioYCantidadRespuesta((BigDecimal)dato[1],(BigDecimal)dato[2]);
            }            
        });
        
        return mapResultado;
        
        
    }
    
    public List<Producto> obtenerListaProductosInventario(Empresa empresa)
    {
        Producto producto;
        //producto.getEstado();
        //producto.getManejarInventario();
        //producto.setEmpresa(empresa);
                
        String queryString="SELECT p FROM Producto p WHERE p.estado=?1 and p.manejarInventario=?2 and p.empresa=?3 ";
        Query query = getEntityManager().createQuery(queryString);
        
        query.setParameter(1,GeneralEnumEstado.ACTIVO.getEstado());
        query.setParameter(2,EnumSiNo.SI.getLetra());
        query.setParameter(3, empresa);
        return query.getResultList();
        
    }
    
    /**
     * =============================================================================
     *                          CLASES ADICIONALES
     * =============================================================================
     */
    
    public static class StockPromedioYCantidadRespuesta
    {
        public BigDecimal promedio;
        public BigDecimal cantidad;

        public StockPromedioYCantidadRespuesta(BigDecimal promedio, BigDecimal cantidad) {
            this.promedio = promedio;
            this.cantidad = cantidad;
        }
        
    }

}
