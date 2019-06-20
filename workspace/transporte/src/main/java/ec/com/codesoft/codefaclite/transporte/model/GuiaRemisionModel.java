/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.transporte.model;

import com.healthmarketscience.rmiio.RemoteInputStreamClient;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ClienteFacturacionBusqueda;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.FacturaBusqueda;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.GuiaRemisionBusqueda;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.TransportistaBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.componentes.ComponenteDatosComprobanteElectronicosInterface;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.controlador.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.controlador.utilidades.ComprobanteElectronicoComponente;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ObserverUpdateInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.enumerador.OrientacionReporteEnum;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.report.ReporteCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfazComunicacionPanel;
import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteEnum;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.guiaRetencion.DetalleGuiaRemisionComprobante;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.ComprobanteDataGuiaRemision;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.ComprobanteDataNotaCredito;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaAdicional;
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
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.FormatoHojaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.OperadorNegocioEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import static ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum.INVENTARIO;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.VentanaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ComprobanteServiceIf;
import ec.com.codesoft.codefaclite.transporte.callback.GuiaRemisionImplComprobante;
import ec.com.codesoft.codefaclite.transporte.data.ComprobanteGuiaTransporteData;
import ec.com.codesoft.codefaclite.transporte.data.ComprobanteGuiaTransporteDetalleData;
import ec.com.codesoft.codefaclite.transporte.panel.GuiaRemisionPanel;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.formato.ComprobantesUtilidades;
import ec.com.codesoft.codefaclite.utilidades.rmi.UtilidadesRmi;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesSwingX;
import es.mityc.firmaJava.libreria.utilidades.Utilidades;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.collections4.map.HashedMap;
import org.eclipse.persistence.sessions.factories.SessionFactory;
import org.jdesktop.swingx.JXDatePicker;

/**
 *
 * @author Carlos
 */
