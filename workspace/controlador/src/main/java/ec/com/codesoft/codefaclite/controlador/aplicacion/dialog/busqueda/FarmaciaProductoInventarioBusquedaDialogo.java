/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Bodega;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Kardex;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PresentacionProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ProductoPresentacionDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Vector;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class FarmaciaProductoInventarioBusquedaDialogo extends ProductoInventarioBusquedaDialogo
{

    public FarmaciaProductoInventarioBusquedaDialogo(EnumSiNo isManejoInvetario, Empresa empresa, Bodega bodega) {
        super(isManejoInvetario, empresa, bodega,false,false);
    }

    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<>();
        titulo.add(new ColumnaDialogo("Codigo", 0.2d));
        titulo.add(new ColumnaDialogo("Nombre", 0.4d));
        titulo.add(new ColumnaDialogo("Nombre generico", 0.4d));
        titulo.add(new ColumnaDialogo("Marca", 0.2d));    
        titulo.add(new ColumnaDialogo("Lote", 0.2d));
        titulo.add(new ColumnaDialogo("F.Exp", 0.15d)); 
        titulo.add(new ColumnaDialogo("Ubicación", 0.3d));
        titulo.add(new ColumnaDialogo("Pvp+iva", 0.10d));
        //titulo.add(new ColumnaDialogo("Pvp+Iva", 0.10d));
        titulo.add(new ColumnaDialogo("Pvp2", 0.10d));
        //titulo.add(new ColumnaDialogo("IVA", 0.05d)); 
        titulo.add(new ColumnaDialogo("UnixCaja", 0.1d));
        titulo.add(new ColumnaDialogo("Cant Caja", 0.1d));        
        titulo.add(new ColumnaDialogo("Cant Uni", 0.1d));        
        titulo.add(new ColumnaDialogo("Stock", 0.1d));        
        return titulo;
    }

    @Override
    public void agregarObjeto(Object[] resultado, Vector vector) {
        Kardex kardex=(Kardex) resultado[0];
        //Producto producto=kardex.getProducto();
        Producto producto=obtenerProductoDisponible(resultado);
        vector.add(producto.getCodigoPersonalizado());
        vector.add(producto.getNombre());
        vector.add(producto.getNombreGenerico());
        vector.add((producto.getMarcaProducto()!=null)?producto.getMarcaProducto().getNombre():"");
        vector.add((kardex.getLote()!=null)?kardex.getLote().getCodigo():"");
        vector.add((kardex.getLote()!=null)?kardex.getLote().getFechaVencimiento():"");
        vector.add((producto.getUbicacion()!=null)?producto.getUbicacion():"");
        //vector.add(producto.getValorUnitario().setScale(3,RoundingMode.HALF_UP));
        vector.add(producto.getValorUnitarioConIva().setScale(3,RoundingMode.HALF_UP));
        BigDecimal precioDistribuidor=(producto.getPrecioDistribuidor()!=null)?producto.getPrecioDistribuidor().setScale(3,RoundingMode.HALF_UP):BigDecimal.ZERO;        
        
        vector.add(precioDistribuidor);
        //vector.add((producto.getCatalogoProducto()!=null && producto.getCatalogoProducto().getIva()!=null)?producto.getCatalogoProducto().getIva().getTarifa().toString():"SN");
        
        //TODO: Mejorar este calculo para poder obtener este calculo desde un lugar general
        BigDecimal totalUnidadesSueltas=kardex.obtenerTotalUnidadesSueltas();
        BigDecimal totalCajas=kardex.obtenerTotalCajas();
        
        if(totalUnidadesSueltas==null)
        {
            totalUnidadesSueltas=BigDecimal.ZERO;
        }
        
        if(totalCajas==null)
        {
            totalCajas=BigDecimal.ZERO;
        }
        
        vector.add(kardex.obtenerCantidadPorCaja().setScale(0, RoundingMode.HALF_UP));
        vector.add(totalCajas);
        vector.add(totalUnidadesSueltas.setScale(0, RoundingMode.HALF_UP));
        vector.add(kardex.getStock().setScale(0,RoundingMode.HALF_UP));
    }
    
    
    
    

    
    
}
