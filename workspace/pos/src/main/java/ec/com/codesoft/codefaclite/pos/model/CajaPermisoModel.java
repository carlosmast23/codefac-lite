/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.pos.model;

import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.interfaces.ControladorVistaIf;
import ec.com.codesoft.codefaclite.controlador.vista.factura.ModelControladorAbstract;
import ec.com.codesoft.codefaclite.controlador.vista.pos.CajaPermisoModelControlador;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.pos.panel.CajaPermisoPanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.CajaPermiso;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Robert
 */
public class CajaPermisoModel extends CajaPermisoPanel implements ControladorVistaIf, CajaPermisoModelControlador.SwingIf
{
    private CajaPermisoModelControlador controlador = new CajaPermisoModelControlador(DialogoCodefac.intefaceMensaje, session, this, ModelControladorAbstract.TipoVista.ESCRITORIO);
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        return this.controlador.obtenerDialogoBusqueda();
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        CajaPermiso cajaPermiso = (CajaPermiso) entidad;
        getjTextCaja().setText("" + cajaPermiso.getCaja().getNombre());
        getjTextUsuario().setText("" + cajaPermiso.getUsuario().getNick());
        getjTextAreaDescripcion().setText(cajaPermiso.getDescripcion());
        getjComboEstado().setSelectedItem(cajaPermiso.getEstadoEnum());
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
    public ModelControladorAbstract getControladorVista() {
        return controlador;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    // GET AND SET
    ////////////////////////////////////////////////////////////////////////////

    public CajaPermisoModelControlador getControlador() {
        return controlador;
    }

    public void setControlador(CajaPermisoModelControlador controlador) {
        this.controlador = controlador;
    }

    @Override
    public String getDescripcionText() {
        return getjTextAreaDescripcion().getText();
    }    
    
    
}