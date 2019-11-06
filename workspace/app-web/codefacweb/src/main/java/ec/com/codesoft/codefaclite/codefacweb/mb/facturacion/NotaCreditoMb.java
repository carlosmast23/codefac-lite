/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.mb.facturacion;

import ec.com.codesoft.codefaclite.codefacweb.mb.test.*;
import ec.com.codesoft.codefaclite.codefacweb.core.GeneralAbstractMb;
import ec.com.codesoft.codefaclite.codefacweb.mb.utilidades.MensajeMb;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ClienteEstablecimientoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.EjemploDialogo;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.EmpleadoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ProformaBusqueda;
import ec.com.codesoft.codefaclite.controlador.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.PrimeFaces;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfacesPropertisFindWeb;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmisionUsuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Robert
 */
@ManagedBean
@ViewScoped
public class NotaCreditoMb  extends GeneralAbstractMb implements Serializable {

    private String primerNumerosSecuencial;
    private PuntoEmision puntoEmisionSeleccionado;
    private List<PuntoEmision> puntosEmision;  
    
    @PostConstruct
    public void init()
    {
        cargarDatosCombos();
        primerNumerosSecuencial = "001";
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        return "Nota de Crédito";
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public InterfaceModelFind obtenerDialogoBusqueda() throws ExcepcionCodefacLite {
        return new ClienteEstablecimientoBusquedaDialogo(sessionMb.getSession().getEmpresa());
    }
    
    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        
    }
    
    //Funciones
    private void cargarDatosCombos()
    {
        try {
            List<PuntoEmisionUsuario> puntosEmisionUsuario = ServiceFactory.getFactory().getPuntoEmisionUsuarioServiceIf().obtenerActivoPorUsuario(sessionMb.getSession().getUsuario(), sessionMb.getSession().getSucursal());
            List<PuntoEmision> puntosEmision = new ArrayList<PuntoEmision>();
            for (PuntoEmisionUsuario puntoEmisionUsuario : puntosEmisionUsuario) {
                puntosEmision.add(puntoEmisionUsuario.getPuntoEmision());
            }
            this.puntosEmision = puntosEmision;
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(NotaCreditoMb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(NotaCreditoMb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Getters and setters

    public String getPrimerNumerosSecuencial() {
        return primerNumerosSecuencial;
    }

    public void setPrimerNumerosSecuencial(String primerNumerosSecuencial) {
        this.primerNumerosSecuencial = primerNumerosSecuencial;
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
    
}
