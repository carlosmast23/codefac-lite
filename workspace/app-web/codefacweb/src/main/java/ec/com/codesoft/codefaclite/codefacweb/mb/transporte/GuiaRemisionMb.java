/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.mb.transporte;

import ec.com.codesoft.codefaclite.codefacweb.core.GeneralAbstractMb;
import ec.com.codesoft.codefaclite.codefacweb.core.SessionMb;
import ec.com.codesoft.codefaclite.codefacweb.mb.facturacion.BarraProgreso;
import ec.com.codesoft.codefaclite.codefacweb.mb.facturacion.ProformaMb;
import ec.com.codesoft.codefaclite.codefacweb.mb.sistema.UtilidadesWeb;
import ec.com.codesoft.codefaclite.codefacweb.mb.utilidades.MensajeMb;
import ec.com.codesoft.codefaclite.codefacweb.mb.utilidades.UtilidadesDialogo;
import ec.com.codesoft.codefaclite.codefacweb.mb.utilidades.UtilidadesReporteWeb;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ClienteEstablecimientoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ProductoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.TransportistaBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.vista.transporte.GuiaRemisionModelControlador;
import ec.com.codesoft.codefaclite.controlador.vista.transporte.GuiaRemisionModelControlador.GuiaRemisionModelInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.servidorinterfaz.callback.ClienteInterfaceComprobante;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PersonaEstablecimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Transportista;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.DetalleProductoGuiaRemision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.GuiaRemision;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.rmi.UtilidadesRmi;
import java.io.Serializable;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import net.sf.jasperreports.engine.JasperPrint;
import org.primefaces.event.SelectEvent;

/**
 * Piloto web de Guía de Remisión: un solo destinatario por guía, con generación y envío del
 * comprobante electrónico al SRI. Sigue el mismo esqueleto que {@code NotaCreditoMb}, consumiendo
 * el controlador compartido {@link GuiaRemisionModelControlador}.
 *
 * @author Carlos
 */
@ManagedBean
@ViewScoped
public class GuiaRemisionMb extends GeneralAbstractMb implements Serializable, GuiaRemisionModelInterface {

    private GuiaRemision guiaRemision;

    private List<DocumentoEnum> documentos;
    private DocumentoEnum documentoSeleccionado;

    private List<PuntoEmision> puntosEmision;
    private PuntoEmision puntoEmisionSeleccionado;

    private Date fechaInicioTransporte;
    private Date fechaFinTransporte;
    private String direccionPartida;

    private Transportista transportistaSeleccionado;

    //Datos del único destinatario del piloto
    private Persona destinatarioSeleccionado;
    private String direccionDestino;
    private String motivoTraslado;
    private String ruta;
    private String autorizacionNumero;
    private String preimpresoDestinatario;
    private Date fechaFacturaDestinatario;
    private boolean enviarCorreoDestinatario;
    private boolean enviarCorreoTransportista;

    private List<DetalleProductoGuiaRemision> detalleProductos;

    //Panel de "agregar detalle"
    private Producto productoSeleccionado;
    private String codigoDetalle;
    private String cantidadDetalle;
    private String descripcionDetalle;

    @ManagedProperty(value = "#{sessionMb}")
    private SessionMb sessionMb;

    private GuiaRemisionModelControlador controlador;

    private BarraProgreso<GuiaRemision> barraProgreso;