public class GuiaRemisionModel extends GuiaRemisionPanel implements ComponenteDatosComprobanteElectronicosInterface{
    
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
            DialogoCodefac.mensaje("Error Validación","Porfavor ingrese un transportista",DialogoCodefac.MENSAJE_INCORRECTO);
            return false;
        }
        
        if(guiaRemision.getDestinatarios()==null || guiaRemision.getDestinatarios().size()==0)
        {
            DialogoCodefac.mensaje("Error Validación","Porfavor ingrese detalles a la guía de remisión",DialogoCodefac.MENSAJE_INCORRECTO);
            return false;
        }
                
        if(guiaRemision.getDireccionPartida()==null || guiaRemision.getDireccionPartida().isEmpty())
        {
            DialogoCodefac.mensaje("Error Validación","Porfavor ingrese la dirección de partida",DialogoCodefac.MENSAJE_INCORRECTO);
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
            
            ComprobanteDataGuiaRemision comprobanteData = new ComprobanteDataGuiaRemision(this.guiaRemision);
            comprobanteData.setMapInfoAdicional(getMapAdicional(guiaRemision));
            //comprobanteData.setMapInfoAdicional(new HashMap<String, String>());
            
            GuiaRemisionImplComprobante gic=new GuiaRemisionImplComprobante(this, guiaRemision);            
            ComprobanteServiceIf comprobanteServiceIf = ServiceFactory.getFactory().getComprobanteServiceIf();
            comprobanteServiceIf.procesarComprobante(comprobanteData, guiaRemision, session.getUsuario(), gic);
            
            DialogoCodefac.mensaje("Correcto","Los datos de la guia de remisión fueron grabados correctamente", DialogoCodefac.MENSAJE_CORRECTO);
        } catch (ServicioCodefacException ex) {
            DialogoCodefac.mensaje("Error","Error al grabar los datos",DialogoCodefac.MENSAJE_INCORRECTO);
            Logger.getLogger(GuiaRemisionModel.class.getName()).log(Level.SEVERE, null, ex);
            throw new ExcepcionCodefacLite(ex.getMessage());
        }
        
        
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite, RemoteException {
        ComprobanteElectronicoComponente.eliminarComprobante(this, guiaRemision,null);
    }

    @Override
    public void imprimir() throws ExcepcionCodefacLite, RemoteException {
        if (this.guiaRemision != null && estadoFormulario.equals(ESTADO_EDITAR)) {
            try {
                String claveAcceso = this.guiaRemision.getClaveAcceso();
                byte[] byteReporte= ServiceFactory.getFactory().getComprobanteServiceIf().getReporteComprobante(claveAcceso);
                JasperPrint jasperPrint=(JasperPrint) UtilidadesRmi.deserializar(byteReporte);
                panelPadre.crearReportePantalla(jasperPrint, guiaRemision.getPreimpreso());
            } catch (RemoteException ex) {
                Logger.getLogger(GuiaRemisionModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(GuiaRemisionModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(GuiaRemisionModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void actualizar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void limpiar() {
        getLblRuc().setText(session.getEmpresa().getIdentificacion());
        //getLblTelefonos().setText(session.getSucursal().getTelefono());
        getLblNombreComercial().setText(session.getEmpresa().getNombreLegal());
        //getLblDireccion().setText(session.getSucursal().getDirecccion());
        getLblCantidadProductos().setText("0");
        ComprobanteElectronicoComponente.cargarSecuencial(ComprobanteEnum.GUIA_REMISION,session.getSucursal(), getCmbPuntoEmision(), getLblEstablecimiento(), getLblSecuencial());
        
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
    public BuscarDialogoModel obtenerDialogoBusqueda() {
        GuiaRemisionBusqueda busqueda=new GuiaRemisionBusqueda();
        BuscarDialogoModel buscarModel=new BuscarDialogoModel(busqueda);
        return buscarModel;
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
                            Map<String, Object> mapParametros = new HashedMap<String, Object>();
                            mapParametros.put("identificacion", identificacion);
                            List<PersonaEstablecimiento> resultados=ServiceFactory.getFactory().getPersonaEstablecimientoServiceIf().obtenerPorMap(mapParametros); //Todo crear mejor un metodo que ya obtener filtrado los datos
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
        ClienteFacturacionBusqueda clienteBusquedaDialogo = new ClienteFacturacionBusqueda();
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
        
        return destinatario;
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
                            puntoEmision.getSucursal().getCodigoSucursal()+"",
                            puntoEmision.getPuntoEmision()+"",
                            ComprobanteEntity.ComprobanteEnumEstado.AUTORIZADO);
                    
                    if(factura==null)
                    {
                        DialogoCodefac.mensaje(MensajeCodefacSistema.Consultas.NO_EXISTE_DATO_BUSCAR);
                        //TODO: IMPLEMENTAR MENSAJE
                    }
                    else
                    {
                        //factura.getSucursal();
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
                TransportistaBusquedaDialogo transportistaBusquedaDialogo = new TransportistaBusquedaDialogo();
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
        
        getBtnBuscarFactura().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FacturaBusqueda facturaBusqueda =null;
                
                if(destinatarioEstablecimiento!=null)
                    facturaBusqueda = new FacturaBusqueda(destinatarioEstablecimiento);
                else
                    facturaBusqueda = new FacturaBusqueda(destinatario);
                    
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
        if (validarDestinarioGuiaRemision(destinatarioGuiaRemision)) {
            guiaRemision.addDestinario(destinatarioGuiaRemision);
            cargarDestinatariosAgregados();
            imprimirTabla();
        }
    }
    
    private boolean validarDestinarioGuiaRemision(DestinatarioGuiaRemision destinatarioGuiaRemision)
    {
        if(destinatarioGuiaRemision.getMotivoTranslado().isEmpty())
        {
            DialogoCodefac.mensaje("Error Validación","Porfavor ingrese un motivo de translado",DialogoCodefac.MENSAJE_INCORRECTO);
            return false;
        }
        
        if (destinatarioGuiaRemision.getCodigoEstablecimiento().isEmpty()) {
            DialogoCodefac.mensaje("Error Validación", "Porfavor ingrese un código de establecimiento", DialogoCodefac.MENSAJE_INCORRECTO);
            return false;
        }
        
        return true;
    }
    
    private void imprimirTabla()
    {
        
        String[] titulos={"","Factura","FechaFact","Destinatario","Código Producto","Descripción","Cantidad"};
        
        DefaultTableModel modeloTabla=UtilidadesTablas.crearModeloTabla(titulos,
        new Class[]{DetalleProductoGuiaRemision.class,
        String.class,
        String.class,
        String.class,
        String.class,
        String.class,
        String.class});


        if(guiaRemision!=null && guiaRemision.getDestinatarios()!=null)
        {
            for (DestinatarioGuiaRemision destinatarios : guiaRemision.getDestinatarios()) {
                for (DetalleProductoGuiaRemision detalle : destinatarios.getDetallesProductos()) {
                    modeloTabla.addRow(new Object[]{
                        detalle,
                        detalle.getDestinatario().getPreimpreso(),
                        detalle.getDestinatario().getFechaEmision().toString(),
                        detalle.getDestinatario().getDestinatorio().getNombresCompletos(),
                        detalle.getCodigoInterno(),
                        detalle.getDescripcion(),
                        detalle.getCantidad(),
                    });

                }            
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

        guiaRemision.setDireccionPartida(getTxtDireccionPartida().getText());
        guiaRemision.setRise("");
        guiaRemision.setFechaIniciaTransporte(new java.sql.Date(getCmbFechaInicio().getDate().getTime()));
        guiaRemision.setFechaEmision(new java.sql.Date(getCmbFechaInicio().getDate().getTime())); //Esto esta variable porque necesito para volver a generar la clave de acceso
        guiaRemision.setFechaFinTransporte(new java.sql.Date(getCmbFechaFin().getDate().getTime()));
        PuntoEmision puntoEmisionSeleccionado= obtenerPuntoEmisionSeleccionado();
        guiaRemision.setPuntoEstablecimiento(puntoEmisionSeleccionado.getSucursal().getCodigoSucursal().toString());
        guiaRemision.setPuntoEmision(puntoEmisionSeleccionado.getPuntoEmision().toString());
        guiaRemision.setObligadoLlevarContabilidad(session.getEmpresa().getObligadoLlevarContabilidad());
        guiaRemision.setEmpresa(session.getEmpresa());
        
        guiaRemision.setDireccionEstablecimiento(session.getSucursal().getDirecccion());
        guiaRemision.setDireccionMatriz(session.getMatriz().getDirecccion());
        
        /**
         * Agregar los correos de los destinatarios
         */
        if(guiaRemision.getDestinatarios()!=null)
        {
            for (DestinatarioGuiaRemision destinatario : guiaRemision.getDestinatarios()) {
                guiaRemision.addDatosAdicionalCorreo(destinatario.getDestinatorio().getCorreoElectronico(), ComprobanteAdicional.Tipo.TIPO_CORREO, ComprobanteAdicional.CampoDefectoEnum.CORREO);
            }
        }
        
        //Agregado correo del transportista
        //guiaRemision.addDatosAdicionalCorreo(guiaRemision.getTransportista().getCorreoElectronico(), ComprobanteAdicional.Tipo.TIPO_CORREO, ComprobanteAdicional.CampoDefectoEnum.CORREO);
  
        
    }
    


    private void listenerCombos() {
        getCmbPuntoEmision().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ComprobanteElectronicoComponente.cargarSecuencial(ComprobanteEnum.GUIA_REMISION,session.getSucursal(), getCmbPuntoEmision(), getLblEstablecimiento(), getLblSecuencial());
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
    
    
}
