/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfacesPropertisFindWeb;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Kardex;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.util.Vector;

/**
 *
 * @author Carlos
 */
public class ProductoInventarioBusquedaDialogo implements InterfaceModelFind<Object[]> , InterfacesPropertisFindWeb {
    
    private Empresa empresa;
    private EnumSiNo isManejoInvetario;
    
    public ProductoInventarioBusquedaDialogo(EnumSiNo isManejoInvetario, Empresa empresa) 
    {
        this.isManejoInvetario = isManejoInvetario;
        this.empresa = empresa;
    }

    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<>();
        titulo.add(new ColumnaDialogo("Codigo", 0.2d));
        titulo.add(new ColumnaDialogo("Nombre", 0.3d));
        titulo.add(new ColumnaDialogo("Ubicación", 0.3d));
        titulo.add(new ColumnaDialogo("Precio Unit", 0.1d));
        titulo.add(new ColumnaDialogo("IVA", 0.1d));        
        titulo.add(new ColumnaDialogo("Stock", 0.1d));        
        return titulo;
    }

    @Override
    public QueryDialog getConsulta(String filter) {
         String whereManejaInventario="";
        if(isManejoInvetario!=null)
        {
            whereManejaInventario=" and u.manejarInventario=?98 ";
        }
        
        String queryString = "SELECT u,k FROM Producto u,Kardex k WHERE  u.empresa=?4 and (u.estado=?1) "+whereManejaInventario;      
        
        queryString+=" and ( LOWER(u.nombre) like ?2 OR u.codigoPersonalizado like ?2 ) ORDER BY u.codigoPersonalizado";
        
        QueryDialog queryDialog=new QueryDialog(queryString);
        queryDialog.agregarParametro(1,GeneralEnumEstado.ACTIVO.getEstado());
        queryDialog.agregarParametro(2,filter);
        queryDialog.agregarParametro(4,empresa);
        return queryDialog;
    }

    @Override
    public void agregarObjeto(Object[] objeto, Vector vector) {
        Producto producto=(Producto) objeto[0];
        Kardex kardex=(Kardex) objeto[1];
        
        vector.add(producto.getCodigoPersonalizado());
        vector.add(producto.getNombre());
        vector.add((producto.getUbicacion()!=null)?producto.getUbicacion():"");
        vector.add(producto.getValorUnitario());
        vector.add(producto.getCatalogoProducto().getIva().toString());
        
        vector.add((kardex!=null)?kardex.getStock():"");
    }

    @Override
    public Vector<String> getNamePropertysObject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.        
    }
    
}
