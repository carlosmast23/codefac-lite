/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.mb.facturacion;

import com.healthmarketscience.rmiio.RemoteInputStreamClient;
import ec.com.codesoft.codefaclite.codefacweb.core.GeneralAbstractMb;
import ec.com.codesoft.codefaclite.codefacweb.core.SessionMb;
import ec.com.codesoft.codefaclite.codefacweb.mb.utilidades.MensajeMb;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ClienteFacturacionBusqueda;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ProductoBusquedaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.enumerador.OrientacionReporteEnum;
import ec.com.codesoft.codefaclite.corecodefaclite.report.ReporteCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfaceModelFind;
import ec.com.codesoft.codefaclite.facturacion.reportdata.ComprobanteVentaData;
import ec.com.codesoft.codefaclite.facturacion.reportdata.InformacionAdicionalData;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PersonaEstablecimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.FormatoHojaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.FacturacionServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RecursosServiceIf;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Carlos
 */
@ManagedBean
@ViewScoped
public class ProformaMb extends GeneralAbstractMb implements Serializable {

    private Factura factura;

    private FacturaDetalle facturaDetalle;

    private List<DocumentoEnum> documentos;
    private List<PuntoEmision> puntosEmision;

    private Producto productoSeleccionado;
    private DocumentoEnum documentoSeleccionado;
    private PuntoEmision puntoEmisionSeleccionado;

    @ManagedProperty(value = "#{sessionMb}")
    private SessionMb sessionMb;

    @PostConstruct
    public void init() {
        factura = new Factura();
        factura.setFechaEmision(UtilidadesFecha.getFechaHoy());
        documentos = new ArrayList<DocumentoEnum>();
        documentos.add(DocumentoEnum.PROFORMA);
        cargarDatosLista();
    }

