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
import ec.com.codesoft.codefaclite.crm.busqueda.ProveedorBusquedaDialogo;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
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
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.StyledEditorKit;

/**
 *
 * @author robert
 */
public class CompraReporteProductoModel  extends CompraReporteProductoPanel
{
    private Producto producto;
    private Persona proveedor;
    private Boolean todos;
    private String opcionReporte;
    private DefaultTableModel modeloTablaDetallesCompra;
    private List<ProductoProveedor>  productoProveedores;
            
    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        agregarBotonListener();
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
        getTxtGenericoProductoProveedor().setText("");
    }

    public String getNombre() {
        return "Compra Reporte Proveedor Producto";
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
    
    public void agregarBotonListener()
    {
        getBtnBuscarGenerico().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                
                if(getCmbTipoReporte().getSelectedItem().equals("Producto")){
                    ProductoBusquedaDialogo productoBusquedaDialogo = new ProductoBusquedaDialogo();
                    BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(productoBusquedaDialogo);
                    buscarDialogoModel.setVisible(true);
                    Producto productoTemp = (Producto) buscarDialogoModel.getResultado();
                    if(productoTemp != null)
                    {
                        producto = productoTemp;
                        proveedor = null;
                        limpiar();
                        getTxtGenericoProductoProveedor().setText("" + producto.getNombre() + " - " + producto.getCodigoPersonalizado());
                    }
                }else if(getCmbTipoReporte().getSelectedItem().equals("Proveedor"))
                {
                    ProveedorBusquedaDialogo proveedorBusquedaDialogo = new ProveedorBusquedaDialogo();
                    BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(proveedorBusquedaDialogo);
                    buscarDialogoModel.setVisible(true);
                    Persona proveedorTemp = (Persona) buscarDialogoModel.getResultado();
                    if(proveedorTemp != null)
                    {
                        proveedor = proveedorTemp;
                        producto = null;
                        limpiar();
//                        if(!proveedor.getNombreLegal().equals("")){
//                            getTxtGenericoProductoProveedor().setText(" " + proveedor.getIdentificacion()+ " - " + proveedor.getNombreLegal());
//                        }else
//                        {
                            getTxtGenericoProductoProveedor().setText(" " + proveedor.getIdentificacion() + " - " + proveedor.getRazonSocial());
//                        }
                    }
                    
                }else
                {
                    DialogoCodefac.mensaje("Advertencia", "Seleccione una opción valida", DialogoCodefac.MENSAJE_ADVERTENCIA);
                }
                       
            }
        });
        
        getBtnObtenerDatos().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                
                try {
                    opcionReporte = (String) getCmbTipoReporte().getSelectedItem();
                    switch(opcionReporte)
                    {
                        case "Producto":
                            if(!todos){
                                buscarPorProductoProveedor(1);
                            }else{
                                buscarTodosProductoProveedor();
                            }
                            break;
                        case "Proveedor":
                            if(!todos){
                                buscarPorProductoProveedor(2);
                            }else{
                                buscarTodosProductoProveedor();
                            }
                            
                            break;
                        default:
                            DialogoCodefac.mensaje("Advertencia", "Seleccione una opción valida", DialogoCodefac.MENSAJE_ADVERTENCIA);
                            break;                
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(CompraReporteProductoModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ServicioCodefacException ex) {
                    Logger.getLogger(CompraReporteProductoModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });
        
        getChckTodos().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(getChckTodos().isSelected())
                {
                    todos = true;
                    getBtnBuscarGenerico().setEnabled(false);
                    getTxtGenericoProductoProveedor().setEnabled(false);
                }
                else
                {
                    todos = false;
                    getBtnBuscarGenerico().setEnabled(true);
                    getTxtGenericoProductoProveedor().setEnabled(true);
                }
            }
        });
        
    }
    
    public void buscarPorProductoProveedor(int opc) throws RemoteException, ServicioCodefacException
    {
        ProductoProveedorServiceIf productoProveedorServiceIf = ServiceFactory.getFactory().getProductoProveedorServiceIf();
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        switch(opc)
        {
            case 1:
                mapParametros.put("producto", producto);
            break;
            case 2:
                mapParametros.put("proveedor", proveedor);
            break;
        }   
        productoProveedores = productoProveedorServiceIf.obtenerPorMap(mapParametros);
        if(productoProveedores != null && productoProveedores.size() > 0){
            mostrarDatosTabla();
        }else{
            DialogoCodefac.mensaje("ProductoProveedor", "Error en busqueda", DialogoCodefac.MENSAJE_ADVERTENCIA);
        }
        
        
    }
    
    public void buscarTodosProductoProveedor() throws RemoteException
    {
        ProductoProveedorServiceIf productoProveedorServiceIf = ServiceFactory.getFactory().getProductoProveedorServiceIf();
        productoProveedores = productoProveedorServiceIf.obtenerTodos();
        if(productoProveedores != null && productoProveedores.size() > 0 )
        {
            mostrarDatosTabla();
        }else{
            DialogoCodefac.mensaje("ProductoProveedor", "Error en busqueda", DialogoCodefac.MENSAJE_ADVERTENCIA);
        }
    }
    
    private void mostrarDatosTabla()
    {
        String[] titulo={"Nombre","Descripción"};
        Persona tempPer = null;
        Producto tempPro = null;
        boolean prod = true;
        this.modeloTablaDetallesCompra = new DefaultTableModel(titulo,0);
        int opc = 0;
        opc = (getCmbTipoReporte().getSelectedItem().equals("Producto") ? 1 : 2);
        
        for (ProductoProveedor detalle : productoProveedores) {
            Vector<String> fila=new Vector<String>();
            prod = true;
            switch(opc)
            {
                case 1:
                    if(tempPro != null && detalle.getProducto().equals(tempPro))
                    {
                        prod = false;
                    }
                    if(prod){
                        fila = new Vector<String>();
                        fila.add(""+detalle.getProducto());
                        fila.add("");
                        this.modeloTablaDetallesCompra.addRow(fila);
                    }
                        fila = new Vector<String>();
                        fila.add("");
                        fila.add(" " + detalle.getProveedor());
                        tempPro = detalle.getProducto();
                    break;
                case 2:
                    if(tempPer != null && detalle.getProveedor().equals(tempPer))
                    {
                        prod = false;
                    }
                    if(prod){
                        fila = new Vector<String>();
                        fila.add(""+detalle.getProveedor());
                        fila.add("");
                        this.modeloTablaDetallesCompra.addRow(fila);
                    }
                        fila = new Vector<String>();
                        fila.add("");
                        fila.add(" " + detalle.getProducto());
                        tempPer = detalle.getProveedor();
                    break;
            }
            
            this.modeloTablaDetallesCompra.addRow(fila);
        }
        getTblInformacionReporte().setModel(this.modeloTablaDetallesCompra);     
    }
    
    private void initModelTablaDetalleCompra() {
        Vector<String> titulo = new Vector<>();
        titulo.add("Nombre");
        titulo.add("Descripción");
        this.modeloTablaDetallesCompra = new DefaultTableModel(titulo, 0);
        getTblInformacionReporte().setModel(modeloTablaDetallesCompra);
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
