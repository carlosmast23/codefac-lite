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
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public class PresupuestoModel extends PresupuestoPanel{

    private Presupuesto presupuesto;
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        presupuesto = new Presupuesto();
        cargarCombos();
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
        DefaultTableModel modeloTablaDetallesPresupuesto = UtilidadesTablas.crearModeloTabla(new String[]{"Proveedor","Producto","Precio compra","Precio venta","Cantidad"}, new Class[]{String.class,String.class,String.class,String.class,String.class});
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
                    getTxtCliente().setText(persona.getRazonSocial()+" - "+persona.getIdentificacion());
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
                Producto producto = (Producto) buscarDialogoModel.getResultado();
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
                Persona persona = (Persona) buscarDialogo.getResultado();
                if(persona != null)
                {
                    getTxtProveedorDetalle().setText(persona.getRazonSocial()+" - "+persona.getIdentificacion());
                }
            }
        });
        
        getBtnAgregarDetalle().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        
        getBtnEditarDetalle().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        
        getBtnEliminarDetalle().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }
    
    public void addListenerCombos()
    {
        
    }
    
    public void addListenerTablas()
    {
        
    }
    
    public void mostrarDatosTabla(Presupuesto presupuesto)
    {
        DefaultTableModel modeloTablaDetallesPresupuesto = UtilidadesTablas.crearModeloTabla(new String[]{"obj","Proveedor","Producto","Precio compra","Precio venta","Cantidad"}, new Class[]{PresupuestoDetalle.class,String.class,String.class,String.class,String.class,String.class});
        List<PresupuestoDetalle> detalles = presupuesto.getPresupuestoDetalles();
        for (PresupuestoDetalle detalle : detalles) 
        {
            Vector<Object> fila=new Vector<>();
            fila.add(detalle);
            fila.add(detalle.getPersona().getNombresCompletos()+"");
            fila.add(detalle.getProducto().getNombre()+"");
            fila.add(detalle.getPrecioCompra()+"");
            fila.add(detalle.getPrecioVenta()+"");
            fila.add(detalle.getCantidad());
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
//        ordenTrabajoDetalle.setNotas(""+getTxtAreaNotas().getText());
//        OrdenTrabajoEnumEstado ordenTrabajoEnumEstado = (OrdenTrabajoEnumEstado) getCmbEstadoDetalle().getSelectedItem();
//        ordenTrabajoDetalle.setEstado(ordenTrabajoEnumEstado.getLetra());
//        if(getCmbDateFechaEntrega()!=null){
//            ordenTrabajoDetalle.setFechaEntrega(new Date(getCmbDateFechaEntrega().getDate().getTime()));
//        }
//        PrioridadEnumEstado prioridadEnumEstado = (PrioridadEnumEstado) getCmbPrioridadDetalle().getSelectedItem();
//        ordenTrabajoDetalle.setPrioridad(prioridadEnumEstado.getLetra());
//        ordenTrabajoDetalle.setEmpleado((Empleado) getCmbAsignadoADetalle().getSelectedItem());
//        
//        if(agregar && getCmbDateFechaEntrega()!=null)
//        {
//            ordenTrabajo.addDetalle(ordenTrabajoDetalle);
//            mostrarDatosTabla(ordenTrabajo);
//        }
//        else{
//            DialogoCodefac.mensaje("Advertencia", "Debe ingresar una fecha", DialogoCodefac.MENSAJE_ADVERTENCIA);
//        }
//            
        
        
    }
}
