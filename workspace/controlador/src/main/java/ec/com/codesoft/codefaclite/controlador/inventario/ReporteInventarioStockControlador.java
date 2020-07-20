/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.inventario;

import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.CatalogoProductoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ProductoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ProveedorBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.controlador.vista.crm.RutaModelControlador;
import ec.com.codesoft.codefaclite.controlador.vista.factura.ModelControladorAbstract;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.interfaces.VistaCodefacIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Bodega;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CategoriaProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ProductoProveedor;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefacInterface;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Robert
 */
public class ReporteInventarioStockControlador extends ModelControladorAbstract<ReporteInventarioStockControlador.CommonIf, ReporteInventarioStockControlador.SwingIf, ReporteInventarioStockControlador.WebIf> implements VistaCodefacIf
{
  
    private List<String> filtroPorCategoria;
    private String elementoSeleccionadoFiltroPorCategoria;
    private List<Bodega> bodegas;
    private Bodega bodega;
    
    private Producto producto;
    private Persona proveedor; 
    private CategoriaProducto categoriaProducto;
    private Boolean todos;
    
    List<ProductoProveedor> productos;
    
    public ReporteInventarioStockControlador(MensajeVistaInterface mensajeVista, SessionCodefacInterface session, ReporteInventarioStockControlador.CommonIf interfaz, TipoVista tipoVista) {
        super(mensajeVista, session, interfaz, tipoVista);
    }

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {       
            this.elementoSeleccionadoFiltroPorCategoria = null;
            this.producto = null;
            this.proveedor = null;
            this.categoriaProducto = null;
            this.todos = false;
            
            filtroPorCategoria = new ArrayList<String>()
            {
                {
                    add("Seleccione busqueda");
                    add("Producto");
                    add("Proveedor");
                    add("Categoria");
                };
            };
            
            //Me sale error aqui no se porque
            //bodegas = ServiceFactory.getFactory().getBodegaServiceIf().obtenerActivosPorEmpresa(this.session.getEmpresa());
        
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite, RemoteException {
        this.iniciar();
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getURLAyuda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public InterfaceModelFind obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    ////////////////////////////////////////////////////////////////////////////
    //                      GET AND SET
    ////////////////////////////////////////////////////////////////////////////

    public List<String> getFiltroPorCategoria() {
        return filtroPorCategoria;
    }

    public void setFiltroPorCategoria(List<String> filtroPorCategoria) {
        this.filtroPorCategoria = filtroPorCategoria;
    }

    public String getElementoSeleccionadoFiltroPorCategoria() {
        return elementoSeleccionadoFiltroPorCategoria;
    }

    public void setElementoSeleccionadoFiltroPorCategoria(String elementoSeleccionadoFiltroPorCategoria) {
        this.elementoSeleccionadoFiltroPorCategoria = elementoSeleccionadoFiltroPorCategoria;
    }

    public List<Bodega> getBodegas() {
        return bodegas;
    }

    public void setBodegas(List<Bodega> bodegas) {
        this.bodegas = bodegas;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Persona getProveedor() {
        return proveedor;
    }

    public void setProveedor(Persona proveedor) {
        this.proveedor = proveedor;
    }

    public CategoriaProducto getCategoriaProducto() {
        return categoriaProducto;
    }

    public void setCategoriaProducto(CategoriaProducto categoriaProducto) {
        this.categoriaProducto = categoriaProducto;
    }

    public Boolean getTodos() {
        return todos;
    }

    public void setTodos(Boolean todos) {
        this.todos = todos;
    }

    public List<ProductoProveedor> getProductos() {
        return productos;
    }

    public void setProductos(List<ProductoProveedor> productos) {
        this.productos = productos;
    }
    
    
    ////////////////////////////////////////////////////////////////////////////
    //                      LISTENERS
    ////////////////////////////////////////////////////////////////////////////
    public void listenerBotonBuscarFiltroPorCategoria(){
        BuscarDialogoModel buscarDialogoModel;
        
        if(elementoSeleccionadoFiltroPorCategoria == null){
            mostrarMensaje(new CodefacMsj("Seleccione una opción valida", CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
            return;
        }
        
        switch(elementoSeleccionadoFiltroPorCategoria)
        {
            case "Producto":
                ProductoBusquedaDialogo productoBusquedaDialogo = new ProductoBusquedaDialogo(session.getEmpresa());
                buscarDialogoModel = new BuscarDialogoModel(productoBusquedaDialogo);
                buscarDialogoModel.setVisible(true);
                Producto productoTemp = (Producto) buscarDialogoModel.getResultado();
                if(productoTemp != null)
                {
                    producto = productoTemp;
                }
            break;
            case "Proveedor":
                ProveedorBusquedaDialogo proveedorBusquedaDialogo = new ProveedorBusquedaDialogo(session.getEmpresa());
                buscarDialogoModel = new BuscarDialogoModel(proveedorBusquedaDialogo);
                buscarDialogoModel.setVisible(true);
                Persona proveedorTemp = (Persona) buscarDialogoModel.getResultado();
                if(proveedorTemp != null)
                {
                    proveedor = proveedorTemp;
                }
                break;
            case "Categoria":
                CatalogoProductoBusquedaDialogo catalogoBusquedaDialogo = new CatalogoProductoBusquedaDialogo(session.getEmpresa());
                buscarDialogoModel = new BuscarDialogoModel(catalogoBusquedaDialogo);
                buscarDialogoModel.setVisible(true);
                CategoriaProducto categoriaProductoTemp = (CategoriaProducto) buscarDialogoModel.getResultado();
                if(categoriaProductoTemp != null)
                {
                    categoriaProducto = categoriaProductoTemp;
                }
                break;

        }
    }
    
    public void listenerBotonBuscar()  throws ExcepcionCodefacLite, RemoteException {
        //no entiendo el error
        Empresa empresa = this.session.getEmpresa();
                switch(elementoSeleccionadoFiltroPorCategoria)
                {
                    case "Producto":
                        try {
                            if(!todos){
                                productos = ServiceFactory.getFactory().getProductoProveedorServiceIf().buscarPorProductoActivo(producto);
                            }else{
                                productos = ServiceFactory.getFactory().getProductoProveedorServiceIf().obtenerTodos();
                            }
                        }catch (ServicioCodefacException ex) {
                            Logger.getLogger(ReporteInventarioStockControlador.class.getName()).log(Level.SEVERE, null, ex);
                            mostrarMensaje(new CodefacMsj(ex.getMessage(),CodefacMsj.TipoMensajeEnum.ERROR));
                            throw new ExcepcionCodefacLite(ex.getMessage());
                        }
                    break;
                    case "Proveedor":
                        try{
                            if(!todos){
                                productos = ServiceFactory.getFactory().getProductoProveedorServiceIf().buscarPorProveedorActivo(proveedor);
                            }else{
                                productos = ServiceFactory.getFactory().getProductoProveedorServiceIf().obtenerTodos();
                            }
                        }catch (ServicioCodefacException ex) {
                            Logger.getLogger(ReporteInventarioStockControlador.class.getName()).log(Level.SEVERE, null, ex);
                            mostrarMensaje(new CodefacMsj(ex.getMessage(),CodefacMsj.TipoMensajeEnum.ERROR));
                            throw new ExcepcionCodefacLite(ex.getMessage());
                        }
                    break;
                    case "Categoria":
                        if(!todos){
                            //CatalogoProducto catalogoProducto = ServiceFactory.getFactory().getCatalogoProductoServiceIf().
                        }else{
                            
                        }
                    default:
                        DialogoCodefac.mensaje("Advertencia", "Seleccione una opción valida", DialogoCodefac.MENSAJE_ADVERTENCIA);
                    break;                
                }
    }
    
    ////////////////////////////////////////////////////////////////////////////
    //                      INTERFACES Y CLASES
    ////////////////////////////////////////////////////////////////////////////
    public interface CommonIf
    {
        
    }
    
    public interface SwingIf extends ReporteInventarioStockControlador.CommonIf
    {   
        void addCheckListener();
    }
    
    public interface WebIf extends ReporteInventarioStockControlador.CommonIf
    {   
    }
    
}
