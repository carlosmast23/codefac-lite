/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.inventario.model;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.inventario.busqueda.CatalogoProductoBusquedaDialogo;
import ec.com.codesoft.codefaclite.inventario.busqueda.CategoriaProductoBusquedaDialogo;
import ec.com.codesoft.codefaclite.inventario.busqueda.ProductoBusquedaDialogo;
import ec.com.codesoft.codefaclite.inventario.busqueda.ProveedorBusquedaDialogo;
import ec.com.codesoft.codefaclite.inventario.panel.ReporteInventarioStockPanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Bodega;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CategoriaProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Perfil;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ProductoProveedor;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.BodegaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ProductoProveedorServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ProductoServiceIf;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DesarrolloSoftware
 */
public class ReporteInventarioStockModel extends ReporteInventarioStockPanel
{
    private Producto producto;
    private Persona proveedor; 
    private CategoriaProducto categoriaProducto;
    private Boolean todos;
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        iniciarValores();
        addListenerBotones();
        addCheckListener();
        addComboListener();
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void imprimir() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void limpiar() {
        this.producto = null;
        this.proveedor = null;
        this.categoriaProducto = null;
        getTxtNombre().setText("");
   }

    @Override
    public String getURLAyuda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        Map<Integer, Boolean> permisos = new HashMap<Integer, Boolean>();
        permisos.put(GeneralPanelInterface.BOTON_NUEVO, true);
        permisos.put(GeneralPanelInterface.BOTON_GRABAR, false);
        permisos.put(GeneralPanelInterface.BOTON_BUSCAR, false);
        permisos.put(GeneralPanelInterface.BOTON_ELIMINAR, false);
        permisos.put(GeneralPanelInterface.BOTON_IMPRIMIR, true);
        permisos.put(GeneralPanelInterface.BOTON_AYUDA, true);
        return permisos;
    }

    @Override
    public List<String> getPerfilesPermisos() {
        List<String> permisos = new ArrayList<String>();
        permisos.add(Perfil.PERFIl_ADMINISTRADOR);
        permisos.add(Perfil.PERFIl_OPERADOR);
        return permisos;
    }

    @Override
    public BuscarDialogoModel obtenerDialogoBusqueda() {
        BuscarDialogoModel buscarDialogoModel = null;
        String opcionReporte = (String) getCmbTipoReporte().getSelectedItem();
        switch(opcionReporte)
        {
            case "Producto":
                ProductoBusquedaDialogo busquedaDialogo = new ProductoBusquedaDialogo();
                buscarDialogoModel = new BuscarDialogoModel(busquedaDialogo);
            break;
            case "Proveedor":
                ProveedorBusquedaDialogo proveedorBusquedaDialogo = new ProveedorBusquedaDialogo();
                buscarDialogoModel = new BuscarDialogoModel(proveedorBusquedaDialogo);
            break;
            case "Categoria":
                CategoriaProductoBusquedaDialogo categoriaProductoBusquedaDialogo = new CategoriaProductoBusquedaDialogo();
                buscarDialogoModel = new BuscarDialogoModel(categoriaProductoBusquedaDialogo);
            break;
        }
        return buscarDialogoModel;
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        String opcionReporte = (String) getCmbTipoReporte().getSelectedItem();
        switch(opcionReporte)
        {
            case "Producto":
                producto = (Producto) entidad;
                getTxtNombre().setText(" " + producto.getNombre()+ " - " + producto.getCodigoPersonalizado());
            break;
            case "Proveedor":
                proveedor =(Persona) entidad;
                getTxtNombre().setText(" " + proveedor.getIdentificacion() + " - " + proveedor.getRazonSocial());
            break;
            case "Categoria":
                categoriaProducto = (CategoriaProducto) entidad;
                getTxtNombre().setText(" " + categoriaProducto.getNombre() + " " + categoriaProducto.getDescripcion());
                
            break;
            default:
                getTxtNombre().setText("");
            break;
        }
        
    }
    
    private void iniciarValores() {
        try {
            BodegaServiceIf bodegaServiceIf = ServiceFactory.getFactory().getBodegaServiceIf();
            List<Bodega> bodegas = bodegaServiceIf.obtenerTodos();
            getCmbBodega().removeAllItems();
            for (Bodega bodega : bodegas) {
                getCmbBodega().addItem(bodega);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(ReporteInventarioStockModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void addListenerBotones()
    {
        getBtnBuscarGenerica().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(getCmbTipoReporte().getSelectedItem().equals("Producto"))
                {
                    ProductoBusquedaDialogo busquedaDialogo = new ProductoBusquedaDialogo();
                    BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(busquedaDialogo);
                    buscarDialogoModel.setVisible(true);
                    Producto productoTemp = (Producto) buscarDialogoModel.getResultado();
                    if(productoTemp != null)
                    {
                        limpiar();
                        producto = productoTemp;
                        getTxtNombre().setText(" " + producto.getNombre()+ " - " + producto.getCodigoPersonalizado());
                        
                    }
                }
                else if(getCmbTipoReporte().getSelectedItem().equals("Proveedor"))
                {
                    ProveedorBusquedaDialogo busquedaDialogo = new ProveedorBusquedaDialogo();
                    BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(busquedaDialogo);
                    buscarDialogoModel.setVisible(true);
                    Persona proveedorTemp = (Persona) buscarDialogoModel.getResultado();
                    if(proveedorTemp != null)
                    {
                        limpiar();
                        proveedor = proveedorTemp;
                        getTxtNombre().setText(" " + proveedor.getNombreSimple() + " " + proveedor.getIdentificacion());
                    }
                }
                else if(getCmbTipoReporte().getSelectedItem().equals("Categoria"))
                {
                    CatalogoProductoBusquedaDialogo busquedaDialogo = new CatalogoProductoBusquedaDialogo(session.getEmpresa());
                    BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(busquedaDialogo);
                    buscarDialogoModel.setVisible(true);
                    CategoriaProducto categoriaProductoTemp = (CategoriaProducto) buscarDialogoModel.getResultado();
                    if(categoriaProductoTemp != null)
                    {
                        limpiar();
                        categoriaProducto = categoriaProductoTemp;
                        getTxtNombre().setText(" " + categoriaProducto.getNombre() + " " + categoriaProducto.getDescripcion());
                    }
                }else
                {
                    DialogoCodefac.mensaje("Advertencia", "Seleccione una opción valida", DialogoCodefac.MENSAJE_ADVERTENCIA);                
                }
            }
        });
        
        getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String opcionReporte = (String) getCmbTipoReporte().getSelectedItem();
                switch(opcionReporte)
                {
                    case "Producto":
                        if(!todos){
                            //buscarProducto();
                            crearMapPorProducto();
                        }else{
                            //buscarTodosProducto();
                            crearMapPorProducto();
                        }
//                      if(productoProveedores != null && productoProveedores.size() > 0){
//                          mostrarDatosTablaProducto();
//                      }else{
//                          DialogoCodefac.mensaje("Producto", "No existen Proveedores para el Producto", DialogoCodefac.MENSAJE_ADVERTENCIA);
//                      }
                    break;
                    case "Proveedor":
                        if(!todos){
//                                buscarPorProveedor();
//                                crearMapPorProveedor();
                        }else{
//                                buscarTodosProductoProveedor();
//                                crearMapPorProveedor();
                        }
//                      if(productoProveedores != null && productoProveedores.size() > 0){
//                          mostrarDatosTablaProveedor();
//                      }else{
//                          DialogoCodefac.mensaje("Proveedor", "No existen Producots para el Proveedor", DialogoCodefac.MENSAJE_ADVERTENCIA);
//                      }
                    break;
                    case "Categoria":
                        if(!todos)
                        {
                            
                        }else
                        {
                            
                        }
                    default:
                        DialogoCodefac.mensaje("Advertencia", "Seleccione una opción valida", DialogoCodefac.MENSAJE_ADVERTENCIA);
                    break;                
                }
            }
        });
    }
    
    public void addComboListener()
    {
        getCmbTipoReporte().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiar();                
            }
        });
    }
    
    public void addCheckListener()
    {
        getCheckTodos().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(getCheckTodos().isSelected())
                {
                    todos = true;
                    getBtnBuscarGenerica().setEnabled(false);
                    getTxtNombre().setEnabled(false);
                    getTxtNombre().setText("");
                }
                else
                {
                    todos = false;
                    getBtnBuscarGenerica().setEnabled(true);
                    getTxtNombre().setEnabled(true);
                    getTxtNombre().setText("");
                }
            }
        });
    }
    
    public List<ProductoProveedor> buscarProducto() throws RemoteException, ServicioCodefacException
    {
        ProductoProveedorServiceIf serviceIf = ServiceFactory.getFactory().getProductoProveedorServiceIf();
        //Map<String,Object> parametros = new HashMap<>();
        //parametros.put("producto", this.producto);
        List<ProductoProveedor> productos = serviceIf.buscarPorProductoActivo(producto);
        return productos;
    }
    
    public List<ProductoProveedor> buscarTodosProducto() throws RemoteException, RemoteException
    {
        ProductoProveedorServiceIf serviceIf = ServiceFactory.getFactory().getProductoProveedorServiceIf();
        List<ProductoProveedor> productos = serviceIf.obtenerTodos();
        return productos;
    }
    
    
    public Map<Producto, List<ProductoProveedor>> mapProducto(List<ProductoProveedor> productos)
    {
        Map<Producto, List<ProductoProveedor>> mapProductos = new TreeMap<>(new Comparator<Producto>() {
            @Override
            public int compare(Producto p1, Producto p2){
                return p1.compareTo(p2);
            }
        });
        
       for(ProductoProveedor pp : productos) 
       {
           if(mapProductos.get(pp.getProducto()) == null)
           {
               List<ProductoProveedor> pps = new ArrayList<>();
               pps.add(pp);
               mapProductos.put(pp.getProducto(), pps);
           }else{
               mapProductos.get(pp.getProducto()).add(pp);
           }  
       }
       
       return mapProductos;
    }
    
    
    
    
    public void crearMapPorProducto()
    {
        
    }
    
    public void buscarPorProveedor()
    {
        
    }
    
    public void buscarPorTodosProveedor(){
        
    }
    
    public void buscarPorCategoria(){
        
    }
    
    public void buscarPorTodosCategoria(){
        
    }
}
