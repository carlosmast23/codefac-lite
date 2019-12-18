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
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
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
import java.math.BigInteger;
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

    @Override
    public ExcelMigrar.MigrarInterface getInterfaceMigrar() {
        return new ExcelMigrar.MigrarInterface() {
            @Override
            public void procesar(ExcelMigrar.FilaResultado fila) throws ExcelMigrar.ExcepcionExcel, ExcelMigrar.ExcepcionExcelRegistroDuplicado {
                try {
                    KardexDetalle kardexDetalle=null; //Referencia para poder grabar cuando se va a ingresar el inventario
                    Producto producto = new Producto();
                    producto.setCodigoPersonalizado(((String) fila.getByEnum(ExcelMigrarProductos.Enum.CODIGO).valor).trim());
                    producto.setNombre(((String) fila.getByEnum(ExcelMigrarProductos.Enum.NOMBRE).valor).trim());

                    Double precioVentaPublico = (Double) fila.getByEnum(ExcelMigrarProductos.Enum.PRECIO_VENTA_PUBLICO).valor;
                    Double precioVentaOferta = (Double) fila.getByEnum(ExcelMigrarProductos.Enum.PRECIO_VENTA_OFERTA).valor;
                    Double precioVentaPromedio = (Double) fila.getByEnum(ExcelMigrarProductos.Enum.PRECIO_VENTA_PROMEDIO).valor;
                    
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
                    Double porcentajeIva = (Double) fila.getByEnum(ExcelMigrarProductos.Enum.IVA_PORCENTAJE).valor;
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
                    String manejaInventario=(String) fila.getByEnum(ExcelMigrarProductos.Enum.MANEJA_INVENTARIO).valor;
                    EnumSiNo manejaInventarioEnumSiNo=EnumSiNo.getEnumByLetra(manejaInventario.substring(0,1));
                    if(manejaInventarioEnumSiNo!=null && manejaInventarioEnumSiNo.equals(EnumSiNo.SI)) // Si cumple esta condicion vamos a grabar el resto de datos para el inventario
                    {
                        String bodegaNombre=(String) fila.getByEnum(ExcelMigrarProductos.Enum.BODEGA).valor;
                        Bodega bodega=ServiceFactory.getFactory().getBodegaServiceIf().buscarPorNombre(bodegaNombre);
                        if(bodega!=null) //Si la bodega existe consulto cuanto quiere agregar al stock
                        {
                            Double stock=(Double) fila.getByEnum(ExcelMigrarProductos.Enum.STOCK).valor;
                            
                            kardexDetalle = new KardexDetalle();
                            kardexDetalle.setCantidad(stock.intValue());
                            kardexDetalle.setPrecioUnitario(BigDecimal.ZERO);
                            kardexDetalle.recalcularTotalSinGarantia();

                            //Setear el documento que esta usando el usuario 
                            kardexDetalle.setCodigoTipoDocumento(TipoDocumentoEnum.STOCK_INICIAL.getCodigo());

                            //Fecha de ingreso                             
                            kardexDetalle.setFechaIngreso(UtilidadesFecha.getFechaHoy());

                            Kardex kardex = new Kardex();
                            kardex.setBodega(bodega);
                            if(precioVentaPromedio>0)
                            {
                                kardex.setPrecioPromedio(new BigDecimal(precioVentaPromedio.toString()));
                            }
                            
                            kardex.setProducto(producto);
                            kardexDetalle.setKardex(kardex);
                            
                        }
                        else
                        {
                            throw new ExcelMigrar.ExcepcionExcel("La bodega no existe");
                        }
                    }
                    
                    
                   
                    producto.setValorUnitario(new BigDecimal(precioVentaPublico.toString()));
                    if(precioVentaOferta>0)
                    {
                        producto.setPrecioDistribuidor(new BigDecimal(precioVentaOferta.toString()));
                    }
                    
                    producto.setEstadoEnum(GeneralEnumEstado.ACTIVO);
                    //producto.setCatalogoProducto(CatalogoPro);

                    ///========================> VALIDAR QUE NO EXISTA UN PRODUCTO SIMILAR YA INGRESADO POR CODIGO <======================//
                    //TODO: Esta parte esta de tener muy en cuenta porque aveces como se genera un codigo esta parte puede ser dificil de encontrar por el codigo y mas facil por el nombre
                    //Producto productoTmp =ServiceFactory.getFactory().getProductoServiceIf().buscarProductoActivoPorCodigo(producto.getCodigoPersonalizado(),session.getEmpresa());
                    Producto productoTmp = ServiceFactory.getFactory().getProductoServiceIf().buscarPorNombreyEstado(producto.getNombre(), GeneralEnumEstado.ACTIVO, session.getEmpresa());
                    
                    if(productoTmp != null) 
                    {
                        //Si maneja inventario y el producto existe entonces solo consulto el producto anterior
                        if(manejaInventarioEnumSiNo.equals(EnumSiNo.SI))
                        {
                            producto=productoTmp;
                            kardexDetalle.getKardex().setProducto(producto);
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
                        producto.setCantidadMinima(0);
                        producto.setStockInicial(0l);
                        //producto.setPrecioDistribuidor(BigDecimal.ZERO);
                        producto.setPrecioTarjeta(BigDecimal.ZERO);
                        producto.setGarantia(EnumSiNo.NO.getLetra());
                        producto.setTipoProductoCodigo(TipoProductoEnum.PRODUCTO.getLetra());
                        producto.setManejarInventario(manejaInventarioEnumSiNo.getLetra()); //TODO:Cambiar para setear un enum
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
                }

            }
        };
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
    public BuscarDialogoModel obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public InputStream getInputStreamExcel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
