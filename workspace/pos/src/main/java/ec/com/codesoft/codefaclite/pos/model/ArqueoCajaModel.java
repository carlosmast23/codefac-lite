/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.pos.model;

import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.interfaces.ControladorVistaIf;
import ec.com.codesoft.codefaclite.controlador.vista.factura.FacturaModelControlador;
import ec.com.codesoft.codefaclite.controlador.vista.factura.ModelControladorAbstract;
import ec.com.codesoft.codefaclite.controlador.vista.pos.ArqueoCajaModelControlador;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.DialogInterfacePanel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;import java.util.Map;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfazPostConstructPanel;
import ec.com.codesoft.codefaclite.pos.panel.ArqueoCajaPanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.ArqueoCaja;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.CajaSession;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.IngresoCaja;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Robert
 */
public class ArqueoCajaModel extends ArqueoCajaPanel implements DialogInterfacePanel<ArqueoCaja>, InterfazPostConstructPanel, ControladorVistaIf, ArqueoCajaModelControlador.SwingIf
{       
    private ArqueoCajaModelControlador controlador = new ArqueoCajaModelControlador(DialogoCodefac.intefaceMensaje, session, this, ModelControladorAbstract.TipoVista.ESCRITORIO);

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        try {
            this.controlador.iniciar();
        } catch (ExcepcionCodefacLite ex) {
            Logger.getLogger(ArqueoCajaModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(ArqueoCajaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    public void cargarDatosPantalla(Object entidad) {
        ArqueoCaja arqueoCaja = (ArqueoCaja) entidad;
        getjTextValorTeorico().setText(arqueoCaja.getValorTeorico().toString());
        getjTextValorFisico().setText(arqueoCaja.getValorFisico().toString());
        getjComboEstado().setSelectedItem(arqueoCaja.getEstadoEnum());
    }

    @Override
    public InterfaceModelFind obtenerDialogoBusqueda() {
        return this.controlador.obtenerDialogoBusqueda();
    }

    @Override
    public ModelControladorAbstract getControladorVista() {
        return controlador;
    }
    
    /**
     * Funciones comunes
     */
    
    public void listenerHora()
    {
        getjTimeHoraRevision().addChangeListener(new  ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }
      
    
    /**
     * Get and Setter
     * @return 
     */
    
    public ArqueoCajaModelControlador getControlador() {
        return controlador;
    }
    
    public void setControlador(ArqueoCajaModelControlador controlador) {
        this.controlador = controlador;
    }
    
    @Override
    public Date getFechaRevision() {
        java.util.Date dateTemp = (java.util.Date) getjDateFechaRevision().getDate();
        return new Date(dateTemp.getTime());
    }

    @Override
    public void setFechaRevision(Date fechaRevision) {        
        getjDateFechaRevision().setDate(UtilidadesFecha.castDateSqlToUtil(fechaRevision));
    }

    @Override
    public Date getHoraRevision() {
        java.util.Date timeTemp = (java.util.Date) getjTimeHoraRevision().getValue();
        return new Date(timeTemp.getTime());
    }

    @Override
    public void setHoraRevision(Date horaRevision) {
        java.util.Date hora = UtilidadesFecha.castDateSqlToUtil(horaRevision);
        getjTimeHoraRevision().setValue(hora);
    }

    @Override
    public ArqueoCaja getResult() throws ExcepcionCodefacLite 
    {
        try {
            controlador.grabar();
            return controlador.getArqueoCaja();
        } catch (ExcepcionCodefacLite ex) {
            Logger.getLogger(ArqueoCajaModel.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } catch (RemoteException ex) {
            Logger.getLogger(ArqueoCajaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void postConstructorExterno(Object[] parametros) 
    {
        CajaSession cajaSession = (CajaSession)parametros[0];
        this.controlador.getArqueoCaja().setCajaSession(cajaSession);
        this.controlador.getArqueoCaja().setUsuario((Usuario) parametros[1]);
        BigDecimal totalCajas = (BigDecimal) parametros[2];
        this.controlador.getArqueoCaja().setValorTeorico(totalCajas.toString());
        getjTextValorTeorico().setText(this.controlador.getArqueoCaja().getValorTeorico());
    }
    
}


