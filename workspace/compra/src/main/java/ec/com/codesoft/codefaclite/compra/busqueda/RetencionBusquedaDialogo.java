/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.compra.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfaceModelFind;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Retencion;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.FechaFormatoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import java.util.Vector;

/**
 *
 * @author Carlos
 */
public class RetencionBusquedaDialogo implements InterfaceModelFind<Retencion> {

    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<>();
        titulo.add(new ColumnaDialogo("Preimpreso",0.2d));
        titulo.add(new ColumnaDialogo("Proveedor",0.3d));
        titulo.add(new ColumnaDialogo("Fecha Emisión",0.15d));
        return titulo;
    }

    @Override
    public QueryDialog getConsulta(String filter) {
        //Retencion ret;
        //ret.getEstado();
        String queryString = "SELECT r FROM Retencion r WHERE r.estado=?2 AND ";
        queryString += " ( LOWER(r.secuencial) like ?1 )";
        QueryDialog queryDialog = new QueryDialog(queryString);
        queryDialog.agregarParametro(1, filter);
        queryDialog.agregarParametro(2,GeneralEnumEstado.ACTIVO.getEstado());
        return queryDialog;
    }

    @Override
    public void agregarObjeto(Retencion t, Vector dato) {
        dato.add(t.getPreimpreso());
        dato.add(t.getProveedor().getNombresCompletos());
        dato.add(FechaFormatoEnum.ANIO_MES_DIA.getFecha(t.getFechaEmision()));
    }
    
}
