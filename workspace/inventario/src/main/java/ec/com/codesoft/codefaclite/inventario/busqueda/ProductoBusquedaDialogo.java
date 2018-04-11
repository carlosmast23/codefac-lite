/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.inventario.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfaceModelFind;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ProductoEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoProductoEnum;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author PC
 */
public class ProductoBusquedaDialogo implements InterfaceModelFind<Producto>
{
    
    private TipoProductoEnum tipoProductoEnum;

    @Override
    public Vector<ColumnaDialogo> getColumnas() 
    {
        Vector<ColumnaDialogo> titulo = new Vector<>();
        titulo.add(new ColumnaDialogo("Codigo", 0.2d));
        titulo.add(new ColumnaDialogo("Nombre", 0.3d));
        titulo.add(new ColumnaDialogo("Precio Unit", 0.3d));
        titulo.add(new ColumnaDialogo("IVA", 0.1d));        
        titulo.add(new ColumnaDialogo("ICE", 0.1d));        
        return titulo;
    }

    @Override
    public void agregarObjeto(Producto t, Vector dato) 
    {
        dato.add(t.getCodigoPersonalizado());
        dato.add(t.getNombre());
        dato.add(t.getValorUnitario());
        dato.add(t.getCatalogoProducto().getIva().toString());
        if(t.getCatalogoProducto() != null){
            dato.add(t.getCatalogoProducto().toString());
        }
    }

    /*
    @Override
    public Boolean buscarObjeto(Producto t, Object valor) 
    {
        return t.getNombre().equals(valor.toString());   
    }*/

    @Override
    public QueryDialog getConsulta(String filter) {
        String queryExtra="";
        if(tipoProductoEnum!=null)
        {
            queryExtra=" u.tipoProducto=?99 and ";
        }        
        
        String queryString = "SELECT u FROM Producto u WHERE (u.estado=?1) and "+queryExtra;
        queryString+=" ( LOWER(u.nombre) like ?2 )";
        QueryDialog queryDialog=new QueryDialog(queryString);
        queryDialog.agregarParametro(1,ProductoEnumEstado.ACTIVO.getEstado());
        queryDialog.agregarParametro(2,filter);
        if(tipoProductoEnum!=null)
        {
            queryDialog.agregarParametro(99,tipoProductoEnum.getLetra());
        }
        //queryDialog.agregarParametro(2,ProductoEnumEstado.INACTIVO.getEstado());
        return queryDialog;
    }

    public TipoProductoEnum getTipoProductoEnum() {
        return tipoProductoEnum;
    }

    public void setTipoProductoEnum(TipoProductoEnum tipoProductoEnum) {
        this.tipoProductoEnum = tipoProductoEnum;
    }
    
    
}
