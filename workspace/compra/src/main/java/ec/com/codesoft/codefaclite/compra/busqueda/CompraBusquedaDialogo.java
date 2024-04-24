/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.compra.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;import java.util.Map;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Compra;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.util.Vector;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public class CompraBusquedaDialogo implements InterfaceModelFind<Compra>
{
    private Sucursal sucursal;
    private Boolean filtrarComprasSinProcesarRetencion;
    private Boolean filtrarOrdenCompra;

    public CompraBusquedaDialogo(Sucursal sucursal) {
        this.sucursal = sucursal;
        this.filtrarComprasSinProcesarRetencion=false;
        filtrarOrdenCompra=false;
    }
    
    public CompraBusquedaDialogo(Sucursal sucursal,Boolean filtrarComprasSinProcesarRetencion) {
        this.sucursal = sucursal;
        this.filtrarComprasSinProcesarRetencion=filtrarComprasSinProcesarRetencion;
        filtrarOrdenCompra=false;
    }
    
    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<>();
        titulo.add(new ColumnaDialogo("preimpreso",0.2d));
        titulo.add(new ColumnaDialogo("cliente",0.4d));
        titulo.add(new ColumnaDialogo("documento",0.15d));
        titulo.add(new ColumnaDialogo("tipo",0.15d));
        titulo.add(new ColumnaDialogo("INV",0.05d));
        titulo.add(new ColumnaDialogo("fecha",0.15d));
        titulo.add(new ColumnaDialogo("total",0.05d));
        return titulo;
    }

    @Override
    public QueryDialog getConsulta(String filter,Map<Integer,Object> mapFiltro) {
        //Compra compra;
        //compra.getCodigoDocumento();
        //compra.getSucursalEmpresa().
        //compra.getE
        //Compra c;
        //c.getEstadoEnum().ELIMINADO;
        
        String whereDocumentoFiltro="";
        if(filtrarOrdenCompra)
        {
            whereDocumentoFiltro=" and c.codigoDocumento=?5 ";
        }
        
        String queryString = "SELECT c FROM Compra c WHERE c.sucursalEmpresa=?4 and c.estado<>?3 and ";
        queryString+= " ( LOWER(c.secuencial) like ?1 OR  LOWER(c.razonSocial) like ?1 )";
        if(filtrarComprasSinProcesarRetencion)
        {
            queryString+= " and c.estadoRetencion=?2 "; 
        }
        
        queryString=queryString+whereDocumentoFiltro;
        
        queryString+=" order by c.id desc";
        
        QueryDialog queryDialog=new QueryDialog(queryString);
        queryDialog.agregarParametro(1,filter);
        
        if(filtrarComprasSinProcesarRetencion)
        {
            queryDialog.agregarParametro(2,Compra.RetencionEnumCompras.NO_EMITIDO.getEstado());
        }
        
        queryDialog.agregarParametro(3,GeneralEnumEstado.ELIMINADO.getEstado());
        queryDialog.agregarParametro(4,sucursal);
        
        if(filtrarOrdenCompra)
        {
            queryDialog.agregarParametro(5,DocumentoEnum.ORDEN_COMPRA.getCodigo());
        }
        
        return queryDialog;
    }

    @Override
    public void agregarObjeto(Compra t, Vector dato) {
        dato.add(t.getPreimpreso());
        System.out.println(t.getPreimpreso());
        dato.add(t.getProveedor().getRazonSocial());
        DocumentoEnum documentoEnum= DocumentoEnum.obtenerDocumentoPorCodigo(t.getCodigoDocumento());
        dato.add(documentoEnum.getNombre());
        dato.add(t.getCodigoTipoDocumento()); 
        dato.add((t.getInventarioIngresoEnum()!=null)?t.getInventarioIngresoEnum().getNombre():"");
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

    public Boolean getFiltrarOrdenCompra() {
        return filtrarOrdenCompra;
    }

    public void setFiltrarOrdenCompra(Boolean filtrarOrdenCompra) {
        this.filtrarOrdenCompra = filtrarOrdenCompra;
    }

    
    
}
