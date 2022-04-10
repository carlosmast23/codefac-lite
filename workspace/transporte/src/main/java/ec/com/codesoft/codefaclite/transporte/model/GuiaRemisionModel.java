/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.transporte.model;

import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ClienteEstablecimientoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.FacturaBusqueda;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.GuiaRemisionBusqueda;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.TransportistaBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.componentes.ComponenteDatosComprobanteElectronicosInterface;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
//import ec.com.codesoft.codefaclite.controlador.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.controlador.utilidades.ComprobanteElectronicoComponente;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ObserverUpdateInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.enumerador.OrientacionReporteEnum;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.controlador.core.swing.ReporteCodefac;
import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.controlador.core.swing.InterfazComunicacionPanel;
//import ec.com.codesoft.codefaclite.controlador.mensajes.CodefacMsj;
import static ec.com.codesoft.codefaclite.controlador.vista.factura.FacturaModelControlador.obtenerComprobanteData;
import ec.com.codesoft.codefaclite.corecodefaclite.general.ParametrosClienteEscritorio;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfazPostConstructPanel;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.callback.ClienteInterfaceComprobante;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.ComprobanteDataGuiaRemision;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.ComprobanteDataInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PersonaEstablecimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Transportista;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.DestinatarioGuiaRemision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.DetalleProductoGuiaRemision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.GuiaRemision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.GuiaRemisionAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ConfiguracionImpresoraEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.FormatoHojaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.OperadorNegocioEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.VentanaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ComprobanteServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.ParametroUtilidades;
import ec.com.codesoft.codefaclite.transporte.callback.GuiaRemisionImplComprobante;
import ec.com.codesoft.codefaclite.transporte.data.ComprobanteGuiaTransporteData;
import ec.com.codesoft.codefaclite.transporte.data.ComprobanteGuiaTransporteDetalleData;
import ec.com.codesoft.codefaclite.transporte.data.ConsolidadoCargaData;
import ec.com.codesoft.codefaclite.transporte.nocallback.GuiaRemisionNoCallBack;
import ec.com.codesoft.codefaclite.transporte.panel.GuiaRemisionPanel;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.list.UtilidadesLista;
import ec.com.codesoft.codefaclite.utilidades.rmi.UtilidadesRmi;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesSwingX;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.jdesktop.swingx.JXDatePicker;

/**
 *
 * @author Carlos
 */
public class GuiaRemisionModel extends GuiaRemisionPanel implements ComponenteDatosComprobanteElectronicosInterface,InterfazPostConstructPanel{
    
    private GuiaRemision guiaRemision;
    //private Transportista transportista;
    private DestinatarioGuiaRemision destinatarioGuiaRemision;
    private Persona destinatario;
    private PersonaEstablecimiento destinatarioEstablecimiento;
    private Factura facturaSeleccionada;
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        listenerTextBox();
        listenerBotones();
        listenerComponentes();
        listenerCombos();
        listenerFechas();
        listenerTablas();
        iniciarComponentesPantalla();
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite, RemoteException {
        getTxtDireccionPartida().setText(session.getSucursal().getDirecccion());
        guiaRemision=new GuiaRemision();
    }
    
