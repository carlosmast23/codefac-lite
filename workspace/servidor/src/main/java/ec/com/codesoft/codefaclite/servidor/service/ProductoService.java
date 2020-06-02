/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoProductoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ConstrainViolationExceptionSQL;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidor.facade.ProductoFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CategoriaProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Kardex;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.KardexDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CrudEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ProductoServiceIf;
import ec.com.codesoft.codefaclite.utilidades.list.UtilidadesLista;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityTransaction;
import org.eclipse.persistence.exceptions.DatabaseException;

/**
 *
 * @author PC
 */
public class ProductoService extends ServiceAbstract<Producto,ProductoFacade> implements ProductoServiceIf
{
    private ProductoFacade productoFacade;
    
    public ProductoService() throws RemoteException
    {
        super(ProductoFacade.class);
        this.productoFacade = new ProductoFacade();
    }
    
       
    public Producto grabar(Producto p) throws ServicioCodefacException
    {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                grabarSinTransaccion(p);
            }
        });        
        return p;
    }
    
    private void grabarSinTransaccion(Producto p) throws java.rmi.RemoteException,ServicioCodefacException{
        validarGrabarProducto(p,CrudEnum.CREAR);
        
        //Si el catalogo producto no esta creado primero crea la entidad
        CatalogoProducto catalogoProducto = p.getCatalogoProducto();
        if (catalogoProducto.getId() == null) {
            CategoriaProducto categoriaProducto = catalogoProducto.getCategoriaProducto();
            if(categoriaProducto!=null)
            {
                if (categoriaProducto.getIdCatProducto() == null)//Si no existe la categoria tambien se los crea
                {
                    entityManager.persist(categoriaProducto);
                }
            }

            entityManager.persist(catalogoProducto);
        }

        //Si no son ensables remover datos para no tener incoherencias
        if (!TipoProductoEnum.EMSAMBLE.getLetra().equals(p.getTipoProductoCodigo())) {
            if (p.getDetallesEnsamble() != null) {
                p.getDetallesEnsamble().clear();
            }
        }
        entityManager.persist(p);
    }
    
    private void validarGrabarProducto(Producto p,CrudEnum estadoEnum) throws java.rmi.RemoteException,ServicioCodefacException    
    {
        //TODO: Analizar porque el Sri supuestamente si deja mandar productos con valor 0 , por el momento solo pongo los menores que 0
        if(p.getValorUnitario().compareTo(BigDecimal.ZERO)<0)
        {
            throw new ServicioCodefacException("El valor unitario del producto no puede ser menor que cero");
        }
        
        /**
         * =================================================================
         *          VALIDAR QUE NO EXITAN PRODUCTOS DUPLICADOS
         * =================================================================
         */
        Producto productoDuplicado=this.buscarProductoActivoPorCodigo(p.getCodigoPersonalizado(),p.getEmpresa());
        if(estadoEnum.equals(CrudEnum.CREAR))
        {            
            if(productoDuplicado!=null)
            {
                throw new ServicioCodefacException("Ya existe un producto ingresado con el mismo código principal");
            }
        }else if(estadoEnum.equals(CrudEnum.EDITAR))
        {
            //TODO: Pensar en alguna validacion para no pueda editar un producto con un codigo que ya existe y generar problemas
            //TODO: Queda pendiente en buscar la solución
        }
        
        if(p.getCatalogoProducto()==null)
        {
            throw new ServicioCodefacException("No se puede grabar el producto sin un catalago de producto");
        }        
        
        if(p.getCatalogoProducto().getIva()==null)
        {
            throw new ServicioCodefacException("No se puede grabar el producto sin especificar un porcentaje de Iva");
        }
    }
    
    public void editarProducto(Producto p) throws java.rmi.RemoteException,ServicioCodefacException
    {
        validarGrabarProducto(p,CrudEnum.EDITAR);
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                entityManager.merge(p);
            }
        });

    }
    
    public void eliminarProducto(Producto p) throws ServicioCodefacException, RemoteException
    {
        
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {

                //Si el producto no maneja inventario lo puede eliminar directamente
                KardexService kardexService = new KardexService();
                List<Kardex> resultadoKardex = kardexService.buscarPorProducto(p, GeneralEnumEstado.ACTIVO);
                List<String> stockPositivoBodega = new ArrayList<String>();
                
                for (Kardex kardex : resultadoKardex) {
                    
                    if(kardex.getEstadoEnum().equals(GeneralEnumEstado.ACTIVO))
                    {                        
                        if (kardex.getStock() > 0) 
                        {
                            //Agrego a la lista la bodega con el kardex que tiene problema antes de eliminar
                            stockPositivoBodega.add(kardex.getBodega().getNombre());                    
                            //throw new ServicioCodefacException("No se puede eliminar el producto porque tiene stock en las bodegas: ");
                            //stockPositivoBodega.add(kardex.getBodega().getNombre());
                        } else 
                        {
                            //Si los kardex no tiene problema y estan con saldos en 0 los elimino y si no se cumple con todos no importa porque se realiza un rollback
                            kardex.setEstadoEnum(GeneralEnumEstado.ELIMINADO);
                            entityManager.merge(kardex);
                        }
                    }
                }

                if (stockPositivoBodega.size() > 0) {
                    throw new ServicioCodefacException("No se puede eliminar el producto porque tiene stock en las bodegas: " + UtilidadesLista.castListToString(stockPositivoBodega, ","));
                }

                //============================================//
                // SI NO TIENEN NINGUNA RESTRICCION ENTONCES ELIMINO EL PRODUCTO Y EL KARDEX //
                //============================================//
                p.setEstadoEnum(GeneralEnumEstado.ELIMINADO);
                entityManager.merge(p);
            }
        });
       
        
    }
    
    public List<Producto> buscar(Empresa empresa)
    {
        Map<String,Object> mapParametros=new HashMap<String,Object>();
        //mapParametros.put("estado",estadoEnum.getEstado());
        mapParametros.put("empresa",empresa);
        
        List<Producto> productos=getFacade().findByMap(mapParametros);
        /*if(productos.size()>0)
        {
            return productos.;
        }*/
        return productos;
        //return productoFacade.findAll();
    }
    
    public Producto buscarPorNombreyEstado(String nombre,GeneralEnumEstado estadoEnum,Empresa empresa) throws RemoteException
    {

        Map<String,Object> mapParametros=new HashMap<String,Object>();
        mapParametros.put("nombre",nombre);
        mapParametros.put("estado",estadoEnum.getEstado());
        mapParametros.put("empresa",empresa);
        
        List<Producto> productos=getFacade().findByMap(mapParametros);
        if(productos.size()>0)
        {
            return productos.get(0);
        }
        return null;
        
    }
    
    public Producto buscarProductoActivoPorCodigo(String codigo,Empresa empresa) throws ServicioCodefacException, RemoteException
    {
        //Producto p;
        //p.getCodigoPersonalizado();
        //p.getEstado();GeneralEnumEstado
        Map<String,Object> mapParametros=new HashMap<String,Object>();        
        mapParametros.put("codigoPersonalizado",codigo);
        mapParametros.put("empresa",empresa);        
        mapParametros.put("estado",GeneralEnumEstado.ACTIVO.getEstado());        
        
        List<Producto> productos=getFacade().findByMap(mapParametros);
        if(productos.size()>0)
        {
            return productos.get(0);
        }
        return null;
        
    }
            
    public List<Producto> obtenerTodosActivos(Empresa empresa) throws java.rmi.RemoteException
    {
        //Producto producto;
        //producto.getEstado()
        Map<String,Object> mapParametros=new HashMap<String, Object>();
        mapParametros.put("estado",GeneralEnumEstado.ACTIVO.getEstado());
        mapParametros.put("empresa",empresa);
        List<Producto> resultados=getFacade().findByMap(mapParametros);
        return resultados;
    }
    
    public Producto buscarGenerarCodigoBarras(EnumSiNo enumSiNo,Empresa empresa ) throws ServicioCodefacException,RemoteException
    {
        //Producto p;
        //p.getEmpresa();
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("estado", GeneralEnumEstado.ACTIVO.getEstado());
        mapParametros.put("generarCodigoBarras", EnumSiNo.SI.getLetra());
        mapParametros.put("empresa",empresa);
        

        List<Producto> resultado=getFacade().findByMap(mapParametros);
        if(resultado.size()>0)
        {
            return resultado.get(0);
        }
        return null;
    }
    
    public void grabarConInventario(Producto p,KardexDetalle kardexDetalle) throws ServicioCodefacException,java.rmi.RemoteException
    {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                
                //Solo grabo el producto cuando no esta creado previamente
                if(p.getIdProducto()==null)
                {
                    grabarSinTransaccion(p); //graba el producto                
                }
                                
                KardexService kardexService=new KardexService();
                
                //Solo grabar cuando existen datos diferente de null
                if(kardexDetalle!=null)
                {
                    kardexService.grabarKardexDetallSinTransaccion(kardexDetalle);
                }
                
            }
        });
    }
    

}
