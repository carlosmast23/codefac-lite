/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.transporte.model;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.DialogInterfacePanel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfazPostConstructPanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Perfil;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Transportista;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ClienteEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TransportistaEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.transporte.TransportistaServiceIf;
import ec.com.codesoft.codefaclite.transporte.busqueda.TransportistaBusquedaDialogo;
import ec.com.codesoft.codefaclite.transporte.panel.TransportistaPanel;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class TransportistaModel extends TransportistaPanel implements DialogInterfacePanel<Transportista>, InterfazPostConstructPanel{
    
    private Transportista transportista;
    private TransportistaServiceIf transportistaService;
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        transportista = new Transportista();
        transportistaService = ServiceFactory.getFactory().getTransportistaServiceIf();
        cargarCombos();
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite, RemoteException {
        transportista = new Transportista();        
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, RemoteException {
        try {
            if (!prevalidar()) {
                //Cancela el evento guardar porque no prevalido
                throw new ExcepcionCodefacLite("Error al prevalidar");
            }
            setearDatos();
            transportista = transportistaService.grabar(transportista);
            DialogoCodefac.mensaje("Datos correctos", "El cliente se guardo correctamente", DialogoCodefac.MENSAJE_CORRECTO);
        } catch (ServicioCodefacException ex) {
            DialogoCodefac.mensaje("Error", ex.getMessage(), DialogoCodefac.MENSAJE_INCORRECTO);
            throw new ExcepcionCodefacLite("Error al prevalidar");
        } catch (RemoteException ex) {
            Logger.getLogger(TransportistaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void editar() throws ExcepcionCodefacLite, RemoteException {
         try {
            setearDatos();
            transportistaService.editar(transportista); 
            DialogoCodefac.mensaje("Correcto","El transportista fue editado correctamente",DialogoCodefac.MENSAJE_CORRECTO);
        } catch (RemoteException ex) {
            Logger.getLogger(Transportista.class.getName()).log(Level.SEVERE, null, ex);
            DialogoCodefac.mensaje("Error","Error de comunicacion con el servidor",DialogoCodefac.MENSAJE_INCORRECTO);
        }
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite, RemoteException {
         Boolean confirmacion = DialogoCodefac.dialogoPregunta("Alerta", "Está seguro que desea eliminar el transportista?", DialogoCodefac.MENSAJE_ADVERTENCIA);
        if (confirmacion) {
            try {
                transportistaService.eliminar(transportista);
                DialogoCodefac.mensaje("Correcto","El transportista se elimino correctamente",DialogoCodefac.MENSAJE_CORRECTO);
            } catch (RemoteException ex) {
                Logger.getLogger(Transportista.class.getName()).log(Level.SEVERE, null, ex);
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
        
    }

    @Override
    public String getURLAyuda() {
        return "http://www.cf.codesoft-ec.com/ayuda#eclientes";
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        Map<Integer, Boolean> permisos = new HashMap<Integer, Boolean>();
        permisos.put(GeneralPanelInterface.BOTON_NUEVO, true);
        permisos.put(GeneralPanelInterface.BOTON_GRABAR, true);
        permisos.put(GeneralPanelInterface.BOTON_BUSCAR, true);
        permisos.put(GeneralPanelInterface.BOTON_ELIMINAR, true);
        permisos.put(GeneralPanelInterface.BOTON_IMPRIMIR, true);
        permisos.put(GeneralPanelInterface.BOTON_AYUDA, true);
        return permisos;
    }

    @Override
    public List<String> getPerfilesPermisos() {
        List<String> permisos = new ArrayList<String>();
        permisos.add(Perfil.PERFIl_ADMINISTRADOR);
        permisos.add(Perfil.PERFIl_OPERADOR);
        return permisos;
    }

    @Override
    public BuscarDialogoModel obtenerDialogoBusqueda() {
        TransportistaBusquedaDialogo transportistaBusquedaDialogo = new TransportistaBusquedaDialogo();
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(transportistaBusquedaDialogo);
        return buscarDialogoModel;
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        transportista = (Transportista) entidad;
        cargarDatosTransportista();
    }
    
    public void setearDatos()
    {
        transportista.setApellidos(getTxtApellidos().getText());
        transportista.setCorreoElectronico(getTxtCorreo().getText());
        transportista.setDireccion(getTxtDireccion().getText());
        transportista.setIdentificacion(getTxtIdentificacion().getText());
        transportista.setNombreComercial(getTxtNombreComercial().getText());
        transportista.setNombres(getTxtNombres().getText());
        transportista.setPlacaVehiculo(getTxtPlaca().getText());
        transportista.setRazonSocial(getTxtRazonSocial().getText());
        transportista.setTelefonoCelular(getTxtCelular().getText());
        transportista.setTelefonoConvencional(getTxtTelefono().getText());
    }
    
    private boolean prevalidar() {
        if (getCmbEstado().getSelectedItem().equals(TransportistaEnumEstado.ELIMINADO)) {
            DialogoCodefac.mensaje("Advertencia", "Si desea eliminar el transportista seleccione el boton de eliminar, seleccione otro estado", DialogoCodefac.MENSAJE_ADVERTENCIA);
            return false;
        }
        return true;
    }
    
    @Override
    public Transportista getResult() throws ExcepcionCodefacLite {
        try {
            grabar();
            return transportista;
        } catch (RemoteException ex) {
            Logger.getLogger(TransportistaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void postConstructorExterno(Object[] parametros) {
        
    }
    
    public void cargarDatosTransportista(){
        getTxtApellidos().setText(transportista.getApellidos());
        getTxtCelular().setText(transportista.getTelefonoCelular());
        getTxtCorreo().setText(transportista.getCorreoElectronico());
        getTxtDireccion().setText(transportista.getDireccion());
        getTxtIdentificacion().setText(transportista.getIdentificacion());
        getTxtNombreComercial().setText(transportista.getNombreComercial());
        getTxtNombres().setText(transportista.getNombres());
        getTxtPlaca().setText(transportista.getPlacaVehiculo());
        getTxtRazonSocial().setText(transportista.getRazonSocial());
        getTxtTelefono().setText(transportista.getTelefonoConvencional());
  
    }
    
    public void cargarCombos()
    {
        getCmbEstado().removeAllItems();
        for(TransportistaEnumEstado tee : TransportistaEnumEstado.values())
        {
            getCmbEstado().addItem(tee);
        }
    }
}
