/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.mb.crm;

import ec.com.codesoft.codefaclite.codefacweb.core.GeneralAbstractMb;
import ec.com.codesoft.codefaclite.codefacweb.mb.utilidades.MensajeMb;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ClienteBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ClienteEstablecimientoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.controlador.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.crm.model.ClienteModel;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Nacionalidad;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PersonaEstablecimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriFormaPago;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EstadoFormEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.OperadorNegocioEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.VentanaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PersonaServiceIf;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;

/**
 *
 * @author Carlos
 */
@ManagedBean
@ViewScoped
public class ClienteMb extends GeneralAbstractMb implements Serializable {

    private Persona cliente;
    //private Persona.TipoIdentificacionEnum[] identificacionesEnumList;
    private List<Nacionalidad> nacionalidadesList;
    private GeneralEnumEstado[] estadosEnumList;
    private String tiposClientes[];
    private OperadorNegocioEnum operadoresNegocio[];
    private List<SriFormaPago> sriFormaPagoList;
    

    //private Persona.TipoIdentificacionEnum identificacionSeleccionada;
    private Nacionalidad nacionalidadSeleccionada;
    private GeneralEnumEstado estadoSeleccionada;
    private OperadorNegocioEnum operadorNegocioSeleccionado;
    private SriFormaPago sriFormaPagoSeleccionada;

    private Boolean identificacionPasaporte;
            
    @PostConstruct
    private void init() {
        cliente = new Persona();
        cargarListas();
        cargarDatosDefecto();
        
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite, UnsupportedOperationException {
        init();
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, UnsupportedOperationException {
        setearDatos();
        if(validarDatosVista())
        {
            try {
                ServiceFactory.getFactory().getPersonaServiceIf().grabar(cliente);
                mostrarDialogoResultado(MensajeCodefacSistema.AccionesFormulario.GUARDADO);
                
            } catch (ServicioCodefacException ex) {
                MensajeMb.mostrarMensajeDialogo("Error",ex.getMessage(),FacesMessage.SEVERITY_ERROR);
                Logger.getLogger(ClienteMb.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RemoteException ex) {
                Logger.getLogger(ClienteMb.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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
        cliente = ((PersonaEstablecimiento) (obj)).getPersona();
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

   /* private void setearDatosAdicionales() {
        this.cliente.setTipoIdentificacionEnum(identificacionSeleccionada);
        this.cliente.setNacionalidad(nacionalidadSeleccionada);
        this.cliente.setEstadoEnum(estadoSeleccionada);
        ClienteModel.crearEstablecimiento(EstadoFormEnum.GRABAR, cliente, interfaceVista);
    }*/



    public List<Nacionalidad> getNacionalidadesList() {
        return nacionalidadesList;
    }

    public void setNacionalidadesList(List<Nacionalidad> nacionalidadesList) {
        this.nacionalidadesList = nacionalidadesList;
    }

    public GeneralEnumEstado[] getEstadosEnumList() {
        return estadosEnumList;
    }

    public void setEstadosEnumList(GeneralEnumEstado[] estadosEnumList) {
        this.estadosEnumList = estadosEnumList;
    }


    public Nacionalidad getNacionalidadSeleccionada() {
        return nacionalidadSeleccionada;
    }

    public void setNacionalidadSeleccionada(Nacionalidad nacionalidadSeleccionada) {
        this.nacionalidadSeleccionada = nacionalidadSeleccionada;
    }

    public GeneralEnumEstado getEstadoSeleccionada() {
        return estadoSeleccionada;
    }

    public void setEstadoSeleccionada(GeneralEnumEstado estadoSeleccionada) {
        this.estadoSeleccionada = estadoSeleccionada;
    }

    private void cargarListas() {
        try {
            //identificacionesEnumList = Persona.TipoIdentificacionEnum.values();
            tiposClientes=Persona.tiposClientes;
            nacionalidadesList = ServiceFactory.getFactory().getNacionalidadServiceIf().obtenerTodos();
            estadosEnumList = GeneralEnumEstado.values();
            operadoresNegocio=OperadorNegocioEnum.values();
            sriFormaPagoList=ServiceFactory.getFactory().getSriServiceIf().obtenerFormasPagoActivo();
        } catch (RemoteException ex) {
            Logger.getLogger(ClienteMb.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void setearDatos() {
        System.out.println("---seteando datos---");
        cliente.setTipoIdentificacionEnum(obtenerTipoIdentificacion());
        cliente.setNacionalidad(nacionalidadSeleccionada);
        cliente.setEstadoEnum(estadoSeleccionada);       
        cliente.setSriFormaPago(sriFormaPagoSeleccionada);
        cliente.setTipoEnum(operadorNegocioSeleccionado);
        System.out.println("---->"+cliente.getIdentificacion());
        System.out.println("---->"+cliente.getTipoIdentificacion());
        
    }
    
    private Persona.TipoIdentificacionEnum obtenerTipoIdentificacion()
    {
        if(identificacionPasaporte)
        {
            return Persona.TipoIdentificacionEnum.PASAPORTE;
        }else
        {
            if(cliente.getIdentificacion().length()==13) //Tamanio del ruc , ver si se crea una variable global para este valor
            {
                return Persona.TipoIdentificacionEnum.RUC;
            }
            else //Caso contrario asumo que es cedula
            {
                return Persona.TipoIdentificacionEnum.CEDULA;
            }
        }
    }

    private boolean validarDatosVista() {
        return true;
    }

    public Boolean getIdentificacionPasaporte() {
        return identificacionPasaporte;
    }

    public void setIdentificacionPasaporte(Boolean identificacionPasaporte) {
        this.identificacionPasaporte = identificacionPasaporte;
    }

    public String[] getTiposClientes() {
        return tiposClientes;
    }

    public void setTiposClientes(String[] tiposClientes) {
        this.tiposClientes = tiposClientes;
    }

    public OperadorNegocioEnum[] getOperadoresNegocio() {
        return operadoresNegocio;
    }

    public void setOperadoresNegocio(OperadorNegocioEnum[] operadoresNegocio) {
        this.operadoresNegocio = operadoresNegocio;
    }

    public OperadorNegocioEnum getOperadorNegocioSeleccionado() {
        return operadorNegocioSeleccionado;
    }

    public void setOperadorNegocioSeleccionado(OperadorNegocioEnum operadorNegocioSeleccionado) {
        this.operadorNegocioSeleccionado = operadorNegocioSeleccionado;
    }

    public List<SriFormaPago> getSriFormaPagoList() {
        return sriFormaPagoList;
    }

    public void setSriFormaPagoList(List<SriFormaPago> sriFormaPagoList) {
        this.sriFormaPagoList = sriFormaPagoList;
    }

    public SriFormaPago getSriFormaPagoSeleccionada() {
        return sriFormaPagoSeleccionada;
    }

    public void setSriFormaPagoSeleccionada(SriFormaPago sriFormaPagoSeleccionada) {
        this.sriFormaPagoSeleccionada = sriFormaPagoSeleccionada;
    }

    private void cargarDatosDefecto() {
        cliente.setDiasCreditoCliente(0);
        
        try {
            nacionalidadSeleccionada=ServiceFactory.getFactory().getNacionalidadServiceIf().obtenerDefaultEcuador();
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ClienteMb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(ClienteMb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void eventoNombreKeyUp() {
        System.out.println("evento razon social");
        System.out.println(cliente.getNombres() +" "+cliente.getApellidos());
        cliente.contruirRazonSocialConNombreYApellidos();
        System.out.println(cliente.getRazonSocial());
    }
    
    

    
    
}
