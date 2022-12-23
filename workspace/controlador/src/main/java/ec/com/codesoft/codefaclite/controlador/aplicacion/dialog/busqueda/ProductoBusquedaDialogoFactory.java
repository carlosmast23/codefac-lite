/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Bodega;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Kardex;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoNegocioEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.BodegaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.ParametroUtilidades;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class ProductoBusquedaDialogoFactory 
{    
    private Sucursal sucursal;
    private Boolean inventarioDefecto;
    private ResultadoEnum resultadoEnum;
    private Boolean isInventario=false;

    public ProductoBusquedaDialogoFactory(Sucursal sucursal,ResultadoEnum resultadoEnum) {
        this.sucursal = sucursal;
        this.resultadoEnum=resultadoEnum;
        inventarioDefecto=false;
    }

    public ProductoBusquedaDialogoFactory(Sucursal sucursal, Boolean inventarioDefecto, ResultadoEnum resultadoEnum) {
        this.sucursal = sucursal;
        this.inventarioDefecto = inventarioDefecto;
        this.resultadoEnum = resultadoEnum;
    }
    
    
    public InterfaceModelFind construirDialogo()
    {
        try {
            if(ParametroUtilidades.comparar(sucursal.getEmpresa(), ParametroCodefac.BUSCADOR_SOLO_INVENTARIO,EnumSiNo.SI) || inventarioDefecto)
            {
                BodegaServiceIf service = ServiceFactory.getFactory().getBodegaServiceIf();
                Bodega bodegaVenta = service.obtenerBodegaVenta(sucursal);
                
                if (bodegaVenta == null) {
                    throw new ServicioCodefacException("No existe un tipo de Bodega de Venta Configurado");
                }
                
                ProductoInventarioBusquedaDialogo productoInventarioBusquedaDialogo = new ProductoInventarioBusquedaDialogo(EnumSiNo.SI,sucursal.getEmpresa(), bodegaVenta, true);

                //cambiar el tipo de buscador si tiene activo el parametro de farmacia
                if (ParametroUtilidades.comparar(sucursal.getEmpresa(), ParametroCodefac.TIPO_NEGOCIO, TipoNegocioEnum.FARMACIA)) {
                    productoInventarioBusquedaDialogo = new FarmaciaProductoInventarioBusquedaDialogo(EnumSiNo.SI, sucursal.getEmpresa(), bodegaVenta);
                } else if (ParametroUtilidades.comparar(sucursal.getEmpresa(), ParametroCodefac.TIPO_NEGOCIO, TipoNegocioEnum.TALLER_AUTOMOTRIZ)) {
                    productoInventarioBusquedaDialogo = new TallerMecanicoInventarioBusquedaDialogo(EnumSiNo.SI,sucursal.getEmpresa(), bodegaVenta);
                }
                isInventario=true;
                return productoInventarioBusquedaDialogo;
                //BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(productoInventarioBusquedaDialogo, 1100);                
                //return buscarDialogoModel;
            }
            else
            {
                isInventario=false;
                ProductoBusquedaDialogo buscarBusquedaDialogo = new ProductoBusquedaDialogo(sucursal.getEmpresa(),false,true);
                return buscarBusquedaDialogo;
                //BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(buscarBusquedaDialogo);
                //return buscarDialogoModel;
            }            
             
        } catch (RemoteException ex) {
            Logger.getLogger(ProductoBusquedaDialogoFactory.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ProductoBusquedaDialogoFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    public Object ejecutarDialogo() throws ServicioCodefacException
    {
        
        InterfaceModelFind interfaceModelFind=construirDialogo();
        BuscarDialogoModel buscarDialogoModel =null;
        if(isInventario)
        {
            buscarDialogoModel = new BuscarDialogoModel(interfaceModelFind, 1100); 
        }
        else
        {
            buscarDialogoModel = new BuscarDialogoModel(interfaceModelFind);
        }
        
        buscarDialogoModel.setVisible(true);
        Object resultado = buscarDialogoModel.getResultado();
        
        if(resultado instanceof Producto)
        {
            Producto producto = (Producto) buscarDialogoModel.getResultado();
            if (resultadoEnum.equals(ResultadoEnum.KARDEX)) {
                Kardex kardex = new Kardex();
                kardex.setProducto(producto);
                return kardex;
            }
            return resultado;
        }
        else if(resultado instanceof Kardex)
        {
            Kardex kardex=(Kardex) resultado;
            if (resultadoEnum.equals(ResultadoEnum.PRODUCTO)) {
                return kardex.getProducto();
            }
            return resultado;
        }
        return null;
    }
    
    
    
    public enum ResultadoEnum
    {
        PRODUCTO,
        KARDEX;
    }
    
}
