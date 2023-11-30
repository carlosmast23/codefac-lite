/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;import java.util.Map;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ProductoComponente;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Zona;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.util.Vector;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class ProductoComponenteBusqueda implements InterfaceModelFind<ProductoComponente>
{
    //private Empresa empresa;

    public ProductoComponenteBusqueda() {
        //this.empresa = empresa;
    }
    
    

    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<ColumnaDialogo>();
        titulo.add(new ColumnaDialogo("Nombre", 0.2d));
        //titulo.add(new ColumnaDialogo("Descripción", 0.3d));
        return titulo;
    }

    @Override
    public QueryDialog getConsulta(String filter,Map<Integer,Object> mapFiltro) {
        //ProductoComponente;
        //Zona z;
        //z.getNombre();
        //z.getDescripcion();
        String queryString=" SELECT u FROM ProductoComponente u where u.estado=?1 and u.nombre like ?3 ";
        QueryDialog queryDialog = new QueryDialog(queryString);
        queryDialog.agregarParametro(1,GeneralEnumEstado.ACTIVO.getEstado());
        //queryDialog.agregarParametro(2, empresa);
        queryDialog.agregarParametro(3, filter);
        return queryDialog;
    }

    @Override
    public void agregarObjeto(ProductoComponente t, Vector dato) {
        dato.add(t.getNombre());
        //dato.add(t.getDescripcion());
    }

    @Override
    public Vector<String> getNamePropertysObject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}