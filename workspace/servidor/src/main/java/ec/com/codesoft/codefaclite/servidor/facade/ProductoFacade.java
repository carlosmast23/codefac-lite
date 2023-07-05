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
    
    public Producto buscarProductoActivoPorCodigoFacade(String codigo,Empresa empresa) throws ServicioCodefacException, RemoteException
    {
        String whereEmpresa="";
        if(!ParametroUtilidades.comparar(empresa,ParametroCodefac.DATOS_COMPARTIDOS_EMPRESA,EnumSiNo.SI))
        {
            whereEmpresa=" AND p.empresa=?3 ";
            //mapParametros.put("empresa",empresa);        
        }
        
        String queryString = "SELECT p FROM Producto p WHERE p.codigoPersonalizado=?1 AND p.estado=?2 AND p.tipoProductoCodigo<>?4  "+whereEmpresa ;
        
        Query query = getEntityManager().createQuery(queryString);
        if(!ParametroUtilidades.comparar(empresa,ParametroCodefac.DATOS_COMPARTIDOS_EMPRESA,EnumSiNo.SI))
        {
            query.setParameter(3,empresa);
        }
        
        query.setParameter(1, codigo);
        query.setParameter(2, GeneralEnumEstado.ACTIVO.getEstado());
        query.setParameter(4, TipoProductoEnum.EMPAQUE.getLetra());
        
        //Map<String,Object> mapParametros=new HashMap<String,Object>();        
        //mapParametros.put("codigoPersonalizado",codigo);
        //mapParametros.put("estado",GeneralEnumEstado.ACTIVO.getEstado());        
        
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
        String queryString = "SELECT FD.DESCRIPCION,SUM(CANTIDAD) AS CANTIDAD FROM FACTURA_DETALLE FD GROUP BY DESCRIPCION ORDER BY SUM(CANTIDAD) DESC " ;
        Query query=getEntityManager().createNativeQuery(queryString);
        //Object resultado= query.getResultList();
        
        List<Object[]> listaResultado= query.getResultList();
        
        return TopProductoRespuesta.castList(listaResultado);
        
    }
      
}
