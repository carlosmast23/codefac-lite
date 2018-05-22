/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servicios.model;

import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ClienteBusquedaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.DialogInterfacePanel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ObserverUpdateInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.servicios.panel.OrdenTrabajoPanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Departamento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empleado;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.OperadorNegocioEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.OrdenTrabajoEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.PrioridadEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.VentanaEnum;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
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
 * @author Carlos
 */
public class OrdenTrabajoModel extends OrdenTrabajoPanel{

    private OrdenTrabajo ordenTrabajo;
    
    @Override
    public void iniciar() {
            this.ordenTrabajo = new OrdenTrabajo();
            addListenerBotones();
            addListenerCombos();
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

    @Override
    public String getNombre() {
        return "Orden de Trabajo";
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
                    getTxtCliente().setText(persona.getRazonSocial()+" - "+persona.getIdentificacion());
                }
            }
        });
        
        getBtnAgregarDetalle().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                agregarDetallesOrdenTrabajo(null);
            }
        });
        
        getBtnActualizarDetalle().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = getTableDetallesOrdenTrabajo().getSelectedRow();
                OrdenTrabajoDetalle ordenTrabajoDetalle = ordenTrabajo.getDetalles().get(fila);
                //mostrarDatosTabla();
            }
        });
 
        getBtnEliminarDetalle().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = getTableDetallesOrdenTrabajo().getSelectedRow();
                ordenTrabajo.getDetalles().remove(fila);
                mostrarDatosTabla(ordenTrabajo);
            }
        });
    }
    
    public void addListenerCombos()
    {
        getCmbTipoOrdenDetalle().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                try {
                    getCmbAsignadoADetalle().removeAllItems();
                    Departamento departamento = (Departamento)getCmbTipoOrdenDetalle().getSelectedItem();
                    Map<String, Object> parametroMap = new HashMap<String, Object>();
                    parametroMap.put("departamento", departamento);
                    List<Empleado> empleados = ServiceFactory.getFactory().getEmpleadoServiceIf().obtenerPorMap(parametroMap);
                    for (Empleado empleado : empleados) {
                        getCmbAsignadoADetalle().addItem(empleado);
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(OrdenTrabajoModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ServicioCodefacException ex) {
                    Logger.getLogger(OrdenTrabajoModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    public void cargarCombos()
    {
        /**
         * Estado general de orden trabajo
         */
        getCmbEstadoOrdenTrabajo().removeAllItems();
        for(GeneralEnumEstado generalEnumEstado : GeneralEnumEstado.values())
        {
            getCmbEstadoOrdenTrabajo().addItem(generalEnumEstado);
        }
        
        /**
         * Estado por detalle de Orden de trabajo
         */
        getCmbEstadoDetalle().removeAllItems();
        for(OrdenTrabajoEnumEstado ordenTrabajoEnumEstado : OrdenTrabajoEnumEstado.values())
        {
            getCmbEstadoDetalle().addItem(ordenTrabajoEnumEstado);
        }
        
        getCmbAsignadoADetalle().removeAllItems();
        
        /**
         * Prioridad por detalle de orden trabajo
         */
        getCmbPrioridadDetalle().removeAllItems();
        for(PrioridadEnumEstado prioridadEnumEstado : PrioridadEnumEstado.values())
        {
            getCmbPrioridadDetalle().addItem(prioridadEnumEstado);
        }
        
        getCmbTipoOrdenDetalle().removeAllItems();
        try{
            List<Departamento> departamentos = ServiceFactory.getFactory().getDepartamentoServiceIf().obtenerTodos();
            for(Departamento departamento : departamentos)
            {
                getCmbTipoOrdenDetalle().addItem(departamento);
            }
        }
        catch(RemoteException e)
        {
            e.printStackTrace();
        }
   
    }
    
    public void limpiarCampos()
    {
        //Orden trabajo
        getTxtCodigo().setText("");
        getTxtCliente().setText("");
        getTxtDescripcion().setText("");
        //Detalle Orden trabajo
        getTxtAreaDescripcion().setText("");
        getTxtAreaNotas().setText("");
  
    }

    public void mostrarDatosTabla(OrdenTrabajo ordenTrabajo)
    {
        
        String[] titulo={"Descripción","Estado","Tipo Orden","Asignado"};
        DefaultTableModel modeloTablaDetallesCompra = UtilidadesTablas.crearModeloTabla(new String[]{"obj","Descripción","Estado","Tipo Orden","Asignado"}, new Class[]{OrdenTrabajoDetalle.class,String.class,String.class,String.class,String.class});
        List<OrdenTrabajoDetalle> detalles = ordenTrabajo.getDetalles();
        for (OrdenTrabajoDetalle detalle : detalles) 
        {
            Vector<Object> fila=new Vector<>();
            fila.add(detalle);
            fila.add(detalle.getDescripcion()+"");
            fila.add(detalle.getEstado()+"");
            //fila.add(detalle.getEmpleado().getDepartamento()+"");
            //fila.add(detalle.getEmpleado().getCliente());
            modeloTablaDetallesCompra.addRow(fila);
        }
        getTableDetallesOrdenTrabajo().setModel(modeloTablaDetallesCompra);
        UtilidadesTablas.ocultarColumna(getTableDetallesOrdenTrabajo(), 0);

    }
    
    public void agregarDetallesOrdenTrabajo(OrdenTrabajoDetalle ordenTrabajoDetalle)
    {
        Boolean agregar = false;
        if(ordenTrabajoDetalle == null)
        {
            ordenTrabajoDetalle = new OrdenTrabajoDetalle();
            agregar = true;
        }
        
        ordenTrabajoDetalle.setDescripcion(getTxtAreaDescripcion().getText());
        ordenTrabajoDetalle.setNotas(getTxtAreaNotas().getText());
        OrdenTrabajoEnumEstado ordenTrabajoEnumEstado = (OrdenTrabajoEnumEstado) getCmbEstadoDetalle().getSelectedItem();
        ordenTrabajoDetalle.setEstado(ordenTrabajoEnumEstado.getLetra());
        ordenTrabajoDetalle.setFechaEntrega(new Date(getCmbDateFechaEntrega().getDate().getTime()));
        PrioridadEnumEstado prioridadEnumEstado = (PrioridadEnumEstado) getCmbPrioridadDetalle().getSelectedItem();
        ordenTrabajoDetalle.setPrioridad(prioridadEnumEstado.getLetra());
        //ordenTrabajoDetalle.setEmpleado((Empleado) getCmbAsignadoADetalle().getSelectedItem());
        
        if(agregar)
        {
            ordenTrabajo.addDetalle(ordenTrabajoDetalle);
        }
        
        mostrarDatosTabla(ordenTrabajo);
    }
}
