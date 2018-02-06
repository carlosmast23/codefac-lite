/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.inventario.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfaceModelFind;
import ec.com.codesoft.codefaclite.servidor.entity.CategoriaProducto;
import ec.com.codesoft.codefaclite.servidor.entity.enumerados.CategoriaProductoEnumEstado;
import java.util.Vector;

/**
 *
 * @author CodesoftDesarrollo
 */
public class CategoriaProductoBusquedaDialogo implements InterfaceModelFind<CategoriaProducto> {

    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<>();
        titulo.add(new ColumnaDialogo("Nombre", 0.2d));
        titulo.add(new ColumnaDialogo("Descripción", 0.3d));
        return titulo;
    }

    @Override
    public QueryDialog getConsulta(String filter) {
        String queryString = "SELECT u FROM CategoriaProducto u WHERE (u.estado=?1) and";
        queryString += " ( LOWER(u.nombre) LIKE " + filter + " )";
        QueryDialog queryDialog = new QueryDialog(queryString);
        queryDialog.agregarParametro(1, CategoriaProductoEnumEstado.ACTIVO.getEstado());
        return queryDialog;
    }

    @Override
    public void agregarObjeto(CategoriaProducto c, Vector dato) {
        dato.add(c.getNombre());
        dato.add(c.getDescripcion());
    }

    @Override
    public Boolean buscarObjeto(CategoriaProducto c, Object valor) {
        return c.getNombre().equals(valor.toString());
    }

}
