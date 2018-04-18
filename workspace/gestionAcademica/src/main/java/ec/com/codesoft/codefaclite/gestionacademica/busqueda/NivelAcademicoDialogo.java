/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.gestionacademica.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfaceModelFind;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.NivelAcademico;
import java.util.Vector;

/**
 *
 * @author Carlos
 */
public class NivelAcademicoDialogo implements InterfaceModelFind<NivelAcademico>{

    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<ColumnaDialogo>();
        titulo.add(new ColumnaDialogo("Nombre", 0.3d));
        titulo.add(new ColumnaDialogo("Periodo", 0.3d));
        titulo.add(new ColumnaDialogo("Nivel", 0.3d));
        titulo.add(new ColumnaDialogo("Estado", 0.2d));
        return titulo;
    }

    @Override
    public QueryDialog getConsulta(String filter) {
        String queryString = "SELECT u FROM NivelAcademico u WHERE ";
        queryString += " ( LOWER(u.nombre) like ?1 )";
        QueryDialog queryDialog = new QueryDialog(queryString);
        queryDialog.agregarParametro(1,filter);
        return queryDialog;
    }

    @Override
    public void agregarObjeto(NivelAcademico t, Vector dato) {
        dato.add(t.getNombre());
        dato.add(t.getPeriodo().getNombre());
        dato.add(t.getNivel().getNombre());
        dato.add(t.getEstadoEnum().getNombre());
    }

    /*
    @Override
    public Boolean buscarObjeto(NivelAcademico t, Object valor) {
        return t.getNombre().equals(valor.toString());
    }*/
    
}
