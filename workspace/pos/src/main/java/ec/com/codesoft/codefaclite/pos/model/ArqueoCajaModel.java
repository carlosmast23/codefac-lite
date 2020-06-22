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
import ec.com.codesoft.codefaclite.utilidades.swing.UtilidadesComboBox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Date;
import java.sql.Timestamp;
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
public class ArqueoCajaModel extends ArqueoCajaPanel implements ControladorVistaIf, ArqueoCajaModelControlador.SwingIf
{
    private ArqueoCaja arqueoCaja;
    private GeneralEnumEstado estado;
    private String valorTeorico;
    private BigDecimal valorFisico;
    private Date fechaRevision;
    private Date horaRevision;
    
    private ArqueoCajaModelControlador controlador = new ArqueoCajaModelControlador(DialogoCodefac.intefaceMensaje, session, this, ModelControladorAbstract.TipoVista.ESCRITORIO);
            
    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        this.controlador.iniciar();
        listenerCombos();
        listenerFecha();
        listenerHora();
        valoresIniciales();
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite, RemoteException {
        this.controlador.nuevo();
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, RemoteException {
        this.controlador.grabar();
        this.setearDatos();
        
    }

    @Override
    public void editar() throws ExcepcionCodefacLite, RemoteException {
        this.controlador.editar();
        this.setearDatos();
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
        this.controlador.cargarDatosPantalla(entidad);
        getjComboEstado().setSelectedItem(this.controlador.getInterfaz().getEstado());
        getjTextValorFisico().setText(this.controlador.getInterfaz().getValorFisico().toString());
        getjTextValorTeorico().setText(this.controlador.getInterfaz().getValorTeorico());
        
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
    
    public void valoresIniciales(){
        this.getjComboEstado().setSelectedItem(GeneralEnumEstado.ACTIVO);
        this.getjDateFechaRevision().setDate(new java.util.Date());
        this.getjTextValorFisico().setText(BigDecimal.ZERO.toString());
        this.getjTextValorTeorico().setText("");
    }
    
    public void setearDatos(){
        this.estado = (GeneralEnumEstado) this.getjComboEstado().getSelectedItem();
        java.util.Date dateTemp = (Date) getjDateFechaRevision().getDate();
        this.fechaRevision = new Date(dateTemp.getTime());
        java.util.Date timeTemp = (Date) getjDateFechaRevision().getDate();
        this.horaRevision = new Date(timeTemp.getTime());
        this.valorFisico = new BigDecimal(this.getjTextValorFisico().getText());
        this.valorTeorico = this.getjTextValorTeorico().getText();
    }
    
    public void listenerHora()
    {
        getjTimeHoraRevision().addChangeListener(new  ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }
    
    public void listenerCombos()
    {
        getjComboEstado().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                estado = (GeneralEnumEstado) getjComboEstado().getSelectedItem();
            }
        });
    }
    
    public void listenerFecha(){
        getjDateFechaRevision().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(getjDateFechaRevision().getDate() != null){
                    java.util.Date dateTemp = (Date) getjDateFechaRevision().getDate();
                    fechaRevision = new Date(dateTemp.getTime());
                }  
            }
        });
    }
    
    /**
     * Get and Setter
     * @return 
     */
    
    @Override
    public ArqueoCaja getArqueoCaja() {
        return this.arqueoCaja;
    }

    @Override
    public void setArqueoCaja(ArqueoCaja arqueoCaja) {
        this.arqueoCaja = arqueoCaja;
    }

    @Override
    public Date getFechaRevision() {
        return fechaRevision;
    }

    @Override
    public void setFechaRevision(Date fechaRevision) {
        this.fechaRevision = fechaRevision;
    }

    @Override
    public Date getHoraRevision() {
        return this.horaRevision;
    }

    @Override
    public void setHoraRevision(Date fechaRevision) {
        this.horaRevision = fechaRevision;
    }

    @Override
    public String getValorTeorico() {
        return this.valorTeorico;
    }

    @Override
    public void setValorTeorico(String valorTeorico) {
        this.valorTeorico = valorTeorico;        
    }

    @Override
    public BigDecimal getValorFisico() {
        return this.valorFisico;
    }

    @Override
    public void setValorFisico(BigDecimal valorFisico) {
        this.valorFisico = valorFisico;
    }

    @Override
    public GeneralEnumEstado getEstado() {
        return estado;
    }

    @Override
    public void setEstado(GeneralEnumEstado estado) {
        this.estado = estado;
    }

    @Override
    public void setEstadosGeneralesVista(GeneralEnumEstado[] estados) {
        UtilidadesComboBox.llenarComboBox(getjComboEstado(), estados);
    }

    
}
