/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vista.pos;

import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.CajaSessionBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.controlador.vista.factura.ModelControladorAbstract;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.interfaces.VistaCodefacIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.Caja;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.CajaPermiso;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.CajaSession;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CajaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CajaSessionEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefacInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.pos.CajaServiceIf;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.list.UtilidadesLista;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Robert
 */
public class CajaSesionModelControlador extends ModelControladorAbstract<CajaSesionModelControlador.CommonIf, CajaSesionModelControlador.SwingIf, CajaSesionModelControlador.WebIf> implements VistaCodefacIf
{
    private CajaSession cajaSession;
    
    private List<CajaEnum> estadosList;
    private List<CajaSessionEnum> estadoCajaSessionList;
    private List<Caja> cajasList;
    
    private List<CajaSession> cajaSessionList;
    private CajaSession cajaSessionSeleccionada;
    
    public CajaSesionModelControlador(MensajeVistaInterface mensajeVista, SessionCodefacInterface session, CajaSesionModelControlador.CommonIf interfaz, TipoVista tipoVista) 
    {
        super(mensajeVista, session, interfaz, tipoVista);
    }

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        
        cajaSession = new CajaSession();       
        cajaSession.setUsuario(this.session.getUsuario());
        cajaSession.setEstadoSessionEnum(CajaSessionEnum.ACTIVO);
        
        estadosList = UtilidadesLista.arrayToList(CajaEnum.values());
        estadoCajaSessionList = UtilidadesLista.arrayToList(CajaSessionEnum.values());
        
        CajaServiceIf cajaServiceIf = ServiceFactory.getFactory().getCajaServiceIf();
        cajasList = cajaServiceIf.buscarCajasAutorizadasPorUsuario(session.getUsuario());
        
        if(cajasList.size()>0)
        {
            cajaSession.setCaja(cajasList.get(0));       
        }
        
