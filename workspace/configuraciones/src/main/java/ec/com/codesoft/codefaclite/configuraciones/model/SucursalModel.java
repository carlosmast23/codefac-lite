/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.configuraciones.model;

import ec.com.codesoft.codefaclite.configuraciones.busqueda.SucursalBusquedaDialogo;
import ec.com.codesoft.codefaclite.configuraciones.panel.SucursalPanel;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.controlador.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;

/**
 *
 * @author Carlos
 */
public class SucursalModel extends SucursalPanel {
    
    private Sucursal sucursal;

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        cargarValoresIniciales();
        
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite, RemoteException {
       
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, RemoteException {
        
        try {
            setearDatos();
            ServiceFactory.getFactory().getSucursalServiceIf().grabar(sucursal);
            DialogoCodefac.mensaje(MensajeCodefacSistema.AccionesFormulario.GUARDADO);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(SucursalModel.class.getName()).log(Level.SEVERE, null, ex);
            DialogoCodefac.mensaje("Error",ex.getMessage(),DialogoCodefac.MENSAJE_INCORRECTO);
        }
    }

    @Override
    public void editar() throws ExcepcionCodefacLite, RemoteException {
         try {
            setearDatos();
            ServiceFactory.getFactory().getSucursalServiceIf().editar(sucursal);
            DialogoCodefac.mensaje(MensajeCodefacSistema.AccionesFormulario.EDITADO);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(SucursalModel.class.getName()).log(Level.SEVERE, null, ex);
            DialogoCodefac.mensaje("Error",ex.getMessage(),DialogoCodefac.MENSAJE_INCORRECTO);
        }
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite, RemoteException {
        setearDatos();
        ServiceFactory.getFactory().getSucursalServiceIf().eliminar(sucursal);
        DialogoCodefac.mensaje(MensajeCodefacSistema.AccionesFormulario.ELIMINADO_CORRECTAMENTE);
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
        sucursal = new Sucursal();
        getTxtCodigoEstablecimiento().setValue(new Integer(0));
        getCmbTipo().setSelectedIndex(0);
        getCmbEmpresa().setSelectedIndex(0);        
    }

    @Override
    public String getURLAyuda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BuscarDialogoModel obtenerDialogoBusqueda() {
        return new BuscarDialogoModel(new SucursalBusquedaDialogo());
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        sucursal=(Sucursal) entidad;
        getTxtNombre().setText(sucursal.getNombre());
        getTxtTelefono().setText(sucursal.getTelefono());
        getTxtDireccion().setText(sucursal.getDirecccion());
        getTxtEmail().setText(sucursal.getEmail());
        getTxtCodigoEstablecimiento().setValue(sucursal.getCodigoSucursal());
        
        Sucursal.TipoSucursalEnum tipoSucursal=sucursal.getTipoSucursalEnum();
        getCmbTipo().setSelectedItem(tipoSucursal);
        
        getCmbEmpresa().setSelectedItem(sucursal.getEmpresa());
    }
    
    private void setearDatos()
    {
        sucursal.setNombre(getTxtNombre().getText());
        sucursal.setTelefono(getTxtTelefono().getText());
        sucursal.setDirecccion(getTxtDireccion().getText());
        sucursal.setEmail(getTxtEmail().getText());

        sucursal.setCodigoSucursal((Integer) getTxtCodigoEstablecimiento().getValue());
        Sucursal.TipoSucursalEnum tipoSucursalEnum=(Sucursal.TipoSucursalEnum) getCmbTipo().getSelectedItem();
        sucursal.setTipo(tipoSucursalEnum.getCodigo());
        
        Empresa empresaSeleccionado=(Empresa) getCmbEmpresa().getSelectedItem();
        sucursal.setEmpresa(empresaSeleccionado);
        
        
    }

    private void cargarValoresIniciales() {
        
        //Cargar los tipos de datos
        getCmbTipo().removeAllItems();
        for (Sucursal.TipoSucursalEnum tipo : Sucursal.TipoSucursalEnum.values()) {
            getCmbTipo().addItem(tipo);
        }
        
        //Cargar la empresa activa
        getCmbEmpresa().removeAllItems();
        getCmbEmpresa().addItem(session.getEmpresa());
    }
    
}