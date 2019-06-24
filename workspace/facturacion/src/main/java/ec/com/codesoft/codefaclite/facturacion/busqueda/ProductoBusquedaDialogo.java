/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfacesPropertisFindWeb;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;

/**
 *
 * @author PC
 */
public class ProductoBusquedaDialogo implements InterfaceModelFind<Producto>, InterfacesPropertisFindWeb {

    private EnumSiNo isManejoInvetario;

    public ProductoBusquedaDialogo(EnumSiNo isManejoInvetario) {
        this.isManejoInvetario = isManejoInvetario;
    }

    public ProductoBusquedaDialogo() {
        this.isManejoInvetario = null; //Si no se quiere filtrar seteo como null
    }

    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<>();
        titulo.add(new ColumnaDialogo("Codigo", 0.2d));
        titulo.add(new ColumnaDialogo("Nombre", 0.3d));
        titulo.add(new ColumnaDialogo("Precio Unit", 0.3d));
        titulo.add(new ColumnaDialogo("IVA", 0.1d));
        titulo.add(new ColumnaDialogo("ICE", 0.1d));
        return titulo;
    }

    @Override
    public void agregarObjeto(Producto t, Vector dato) {
        dato.add(t.getCodigoPersonalizado());
        dato.add(t.getNombre());
        dato.add(t.getValorUnitario());

        if (t.getCatalogoProducto().getIva() != null) {
            dato.add(t.getCatalogoProducto().getIva().toString());
        } else {
            dato.add("Sin definir iva");
        }
        if (t.getCatalogoProducto().getIce() != null) {
            dato.add(t.getCatalogoProducto().getIce().toString());
        } else {
            dato.add("Sin definir ice");
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
        String subQueryManejar = "and u.manejarInventario=?3";

        String queryString = "SELECT u FROM Producto u WHERE (u.estado=?1) " + (isManejoInvetario != null ? subQueryManejar : "") + " and";
        queryString += " ( LOWER(u.nombre) like ?2 )";
        QueryDialog queryDialog = new QueryDialog(queryString);
        queryDialog.agregarParametro(1, GeneralEnumEstado.ACTIVO.getEstado());
        queryDialog.agregarParametro(2, filter);

        if (isManejoInvetario != null) {
            queryDialog.agregarParametro(3, isManejoInvetario.getLetra());
        }

        return queryDialog;
    }

    @Override
    public Vector<String> getNamePropertysObject() {
        Vector<String> propiedades = new Vector<String>();
        propiedades.add("codigoPersonalizado");
        propiedades.add("nombre");
        propiedades.add("valorUnitario");
        propiedades.add("catalogoProducto.iva");
        propiedades.add("catalogoProducto.ice");
        return propiedades;
    }

}
