/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.pos.model;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.vista.pos.CajaModelControlador;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.pos.panel.CajaPanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.Caja;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.utilidades.swing.UtilidadesComboBox;
import es.mityc.firmaJava.libreria.utilidades.Utilidades;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class CajaModel extends CajaPanel implements CajaModelControlador.Interface{
    
    private CajaModelControlador controlador;
    private Caja caja;
    
    private GeneralEnumEstado estado;
    private Sucursal sucursal;
    private PuntoEmision puntoEmision;
    private String nombre;
    private String descripcion;
    
    private List<PuntoEmision> puntosEmisionLista;
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        this.controlador = new CajaModelControlador(DialogoCodefac.intefaceMensaje, session,this);
        try {
            this.controlador.iniciar();
            listenerCombos();
            valoresIniciales();
        } catch (ServicioCodefacException ex) {
            throw  new ExcepcionCodefacLite(ex.getMessage());
        }
    }
    
    @Override
    public void nuevo() throws ExcepcionCodefacLite, RemoteException {
        this.controlador.nuevo();
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, RemoteException {
        try {
            this.controlador.grabar();
        } catch (ServicioCodefacException ex) {
            throw  new ExcepcionCodefacLite(ex.getMessage());
        }
    }

    @Override
    public void editar() throws ExcepcionCodefacLite, RemoteException {
        try{
            this.controlador.editar();
        }catch(ServicioCodefacException ex){
            throw new ExcepcionCodefacLite(ex.getMessage());
        }
        
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite, RemoteException {
        try{
            this.controlador.eliminar();
        }catch(ServicioCodefacException e){
            throw new ExcepcionCodefacLite(e.getMessage());
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
    public void limpiar(){
        try {
            this.controlador.iniciar();
        } catch (RemoteException ex) {
            Logger.getLogger(CajaModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(CajaModel.class.getName()).log(Level.SEVERE, null, ex);
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
    public BuscarDialogoModel obtenerDialogoBusqueda() {
        return this.controlador.obtenerDialogoBusqueda();
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        this.caja = (Caja) entidad;
        getjTextNombre().setText(this.caja.getNombre());
        getjTextAreaDescripcion().setText(this.caja.getDescripcion());
        getjComboSucursal().setSelectedItem(this.caja.getSucursal());
        getjComboPuntoEmision().setSelectedItem(this.caja.getPuntoEmision());
        String descripcionTemp = (this.caja.getDescripcion() != null)? this.caja.getDescripcion() : "";
        getjTextAreaDescripcion().setText(descripcionTemp);
        
    }

    @Override
    public void setEstadosGeneralesVista(GeneralEnumEstado[] estadoGeneralesLista) {
        UtilidadesComboBox.llenarComboBox(getjComboEstado(), estadoGeneralesLista);
    }

    @Override
    public Caja getCaja() {
        return this.caja;
    }

    @Override
    public void setCaja(Caja caja) {
        this.caja = caja;
    }

    @Override
    public void setSucursalesVista(List<Sucursal> sucursalLista) {
        UtilidadesComboBox.llenarComboBox(getjComboSucursal(), sucursalLista);
    }

    @Override
    public void setPuntosEmisionVista(List<PuntoEmision> puntosEmisionLista) {
        this.puntosEmisionLista = puntosEmisionLista;
    }

    @Override
    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    @Override
    public Sucursal getSucursal() {
        return this.sucursal;
    }

    @Override
    public PuntoEmision getPuntoEmision() {
        return this.puntoEmision;
    }
    
    @Override
    public void setPuntoEmision(PuntoEmision puntoEmision) {
        this.puntoEmision = puntoEmision;
    }

    @Override
    public GeneralEnumEstado getEnumEstado() {
        return this.estado;
    }
  
    @Override
    public void setEnumEstado(GeneralEnumEstado generalEnumEstado) {
        this.estado = generalEnumEstado;
    }
    
    public void listenerCombos(){
        getjComboEstado().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                estado = (GeneralEnumEstado) getjComboEstado().getSelectedItem();
            }
        });
        getjComboSucursal().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                valoresSinSeleccionCombo();
            }
        });
        getjComboPuntoEmision().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                puntoEmision = (PuntoEmision) getjComboPuntoEmision().getSelectedItem();    
            }
        });
        
    }

    @Override
    public String getNombre() {
        return getjTextNombre().getText();
    }

    @Override
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String getDescripcion() {
        return getjTextAreaDescripcion().getText();
    }

    @Override
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public void valoresIniciales(){
        this.estado = (GeneralEnumEstado) getjComboEstado().getSelectedItem();
        valoresSinSeleccionCombo();
    }
    
    public void valoresSinSeleccionCombo(){
        try {
            sucursal = (Sucursal) getjComboSucursal().getSelectedItem();
            List<PuntoEmision> puntoEmisionLista;
            puntoEmisionLista = ServiceFactory.getFactory().getPuntoVentaServiceIf().obtenerActivosPorSucursal(sucursal);
            UtilidadesComboBox.llenarComboBox(getjComboPuntoEmision(), puntoEmisionLista);
            puntoEmision = (PuntoEmision) getjComboPuntoEmision().getSelectedItem();    
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(CajaModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(CajaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
