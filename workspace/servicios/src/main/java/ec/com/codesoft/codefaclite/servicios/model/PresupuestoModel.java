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
import ec.com.codesoft.codefaclite.servicios.busqueda.PresupuestoBusqueda;
import ec.com.codesoft.codefaclite.servicios.panel.PresupuestoPanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Presupuesto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PresupuestoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.compra.OrdenCompra;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.compra.OrdenCompraDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.OrdenCompraServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PresupuestoServiceIf;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.tabla.PopupMenuTabla;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.ArrayList;
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
public class PresupuestoModel extends PresupuestoPanel{

    private Presupuesto presupuesto;
    
    private Producto producto;
    private Persona persona;
    private Map<Persona,List<PresupuestoDetalle>> mapClientes;
    private Map<Integer,List<PresupuestoDetalle>> mapOrden;
    
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
            
            /**
             * El momento que se graba el Presupuesto genero de cada detalle presuesto la orden de compra
             */
            for (Map.Entry<Integer, List<PresupuestoDetalle>> entry : this.mapOrden.entrySet()) {
                Integer key = entry.getKey();
                List<PresupuestoDetalle> detalles = entry.getValue();
                OrdenCompra ordenCompra = new OrdenCompra();
                ordenCompra.setProveedor(detalles.get(0).getPersona());
                    /**
                     * Todos los presupuestos por el momento van a estar ligados a Servicios   
                     */     
                TipoDocumentoEnum tde = TipoDocumentoEnum.COMPRA_SERVICIOS;
                ordenCompra.setCodigoTipoDocumento(tde.getCodigo());
                ordenCompra.setFechaCreacion(UtilidadesFecha.getFechaHoy());
                ordenCompra.setFechaIngreso(this.presupuesto.getFechaPresupuesto());
                
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
                    ordenCompraDetalle.setValorIce(BigDecimal.ZERO);
                    ordenCompraDetalle.setIva(BigDecimal.ZERO);
                    /**
                     * Agregando detalle a Orden Compra
                     */
                    ordenCompra.addDetalle(ordenCompraDetalle);
                }
                
                OrdenCompraServiceIf compraServiceIf = ServiceFactory.getFactory().getOrdenCompraServiceIf();
                compraServiceIf.grabar(ordenCompra);   
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
        this.presupuesto = (Presupuesto) buscarDialogoModel.getResultado();
        if(this.presupuesto != null){
            getTxtCodigo().setText(""+this.presupuesto.getId());
            getTxtCliente().setText(this.presupuesto.getPersona().getIdentificacion()+" - "+this.presupuesto.getPersona().getRazonSocial());                
            cargarDetallesOrdenTrabajo(this.presupuesto.getOrdenTrabajoDetalle().getOrdenTrabajo());
            getCmbDetallesOrdenTrabajo().setSelectedItem(this.presupuesto.getOrdenTrabajoDetalle());
            GeneralEnumEstado generalEnumEstado = GeneralEnumEstado.getEnum(this.presupuesto.getEstado());
            getCmbEstadoPresupuesto().setSelectedItem(generalEnumEstado);
            getCmbFechaPresupuesto().setDate(this.presupuesto.getFechaPresupuesto());
            getLblIndicarFechaValidez().setText(""+this.presupuesto.getFechaValidez());
            ordenarDetallesEnFuncionDeCliente();
            mostrarDatosTabla();
            calcularTotales();
        }
        
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
                    presupuesto.setOrdenTrabajoDetalle((OrdenTrabajoDetalle) getCmbDetallesOrdenTrabajo().getSelectedItem());
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
    
