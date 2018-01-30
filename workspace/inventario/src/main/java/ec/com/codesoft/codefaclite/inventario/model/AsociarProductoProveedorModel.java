/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.inventario.model;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.inventario.busqueda.ProductoBusquedaDialogo;
import ec.com.codesoft.codefaclite.inventario.busqueda.ProveedorBusquedaDialogo;
import ec.com.codesoft.codefaclite.inventario.panel.AsociarProductoProveedorPanel;
import ec.com.codesoft.codefaclite.servidor.entity.Persona;
import ec.com.codesoft.codefaclite.servidor.entity.Producto;
import ec.com.codesoft.codefaclite.servidor.entity.ProductoProveedor;
import ec.com.codesoft.codefaclite.servidor.entity.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidor.service.ProductoProveedorService;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Carlos
 */
public class AsociarProductoProveedorModel extends AsociarProductoProveedorPanel{
    /**
     * Entidad mediante la cual se va a referenciar al objeto en pantalla
     */
    private ProductoProveedor productoProveedor;
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        iniciarValores();
        agregarListenerBotones();
        agregarListenerCombo();
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        
    }
    
    private void setearValores()
    {
        productoProveedor.setDescripcion(getTxtDescripcion().getText());
        productoProveedor.setCosto(new BigDecimal(getTxtCosto().getText()));
        productoProveedor.setEstado("a");
        //productoProveedor.setProducto(getPr);
    }

    @Override
    public void editar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void imprimir() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buscar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void limpiar() {
        limpiarVariables();
    }

    @Override
    public String getNombre() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getURLAyuda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void agregarListenerBotones() {
        getBtnBuscarProveedor().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProveedorBusquedaDialogo buscarBusquedaDialogo = new ProveedorBusquedaDialogo();
                BuscarDialogoModel buscarDialogo = new BuscarDialogoModel(buscarBusquedaDialogo);
                buscarDialogo.setVisible(true);
                Persona proveedor = (Persona) buscarDialogo.getResultado();
                if (proveedor != null) {
                    String identificacion=proveedor.getIdentificacion();
                    String nombre =proveedor.getRazonSocial();
                    getTxtProveedor().setText(identificacion+" - "+nombre);
                    cargarTablaProductoProveedor(proveedor);
                }
            }
        });
        
        getBtnProductoBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductoBusquedaDialogo productoDialogo = new ProductoBusquedaDialogo();
                BuscarDialogoModel buscarDialogo = new BuscarDialogoModel(productoDialogo);
                buscarDialogo.setVisible(true);
                Producto producto = (Producto) buscarDialogo.getResultado();
                if (producto != null) {
                    String nombre=producto.getNombre();
                    getTxtProducto().setText(nombre);
                }
                
            }
        });
    }
    
    private void cargarTablaProductoProveedor(Persona persona)
    {
        String[] titulos={"Producto","Costo"};
        DefaultTableModel modeloTabla=new DefaultTableModel(titulos,0);
        Map<String,Object> parametros=new HashMap<String,Object>();
        parametros.put("proveedor",persona);
        
        ProductoProveedorService servicio=new ProductoProveedorService();
        List<ProductoProveedor> lista= servicio.obtenerPorMap(parametros);
        List<String> fila=new ArrayList<String>();
        for (ProductoProveedor productoProveedor : lista) {
            fila.add(productoProveedor.getProducto().getNombre());
            fila.add(productoProveedor.getCosto().toString());
            modeloTabla.addColumn(fila);
        }
        getTblProveedorProducto().setModel(modeloTabla);
    }

    private void agregarListenerCombo() {
        
    }

    private void iniciarValores() {
        getCmbIva().removeAllItems();
        EnumSiNo[] enums= EnumSiNo.values();
        for (EnumSiNo aEnum : enums) {
            getCmbIva().addItem(aEnum);
        }
    }

    private void limpiarVariables() {
        productoProveedor=new ProductoProveedor();
    }
    
}
