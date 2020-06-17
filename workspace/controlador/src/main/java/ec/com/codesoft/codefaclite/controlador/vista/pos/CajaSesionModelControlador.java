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

/**
 *
 * @author Robert
 */
public class CajaSesionModelControlador extends ModelControladorAbstract<CajaSesionModelControlador.Interface, CajaSesionModelControlador.Interface, CajaSesionModelControlador.Interface>
{

    public CajaSesionModelControlador(MensajeVistaInterface mensajeVista, SessionCodefacInterface session, Interface interfaz, TipoVista tipoVista) 
    {
        super(mensajeVista, session, interfaz, tipoVista);
    }
   public void iniciar() throws RemoteException, ServicioCodefacException{
        getInterfaz().setCajaSession(new CajaSession());
        CajaEnum[] estadosCaja = CajaEnum.values();
        
    }
    
    public void nuevo(){
        getInterfaz().setCajaSession(new CajaSession());
    }
    
    public void grabar() throws ServicioCodefacException, RemoteException{ 
        try
        {
            if(getInterfaz().getCajaSession()== null){
                throw new ServicioCodefacException("Caja sesión nula");
            }         
            //Datos
            obtenerDatos();
            //Grabar
            //ServiceFactory.getFactory().getAr
            //ServiceFactory.getFactory().getCajaServiceIf().grabar(interfaz.getCajaSession());
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
        //Datos
        obtenerDatos();
        //Editar
        //ServiceFactory.getFactory().getCajaServiceIf().editar(interfaz.getCaja());
        //Mensaje
        mostrarMensaje(MensajeCodefacSistema.AccionesFormulario.EDITADO);
    }
    
    public void eliminar() throws ServicioCodefacException{
        /*try
        {
            //DialogoCodefac.dialogoPregunta("Alerta", "Está seguro que desea eliminar el cliente?", DialogoCodefac.MENSAJE_ADVERTENCIA);          
            ServiceFactory.getFactory().getCajaServiceIf().eliminar(interfaz.getCaja());
        } catch(RemoteException e){
            mostrarMensaje(new CodefacMsj("Error", e.getMessage(), DialogoCodefac.MENSAJE_INCORRECTO));
        }*/
    }
    
    /*public BuscarDialogoModel obtenerDialogoBusqueda() {
        CajaBusquedaDialogo cajaBusquedaDialogo = new CajaBusquedaDialogo();
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(cajaBusquedaDialogo);
        return buscarDialogoModel;
    }*/
    
    public interface Interface{
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
    
}
