/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vista.factura;

import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.EmpleadoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.RutaBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.controlador.utilidades.ComprobanteElectronicoComponente;
import ec.com.codesoft.codefaclite.controlador.vista.transporte.GuiaRemisionLoteControlador;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.interfaces.VistaCodefacIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Departamento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empleado;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Ruta;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.DestinatarioGuiaRemision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.GuiaRemision;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefacInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.respuesta.FacturaLoteRespuesta;
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
public class FacturaPedidoLoteModelControlador extends ModelControladorAbstract<FacturaPedidoLoteModelControlador.CommonIf,FacturaPedidoLoteModelControlador.SwingIf,FacturaPedidoLoteModelControlador.WebIf> implements VistaCodefacIf {

    private java.util.Date fechaInicial;
    private java.util.Date fechaFin;
    
    
    private List<Factura> ventasList;
    private List<Factura> ventasSeleccionadasList;
    private List<PuntoEmision> puntoEmisionList;
    
    private Factura facturaSeleccionada;
    
    private Empleado vendedorSeleccionado;
    private Ruta rutaSeleccionada;
    private PuntoEmision puntoEmisionSeleccionado;
    
    private Boolean seleccionTodosVendedor;
    private Boolean seleccionTodosRuta;
    
    public FacturaPedidoLoteModelControlador(MensajeVistaInterface mensajeVista, SessionCodefacInterface session, CommonIf interfaz, TipoVista tipoVista) {
        super(mensajeVista, session, interfaz, tipoVista);
    }

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        try {
            puntoEmisionList= ServiceFactory.getFactory().getPuntoEmisionUsuarioServiceIf().obtenerActivosPorSucursalCastPuntoEmision(session.getUsuario(),session.getSucursal());
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(FacturaPedidoLoteModelControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, RemoteException {
        
        try 
        {
            List<Factura> facturasProcesar=construirFacturas();
            if(facturasProcesar.size()==0)
            {
                mostrarMensaje(new CodefacMsj("Seleccione alguna factura para procesar ",CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
                throw new ExcepcionCodefacLite("Seleccione alguna factura para procesar ");
            }
            
            FacturaLoteRespuesta respuesta=ServiceFactory.getFactory().getFacturacionServiceIf().grabarLote(construirFacturas());
            
            mostrarMensaje(new CodefacMsj("El proceso finalizo", CodefacMsj.TipoMensajeEnum.CORRECTO));
            
            if(respuesta.noProcesadosList.size()>0)
            {
                mostrarMensaje(new CodefacMsj(respuesta.costruirMensajeError(), CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
            }
            
            ///Procesar las facturas de forma electronica
            
            
            
        } catch (ServicioCodefacException ex) {            
            mostrarMensaje(new CodefacMsj(ex.getMessage(), CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
            Logger.getLogger(GuiaRemisionLoteControlador.class.getName()).log(Level.SEVERE, null, ex);
            throw new ExcepcionCodefacLite("Cancelado grabar");
        }
        
        
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
        fechaInicial=new Date();
        fechaFin=new Date();
        ventasList=new ArrayList<Factura>();
        ventasSeleccionadasList=new ArrayList<Factura>();
        seleccionTodosRuta=true;
        seleccionTodosVendedor=true;
                
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void listenerConsultarGuiasRemision()
    {        
        try {
            ventasList=ServiceFactory.getFactory().getFacturacionServiceIf().obtenerFacturasReporte(
                    null,
                    new java.sql.Date(fechaInicial.getTime()),
                    new java.sql.Date(fechaFin.getTime()),
                    ComprobanteEntity.ComprobanteEnumEstado.AUTORIZADO,
                    Boolean.FALSE,
                    null,
                    Boolean.FALSE,
                    null,
                    session.getEmpresa(),
                    DocumentoEnum.PROFORMA,
                    null,
                    null,
                    vendedorSeleccionado,
                    null);
            
        } catch (RemoteException ex) {
            Logger.getLogger(GuiaRemisionLoteControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private boolean validarDatosIngresados()
    {        
        
        if(ventasSeleccionadasList==null || ventasSeleccionadasList.size()==0)
        {
            mostrarMensaje(new CodefacMsj("Seleccione al menos una venta para continuar",CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
            return false;
        }
        //Verificar si todos las ventas seleccionadas tiene una direccion importante para procesar la guia de remision
        
        for (Factura venta : ventasSeleccionadasList) {
            String direccion=venta.getSucursal().getDireccion();
            if(direccion==null || direccion.trim().isEmpty())
            {
                mostrarMensaje(new CodefacMsj("El cliente "+venta.getSucursal().getPersona().getNombresCompletos()+" , con Identificación "+venta.getSucursal().getPersona().getIdentificacion()+" es necesario definir una dirección ",CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
                return false;
            }
        }
        
        return true;
    }
    
        
    public void listenerBuscarVendedor()
    {
        EmpleadoBusquedaDialogo busqueda=new EmpleadoBusquedaDialogo(Departamento.TipoEnum.Ventas);
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(busqueda);
        buscarDialogoModel.setVisible(true);
        if (buscarDialogoModel.getResultado() != null) {
            vendedorSeleccionado=((Empleado) buscarDialogoModel.getResultado());
            seleccionTodosVendedor=false;
        }
    }
    
    public void listenerBuscarRuta()
    {
        RutaBusquedaDialogo busqueda=new RutaBusquedaDialogo(session.getEmpresa());
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(busqueda);
        buscarDialogoModel.setVisible(true);
        if (buscarDialogoModel.getResultado() != null) {
            rutaSeleccionada=(Ruta) buscarDialogoModel.getResultado();
            vendedorSeleccionado=rutaSeleccionada.getVendedor();
            seleccionTodosRuta=false;
            seleccionTodosVendedor=false;
        }
    }
    
    public void listenerCheckVendedores()
    {
        if(seleccionTodosVendedor)
        {
            vendedorSeleccionado=null;
        }
    }
    
    public void listenerCheckRutas()
    {
        if(seleccionTodosRuta)
        {
            rutaSeleccionada=null;
        }
    }
    
    ///////////////////////////////////////////////////////////////////////////
    //                      METODOS GET AND SET
    ///////////////////////////////////////////////////////////////////////////

    public Date getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public List<Factura> getVentasList() {
        return ventasList;
    }

    public void setVentasList(List<Factura> ventasList) {
        this.ventasList = ventasList;
    }

    public List<Factura> getVentasSeleccionadasList() {
        return ventasSeleccionadasList;
    }

    public void setVentasSeleccionadasList(List<Factura> ventasSeleccionadasList) {
        this.ventasSeleccionadasList = ventasSeleccionadasList;
    }

    public Factura getFacturaSeleccionada() {
        return facturaSeleccionada;
    }

    public void setFacturaSeleccionada(Factura facturaSeleccionada) {
        this.facturaSeleccionada = facturaSeleccionada;
    }


    public Empleado getVendedorSeleccionado() {
        return vendedorSeleccionado;
    }

    public void setVendedorSeleccionado(Empleado vendedorSeleccionado) {
        this.vendedorSeleccionado = vendedorSeleccionado;
    }

    public Ruta getRutaSeleccionada() {
        return rutaSeleccionada;
    }

    public void setRutaSeleccionada(Ruta rutaSeleccionada) {
        this.rutaSeleccionada = rutaSeleccionada;
    }

    public Boolean getSeleccionTodosVendedor() {
        return seleccionTodosVendedor;
    }

    public void setSeleccionTodosVendedor(Boolean seleccionTodosVendedor) {
        this.seleccionTodosVendedor = seleccionTodosVendedor;
    }

    public Boolean getSeleccionTodosRuta() {
        return seleccionTodosRuta;
    }

    public void setSeleccionTodosRuta(Boolean seleccionTodosRuta) {
        this.seleccionTodosRuta = seleccionTodosRuta;
    }

    public List<PuntoEmision> getPuntoEmisionList() {
        return puntoEmisionList;
    }

    public void setPuntoEmisionList(List<PuntoEmision> puntoEmisionList) {
        this.puntoEmisionList = puntoEmisionList;
    }

    public PuntoEmision getPuntoEmisionSeleccionado() {
        return puntoEmisionSeleccionado;
    }

    public void setPuntoEmisionSeleccionado(PuntoEmision puntoEmisionSeleccionado) {
        this.puntoEmisionSeleccionado = puntoEmisionSeleccionado;
    }
    
    

    private List<Factura> construirFacturas() {
        List<Factura> facturasProcesar=new ArrayList<Factura>();
        for (Factura proforma : ventasSeleccionadasList) {
            try {
                Factura facturaNueva = (Factura) proforma.clone();
                facturaNueva.setId(null);
                facturaNueva.setCodigoDocumentoEnum(DocumentoEnum.FACTURA);
                facturaNueva.setProforma(proforma);
                facturaNueva.setPuntoEmision(puntoEmisionSeleccionado.getPuntoEmision());
                facturaNueva.setPuntoEstablecimiento(new BigDecimal(session.getSucursal().getCodigoSucursal().toString()));
                facturasProcesar.add(facturaNueva);
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(FacturaPedidoLoteModelControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return facturasProcesar;
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
    }

    public interface WebIf extends CommonIf {
        //TODO: Implementacion de las interafaces solo para la web
    }
    
    
}
