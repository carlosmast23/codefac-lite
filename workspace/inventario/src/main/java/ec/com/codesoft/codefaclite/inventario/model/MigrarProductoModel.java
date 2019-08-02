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
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoProductoEnum;
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
                   Producto producto=new Producto();
                   producto.setCodigoPersonalizado((String) fila.getByEnum(ExcelMigrarProductos.Enum.CODIGO).valor);
                   producto.setNombre((String) fila.getByEnum(ExcelMigrarProductos.Enum.NOMBRE).valor);
                   
                   Double precioVentaPublico=(Double) fila.getByEnum(ExcelMigrarProductos.Enum.PRECIO_VENTA_PUBLICO).valor;
                   String catalogoProducto=(String) fila.getByEnum(ExcelMigrarProductos.Enum.CATALOGO_PRODUCTO).valor;
                  
                   CatalogoProducto catalogoProductoTmp=ServiceFactory.getFactory().getCatalogoProductoServiceIf().obtenerPorNombre(catalogoProducto); 
                   if(catalogoProductoTmp==null)
                   {
                       throw new ExcelMigrar.ExcepcionExcel("No existe un catalogo de producto para migrar");
                   }
                   
                   producto.setCatalogoProducto(catalogoProductoTmp);
                   BigDecimal precioVenta=BigDecimal.ZERO;
                   if(catalogoProductoTmp.getIva().getTarifa().equals(0))
                   {
                       
                       precioVenta=new BigDecimal(precioVentaPublico.toString());
                   }
                   else
                   {
                       String ivaDefecto=session.getParametrosCodefac().get(ParametroCodefac.IVA_DEFECTO).valor; //Valor en entero ejemplo 12
                       BigDecimal valorTransformar=new BigDecimal(ivaDefecto).divide(new BigDecimal("100"),2,BigDecimal.ROUND_HALF_UP).add(BigDecimal.ONE);
                       precioVenta=new BigDecimal(precioVentaPublico).divide(valorTransformar,3,BigDecimal.ROUND_HALF_UP);
                   }
                   
                   producto.setValorUnitario(precioVenta);
                   producto.setEstadoEnum(GeneralEnumEstado.ACTIVO);
                   //producto.setCatalogoProducto(CatalogoPro);
                   
                   Producto productoTmp=ServiceFactory.getFactory().getProductoServiceIf().buscarPorNombreyEstado(producto.getNombre(), GeneralEnumEstado.ACTIVO,session.getEmpresa());
                   if(productoTmp!=null)
                   {
                       throw new ExcelMigrar.ExcepcionExcelRegistroDuplicado("El dato ya se encuentra registrado en el sistema");
                   }
                   
                   producto.setCantidadMinima(0);
                   producto.setStockInicial(0l);
                   producto.setPrecioDistribuidor(BigDecimal.ZERO);
                   producto.setPrecioTarjeta(BigDecimal.ZERO);
                   producto.setGarantia(EnumSiNo.NO.getLetra());
                   producto.setTipoProductoCodigo(TipoProductoEnum.PRODUCTO.getLetra());
                   producto.setManejarInventario(EnumSiNo.NO.getLetra());
                   
                   ServiceFactory.getFactory().getProductoServiceIf().grabar(producto);
                   
                   
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
