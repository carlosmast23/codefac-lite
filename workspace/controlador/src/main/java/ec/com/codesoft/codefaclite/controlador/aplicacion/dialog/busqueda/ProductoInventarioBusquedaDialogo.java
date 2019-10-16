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
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Bodega;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Kardex;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.KardexServiceIf;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class ProductoInventarioBusquedaDialogo implements InterfaceModelFind<Producto> , InterfacesPropertisFindWeb {
    
    private Empresa empresa;
    private EnumSiNo isManejoInvetario;
    private Bodega bodega;
    
    public ProductoInventarioBusquedaDialogo(EnumSiNo isManejoInvetario, Empresa empresa, Bodega bodega) 
    {
        this.isManejoInvetario = isManejoInvetario;
        this.empresa = empresa;
        this.bodega = bodega;
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
        
        String queryString = "SELECT u FROM Producto u where u.empresa=?4 and (u.estado=?1)"+whereManejaInventario;      
        
        queryString+=" and ( LOWER(u.nombre) like ?2 OR u.codigoPersonalizado like ?2 ) ORDER BY u.codigoPersonalizado";
        
        QueryDialog queryDialog=new QueryDialog(queryString);
        queryDialog.agregarParametro(1,GeneralEnumEstado.ACTIVO.getEstado());
        queryDialog.agregarParametro(2,filter);
        queryDialog.agregarParametro(4,empresa);
        
        if(isManejoInvetario!=null)
        {
            queryDialog.agregarParametro(98,isManejoInvetario.getLetra());
        }
       
        return queryDialog;
    }
    
    
    @Override
    public void agregarObjeto(Producto producto, Vector vector) {
        try {
            //Producto producto=(Producto) objeto[0];
            //Kardex kardex=(Kardex) objeto[1];
            KardexServiceIf servicio = ServiceFactory.getFactory().getKardexServiceIf();
            Kardex kardex = servicio.buscarKardexPorProductoyBodega(this.bodega, producto);
            vector.add(producto.getCodigoPersonalizado());
            vector.add(producto.getNombre());
            vector.add((producto.getUbicacion()!=null)?producto.getUbicacion():"");
            vector.add(producto.getValorUnitario());
            vector.add(producto.getCatalogoProducto().getIva().toString());
            vector.add((kardex!=null)?kardex.getStock():"");
        } catch (RemoteException ex) {
            Logger.getLogger(ProductoInventarioBusquedaDialogo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Vector<String> getNamePropertysObject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.        
    }
    
}
