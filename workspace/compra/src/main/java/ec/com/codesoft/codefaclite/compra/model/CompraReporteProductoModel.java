/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.compra.model;

import com.healthmarketscience.rmiio.RemoteInputStreamClient;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.compra.panel.CompraReporteProductoPanel;
import ec.com.codesoft.codefaclite.compra.reportdata.InformacionProductoProveedor;
import ec.com.codesoft.codefaclite.compra.reportdata.ReporteProductoProveedor;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ProductoBusquedaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.report.ReporteCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.crm.busqueda.ProveedorBusquedaDialogo;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Perfil;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ProductoProveedor;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ProductoProveedorServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RecursosServiceIf;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.StyledEditorKit;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;

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
    private Map<Producto,List<ProductoProveedor>> mapProductos;
    private Map<Persona,List<ProductoProveedor>> mapProveedores;
            
    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        agregarBotonListener();
        todos = false;
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        limpiar();
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
    public void imprimir() throws RemoteException {
        InputStream path= null;
        List<ReporteProductoProveedor> datos;
        try {
            /**
             * Inicializar valores para reportes
             */
            RecursosServiceIf service= ServiceFactory.getFactory().getRecursosServiceIf();
            Map<String, Object> parametros = new HashMap<String, Object>();
            path = RemoteInputStreamClient.wrap(service.getResourceInputStream(RecursoCodefac.JASPER_COMPRA, "reporteProductoProveedor.jrxml"));
            InputStream pathSubReporte = RemoteInputStreamClient.wrap(service.getResourceInputStream(RecursoCodefac.JASPER_COMPRA, "subReporteProductoProveedor.jrxml"));
            JasperReport reportPiePagina = JasperCompileManager.compileReport(pathSubReporte);
            parametros.put("subreporte_datos",reportPiePagina);
            opcionReporte = (String) getCmbTipoReporte().getSelectedItem();
            switch (opcionReporte) {
                case "Producto":
                    datos = cargarDatosReporteProducto();
                    ReporteCodefac.generarReporteInternalFramePlantilla(path, parametros, datos, panelPadre, "Reporte Producto/Proveedor");           
                    break;
                case "Proveedor":
                    datos= cargarDatosReporteProveedor();
                    ReporteCodefac.generarReporteInternalFramePlantilla(path, parametros, datos, panelPadre, "Reporte Producto/Proveedor");           
                    break;
            }
        } catch (IOException ex) {
            Logger.getLogger(CompraReporteProductoModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            Logger.getLogger(CompraReporteProductoModel.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                path.close();
            } catch (IOException ex) {
                Logger.getLogger(CompraReporteProductoModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
      
        
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
        producto = null;
        proveedor = null;
        todos = false;
        opcionReporte = "";
        productoProveedores = null;
        mapProductos = null;
        mapProveedores = null;
    }

    public String getNombre() {
        return "Reporte Producto/Proveedor";
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
        opcionReporte = (String) getCmbTipoReporte().getSelectedItem();
        switch(opcionReporte)
        {
            case "Producto":
                ProductoBusquedaDialogo productoBusquedaDialogo = new ProductoBusquedaDialogo();
                buscarDialogoModel = new BuscarDialogoModel(productoBusquedaDialogo);
            break;
            case "Proveedor":
                ProveedorBusquedaDialogo proveedorBusquedaDialogo = new ProveedorBusquedaDialogo();
                buscarDialogoModel = new BuscarDialogoModel(proveedorBusquedaDialogo);
            break;
        }
        return buscarDialogoModel;
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        opcionReporte = (String) getCmbTipoReporte().getSelectedItem();
        switch(opcionReporte)
        {
            case "Producto":
                producto = (Producto) entidad;
                getTxtGenericoProductoProveedor().setText("" + producto.getNombre() + " - " + producto.getCodigoPersonalizado());
            break;
            case "Proveedor":
                proveedor =(Persona) entidad;
                getTxtGenericoProductoProveedor().setText(" " + proveedor.getIdentificacion() + " - " + proveedor.getRazonSocial());
            break;
            default:
                getTxtGenericoProductoProveedor().setText("");
            break;
        }
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
                        getTxtGenericoProductoProveedor().setText(" " + proveedor.getIdentificacion() + " - " + proveedor.getRazonSocial());
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
                                buscarPorProductor();
                                crearMapPorProducto();
                            }else{
                                buscarTodosProductoProveedor();
                                crearMapPorProducto();
                            }
                            if(productoProveedores != null && productoProveedores.size() > 0){
                                mostrarDatosTablaProducto();
                            }else{
                                DialogoCodefac.mensaje("Producto", "No existen Proveedores para el Producto", DialogoCodefac.MENSAJE_ADVERTENCIA);
                            }
                            break;
                        case "Proveedor":
                            if(!todos){
                                buscarPorProveedor();
                                crearMapPorProveedor();
                            }else{
                                buscarTodosProductoProveedor();
                                crearMapPorProveedor();
                            }
                            if(productoProveedores != null && productoProveedores.size() > 0){
                                mostrarDatosTablaProveedor();
                            }else{
                                DialogoCodefac.mensaje("Proveedor", "No existen Producots para el Proveedor", DialogoCodefac.MENSAJE_ADVERTENCIA);
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
                    getTxtGenericoProductoProveedor().setText("");                    
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
    
    public void agregarComboListener()
    {
        getCmbTipoReporte().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiar();                
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
        
    }
    
    public void buscarPorProveedor() throws RemoteException, ServicioCodefacException
    {
        ProductoProveedorServiceIf productoProveedorServiceIf = ServiceFactory.getFactory().getProductoProveedorServiceIf();
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("proveedor", proveedor);
        productoProveedores = productoProveedorServiceIf.obtenerPorMap(mapParametros);
    }
    
    public void buscarPorProductor() throws RemoteException, ServicioCodefacException
    {
        ProductoProveedorServiceIf productoProveedorServiceIf = ServiceFactory.getFactory().getProductoProveedorServiceIf();
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("producto", producto);
        productoProveedores = productoProveedorServiceIf.obtenerPorMap(mapParametros);
    }
    
    public void buscarTodosProductoProveedor() throws RemoteException, RemoteException
    {
        ProductoProveedorServiceIf productoProveedorServiceIf = ServiceFactory.getFactory().getProductoProveedorServiceIf();
        productoProveedores = productoProveedorServiceIf.obtenerTodos();
    }
    
    public void crearMapPorProducto()
    {
        mapProductos = new TreeMap<Producto,List<ProductoProveedor>>(new Comparator<Producto>() {
            @Override
            public int compare(Producto o1, Producto o2) {
                return o1.compareTo(o2);
            }
        });
        
        for(ProductoProveedor pp : productoProveedores)
        {
            if(mapProductos.get(pp.getProducto()) == null)
            {
                List<ProductoProveedor> pps = new ArrayList<>();
                pps.add(pp);
                mapProductos.put(pp.getProducto(), pps);
            }
            else
            {
                mapProductos.get(pp.getProducto()).add(pp);
            }
        }
    }
    
    public void crearMapPorProveedor()
    {
        mapProveedores = new TreeMap<Persona,List<ProductoProveedor>>(new Comparator<Persona>() {
            @Override
            public int compare(Persona p1, Persona p2) {
                return p1.compareTo(p2);
            }
        });
        
        for(ProductoProveedor pp : productoProveedores)
        {
            if(mapProveedores.get(pp.getProveedor()) == null)
            {
                List<ProductoProveedor> pps = new ArrayList<>();
                pps.add(pp);
                mapProveedores.put(pp.getProveedor(), pps);
            }
            else
            {
                mapProveedores.get(pp.getProveedor()).add(pp);
            }
        }
    }
    

    private void mostrarDatosTablaProveedor()
    {
        Vector<String> titulo;
        DefaultTableModel defaultTableModel = UtilidadesTablas.crearModeloTabla(new String[]{"Nombre","Descripción","Precio","IVA"}, new Class[]{String.class,String.class,String.class,String.class});
        for (Map.Entry<Persona, List<ProductoProveedor>> entry : mapProveedores.entrySet()) 
        {
            Persona key = entry.getKey();
            titulo = new Vector<>();
            titulo.add(""+key.getNombresCompletos());
            defaultTableModel.addRow(titulo);
            List<ProductoProveedor> productoProveedors = entry.getValue();
            for (ProductoProveedor productoProveedor : productoProveedors) 
            {
                titulo = new Vector<>();
                titulo.add("");
                titulo.add("" + productoProveedor.getProducto().getNombre());
                titulo.add("" + productoProveedor.getCosto().toString());
                titulo.add("" + productoProveedor.getConIva());
                defaultTableModel.addRow(titulo);
            }            
        }
        getTblInformacionReporte().setModel(defaultTableModel);     
    }
    
    private void mostrarDatosTablaProducto()
    {
        Vector<String> titulo;
        DefaultTableModel defaultTableModel = UtilidadesTablas.crearModeloTabla(new String[]{"Nombre","Descripción","Precio","IVA"}, new Class[]{String.class,String.class,String.class,String.class});
        for (Map.Entry<Producto, List<ProductoProveedor>> entry : mapProductos.entrySet()) 
        {
            Producto key = entry.getKey();
            titulo = new Vector<>();
            titulo.add(""+key.getNombre());titulo.add("");
            defaultTableModel.addRow(titulo);
            List<ProductoProveedor> productoProveedors = entry.getValue();
            for (ProductoProveedor productoProveedor : productoProveedors) 
            {
                titulo = new Vector<>();
                titulo.add("");
                titulo.add("" + productoProveedor.getProveedor().getNombresCompletos());
                titulo.add("" + productoProveedor.getCosto().toString());
                titulo.add("" + productoProveedor.getConIva());
                defaultTableModel.addRow(titulo);
            }            
        }
        getTblInformacionReporte().setModel(defaultTableModel);
    }
    
    private void initModelTablaDetalleCompra() {
        Vector<String> titulo = new Vector<>();
        titulo.add("Descripción");
        titulo.add("Nombre");
        titulo.add("Precio");
        titulo.add("Graba IVA");
        this.modeloTablaDetallesCompra = new DefaultTableModel(titulo, 0);
        getTblInformacionReporte().setModel(modeloTablaDetallesCompra);
    }
    
    private List<ReporteProductoProveedor> cargarDatosReporteProducto()
    {
        
        List<ReporteProductoProveedor> reporteProductoProveedors = new ArrayList<>();
 
        for (Map.Entry<Producto, List<ProductoProveedor>> entry : mapProductos.entrySet()) 
        {
            Producto key = entry.getKey();
            List<ProductoProveedor> productoProveedors = entry.getValue();
            /**
             * Agregar nombre Producto
             */
            ReporteProductoProveedor rpp = new ReporteProductoProveedor();
            rpp.setNombre("" + key.getNombre());
            /**
             * Agregar Información
             */
            List<InformacionProductoProveedor> informacionProductoProveedors = obtenerInformacion(productoProveedors);
            rpp.setInformacion(informacionProductoProveedors);
             /**
             * Agregando a Lista
             */    
            reporteProductoProveedors.add(rpp);
        }
        
        return reporteProductoProveedors;
        
    }
    
    private  List<ReporteProductoProveedor> cargarDatosReporteProveedor()
    {
        List<ReporteProductoProveedor> reporteProductoProveedors = new ArrayList<>();
        
        for (Map.Entry<Persona, List<ProductoProveedor>> entry : mapProveedores.entrySet()) 
        {
            Persona key = entry.getKey();
            List<ProductoProveedor> productoProveedors = entry.getValue();
            /**
             * Agregar nombre Proveedor
             */
            ReporteProductoProveedor rpp = new ReporteProductoProveedor();
            rpp.setNombre("" + key.getNombresCompletos());
            /**
             * Agregar Información
             */
            List<InformacionProductoProveedor> informacionProductoProveedors = obtenerInformacion(productoProveedors);
            rpp.setInformacion(informacionProductoProveedors);
             /**
             * Agregando a Lista
             */    
            reporteProductoProveedors.add(rpp);            
        }
        return reporteProductoProveedors;
    }
    
    public  List<InformacionProductoProveedor> obtenerInformacion(List<ProductoProveedor> productoProveedors)
    {
        
        List<InformacionProductoProveedor> informacionProductoProveedors = new ArrayList<>();
        for (ProductoProveedor productoProveedor : productoProveedors) 
        {
            InformacionProductoProveedor ipp = new InformacionProductoProveedor();
            opcionReporte = (String) getCmbTipoReporte().getSelectedItem();
            switch (opcionReporte) {
                case "Producto":
                    ipp.setDescripcion(""+productoProveedor.getProveedor().getNombresCompletos());
                    break;
                case "Proveedor":
                    ipp.setDescripcion(""+productoProveedor.getProducto().getNombre());
     
                    break;
            }
            ipp.setPrecio(""+productoProveedor.getCosto().toString());
            ipp.setIva(""+productoProveedor.getConIva());
            informacionProductoProveedors.add(ipp);
        }
        

        
        return informacionProductoProveedors;
    }
    
    
}
