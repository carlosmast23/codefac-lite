/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.configuraciones.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfaceModelFind;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoVenta;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.util.Vector;

/**
 *
 * @author Carlos
 */
public class PuntoVentaBusquedaDialogo implements InterfaceModelFind<PuntoVenta>{

    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<ColumnaDialogo>();
        titulo.add(new ColumnaDialogo("Nombre", 100));
        titulo.add(new ColumnaDialogo("Secuencial", 100));
        //titulo.add(new ColumnaDialogo("Empresa", 100));
        return titulo;
    }

    @Override
    public QueryDialog getConsulta(String filter) {
        PuntoVenta pv;
        //pv.getEstado();
        //pv.getDescripcion();
        //pv.getDescripcion()
        String queryString = "SELECT pv FROM PuntoVenta pv WHERE pv.estado=?1 or ";
        queryString += "(LOWER(pv.descripcion) like ?2) ";
        QueryDialog queryDialog = new QueryDialog(queryString);
        queryDialog.agregarParametro(1, GeneralEnumEstado.ACTIVO.getEstado());
        queryDialog.agregarParametro(2, filter);
        
        return queryDialog;
        
    }

    @Override
    public void agregarObjeto(PuntoVenta t, Vector dato) {
        dato.add(t.getDescripcion());
        dato.add(t.getPuntoEmision());
        //dato.add(t.getPuntoEmision());
    }
    
}
