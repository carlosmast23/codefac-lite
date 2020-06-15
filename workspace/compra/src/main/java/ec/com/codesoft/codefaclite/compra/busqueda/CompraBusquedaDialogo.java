/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.compra.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Compra;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.util.Vector;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public class CompraBusquedaDialogo implements InterfaceModelFind<Compra>
{
    private Empresa empresa;

    public CompraBusquedaDialogo(Empresa empresa) {
        this.empresa = empresa;
    }
    
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
        //Compra compra;
        //compra.getE
        //Compra c;
        //c.getEstadoEnum().ELIMINADO;
        
        String queryString = "SELECT c FROM Compra c WHERE c.empresa=?4 and c.estado<>?3 and ";
        queryString+= " ( LOWER(c.secuencial) like ?1 )";
        queryString+= " and c.estadoRetencion=?2"; 
        QueryDialog queryDialog=new QueryDialog(queryString);
        queryDialog.agregarParametro(1,filter);
        queryDialog.agregarParametro(2,Compra.RetencionEnumCompras.NO_EMITIDO.getEstado());
        queryDialog.agregarParametro(3,GeneralEnumEstado.ELIMINADO.getEstado());
        queryDialog.agregarParametro(4,empresa);
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

    @Override
    public Vector<String> getNamePropertysObject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
