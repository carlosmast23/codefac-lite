/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade;

import ec.com.codesoft.codefaclite.servidor.service.ProductoService;
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
import ec.com.codesoft.codefaclite.servidorinterfaz.respuesta.ProductoConversionPresentacionRespuesta;
import ec.com.codesoft.codefaclite.servidorinterfaz.respuesta.TopProductoRespuesta;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.ParametroUtilidades;
import jakarta.persistence.EntityManager;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.persistence.Query;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    public List<PresentacionProducto> obtenerPresentacionesProductoFacade(Producto producto,EntityManager em) throws RemoteException,ServicioCodefacException
    {
        ProductoPresentacionDetalle ppd;
        //ppd.getPresentacionProducto();
        //ppd.getProductoEmpaquetado();
        //ppd.getProductoOriginal();
        String queryString = "SELECT DISTINCT pd.presentacionProducto FROM ProductoPresentacionDetalle pd WHERE ( pd.productoEmpaquetado=?1 OR pd.productoOriginal=?1) " ;
        Query query = em.createQuery(queryString);
        query.setParameter(1, producto);
        
        return query.getResultList();
    }
    
    public List<ProductoPresentacionDetalle> buscarPresentacionesPorProductoFacade(Producto producto,EntityManager em) throws RemoteException,ServicioCodefacException
    {
        //ProductoPresentacionDetalle ppd;
        //ppd.getProductoEmpaquetado().getEstado();        
        //ppd.getPresentacionProducto()
        String queryString = "SELECT DISTINCT pd FROM ProductoPresentacionDetalle pd WHERE (pd.productoOriginal=?2 and pd.productoEmpaquetado.estado=?3 ) " ;
        Query query = em.createQuery(queryString);        
        query.setParameter(2, producto);
        query.setParameter(3, GeneralEnumEstado.ACTIVO.getEstado());
        
        List<ProductoPresentacionDetalle> productoList=query.getResultList();
                
        return productoList;
    }
    
    public ProductoPresentacionDetalle buscarProductoPorPresentacionFacade(PresentacionProducto presentacion,Producto producto,EntityManager em) throws RemoteException,ServicioCodefacException
    {
        ProductoPresentacionDetalle ppd;
        //ppd.getPresentacionProducto()
        String queryString = "SELECT DISTINCT pd FROM ProductoPresentacionDetalle pd WHERE pd.presentacionProducto=?1 AND (pd.productoOriginal=?2 )" ;
        Query query = em.createQuery(queryString);
        query.setParameter(1, presentacion);
        query.setParameter(2, producto);
        
        List<ProductoPresentacionDetalle> productoList=query.getResultList();
        
        if(productoList.size()>0)
        {
            return productoList.get(0);
        }
        
        return null;
    }
    
    public ProductoPresentacionDetalle buscarProductoPorPresentacionCodigoFacade(String presentacionCodigo,Producto producto,EntityManager em) throws RemoteException,ServicioCodefacException
    {
        //ProductoPresentacionDetalle ppd;
        //ppd.getPresentacionProducto().getNombre();
        //ppd.getPresentacionProducto()
        String queryString = "SELECT DISTINCT pd FROM ProductoPresentacionDetalle pd WHERE pd.presentacionProducto.nombre=?1 AND (pd.productoOriginal=?2 )" ;
        Query query = em.createQuery(queryString);
        query.setParameter(1, presentacionCodigo);
        query.setParameter(2, producto);
        
        List<ProductoPresentacionDetalle> productoList=query.getResultList();
        
        if(productoList.size()>0)
        {
            return productoList.get(0);
        }
        
        return null;
    }
    
    public List<ProductoActividad> buscarActividadPorProducto(Producto producto,EntityManager em) throws RemoteException,ServicioCodefacException
    {
        //ProductoActividad pa;
        //pa.getProducto()
        
        String queryString = "SELECT DISTINCT pa FROM ProductoActividad pa WHERE pa.producto=?1 " ;
        Query query = em.createQuery(queryString);
        query.setParameter(1, producto);
        
        return query.getResultList();
    }
    
    public List<ProductoComponenteDetalle> buscarComponentePorProducto(Producto producto,EntityManager em) throws RemoteException,ServicioCodefacException
    {
        //ProductoComponenteDetalle pcd;
        //pcd.getProducto();
        
        String queryString = "SELECT DISTINCT pd FROM ProductoComponenteDetalle pd WHERE pd.producto=?1 " ;
        Query query = em.createQuery(queryString);
        query.setParameter(1, producto);
        
        return query.getResultList();
    
    }
    
    public Producto buscarProductoEmpaquePrincipal(Producto producto,EntityManager em) throws RemoteException,ServicioCodefacException
    {
        String queryString = "SELECT DISTINCT pd FROM ProductoPresentacionDetalle pd WHERE pd.productoEmpaquetado=?1 " ;
        Query query = em.createQuery(queryString);
        query.setParameter(1, producto);
        
        List<ProductoPresentacionDetalle> productoList=query.getResultList();
        
        if(productoList.size()>0)
        {
            return productoList.get(0).getProductoOriginal();
        }
        
        return null;
    }
    
    
    //TODO: Buscar por Id
    /*@Deprecated
    public Producto buscarPorId(Object primaryKey) throws java.rmi.RemoteException
    {
        return find(primaryKey);
    }*/
    
    public ProductoConversionPresentacionRespuesta convertirProductoEmpaqueSecundarioEnPrincipal(Producto productoEmpaqueSecundario,BigDecimal cantidad,BigDecimal precioUnitario) throws RemoteException,ServicioCodefacException
    {
        ProductoPresentacionDetalle presentacionDetalle = productoEmpaqueSecundario.buscarPresentacionDetalleProducto();
        
        if(presentacionDetalle==null)
        {
            throw new ServicioCodefacException("No se pudo encontrar la presentación principal para el producto: "+productoEmpaqueSecundario.getNombre()+"\n Posible Solución: Volver a REINGRESAR LA COMPRA");
        }
        
        //TODO: Codigo por el momento para encontrar un error que puede dar al querer convertir un empaque en producto normal
        if (presentacionDetalle.getProductoOriginal().getTipoProductoEnum().equals(TipoProductoEnum.EMPAQUE)) {
            String mensajeError = "Error al convertir el Producto:" + presentacionDetalle.getProductoOriginal().getNombre() + " en la presentacion original para poder guardar.\\n Error con id producto original: " + presentacionDetalle.getProductoOriginal().getIdProducto() + " id producto secundario: " + productoEmpaqueSecundario.getIdProducto();
            Logger.getLogger(ProductoService.class.getName()).log(Level.SEVERE, mensajeError);
            throw new ServicioCodefacException(mensajeError);

        }
        
        if(presentacionDetalle.getCantidad().compareTo(BigDecimal.ZERO)<=0)
        {
            throw new ServicioCodefacException("Error con la PRESENTACION del producto "+presentacionDetalle.getProductoOriginal().getNombre()+" porque tiene una presentacion con una CANTIDAD NO VALIDA");
        }
        
        BigDecimal cantidadEquivalencia = presentacionDetalle.getCantidad();
        cantidad = cantidad.multiply(cantidadEquivalencia);
        precioUnitario = (precioUnitario.divide(cantidadEquivalencia, 6, BigDecimal.ROUND_HALF_UP));
        //Finalmente dejo seleccionado el producto principal para que continue con el proceso
        ProductoConversionPresentacionRespuesta respuesta=new ProductoConversionPresentacionRespuesta(productoEmpaqueSecundario, presentacionDetalle.getProductoOriginal(), cantidad, precioUnitario);
        return  respuesta;
    }
    
    
    public List<Producto> reporteProductoFacade(Producto producto,Boolean pendienteActualizarPrecio,EntityManager em) throws RemoteException,ServicioCodefacException
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
        
        Query query = em.createQuery(queryString);
        
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
    
    public Producto buscarProductoActivoPorCodigoFacade(String codigo,String nombre,Empresa empresa,Boolean consultarPresentaciones,EntityManager em) throws ServicioCodefacException, RemoteException
    {
        //Producto p;
        //p.getCodigoUPC();
        //p.getTipoProductoCodigo()
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
            whereCodigo=" AND ( p.codigoPersonalizado=?1 OR p.codigoUPC=?1 ) ";
        }
        
        String whereNombre="";
        if(nombre!=null)
        {
            whereNombre=" AND p.nombre=?5 ";
        }
                
        
        String queryString = "SELECT p FROM Producto p WHERE p.estado=?2 and ( p.tipoProductoCodigo=?7 or p.tipoProductoCodigo=?8 or p.tipoProductoCodigo=?9 ) "+wherePresentaciones+whereEmpresa +whereCodigo+whereNombre;
        
        Query query = em.createQuery(queryString);
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
        
        query.setParameter(7, "p");
        query.setParameter(8, "e");
        query.setParameter(9, "m");
        
        //Cuando este configurado como datos compartidos no tomo en cuenta de donde esta cogiendo la empresa
        
        List<Producto> productos=query.getResultList();
        if(productos.size()>0)
        {
            
            for (Producto producto : productos) {
                //Verificar si no esta eliminado la presentaciones original
                Producto productoConsultado = producto;

                if (productoConsultado.getTipoProductoEnum().equals(TipoProductoEnum.EMPAQUE)) 
                {
                    Producto productoOriginal = productoConsultado.buscarProductoEmpaquePrincipal();
                    if (productoOriginal.getEstadoEnum().equals(GeneralEnumEstado.ACTIVO))
                    {                        
                        return productoConsultado;
                    }
                }
                else
                {
                    // Si es un producto original y opor consecuencia de la consulta esta activo devuelvo el mismo producto
                    return productoConsultado;
                }
            }            
            
            return null;
        }
        return null;
        
    }
    
    public List<TopProductoRespuesta> topProductosMasVendidosFacade(EntityManager em) throws ServicioCodefacException, RemoteException
    {
        String queryString = "SELECT FD.CODIGO_PRINCIPAL,FD.DESCRIPCION,SUM(CANTIDAD) AS CANTIDAD FROM FACTURA_DETALLE FD INNER JOIN FACTURA F ON FD.FACTURA_ID =F.ID INNER JOIN PRODUCTO P ON FD.CODIGO_PRINCIPAL=P.CODIGO_PERSONALIZADO WHERE F.ESTADO <>'E' AND F.ESTADO<>'N' AND F.ESTADO_NOTA_CREDITO='N' AND P.TIPO_PRODUCTO_COD='p' GROUP BY DESCRIPCION,CODIGO_PRINCIPAL ORDER BY SUM(CANTIDAD) DESC" ;
        Query query=em.createNativeQuery(queryString);
        //Object resultado= query.getResultList();
        
        List<Object[]> listaResultado= query.getResultList();
        
        return TopProductoRespuesta.castList(listaResultado);
        
    }
    
      
}
