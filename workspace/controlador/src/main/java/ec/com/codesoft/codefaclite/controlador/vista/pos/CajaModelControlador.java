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
import ec.com.codesoft.codefaclite.controlador.vista.crm.EjemploModelControlador;
import ec.com.codesoft.codefaclite.controlador.vista.crm.ProductoModelControlador;
import ec.com.codesoft.codefaclite.controlador.vista.factura.ModelControladorAbstract;
import ec.com.codesoft.codefaclite.controlador.vista.factura.ModelControladorAbstract.MensajeVistaInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.interfaces.VistaCodefacIf;
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
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Robert
 */
public class CajaModelControlador extends ModelControladorAbstract<CajaModelControlador.CommonIf, CajaModelControlador.SwingIf, CajaModelControlador.WebIf> implements VistaCodefacIf
{
    /**
     * Controlador Generico
     */
    public CajaModelControlador(MensajeVistaInterface mensajeVista, SessionCodefacInterface session, CajaModelControlador.CommonIf interfaz, TipoVista tipoVista) {
        super(mensajeVista, session, interfaz, tipoVista);
    }
    
    /**
    * Metodo iniciar
    * @throws java.rmi.RemoteException
    * @throws ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException
    */ 
    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        getInterfaz().setCaja(new Caja());
        CajaEnum[] estadoGeneralesLista = CajaEnum.values();
        List<Sucursal> sucursalLista = ServiceFactory.getFactory().getSucursalServiceIf().obtenerTodos();
        this.getInterfaz().setEstadosGeneralesVista(estadoGeneralesLista);
        this.getInterfaz().setSucursalesVista(sucursalLista);
        this.getInterfaz().setDescripcion("");
        this.getInterfaz().setNombre("");
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite, RemoteException {
        getInterfaz().setCaja(new Caja());
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, RemoteException {
        try
        {
            if(getInterfaz().getCaja() == null){
                throw new ServicioCodefacException("Caja nula");
            }        
            if(getInterfaz().getEnumEstado() == null){
                throw new ServicioCodefacException("Estado null");
            }
            if(getInterfaz().getSucursal() == null){
                throw new ServicioCodefacException("Sucursal null");
            }
            if(getInterfaz().getPuntoEmision() == null){
                throw new ServicioCodefacException("Punto de Emisión null");
            }
            //Datos
            obtenerDatos();
            //Grabar
            ServiceFactory.getFactory().getCajaServiceIf().grabar(getInterfaz().getCaja());
            //Mensaje
            mostrarMensaje(MensajeCodefacSistema.AccionesFormulario.GUARDADO);
        }
        catch(ServicioCodefacException e)
        {
            mostrarMensaje(new CodefacMsj("Error", e.getMessage(), DialogoCodefac.MENSAJE_INCORRECTO));
            try {
                throw e;
            } catch (ServicioCodefacException ex) {
                Logger.getLogger(CajaModelControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }   
    }

    @Override
    public void editar() throws ExcepcionCodefacLite, RemoteException {
        
        try {
            //Datos
            obtenerDatos();
            //Editar
            ServiceFactory.getFactory().getCajaServiceIf().editar(getInterfaz().getCaja());
            mostrarMensaje(MensajeCodefacSistema.AccionesFormulario.EDITADO);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(CajaModelControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Mensaje
        
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite, RemoteException {
        try
        {
            Boolean respuesta = dialogoPregunta(MensajeCodefacSistema.Preguntas.ELIMINAR_REGISTRO);
            if(!respuesta){
                throw new ServicioCodefacException("Error elimando Caja");
            }            
            ServiceFactory.getFactory().getCajaServiceIf().eliminar(getInterfaz().getCaja());
        } catch (RemoteException ex) {
            Logger.getLogger(ProductoModelControlador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(CajaModelControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void imprimir() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void limpiar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getURLAyuda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public InterfaceModelFind obtenerDialogoBusqueda() {
        CajaBusquedaDialogo cajaBusquedaDialogo = new CajaBusquedaDialogo();
        return cajaBusquedaDialogo;
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        Caja cajatemp = (Caja) entidad;
        getInterfaz().setCaja(cajatemp); 

    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public interface CommonIf
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
    
    public interface SwingIf extends CajaModelControlador.CommonIf
    {
        //TODO: Implementacion de las interfaces solo necesarias para Swing
    }
    
    public interface WebIf extends CajaModelControlador.CommonIf
    {
        //TODO: Implementacion de las interafaces solo para la web
    }
    
    public void obtenerDatos(){
        getInterfaz().getCaja().setEstadoEnum(getInterfaz().getEnumEstado());
        getInterfaz().getCaja().setSucursal(getInterfaz().getSucursal());
        getInterfaz().getCaja().setPuntoEmision(getInterfaz().getPuntoEmision());
        getInterfaz().getCaja().setNombre(getInterfaz().getNombre());
        getInterfaz().getCaja().setDescripcion(getInterfaz().getDescripcion());
    }
}
