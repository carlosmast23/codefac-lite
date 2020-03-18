/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.inventario.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Compra;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author PC
 */
public class CompraBusquedaDialogo implements InterfaceModelFind<Compra>
{
    private Empresa empresa;
    private GeneralEnumEstado estadoEnum;

    public CompraBusquedaDialogo(Empresa empresa) {
        this.empresa = empresa;
        this.estadoEnum=GeneralEnumEstado.ACTIVO;
    }

    public CompraBusquedaDialogo(Empresa empresa, GeneralEnumEstado estadoEnum) {
        this.empresa = empresa;
        this.estadoEnum = estadoEnum;
    }

    
    
    @Override
    public Vector<ColumnaDialogo> getColumnas() 
    {
        Vector<ColumnaDialogo> titulo = new Vector<>();
        titulo.add(new ColumnaDialogo("Preimpreso", 0.2d));
        titulo.add(new ColumnaDialogo("Proveedor", 0.3d));
        titulo.add(new ColumnaDialogo("IVA", 0.3d));
        titulo.add(new ColumnaDialogo("Total", 0.1d));        
        titulo.add(new ColumnaDialogo("Fecha", 0.1d));        
        return titulo;
    }

    @Override
    public void agregarObjeto(Compra t, Vector dato) 
    {
        dato.add(t.getPreimpreso());
        dato.add(t.getProveedor().getRazonSocial());
        dato.add(t.getIva()+"");
        dato.add(t.getTotal()+"");
        dato.add(t.getFechaFactura()+"");

    }
    /*

    @Override
    public Boolean buscarObjeto(Compra t, Object valor) 
    {
        return t.getPreimpreso().equals(valor.toString());   
    }*/

    @Override
    public QueryDialog getConsulta(String filter) {

        String queryString = "SELECT u FROM Compra u WHERE u.empresa=?4 and (u.codigoTipoDocumento=?1) and u.inventarioIngreso=?2 and ";
        queryString+=" ( LOWER(u.secuencial) like ?3 ) and u.estado=?5 ";
        QueryDialog queryDialog=new QueryDialog(queryString);
        queryDialog.agregarParametro(1,TipoDocumentoEnum.COMPRA_INVENTARIO.getCodigo());
        queryDialog.agregarParametro(2,EnumSiNo.NO.getLetra());
        queryDialog.agregarParametro(3,filter);
        queryDialog.agregarParametro(4,empresa);
        queryDialog.agregarParametro(5,estadoEnum.getEstado());
        
        return queryDialog;
    }
}
