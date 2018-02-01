/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.inventario.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfaceModelFind;
import ec.com.codesoft.codefaclite.servidor.entity.Bodega;
import java.util.Vector;

/**
 *
 * @author CodesoftDesarrollo
 */
public class BodegaBusquedaDialogo implements InterfaceModelFind<Bodega> {

    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<>();
        titulo.add(new ColumnaDialogo("Nombre", 0.2d));
        titulo.add(new ColumnaDialogo("Descripción", 0.3d));
        titulo.add(new ColumnaDialogo("Encargado", 0.3d));
        return titulo;
    }

    @Override
    public QueryDialog getConsulta(String filter) {
        String queryString = "SELECT u FROM Bodega u WHERE ";
        queryString += " ( LOWER(u.nombre) LIKE " + filter + " )";
        QueryDialog queryDialog = new QueryDialog(queryString);
        return queryDialog;
    }

    @Override
    public void agregarObjeto(Bodega t, Vector dato) {
        dato.add(t.getNombre());
        dato.add(t.getDescripcion());
        dato.add(t.getEncargado());
    }

    @Override
    public Boolean buscarObjeto(Bodega t, Object valor) {
        return t.getNombre().equals(valor.toString());
    }

}
