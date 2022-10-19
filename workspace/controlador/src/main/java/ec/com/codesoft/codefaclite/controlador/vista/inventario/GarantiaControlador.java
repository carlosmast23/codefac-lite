/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vista.inventario;

import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.GarantiaBusqueda;
import ec.com.codesoft.codefaclite.controlador.vista.factura.ModelControladorAbstract;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.interfaces.VistaCodefacIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Bodega;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empleado;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Garantia;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefacInterface;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class GarantiaControlador extends  ModelControladorAbstract<GarantiaControlador.ICommon,GarantiaControlador.ISwing,GarantiaControlador.IWeb> implements  VistaCodefacIf
{
    private Garantia garantia;
    private List<Bodega> bodegaList ; 
    private List<Empleado> empleadoList;

    public GarantiaControlador(MensajeVistaInterface mensajeVista, SessionCodefacInterface session, ICommon interfaz, TipoVista tipoVista) {
        super(mensajeVista, session, interfaz, tipoVista);
    }

    
    public Garantia getGarantia() {
        return garantia;
    }

    public void setGarantia(Garantia garantia) {
        this.garantia = garantia;
    }

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        garantia=new Garantia();
        garantia.setFechaIngreso(UtilidadesFecha.getFechaHoy());
        cargarDatosIniciales();
        
    }
    
    private void cargarDatosIniciales()
    {
        try {
            this.bodegaList=ServiceFactory.getFactory().getBodegaServiceIf().obtenerActivosPorEmpresa(session.getEmpresa());
            //TODO: Filtrar los empleados por empresa
            this.empleadoList=ServiceFactory.getFactory().getEmpleadoServiceIf().obtenerTodos();
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(GarantiaControlador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(GarantiaControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, RemoteException {
        try {           
            garantia=ServiceFactory.getFactory().getGarantiaServiceIf().grabar(garantia, session.getEmpresa(), session.getUsuario());
            mostrarMensaje(MensajeCodefacSistema.AccionesFormulario.GUARDADO);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(LoteControlador.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensaje(new CodefacMsj(ex.getMessage(),CodefacMsj.TipoMensajeEnum.ERROR));
            throw new ExcepcionCodefacLite(ex.getMessage());
        }
    }

    @Override
    public void editar() throws ExcepcionCodefacLite, RemoteException {
        try {           
            session.getUsuario();
            ServiceFactory.getFactory().getGarantiaServiceIf().editar(garantia, session.getEmpresa(), session.getUsuario());
            
            mostrarMensaje(MensajeCodefacSistema.AccionesFormulario.EDITADO);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(LoteControlador.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensaje(new CodefacMsj(ex.getMessage(),CodefacMsj.TipoMensajeEnum.ERROR));
            throw new ExcepcionCodefacLite(ex.getMessage());
        }
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        garantia=new Garantia();
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
        return new GarantiaBusqueda(session.getEmpresa());
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        this.garantia=(Garantia) entidad;
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Bodega> getBodegaList() {
        return bodegaList;
    }

    public void setBodegaList(List<Bodega> bodegaList) {
        this.bodegaList = bodegaList;
    }

    public List<Empleado> getEmpleadoList() {
        return empleadoList;
    }

    public void setEmpleadoList(List<Empleado> empleadoList) {
        this.empleadoList = empleadoList;
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