    public void addListenerTablas()
    {
        getTableDetallesPresupuesto().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int fila = getTableDetallesPresupuesto().getSelectedRow();
                int columna = getTableDetallesPresupuesto().getSelectedColumn();
                getBtnAgregarDetalle().setEnabled(false);
                try{
                    PresupuestoDetalle presupuestoDetalle =  (PresupuestoDetalle) getTableDetallesPresupuesto().getValueAt(fila, 0);
                    if(presupuestoDetalle != null){
                        cargarInformacionDetallePresupuesto( presupuestoDetalle);
                        if(columna == 7)
                        {
                            if(!mapClientes.isEmpty())
                            {
                                cambiarOrden();
                            }
                        }
                    }
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }                
            }
        });
        
    }
    
    public void addListenerOpcionesPopupMenu()
    {
        
    }
    
    public void cambiarOrden()
    {
        Integer[] ordenes = new Integer[mapClientes.size()];
        for(int i=0; i< mapClientes.size(); i++)
        {
            ordenes[i]=i+1;
        }
        
        //JComboBox jcb = new JComboBox((ComboBoxModel) ordenes);
        //jcb.setEditable(true);
        int opcion = (int) JOptionPane.showInputDialog(null,"Seleccione el número de orden:", "Elegir" ,JOptionPane.QUESTION_MESSAGE,null,ordenes, ordenes[0]);
        System.out.println("---->Opcion: " + opcion);
    
    }
    
    public void cargarDetallesOrdenTrabajo(OrdenTrabajo ordenTrabajo)
    {
        getTxtOrdenTrabajo().setText(ordenTrabajo.getCodigo()+" - "+ordenTrabajo.getDescripcion());
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
                throw new ExcepcionCodefacLite("Necesita seleccionar un producto");
            }
            
            if (!panelPadre.validarPorGrupo("detalles")) {
                return;
            }
            
            presupuestoDetalle.setProducto(this.producto);
            presupuestoDetalle.setPersona(this.persona);
            
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
        }catch(ExcepcionCodefacLite e)
        {
             Logger.getLogger(PresupuestoModel.class.getName()).log(Level.SEVERE, null, e);
        }

        if(agregar)
        {
            this.presupuesto.addDetalle(presupuestoDetalle);
            ordenarDetallesEnFuncionDeOrdenCompra();
            mostrarDatosTabla();
        }
        
        
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
            this.presupuesto.setDescripcion(getTxtDescripcion().getText());
            this.presupuesto.setObservaciones(getTxtAreaObservaciones().getText());
            GeneralEnumEstado generalEnumEstado = (GeneralEnumEstado) getCmbEstadoPresupuesto().getSelectedItem();
            this.presupuesto.setEstado(generalEnumEstado.getEstado());
            try{   
                this.presupuesto.setFechaPresupuesto(new Date(getCmbFechaPresupuesto().getDate().getTime()));
            }
            catch(Exception e)
            {
                DialogoCodefac.mensaje("Alerta", "Seleccione la fecha de ingreso para Orden Trabajo", DialogoCodefac.MENSAJE_ADVERTENCIA);
            }
            this.presupuesto.setFechaCreacion(UtilidadesFecha.getFechaHoy());
            
            /**
            *  Agregar referencia por Orden   
            */
            int c=0;
            for(Map.Entry<Persona,List<PresupuestoDetalle>> datoMap : mapClientes.entrySet())
            {
                c+=1;
                List<PresupuestoDetalle> presupuestoDetalles = datoMap.getValue();
                for (PresupuestoDetalle presupuestoDetalle : presupuestoDetalles) {
                     /**
                     *  Agregar referencia por proveedor  
                     */   
                    presupuestoDetalle.setNumeroOrdenCompra(c);
                }
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
    
    public void ordenarDetallesEnFuncionDeOrdenCompra()
    {
        ordenarDetallesEnFuncionDeCliente();
        int c=0;
        mapOrden = new HashMap<Integer,List<PresupuestoDetalle>>();
        for (Map.Entry<Persona, List<PresupuestoDetalle>> entry : mapClientes.entrySet()) 
        {
            //Número de Orden a emplear por cada proveedor, cada proveedor puede tener varias detalles de ordenes de compra
            c+=1;
            mapOrden.put(c,new ArrayList<PresupuestoDetalle>());
            List<PresupuestoDetalle> value = entry.getValue();
            for (PresupuestoDetalle presupuestoDetalle : value) 
            {
                
                if(presupuestoDetalle.getNumeroOrdenCompra() == null){
                    presupuestoDetalle.setNumeroOrdenCompra(c);
                    mapOrden.get(c).add(presupuestoDetalle);
                }
            }    

        }
    }
    
    public void mostrarDatosTabla()
    {
        int c=0;
        Vector<Object> fila;
        DefaultTableModel modeloTablaDetallesPresupuesto = 
        UtilidadesTablas.crearModeloTabla(new String[]{"obj","#","Proveedor","Producto","Valor compra","Valor venta","Cantidad","--"}, new Class[]{PresupuestoDetalle.class,String.class,String.class,String.class,String.class,String.class,String.class,Boolean.class});
        for(Map.Entry<Persona,List<PresupuestoDetalle>> datoMap : mapClientes.entrySet())
        {
            boolean b = true; 
            c+=1;
            String titulo = "Orden " + c + " :";
            List<PresupuestoDetalle> detallesPorProveedor = datoMap.getValue();
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
                    System.out.println("aaaaaaaaaa" + detalle.getNumeroOrdenCompra());
                    EnumSiNo siNo = EnumSiNo.getEnumByLetra(detalle.getEstado()) ;
                    fila.add(siNo.getBool());
                    modeloTablaDetallesPresupuesto.addRow(fila);
            }
        }
        getTableDetallesPresupuesto().setModel(modeloTablaDetallesPresupuesto);
        /**
         * Agregar PopupMenu en Tabla
         */
        String []opcionesMenu = {"Eliminar","Editar"};
        
        
        List<JMenuItem> jMenuItems = new ArrayList<>(); 
        JMenuItem nuevo = new JMenuItem("Opcion 1");
        JMenuItem opcion2 = new JMenuItem("Opcion 2");
        jMenuItems.add(nuevo);
        jMenuItems.add(opcion2);
        
        nuevo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                opcionMenu1();
            }
        });
        
        opcion2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                opcionMenu2();
            }
        });
        
        PopupMenuTabla menuTabla = new PopupMenuTabla(getTableDetallesPresupuesto(), jMenuItems);
        UtilidadesTablas.ocultarColumna(getTableDetallesPresupuesto(), 0);
        
    }
    
    public void optenerEventosPorOpciondeMenu(List<JMenuItem> items)
    {
        ActionListener[] oml = items.get(0).getActionListeners();
        for (ActionListener actionListener : oml) {
            System.out.println("-------> " + actionListener);
        }
        
    }
    
    public void opcionesMenuPopup(String op)
    {
        switch(op)
        {
            case "Nuevo":
                JOptionPane.showConfirmDialog(rootPane, "Nuevo");
            break;    
            case "Eliminar":
                JOptionPane.showConfirmDialog(rootPane, "Eliminar");
            break;
            case "Editar":
                JOptionPane.showConfirmDialog(rootPane, "Editar");
            break;
        }
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
    public void opcionMenu1()
    {
        int n = this.mapOrden.size(); 
        int fila = getTableDetallesPresupuesto().getSelectedRow();
        PresupuestoDetalle presupuestoDetalle = (PresupuestoDetalle) getTableDetallesPresupuesto().getValueAt(fila, 0);
        if(presupuestoDetalle != null)
        {
            presupuestoDetalle.setNumeroOrdenCompra(n);
            mapOrden.put(n,new ArrayList<PresupuestoDetalle>());
            mapOrden.get(n).add(presupuestoDetalle);            
        }
        ordenarDetallesEnFuncionDeOrdenCompra();
        mostrarDatosTabla();
        
    }
   
    public void opcionMenu2()
    {
        
    }
}
