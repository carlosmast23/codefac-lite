/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.compra.model;

import ec.com.codesoft.codefaclite.compra.panel.CompraXmlPanel;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ProductoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.vistas.core.components.ITableBindingAddData;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.DialogInterfacePanel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfazPostConstructPanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Compra;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CompraDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ProductoProveedor;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ProductoProveedorServiceIf;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class CompraXmlModel extends CompraXmlPanel implements DialogInterfacePanel<Compra>, InterfazPostConstructPanel {

    private static final Integer COLUMNA_OBJETO = 0;
    private Compra compra;
    private Producto productoSeleccionado;

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        crearModeloTabla();
        listenerBotones();
    }

    private void listenerBotones() {
        getBtnActualizar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Compra:" + compra.getRazonSocial());
                actualizarBindingCompontValues();
            }
        });
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

    @Override
    public Compra getResult() throws ExcepcionCodefacLite {

        return null;
    }

    public void crearModeloTabla() {
        String titulo[] = new String[]{"Objeto", "Cod Sistema", "Cod Xml", "Descripción compra", "Cantidad", "Precio"};
        DefaultTableModel modelo = UtilidadesTablas.crearModeloTabla(titulo, new Class[]{Object.class, String.class, String.class, String.class, String.class, String.class});
        getTblDetalles().setModel(modelo);
        UtilidadesTablas.definirTamanioColumnas(getTblDetalles(), new Integer[]{0});
    }

    public ITableBindingAddData getTableBindingAddData() {

        return new ITableBindingAddData<CompraDetalle>() {
            @Override
            public Object[] addData(CompraDetalle value) {

                String codigoSistema="";
                if(value.getProductoProveedor()!=null)
                {
                    codigoSistema=value.getProductoProveedor().getProducto().getCodigoPersonalizado();
                }
                //if(value.get)
                return new Object[]{
                    value,
                    codigoSistema,
                    "",
                    value.getDescripcion(),
                    value.getCantidad(),
                    value.getPrecioUnitario() + "",};
            }

            @Override
            public void setData(CompraDetalle objetoOriginal, Object objetoModificado, Integer columnaModificada) {

            }

        };

    }

    public void listenerBtnBuscarProveedorDialogo() {
        ProductoBusquedaDialogo buscarBusquedaDialogo = new ProductoBusquedaDialogo(session.getEmpresa());
        BuscarDialogoModel buscarDialogo = new BuscarDialogoModel(buscarBusquedaDialogo);
        buscarDialogo.setVisible(true);
        Producto productoTmp = (Producto) buscarDialogo.getResultado();
        this.productoSeleccionado = productoTmp;
    }

    public void listenerBtnEditarDetalle() {
        Integer filaSeleccionada = getTblDetalles().getSelectedRow();
        if (filaSeleccionada > 0) {
            if (this.productoSeleccionado != null) {
                CompraDetalle compraDetalle = (CompraDetalle) getTblDetalles().getValueAt(filaSeleccionada, COLUMNA_OBJETO);
                ProductoProveedor productoProveedor= buscarProductoProveedor(productoSeleccionado, compra.getProveedor());
                compraDetalle.setProductoProveedor(productoProveedor);                
            }
        } else {
            //MensajeCodefacSistema
        }

    }

    public ProductoProveedor buscarProductoProveedor(Producto productoSeleccionado,Persona proveedor) {
        
        ProductoProveedor productoProveedor=null;
        try {
            
            ProductoProveedorServiceIf serviceProductoProveedor = ServiceFactory.getFactory().getProductoProveedorServiceIf();
            
            List<ProductoProveedor> resultados = serviceProductoProveedor.buscarProductoProveedorActivo(productoSeleccionado,proveedor);
            
            if (resultados != null && resultados.size() > 0) {
                productoProveedor = resultados.get(0); //Si existe el proveedor solo seteo la variale
                
            } else {//Cuando no existe crea un nuevo producto proveedor
                productoProveedor = new ProductoProveedor(); //Si no existe el item lo creo para posteriormente cuando grabe persistir con la base de datos
                productoProveedor.setDescripcion("");
                productoProveedor.setEstado("a");
                productoProveedor.setProducto(productoSeleccionado);
                productoProveedor.setProveedor(compra.getProveedor());
                
            }
            
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(CompraXmlModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(CompraXmlModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return productoProveedor;
    }

    @Override
    public void postConstructorExterno(Object[] parametros) {
        Compra compraXml = (Compra) parametros[0];
        this.compra = compraXml;
        //getTxtProveedor().setText(this.compra.getRazonSocial());
        System.out.println("Compra: " + compraXml);
        actualizarBindingCompontValues();
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public Producto getProductoSeleccionado() {
        return productoSeleccionado;
    }

    public void setProductoSeleccionado(Producto productoSeleccionado) {
        this.productoSeleccionado = productoSeleccionado;
    }

    /**
     * ========================================================================
     * METODOS GET AND SET
     * ========================================================================
     */
}
