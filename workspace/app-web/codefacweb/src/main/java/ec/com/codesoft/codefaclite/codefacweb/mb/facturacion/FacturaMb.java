/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.mb.facturacion;

import ec.com.codesoft.codefaclite.codefacweb.core.GeneralAbstractMb;
import ec.com.codesoft.codefaclite.codefacweb.core.SessionMb;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ProformaBusqueda;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Carlos
 */
@ManagedBean
@ViewScoped
public class FacturaMb extends GeneralAbstractMb implements Serializable {

    private Factura factura;

    private FacturaDetalle facturaDetalle;

    private List<DocumentoEnum> documentos;
    private List<PuntoEmision> puntosEmision;

    private Producto productoSeleccionado;
    private DocumentoEnum documentoSeleccionado;
    private PuntoEmision puntoEmisionSeleccionado;
    /**
     * TODO:Por el momento seteo con una variable adicional de la fecha porque
     * en el modelo esta con sql y fuciona correctamente para las consultas pero
     * cuando hago ese cambio en el modelo tengo problemas con otras
     * funcionalidades
     */
    private java.util.Date fechaEmision;

    @ManagedProperty(value = "#{sessionMb}")
    private SessionMb sessionMb;
    
    
    @PostConstruct
    public void init() {
        factura = new Factura();
        factura.setCliente(new Persona());//Esto solo hago para evitar advertencias
        productoSeleccionado = new Producto();
        facturaDetalle = new FacturaDetalle();

        fechaEmision = UtilidadesFecha.getFechaHoy();
        documentos = new ArrayList<DocumentoEnum>();
        documentos.add(DocumentoEnum.PROFORMA);
        cargarDatosLista();
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


    @Override
    public void nuevo() throws ExcepcionCodefacLite, UnsupportedOperationException {
         init(); //Llamo a este metodo para que se seteen en blanco las variables
        System.err.println("Ejecutando metodo de nuevo");
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, UnsupportedOperationException {
        
    }

    @Override
    public void editar() throws ExcepcionCodefacLite, UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void imprimir() throws ExcepcionCodefacLite, UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite, UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buscar() throws ExcepcionCodefacLite, UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarBusqueda(Object obj) throws ExcepcionCodefacLite, UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String titulo() throws ExcepcionCodefacLite, UnsupportedOperationException {
        return "factura";
    }

    @Override
    public InterfaceModelFind obtenerDialogoBusqueda() throws ExcepcionCodefacLite {
        return new ProformaBusqueda();
    }

    public SessionMb getSessionMb() {
        return sessionMb;
    }

    public void setSessionMb(SessionMb sessionMb) {
        this.sessionMb = sessionMb;
    }

}
