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
import ec.com.codesoft.codefaclite.controlador.vista.pos.ArqueoCajaModelControlador;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.pos.panel.ArqueoCajaPanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.ArqueoCaja;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CajaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.swing.UtilidadesComboBox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.eclipse.persistence.internal.sessions.UnitOfWorkImpl;

/**
 *
 * @author Robert
 */
public class ArqueoCajaModel extends ArqueoCajaPanel implements ControladorVistaIf, ArqueoCajaModelControlador.SwingIf
{       
    private ArqueoCajaModelControlador controlador = new ArqueoCajaModelControlador(DialogoCodefac.intefaceMensaje, session, this, ModelControladorAbstract.TipoVista.ESCRITORIO);

    public void iniciar() throws ExcepcionCodefacLite {
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
    public void eliminar() throws ExcepcionCodefacLite {
        //TODO: verificar que eliminar debe funcionar directamente en el contralador
        this.controlador.eliminar();
    }

    @Override
    public void imprimir() throws ExcepcionCodefacLite   {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizar() throws ExcepcionCodefacLite   {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void limpiar() {
        try {
            this.controlador.iniciar();
        } catch (ExcepcionCodefacLite ex) {
            Logger.getLogger(ArqueoCajaModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
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
}