        //Consultar las cajas session que tenga activas
        cajaSessionList=ServiceFactory.getFactory().getCajaSesionServiceIf().obtenerCajaSessionPorUsuario(session.getUsuario());
        if(cajaSessionList.size()>0)
        {
            cajaSessionSeleccionada=cajaSessionList.get(0);
            cargarDatosPantalla(cajaSessionSeleccionada);
        }
        
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite, RemoteException {
        this.iniciar();
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, RemoteException {
        try
        {
            setearDatos();
            
            
            if(getInterazEscritorio().getTipoProcesoEnum().equals(TipoProcesoCajaEnum.APERTURA_CAJA))
            {
                //Grabar
                ServiceFactory.getFactory().getCajaSesionServiceIf().grabar(cajaSession);
                mostrarMensaje(MensajeCodefacSistema.AccionesFormulario.GUARDADO);
            }
            else if(getInterazEscritorio().getTipoProcesoEnum().equals(TipoProcesoCajaEnum.CIERRE_CAJA))
            {
                ServiceFactory.getFactory().getCajaSesionServiceIf().editar(cajaSession);
                mostrarMensaje(MensajeCodefacSistema.AccionesFormulario.GUARDADO);
            }
            
        }
        catch(ServicioCodefacException e)
        {
            mostrarMensaje(new CodefacMsj("Error", e.getMessage(), DialogoCodefac.MENSAJE_INCORRECTO));
            Logger.getLogger(CajaModelControlador.class.getName()).log(Level.SEVERE, null, e);
        }      
    }

    @Override
    public void editar() throws ExcepcionCodefacLite, RemoteException {
        try {       
            //Editar
            ServiceFactory.getFactory().getCajaSesionServiceIf().editar(cajaSession);
            //Mensaje
            mostrarMensaje(MensajeCodefacSistema.AccionesFormulario.EDITADO);
        } 
        catch (ServicioCodefacException e) 
        {
            mostrarMensaje(new CodefacMsj("Error", e.getMessage(), DialogoCodefac.MENSAJE_INCORRECTO));
            Logger.getLogger(CajaSesionModelControlador.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite, RemoteException {
        try
        {
            Boolean respuesta = dialogoPregunta(MensajeCodefacSistema.Preguntas.ELIMINAR_REGISTRO);
            if(!respuesta){
                throw new ServicioCodefacException("Error elimando Caja");
            }            
            ServiceFactory.getFactory().getCajaSesionServiceIf().eliminar(cajaSession);
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
    public void limpiar(){
        try {
            this.iniciar();
        } catch (ExcepcionCodefacLite | RemoteException ex) {
            Logger.getLogger(CajaSesionModelControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        CajaSessionBusquedaDialogo cajaBusquedaDialogo = new CajaSessionBusquedaDialogo(session);
        return cajaBusquedaDialogo;
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        cajaSession = (CajaSession)entidad;
        //Hago esta validacion porque puede ser que me esta mandando datos vacios a cargar
        if(cajaSession!=null && cajaSession.getId()!=null)
        {
            getInterfaz().cargarDatosVista(cajaSession);
        }
        
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    public CajaSession buscarSessionDesdeCaja(Caja caja)
    {
        //Caja caja = (Caja) getjCmbCajaPermiso().getSelectedItem();
        if (caja != null) {
            try {
                List<CajaSession> cajasSessionList = ServiceFactory.getFactory().getCajaSesionServiceIf().obtenerCajaSessionPorUsuarioYSucursal(session.getUsuario(), session.getSucursal());
                if (cajasSessionList.size() > 0) {
                    for (CajaSession cajaSession : cajasSessionList) 
                    {
                        if(cajaSession.getCaja().equals(caja))
                        {
                            return cajaSession;
                        }
                    }
                }
            } catch (RemoteException ex) {
                Logger.getLogger(CajaSesionModelControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
  
    /**
     * Agregado interfaces 
     */
    public interface CommonIf
    {
        public String valorApertura();
        public void cargarDatosVista(CajaSession cajaSession);
    }
    
    public interface SwingIf extends CajaSesionModelControlador.CommonIf
    {
        public TipoProcesoCajaEnum getTipoProcesoEnum();
    }
    
    public interface WebIf extends CajaSesionModelControlador.CommonIf
    {
    }
    
    ////////////////////////////////////////////////////////////////////////////
    //                      GET AND SET
    ////////////////////////////////////////////////////////////////////////////

    public CajaSession getCajaSession() {
        return cajaSession;
    }

    public void setCajaSession(CajaSession cajaSession) {
        this.cajaSession = cajaSession;
    }
    
    public List<CajaEnum> getEstadosList() {
        return estadosList;
    }

    public void setEstadosList(List<CajaEnum> estadosList) {
        this.estadosList = estadosList;
    }

    public List<CajaSessionEnum> getEstadoCajaSessionList() {
        return estadoCajaSessionList;
    }

    public void setEstadoCajaSessionList(List<CajaSessionEnum> estadoCajaSessionList) {
        this.estadoCajaSessionList = estadoCajaSessionList;
    }

    public List<Caja> getCajasList() {
        return cajasList;
    }

    public void setCajasList(List<Caja> cajasList) {
        this.cajasList = cajasList;
    }

    public List<CajaSession> getCajaSessionList() {
        return cajaSessionList;
    }

    public void setCajaSessionList(List<CajaSession> cajaSessionList) {
        this.cajaSessionList = cajaSessionList;
    }

    public CajaSession getCajaSessionSeleccionada() {
        return cajaSessionSeleccionada;
    }

    public void setCajaSessionSeleccionada(CajaSession cajaSessionSeleccionada) {
        this.cajaSessionSeleccionada = cajaSessionSeleccionada;
    }

    
    
    ////////////////////////////////////////////////////////////////////////////
    // Funciones
    ////////////////////////////////////////////////////////////////////////////
    
    public void setearDatos()
    {
        if(getInterfaz().valorApertura().length()>0)
        {
            this.cajaSession.setValorApertura(new BigDecimal(getInterfaz().valorApertura()));
        }
    }
    
    public enum TipoProcesoCajaEnum
    {
        APERTURA_CAJA,
        CIERRE_CAJA;
    }
}
