/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vista.servicio;

import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ClienteEstablecimientoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.EmpleadoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.vista.inventario.*;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.MarcaProductoDialogo;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ObjetoMantenimientoBusqueda;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.SegmentoProductoBusqueda;
import ec.com.codesoft.codefaclite.controlador.vista.crm.RutaModelControlador;
import ec.com.codesoft.codefaclite.controlador.vista.factura.ModelControladorAbstract;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.interfaces.VistaCodefacIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Departamento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empleado;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.MarcaProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ObjetoMantenimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PersonaEstablecimiento;
//import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SegmentoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoObjetoMantenimientoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefacInterface;
import ec.com.codesoft.codefaclite.utilidades.list.UtilidadesLista;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class ObjetoMantenimientoControlador extends ModelControladorAbstract<ObjetoMantenimientoControlador.CommonIf,ObjetoMantenimientoControlador.SwingIf,ObjetoMantenimientoControlador.WebIf> implements VistaCodefacIf {

    private List<TipoObjetoMantenimientoEnum> tipoList;
    
    private ObjetoMantenimiento objetoMantenimiento;
    
    public ObjetoMantenimientoControlador(MensajeVistaInterface mensajeVista, SessionCodefacInterface session, CommonIf interfaz, TipoVista tipoVista) {
        super(mensajeVista, session, interfaz, tipoVista);
    }

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        tipoList=UtilidadesLista.arrayToList(TipoObjetoMantenimientoEnum.values());
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, RemoteException 
    {
        try {
            setearDatosAdicionales();
            ServiceFactory.getFactory().getObjetoMantenimientoServiceIf().grabar(objetoMantenimiento,session.getEmpresa(), session.getUsuario());
            mostrarMensaje(MensajeCodefacSistema.AccionesFormulario.GUARDADO);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ObjetoMantenimientoControlador.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensaje(new CodefacMsj(ex.getMessage(),CodefacMsj.TipoMensajeEnum.ERROR));
            throw new ExcepcionCodefacLite(ex.getMessage());
        }
    }

    @Override
    public void editar() throws ExcepcionCodefacLite, RemoteException {
        try {
            setearDatosAdicionales();
            ServiceFactory.getFactory().getObjetoMantenimientoServiceIf().editar(objetoMantenimiento,session.getEmpresa(), session.getUsuario());
            mostrarMensaje(MensajeCodefacSistema.AccionesFormulario.EDITADO);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ObjetoMantenimientoControlador.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensaje(new CodefacMsj(ex.getMessage(),CodefacMsj.TipoMensajeEnum.ERROR));
            throw new ExcepcionCodefacLite(ex.getMessage());
        }
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite, RemoteException {
        if(this.dialogoPregunta(MensajeCodefacSistema.Preguntas.ELIMINAR_REGISTRO))
        {
            try {
                ServiceFactory.getFactory().getObjetoMantenimientoServiceIf().eliminar(objetoMantenimiento);
                mostrarMensaje(MensajeCodefacSistema.AccionesFormulario.ELIMINADO_CORRECTAMENTE);                
            } catch (ServicioCodefacException ex) {
                Logger.getLogger(RutaModelControlador.class.getName()).log(Level.SEVERE, null, ex);
                mostrarMensaje(new CodefacMsj(ex.getMessage(),CodefacMsj.TipoMensajeEnum.ERROR));
                throw new ExcepcionCodefacLite(ex.getMessage());
            }
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
        objetoMantenimiento=new ObjetoMantenimiento();        
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
        return new ObjetoMantenimientoBusqueda(session.getEmpresa());
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        objetoMantenimiento=(ObjetoMantenimiento) entidad;
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private void setearDatosAdicionales()
    {
        objetoMantenimiento.setEmpresa(session.getEmpresa());
    }

    public ObjetoMantenimiento getSegmentoProducto() {
        return objetoMantenimiento;
    }

    public void setSegmentoProducto(ObjetoMantenimiento segmentoProducto) {
        this.objetoMantenimiento = segmentoProducto;
    }

    public void listenerBotonBuscarCliente()
    {
        ClienteEstablecimientoBusquedaDialogo busqueda=new ClienteEstablecimientoBusquedaDialogo(session);
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(busqueda);
        buscarDialogoModel.setVisible(true);
        if (buscarDialogoModel.getResultado() != null) 
        {
            PersonaEstablecimiento personaEstablecimiento= (PersonaEstablecimiento) buscarDialogoModel.getResultado();
            objetoMantenimiento.setPropietario(personaEstablecimiento.getPersona());
        }
    }
    
    ///////////////////////////////////////////////////////////////////////////
    //                      METODOS GET AND SET
    ///////////////////////////////////////////////////////////////////////////

    public ObjetoMantenimiento getObjetoMantenimiento() {
        return objetoMantenimiento;
    }

    public void setObjetoMantenimiento(ObjetoMantenimiento objetoMantenimiento) {
        this.objetoMantenimiento = objetoMantenimiento;
    }

    public List<TipoObjetoMantenimientoEnum> getTipoList() {
        return tipoList;
    }

    public void setTipoList(List<TipoObjetoMantenimientoEnum> tipoList) {
        this.tipoList = tipoList;
    }

    

    
    
    
        
    ///////////////////////////////////////////////////////////////////////////
    //             METODOS QUE CONTIENEN INTERFACES PARA LA IMPLEMTACION
    ///////////////////////////////////////////////////////////////////////////
    
    public interface CommonIf
    {
        //TODO: Implementacion de todas las interfaces comunes
    }
    
    public interface SwingIf extends CommonIf
    {
        //TODO: Implementacion de las interfaces solo necesarias para Swing
    }
    
    public interface WebIf extends CommonIf
    {
        //TODO: Implementacion de las interafaces solo para la web
    }    
}
