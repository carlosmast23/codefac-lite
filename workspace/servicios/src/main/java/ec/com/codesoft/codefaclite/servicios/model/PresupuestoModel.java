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
import ec.com.codesoft.codefaclite.corecodefaclite.report.ReporteCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.util.UtilidadesComprobantes;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servicios.busqueda.PresupuestoBusqueda;
import ec.com.codesoft.codefaclite.servicios.panel.PresupuestoPanel;
import ec.com.codesoft.codefaclite.servicios.reportdata.OrdenCompraDataReporte;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.CorreoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Presupuesto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PresupuestoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ProductoProveedor;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.EstudianteInscrito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.compra.OrdenCompra;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.compra.OrdenCompraDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.VentanaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.OrdenCompraServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PresupuestoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ProductoProveedorServiceIf;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.tabla.PopupMenuTabla;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.InputStream;
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
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public class PresupuestoModel extends PresupuestoPanel implements Runnable{
    
    private static final String PATH_REPORTE_TMP = "tmp/reporteOrdenCompra.pdf";
    
    private Presupuesto presupuesto;
    
    private Producto producto;
    private Persona persona;
    private ProductoProveedor productoProveedor;
    private Map<Persona,List<PresupuestoDetalle>> mapClientes;
    private Map<Integer,List<PresupuestoDetalle>> mapOrden;
    private List<OrdenCompra> ordenesCompra;
    private String correoEmpleado;
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        presupuesto = new Presupuesto();
        getTxtCodigo().setText("");
        cargarCombos();
        addListenerBotones();
        addListenerCombos();
        addListenerTablas();
        addListenerTextos();
        initDatosTabla();
        this.getCmbFechaPresupuesto().setDate(UtilidadesFecha.getFechaHoy());
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        
        if(presupuesto != null && presupuesto.getPresupuestoDetalles() != null || presupuesto.getPersona() != null || getCmbDetallesOrdenTrabajo().getItemCount() > 0)
        {
            Boolean respuesta = DialogoCodefac.dialogoPregunta("Alerta", "Si desea continuar se perderan los datos sin guardar?", DialogoCodefac.MENSAJE_ADVERTENCIA);
            if (respuesta) {
                   this.presupuesto = new Presupuesto();
                   this.producto = null;
                   this.persona = null;
                   this.productoProveedor = null;
                   this.mapClientes = null;    
                   limpiar();
                   initDatosTabla();
            }else{
                throw new ExcepcionCodefacLite("Cancelacion usuario");
            }
        }

    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        try {
            setearDatos();
            if (presupuesto.getPersona() == null) {
                DialogoCodefac.mensaje("Alerta", "Necesita seleccionar un cliente", DialogoCodefac.MENSAJE_ADVERTENCIA);
                throw new ExcepcionCodefacLite("Necesita seleccionar un Cliente");
            }
            if(presupuesto.getOrdenTrabajoDetalle() == null)
            {
                DialogoCodefac.mensaje("Alerta", "Necesita seleccionar una Orden de Trabajo y seleccionar un detalle de la Orden", DialogoCodefac.MENSAJE_ADVERTENCIA);
                throw new ExcepcionCodefacLite("Necesita seleccionar una Orden de Trabajo y seleccionar un detalle de la Orden");
            }
            if(presupuesto.getFechaPresupuesto() == null)
            {
                DialogoCodefac.mensaje("Alerta", "Necesita seleccionar una fecha para presupuestar", DialogoCodefac.MENSAJE_ADVERTENCIA);
                throw new ExcepcionCodefacLite("Necesita seleccionar una fecha para presupuestar");
            }
                       
            Boolean respuesta = DialogoCodefac.dialogoPregunta("Alerta", "Estas seguro que desea realizar el presupuesto?", DialogoCodefac.MENSAJE_ADVERTENCIA);
            if (!respuesta) {
                throw new ExcepcionCodefacLite("Cancelacion usuario de Presupuesto");
            }
            
            PresupuestoServiceIf servicio = ServiceFactory.getFactory().getPresupuestoServiceIf();
            servicio.grabar(presupuesto);
            DialogoCodefac.mensaje("Correcto","El presupuesto fue grabado correctamente",DialogoCodefac.MENSAJE_CORRECTO);
            
            /**
             * Me permite grabar todas las ordenes de compra generadas para despues enviar por correo a los Empleados
             */
                this.ordenesCompra = new ArrayList<>();
                if(this.presupuesto.getOrdenTrabajoDetalle().getEmpleado().getCorreoElectronico() != null){
                    this.correoEmpleado = this.presupuesto.getOrdenTrabajoDetalle().getEmpleado().getCorreoElectronico();
                }else{
                    this.correoEmpleado = "Sin Asignar Correo";
                }    
            /**
             * El momento que se graba el Presupuesto genero de cada detalle presuesto la orden de compra
             */
            OrdenCompra ordenCompra;
            for (Map.Entry<Integer, List<PresupuestoDetalle>> entry : this.mapOrden.entrySet()) 
            {
                Integer key = entry.getKey();
                List<PresupuestoDetalle> detalles = entry.getValue();
                ordenCompra = new OrdenCompra();
                ordenCompra.setProveedor(detalles.get(0).getPersona());
                    /**
                     * Todos los presupuestos por el momento van a estar ligados a Servicios   
                     */     
                    TipoDocumentoEnum tde = TipoDocumentoEnum.COMPRA_SERVICIOS;
                    ordenCompra.setCodigoTipoDocumento(tde.getCodigo());
                    ordenCompra.setFechaCreacion(UtilidadesFecha.getFechaHoy());
                    ordenCompra.setFechaIngreso(this.presupuesto.getFechaPresupuesto());
                    ordenCompra.setDescuentoImpuestos(BigDecimal.ZERO);
                    ordenCompra.setDescuentoSinImpuestos(BigDecimal.ZERO);
                    ordenCompra.setObservacion(this.presupuesto.getDescripcion());
                    ordenCompra.setIva(BigDecimal.ZERO);
                    
                    for(PresupuestoDetalle pd : detalles){
                        OrdenCompraDetalle ordenCompraDetalle = new OrdenCompraDetalle();
                        /**
                         * Setean valores de Detalle Orden Compra 
                         */
                        ordenCompraDetalle.setCantidad(pd.getCantidad().intValue());
                        ordenCompraDetalle.setDescripcion(pd.getProducto().getNombre());
                        ordenCompraDetalle.setDescuento(pd.getDescuentoVenta());
                        ordenCompraDetalle.setPrecioUnitario(pd.getPrecioVenta());
                        ordenCompraDetalle.setTotal(ordenCompraDetalle.getSubtotal());
                        ordenCompraDetalle.setValorIce(ordenCompraDetalle.getValorIce());
                        ordenCompraDetalle.setIva(ordenCompraDetalle.getIva());
                        ordenCompraDetalle.setProductoProveedor(pd.getProductoProveedor());
                        /**
                         * Agregando detalle a Orden Compra
                         */
                        ordenCompra.addDetalle(ordenCompraDetalle);
                    }
                /**
                 * Grabando la orden de compra por Proveedor
                 */
                OrdenCompraServiceIf compraServiceIf = ServiceFactory.getFactory().getOrdenCompraServiceIf();
                compraServiceIf.grabar(ordenCompra);
                this.ordenesCompra.add(ordenCompra);
                //this.corresEmpleados.

            }
            
            }catch (ServicioCodefacException ex) {
                Logger.getLogger(Presupuesto.class.getName()).log(Level.SEVERE, null, ex);
            }catch (RemoteException ex) {
                Logger.getLogger(Presupuesto.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    @Override
    public void editar() throws ExcepcionCodefacLite {
         try {
            setearDatos();
            PresupuestoServiceIf servicio = ServiceFactory.getFactory().getPresupuestoServiceIf();
            servicio.editar(this.presupuesto);
            DialogoCodefac.mensaje("Correcto","El presupuesto fue editado correctamente",DialogoCodefac.MENSAJE_CORRECTO);
            
      } 
      catch (RemoteException ex) 
      {
            Logger.getLogger(OrdenTrabajoModel.class.getName()).log(Level.SEVERE, null, ex);
            DialogoCodefac.mensaje("Error","Error de comunicacion con el servidor",DialogoCodefac.MENSAJE_INCORRECTO);
      } catch (ServicioCodefacException ex) {
            Logger.getLogger(PresupuestoModel.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        PresupuestoBusqueda presupuestoBusqueda = new PresupuestoBusqueda();
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(presupuestoBusqueda);
        buscarDialogoModel.setVisible(true);
        Presupuesto tempPresupuesto = (Presupuesto) buscarDialogoModel.getResultado();
        if(tempPresupuesto != null){
            this.presupuesto =  tempPresupuesto;
            getTxtCodigo().setText(""+this.presupuesto.getId());
            getTxtCliente().setText(this.presupuesto.getPersona().getIdentificacion()+" - "+this.presupuesto.getPersona().getRazonSocial());  
            getCmbTipoPresupuesto().setSelectedItem(presupuesto.getCatalogoProducto());
            cargarDetallesOrdenTrabajo(this.presupuesto.getOrdenTrabajoDetalle().getOrdenTrabajo());
            getCmbDetallesOrdenTrabajo().setSelectedItem(this.presupuesto.getOrdenTrabajoDetalle());
            Presupuesto.EstadoEnum generalEnumEstado = Presupuesto.EstadoEnum.getByLetra(this.presupuesto.getEstado());
            getCmbEstadoPresupuesto().setSelectedItem(generalEnumEstado);
            getCmbFechaPresupuesto().setDate(this.presupuesto.getFechaPresupuesto());
            getLblIndicarFechaValidez().setText(""+this.presupuesto.getFechaValidez());
            ordenarDetallesEnFuncionDeCliente();
            mostrarDatosTabla();
            calcularTotales();
        }
        else{
             throw new ExcepcionCodefacLite("Cancelando Busqueda");
        }
        
    }

    @Override
    public void limpiar() {
        limpiarDetalles();
        limpiarTotales();
        getTxtOrdenTrabajo().setText("");
        getTxtCliente().setText("");
        getCmbDetallesOrdenTrabajo().removeAllItems();
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
        for(Presupuesto.EstadoEnum gem : Presupuesto.EstadoEnum.values())
        {
            getCmbEstadoPresupuesto().addItem(gem);
        }
        
        
        //Cargando los tipos de catalogos disponibles para los servicios
        getCmbTipoPresupuesto().removeAllItems();        
        try {
            List<CatalogoProducto> listaCatalogos = ServiceFactory.getFactory().getCatalogoProductoServiceIf().obtenerPorModulo(ModuloCodefacEnum.SERVICIOS);
            for (CatalogoProducto listaCatalogo : listaCatalogos) {
                getCmbTipoPresupuesto().addItem(listaCatalogo);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(PresupuestoModel.class.getName()).log(Level.SEVERE, null, ex);
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
            public void actionPerformed(ActionEvent e){
                ClienteBusquedaDialogo buscarBusquedaDialogo = new ClienteBusquedaDialogo();
                BuscarDialogoModel buscarDialogo = new BuscarDialogoModel(buscarBusquedaDialogo);
                buscarDialogo.setVisible(true);
                Persona persona = (Persona) buscarDialogo.getResultado();
                if(persona != null)
                {
                    if(presupuesto.getPersona() != null){
                        boolean respuesta = DialogoCodefac.dialogoPregunta("Advertencia", "Desea modificar el cliente de la Orden de Trabajo?", DialogoCodefac.MENSAJE_ADVERTENCIA);
                        if(respuesta){
                            presupuesto.setPersona(persona);
                            getTxtCliente().setText(persona.getIdentificacion()+" - "+persona.getRazonSocial());
                        }else{
                            try {
                                throw new ExcepcionCodefacLite("Cancelacion usuario");
                            } catch (ExcepcionCodefacLite ex) {
                                Logger.getLogger(PresupuestoModel.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
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
                    presupuesto.setPersona(ordenTrabajo.getCliente());
                    getTxtCliente().setText(ordenTrabajo.getCliente().getIdentificacion()+" - "+ordenTrabajo.getCliente().getRazonSocial());
                    cargarDetallesOrdenTrabajo(ordenTrabajo);
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
                }, VentanaEnum.CLIENTE, false,formularioActual);
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
                            verificarExistenciadeProductoProveedor();
                            getTxtProductoDetalle().setText(producto.getCodigoEAN()+" - "+ producto.getNombre());
                        }
                }
                }, VentanaEnum.PRODUCTO, false,formularioActual);
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
                    verificarExistenciadeProductoProveedor();
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
                calcularTotales();
            }
        });
        
        getBtnEditarDetalle().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               editarDetallePresupuesto();
            }
        });
        
        getBtnEliminarDetalle().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarDetallePresupuesto();
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
                   obtenerOrdenTrabajoDetalle();
                }
            }    
        });
        
        getCmbDetallesOrdenTrabajo().addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                if(getCmbDetallesOrdenTrabajo().getItemCount() == 0)
                {
                    DialogoCodefac.mensaje("Advertencia", "Seleccione una Orden de Trabajo", DialogoCodefac.MENSAJE_ADVERTENCIA);
                }
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) { }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) { }
        });
        
        getCmbDetallesOrdenTrabajo().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OrdenTrabajoDetalle otd = (OrdenTrabajoDetalle) getCmbDetallesOrdenTrabajo().getSelectedItem();
                if(otd != null){
                    obtenerOrdenTrabajoDetalle();
                }
            }
        });
    }
    
    public void obtenerOrdenTrabajoDetalle()
    {
         presupuesto.setOrdenTrabajoDetalle((OrdenTrabajoDetalle) getCmbDetallesOrdenTrabajo().getSelectedItem());
         getTxtDescripcion().setText(presupuesto.getOrdenTrabajoDetalle().getDescripcion());
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
    
    public void addListenerTablas()
    {
        getTableDetallesPresupuesto().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
//                int fila = getTableDetallesPresupuesto().getSelectedRow();
//                int columna = getTableDetallesPresupuesto().getSelectedColumn();
//                getBtnAgregarDetalle().setEnabled(false);
//                try{
//                    PresupuestoDetalle presupuestoDetalle =  (PresupuestoDetalle) getTableDetallesPresupuesto().getValueAt(fila, 0);
//                    if(presupuestoDetalle != null){
//                        cargarInformacionDetallePresupuesto( presupuestoDetalle);
//                        if(columna == 7)
//                        {
//                            if(!mapClientes.isEmpty())
//                            {
//                                cambiarOrden();
//                            }
//                        }
//                    }
//                }
//                catch(Exception e)
//                {
//                    e.printStackTrace();
//                }                
            }
        });
        
        getTableDetallesPresupuesto().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                getTableDetallesPresupuesto().addKeyListener(new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {}

                    @Override
                    public void keyPressed(KeyEvent e) {
                        //Evento cuando se desea eliminar un dato de los detalles
                        if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                            eliminarDetallePresupuesto();
                            
                        }      
                        
                        //Permite salir del modo edicion y regresa al modo ingreso
                        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                            editarDetallePresupuesto();
                        }

                    }

                    @Override
                    public void keyReleased(KeyEvent e) {}
                });
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });
    }
          
    public void cargarDetallesOrdenTrabajo(OrdenTrabajo ordenTrabajo)
    {
        getTxtOrdenTrabajo().setText(ordenTrabajo.getId() + " - "+ordenTrabajo.getDescripcion());
        getCmbDetallesOrdenTrabajo().removeAllItems();
        for(OrdenTrabajoDetalle pd : ordenTrabajo.getDetalles())
        {
            getCmbDetallesOrdenTrabajo().addItem(pd);
        }
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
    
    public void agregarDetallesPresupuesto(PresupuestoDetalle presupuestoDetalle,boolean estado)
    {
        Boolean agregar = false;
        Boolean perPro = true;
        if(presupuestoDetalle == null)
        {
            presupuestoDetalle = new PresupuestoDetalle();
            agregar = true;
        }
        
        try{
            if(this.persona == null){
                DialogoCodefac.mensaje("Advertencia", "Debe seleccionar un proveedor", DialogoCodefac.MENSAJE_ADVERTENCIA);
                perPro = false;
            }
         
            if(this.producto == null)
            {
                DialogoCodefac.mensaje("Advertencia", "Debe seleccionar un producto", DialogoCodefac.MENSAJE_ADVERTENCIA);
                perPro = false;
            }
                
            if (!panelPadre.validarPorGrupo("detalles")) {
                return;
            }
            
            if(perPro){
                presupuestoDetalle.setProducto(this.producto);
                presupuestoDetalle.setPersona(this.persona);
                presupuestoDetalle.setProductoProveedor(this.productoProveedor);

                if(verificarCamposValidados())
                {
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
                }
                else{
                    throw new ExcepcionCodefacLite("Campos detalles no validos");
                }
            }   
        }catch(ExcepcionCodefacLite e)
        {
             Logger.getLogger(PresupuestoModel.class.getName()).log(Level.SEVERE, null, e);
        }

        if(agregar && perPro)
        {
            buscarNumeroOrdenPresupuestoDetalle(presupuestoDetalle);
            this.presupuesto.addDetalle(presupuestoDetalle);            
            ordenarDetallesEnFuncionDeOrdenCompra();
            mostrarDatosTabla();
            limpiarDetalles();
        }
        if(perPro){
            limpiarDetalles();
        }
        
     }
    
    /**
     * Funcion que me permite buscar el numero de una orden de compra o la creaa
     */
    private void buscarNumeroOrdenPresupuestoDetalle(PresupuestoDetalle presupuestoDetalle)
    {
        int numeroOrdenMaximo=0;
        
        //Solo verificar cuando exista detalles agregados
        if(presupuesto.getPresupuestoDetalles()!=null && presupuesto.getPresupuestoDetalles().size()>0)
        {
            for(PresupuestoDetalle detalle:presupuesto.getPresupuestoDetalles())
            {
                if(detalle.getPersona().equals(presupuestoDetalle.getPersona()))
                {
                    presupuestoDetalle.setNumeroOrdenCompra(detalle.getNumeroOrdenCompra());//Si ya existe un detalle con el mismo proveedor solo seteo el mismo valor por defecto al nuevo detalle
                    return;
                }
                else
                {
                    //Busca el numero de orden mas alto
                    if(detalle.getNumeroOrdenCompra()>numeroOrdenMaximo)
                    {
                        numeroOrdenMaximo=detalle.getNumeroOrdenCompra();
                    }
                }
            }
        }
        
        //Si no encuentra ningun proveedor anterior que coincida creo una nueva orden con un numero nuevo
        presupuestoDetalle.setNumeroOrdenCompra(numeroOrdenMaximo+1); //seteo el maximo numero de orden +1 
    }
    
    private int obtenerNumeroOrdenPresupuesto()
    {
        int numeroOrden=0;
        for(PresupuestoDetalle presupuestoDetalle: presupuesto.getPresupuestoDetalles())
        {
            if(presupuestoDetalle.getNumeroOrdenCompra()>numeroOrden)
            {
                numeroOrden = presupuestoDetalle.getNumeroOrdenCompra();
            }
        }
        
        return numeroOrden;
    }
    
    /**
     * Todo: Cambiar esta funcionalidad cuando aprenda lamba, hasta el lunes
     */
    private int obtenerNumeroOrdenCompraPorProveedor(PresupuestoDetalle presupuestoDetalleTemp)
    {
        int numeroOrdenes = 0;        
        if(presupuestoDetalleTemp != null)
        {
            for(PresupuestoDetalle presupuestoDetalle: presupuesto.getPresupuestoDetalles())
            {
                if(presupuestoDetalleTemp.getNumeroOrdenCompra().equals(presupuestoDetalle.getNumeroOrdenCompra()))
                {
                    numeroOrdenes +=1; 
                }
            }                   
        }
            
        return numeroOrdenes;
    }
    
    
    private List obtenerNumerosOrdenCompra(PresupuestoDetalle presupuestoDetalle)
    {
        List ordenesProveedor = new ArrayList<>();
        
        for(PresupuestoDetalle pd : presupuesto.getPresupuestoDetalles())
        {
            if(presupuestoDetalle.getPersona().equals(pd.getPersona()))
            {
                if(!ordenesProveedor.contains(pd.getNumeroOrdenCompra()))
                {
                    ordenesProveedor.add(pd.getNumeroOrdenCompra());
                }
            } 
        }
        Collections.sort(ordenesProveedor);
        
        return ordenesProveedor;
    }
    
    public void cargarInformacionDetallePresupuesto(PresupuestoDetalle presupuestoDetalle)
    {
        this.producto = presupuestoDetalle.getProducto();
        this.persona = presupuestoDetalle.getPersona();
        this.productoProveedor = presupuestoDetalle.getProductoProveedor();
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
        this.getTxtPrecioCompra().setText("0.00");
        this.getTxtDescuentoPrecioCompra().setText("0.00");
        this.getTxtPrecioVenta().setText("0.00");
        this.getTxtDescuentoPrecioVenta().setText("0.00");
        this.getTxtCantidad().setText("1");
        this.persona = null;
        this.producto = null;
    }
    
    public void limpiarTotales()
    {
        this.getLblDescuentoCompra().setText("0.00");
        this.getLblSubtotaDescuentoVenta().setText("0.00");
        this.getLblSubtotalCompra().setText("0.00");
        this.getLblSubtotalVenta().setText("0.00");
        this.getLblTotalCompra().setText("0.00");
        this.getLblTotalVenta().setText("0.00");
    }
     
    private void setearDatos() 
    {         
            this.presupuesto.setDescripcion(getTxtDescripcion().getText());
            this.presupuesto.setCatalogoProducto((CatalogoProducto) getCmbTipoPresupuesto().getSelectedItem());
            this.presupuesto.setObservaciones(getTxtAreaObservaciones().getText());
            Presupuesto.EstadoEnum generalEnumEstado = (Presupuesto.EstadoEnum) getCmbEstadoPresupuesto().getSelectedItem();
            this.presupuesto.setEstado(generalEnumEstado.getLetra());
            try{   
                this.presupuesto.setFechaPresupuesto(new Date(getCmbFechaPresupuesto().getDate().getTime()));
            }
            catch(Exception e)
            {
                DialogoCodefac.mensaje("Alerta", "Seleccione la fecha de ingreso para Orden Trabajo", DialogoCodefac.MENSAJE_ADVERTENCIA);
            }
            this.presupuesto.setFechaCreacion(UtilidadesFecha.getFechaHoy());
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
        //Metodo que permite ordentar los maps por las proveedores
        mapClientes = new TreeMap<Persona,List<PresupuestoDetalle>>(new Comparator<Persona>() {
            @Override
            public int compare(Persona o1, Persona o2) {
                return o1.compareTo(o2);
            }
        });

        for(PresupuestoDetalle pd : presupuesto.getPresupuestoDetalles())
        {
            
            if(mapClientes.get(pd.getPersona()) == null)
            {
                List<PresupuestoDetalle> detalles=new ArrayList<PresupuestoDetalle>();
                detalles.add(pd); //Agrego el dato a la nueva lista
                mapClientes.put(pd.getPersona(),detalles); //Agredo el proveedor, con el detalle
            }
            else
            {
                mapClientes.get(pd.getPersona()).add(pd);
            }
        }
    }
    
    public void ordenarDetallesEnFuncionDeOrdenCompra()
    {
        //int c = 0;
        ordenarDetallesEnFuncionDeCliente();
        mapOrden = new HashMap<>();
        
        for (PresupuestoDetalle pd : presupuesto.getPresupuestoDetalles()) 
        {
            //Si no existe el numero de orden creo
            if(mapOrden.get(pd.getNumeroOrdenCompra()) == null)
            {
                List<PresupuestoDetalle> detalles=new ArrayList<>();
                detalles.add(pd);
                mapOrden.put(pd.getNumeroOrdenCompra(),detalles);
            }
            else //Si ya existe solo consulto
            {
                List<PresupuestoDetalle> detalles=mapOrden.get(pd.getNumeroOrdenCompra());
                detalles.add(pd);//Solo agrego porque la referencia de la lista edita al map
            }
            
        }
    }
    
    public void mostrarDatosTabla()
    {
        int c=0;
        Vector<Object> fila;
        DefaultTableModel modeloTablaDetallesPresupuesto = 
        UtilidadesTablas.crearModeloTabla(new String[]{"obj","#","Proveedor","Producto","Valor compra","Valor venta","Cantidad"}, new Class[]{PresupuestoDetalle.class,String.class,String.class,String.class,String.class,String.class,String.class});
        for(Map.Entry<Integer,List<PresupuestoDetalle>> datoMap : mapOrden.entrySet())
        {
            boolean b = true; 
            List<PresupuestoDetalle> detallesPorProveedor = datoMap.getValue();
            String titulo = "Orden " + detallesPorProveedor.get(0).getNumeroOrdenCompra() + " :";
            for (PresupuestoDetalle detalle : detallesPorProveedor) {
                if(b){
                    fila=new Vector<>();
                    fila.add(null);fila.add(titulo+"");fila.add("");;fila.add("");;fila.add("");;fila.add("");;fila.add("");;fila.add(true);
                    b = false;
                    modeloTablaDetallesPresupuesto.addRow(fila);
                }
                    fila = new Vector<>();
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
        /**
         * Agregar PopupMenu en Tabla
         */        
        
        List<JMenuItem> jMenuItems = new ArrayList<>(); 
        JMenuItem nuevo = new JMenuItem("Cambiar orden compra");
        JMenuItem ordenCompra = new JMenuItem("Desea generar las Ordenes de Compra?");
        jMenuItems.add(nuevo);
        jMenuItems.add(ordenCompra);
        
        nuevo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                opcionMenuCambiarOrdenCompra();
            }
        });
        
        ordenCompra.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                opcionMenuGenerarOrdenCompra();
            }
        });
        
        PopupMenuTabla menuTabla = new PopupMenuTabla(getTableDetallesPresupuesto(), jMenuItems);
        UtilidadesTablas.ocultarColumna(getTableDetallesPresupuesto(), 0);
        UtilidadesTablas.cambiarColorFila(getTableDetallesPresupuesto());
    }
    
    private boolean verificarCamposValidados() {
        boolean b = true;
        List<JTextField> camposValidar = new ArrayList<JTextField>();
        camposValidar.add(getTxtPrecioCompra());
        camposValidar.add(getTxtPrecioVenta());
        camposValidar.add(getTxtDescuentoPrecioCompra());
        camposValidar.add(getTxtDescuentoPrecioVenta());
        camposValidar.add(getTxtCantidad());
        for (JTextField campo : camposValidar) {
            if (!campo.getBackground().equals(Color.white)) {
                b = false;
            }
        }
        return b;
    }
    
    @Override
    public BuscarDialogoModel obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * Opciones de PopupMenu
     */
    public void opcionMenuCambiarOrdenCompra()
    {
        boolean respuesta =  DialogoCodefac.dialogoPregunta("Advertencia", "Desea colocar el detalle en una nueva orden de compra?", DialogoCodefac.MENSAJE_ADVERTENCIA);
        /**
         * Permite saber si elegi la orden de compra
        */
        int n = -1;
        if(respuesta){
            /**
             * Permite obtener el numero de orden de compra actual
             */
            int numeroOrden = obtenerNumeroOrdenPresupuesto() + 1;
            int fila = getTableDetallesPresupuesto().getSelectedRow();
            PresupuestoDetalle presupuestoDetalle = (PresupuestoDetalle) getTableDetallesPresupuesto().getValueAt(fila, 0);
            if(presupuestoDetalle != null)
            {
                if(obtenerNumeroOrdenCompraPorProveedor(presupuestoDetalle)>0){
                    /**
                     * Me permite obtener el numero de ordenes de compra existentes por el Proveedor seleccionado
                     */
                    List ordenes = obtenerNumerosOrdenCompra(presupuestoDetalle);
                    
                    String menu = "Ordenes: \n";
                    for(Object orden : ordenes)
                    {
                        menu += "\t- Orden " + orden.toString()+ "\n";
                    }
                    menu += "\t- Nueva Orden: "+numeroOrden;
                    n = Integer.parseInt(JOptionPane.showInputDialog(rootPane, menu));
                    
                        if(n != -1)
                        {
                            presupuestoDetalle.setNumeroOrdenCompra(n);
                        }
                    
                }
            }
            /**
             * Orden los detalles del map en funcion del numero de la orden de compra
             */
            ordenarDetallesEnFuncionDeOrdenCompra();
            mostrarDatosTabla();
        }
        
    }
   
    public void opcionMenuGenerarOrdenCompra()
    {
        for (Map.Entry<Integer, List<PresupuestoDetalle>> entry : this.mapOrden.entrySet()) {
            Integer key = entry.getKey();
            List<PresupuestoDetalle> value = entry.getValue();
            
        }
       
    }
    
    /**
     * Verifcar Producto Proveedor
     */
    private void verificarExistenciadeProductoProveedor()
    {
        //Setear las varibales por defecto
        getTxtCantidad().setText("1");
        
        if (producto != null) {
            try {
                //Buscar si existe el producto vinculado con un proveedor
                ProductoProveedorServiceIf serviceProductoProveedor = ServiceFactory.getFactory().getProductoProveedorServiceIf();
                Map<String, Object> mapParametros = new HashMap<String, Object>();
                mapParametros.put("producto", this.producto);
                mapParametros.put("proveedor", this.persona);
                List<ProductoProveedor> resultados = serviceProductoProveedor.obtenerPorMap(mapParametros);
                if (resultados != null && resultados.size() > 0) {
                    productoProveedor = resultados.get(0); //Si existe el proveedor solo seteo la variale
                    getTxtPrecioCompra().setText(productoProveedor.getCosto() + "");
                    //EnumSiNo enumSiNo = EnumSiNo.getEnumByLetra(productoProveedor.getConIva());
                    //getCmbCobraIva().setSelectedItem(enumSiNo);
                } else {//Cuando no existe crea un nuevo producto proveedor
                    productoProveedor = new ProductoProveedor(); //Si no existe el item lo creo para posteriormente cuando grabe persistir con la base de datos
                    productoProveedor.setDescripcion("");
                    productoProveedor.setEstado("a");
                    productoProveedor.setProducto(this.producto);
                    productoProveedor.setProveedor(this.persona);
                    getTxtPrecioCompra().setText("0.00"); //Seteo con el valor de 0 porque no existe el costo grabado
                    //getCmbCobraIva().setSelectedItem(EnumSiNo.SI); //Seteo por defecto el valor de SI cuando no existe en la base de datos
                }
            } catch (RemoteException ex) {
                Logger.getLogger(PresupuestoModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ServicioCodefacException ex) {
                Logger.getLogger(PresupuestoModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void editarDetallePresupuesto()
    {
        try{
            int fila = getTableDetallesPresupuesto().getSelectedRow();
            PresupuestoDetalle presupuestoDetalle = (PresupuestoDetalle) getTableDetallesPresupuesto().getValueAt(fila, 0);
            if(presupuestoDetalle != null)
            {
                boolean b = (boolean) getTableDetallesPresupuesto().getValueAt(fila,7);
                agregarDetallesPresupuesto(presupuestoDetalle,b);
                limpiarDetalles();
                mostrarDatosTabla();
                calcularTotales();                   
                getBtnAgregarDetalle().setEnabled(true);
                
            }else{
                limpiarDetalles();
                getBtnAgregarDetalle().setEnabled(true);
            }
        }catch(Exception exc)
        {
            exc.printStackTrace();
        }
                
    }
    
    private void eliminarDetallePresupuesto()
    {
        try{
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
        }catch(Exception exc)
        {
            exc.printStackTrace();
        }              
    }
    
    private void generarReportePdf(OrdenCompra ordenCompra)
    {
        Map parametros =  new HashMap();
        List<OrdenCompraDataReporte> dataReportes = new ArrayList<>();
        if(ordenCompra.getDetalles() != null)
        {
            parametros.put("codigo", ordenCompra.getId().toString());
            parametros.put("proveedor", ordenCompra.getProveedor().getNombresCompletos());
            parametros.put("descripcion", ordenCompra.getObservacion());
            parametros.put("estado", "no definido");
            parametros.put("fecha", "" + ordenCompra.getFechaIngreso());
            parametros.put("subtotal12", "" + ordenCompra.getSubtotalImpuestos().subtract(ordenCompra.getDescuentoImpuestos()));
            parametros.put("subtotal0", "" + ordenCompra.getSubtotalSinImpuestos().subtract(ordenCompra.getDescuentoSinImpuestos()));
            parametros.put("descuento12", "" + ordenCompra.getDescuentoImpuestos());
            parametros.put("descuento0", "" + ordenCompra.getDescuentoSinImpuestos());
            parametros.put("subtotalImpuestos", "" + ordenCompra.getSubtotalImpuestos());
            parametros.put("subtotalSinImpuestos", "" + ordenCompra.getSubtotalSinImpuestos());
            parametros.put("iva", "" + ordenCompra.getIva());
            parametros.put("total", "" + ordenCompra.getTotal());
            
            for(OrdenCompraDetalle otd : ordenCompra.getDetalles())
            {
                OrdenCompraDataReporte dataReporte = new OrdenCompraDataReporte();
                dataReporte.setCantidad("" + otd.getCantidad().toString());
                dataReporte.setDescripcion("" + otd.getDescripcion());
                dataReporte.setValorUnitario("" + otd.getPrecioUnitario().toString());
                dataReporte.setValorTotal("" + otd.getTotal().toString());
                
                dataReportes.add(dataReporte);
                
            }
            InputStream path = RecursoCodefac.JASPER_COMPRA.getResourceInputStream("orden_compra.jrxml");
            //ReporteCodefac.generarReporteInternalFramePlantilla(path, parametros, dataReportes, panelPadre, "Orden de Compra");
            UtilidadesComprobantes.generarReporteJasper(path, parametros, dataReportes, PATH_REPORTE_TMP);
        }
    }
    
    private void enviarCorreo(EstudianteInscrito estudianteInscrito, String mensaje)
    {
        CorreoCodefac correoCodefac = new CorreoCodefac() {
            @Override
            public String getMensaje() {
                return mensaje;
            }

            @Override
            public String getTitulo() {
                return "Orden Compra";
            }

            @Override
            public Map<String, String> getPathFiles() {
                HashMap<String, String> mapArchivos = new HashMap<String, String>();
                mapArchivos.put("Comunicado.pdf", PATH_REPORTE_TMP);
                return mapArchivos;
            }

            @Override
            public List<String> getDestinatorios() {
                List<String> destinatarios = new ArrayList<String>();
                destinatarios.add(estudianteInscrito.getEstudiante().getRepresentante().getCorreoElectronico());
                return destinatarios;
            }
        };

        correoCodefac.enviarCorreo();
    }
    
    public void enviarComunicados()
    {
        if(!this.ordenesCompra.isEmpty())
        {
            for(OrdenCompra ordenCompra : this.ordenesCompra)
            {
                /**
                 * Generar Reporte
                 */
                generarReportePdf(ordenCompra);
                /**
                 * Crear mensaje para Empleado si existe
                 */
                String mensaje;
            }
        }
    }
            
    @Override
    public void run() 
    {
        
    }
    
    public String crearMensajeCorreo(OrdenCompra ordenCompra )
    {
        return "corre";
    }
}
