/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.configuraciones.model;

import ec.com.codesoft.codefaclite.configuraciones.busqueda.PuntoVentaBusquedaDialogo;
import ec.com.codesoft.codefaclite.configuraciones.panel.PuntoVentaPanel;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoVenta;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
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
public class PuntoVentaModel extends PuntoVentaPanel{
    
    PuntoVenta puntoVenta;

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        valoresIniciales();
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite, RemoteException {
        
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, RemoteException {
        try {
            setearDatos();
            ServiceFactory.getFactory().getPuntoVentaServiceIf().grabar(puntoVenta);
            DialogoCodefac.mensaje(MensajeCodefacSistema.AccionesFormulario.GUARDADO);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(SucursalModel.class.getName()).log(Level.SEVERE, null, ex);
            DialogoCodefac.mensaje("Error",ex.getMessage(),DialogoCodefac.MENSAJE_INCORRECTO);
        }
    }

    @Override
    public void editar() throws ExcepcionCodefacLite, RemoteException {
        
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
        puntoVenta=new PuntoVenta();
        
        getCmbSucursal().setSelectedIndex(0);
        getCmbTipoFacturacion().setSelectedIndex(0);
        
        getTxtPuntoEmision().setValue(new Integer(0));
        
        getTxtFactura().setValue(new Integer(0));
        getTxtNotaCredito().setValue(new Integer(0));
        getTxtNotaDebito().setValue(new Integer(0));
        getTxtGuiaRemision().setValue(new Integer(0));
        getTxtRetenciones().setValue(new Integer(0));
        getTxtRetenciones().setValue(new Integer(0));
        getTxtNotaVenta().setValue(new Integer(0));
        
        
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
        return new BuscarDialogoModel(new PuntoVentaBusquedaDialogo());
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        PuntoVenta puntoVenta=(PuntoVenta) entidad;
        
        getTxtDescripcion().setText(puntoVenta.getDescripcion());
        getTxtPuntoEmision().setValue(puntoVenta.getPuntoEmision());
        getTxtFactura().setValue(puntoVenta.getSecuencialFactura());
        getTxtNotaCredito().setValue(puntoVenta.getSecuencialFactura());
        getTxtNotaDebito().setValue(puntoVenta.getSecuencialFactura());
        getTxtGuiaRemision().setValue(puntoVenta.getSecuencialFactura());
        getTxtRetenciones().setValue(puntoVenta.getSecuencialFactura());
        getTxtNotaVenta().setValue(puntoVenta.getSecuencialFactura());
        
        getCmbSucursal().setSelectedItem(puntoVenta.getSucursal());
        getCmbTipoFacturacion().setSelectedItem(puntoVenta.getTipoFacturacionEnum());
        
    }

    private void valoresIniciales() {
        
        getCmbTipoFacturacion().removeAllItems();
        ComprobanteEntity.TipoEmisionEnum[] tipos = ComprobanteEntity.TipoEmisionEnum.values();
        for (ComprobanteEntity.TipoEmisionEnum tipo : tipos) {
            getCmbTipoFacturacion().addItem(tipo);
        }
        
        try {
            List<Sucursal> sucursales=ServiceFactory.getFactory().getSucursalServiceIf().obtenerTodos(); //TODO: Remplazar por un metodo que solo obtenga sucursal activas
            getCmbSucursal().removeAllItems();
            for (Sucursal sucursal : sucursales) {
                getCmbSucursal().addItem(sucursal);
            }
            
        } catch (RemoteException ex) {
            Logger.getLogger(PuntoVentaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private void setearDatos() {
        puntoVenta.setDescripcion(getTxtDescripcion().getText());
        puntoVenta.setPuntoEmision((Integer) getTxtPuntoEmision().getValue());
        
        puntoVenta.setSecuencialFactura((Integer) getTxtPuntoEmision().getValue());
        puntoVenta.setSecuencialNotaCredito((Integer) getTxtNotaCredito().getValue());
        puntoVenta.setSecuencialNotaDebito((Integer) getTxtNotaDebito().getValue());
        puntoVenta.setSecuencialGuiaRemision((Integer) getTxtGuiaRemision().getValue());
        puntoVenta.setSecuencialRetenciones((Integer) getTxtRetenciones().getValue());
        puntoVenta.setSecuencialNotaVenta((Integer) getTxtNotaVenta().getValue());
        
        Sucursal sucursal=(Sucursal) getCmbSucursal().getSelectedItem();
        puntoVenta.setSucursal(sucursal);
        
        ComprobanteEntity.TipoEmisionEnum tipoEmisionEnum=(ComprobanteEntity.TipoEmisionEnum) getCmbTipoFacturacion().getSelectedItem();
        puntoVenta.setTipoFacturacion(tipoEmisionEnum.getCodigoSri());
        
        
    }
    
}
