/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.mb.crm;

import ec.com.codesoft.codefaclite.codefacweb.core.GeneralAbstractMb;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ClienteBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ClienteEstablecimientoBusquedaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfaceModelFind;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PersonaEstablecimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.VentanaEnum;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Carlos
 */
@ManagedBean
@ViewScoped
public class ClienteMb extends GeneralAbstractMb implements Serializable {
    
    private Persona cliente;
    
    @PostConstruct
    private void init()
    {
        cliente=new Persona();
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite, UnsupportedOperationException {
        
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, UnsupportedOperationException {
        
    }

    @Override
    public void editar() throws ExcepcionCodefacLite, UnsupportedOperationException {
        
    }

    @Override
    public void imprimir() throws ExcepcionCodefacLite, UnsupportedOperationException {
        
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite, UnsupportedOperationException {
        
    }

    @Override
    public void buscar() throws ExcepcionCodefacLite, UnsupportedOperationException {
        
    }

    @Override
    public void cargarBusqueda(Object obj) throws ExcepcionCodefacLite, UnsupportedOperationException {
        cliente=((PersonaEstablecimiento) (obj)).getPersona();
    }

    @Override
    public String titulo() throws ExcepcionCodefacLite, UnsupportedOperationException {
        return "Cliente";
    }

    @Override
    public InterfaceModelFind obtenerDialogoBusqueda() throws ExcepcionCodefacLite {
       return new ClienteEstablecimientoBusquedaDialogo();
    }

    public Persona getCliente() {
        return cliente;
    }

    public void setCliente(Persona cliente) {
        this.cliente = cliente;
    }
    
    
    
}