    @Override
    public void grabar() {
        try {
            System.out.println("===========>INICIANDO PROCESO GRABAR <==============");
            validar();
            setearDatosAdicionales();

            FacturacionServiceIf servicio = ServiceFactory.getFactory().getFacturacionServiceIf();
            factura=servicio.grabarProforma(factura);
            MensajeMb.mostrarMensaje("Correcto", "Proforma grabada correctamente", FacesMessage.SEVERITY_INFO);
            imprimir();
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ProformaMb.class.getName()).log(Level.SEVERE, null, ex);
            MensajeMb.mostrarMensaje("Error al grabar", ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        } catch (RemoteException ex) {
            Logger.getLogger(ProformaMb.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void eliminar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buscar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarBusqueda(Object obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public InterfaceModelFind obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void saludo() {
        System.out.println("Hola todos");
    }

    public void abrirDialogoBuscarCliente() {
        System.out.println("Abriendo dialogo init");
        ClienteFacturacionBusqueda clienteBusquedaDialogo = new ClienteFacturacionBusqueda();
        abrirDialogoBusqueda(clienteBusquedaDialogo);
        System.out.println("Abriendo dialogo fin");
    }

    public void abrirDialogoBusquedaProducto() {
        ProductoBusquedaDialogo dialogModel = new ProductoBusquedaDialogo();
        abrirDialogoBusqueda(dialogModel);
    }

    public void abrirDialogoBusqueda(InterfaceModelFind modeloBusqueda) {
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

    public void seleccionarCliente(SelectEvent event) {
        PersonaEstablecimiento clienteOficina = (PersonaEstablecimiento) event.getObject();
        cargarDatosCliente(clienteOficina);
    }

    public void seleccionarProducto(SelectEvent event) {
        productoSeleccionado = (Producto) event.getObject();
        cargarDetalleFacturaAgregar(productoSeleccionado);
    }

    private void cargarDetalleFacturaAgregar(Producto productoSeleccionado) {
        facturaDetalle = new FacturaDetalle();
        facturaDetalle.setCantidad(BigDecimal.ZERO);
        facturaDetalle.setDescripcion(productoSeleccionado.getNombre());
        facturaDetalle.setPrecioUnitario(productoSeleccionado.getValorUnitario());
        facturaDetalle.setDescuento(BigDecimal.ZERO);
        facturaDetalle.setIvaPorcentaje(sessionMb.getSession().obtenerIvaActual().intValue());

        facturaDetalle.setTipoDocumentoEnum(TipoDocumentoEnum.LIBRE);//TODO: Por el momento solo dejo como documento por defecto libre
        facturaDetalle.setReferenciaId(productoSeleccionado.getIdProducto());

    }

    public void agregarProducto() {
        //facturaDetalle.        
        facturaDetalle.calcularTotalDetalle();
        facturaDetalle.calculaIva();

        factura.addDetalle(facturaDetalle);
        factura.calcularTotalesDesdeDetalles();
    }

    public void cargarDatosCliente(PersonaEstablecimiento establecimiento) {
        if (establecimiento != null) {
            factura.setCliente(establecimiento.getPersona());
            factura.setSucursal(establecimiento);
        }
    }

    /**
     * ==========================> METODOS GET AND SET
     * <=============================
     */
    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public FacturaDetalle getFacturaDetalle() {
        return facturaDetalle;
    }

    public void setFacturaDetalle(FacturaDetalle facturaDetalle) {
        this.facturaDetalle = facturaDetalle;
    }

    public Producto getProductoSeleccionado() {
        return productoSeleccionado;
    }

    public void setProductoSeleccionado(Producto productoSeleccionado) {
        this.productoSeleccionado = productoSeleccionado;
    }

    public SessionMb getSessionMb() {
        return sessionMb;
    }

    public void setSessionMb(SessionMb sessionMb) {
        this.sessionMb = sessionMb;
    }

    private void validar() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void setearDatosAdicionales() {
        Persona.TipoIdentificacionEnum tipoIdentificacionEnum = factura.getCliente().getTipoIdentificacionEnum();
        String codigoSri = tipoIdentificacionEnum.getCodigoSriVenta();
        factura.setTipoIdentificacionCodigoSri(codigoSri); //TODO: Ver si esta variable se debe grabar en el servidor

        factura.setEmpresa(sessionMb.getSession().getEmpresa());
        factura.setFechaCreacion(UtilidadesFecha.getFechaHoy());

        factura.setCodigoDocumento(DocumentoEnum.PROFORMA.getCodigo());

        factura.setObligadoLlevarContabilidad(sessionMb.getSession().getEmpresa().getObligadoLlevarContabilidad());
        factura.setDireccionEstablecimiento(sessionMb.getSession().getSucursal().getDirecccion());
        factura.setDireccionMatriz(sessionMb.getSession().getMatriz().getDirecccion());
        factura.setPuntoEmision(puntoEmisionSeleccionado.getPuntoEmision().toString());
        factura.setPuntoEstablecimiento(sessionMb.getSession().getSucursal().getCodigoSucursal().toString());

    }

    public List<DocumentoEnum> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<DocumentoEnum> documentos) {
        this.documentos = documentos;
    }

    public DocumentoEnum getDocumentoSeleccionado() {
        return documentoSeleccionado;
    }

    public void setDocumentoSeleccionado(DocumentoEnum documentoSeleccionado) {
        this.documentoSeleccionado = documentoSeleccionado;
    }

    private void cargarDatosLista() {
        try {
            puntosEmision = ServiceFactory.getFactory().getPuntoVentaServiceIf().obtenerActivosPorSucursal(sessionMb.getSession().getSucursal());
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ProformaMb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(ProformaMb.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public List<PuntoEmision> getPuntosEmision() {
        return puntosEmision;
    }

    public void setPuntosEmision(List<PuntoEmision> puntosEmision) {
        this.puntosEmision = puntosEmision;
    }

    public PuntoEmision getPuntoEmisionSeleccionado() {
        return puntoEmisionSeleccionado;
    }

    public void setPuntoEmisionSeleccionado(PuntoEmision puntoEmisionSeleccionado) {
        this.puntoEmisionSeleccionado = puntoEmisionSeleccionado;
    }

    @Override
    public void imprimir() {
        try {
            System.out.println("Imprimir ...");
            List<ComprobanteVentaData> dataReporte = getDetalleDataReporte(factura);
            Map<String, Object> mapParametros = getMapParametrosReporte(factura);
            InputStream path = RecursoCodefac.JASPER_COMPROBANTES_ELECTRONICOS.getResourceInputStream("proforma.jrxml");
            JasperPrint jasperPrint=ReporteCodefac.construirReporte(path, mapParametros, dataReporte, sessionMb.getSession(), "Proforma", OrientacionReporteEnum.VERTICAL, FormatoHojaEnum.A4);
            //JasperPrint jasperPrint = JasperFillManager.fillReport(path, mapParametros, new JRBeanCollectionDataSource(dataReporte));

            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.addHeader("Content-disposition", "attachment; filename=reporte.pdf");
            ServletOutputStream stream = response.getOutputStream();

            JasperExportManager.exportReportToPdfStream(jasperPrint, stream);

            stream.flush();
            stream.close();
            FacesContext.getCurrentInstance().responseComplete();
            System.out.println("termino de imprimir");
            
            
            

        } catch (JRException ex) {
            ex.printStackTrace();
            Logger.getLogger(ProformaMb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            ex.printStackTrace();
            Logger.getLogger(ProformaMb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<ComprobanteVentaData> getDetalleDataReporte(Factura facturaProcesando) {
        List<ComprobanteVentaData> dataReporte = new ArrayList<ComprobanteVentaData>();

        for (FacturaDetalle detalle : facturaProcesando.getDetalles()) {

            ComprobanteVentaData data = new ComprobanteVentaData();
            data.setCantidad(detalle.getCantidad().toString());
            data.setCodigo(detalle.getId().toString());
            data.setNombre(detalle.getDescripcion().toString());
            data.setPrecioUnitario(detalle.getPrecioUnitario().toString());
            data.setTotal(detalle.getTotal().toString());

            //Datos adicionales para las proformas
            data.setDescuento(detalle.getDescuento().toString());
            data.setDescripcion(detalle.getDescripcion());

            dataReporte.add(data);
        }
        return dataReporte;
    }

    /**
     * TODO: Ver alguna forma de unir con el metodo de factura de escritorio
     * porque se repite los metodos
     *
     * @param facturaProcesando
     * @return
     */
    public Map<String, Object> getMapParametrosReporte(Factura facturaProcesando) {
        Map<String, Object> mapParametros = getMapParametrosReporteCommun(facturaProcesando); //To change body of generated methods, choose Tools | Templates.
        mapParametros.put("estado", factura.getEnumEstadoProforma().getNombre());
        //subtotal_cero
        //Datos adicionales para las proformas
        mapParametros.put("secuencial", facturaProcesando.getSecuencial().toString());
        mapParametros.put("cliente_nombres", facturaProcesando.getRazonSocial());
        mapParametros.put("cliente_identificacion", facturaProcesando.getIdentificacion());
        mapParametros.put("fecha_emision", facturaProcesando.getFechaEmision().toString());
        mapParametros.put("subtotal_cero", facturaProcesando.getSubtotalSinImpuestos().toString());
        mapParametros.put("descuento", facturaProcesando.getDescuentoImpuestos().add(facturaProcesando.getDescuentoSinImpuestos()).toString());
        mapParametros.put("iva_porcentaje", sessionMb.getSession().obtenerIvaActual().toString());
        mapParametros.put("informacionAdicionalList", obtenerDatosAdicionales());

        try {
            RecursosServiceIf service = ServiceFactory.getFactory().getRecursosServiceIf();
            InputStream inputStream = RemoteInputStreamClient.wrap(service.getResourceInputStream(RecursoCodefac.JASPER_COMPROBANTES_ELECTRONICOS, "datos_adicionalesA4.jrxml"));
            JasperReport reportDatosAdicionales = JasperCompileManager.compileReport(inputStream);
            mapParametros.put("SUBREPORT_INFO_ADICIONAL", reportDatosAdicionales);
        } catch (RemoteException ex) {
            Logger.getLogger(ProformaMb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ProformaMb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            Logger.getLogger(ProformaMb.class.getName()).log(Level.SEVERE, null, ex);
        }

        //SUBREPORT_INFO_ADICIONAL
        // mapParametros.put("estado",facturaProcesando.getEstadoEnum());
        return mapParametros;
    }

    public Map<String, Object> getMapParametrosReporteCommun(Factura facturaProcesando) {
        //map de los parametros faltantes
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("codigo", facturaProcesando.getPreimpreso());
        mapParametros.put("cedula", facturaProcesando.getIdentificacion());
        mapParametros.put("cliente", facturaProcesando.getRazonSocial());
        mapParametros.put("direccion", facturaProcesando.getDireccion());
        mapParametros.put("telefonos", facturaProcesando.getTelefono());
        mapParametros.put("fechaIngreso", facturaProcesando.getFechaEmision().toString());
        mapParametros.put("subtotal", facturaProcesando.getSubtotalImpuestos().add(facturaProcesando.getSubtotalSinImpuestos()).toString());
        mapParametros.put("iva", facturaProcesando.getIva().toString());
        mapParametros.put("total", facturaProcesando.getTotal().toString());
        mapParametros.put("autorizacion", facturaProcesando.getClaveAcceso());

        return mapParametros;

    }

    private List<InformacionAdicionalData> obtenerDatosAdicionales() {
        List<InformacionAdicionalData> datosAdicionalesData = new ArrayList<InformacionAdicionalData>();
        if (factura.getDatosAdicionales() != null) {
            for (FacturaAdicional datoAdicional : factura.getDatosAdicionales()) {
                InformacionAdicionalData data = new InformacionAdicionalData();
                data.setNombre(datoAdicional.getCampo());
                data.setValor(datoAdicional.getValor());
                datosAdicionalesData.add(data);
            }
        }
        return datosAdicionalesData;
    }

}
