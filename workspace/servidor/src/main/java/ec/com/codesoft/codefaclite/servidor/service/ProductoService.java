/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.controlador.excel.ExcelMigrar;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoProductoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ConstrainViolationExceptionSQL;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidor.facade.ProductoFacade;
import ec.com.codesoft.codefaclite.servidor.facade.UtilidadFacade;
import ec.com.codesoft.codefaclite.servidor.service.gestionAcademica.CatalogoProductoService;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.dataExport.ProductoExportar;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Bodega;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CasaComercial;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CategoriaProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ImpuestoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Kardex;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.KardexDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.MarcaProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PresentacionProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ProductoEnsamble;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ProductoPresentacionDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ProductoProveedor;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SegmentoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.TipoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CrudEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModoProcesarEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.reportData.ProductoPrecioDataTable;
import ec.com.codesoft.codefaclite.servidorinterfaz.respuesta.ProductoConversionPresentacionRespuesta;
import ec.com.codesoft.codefaclite.servidorinterfaz.respuesta.TopProductoRespuesta;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ProductoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.ParametroUtilidades;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.list.UtilidadesLista;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesExpresionesRegulares;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import ec.com.codesoft.codefaclite.utilidades.validadores.ExpresionRegular;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesCodigos;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
    
    public ProductoPresentacionDetalle buscarProductoPorPresentacion(PresentacionProducto presentacion,Producto producto) throws RemoteException,ServicioCodefacException
    {
        Producto productoPrincipal= buscarProductoEmpaquePrincipal(producto);
        return getFacade().buscarProductoPorPresentacionFacade(presentacion, productoPrincipal);
        
    }
    
    public ProductoConversionPresentacionRespuesta convertirProductoEmpaqueSecundarioEnPrincipal(Producto productoEmpaqueSecundario,BigDecimal cantidad,BigDecimal precioUnitario) throws RemoteException,ServicioCodefacException
    {
        ProductoPresentacionDetalle presentacionDetalle = productoEmpaqueSecundario.buscarPresentacionDetalleProducto();
        
        //TODO: Codigo por el momento para encontrar un error que puede dar al querer convertir un empaque en producto normal
        if (!presentacionDetalle.getProductoOriginal().getTipoProductoEnum().equals(TipoProductoEnum.PRODUCTO)) {
            String mensajeError = "Error al convertir el Producto:" + presentacionDetalle.getProductoOriginal().getNombre() + " en la presentacion original para poder guardar.\\n Error con id producto original: " + presentacionDetalle.getProductoOriginal().getIdProducto() + " id producto secundario: " + productoEmpaqueSecundario.getIdProducto();
            Logger.getLogger(ProductoService.class.getName()).log(Level.SEVERE, mensajeError);
            throw new ServicioCodefacException(mensajeError);

        }
        
        BigDecimal cantidadEquivalencia = presentacionDetalle.getCantidad();
        cantidad = cantidad.multiply(cantidadEquivalencia);
        precioUnitario = (precioUnitario.divide(cantidadEquivalencia, 6, BigDecimal.ROUND_HALF_UP));
        //Finalmente dejo seleccionado el producto principal para que continue con el proceso
        ProductoConversionPresentacionRespuesta respuesta=new ProductoConversionPresentacionRespuesta(productoEmpaqueSecundario, presentacionDetalle.getProductoOriginal(), cantidad, precioUnitario);
        return  respuesta;
    }
    
    /**
     * Metodo que me va a permitir enviar un producto y buscar cuando tenga varios empaques cual es el que se debe utilizar para las compras
     * //TODO: Por el momento va a buscar siempre el que tenga la mayor cantidad en la presentacion
     * @return 
     */
    public Producto buscarProductoDefectoCompras( Producto producto)throws RemoteException,ServicioCodefacException
    {
        List<ProductoPresentacionDetalle> presentacionList=this.buscarPresentacionesPorProducto(producto);
        ProductoPresentacionDetalle presentacionDetalle=null;
        if(presentacionList.size()>0)
        {
            presentacionDetalle=UtilidadesLista.obtenerDatoMayor(presentacionList, new Comparator<ProductoPresentacionDetalle>() 
            {
                @Override
                public int compare(ProductoPresentacionDetalle o1, ProductoPresentacionDetalle o2) {
                    return o1.getCantidad().compareTo(o2.getCantidad());
                }

            });
        }
        
        //Si el sistema no detecta ninguna presentacion entonces retorno el mismo producto
        if(presentacionDetalle==null)
        {
            return producto;
        }
        
        return presentacionDetalle.getProductoEmpaquetado();
    }
    
    public  List<ProductoPresentacionDetalle> buscarPresentacionesPorProducto(Producto producto) throws RemoteException,ServicioCodefacException
    {
        //Producto productoPrincipal= buscarProductoEmpaquePrincipal(producto);
        return getFacade().buscarPresentacionesPorProductoFacade(producto);
        
    }
    
    public Producto buscarProductoEmpaquePrincipal(Producto producto) throws RemoteException,ServicioCodefacException
    {
        //Solo buscar el producto principal cuando estoy buscando desde un empaque
        if(producto.getTipoProductoEnum().equals(TipoProductoEnum.EMPAQUE))
        {
            return getFacade().buscarProductoEmpaquePrincipal(producto);
        }        
        
        
        //Si no encuentra 
        return producto;
    }
    
    public List<Producto> buscarPorCategoria(CategoriaProducto categoria) throws RemoteException,ServicioCodefacException
    {
        //Producto producto;
        //producto.getCatalogoProducto().getCategoriaProducto();
        Map<String,Object> mapParametros=new HashMap<String,Object>();
        mapParametros.put("catalogoProducto.categoriaProducto",categoria);
        mapParametros.put("estado",GeneralEnumEstado.ACTIVO.getEstado());
        //mapParametros.put("empresa",empresa);        
        List<Producto> productos=getFacade().findByMap(mapParametros);
        
        return productos;
    }
    
    public List<Producto> reporteProducto(Producto producto,Boolean pendienteActualizarPrecio) throws RemoteException,ServicioCodefacException
    {
        return  getFacade().reporteProductoFacade(producto,pendienteActualizarPrecio);
    }
    
    public List<PresentacionProducto> obtenerPresentacionesProducto(Producto producto) throws RemoteException,ServicioCodefacException
    {
        Producto productoPrincipal=buscarProductoEmpaquePrincipal(producto);
        return  getFacade().obtenerPresentacionesProductoFacade(productoPrincipal);
    }
    
    private void generarCodigoProducto(Producto producto) throws RemoteException,ServicioCodefacException
    {
        UtilidadesService utilidadService=new UtilidadesService();
        String codigoPersonalizado=UtilidadesCodigos.generarCodigoConTiempo();
        //Integer ultimoId=utilidadService.obtenerCodigoMaximoPorId(Producto.NOMBRE_TABLA,Producto.NOMBRE_CAMPO_ID);
        producto.setCodigoPersonalizado(codigoPersonalizado);
    }
      
    public Producto grabar(Producto p,Boolean generarCodigo) throws RemoteException, ServicioCodefacException
    {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                grabarSinTransaccion(p,generarCodigo,true);
            }
        });        
        
        //TODO: Metodo temporal porque cuando se ejecuta sin esta parte causa conflicto al utilizar el producto en el resto de pantallas
        //Al utilizar por segunda vez el mismo objeto por algun motivo genera un error y luego de eso ya funciona correctamente
        try {
            ejecutarTransaccion(new MetodoInterfaceTransaccion() {
                @Override
                public void transaccion() throws ServicioCodefacException, RemoteException {

                    Producto pTmp = getFacade().find(p.getIdProducto());
                    entityManager.merge(pTmp);

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return p;
    }
    
    //TODO: revisar una mejor solucion con lo de generar kardex
    public void grabarSinTransaccion(Producto p,Boolean generarCodigo,Boolean generarKardex) throws java.rmi.RemoteException,ServicioCodefacException{
        
        if(generarCodigo)
        {
            generarCodigoProducto(p);
        }
        
        p.setEstadoEnum(GeneralEnumEstado.ACTIVO);
        validarGrabarProducto(p,CrudEnum.CREAR);
        
        //Agregando datos por defecto
        p.setFechaCreacion(UtilidadesFecha.getFechaHoyTimeStamp());        
                
        CatalogoProducto catalogoProducto = p.getCatalogoProducto();
        p.setCatalogoProducto(null);
        
        TipoProducto tipoProducto=p.getTipoProducto();
        p.setTipoProducto(null);
        
        SegmentoProducto segmentoProducto=p.getSegmentoProducto();
        p.setSegmentoProducto(null);
        
        MarcaProducto marcaProducto=p.getMarcaProducto();
        p.setMarcaProducto(null);
        
        CasaComercial casaComercial=p.getCasaComercial();
        p.setCasaComercial(null);
        
        //PresentacionProducto presentacion=p.getPresentacion();
        //p.setPresentacion(null);
        
        List<ProductoProveedor> productoProveedorList=p.getProductoProveedorList();
        p.setProductoProveedorList(null);
        
        List<ProductoPresentacionDetalle> productoPresentacionList=p.getPresentacionList();
        p.setPresentacionList(null);
        
        entityManager.flush();
        entityManager.persist(p);
        entityManager.flush();
        
        //Si el catalogo producto no esta creado primero crea la entidad        
        if (catalogoProducto.getId() == null) {
            CategoriaProducto categoriaProducto = catalogoProducto.getCategoriaProducto();
            if(categoriaProducto!=null)
            {
                CategoriaProductoService categoriaProductoService=new CategoriaProductoService();
                CategoriaProducto categoriaTmp=categoriaProductoService.buscarPorId(categoriaProducto.getIdCatProducto());
                //Si no existe la categoria entonces la voy a crear
                if(categoriaTmp==null)
                {
                    categoriaProducto.setIdCatProducto(null);
                    entityManager.persist(categoriaProducto);
                    entityManager.flush();
                }
                
            }

            entityManager.persist(catalogoProducto);
            entityManager.flush();
            
            p.setCatalogoProducto(catalogoProducto);
        }
        
                
        //grabar los proveedor
        if(productoProveedorList!=null)
        {
            for (ProductoProveedor productoProveedor : productoProveedorList) 
            {
                if(productoProveedor.getId()==null)
                {
                    entityManager.persist(productoProveedor);
                }
            }
        }
        
        //Si no esta creado el tipo primero creo el tipo
        if(tipoProducto!=null)
        {   
            if(tipoProducto.getId()==null)
            {
                ServiceFactory.getFactory().getTipoProductoServiceIf().grabarSinTransaccion(tipoProducto, p.getEmpresa(),null);
                entityManager.flush();
                tipoProducto=ServiceFactory.getFactory().getTipoProductoServiceIf().buscarPorNombre(p.getEmpresa(), tipoProducto.getNombre());
            }
            
        }
        
        if(segmentoProducto!=null)
        {   
            if(segmentoProducto.getId()==null)
            {
                ServiceFactory.getFactory().getSegmentoProductoServiceIf().grabarSinTransaccion(segmentoProducto,  p.getEmpresa(), null);
                entityManager.flush();
                segmentoProducto=ServiceFactory.getFactory().getSegmentoProductoServiceIf().buscarPorNombre(p.getEmpresa(), segmentoProducto.getNombre());
            }
                        
        }
        
        if(marcaProducto!=null)
        {
            if(marcaProducto.getId()==null)
            {
                ServiceFactory.getFactory().getMarcaProductoServiceIf().grabarSinTransaccion(marcaProducto);
                entityManager.flush();
                marcaProducto=ServiceFactory.getFactory().getMarcaProductoServiceIf().buscarPorNombre(p.getEmpresa(),marcaProducto.getNombre());
            }
        
        }
        
        if(casaComercial!=null)
        {
            if(casaComercial.getId()==null)
            {
                ServiceFactory.getFactory().getCasaComercialServiceIf().grabarSinTransaccion(casaComercial);
                entityManager.flush();
                casaComercial=ServiceFactory.getFactory().getCasaComercialServiceIf().buscarPorNombre(p.getEmpresa(),casaComercial.getNombre());
            }
        
        }
        
        grabarEmpaques(p, productoPresentacionList);
        
        
        /*if(presentacion!=null)
        {
            if(presentacion.getId()==null)
            {
                ServiceFactory.getFactory().getPresentacionProductoServiceIf().grabarSinTransaccion(presentacion);
                entityManager.flush();
                presentacion=ServiceFactory.getFactory().getPresentacionProductoServiceIf().buscarPorNombre(p.getEmpresa(),presentacion.getNombre());
            }
            
            //Agregar por defecto una presentacion a la lista de detalles
            ProductoPresentacionDetalle presentacionDetalleTmp=new ProductoPresentacionDetalle();
            presentacionDetalleTmp.setCantidad(new BigDecimal(1));
            presentacionDetalleTmp.setPresentacionProducto(presentacion);
            presentacionDetalleTmp.setProductoEmpaquetado(p);
            presentacionDetalleTmp.setProductoOriginal(p);
            entityManager.persist(presentacionDetalleTmp);
            entityManager.flush();
        
        }*/
        
        
        
        
        p.setTipoProducto(tipoProducto);            
        p.setSegmentoProducto(segmentoProducto);
        p.setMarcaProducto(marcaProducto);
        p.setCasaComercial(casaComercial);
        //p.setPresentacion(presentacion);
        p.setProductoProveedorList(productoProveedorList);
        p.setPresentacionList(productoPresentacionList);
        //p.setPresentacionList(productoPresentacionList);

        //Si no son ensables remover datos para no tener incoherencias
        if (!TipoProductoEnum.EMSAMBLE.getLetra().equals(p.getTipoProductoCodigo())) {
            if (p.getDetallesEnsamble() != null) {
                p.getDetallesEnsamble().clear();
            }
        }
        
        entityManager.merge(p);
        entityManager.flush();
        
        //Crear los KARDEX CUANDO NO EXISTA
        if(generarKardex)
        {
            //if(p.getManejarInventarioEnum().equals(EnumSiNo.SI) && !p.getTipoProductoEnum().equals(TipoProductoEnum.EMPAQUE))
            if(!p.getTipoProductoEnum().equals(TipoProductoEnum.EMPAQUE))
            {
                ServiceFactory.getFactory().getKardexServiceIf().crearKardexSiNoExisteSinTransaccion(p);
            }
        }
        
        validacionPostGrabarSinTransaccion(p);
    }
    
    private void validacionPostGrabarSinTransaccion(Producto producto) throws ServicioCodefacException
    {
        for (ProductoPresentacionDetalle productoPresentacionDetalle : producto.getPresentacionList()) 
        {
            Producto productoEmpaquetado=productoPresentacionDetalle.getProductoEmpaquetado();
            
            entityManager.flush();
            if(productoPresentacionDetalle.getProductoOriginal()==null || productoPresentacionDetalle.getProductoOriginal().getIdProducto()==null)
            {
                throw new ServicioCodefacException("Error al grabar la presentación: "+productoPresentacionDetalle.getPresentacionProducto().getNombre()+", por que no tiene presentación original ");
            }
            
            
            if(productoPresentacionDetalle.getProductoEmpaquetado()==null || productoPresentacionDetalle.getProductoEmpaquetado().getIdProducto()==null)
            {
                throw new ServicioCodefacException("Error al grabar la presentación: "+productoPresentacionDetalle.getPresentacionProducto().getNombre()+", por que no tiene presentación adicional ");
            }
        }
    }
    
    private void grabarEmpaques(Producto producto,List<ProductoPresentacionDetalle> productoPresentacionList)
    {
        //grabar los detalles de  la presentacion
        if(productoPresentacionList!=null)
        {                                    
            for (ProductoPresentacionDetalle presentacionDetalle : productoPresentacionList) 
            {
                //Validacion para verificar que si tenga la referencia al nuevo producto de forma correcta
                
                if(presentacionDetalle.getId()==null || presentacionDetalle.getId()<0)
                {                    
                    try {
                        presentacionDetalle.setId(null);
                        Producto productoEmpaquetado =null;
                        if(presentacionDetalle.getTipoEnum().equals(ProductoPresentacionDetalle.TipoPresentacionEnum.ORIGINAL))
                        {
                            productoEmpaquetado=producto;
                        }
                        else
                        {
                            //Saco una copia del producto original para crear el producto empaqueteado con sus propias caracteristicas
                            productoEmpaquetado = (Producto) ServiceFactory.getFactory().getUtilidadesServiceIf().mergeEntity(presentacionDetalle.getProductoOriginal());
                            productoEmpaquetado.setIdProducto(null);
                            //Eliminar los detalles por que puede dar referencia un objecto que ya estaba previamente grabado
                            productoEmpaquetado.setDetallesEnsamble(null);
                            productoEmpaquetado.setPresentacionList(null);
                            productoEmpaquetado.setProductoProveedorList(null);
                            /*productoEmpaquetado.setTipoProducto(null);
                            productoEmpaquetado.setSegmentoProducto(null);
                            productoEmpaquetado.setCasaComercial(null);*/                        
                            productoEmpaquetado.setTipoProductoEnum(TipoProductoEnum.EMPAQUE);
                            //productoEmpaquetado.setPresentacionList(productoPresentacionList);
                                                    
                            //productoEmpaquetado.setPresentacion(presentacionDetalle.getPresentacionProducto());
                            if (presentacionDetalle.getPvpTmp() == null) 
                            {
                                productoEmpaquetado.setValorUnitario(productoEmpaquetado.getValorUnitario().multiply(presentacionDetalle.getCantidad()));
                                if(productoEmpaquetado.getPrecioDistribuidor()!=null)
                                {
                                    productoEmpaquetado.setPrecioDistribuidor(productoEmpaquetado.getPrecioDistribuidor().multiply(presentacionDetalle.getCantidad()));
                                }
                                
                                if(productoEmpaquetado.getPrecioTarjeta()!=null)
                                {
                                    productoEmpaquetado.setPrecioTarjeta(productoEmpaquetado.getPrecioTarjeta().multiply(presentacionDetalle.getCantidad()));
                                }
                                
                                if(productoEmpaquetado.getPvp4()!=null)
                                {
                                    productoEmpaquetado.setPvp4(productoEmpaquetado.getPvp4().multiply(presentacionDetalle.getCantidad()));
                                }
                                
                                if(productoEmpaquetado.getPvp5()!=null)
                                {
                                    productoEmpaquetado.setPvp5(productoEmpaquetado.getPvp5().multiply(presentacionDetalle.getCantidad()));
                                }
                                
                                if(productoEmpaquetado.getPvp6()!=null)
                                {
                                    productoEmpaquetado.setPvp6(productoEmpaquetado.getPvp6().multiply(presentacionDetalle.getCantidad()));
                                }
                                
                            } else {
                                productoEmpaquetado.setValorUnitario(presentacionDetalle.getPvpTmp());
                            }
                            
                            if(!UtilidadesTextos.verificarNullOVacio(presentacionDetalle.getCodigoTmp()))
                            {
                                productoEmpaquetado.setCodigoPersonalizado(presentacionDetalle.getCodigoTmp());
                            }

                            entityManager.persist(productoEmpaquetado);
                            entityManager.flush();                            
                        }
                        
                        
                        presentacionDetalle.setProductoEmpaquetado(productoEmpaquetado);                        
                    } catch (RemoteException ex) {
                        Logger.getLogger(ProductoService.class.getName()).log(Level.SEVERE, null, ex);
                    }
                                        
                    entityManager.persist(presentacionDetalle);
                    //entityManager.flush();
                    
                }
            }
            
            //finalmente luego que ya tengo creando los nuevos productos le enlazo el detalle por que estaba dando problemas recursivos al enlazar en el mismo proceso repetitivo
            for (ProductoPresentacionDetalle productoPresentacionDetalle : productoPresentacionList) {
                productoPresentacionDetalle.getProductoEmpaquetado().setPresentacionList(productoPresentacionList);                
            }
            
            producto.setPresentacionList(productoPresentacionList);
            //Actualizar los cambios en el producto
            entityManager.merge(producto);
            
        }

    }
    
    private void eliminarEmpaques(Producto producto,List<ProductoPresentacionDetalle> productoPresentacionList) throws ServicioCodefacException, RemoteException
    {
        //Detalle Original
        List<ProductoPresentacionDetalle> detallesOriginales= ServiceFactory.getFactory().getProductoPresentacionDetalleServiceIf().buscarPorProducto(producto);
        
        for (ProductoPresentacionDetalle detalle : detallesOriginales) 
        {
            //Si no encuentra en la lista la presentación significa que fue eliminado
            if(!productoPresentacionList.contains(detalle))
            {
                detalle= entityManager.merge(detalle);
                //Elimino el dato que ya no voy a ocupar
                entityManager.remove(detalle);
            }
        }
        
    }
    
    /*private void validarEdicionCodigoPrincipal(Producto producto) throws ServicioCodefacException, RemoteException
    {
        if(ParametroUtilidades.comparar(producto.getEmpresa(),ParametroCodefac.PERMITIR_EDITAR_CODIGO,EnumSiNo.NO))
        {
            //Verificar que no edite el código principal del producto        
            Producto productoOriginal = ServiceFactory.getFactory().getProductoServiceIf().buscarProductoActivoPorCodigo(producto.getCodigoPersonalizado(), producto.getEmpresa());
            if (productoOriginal == null) {
                throw new ServicioCodefacException("No se puede editar el código principal");
            }
        }
        
    }*/
    
    private void validarGrabarProducto(Producto p,CrudEnum estadoEnum) throws java.rmi.RemoteException,ServicioCodefacException    
    {
        
        if(p.getCodigoPersonalizado()==null || p.getCodigoPersonalizado().trim().isEmpty())
        {
            throw new ServicioCodefacException("El Código principal del producto no puede estar vacio ");
        }
        
        if(p.getCodigoPersonalizado().length()>Producto.TAMANIO_MAX_CODIGO)
        {
            throw new ServicioCodefacException("El código debe tener un tamaño maximo de "+Producto.TAMANIO_MAX_CODIGO+" caracteres ");
        }
        
        //TODO: Analizar porque el Sri supuestamente si deja mandar productos con valor 0 , por el momento solo pongo los menores que 0
        if(p.getValorUnitario().compareTo(BigDecimal.ZERO)<0)
        {
            throw new ServicioCodefacException("El valor unitario del producto no puede ser menor que cero");
        }
                
        if(p.getCatalogoProducto()==null)
        {
            throw new ServicioCodefacException("No se puede grabar el producto sin un catalago de producto");
        }        
        
        if(p.getCatalogoProducto().getIva()==null)
        {
            throw new ServicioCodefacException("No se puede grabar el producto sin especificar un porcentaje de Iva");
        }
        
        //TODO: Esta validacion por el momento queda comentada por que siempre se esta grabando con ese dato en No
        //Validar que cuando requiera imprimir el código tenga un formato correcto
        //if(p.getGenerarCodigoBarrasEnum().equals(EnumSiNo.SI))
        //{
            //TODO: Validar para caracteres generales 
            //Link: https://es.wikipedia.org/wiki/Code_39
            //if(!UtilidadesExpresionesRegulares.validar(p.getCodigoPersonalizado(),ExpresionRegular.soloNumeros))
            //{
            //    throw new ServicioCodefacException("El Código solo puede ser numérico cuando se selecciona la opción de generar código de barras");
            //}
        //}
        
        
        /**
         * TODO: Ver si este tipo de validacion se puede convertir en algo estandar para todos lo servicios
         * =================================================================
         *          VALIDAR QUE NO EXITAN PRODUCTOS DUPLICADOS
         * =================================================================
         */
        /*if(estadoEnum.equals(CrudEnum.EDITAR))
        {
            //validarEdicionCodigoPrincipal(p);
            
            Producto productoTmp=getFacade().find(p.getIdProducto());
            if(productoTmp!=null && p!=null && productoTmp.getCodigoPersonalizado()!=null && p.getCodigoPersonalizado()!=null)
            {
                if(productoTmp.getCodigoPersonalizado().equals(p.getCodigoPersonalizado()))
                {
                    return ;
                }
            }
        }*/
        
        //Terminar de hacer esta parte pero tomar en cuenta que luego cuando traigan productos de importacion me esta generando problemas
        //Producto productoDuplicado=this.buscarProductoActivoPorCodigo(p.getCodigoPersonalizado(),p.getEmpresa());

        /*if (productoDuplicado!=null) 
        {
            if(!p.getIdProducto().equals(productoDuplicado.getIdProducto()))
            {
                System.out.println("Producto con problemas: "+productoDuplicado);
                System.out.println("Producto con problemas: "+productoDuplicado.getIdProducto());
                System.out.println("Producto con problemas: "+productoDuplicado.getNombre());
                throw new ServicioCodefacException("Ya existe un producto ingresado con el mismo código principal");
            }
        }*/
        
        //Verificar que no ingresen presentaciones duplicadas y que al menos tenga una presentacion principal
        if(p.getPresentacionList()!=null && p.getPresentacionList().size()>0)
        {
            //Buscar datos duplicados
            //Set<ProductoPresentacionDetalle> repetidoList= UtilidadesLista.buscarDuplicados(p.getPresentacionList());
            for (ProductoPresentacionDetalle detalle: p.getPresentacionList()) 
            {
                Boolean detalleRepetido=null;
                for (ProductoPresentacionDetalle detalleTmp : p.getPresentacionList()) 
                {
                    if(detalle.getPresentacionProducto()==null)
                    {
                        continue;
                    }
                    
                    if(detalle.getPresentacionProducto().equals(detalleTmp.getPresentacionProducto()))
                    {                        
                        //El primer dato que encuntra no lo toma en cuenta por que siempre va a tener un dato
                        if(detalleRepetido==null)
                        {
                            detalleRepetido=false;
                        }
                        else
                        {
                            // si encuentra un producto repetido por segunda vez ya no es permitido
                            throw new ServicioCodefacException("La presentación "+detalle.getPresentacionProducto().getNombre()+" esta repetida y no se puede grabar");
                        }
                    }
                }
            
            }
            
            
            Boolean tienePresentacionPrincipal=false;
            for (ProductoPresentacionDetalle detalle: p.getPresentacionList()) 
            {
                if(detalle!=null && detalle.getTipoEnum().equals(ProductoPresentacionDetalle.TipoPresentacionEnum.ORIGINAL))
                {
                    tienePresentacionPrincipal=true;
                }
            }
            
            if(!tienePresentacionPrincipal)
            {
                throw new ServicioCodefacException("No se puede grabar el producto sin agregar una presentación original");
            }
            
        }
        
        //VALIDACIONES ADICIONALES que requieren MAYOR POTENCIAL DE PROCESAMIENTO
        //Validar que no existe un dato repedito al crear o al editar
        validarDatoRepetido(p,estadoEnum,new ValidarDatoRepetidoIf() {
            @Override
            public Object verificarDatoRepetido() throws ServicioCodefacException, RemoteException {
                //Si es empaque no hago esa validacion por que esos datos son copias del principal
                //TODO: toca revisar esa logica por que sideberia validar por que si modifica otro codigo conocido puede crear conflicto
                if(!p.getTipoProductoEnum().equals(TipoProductoEnum.EMPAQUE))
                {
                    return buscarProductoActivoPorCodigo(p.getCodigoPersonalizado(),p.getEmpresa());
                }
                return null;
            }
        });
        
        //Validar que se puede modificar o no el codigo del mismo datos
        validarEdicionCampo(p.getEmpresa(), estadoEnum, new ValidarEdicionCampoIf<Producto>() {
            @Override
            public Object getId() {
                return p.getIdProducto();
            }

            @Override
            public Boolean compararCampos(Producto dato) {
                return p.getCodigoPersonalizado().equals(dato.getCodigoPersonalizado());
            }
        });
        
        

        ///////////////////////////////////////////////////////////////////////
        ///             HACER UNAS CORRECIONES ANTES DE GRABAR
        ///////////////////////////////////////////////////////////////////////
        //Quitar espacios en blanco
        p.setNombre(p.getNombre().trim());
        
        //Corregir datos cuando no tengan seteados para poner por defecto
        if(p.getDisponibleVentaEnum()==null)
        {
            p.setDisponibleVentaEnum(EnumSiNo.SI);
        }
        
        if(p.getDisponibleCompraEnum()==null)
        {
            p.setDisponibleCompraEnum(EnumSiNo.SI);
        }
        
        //TODO: Setear los valores por defecto
        if(p.getDetallesEnsamble()!=null)
        {
            for (ProductoEnsamble productoEnsamble : p.getDetallesEnsamble()) 
            {
                if(productoEnsamble.getId()!=null && productoEnsamble.getId()<0)
                {
                    productoEnsamble.setId(null);
                }
            }
        }
        
    }
    
    public void editarProducto(Producto producto) throws java.rmi.RemoteException,ServicioCodefacException
    {
        validarGrabarProducto(producto,CrudEnum.EDITAR);
        producto.setFechaUltimaEdicion(UtilidadesFecha.getFechaHoyTimeStamp());
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                
                //Buscar componentes ensambles eliminados
                List<ProductoEnsamble> productoEnsambleEliminar=productoEnsamblesEliminados(producto);
                
                //TODO: Por el momento ELIMINO directamente de la base de datos pero se deberia manejar por ESTADOS
                for (ProductoEnsamble productoEnsamble : productoEnsambleEliminar) 
                {
                    ProductoEnsamble productoEnsambleTmp=entityManager.merge(productoEnsamble);
                    entityManager.remove(productoEnsambleTmp);
                }
                
                
                //TODO: Por el momento copio todo el codigo para crear las presentaciones pero se deberia utilizar el mismo metodo de grabar
                //CAMBIAR urge
               // PresentacionProducto presentacion=producto.getPresentacion();
                //producto.setPresentacion(null);

                List<ProductoPresentacionDetalle> productoPresentacionList = producto.getPresentacionList();
                producto.setPresentacionList(null);
                grabarEmpaques(producto, productoPresentacionList);
                eliminarEmpaques(producto, productoPresentacionList);
                //producto.setPresentacion(presentacion);
                producto.setPresentacionList(productoPresentacionList);

                entityManager.merge(producto.getCatalogoProducto());
                entityManager.merge(producto);

                //Crear los KARDEX CUANDO NO EXISTA
                if (producto.getManejarInventarioEnum().equals(EnumSiNo.SI)) {
                    ServiceFactory.getFactory().getKardexServiceIf().crearKardexSiNoExisteSinTransaccion(producto);
                }

            }
        });
        
        //TODO: Metodo temporal porque cuando se ejecuta sin esta parte causa conflicto al utilizar el producto en el resto de pantallas
        //Al utilizar por segunda vez el mismo objeto por algun motivo genera un error y luego de eso ya funciona correctamente
        try {
            ejecutarTransaccion(new MetodoInterfaceTransaccion() {
                @Override
                public void transaccion() throws ServicioCodefacException, RemoteException {

                    Producto pTmp = getFacade().find(producto.getIdProducto());
                    List<Kardex> kardexList=ServiceFactory.getFactory().getKardexServiceIf().buscarPorProducto(pTmp);
                    for (Kardex kardex : kardexList) {
                        entityManager.merge(kardex);
                    }
                    
                    entityManager.merge(pTmp);

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    private List<ProductoEnsamble> productoEnsamblesEliminados(Producto producto) throws ServicioCodefacException, RemoteException
    {
        if(producto.getTipoProductoEnum().equals(TipoProductoEnum.EMSAMBLE))
        {
            List<ProductoEnsamble> productosActualesList= producto.getDetallesEnsamble();
            List<ProductoEnsamble> productoOriginalList= ServiceFactory.getFactory().getProductoEnsambleServiceIf().buscarPorProducto(producto);
            return UtilidadesLista.restarListas(productoOriginalList, productosActualesList);
        }
        else
        {
            return new ArrayList<ProductoEnsamble>();
        }
    }
    
    public void eliminarProducto(Producto p,ModoProcesarEnum modoProcesar) throws ServicioCodefacException, RemoteException
    {
        
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {

                //Hacer validaciones mas complicadas si sale que el producto maneja inventario
                if(p.getManejarInventarioEnum().equals(EnumSiNo.SI))
                {
                    //Si el producto no maneja inventario lo puede eliminar directamente
                    KardexService kardexService = new KardexService();
                    List<Kardex> resultadoKardex = kardexService.buscarPorProducto(p, GeneralEnumEstado.ACTIVO);
                    List<String> stockPositivoBodega = new ArrayList<String>();

                    for (Kardex kardex : resultadoKardex) {

                        if(kardex.getEstadoEnum().equals(GeneralEnumEstado.ACTIVO))
                        {                        
                            //if (kardex.getStock() > 0) 
                            if (kardex.getStock().compareTo(BigDecimal.ZERO)==0) 
                            {
                                //Si los kardex no tiene problema y estan con saldos en 0 los elimino y si no se cumple con todos no importa porque se realiza un rollback
                                kardex.setEstadoEnum(GeneralEnumEstado.ELIMINADO);
                                entityManager.merge(kardex);

                            } else 
                            {
                                System.out.println(kardex.getStock());
                                Logger.getLogger(ServiceAbstract.class.getName()).log(Level.WARNING, null, "Producto no se puede borrar por que tiene stock "+kardex.getStock()+" en producto "+kardex.getProducto().getNombre() );
                                //Agrego a la lista la bodega con el kardex que tiene problema antes de eliminar
                                stockPositivoBodega.add(kardex.getBodega().getNombre());                    
                            }
                        }
                    }

                    //Solo procesar esta comprobacion cuando sea en modo normal
                    if(modoProcesar.equals(ModoProcesarEnum.NORMAL))
                    {
                        if (stockPositivoBodega.size() > 0) {
                            throw new ServicioCodefacException("No se puede eliminar el producto porque tiene stock en las bodegas: " + UtilidadesLista.castListToString(stockPositivoBodega, ","));
                        }
                    }
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
        
        /*Map<String,Object> mapParametros=new HashMap<String,Object>();        
        mapParametros.put("codigoPersonalizado",codigo);
        mapParametros.put("estado",GeneralEnumEstado.ACTIVO.getEstado());        
        
        //Cuando este configurado como datos compartidos no tomo en cuenta de donde esta cogiendo la empresa
        if(!ParametroUtilidades.comparar(empresa,ParametroCodefac.DATOS_COMPARTIDOS_EMPRESA,EnumSiNo.SI))
        {
            mapParametros.put("empresa",empresa);        
        }        
        
        
        
        List<Producto> productos=getFacade().findByMap(mapParametros);
        if(productos.size()>0)
        {
            return productos.get(0);
        }*/
        return getFacade().buscarProductoActivoPorCodigoFacade(codigo, empresa);
        
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
    
    public void actualizarPrecios(List<ProductoPrecioDataTable> productos ) throws RemoteException, ServicioCodefacException
    {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                
                for (ProductoPrecioDataTable productoData : productos) 
                {
                    //Solo calcular el precio de venta si tiene un costo mayor que cero
                    /*if(productoData.costoCalculo.compareTo(BigDecimal.ZERO)>0)
                    {
                        Producto producto= productoData.producto;        
                        
                        if(productoData.porcentajePvp1!=null && productoData.porcentajePvp1.compareTo(BigDecimal.ZERO)>=0)
                        {
                            producto.setValorUnitario(productoData.calcularPvp1());
                        }
                        
                        if(productoData.porcentajePvp2!=null && productoData.porcentajePvp2.compareTo(BigDecimal.ZERO)>=0)
                        {
                            producto.setPrecioDistribuidor(productoData.calcularPvp2());
                        }
                        
                        if(productoData.porcentajePvp3!=null && productoData.porcentajePvp3.compareTo(BigDecimal.ZERO)>=0)
                        {
                            producto.setPrecioTarjeta(productoData.calcularPvp3());                            
                        }
                        
                        if(productoData.porcentajePvp4!=null && productoData.porcentajePvp4.compareTo(BigDecimal.ZERO)>=0)
                        {
                            producto.setPvp4(productoData.calcularPvp4());
                        }
                        
                        if(productoData.porcentajePvp5!=null && productoData.porcentajePvp5.compareTo(BigDecimal.ZERO)>=0)
                        {
                            producto.setPvp5(productoData.calcularPvp5());
                        }
                        
                        if(productoData.porcentajePvp6!=null && productoData.porcentajePvp6.compareTo(BigDecimal.ZERO)>=0)
                        {
                            producto.setPvp6(productoData.calcularPvp6());
                        }*/
                    
                        Producto producto= productoData.producto;        
                        producto.setValorUnitario(productoData.pvp1);
                        producto.setPrecioDistribuidor(productoData.pvp2);
                        producto.setPrecioTarjeta(productoData.pvp3);
                        producto.setPvp4(productoData.pvp4);
                        producto.setPvp5(productoData.pvp5);
                        producto.setPvp6(productoData.pvp6);
                        
                        producto.setActualizarPrecioEnum(EnumSiNo.NO);
                        entityManager.merge(producto);
                    //}
                    
                }
            }
        });
    }   
    
    public Producto crearProductoPorDefectoSinTransaccion(Empresa empresa,Integer ivaDefecto) throws RemoteException, ServicioCodefacException
    {
        Producto producto=new Producto();
        producto.setCodigoPersonalizado("001");
        producto.setNombre("libre");
        producto.setValorUnitario(new BigDecimal("1"));
        producto.setManejarInventarioEnum(EnumSiNo.NO);
        producto.setGenerarCodigoBarrasEnum(EnumSiNo.NO);
        producto.setTipoProductoEnum(TipoProductoEnum.PRODUCTO);
        producto.setEmpresa(empresa);
        CatalogoProducto catalogoProducto=crearCatalogoProductoDefectoSinTransaccion(empresa,producto.getNombre(),ivaDefecto);
        producto.setCatalogoProducto(catalogoProducto);
        return producto;        
    }
    
    private CatalogoProducto crearCatalogoProductoDefectoSinTransaccion(Empresa empresa,String nombre,Integer ivaDefecto) throws RemoteException, ServicioCodefacException
    {
        CatalogoProducto catalogoProducto=new CatalogoProducto();
        
        //TODO: Unir codigo
       // CategoriaProducto categoriaProducto=categoriaSeleccionada;
        //catalogoProducto.setCategoriaProducto(categoriaProducto);
        
        /*if(this.getIceSeleccionado()!=null && !this.getIceSeleccionado().getClass().equals(String.class))
        {
            ImpuestoDetalle ice= this.getIceSeleccionado();
            catalogoProducto.setIce(ice);
        }
        
        if( this.getIrbpnrSeleccionado()!=null && !this.getIrbpnrSeleccionado().getClass().equals(String.class))
        {
            ImpuestoDetalle ibpnr=this.getIrbpnrSeleccionado();
            catalogoProducto.setIrbpnr(ibpnr);
        }*/
        //ParametroCodefacService parametroService=new ParametroCodefacService();
        //TODO: Ver si para temnas recurentes creo metodos especificos para que devuelvan el valor convertido
        //ParametroCodefac parametroIvaDefecto=parametroService.getParametroByNombre(ParametroCodefac.IVA_DEFECTO, empresa);
        
        ImpuestoDetalleService ivaImpuestoService=new ImpuestoDetalleService();
        ImpuestoDetalle impuestoDetalle=ivaImpuestoService.buscarPorTarifa(ivaDefecto);
       
        catalogoProducto.setIva(impuestoDetalle);
        
        catalogoProducto.setNombre(nombre);
        
        TipoProductoEnum tipoProductoEnum=TipoProductoEnum.PRODUCTO;
        catalogoProducto.setModuloCod(ModuloCodefacEnum.INVENTARIO.getCodigo());
        return catalogoProducto;
    }
    
    public void grabarConInventario(Producto p,KardexDetalle kardexDetalle) throws ServicioCodefacException,java.rmi.RemoteException
    {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                
                //Solo grabo el producto cuando no esta creado previamente
                //System.out.println("Costo: "+kardexDetalle.getKardex().getCostoPromedio());
                if(p.getIdProducto()==null)
                {
                    grabarSinTransaccion(p,false,false); //graba el producto                
                }
                else //Si ya tiene grabado el producto solo actualizo los datos
                {
                    //TODO: Ver si puedo utilizar el metodo de actualizar para evitar alguna inconsistencia
                    entityManager.merge(p);
                }
                                
                
                //Solo grabar cuando existen datos diferente de null
                if(kardexDetalle!=null)
                {
                    KardexService kardexService=new KardexService();
                    kardexService.grabarKardexDetallSinTransaccion(kardexDetalle,kardexDetalle.getKardex().getLote(),true);
                }
                
            }
        });
    }
    
    public List<Producto> buscarPorCategoria(Empresa empresa, CategoriaProducto categoria)
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
    
    public List<Producto> buscarProductoActivo(Producto producto,Empresa empresa) throws ServicioCodefacException, RemoteException
    {
        Map<String,Object> mapParametros = new HashMap<String,Object>();        
        mapParametros.put("producto",producto);
        mapParametros.put("empresa",empresa);        
        mapParametros.put("estado",GeneralEnumEstado.ACTIVO.getEstado());        
        
        List<Producto> productos = getFacade().findByMap(mapParametros);
        if(productos.size()>0)
        {
            return productos;
        }
        return null;
    }
    
    public List<TopProductoRespuesta> topProductosMasVendidosService() throws ServicioCodefacException, RemoteException
    {
        return getFacade().topProductosMasVendidosFacade();
    }
    
    
    public String actualizarProductoExportados(ProductoExportar productoExportar,Empresa empresa) throws  ServicioCodefacException, RemoteException
    {        
        Integer cantidadActualizada=0;
        Integer cantidadGrabado=0;
        for (Producto productoImportar : productoExportar.getProductoList()) 
        {
            try 
            {
                if(productoImportar.getNombre().indexOf("euroesmalte")>=0)
                {
                    System.out.println("revisar");
                }
                Logger.getLogger(ProductoService.class.getName()).log(Level.SEVERE,"Importando: "+productoImportar.getNombre());
                Producto productoBuscado=buscarProductoActivoPorCodigo(productoImportar.getCodigoPersonalizado(), empresa);
                //Si encuentra el producto lo que tengo que hacer es actualiar en el sistema
                if(productoBuscado!=null)
                {
                    editarProducto(productoImportar);
                    cantidadActualizada++;
                }
                else
                {
                    //Si no existe el producto lo que tenemos que hacer es grabar el nuevo producto
                    productoImportar.setIdProducto(null);
                    productoImportar.getCatalogoProducto().setId(null);
                    grabar(productoImportar, Boolean.FALSE);
                    cantidadGrabado++;
                }            
            }   
            catch(ServicioCodefacException ser)
            {                
                Logger.getLogger(ProductoService.class.getName()).log(Level.SEVERE,"Importando con problemas: "+productoImportar.getNombre());
                ser.printStackTrace();
            }
        }
        return "Proceso Terminando:\nActualizados: "+cantidadActualizada+"\nGrabados: "+cantidadGrabado;
    }

}
