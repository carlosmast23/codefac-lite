/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.pos.model;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.vista.pos.CajaModelControlador;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;import java.util.Map;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.controlador.interfaces.ControladorVistaIf;
import ec.com.codesoft.codefaclite.controlador.vista.factura.ModelControladorAbstract;
import ec.com.codesoft.codefaclite.pos.panel.CajaPanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.Caja;
import ec.com.codesoft.codefaclite.utilidades.swing.UtilidadesComboBox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Robert
 */
public class CajaModel extends CajaPanel implements ControladorVistaIf, CajaModelControlador.SwingIf{
     
    private CajaModelControlador controlador=null;
    private List<Sucursal> sucursalList;
    //private PuntoEmision puntoEmision;
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        controlador=new CajaModelControlador(DialogoCodefac.intefaceMensaje, session, this,ModelControladorAbstract.TipoVista.ESCRITORIO);
        
        //Cargar datos iniciales al combo
        try {
            sucursalList = ServiceFactory.getFactory().getSucursalServiceIf().consultarActivosPorEmpresa(session.getEmpresa());
            UtilidadesComboBox.llenarComboBox(getjComboSucursal(), sucursalList);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(CajaModelControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        this.controlador.iniciar();
        listenerCombos();
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
        this.controlador.editar();
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite, RemoteException {
        this.controlador.eliminar();
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
        Caja caja = (Caja) entidad;
        getjTextNombre().setText(caja.getNombre());
        getjComboSucursal().setSelectedItem(caja.getSucursal());
        getjComboPuntoEmision().setSelectedItem(caja.getPuntoEmision());
        getCmbPuntoEmision2().setSelectedItem(caja.getPuntoEmision2());
        getjTextAreaDescripcion().setText(caja.getDescripcion());
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
       
    public void listenerCombos()
    {
        getjComboSucursal().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Sucursal sucursal = (Sucursal) getjComboSucursal().getSelectedItem();
                    List<PuntoEmision> puntoEmisionLista;
                    puntoEmisionLista = ServiceFactory.getFactory().getPuntoVentaServiceIf().obtenerActivosPorSucursal(sucursal);                   
                    UtilidadesComboBox.llenarComboBox(getjComboPuntoEmision(), puntoEmisionLista); 
                    UtilidadesComboBox.llenarComboBox(getCmbPuntoEmision2(), puntoEmisionLista,true);
                    //puntoEmision = (PuntoEmision) getjComboPuntoEmision().getSelectedItem();
                } catch (ServicioCodefacException | RemoteException ex) {
                    Logger.getLogger(CajaModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    @Override
    public PuntoEmision getPuntoEmision() {
        return (PuntoEmision) getjComboPuntoEmision().getSelectedItem();
    }
    
    @Override
    public PuntoEmision getPuntoEmision2() {
        return (PuntoEmision) getCmbPuntoEmision2().getSelectedItem();
    }

    @Override
    public String getDescripcion() {
        return getjTextAreaDescripcion().getText();
    }

    ////////////////////////////////////////////////////////////////////////////
    // GET AND SET
    ////////////////////////////////////////////////////////////////////////////

    public CajaModelControlador getControlador() {
        return controlador;
    }

    public void setControlador(CajaModelControlador controlador) {
        this.controlador = controlador;
    }
    
}
