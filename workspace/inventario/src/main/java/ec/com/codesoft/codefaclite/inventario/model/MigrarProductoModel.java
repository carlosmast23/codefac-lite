/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.inventario.model;

import ec.com.codesoft.codefaclite.controlador.excel.ExcelMigrar;
import ec.com.codesoft.codefaclite.controlador.excel.entidades.ExcelMigrarProductos;
import ec.com.codesoft.codefaclite.controlador.model.MigrarModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Bodega;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CategoriaProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ImpuestoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Kardex;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.KardexDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoProductoEnum;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import java.io.InputStream;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class MigrarProductoModel extends MigrarModel {

    private static final Logger LOG = Logger.getLogger(MigrarProductoModel.class.getName());
    

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        super.iniciar(); //To change body of generated methods, choose Tools | Templates.
        setTitle("Migrar Productos");
    }
    
    private void validarProducto(Producto producto) throws ExcelMigrar.ExcepcionExcel
    {
        if(producto.getCodigoPersonalizado()==null || producto.getCodigoPersonalizado().trim().isEmpty())
        {
            throw new ExcelMigrar.ExcepcionExcel("El código no puede ser vacio");
        }
        
        if(producto.getCodigoPersonalizado().length()>Producto.TAMANIO_MAX_CODIGO)
        {
            throw new ExcelMigrar.ExcepcionExcel("El código debe tener un tamaño maximo de "+Producto.TAMANIO_MAX_CODIGO+" caracteres ");
        }
        
        //throw new ExcelMigrar.ExcepcionExcel("La bodega no existe");
    }

    //TODO: Revisar que cuando migro nuevamente se estan creando varios kardex repetidos
    @Deprecated
    @Override
    public ExcelMigrar.MigrarInterface getInterfaceMigrar() {
        return new ExcelMigrar.MigrarInterface() {
            @Override
            public void procesar(ExcelMigrar.FilaResultado fila) throws ExcelMigrar.ExcepcionExcel, ExcelMigrar.ExcepcionExcelRegistroDuplicado {
                try {
                    
                    Producto producto = new Producto();
                    producto.setCodigoPersonalizado(((String) fila.getByEnum(ExcelMigrarProductos.Enum.CODIGO).valor).trim());
                    producto.setNombre(((String) fila.getByEnum(ExcelMigrarProductos.Enum.NOMBRE).valor).trim());
                    System.out.println(producto.getNombre());         
                    
                    //Obtener datos iniciales del inventario que necesito
                    String manejaInventario=(String) fila.getByEnum(ExcelMigrarProductos.Enum.MANEJA_INVENTARIO).valor;
                    EnumSiNo manejaInventarioEnumSiNo=EnumSiNo.getEnumByLetra(manejaInventario.substring(0,1));
                    
                    Double stockMinimo=0d;
                    
                    KardexDetalle kardexDetalle=null; //Referencia para poder grabar cuando se va a ingresar el inventario
                    
                    //TODO: Validar para desde el inicio separar en 2 flujo de crear y actualizar
                    Producto productoTmp =ServiceFactory.getFactory().getProductoServiceIf().buscarProductoActivoPorCodigo(producto.getCodigoPersonalizado(),session.getEmpresa());
                    //Producto productoTmp = ServiceFactory.getFactory().getProductoServiceIf().buscarPorNombreyEstado(producto.getNombre(), GeneralEnumEstado.ACTIVO, session.getEmpresa());
                    
                    //Si no existe el producto grabado ingreso todos los demas campos
                    if(productoTmp==null)
                    {
                    
                        Double precioVentaPublico = (Double) fila.getByEnum(ExcelMigrarProductos.Enum.PRECIO_VENTA_PUBLICO).valor;
                        validarProducto(producto);

                        /**
                         * ==========> BUSCAR O CREAR LA CATEGORIA SI NO EXISTE PARA CREAR <=======
                         */
                        String nombreCategoria=((String) fila.getByEnum(ExcelMigrarProductos.Enum.CATEGORIA).valor).trim();

                        CategoriaProducto categoriaProducto=ServiceFactory.getFactory().getCategoriaProductoServiceIf().buscarPorNombre(session.getEmpresa(),nombreCategoria); //TODO: Revisar este tema porque segur que toca manejar las categorias por empresa
                        if(categoriaProducto==null)
                        {
                            categoriaProducto=new CategoriaProducto();
                            categoriaProducto.setNombre(nombreCategoria);
                            categoriaProducto.setDescripcion(nombreCategoria);
                            categoriaProducto.setEstadoEnum(GeneralEnumEstado.ACTIVO);
                            categoriaProducto.setEmpresa(session.getEmpresa());
                        }

                        /**
                         * ============> CREAR EL CATALOGO PRODUCTO <=====================
                         */
                        Object porcentajeIvaObj=fila.getByEnum(ExcelMigrarProductos.Enum.IVA_PORCENTAJE).valor;
                        Double porcentajeIva = (Double)Double.parseDouble(porcentajeIvaObj.toString());
                        ImpuestoDetalle impuestoDetalleIva = ServiceFactory.getFactory().getImpuestoDetalleServiceIf().buscarPorTarifa(porcentajeIva.intValue());
                        CatalogoProducto catalogoProducto = new CatalogoProducto();
                        catalogoProducto.setEstadoEnum(GeneralEnumEstado.ACTIVO);
                        catalogoProducto.setIva(impuestoDetalleIva);
                        catalogoProducto.setNombre(producto.getNombre());
                        catalogoProducto.setModuloCodEnum(ModuloCodefacEnum.INVENTARIO);
                        catalogoProducto.setCategoriaProducto(categoriaProducto);

                        //catalogoProducto.setTipoCodEnum(null); Este campo no me parece que sirve para cuando se crea para inventario
                        //Setear el catalogo del producto con el producto
                        producto.setCatalogoProducto(catalogoProducto);
                        /**
                         * ===================================================================
                         *             CREA DATOS ADICIONALES EN EL INVENTARIO
                         * ===================================================================
                         */                        
                        String marca=(String) fila.getByEnum(ExcelMigrarProductos.Enum.MARCA).valor;
                        
                        try
                        {
                            stockMinimo=(Double) fila.getByEnum(ExcelMigrarProductos.Enum.STOCK_MINIMO).valor;
                        }
                        catch(ClassCastException e)
                        {
                            
                        }

                                                
                        kardexDetalle=generarMovimientoInventario(manejaInventarioEnumSiNo, fila, producto,productoTmp);



                        producto.setValorUnitario(new BigDecimal(precioVentaPublico.toString()));

                        Object precioVentaOfertaObj=fila.getByEnum(ExcelMigrarProductos.Enum.PRECIO_VENTA_OFERTA).valor;
                        if(precioVentaOfertaObj!=null && !precioVentaOfertaObj.toString().isEmpty())
                        {
                            Double precioVentaOferta = (Double) precioVentaOfertaObj;
                            if(precioVentaOferta>0)
                            {
                                producto.setPrecioDistribuidor(new BigDecimal(precioVentaOferta.toString()));
                            }
                        }
                                            ///PVP4
                        Object PVP3Obj=fila.getByEnum(ExcelMigrarProductos.Enum.PRECIO_VENTA_PROMEDIO).valor;
                        if(PVP3Obj!=null && !PVP3Obj.toString().isEmpty())
                        {
                            Double PVP3 = (Double) PVP3Obj;
                            if(PVP3>0)
                            {
                                producto.setPrecioTarjeta(new BigDecimal(PVP3.toString()));
                            }
                        }


                        ///PVP4
                        Object PVP4Obj=fila.getByEnum(ExcelMigrarProductos.Enum.PRECIO_VENTA_4).valor;
                        if(PVP4Obj!=null && !PVP4Obj.toString().isEmpty())
                        {
                            Double PVP4 = (Double) PVP4Obj;
                            if(PVP4>0)
                            {
                                producto.setPvp4(new BigDecimal(PVP4.toString()));
                            }
                        }

                        ///PVP5
                        Object PVP5Obj=fila.getByEnum(ExcelMigrarProductos.Enum.PRECIO_VENTA_5).valor;
                        if(PVP5Obj!=null && !PVP5Obj.toString().isEmpty())
                        {
                            Double PVP5 = (Double) PVP5Obj;
                            if(PVP5>0)
                            {
                                producto.setPvp5(new BigDecimal(PVP5.toString()));
                            }
                        }

                        ///PVP6
                        Object PVP6Obj=fila.getByEnum(ExcelMigrarProductos.Enum.PRECIO_VENTA_6).valor;
                        if(PVP6Obj!=null && !PVP6Obj.toString().isEmpty())
                        {
                            Double PVP6 = (Double) PVP6Obj;
                            if(PVP6>0)
                            {
                                producto.setPvp6(new BigDecimal(PVP6.toString()));
                            }
                        }



                        producto.setEstadoEnum(GeneralEnumEstado.ACTIVO);
                    }
                    else
                    {
                        kardexDetalle=generarMovimientoInventario(manejaInventarioEnumSiNo, fila, producto,productoTmp);
                    }
                    //producto.setCatalogoProducto(CatalogoPro);

                    ///========================> VALIDAR QUE NO EXISTA UN PRODUCTO SIMILAR YA INGRESADO POR CODIGO <======================//
                    //TODO: Esta parte esta de tener muy en cuenta porque aveces como se genera un codigo esta parte puede ser dificil de encontrar por el codigo y mas facil por el nombre
                    //Producto productoTmp =ServiceFactory.getFactory().getProductoServiceIf().buscarProductoActivoPorCodigo(producto.getCodigoPersonalizado(),session.getEmpresa());
                    //TODO: Esta de tener presente esta parte cuando ya existe un producto no debe crear otro kardex solo debe actualizar al mismo producto
                    //TODO: Mejorar esta parte
                    
                    if(productoTmp != null) 
                    {
                        //TODO:Unificar la migracion con el dato d abajo
                        productoTmp.setUbicacion((String) fila.getByEnum(ExcelMigrarProductos.Enum.UBICACION).valor);
                        Logger.getLogger(MigrarProductoModel.class.getName()).log(Level.WARNING,"Producto actualizado : "+productoTmp.getNombre());
                        //Si maneja inventario y el producto existe entonces solo consulto el producto anterior
                        if(manejaInventarioEnumSiNo.equals(EnumSiNo.SI))
                        {
                            producto=productoTmp;
                            if(kardexDetalle!=null)
                            {
                                kardexDetalle.getKardex().setProducto(producto);
                            }
                        }
                        else
                        {
                            throw new ExcelMigrar.ExcepcionExcelRegistroDuplicado("El dato ya se encuentra registrado en el sistema");
                        }
                    }
                    else
                    {
                        /**
                         * =====================================================
                         *          SI NO TIENE CREADO PREVIAMENTE EL PRODUCTO CREO LOS DATOS
                         * =====================================================
                         */
                        producto.setCantidadMinima(stockMinimo.intValue());
                        producto.setStockInicial(0l);
                        //Todo Migrar la marca queda temporalmente descativada por que ahora es una tabla aparte
                        //producto.setMarca(marca);
                        //producto.setPrecioDistribuidor(BigDecimal.ZERO);
                        //producto.setPrecioTarjeta(BigDecimal.ZERO);
                        producto.setGarantia(EnumSiNo.NO.getLetra());
                        producto.setTipoProductoCodigo(TipoProductoEnum.PRODUCTO.getLetra());
                        producto.setManejarInventarioEnum(manejaInventarioEnumSiNo); //TODO:Cambiar para setear un enum
                        producto.setEmpresa(session.getEmpresa());
                        producto.setUbicacion((String) fila.getByEnum(ExcelMigrarProductos.Enum.UBICACION).valor);
                    }

                    ServiceFactory.getFactory().getProductoServiceIf().grabarConInventario(producto,kardexDetalle);
                    LOG.log(Level.INFO,"Migrado producto "+producto.getNombre());

                } catch (RemoteException ex) {
                    Logger.getLogger(MigrarProductoModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ServicioCodefacException ex) {
                    Logger.getLogger(MigrarProductoModel.class.getName()).log(Level.SEVERE, null, ex);
                    throw new ExcelMigrar.ExcepcionExcel(ex.getMessage());
                } catch(NumberFormatException ex)
                {
                    Logger.getLogger(MigrarProductoModel.class.getName()).log(Level.SEVERE, null, ex);
                    throw new ExcelMigrar.ExcepcionExcel("Error ingresando letras en un campo de números en el producto: "+fila.getByEnum(ExcelMigrarProductos.Enum.NOMBRE).valor);
                }
                catch(ClassCastException ex)
                {
                    Logger.getLogger(MigrarProductoModel.class.getName()).log(Level.SEVERE, null, ex);
                    throw new ExcelMigrar.ExcepcionExcel("Error de conversión con el campo: "+ExcelMigrar.LOG_ULTIMO_CAMPO_LEIDO+"\nDescripción:"+ex.getMessage());
                }
                catch(Exception ex)
                {
                    Logger.getLogger(MigrarProductoModel.class.getName()).log(Level.SEVERE, null, ex);
                    throw new ExcelMigrar.ExcepcionExcel(ex.getMessage());
                }

            }
        };
    }
    
    //todo: Mejorar esa parte para no mandar el producto Tmp
    private KardexDetalle generarMovimientoInventario(EnumSiNo manejaInventarioEnumSiNo,ExcelMigrar.FilaResultado fila,Producto producto,Producto productoTmp) throws ServicioCodefacException, RemoteException, ExcelMigrar.ExcepcionExcel
    {
        KardexDetalle kardexDetalle=null;
        if (manejaInventarioEnumSiNo != null && manejaInventarioEnumSiNo.equals(EnumSiNo.SI)) // Si cumple esta condicion vamos a grabar el resto de datos para el inventario
        {
            String bodegaNombre = (String) fila.getByEnum(ExcelMigrarProductos.Enum.BODEGA).valor;
            Bodega bodega = ServiceFactory.getFactory().getBodegaServiceIf().buscarPorNombre(bodegaNombre);
            if (bodega != null) //Si la bodega existe consulto cuanto quiere agregar al stock
            {
                Double stock = 0d;
                ExcelMigrar.CampoResultado campoResultado = fila.getByEnum(ExcelMigrarProductos.Enum.STOCK);
                if (campoResultado != null) {
                    stock = (Double) campoResultado.valor;
                }
                
                //Crear siempre un kardex aunque no tenga movimientos
                Kardex kardex = null;
                if (productoTmp == null) {
                    kardex = new Kardex();
                    kardex.setBodega(bodega);
                    //Obtener costo de la plantilla
                    Double costo = (Double) obtenerDatoPlantilla(fila, ExcelMigrarProductos.Enum.COSTO);
                    if (costo != null) {
                        kardex.setCostoPromedio(new BigDecimal(costo + ""));
                    }
                }
  
                

                //if (stock > 0) {
                    kardexDetalle = new KardexDetalle();
                    //kardexDetalle.setCantidad(stock.intValue());
                    kardexDetalle.setCantidad(new BigDecimal(stock));
                    kardexDetalle.setPrecioUnitario(BigDecimal.ZERO);
                    kardexDetalle.recalcularTotalSinGarantia();

                    //Setear el documento que esta usando el usuario 
                    kardexDetalle.setCodigoTipoDocumento(TipoDocumentoEnum.STOCK_INICIAL.getCodigo());

                    //Fecha de ingreso                             
                    kardexDetalle.setFechaIngreso(UtilidadesFecha.getFechaHoyTimeStamp());
                    kardexDetalle.setFechaDocumento(UtilidadesFecha.getFechaHoy());
                    
                    //Si no existe antes el producto tengo que crear un nuevo KARDEX
                    //Kardex kardex =null;
                    //if(productoTmp==null)
                    //{
                    //    kardex = new Kardex();
                    //    kardex.setBodega(bodega);                        
                    //    //Obtener costo de la plantilla
                    //    Double costo = (Double) obtenerDatoPlantilla(fila, ExcelMigrarProductos.Enum.COSTO);
                    //    if (costo != null) {
                    //        kardex.setCostoPromedio(new BigDecimal(costo + ""));
                    //    }
                    //}
                    //else //En este caso se supone que ya existe el PRODUCTO y tengo que actualizar los movimientos para que coincida con el nuevo total
                    if(productoTmp!=null)
                    {
                        kardexDetalle.setCodigoTipoDocumento(TipoDocumentoEnum.STOCK_AJUSTE_MIGRADO.getCodigo());
                        kardex= ServiceFactory.getFactory().getKardexServiceIf().buscarKardexPorProductoyBodega(bodega, productoTmp);
                        BigDecimal stockActual=kardex.getStock();
                        BigDecimal stockAjuste=new BigDecimal(stock).subtract(stockActual);                       
                        //cuando tengo que hacer un ajuste inventario envio los nuevos datos
                        kardexDetalle.setCantidad(stockAjuste);
                    }
                    

                    kardex.setProducto(producto);
                    kardexDetalle.setKardex(kardex);

                //}

            } else {
                throw new ExcelMigrar.ExcepcionExcel("La bodega no existe");
            }
        }
        return kardexDetalle;
    
    }
    
    

    @Override
    public ExcelMigrar getExcelMigrar() {
        return new ExcelMigrarProductos();
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void imprimir() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void limpiar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getURLAyuda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public InterfaceModelFind obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public InputStream getInputStreamExcel() {
        return RecursoCodefac.PLANTILLAS_EXCEL.getResourceInputStream("productos.xlsx");
    }
    

}
