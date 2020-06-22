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
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.CajaSession;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CajaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CajaSessionEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefacInterface;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
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

    public CajaSesionModelControlador(MensajeVistaInterface mensajeVista, SessionCodefacInterface session, CajaSesionModelControlador.CommonIf interfaz, TipoVista tipoVista) 
    {
        super(mensajeVista, session, interfaz, tipoVista);
    }

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        getInterfaz().setCajaSession(new CajaSession());
        CajaEnum[] estadosCaja = CajaEnum.values();
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite, RemoteException {
        getInterfaz().setCajaSession(new CajaSession());
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, RemoteException {
         try
        {
            if(getInterfaz().getCajaSession()== null){
                throw new ServicioCodefacException("Caja sesión nula");
            }         
            //Datos
            obtenerDatos();
            //Grabar
            ServiceFactory.getFactory().getCajaSesionServiceIf().grabar(getInterfaz().getCajaSession());
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
            ServiceFactory.getFactory().getCajaSesionServiceIf().editar(getInterfaz().getCajaSession());
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(CajaSesionModelControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Mensaje
        mostrarMensaje(MensajeCodefacSistema.AccionesFormulario.EDITADO);
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite, RemoteException {
        try {
            ServiceFactory.getFactory().getCajaSesionServiceIf().eliminar(getInterfaz().getCajaSession());
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
        //this.interfaz.getCajaSession().setArqueoCaja();
        //this.interfaz.getCajaSession().setCaja();
        this.getInterfaz().getCajaSession().setEstado(this.getInterfaz().getCajaSession().getEstado());
        this.getInterfaz().getCajaSession().setEstadoCierreCaja(this.getInterfaz().getCajaSession().getEstadoCierreCaja());
        //this.interfaz.getCajaSession().setFechaHoraApertura();
        //this.interfaz.getCajaSession().setUsuario(usuario);
        this.getInterfaz().getCajaSession().setValorApertura(this.getInterfaz().getValorApertura());
        this.getInterfaz().getCajaSession().setValorCierre(this.getInterfaz().getValorCierre());
    }
    
    /**
     * Agregado interfaces 
     */
    public interface CommonIf
    {
        public void iniciar();
        
        public CajaSession getCajaSession();
        public void setCajaSession(CajaSession cajaSession);
        
        public BigDecimal getValorApertura();
        public void setValorApertura(BigDecimal valorApertura);
        public BigDecimal getValorCierre();
        public void setValorCierre(BigDecimal valorCierre);
        
        public Date getFechaApertura();
        public void setFechaApertura(Date fechaApertura);
        public Date getHoraApertura();
        public void setHoraApertura(Date fechaApertura);
        public Date getFechaCierre();
        public void setFechaCierre(Date fechaCierre);
        public Date getHoraCierre();
        public void setHoraCierre(Date fechaCierre);
        
        public CajaSessionEnum getEstadoCajaSesion();
        public void setEstadoCajaSesion(CajaSessionEnum cajaSessionEnum);
        public CajaSessionEnum getEstadoCierreCajaSesion();
        public void setEstadoCierreCajaSesion(CajaSessionEnum cajaSessionEnum);
        
    }
    
    public interface SwingIf extends CajaSesionModelControlador.CommonIf
    {
        
    }
    
    public interface WebIf extends CajaSesionModelControlador.CommonIf
    {
        
    }
    
}
