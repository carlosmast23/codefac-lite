/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.inventario.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Bodega;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.util.Vector;

/**
 *
 * @author CodesoftDesarrollo
 */
public class BodegaBusquedaDialogo implements InterfaceModelFind<Bodega> {
    
    private Empresa empresa;

    public BodegaBusquedaDialogo(Empresa empresa) {
        this.empresa = empresa;
    }
    
    
    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<>();
        titulo.add(new ColumnaDialogo("Nombre", 0.2d));
        titulo.add(new ColumnaDialogo("Descripción", 0.3d));
        titulo.add(new ColumnaDialogo("Tipo", 0.1d));
        titulo.add(new ColumnaDialogo("Sucursal", 0.1d));
        titulo.add(new ColumnaDialogo("Encargado", 0.3d));
        return titulo;
    }

    @Override
    public QueryDialog getConsulta(String filter) {
        String queryString = "SELECT u FROM Bodega u WHERE (u.empresa=?3 or u.empresa=NULL) and (u.estado=?1) and";
        queryString += " ( LOWER(u.nombre) LIKE ?2 )";
        QueryDialog queryDialog = new QueryDialog(queryString);
        queryDialog.agregarParametro(1, GeneralEnumEstado.ACTIVO.getEstado());
        queryDialog.agregarParametro(2,filter);
        queryDialog.agregarParametro(3,empresa);
        return queryDialog;
    }

    @Override
    public void agregarObjeto(Bodega t, Vector dato) {
        dato.add(t.getNombre());
        dato.add(t.getDescripcion());
        dato.add(t.getTipoBodegaEnum().getNombre());
        if(t.getSucursal()!=null)
        {
            dato.add(t.getSucursal().getNombre());
        }
        else
        {
            dato.add(Sucursal.getSucursalPermitirTodos().getNombre());
        }        
        
        dato.add(t.getEncargado());
    }

    /*
    @Override
    public Boolean buscarObjeto(Bodega t, Object valor) {
        return t.getNombre().equals(valor.toString());
    }
*/

}
