/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda;


import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.panel.TallerMecanicoDialogoInfo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ComponenteFiltro;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.DialogPanelAuxIf;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.DialogoConfigAuxIf;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.FiltroDialogoIf;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;import java.util.Map;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfacesPropertisFindWeb;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Bodega;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CategoriaProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Kardex;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.MarcaProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PresentacionProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ProductoPresentacionDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SegmentoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoProductoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoQueryEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoStockEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.KardexServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.ParametroUtilidades;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 *
 * @author Carlos
 */
public class ProductoInventarioBusquedaDialogo extends ProductoInventarioBusquedaSimpleDialogo
{
    
    public ProductoInventarioBusquedaDialogo(EnumSiNo isManejoInvetario, Empresa empresa, Bodega bodega, Boolean mostrarStockNegativo, Boolean mostrarPresentaciones) {
        super(isManejoInvetario, empresa, bodega, mostrarStockNegativo, mostrarPresentaciones);
    }
    
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<>();
        titulo.add(new ColumnaDialogo("Codigo", 0.2d));
        titulo.add(new ColumnaDialogo("Codigo Aux", 0.2d));
        titulo.add(new ColumnaDialogo("Nombre", 0.5d));
        titulo.add(new ColumnaDialogo("UNI", 0.1d));        
        titulo.add(new ColumnaDialogo("Lote", 0.2d));
        //titulo.add(new ColumnaDialogo("Lote", 0.3d));
        titulo.add(new ColumnaDialogo("Marca", 0.25d));
        titulo.add(new ColumnaDialogo("Aplicación", 0.25d));
        titulo.add(new ColumnaDialogo("Ubicación", 0.2d));        
        titulo.add(new ColumnaDialogo("Costo", 0.10d));
        titulo.add(new ColumnaDialogo("Pvp+Iva", 0.10d));
        titulo.add(new ColumnaDialogo("IVA", 0.05d));        
        titulo.add(new ColumnaDialogo("Stock", 0.08d));        
        return titulo;
    }

    @Override
    public void agregarDatosAdicionales(Object[] resultado, Vector vector) {
        Kardex kardex=(Kardex) resultado[0];
        Producto producto=obtenerProductoDisponible(resultado);
        
        //vector.add(3, empresa);
        vector.add(3,(producto.getMarcaProducto()!=null)?producto.getMarcaProducto().getNombre():"");
        vector.add(3,producto.obtenerNombrePresentacion());
        
        /*if (kardex.getLote() != null) {
            vector.add(3,(kardex.getLote().getCodigo()));
        } else {
            vector.add(3,"");
        }*/
        
        //vector.add((kardex.getLote()!=null)?kardex.getLote().getCodigo():"");
        
    }
    
    
}
