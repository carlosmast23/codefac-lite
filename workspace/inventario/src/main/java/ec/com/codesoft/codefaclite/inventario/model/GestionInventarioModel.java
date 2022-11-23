/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.inventario.model;

import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.LoteBusqueda;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ProductoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;import java.util.Map;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ObserverUpdateInterface;
import ec.com.codesoft.codefaclite.inventario.panel.GestionInventarioPanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Bodega;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Compra;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Kardex;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.KardexDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.KardexItemEspecifico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Lote;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.FechaFormatoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.OperadorNegocioEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.VentanaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.BodegaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.ParametroUtilidades;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import es.mityc.firmaJava.libreria.utilidades.UtilidadFechas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import org.apache.commons.net.ntp.TimeStamp;

/**
 *
 * @author Carlos
 */
public class GestionInventarioModel extends GestionInventarioPanel{
    
    /**
     * Referencia del producto que desean grabar
     */
    private Producto productoSeleccionado;
    
    private Lote lote;
    
    /**
     * Referencia para almacenar el kardex detalle a grabar
     */
    private KardexDetalle kardexDetalle;

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        valoresIniciales();
        listenerBotones();
        kardexDetalle=new KardexDetalle();
       
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite, RemoteException {
        getCmbTipoDocumento().setSelectedIndex(0);
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, RemoteException {
        
        try {
            setearVariables();
            ServiceFactory.getFactory().getKardexServiceIf().ingresarInventario(kardexDetalle,lote);
            DialogoCodefac.mensaje("Correcto","El proceso de grabo correctamente",DialogoCodefac.MENSAJE_CORRECTO);
            verificarActualizarPreciosVenta();
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(GestionInventarioModel.class.getName()).log(Level.SEVERE, null, ex);
            DialogoCodefac.mensaje("Error",ex.getMessage(),DialogoCodefac.MENSAJE_INCORRECTO);
            throw new ExcepcionCodefacLite("Error");
        }
    }
    
    private void verificarActualizarPreciosVenta()
    {
        try {
            /*
<<<<<<< HEAD
            Producto productoActualizado= ServiceFactory.getFactory().getProductoServiceIf().buscarPorId(kardexDetalle.getKardex().getProducto().getIdProducto());
            if(productoActualizado!=null && productoActualizado.getActualizarPrecioEnum()!=null && productoActualizado.getActualizarPrecioEnum().equals(EnumSiNo.SI))
=======*/
            if (ParametroUtilidades.comparar(session.getEmpresa(), ParametroCodefac.ADVERTENCIA_ACTUALIZAR_COSTO, EnumSiNo.SI)) 
            {
                Producto productoActualizado= ServiceFactory.getFactory().getProductoServiceIf().buscarPorId(kardexDetalle.getKardex().getProducto().getIdProducto());
                if(productoActualizado!=null)
                {
                    Boolean respuesta=DialogoCodefac.dialogoPregunta(new CodefacMsj("El producto tiene cambios en los costos, desea actualizar los precios de venta ?", CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
                    if(respuesta)
                    {
                        List<Producto> productoList=new ArrayList<Producto>();
                        productoList.add(productoActualizado);
                        Object[] parametros = {productoList};
                        panelPadre.crearVentanaCodefac(VentanaEnum.UTILIDAD_PRECIO, true, parametros);
                    }
                }
            }
        } catch (RemoteException ex) {
            Logger.getLogger(IngresoInventarioModel.class.getName()).log(Level.SEVERE, null, ex);
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
        lote=null;
        productoSeleccionado=null;
        kardexDetalle=new KardexDetalle();
        getCmbFechaIngreso().setDate(UtilidadesFecha.getFechaHoy());
        if(getCmbBodega().getItemCount()>0)
        {
            getCmbBodega().setSelectedIndex(0);
        }
        //getCmbTipoDocumento().setSelectedIndex(0);
        getTxtDescripcion().setText("");
        getTxtCantidad().setText("1");
        cargarDatosPantalla();
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
        permisos.put(GeneralPanelInterface.BOTON_ELIMINAR, true);
        permisos.put(GeneralPanelInterface.BOTON_IMPRIMIR, true);
        permisos.put(GeneralPanelInterface.BOTON_AYUDA, true);
        return permisos;
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /*@Override
    public BuscarDialogoModel obtenerDialogoBusqueda() {
        ProductoBusquedaDialogo productoBusquedaDialogo = new ProductoBusquedaDialogo(session.getEmpresa());
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(productoBusquedaDialogo);
        return buscarDialogoModel;
    }*/

    /*@Override
    public void cargarDatosPantalla(Object entidad) {
        Producto producto=(Producto) entidad;
        
    }*/

    private void valoresIniciales() 
    {
        //Agregar los tipos de datos permitidos
        getCmbTipoDocumento().removeAllItems();
        getCmbTipoDocumento().addItem(TipoDocumentoEnum.AGREGAR_MERCADERIA_MANUAL);
        getCmbTipoDocumento().addItem(TipoDocumentoEnum.QUITAR_MERCADERIA_MANUAL);
        getCmbTipoDocumento().addItem(TipoDocumentoEnum.AJUSTE_EXACTO_INVENTARIO);
        
        try {
                       
            getCmbBodega().removeAllItems();
            BodegaServiceIf servicioBodega = ServiceFactory.getFactory().getBodegaServiceIf();
            List<Bodega> bodegas = servicioBodega.obtenerActivosPorSucursal(session.getSucursal());
            for (Bodega bodega : bodegas) {
                getCmbBodega().addItem(bodega);                
            }
        } catch (RemoteException ex) {
            Logger.getLogger(GestionInventarioModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(GestionInventarioModel.class.getName()).log(Level.SEVERE, null, ex);
        }

        
    }
    
    private void cargarDatosPantalla()
    {
        if(kardexDetalle!=null)
        {
            if(lote!=null)
            {
                getTxtLoteNombre().setText(lote.getCodigo());
            }
            else
            {
                getTxtLoteNombre().setText("");
           }
        }
    }

    private void listenerBotones() 
    {
        getBtnBuscarProducto().addActionListener(listenerBuscarProducto);       
        getBtnBuscarLote().addActionListener(listenerBuscarLote);        
        getBtnCrearLote().addActionListener(listenerCrearLote);        
    }
    
    private ActionListener listenerCrearLote=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            
            Object[] paramPostConstruct = new Object[1];
            paramPostConstruct[0] = productoSeleccionado;
            
            ObserverUpdateInterface observerCreate=new ObserverUpdateInterface<Lote>() 
            {
                @Override
                public void updateInterface(Lote entity) {
                    if(entity!=null)
                    {
                        lote = entity;
                        cargarDatosPantalla();
                    }

                }
            };
            panelPadre.crearDialogoCodefac(observerCreate, VentanaEnum.LOTE, false,paramPostConstruct,formularioActual);        
        }
    };
    
    private ActionListener listenerBuscarLote=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            LoteBusqueda busqueda=new LoteBusqueda(session.getEmpresa(),productoSeleccionado);
            BuscarDialogoModel buscarDialogo = new BuscarDialogoModel(busqueda);            
            buscarDialogo.setVisible(true);

            if (buscarDialogo.getResultado() != null) 
            {
                lote= (Lote) buscarDialogo.getResultado();
            }
            
            cargarDatosPantalla();
        }
    };
    
    private ActionListener listenerBuscarProducto=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            ProductoBusquedaDialogo buscarBusquedaDialogo = new ProductoBusquedaDialogo(EnumSiNo.SI,session.getEmpresa(),true,true);
            BuscarDialogoModel buscarDialogo = new BuscarDialogoModel(buscarBusquedaDialogo);            
            buscarDialogo.setVisible(true);

            if (buscarDialogo.getResultado() != null) 
            {
                limpiar();
                productoSeleccionado = (Producto) buscarDialogo.getResultado();                
                getTxtProducto().setText(productoSeleccionado.toString());
                
                getTxtCodigoUnico().setEnabled(false);
                getTxtCantidad().setEnabled(true);                
                if(productoSeleccionado.getGarantiaEnum().equals(EnumSiNo.SI))
                {
                    getTxtCodigoUnico().setEnabled(true);
                    getTxtCantidad().setText("1");
                    getTxtCantidad().setEnabled(false);
                }

            }

        }
    };
    
