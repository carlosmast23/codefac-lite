/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.pos.model;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.vista.pos.CajaModelControlador;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.controlador.interfaces.ControladorVistaIf;
import ec.com.codesoft.codefaclite.controlador.vista.crm.EjemploModelControlador;
import ec.com.codesoft.codefaclite.controlador.vista.factura.ModelControladorAbstract;
import ec.com.codesoft.codefaclite.pos.panel.CajaPanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.Caja;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CajaEnum;
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
 * @author Robert
 */
public class CajaModel extends CajaPanel implements ControladorVistaIf, CajaModelControlador.SwingIf{
    
    private Caja caja;

    private CajaEnum estado;
    private Sucursal sucursal;
    private PuntoEmision puntoEmision;
    private String nombre;
    private String descripcion;

    private List<PuntoEmision> puntosEmisionLista;
    
    
    private CajaModelControlador controlador=new CajaModelControlador(DialogoCodefac.intefaceMensaje, session, this,ModelControladorAbstract.TipoVista.ESCRITORIO);
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        this.controlador.iniciar();
        listenerCombos();
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
        this.enviarControlador();
    }

    @Override
    public void editar() throws ExcepcionCodefacLite, RemoteException {
        this.controlador.editar();
        this.setearDatos();
        this.enviarControlador();
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
            Logger.getLogger(CajaModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(CajaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        this.controlador.cargarDatosPantalla(entidad);
        getjTextNombre().setText(this.controlador.getInterfaz().getCaja().getNombre());
        getjComboSucursal().setSelectedItem(this.controlador.getInterfaz().getCaja().getSucursal());
        getjComboPuntoEmision().setSelectedItem(this.controlador.getInterfaz().getCaja().getPuntoEmision());
        String descripcionTemp = (this.controlador.getInterfaz().getCaja().getDescripcion() != null)? this.controlador.getInterfaz().getCaja().getDescripcion() : "";
        getjTextAreaDescripcion().setText(descripcionTemp);
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ModelControladorAbstract getControladorVista() {
        return controlador;
    }
    
     public void valoresIniciales(){
        this.estado = (CajaEnum) getjComboEstado().getSelectedItem();
        valoresSinSeleccionCombo();
    }
       
    public void listenerCombos(){
        getjComboEstado().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                estado = (CajaEnum) getjComboEstado().getSelectedItem();
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
    
    public void setearDatos(){
        this.estado = ((CajaEnum) getjComboEstado().getSelectedItem() != null ) ? (CajaEnum) getjComboEstado().getSelectedItem() : null ;
        this.sucursal = ((Sucursal) getjComboSucursal().getSelectedItem() != null ) ? (Sucursal) getjComboSucursal().getSelectedItem() : null ;
        this.puntoEmision = ((PuntoEmision) getjComboSucursal().getSelectedItem() != null ) ? (PuntoEmision) getjComboSucursal().getSelectedItem() : null ;
        this.nombre = (getjTextNombre().getText() != null) ? getjTextNombre().getText() : "";
        this.descripcion = (getjTextAreaDescripcion().getText() != null) ? getjTextAreaDescripcion().getText() : "";
    }
    
    public void enviarControlador(){
        this.controlador.getInterfaz().setEnumEstado(this.estado);
        this.controlador.getInterfaz().setSucursal(this.sucursal);
        this.controlador.getInterfaz().setPuntoEmision(this.puntoEmision);
        this.controlador.getInterfaz().setNombre(this.nombre);
        this.controlador.getInterfaz().setDescripcion(this.descripcion);
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
    public CajaEnum getEnumEstado() {
        return this.estado;
    }

    @Override
    public void setEnumEstado(CajaEnum generalEnumEstado) {
        this.estado = generalEnumEstado;
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
   
    @Override
    public void setEstadosGeneralesVista(CajaEnum[] estadoGeneralesLista) {
        UtilidadesComboBox.llenarComboBox(getjComboEstado(), estadoGeneralesLista);
    }
}
