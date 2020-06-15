/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.compra.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.compra.OrdenCompra;
import java.util.Vector;

/**
 *
 * @author Carlos
 */
public class OrdenCompraBusqueda implements InterfaceModelFind<OrdenCompra>
{

    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<>();
        titulo.add(new ColumnaDialogo("Codigo",0.2d));
        titulo.add(new ColumnaDialogo("Proveedor",0.3d));
        titulo.add(new ColumnaDialogo("tipo",0.15d));
        titulo.add(new ColumnaDialogo("fecha",0.15d));
        titulo.add(new ColumnaDialogo("total",0.1d));
        return titulo;
    }

    @Override
    public QueryDialog getConsulta(String filter) {
        OrdenCompra oc;
        String queryString = "SELECT c FROM OrdenCompra c WHERE CAST(c.id CHAR(64) ) like ?1";
        //queryString+="  c.id = ?1 ";
        QueryDialog queryDialog=new QueryDialog(queryString);
        queryDialog.agregarParametro(1,filter);
        return queryDialog;
    }

    @Override
    public void agregarObjeto(OrdenCompra t, Vector dato) {
        dato.add(t.getId());
        dato.add((t.getProveedor()!=null)?t.getProveedor().getRazonSocial():"");
        dato.add(t.getCodigoTipoDocumento()); 
        dato.add(t.getFechaIngreso());
        dato.add(t.getTotal());
    }

    /*
    @Override
    public Boolean buscarObjeto(Compra t, Object valor) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/

    @Override
    public Vector<String> getNamePropertysObject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
