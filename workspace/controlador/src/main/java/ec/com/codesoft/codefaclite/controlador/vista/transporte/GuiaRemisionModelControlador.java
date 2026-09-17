/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vista.transporte;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.vista.factura.ModelControladorAbstract;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.general.ParametrosClienteEscritorio;
import ec.com.codesoft.codefaclite.servidorinterfaz.callback.ClienteInterfaceComprobante;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.ComprobanteDataGuiaRemision;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Transportista;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.DestinatarioGuiaRemision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.DetalleProductoGuiaRemision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.GuiaRemision;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefacInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ComprobanteServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.transporte.GuiaRemisionServiceIf;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controlador compartido para la pantalla de Guía de Remisión, pensado para ser consumido tanto
 * por el panel de escritorio como por el managed bean web (piloto), siguiendo el mismo patrón que
 * {@link ec.com.codesoft.codefaclite.controlador.vista.factura.NotaCreditoModelControlador}.
 *
 * Piloto web: solo maneja UN destinatario por guía (el modelo de datos/SRI sí soporta varios, pero
 * esta primera version arma la lista de destinatarios con un solo elemento).
 *
 * @author Carlos
 */
public class GuiaRemisionModelControlador extends ModelControladorAbstract {

    public GuiaRemisionModelInterface interfaz;
    public SessionCodefacInterface session;

    public GuiaRemisionModelControlador(GuiaRemisionModelInterface interfaz, SessionCodefacInterface session, MensajeVistaInterface mensajeVista) {
        super(mensajeVista);
        this.interfaz = interfaz;
        this.session = session;
    }

