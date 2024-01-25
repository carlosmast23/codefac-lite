/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servicios.model;

import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ClienteEstablecimientoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.EmpleadoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.OrdenTrabajoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ProductoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ProveedorBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.TallerMecanicoInventarioBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.comprobantes.MonitorComprobanteData;
import ec.com.codesoft.codefaclite.controlador.comprobantes.MonitorComprobanteModel;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;import java.util.Map;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ObserverUpdateInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.controlador.core.swing.ReporteCodefac;
import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.controlador.vista.servicio.PresupuestoControlador;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.util.UtilidadesComprobantes;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servicios.busqueda.PresupuestoBusqueda;
import ec.com.codesoft.codefaclite.controlador.vista.servicio.PresupuestoData;
import ec.com.codesoft.codefaclite.servicios.panel.PresupuestoPanel;
import ec.com.codesoft.codefaclite.servicios.reportdata.OrdenCompraDataReporte;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.CorreoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Bodega;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empleado;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Kardex;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PersonaEstablecimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Presupuesto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PresupuestoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ProductoProveedor;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.compra.OrdenCompra;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.compra.OrdenCompraDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ConfiguracionImpresoraEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoProductoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.VentanaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.BodegaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.OrdenTrabajoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PresupuestoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ProductoProveedorServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.ParametroUtilidades;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.list.UtilidadesLista;
import ec.com.codesoft.codefaclite.utilidades.tabla.PopupMenuTabla;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesImpuestos;
import java.awt.Color;
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
import java.math.RoundingMode;
import java.net.URL;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenuItem;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public class PresupuestoModel extends PresupuestoPanel implements Runnable{
    
    private PresupuestoModel instanceThis = this;
    
    private static final String PATH_REPORTE_TMP = "tmp/reporteOrdenCompra.pdf";
    
    public static final String ETIQUETA_NOMBRE_CLIENTE = "[nombre_cliente]";
    public static final String ETIQUETA_NOMBRE_EMPLEADO = "[nombre_empleado]";
    
    private Presupuesto presupuesto;
    private PresupuestoDetalle detalleSeleccionado;
    
    private Producto producto;
    private Bodega bodega;
    private Persona persona;
    private Empleado empleadoDetalle;
    private Kardex kardex;
    private ProductoProveedor productoProveedor;
    //private Map<Persona,List<PresupuestoDetalle>> mapClientes;
    //private Map<Integer,List<PresupuestoDetalle>> mapOrden;
    private List<OrdenCompra> ordenesCompra;
    private Empleado empleado;
    
    /**
     * Hilo para procesar el envio de las notificaciones
     */
    private Thread hiloNotificaciones;

    public PresupuestoModel() {
        super.listaExclusionComponentes.add(getTxtPrecioVenta());
        super.listaExclusionComponentes.add(getTxtPrecioCompra());
        super.listaExclusionComponentes.add(getTxtCantidad());
        super.listaExclusionComponentes.add(getTxtDescuentoPrecioCompra());
        super.listaExclusionComponentes.add(getTxtDescuentoPrecioVenta());
    }
    
    
    
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        presupuesto = new Presupuesto();
        getPnlDatosAdicionalesDetalle().setVisible(false);
        getTxtCodigo().setText("");
        cargarCombos();
        addListenerBotones();
        addListenerCombos();
        addListenerTablas();
        addListenerTextos();
        addListenerCheckBox();
        initDatosTabla();
        this.getCmbFechaPresupuesto().setDate(UtilidadesFecha.getFechaHoy());
        this.setEnabled(false);
        validacionDatosIngresados=false;        
    }
    
    
    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        this.presupuesto=new Presupuesto();
        //TODO: Desctivar un momento hasta ver como funciona este tema
        /*if(presupuesto != null && presupuesto.getPresupuestoDetalles() != null || presupuesto.getPersona() != null || getCmbDetallesOrdenTrabajo().getItemCount() > 0)
        {
            Boolean respuesta = DialogoCodefac.dialogoPregunta("Alerta", "Si desea continuar se perderan los datos sin guardar?", DialogoCodefac.MENSAJE_ADVERTENCIA);
            if (respuesta) {
                   this.presupuesto = new Presupuesto();
                   this.producto = null;
                   this.persona = null;
                   this.productoProveedor = null;
                   this.mapClientes = null;
                   this.ordenesCompra = null;
                   limpiar();
                   initDatosTabla();
            }else{
                throw new ExcepcionCodefacLite("Cancelacion usuario");
            }
        }*/

    }
    
    private void validarDatos() throws ExcepcionCodefacLite
    {
        if (presupuesto.getPersona() == null) {
            DialogoCodefac.mensaje("Alerta", "Necesita seleccionar un cliente", DialogoCodefac.MENSAJE_ADVERTENCIA);
            throw new ExcepcionCodefacLite("Necesita seleccionar un Cliente");
        }
        if (presupuesto.getOrdenTrabajoDetalle() == null) {
            DialogoCodefac.mensaje("Alerta", "Necesita seleccionar una Orden de Trabajo y seleccionar un detalle de la Orden", DialogoCodefac.MENSAJE_ADVERTENCIA);
            throw new ExcepcionCodefacLite("Necesita seleccionar una Orden de Trabajo y seleccionar un detalle de la Orden");
        }
        if (presupuesto.getFechaPresupuesto() == null) {
            DialogoCodefac.mensaje("Alerta", "Necesita seleccionar una fecha para presupuestar", DialogoCodefac.MENSAJE_ADVERTENCIA);
            throw new ExcepcionCodefacLite("Necesita seleccionar una fecha para presupuestar");
        }
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        try {
            
            setearDatos();
            validarDatos();
                       
            Boolean respuesta = DialogoCodefac.dialogoPregunta("Alerta", "Estas seguro que desea realizar el presupuesto?", DialogoCodefac.MENSAJE_ADVERTENCIA);
            if (!respuesta) {
                throw new ExcepcionCodefacLite("Cancelacion usuario de Presupuesto");
            }

            PresupuestoServiceIf servicio = ServiceFactory.getFactory().getPresupuestoServiceIf();
            
            servicio.consultarActividadPresupuesto(empleado);
            presupuesto=servicio.grabar(presupuesto,getChkEnviarCorreo().isSelected());
            
            DialogoCodefac.mensaje("Correcto","El presupuesto fue grabado correctamente",DialogoCodefac.MENSAJE_CORRECTO);
            imprimir();
            
            }catch (ServicioCodefacException ex) {
                Logger.getLogger(Presupuesto.class.getName()).log(Level.SEVERE, null, ex);
                DialogoCodefac.mensaje("Error",ex.getMessage(),DialogoCodefac.MENSAJE_ADVERTENCIA);
                throw new ExcepcionCodefacLite(ex.getMessage());
            }catch (RemoteException ex) {
                Logger.getLogger(Presupuesto.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    @Override
    public void editar() throws ExcepcionCodefacLite {
         try {
            if(editarFuncionEstadoPresupuesto())
            { 
                setearDatos();
                PresupuestoServiceIf servicio = ServiceFactory.getFactory().getPresupuestoServiceIf();
                servicio.editar(this.presupuesto,getChkEnviarCorreo().isSelected());
                DialogoCodefac.mensaje("Correcto","El presupuesto fue editado correctamente",DialogoCodefac.MENSAJE_CORRECTO);
            }else
            {
                DialogoCodefac.mensaje("Advertencia", "El estado del presupuesto es incorrecto", DialogoCodefac.MENSAJE_ADVERTENCIA);
            }
      } 
      catch (RemoteException ex) 
      {
            Logger.getLogger(OrdenTrabajoModel.class.getName()).log(Level.SEVERE, null, ex);
            DialogoCodefac.mensaje("Error","Error de comunicacion con el servidor",DialogoCodefac.MENSAJE_INCORRECTO);
      } catch (ServicioCodefacException ex) {
            Logger.getLogger(PresupuestoModel.class.getName()).log(Level.SEVERE, null, ex);
            DialogoCodefac.mensaje(new CodefacMsj(ex.getMessage(), CodefacMsj.TipoMensajeEnum.ERROR));
            throw new ExcepcionCodefacLite(ex.getMessage());
        }
    }
    
    public boolean editarFuncionEstadoPresupuesto()
    {   
        Presupuesto.EstadoEnum generalEnumEstado = Presupuesto.EstadoEnum.getByLetra(this.presupuesto.getEstado());
        Presupuesto.EstadoEnum estadoPresupuestoEnum  = (Presupuesto.EstadoEnum) getCmbEstadoPresupuesto().getSelectedItem();    
        boolean b = false;
        switch (generalEnumEstado) {
            case ABANDONADO:
                if (!(estadoPresupuestoEnum.equals(Presupuesto.EstadoEnum.FACTURADO) || estadoPresupuestoEnum.equals(Presupuesto.EstadoEnum.ANULADO))) {
                    return true;
                }
                break;
            case ANULADO:
                if (estadoPresupuestoEnum.equals(Presupuesto.EstadoEnum.TERMINADO)) {
                    return true;
                }
                break;
            case APROBADO:
                return true;

            case FACTURADO:
                DialogoCodefac.mensaje("Advertencia", "El presupuesto esta facturado no se permiten modificaciones", DialogoCodefac.MENSAJE_ADVERTENCIA);
                break;
            case PRESUPUESTANDO:
                if (!(estadoPresupuestoEnum.equals(Presupuesto.EstadoEnum.FACTURADO) || estadoPresupuestoEnum.equals(Presupuesto.EstadoEnum.ANULADO))) {
                    return true;
                }
                break;
            case TERMINADO:
                if (estadoPresupuestoEnum.equals(Presupuesto.EstadoEnum.PRESUPUESTANDO) || estadoPresupuestoEnum.equals(Presupuesto.EstadoEnum.ABANDONADO) || estadoPresupuestoEnum.equals(Presupuesto.EstadoEnum.TERMINADO)) {
                    return true;
                }
                break;

        }
        return false;
    }
    
    @Override
    public void eliminar() throws ExcepcionCodefacLite {
        if(getEstadoFormularioEnum().equals(EstadoFormularioEnum.EDITAR)) //Opcion solo disponible para editar
        {            
            if(DialogoCodefac.dialogoPregunta(MensajeCodefacSistema.Preguntas.ELIMINAR_REGISTRO))
            {
                try 
                {                    
                    ServiceFactory.getFactory().getPresupuestoServiceIf().eliminar(presupuesto);
                    DialogoCodefac.mensaje(MensajeCodefacSistema.AccionesFormulario.ELIMINADO_CORRECTAMENTE);
                } catch (ServicioCodefacException ex) {
                    Logger.getLogger(PresupuestoModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (RemoteException ex) {
                    Logger.getLogger(PresupuestoModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }                        
        }
    }

    @Override
    public void imprimir() {
        //InputStream path = RecursoCodefac.JASPER_SERVICIO.getResourceInputStream("presupuesto.jrxml");
        //ReporteCodefac.generarReporteInternalFramePlantilla(path, obtenerMapReporte(), obtenerDetallesReporte(), panelPadre,"Presupuesto");
        JasperPrint jasper= PresupuestoControlador.getReporteJasperPresupuesto(presupuesto);
        ReporteCodefac.generarReporteInternalFrame(jasper, panelPadre, "Presupuesto "+presupuesto.getCodigo(), ConfiguracionImpresoraEnum.NINGUNA);
    }
    
    /*private Map<String,Object> obtenerMapReporte()
    {
        Map<String,Object> parametros=new HashMap<String,Object>();
        parametros.put("identificacion", presupuesto.getPersona().getIdentificacion());
        parametros.put("razonSocial", presupuesto.getPersona().getRazonSocial());
        parametros.put("telefonos",(presupuesto.getPersona().getEstablecimientos().get(0).getTelefonoCelular()!=null )?presupuesto.getPersona().getEstablecimientos().get(0).getTelefonoCelular():"");
        parametros.put("direccion", (presupuesto.getPersona().getEstablecimientos().get(0).getDireccion()!=null)?presupuesto.getPersona().getEstablecimientos().get(0).getDireccion():"");
        parametros.put("estado", presupuesto.getEstadoEnum().getNombre());
        parametros.put("fecha", presupuesto.getFechaPresupuesto().toString());
        parametros.put("correo", (presupuesto.getPersona().getCorreoElectronico()!=null)?presupuesto.getPersona().getCorreoElectronico():"");
        parametros.put("codigo", presupuesto.getId().toString());
        
        String objetoMantenimiento="";
        String kilometraje="";
        if(presupuesto.obtenerObjectoMantenimiento()!=null)
        {
            objetoMantenimiento=presupuesto.obtenerObjectoMantenimiento().toString();
            kilometraje=presupuesto.obtenerObjectoMantenimiento().getKilometraje()+"";
        }
        
        parametros.put("objectoMantenimiento", objetoMantenimiento);
        parametros.put("kilometraje", kilometraje);
        
        parametros.put("notas", presupuesto.getOrdenTrabajoDetalle().getNotas());
        
        //Datos de la orden de trabajo
        parametros.put("ordenTrabajo", presupuesto.getOrdenTrabajoDetalle().getOrdenTrabajo().getId().toString());
        parametros.put("descripcion", presupuesto.getDescripcion());
        parametros.put("observaciones", presupuesto.getObservaciones());
        
        //Calcular los totales
        parametros.put("subtotal", presupuesto.getTotalVenta().toString());
        parametros.put("descuento", presupuesto.getDescuentoVenta().toString());
        parametros.put("total", presupuesto.calcularTotalMenosDescuentos().toString());
        
        //Calcular los totales
        Presupuesto.ResultadoTotales totales=presupuesto.obtenerMapReporteTotales(session.getEmpresa().getIdentificacion());
        
        parametros.put("valorPagarCliente", totales.valorPagarCliente.toString());
        parametros.put("valorProveedores", totales.valoresProveedores.toString());
        parametros.put("produccionInterna", totales.produccionInterna.toString());
        parametros.put("utilidad", totales.utilidad.toString());
        
        return parametros ;
    }*/
    
    
        
    
    /*private List<PresupuestoData> obtenerDetallesReporte()
    {
        List<PresupuestoData> datos=new ArrayList<PresupuestoData>();
        if(presupuesto.getPresupuestoDetalles()!=null)
        {
            for (PresupuestoDetalle presupuestoDetalle : presupuesto.getPresupuestoDetalles()) {
                PresupuestoData presupuestoData=new PresupuestoData();
                presupuestoData.setCantidad(presupuestoDetalle.getCantidad().toString());
                presupuestoData.setDescuento(presupuestoDetalle.getDescuentoCompra().toString());
                presupuestoData.setIdentificacion(presupuestoDetalle.getPersona().getIdentificacion());
                presupuestoData.setRazonSocial(presupuestoDetalle.getPersona().getRazonSocial());
                presupuestoData.setProducto(presupuestoDetalle.getProductoProveedor().getProducto().getNombre());
                presupuestoData.setSubtotal(presupuestoDetalle.calcularSubtotalCompra().toString());
                presupuestoData.setTotal(presupuestoDetalle.calcularTotalCompra().toString());
                presupuestoData.setCategoria(presupuestoDetalle.getProducto().getTipoProductoEnum().getNombre());
                
                datos.add(presupuestoData);
            }
            
            //Ordenar los datos en función del tipo de servicio
            UtilidadesLista.ordenarLista(datos, new Comparator<PresupuestoData>() {
                @Override
                public int compare(PresupuestoData dato1, PresupuestoData dato2) {
                    return dato1.getCategoria().compareTo(dato2.getCategoria());
                }
            });
            
        }
        return datos;
        
    }*/

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
            Presupuesto.EstadoEnum generalEnumEstadoTemp = Presupuesto.EstadoEnum.getByLetra(this.presupuesto.getEstado());
            switch(generalEnumEstadoTemp)
            {
                case FACTURADO:
                    DialogoCodefac.mensaje("Advertencia", "El presupuesto esta factura no se permiten modificaciones", DialogoCodefac.MENSAJE_ADVERTENCIA);
                break;
                case PRESUPUESTANDO:
                break;
                case TERMINADO:
                    //DialogoCodefac.mensaje("Advertencia", "El presupuesto esta terminado no se permiten modificaciones", DialogoCodefac.MENSAJE_ADVERTENCIA);
                break;
                   
            }
            
            getCmbEstadoPresupuesto().setSelectedItem(generalEnumEstadoTemp);
            getTxtCodigo().setText(""+this.presupuesto.getId());
            getTxtCliente().setText(this.presupuesto.getPersona().getIdentificacion()+" - "+this.presupuesto.getPersona().getRazonSocial());  
            getTxtAreaObservaciones().setText(this.presupuesto.getObservaciones());
            getCmbTipoPresupuesto().setSelectedItem(presupuesto.getCatalogoProducto());
            /**
             * Cargar detalles que existian de la Orden de trabajo
             */
            cargarDetallesOrdenTrabajo(this.presupuesto.getOrdenTrabajoDetalle().getOrdenTrabajo());
            getCmbFechaPresupuesto().setDate(this.presupuesto.getFechaPresupuesto());
            
            //Cargar los datos de la factura en el caso que tenga relacionada una venta
            getTxtVentaRelacionada().setText((presupuesto.getFactura()!=null)?presupuesto.getFactura().getPreimpreso():"");
            
            if(this.presupuesto.getFechaValidez()!=null)
            {
                getTxtDiasPresupuesto().setText("" + UtilidadesFecha.obtenerDistanciaDias(this.presupuesto.getFechaPresupuesto(), this.presupuesto.getFechaValidez()));
            }
            else
            {
                getTxtDiasPresupuesto().setText("");
            }
            
            getLblIndicarFechaValidez().setText(""+this.presupuesto.getFechaValidez());
            //ordenarDetallesEnFuncionDeOrdenCompra();
            mostrarDatosTabla();
            calcularTotales(false);
        }
        else{
             throw new ExcepcionCodefacLite("Cancelando Busqueda");
        }
        
    }

    @Override
    public void limpiar() {        
        limpiarDetalles();
        limpiarTotales();
        this.getTxtOrdenTrabajo().setText("");
        this.getTxtCliente().setText("");
        this.getCmbDetallesOrdenTrabajo().removeAllItems();
        this.getTxtCodigo().setText("");
        this.getTxtDescripcion().setText("");
        this.getLblObjetoMantenimiento().setText("");
        this.getTxtKilometraje().setText("");
        this.getLblTotalProductos().setText("0.00");
        this.getLblTotalServicios().setText("0.00");
        this.getTxtVentaRelacionada().setText("");
        this.getChkEnviarCorreo().setSelected(false);
        
        //Hacer invible los campos de la venta que por el momento no estoy usando
        getLblDescuentoVenta().setVisible(false);
        getLblPrecioVenta().setVisible(false);
        getTxtPrecioVenta().setVisible(false);
        getTxtDescuentoPrecioVenta().setVisible(false);
        
        
        getCmbEstadoPresupuesto().setSelectedItem(Presupuesto.EstadoEnum.PRESUPUESTANDO);
        getBtnAgregarDetalle().setEnabled(true);
        initDatosTabla();
    }

    public String getNombre() {
        return "Presupuesto";
    }

    @Override
    public String getURLAyuda() 
    {
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
         * Cargar los tipos de documentos para cargar del inventario
         */
        /*getCmbTipoDocumento().removeAllItems();
        getCmbTipoDocumento().addItem(TipoDocumentoEnum.INVENTARIO);
        getCmbTipoDocumento().addItem(TipoDocumentoEnum.LIBRE);*/
        
        /**
         * Estado general de Presupuesto
         */
        getCmbEstadoPresupuesto().removeAllItems();
        for(Presupuesto.EstadoEnum gem : Presupuesto.EstadoEnum.values())
        {
            getCmbEstadoPresupuesto().addItem(gem);
        }
        
        
        /**
         * Cargando los tipos de catalogos disponibles para los servicios
         */
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
        //DefaultTableModel modeloTablaDetallesPresupuesto = new DE;
        getTableDetallesPresupuesto().setModel(new DefaultTableModel());
        
        //DefaultTableModel modeloTablaDetallesPresupuesto2 = UtilidadesTablas.crearModeloTabla(new String[]{"#","Proveedor","Producto","Valor compra","Descuento compra","Valor venta","Descuento venta","Cantidad"}, new Class[]{String.class,String.class,String.class,String.class,String.class,String.class,String.class,String.class});
        getTableDetallesServicio().setModel(new DefaultTableModel());
    }
    
    private void cargarVistaEmpleado(Empleado empleado)
    {
        //TipoDocumentoEnum tipoDocumentoEnum= (TipoDocumentoEnum) getCmbTipoDocumento().getSelectedItem();
        
        if(producto!=null && producto.getTipoProductoEnum().equals(TipoProductoEnum.SERVICIO))
        {
            getPnlDatosAdicionalesDetalle().setVisible(true);
            if(empleado!=null)
            {
                getTxtEmpleadoDetalle().setText(empleado.getNombresCompletos());
            }
            else
            {
                getTxtEmpleadoDetalle().setText("");
            }
        }
        else
        {
            getPnlDatosAdicionalesDetalle().setVisible(false);
        }
        
    }
    
    public void addListenerBotones()
    {        
        
        getBtnEmpleadoBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                EmpleadoBusquedaDialogo empleadosBusquedaDialogo = new EmpleadoBusquedaDialogo();
                BuscarDialogoModel buscarDialogo = new BuscarDialogoModel(empleadosBusquedaDialogo);
                buscarDialogo.setVisible(true);
                Empleado empleado = (Empleado) buscarDialogo.getResultado();
                if(empleado!=null)
                {
                    empleadoDetalle=empleado;
                    cargarVistaEmpleado(empleado);
                }
            }
        });
        
        
        getBtnCliente().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                ClienteEstablecimientoBusquedaDialogo buscarBusquedaDialogo = new ClienteEstablecimientoBusquedaDialogo(session);
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
                    //Verificar que no tenga un presupuesto generado para lanzar una advertencia                                        
                    if(verificarPresupuestoActivo(ordenTrabajo))
                    {
                        if(!DialogoCodefac.dialogoPregunta(new CodefacMsj("Advertencia","Ya existe un presupuesto para esta orden de trabajo, desea continuar ?",CodefacMsj.ModoMensajeEnum.MENSAJE_ADVERTENCIA)))
                        {
                            return;
                        }
                    }
                    
                    presupuesto.setPersona(ordenTrabajo.getCliente());
                    getTxtCliente().setText(ordenTrabajo.getCliente().getIdentificacion()+" - "+ordenTrabajo.getCliente().getRazonSocial());
                    getLblObjetoMantenimiento().setText((ordenTrabajo.getObjetoMantenimiento()!=null)?ordenTrabajo.getObjetoMantenimiento().toString():"");
                    
                    String kilometrajeTxt="";
                    if(ordenTrabajo.getObjetoMantenimiento()!=null)
                    {
                        if(ordenTrabajo.getKilometraje()!=null)
                        {
                            kilometrajeTxt=ordenTrabajo.getKilometraje()+"";
                        }
                        else
                        {
                            kilometrajeTxt=ordenTrabajo.getObjetoMantenimiento().getKilometraje()+"";
                        }
                    }
                    getTxtKilometraje().setText(kilometrajeTxt);
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
            public void actionPerformed(ActionEvent e) 
            {
                if(persona!=null)
                {
                    //if(persona.getIdentificacion().equals(session.getEmpresa().getIdentificacion()))
                    //TipoDocumentoEnum tipoDocumentoEnum= (TipoDocumentoEnum) getCmbTipoDocumento().getSelectedItem();
                    
                    //if(tipoDocumentoEnum.equals(TipoDocumentoEnum.INVENTARIO))
                    //{
                        buscarProductosConInventario();
                    //}
                    //else if(tipoDocumentoEnum.equals(TipoDocumentoEnum.LIBRE))
                    //{
                        //Buscar el producto sin inventario
                    //    buscarProductoSinInventario();
                    //}
                }
                else
                {
                    DialogoCodefac.mensaje(new CodefacMsj("Por favor seleccione primero un proveedor ",CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
                }
            }
        });
        
        getBtnProveedor().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProveedorBusquedaDialogo buscarBusquedaDialogo = new ProveedorBusquedaDialogo(session.getEmpresa());
                BuscarDialogoModel buscarDialogo = new BuscarDialogoModel(buscarBusquedaDialogo);
                buscarDialogo.setVisible(true);
                persona = ((PersonaEstablecimiento) buscarDialogo.getResultado()).getPersona();
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
                calcularTotales(true);
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
    
    private Boolean verificarPresupuestoActivo(OrdenTrabajo ordenTrabajo)
    {
        try {
            List<Presupuesto> presupuestoList=ServiceFactory.getFactory().getPresupuestoServiceIf().consultarPorOrdenTrabajo(ordenTrabajo);
            if(presupuestoList.size()>0)
            {
                return true;
            }
            
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(PresupuestoModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(PresupuestoModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    private void buscarProductoSinInventario()
    {
        ProductoBusquedaDialogo productoBusquedaDialogo = new ProductoBusquedaDialogo(session.getEmpresa(),true,true);
        productoBusquedaDialogo.setAplicacionColumna(true);
        
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(productoBusquedaDialogo);
        buscarDialogoModel.setVisible(true);
        producto = (Producto) buscarDialogoModel.getResultado();
        if (producto != null) {
            cargarVistaEmpleado(empleado);
            cargarDatosDetallePantalla(producto);
        }
    }
    
    private void buscarProductosConInventario()
    {
        try {
            //TODO: Ver si esta parte de la bodega se puede optimizar para poder crear directamente en la parte de Busqueda Dialogo y unificar esta parte en la pantalla de facturacion
            BodegaServiceIf service = ServiceFactory.getFactory().getBodegaServiceIf();
            Bodega bodegaVenta = service.obtenerBodegaVenta(session.getSucursal());
            if(bodegaVenta==null)
            {
                DialogoCodefac.mensaje(new CodefacMsj("No existe un tipo de Bodega de Venta Configurado",CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
            }
            
            //ProductoInventarioBusquedaDialogo productoInventarioBusquedaDialogo = new ProductoInventarioBusquedaDialogo(EnumSiNo.SI, session.getEmpresa(), bodegaVenta,true);
            TallerMecanicoInventarioBusquedaDialogo productoInventarioBusquedaDialogo=new TallerMecanicoInventarioBusquedaDialogo(EnumSiNo.SI, session.getEmpresa(), bodegaVenta);
            
            BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(productoInventarioBusquedaDialogo,1100);
            buscarDialogoModel.setVisible(true);
            Object resultadoTemporal=buscarDialogoModel.getResultado();
            if(resultadoTemporal instanceof Object[])
            {
                Object resultado[]=(Object[]) resultadoTemporal;
                kardex=(Kardex) resultado[0];
            }
            else
            {
                kardex = (Kardex) resultadoTemporal;
            }
            
            producto=kardex.getProducto();
            bodega=kardex.getBodega();
            if(getChkInventarioProveedor().isSelected())
            {
                /*String numeroPrecio=ParametroUtilidades.obtenerValorParametro(session.getEmpresa(),ParametroCodefac.NUMERO_PRECIO_PRESUPUESTO);
                BigDecimal precio=BigDecimal.ZERO;
                if(numeroPrecio==null)
                {
                    precio=producto.getValorUnitario();
                }
                else
                {
                    precio=producto.obtenerPrecioVentaPorNombre(numeroPrecio);                
                }*/
                BigDecimal precio=producto.obtenerPrecioPresupuesto(session.getEmpresa());
                //getTxtPrecioCompra().setText(producto.getValorUnitario()+"");
                getTxtPrecioCompra().setText(precio+""); 
            }
            else
            {
                getTxtPrecioCompra().setText(kardex.getCostoPromedio()+"");
            }
            
            cargarVistaEmpleado(empleado);
            cargarDatosDetallePantalla(producto);
            //kardexSeleccionado = kardex;
            //productoSeleccionado = kardexSeleccionado.getProducto();
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(PresupuestoModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(PresupuestoModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void cargarDatosDetallePantalla(Producto producto)
    {
        verificarExistenciadeProductoProveedor();
        getTxtProductoDetalle().setText(producto.getCodigoEAN() + " - " + producto.getNombre());
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
                else
                {
                    obtenerOrdenTrabajoDetalle();
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
                if(!(getCmbDetallesOrdenTrabajo().getItemCount() == 0))
                {
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
    
    public void addListenerCheckBox()
    {
        getChkInventarioProveedor().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                try {
                    persona=ServiceFactory.getFactory().getPersonaServiceIf().buscarPorIdentificacion(session.getEmpresa().getIdentificacion(),session.getEmpresa());
                    
                    //Si no existe creado el dato le creo un proveedor con la misma identificación
                    if(persona==null)
                    {
                        persona=ServiceFactory.getFactory().getPersonaServiceIf().crearProveedorDesdeEmpresa(session.getEmpresa());
                    }
                    
                    if(persona != null)
                    {
                        getTxtProveedorDetalle().setText(persona.getRazonSocial()+" - "+persona.getIdentificacion());
                    }
                    
                } catch (RemoteException ex) {
                    Logger.getLogger(PresupuestoModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ServicioCodefacException ex) {
                    Logger.getLogger(PresupuestoModel.class.getName()).log(Level.SEVERE, null, ex);
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
             public void focusGained(FocusEvent e) {}

             @Override
             public void focusLost(FocusEvent e) {
                calcularFechaProxima();
             }
         });
        
        getTxtSubtotalVentas().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {}

            @Override
            public void focusLost(FocusEvent e) {
                actualizarTotalVentaVista();
            }
        });
        
        getTxtSubtotalVentas().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) 
                {
                    actualizarTotalVentaVista();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });
       
        getTxtDescuentoVentas().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            }

            @Override
            public void focusLost(FocusEvent e) {
                actualizarTotalVentaVista();
            }
        });
        
    }
    
    private void actualizarTotalVentaVista()
    {
        BigDecimal precioVenta= new BigDecimal(getTxtSubtotalVentas().getText());
        BigDecimal descuentoVenta= new BigDecimal(getTxtDescuentoVentas().getText());
        
        BigDecimal total=precioVenta.subtract(descuentoVenta);
        getLblTotalVenta().setText(total.toString());
        
    }
    
    private void cargarDetalleSeleccionado(JTable tablaSeleccionada)
    {
        getBtnAgregarDetalle().setEnabled(false);
        try {
            int fila = tablaSeleccionada.getSelectedRow();
            if(fila>=0)
            {
                PresupuestoDetalle presupuestoDetalle = (PresupuestoDetalle) tablaSeleccionada.getValueAt(fila, 0);

                if (presupuestoDetalle != null) {
                    cargarInformacionDetallePresupuesto(presupuestoDetalle);
                } else {
                    limpiarDetalles();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void addListenerTablas()
    {
        getTableDetallesServicio().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cargarDetalleSeleccionado(getTableDetallesServicio());
            }
        });
        
        getTableDetallesPresupuesto().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {                
                cargarDetalleSeleccionado(getTableDetallesPresupuesto());
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
        if(ordenTrabajo.getDescripcion() !=  null && !(ordenTrabajo.getDescripcion().equals("")))
        {
            getTxtOrdenTrabajo().setText("N° " + ordenTrabajo.getId() + " - " + ordenTrabajo.getDescripcion());    
        }
        else{
            getTxtOrdenTrabajo().setText("N° " + ordenTrabajo.getId() + " - " + "Orden trabajo");    
        }

        getCmbDetallesOrdenTrabajo().removeAllItems();
        for(OrdenTrabajoDetalle pd : ordenTrabajo.getDetalles())
        {
            if(pd.getEstadoEnum().equals(OrdenTrabajoDetalle.EstadoEnum.RECIBIDO) || pd.getEstadoEnum().equals(OrdenTrabajoDetalle.EstadoEnum.PRESUPUESTADO))
            {
                getCmbDetallesOrdenTrabajo().addItem(pd);
            }
        }
        /**
          * Definir el detalle escogido
          */
        getCmbDetallesOrdenTrabajo().setSelectedItem((OrdenTrabajoDetalle)this.presupuesto.getOrdenTrabajoDetalle());
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
        //Boolean perPro = true;
        if(presupuestoDetalle == null)
        {
            presupuestoDetalle = new PresupuestoDetalle(true);
            agregar = true;
        }
        
        try{
            if(this.persona == null){
                DialogoCodefac.mensaje("Advertencia", "Debe seleccionar un proveedor", DialogoCodefac.MENSAJE_ADVERTENCIA);
                return;
            }
         
            if(this.producto == null)
            {
                DialogoCodefac.mensaje("Advertencia", "Debe seleccionar un producto", DialogoCodefac.MENSAJE_ADVERTENCIA);
                return;
            }
            
               
            if (!panelPadre.validarPorGrupo("detalles")) {
                return;
            }
            
            
            //Presupuesto.EstadoEnum generalEnumEstado = Presupuesto.EstadoEnum.getByLetra(this.presupuesto.getEstado());
            Presupuesto.EstadoEnum generalEnumEstado = presupuesto.getEstadoEnum();
            
            //Validacion solo para el modo editar
            if(getEstadoFormularioEnum().equals(getEstadoFormularioEnum().EDITAR))
            {
                if(generalEnumEstado.equals(Presupuesto.EstadoEnum.FACTURADO))
                {
                    DialogoCodefac.mensaje("Advertencia", "No se puede editar un presupuesto facturado", DialogoCodefac.MENSAJE_ADVERTENCIA);
                }
                else if(generalEnumEstado.equals(Presupuesto.EstadoEnum.ANULADO))
                {
                    DialogoCodefac.mensaje("Advertencia", "No se puede editar un presupuesto anulado o eliminado", DialogoCodefac.MENSAJE_ADVERTENCIA);
                }
            }
            
            presupuestoDetalle.setProducto(this.producto);
            presupuestoDetalle.setBodega(this.bodega);
            presupuestoDetalle.setPersona(this.persona);
            presupuestoDetalle.setEmpleado(this.empleadoDetalle);
            presupuestoDetalle.setKardexId((this.kardex!=null)?this.kardex.getId():null);
            if (verificarCamposValidados()) {
                
                
                BigDecimal precioCompra = new BigDecimal(getTxtPrecioCompra().getText());
                
                BigDecimal descuentoCompra = new BigDecimal(getTxtDescuentoPrecioCompra().getText());
                
                BigDecimal precioVenta = new BigDecimal(getTxtPrecioVenta().getText());
                
                BigDecimal descuentoVenta = new BigDecimal(getTxtDescuentoPrecioVenta().getText());
                
                if(precioCompra.subtract(descuentoCompra).compareTo(BigDecimal.ZERO)<=0)
                {
                    DialogoCodefac.mensaje("Error","No se puede ingresar un detalle con precio de compra igual o menor que 0",DialogoCodefac.MENSAJE_INCORRECTO);
                    return;
                }
                
                presupuestoDetalle.setReservadoEnum(EnumSiNo.getEnumByBoolean(getChkReserva().isSelected()));
                presupuestoDetalle.setPrecioCompra(precioCompra.setScale(2, BigDecimal.ROUND_HALF_UP));                
                presupuestoDetalle.setDescuentoCompra(descuentoCompra.setScale(2, BigDecimal.ROUND_HALF_UP));
                presupuestoDetalle.setPrecioVenta(precioVenta.setScale(2, BigDecimal.ROUND_HALF_UP));
                presupuestoDetalle.setDescuentoVenta(descuentoVenta.setScale(2, BigDecimal.ROUND_HALF_UP));
                presupuestoDetalle.setCantidad(new BigDecimal(getTxtCantidad().getText()));
                
                if (estado) {
                    presupuestoDetalle.setEstado(EnumSiNo.SI.getLetra());
                } else {
                    presupuestoDetalle.setEstado(EnumSiNo.NO.getLetra());
                }
                BigDecimal costo = new BigDecimal(getTxtPrecioVenta().getText());
                if(costo==null)
                {
                    costo=BigDecimal.ZERO;
                }
                this.productoProveedor.setCosto(costo.setScale(2, BigDecimal.ROUND_HALF_UP));
                presupuestoDetalle.setProductoProveedor(this.productoProveedor);
            } else {
                throw new ExcepcionCodefacLite("Campos detalles no validos");
            }

        } catch (ExcepcionCodefacLite e) {
            Logger.getLogger(PresupuestoModel.class.getName()).log(Level.SEVERE, null, e);
        }

        if(agregar )
        {
            //buscarNumeroOrdenPresupuestoDetalle(presupuestoDetalle);
            this.presupuesto.addDetalle(presupuestoDetalle);            
            //ordenarDetallesEnFuncionDeOrdenCompra();
            mostrarDatosTabla();
            limpiarDetalles();
        }
        
        
        limpiarDetalles();
        
     }
    
    /**
     * Funcion que me permite buscar el numero de una orden de compra o la creaa
     */
    /*private void buscarNumeroOrdenPresupuestoDetalle(PresupuestoDetalle presupuestoDetalle)
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
    }*/
    
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
        this.detalleSeleccionado=presupuestoDetalle;
        this.producto = presupuestoDetalle.getProducto();
        this.persona = presupuestoDetalle.getPersona();
        this.empleadoDetalle=presupuestoDetalle.getEmpleado();
        this.productoProveedor = presupuestoDetalle.getProductoProveedor();
        getTxtProveedorDetalle().setText(presupuestoDetalle.getPersona().getIdentificacion()+" - "+presupuestoDetalle.getPersona().getRazonSocial() );
        getTxtProductoDetalle().setText(presupuestoDetalle.getProducto().getCodigoEAN()+" - "+presupuestoDetalle.getProducto().getNombre());
        getTxtPrecioCompra().setText(presupuestoDetalle.getPrecioCompra()+"");
        getTxtDescuentoPrecioCompra().setText(presupuestoDetalle.getDescuentoCompra()+"");
        getTxtPrecioVenta().setText(presupuestoDetalle.getPrecioVenta()+"");
        getTxtDescuentoPrecioVenta().setText(presupuestoDetalle.getDescuentoVenta()+"");
        getTxtCantidad().setText(presupuestoDetalle.getCantidad().intValue()+"");
        cargarVistaEmpleado(empleado);
        getChkReserva().setSelected((presupuestoDetalle.getReservadoEnum()!=null)?presupuestoDetalle.getReservadoEnum().getBool():false);
        
    }
    
    public void calcularTotales(Boolean recalcularVenta)
    {
        List<PresupuestoDetalle> detalles = this.presupuesto.getPresupuestoDetalles();
        BigDecimal subtotalCompra = new BigDecimal(BigInteger.ZERO);
        BigDecimal descuentoCompra = new BigDecimal(BigInteger.ZERO);
        BigDecimal subtotalVenta = new BigDecimal(BigInteger.ZERO);
        BigDecimal descuentoVenta = new BigDecimal(BigInteger.ZERO);
        BigDecimal totalCompra = new BigDecimal(BigInteger.ZERO);
        BigDecimal totalVenta = new BigDecimal(BigInteger.ZERO);
        
        //BigDecimal totalCompra=BigDecimal.ZERO;
        for (PresupuestoDetalle detalle : detalles) {
            
            Producto producto=detalle.getProducto();
            TipoProductoEnum tipoProducto=producto.getTipoProductoEnum();
            
            if(recalcularVenta)
            {
                subtotalVenta = subtotalVenta.add(detalle.getPrecioCompra().multiply(detalle.getCantidad()));
                //subtotalVenta = subtotalVenta.add(detalle.getPrecioCompra());
                descuentoVenta = descuentoVenta.add(detalle.getDescuentoCompra());
            }
            
            if(tipoProducto.equals(TipoProductoEnum.PRODUCTO))
            {
                subtotalCompra = subtotalCompra.add(detalle.getPrecioCompra().multiply(detalle.getCantidad()));
                //subtotalCompra = subtotalCompra.add(detalle.getPrecioCompra());
                descuentoCompra = descuentoCompra.add(detalle.getDescuentoCompra());
            }
            
            //totalVenta = subtotalVenta.subtract(descuentoVenta);
        }
        
        if(!recalcularVenta)
        {
            subtotalVenta=this.presupuesto.getTotalVenta();
            descuentoVenta=this.presupuesto.getDescuentoVenta();
        }
        
        totalVenta=subtotalVenta.subtract(descuentoVenta);
        totalCompra=subtotalCompra.subtract(descuentoCompra);
        /**
         * Mostrar valores en pantalla
         */
        getLblSubtotalCompra().setText(subtotalCompra.setScale(2, RoundingMode.HALF_UP).toString());
        
        //valores de los sibtotales
        getLblTotalProductos().setText(subtotalCompra.setScale(2, RoundingMode.HALF_UP).toString());
        getLblTotalServicios().setText(subtotalVenta.subtract(subtotalCompra).setScale(2, RoundingMode.HALF_UP).toString());
        
        
        getLblDescuentoCompra().setText(descuentoCompra.toString());
        getLblTotalCompra().setText(totalCompra.setScale(2, RoundingMode.HALF_UP).toString());
        
        //getTxtSubtotalVentas().setText(subtotalVenta.toString());
        //getTxtDescuentoVentas().setText(descuentoVenta.toString());
        
        //BigDecimal subtotalVentas=(presupuesto.getTotalVenta()!=null)?presupuesto.getTotalVenta():BigDecimal.ZERO;
        //BigDecimal descuentoVentas=(presupuesto.getDescuentoVenta()!=null)?presupuesto.getDescuentoVenta():BigDecimal.ZERO;
        
        getTxtSubtotalVentas().setText(subtotalVenta.setScale(2, RoundingMode.HALF_UP).toString());
        getTxtDescuentoVentas().setText(descuentoVenta.toString());

        BigDecimal valorIva=UtilidadesImpuestos.calcularValorIva(ParametrosSistemaCodefac.obtenerIvaDefecto(), totalVenta);
        
        getLblIva().setText(valorIva.setScale(2,ParametrosSistemaCodefac.REDONDEO_POR_DEFECTO)+"");
                
        
        BigDecimal totalConIva=UtilidadesImpuestos.agregarValorIva(ParametrosSistemaCodefac.obtenerIvaDefecto(), totalVenta);
        getLblTotalVenta().setText(totalConIva.setScale(2,ParametrosSistemaCodefac.REDONDEO_POR_DEFECTO)+"");
        
        /**
         * Cargar valores en Presupuesto}
         */
        setearValoresPresupuesto(descuentoCompra, descuentoVenta, totalCompra, totalVenta);
        //Boolean.FALSE
       
    }
    
    
    public void limpiarDetalles()
    {
        if(!getChkInventarioProveedor().isSelected())
        {
            this.persona = null;
            this.getTxtProveedorDetalle().setText("");
        }
        
        this.getTxtProductoDetalle().setText("");
        this.getTxtPrecioCompra().setText("0.00");
        this.getTxtDescuentoPrecioCompra().setText("0.00");
        this.getTxtPrecioVenta().setText("0.00");
        this.getTxtDescuentoPrecioVenta().setText("0.00");
        this.getTxtCantidad().setText("1");
        
        this.producto = null;
        cargarVistaEmpleado(empleadoDetalle);
    }
    
    public void limpiarTotales()
    {
        this.getLblDescuentoCompra().setText("0.00");
        this.getTxtDescuentoVentas().setText("0");
        this.getLblSubtotalCompra().setText("0.00");
        this.getTxtSubtotalVentas().setText("0");
        this.getLblTotalCompra().setText("0.00");
        this.getLblTotalVenta().setText("0.00");
    }
     
    private void setearDatos() 
    {               
            Presupuesto.EstadoEnum generalEnumEstado = (Presupuesto.EstadoEnum) getCmbEstadoPresupuesto().getSelectedItem();
            this.presupuesto.setEmpresa(session.getEmpresa());
            this.presupuesto.setSucursal(session.getSucursal());
            this.presupuesto.setUsuario(session.getUsuario());
            this.presupuesto.setEstado(generalEnumEstado.getLetra());
            this.presupuesto.setDescripcion(""+getTxtDescripcion().getText());
            this.presupuesto.setCatalogoProducto((CatalogoProducto) getCmbTipoPresupuesto().getSelectedItem());
            this.presupuesto.setObservaciones(""+getTxtAreaObservaciones().getText());
            try{   
                this.presupuesto.setFechaPresupuesto(new Date(getCmbFechaPresupuesto().getDate().getTime()));
            }
            catch(Exception e)
            {
                DialogoCodefac.mensaje("Alerta", "Seleccione la fecha de ingreso para Orden Trabajo", DialogoCodefac.MENSAJE_ADVERTENCIA);
            }
            //Todo: Setear este dato al momento de crear en el servidor
            this.presupuesto.setFechaCreacion(UtilidadesFecha.getFechaHoyTimeStamp());
            
            /**
             * Grabar los totales de la venta
             */
            this.presupuesto.setTotalVenta(new BigDecimal(getTxtSubtotalVentas().getText()));
            this.presupuesto.setDescuentoVenta(new BigDecimal(getTxtDescuentoVentas().getText()));
            
    }
    
    public void setearValoresPresupuesto(BigDecimal descuentoCompra, BigDecimal descuentoVenta, BigDecimal totalCompra, BigDecimal totalVenta)
    {
        this.presupuesto.setDescuentoCompra(descuentoCompra);
        //this.presupuesto.setDescuentoVenta(descuentoVenta);
        this.presupuesto.setTotalCompra(totalCompra);
        //this.presupuesto.setTotalVenta(totalVenta);
    }
    
    /*public void ordenarDetallesEnFuncionDeCliente()
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
    }*/
    
    /*public void ordenarDetallesEnFuncionDeOrdenCompra()
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
    }*/
    
    public void mostrarDatosTabla()
    {
        String[] tituloList={"obj","Código","Producto","Val Compra","Desc Compra","Val Venta","Desc Venta","Cantidad"};
        Class[] claseList=new Class[]{PresupuestoDetalle.class,String.class,String.class,String.class,String.class,String.class,String.class,String.class,String.class};
        int c=0;
        Vector<Object> fila;
        DefaultTableModel modeloTablaDetallesPresupuesto =UtilidadesTablas.crearModeloTabla(tituloList,claseList);
        
        DefaultTableModel modeloTablaDetallesProductos = UtilidadesTablas.crearModeloTabla(tituloList,claseList);
        
        
        //for(Map.Entry<Integer,List<PresupuestoDetalle>> datoMap : mapOrden.entrySet())
        //{
            //boolean b = true; 
            //List<PresupuestoDetalle> detallesPorProveedor = datoMap.getValue();
            //String titulo = "Orden " + detallesPorProveedor.get(0).getNumeroOrdenCompra() + " :";
            for (PresupuestoDetalle detalle : presupuesto.getPresupuestoDetalles()) 
            {
                //if(b){
                    //fila=new Vector<>();
                    //fila.add(null);fila.add(titulo+"");fila.add("");fila.add("");fila.add("");fila.add("");fila.add("");fila.add("");fila.add("");
                    //b = false;
                    //modeloTablaDetallesPresupuesto.addRow(fila);
                //}
                    fila = new Vector<>();
                    fila.add(detalle);
                    //fila.add("");
                    //fila.add(detalle.getPersona().getIdentificacion()+"");
                    fila.add(detalle.getProducto().getCodigoPersonalizado()+"");
                    fila.add(detalle.getProducto().getNombre()+"");
                    fila.add(""+detalle.getPrecioCompra().multiply(detalle.getCantidad()));
                    //fila.add(detalle.getPrecioCompra().subtract(detalle.getDescuentoCompra())+"");
                    fila.add(""+detalle.getDescuentoCompra());
                    fila.add(detalle.getPrecioVenta().multiply(detalle.getCantidad()));
                    //fila.add(detalle.getPrecioVenta().subtract(detalle.getDescuentoVenta())+"");
                    fila.add(""+detalle.getDescuentoVenta());
                    fila.add(detalle.getCantidad().toString());
                    
                    if(detalle.getProducto().getTipoProductoEnum().equals(TipoProductoEnum.PRODUCTO))
                    {
                        modeloTablaDetallesProductos.addRow(fila);
                    }
                    else if(detalle.getProducto().getTipoProductoEnum().equals(TipoProductoEnum.SERVICIO))
                    {
                        modeloTablaDetallesPresupuesto.addRow(fila);
                    }
            }
        //}
        
        
        getTableDetallesPresupuesto().setModel(modeloTablaDetallesProductos);
        getTableDetallesServicio().setModel(modeloTablaDetallesPresupuesto);
        
        Integer[] tamanioTabla=new Integer[]{0,30,100};
        UtilidadesTablas.cambiarTamanioColumnas(getTableDetallesPresupuesto(),tamanioTabla);
        UtilidadesTablas.cambiarTamanioColumnas(getTableDetallesServicio(),tamanioTabla);
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
                //opcionMenuCambiarOrdenCompra();
            }
        });
        
        ordenCompra.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //opcionMenuGenerarOrdenCompra();
            }
        });
        
        PopupMenuTabla menuTabla = new PopupMenuTabla(getTableDetallesPresupuesto(), jMenuItems);
        UtilidadesTablas.ocultarColumna(getTableDetallesPresupuesto(), 0);
        //UtilidadesTablas.cambiarColorFila(getTableDetallesPresupuesto());
        
        UtilidadesTablas.ocultarColumna(getTableDetallesServicio(), 0);
        //UtilidadesTablas.cambiarColorFila(getTableDetallesServicio());
        
        //UtilidadesTablas.ocultarColumna(getTableDetallesPresupuesto(), 1);
        //UtilidadesTablas.ocultarColumna(getTableDetallesServicio(), 1);
        
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
    public InterfaceModelFind obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * Opciones de PopupMenu
     */
    /*public void opcionMenuCambiarOrdenCompra()
    {
        boolean respuesta =  DialogoCodefac.dialogoPregunta("Advertencia", "Desea colocar el detalle en una nueva orden de compra?", DialogoCodefac.MENSAJE_ADVERTENCIA);

        int n = -1;
        if(respuesta){

            int numeroOrden = obtenerNumeroOrdenPresupuesto() + 1;
            int fila = getTableDetallesPresupuesto().getSelectedRow();
            PresupuestoDetalle presupuestoDetalle = (PresupuestoDetalle) getTableDetallesPresupuesto().getValueAt(fila, 0);
            if(presupuestoDetalle != null)
            {
                if(obtenerNumeroOrdenCompraPorProveedor(presupuestoDetalle)>0){

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
            
            //Orden los detalles del map en funcion del numero de la orden de compra
            ordenarDetallesEnFuncionDeOrdenCompra();
            mostrarDatosTabla();
        }
        
    }*/
   
    /*public void opcionMenuGenerarOrdenCompra()
    {
        for (Map.Entry<Integer, List<PresupuestoDetalle>> entry : this.mapOrden.entrySet()) {
            Integer key = entry.getKey();
            List<PresupuestoDetalle> value = entry.getValue();
            
        }
       
    }*/
    
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
                List<ProductoProveedor> resultados = serviceProductoProveedor.buscarProductoProveedorActivo(producto,persona);
                if (resultados != null && resultados.size() > 0) {
                    productoProveedor = resultados.get(0); //Si existe el proveedor solo seteo la variale
                    //getTxtPrecioCompra().setText(productoProveedor.getCosto() + "");
                    //EnumSiNo enumSiNo = EnumSiNo.getEnumByLetra(productoProveedor.getConIva());
                    //getCmbCobraIva().setSelectedItem(enumSiNo);
                } else {//Cuando no existe crea un nuevo producto proveedor
                    productoProveedor = new ProductoProveedor(); //Si no existe el item lo creo para posteriormente cuando grabe persistir con la base de datos
                    productoProveedor.setDescripcion("");
                    productoProveedor.setEstado("a");
                    productoProveedor.setProducto(this.producto);
                    productoProveedor.setProveedor(this.persona);
                    productoProveedor.setCosto(this.producto.getValorUnitario());
                    //getTxtPrecioCompra().setText("0.00"); //Seteo con el valor de 0 porque no existe el costo grabado
                    //getCmbCobraIva().setSelectedItem(EnumSiNo.SI); //Seteo por defecto el valor de SI cuando no existe en la base de datos
                }
                
                //Setear en la pantalla el precio de producto proveedor
                if(getChkInventarioProveedor().isSelected())
                {
                    BigDecimal precio=productoProveedor.getProducto().obtenerPrecioPresupuesto(session.getEmpresa());
                    getTxtPrecioCompra().setText(precio+"");                    
                    //getTxtPrecioCompra().setText(productoProveedor.getProducto().getValorUnitario()+"");                    
                }
                else
                {
                    getTxtPrecioCompra().setText(productoProveedor.getCosto()+"");
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
        
        //int fila = getTableDetallesPresupuesto().getSelectedRow();
        //if (fila >= 0) {
        //if (detalleSeleccionado!=null) {
            try {
                //PresupuestoDetalle presupuestoDetalle = (PresupuestoDetalle) getTableDetallesPresupuesto().getValueAt(fila, 0);
                if (detalleSeleccionado != null) {
                    agregarDetallesPresupuesto(detalleSeleccionado, false);
                    limpiarDetalles();
                    mostrarDatosTabla();
                    calcularTotales(true);
                    getBtnAgregarDetalle().setEnabled(true);
                    detalleSeleccionado=null;
                } else {
                    limpiarDetalles();
                    getBtnAgregarDetalle().setEnabled(true);
                }
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        //}
                
    }
    
    private void eliminarDetallePresupuesto()
    {
        try{
            PresupuestoDetalle presupuestoDetalle = null;
            int fila = getTableDetallesPresupuesto().getSelectedRow();
            
            if(fila>=0)
            {
                presupuestoDetalle = (PresupuestoDetalle) getTableDetallesPresupuesto().getValueAt(fila,0);
                
            }
            else
            {
                fila = getTableDetallesServicio().getSelectedRow();
                if(fila>=0)
                {
                    presupuestoDetalle=(PresupuestoDetalle) getTableDetallesServicio().getValueAt(fila,0);
                }
            }
            
            
            if(presupuestoDetalle != null){
                
                    System.out.println("Eliminando detalle con producto: "+presupuestoDetalle.getProducto().getNombre());
                    presupuesto.getPresupuestoDetalles().remove(presupuestoDetalle);
                    getBtnAgregarDetalle().setEnabled(true);
                    limpiarDetalles();
                    //ordenarDetallesEnFuncionDeOrdenCompra();
                    mostrarDatosTabla();
                    calcularTotales(true);
            }
            else{
                getBtnAgregarDetalle().setEnabled(true);
                limpiarDetalles();
            }
        }catch(Exception e)
        {
            e.printStackTrace();
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
            //InputStream path = RecursoCodefac.JASPER_COMPRA.getResourceInputStream("orden_compra.jrxml");
            URL path = RecursoCodefac.JASPER_COMPRA.getResourceURL("orden_compra.jrxml");
            //ReporteCodefac.generarReporteInternalFramePlantilla(path, parametros, dataReportes, panelPadre, "Orden de Compra");
            UtilidadesComprobantes.generarReporteJasper(path, parametros, dataReportes, PATH_REPORTE_TMP);
        }
    }
    
    private void enviarCorreo(Empleado empleado, String mensaje)
    {
        CorreoCodefac correoCodefac = new CorreoCodefac();
        
        HashMap<String, String> mapArchivos = new HashMap<String, String>();
                mapArchivos.put("Comunicado.pdf", PATH_REPORTE_TMP);
                
                List<String> destinatarios = new ArrayList<String>();
                destinatarios.add(empleado.getCorreoElectronico());

        try {
            correoCodefac.enviarCorreo(session.getEmpresa(),mensaje,"Orden Compra",destinatarios,mapArchivos);
        } catch (CorreoCodefac.ExcepcionCorreoCodefac ex) {
            Logger.getLogger(PresupuestoModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void enviarComunicados()
    {
       
        try{
            Empleado empleadoTemp = this.presupuesto.getOrdenTrabajoDetalle().getEmpleado();
            
            if(!this.ordenesCompra.isEmpty()){
                
                MonitorComprobanteData monitorData = getMonitorComprobanteData();
                MonitorComprobanteModel.getInstance().mostrar();
                
                int contador = 0;
                for (OrdenCompra ordenCompra : this.ordenesCompra) {
                    contador++;
                    /**
                     * Generar Reporte
                     */
                    generarReportePdf(ordenCompra);
                    /**
                     * Enviar al correo
                     */
                    String mensaje = construirMensaje(empleadoTemp, ordenCompra.getProveedor());
                    enviarCorreo(empleadoTemp, mensaje);
                    
                    double relacion = (double) contador / (double) this.ordenesCompra.size();
                    int porcentaje = (int) (relacion * 100);
                    monitorData.getBarraProgreso().setValue(porcentaje);
                    
                }
           }
           else{
               DialogoCodefac.mensaje("Advertencia", "Empleado sin asignar ", SOMEBITS);
           }     
           
            
           
       }catch(Exception e)
       {
           e.printStackTrace();
       }
               
    }
    
    public String construirMensaje(Empleado empleado, Persona persona)
    {
        String mensaje = "Estimad@ [nombre_empleado] realize la orden de compra del Cliente [nombre_cliente]";
        
        mensaje = mensaje.replace(ETIQUETA_NOMBRE_EMPLEADO, empleado.getNombresCompletos());
        mensaje = mensaje.replace(ETIQUETA_NOMBRE_CLIENTE, persona.getNombresCompletos());

        return mensaje;
    }
    
    /**
     * Obtiene una version del monitor del comprobante para mostrar el avance de
     * las notificaciones enviadas
     *
     * @return
     */
    private MonitorComprobanteData getMonitorComprobanteData() {
        //Obtener una instancia del monitor para mostrar el avance de los datos
        MonitorComprobanteData monitorData = MonitorComprobanteModel.getInstance().agregarComprobante();

        monitorData.getLblPreimpreso().setText("enviado notificaciones");
        monitorData.getBtnAbrir().setEnabled(false);
        monitorData.getBtnReporte().setEnabled(false);
        monitorData.getBtnCerrar().setEnabled(false);
        monitorData.getBarraProgreso().setString("enviando notificaciones");
        monitorData.getBarraProgreso().setStringPainted(true);

        return monitorData;

    }
    
    public void modificarEstadoOrdenTrabajoPorDetalle() throws ServicioCodefacException
    {
        try {
            PresupuestoServiceIf servicioPresupuesto = ServiceFactory.getFactory().getPresupuestoServiceIf();
            OrdenTrabajoServiceIf servicioOrden = ServiceFactory.getFactory().getOrdenTrabajoServiceIf();
            /**
            * Obtengo las Detalles de las ordenes de trabajo ligadas a los presupuestos   
            */
            List<OrdenTrabajoDetalle> ordenesTrabajoDetalles = servicioPresupuesto.listarOrdenesTrabajo(this.presupuesto.getOrdenTrabajoDetalle().getOrdenTrabajo());
            OrdenTrabajo ordenTrabajo = this.presupuesto.getOrdenTrabajoDetalle().getOrdenTrabajo();
            if(ordenesTrabajoDetalles.size() == this.presupuesto.getOrdenTrabajoDetalle().getOrdenTrabajo().getDetalles().size())
            {
                /**
                * Modificar el campo ya que se esta ligando un detalle de la Orden de Trabajo a un Presupuesto
                */
                ordenTrabajo.setEstado(OrdenTrabajo.EstadoEnum.FINALIZADO.getEstado());
            }else{
                ordenTrabajo.setEstado(OrdenTrabajo.EstadoEnum.LIGADO.getEstado());
            }
            servicioOrden.editar(ordenTrabajo);
            
            
        } catch (RemoteException ex) {
            Logger.getLogger(PresupuestoModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run() 
    {
        enviarComunicados();
    }
    
    public void empezarEnviarComunicados()
    {
        Presupuesto.EstadoEnum estadoEnum = (Presupuesto.EstadoEnum) getCmbEstadoPresupuesto().getSelectedItem();
        if(estadoEnum.equals(Presupuesto.EstadoEnum.TERMINADO))
        {
            hiloNotificaciones = new Thread(instanceThis);
            hiloNotificaciones.start();
            DialogoCodefac.mensaje("Correcto", "Las notificaciones se estan enviado , puede revisar en el monitor", DialogoCodefac.MENSAJE_CORRECTO);
        }
    }
    
}
