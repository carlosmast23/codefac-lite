/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servicios.model;

import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ClienteBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.DialogInterfacePanel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ObserverUpdateInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.report.ReporteCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servicios.busqueda.OrdenTrabajoBusqueda;
import ec.com.codesoft.codefaclite.servicios.panel.OrdenTrabajoPanel;
import ec.com.codesoft.codefaclite.servicios.reportdata.OrdenTrabajoDataReporte;
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
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.OrdenTrabajoServiceIf;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.ArrayList;
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
        cargarValoresIniciales();
        initDatosTabla();
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        limpiar();
        initDatosTabla();
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        try {
            if (ordenTrabajo.getCliente() == null) {
                DialogoCodefac.mensaje("Alerta", "Necesita seleccionar un cliente", DialogoCodefac.MENSAJE_ADVERTENCIA);
                throw new ExcepcionCodefacLite("Necesita seleccionar un Cliente");
            }
            
            if (ordenTrabajo.getDetalles() == null || ordenTrabajo.getDetalles().isEmpty()) {
                DialogoCodefac.mensaje("Alerta", "No existen detalles para la Orden", DialogoCodefac.MENSAJE_ADVERTENCIA);
                throw new ExcepcionCodefacLite("Necesita crear detalles ");
            }
            
            
            Boolean respuesta = DialogoCodefac.dialogoPregunta("Alerta", "Estas seguro que desea realizar la Orden de trabajo?", DialogoCodefac.MENSAJE_ADVERTENCIA);
            if (!respuesta) {
                throw new ExcepcionCodefacLite("Cancelacion usuario");
            }
            
            OrdenTrabajoServiceIf servicio = ServiceFactory.getFactory().getOrdenTrabajoServiceIf();
            setearDatos();
            servicio.grabar(ordenTrabajo);        
            }catch (ServicioCodefacException ex) {
                Logger.getLogger(OrdenTrabajoModel.class.getName()).log(Level.SEVERE, null, ex);
            }catch (RemoteException ex) {
                Logger.getLogger(OrdenTrabajoModel.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    @Override
    public void editar() throws ExcepcionCodefacLite {
      try {
            setearDatos();
            OrdenTrabajoServiceIf servicio = ServiceFactory.getFactory().getOrdenTrabajoServiceIf(); 
            servicio.editar(this.ordenTrabajo);
            DialogoCodefac.mensaje("Correcto","La Ordem de Trabajo fue editada correctamente",DialogoCodefac.MENSAJE_CORRECTO);
            
      } 
      catch (RemoteException ex) 
      {
            Logger.getLogger(OrdenTrabajoModel.class.getName()).log(Level.SEVERE, null, ex);
            DialogoCodefac.mensaje("Error","Error de comunicacion con el servidor",DialogoCodefac.MENSAJE_INCORRECTO);
      }
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void imprimir() {
        Map parametros =  new HashMap();
        List<OrdenTrabajoDataReporte> dataReportes = new ArrayList<>();
        if(this.ordenTrabajo.getDetalles() != null)
        {
            parametros.put("codigo",this.ordenTrabajo.getCodigo());
            parametros.put("cliente", this.ordenTrabajo.getCliente().getNombresCompletos());
            parametros.put("descripcion", this.ordenTrabajo.getDescripcion());
            GeneralEnumEstado generalEnumEstado = GeneralEnumEstado.getEnum(this.ordenTrabajo.getEstado());
            parametros.put("estado", generalEnumEstado.getNombre());
            parametros.put("fechaIngreso", ""+ this.ordenTrabajo.getFechaIngreso());
            for(OrdenTrabajoDetalle otd : this.ordenTrabajo.getDetalles())
            {
                OrdenTrabajoDataReporte dataReporte = new OrdenTrabajoDataReporte();
                if(otd.getEmpleado().getDepartamento()!=null){
                    dataReporte.setDepartamento(""+otd.getEmpleado().getDepartamento().getNombre());;
                }else{
                    dataReporte.setDepartamento("");
                }
                dataReporte.setDescripciond(""+otd.getDescripcion());
                dataReporte.setFechaEntrega(""+otd.getFechaEntrega());
                dataReporte.setNotas(""+otd.getNotas());
                if(otd.getEmpleado().getCliente()!=null){
                    dataReporte.setPersona(""+otd.getEmpleado().getCliente().getNombresCompletos());
                }else{
                    dataReporte.setPersona("");
                }
                PrioridadEnumEstado pee = PrioridadEnumEstado.getEnum(otd.getPrioridad());
                dataReporte.setPrioridad(""+pee.getNombre());
                OrdenTrabajoEnumEstado otee = OrdenTrabajoEnumEstado.getEnum(otd.getEstado());
                dataReporte.setEstadod(""+otee.getNombre());
                dataReportes.add(dataReporte);
            }
            InputStream path = RecursoCodefac.JASPER_SERVICIO.getResourceInputStream("ordenTrabajo.jrxml");
            ReporteCodefac.generarReporteInternalFramePlantilla(path, parametros, dataReportes, panelPadre, "Orden de Trabajo");
        }
    }

    @Override
    public void actualizar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buscar() throws ExcepcionCodefacLite {
        OrdenTrabajoBusqueda ordenTrabajoBusqueda = new OrdenTrabajoBusqueda();
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(ordenTrabajoBusqueda);
        buscarDialogoModel.setVisible(true);
        this.ordenTrabajo = (OrdenTrabajo) buscarDialogoModel.getResultado();
        if(ordenTrabajo != null){
            this.getTxtCodigo().setText(this.ordenTrabajo.getCodigo());
            this.getTxtCliente().setText(""+this.ordenTrabajo.getCliente());
            this.getTxtDescripcion().setText(""+this.ordenTrabajo.getDescripcion());
            this.getCmbDateFechaIngreso().setDate(this.ordenTrabajo.getFechaIngreso());
            GeneralEnumEstado generalEnumEstado = GeneralEnumEstado.getEnum(this.ordenTrabajo.getEstado());
            getCmbEstadoOrdenTrabajo().setSelectedItem(generalEnumEstado);
            mostrarDatosTabla();
        }
        
    }

    @Override
    public void limpiar() {
        this.ordenTrabajo = new OrdenTrabajo();
        limpiarCampos();
        limpiarCamposDetalles();
        cargarCombos(); 
        initDatosTabla();
    }

//    @Override
    public String getNombre() {
        return "Orden de Trabajo";
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
    
    public void cargarValoresIniciales()
    {
        addListenerBotones();
        addListenerCombos();
        addListenerTablas();
        cargarCombos();
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
                    ordenTrabajo.setCliente(persona);
                    getTxtCliente().setText(persona.getRazonSocial()+" - "+persona.getIdentificacion());
                }
            }
        });
        
        getBtnAgregarDetalle().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                agregarDetallesOrdenTrabajo(null);
                limpiarCamposDetalles();
                cargarCombos();
            }
        });
        
        getBtnActualizarDetalle().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = getTableDetallesOrdenTrabajo().getSelectedRow();
                OrdenTrabajoDetalle ordenTrabajoDetalle = ordenTrabajo.getDetalles().get(fila);
                agregarDetallesOrdenTrabajo(ordenTrabajoDetalle);
                limpiarCamposDetalles();
                cargarCombos();
                getBtnAgregarDetalle().setEnabled(true);
            }
        });
 
        getBtnEliminarDetalle().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = getTableDetallesOrdenTrabajo().getSelectedRow();
                ordenTrabajo.getDetalles().remove(fila);
                mostrarDatosTabla();
                getBtnAgregarDetalle().setEnabled(true);
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
    
    public void addListenerTablas()
    {
        getTableDetallesOrdenTrabajo().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int fila = getTableDetallesOrdenTrabajo().getSelectedRow();
                getBtnAgregarDetalle().setEnabled(false);
                OrdenTrabajoDetalle ordenTrabajoDetalle = ordenTrabajo.getDetalles().get(fila);
                cargarInformacionDetalleOrdenTrabajo( ordenTrabajoDetalle);
                
            }
        });
    }
    
    public void cargarInformacionDetalleOrdenTrabajo(OrdenTrabajoDetalle ordenTrabajoDetalle)
    {
        getTxtAreaDescripcion().setText(ordenTrabajoDetalle.getDescripcion());
        getTxtAreaNotas().setText(ordenTrabajoDetalle.getNotas());
        /**
         * Cargar información a los combos
         */
        OrdenTrabajoEnumEstado ordenTrabajoEnumEstado = OrdenTrabajoEnumEstado.getEnum(ordenTrabajoDetalle.getEstado());
        getCmbEstadoDetalle().setSelectedItem(ordenTrabajoEnumEstado);
        getCmbDateFechaEntrega().setDate(ordenTrabajoDetalle.getFechaEntrega());
        PrioridadEnumEstado prioridadEnumEstado = PrioridadEnumEstado.getEnum(ordenTrabajoDetalle.getPrioridad());
        getCmbPrioridadDetalle().setSelectedItem(prioridadEnumEstado);
        Departamento departamento = ordenTrabajoDetalle.getEmpleado().getDepartamento();
        getCmbTipoOrdenDetalle().setSelectedItem(departamento);
        getCmbAsignadoADetalle().setSelectedItem(ordenTrabajoDetalle.getEmpleado());
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
    
    public void initDatosTabla()
    {
        DefaultTableModel modeloTablaDetallesCompra = UtilidadesTablas.crearModeloTabla(new String[]{"Descripción","Estado","Tipo Orden","Asignado"}, new Class[]{OrdenTrabajoDetalle.class,String.class,String.class,String.class,String.class});
        getTableDetallesOrdenTrabajo().setModel(modeloTablaDetallesCompra);
    }
    
    public void mostrarDatosTabla()
    {
        DefaultTableModel modeloTablaDetallesCompra = UtilidadesTablas.crearModeloTabla(new String[]{"obj","Descripción","Estado","Tipo Orden","Asignado"}, new Class[]{OrdenTrabajoDetalle.class,String.class,String.class,String.class,String.class});
        List<OrdenTrabajoDetalle> detalles = ordenTrabajo.getDetalles();
        for (OrdenTrabajoDetalle detalle : detalles) 
        {
            Vector<Object> fila=new Vector<>();
            fila.add(detalle);
            fila.add(detalle.getDescripcion()+"");
            fila.add(detalle.getEstado()+"");
            if(detalle.getEmpleado().getDepartamento()!= null){
                fila.add(detalle.getEmpleado().getDepartamento().getNombre()+"");
            }else
            {
                fila.add("");
            }
            
            if(detalle.getEmpleado().getCliente()!=null){
                fila.add(detalle.getEmpleado().getCliente().getNombresCompletos()+"");
            }
            else
            {
                fila.add("");
            }
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
        
        ordenTrabajoDetalle.setDescripcion(""+getTxtAreaDescripcion().getText());
        ordenTrabajoDetalle.setNotas(""+getTxtAreaNotas().getText());
        OrdenTrabajoEnumEstado ordenTrabajoEnumEstado = (OrdenTrabajoEnumEstado) getCmbEstadoDetalle().getSelectedItem();
        ordenTrabajoDetalle.setEstado(ordenTrabajoEnumEstado.getLetra());
        if(getCmbDateFechaEntrega()!=null){
            ordenTrabajoDetalle.setFechaEntrega(new Date(getCmbDateFechaEntrega().getDate().getTime()));
        }
        PrioridadEnumEstado prioridadEnumEstado = (PrioridadEnumEstado) getCmbPrioridadDetalle().getSelectedItem();
        ordenTrabajoDetalle.setPrioridad(prioridadEnumEstado.getLetra());
        ordenTrabajoDetalle.setEmpleado((Empleado) getCmbAsignadoADetalle().getSelectedItem());
        
        if(agregar && getCmbDateFechaEntrega()!=null)
        {
            ordenTrabajo.addDetalle(ordenTrabajoDetalle);
            mostrarDatosTabla();
        }
        else{
            DialogoCodefac.mensaje("Advertencia", "Debe ingresar una fecha", DialogoCodefac.MENSAJE_ADVERTENCIA);
        }
            
        
        
    }
    
    public void limpiarCampos()
    {
        //Orden trabajo
        getTxtCodigo().setText("");
        getTxtCliente().setText("");
        getTxtDescripcion().setText("");
    }
    
    public void limpiarCamposDetalles()
    {
        //Detalle Orden trabajo
        getTxtAreaDescripcion().setText("");
        getTxtAreaNotas().setText("");
    }
    
    public void setearDatos() throws ExcepcionCodefacLite
    {
        try{ 
            this.ordenTrabajo.setFechaIngreso(new Date(getCmbDateFechaIngreso().getDate().getTime()));
            this.ordenTrabajo.setCodigo(""+getTxtCodigo().getText());
            this.ordenTrabajo.setDescripcion(""+getTxtDescripcion().getText());
            GeneralEnumEstado generalEnumEstado = (GeneralEnumEstado) getCmbEstadoOrdenTrabajo().getSelectedItem();
            this.ordenTrabajo.setEstado(generalEnumEstado.getEstado());
        }
        catch(Exception e)
        {
            DialogoCodefac.mensaje("Alerta", "Seleccione la fecha de ingreso para Orden Trabajo", DialogoCodefac.MENSAJE_ADVERTENCIA);
        }
                
    }
}
