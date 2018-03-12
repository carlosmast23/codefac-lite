/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.gestionacademica.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfaceModelFind;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Nivel;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.util.Vector;

/**
 *
 * @author CodesoftDesarrollo
 */
public class NivelBusquedaDialogo implements InterfaceModelFind<Nivel> {

    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<>();
        titulo.add(new ColumnaDialogo("Nombre", 0.3d));
        titulo.add(new ColumnaDialogo("Orden", 0.2d));
        titulo.add(new ColumnaDialogo("Nivel Posterior", 0.3d));
        return titulo;
    }

    @Override
    public QueryDialog getConsulta(String filter) {
        String queryString = "SELECT u FROM Nivel u WHERE (u.estado=?1) and";
        queryString += " ( LOWER(u.nombre) LIKE " + filter + " )";
        QueryDialog queryDialog = new QueryDialog(queryString);
        queryDialog.agregarParametro(1, GeneralEnumEstado.ACTIVO.getEstado());
        return queryDialog;
    }

    @Override
    public void agregarObjeto(Nivel n, Vector dato) {
        dato.add(n.getNombre());
        dato.add(n.getOrden());
        if (n.getNivelPosterior() != null) {
            dato.add(n.getNivelPosterior().getNombre());
        }else
            dato.add("S/N");
    }

    /*
    @Override
    public Boolean buscarObjeto(Nivel n, Object valor) {
        return n.getNombre().equals(valor.toString());
    }*/

}
