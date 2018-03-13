/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.configuraciones.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfaceModelFind;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Perfil;
import java.util.Vector;

/**
 *
 * @author Carlos
 */
public class PerfilBusquedaDialogo implements InterfaceModelFind<Perfil>{

    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<ColumnaDialogo>();
        titulo.add(new ColumnaDialogo("Nombre",100));
        return titulo;
    }

    @Override
    public QueryDialog getConsulta(String filter) {
        String queryString = "SELECT u FROM Perfil u WHERE ";
        queryString+=" ( LOWER(u.nombre) like "+filter+" )";
        QueryDialog queryDialog=new QueryDialog(queryString);
        return queryDialog;
    }

    @Override
    public void agregarObjeto(Perfil t, Vector dato) {
        dato.add(t.getNombre());
    }

    /*
    @Override
    public Boolean buscarObjeto(Perfil t, Object valor) {
        return t.getNombre().equals(valor.toString());
    }*/
    
}
