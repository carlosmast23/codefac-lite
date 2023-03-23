/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import java.util.Vector;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class ProductoImagenBusquedaDialogo extends ProductoBusquedaDialogo{
    
    public ProductoImagenBusquedaDialogo(Empresa empresa, Boolean productosVenta, Boolean productosCompra) {
        super(empresa, productosVenta, productosCompra);
    }
    
    @Override
    public Vector<ColumnaDialogo> getColumnas() 
    {
        Vector<ColumnaDialogo> titulo = new Vector<>();
        titulo.add(new ColumnaDialogo("Imagen", 0.3d));
        titulo.add(new ColumnaDialogo("Nombre", 0.3d));
        titulo.add(new ColumnaDialogo("Código", 0.2d));        
        titulo.add(new ColumnaDialogo("Pvp1 ", 0.05d));
        titulo.add(new ColumnaDialogo("Pvp1 + Iva ", 0.05d));
        titulo.add(new ColumnaDialogo("IVA", 0.05d));        
        return titulo;
    }
    
    @Override
    public Vector<String> getNamePropertysObject() {
        Vector<String> propiedades = new Vector<String>();
        propiedades.add("imagen");
        propiedades.add("nombre");
        propiedades.add("codigoPersonalizado");
        propiedades.add("valorUnitario");
        propiedades.add("valorUnitarioConIva");
        propiedades.add("catalogoProducto.iva");
        return propiedades;
    }
    
}
