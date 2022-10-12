/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servicios.model;

import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ClienteEstablecimientoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.DialogInterfacePanel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;import java.util.Map;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ObserverUpdateInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.enumerador.OrientacionReporteEnum;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.controlador.core.swing.ReporteCodefac;
import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servicios.busqueda.OrdenTrabajoBusqueda;
import ec.com.codesoft.codefaclite.servicios.panel.OrdenTrabajoPanel;
import ec.com.codesoft.codefaclite.servicios.reportdata.OrdenTrabajoDataReporte;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Departamento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empleado;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Lote;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ObjetoMantenimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PersonaEstablecimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.FormatoHojaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.OperadorNegocioEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.PrioridadEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.VentanaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.OrdenTrabajoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PresupuestoServiceIf;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.swing.UtilidadesComboBox;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.collections4.map.HashedMap;
import org.jdesktop.swingx.prompt.PromptSupport;

/**
 *
 * @author Carlos
 */
public class OrdenTrabajoModel extends OrdenTrabajoPanel{

    private OrdenTrabajo ordenTrabajo;
    private List<String> categoriasDetallesOrdenTrabajo;
    
    @Override
    public void iniciar() {
        this.ordenTrabajo = new OrdenTrabajo();
        this.categoriasDetallesOrdenTrabajo = new ArrayList<>();
        agregarListener();
        cargarValoresIniciales();
        initDatosTabla();
        PromptSupport.setPrompt("Ingrese descripción corta", getTxtCategoria());
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite {

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
            setearDatos();
           
            OrdenTrabajoServiceIf servicio = ServiceFactory.getFactory().getOrdenTrabajoServiceIf();
            ordenTrabajo = servicio.grabar(ordenTrabajo);
            imprimir();
   
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
            limpiar();
            
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
        imprimirReporte(this.ordenTrabajo);
    }
    
    private void imprimirReporte(OrdenTrabajo ordenTrabajo)
    {
        Map parametros =  new HashMap();
        List<OrdenTrabajoDataReporte> dataReportes = new ArrayList<>();
        if(ordenTrabajo.getDetalles() != null)
        {
            parametros.put("codigo",ordenTrabajo.getId().toString());
            parametros.put("cliente", ordenTrabajo.getCliente().getNombresCompletos());
            parametros.put("descripcion", ordenTrabajo.getDescripcion());
            //GeneralEnumEstado generalEnumEstado = GeneralEnumEstado.getEnum(this.ordenTrabajo.getEstado());
            parametros.put("estado",(ordenTrabajo.getEstadoEnum()!=null)?ordenTrabajo.getEstadoEnum().getNombre():"Sin estado");
            parametros.put("fechaIngreso", ""+ ordenTrabajo.getFechaIngreso());
            parametros.put("direccion", ""+ ordenTrabajo.getCliente().getEstablecimientos().get(0).getDireccion());
            parametros.put("telefonos", ""+ ordenTrabajo.getCliente().getEstablecimientos().get(0).getTelefonoCelular());
            parametros.put("cedula", ""+ ordenTrabajo.getCliente().getIdentificacion());
            parametros.put("correo",(ordenTrabajo.getCliente().getCorreoElectronico()!=null)?ordenTrabajo.getCliente().getCorreoElectronico():"");
            
            ParametroCodefac parametroCodefac=session.getParametrosCodefac().get(ParametroCodefac.ORDEN_TRABAJO_OBSERVACIONES);
            parametros.put("observacionOrdenTrabajo",(parametroCodefac!=null)?parametroCodefac.getValor():"");
            
            parametros.put("empleado",(session.getUsuario().getEmpleado()!=null)?session.getUsuario().getEmpleado().getNombresCompletos():"");
            
            parametros.put("empleado",(session.getUsuario().getEmpleado()!=null)?session.getUsuario().getEmpleado().getNombresCompletos():"");
            
            if(ordenTrabajo.getObjetoMantenimiento()!=null)
            {
                parametros.put("codigo_objeto",ordenTrabajo.getObjetoMantenimiento().getCodigo());
                parametros.put("nombre_objeto",ordenTrabajo.getObjetoMantenimiento().getNombre());
            }
            
            for(OrdenTrabajoDetalle otd : ordenTrabajo.getDetalles())
            {
                OrdenTrabajoDataReporte dataReporte = new OrdenTrabajoDataReporte();
                if(otd.getDepartamento()!=null){
                    dataReporte.setDepartamento(""+otd.getDepartamento().getNombre());;
                }else{
                    dataReporte.setDepartamento("");
                }
                dataReporte.setDescripciond(""+otd.getDescripcion());
                dataReporte.setFechaEntrega(""+otd.getFechaEntrega());
                dataReporte.setNotas(""+otd.getNotas());
                dataReporte.setTitulo(""+otd.getTitulo());
                if(otd.getEmpleado() != null){
                    String nombresCompletos = otd.getEmpleado().getApellidos()+" "+otd.getEmpleado().getNombres();
                    dataReporte.setPersona("" + nombresCompletos );
                    
                }else{
                    dataReporte.setPersona("");
                }
                PrioridadEnumEstado pee = PrioridadEnumEstado.getEnum(otd.getPrioridad());
                dataReporte.setPrioridad(""+pee.getNombre());
                OrdenTrabajoDetalle.EstadoEnum otee = OrdenTrabajoDetalle.EstadoEnum.getEnum(otd.getEstado());
                dataReporte.setEstadod(""+otee.getNombre());
                dataReportes.add(dataReporte);
            }
            
            //Seleccionar el tipo de formato del reporte segun la configuracion general
            ParametroCodefac parametroFormatoOrden=session.getParametrosCodefac().get(ParametroCodefac.FORMATO_ORDEN_TRABAJO);
            FormatoHojaEnum formatoHojaEnum=FormatoHojaEnum.A4; //Si no configura nada por defecto configura en A4
            if(parametroFormatoOrden!=null)
            {
                formatoHojaEnum=FormatoHojaEnum.buscarPorLetra(parametroFormatoOrden.getValor());
            }
            
            String nombreReporte="ordenTrabajo.jrxml"; //Por defecto selecciona un reporte en vertical
            
            switch (formatoHojaEnum) {
                case A4:
                    nombreReporte = "ordenTrabajo.jrxml";
                    break;

                case A5:
                    nombreReporte = "ordenTrabajoA5.jrxml";
                    break;
            }
            
            //parametros.put("observaciones",(parametroFormatoOrden!=null)?parametroFormatoOrden.getValor():"");
            
            InputStream path = RecursoCodefac.JASPER_SERVICIO.getResourceInputStream(nombreReporte);
            ReporteCodefac.generarReporteInternalFramePlantilla(path, parametros, dataReportes, panelPadre, "Orden de Trabajo",OrientacionReporteEnum.VERTICAL,formatoHojaEnum);
        }
    }

    @Override
    public void actualizar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buscar() throws ExcepcionCodefacLite{
        OrdenTrabajoBusqueda ordenTrabajoBusqueda = new OrdenTrabajoBusqueda();
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(ordenTrabajoBusqueda);
        buscarDialogoModel.setVisible(true);
        OrdenTrabajo ordenTrabajoTemp = (OrdenTrabajo) buscarDialogoModel.getResultado();
        if(ordenTrabajoTemp != null){
            ordenTrabajo = ordenTrabajoTemp;
            getLblCodigo().setText(this.ordenTrabajo.getId()+"");
            cargarDatosCliente(ordenTrabajo.getCliente());
            getTxtDescripcion().setText(""+this.ordenTrabajo.getDescripcion());
            getCmbDateFechaIngreso().setDate(this.ordenTrabajo.getFechaIngreso());
            //GeneralEnumEstado generalEnumEstado = GeneralEnumEstado.getEnum(this.ordenTrabajo.getEstado());
            getCmbEstadoOrdenTrabajo().setSelectedItem(ordenTrabajo.getEstadoEnum());
            OrdenTrabajo.EstadoEnum generalEstadoEnum = this.ordenTrabajo.getEstadoEnum();
            getCmbEstadoDetallesOrdenTrabajo().setSelectedItem(generalEstadoEnum);
            mostrarDatosTabla();           
        }
        else
        {
                throw new ExcepcionCodefacLite("Cancelando Busqueda");
        }
        
    }

    @Override
    public void limpiar() {
        limpiarVariables();
        limpiarCampos();
        limpiarCamposDetalles();
        cargarCombos(); 
        initDatosTabla();
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
        cargarCombos();
        addListenerBotones();
        addListenerCombos();
        addListenerTablas();
        this.getCmbDateFechaIngreso().setDate(UtilidadesFecha.getFechaHoy());
        this.getCmbDateFechaEntrega().setDate(UtilidadesFecha.getFechaHoy());
       
    }
    
    public void addListenerBotones()
    {
        getBtnCliente().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {               
                ClienteEstablecimientoBusquedaDialogo clienteBusquedaDialogo = new ClienteEstablecimientoBusquedaDialogo(session);
                BuscarDialogoModel buscarDialogo = new BuscarDialogoModel(clienteBusquedaDialogo);
                buscarDialogo.setVisible(true);
                PersonaEstablecimiento establecimiento = (PersonaEstablecimiento) buscarDialogo.getResultado();
                if(establecimiento != null)
                {
                    ordenTrabajo.setCliente(establecimiento.getPersona());
                    cargarDatosCliente(establecimiento.getPersona());
                    getTxtCategoria().requestFocus();
                    
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
                actualizarDetalleOrdenTrabajo();
            }
        });
 
        getBtnEliminarDetalle().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarDetalleOrdenTrabajo();
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
                    //Map<String, Object> parametroMap = new HashMap<String, Object>();
                    //parametroMap.put("departamento", departamento);
                    List<Empleado> empleados = ServiceFactory.getFactory().getEmpleadoServiceIf().buscarPorDepartamento(departamento,session.getEmpresa());
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
                try
                {
                    int fila = getTableDetallesOrdenTrabajo().getSelectedRow();
                    getBtnAgregarDetalle().setEnabled(false);
                    OrdenTrabajoDetalle ordenTrabajoDetalle = (OrdenTrabajoDetalle) getTableDetallesOrdenTrabajo().getValueAt(fila,0);
                    if(ordenTrabajoDetalle != null){
                        cargarInformacionDetalleOrdenTrabajo( ordenTrabajoDetalle);
                    }
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }  
                
            }
        });
        
         getTableDetallesOrdenTrabajo().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                getTableDetallesOrdenTrabajo().addKeyListener(new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {}

                    @Override
                    public void keyPressed(KeyEvent e) {
                        //Evento cuando se desea eliminar un dato de los detalles
                        if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                            eliminarDetalleOrdenTrabajo();
                        }      
                        
                        //Permite salir del modo edicion y regresa al modo ingreso
                        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                            actualizarDetalleOrdenTrabajo();
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
    
    public void cargarInformacionDetalleOrdenTrabajo(OrdenTrabajoDetalle ordenTrabajoDetalle)
    {
        /**
         * Cargar informacion a los text
         */
        getTxtAreaDescripcion().setText(ordenTrabajoDetalle.getDescripcion());
        getTxtAreaNotas().setText(ordenTrabajoDetalle.getNotas());
        getTxtCategoria().setText(ordenTrabajoDetalle.getTitulo());
        /**
         * Cargar información a los combos
         */
        OrdenTrabajoDetalle.EstadoEnum ordenTrabajoEnumEstado = OrdenTrabajoDetalle.EstadoEnum.getEnum(ordenTrabajoDetalle.getEstado());
        getCmbEstadoDetalle().setSelectedItem(ordenTrabajoEnumEstado);
        getCmbDateFechaEntrega().setDate(ordenTrabajoDetalle.getFechaEntrega());
        PrioridadEnumEstado prioridadEnumEstado = PrioridadEnumEstado.getEnum(ordenTrabajoDetalle.getPrioridad());
        getCmbPrioridadDetalle().setSelectedItem(prioridadEnumEstado);
        Departamento departamento = ordenTrabajoDetalle.getDepartamento();
        getCmbTipoOrdenDetalle().setSelectedItem(departamento);
        getCmbAsignadoADetalle().setSelectedItem(ordenTrabajoDetalle.getEmpleado());
    }
    
    public void cargarCombos()
    {
        /**
         * Estado general de orden trabajo
         */
        getCmbEstadoOrdenTrabajo().removeAllItems();
        for(OrdenTrabajo.EstadoEnum enumEstado : OrdenTrabajo.EstadoEnum.values())
        {
            getCmbEstadoOrdenTrabajo().addItem(enumEstado);
        }
        
        /**
         * Estado por detalle de Orden de trabajo
         */
        getCmbEstadoDetalle().removeAllItems();
        for(OrdenTrabajoDetalle.EstadoEnum ordenTrabajoEnumEstado : OrdenTrabajoDetalle.EstadoEnum.values())
        {
            getCmbEstadoDetalle().addItem(ordenTrabajoEnumEstado);
        }
        
        getCmbAsignadoADetalle().removeAllItems();
        
        /**
         * Estado general para los detalles de orden trabajo
         */
        getCmbEstadoDetallesOrdenTrabajo().removeAllItems();
        for(OrdenTrabajo.EstadoEnum estadoEnum: OrdenTrabajo.EstadoEnum.values())
        {
            getCmbEstadoDetallesOrdenTrabajo().addItem(estadoEnum);
        }
        getCmbEstadoDetallesOrdenTrabajo().setEnabled(false);
        
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
        DefaultTableModel modeloTablaDetallesCompra = UtilidadesTablas.crearModeloTabla(new String[]{"Descripción","Estado","Departamento","Empleado"}, new Class[]{OrdenTrabajoDetalle.class,String.class,String.class,String.class,String.class});
        getTableDetallesOrdenTrabajo().setModel(modeloTablaDetallesCompra);
    }
    
    public void mostrarDatosTabla()
    {
        DefaultTableModel modeloTablaDetallesCompra = UtilidadesTablas.crearModeloTabla(new String[]{"obj","Titulo","Descripción","Estado","Departamento","Empleado"}, new Class[]{OrdenTrabajoDetalle.class,String.class,String.class,String.class,String.class,String.class});
        List<OrdenTrabajoDetalle> detalles = ordenTrabajo.getDetalles();
        Vector<Object> fila;
        for (OrdenTrabajoDetalle detalle : detalles) 
        {
            fila=new Vector<>();
            fila.add(detalle);
            fila.add(""+detalle.getTitulo());
            fila.add(""+detalle.getDescripcion()+"");
            OrdenTrabajoDetalle.EstadoEnum ordenTrabajoEnumEstado = OrdenTrabajoDetalle.EstadoEnum.getEnum(detalle.getEstado());
            fila.add(""+ordenTrabajoEnumEstado.getNombre());
            
            try{
                if(detalle.getDepartamento()!= null){
                    fila.add(detalle.getDepartamento().getNombre()+"");
                }else
                {
                    fila.add("Sin departamento");
                }

                if(detalle.getEmpleado() !=null ){
                    String nombresCompletos = detalle.getEmpleado().getApellidos()+" "+detalle.getEmpleado().getNombres();
                    fila.add(""+nombresCompletos);
                }
                else
                {
                    fila.add("Sin empleado");
                }
            }catch(Exception e)
            {
                e.printStackTrace();
            }
            modeloTablaDetallesCompra.addRow(fila);
        }
        
        getTableDetallesOrdenTrabajo().setModel(modeloTablaDetallesCompra);
        UtilidadesTablas.ocultarColumna(getTableDetallesOrdenTrabajo(), 0);
        UtilidadesTablas.bloquearColumnasTabla(getTableDetallesOrdenTrabajo(), new Boolean[]{true,false,true,false,true});
        

    }
    
    public void agregarDetallesOrdenTrabajo(OrdenTrabajoDetalle ordenTrabajoDetalle)
    {
        Boolean agregar = false;
        if(ordenTrabajoDetalle == null)
        {
            ordenTrabajoDetalle = new OrdenTrabajoDetalle();
            agregar = true;
        }
        
        if(!panelPadre.validarPorGrupo("detalles"))
        {
            return;
        }    
        
        if(verificarCamposValidados())
        {
            ordenTrabajoDetalle.setDescripcion(""+getTxtAreaDescripcion().getText());
            Departamento departamento=(Departamento) getCmbTipoOrdenDetalle().getSelectedItem();
            ordenTrabajoDetalle.setDepartamento(departamento);
            ordenTrabajoDetalle.setNotas(""+getTxtAreaNotas().getText());
            OrdenTrabajoDetalle.EstadoEnum ordenTrabajoEnumEstado = (OrdenTrabajoDetalle.EstadoEnum) getCmbEstadoDetalle().getSelectedItem();
            if(!agregar){
                /**
                 * Cuando se guarda por primera, por defecto se guarda por recibido
                 */
                ordenTrabajoEnumEstado = OrdenTrabajoDetalle.EstadoEnum.RECIBIDO;
                ordenTrabajoDetalle.setEstado(ordenTrabajoEnumEstado.getLetra());
            }else{
                ordenTrabajoDetalle.setEstado(ordenTrabajoEnumEstado.getLetra());    
            }
            if(getCmbDateFechaEntrega()!=null){
                ordenTrabajoDetalle.setFechaEntrega(new Date(getCmbDateFechaEntrega().getDate().getTime()));
            }
            PrioridadEnumEstado prioridadEnumEstado = (PrioridadEnumEstado) getCmbPrioridadDetalle().getSelectedItem();
            ordenTrabajoDetalle.setPrioridad(prioridadEnumEstado.getLetra());
            if((Empleado) getCmbAsignadoADetalle().getSelectedItem() != null){
                ordenTrabajoDetalle.setEmpleado((Empleado) getCmbAsignadoADetalle().getSelectedItem());
            }else{
                DialogoCodefac.mensaje("Advertencia", "Se necesita crear Departamento o Empleado...", DialogoCodefac.MENSAJE_ADVERTENCIA);
            }
            ordenTrabajoDetalle.setTitulo(""+getTxtCategoria().getText());
        }
        
        if(agregar)
        {
            ordenTrabajo.addDetalle(ordenTrabajoDetalle);
            procesoMostrarDetalles();
        }else
        {
            procesoMostrarDetalles();
        }
        
        getTxtCategoria().requestFocus();
    }
    
    public void limpiarCampos()
    {
        //Orden trabajo
        getLblCodigo().setText("");
        getTxtCliente().setText("");
        getTxtDescripcion().setText("");
        getLblNombreLegal().setText("");
        getLblRazonSocial().setText("");
        
        getCmbObjetoMantenimiento().removeAllItems();
    }
    
    public void limpiarCamposDetalles()
    {
        //Detalle Orden trabajo
        getTxtAreaDescripcion().setText("");
        getTxtAreaNotas().setText("");
        getCmbDateFechaEntrega().setDate(UtilidadesFecha.getFechaHoy());
        getTxtCategoria().setText("");
    }
    
    public void setearDatos() throws ExcepcionCodefacLite
    {
            //GeneralEnumEstado generalEnumEstado = (GeneralEnumEstado) getCmbEstadoOrdenTrabajo().getSelectedItem();
            this.ordenTrabajo.setFechaIngreso(new Date(getCmbDateFechaIngreso().getDate().getTime()));
            this.ordenTrabajo.setDescripcion(""+getTxtDescripcion().getText());
            
            ObjetoMantenimiento objetoMantenimientoTmp= (ObjetoMantenimiento) getCmbObjetoMantenimiento().getSelectedItem();
            this.ordenTrabajo.setObjetoMantenimiento(objetoMantenimientoTmp);
            this.ordenTrabajo.setUsuario(session.getUsuario());
            //this.ordenTrabajo.setEstado(generalEnumEstado.getEstado());
    }
    
  
    
    private boolean verificarCamposValidados() {
        //bandera para comprobar que todos los campos esten validados
        boolean b1 = true, b2 = true;
        List<JTextArea> camposValidar = new ArrayList<>();
        //Ingresar los campos para validar 
        camposValidar.add(getTxtAreaDescripcion());
        camposValidar.add(getTxtAreaNotas());
        
        //Obtener el estado de validacion de los campos
        for (JTextArea campo : camposValidar) {
            if (!campo.getBackground().equals(Color.white)) {
                b1 = false;
            }
        }
        
        List<JTextField> camposFields = new ArrayList<>();
        camposFields.add(getTxtCategoria());
        for (JTextField camposField : camposFields) {
            if(!camposField.getBackground().equals(Color.WHITE)){
                b2 = false;
            }
        }
      
        return b1&&b2;
    }
    
    public void actualizarDetalleOrdenTrabajo()
    {
        int fila = getTableDetallesOrdenTrabajo().getSelectedRow();
        OrdenTrabajoDetalle ordenTrabajoDetalle = ordenTrabajo.getDetalles().get(fila);
        agregarDetallesOrdenTrabajo(ordenTrabajoDetalle);
        getBtnAgregarDetalle().setEnabled(true);
    }
    
    public void eliminarDetalleOrdenTrabajo()
    {
        int fila = getTableDetallesOrdenTrabajo().getSelectedRow();
        ordenTrabajo.getDetalles().remove(fila);
        procesoMostrarDetalles();
        getBtnAgregarDetalle().setEnabled(true);
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
    public boolean salirSinGrabar() {
           //TODO: Arreglar esta validacion
           //if(ordenTrabajo != null && ordenTrabajo.getCliente() != null || ordenTrabajo.getDetalles().size()>0)
           //{
           // return false;
           //}
           return true;
    }

    private void limpiarVariables() {
       this.ordenTrabajo=new OrdenTrabajo();
    }
    
    public void procesoMostrarDetalles()
    {
        mostrarDatosTabla();
        limpiarCamposDetalles();
        cargarCombos();
    }
    
    private void cargarDatosCliente(Persona cliente)
    {
        if(cliente==null)
        {
            getLblRazonSocial().setText("");
            getLblNombreLegal().setText("");            
        }
        else
        {
            getLblRazonSocial().setText(cliente.getRazonSocial());
            getLblNombreLegal().setText(cliente.getEstablecimientos().get(0).getNombreComercial());
            getTxtCliente().setText(cliente.getIdentificacion());
            cargarObjectosMantenimiento(cliente);
        }
    }
    
    private void cargarObjectosMantenimiento(Persona cliente)
    {
        getCmbObjetoMantenimiento().removeAllItems();
        if(cliente!=null)
        {
            try {
                List<ObjetoMantenimiento> resultado= ServiceFactory.getFactory().getObjetoMantenimientoServiceIf().buscarPorPropietario(session.getEmpresa(),cliente);
                UtilidadesComboBox.llenarComboBox(getCmbObjetoMantenimiento(), resultado);
            } catch (ServicioCodefacException ex) {
                Logger.getLogger(OrdenTrabajoModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RemoteException ex) {
                Logger.getLogger(OrdenTrabajoModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void agregarListener() {
        
        getBtnBuscarUltimoMantenimiento().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ObjetoMantenimiento objetoMantenimiento=(ObjetoMantenimiento) getCmbObjetoMantenimiento().getSelectedItem();
                    OrdenTrabajo ultimaOT=ServiceFactory.getFactory().getOrdenTrabajoServiceIf().consultarUltimaOTporObjectoMantenimiento(objetoMantenimiento);
                    
                    if(ultimaOT!=null)
                    {
                        imprimirReporte(ultimaOT);
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(OrdenTrabajoModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ServicioCodefacException ex) {
                    Logger.getLogger(OrdenTrabajoModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        getBtnAgregarObjecto().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] paramPostConstruct = new Object[1];
                paramPostConstruct[0] = ordenTrabajo.getCliente();

                ObserverUpdateInterface observerCreate = new ObserverUpdateInterface<ObjetoMantenimiento>() {
                    @Override
                    public void updateInterface(ObjetoMantenimiento entity) {
                        if (entity != null) 
                        {
                            getCmbObjetoMantenimiento().addItem(entity);
                            getCmbObjetoMantenimiento().setSelectedItem(entity);
                        }

                    }
                };
                panelPadre.crearDialogoCodefac(observerCreate, VentanaEnum.OBJETO_MANTENIMIENTO, false, paramPostConstruct, formularioActual);
            }
        });
        
        getTxtCliente().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                String identificacion=getTxtCliente().getText();
                if(!identificacion.equals(""))
                {
                    //mapParametros.put("tipo",OperadorNegocioEnum.CLIENTE); //TODO: Falta optimizar cuando sean clientes y proveedores o ambos 

                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        try {
                            Map<String, Object> mapParametros = new HashedMap<String, Object>();
                            mapParametros.put("identificacion", identificacion);
                            //ServiceFactory.getFactory().getPersonaServiceIf().buscarPorIdentificacion(identificacion, session.getEmpresa());
                            Persona persona=ServiceFactory.getFactory().getPersonaServiceIf().buscarPorIdentificacion(identificacion,session.getEmpresa()); //Todo crear mejor un metodo que ya obtener filtrado los datos
                            if(persona==null)
                            {
                                if(DialogoCodefac.dialogoPregunta("Crear Cliente","No existe el Cliente, lo desea crear?",DialogoCodefac.MENSAJE_ADVERTENCIA))
                                {
                                    Object[] parametros = {OperadorNegocioEnum.CLIENTE, getTxtCliente().getText()};
                                    panelPadre.crearDialogoCodefac(new ObserverUpdateInterface<Persona>() {
                                        @Override
                                        public void updateInterface(Persona entity) {
                                            ordenTrabajo.setCliente(entity);
                                            if (ordenTrabajo.getCliente() != null) 
                                            {
                                                cargarDatosCliente(entity);
                                            }
                                        }
                                    }, VentanaEnum.CLIENTE, false, parametros, formularioActual);
                                }
                            }
                            else
                            {
                                ordenTrabajo.setCliente(persona);
                                cargarDatosCliente(persona);
                               //Opcion cuando encuentra los datos del cliente 
                            }
                        } catch (RemoteException ex) {
                            Logger.getLogger(OrdenTrabajoModel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });
    }
    
}
