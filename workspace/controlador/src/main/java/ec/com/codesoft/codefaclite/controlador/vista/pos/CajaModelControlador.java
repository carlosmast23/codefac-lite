/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vista.pos;

import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.CajaBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.controlador.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.controlador.vista.crm.ProductoModelControlador;
import ec.com.codesoft.codefaclite.controlador.vista.factura.ModelControladorAbstract;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmisionUsuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.Caja;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CajaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefacInterface;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Robert
 */
public class CajaModelControlador extends ModelControladorAbstract<CajaModelControlador.Interface>
{
    /**
     * Controlador Generico
     */
    public CajaModelControlador(MensajeVistaInterface mensajeVista, SessionCodefacInterface session, Interface interfaz) {
        super(mensajeVista, session, interfaz);
    }
    
    /**
     * Metodo iniciar
     * @throws java.rmi.RemoteException
     * @throws ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException
     */ 
    public void iniciar() throws RemoteException, ServicioCodefacException{
        interfaz.setCaja(new Caja());
        CajaEnum[] estadoGeneralesLista = CajaEnum.values();
        List<Sucursal> sucursalLista = ServiceFactory.getFactory().getSucursalServiceIf().obtenerTodos();
        List<PuntoEmision> puntosEmisionLista = ServiceFactory.getFactory().getPuntoVentaServiceIf().obtenerTodos();
        this.interfaz.setEstadosGeneralesVista(estadoGeneralesLista);
        this.interfaz.setSucursalesVista(sucursalLista);
        this.interfaz.setDescripcion("");
        this.interfaz.setNombre("");
    }
    
    public void nuevo(){
        interfaz.setCaja(new Caja());
    }
    
    public void grabar() throws ServicioCodefacException, RemoteException{ 
        try
        {
            if(interfaz.getCaja() == null){
                throw new ServicioCodefacException("Caja nula");
            }        
            if(interfaz.getEnumEstado() == null){
                throw new ServicioCodefacException("Estado null");
            }
            if(interfaz.getSucursal() == null){
                throw new ServicioCodefacException("Sucursal null");
            }
            if(interfaz.getPuntoEmision() == null){
                throw new ServicioCodefacException("Punto de Emisión null");
            }
            //Datos
            obtenerDatos();
            //Grabar
            ServiceFactory.getFactory().getCajaServiceIf().grabar(interfaz.getCaja());
            //Mensaje
            mostrarMensaje(MensajeCodefacSistema.AccionesFormulario.GUARDADO);
        }
        catch(ServicioCodefacException e)
        {
            mostrarMensaje(new CodefacMsj("Error", e.getMessage(), DialogoCodefac.MENSAJE_INCORRECTO));
            throw e;
        }   
    }
    
    public void editar() throws ServicioCodefacException{
        try 
        {
            //Datos
            obtenerDatos();
            //Editar
            ServiceFactory.getFactory().getCajaServiceIf().editar(interfaz.getCaja());
            //Mensaje
            mostrarMensaje(MensajeCodefacSistema.AccionesFormulario.EDITADO);
        } catch (RemoteException e) {
            mostrarMensaje(new CodefacMsj("Error", e.getMessage(), DialogoCodefac.MENSAJE_INCORRECTO));
        }
    }
    
    public void eliminar() throws ServicioCodefacException{
        try
        {
            //DialogoCodefac.dialogoPregunta("Alerta", "Está seguro que desea eliminar el cliente?", DialogoCodefac.MENSAJE_ADVERTENCIA);          
            ServiceFactory.getFactory().getCajaServiceIf().eliminar(interfaz.getCaja());
        } catch(RemoteException e){
            mostrarMensaje(new CodefacMsj("Error", e.getMessage(), DialogoCodefac.MENSAJE_INCORRECTO));
        }
    }
    
    public BuscarDialogoModel obtenerDialogoBusqueda() {
        CajaBusquedaDialogo cajaBusquedaDialogo = new CajaBusquedaDialogo();
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(cajaBusquedaDialogo);
        return buscarDialogoModel;
    }
    
    
    /**
     * Interface para metodos genericos
     */
    public interface Interface
    {
        //Cargar información necesaria
        public void setEstadosGeneralesVista(CajaEnum[] estadoGeneralesLista);
        public void setSucursalesVista(List<Sucursal> sucursalLista);
        public void setPuntosEmisionVista(List<PuntoEmision> puntosEmisionLista);
        
        public PuntoEmision getPuntoEmision();
        public void setPuntoEmision(PuntoEmision puntoEmision);
        public CajaEnum getEnumEstado();
        public void setEnumEstado(CajaEnum generalEnumEstado);
        public Sucursal getSucursal();
        public void setSucursal(Sucursal sucursal);
        public String getNombre();
        public void setNombre(String nombre);
        public String getDescripcion();
        public void setDescripcion(String descripcion);
        
        public void cargarDatosPantalla(Object entidad);
       
        
        //Iniciar valores de caja
        public Caja getCaja();
        public void setCaja(Caja caja);
        
    }
    
    public void obtenerDatos(){
        interfaz.getCaja().setEstado(interfaz.getEnumEstado().getEstado());
        interfaz.getCaja().setSucursal(interfaz.getSucursal());
        interfaz.getCaja().setPuntoEmision(interfaz.getPuntoEmision());
        interfaz.getCaja().setNombre(interfaz.getNombre());
        interfaz.getCaja().setDescripcion(interfaz.getDescripcion());
    }
}
