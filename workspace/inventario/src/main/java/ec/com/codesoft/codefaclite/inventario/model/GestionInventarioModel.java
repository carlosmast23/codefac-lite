/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.inventario.model;

import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ProductoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.inventario.panel.GestionInventarioPanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Bodega;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Kardex;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.KardexDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.BodegaServiceIf;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;

/**
 *
 * @author Carlos
 */
public class GestionInventarioModel extends GestionInventarioPanel{
    
    /**
     * Referencia del producto que desean grabar
     */
    private Producto productoSeleccionado;
    
    /**
     * Referencia para almacenar el kardex detalle a grabar
     */
    private KardexDetalle kardexDetalle;

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        valoresIniciales();
        listenerBotones();
       
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, RemoteException {
        
        try {
            setearVariables();
            ServiceFactory.getFactory().getKardexServiceIf().ingresarInventario(kardexDetalle);
            DialogoCodefac.mensaje("Correcto","El proceso de grabo correctamente",DialogoCodefac.MENSAJE_CORRECTO);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(GestionInventarioModel.class.getName()).log(Level.SEVERE, null, ex);
            DialogoCodefac.mensaje("Error",ex.getMessage(),DialogoCodefac.MENSAJE_INCORRECTO);
            throw new ExcepcionCodefacLite("Error");
        }
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
        productoSeleccionado=null;
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
        ProductoBusquedaDialogo productoBusquedaDialogo = new ProductoBusquedaDialogo();
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(productoBusquedaDialogo);
        return buscarDialogoModel;
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        Producto producto=(Producto) entidad;
        
    }

    private void valoresIniciales() 
    {
        //Agregar los tipos de datos permitidos
        getCmbTipoDocumento().removeAllItems();
        getCmbTipoDocumento().addItem(TipoDocumentoEnum.AGREGAR_MERCADERIA_MANUAL);
        getCmbTipoDocumento().addItem(TipoDocumentoEnum.QUITAR_MERCADERIA_MANUAL);
        
        try {
                       
            getCmbBodega().removeAllItems();
            BodegaServiceIf servicioBodega = ServiceFactory.getFactory().getBodegaServiceIf();
            List<Bodega> bodegas = servicioBodega.obtenerTodos();
            for (Bodega bodega : bodegas) {
                getCmbBodega().addItem(bodega);                
            }
        } catch (RemoteException ex) {
            Logger.getLogger(GestionInventarioModel.class.getName()).log(Level.SEVERE, null, ex);
        }

        
    }

    private void listenerBotones() {
        getBtnBuscarProducto().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductoBusquedaDialogo buscarBusquedaDialogo = new ProductoBusquedaDialogo();
                BuscarDialogoModel buscarDialogo = new BuscarDialogoModel(buscarBusquedaDialogo);
                buscarDialogo.setVisible(true);
                
                if(buscarDialogo.getResultado()!=null)
                {
                    productoSeleccionado=(Producto) buscarDialogo.getResultado();
                    getTxtProducto().setText(productoSeleccionado.toString());
                    
                }
                
            }
        });
    }
    
    private void setearVariables()
    {
        kardexDetalle=new KardexDetalle();
        kardexDetalle.setCantidad(Integer.parseInt(getTxtCantidad().getText()));
        kardexDetalle.setPrecioUnitario(new BigDecimal(getTxtPrecio().getText()));
        kardexDetalle.recalcularTotal();
        
        
        Kardex kardex=new Kardex();
        
        Bodega bodegaSeleccionada=(Bodega)getCmbBodega().getSelectedItem();
        kardex.setBodega(bodegaSeleccionada);
        kardex.setProducto(productoSeleccionado);
    
        kardexDetalle.setKardex(kardex);
        
    }
    
   
    
}
