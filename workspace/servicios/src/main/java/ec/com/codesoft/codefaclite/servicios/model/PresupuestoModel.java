/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servicios.model;

import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ClienteBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.OrdenTrabajoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ProductoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.DialogInterfacePanel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ObserverUpdateInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.servicios.panel.PresupuestoPanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empleado;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Presupuesto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PresupuestoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.OrdenTrabajoEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.PrioridadEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PresupuestoServiceIf;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public class PresupuestoModel extends PresupuestoPanel{

    private Presupuesto presupuesto;
    
    private Producto producto;
    private Persona persona;
    private Map<Persona,List<PresupuestoDetalle>> mapClientes;
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        presupuesto = new Presupuesto();
        getTxtCodigo().setText(""+presupuesto.getId());
        cargarCombos();
        addListenerBotones();
        addListenerCombos();
        addListenerTablas();
        addListenerTextos();
        initDatosTabla();
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        limpiar();
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        try {
            if (presupuesto.getPersona() == null) {
                DialogoCodefac.mensaje("Alerta", "Necesita seleccionar un cliente", DialogoCodefac.MENSAJE_ADVERTENCIA);
                throw new ExcepcionCodefacLite("Necesita seleccionar un Cliente");
            }
            if(presupuesto.getOrdenTrabajoDetalle() == null)
            {
                DialogoCodefac.mensaje("Alerta", "Necesita seleccionar una Orden de Trabajo y seleccionar un detalle de la Orden", SOMEBITS);
                throw new ExcepcionCodefacLite("Necesita seleccionar una Orden de Trabajo y seleccionar un detalle de la Orden");
            }    
                       
            Boolean respuesta = DialogoCodefac.dialogoPregunta("Alerta", "Estas seguro que desea realizar el presupuesto?", DialogoCodefac.MENSAJE_ADVERTENCIA);
            if (!respuesta) {
                throw new ExcepcionCodefacLite("Cancelacion usuario de Presupuesto");
            }
            
            PresupuestoServiceIf servicio = ServiceFactory.getFactory().getPresupuestoServiceIf();
            setearDatos();
            servicio.grabar(presupuesto);        
            }catch (ServicioCodefacException ex) {
                Logger.getLogger(Presupuesto.class.getName()).log(Level.SEVERE, null, ex);
            }catch (RemoteException ex) {
                Logger.getLogger(Presupuesto.class.getName()).log(Level.SEVERE, null, ex);
            }
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
        this.presupuesto = new Presupuesto();
        this.producto = null;
        this.persona = null;
        this.mapClientes = null;
        limpiarDetalles();
        limpiarTotales();
    }

    public String getNombre() {
        return "Presupuesto";
    }

    @Override
    public String getURLAyuda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        Map<Integer, Boolean> permisos = new HashMap<Integer, Boolean>();
        permisos.put(GeneralPanelInterface.BOTON_NUEVO, true);
        permisos.put(GeneralPanelInterface.BOTON_GRABAR, true);
        permisos.put(GeneralPanelInterface.BOTON_BUSCAR, true);
        permisos.put(GeneralPanelInterface.BOTON_ELIMINAR, true);
        permisos.put(GeneralPanelInterface.BOTON_IMPRIMIR, true);
        permisos.put(GeneralPanelInterface.BOTON_AYUDA, true);
        return permisos;
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void cargarCombos()
    {
        /**
         * Estado general de Presupuesto
         */
        getCmbEstadoPresupuesto().removeAllItems();
        for(GeneralEnumEstado gem : GeneralEnumEstado.values())
        {
            getCmbEstadoPresupuesto().addItem(gem);
        }
        
        
    }
    
    public void initDatosTabla()
    {
        DefaultTableModel modeloTablaDetallesPresupuesto = UtilidadesTablas.crearModeloTabla(new String[]{"Proveedor","Producto","Valor compra","Valor venta","Cantidad"}, new Class[]{String.class,String.class,String.class,String.class,String.class});
        getTableDetallesPresupuesto().setModel(modeloTablaDetallesPresupuesto);
    }
    
    public void addListenerBotones()
    {
        
        getBtnCliente().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClienteBusquedaDialogo buscarBusquedaDialogo = new ClienteBusquedaDialogo();
                BuscarDialogoModel buscarDialogo = new BuscarDialogoModel(buscarBusquedaDialogo);
                buscarDialogo.setVisible(true);
                Persona persona = (Persona) buscarDialogo.getResultado();
                if(persona != null)
                {
                    presupuesto.setPersona(persona);
                    getTxtCliente().setText(persona.getIdentificacion()+" - "+persona.getRazonSocial());
                }
            }
        });
        
        getBtnOrdenTrabajo().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OrdenTrabajoBusquedaDialogo busquedaDialogo = new OrdenTrabajoBusquedaDialogo();
                BuscarDialogoModel dialogoModel = new BuscarDialogoModel(busquedaDialogo);
                dialogoModel.setVisible(true);
                OrdenTrabajo ordenTrabajo = (OrdenTrabajo) dialogoModel.getResultado();
                if(ordenTrabajo != null)
                {
                    getTxtOrdenTrabajo().setText(ordenTrabajo.getCodigo()+" - "+ordenTrabajo.getDescripcion());
                    getCmbDetallesOrdenTrabajo().removeAllItems();
                    for(OrdenTrabajoDetalle pd : ordenTrabajo.getDetalles())
                    {
                        getCmbDetallesOrdenTrabajo().addItem(pd);
                    }
                }
                
            }
        });
      
        getBtnCrearProveedor().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelPadre.crearDialogoCodefac(new ObserverUpdateInterface<Persona>() {
                @Override
                public void updateInterface(Persona entity) {
                        persona = entity;
                        if (persona != null) {
                            getTxtProveedorDetalle().setText(persona.getIdentificacion()+" - "+persona.getRazonSocial());
                        }
                }
                }, DialogInterfacePanel.CLIENTE_PANEL, false);
            }
        });
        
        getBtnCrearProducto().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelPadre.crearDialogoCodefac(new ObserverUpdateInterface<Producto>() {
                @Override
                public void updateInterface(Producto entity) {
                        producto = entity;
                        if (producto != null) {
                            getTxtProductoDetalle().setText(producto.getCodigoEAN()+" - "+ producto.getNombre());
                        }
                }
                }, DialogInterfacePanel.PRODUCTO_PANEL, false);
            }
        });
        
        getBtnProducto().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductoBusquedaDialogo productoBusquedaDialogo = new ProductoBusquedaDialogo();
                BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(productoBusquedaDialogo);
                buscarDialogoModel.setVisible(true);
                producto = (Producto) buscarDialogoModel.getResultado();
                if(producto != null)
                {
                    getTxtProductoDetalle().setText(producto.getCodigoEAN()+" - "+ producto.getNombre());
                }
            }
        });
        
        getBtnProveedor().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClienteBusquedaDialogo buscarBusquedaDialogo = new ClienteBusquedaDialogo();
                BuscarDialogoModel buscarDialogo = new BuscarDialogoModel(buscarBusquedaDialogo);
                buscarDialogo.setVisible(true);
                persona = (Persona) buscarDialogo.getResultado();
                if(persona != null)
                {
                    getTxtProveedorDetalle().setText(persona.getRazonSocial()+" - "+persona.getIdentificacion());
                }
            }
        });
        
        /**
         * Agregar, Editar y Eliminar Detalles de Presupuesto
         */
        
        getBtnAgregarDetalle().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarDetallesPresupuesto(null,false);
                limpiarDetalles();
                calcularTotales();
            }
        });
        
        getBtnEditarDetalle().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                int fila = getTableDetallesPresupuesto().getSelectedRow();
                PresupuestoDetalle presupuestoDetalle = (PresupuestoDetalle) getTableDetallesPresupuesto().getValueAt(fila, 0);
                if(presupuestoDetalle != null)
                {
                    boolean b = (boolean) getTableDetallesPresupuesto().getValueAt(fila,7);
                    agregarDetallesPresupuesto(presupuestoDetalle,b);
                    limpiarDetalles();
                    calcularTotales();                    
                    getBtnAgregarDetalle().setEnabled(true);
                }
                }catch(Exception exc)
                {
                    exc.printStackTrace();
                }
                
            }
        });
        
        getBtnEliminarDetalle().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaTabla = getTableDetallesPresupuesto().getSelectedRow();
                PresupuestoDetalle presupuestoDetalle = (PresupuestoDetalle) getTableDetallesPresupuesto().getValueAt(filaTabla,0);
                if(presupuestoDetalle != null){
                    for(PresupuestoDetalle pd : presupuesto.getPresupuestoDetalles())
                    {
                        if(pd.equals(presupuestoDetalle)){
                            presupuesto.getPresupuestoDetalles().remove(pd);
                        }
                    }
                }
                getBtnAgregarDetalle().setEnabled(true);
                mostrarDatosTabla();
                calcularTotales();
            }
        });
    }
    
    public void addListenerCombos()
    {
        getCmbOpcionDiaMes().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {             
                calcularFechaProxima();
            }
        });
                
        
        getCmbDetallesOrdenTrabajo().addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(getCmbDetallesOrdenTrabajo().getItemCount()==0)
                {
                    DialogoCodefac.mensaje("Advertencia", "Seleccione una Orden de Trabajo", DialogoCodefac.MENSAJE_ADVERTENCIA);
                }else
                {
                    presupuesto.setOrdenTrabajoDetalle((OrdenTrabajoDetalle) getCmbDetallesOrdenTrabajo().getSelectedItem());
                            
                }
            }    
        });  
        
        getCmbDetallesOrdenTrabajo().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OrdenTrabajoDetalle otd = (OrdenTrabajoDetalle) getCmbDetallesOrdenTrabajo().getSelectedItem();
                if(otd != null){
                    presupuesto.setOrdenTrabajoDetalle((OrdenTrabajoDetalle) getCmbDetallesOrdenTrabajo().getSelectedItem());
                    System.out.println("Ingrese orden detalle");
                }
            }
        });
    }
    
    public void addListenerTextos()
    {
         getTxtDiasPresupuesto().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calcularFechaProxima();
            }
        });
        getTxtDiasPresupuesto().addFocusListener(new FocusListener() {
             @Override
             public void focusGained(FocusEvent e) {
     
             }

             @Override
             public void focusLost(FocusEvent e) {
                calcularFechaProxima();
             }
         });
    }
    
    public void calcularFechaProxima()
    {
        java.util.Date Fecha;
        if(!getTxtDiasPresupuesto().getText().equals("") || getCmbFechaPresupuesto().getDate() != null){
           Fecha = UtilidadesFecha.fechaProxima(getCmbFechaPresupuesto().getDate(),Integer.parseInt(getTxtDiasPresupuesto().getText()), getCmbOpcionDiaMes().getSelectedItem().toString());
           presupuesto.setFechaValidez(new Date(Fecha.getTime()));          
           getLblIndicarFechaValidez().setText("Fecha: " + presupuesto.getFechaValidez());
        }
    }
    public void addListenerTablas()
    {
        getTableDetallesPresupuesto().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int fila = getTableDetallesPresupuesto().getSelectedRow();
                getBtnAgregarDetalle().setEnabled(false);
                try{
                    PresupuestoDetalle presupuestoDetalle =  (PresupuestoDetalle) getTableDetallesPresupuesto().getValueAt(fila, 0);
                    if(presupuestoDetalle != null){
                        cargarInformacionDetallePresupuesto( presupuestoDetalle);
                    }
                }
                catch(Exception e)
                {
                    
                }
            }
        });
        
    }
    
    public void mostrarDatosTabla()
    {
        DefaultTableModel modeloTablaDetallesPresupuesto = UtilidadesTablas.crearModeloTabla(new String[]{"obj","Proveedor","Producto","Valor compra","Valor venta","Cantidad"}, new Class[]{PresupuestoDetalle.class,String.class,String.class,String.class,String.class,String.class});
        List<PresupuestoDetalle> detalles = presupuesto.getPresupuestoDetalles();
        for (PresupuestoDetalle detalle : detalles) 
        {
            Vector<Object> fila=new Vector<>();
            fila.add(detalle);
            fila.add(detalle.getPersona().getNombresCompletos()+"");
            fila.add(detalle.getProducto().getNombre()+"");
            fila.add(detalle.getPrecioCompra().subtract(detalle.getDescuentoCompra())+"");
            fila.add(detalle.getPrecioVenta().subtract(detalle.getDescuentoVenta())+"");
            fila.add(detalle.getCantidad().toString());
            modeloTablaDetallesPresupuesto.addRow(fila);
        }
        getTableDetallesPresupuesto().setModel(modeloTablaDetallesPresupuesto);
        UtilidadesTablas.ocultarColumna(getTableDetallesPresupuesto(), 0);
    }
    
    public void agregarDetallesPresupuesto(PresupuestoDetalle presupuestoDetalle,boolean estado)
    {
        Boolean agregar = false;
        if(presupuestoDetalle == null)
        {
            presupuestoDetalle = new PresupuestoDetalle();
            agregar = true;
        }
        
        try{
            if(this.persona == null){
                DialogoCodefac.mensaje("Advertencia", "Debe seleccionar un proveedor", DialogoCodefac.MENSAJE_ADVERTENCIA);
                throw new ExcepcionCodefacLite("Necesita seleccionar un Cliente");
            }
         
            if(this.producto == null)
            {
                DialogoCodefac.mensaje("Advertencia", "Debe seleccionar un producto", DialogoCodefac.MENSAJE_ADVERTENCIA);
                throw new ExcepcionCodefacLite("Necesita seleccionar un Cliente");
            }
                      
            presupuestoDetalle.setProducto(this.producto);
            presupuestoDetalle.setPersona(this.persona);
            BigDecimal precioCompra = new BigDecimal(getTxtPrecioCompra().getText());
            presupuestoDetalle.setPrecioCompra(precioCompra.setScale(2,BigDecimal.ROUND_HALF_UP));
            BigDecimal descuentoCompra = new BigDecimal(getTxtDescuentoPrecioCompra().getText());
            presupuestoDetalle.setDescuentoCompra(descuentoCompra.setScale(2,BigDecimal.ROUND_HALF_UP));
            BigDecimal precioVenta = new BigDecimal(getTxtPrecioVenta().getText());
            presupuestoDetalle.setPrecioVenta(precioVenta.setScale(2,BigDecimal.ROUND_HALF_UP));
            BigDecimal descuentoVenta = new BigDecimal(getTxtDescuentoPrecioVenta().getText());
            presupuestoDetalle.setDescuentoVenta(descuentoVenta.setScale(2,BigDecimal.ROUND_HALF_UP));
            presupuestoDetalle.setCantidad(new BigDecimal(getTxtCantidad().getText()));
            
            if(estado){
                presupuestoDetalle.setEstado(EnumSiNo.SI.getLetra());
            }else{
            
                
                presupuestoDetalle.setEstado(EnumSiNo.NO.getLetra());
            }
                
            
        }catch(ExcepcionCodefacLite e)
        {
             Logger.getLogger(PresupuestoModel.class.getName()).log(Level.SEVERE, null, e);
        }

        if(agregar)
        {
            this.presupuesto.addDetalle(presupuestoDetalle);
        }
        //Collections.sort(this.presupuesto.getPresupuestoDetalles());
        //mostrarDatosTabla();
        ordenarDetallesEnFuncionDeCliente();
        mostrarDatosTabla2();
        
     }
    
    public void cargarInformacionDetallePresupuesto(PresupuestoDetalle presupuestoDetalle)
    {
        this.producto = presupuestoDetalle.getProducto();
        this.persona = presupuestoDetalle.getPersona();
        getTxtProveedorDetalle().setText(presupuestoDetalle.getPersona().getIdentificacion()+" - "+presupuestoDetalle.getPersona().getRazonSocial() );
        getTxtProductoDetalle().setText(presupuestoDetalle.getProducto().getCodigoEAN()+" - "+presupuestoDetalle.getProducto().getNombre());
        getTxtPrecioCompra().setText(presupuestoDetalle.getPrecioCompra()+"");
        getTxtDescuentoPrecioCompra().setText(presupuestoDetalle.getDescuentoCompra()+"");
        getTxtPrecioVenta().setText(presupuestoDetalle.getPrecioVenta()+"");
        getTxtDescuentoPrecioVenta().setText(presupuestoDetalle.getDescuentoVenta()+"");
        getTxtCantidad().setText(presupuestoDetalle.getCantidad()+"");
        
    }
    
    public void calcularTotales()
    {
        List<PresupuestoDetalle> detalles = this.presupuesto.getPresupuestoDetalles();
        BigDecimal subtotalCompra = new BigDecimal(BigInteger.ZERO);
        BigDecimal descuentoCompra = new BigDecimal(BigInteger.ZERO);
        BigDecimal subtotalVenta = new BigDecimal(BigInteger.ZERO);
        BigDecimal descuentoVenta = new BigDecimal(BigInteger.ZERO);
        BigDecimal totalCompra = new BigDecimal(BigInteger.ZERO);
        BigDecimal totalVenta = new BigDecimal(BigInteger.ZERO);
        
        for (PresupuestoDetalle detalle : detalles) {
            subtotalCompra = subtotalCompra.add(detalle.getPrecioCompra()).multiply(detalle.getCantidad());
            descuentoCompra = descuentoCompra.add(detalle.getDescuentoCompra()).multiply(detalle.getCantidad());
            subtotalVenta = subtotalVenta.add(detalle.getPrecioVenta()).multiply(detalle.getCantidad());
            descuentoVenta = descuentoVenta.add(detalle.getDescuentoVenta()).multiply(detalle.getCantidad());
            totalCompra = subtotalCompra.subtract(descuentoCompra);
            totalVenta = subtotalVenta.subtract(descuentoVenta);
        }
        /**
         * Mostrar valores en pantalla
         */
        getLblSubtotalCompra().setText(subtotalCompra.toString());
        getLblDescuentoCompra().setText(descuentoCompra.toString());
        getLblSubtotalVenta().setText(subtotalVenta.toString());
        getLblSubtotaDescuentoVenta().setText(descuentoVenta.toString());
        getLblTotalCompra().setText(totalCompra.toString());
        getLblTotalVenta().setText(totalVenta.toString());
        
        /**
         * Cargar valores en Presupuesto}
         */
        setearValoresPresupuesto(descuentoCompra, descuentoVenta, totalCompra, totalVenta);
        
       
    }
    
    public void limpiarDetalles()
    {
        this.getTxtProveedorDetalle().setText("");
        this.getTxtProductoDetalle().setText("");
        this.getTxtPrecioCompra().setText("");
        this.getTxtDescuentoPrecioCompra().setText("");
        this.getTxtPrecioVenta().setText("");
        this.getTxtDescuentoPrecioVenta().setText("");
        this.getTxtCantidad().setText("");
        this.persona = null;
        this.producto = null;
    }
    
    public void limpiarTotales()
    {
        this.getLblDescuentoCompra().setText("");
        this.getLblSubtotaDescuentoVenta().setText("");
        this.getLblSubtotalCompra().setText("");
        this.getLblSubtotalVenta().setText("");
        this.getLblTotalCompra().setText("");
        this.getLblTotalVenta().setText("");
    }
     
    private void setearDatos() 
    {
        try{
            
            this.presupuesto.setDescripcion(getTxtDescripcion().getText());
            this.presupuesto.setObservaciones(getTxtAreaObservaciones().getText());
            GeneralEnumEstado generalEnumEstado = (GeneralEnumEstado) getCmbEstadoPresupuesto().getSelectedItem();
            this.presupuesto.setEstado(generalEnumEstado.getEstado());
            this.presupuesto.setFechaPresupuesto(new Date(getCmbFechaPresupuesto().getDate().getTime()));
            this.presupuesto.setFechaCreacion(UtilidadesFecha.getFechaHoy());
            
//            /**
//             * Guardar referencia del detalle de la Orden de trabajo para el presupuesto a realizar 
//             */
//            OrdenTrabajoDetalle ordenTrabajoDetalle = (OrdenTrabajoDetalle) getCmbDetallesOrdenTrabajo().getSelectedItem();
//            if(ordenTrabajoDetalle !=  null){
//                this.presupuesto.setOrdenTrabajoDetalle(ordenTrabajoDetalle);
//            }
            
        }
        catch(Exception e)
        {
            DialogoCodefac.mensaje("Alerta", "Seleccione la fecha de ingreso para Orden Trabajo", DialogoCodefac.MENSAJE_ADVERTENCIA);
        }
    }
    
     public void setearValoresPresupuesto(BigDecimal descuentoCompra, BigDecimal descuentoVenta, BigDecimal totalCompra, BigDecimal totalVenta)
    {
        this.presupuesto.setDescuentoCompra(descuentoCompra);
        this.presupuesto.setDescuentoVenta(descuentoVenta);
        this.presupuesto.setTotalCompra(totalCompra);
        this.presupuesto.setTotalVenta(totalVenta);
    }
    
    public void ordenarDetallesEnFuncionDeCliente()
    {
        mapClientes = new TreeMap<Persona,List<PresupuestoDetalle>>(new Comparator<Persona>() {
            @Override
            public int compare(Persona o1, Persona o2) {
                return o1.compareTo(o2);
            }
        });
        
        for(PresupuestoDetalle pd : presupuesto.getPresupuestoDetalles()){
            if(mapClientes.get(pd.getPersona()) == null)
            {
                mapClientes.put(pd.getPersona(),new ArrayList<PresupuestoDetalle>());
            }
            mapClientes.get(pd.getPersona()).add(pd);
        }
    }
    
    public void mostrarDatosTabla2()
    {
        int c=0;
        DefaultTableModel modeloTablaDetallesPresupuesto = UtilidadesTablas.crearModeloTabla(new String[]{"obj","#","Proveedor","Producto","Valor compra","Valor venta","Cantidad","--"}, new Class[]{PresupuestoDetalle.class,String.class,String.class,String.class,String.class,String.class,String.class,Boolean.class});
        for(Map.Entry<Persona,List<PresupuestoDetalle>> datoMap : mapClientes.entrySet())
        {
            boolean b = true; c+=1;
            String titulo = "Proveedor " + c + " :";
            List<PresupuestoDetalle> detallesPorProveedor = datoMap.getValue();
            for (PresupuestoDetalle detalle : detallesPorProveedor) {
                Vector<Object> fila=new Vector<>();
                if(b){
                    Vector<Object> fil1=new Vector<>();
                    fil1.add(detalle);
                    fil1.add(titulo+"");
                    b = false;
                    detalle.setNumeroOrdenCompra(c);
                    modeloTablaDetallesPresupuesto.addRow(fil1);
                }
                    fila.add(detalle);
                    fila.add("");
                    fila.add(detalle.getPersona().getNombresCompletos()+"");
                    fila.add(detalle.getProducto().getNombre()+"");
                    fila.add(detalle.getPrecioCompra().subtract(detalle.getDescuentoCompra())+"");
                    fila.add(detalle.getPrecioVenta().subtract(detalle.getDescuentoVenta())+"");
                    fila.add(detalle.getCantidad().toString());
                    EnumSiNo siNo = EnumSiNo.getEnumByLetra(detalle.getEstado()) ;
                    fila.add(siNo.getBool());                  
                    modeloTablaDetallesPresupuesto.addRow(fila);

            }
        }
        getTableDetallesPresupuesto().setModel(modeloTablaDetallesPresupuesto);
        UtilidadesTablas.ocultarColumna(getTableDetallesPresupuesto(), 0);
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
