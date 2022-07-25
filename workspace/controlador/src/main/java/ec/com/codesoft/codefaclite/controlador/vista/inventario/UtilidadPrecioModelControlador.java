/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vista.inventario;

import ec.com.codesoft.codefaclite.controlador.vista.factura.FacturaPedidoLoteModelControlador;
import ec.com.codesoft.codefaclite.controlador.vista.factura.ModelControladorAbstract;
import ec.com.codesoft.codefaclite.controlador.vistas.core.components.TableBindingImp;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.interfaces.VistaCodefacIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefacInterface;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class UtilidadPrecioModelControlador extends ModelControladorAbstract<UtilidadPrecioModelControlador.CommonIf,UtilidadPrecioModelControlador.SwingIf,UtilidadPrecioModelControlador.WebIf> implements VistaCodefacIf {

    private List<ProductoPrecioDataTable> productoList;
    private List<ProductoPrecioDataTable> productoSeleccionadoList;
    private ProductoPrecioDataTable productoSeleccionado;
    private TableBindingImp tableBindingControlador;
    
    public UtilidadPrecioModelControlador(MensajeVistaInterface mensajeVista, SessionCodefacInterface session, CommonIf interfaz, TipoVista tipoVista) {
        super(mensajeVista, session, interfaz, tipoVista);
    }

    
    public void listenerConsultarProductos()
    {
        try {
            List<Producto> productoTmpList= ServiceFactory.getFactory().getProductoServiceIf().obtenerTodosActivos(session.getEmpresa());
            castListDataTable(productoTmpList);
            
        } catch (RemoteException ex) {
            Logger.getLogger(UtilidadPrecioModelControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void castListDataTable(List<Producto> productoTmpList)
    {
        productoList=new ArrayList<ProductoPrecioDataTable>();
        for (Producto producto : productoTmpList) 
        {
            ProductoPrecioDataTable productoDataTable=new ProductoPrecioDataTable(
                    producto, 
                    BigDecimal.ONE, 
                    BigDecimal.ONE, 
                    BigDecimal.ONE, 
                    BigDecimal.ONE, 
                    BigDecimal.ONE, 
                    BigDecimal.ONE
            );
            
            productoList.add(productoDataTable);
        }
        
    }
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        this.productoList=new ArrayList<ProductoPrecioDataTable>();
        this.productoSeleccionadoList=new ArrayList<ProductoPrecioDataTable>();
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
    //                          METODOS GET AND SET
    ////////////////////////////////////////////////////////////////////////////

    public List<ProductoPrecioDataTable> getProductoList() {
        return productoList;
    }

    public void setProductoList(List<ProductoPrecioDataTable> productoList) {
        this.productoList = productoList;
    }

    public List<ProductoPrecioDataTable> getProductoSeleccionadoList() {
        return productoSeleccionadoList;
    }

    public void setProductoSeleccionadoList(List<ProductoPrecioDataTable> productoSeleccionadoList) {
        this.productoSeleccionadoList = productoSeleccionadoList;
    }
    
    public ProductoPrecioDataTable getProductoSeleccionado() {
        return productoSeleccionado;
    }

    public void setProductoSeleccionado(ProductoPrecioDataTable productoSeleccionado) {
        this.productoSeleccionado = productoSeleccionado;
    }

    public TableBindingImp getTableBindingControlador() {
        return tableBindingControlador;
    }

    public void setTableBindingControlador(TableBindingImp tableBindingControlador) {
        this.tableBindingControlador = tableBindingControlador;
    }
    
    
    
    
    ///////////////////////////////////////////////////////////////////////////
    //             METODOS QUE CONTIENEN INTERFACES PARA LA IMPLEMTACION
    ///////////////////////////////////////////////////////////////////////////
    public interface CommonIf {
        //TODO: Implementacion de todas las interfaces comunes
        
    }

    public interface SwingIf extends CommonIf {
        //TODO: Implementacion de las interfaces solo necesarias para Swing
        
    }

    public interface WebIf extends CommonIf {
        //TODO: Implementacion de las interafaces solo para la web
    }
    
    
    ///////////////////////////////////////////////////////////////////////////
    ///                 CLASES ADICIONALES
    ///////////////////////////////////////////////////////////////////////////
    public class ProductoPrecioDataTable
    {
        public Producto producto;
        
        public BigDecimal porcentajePvp1;
        public BigDecimal porcentajePvp2;
        public BigDecimal porcentajePvp3;
        public BigDecimal porcentajePvp4;
        public BigDecimal porcentajePvp5;
        public BigDecimal porcentajePvp6;

        public ProductoPrecioDataTable(Producto producto, BigDecimal porcentajePvp1, BigDecimal porcentajePvp2, BigDecimal porcentajePvp3, BigDecimal porcentajePvp4, BigDecimal porcentajePvp5, BigDecimal porcentajePvp6) {
            this.producto = producto;
            this.porcentajePvp1 = porcentajePvp1;
            this.porcentajePvp2 = porcentajePvp2;
            this.porcentajePvp3 = porcentajePvp3;
            this.porcentajePvp4 = porcentajePvp4;
            this.porcentajePvp5 = porcentajePvp5;
            this.porcentajePvp6 = porcentajePvp6;
        }
        
        
        
        
    }
}
