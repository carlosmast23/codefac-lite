/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.mb.facturacion;

import ec.com.codesoft.codefaclite.codefacweb.core.DialogoWeb;
import ec.com.codesoft.codefaclite.codefacweb.mb.test.*;
import ec.com.codesoft.codefaclite.codefacweb.core.GeneralAbstractMb;
import ec.com.codesoft.codefaclite.codefacweb.mb.utilidades.MensajeMb;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ClienteEstablecimientoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ClienteFacturacionBusqueda;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.EjemploDialogo;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.EmpleadoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ProformaBusqueda;
import ec.com.codesoft.codefaclite.controlador.comprobante.reporte.ControladorReporteFactura;
import ec.com.codesoft.codefaclite.controlador.comprobante.reporte.ReporteFacturaData;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.excel.Excel;
import ec.com.codesoft.codefaclite.controlador.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.controlador.model.ReporteDialogListener;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.PrimeFaces;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfacesPropertisFindWeb;
import ec.com.codesoft.codefaclite.corecodefaclite.enumerador.OrientacionReporteEnum;
import ec.com.codesoft.codefaclite.corecodefaclite.report.ReporteCodefac;
import ec.com.codesoft.codefaclite.facturacion.model.FacturaReporteModel;
import ec.com.codesoft.codefaclite.facturacion.reportdata.ComprobanteVentaData;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PersonaEstablecimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmisionUsuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.FormatoHojaEnum;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Carlos
 */
@ManagedBean
@ViewScoped
public class FacturaReporteMb  extends GeneralAbstractMb implements DialogoWeb<Factura>, Serializable {

    private String texto;
    private List<DocumentoEnum> documentos;
    private ComprobanteEntity.ComprobanteEnumEstado[] comprobanteEstados;
    private List<PuntoEmision> puntosEmision;
    private FacturaReporteModel.TipoReporteEnum[] tiposReporte;
    
    private DocumentoEnum documentoSeleccionado;
    private ComprobanteEntity.ComprobanteEnumEstado comprobanteEstadoSeleccionado;
    private PuntoEmision puntoEmisionSeleccionado;
    private FacturaReporteModel.TipoReporteEnum tipoReporteSeleccionado;
    private java.util.Date fechaInicial;
    private java.util.Date fechaFinal;
    private Boolean clienteCheckTodos;
    private Boolean puntoEmisionCheckTodos;
    private Boolean notaCreditoCheck;
    private Boolean notaDebitoCheck;
    private Boolean reporteAgrupadoReferidoCheck;
    private Boolean filtrarReferidos;
    private PersonaEstablecimiento persona;
    private Persona cliente;
    private Persona referido;
    private ControladorReporteFactura controladorReporte;
    private List<ReporteFacturaData> data;
            
