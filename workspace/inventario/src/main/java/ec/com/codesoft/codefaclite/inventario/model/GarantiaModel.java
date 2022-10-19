/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.inventario.model;

import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ProductoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ProductoInventarioEspecificoDialogo;
import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.interfaces.ControladorVistaIf;
import ec.com.codesoft.codefaclite.controlador.vista.factura.ModelControladorAbstract;
import ec.com.codesoft.codefaclite.controlador.vista.inventario.GarantiaControlador;
import ec.com.codesoft.codefaclite.controlador.vista.inventario.LoteControlador;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.DialogInterfacePanel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfazPostConstructPanel;
import ec.com.codesoft.codefaclite.inventario.panel.GarantiaPanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Garantia;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.KardexItemEspecifico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Lote;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class GarantiaModel extends GarantiaPanel implements DialogInterfacePanel<Garantia>,InterfazPostConstructPanel,ControladorVistaIf,GarantiaControlador.ISwing {
    
    private GarantiaControlador controlador;
    private Producto producto;
    private KardexItemEspecifico itemEspecifico;

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        controlador=new GarantiaControlador(DialogoCodefac.intefaceMensaje, session, this, ModelControladorAbstract.TipoVista.ESCRITORIO);
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
        producto=new Producto();
        itemEspecifico=new KardexItemEspecifico();
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
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
    public Garantia getResult() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void postConstructorExterno(Object[] parametros) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ModelControladorAbstract getControladorVista() {
        return controlador;
    }

    @Override
    public void limpiarPantalla() {
        limpiar();
    }

    public GarantiaControlador getControlador() {
        return controlador;
    }

    public void setControlador(GarantiaControlador controlador) {
        this.controlador = controlador;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public KardexItemEspecifico getItemEspecifico() {
        return itemEspecifico;
    }

    public void setItemEspecifico(KardexItemEspecifico itemEspecifico) {
        this.itemEspecifico = itemEspecifico;
    }
    
    
    
    public void listenerBuscarProducto() {
        ProductoBusquedaDialogo productoBusquedaDialogo = new ProductoBusquedaDialogo(session.getEmpresa(), null, null);
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(productoBusquedaDialogo);
        buscarDialogoModel.setVisible(true);
        Producto producto=(Producto) buscarDialogoModel.getResultado();
        if(producto!=null)
        {
            this.producto=producto;
            actualizarBindingCompontValues();
        }
    }
    
    public void listenerBuscarProductoEspecifico()
    {
        ProductoInventarioEspecificoDialogo dialogoBusqueda = new ProductoInventarioEspecificoDialogo(producto);
        BuscarDialogoModel buscarDialogo = new BuscarDialogoModel(dialogoBusqueda);
        buscarDialogo.setVisible(true);

        if (buscarDialogo.getResultado() != null) {
            this.itemEspecifico = (KardexItemEspecifico) buscarDialogo.getResultado();
            actualizarBindingCompontValues();

        }
    
    }
    
    
    
}
