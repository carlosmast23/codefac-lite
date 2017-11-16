/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.crm.model;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.crm.busqueda.ProductoBusquedaDialogo;
import ec.com.codesoft.codefaclite.crm.panel.ProductoForm;
import ec.com.codesoft.codefaclite.servidor.entity.Producto;
import ec.com.codesoft.codefaclite.servidor.service.ProductoService;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author PC
 */
public class ProductoModel extends ProductoForm
{
    private Producto producto;
    private ProductoService productoService;
    private BigDecimal d;
    
    public ProductoModel()
    {
        productoService = new ProductoService();
    }
    
    @Override
    public void grabar() throws ExcepcionCodefacLite 
    {
        producto = new Producto();
        producto.setCodigoPrincipal(getjTextCodigoPrincipal().getText());
        producto.setCodigoAuxiliar(getjTextCodigoAuxiliar().getText());
        if(getjComboTipoProducto().getSelectedItem().equals("Bien"))
        {
            producto.setTipoProducto("B");
        }
        else
        {
            producto.setTipoProducto("S");
        }
        
        producto.setNombre(getjTextNombre().getText()); 
        d = new BigDecimal(getjTextValorUnitario().getText());
        producto.setValorUnitario(d);
        productoService.grabar(producto);
    }

    @Override
    public void editar() throws ExcepcionCodefacLite {
        producto = new Producto();
        producto.setCodigoPrincipal(getjTextCodigoPrincipal().getText());
        producto.setCodigoAuxiliar(getjTextCodigoAuxiliar().getText());
        if(getjComboTipoProducto().getSelectedItem().equals("Bien"))
        {
            producto.setTipoProducto("B");
        }
        else
        {
            producto.setTipoProducto("S");
        }
        
        producto.setNombre(getjTextNombre().getText()); 
        d = new BigDecimal(getjTextValorUnitario().getText());
        producto.setValorUnitario(d);
        
        productoService.grabar(producto);
    }

    @Override
    public void eliminar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void imprimir() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buscar() throws ExcepcionCodefacLite {
        ProductoBusquedaDialogo productoBusquedaDialogo = new ProductoBusquedaDialogo();
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(productoBusquedaDialogo);
        buscarDialogoModel.setVisible(true);
        producto = (Producto) buscarDialogoModel.getResultado();

        if(producto == null)
        {
            throw new ExcepcionCodefacLite("Excepcion lanzada desde buscar producto vacio");
        }
        
        getjTextCodigoPrincipal().setText(producto.getCodigoPrincipal());
        getjTextCodigoAuxiliar().setText(producto.getCodigoAuxiliar());
        if(producto.getTipoProducto().equals('B')){
            getjComboTipoProducto().setSelectedItem("Bien");
        }
        else{
            getjComboTipoProducto().setSelectedItem("Servicio");
        }
    }

    @Override
    public void limpiar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getNombre() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getURLAyuda() {
        return "https://support.office.com/es-es";
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() 
    {
        Map<Integer,Boolean> permisos=new HashMap<Integer,Boolean>();
        permisos.put(GeneralPanelInterface.BOTON_NUEVO,true);
        permisos.put(GeneralPanelInterface.BOTON_GRABAR,true);
        permisos.put(GeneralPanelInterface.BOTON_BUSCAR, true);
        permisos.put(GeneralPanelInterface.BOTON_ELIMINAR, true);
        permisos.put(GeneralPanelInterface.BOTON_IMPRIMIR, true);
        permisos.put(GeneralPanelInterface.BOTON_AYUDA, true);
        return permisos;
    }
    
}
