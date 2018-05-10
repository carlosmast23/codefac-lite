/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.compra.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfaceModelFind;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Compra;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoFacturacionEnumEstado;
import java.util.Vector;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public class CompraBusqueda implements InterfaceModelFind<Compra>
{

    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<>();
        titulo.add(new ColumnaDialogo("preimpreso",0.2d));
        titulo.add(new ColumnaDialogo("cliene",0.3d));
        titulo.add(new ColumnaDialogo("documento",0.15d));
        titulo.add(new ColumnaDialogo("tipo",0.15d));
        titulo.add(new ColumnaDialogo("fecha",0.15d));
        titulo.add(new ColumnaDialogo("total",0.1d));
        return titulo;
    }

    @Override
    public QueryDialog getConsulta(String filter) {
        String queryString = "SELECT c FROM Compra c WHERE ";
        queryString+=" ( LOWER(c.secuencial) like ?1 )";
        QueryDialog queryDialog=new QueryDialog(queryString);
        queryDialog.agregarParametro(1,filter);
        return queryDialog;
    }

    @Override
    public void agregarObjeto(Compra t, Vector dato) {
        dato.add(t.getPreimpreso());
        System.out.println(t.getPreimpreso());
        dato.add(t.getProveedor().getRazonSocial());
        DocumentoEnum estadoEnum= DocumentoEnum.obtenerDocumentoPorCodigo(t.getCodigoDocumento());
        dato.add(estadoEnum.getNombre());
        dato.add(t.getCodigoTipoDocumento()); 
        dato.add(t.getFechaFactura());
        dato.add(t.getTotal());
    }

    /*
    @Override
    public Boolean buscarObjeto(Compra t, Object valor) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/
    
}
