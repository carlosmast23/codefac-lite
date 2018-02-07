/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfaceModelFind;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ProductoEnumEstado;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author PC
 */
public class ProductoBusquedaDialogo implements InterfaceModelFind<Producto>
{

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
        
        if(t.getIva() != null)
        {
            dato.add(t.getIva().toString());    
        }else
        {
            dato.add("Sin definir iva");
        }
        if(t.getIce()!= null )
        {
            dato.add(t.getIce().toString());    
        }else
        {
            dato.add("Sin definir ice");
        }
        
    }

    @Override
    public Boolean buscarObjeto(Producto t, Object valor) 
    {
        return t.getNombre().equals(valor.toString());   
    }

    @Override
    public QueryDialog getConsulta(String filter) {
        String queryString = "SELECT u FROM Producto u WHERE (u.estado=?1) and";
        queryString+=" ( LOWER(u.nombre) like "+filter+" )";
        QueryDialog queryDialog=new QueryDialog(queryString);
        queryDialog.agregarParametro(1,ProductoEnumEstado.ACTIVO.getEstado());
        return queryDialog;
    }
    
}
