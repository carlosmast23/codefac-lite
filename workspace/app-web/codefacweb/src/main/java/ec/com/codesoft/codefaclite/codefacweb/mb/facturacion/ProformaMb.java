/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.mb.facturacion;

import ec.com.codesoft.codefaclite.codefacweb.core.GeneralAbstractMb;
import ec.com.codesoft.codefaclite.codefacweb.core.SessionMb;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ClienteFacturacionBusqueda;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.FacturaBusqueda;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ProductoBusquedaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfaceModelFind;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PersonaEstablecimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.FacturacionServiceIf;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import java.io.Serializable;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
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
    private Producto productoSeleccionado;
    private FacturaDetalle facturaDetalle;

    @ManagedProperty(value = "#{sessionMb}")
    private SessionMb sessionMb;

    @PostConstruct
    public void init() {
        factura = new Factura();
        factura.setFechaEmision(UtilidadesFecha.getFechaHoy());
    }

    @Override
    public void grabar() {
        try {
            validar();
            setearDatosAdicionales();
            
            FacturacionServiceIf servicio = ServiceFactory.getFactory().getFacturacionServiceIf();
            servicio.grabar(factura);
            System.out.println("grabado correctamente");
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ProformaMb.class.getName()).log(Level.SEVERE, null, ex);
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        
    }

}
