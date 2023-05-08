/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servicios.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.util.Vector;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;import java.util.Map;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public class OrdenTrabajoBusqueda implements InterfaceModelFind<OrdenTrabajo>
{

    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<>();
        titulo.add(new ColumnaDialogo("Código OT",0.2d));
        titulo.add(new ColumnaDialogo("Código ",0.2d));
        titulo.add(new ColumnaDialogo("Modelo ",0.2d));
        titulo.add(new ColumnaDialogo("Cliente",0.5d));
        titulo.add(new ColumnaDialogo("Descripción",0.3d));
        titulo.add(new ColumnaDialogo("Estado",0.15d));
        titulo.add(new ColumnaDialogo("Ingreso",0.15d));
        return titulo;
    }

    @Override
    public QueryDialog getConsulta(String filter,Map<Integer,Object> mapFiltro) {
        //OrdenTrabajo ordenTrabajo;
        //ordenTrabajo.getId()
        //ordenTrabajo.getCliente().getNombreSimple();
        String queryString = "SELECT o FROM OrdenTrabajo o WHERE ";
        queryString+=" o.estado=?2 OR CAST(o.id CHAR(64)) like ?1 ORDER BY o.id desc ";
        QueryDialog queryDialog=new QueryDialog(queryString);
        queryDialog.agregarParametro(1, filter);
        queryDialog.agregarParametro(2, GeneralEnumEstado.ACTIVO.getEstado());
        return queryDialog;
    }

    @Override
    public void agregarObjeto(OrdenTrabajo o, Vector dato) 
    {
        dato.add(o.getId());
        dato.add((o.getObjetoMantenimiento()!=null)?o.getObjetoMantenimiento().getCodigo():"");
        dato.add((o.getObjetoMantenimiento()!=null)?o.getObjetoMantenimiento().getModelo():"");
        dato.add(o.getCliente().getNombresCompletos());
        dato.add(o.getDetalles().get(0).getDescripcion());
        dato.add(o.getEstadoEnum().getNombre());
        dato.add(o.getFechaIngreso());
    }

    @Override
    public Vector<String> getNamePropertysObject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
