/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vista.crm;

import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ClienteEstablecimientoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.EmpleadoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.RutaBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.controlador.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.controlador.vista.factura.ModelControladorAbstract;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.interfaces.VistaCodefacIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Departamento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empleado;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PersonaEstablecimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Ruta;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.RutaDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefacInterface;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class RutaModelControlador extends ModelControladorAbstract<RutaModelControlador.CommonIf,RutaModelControlador.SwingIf,RutaModelControlador.WebIf> implements VistaCodefacIf  {

    private Ruta ruta;
    
    /**
     * Variable temporal para poder agreagar
     */
    private RutaDetalle rutaDetalle;
    
    
    
    public RutaModelControlador(MensajeVistaInterface mensajeVista, SessionCodefacInterface session, CommonIf interfaz, TipoVista tipoVista) {
        super(mensajeVista, session, interfaz, tipoVista);
    }

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, RemoteException {
        try {
            setearDatosAdicionales();
            ServiceFactory.getFactory().getRutaServiceIf().grabar(ruta);
            mostrarMensaje(MensajeCodefacSistema.AccionesFormulario.GUARDADO);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ZonaControlador.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensaje(new CodefacMsj(ex.getMessage(),CodefacMsj.TipoMensajeEnum.ERROR));
            throw new ExcepcionCodefacLite(ex.getMessage());
        }
    }

    @Override
    public void editar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        ruta=new Ruta();
        rutaDetalle=Ruta.getInstanceActivo();
        //rutaDetalle.setOrden(0);
        //rutaDetalle.setRuta(ruta);        
        
        //ruta.addDetalle(rutaDetalle);
        
        
        ruta.setDiaVisita(1);
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
        return new RutaBusquedaDialogo(session.getEmpresa());
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        ruta=(Ruta) entidad;
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void listenerBotonBuscarCliente()
    {
        ClienteEstablecimientoBusquedaDialogo clienteBusquedaDialogo = new ClienteEstablecimientoBusquedaDialogo(session);
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(clienteBusquedaDialogo);
        buscarDialogoModel.setVisible(true);
        if (buscarDialogoModel.getResultado() != null) 
        {   
            rutaDetalle.setEstablecimiento((PersonaEstablecimiento)buscarDialogoModel.getResultado());
        }
    }
    
    public void listenerBotonBuscarVendedor()
    {
        EmpleadoBusquedaDialogo busqueda=new EmpleadoBusquedaDialogo(Departamento.TipoEnum.Ventas);
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(busqueda);
        buscarDialogoModel.setVisible(true);
        if (buscarDialogoModel.getResultado() != null) {
            ruta.setVendedor((Empleado) buscarDialogoModel.getResultado());
        }
    }
    
    public void listenerAddDetalle()
    {
        if(validarAgregarDetalle(rutaDetalle))
        {
            ruta.addDetalle(rutaDetalle);

            //Despues de agregar al detalle creo uno nuevo
            rutaDetalle=Ruta.getInstanceActivo();
            rutaDetalle.setOrden(0);
        }
    }
    
    public boolean validarAgregarDetalle(RutaDetalle rutaDetalle)
    {
        if (rutaDetalle.getEstablecimiento() == null) {
            mostrarMensaje(new CodefacMsj("No se puede agregar sin un Establecimiento", CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
            return false;
        }
        
        //TODO: Verificar que no exista una rutaDetalle duplicada en la ruta
        if(this.ruta.verificarDatoDuplicado(rutaDetalle))
        {
            mostrarMensaje(new CodefacMsj("No se puede agregar un Establecimiento Repetido", CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
            return false;
        }
        
        return true;
    }
    
    ///////////////////////////////////////////////////////////////////////////
    //                          GET AND SET
    ///////////////////////////////////////////////////////////////////////////

    public Ruta getRuta() {
        return ruta;
    }

    public void setRuta(Ruta ruta) {
        this.ruta = ruta;
    }

    public RutaDetalle getRutaDetalle() {
        return rutaDetalle;
    }

    public void setRutaDetalle(RutaDetalle rutaDetalle) {
        this.rutaDetalle = rutaDetalle;
    }
    
    

    private void setearDatosAdicionales() {
        ruta.setEmpresa(session.getEmpresa());
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + Objects.hashCode(this.ruta);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RutaModelControlador other = (RutaModelControlador) obj;
        if (!Objects.equals(this.ruta, other.ruta)) {
            return false;
        }
        return true;
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
