/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ComponenteFiltro;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Bodega;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Kardex;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Vector;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class TallerMecanicoInventarioBusquedaDialogo extends ProductoInventarioBusquedaDialogo{
    
    public TallerMecanicoInventarioBusquedaDialogo(EnumSiNo isManejoInvetario, Empresa empresa, Bodega bodega) {
        super(isManejoInvetario, empresa, bodega, true);
    }
    
        @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<>();
        titulo.add(new ColumnaDialogo("Item", 0.2d));
        titulo.add(new ColumnaDialogo("Descripción", 0.4d));
        titulo.add(new ColumnaDialogo("Aplicación", 0.4d));
        titulo.add(new ColumnaDialogo("Marca", 0.2d));            
        titulo.add(new ColumnaDialogo("Ubicación", 0.3d));       
        titulo.add(new ColumnaDialogo("Stock", 0.05d));        
        titulo.add(new ColumnaDialogo("Reserva", 0.05d));        
        titulo.add(new ColumnaDialogo("Disp", 0.05d));        
        return titulo;
    }
    
        @Override
    public void agregarObjeto(Kardex kardex, Vector vector) {
        Producto producto=kardex.getProducto();
        vector.add(producto.getCodigoPersonalizado());
        vector.add(producto.getNombre());
        vector.add(producto.getAplicacionProducto());
        vector.add(producto.getMarcaProducto());
        vector.add((producto.getUbicacion()!=null)?producto.getUbicacion():"");
        vector.add(kardex.getStock().setScale(0,RoundingMode.HALF_UP));
        vector.add("0");
        vector.add(kardex.getStock().setScale(0,RoundingMode.HALF_UP));
    }

    @Override
    public Vector<ComponenteFiltro> getfiltrosList() {
        Vector<ComponenteFiltro> filtroList=new Vector<ComponenteFiltro>();         
        
        ComponenteFiltro componenteFiltro=new ComponenteFiltro(ComponenteFiltro.TipoFiltroEnum.TEXTO,"Código: ",96);
        filtroList.add(componenteFiltro);
        //filtroList.add(e);
        return filtroList;
                
    }

    @Override
    public String getFiltroPorCodigo() {
        //Producto p;p.getCodigoPersonalizado()
        
        return " AND LOWER(u.codigoPersonalizado) like ?96 ";
    }

    @Override
    public String getFiltroPorMarca() {
        return "";
    }
    
    
    
}
