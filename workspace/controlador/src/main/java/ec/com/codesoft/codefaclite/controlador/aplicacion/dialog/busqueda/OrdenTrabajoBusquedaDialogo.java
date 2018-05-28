/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfaceModelFind;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ProductoEnumEstado;
import java.util.Vector;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public class OrdenTrabajoBusquedaDialogo implements InterfaceModelFind<OrdenTrabajo>
{

    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<>();
        titulo.add(new ColumnaDialogo("Codigo", 0.2d));
        titulo.add(new ColumnaDialogo("Nombre", 0.3d));
        titulo.add(new ColumnaDialogo("Precio Unit", 0.3d));
        titulo.add(new ColumnaDialogo("IVA", 0.1d));        
        titulo.add(new ColumnaDialogo("ICE", 0.1d)); 
        return titulo;
    }

    @Override
    public QueryDialog getConsulta(String filter) {
        String queryString = "SELECT ot FROM OrdenTrabajo ot WHERE (ot.estado=?1) and";
        queryString+=" ( LOWER(ot.codigo) like ?2 ) or ot.descripcion like lower(?3)";
        QueryDialog queryDialog=new QueryDialog(queryString);
        queryDialog.agregarParametro(1,GeneralEnumEstado.ACTIVO.getEstado());
        queryDialog.agregarParametro(2,filter);
        queryDialog.agregarParametro(3,filter);
        return queryDialog;   
    }

    @Override
    public void agregarObjeto(OrdenTrabajo t, Vector dato) {
        dato.add(t.getCodigo());
        dato.add(""+t.getCliente().getNombresCompletos());
        dato.add(t.getDescripcion());
        dato.add(t.getFechaIngreso());
    }
    
}
