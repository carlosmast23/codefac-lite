/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfaceModelFind;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.NotaCredito;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.NotaCreditoEnumEstado;
import java.util.Vector;

/**
 *
 * @author PC
 */
public class NotaCreditoBusqueda implements InterfaceModelFind <NotaCredito>
{

    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<>();
        titulo.add(new ColumnaDialogo("Id", 0.2d));
        titulo.add(new ColumnaDialogo("preimpreso", 0.2d));
        titulo.add(new ColumnaDialogo("cliente", 0.3d));
        titulo.add(new ColumnaDialogo("fecha", 0.2d));
        titulo.add(new ColumnaDialogo("total", 0.1d));        
        return titulo;
    }

    @Override
    public QueryDialog getConsulta(String filter) {
        String queryString = "SELECT u FROM NotaCredito u WHERE u.estado<>?1 ";
        //queryString+="AND ( u.cliente.razonSocial like ?4 )";
        queryString+="AND ( LOWER(u.cliente.razonSocial) like ?4 OR CONCAT(u.secuencial, '') like ?3 )";
        queryString+=" ORDER BY u.secuencial+0 DESC ";
        
        QueryDialog queryDialog=new QueryDialog(queryString);
        queryDialog.agregarParametro(1,ComprobanteEntity.ComprobanteEnumEstado.ELIMINADO.getEstado());
        //queryDialog.agregarParametro(2,NotaCreditoEnumEstado.ANULADO.getEstado());
        //queryDialog.agregarParametro(3,NotaCreditoEnumEstado.SIN_AUTORIZAR.getEstado());
        queryDialog.agregarParametro(3,filter);
        queryDialog.agregarParametro(4,filter);
        return queryDialog;
    }

    @Override
    public void agregarObjeto(NotaCredito t, Vector dato) {
        dato.add(t.getId());
        dato.add(t.getPreimpreso());
        dato.add(t.getCliente().getRazonSocial());
        dato.add(t.getFechaEmision());
        dato.add(t.getTotal());
    }

    /*
    @Override
    public Boolean buscarObjeto(NotaCredito t, Object valor) {
        return true;
    }*/
    
}
