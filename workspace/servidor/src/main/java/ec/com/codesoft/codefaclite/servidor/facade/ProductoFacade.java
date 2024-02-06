/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PresentacionProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ProductoActividad;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ProductoComponenteDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ProductoPresentacionDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.OperadorNegocioEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoProductoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.respuesta.TopProductoRespuesta;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.ParametroUtilidades;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.persistence.Query;

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
    
    public List<PresentacionProducto> obtenerPresentacionesProductoFacade(Producto producto) throws RemoteException,ServicioCodefacException
    {
        ProductoPresentacionDetalle ppd;
        //ppd.getPresentacionProducto();
        //ppd.getProductoEmpaquetado();
        //ppd.getProductoOriginal();
        String queryString = "SELECT DISTINCT pd.presentacionProducto FROM ProductoPresentacionDetalle pd WHERE ( pd.productoEmpaquetado=?1 OR pd.productoOriginal=?1) " ;
        Query query = getEntityManager().createQuery(queryString);
        query.setParameter(1, producto);
        
        return query.getResultList();
    }
    
    public List<ProductoPresentacionDetalle> buscarPresentacionesPorProductoFacade(Producto producto) throws RemoteException,ServicioCodefacException
    {
        //ProductoPresentacionDetalle ppd;
        //ppd.getProductoEmpaquetado().getEstado();        
        //ppd.getPresentacionProducto()
        String queryString = "SELECT DISTINCT pd FROM ProductoPresentacionDetalle pd WHERE (pd.productoOriginal=?2 and pd.productoEmpaquetado.estado=?3 ) " ;
        Query query = getEntityManager().createQuery(queryString);        
        query.setParameter(2, producto);
        query.setParameter(3, GeneralEnumEstado.ACTIVO.getEstado());
        
        List<ProductoPresentacionDetalle> productoList=query.getResultList();
                
        return productoList;
    }
    
    public ProductoPresentacionDetalle buscarProductoPorPresentacionFacade(PresentacionProducto presentacion,Producto producto) throws RemoteException,ServicioCodefacException
    {
        ProductoPresentacionDetalle ppd;
        //ppd.getPresentacionProducto()
        String queryString = "SELECT DISTINCT pd FROM ProductoPresentacionDetalle pd WHERE pd.presentacionProducto=?1 AND (pd.productoOriginal=?2 )" ;
        Query query = getEntityManager().createQuery(queryString);
        query.setParameter(1, presentacion);
        query.setParameter(2, producto);
        
        List<ProductoPresentacionDetalle> productoList=query.getResultList();
        
        if(productoList.size()>0)
        {
            return productoList.get(0);
        }
        
        return null;
    }
    
    public ProductoPresentacionDetalle buscarProductoPorPresentacionCodigoFacade(String presentacionCodigo,Producto producto) throws RemoteException,ServicioCodefacException
    {
        //ProductoPresentacionDetalle ppd;
        //ppd.getPresentacionProducto().getNombre();
        //ppd.getPresentacionProducto()
        String queryString = "SELECT DISTINCT pd FROM ProductoPresentacionDetalle pd WHERE pd.presentacionProducto.nombre=?1 AND (pd.productoOriginal=?2 )" ;
        Query query = getEntityManager().createQuery(queryString);
        query.setParameter(1, presentacionCodigo);
        query.setParameter(2, producto);
        
        List<ProductoPresentacionDetalle> productoList=query.getResultList();
        
        if(productoList.size()>0)
        {
            return productoList.get(0);
        }
        
        return null;
    }
    
    public List<ProductoActividad> buscarActividadPorProducto(Producto producto) throws RemoteException,ServicioCodefacException
    {
        //ProductoActividad pa;
        //pa.getProducto()
        
        String queryString = "SELECT DISTINCT pa FROM ProductoActividad pa WHERE pa.producto=?1 " ;
        Query query = getEntityManager().createQuery(queryString);
        query.setParameter(1, producto);
        
        return query.getResultList();
    }
    
    public List<ProductoComponenteDetalle> buscarComponentePorProducto(Producto producto) throws RemoteException,ServicioCodefacException
    {
        //ProductoComponenteDetalle pcd;
        //pcd.getProducto();
        
        String queryString = "SELECT DISTINCT pd FROM ProductoComponenteDetalle pd WHERE pd.producto=?1 " ;
        Query query = getEntityManager().createQuery(queryString);
        query.setParameter(1, producto);
        
        return query.getResultList();
    
    }
    
    public Producto buscarProductoEmpaquePrincipal(Producto producto) throws RemoteException,ServicioCodefacException
    {
        String queryString = "SELECT DISTINCT pd FROM ProductoPresentacionDetalle pd WHERE pd.productoEmpaquetado=?1 " ;
        Query query = getEntityManager().createQuery(queryString);
        query.setParameter(1, producto);
        
        List<ProductoPresentacionDetalle> productoList=query.getResultList();
        
        if(productoList.size()>0)
        {
            return productoList.get(0).getProductoOriginal();
        }
        
        return null;
    }
    
    public List<Producto> reporteProductoFacade(Producto producto,Boolean pendienteActualizarPrecio) throws RemoteException,ServicioCodefacException
    {
        //producto.getIdProducto()
        
        String wherePendienteActualizar = "";

        if (pendienteActualizarPrecio) 
        {
            wherePendienteActualizar = " AND p.actualizarPrecio=?3 ";
        }
        
        String whereProducto="";
        if(producto!=null)
        {
            whereProducto=" AND p.idProducto=?2 ";
        }
        
        String queryString = "SELECT p FROM Producto p WHERE p.estado=?1 "+whereProducto+wherePendienteActualizar ;
        
        Query query = getEntityManager().createQuery(queryString);
        
        query.setParameter(1,GeneralEnumEstado.ACTIVO.getEstado());
        
        if (pendienteActualizarPrecio) 
        {
            query.setParameter(3,EnumSiNo.SI.getLetra());
        }
        
        if(producto!=null)
        {
            query.setParameter(2,producto.getIdProducto());
        }
        
        
        return query.getResultList();
    }
    
    public Producto buscarProductoActivoPorCodigoFacade(String codigo,String nombre,Empresa empresa,Boolean consultarPresentaciones) throws ServicioCodefacException, RemoteException
    {
        //Producto p;
        //p.getNombre()
        String whereEmpresa="";
        if(!ParametroUtilidades.comparar(empresa,ParametroCodefac.DATOS_COMPARTIDOS_EMPRESA,EnumSiNo.SI))
        {
            whereEmpresa=" AND p.empresa=?3 ";
            //mapParametros.put("empresa",empresa);        
        }
        
        String wherePresentaciones="";
        if(!consultarPresentaciones)
        {
            wherePresentaciones=" AND p.tipoProductoCodigo<>?4";
        }
        
        String whereCodigo="";
        if(codigo!=null)
        {
            whereCodigo=" AND p.codigoPersonalizado=?1";
        }
        
        String whereNombre="";
        if(nombre!=null)
        {
            whereNombre=" AND p.nombre=?5 ";
        }
                
        
        String queryString = "SELECT p FROM Producto p WHERE p.estado=?2 "+wherePresentaciones+whereEmpresa +whereCodigo+whereNombre;
        
        Query query = getEntityManager().createQuery(queryString);
        if(!ParametroUtilidades.comparar(empresa,ParametroCodefac.DATOS_COMPARTIDOS_EMPRESA,EnumSiNo.SI))
        {
            query.setParameter(3,empresa);
        }
        
        if(codigo!=null)
        {
            query.setParameter(1, codigo);
        }
        
        query.setParameter(2, GeneralEnumEstado.ACTIVO.getEstado());
        
        if(!consultarPresentaciones)
        {
            query.setParameter(4, TipoProductoEnum.EMPAQUE.getLetra());
        }
        
        if(nombre!=null)
        {
                query.setParameter(5, nombre);
        }
        
        //Cuando este configurado como datos compartidos no tomo en cuenta de donde esta cogiendo la empresa
        
        List<Producto> productos=query.getResultList();
        if(productos.size()>0)
        {
            return productos.get(0);
        }
        return null;
        
    }
    
    public List<TopProductoRespuesta> topProductosMasVendidosFacade() throws ServicioCodefacException, RemoteException
    {
        String queryString = "SELECT FD.CODIGO_PRINCIPAL,FD.DESCRIPCION,SUM(CANTIDAD) AS CANTIDAD FROM FACTURA_DETALLE FD INNER JOIN FACTURA F ON FD.FACTURA_ID =F.ID INNER JOIN PRODUCTO P ON FD.CODIGO_PRINCIPAL=P.CODIGO_PERSONALIZADO WHERE F.ESTADO <>'E' AND F.ESTADO<>'N' AND F.ESTADO_NOTA_CREDITO='N' AND P.TIPO_PRODUCTO_COD='p' GROUP BY DESCRIPCION,CODIGO_PRINCIPAL ORDER BY SUM(CANTIDAD) DESC" ;
        Query query=getEntityManager().createNativeQuery(queryString);
        //Object resultado= query.getResultList();
        
        List<Object[]> listaResultado= query.getResultList();
        
        return TopProductoRespuesta.castList(listaResultado);
        
    }
      
}