    @PostConstruct
    public void init() {
        controlador = new GuiaRemisionModelControlador(this, sessionMb.getSession(), MensajeMb.intefaceMensaje);
        cargarDatosLista();
        try {
            nuevo();
        } catch (ExcepcionCodefacLite ex) {
            Logger.getLogger(GuiaRemisionMb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedOperationException ex) {
            Logger.getLogger(GuiaRemisionMb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void cargarDatosLista() {
        documentos = new ArrayList<DocumentoEnum>();
        documentos.add(DocumentoEnum.GUIA_REMISION);
        documentos.add(DocumentoEnum.GUIA_REMISION_INTERNA);

        try {
            List<PuntoEmision> puntosEmisionTmp = ServiceFactory.getFactory().getCajaPermisoServiceIf().buscarPuntosEmisionPorCajas(sessionMb.getSession().getUsuario());
            if (puntosEmisionTmp == null || puntosEmisionTmp.isEmpty()) {
                puntosEmisionTmp = ServiceFactory.getFactory().getPuntoEmisionUsuarioServiceIf().obtenerPuntosEmisionPorUsuario(sessionMb.getSession().getUsuario(), sessionMb.getSession().getSucursal());
            }
            this.puntosEmision = puntosEmisionTmp;
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(GuiaRemisionMb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(GuiaRemisionMb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite, UnsupportedOperationException {
        guiaRemision = new GuiaRemision();
        documentoSeleccionado = DocumentoEnum.GUIA_REMISION;
        direccionPartida = sessionMb.getSession().getSucursal().getDirecccion();
        fechaInicioTransporte = UtilidadesFecha.getFechaHoy();
        fechaFinTransporte = UtilidadesFecha.getFechaHoy();
        transportistaSeleccionado = null;

        limpiarDatosDestinatario();
        detalleProductos = new ArrayList<DetalleProductoGuiaRemision>();

        if (puntosEmision != null && !puntosEmision.isEmpty()) {
            puntoEmisionSeleccionado = puntosEmision.get(0);
        }
    }

    private void limpiarDatosDestinatario() {
        destinatarioSeleccionado = null;
        direccionDestino = "";
        motivoTraslado = "";
        ruta = "";
        autorizacionNumero = "";
        preimpresoDestinatario = "";
        fechaFacturaDestinatario = null;
        enviarCorreoDestinatario = false;
        enviarCorreoTransportista = false;
        limpiarDetalleProducto();
    }

    private void limpiarDetalleProducto() {
        productoSeleccionado = null;
        codigoDetalle = "";
        cantidadDetalle = "";
        descripcionDetalle = "";
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, UnsupportedOperationException {
        controlador.grabar();
        UtilidadesWeb.ejecutarJavascript("mostrarComprobantesRC();");
    }

    @Override
    public void editar() throws ExcepcionCodefacLite, UnsupportedOperationException {
        throw new UnsupportedOperationException("Editar guía de remisión no está disponible todavía en el web");
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite, UnsupportedOperationException {
        throw new UnsupportedOperationException("Eliminar guía de remisión no está disponible todavía en el web");
    }

    @Override
    public void imprimir() throws ExcepcionCodefacLite, UnsupportedOperationException {
        if (barraProgreso != null) {
            barraProgreso.imprimir();
        }
    }

    @Override
    public void buscar() throws ExcepcionCodefacLite, UnsupportedOperationException {
        throw new UnsupportedOperationException("Buscar guías de remisión no está disponible todavía en el web");
    }

    @Override
    public String titulo() throws ExcepcionCodefacLite, UnsupportedOperationException {
        return "Guía de Remisión";
    }

    @Override
    public InterfaceModelFind obtenerDialogoBusqueda() {
        return null;
    }

    /*
     * ===================== Búsqueda de transportista =====================
     */
    public void abrirDialogoBuscarTransportista() {
        TransportistaBusquedaDialogo dialogo = new TransportistaBusquedaDialogo(sessionMb.getSession().getEmpresa());
        UtilidadesDialogo.abrirDialogoBusqueda(dialogo);
    }

    public void seleccionarTransportista(SelectEvent event) {
        transportistaSeleccionado = (Transportista) event.getObject();
    }

    /*
     * ===================== Búsqueda del destinatario =====================
     */
    public void abrirDialogoBuscarDestinatario() {
        ClienteEstablecimientoBusquedaDialogo dialogo = new ClienteEstablecimientoBusquedaDialogo(sessionMb.getSession());
        dialogo.setPrimeraColumnaNombre(true);
        UtilidadesDialogo.abrirDialogoBusqueda(dialogo);
    }

    public void seleccionarDestinatario(SelectEvent event) {
        PersonaEstablecimiento establecimiento = (PersonaEstablecimiento) event.getObject();
        if (establecimiento != null) {
            destinatarioSeleccionado = establecimiento.getPersona();
            if (direccionDestino == null || direccionDestino.trim().isEmpty()) {
                direccionDestino = establecimiento.getDireccion();
            }
        }
    }

    /*
     * ===================== Búsqueda/agregado de productos al detalle =====================
     */
    public void abrirDialogoBusquedaProducto() {
        ProductoBusquedaDialogo dialogo = new ProductoBusquedaDialogo(sessionMb.getSession().getEmpresa(), true, false);
        UtilidadesDialogo.abrirDialogoBusqueda(dialogo);
    }

    public void seleccionarProducto(SelectEvent event) {
        productoSeleccionado = (Producto) event.getObject();
        codigoDetalle = productoSeleccionado.getCodigoPersonalizado();
        cantidadDetalle = "1";
        descripcionDetalle = productoSeleccionado.getNombre();
    }

    public void agregarDetalle() {
        if (codigoDetalle == null || codigoDetalle.trim().isEmpty() || cantidadDetalle == null || cantidadDetalle.trim().isEmpty()) {
            MensajeMb.mensaje(new CodefacMsj("Error Validación", "Por favor busque un producto y confirme la cantidad", DialogoCodefac.MENSAJE_INCORRECTO));
            return;
        }

        DetalleProductoGuiaRemision detalle = new DetalleProductoGuiaRemision();
        detalle.setCantidad(new BigDecimal(cantidadDetalle));
        detalle.setCodigoAdicional("");
        detalle.setCodigoInterno(codigoDetalle);
        detalle.setDescripcion(descripcionDetalle);

        if (productoSeleccionado != null) {
            detalle.setReferenciaId(productoSeleccionado.getIdProducto());
            detalle.setTipoReferenciaEnum(DetalleProductoGuiaRemision.TipoReferenciaEnum.PRODUCTO);
        }

        detalleProductos.add(detalle);
        limpiarDetalleProducto();
    }

    public void eliminarDetalle(DetalleProductoGuiaRemision detalle) {
        detalleProductos.remove(detalle);
    }

    /*
     * ===================== GuiaRemisionModelInterface =====================
     */
    @Override
    public GuiaRemision obtenerGuiaRemision() {
        return guiaRemision;
    }

    @Override
    public DocumentoEnum obtenerDocumentoSeleccionado() {
        return documentoSeleccionado;
    }

    @Override
    public PuntoEmision obtenerPuntoEmisionSeleccionado() {
        return puntoEmisionSeleccionado;
    }

    @Override
    public Date obtenerFechaInicioTransporte() {
        return fechaInicioTransporte;
    }

    @Override
    public Date obtenerFechaFinTransporte() {
        return fechaFinTransporte;
    }

    @Override
    public String obtenerDireccionPartida() {
        return direccionPartida;
    }

    @Override
    public Transportista obtenerTransportistaSeleccionado() {
        return transportistaSeleccionado;
    }

    @Override
    public Persona obtenerDestinatarioSeleccionado() {
        return destinatarioSeleccionado;
    }

    @Override
    public String obtenerDireccionDestino() {
        return direccionDestino;
    }

    @Override
    public String obtenerMotivoTraslado() {
        return motivoTraslado;
    }

    @Override
    public String obtenerRuta() {
        return ruta;
    }

    @Override
    public String obtenerAutorizacionNumero() {
        return autorizacionNumero;
    }

    @Override
    public String obtenerPreimpresoDestinatario() {
        return preimpresoDestinatario;
    }

    @Override
    public Date obtenerFechaFacturaDestinatario() {
        return fechaFacturaDestinatario;
    }

    @Override
    public List<DetalleProductoGuiaRemision> obtenerDetalleProductos() {
        return detalleProductos;
    }

    @Override
    public boolean obtenerEnviarCorreoDestinatario() {
        return enviarCorreoDestinatario;
    }

    @Override
    public boolean obtenerEnviarCorreoTransportista() {
        return enviarCorreoTransportista;
    }

    @Override
    public ClienteInterfaceComprobante obtenerClienteInterfaceComprobante(GuiaRemision guiaRemisionGrabada) {
        try {
            barraProgreso = new BarraProgreso<GuiaRemision>(guiaRemisionGrabada, new BarraProgreso.InterfazBoton<GuiaRemision>() {
                @Override
                public void alertaListener(String mensajeAlerta) {
                    MensajeMb.mensaje("Alerta", mensajeAlerta, javax.faces.application.FacesMessage.SEVERITY_WARN);
                }

                @Override
                public void imprimirListener(GuiaRemision dato) {
                    try {
                        dato = (GuiaRemision) ServiceFactory.getFactory().getGuiaRemisionServiceIf().buscarPorId(dato.getId());
                        imprimirGuiaRemisionRide(dato);
                    } catch (RemoteException ex) {
                        Logger.getLogger(GuiaRemisionMb.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                @Override
                public String tituloBarra(GuiaRemision dato) {
                    return dato.getSecuencial() + "";
                }
            });

            return new ProformaMb.InterfazCallBack(barraProgreso, sessionMb);
        } catch (RemoteException ex) {
            Logger.getLogger(GuiaRemisionMb.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private void imprimirGuiaRemisionRide(GuiaRemision guiaRemision) {
        try {
            byte[] byteReporte = ServiceFactory.getFactory().getComprobanteServiceIf().getReporteComprobante(guiaRemision.getClaveAcceso(), guiaRemision.getEmpresa());
            JasperPrint jasperPrint = (JasperPrint) UtilidadesRmi.deserializar(byteReporte);
            UtilidadesReporteWeb.generarReporteHojaNuevaPdf(jasperPrint, "guia_remision_" + guiaRemision.getSecuencial() + ".pdf");
        } catch (RemoteException ex) {
            Logger.getLogger(GuiaRemisionMb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (java.io.IOException ex) {
            Logger.getLogger(GuiaRemisionMb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GuiaRemisionMb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void procesarMonitor(GuiaRemision guiaRemisionGrabada) {
        try {
            sessionMb.getBarraProgresoList().add(barraProgreso);
            sessionMb.setActualizarMonitor(true);
            nuevo();
            UtilidadesWeb.ejecutarJavascript("PF('poll').start();");
        } catch (ExcepcionCodefacLite ex) {
            Logger.getLogger(GuiaRemisionMb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
     * ===================== Getters/Setters de estado de pantalla =====================
     */
    public GuiaRemision getGuiaRemision() {
        return guiaRemision;
    }

    public List<DocumentoEnum> getDocumentos() {
        return documentos;
    }

    public DocumentoEnum getDocumentoSeleccionado() {
        return documentoSeleccionado;
    }

    public void setDocumentoSeleccionado(DocumentoEnum documentoSeleccionado) {
        this.documentoSeleccionado = documentoSeleccionado;
    }

    public List<PuntoEmision> getPuntosEmision() {
        return puntosEmision;
    }

    public PuntoEmision getPuntoEmisionSeleccionado() {
        return puntoEmisionSeleccionado;
    }

    public void setPuntoEmisionSeleccionado(PuntoEmision puntoEmisionSeleccionado) {
        this.puntoEmisionSeleccionado = puntoEmisionSeleccionado;
    }

    public Date getFechaInicioTransporte() {
        return fechaInicioTransporte;
    }

    public void setFechaInicioTransporte(Date fechaInicioTransporte) {
        this.fechaInicioTransporte = fechaInicioTransporte;
    }

    public Date getFechaFinTransporte() {
        return fechaFinTransporte;
    }

    public void setFechaFinTransporte(Date fechaFinTransporte) {
        this.fechaFinTransporte = fechaFinTransporte;
    }

    public String getDireccionPartida() {
        return direccionPartida;
    }

    public void setDireccionPartida(String direccionPartida) {
        this.direccionPartida = direccionPartida;
    }

    public Transportista getTransportistaSeleccionado() {
        return transportistaSeleccionado;
    }

    public void setTransportistaSeleccionado(Transportista transportistaSeleccionado) {
        this.transportistaSeleccionado = transportistaSeleccionado;
    }

    public Persona getDestinatarioSeleccionado() {
        return destinatarioSeleccionado;
    }

    public void setDestinatarioSeleccionado(Persona destinatarioSeleccionado) {
        this.destinatarioSeleccionado = destinatarioSeleccionado;
    }

    public String getDireccionDestino() {
        return direccionDestino;
    }

    public void setDireccionDestino(String direccionDestino) {
        this.direccionDestino = direccionDestino;
    }

    public String getMotivoTraslado() {
        return motivoTraslado;
    }

    public void setMotivoTraslado(String motivoTraslado) {
        this.motivoTraslado = motivoTraslado;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getAutorizacionNumero() {
        return autorizacionNumero;
    }

    public void setAutorizacionNumero(String autorizacionNumero) {
        this.autorizacionNumero = autorizacionNumero;
    }

    public String getPreimpresoDestinatario() {
        return preimpresoDestinatario;
    }

    public void setPreimpresoDestinatario(String preimpresoDestinatario) {
        this.preimpresoDestinatario = preimpresoDestinatario;
    }

    public Date getFechaFacturaDestinatario() {
        return fechaFacturaDestinatario;
    }

    public void setFechaFacturaDestinatario(Date fechaFacturaDestinatario) {
        this.fechaFacturaDestinatario = fechaFacturaDestinatario;
    }

    public boolean isEnviarCorreoDestinatario() {
        return enviarCorreoDestinatario;
    }

    public void setEnviarCorreoDestinatario(boolean enviarCorreoDestinatario) {
        this.enviarCorreoDestinatario = enviarCorreoDestinatario;
    }

    public boolean isEnviarCorreoTransportista() {
        return enviarCorreoTransportista;
    }

    public void setEnviarCorreoTransportista(boolean enviarCorreoTransportista) {
        this.enviarCorreoTransportista = enviarCorreoTransportista;
    }

    public List<DetalleProductoGuiaRemision> getDetalleProductos() {
        return detalleProductos;
    }

    public Producto getProductoSeleccionado() {
        return productoSeleccionado;
    }

    public String getCodigoDetalle() {
        return codigoDetalle;
    }

    public void setCodigoDetalle(String codigoDetalle) {
        this.codigoDetalle = codigoDetalle;
    }

    public String getCantidadDetalle() {
        return cantidadDetalle;
    }

    public void setCantidadDetalle(String cantidadDetalle) {
        this.cantidadDetalle = cantidadDetalle;
    }

    public String getDescripcionDetalle() {
        return descripcionDetalle;
    }

    public void setDescripcionDetalle(String descripcionDetalle) {
        this.descripcionDetalle = descripcionDetalle;
    }

    public SessionMb getSessionMb() {
        return sessionMb;
    }

    public void setSessionMb(SessionMb sessionMb) {
        this.sessionMb = sessionMb;
    }

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void limpiar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    public void cargarDatosPantalla(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
