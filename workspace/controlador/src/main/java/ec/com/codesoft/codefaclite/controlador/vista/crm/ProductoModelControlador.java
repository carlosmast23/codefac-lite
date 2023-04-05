/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vista.crm;

import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ProductoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ProductoBusquedaDialogoFactory;
import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.utilidades.UtilidadesImagenesCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.controlador.vista.factura.ModelControladorAbstract;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;import java.util.Map;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.interfaces.VistaCodefacIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CasaComercial;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CategoriaProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Impuesto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ImpuestoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Kardex;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.MarcaProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PresentacionProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ProductoPresentacionDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SegmentoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.TipoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModoProcesarEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoProductoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ModoSistemaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefacInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.CategoriaProductoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ImpuestoDetalleServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ImpuestoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PresentacionProductoServiceIf;
import ec.com.codesoft.codefaclite.utilidades.swing.UtilidadesComboBox;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesImpuestos;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class ProductoModelControlador extends ModelControladorAbstract<ProductoModelControlador.CommonIf,ProductoModelControlador.SwingIf,ProductoModelControlador.WebIf> implements VistaCodefacIf{
    
    private List<EnumSiNo> imprimirCodigoBarrasList;
    private List<EnumSiNo> llevaInventarioList;
    private List<EnumSiNo> garantiaList;
    private List<TipoProductoEnum> tipoProductosList;
    private List<CategoriaProducto> categoriaProductosList;
    //private List<PresentacionProducto> presentacionProductosList;
    private List<ImpuestoDetalle> ivaList;
    private List<ImpuestoDetalle> iceList;
    private List<ImpuestoDetalle> irbpnrList;
    private List<IvaOpcionEnum> ivaOpcionList;
    private List<MarcaProducto> marcaProductoList;
    private List<CasaComercial> casaComercialList;
    private List<TipoProducto> tipoProductoList;
    private List<SegmentoProducto> segmentoProductoList;
    
    //private Interfaz interfaz;
    private CategoriaProducto categoriaSeleccionada;
    //private PresentacionProducto presentacionSeleccionada;
    private ImpuestoDetalle ivaSeleccionado;
    private ImpuestoDetalle iceSeleccionado;
    private ImpuestoDetalle irbpnrSeleccionado;
    private IvaOpcionEnum ivaOpcionSeleccionado;
    private IvaOpcionEnum ivaOpcion2;
    private IvaOpcionEnum ivaOpcion3;
    private IvaOpcionEnum ivaOpcion4;
    private IvaOpcionEnum ivaOpcion5;
    private IvaOpcionEnum ivaOpcion6;
    
    private Boolean generarCodigoAutomatico;
    //private ProductoPresentacionDetalle productoPresentacionDetalle;
    //private MarcaProducto marcaProductoSeleccionado;
    
    public Producto producto;
    

    public ProductoModelControlador(MensajeVistaInterface mensajeVista, SessionCodefacInterface session,ProductoModelControlador.CommonIf interfaz,TipoVista tipoVista) {
        super(mensajeVista, session,interfaz,tipoVista);
    }
    
    
    
    public void iniciarCombosBox()
    {
        //ImpuestoServiceIf impuestoService = ServiceFactory.getFactory().getImpuestoServiceIf();
        //ImpuestoDetalleServiceIf impuestoDetalleService = ServiceFactory.getFactory().getImpuestoDetalleServiceIf();
        
        
        
        //getComboIce().setEditable(true);
        //getComboIce().setSelectedItem("Seleccione : ");
        
        //listaImpuestosDetalle = new ArrayList<ImpuestoDetalle>();
        //Impuesto irbpnr = impuestoService.obtenerImpuestoPorCodigo(Impuesto.IRBPNR);
        //for (ImpuestoDetalle impuesto : irbpnr.getDetalleImpuestos()) {
        //    listaImpuestosDetalle.add(impuesto);
        //}
        //interfaz.llenarComboIrbpnr(irbpnr.getDetalleImpuestos());
        
        //Agregar combo de garantia
        //interfaz.llenarCmbGarantia(EnumSiNo.values());
        //getComboIrbpnr().setEditable(true);
        //getComboIrbpnr().setSelectedItem("Seleccione: ");
            
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    /**
     * TODO: Cambiar la forma de crear las listas por usando clases internas sabe generar problemas cuando mando por rmi al servidor con error de Serializacion
     * @throws ExcepcionCodefacLite
     * @throws RemoteException 
     */
    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        
        generarCodigoAutomatico=false;
        
        imprimirCodigoBarrasList=Arrays.asList(EnumSiNo.NO,EnumSiNo.SI);
        
        
        //Agregar las opcoiones segun los modulos habilitados
        if (session.getModulos().contains(ModuloCodefacEnum.INVENTARIO) || ParametrosSistemaCodefac.MODO.equals(ModoSistemaEnum.DESARROLLO)) {
            llevaInventarioList=new ArrayList<EnumSiNo>(){
                {
                    add(EnumSiNo.SI);
                    add(EnumSiNo.NO);
                }
            };//new EnumSiNo[]{EnumSiNo.SI, EnumSiNo.NO});
        } else {
            llevaInventarioList=new ArrayList<EnumSiNo>(){
                {
                    add(EnumSiNo.NO);
                }
            };
        }
        
        garantiaList=new ArrayList<EnumSiNo>()
        {
            {
                add(EnumSiNo.NO);
                add(EnumSiNo.SI);
            }
        };
        
        tipoProductosList=new ArrayList<TipoProductoEnum>(){
                {
                    add(TipoProductoEnum.PRODUCTO);
                    add(TipoProductoEnum.EMSAMBLE);
                    add(TipoProductoEnum.SERVICIO);
                }        
        };
        
        ivaOpcionList=new ArrayList<IvaOpcionEnum>()
        {
            {
                add(IvaOpcionEnum.SIN_IVA);
                add(IvaOpcionEnum.CON_IVA);
            }
        };
        
        CategoriaProductoServiceIf catProdService = ServiceFactory.getFactory().getCategoriaProductoServiceIf();
       
        
        
        categoriaProductosList = catProdService.obtenerTodosPorEmpresa(session.getEmpresa()); 
        
        
        try {        
            cargarMarcasActivas();
            casaComercialList=ServiceFactory.getFactory().getCasaComercialServiceIf().obtenerActivosPorEmpresa(session.getEmpresa());
            tipoProductoList=ServiceFactory.getFactory().getTipoProductoServiceIf().obtenerActivosPorEmpresa(session.getEmpresa());
            segmentoProductoList=ServiceFactory.getFactory().getSegmentoProductoServiceIf().obtenerActivosPorEmpresa(session.getEmpresa());
            
            PresentacionProductoServiceIf presentacionService = ServiceFactory.getFactory().getPresentacionProductoServiceIf();
            //presentacionProductosList=presentacionService.obtenerActivosPorEmpresa(session.getEmpresa()); 
            
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ProductoModelControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        cargarDatosIva();
        cargarDatosIce();
        cargarDatosIrbpnr();

    }
    
    public void cargarCategoriasActivas()
    {
        try {
            categoriaProductosList=ServiceFactory.getFactory().getCategoriaProductoServiceIf().obtenerTodosPorEmpresa(session.getEmpresa());
        } catch (RemoteException ex) {
            Logger.getLogger(ProductoModelControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void cargarMarcasActivas()
    {
        try {
            marcaProductoList=ServiceFactory.getFactory().getMarcaProductoServiceIf().obtenerActivosPorEmpresa(session.getEmpresa());
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ProductoModelControlador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(ProductoModelControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void cargarDatosIrbpnr()
    {
        try {
            List<ImpuestoDetalle> listaImpuestosDetalle = new ArrayList<ImpuestoDetalle>();
            Impuesto irbpnr = ServiceFactory.getFactory().getImpuestoServiceIf().obtenerImpuestoPorCodigo(Impuesto.IRBPNR);
            for (ImpuestoDetalle impuesto : irbpnr.getDetalleImpuestos()) {
                listaImpuestosDetalle.add(impuesto);
            }
            irbpnrList=irbpnr.getDetalleImpuestos();
            //interfaz.llenarComboIrbpnr(irbpnr.getDetalleImpuestos());
        } catch (RemoteException ex) {
            Logger.getLogger(ProductoModelControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void cargarDatosIce()
    {
        try {
            List<ImpuestoDetalle> listaImpuestosDetalle = new ArrayList<ImpuestoDetalle>();
            Impuesto ice = ServiceFactory.getFactory().getImpuestoServiceIf().obtenerImpuestoPorCodigo(Impuesto.ICE);
            for (ImpuestoDetalle impuesto : ice.getDetalleImpuestos()) {
                listaImpuestosDetalle.add(impuesto);
            }
            iceList=ice.getDetalleImpuestos();
            //interfaz.llenarComboIce(ice.getDetalleImpuestos());
        } catch (RemoteException ex) {
            Logger.getLogger(ProductoModelControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * TODO: ver si esta parte se puede unificar con un serivico
     * ademas la pantalla de configuracion inicial esta usando una logica similar para unir
     */
    private void cargarDatosIva()
    {        
        try {
            List<ImpuestoDetalle> impuestoDetalleList = ServiceFactory.getFactory().getImpuestoDetalleServiceIf().obtenerIvaVigente();
            ImpuestoDetalle impuestoDefault = null;
            
            String ivaDefecto = ParametrosSistemaCodefac.IVA_DEFECTO; //TODO: Analizar que configuracion vamos a usar por defecto agrego esto para poder usas cuando aun no se ha configurado un iva
            ParametroCodefac parametroIva = session.getParametrosCodefac().get(ParametroCodefac.IVA_DEFECTO);
            if (parametroIva != null) {
                ivaDefecto = parametroIva.valor;
            }
            
            List<ImpuestoDetalle> listaImpuestosDetalle = new ArrayList<ImpuestoDetalle>();
            for (ImpuestoDetalle impuesto : impuestoDetalleList) {
                if (impuesto.getTarifa().toString().equals(ivaDefecto)) {
                    impuestoDefault = impuesto;
                }
                listaImpuestosDetalle.add(impuesto);
            }
            ivaList=impuestoDetalleList;
            ivaSeleccionado=impuestoDefault;
        } catch (RemoteException ex) {
            Logger.getLogger(ProductoModelControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite, RemoteException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, RemoteException {
        try {           
            UtilidadesImagenesCodefac.moverArchivo(producto.getPathFotoTmp(),session.getEmpresa());
            setearValoresProducto(producto);
            validar(producto);            
            producto=ServiceFactory.getFactory().getProductoServiceIf().grabar(producto,generarCodigoAutomatico);
            mostrarMensaje(MensajeCodefacSistema.AccionesFormulario.GUARDADO);
            //DialogoCodefac.mensaje("Datos correctos", "El Producto se guardo correctamente", DialogoCodefac.MENSAJE_CORRECTO);
        } catch (ServicioCodefacException ex) {
            mostrarMensaje(new CodefacMsj(ex.getMessage(), CodefacMsj.TipoMensajeEnum.ERROR));
            //DialogoCodefac.mensaje("Error", ex.getMessage(), DialogoCodefac.MENSAJE_INCORRECTO);
            throw new ExcepcionCodefacLite("Error al grabar");
        } catch (RemoteException ex) {
            Logger.getLogger(ProductoModelControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void editar() throws ExcepcionCodefacLite, RemoteException {
        try {
            
            UtilidadesImagenesCodefac.moverArchivo(producto.getPathFotoTmp(),session.getEmpresa());
            setearValoresProducto(producto);            
            ServiceFactory.getFactory().getProductoServiceIf().editarProducto(producto);
            mostrarMensaje(MensajeCodefacSistema.AccionesFormulario.EDITADO);
            //DialogoCodefac.mensaje("Datos correctos", "El producto se edito correctamente", DialogoCodefac.MENSAJE_CORRECTO);
        } catch (RemoteException ex) {
            Logger.getLogger(ProductoModelControlador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ProductoModelControlador.class.getName()).log(Level.SEVERE, null, ex);
            //DialogoCodefac.mensaje("Datos correctos", ex.getMessage(), DialogoCodefac.MENSAJE_INCORRECTO);
            mostrarMensaje(new CodefacMsj(ex.getMessage(), CodefacMsj.TipoMensajeEnum.ERROR));
            throw new ExcepcionCodefacLite(ex.getMessage());
        }
        
    }
    
 
    @Override
    public void eliminar() throws ExcepcionCodefacLite, RemoteException {
        //if (estadoFormulario.equals(GeneralPanelInterface.ESTADO_EDITAR)) 
        //{
            try {
                Boolean respuesta =dialogoPregunta(new CodefacMsj("Estas seguro que desea eliminar el producto?", CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
                //Boolean respuesta = DialogoCodefac.dialogoPregunta("Alerta", "Estas seguro que desea eliminar el producto?", DialogoCodefac.MENSAJE_ADVERTENCIA);
                if (!respuesta) {
                    throw new ExcepcionCodefacLite("Cancelacion usuario");
                }
                
                ServiceFactory.getFactory().getProductoServiceIf().eliminarProducto(producto,ModoProcesarEnum.NORMAL);
                DialogoCodefac.mensaje(MensajeCodefacSistema.AccionesFormulario.ELIMINADO_CORRECTAMENTE);
            } catch (RemoteException ex) {
                Logger.getLogger(ProductoModelControlador.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ServicioCodefacException ex) {
                
                DialogoCodefac.mensaje("Error",ex.getMessage(),DialogoCodefac.MENSAJE_INCORRECTO);
                Logger.getLogger(ProductoModelControlador.class.getName()).log(Level.SEVERE, null, ex);
                
                //Preguntar si desea procesar el producto de forma forzada
                Boolean continuar = DialogoCodefac.dialogoPregunta(MensajeCodefacSistema.Preguntas.PROCESAR_MODO_FORZADO);
                if(continuar)
                {
                    try {
                        ServiceFactory.getFactory().getProductoServiceIf().eliminarProducto(producto,ModoProcesarEnum.FORZADO);
                        DialogoCodefac.mensaje(MensajeCodefacSistema.AccionesFormulario.ELIMINADO_CORRECTAMENTE);
                    } catch (ServicioCodefacException ex1) {                        
                        Logger.getLogger(ProductoModelControlador.class.getName()).log(Level.SEVERE, null, ex1);
                        DialogoCodefac.mensaje(new CodefacMsj(ex1.getMessage(), CodefacMsj.TipoMensajeEnum.ERROR));
                        throw new ExcepcionCodefacLite(ex.getMessage());
                    }
                }
                else
                {
                    throw new ExcepcionCodefacLite(ex.getMessage());
                }
                
            }
        //}
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
        System.out.println("limpiando controlador producto ...");
        producto=new Producto();
        producto.setGenerarCodigoBarrasEnum(EnumSiNo.NO);
        producto.setManejarInventarioEnum(EnumSiNo.NO);
        producto.setTipoProductoEnum(TipoProductoEnum.PRODUCTO);
        producto.setGarantiaEnum(EnumSiNo.NO);
        producto.setValorUnitario(BigDecimal.ZERO);
        producto.setMarcaProducto(null);
        producto.setAplicacionProducto("");
        
        ivaOpcionSeleccionado=IvaOpcionEnum.SIN_IVA;
        ivaOpcion2=IvaOpcionEnum.SIN_IVA;
        ivaOpcion3=IvaOpcionEnum.SIN_IVA;
        ivaOpcion4=IvaOpcionEnum.SIN_IVA;
        ivaOpcion5=IvaOpcionEnum.SIN_IVA;
        ivaOpcion6=IvaOpcionEnum.SIN_IVA;
        
        categoriaSeleccionada=null;
        //presentacionSeleccionada=null;
        iceSeleccionado=null;
        irbpnrSeleccionado=null;
        generarCodigoAutomatico=false;
        //productoPresentacionDetalle=new ProductoPresentacionDetalle();
        //getInterazEscritorio().setearChkGenerarCodAutomatico(false);
        
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
        //ProductoBusquedaDialogo productoBusquedaDialogo = new ProductoBusquedaDialogo(session.getEmpresa(),null,null);
        ProductoBusquedaDialogoFactory busquedaFactory=new ProductoBusquedaDialogoFactory(session.getSucursal(), ProductoBusquedaDialogoFactory.ResultadoEnum.PRODUCTO);
        
        return (InterfaceModelFind) busquedaFactory.construirDialogo();
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        
        if(entidad instanceof Kardex)
        {
            producto=((Kardex)entidad).getProducto();
        }
        else
        {
            producto=(Producto) entidad;
        }
        
        System.out.println("cargando pantlla producto controlador ...");
        
        
        iceSeleccionado=producto.getCatalogoProducto().getIce();
        ivaSeleccionado=producto.getCatalogoProducto().getIva();
        irbpnrSeleccionado=producto.getCatalogoProducto().getIva();
        categoriaSeleccionada=producto.getCatalogoProducto().getCategoriaProducto();
        
        /*if(producto.getPresentacionList()!=null && producto.getPresentacionList().size()>0)
        {
            productoPresentacionDetalle=producto.obtenerProductoPresentacionPorDefecto();
        }*/
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    //========================================================================//
    //              IMPRIMIR CODIGO DE BARRAS
    //========================================================================//
    public List<EnumSiNo> getImprimirCodigoBarrasList() {
        return imprimirCodigoBarrasList;
    }

    public void setImprimirCodigoBarrasList(List<EnumSiNo> imprimirCodigoBarrasList) {
        this.imprimirCodigoBarrasList = imprimirCodigoBarrasList;
    }

    public List<EnumSiNo> getLlevaInventarioList() {
        return llevaInventarioList;
    }

    public void setLlevaInventarioList(List<EnumSiNo> llevaInventarioList) {
        this.llevaInventarioList = llevaInventarioList;
    }

    public List<TipoProductoEnum> getTipoProductosList() {
        return tipoProductosList;
    }

    public void setTipoProductosList(List<TipoProductoEnum> tipoProductosList) {
        this.tipoProductosList = tipoProductosList;
    }

    public List<CategoriaProducto> getCategoriaProductosList() {
        return categoriaProductosList;
    }

    public void setCategoriaProductosList(List<CategoriaProducto> categoriaProductosList) {
        this.categoriaProductosList = categoriaProductosList;
    }

    /*public List<PresentacionProducto> getPresentacionProductosList() {
        return presentacionProductosList;
    }

    public void setPresentacionProductosList(List<PresentacionProducto> presentacionProductosList) {
        this.presentacionProductosList = presentacionProductosList;
    }*/

    /*public PresentacionProducto getPresentacionSeleccionada() {
        return presentacionSeleccionada;
    }

    public void setPresentacionSeleccionada(PresentacionProducto presentacionSeleccionada) {
        this.presentacionSeleccionada = presentacionSeleccionada;
    }*/

    
    
    public CategoriaProducto getCategoriaSeleccionada() {
        return categoriaSeleccionada;
    }

    public void setCategoriaSeleccionada(CategoriaProducto categoriaSeleccionada) {
        this.categoriaSeleccionada = categoriaSeleccionada;
    }

    public ImpuestoDetalle getIvaSeleccionado() {
        return ivaSeleccionado;
    }

    public void setIvaSeleccionado(ImpuestoDetalle ivaSeleccionado) {
        this.ivaSeleccionado = ivaSeleccionado;
    }

    public List<ImpuestoDetalle> getIvaList() {
        return ivaList;
    }

    public void setIvaList(List<ImpuestoDetalle> ivaList) {
        this.ivaList = ivaList;
    }

    public List<ImpuestoDetalle> getIceList() {
        return iceList;
    }

    public void setIceList(List<ImpuestoDetalle> iceList) {
        this.iceList = iceList;
    }

    public ImpuestoDetalle getIceSeleccionado() {
        return iceSeleccionado;
    }

    public void setIceSeleccionado(ImpuestoDetalle iceSeleccionado) {
        this.iceSeleccionado = iceSeleccionado;
    }

    public List<ImpuestoDetalle> getIrbpnrList() {
        return irbpnrList;
    }

    public void setIrbpnrList(List<ImpuestoDetalle> irbpnrList) {
        this.irbpnrList = irbpnrList;
    }

    public ImpuestoDetalle getIrbpnrSeleccionado() {
        return irbpnrSeleccionado;
    }

    public void setIrbpnrSeleccionado(ImpuestoDetalle irbpnrSeleccionado) {
        this.irbpnrSeleccionado = irbpnrSeleccionado;
    }

    public List<IvaOpcionEnum> getIvaOpcionList() {
        return ivaOpcionList;
    }

    public void setIvaOpcionList(List<IvaOpcionEnum> ivaOpcionList) {
        this.ivaOpcionList = ivaOpcionList;
    }

    public IvaOpcionEnum getIvaOpcionSeleccionado() {
        return ivaOpcionSeleccionado;
    }

    public void setIvaOpcionSeleccionado(IvaOpcionEnum ivaOpcionSeleccionado) {
        this.ivaOpcionSeleccionado = ivaOpcionSeleccionado;
    }

    public IvaOpcionEnum getIvaOpcion2() {
        return ivaOpcion2;
    }

    public void setIvaOpcion2(IvaOpcionEnum ivaOpcion2) {
        this.ivaOpcion2 = ivaOpcion2;
    }

    public IvaOpcionEnum getIvaOpcion3() {
        return ivaOpcion3;
    }

    public void setIvaOpcion3(IvaOpcionEnum ivaOpcion3) {
        this.ivaOpcion3 = ivaOpcion3;
    }

    public IvaOpcionEnum getIvaOpcion4() {
        return ivaOpcion4;
    }

    public void setIvaOpcion4(IvaOpcionEnum ivaOpcion4) {
        this.ivaOpcion4 = ivaOpcion4;
    }

    public IvaOpcionEnum getIvaOpcion5() {
        return ivaOpcion5;
    }

    public void setIvaOpcion5(IvaOpcionEnum ivaOpcion5) {
        this.ivaOpcion5 = ivaOpcion5;
    }

    public IvaOpcionEnum getIvaOpcion6() {
        return ivaOpcion6;
    }

    public void setIvaOpcion6(IvaOpcionEnum ivaOpcion6) {
        this.ivaOpcion6 = ivaOpcion6;
    }
    
    
    

    public List<EnumSiNo> getGarantiaList() {
        return garantiaList;
    }

    public void setGarantiaList(List<EnumSiNo> garantiaList) {
        this.garantiaList = garantiaList;
    }

    public Boolean getGenerarCodigoAutomatico() {
        return generarCodigoAutomatico;
    }

    public void setGenerarCodigoAutomatico(Boolean generarCodigoAutomatico) {
        this.generarCodigoAutomatico = generarCodigoAutomatico;
    }

    public List<MarcaProducto> getMarcaProductoList() {
        return marcaProductoList;
    }

    public void setMarcaProductoList(List<MarcaProducto> marcaProductoList) {
        this.marcaProductoList = marcaProductoList;
    }

    public List<TipoProducto> getTipoProductoList() {
        return tipoProductoList;
    }

    public void setTipoProductoList(List<TipoProducto> tipoProductoList) {
        this.tipoProductoList = tipoProductoList;
    }

    public List<SegmentoProducto> getSegmentoProductoList() {
        return segmentoProductoList;
    }

    public void setSegmentoProductoList(List<SegmentoProducto> segmentoProductoList) {
        this.segmentoProductoList = segmentoProductoList;
    }

    public List<CasaComercial> getCasaComercialList() {
        return casaComercialList;
    }

    public void setCasaComercialList(List<CasaComercial> casaComercialList) {
        this.casaComercialList = casaComercialList;
    }

    /*public ProductoPresentacionDetalle getProductoPresentacionDetalle() {
        return productoPresentacionDetalle;
    }

    public void setProductoPresentacionDetalle(ProductoPresentacionDetalle productoPresentacionDetalle) {
        this.productoPresentacionDetalle = productoPresentacionDetalle;
    }*/

    
    

    private void setearValoresProducto(Producto producto) {
        
        producto.setEstadoEnum(GeneralEnumEstado.ACTIVO);
        
        //Si el tipo que contralo es de escritorio termino de implementar las vistas
        if(tipoVista.equals(TipoVista.ESCRITORIO))
        {
            getInterazEscritorio().setearValoresProducto(producto);
        }

          
        BigDecimal valorUnitario=producto.getValorUnitario();
        BigDecimal precioDistribuidor=producto.getPrecioDistribuidor();
        BigDecimal precioTarjeta=producto.getPrecioTarjeta();
        BigDecimal pvp4=producto.getPvp4();
        BigDecimal pvp5=producto.getPvp5();
        BigDecimal pvp6=producto.getPvp6();
        //valorUnitario = new BigDecimal(getTextValorUnitario().getText());
        
        BigDecimal ivaDefecto=new BigDecimal(session.getParametrosCodefac().get(ParametroCodefac.IVA_DEFECTO).getValor());
        BigDecimal ivaTmp=ivaDefecto.divide(new BigDecimal("100"),2,BigDecimal.ROUND_HALF_UP).add(BigDecimal.ONE);            

        //Si el valor esta incluido el iva calculo el valor sin iva
        if(getIvaOpcionSeleccionado().equals(IvaOpcionEnum.CON_IVA))
        {            
            
            //TODO: Ver si el tema de los decimales se puede hacer configurable
            valorUnitario=valorUnitario.divide(ivaTmp,4,BigDecimal.ROUND_HALF_UP);
            //TODO: Agregar el resto de los calculos para los demas precios
                        
            //pvp4=pvp4.divide(ivaTmp,4,BigDecimal.ROUND_HALF_UP);
            //pvp5=pvp5.divide(ivaTmp,4,BigDecimal.ROUND_HALF_UP);
            //pvp6=pvp6.divide(ivaTmp,4,BigDecimal.ROUND_HALF_UP);
            
        }
        
        if(getIvaOpcion2().equals(IvaOpcionEnum.CON_IVA))
        {
            precioDistribuidor=precioDistribuidor.divide(ivaTmp,4,BigDecimal.ROUND_HALF_UP);
        }
        
        if(getIvaOpcion3().equals(IvaOpcionEnum.CON_IVA))
        {
            precioTarjeta=precioTarjeta.divide(ivaTmp,4,BigDecimal.ROUND_HALF_UP);
        }
        
        if(getIvaOpcion4().equals(IvaOpcionEnum.CON_IVA))
        {
            pvp4=pvp4.divide(ivaTmp,4,BigDecimal.ROUND_HALF_UP);
        }
        
        if(getIvaOpcion5().equals(IvaOpcionEnum.CON_IVA))
        {
            pvp5=pvp5.divide(ivaTmp,4,BigDecimal.ROUND_HALF_UP);
        }
        
        if(getIvaOpcion6().equals(IvaOpcionEnum.CON_IVA))
        {
            pvp6=pvp6.divide(ivaTmp,4,BigDecimal.ROUND_HALF_UP);
        }
        
        
        
        producto.setValorUnitario(valorUnitario);
        producto.setPrecioDistribuidor(precioDistribuidor);
        producto.setPrecioTarjeta(precioTarjeta);
        producto.setPvp4(pvp4);
        producto.setPvp5(pvp5);
        producto.setPvp6(pvp6);
        
        
        CatalogoProducto catalogoProducto=crearCatalogoProducto(producto);
        producto.setCatalogoProducto(catalogoProducto);
        
        producto.setEmpresa(session.getEmpresa());
        
        
        if(producto.getPathFotoTmp()!=null)
        {
            producto.setImagen(producto.getPathFotoTmp().getFileName().toString());
            producto.setPathFotoTmp(null);
        }
        
        //Agregar el producto presentacion al producto      
        /*if(productoPresentacionDetalle!=null && productoPresentacionDetalle.getPresentacionProducto()!=null &&  productoPresentacionDetalle.getCantidad()!=null)
        {
            producto.addPresentacion(productoPresentacionDetalle);
        }*/

    }

    private CatalogoProducto crearCatalogoProducto(Producto producto)
    {
        CatalogoProducto catalogoProducto=new CatalogoProducto();
        
        //Si existe el catalogo previamente creado solo lo cargo para que lo edite
        if(producto.getCatalogoProducto()!=null && producto.getCatalogoProducto().getId()!=null)
        {
            catalogoProducto=producto.getCatalogoProducto();
        }
        
        
        //TODO: Unir codigo
        CategoriaProducto categoriaProducto=categoriaSeleccionada;
        catalogoProducto.setCategoriaProducto(categoriaProducto);
        
        if(this.getIceSeleccionado()!=null && !this.getIceSeleccionado().getClass().equals(String.class))
        {
            ImpuestoDetalle ice= this.getIceSeleccionado();
            catalogoProducto.setIce(ice);
        }
        
        if( this.getIrbpnrSeleccionado()!=null && !this.getIrbpnrSeleccionado().getClass().equals(String.class))
        {
            ImpuestoDetalle ibpnr=this.getIrbpnrSeleccionado();
            catalogoProducto.setIrbpnr(ibpnr);
        }
        
        ImpuestoDetalle iva= this.getIvaSeleccionado();
        catalogoProducto.setIva(iva);
        
        catalogoProducto.setNombre(this.producto.getNombre());
        
        TipoProductoEnum tipoProductoEnum=TipoProductoEnum.PRODUCTO;
        catalogoProducto.setModuloCod(ModuloCodefacEnum.INVENTARIO.getCodigo());
        return catalogoProducto;
    }

    private void validar(Producto producto) throws ExcepcionCodefacLite {
        if(producto.getValorUnitario().equals(BigDecimal.ZERO))
        {
            if(!dialogoPregunta(new CodefacMsj("Desea grabar un producto con valor Cero ?", CodefacMsj.TipoMensajeEnum.ADVERTENCIA)))
            {
                throw  new ExcepcionCodefacLite("Error de validación producto precio cero");
            }
        }
    }
    
    
    
    
    
    
    //========================================================================//
    //              INTERFAZ Y CODIGOS ADICIONALES
    //========================================================================//
    
    public interface CommonIf
    {
        //public void llenarCmbGenerarCodigoBarras(EnumSiNo[] datos);
        //public void llenarCmbTipoProducto(TipoProductoEnum[] tipoProductoList);
        //public void llenarCmbCategoriaProducto(List<CategoriaProducto> catProdList);
        //public void llenarComboIva(List<ImpuestoDetalle> impuestos);
        //public void llenarComboIce(List<ImpuestoDetalle> impuestos);
        //public void llenarComboIrbpnr(List<ImpuestoDetalle> impuestos);
        //public void llenarCmbGarantia(EnumSiNo[] datos);
        
        //public void seleccionarComboIva(ImpuestoDetalle impuesto);
    }
    
    public interface SwingIf extends CommonIf
    {
        public void setearValoresProducto(Producto producto);
        //public void setearChkGenerarCodAutomatico(Boolean valor);
    }
    
    public interface WebIf extends CommonIf
    {
    
    }
    
    public enum IvaOpcionEnum
    {
        SIN_IVA("Sin Iva"),
        CON_IVA("Con Iva");
        
        private String nombre;

        private IvaOpcionEnum(String nombre) {
            this.nombre = nombre;
        }

        @Override
        public String toString() {
            return nombre;
        }
        
        public BigDecimal quitarIva(BigDecimal valor)
        {
            if(nombre.equals(CON_IVA.nombre))
            {
                UtilidadesImpuestos.quitarValorIva(BigDecimal.ZERO,valor,6);
            }
            return valor;
        }
        
        public static IvaOpcionEnum findByToString(String nombre)
        {
            for (IvaOpcionEnum value : IvaOpcionEnum.values()) {
                if(value.toString().equals(nombre))
                {
                    return value;
                }
            }
            return null;
        }
        
    }
}
