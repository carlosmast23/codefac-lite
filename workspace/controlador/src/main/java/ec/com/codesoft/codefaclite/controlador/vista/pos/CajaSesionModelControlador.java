/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vista.pos;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.controlador.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.controlador.vista.factura.ModelControladorAbstract;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.interfaces.VistaCodefacIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.CajaPermiso;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.CajaSession;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CajaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CajaSessionEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefacInterface;
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
    private List<CajaPermiso> cajasList;
    
    public java.util.Date fechaApertura;
    public java.util.Date horaApertura;
    public java.util.Date fechaCierre;
    public java.util.Date horaCierre;
    
    public CajaSesionModelControlador(MensajeVistaInterface mensajeVista, SessionCodefacInterface session, CajaSesionModelControlador.CommonIf interfaz, TipoVista tipoVista) 
    {
        super(mensajeVista, session, interfaz, tipoVista);
    }

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        cajaSession = new CajaSession();
        
        cajaSession.setUsuario(this.session.getUsuario());
        cajaSession.setEstadoEnum(CajaEnum.ACTIVO);
        cajaSession.setEstadoSessionEnum(CajaSessionEnum.ACTIVO);
        
        estadosList = UtilidadesLista.arrayToList(CajaEnum.values());
        estadoCajaSessionList = UtilidadesLista.arrayToList(CajaSessionEnum.values());
        
        if(this.session.getUsuario().getCajasPermisoUsuario() != null)
        {
            cajasList = this.session.getUsuario().getCajasPermisoUsuario();
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
            //Grabar
            setearDatos();
            ServiceFactory.getFactory().getCajaSesionServiceIf().grabar(cajaSession);
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
            ServiceFactory.getFactory().getCajaSesionServiceIf().editar(cajaSession);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(CajaSesionModelControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Mensaje
        mostrarMensaje(MensajeCodefacSistema.AccionesFormulario.EDITADO);
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite, RemoteException {
        try {
            ServiceFactory.getFactory().getCajaSesionServiceIf().eliminar(cajaSession);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(CajaSesionModelControlador.class.getName()).log(Level.SEVERE, null, ex);
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
        //CajaBusquedaDialogo cajaBusquedaDialogo = new CajaBusquedaDialogo();
        //return cajaBusquedaDialogo;
        return null;
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void obtenerDatos(){
        //this.getInterfaz().getCajaSession().setEstado(this.getInterfaz().getCajaSession().getEstado());
        //this.getInterfaz().getCajaSession().setEstadoCierreCaja(this.getInterfaz().getCajaSession().getEstadoCierreCaja());
        //this.getInterfaz().getCajaSession().setValorApertura(this.getInterfaz().getValorApertura());
        //this.getInterfaz().getCajaSession().setValorCierre(this.getInterfaz().getValorCierre());
    }
   
    /**
     * Agregado interfaces 
     */
    public interface CommonIf
    {
        
    }
    
    public interface SwingIf extends CajaSesionModelControlador.CommonIf
    {
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

    public List<CajaPermiso> getCajasList() {
        return cajasList;
    }

    public void setCajasList(List<CajaPermiso> cajasList) {
        this.cajasList = cajasList;
    }

    public java.util.Date getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(java.util.Date fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public java.util.Date getHoraApertura() {
        return horaApertura;
    }

    public void setHoraApertura(java.util.Date horaApertura) {
        this.horaApertura = horaApertura;
    }

    public java.util.Date getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(java.util.Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public java.util.Date getHoraCierre() {
        return horaCierre;
    }

    public void setHoraCierre(java.util.Date horaCierre) {
        this.horaCierre = horaCierre;
    }

    ////////////////////////////////////////////////////////////////////////////
    // Funciones
    ////////////////////////////////////////////////////////////////////////////
    
    public void iniciarCajaSesion() 
    {
        if(cajaSession.getEstadoEnum() == null)
        {
            fechaApertura = new java.util.Date();
            horaApertura = new java.util.Date(); 
            cajaSession.setFechaHoraApertura(UtilidadesFecha.castDateSqlToTimeStampSql(UtilidadesFecha.FechaHoraPorUnion( UtilidadesFecha.castDateUtilToSql(fechaApertura),  UtilidadesFecha.castDateUtilToSql(horaApertura))));
            
            cajaSession.setEstadoSessionEnum(CajaSessionEnum.ACTIVO);
        }
    }

    public void cerrarCajaSesion() 
    {
        if(cajaSession.getEstadoEnum().equals(CajaEnum.ACTIVO))
        {
            fechaCierre = new java.util.Date();
            horaCierre = new java.util.Date();
            cajaSession.setFechaHoraApertura(UtilidadesFecha.castDateSqlToTimeStampSql(UtilidadesFecha.FechaHoraPorUnion( UtilidadesFecha.castDateUtilToSql(fechaApertura),  UtilidadesFecha.castDateUtilToSql(horaApertura))));
            
            cajaSession.setEstadoSessionEnum(CajaSessionEnum.FINALIZADO);
        }
    }
    
    private void setearDatos() 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
