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
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
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
        String queryString = "SELECT u FROM Usuario u WHERE (u.estado=?1 or u.estado=?2 ) and  ";
        queryString+=" ( LOWER(u.nick) like ?3 )";
        QueryDialog queryDialog=new QueryDialog(queryString);
        queryDialog.agregarParametro(1,GeneralEnumEstado.ACTIVO.getEstado());
        queryDialog.agregarParametro(2,GeneralEnumEstado.INACTIVO.getEstado());
        queryDialog.agregarParametro(3,filter);
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
