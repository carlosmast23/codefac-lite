/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.inventario.model;

import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ProductoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ProductoBusquedaDialogoFactory;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ProductoInventarioEspecificoDialogo;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;import java.util.Map;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.inventario.panel.TransferenciaBodegasPanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Bodega;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Kardex;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.KardexDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.KardexItemEspecifico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.BodegaServiceIf;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
public class TransferenciaBodegasModel extends TransferenciaBodegasPanel{
    
    /**
     * Referencia del producto que desean grabar
     */
    private Producto productoSeleccionado;
    
    private Bodega bodegaOrigen;
    
    private Bodega bodegaDestino;

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        valoresIniciales();
        listenerBotones();
        listenerComboBox();
       
    }
    
    private void listenerComboBox()
    {
        getCmbBodegaOrigen().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarCostoTransferencia();
            }
        });
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, RemoteException {
        
        BigDecimal precio=null;
        if(getTxtPrecio().getText()!=null && !getTxtPrecio().getText().trim().isEmpty())
        {
            precio=new BigDecimal(getTxtPrecio().getText());
        }
        
        try {
            setearVariables();
            ServiceFactory.getFactory().getKardexServiceIf().transferirProductoBodegas(
                    productoSeleccionado, 
                    bodegaOrigen, 
                    bodegaDestino, 
                    getTxtDescripcion().getText(), 
                    new BigDecimal(getTxtCantidad().getText()),
                    precio, 
                    new java.sql.Date(getCmbFechaIngreso().getDate().getTime()));
            
            DialogoCodefac.mensaje("Correcto","La transferencia de bodegas se realizo correctamente",DialogoCodefac.MENSAJE_CORRECTO);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(GestionInventarioModel.class.getName()).log(Level.SEVERE, null, ex);
            DialogoCodefac.mensaje("Error",ex.getMessage(),DialogoCodefac.MENSAJE_INCORRECTO);
            throw new ExcepcionCodefacLite(ex.getMessage());
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
        getCmbFechaIngreso().setDate(UtilidadesFecha.getFechaHoy());
        getCmbBodegaOrigen().setSelectedIndex(0);
        getCmbBodegaDestino().setSelectedIndex(0);
        getTxtCantidad().setText("1"); //Seteo por defecto 1 por es la cantidad minima a transferir
        
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
        permisos.put(GeneralPanelInterface.BOTON_BUSCAR, false);
        permisos.put(GeneralPanelInterface.BOTON_ELIMINAR, false);
        permisos.put(GeneralPanelInterface.BOTON_IMPRIMIR, false);
        permisos.put(GeneralPanelInterface.BOTON_AYUDA, false);
        return permisos;
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public InterfaceModelFind obtenerDialogoBusqueda() {
        //ProductoBusquedaDialogo productoBusquedaDialogo = new ProductoBusquedaDialogo(session.getEmpresa());
        //BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(productoBusquedaDialogo);
        //return buscarDialogoModel;
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        //Producto producto=(Producto) entidad;
        
    }

    private void valoresIniciales() 
    {        
        try {
                       
            getCmbBodegaOrigen().removeAllItems();
            BodegaServiceIf servicioBodega = ServiceFactory.getFactory().getBodegaServiceIf();
            
            //Cargar solo las bodegas activas de la misma sucursal desde la cual se puede enviar los productos
            List<Bodega> bodegaSucursalList= servicioBodega.obtenerActivosPorSucursal(session.getSucursal());
            for (Bodega bodega : bodegaSucursalList) 
            {
                getCmbBodegaOrigen().addItem(bodega);
            }
            
            
            List<Bodega> bodegas = servicioBodega.obtenerActivosPorEmpresa(session.getEmpresa());
            for (Bodega bodega : bodegas) {
                //getCmbBodegaOrigen().addItem(bodega);                
                getCmbBodegaDestino().addItem(bodega);                
            }
            
            
            
            
        } catch (RemoteException ex) {
            Logger.getLogger(GestionInventarioModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(GestionInventarioModel.class.getName()).log(Level.SEVERE, null, ex);
        }

        
    }

    private void listenerBotones() {
        
        getBtnBuscarProductoEspecifico().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductoInventarioEspecificoDialogo dialogoBusqueda=new ProductoInventarioEspecificoDialogo(productoSeleccionado);
                BuscarDialogoModel buscarDialogo = new BuscarDialogoModel(dialogoBusqueda);
                buscarDialogo.setVisible(true);

                if (buscarDialogo.getResultado() != null) {
                    KardexItemEspecifico itemEspecifico = (KardexItemEspecifico) buscarDialogo.getResultado();
                    getTxtProductoEspecifico().setText(itemEspecifico.getCodigoEspecifico());

                }
                
            }
        });
        
        getBtnBuscarProducto().addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                ProductoBusquedaDialogoFactory busquedaFactory=new ProductoBusquedaDialogoFactory(session.getSucursal(), ProductoBusquedaDialogoFactory.ResultadoEnum.PRODUCTO);
                InterfaceModelFind buscarBusquedaDialogo = busquedaFactory.construirDialogo();
                //ProductoBusquedaDialogo buscarBusquedaDialogo = new ProductoBusquedaDialogo(session.getEmpresa(),true,true);
                BuscarDialogoModel buscarDialogo = new BuscarDialogoModel(buscarBusquedaDialogo);
                buscarDialogo.setVisible(true);
                
                if(buscarDialogo.getResultado()!=null)
                {
                    Kardex kardex= (Kardex) buscarDialogo.getResultado();                    
                    productoSeleccionado=kardex.getProducto();
                    getTxtProducto().setText(productoSeleccionado.toString());
                    buscarCostoTransferencia();
                }
                
            }
        });
    }
    
    private void buscarCostoTransferencia()
    {
        if(this.productoSeleccionado!=null)
        {
            //Buscar si existe una bodega que por defecto este seleccionada
            Bodega bodegaSeleccionada=(Bodega) getCmbBodegaOrigen().getSelectedItem();
            if(bodegaSeleccionada!=null)
            {
                try {
                    //buscar el kardex detalle para sacar el costo para poder hacer una transferencia
                    Kardex kardex=ServiceFactory.getFactory().getKardexServiceIf().buscarKardexPorProductoyBodegayLote(bodegaSeleccionada, productoSeleccionado,null);
                    if(kardex!=null)
                    {
                        getTxtPrecio().setText(kardex.getPrecioUltimo()+"");
                    }
                    
                } catch (RemoteException ex) {
                    Logger.getLogger(TransferenciaBodegasModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    private void setearVariables()
    {
        bodegaOrigen=(Bodega) getCmbBodegaOrigen().getSelectedItem();
        bodegaDestino=(Bodega) getCmbBodegaDestino().getSelectedItem();        
        
        
    }
    
   
    
}
