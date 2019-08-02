/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfacesPropertisFindWeb;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.util.Vector;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;

/**
 *
 * @author PC
 */
public class ProductoBusquedaDialogo implements InterfaceModelFind<Producto> , InterfacesPropertisFindWeb
{
    private Empresa empresa;
    /**
     * Variable para hacer ese filtro cuando lo requiera
     */
    private EnumSiNo generarCodigoBarrasEnum; 

    public ProductoBusquedaDialogo(Empresa empresa) 
    {
        this.generarCodigoBarrasEnum = null; //Le pongo en null para que filtre todo
        this.empresa=empresa;
    }
    
    

    @Override
    public Vector<ColumnaDialogo> getColumnas() 
    {
        Vector<ColumnaDialogo> titulo = new Vector<>();
        titulo.add(new ColumnaDialogo("Codigo", 0.2d));
        titulo.add(new ColumnaDialogo("Nombre", 0.3d));
        titulo.add(new ColumnaDialogo("Precio Unit", 0.3d));
        titulo.add(new ColumnaDialogo("IVA", 0.1d));        
        titulo.add(new ColumnaDialogo("ICE", 0.1d));        
        return titulo;
    }

    @Override
    public void agregarObjeto(Producto t, Vector dato) 
    {
        dato.add(t.getCodigoPersonalizado());
        dato.add(t.getNombre());
        dato.add(t.getValorUnitario());
        dato.add(t.getCatalogoProducto().getIva().toString());
        if(t.getCatalogoProducto().getIce() != null){
            dato.add(t.getCatalogoProducto().getIce().toString());
        }
    }

    /*
    @Override
    public Boolean buscarObjeto(Producto t, Object valor) 
    {
        return t.getNombre().equals(valor.toString());   
    }*/

    @Override
    public QueryDialog getConsulta(String filter) {
        //Producto p;
        //p.getGenerarCodigoBarras()
        String queryString = "SELECT u FROM Producto u WHERE u.empresa=?4 and (u.estado=?1) ";
        
        if (generarCodigoBarrasEnum != null) {
            queryString+=" and  u.generarCodigoBarras=?3 ";
        }
        
        queryString+=" and ( LOWER(u.nombre) like ?2 OR u.codigoPersonalizado like ?2 ) ORDER BY u.codigoPersonalizado";

        QueryDialog queryDialog=new QueryDialog(queryString);
        queryDialog.agregarParametro(1,GeneralEnumEstado.ACTIVO.getEstado());
        queryDialog.agregarParametro(2,filter);
        
        if (generarCodigoBarrasEnum != null)
        {
            queryDialog.agregarParametro(3,generarCodigoBarrasEnum.getLetra());
        }
        queryDialog.agregarParametro(4,empresa);
        //queryDialog.agregarParametro(2,ProductoEnumEstado.INACTIVO.getEstado());
        return queryDialog;
    }

    public EnumSiNo getGenerarCodigoBarrasEnum() {
        return generarCodigoBarrasEnum;
    }

    public void setGenerarCodigoBarrasEnum(EnumSiNo generarCodigoBarrasEnum) {
        this.generarCodigoBarrasEnum = generarCodigoBarrasEnum;
    }

        @Override
    public Vector<String> getNamePropertysObject() {
        Vector<String> propiedades = new Vector<String>();
        propiedades.add("codigoPersonalizado");
        propiedades.add("nombre");
        propiedades.add("valorUnitario");
        propiedades.add("catalogoProducto.iva");
        propiedades.add("catalogoProducto.ice");
        return propiedades;
    }
    
    
    
}
