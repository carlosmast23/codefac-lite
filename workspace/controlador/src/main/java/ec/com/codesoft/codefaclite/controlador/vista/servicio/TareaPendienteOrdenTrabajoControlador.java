/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vista.servicio;

import ec.com.codesoft.codefaclite.controlador.vista.factura.*;
import ec.com.codesoft.codefaclite.controlador.aplicacion.ControladorCodefacInterface;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.EmpleadoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.RutaBusquedaDialogo;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.controlador.utilidades.ComprobanteElectronicoComponente;
import ec.com.codesoft.codefaclite.controlador.vista.transporte.GuiaRemisionLoteControlador;
import ec.com.codesoft.codefaclite.controlador.vistas.core.components.TableBindingImp;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;import java.util.Map;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.interfaces.VistaCodefacIf;
import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteElectronicoService;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.servidorinterfaz.callback.ClienteInterfaceComprobanteLote;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.ComprobanteDataFactura;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.ComprobanteDataInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Departamento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empleado;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PresupuestoDetalleActividad;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Ruta;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.DestinatarioGuiaRemision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.GuiaRemision;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefacInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.parameros.CarteraParametro;
import ec.com.codesoft.codefaclite.servidorinterfaz.parameros.FacturaParametro;
import ec.com.codesoft.codefaclite.servidorinterfaz.respuesta.FacturaLoteRespuesta;
import ec.com.codesoft.codefaclite.utilidades.list.UtilidadesLista;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class TareaPendienteOrdenTrabajoControlador extends ModelControladorAbstract<TareaPendienteOrdenTrabajoControlador.CommonIf,TareaPendienteOrdenTrabajoControlador.SwingIf,TareaPendienteOrdenTrabajoControlador.WebIf> implements VistaCodefacIf 
{
    //private List<OrdenTrabajoDetalleDataTable> detallesOTList;
    private List<PresupuestoDetalleActividad> actividadList;
    private PresupuestoDetalleActividad ordenDetalleSeleccionada;
    private List<PresupuestoDetalleActividad> actividadListSeleccionada;
    //private List<OrdenTrabajoDetalleDataTable> detallesOTSeleccionadaList;
    
    private TableBindingImp tableBindingControlador;
    
    
    public TareaPendienteOrdenTrabajoControlador(MensajeVistaInterface mensajeVista, SessionCodefacInterface session, CommonIf interfaz, TipoVista tipoVista) {
        super(mensajeVista, session, interfaz, tipoVista);                
    }

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, RemoteException 
    {   
        try {
            ServiceFactory.getFactory().getPresupuestoServiceIf().actualizarActividadesPresupuestos(actividadListSeleccionada);
            //ServiceFactory.getFactory().getOrdenTrabajoServiceIf().terminarDetallesOrdenesTrabajo(session.getUsuario().getEmpleado(),getDetallesModificar());
            mostrarMensaje(MensajeCodefacSistema.AccionesFormulario.PROCESO_CORRECTO);
            limpiar();
        } catch (ServicioCodefacException ex) {
            mostrarMensaje(new CodefacMsj(ex.getMessage(), CodefacMsj.TipoMensajeEnum.ERROR));
            Logger.getLogger(TareaPendienteOrdenTrabajoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    /*private List<PresupuestoDetalleActividad> getActividades()
    {
        List<PresupuestoDetalleActividad> resultadoList=new ArrayList<PresupuestoDetalleActividad>();
        for (PresupuestoDetalleActividad actividad : actividadList) {
            resultadoList.add(actividad);
        }
        return resultadoList;
    }*/
    
    private List<Factura> obtenerNotasVentaProcesadas(FacturaLoteRespuesta respuesta)
    {
        List<Factura> notaVentaList=new ArrayList<Factura>();
        for (Factura factura : respuesta.procesadosList) {
            if(factura.getCodigoDocumentoEnum().equals(DocumentoEnum.NOTA_VENTA_INTERNA))
            {
                notaVentaList.add(factura);
            }
        }
        return notaVentaList;
    }
    
    private List<ComprobanteDataInterface> obtenerComprobantesParaProcesarElectronicamente(FacturaLoteRespuesta respuesta)
    {
        List<ComprobanteDataInterface> comprobantesProcesarList=new ArrayList<ComprobanteDataInterface>();
        
        for (Factura factura : respuesta.procesadosList) {
            //Solo procesar los documentos que son de tipo facturas
            if(factura.getCodigoDocumentoEnum().equals(DocumentoEnum.FACTURA))
            {
                ComprobanteDataFactura comprobanteData = new ComprobanteDataFactura(factura);
                comprobanteData.setMapInfoAdicional(factura.getMapAdicional());
                comprobantesProcesarList.add(comprobanteData);
            }
        }
                
        return comprobantesProcesarList;
    }
    

    @Override
    public void editar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void imprimir() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void limpiar() {
        this.actividadList= filtrarActividades();
        //this.detallesOTSeleccionadaList=new ArrayList<OrdenTrabajoDetalleDataTable>();
    }
    
    public List<PresupuestoDetalleActividad> filtrarActividades()
    {
        List<PresupuestoDetalleActividad> resultadoList=new ArrayList<PresupuestoDetalleActividad>();
        try {
            Empleado empleado= session.getUsuario().getEmpleado();
            
            resultadoList= ServiceFactory.getFactory().getPresupuestoServiceIf().consultarActividadesPendientesPresupuesto(empleado);
            return resultadoList;
            /*List<OrdenTrabajoDetalle>detallesOTList=ServiceFactory.getFactory().getOrdenTrabajoServiceIf().filtrarDetallesPorEstadoYEmpleado(OrdenTrabajoDetalle.EstadoEnum.RECIBIDO, empleado);
            for (OrdenTrabajoDetalle ordenTrabajoDetalle : detallesOTList) 
            {
                resultadoList.add(new OrdenTrabajoDetalleDataTable(ordenTrabajoDetalle, Boolean.TRUE,""));
            }*/
            
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(TareaPendienteOrdenTrabajoControlador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(TareaPendienteOrdenTrabajoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultadoList;
    }

    @Override
    public String getURLAyuda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public InterfaceModelFind obtenerDialogoBusqueda() {
        return null;
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
        
    ///////////////////////////////////////////////////////////////////////////
    //                      METODOS GET AND SET
    ///////////////////////////////////////////////////////////////////////////

    public List<PresupuestoDetalleActividad> getActividadList() {
        return actividadList;
    }

    public void setActividadList(List<PresupuestoDetalleActividad> actividadList) {
        this.actividadList = actividadList;
    }

    public PresupuestoDetalleActividad getOrdenDetalleSeleccionada() {
        return ordenDetalleSeleccionada;
    }

    public void setOrdenDetalleSeleccionada(PresupuestoDetalleActividad ordenDetalleSeleccionada) {
        this.ordenDetalleSeleccionada = ordenDetalleSeleccionada;
    }

    public List<PresupuestoDetalleActividad> getActividadListSeleccionada() {
        return actividadListSeleccionada;
    }

    public void setActividadListSeleccionada(List<PresupuestoDetalleActividad> actividadListSeleccionada) {
        this.actividadListSeleccionada = actividadListSeleccionada;
    }

    

    

    /*public List<OrdenTrabajoDetalleDataTable> getDetallesOTSeleccionadaList() {
        return detallesOTSeleccionadaList;
    }

    public void setDetallesOTSeleccionadaList(List<OrdenTrabajoDetalleDataTable> detallesOTSeleccionadaList) {
        this.detallesOTSeleccionadaList = detallesOTSeleccionadaList;
    }*/

    public TableBindingImp getTableBindingControlador() {
        return tableBindingControlador;
    }

    public void setTableBindingControlador(TableBindingImp tableBindingControlador) {
        this.tableBindingControlador = tableBindingControlador;
    }
    
    

    public void listenerFiltrar()
    {
        System.out.println("filtrando datos");
        actividadList=filtrarActividades();
    }
        
    ///////////////////////////////////////////////////////////////////////////
    //             METODOS QUE CONTIENEN INTERFACES PARA LA IMPLEMTACION
    ///////////////////////////////////////////////////////////////////////////
    public interface CommonIf {
        //TODO: Implementacion de todas las interfaces comunes
        public void cerrarPantalla();
    }

    public interface SwingIf extends CommonIf {
        //TODO: Implementacion de las interfaces solo necesarias para Swing
        public void abrirGuiaRemision(GuiaRemision guiaRemision);
        public ClienteInterfaceComprobanteLote getInterfaceCallBack();
        public void generarNotasVentaInterna(List<Factura> facturas);
    }

    public interface WebIf extends CommonIf {
        //TODO: Implementacion de las interafaces solo para la web
    }
    
    ///////////////////////////////////////////////////////////////////////////
    ///                 CLASES ADICIONALES
    ///////////////////////////////////////////////////////////////////////////
    /*public class ActividadesDataTable
    {

        public OrdenTrabajoDetalleDataTable(OrdenTrabajoDetalle detalleOrdenTrabajo, Boolean aprobar, String novedades) {
            this.detalleOrdenTrabajo = detalleOrdenTrabajo;
            this.aprobar = aprobar;
            this.novedades=novedades;
        }
        
        public OrdenTrabajoDetalle detalleOrdenTrabajo;
        public Boolean aprobar;
        public String novedades; 
    }*/
    
    
}