    public void grabar() throws ExcepcionCodefacLite {
        try {
            grabarGuiaRemision();
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(GuiaRemisionModelControlador.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensaje(new CodefacMsj("Error", ex.getMessage(), DialogoCodefac.MENSAJE_INCORRECTO));
            throw new ExcepcionCodefacLite(ex.getMessage());
        } catch (RemoteException ex) {
            Logger.getLogger(GuiaRemisionModelControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void grabarGuiaRemision() throws ExcepcionCodefacLite, ServicioCodefacException, RemoteException {
        GuiaRemision guiaRemision = interfaz.obtenerGuiaRemision();

        setearValoresGuiaRemision(guiaRemision);
        armarDestinatarioUnico(guiaRemision);

        if (!validarFormularioGuiaRemision(guiaRemision)) {
            throw new ExcepcionCodefacLite("Error de validación");
        }

        GuiaRemisionServiceIf servicio = ServiceFactory.getFactory().getGuiaRemisionServiceIf();
        GuiaRemision guiaRemisionGrabada = servicio.grabar(guiaRemision);
        mostrarMensaje(MensajeCodefacSistema.AccionesFormulario.GUARDADO);

        //Solo procesar los documentos de comprobantes que son electrónicos
        if (guiaRemisionGrabada.getCodigoDocumentoEnum().getComprobanteElectronico()) {
            ComprobanteDataGuiaRemision comprobanteData = obtenerComprobanteData(guiaRemisionGrabada);

            ClienteInterfaceComprobante cic = interfaz.obtenerClienteInterfaceComprobante(guiaRemisionGrabada);

            if (ParametrosClienteEscritorio.tipoClienteEnum.equals(ParametrosClienteEscritorio.TipoClienteSwingEnum.REMOTO)) {
                cic = null;
            }

            ComprobanteServiceIf comprobanteServiceIf = ServiceFactory.getFactory().getComprobanteServiceIf();
            comprobanteServiceIf.procesarComprobante(comprobanteData, guiaRemisionGrabada, session.getUsuario(), cic);
        }

        interfaz.procesarMonitor(guiaRemisionGrabada);
    }

    private ComprobanteDataGuiaRemision obtenerComprobanteData(GuiaRemision guiaRemision) {
        ComprobanteDataGuiaRemision comprobanteData = new ComprobanteDataGuiaRemision(guiaRemision);
        //GuiaRemision ya expone getMapAdicional() propio, no hace falta duplicarlo aca
        comprobanteData.setMapInfoAdicional(guiaRemision.getMapAdicional());
        return comprobanteData;
    }

    /**
     * Arma el único destinatario del piloto web (con su detalle de productos) y lo deja como la
     * lista de destinatarios de la guía, replicando los valores por defecto que usa el escritorio
     * cuando se agrega un destinatario manualmente (sin factura de respaldo).
     */
    private void armarDestinatarioUnico(GuiaRemision guiaRemision) {
        DestinatarioGuiaRemision destinatario = new DestinatarioGuiaRemision();

        String autorizacionTxt = interfaz.obtenerAutorizacionNumero();
        if (UtilidadesTextos.verificarNullOVacio(autorizacionTxt)) {
            //Por defecto dejo un numero de autorizacion solo para poder pasar la validacion, igual que el escritorio
            autorizacionTxt = "0000000000";
        }
        destinatario.setAutorizacionNumero(autorizacionTxt);

        destinatario.setCodDucumentoSustento("001-001-000000001"); //TODO: Por defecto queda seteado de esta forma para el tema de los preimpresos, igual que el escritorio

        Persona destinatarioSeleccionado = interfaz.obtenerDestinatarioSeleccionado();
        destinatario.setDestinatorio(destinatarioSeleccionado);
        destinatario.setIdentificacion(destinatarioSeleccionado.getIdentificacion());
        destinatario.setRazonSocial(destinatarioSeleccionado.getRazonSocial());

        destinatario.setDireccionDestino(interfaz.obtenerDireccionDestino());

        if (interfaz.obtenerFechaFacturaDestinatario() != null) {
            destinatario.setFechaEmision(UtilidadesFecha.castDateUtilToSql(interfaz.obtenerFechaFacturaDestinatario()));
        }

        destinatario.setGuiaRemision(guiaRemision);

        String motivoTrasladoTxt = interfaz.obtenerMotivoTraslado();
        if (UtilidadesTextos.verificarNullOVacio(motivoTrasladoTxt)) {
            motivoTrasladoTxt = "s/m";
        }
        destinatario.setMotivoTranslado(motivoTrasladoTxt);

        destinatario.setPreimpreso(interfaz.obtenerPreimpresoDestinatario());
        destinatario.setRuta(interfaz.obtenerRuta());
        destinatario.setFacturaReferencia(null);
        destinatario.setCodigoEstablecimiento("001");

        List<DetalleProductoGuiaRemision> detalles = interfaz.obtenerDetalleProductos();
        if (detalles != null) {
            for (DetalleProductoGuiaRemision detalle : detalles) {
                destinatario.addProducto(detalle);
            }
        }

        guiaRemision.addDestinario(destinatario);
    }

    private void setearValoresGuiaRemision(GuiaRemision guiaRemision) {
        Transportista transportista = interfaz.obtenerTransportistaSeleccionado();
        guiaRemision.setTransportista(transportista);
        if (transportista != null) {
            guiaRemision.setDireccion(transportista.getDireccion());
            guiaRemision.setPlaca(transportista.getPlacaVehiculo());
        }

        //La identificación/razón social del comprobante debe corresponder al destinatario, igual que el escritorio
        Persona destinatarioSeleccionado = interfaz.obtenerDestinatarioSeleccionado();
        if (destinatarioSeleccionado != null) {
            guiaRemision.setIdentificacion(destinatarioSeleccionado.getIdentificacion());
            guiaRemision.setRazonSocial(destinatarioSeleccionado.getRazonSocial());
        }

        DocumentoEnum documentoEnum = interfaz.obtenerDocumentoSeleccionado();
        guiaRemision.setCodigoDocumentoEnum(documentoEnum);
        guiaRemision.setDireccionPartida(interfaz.obtenerDireccionPartida());
        guiaRemision.setRise("");

        if (interfaz.obtenerFechaInicioTransporte() != null) {
            guiaRemision.setFechaIniciaTransporte(UtilidadesFecha.castDateUtilToSql(interfaz.obtenerFechaInicioTransporte()));
            //Esta variable se necesita para volver a generar la clave de acceso, igual que el escritorio
            guiaRemision.setFechaEmision(UtilidadesFecha.castDateUtilToSql(interfaz.obtenerFechaInicioTransporte()));
        }
        if (interfaz.obtenerFechaFinTransporte() != null) {
            guiaRemision.setFechaFinTransporte(UtilidadesFecha.castDateUtilToSql(interfaz.obtenerFechaFinTransporte()));
        }

        PuntoEmision puntoEmisionSeleccionado = interfaz.obtenerPuntoEmisionSeleccionado();
        if (puntoEmisionSeleccionado != null) {
            guiaRemision.setPuntoEstablecimiento(new BigDecimal(puntoEmisionSeleccionado.getSucursal().getCodigoSucursal().toString()));
            guiaRemision.setPuntoEmision(puntoEmisionSeleccionado.getPuntoEmision());
            guiaRemision.setPuntoEmisionId(puntoEmisionSeleccionado.getId());
        }

        guiaRemision.setObligadoLlevarContabilidad(session.getEmpresa().getObligadoLlevarContabilidad());
        guiaRemision.setContribuyenteEspecial(session.getEmpresa().getContribuyenteEspecial());
        guiaRemision.setEmpresa(session.getEmpresa());
        guiaRemision.setSucursalEmpresa(session.getSucursal());

        guiaRemision.setDireccionEstablecimiento(session.getSucursal().getDirecccion());
        guiaRemision.setDireccionMatriz(session.getMatriz().getDirecccion());
        guiaRemision.setUsuario(session.getUsuario());

        //Correo del destinatario, igual que el escritorio
        if (interfaz.obtenerEnviarCorreoDestinatario() && destinatarioSeleccionado != null && destinatarioSeleccionado.getCorreoElectronico() != null && !destinatarioSeleccionado.getCorreoElectronico().trim().isEmpty()) {
            guiaRemision.addDatosAdicionalCorreo(destinatarioSeleccionado.getCorreoElectronico(), ComprobanteAdicional.Tipo.TIPO_CORREO, ComprobanteAdicional.CampoDefectoEnum.CORREO);
        }

        //Correo del transportista, igual que el escritorio
        if (interfaz.obtenerEnviarCorreoTransportista() && guiaRemision.getTransportista() != null && guiaRemision.getTransportista().getCorreoElectronico() != null && !guiaRemision.getTransportista().getCorreoElectronico().trim().isEmpty()) {
            guiaRemision.addDatosAdicionalCorreo(guiaRemision.getTransportista().getCorreoElectronico(), ComprobanteAdicional.Tipo.TIPO_CORREO, ComprobanteAdicional.CampoDefectoEnum.CORREO);
        }
    }

    private boolean validarFormularioGuiaRemision(GuiaRemision guiaRemision) {
        if (guiaRemision.getTransportista() == null) {
            mostrarMensaje(new CodefacMsj("Error Validación", "Por favor ingrese un transportista", DialogoCodefac.MENSAJE_INCORRECTO));
            return false;
        }

        if (guiaRemision.getDestinatarios() == null || guiaRemision.getDestinatarios().size() == 0) {
            mostrarMensaje(new CodefacMsj("Error Validación", "Por favor ingrese detalles a la guía de remisión", DialogoCodefac.MENSAJE_INCORRECTO));
            return false;
        }

        if (guiaRemision.getDireccionPartida() == null || guiaRemision.getDireccionPartida().isEmpty()) {
            mostrarMensaje(new CodefacMsj("Error Validación", "Por favor ingrese la dirección de partida", DialogoCodefac.MENSAJE_INCORRECTO));
            return false;
        }

        if (guiaRemision.getFechaIniciaTransporte() == null || guiaRemision.getFechaFinTransporte() == null) {
            mostrarMensaje(new CodefacMsj("Error Validación", "Por favor ingrese las fechas de inicio y fin del transporte", DialogoCodefac.MENSAJE_INCORRECTO));
            return false;
        }

        if (guiaRemision.getFechaIniciaTransporte().compareTo(guiaRemision.getFechaFinTransporte()) > 0) {
            mostrarMensaje(new CodefacMsj("Error Validación", "La fecha de inicio no puede ser superior a la fecha de fin del transporte", DialogoCodefac.MENSAJE_INCORRECTO));
            return false;
        }

        DestinatarioGuiaRemision destinatario = guiaRemision.getDestinatarios().get(0);
        if (destinatario.getDireccionDestino() == null || destinatario.getDireccionDestino().trim().isEmpty()) {
            mostrarMensaje(new CodefacMsj("Error Validación", "Por favor ingrese una dirección de destino", DialogoCodefac.MENSAJE_INCORRECTO));
            return false;
        }

        if (destinatario.getDetallesProductos() == null || destinatario.getDetallesProductos().isEmpty()) {
            mostrarMensaje(new CodefacMsj("Error Validación", "Por favor ingrese al menos un producto en el detalle", DialogoCodefac.MENSAJE_INCORRECTO));
            return false;
        }

        return true;
    }

    /**
     * Interfaz que debe implementar cada vista (escritorio o web) para exponer al controlador los
     * datos del formulario, sin que el controlador dependa de widgets Swing ni de componentes JSF.
     */
    public interface GuiaRemisionModelInterface {

        GuiaRemision obtenerGuiaRemision();

        DocumentoEnum obtenerDocumentoSeleccionado();

        PuntoEmision obtenerPuntoEmisionSeleccionado();

        java.util.Date obtenerFechaInicioTransporte();

        java.util.Date obtenerFechaFinTransporte();

        String obtenerDireccionPartida();

        Transportista obtenerTransportistaSeleccionado();

        Persona obtenerDestinatarioSeleccionado();

        String obtenerDireccionDestino();

        String obtenerMotivoTraslado();

        String obtenerRuta();

        String obtenerAutorizacionNumero();

        String obtenerPreimpresoDestinatario();

        java.util.Date obtenerFechaFacturaDestinatario();

        List<DetalleProductoGuiaRemision> obtenerDetalleProductos();

        boolean obtenerEnviarCorreoDestinatario();

        boolean obtenerEnviarCorreoTransportista();

        ClienteInterfaceComprobante obtenerClienteInterfaceComprobante(GuiaRemision guiaRemisionGrabada);

        void procesarMonitor(GuiaRemision guiaRemisionGrabada);
    }
}
