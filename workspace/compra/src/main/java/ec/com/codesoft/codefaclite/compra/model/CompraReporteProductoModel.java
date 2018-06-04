/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.compra.model;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.crm.busqueda.ProductoBusquedaDialogo;
import ec.com.codesoft.codefaclite.compra.panel.CompraReporteProductoPanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ProductoProveedor;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ProductoProveedorServiceIf;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.text.StyledEditorKit;

/**
 *
 * @author robert
 */
public class CompraReporteProductoModel  extends CompraReporteProductoPanel
{
    private Producto producto;
    private Boolean todos;
    private String opcionReporte;
            
    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        agregarListener();
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

//    @Override
    public String getNombre() {
        return "Compra Reporte Producto";
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
    
    public void agregarListener()
    {
        getBtnBuscarProducto().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                ProductoBusquedaDialogo productoBusquedaDialogo = new ProductoBusquedaDialogo();
                BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(productoBusquedaDialogo);
                buscarDialogoModel.setVisible(true);
                producto = (Producto) buscarDialogoModel.getResultado();
                if(producto != null)
                {
                    String nombre = producto.getNombre();
                    String codigo;
                    if(producto.getCodigoPersonalizado() != null){
                        codigo = producto.getCodigoPersonalizado();    
                    }else{
                        codigo = "";
                    }
                    
                    getTxtProducto().setText(nombre + " - " + codigo);
                }
            }
        });
        
        getBtnObtenerDatos().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                if(producto != null) 
                {
                    try
                    {
                        if(todos){
                            buscarPorProducto();
                        }else
                        {
                            buscarTodosLosProductos();
                        }
                                    
                    }
                    catch(ServicioCodefacException | RemoteException exc)
                    {
                        System.out.println("Se produjo un error en obtener los productosProveedores");
                    }
                    
                }
            }
        });
        
        getChckTodos().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(getChckTodos().isSelected())
                {
                    todos = true;
                }
                else
                {
                    todos = false;
                }
            }
        });
        
        getCmbTipoReporte().addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                opcionReporte = (String) getCmbTipoReporte().getSelectedItem();
               
                switch(opcionReporte)
                {
                    case "Proveedor":
                        break;
                    case "Producto":
                        break;
                    default:
                        DialogoCodefac.mensaje("Error", "Debe seleccionar un tipo de busqueda para el reporte", DialogoCodefac.MENSAJE_ADVERTENCIA);
                        break;
                }                      
            }
        });
    }
    
    public void buscarPorProducto() throws RemoteException, ServicioCodefacException
    {
        ProductoProveedorServiceIf productoProveedorServiceIf = ServiceFactory.getFactory().getProductoProveedorServiceIf();
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("producto", producto);
        List<ProductoProveedor> productoProveedores = productoProveedorServiceIf.obtenerPorMap(mapParametros);
        if(productoProveedores != null && productoProveedores.size() > 0)
        {
            for(ProductoProveedor productoProveedor : productoProveedores)
            {
                System.out.println("Producto -->" + productoProveedor.getProducto().getNombre());
                System.out.println("Proveedor -->" + productoProveedor.getProveedor());
            }
        }
        else
        {
            DialogoCodefac.mensaje("ProductoProveedor", "Error en busqueda", DialogoCodefac.MENSAJE_ADVERTENCIA);
        }
    }
    
    public void buscarTodosLosProductos()
    {
        
    }

    @Override
    public BuscarDialogoModel obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
