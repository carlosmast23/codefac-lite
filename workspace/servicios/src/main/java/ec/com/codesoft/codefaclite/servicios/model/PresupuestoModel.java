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
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.servicios.panel.PresupuestoPanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empleado;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Presupuesto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PresupuestoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.OrdenTrabajoEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.PrioridadEnumEstado;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public class PresupuestoModel extends PresupuestoPanel{

    private Presupuesto presupuesto;
    
    private Producto producto;
    private Persona persona;
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        presupuesto = new Presupuesto();
        cargarCombos();
        addListenerBotones();
        initDatosTabla();
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
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        
        getBtnCrearProducto().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        
        getBtnProducto().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductoBusquedaDialogo productoBusquedaDialogo = new ProductoBusquedaDialogo();
                BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(productoBusquedaDialogo);
                buscarDialogoModel.setVisible(true);
                Producto productoD = (Producto) buscarDialogoModel.getResultado();
                if(productoD != null)
                {
                    producto = productoD;
                    getTxtProductoDetalle().setText(productoD.getCodigoEAN()+" - "+ productoD.getNombre());
                }
            }
        });
        
        getBtnProveedor().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClienteBusquedaDialogo buscarBusquedaDialogo = new ClienteBusquedaDialogo();
                BuscarDialogoModel buscarDialogo = new BuscarDialogoModel(buscarBusquedaDialogo);
                buscarDialogo.setVisible(true);
                Persona personaD = (Persona) buscarDialogo.getResultado();
                if(personaD != null)
                {
                    getTxtProveedorDetalle().setText(personaD.getRazonSocial()+" - "+personaD.getIdentificacion());
                    persona = personaD;
                }
            }
        });
        
        getBtnAgregarDetalle().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarDetallesPresupuesto(null);
                limpiarDetalles();
            }
        });
        
        getBtnEditarDetalle().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = getTableDetallesPresupuesto().getSelectedRow();
                PresupuestoDetalle presupuestoDetalle = presupuesto.getPresupuestoDetalles().get(fila);
                agregarDetallesPresupuesto(presupuestoDetalle);
                getBtnAgregarDetalle().setEnabled(true);
                
            }
        });
        
        getBtnEliminarDetalle().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = getTableDetallesPresupuesto().getSelectedRow();
                presupuesto.getPresupuestoDetalles().remove(fila);
                mostrarDatosTabla();
                getBtnAgregarDetalle().setEnabled(true);
            }
        });
    }
    
    public void addListenerCombos()
    {
        
    }
    
    public void addListenerTablas()
    {
        getTableDetallesPresupuesto().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int fila = getTableDetallesPresupuesto().getSelectedRow();
                getBtnAgregarDetalle().setEnabled(false);
                PresupuestoDetalle presupuestoDetalle = presupuesto.getPresupuestoDetalles().get(fila);
                cargarInformacionDetallePresupuesto( presupuestoDetalle);
                getBtnAgregarDetalle().setEnabled(false);
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
    
    public void agregarDetallesPresupuesto(PresupuestoDetalle presupuestoDetalle)
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
            else{
                presupuestoDetalle.setPersona(this.persona);
            }
                    
            if(this.producto == null)
            {
                presupuestoDetalle.setProducto(this.producto);
                DialogoCodefac.mensaje("Advertencia", "Debe seleccionar un producto", DialogoCodefac.MENSAJE_ADVERTENCIA);
                throw new ExcepcionCodefacLite("Necesita seleccionar un Cliente");
            }
            else
            {
                presupuestoDetalle.setProducto(this.producto);
            }
            
            BigDecimal precioCompra = new BigDecimal(getTxtPrecioCompra().getText());
            presupuestoDetalle.setPrecioCompra(precioCompra.setScale(2,BigDecimal.ROUND_HALF_UP));
            BigDecimal descuentoCompra = new BigDecimal(getTxtDescuentoPrecioCompra().getText());
            presupuestoDetalle.setDescuentoCompra(descuentoCompra.setScale(2,BigDecimal.ROUND_HALF_UP));
            BigDecimal precioVenta = new BigDecimal(getTxtPrecioVenta().getText());
            presupuestoDetalle.setPrecioVenta(precioVenta.setScale(2,BigDecimal.ROUND_HALF_UP));
            BigDecimal descuentoVenta = new BigDecimal(getTxtDescuentoPrecioVenta().getText());
            presupuestoDetalle.setDescuentoVenta(descuentoVenta.setScale(2,BigDecimal.ROUND_HALF_UP));
            presupuestoDetalle.setCantidad(new BigDecimal(getTxtCantidad().getText()));
            
        }catch(ExcepcionCodefacLite e)
        {
             Logger.getLogger(PresupuestoModel.class.getName()).log(Level.SEVERE, null, e);
        }

        if(agregar)
        {
            this.presupuesto.addDetalle(presupuestoDetalle);
            mostrarDatosTabla();
        }
        else{
            DialogoCodefac.mensaje("Advertencia", "Debe ingresar una fecha", DialogoCodefac.MENSAJE_ADVERTENCIA);
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
        for (PresupuestoDetalle detalle : detalles) {
            subtotalCompra.add(detalle.getPrecioCompra());
            descuentoCompra.add(detalle.getDescuentoCompra());
            subtotalVenta.add(detalle.getPrecioVenta());
            descuentoVenta.add(detalle.getDescuentoVenta());
        }
        
        getLblSubtotalCompra().setText(subtotalVenta.toString());
        getLblDescuentoCompra().setText(descuentoCompra.toString());
        getLblSubtotalVenta().setText(subtotalVenta.toString());
        getLblSubtotaDescuentoVenta().setText(descuentoCompra.toString());
        getLblTotalCompra().setText(subtotalCompra.subtract(descuentoCompra).toString());
        getLblTotalVenta().setText(subtotalVenta.subtract(descuentoVenta).toString());
       
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
}
