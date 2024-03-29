/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.gestionacademica.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Aula;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.util.Vector;

/**
 *
 * @author CodesoftDesarrollo
 */
public class AulaBusquedaDialogo implements InterfaceModelFind<Aula> {

    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<>();
        titulo.add(new ColumnaDialogo("Nombre", 0.3d));
        titulo.add(new ColumnaDialogo("Ubicacion", 0.3d));
        titulo.add(new ColumnaDialogo("Capacidad", 0.2d));
        titulo.add(new ColumnaDialogo("Estado", 0.3d));
        return titulo;
    }

    @Override
    public QueryDialog getConsulta(String filter) {
        String queryString = "SELECT u FROM Aula u WHERE ";
        queryString += " ( LOWER(u.nombre) LIKE ?1 ) "
                    + "and u.estado <> ?2";
        QueryDialog queryDialog = new QueryDialog(queryString);
        queryDialog.agregarParametro(1, filter);
        queryDialog.agregarParametro(2, GeneralEnumEstado.ELIMINADO.getEstado());
        return queryDialog;
    }

    @Override
    public void agregarObjeto(Aula a, Vector dato) {
        dato.add(a.getNombre());
        dato.add(a.getUbicacion());
        dato.add(a.getCapacidad());
        dato.add(a.getEstadoEnum().getNombre());
    }

    /*
    @Override
    public Boolean buscarObjeto(Aula a, Object valor) {
        return a.getNombre().equals(valor.toString());
    }*/

}
