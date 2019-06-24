/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfacesPropertisFindWeb;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.io.Serializable;
import java.util.Vector;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;

/**
 *
 * @author Carlos
 */
public class ProformaBusqueda implements InterfaceModelFind<Factura>, InterfacesPropertisFindWeb,Serializable {
    
    private static final long serialVersionUID = -1238278914412853685L;
    
    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<>();
        titulo.add(new ColumnaDialogo("Secuencial", 0.2d));
        titulo.add(new ColumnaDialogo("cliente", 0.3d));
        titulo.add(new ColumnaDialogo("documento", 0.15d));
        titulo.add(new ColumnaDialogo("estado", 0.15d));
        titulo.add(new ColumnaDialogo("fecha", 0.15d));
        titulo.add(new ColumnaDialogo("total", 0.1d));
        return titulo;
    }

    @Override
    public QueryDialog getConsulta(String filter) {
        String queryString = "SELECT u FROM Factura u WHERE u.estado=?1 ";

        queryString += " AND (u.codigoDocumento=?3) ";

        queryString += "AND ( LOWER(u.cliente.razonSocial) like ?2 OR CONCAT(u.secuencial, '') like ?2 )";
        queryString += " ORDER BY u.id DESC ";
        QueryDialog queryDialog = new QueryDialog(queryString);
        queryDialog.agregarParametro(1, GeneralEnumEstado.ACTIVO.getEstado());
        queryDialog.agregarParametro(2, filter);
        queryDialog.agregarParametro(3, DocumentoEnum.PROFORMA.getCodigo());

        return queryDialog;
    }

    @Override
    public void agregarObjeto(Factura t, Vector dato) {

        dato.add(t.getSecuencial());
        //System.out.println(t.getPreimpreso());
        dato.add(t.getCliente().getRazonSocial());
        DocumentoEnum documentoEnum = DocumentoEnum.obtenerDocumentoPorCodigo(t.getCodigoDocumento());
        dato.add(documentoEnum.getNombre()); //TODO: Veri si para cosnultar por documento sea una propiedad intrinsica de la factura        
        dato.add((t.getEstadoEnum() != null) ? t.getEstadoEnum().getNombre() : "Sin estado");

        dato.add(t.getFechaEmision());
        dato.add(t.getTotal());
    }

    @Override
    public Vector<String> getNamePropertysObject() {
        Vector<String> propiedades = new Vector<String>();
        propiedades.add("secuencial");
        propiedades.add("cliente.razonSocial");
        propiedades.add("");//TODO: Ver como puedo hacer para establecer una propiedad personalizada
        propiedades.add("estado");
        propiedades.add("fechaEmision");
        propiedades.add("total");
        return propiedades;
    }

}
