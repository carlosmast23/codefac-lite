/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servicios.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfaceModelFind;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Presupuesto;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.util.Vector;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public class PresupuestoBusqueda implements InterfaceModelFind<Presupuesto>
{

    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<>();
        titulo.add(new ColumnaDialogo("Código",0.2d));
        titulo.add(new ColumnaDialogo("Descripción",0.3d));
        titulo.add(new ColumnaDialogo("Estado",0.15d));
        titulo.add(new ColumnaDialogo("Total",0.10d));
        titulo.add(new ColumnaDialogo("Fecha ingreso",0.15d));
        titulo.add(new ColumnaDialogo("Fecha fin",0.15d));
        return titulo;
    }

    @Override
    public QueryDialog getConsulta(String filter) {
        //String queryString = "SELECT p FROM Presupuesto p WHERE ";
        //String queryString = "SELECT p FROM Presupuesto p WHERE CAST(p.id CHAR(64) ) like ?1 and p.estado=?2 ";
        String queryString = "SELECT p FROM Presupuesto p WHERE CAST(p.id CHAR(64) ) like ?1 order by p.id desc ";
        //queryString+=" ( LOWER(p.id) like ?1 ) and p.estado=?2 ";
        QueryDialog queryDialog=new QueryDialog(queryString);
        queryDialog.agregarParametro(1, filter);
        //queryDialog.agregarParametro(2, GeneralEnumEstado.ACTIVO.getEstado());
        return queryDialog;
    }

    @Override
    public void agregarObjeto(Presupuesto p, Vector dato) 
    {
        dato.add(p.getId());
        dato.add(p.getDescripcion());
        dato.add((p.getEstadoEnum()!=null)?p.getEstadoEnum().getNombre():"Sin estado");
        dato.add(p.getTotalVenta());
        dato.add(p.getFechaPresupuesto());
        dato.add(p.getFechaValidez());
    }
    
}
