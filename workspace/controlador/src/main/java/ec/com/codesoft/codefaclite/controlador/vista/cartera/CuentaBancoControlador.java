/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.com.codesoft.codefaclite.controlador.vista.cartera;

import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.CuentaBancoBusqueda;
import ec.com.codesoft.codefaclite.controlador.vista.factura.ModelControladorAbstract;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.interfaces.VistaCodefacIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.banco.Banco;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.banco.CuentaBanco;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefacInterface;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DellWin10
 */
public class CuentaBancoControlador extends ModelControladorAbstract<CuentaBancoControlador.ICommon, CuentaBancoControlador.ISwing, CuentaBancoControlador.IWeb> implements  VistaCodefacIf{
    
    private CuentaBanco cuentaBanco;
    
    private List<Banco> bancoList;
    private CuentaBanco.TipoCuentaBancariaEnum[] tipoCuentaList;

    public CuentaBancoControlador(MensajeVistaInterface mensajeVista, SessionCodefacInterface session, ICommon interfaz, TipoVista tipoVista) {
        super(mensajeVista, session, interfaz, tipoVista);
    }

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        cuentaBanco=new CuentaBanco();
        cargarDatos();
    }
    
    private void cargarDatos()
    {
        try {
            this.bancoList=ServiceFactory.getFactory().getBancoServiceIf().obtenerTodos();
            this.tipoCuentaList=CuentaBanco.TipoCuentaBancariaEnum.values();
        } catch (RemoteException ex) {
            Logger.getLogger(CuentaBancoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, RemoteException {
        
        try {           
            cuentaBanco=ServiceFactory.getFactory().getCuentaBancoServiceIf().grabar(cuentaBanco, session.getEmpresa(), session.getUsuario());
            mostrarMensaje(MensajeCodefacSistema.AccionesFormulario.GUARDADO);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(CuentaBancoControlador.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensaje(new CodefacMsj(ex.getMessage(),CodefacMsj.TipoMensajeEnum.ERROR));
            throw new ExcepcionCodefacLite(ex.getMessage());
        }
    }

    @Override
    public void editar() throws ExcepcionCodefacLite, RemoteException {
        try {           
            session.getUsuario();
            ServiceFactory.getFactory().getCuentaBancoServiceIf().editar(cuentaBanco, session.getEmpresa(), session.getUsuario());
            mostrarMensaje(MensajeCodefacSistema.AccionesFormulario.EDITADO);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(CuentaBancoControlador.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensaje(new CodefacMsj(ex.getMessage(),CodefacMsj.TipoMensajeEnum.ERROR));
            throw new ExcepcionCodefacLite(ex.getMessage());
        }
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite, RemoteException {
        try {
            
            if(!dialogoPregunta(MensajeCodefacSistema.Preguntas.ELIMINAR_REGISTRO))
            {
                throw new ExcepcionCodefacLite("Acción cancelada de eliminar");                
            }
            
            ServiceFactory.getFactory().getCuentaBancoServiceIf().eliminar(cuentaBanco);
            mostrarMensaje(MensajeCodefacSistema.AccionesFormulario.ELIMINADO_CORRECTAMENTE);            
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(CuentaBancoControlador.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensaje(new CodefacMsj(ex.getMessage(),CodefacMsj.TipoMensajeEnum.ERROR));
            throw new ExcepcionCodefacLite(ex.getMessage());
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
        cuentaBanco=new CuentaBanco();
        getInterfaz().limpiarPantalla();
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
        return new CuentaBancoBusqueda(session.getEmpresa());
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        this.cuentaBanco=(CuentaBanco) entidad;
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public CuentaBanco getCuentaBanco() {
        return cuentaBanco;
    }

    public void setCuentaBanco(CuentaBanco cuentaBanco) {
        this.cuentaBanco = cuentaBanco;
    }

    public List<Banco> getBancoList() {
        return bancoList;
    }

    public void setBancoList(List<Banco> bancoList) {
        this.bancoList = bancoList;
    }

    public CuentaBanco.TipoCuentaBancariaEnum[] getTipoCuentaList() {
        return tipoCuentaList;
    }

    public void setTipoCuentaList(CuentaBanco.TipoCuentaBancariaEnum[] tipoCuentaList) {
        this.tipoCuentaList = tipoCuentaList;
    }

    
     ////////////////////////////////////////////////////////////////////////////
    ///                          INTERFACES                                 ////
    ////////////////////////////////////////////////////////////////////////////
    
    public interface ICommon
    {
        public void limpiarPantalla();
    }
    
    public interface ISwing extends ICommon
    {
        
    }
    
    public interface  IWeb extends ICommon
    {
    
    }
}