    private void setearVariables()
    {
        //kardexDetalle=new KardexDetalle();
        kardexDetalle.setCantidad(new BigDecimal(getTxtCantidad().getText()));
        //Si no tiene ingresado un precio unitario grabo como null
        String precioUnitarioTxt=(!getTxtPrecio().getText().isEmpty())?getTxtPrecio().getText():"0";
        kardexDetalle.setPrecioUnitario(new BigDecimal(precioUnitarioTxt));
        kardexDetalle.recalcularTotalSinGarantia();
        
        //Setear el documento que esta usando el usuario 
        TipoDocumentoEnum tipoDocumentoEnum=(TipoDocumentoEnum) getCmbTipoDocumento().getSelectedItem();
        kardexDetalle.setCodigoTipoDocumento(tipoDocumentoEnum.getCodigo());
        
        //Fecha de ingreso 
        
        java.util.Date fechaUtil=getCmbFechaIngreso().getDate();
        kardexDetalle.setFechaIngreso(UtilidadesFecha.getFechaHoyTimeStamp());
        kardexDetalle.setFechaDocumento(new java.sql.Date(fechaUtil.getTime()));
        kardexDetalle.setDescripcion(getTxtDescripcion().getText());
        
        
        Kardex kardex=new Kardex();
        
        Bodega bodegaSeleccionada=(Bodega)getCmbBodega().getSelectedItem();
        kardex.setBodega(bodegaSeleccionada);
        kardex.setProducto(productoSeleccionado);
        
        
    
        kardexDetalle.setKardex(kardex);
        
        //Agregar un detalle personalizado si el prodicto maneja GARANTIA
        if(productoSeleccionado.getGarantiaEnum().equals(EnumSiNo.SI))
        {
            KardexItemEspecifico kardexItemEspecifico=new KardexItemEspecifico();
            kardexItemEspecifico.setCodigoEspecifico(getTxtCodigoUnico().getText());
            kardexItemEspecifico.setEstadoEnum(GeneralEnumEstado.ACTIVO);
            kardexItemEspecifico.setObservaciones(getTxtDescripcion().getText());
            kardexDetalle.addDetalle(kardexItemEspecifico);
        }
        
    }
    
     @Override
    public InterfaceModelFind obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buscar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
