/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.com.codesoft.codefaclite.controlador.vista.inventario;

import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.CategoriaProductoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.DescuentoBusqueda;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.LoteBusqueda;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ProductoBusquedaDialogoFactory;
import ec.com.codesoft.codefaclite.controlador.vista.factura.ModelControladorAbstract;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import java.util.Map;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.interfaces.VistaCodefacIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CategoriaProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Descuento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.DescuentoProductoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefacInterface;
import ec.com.codesoft.codefaclite.utilidades.list.UtilidadesLista;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DellWin10
 */
public class DescuentoControlador extends ModelControladorAbstract<DescuentoControlador.ICommon, DescuentoControlador.ISwing, DescuentoControlador.IWeb> implements VistaCodefacIf {

    private Descuento descuento;

    private List<Descuento.AlcanceEnum> alcanceList;
    private List<TipoBusquedaProductoEnum> tipoBusquedaProductoList;
    private TipoBusquedaProductoEnum tipoBusquedaProductoSeleccionado;
    private DescuentoProductoDetalle productoSeleccionado;

    public DescuentoControlador(MensajeVistaInterface mensajeVista, SessionCodefacInterface session, ICommon interfaz, TipoVista tipoVista) {
        super(mensajeVista, session, interfaz, tipoVista);
    }

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        descuento = new Descuento();
        cargarDatosIniciales();
    }

    private void cargarDatosIniciales() {
        alcanceList = UtilidadesLista.arrayToList(Descuento.AlcanceEnum.values());
        tipoBusquedaProductoList=UtilidadesLista.arrayToList(TipoBusquedaProductoEnum.values());        
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, RemoteException {

        try {
            descuento = ServiceFactory.getFactory().getDescuentoSeviceIf().grabar(descuento, session.getEmpresa(), session.getUsuario());
            mostrarMensaje(MensajeCodefacSistema.AccionesFormulario.GUARDADO);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(DescuentoControlador.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensaje(new CodefacMsj(ex.getMessage(), CodefacMsj.TipoMensajeEnum.ERROR));
            throw new ExcepcionCodefacLite(ex.getMessage());
        }
    }

    @Override
    public void editar() throws ExcepcionCodefacLite, RemoteException {
        try {
            session.getUsuario();
            ServiceFactory.getFactory().getDescuentoSeviceIf().editar(descuento, session.getEmpresa(), session.getUsuario());
            mostrarMensaje(MensajeCodefacSistema.AccionesFormulario.EDITADO);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(DescuentoControlador.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensaje(new CodefacMsj(ex.getMessage(), CodefacMsj.TipoMensajeEnum.ERROR));
            throw new ExcepcionCodefacLite(ex.getMessage());
        }
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite, RemoteException {
        try {

            if (!dialogoPregunta(MensajeCodefacSistema.Preguntas.ELIMINAR_REGISTRO)) {
                throw new ExcepcionCodefacLite("Acción cancelada de eliminar");
            }

            ServiceFactory.getFactory().getDescuentoSeviceIf().eliminar(descuento);
            mostrarMensaje(MensajeCodefacSistema.AccionesFormulario.ELIMINADO_CORRECTAMENTE);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(DescuentoControlador.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensaje(new CodefacMsj(ex.getMessage(), CodefacMsj.TipoMensajeEnum.ERROR));
            throw new ExcepcionCodefacLite(ex.getMessage());
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
    public void limpiar() {
        descuento = new Descuento();
        getInterfaz().limpiarPantalla();
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
        return new DescuentoBusqueda(session.getEmpresa());
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        this.descuento = (Descuento) entidad;
    }
    
    public void agregarProductoListener()
    {
        try 
        {
            cargarProductosVista();
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(DescuentoControlador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(DescuentoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cargarProductosVista() throws ServicioCodefacException, RemoteException {
        
        switch (this.tipoBusquedaProductoSeleccionado) {
            case INDIVIDUAL:
                ProductoBusquedaDialogoFactory busquedaFactory = new ProductoBusquedaDialogoFactory(session.getSucursal(), ProductoBusquedaDialogoFactory.ResultadoEnum.PRODUCTO);
                Producto productoTmp = (Producto) busquedaFactory.ejecutarDialogo();
                descuento.agregarProducto(productoTmp);
                break;

            case CATEGORIA:                
                CategoriaProductoBusquedaDialogo catProdBusquedaDialogo = new CategoriaProductoBusquedaDialogo(session.getEmpresa());
                BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(catProdBusquedaDialogo);
                buscarDialogoModel.setVisible(true);
                CategoriaProducto catProducto = (CategoriaProducto) buscarDialogoModel.getResultado();
                List<Producto> productoList=ServiceFactory.getFactory().getProductoServiceIf().buscarPorCategoria(catProducto);
                descuento.agregarProductoPorLote(productoList);
                break;

            case TODOS:
                List<Producto> productoList2=ServiceFactory.getFactory().getProductoServiceIf().obtenerTodosActivos(session.getEmpresa());
                descuento.agregarProductoPorLote(productoList2);
                //TODO: Consultar todos
                break;
        }
        
        /*if(this.tipoBusquedaProductoSeleccionado)
        {
        
        }*/
    }

    ///////////////////////////////////////////////////////////////////////////////
    public Descuento getDescuento() {
        return descuento;
    }

    public void setDescuento(Descuento descuento) {
        this.descuento = descuento;
    }

    public List<Descuento.AlcanceEnum> getAlcanceList() {
        return alcanceList;
    }

    public void setAlcanceList(List<Descuento.AlcanceEnum> alcanceList) {
        this.alcanceList = alcanceList;
    }

    public List<TipoBusquedaProductoEnum> getTipoBusquedaProductoList() {
        return tipoBusquedaProductoList;
    }

    public void setTipoBusquedaProductoList(List<TipoBusquedaProductoEnum> tipoBusquedaProductoList) {
        this.tipoBusquedaProductoList = tipoBusquedaProductoList;
    }

    public TipoBusquedaProductoEnum getTipoBusquedaProductoSeleccionado() {
        return tipoBusquedaProductoSeleccionado;
    }

    public void setTipoBusquedaProductoSeleccionado(TipoBusquedaProductoEnum tipoBusquedaProductoSeleccionado) {
        this.tipoBusquedaProductoSeleccionado = tipoBusquedaProductoSeleccionado;
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public DescuentoProductoDetalle getProductoSeleccionado() {
        return productoSeleccionado;
    }

    public void setProductoSeleccionado(DescuentoProductoDetalle productoSeleccionado) {
        this.productoSeleccionado = productoSeleccionado;
    }
    
    

    /*public void listenerBotonBuscarProducto() {
        ProductoBusquedaDialogo busqueda = new ProductoBusquedaDialogo(EnumSiNo.SI, session.getEmpresa(),true,true);
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(busqueda);
        buscarDialogoModel.setVisible(true);
        if (buscarDialogoModel.getResultado() != null) {
            desc8.setProducto((Producto) buscarDialogoModel.getResultado());
        }
    }*/

 /*public Lote getLote() {
        return lote;
    }

    public void setLote(Lote lote) {
        this.lote = lote;
    }*/
    ////////////////////////////////////////////////////////////////////////////
    ///                 CLASES ADICIONALES
    /////////////////////////////////////////////////////////////////////////////
    public enum TipoBusquedaProductoEnum {
        INDIVIDUAL,
        CATEGORIA,
        TODOS;

    }

    ////////////////////////////////////////////////////////////////////////////
    ///                          INTERFACES                                 ////
    ////////////////////////////////////////////////////////////////////////////
    public interface ICommon {

        public void limpiarPantalla();
    }

    public interface ISwing extends ICommon {

    }

    public interface IWeb extends ICommon {

    }
}
