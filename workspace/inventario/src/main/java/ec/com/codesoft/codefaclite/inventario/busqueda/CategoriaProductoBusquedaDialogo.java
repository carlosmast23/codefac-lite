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
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.util.Vector;

/**
 *
 * @author CodesoftDesarrollo
 */
public class CategoriaProductoBusquedaDialogo implements InterfaceModelFind<CategoriaProducto> {
    private Empresa empresa;

    public CategoriaProductoBusquedaDialogo(Empresa empresa) {
        this.empresa = empresa;
    }
    
    
    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<>();
        titulo.add(new ColumnaDialogo("Nombre", 0.2d));
        titulo.add(new ColumnaDialogo("Descripción", 0.3d));
        return titulo;
    }

    @Override
    public QueryDialog getConsulta(String filter) {
        CategoriaProducto u=new CategoriaProducto();
        
        String queryString = "SELECT u FROM CategoriaProducto u WHERE u.empresa=?3 and (u.estado=?1) and";
        queryString += " ( LOWER(u.nombre) LIKE ?2 )";
        QueryDialog queryDialog = new QueryDialog(queryString);
        queryDialog.agregarParametro(1,GeneralEnumEstado.ACTIVO.getEstado());
        queryDialog.agregarParametro(2,filter);
        queryDialog.agregarParametro(3,empresa);
        
        return queryDialog;
    }

    @Override
    public void agregarObjeto(CategoriaProducto c, Vector dato) {
        dato.add(c.getNombre());
        dato.add(c.getDescripcion());
    }

    /*
    @Override
    public Boolean buscarObjeto(CategoriaProducto c, Object valor) {
        return c.getNombre().equals(valor.toString());
    }
*/

    @Override
    public Vector<String> getNamePropertysObject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
