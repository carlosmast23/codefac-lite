/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.inventario.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CategoriaProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CategoriaProductoEnumEstado;
import java.util.Vector;

/**
 *
 * @author Carlos
 */
public class CatalogoProductoBusquedaDialogo implements InterfaceModelFind<CatalogoProducto>{

    private Empresa empresa;

    public CatalogoProductoBusquedaDialogo(Empresa empresa) {
        this.empresa = empresa;
    }
    
    
    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<>();
        titulo.add(new ColumnaDialogo("Nombre", 0.5d));
        titulo.add(new ColumnaDialogo("Tipo", 0.3d));
        titulo.add(new ColumnaDialogo("Iva", 0.2d));
        return titulo;
    }

    @Override
    public QueryDialog getConsulta(String filter) {
        //CatalogoProducto c;
        //c.getEmpresa();
        String queryString = "SELECT u FROM CatalogoProducto u WHERE u.empresa=?2 and ";
        queryString += " ( LOWER(u.nombre) LIKE ?1 )";
        QueryDialog queryDialog = new QueryDialog(queryString);
        queryDialog.agregarParametro(1,filter);
        queryDialog.agregarParametro(2,empresa);
        return queryDialog;
    }

    @Override
    public void agregarObjeto(CatalogoProducto t, Vector dato) {
        dato.add(t.getNombre());
        dato.add(t.getModuloCodefacEnum().getNombre());
        dato.add(t.getIva().getTarifa());
    }
    
}