    private boolean validarFormulario()
    {
        if(guiaRemision.getTransportista()==null)
        {
            DialogoCodefac.mensaje("Error Validación","Por favor ingrese un transportista",DialogoCodefac.MENSAJE_INCORRECTO);
            return false;
        }
        
        if(guiaRemision.getDestinatarios()==null || guiaRemision.getDestinatarios().size()==0)
        {
            DialogoCodefac.mensaje("Error Validación","Por favor ingrese detalles a la guía de remisión",DialogoCodefac.MENSAJE_INCORRECTO);
            return false;
        }
                
        if(guiaRemision.getDireccionPartida()==null || guiaRemision.getDireccionPartida().isEmpty())
        {
            DialogoCodefac.mensaje("Error Validación","Por favor ingrese la dirección de partida",DialogoCodefac.MENSAJE_INCORRECTO);
            return false;
        }
        
        if(guiaRemision.getFechaIniciaTransporte().compareTo(guiaRemision.getFechaFinTransporte())>0)
        {
            DialogoCodefac.mensaje("Error Validación","La fecha de inicio no puede ser superior a la fecha de fin del transporte",DialogoCodefac.MENSAJE_INCORRECTO);
            return false;
        }
        return true;
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, RemoteException {
        try {
           
            setearValores();           
                        
            if(!validarFormulario())
            {
                throw new ExcepcionCodefacLite("Error de validación");
            }
            
            guiaRemision=ServiceFactory.getFactory().getGuiaRemisionServiceIf().grabar(guiaRemision);            
            
            //Solo procesar los documentos de comprobantes que son electrónicos
            if(guiaRemision.getCodigoDocumentoEnum().getComprobanteElectronico())
            {
                ComprobanteDataGuiaRemision comprobanteData =obtenerComprobanteData(guiaRemision);
                GuiaRemisionImplComprobante gic=new GuiaRemisionImplComprobante(this, guiaRemision);           

                if (ParametrosClienteEscritorio.tipoClienteEnum.equals(ParametrosClienteEscritorio.TipoClienteSwingEnum.REMOTO)) 
                {
                    gic = null;
                    GuiaRemisionNoCallBack respuestaNoCallBack = new GuiaRemisionNoCallBack(guiaRemision, this);
                    respuestaNoCallBack.iniciar();
                }

                ComprobanteServiceIf comprobanteServiceIf = ServiceFactory.getFactory().getComprobanteServiceIf();
                comprobanteServiceIf.procesarComprobante(comprobanteData, guiaRemision, session.getUsuario(), gic);
            }
            else //Proceso para el resto de documentos que no son electrónicos
            {
                //Si el documento es una guia de remisión interna dejo imprimiendo directamente el documento
                if(guiaRemision.getCodigoDocumentoEnum().equals(DocumentoEnum.GUIA_REMISION_INTERNA))
                {
                    imprimirGuiaRemision(guiaRemision);
                }
            }     
            
            //Pongo el mensaje por que hasta que acepte se puede procesar en segundo plano la guia de remision
            DialogoCodefac.mensaje("Correcto","Los datos de la guia de remisión fueron grabados correctamente", DialogoCodefac.MENSAJE_CORRECTO);
            imprimirConsolidadoCarga(guiaRemision);
            
        } catch (ServicioCodefacException ex) {
            DialogoCodefac.mensaje("Error",ex.getMessage(),DialogoCodefac.MENSAJE_INCORRECTO);
            Logger.getLogger(GuiaRemisionModel.class.getName()).log(Level.SEVERE, null, ex);
            throw new ExcepcionCodefacLite(ex.getMessage());
        }
        
        
    }
    
    private ComprobanteDataGuiaRemision obtenerComprobanteData(GuiaRemision guiaRemision)
    {
        ComprobanteDataGuiaRemision comprobanteData = new ComprobanteDataGuiaRemision(guiaRemision);
        comprobanteData.setMapInfoAdicional(getMapAdicional(guiaRemision));
        return comprobanteData;
    }
    
    private void imprimirConsolidadoCarga(GuiaRemision guiaRemision)
    {
        if(!DialogoCodefac.dialogoPregunta(new CodefacMsj("Desea imprimir el consolidado de carga ?", CodefacMsj.TipoMensajeEnum.ADVERTENCIA)))
        {
            return ;
        }
        
        Map<Long,ConsolidadoCargaData> mapResulados=new HashMap<Long,ConsolidadoCargaData>();
        
        for (DestinatarioGuiaRemision destinatario : guiaRemision.getDestinatarios()) {
            for (DetalleProductoGuiaRemision detallesProducto : destinatario.getDetallesProductos()) {
                
                FacturaDetalle facturaDetalle= consultarFacturaDetalle(detallesProducto.getReferenciaId());
                ConsolidadoCargaData data=mapResulados.get(facturaDetalle.getReferenciaId());
                if(data==null)
                {
                    data=new ConsolidadoCargaData(detallesProducto);                    
                    mapResulados.put(facturaDetalle.getReferenciaId(),data);
                }
                else
                {
                    data.agregarCantidad(detallesProducto.getCantidad());
                }
                
                
                //Agregar el valor total del detalle                
                BigDecimal totalDetalle=facturaDetalle.calcularTotalFinal();
                data.setTotal(data.getTotal().add(totalDetalle));
                
                if(data.getCodigoInterno().equals("12210"))
                {
                    System.out.println("Dato principal ,valor= "+totalDetalle+" -> "+facturaDetalle.getFactura().getPreimpreso());
                    System.out.println("Destinatario Detalle"+destinatario.getCodDucumentoSustento());
                }
                
            }
        }
        
        ////////////////////////////////////////////////////////////////////////
        //Transformar en una lista el map
        List<ConsolidadoCargaData> listaReporte=new ArrayList<ConsolidadoCargaData>(mapResulados.values());  
        UtilidadesLista.ordenarLista(listaReporte,new Comparator<ConsolidadoCargaData>() {
            @Override
            public int compare(ConsolidadoCargaData o1, ConsolidadoCargaData o2) {
                return o1.getDescripcion().compareTo(o2.getDescripcion());
            }
        });
        
        ReporteCodefac.generarReporteInternalFramePlantilla(RecursoCodefac.JASPER_TRANSPORTE,"consolidadoCarga.jrxml", new HashMap(), listaReporte, panelPadre, "Consolidado de Carga", OrientacionReporteEnum.VERTICAL, FormatoHojaEnum.A4, ConfiguracionImpresoraEnum.NINGUNA);
    }
    
    private FacturaDetalle consultarFacturaDetalle(Long referenciaId)
    {
        try {
            FacturaDetalle facturaDetalle= ServiceFactory.getFactory().getFacturaDetalleServiceIf().buscarPorId(referenciaId);
            return facturaDetalle;
            
        } catch (RemoteException ex) {
            Logger.getLogger(ConsolidadoCargaData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
         
    }
    
    private Map<String,String> getMapAdicional(GuiaRemision guiaRemision)
    {
        Map<String, String> parametroMap = new HashMap<String, String>();
        if (guiaRemision.getDatosAdicionales() != null) {
            for (GuiaRemisionAdicional datoAdicional : guiaRemision.getDatosAdicionales()) {
                parametroMap.put(datoAdicional.getCampo(), datoAdicional.getValor());
            }
        }
        return parametroMap;
    }

    @Override
    public void editar() throws ExcepcionCodefacLite, RemoteException {
        if(!guiaRemision.getEstadoEnum().equals(ComprobanteEntity.ComprobanteEnumEstado.SIN_AUTORIZAR))
        {
            DialogoCodefac.mensaje("Advertencia","La guia de remisión no se pueden modificar ",DialogoCodefac.MENSAJE_ADVERTENCIA);
            throw new ExcepcionCodefacLite("cancelar el evento editar");
        }
        
        try {
            setearValores();
            ServiceFactory.getFactory().getGuiaRemisionServiceIf().editar(guiaRemision);
            DialogoCodefac.mensaje(MensajeCodefacSistema.AccionesFormulario.EDITADO);
            
        } catch (RemoteException ex) {
            Logger.getLogger(GuiaRemisionModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            DialogoCodefac.mensaje(ex.getMessage(), DialogoCodefac.MENSAJE_ADVERTENCIA);
            Logger.getLogger(GuiaRemisionModel.class.getName()).log(Level.SEVERE, null, ex);
            throw new ExcepcionCodefacLite(ex.getMessage());
        }
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite, RemoteException {
        ComprobanteElectronicoComponente.eliminarComprobante(this, guiaRemision,null);
    }

    @Override
    public void imprimir() throws ExcepcionCodefacLite, RemoteException {
        if (this.guiaRemision != null && estadoFormulario.equals(ESTADO_EDITAR)) {
            String[] opciones = {"Ride", "Consolidado de Carga", "Cancelar"};
            int opcionSeleccionada = DialogoCodefac.dialogoPreguntaPersonalizada("Reporte", "Por favor seleccione el tipo de reporte?", DialogoCodefac.MENSAJE_CORRECTO, opciones);
            switch (opcionSeleccionada) {
                case 0:
                    imprimirGuiaRemision(guiaRemision);
                case 1:
                    imprimirConsolidadoCarga(guiaRemision);
                    break;
            }
        }
    }
    
    private void imprimirGuiaRemision(GuiaRemision guiaRemision)
    {
        try {
            
            if(guiaRemision.getCodigoDocumentoEnum().equals(DocumentoEnum.GUIA_REMISION))
            {            
                String claveAcceso = guiaRemision.getClaveAcceso();
                byte[] byteReporte= ServiceFactory.getFactory().getComprobanteServiceIf().getReporteComprobante(claveAcceso,guiaRemision.getEmpresa());
                JasperPrint jasperPrint=(JasperPrint) UtilidadesRmi.deserializar(byteReporte);
                panelPadre.crearReportePantalla(jasperPrint, guiaRemision.getPreimpreso());
                
            }
            //Volver a generar el Ride
            else if(guiaRemision.getCodigoDocumentoEnum().equals(DocumentoEnum.GUIA_REMISION_INTERNA))
            {
                ComprobanteDataInterface dataFactura= obtenerComprobanteData(guiaRemision);
                ComprobanteServiceIf comprobanteService=ServiceFactory.getFactory().getComprobanteServiceIf();
                byte[] byteReporte=comprobanteService.getReporteComprobanteComprobante(dataFactura, session.getUsuario(),guiaRemision.getPreimpreso());
                JasperPrint jasperPrint=(JasperPrint) UtilidadesRmi.deserializar(byteReporte);
                panelPadre.crearReportePantalla(jasperPrint,guiaRemision.getPreimpreso());
            }
            
        } catch (RemoteException ex) {
            Logger.getLogger(GuiaRemisionModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GuiaRemisionModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GuiaRemisionModel.class.getName()).log(Level.SEVERE, null, ex);
        }
                        
    }

    @Override
    public void actualizar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void limpiar() {
        //getLblRuc().setText(session.getEmpresa().getIdentificacion());
        //getLblTelefonos().setText(session.getSucursal().getTelefono());
        //getLblNombreComercial().setText(session.getEmpresa().getNombreLegal());
        //getLblDireccion().setText(session.getSucursal().getDirecccion());
        getLblCantidadProductos().setText("0");
        ComprobanteElectronicoComponente.cargarSecuencial(session.getUsuario(),DocumentoEnum.GUIA_REMISION,session.getSucursal(), getCmbPuntoEmision(), getLblEstablecimiento(), getLblSecuencial());
        
        ///Limpiar Variables
        //guiaRemision=new GuiaRemision();
        //transportista=new Transportista();
        destinatarioGuiaRemision=new DestinatarioGuiaRemision();
        destinatario=new Persona();
        
        //Limpiar etiquetas de la pantalla
        getLblNombresTransportista().setText("");
        getLblPlacaTransportista().setText("");
        //getTxtDireccionPartida().setText("");
        getCmbFechaInicio().setDate(UtilidadesFecha.getFechaHoy());
        getCmbFechaFin().setDate(UtilidadesFecha.getFechaHoy());
        getTxtIdentificacionTransportista().setText("");
        getTxtIdentificacionDestinatario().setText("");
        getTxtDireccionDestino().setText("");
        //getTxtMotivoTraslado().setText("");
        getTxtRuta().setText("");
        
        getTxtPreimpreso().setText("");
        getTxtAutorizacion().setText("");
        getTxtDocAduanero().setText("");
        getCmbFechaFactura().setDate(UtilidadesFecha.hoy());
        getTxtCantidad().setText("");
        getTxtDescripcionDetalle().setText("");
        getTxtCodigoDetalle().setText("");
        getCmbDestinatarios().removeAllItems();
        getTxtCodigoSucursal().setValue(1);
        
        ParametroCodefac parametroMotivoTraslado= session.getParametrosCodefac().get(ParametroCodefac.MOTIVO_TRASLADO_GUIA_REMISION);
        if(estadoFormulario.equals(ESTADO_GRABAR) &&  parametroMotivoTraslado!=null)
        {
            getTxtMotivoTraslado().setText(parametroMotivoTraslado.getValor());
        }
        
        imprimirTabla();
        
        
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

    @Override
    public InterfaceModelFind obtenerDialogoBusqueda() {
        GuiaRemisionBusqueda busqueda=new GuiaRemisionBusqueda();
        //BuscarDialogoModel buscarModel=new BuscarDialogoModel(busqueda);
        return busqueda;
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        guiaRemision=(GuiaRemision) entidad;
        cargarDatosTransportista();
        
        DestinatarioGuiaRemision destinarioDefecto=guiaRemision.getDestinatarios().get(0);
        PersonaEstablecimiento sucursal=(destinarioDefecto.getSucursal()!=null)?destinarioDefecto.getSucursal():destinarioDefecto.getDestinatorio().getEstablecimientos().get(0);
                
        cargarCliente(sucursal);
        getTxtDireccionPartida().setText(guiaRemision.getDireccionPartida());
        getCmbFechaInicio().setDate(guiaRemision.getFechaIniciaTransporte());
        getCmbFechaFin().setDate(guiaRemision.getFechaFinTransporte());
        cargarDestinatariosAgregados();
        imprimirTabla();
        //getLblSecuencial().setText(guiaRemision.getPreimpreso());
        ComprobanteElectronicoComponente.cargarSecuencialConsulta(guiaRemision,getCmbPuntoEmision(),getLblEstablecimiento(),getLblSecuencial());
        getPnlDatosAdicionales().habiliarBotonAutorizar();
        //cargarDatoFactura(guiaRemision.getre)
        
    }

    private void iniciarComponentesPantalla() {
        agregarPlaceHolder();
        iniciarComponentesComboBox();
        
        
    }
    
    private void iniciarComponentesComboBox()
    {
        getCmbDocumento().addItem(DocumentoEnum.GUIA_REMISION);
        getCmbDocumento().addItem(DocumentoEnum.GUIA_REMISION_INTERNA);
        
        //Seleccionar el documento segun la configuracion por defecto
        DocumentoEnum documentoConfigurado=ParametroUtilidades.obtenerValorParametroEnum(session.getEmpresa(),ParametroCodefac.DOCUMENTO_GUIA_REMISION_DEFECTO, DocumentoEnum.GUIA_REMISION);
        if(documentoConfigurado!=null)
        {
            getCmbDocumento().setSelectedItem(documentoConfigurado);
        }
    }

    private void agregarPlaceHolder() {
        UtilidadesSwingX.placeHolder("Identificación",getTxtIdentificacionDestinatario());
        UtilidadesSwingX.placeHolder("Identificación",getTxtIdentificacionTransportista());
        UtilidadesSwingX.placeHolder("Autorización", getTxtAutorizacion());
        UtilidadesSwingX.placeHolder("Preimpreso", getTxtPreimpreso());
        UtilidadesSwingX.placeHolder("Doc.Aduanero", getTxtDocAduanero());
        
        UtilidadesSwingX.placeHolder("Dirección Destino", getTxtDireccionDestino());
        UtilidadesSwingX.placeHolder("Motivo Traslado", getTxtMotivoTraslado());
        UtilidadesSwingX.placeHolder("Ruta", getTxtRuta());
        
        
        
    }

    private void listenerTextBox() {
        
        getTxtIdentificacionDestinatario().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                String identificacion=getTxtIdentificacionDestinatario().getText();
                if(!identificacion.equals(""))
                {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        try {
                            //Map<String, Object> mapParametros = new HashedMap<String, Object>();
                            //mapParametros.put("identificacion", identificacion);
                            List<PersonaEstablecimiento> resultados=ServiceFactory.getFactory().getPersonaEstablecimientoServiceIf().buscarActivoPorIdentificacion(identificacion,session.getEmpresa()); //Todo crear mejor un metodo que ya obtener filtrado los datos
                            if(resultados.size()==0)
                            {
                                if(DialogoCodefac.dialogoPregunta("Crear Cliente","No existe el Cliente, lo desea crear?",DialogoCodefac.MENSAJE_ADVERTENCIA))
                                {
                                    btnListenerAgregarCliente();
                                }
                            }
                            else
                            {
                                cargarCliente(resultados.get(0));
                            }
                        } catch (RemoteException ex) {
                            Logger.getLogger(GuiaRemisionModel.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ServicioCodefacException ex) {
                            Logger.getLogger(GuiaRemisionModel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });
    }
    
    private void btnListenerAgregarCliente()
    {
        Object[] parametros={OperadorNegocioEnum.CLIENTE,getTxtIdentificacionDestinatario().getText()};
        panelPadre.crearDialogoCodefac(new ObserverUpdateInterface<Persona>() {
            @Override
            public void updateInterface(Persona entity) {
                if(entity!=null)
                {
                    destinatario=entity;
                }
            }
        },VentanaEnum.CLIENTE, false,parametros,this);
    }
   
    private void cargarCliente(PersonaEstablecimiento cliente)
    {
        if (cliente != null) {
            //factura.setCliente(cliente);
            //Elimino datos adicionales del anterior cliente si estaba seleccionado
            //factura.eliminarTodosDatosAdicionales();
            
            //cargarFormaPago();
            getTxtIdentificacionDestinatario().setText(cliente.getPersona().getIdentificacion());
            getLblNombresCompletosDestinatarios().setText(cliente.getPersona().getRazonSocial());
            getTxtDireccionDestino().setText(cliente.getDireccion());  
            getTxtCodigoSucursal().setValue(Integer.parseInt(cliente.getCodigoSucursal()));
        };
    }
    
    private void btnListenerBuscarCliente() {
        ClienteEstablecimientoBusquedaDialogo clienteBusquedaDialogo = new ClienteEstablecimientoBusquedaDialogo(session);
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(clienteBusquedaDialogo);
        buscarDialogoModel.setVisible(true);
        //factura.setCliente((Persona) buscarDialogoModel.getResultado());      
        if(buscarDialogoModel.getResultado()!=null)
        {
            cargarDatosDestinatario((PersonaEstablecimiento) buscarDialogoModel.getResultado());
            /*destinatarioEstablecimiento=(PersonaEstablecimiento) buscarDialogoModel.getResultado();
            destinatario=destinatarioEstablecimiento.getPersona();
            cargarCliente(destinatarioEstablecimiento);        */
        }
        
    }
    
    private void cargarDatosDestinatario(PersonaEstablecimiento personaEstablecimiento)
    {
        destinatarioEstablecimiento=personaEstablecimiento;
        destinatario=destinatarioEstablecimiento.getPersona();
        cargarCliente(destinatarioEstablecimiento);
    }
    
    private void cargarDatoFactura(Factura factura)
    {
        facturaSeleccionada=factura;
        getTxtPreimpreso().setText(factura.getPreimpreso());
        getTxtAutorizacion().setText(factura.getClaveAcceso());
        getCmbFechaFactura().setDate(factura.getFechaEmision());
   }
    
    private DestinatarioGuiaRemision crearDestinatario()
    {
        try {
            //Validaciones previas para agregar el destinatario
            if(getTxtAutorizacion().getText().trim().isEmpty())
            {
                DialogoCodefac.mensaje("Advertencia","No se puede agregar facturas sin autorización",DialogoCodefac.MENSAJE_ADVERTENCIA);
                return null;
            }
            
            if(getTxtRuta().getText().trim().isEmpty())
            {
                getTxtRuta().setText("Sin Ruta");
            }
            
            DocumentoEnum documentoEnum=(DocumentoEnum) getCmbDocumento().getSelectedItem();
            if(!documentoEnum.equals(DocumentoEnum.GUIA_REMISION_INTERNA))
            {
                if(getTxtAutorizacion().getText().length()<=9)
                {
                    DialogoCodefac.mensaje("Advertencia","La autorización tiene que tener más de 9 digitos",DialogoCodefac.MENSAJE_ADVERTENCIA);
                    return null;
                }
            }
            
            /*
            DestinatarioGuiaRemision destinatario=new DestinatarioGuiaRemision();
            destinatario.setAutorizacionNumero(getTxtAutorizacion().getText());
            destinatario.setCodDucumentoSustento(""); //TODO: falta ver si solo pongo el codigo de la factura o pueden haber otros documentos que se pueden transportar
            destinatario.setDestinatorio(this.destinatario);
            destinatario.setDireccionDestino(getTxtDireccionDestino().getText());
            destinatario.setFechaEmision(new java.sql.Date(getCmbFechaFactura().getDate().getTime()));
            destinatario.setGuiaRemision(guiaRemision);
            destinatario.setMotivoTranslado(getTxtMotivoTraslado().getText());
            destinatario.setPreimpreso(getTxtPreimpreso().getText());
            destinatario.setRazonSocial(this.destinatario.getRazonSocial());
            destinatario.setRuta(destinatario.getRuta());
            destinatario.setFacturaReferencia(facturaSeleccionada);
            destinatario.setIdentificacion(this.destinatario.getIdentificacion());
            destinatario.setCodigoEstablecimiento((Integer) getTxtCodigoSucursal().getValue());
            
            ///Agregado detalle  de los productos de la factura enlazada
            for (FacturaDetalle facturaDetalle : facturaSeleccionada.getDetalles()) {
            
            //Vericar que cuando sea productos esta activado la opcion de poder transportar en la guia de remision
            if(facturaDetalle.getTipoDocumentoEnum().equals(TipoDocumentoEnum.INVENTARIO) || facturaDetalle.getTipoDocumentoEnum().equals(TipoDocumentoEnum.LIBRE))
            {
            try {
            Producto producto = (Producto) ServiceFactory.getFactory().getFacturaDetalleServiceIf().getReferenciaDetalle(facturaDetalle);
            if(producto.getTransportarEnGuiaRemisionEnum().equals(EnumSiNo.NO))
            {
            continue;
            }
            } catch (ServicioCodefacException ex) {
            Logger.getLogger(GuiaRemisionModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RemoteException ex) {
            Logger.getLogger(GuiaRemisionModel.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            }
            
            DetalleProductoGuiaRemision detalle=new DetalleProductoGuiaRemision();
            detalle.setCantidad(facturaDetalle.getCantidad().intValue());
            detalle.setCodigoAdicional("");
            detalle.setCodigoInterno(facturaDetalle.getReferenciaId()+""); //Todo: Ver si en este campo para futuras versiones se graba mejor el codigo de los productos , sevicios , etc
            detalle.setDescripcion(facturaDetalle.getDescripcion());
            detalle.setReferenciaId(facturaDetalle.getId());
            destinatario.addProducto(detalle);
            }
            
            if(destinatario.getDetallesProductos()==null || destinatario.getDetallesProductos().size()==0)
            {
            DialogoCodefac.mensaje(new CodefacMsj("El destinatario no tiene ningun detalle",CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
            return null;
            }*/
            java.util.Date fechaFactura=getCmbFechaFactura().getDate();
            //getTxtRuta().getText();
            DestinatarioGuiaRemision destinatario=DestinatarioGuiaRemision.crearDestinatario(
                    guiaRemision,
                    facturaSeleccionada,
                    getTxtAutorizacion().getText(),
                    this.destinatario,
                    getTxtDireccionDestino().getText(),
                    fechaFactura,
                    getTxtMotivoTraslado().getText(),
                    getTxtRuta().getText(),
                    getTxtPreimpreso().getText(),
                    (Integer) getTxtCodigoSucursal().getValue());   
            
            return destinatario;
            
        } catch (ServicioCodefacException ex) {
            DialogoCodefac.mensaje(new CodefacMsj(ex.getMessage(), CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
            Logger.getLogger(GuiaRemisionModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(GuiaRemisionModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private void cargarDatosTransportista() {
        if(guiaRemision.getTransportista()!=null)
        {
            getTxtIdentificacionTransportista().setText(guiaRemision.getTransportista().getIdentificacion());
            getLblNombresTransportista().setText(guiaRemision.getTransportista().getRazonSocial());
            getLblPlacaTransportista().setText(guiaRemision.getTransportista().getPlacaVehiculo());
        }
    }
    
    
    private void listenerBotones() {
        
        getBtnCargarFacturaIgualSecuencial().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    PuntoEmision puntoEmision=(PuntoEmision) getCmbPuntoEmision().getSelectedItem();
                    
                    Factura factura=ServiceFactory.getFactory().getFacturacionServiceIf().buscarPorPremimpresoYEstado(
                            Integer.parseInt(getLblSecuencial().getText().replace("-","")),
                            new BigDecimal(puntoEmision.getSucursal().getCodigoSucursal()),
                            puntoEmision.getPuntoEmision(),
                            ComprobanteEntity.ComprobanteEnumEstado.AUTORIZADO);
                    
                    if(factura==null)
                    {
                        DialogoCodefac.mensaje(MensajeCodefacSistema.Consultas.NO_EXISTE_DATO_BUSCAR);
                        //TODO: IMPLEMENTAR MENSAJE
                    }
                    else
                    {
                        //factura.getSucursal();
                        getCmbFechaInicio().setDate(factura.getFechaEmision());
                        getCmbFechaFin().setDate(factura.getFechaEmision());
                        cargarDatosDestinatario(factura.getSucursal());
                        cargarDatoFactura(factura);
                        agregarDetalleGuiaRemision();

                    }
                    
                } catch (RemoteException ex) {
                    Logger.getLogger(GuiaRemisionModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        getBtnBuscarTransportista().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TransportistaBusquedaDialogo transportistaBusquedaDialogo = new TransportistaBusquedaDialogo(session.getEmpresa());
                BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(transportistaBusquedaDialogo);
                buscarDialogoModel.setVisible(true);
                Transportista transportistaTemp = (Transportista) buscarDialogoModel.getResultado();
                if (transportistaTemp != null) {                    
                    guiaRemision.setTransportista(transportistaTemp);
                    cargarDatosTransportista();
                }
            }

        });
        
        getBtnAgregarDestinarioGuiaRemision().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarDetalleGuiaRemision();
            }
        });
        
        getBtnAgregarDestinatario().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnListenerAgregarCliente();
            }
        });
        
        getBtnBuscarDestinatario().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnListenerBuscarCliente();
            }
        });
        
        getBtnEliminarDetalle().addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                eliminarFila();
            }
        });
        
        getBtnBuscarFactura().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FacturaBusqueda facturaBusqueda =null;
                
                if(destinatarioEstablecimiento!=null)
                    facturaBusqueda = new FacturaBusqueda(destinatarioEstablecimiento,session.getSucursal());
                else
                    facturaBusqueda = new FacturaBusqueda(destinatario,session.getSucursal());
                    
                facturaBusqueda.setEstadoEnviadoGuiaRemision(EnumSiNo.NO);
                
                BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(facturaBusqueda);
                buscarDialogoModel.setVisible(true);
                Factura facturaTmp = (Factura) buscarDialogoModel.getResultado();
                if(facturaTmp!=null)
                {                    
                    cargarDatoFactura(facturaTmp);
                }
                else
                {
                    facturaSeleccionada=null;
                }
                
            }
        });
    }
    
    private void agregarDetalleGuiaRemision()
    {
        DestinatarioGuiaRemision destinatarioGuiaRemision = crearDestinatario();
        if(destinatarioGuiaRemision!=null)
        {
            if (validarDestinarioGuiaRemision(destinatarioGuiaRemision)) {
                guiaRemision.addDestinario(destinatarioGuiaRemision);
                cargarDestinatariosAgregados();
                imprimirTabla();
            }
        }
    }
    
    //TODO: Agregar estas validaciones en el servidor
    private boolean validarDestinarioGuiaRemision(DestinatarioGuiaRemision destinatarioGuiaRemision)
    {
        if(destinatarioGuiaRemision.getDireccionDestino()==null || destinatarioGuiaRemision.getDireccionDestino().trim().isEmpty())
        {
            DialogoCodefac.mensaje("Error Validación","Por favor ingrese una dirección de destino ",DialogoCodefac.MENSAJE_INCORRECTO);
            return false;
        }
        
        if(destinatarioGuiaRemision.getMotivoTranslado().isEmpty())
        {
            DialogoCodefac.mensaje("Error Validación","Por favor ingrese un motivo de traslado",DialogoCodefac.MENSAJE_INCORRECTO);
            return false;
        }
        
        if (destinatarioGuiaRemision.getCodigoEstablecimiento().isEmpty()) {
            DialogoCodefac.mensaje("Error Validación", "Por favor ingrese un código de establecimiento", DialogoCodefac.MENSAJE_INCORRECTO);
            return false;
        }
        
        return true;
    }
    
    private void imprimirTabla()
    {
        
        String[] titulos={"","Motivo","Ruta","Factura","FechaFact","Destinatario","Código Producto","Descripción","Cantidad"};
        
        DefaultTableModel modeloTabla=UtilidadesTablas.crearModeloTabla(titulos,
        new Class[]{DetalleProductoGuiaRemision.class,
        String.class,
        String.class,
        String.class,
        String.class,
        String.class,
        String.class,
        String.class,
        String.class});

        if(guiaRemision!=null && guiaRemision.getDestinatarios()!=null)
        {
            List<DetalleProductoGuiaRemision> listaProductosTmp=new ArrayList<DetalleProductoGuiaRemision>();
            
            for (DestinatarioGuiaRemision destinatarios : guiaRemision.getDestinatarios()) {
                for (DetalleProductoGuiaRemision detalle : destinatarios.getDetallesProductos()) {
                   listaProductosTmp.add(detalle);
                }            
            }
            
            UtilidadesLista.ordenarLista(listaProductosTmp,new Comparator<DetalleProductoGuiaRemision>() {
                @Override
                public int compare(DetalleProductoGuiaRemision o1, DetalleProductoGuiaRemision o2) {
                    return o1.getDestinatario().getPreimpreso().compareTo(o2.getDestinatario().getPreimpreso());
                }
            });
            
            for (DetalleProductoGuiaRemision detalle : listaProductosTmp) {
                    modeloTabla.addRow(new Object[]{
                        detalle,
                        detalle.getDestinatario().getMotivoTranslado(),
                        detalle.getDestinatario().getRuta(),
                        detalle.getDestinatario().getPreimpreso(),
                        detalle.getDestinatario().getFechaEmision().toString(),
                        detalle.getDestinatario().getDestinatorio().getNombresCompletos(),
                        detalle.getCodigoInterno(),
                        detalle.getDescripcion(),
                        detalle.getCantidad(),
                    });

            }            
            
            //Imprimir el total de la cantidad de productos a transportar
            getLblCantidadProductos().setText(guiaRemision.obtenerTotalProductos().toString());
        
        }
        
        
        getTblGuiaRemision().setModel(modeloTabla);
        UtilidadesTablas.ocultarColumna(getTblGuiaRemision(),0);
        
    }
    
    private void cargarDestinatariosAgregados()
    {
        getCmbDestinatarios().removeAllItems();
        for (DestinatarioGuiaRemision destinatario : guiaRemision.getDestinatarios()) {
            getCmbDestinatarios().addItem(destinatario);
        }
        
    }
    
    private PuntoEmision obtenerPuntoEmisionSeleccionado()
    {
        return (PuntoEmision) getCmbPuntoEmision().getSelectedItem();
    }

    private void setearValores() {
        Transportista transportista=guiaRemision.getTransportista();
        if(transportista!=null)
        {
            guiaRemision.setTransportista(transportista);
            guiaRemision.setIdentificacion(transportista.getIdentificacion());
            guiaRemision.setDireccion(transportista.getDireccion());
            guiaRemision.setRazonSocial(transportista.getRazonSocial());
            guiaRemision.setPlaca(transportista.getPlacaVehiculo());
        }

        DocumentoEnum documentoEnum=(DocumentoEnum) getCmbDocumento().getSelectedItem();
        guiaRemision.setCodigoDocumentoEnum(documentoEnum);
        guiaRemision.setDireccionPartida(getTxtDireccionPartida().getText());
        guiaRemision.setRise("");
        guiaRemision.setFechaIniciaTransporte(new java.sql.Date(getCmbFechaInicio().getDate().getTime()));
        guiaRemision.setFechaEmision(new java.sql.Date(getCmbFechaInicio().getDate().getTime())); //Esto esta variable porque necesito para volver a generar la clave de acceso
        guiaRemision.setFechaFinTransporte(new java.sql.Date(getCmbFechaFin().getDate().getTime()));
        PuntoEmision puntoEmisionSeleccionado= obtenerPuntoEmisionSeleccionado();
        guiaRemision.setPuntoEstablecimiento(new BigDecimal(puntoEmisionSeleccionado.getSucursal().getCodigoSucursal().toString()));
        guiaRemision.setPuntoEmision(puntoEmisionSeleccionado.getPuntoEmision());
        guiaRemision.setPuntoEmisionId(puntoEmisionSeleccionado.getId());
        guiaRemision.setObligadoLlevarContabilidad(session.getEmpresa().getObligadoLlevarContabilidad());
        guiaRemision.setContribuyenteEspecial(session.getEmpresa().getContribuyenteEspecial());
        guiaRemision.setEmpresa(session.getEmpresa());
        guiaRemision.setSucursalEmpresa(session.getSucursal());
        
        guiaRemision.setDireccionEstablecimiento(session.getSucursal().getDirecccion());
        guiaRemision.setDireccionMatriz(session.getMatriz().getDirecccion());
        guiaRemision.setUsuario(session.getUsuario());
        
        /**
         * Agregar los correos de los destinatarios
         */
        if(getChkEnviarCorreoClientes().isSelected() && guiaRemision.getDestinatarios()!=null)
        {
            for (DestinatarioGuiaRemision destinatario : guiaRemision.getDestinatarios()) {
                guiaRemision.addDatosAdicionalCorreo(destinatario.getDestinatorio().getCorreoElectronico(), ComprobanteAdicional.Tipo.TIPO_CORREO, ComprobanteAdicional.CampoDefectoEnum.CORREO);
            }
        }
        
        //Agregado correo del transportista
        if(getChkEnviarCorreoTransportista().isSelected() &&  
                guiaRemision.getTransportista()!=null && 
                guiaRemision.getTransportista().getCorreoElectronico()!=null && 
                !guiaRemision.getTransportista().getCorreoElectronico().trim().isEmpty())
        {
            guiaRemision.addDatosAdicionalCorreo(guiaRemision.getTransportista().getCorreoElectronico(), ComprobanteAdicional.Tipo.TIPO_CORREO, ComprobanteAdicional.CampoDefectoEnum.CORREO);
        }
  
        
    }
    


    private void listenerCombos() {
        getCmbPuntoEmision().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ComprobanteElectronicoComponente.cargarSecuencial(session.getUsuario(),DocumentoEnum.GUIA_REMISION,session.getSucursal(), getCmbPuntoEmision(), getLblEstablecimiento(), getLblSecuencial());
            }
        });
        
        
    }
    
    public void imprimirComprobanteGuiaRemision(GuiaRemision guiaRemision) {

         List<ComprobanteGuiaTransporteData> dataReporte=getDetalleDataReporte(guiaRemision);
            
            //map de los parametros faltantes
            Map<String,Object> mapParametros=getMapParametrosReporte(guiaRemision);
            
            
            InputStream path = RecursoCodefac.JASPER_TRANSPORTE.getResourceInputStream("comprobante_guia_remision.jrxml");            
           
            
           ReporteCodefac.generarReporteInternalFramePlantilla(path, mapParametros, dataReporte, this.panelPadre, "Comprobante Guía Remisión ",OrientacionReporteEnum.VERTICAL,FormatoHojaEnum.A5);
    }
    
    /**
     * TODO: Ver si estos metodos se pueden unir con los de la factura
     * @param guiaRemision
     * @return 
     */
    public Map<String, Object> getMapParametrosReporte(GuiaRemision guiaRemision) {
        //map de los parametros faltantes
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cliente", guiaRemision.getRazonSocial());
        mapParametros.put("cedula", guiaRemision.getIdentificacion());
        mapParametros.put("direccion", guiaRemision.getDireccion());
        mapParametros.put("telefonos", guiaRemision.getTelefono());
        mapParametros.put("fechaIngreso", UtilidadesFecha.formatoDiaMesAño(new java.sql.Date(guiaRemision.getFechaEmision().getTime())));
        mapParametros.put("codigo", guiaRemision.getPreimpreso().toString());
        mapParametros.put("autorizacion", guiaRemision.getClaveAcceso());
        mapParametros.put("origen", guiaRemision.getDireccionPartida());
        mapParametros.put("placa", guiaRemision.getPlaca());
        
        
        InputStream inputStreamSubReporte = RecursoCodefac.JASPER_TRANSPORTE.getResourceInputStream("comprobanteGuiaRemisionDetalle.jrxml");
        try {
            JasperReport reportDetalle = JasperCompileManager.compileReport(inputStreamSubReporte);
            mapParametros.put("pl_url_detalle",reportDetalle);
        } catch (JRException ex) {
            Logger.getLogger(GuiaRemisionModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return mapParametros;

    }
    
    public List<ComprobanteGuiaTransporteData> getDetalleDataReporte(GuiaRemision guiaRemision)
    {
        List<ComprobanteGuiaTransporteData> dataReporte = new ArrayList<ComprobanteGuiaTransporteData>();
        
        guiaRemision.getDestinatarios();

        for (DestinatarioGuiaRemision detalle : guiaRemision.getDestinatarios()) {

            ComprobanteGuiaTransporteData data = new ComprobanteGuiaTransporteData();
            
            data.setDestino(detalle.getDireccionDestino());
            data.setIdentificacion_persona(detalle.getIdentificacion());
            data.setMotivo_traslado(detalle.getMotivoTranslado());
            data.setPreimpreso(detalle.getPreimpreso());
            data.setRazon_social_persona(detalle.getRazonSocial());
            
            List<ComprobanteGuiaTransporteDetalleData> productosData=new ArrayList<ComprobanteGuiaTransporteDetalleData>();
            //List<DetalleProductoGuiaRemision> detallesProductos= detalle.getDetallesProductos();
            //Detalle detalle.getDetallesProductos();
            for (DetalleProductoGuiaRemision detalleProducto : detalle.getDetallesProductos()) {
                ComprobanteGuiaTransporteDetalleData detalleProductoData=new ComprobanteGuiaTransporteDetalleData();
                detalleProductoData.setCantidad(detalleProducto.getCantidad().toString());
                detalleProductoData.setCodigo_principal(detalleProducto.getCodigoInterno());
                detalleProductoData.setDescripcion(detalleProducto.getDescripcion());
                productosData.add(detalleProductoData);
            }

            data.setProductos(new JRBeanCollectionDataSource(productosData));            
            
            dataReporte.add(data);
        }
        return dataReporte;
    }
    
        @Override
    public ComprobanteEntity getComprobante() {
        return guiaRemision;
    }    

    private void listenerComponentes() {
        getPnlDatosAdicionales().setComprobante(this);
    }

    @Override
    public void eventoCambiarEstado() {
        if(estadoFormulario.equals(ESTADO_GRABAR))
        {
            getPnlDatosAdicionales().habilitar(false);
            getBtnCargarFacturaIgualSecuencial().setEnabled(true);
        }
        else if(estadoFormulario.equals(ESTADO_EDITAR))
        {
            getPnlDatosAdicionales().habilitar(true);
            getBtnCargarFacturaIgualSecuencial().setEnabled(false);
        }
    }
    
    @Override
    public Empresa getEmpresa() {
        return session.getEmpresa();
    }

    @Override
    public InterfazComunicacionPanel getPanelPadre() {
        return panelPadre;
    }

    @Override
    public List<ComprobanteAdicional> getDatosAdicionales() {
        return (List<ComprobanteAdicional>)(Object) guiaRemision.getDatosAdicionales();
    }

    private void listenerFechas() {
        getCmbFechaInicio().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validarFechaValida(getCmbFechaInicio());
            }
        });
        
        getCmbFechaFin().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validarFechaValida(getCmbFechaFin());
            }
        });
    }
    
    private void  validarFechaValida(JXDatePicker cmbFecha)
    {
        try {
            java.sql.Date fechaSeleccionada=new java.sql.Date(cmbFecha.getDate().getTime());
            java.sql.Date fechaHoy=UtilidadesFecha.getFechaHoy();
            System.out.println(fechaSeleccionada.compareTo(fechaHoy));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            
            if(sdf.parse(sdf.format(fechaSeleccionada)).compareTo(sdf.parse(sdf.format(fechaHoy)))<0)
            {
                Boolean respuesta=DialogoCodefac.dialogoPregunta("Advertencia","Esta seguro que quiere seleccionar una fecha anterior a la fecha actual ?",DialogoCodefac.MENSAJE_ADVERTENCIA);
                if(!respuesta)
                {
                    cmbFecha.setDate(UtilidadesFecha.getFechaHoy());
                }
            }
        } catch (ParseException ex) {
            Logger.getLogger(GuiaRemisionModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public ClienteInterfaceComprobante getInterfaceComprobante() throws RemoteException {
        return new GuiaRemisionImplComprobante(this, guiaRemision);
    }

    @Override
    public ComprobanteDataInterface obtenerComprobanteData() {
        ComprobanteDataGuiaRemision comprobanteData= new ComprobanteDataGuiaRemision(this.guiaRemision);
        comprobanteData.setMapInfoAdicional(getMapAdicional(guiaRemision));
        return comprobanteData;
    }

    /**
     * [0] -> campo 0 corrresponde a la guia de remision
     * [1] -> campo corresponde valor para enviar correos a los clientes
     * [2] -> campo corresponde valor para enviar correos al transportista
     * @param parametros 
     */
    @Override
    public void postConstructorExterno(Object[] parametros) {
        this.guiaRemision=(GuiaRemision) parametros[0];
        getChkEnviarCorreoClientes().setSelected((boolean) parametros[1]);
        getChkEnviarCorreoTransportista().setSelected((boolean) parametros[2]);
        imprimirTabla();
    }

    private void listenerTablas() {        
        JPopupMenu jpopMenuItem=new JPopupMenu();
        JMenuItem itemRide= new JMenuItem("Editar cantidad");
        JMenuItem itemEliminar= new JMenuItem("Eliminar Item");
        
        itemRide.addActionListener(listenerEditarFila);
        itemEliminar.addActionListener(listenerEliminarFila);
        
        jpopMenuItem.add(itemRide);
        jpopMenuItem.add(itemEliminar);
                
        getTblGuiaRemision().setComponentPopupMenu(jpopMenuItem);        
    }
    
    ActionListener listenerEliminarFila=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            eliminarFila();
        }
    };
    
    private void eliminarFila()
    {
        int filaSeleccionada = getTblGuiaRemision().getSelectedRow();
        if (filaSeleccionada >= 0) {

            DetalleProductoGuiaRemision detalle = (DetalleProductoGuiaRemision) getTblGuiaRemision().getValueAt(filaSeleccionada, 0);
            guiaRemision.eliminarDetalleProducto(detalle);
            imprimirTabla();
        }
    }
    
    
    ActionListener listenerEditarFila=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            int filaSeleccionada=getTblGuiaRemision().getSelectedRow();
                if(filaSeleccionada>=0)
                {
                    DetalleProductoGuiaRemision detalle= (DetalleProductoGuiaRemision) getTblGuiaRemision().getValueAt(filaSeleccionada,0);
                    String cantidadTxt = JOptionPane.showInputDialog(null, "Ingrese la cantidad: ");
                    if(cantidadTxt!=null)
                    {
                        try
                        {
                            Integer nuevaCantidad=Integer.parseInt(cantidadTxt);
                            detalle.setCantidad(nuevaCantidad);
                            imprimirTabla();
                        }
                        catch(Exception ex)
                        {
                            ex.printStackTrace();
                        }
                    }
                    
                }
        }
    };
    
    
}