    @PostConstruct
    public void init()
    {
       
        try {
            valoresIniciales();
            cargarCombos();
        } catch (RemoteException ex) {
            Logger.getLogger(FacturaReporteMb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(FacturaReporteMb.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
    
    @Override
    public void grabar() throws ExcepcionCodefacLite {
        System.out.println("grabar creado desde ejemplo");
        MensajeMb.mostrarMensajeDialogo(MensajeCodefacSistema.AccionesFormulario.GUARDADO);
    }

    @Override
    public void editar() throws ExcepcionCodefacLite {
        System.out.println("editar creado desde ejemplo");
        MensajeMb.mostrarMensajeDialogo(MensajeCodefacSistema.AccionesFormulario.EDITADO);
    }

    @Override
    public void imprimir() throws ExcepcionCodefacLite {
        imprimirReporte();
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite {
        
    }

    @Override
    public void buscar() throws ExcepcionCodefacLite {
        
    }

    @Override
    public void cargarBusqueda(Object obj) throws ExcepcionCodefacLite {
        
    }

    @Override
    public String titulo() throws ExcepcionCodefacLite,UnsupportedOperationException{
        return "Reporte factura";
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public InterfaceModelFind obtenerDialogoBusqueda() throws ExcepcionCodefacLite {
        return new ClienteEstablecimientoBusquedaDialogo(sessionMb.getSession().getEmpresa());
    }
    
    
    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        
    }

    public Factura getResultDialogo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * Funciones
     */
    public void cargarCombos() throws RemoteException, ServicioCodefacException
    {
        //Estado de comprobantes
        this.comprobanteEstados = ComprobanteEntity.ComprobanteEnumEstado.values();
        
        //Tipos de documentos
        this.documentos = new ArrayList<DocumentoEnum>();
        this.documentos.add(DocumentoEnum.FACTURA);
        this.documentos.add(DocumentoEnum.NOTA_VENTA_INTERNA);
        this.documentos.add(DocumentoEnum.NOTA_CREDITO);
 
        //Punto de emision
        List<PuntoEmisionUsuario> puntosEmisionUsuario=ServiceFactory.getFactory().getPuntoEmisionUsuarioServiceIf().obtenerActivoPorUsuario(sessionMb.getSession().getUsuario(),sessionMb.getSession().getSucursal());
        List<PuntoEmision> puntosEmision=new ArrayList<PuntoEmision>();
        for (PuntoEmisionUsuario puntoEmisionUsuario : puntosEmisionUsuario) {
            puntosEmision.add(puntoEmisionUsuario.getPuntoEmision());
        }
        this.puntosEmision=puntosEmision;
        
        //Tipo Reporte
        this.tiposReporte = FacturaReporteModel.TipoReporteEnum.values();
    }
    
    public void valoresIniciales(){
        filtrarReferidos = false;      
        persona = null;
        referido = null;
        cliente = null;
        //TODO: preguntar al Master si es conveniente mostrar estos campos
        ///Para el reporte de facturacion no me importa que sean visibles estos campos del referido
        //getLblReferido().setVisible(false);
        //getTxtReferido().setVisible(false);
        //getBtnBuscarReferido().setVisible(false);
        //getChkTodosReferidos().setVisible(false);
        //getChkReporteAgrupadoReferido().setVisible(false);
    }
    
    public void abrirDialogoBuscarCliente()
    {
        ClienteFacturacionBusqueda clienteBusquedaDialogo = new ClienteFacturacionBusqueda(sessionMb.getSession().getEmpresa());   
        abrirDialogoBusqueda(clienteBusquedaDialogo);
    }
    
    public void abrirDialogoBusqueda(InterfaceModelFind modeloBusqueda) 
    {
        //find();
        System.out.println("Abriendo dialogo busqueda");

        //Establecer objeto de la clase que tiene la implemetacion del dialogo de busqueda que necesito para construir el dialogo web
        //TODO: Solucion temporal porque es una gasto innesario de memoria , buscar otra forma
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        sessionMap.put("busquedaClase", modeloBusqueda);

        //Esstablecer porpiedades que se van a enviar al dialogo en map
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("resizable", false);
        options.put("draggable", false);
        options.put("modal", true);
        //options.put("busquedaClase", new EmpleadoBusquedaDialogo() ); //TODO: Mando por defecto un dialogo por defecto
        String nombreDialogoBusqueda = "dialogo_busqueda";
        //PrimeFaces.current().dialog()
        PrimeFaces.current().dialog().openDynamic(nombreDialogoBusqueda, options, null);           
    }
    
    public void seleccionarCliente(SelectEvent event)
    {
        PersonaEstablecimiento clienteOficina = (PersonaEstablecimiento) event.getObject();
        cargarDatosCliente(clienteOficina);
        //cargarDatosAdicionalesCliente();
    }
    
    public void cargarDatosCliente(PersonaEstablecimiento establecimiento) {
        if (establecimiento != null) {
            persona = establecimiento;
            cliente = persona.getPersona();
        }
    }
    
    public void consultar(){
        java.sql.Date fechaInicio=null;
        java.sql.Date fechaFin =null;

            ComprobanteEntity.ComprobanteEnumEstado estadoFactura = (ComprobanteEntity.ComprobanteEnumEstado)getComprobanteEstadoSeleccionado();
            String estadoStr = estadoFactura.getEstado();
            
            if (fechaInicial != null) {
                fechaInicio = new java.sql.Date(fechaInicial.getTime());
            }
            if (fechaFinal != null) {
                fechaFin = new java.sql.Date(fechaFinal.getTime());
            }


            DocumentoEnum documentoConsultaEnum = (DocumentoEnum) documentoSeleccionado;
            
            //Seteando datos para el controlador         
            controladorReporte =crearControlador();
            controladorReporte.setPersona(persona);
            controladorReporte.setFechaInicio(fechaInicio);
            controladorReporte.setFechaFin(fechaFin);
            controladorReporte.setEstadoFactura(estadoFactura);
            controladorReporte.setFiltrarReferidos(filtrarReferidos);
            controladorReporte.setReferido(referido);
            controladorReporte.setReporteAgrupado(reporteAgrupadoReferidoCheck);
            controladorReporte.setAfectarNotaCredito(notaCreditoCheck);
            controladorReporte.setDocumentoConsultaEnum(documentoConsultaEnum);
            PuntoEmision puntoEmisionReporte = ((puntoEmisionCheckTodos)?null:puntoEmisionSeleccionado);
            controladorReporte.setPuntoEmision(puntoEmisionReporte);
            
            controladorReporte.generarReporte();
            data=controladorReporte.getData();
            //imprimirTabla();      
                
    }
    
    public ControladorReporteFactura crearControlador()
    {
        return new ControladorReporteFactura(sessionMb.getSession().getEmpresa());
    }
    
    public void imprimirReporte()
    {
        String titulo = "";
        InputStream path = null;
        FacturaReporteModel.TipoReporteEnum tipoReporteEnum=(FacturaReporteModel.TipoReporteEnum) tipoReporteSeleccionado;
        switch(tipoReporteEnum)
        {
            case AGRUPADO_POR_CATEGORIA:
                titulo = "Reporte Ventas Agrupado por Punto de Emisión";
                path =  RecursoCodefac.JASPER_FACTURACION.getResourceInputStream("reporte_ventas_punto_emision.jrxml");
                imprimirReporteFactura(titulo, path);
                break;  

            case NORMAL:
                titulo = "Reporte " + documentoSeleccionado.getNombre();
                path =  RecursoCodefac.JASPER_FACTURACION.getResourceInputStream("reporte_documentos.jrxml");
                imprimirReporteFactura(titulo, path);
                break;
        }

    }
    
    public void imprimirReporteFactura(String titulo, InputStream path){
        //ControladorReporteFactura.TotalSumatoria total=controladorReporte.totalSinNotaCredito();
        
        try {
            System.out.println("Imprimir ...");
            //no cacho List<ComprobanteVentaData> dataReporte = getDetalleDataReporte(factura);
            //no cacho Map<String, Object> mapParametros = getMapParametrosReporte(factura); 
            JasperPrint jasperPrint = ReporteCodefac.construirReporte(path, controladorReporte.mapParametrosReportePdf(), data, sessionMb.getSession(), titulo, OrientacionReporteEnum.HORIZONTAL, FormatoHojaEnum.A4);
            //JasperPrint jasperPrint = JasperFillManager.fillReport(path, mapParametros, new JRBeanCollectionDataSource(dataReporte));
            
            Map<String, Object>mapParametros = ReporteCodefac.mapReportePlantilla(OrientacionReporteEnum.HORIZONTAL, FormatoHojaEnum.A4, sessionMb.getSession());
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            //HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            net.sf.jasperreports.engine.JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
            //byte[] bytes = JasperRunManager.runReportToPdf(path, mapParametros, new JRBeanCollectionDataSource(dataReporte));
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            //response.setHeader("Content-disposition", "inline; filename=proforma");
            response.setContentType("application/pdf");
            response.setContentLength(baos.size());

            ServletOutputStream outStream = response.getOutputStream();
            baos.writeTo(outStream);
            //outStream.write(baos, 0, baos.size());

            outStream.flush();
            outStream.close();

            FacesContext.getCurrentInstance().responseComplete();

        } catch (JRException ex) {
            ex.printStackTrace();
            Logger.getLogger(ProformaMb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            ex.printStackTrace();
            Logger.getLogger(ProformaMb.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
    /**
     * Getters and Setters 
     * @return 
     */
    
    public ComprobanteEntity.ComprobanteEnumEstado[] getComprobanteEstados() {
        return comprobanteEstados;
    }

    public void setComprobanteEnumEstados(ComprobanteEntity.ComprobanteEnumEstado[] comprobanteEstados) {
        this.comprobanteEstados = comprobanteEstados;
    }

    public List<DocumentoEnum> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<DocumentoEnum> documentos) {
        this.documentos = documentos;
    }

    public List<PuntoEmision> getPuntosEmision() {
        return puntosEmision;
    }

    public void setPuntosEmision(List<PuntoEmision> puntosEmision) {
        this.puntosEmision = puntosEmision;
    }

    public ComprobanteEntity.ComprobanteEnumEstado getComprobanteEstadoSeleccionado() {
        return comprobanteEstadoSeleccionado;
    }

    public void setComprobanteSeleccionado(ComprobanteEntity.ComprobanteEnumEstado comprobanteSeleccionado) {
        this.comprobanteEstadoSeleccionado = comprobanteSeleccionado;
    }

    public DocumentoEnum getDocumentoSeleccionado() {
        return documentoSeleccionado;
    }

    public void setDocumentoSeleccionado(DocumentoEnum documentoSeleccionado) {
        this.documentoSeleccionado = documentoSeleccionado;
    }

    public PuntoEmision getPuntoEmisionSeleccionado() {
        return puntoEmisionSeleccionado;
    }

    public void setPuntoEmisionSeleccionado(PuntoEmision puntoEmisionSeleccionado) {
        this.puntoEmisionSeleccionado = puntoEmisionSeleccionado;
    }

    public FacturaReporteModel.TipoReporteEnum[] getTiposReporte() {
        return tiposReporte;
    }

    public void setTiposReporte(FacturaReporteModel.TipoReporteEnum[] tiposReporte) {
        this.tiposReporte = tiposReporte;
    }

    public FacturaReporteModel.TipoReporteEnum getTipoReporteSeleccionado() {
        return tipoReporteSeleccionado;
    }

    public void setTipoReporteSeleccionado(FacturaReporteModel.TipoReporteEnum tipoReporteSeleccionado) {
        this.tipoReporteSeleccionado = tipoReporteSeleccionado;
    }

    public Date getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public Boolean getClienteCheckTodos() {
        return clienteCheckTodos;
    }

    public void setClienteCheckTodos(Boolean clienteCheckTodos) {
        this.clienteCheckTodos = clienteCheckTodos;
    }

    public Boolean getPuntoEmisionCheckTodos() {
        return puntoEmisionCheckTodos;
    }

    public void setPuntoEmisionCheckTodos(Boolean puntoEmisionCheckTodos) {
        this.puntoEmisionCheckTodos = puntoEmisionCheckTodos;
    }

    public Boolean getNotaCreditoCheck() {
        return notaCreditoCheck;
    }

    public void setNotaCreditoCheck(Boolean notaCreditoCheck) {
        this.notaCreditoCheck = notaCreditoCheck;
    }

    public Boolean getNotaDebitoCheck() {
        return notaDebitoCheck;
    }

    public void setNotaDebitoCheck(Boolean notaDebitoCheck) {
        this.notaDebitoCheck = notaDebitoCheck;
    }

    public Persona getCliente() {
        return cliente;
    }

    public void setCliente(Persona cliente) {
        this.cliente = cliente;
    }

    public Boolean getReporteAgrupadoReferidoCheck() {
        return reporteAgrupadoReferidoCheck;
    }

    public void setReporteAgrupadoReferidoCheck(Boolean reporteAgrupadoReferidoCheck) {
        this.reporteAgrupadoReferidoCheck = reporteAgrupadoReferidoCheck;
    }
    
    
       
}
