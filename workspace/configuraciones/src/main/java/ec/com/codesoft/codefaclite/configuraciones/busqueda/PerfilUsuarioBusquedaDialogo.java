/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.configuraciones.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfaceModelFind;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PerfilUsuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import java.util.Vector;

/**
 *
 * @author Carlos
 */
public class PerfilUsuarioBusquedaDialogo implements InterfaceModelFind<Usuario> {

    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<ColumnaDialogo>();
        titulo.add(new ColumnaDialogo("Usuario", 100));        
        return titulo;
    }

    @Override
    public QueryDialog getConsulta(String filter) {
        String queryString = "SELECT u FROM Usuario u WHERE ";
        queryString+=" ( LOWER(u.nick) like "+filter+" )";
        QueryDialog queryDialog=new QueryDialog(queryString);
        return queryDialog;
    }

    @Override
    public void agregarObjeto(Usuario t, Vector dato) {
        dato.add(t.getNick());
    }

    /*
    @Override
    public Boolean buscarObjeto(Usuario t, Object valor) {
        return t.getNick().equals(valor.toString());
    }*/
    
}
