/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.prestamos.model;

import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.PrestamoTablaInteresDialogo;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
//import ec.com.codesoft.codefaclite.controlador.mensajes.CodefacMsj;
//import ec.com.codesoft.codefaclite.controlador.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.prestamos.panel.TablaInteresPanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.PrestamoTablaInteres;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.MensajeCodefacSistema;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class TablaInteresModel extends TablaInteresPanel{
    
    private PrestamoTablaInteres prestamoTablaInteres;

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
            seterarValoresVista();
            ServiceFactory.getFactory().getPrestamoTablaInteresServiceIf().grabar(prestamoTablaInteres);
            DialogoCodefac.mensaje(MensajeCodefacSistema.AccionesFormulario.GUARDADO);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(TablaInteresModel.class.getName()).log(Level.SEVERE, null, ex);
            DialogoCodefac.mensaje(ex.getMessage(),DialogoCodefac.MENSAJE_INCORRECTO);
            throw new ExcepcionCodefacLite(ex.getMessage());
        }
    }

    @Override
    public void editar() throws ExcepcionCodefacLite, RemoteException {
        try {
            seterarValoresVista();
            ServiceFactory.getFactory().getPrestamoTablaInteresServiceIf().editar(prestamoTablaInteres);
            DialogoCodefac.mensaje(MensajeCodefacSistema.AccionesFormulario.EDITADO);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(TablaInteresModel.class.getName()).log(Level.SEVERE, null, ex);
            DialogoCodefac.mensaje(ex.getMessage(),DialogoCodefac.MENSAJE_INCORRECTO);
            throw new ExcepcionCodefacLite(ex.getMessage());
        }
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite, RemoteException {
        try {
            ServiceFactory.getFactory().getPrestamoTablaInteresServiceIf().eliminar(prestamoTablaInteres);
            DialogoCodefac.mensaje(MensajeCodefacSistema.AccionesFormulario.ELIMINADO_CORRECTAMENTE);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(TablaInteresModel.class.getName()).log(Level.SEVERE, null, ex);
            DialogoCodefac.mensaje(ex.getMessage(),DialogoCodefac.MENSAJE_INCORRECTO);
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
        prestamoTablaInteres=new PrestamoTablaInteres();
        getTxtInteres().setText("0");
        getTxtMeses().setValue(0);
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
    public InterfaceModelFind obtenerDialogoBusqueda() {
        return new PrestamoTablaInteresDialogo(session.getEmpresa());
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
       prestamoTablaInteres=(PrestamoTablaInteres) entidad;
        getTxtMeses().setValue(prestamoTablaInteres.getMeses());
        getTxtInteres().setText(prestamoTablaInteres.getPorcentaje().toString());
        
    }
    
    private void seterarValoresVista()
    {
        Integer meses=(Integer) getTxtMeses().getValue();
        BigDecimal porcentaje=new BigDecimal(getTxtInteres().getText());
        
        prestamoTablaInteres.setMeses(meses);
        prestamoTablaInteres.setPorcentaje(porcentaje);
        prestamoTablaInteres.setSucursal(session.getSucursal());
    }
    
}
