/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.gestionacademica.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.util.Vector;

/**
 *
 * @author CodesoftDesarrollo
 */
public class PeriodoBusquedaDialogo implements InterfaceModelFind<Periodo> {

    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<>();
        titulo.add(new ColumnaDialogo("Nombre", 0.3d));
        titulo.add(new ColumnaDialogo("Fecha inicio", 0.2d));
        titulo.add(new ColumnaDialogo("Fecha fin", 0.2d));
        titulo.add(new ColumnaDialogo("Estado", 0.2d));
        return titulo;
    }

    @Override
    public QueryDialog getConsulta(String filter) {
        String queryString = "SELECT u FROM Periodo u WHERE (u.estado=?1 or u.estado=?2) and";
        queryString += " ( LOWER(u.nombre) LIKE ?3 )";
        QueryDialog queryDialog = new QueryDialog(queryString);
        queryDialog.agregarParametro(1, GeneralEnumEstado.ACTIVO.getEstado());
        queryDialog.agregarParametro(2, GeneralEnumEstado.INACTIVO.getEstado());
        queryDialog.agregarParametro(3, filter);
        return queryDialog;
    }

    @Override
    public void agregarObjeto(Periodo p, Vector dato) {
        dato.add(p.getNombre());
        dato.add(p.getFechaInicio());
        dato.add(p.getFechaFin());
        dato.add(p.getEStadoEnum().getNombre());
    }

    /*
    @Override
    public Boolean buscarObjeto(Periodo p, Object valor) {
        return p.getNombre().equals(valor.toString());
    }*/

}
