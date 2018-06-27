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
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoFacturacionEnumEstado;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Carlos
 */
public class FacturaBusqueda implements InterfaceModelFind<Factura> {

    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<>();
        //titulo.add(new ColumnaDialogo("Id", 0.2d));
        titulo.add(new ColumnaDialogo("preimpreso", 0.2d));
        titulo.add(new ColumnaDialogo("cliente", 0.3d));
        titulo.add(new ColumnaDialogo("documento", 0.15d));
        titulo.add(new ColumnaDialogo("tipo", 0.15d));
        titulo.add(new ColumnaDialogo("fecha", 0.15d));
        titulo.add(new ColumnaDialogo("total", 0.1d));        
        return titulo;
    }

    @Override
    public QueryDialog getConsulta(String filter) {
        String queryString = "SELECT u FROM Factura u WHERE u.estado<>?1 ";
        queryString+="AND ( LOWER(u.cliente.razonSocial) like ?2 OR CONCAT(u.secuencial, '') like ?2 )";
        QueryDialog queryDialog=new QueryDialog(queryString);
        queryDialog.agregarParametro(1,ComprobanteEntity.ComprobanteEnumEstado.ELIMINADO.getEstado());
        queryDialog.agregarParametro(2,filter);
        //queryDialog.agregarParametro(3,FacturaEnumEstado.SIN_AUTORIZAR.getEstado());
        return queryDialog;
    }

    @Override
    public void agregarObjeto(Factura t, Vector dato) {
        //dato.add(t.getId());
        dato.add(t.getPreimpreso());
        System.out.println(t.getPreimpreso());
        dato.add(t.getCliente().getRazonSocial());
        DocumentoEnum estadoEnum= DocumentoEnum.obtenerDocumentoPorCodigo(t.getCodigoDocumento());
        dato.add(estadoEnum.getNombre());
        
        TipoFacturacionEnumEstado tipoFactura=TipoFacturacionEnumEstado.getEnumByEstado(t.getTipoFacturacion());
        dato.add(tipoFactura.getNombre());
        
        dato.add(t.getFechaEmision());
        dato.add(t.getTotal());
    }
/*
    @Override
    public Boolean buscarObjeto(Factura t, Object valor) {
        //if(t.getCliente().getIdentificacion().indexOf(valor.toString())>=0 || t.getPreimpreso().indexOf(valor.toString())>=0)
        String preimpreso=t.getPreimpreso().toLowerCase();
        String valorBuscar=valor.toString().toLowerCase();
        if(preimpreso.indexOf(valorBuscar)>=0)
        {
            return true;
        }   
        else
        {
            return false;
        }  
        //return true;
    }*/
    
}
