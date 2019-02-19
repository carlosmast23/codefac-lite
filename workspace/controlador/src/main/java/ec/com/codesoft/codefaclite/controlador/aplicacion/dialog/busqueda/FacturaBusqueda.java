/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfaceModelFind;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PersonaEstablecimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
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
    private Persona cliente;
    private PersonaEstablecimiento establecimiento;
    private EnumSiNo estadoEnviadoGuiaRemision;

    public FacturaBusqueda(Persona cliente) {
        this.cliente = cliente;    
    }
    
    public FacturaBusqueda(PersonaEstablecimiento establecimiento) {
        this.establecimiento = establecimiento;    
    }

    public FacturaBusqueda() {
        
    }
    
    
    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<>();
        //titulo.add(new ColumnaDialogo("Id", 0.2d));
        titulo.add(new ColumnaDialogo("preimpreso", 0.2d));
        titulo.add(new ColumnaDialogo("cliente", 0.3d));
        titulo.add(new ColumnaDialogo("documento", 0.15d));
        //titulo.add(new ColumnaDialogo("tipo", 0.15d));
        titulo.add(new ColumnaDialogo("estado", 0.15d));
        titulo.add(new ColumnaDialogo("fecha", 0.15d));
        titulo.add(new ColumnaDialogo("total", 0.1d));        
        return titulo;
    }

    @Override
    public QueryDialog getConsulta(String filter) {
        //Factura f;
        //f.getEstadoEnviadoGuiaRemision()
        //f.getSecuencial();
        String queryString = "SELECT u FROM Factura u WHERE u.estado<>?1 ";
        if(cliente!=null)
        {
            queryString+=" AND u.cliente=?10 ";
        }
        
        if(establecimiento!=null)
        {
            queryString+=" AND u.sucursal=?12 ";
        }
        
        if(estadoEnviadoGuiaRemision!=null)
        {
            queryString+=" AND u.estadoEnviadoGuiaRemision=?11";
        }
        
        queryString+=" AND (u.codigoDocumento=?3 OR  u.codigoDocumento=?4) ";
        
        queryString+="AND ( LOWER(u.cliente.razonSocial) like ?2 OR CONCAT(u.secuencial, '') like ?2 )";
        queryString+=" ORDER BY u.secuencial+0 DESC ";
        QueryDialog queryDialog=new QueryDialog(queryString);
        queryDialog.agregarParametro(1,ComprobanteEntity.ComprobanteEnumEstado.ELIMINADO.getEstado());
        queryDialog.agregarParametro(2,filter);
        queryDialog.agregarParametro(3,DocumentoEnum.FACTURA.getCodigo());
        queryDialog.agregarParametro(4,DocumentoEnum.NOTA_VENTA.getCodigo());
        
        if (cliente != null) {
            queryDialog.agregarParametro(10,cliente);
        }
        
        if (establecimiento != null) {
            queryDialog.agregarParametro(12, establecimiento);
        }
        
        if(estadoEnviadoGuiaRemision!=null)
        {
            queryDialog.agregarParametro(11,estadoEnviadoGuiaRemision.getLetra());
        }
        
        
        
        //queryDialog.agregarParametro(3,FacturaEnumEstado.SIN_AUTORIZAR.getEstado());
        return queryDialog;
    }

    @Override
    public void agregarObjeto(Factura t, Vector dato) {
        //dato.add(t.getId());
        dato.add(t.getPreimpreso());
        System.out.println(t.getPreimpreso());
        dato.add(t.getCliente().getRazonSocial());
        DocumentoEnum documentoEnum= DocumentoEnum.obtenerDocumentoPorCodigo(t.getCodigoDocumento());        
        dato.add(documentoEnum.getNombre()); //TODO: Veri si para cosnultar por documento sea una propiedad intrinsica de la factura
        
        //ComprobanteEntity.TipoEmisionEnum tipoFactura=ComprobanteEntity.TipoEmisionEnum.getEnumByEstado(t.getTipoFacturacion());
        //dato.add(tipoFactura.getNombre());
        
        dato.add((t.getEstadoEnum()!=null)?t.getEstadoEnum().getNombre():"Sin estado");
        
        dato.add(t.getFechaEmision());
        dato.add(t.getTotal());
    }

    public void setEstadoEnviadoGuiaRemision(EnumSiNo estadoEnviadoGuiaRemision) {
        this.estadoEnviadoGuiaRemision = estadoEnviadoGuiaRemision;
    }
    
    
    
}
