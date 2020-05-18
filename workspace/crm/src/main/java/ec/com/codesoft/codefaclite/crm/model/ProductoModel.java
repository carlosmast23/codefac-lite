/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.crm.model;

import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ProductoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.vista.crm.ProductoModelControlador;
import ec.com.codesoft.codefaclite.controlador.vista.crm.ProductoModelControlador.ProductoModelControladorInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.DialogInterfacePanel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfazPostConstructPanel;
import ec.com.codesoft.codefaclite.crm.panel.ProductoForm;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CategoriaProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Impuesto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ImpuestoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ProductoEnsamble;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoProductoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.CategoriaProductoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ImpuestoDetalleServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ImpuestoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ProductoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ModoSistemaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.utilidades.swing.UtilidadesComboBox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author PC
 */
public class ProductoModel extends ProductoForm implements DialogInterfacePanel<Producto> , InterfazPostConstructPanel,ProductoModelControladorInterface {

    private Producto producto;
    private Impuesto impuesto;
    private CategoriaProducto catProducto;

    private ProductoServiceIf productoService;
    private ImpuestoServiceIf impuestoService;
    private ImpuestoDetalleServiceIf impuestoDetalleService;
    private CategoriaProductoServiceIf catProdService;
    //private BigDecimal d;

    /*
    Referencia sobre el producto seleccionado para el ensamble
     */
    private Producto productoEnsamble;
    
    private ProductoModelControlador controlador;

    public ProductoModel() {
                
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        try {
            setearValoresProducto(producto);
            producto=productoService.grabar(producto);
            DialogoCodefac.mensaje("Datos correctos", "El Producto se guardo correctamente", DialogoCodefac.MENSAJE_CORRECTO);
        } catch (ServicioCodefacException ex) {
            DialogoCodefac.mensaje("Error", ex.getMessage(), DialogoCodefac.MENSAJE_INCORRECTO);
            throw new ExcepcionCodefacLite("Error al grabar");
        } catch (RemoteException ex) {
            Logger.getLogger(ProductoModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void editar() throws ExcepcionCodefacLite {
        try {
            
            setearValoresProducto(producto);
            productoService.editarProducto(producto);
            DialogoCodefac.mensaje("Datos correctos", "El producto se edito correctamente", DialogoCodefac.MENSAJE_CORRECTO);
        } catch (RemoteException ex) {
            Logger.getLogger(ProductoModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ProductoModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setearValoresProducto(Producto producto) {
        producto.setCodigoPersonalizado(getTxtCodigoPersonalizado().getText());
        producto.setCodigoEAN(getTxtCodigoEAN().getText());
        producto.setCodigoUPC(getTxtCodigoUPC().getText());
        producto.setEstadoEnum(GeneralEnumEstado.ACTIVO);

          
        BigDecimal valorUnitario=BigDecimal.ZERO;
        producto.setNombre(getTextNombre().getText());
        valorUnitario = new BigDecimal(getTextValorUnitario().getText());

        //Si el valor esta incluido el iva calculo el valor sin iva
        if(getCmbIvaOpcionPrecioVentaPublico().getSelectedItem().equals(IvaOpcionEnum.CON_IVA))
        {            
            BigDecimal ivaDefecto=new BigDecimal(session.getParametrosCodefac().get(ParametroCodefac.IVA_DEFECTO).getValor());
            BigDecimal ivaTmp=ivaDefecto.divide(new BigDecimal("100"),2,BigDecimal.ROUND_HALF_UP).add(BigDecimal.ONE);            
            valorUnitario=valorUnitario.divide(ivaTmp,3,BigDecimal.ROUND_HALF_UP);
            
        }
        producto.setValorUnitario(valorUnitario);
        /**
         * Setear valores adicionales
         */
        producto.setUbicacion(getTxtUbicacion().getText());
        producto.setGarantia(((EnumSiNo) getCmbGarantia().getSelectedItem()).getLetra());
        producto.setCantidadMinima(Integer.parseInt(getTxtCantidadMinima().getText()));
        producto.setPrecioDistribuidor(new BigDecimal(getTxtPrecioDistribuidor().getText()));
        producto.setPrecioTarjeta(new BigDecimal(getTxtPrecioTarjeta().getText()));
        producto.setPrecioSinSubsidio(new BigDecimal(getTxtPrecio1SinSubsidio().getText()));
        //producto.setStockInicial(Long.parseLong(getTxtStockInicial().getText()));
        producto.setMarca(getTxtMarca().getText());
        producto.setImagen(getTxtImagenProducto().getText());

        //catProducto= (CategoriaProducto) getCmbCategoriaProducto().getSelectedItem();
        //producto.setCategoriaProducto(catProducto);
        producto.setCaracteristicas(getTxtCaracteristica().getText());
        producto.setObservaciones(getTxtObservaciones().getText());
        
        TipoProductoEnum tipoProductoEnum=(TipoProductoEnum) getCmbTipoProducto().getSelectedItem();
        producto.setTipoProductoCodigo(tipoProductoEnum.getLetra());
        /**
         * AGREGAR LOS DATOS ADICIONALES DEL CATALOGO PRODUCTO
         */
        CatalogoProducto catalogoProducto=crearCatalogoProducto();
        producto.setCatalogoProducto(catalogoProducto);
        
        EnumSiNo enumSiNo=(EnumSiNo) getCmbManejaInventario().getSelectedItem();
        producto.setManejarInventario(enumSiNo.getLetra());
        
        //Setear la opcion de si desea generar el codigo de barras
        enumSiNo=(EnumSiNo) getCmbGenerarCodigoBarras().getSelectedItem();        
        producto.setGenerarCodigoBarras(enumSiNo);
        
        enumSiNo=enumSiNo.getEnumByBoolean(getChkTransportarGuiaRemision().isSelected());        
        producto.setTransportarEnGuiaRemisionEnum(enumSiNo);
        
        enumSiNo=enumSiNo.getEnumByBoolean(getChkOcultarDetalleVenta().isSelected());        
        producto.setOcultarDetalleVentaEnum(enumSiNo);
        
        producto.setEmpresa(session.getEmpresa());
        

    }
    
    private CatalogoProducto crearCatalogoProducto()
    {
        CatalogoProducto catalogoProducto=new CatalogoProducto();
        CategoriaProducto categoriaProducto=(CategoriaProducto) getCmbCategoriaProducto().getSelectedItem();
        catalogoProducto.setCategoriaProducto(categoriaProducto);
        if(getComboIce().getSelectedItem()!=null && !getComboIce().getSelectedItem().getClass().equals(String.class))
        {
            ImpuestoDetalle ice= (ImpuestoDetalle) getComboIce().getSelectedItem();
            catalogoProducto.setIce(ice);
        }
        
        if( getComboIrbpnr().getSelectedItem()!=null && !getComboIrbpnr().getSelectedItem().getClass().equals(String.class))
        {
            ImpuestoDetalle ibpnr=(ImpuestoDetalle) getComboIrbpnr().getSelectedItem();
            catalogoProducto.setIrbpnr(ibpnr);
        }
        
        ImpuestoDetalle iva= (ImpuestoDetalle) getComboIva().getSelectedItem();
        catalogoProducto.setIva(iva);
        
        catalogoProducto.setNombre(getTextNombre().getText());
        
        TipoProductoEnum tipoProductoEnum=TipoProductoEnum.PRODUCTO;
        catalogoProducto.setModuloCod(ModuloCodefacEnum.INVENTARIO.getCodigo());
        return catalogoProducto;
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite {
        if (estadoFormulario.equals(GeneralPanelInterface.ESTADO_EDITAR)) 
        {
            try {
                Boolean respuesta = DialogoCodefac.dialogoPregunta("Alerta", "Estas seguro que desea eliminar el producto?", DialogoCodefac.MENSAJE_ADVERTENCIA);
                if (!respuesta) {
                    throw new ExcepcionCodefacLite("Cancelacion usuario");
                }
                productoService.eliminarProducto(producto);
                DialogoCodefac.mensaje("Datos correctos", "El producto se elimino correctamente", DialogoCodefac.MENSAJE_CORRECTO);
            } catch (RemoteException ex) {
                Logger.getLogger(ProductoModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ServicioCodefacException ex) {
                DialogoCodefac.mensaje("Error",ex.getMessage(),DialogoCodefac.MENSAJE_INCORRECTO);
                Logger.getLogger(ProductoModel.class.getName()).log(Level.SEVERE, null, ex);
                throw new ExcepcionCodefacLite(ex.getMessage());
            }
        }

    }

    @Override
    public void imprimir() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buscar() throws ExcepcionCodefacLite {
        ProductoBusquedaDialogo productoBusquedaDialogo = new ProductoBusquedaDialogo(session.getEmpresa());
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(productoBusquedaDialogo);
        buscarDialogoModel.setVisible(true);
        Producto productoTmp = (Producto) buscarDialogoModel.getResultado();

        if (productoTmp == null) {
            throw new ExcepcionCodefacLite("Excepcion lanzada desde buscar producto vacio");
        }
        else
        {
            this.producto=productoTmp;
            cargarProducto();
        }

    }
    
    private void cargarProducto()
    {
        getTxtCodigoPersonalizado().setText(producto.getCodigoPersonalizado());
        getTxtCodigoEAN().setText(producto.getCodigoEAN());
        getTxtCodigoUPC().setText(producto.getCodigoUPC());
        getTextNombre().setText(producto.getNombre());
        getTextValorUnitario().setText(producto.getValorUnitario().toString());
        getCmbIvaOpcionPrecioVentaPublico().setSelectedItem(IvaOpcionEnum.SIN_IVA); //Seleccciona esta opcion porque por defecto los precios en la base de datos estan grabados sin Iva


        /**
         * Cargar datos adicionales
         */
        getTxtUbicacion().setText((producto.getUbicacion() != null) ? producto.getUbicacion() : "");
        getCmbGarantia().setSelectedItem(EnumSiNo.getEnumByLetra(producto.getGarantia()));
        //get().setText((producto.getUbicacion()!=null)?producto.getUbicacion():"");
        getTxtCantidadMinima().setText((producto.getCantidadMinima() != null) ? producto.getCantidadMinima() + "" : "");
        getTxtPrecioDistribuidor().setText((producto.getPrecioDistribuidor() != null) ? producto.getPrecioDistribuidor() + "" : "");
        getTxtPrecioTarjeta().setText((producto.getPrecioTarjeta() != null) ? producto.getPrecioTarjeta() + "" : "");
        //getTxtStockInicial().setText((producto.getStockInicial() != null) ? producto.getStockInicial() + "" : "");
        getTxtMarca().setText((producto.getMarca() != null) ? producto.getMarca() + "" : "");
        getTxtImagenProducto().setText((producto.getImagen() != null) ? producto.getImagen() + "" : "");
        //getCmbCategoriaProducto().setSelectedItem(producto.getCatalogoProducto().getCategoriaProducto());
        getTxtCaracteristica().setText((producto.getCaracteristicas() != null) ? producto.getCaracteristicas() + "" : "");
        getTxtObservaciones().setText((producto.getObservaciones() != null) ? producto.getObservaciones() + "" : "");
        getCmbTipoProducto().setSelectedItem(producto.getTipoProductoEnum());
        
        /**
         * Cargar datos de la entidad catalogo producto
         */
        getCmbCategoriaProducto().setSelectedItem(producto.getCatalogoProducto().getCategoriaProducto());
        getComboIva().setSelectedItem(producto.getCatalogoProducto().getIva());
        getComboIrbpnr().setSelectedItem(producto.getCatalogoProducto().getIrbpnr());
        getComboIce().setSelectedItem(producto.getCatalogoProducto().getIce()); 
        getTxtPrecio1SinSubsidio().setText((producto.getPrecioSinSubsidio()!=null)?producto.getPrecioSinSubsidio().toString():"0");
        
        //Setear la opcion de inventario y si no esta escogida ninguna opcion de si maneja inventario por defecte seteo en no
        String letraInventario=(producto.getManejarInventario()!=null)?producto.getManejarInventario():EnumSiNo.NO.getLetra();        
        EnumSiNo enumInventario=EnumSiNo.getEnumByLetra(letraInventario);        
        getCmbManejaInventario().setSelectedItem(enumInventario);
        
        //Cargar si desea generar el codigo de los productos
        String letraGenerarCodBarras=(producto.getGenerarCodigoBarras()!=null)?producto.getGenerarCodigoBarras():EnumSiNo.NO.getLetra();        
        EnumSiNo enumGenerarCodigoBarras=EnumSiNo.getEnumByLetra(letraGenerarCodBarras);        
        getCmbGenerarCodigoBarras().setSelectedItem(enumGenerarCodigoBarras);
        
        getChkTransportarGuiaRemision().setSelected(producto.getTransportarEnGuiaRemisionEnum().getBool());
        getChkOcultarDetalleVenta().setSelected(producto.getOcultarDetalleVentaEnum().getBool());
        
        actualizarTablaEnsamble();
    
    }
    

    @Override
    public void limpiar() {

        this.producto = new Producto();

        initModelTablaDatosEnsamble();
        setearValoresIniciales();

        getComboIce().setEditable(true);
        getComboIce().setSelectedItem("Seleccione : ");

        getComboIrbpnr().setEditable(true);
        getComboIrbpnr().setSelectedItem("Seleccione: ");
        
        getCmbIvaOpcionPrecioVentaPublico().setSelectedItem(IvaOpcionEnum.SIN_IVA);
        
        getCmbGenerarCodigoBarras().setSelectedItem(EnumSiNo.NO);
        
        getChkTransportarGuiaRemision().setEnabled(true);
        getChkTransportarGuiaRemision().setSelected(true);
        
        getChkOcultarDetalleVenta().setEnabled(true);
        
        


    }

//    @Override
    public String getNombre() {
        return "Producto";
    }

    @Override
    public String getURLAyuda() {
        return "https://docs.google.com/document/d/e/2PACX-1vRxHiHd5vpEu1In25BKtCXigpl4m1phGAZwNR7Rh2Jm-Xqe7ffQpivlYJsMAWHFBS0BOnYxj4dpUi7H/pub?embedded=true#h.4y1m2n6myaod";
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
    public Producto getResult() throws ExcepcionCodefacLite {
        try {
            grabar();
            return producto;
        } catch (ExcepcionCodefacLite ex) {
            Logger.getLogger(ProductoModel.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }

    @Override
    public void iniciar() {
        controlador=new ProductoModelControlador(DialogoCodefac.intefaceMensaje, session,this);
        listenerComboBox();
        listenerBotones();
        
        mapDatosIngresadosDefault.put(getTextValorUnitario(),"0");
        mapDatosIngresadosDefault.put(getTxtCantidadEnsamble(),"0");
        mapDatosIngresadosDefault.put(getTxtCantidadEnsamble(),"0");
        
        mapDatosIngresadosDefault.put(getTxtCantidadMinima(),"0");
        mapDatosIngresadosDefault.put(getTxtPrecioDistribuidor(),"0");
        mapDatosIngresadosDefault.put(getTxtPrecioTarjeta(),"0");
        //mapDatosIngresadosDefault.put(getTxtStockInicial(),"0");
        
        productoService = ServiceFactory.getFactory().getProductoServiceIf();
        impuestoService = ServiceFactory.getFactory().getImpuestoServiceIf();
        impuestoDetalleService = ServiceFactory.getFactory().getImpuestoDetalleServiceIf();
        catProdService = ServiceFactory.getFactory().getCategoriaProductoServiceIf();
        iniciarCombosBox();
    }

    @Override
    public void nuevo() {
        
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void iniciarCombosBox() {
            
        controlador.iniciarCombosBox();
    }

    private void listenerComboBox() {

        getCmbManejaInventario().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                if (getCmbManejaInventario().getSelectedItem().equals(EnumSiNo.SI)) 
                {
                    getTabMenu().setEnabledAt(2, true);
                    getTabMenu().setEnabledAt(3, true);
                }
                else
                {
                    getTabMenu().setEnabledAt(2, false);
                    getTabMenu().setEnabledAt(3, false);
                }

                
            }
        });
        

    }

    private void seleccionarTipoProducto(TipoProductoEnum tipoProducto) {
        if (tipoProducto!=null && tipoProducto.equals(TipoProductoEnum.PRODUCTO)) {
            getTxtCodigoEAN().setEnabled(true);
            getTxtCodigoUPC().setEnabled(true);
        } else {
            getTxtCodigoEAN().setEnabled(false);
            getTxtCodigoUPC().setEnabled(false);

        }
    }

    private void listenerBotones() {
        
        getBtnBuscarProductoEnsamble().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductoBusquedaDialogo productoBusquedaDialogo = new ProductoBusquedaDialogo(session.getEmpresa());
                BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(productoBusquedaDialogo);
                buscarDialogoModel.setVisible(true);
                productoEnsamble = (Producto) buscarDialogoModel.getResultado();

                if (productoEnsamble != null) {
                    getTxtProductoEnsamble().setText(productoEnsamble.getNombre());
                }
            }
        });

        getBtnAgregarEnsamble().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductoEnsamble componenteEnsamble = new ProductoEnsamble();
                componenteEnsamble.setCantidad(Integer.parseInt(getTxtCantidadEnsamble().getText()));
                componenteEnsamble.setComponenteEnsamble(productoEnsamble);
                producto.addProductoEnsamble(componenteEnsamble);
                actualizarTablaEnsamble();
            }
        });

    }

    private void actualizarTablaEnsamble() {
        String[] titulo = {"Cantidad", "Nombre", "Precio Venta"};
        DefaultTableModel tableModel = new DefaultTableModel(titulo, 0);

        if(producto.getDetallesEnsamble()!=null)
        {
            for (ProductoEnsamble productoEnsamble : producto.getDetallesEnsamble()) {
                Vector<String> fila = new Vector<String>();
                fila.add(productoEnsamble.getCantidad() + "");
                fila.add(productoEnsamble.getComponenteEnsamble().getNombre());
                fila.add(productoEnsamble.getComponenteEnsamble().getValorUnitario() + "");
                tableModel.addRow(fila);
            }
        }
        getTblDatosEnsamble().setModel(tableModel);
    }
    
    private void setearValoresIniciales()
    {
        getTextValorUnitario().setText("0");
        getTxtCantidadEnsamble().setText("0");
        getTxtCantidadMinima().setText("0");
        getTxtPrecioDistribuidor().setText("0");
        getTxtPrecioTarjeta().setText("0");
        getTxtPrecio1SinSubsidio().setText("0");
        //getTxtStockInicial().setText("0");
    }
    
    private void initModelTablaDatosEnsamble()
    {
        String[] titulo = {"Cantidad","Nombre","Precio Venta"};
        DefaultTableModel tableModel = new DefaultTableModel(titulo, 0);
        getTblDatosEnsamble().setModel(tableModel);
    }

    @Override
    public BuscarDialogoModel obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void postConstructorExterno(Object[] parametros) {
      
        EnumSiNo enumSiNo=(EnumSiNo) parametros[0];
        getCmbManejaInventario().setSelectedItem(enumSiNo);
        
        //TODO: Mejorar esta parte porque solo va a funcionar para el codigo personalizado y si quieren manejar algunos codigos no funciona
        String codigoProducto=(String) parametros[1];
        getTxtCodigoPersonalizado().setText(codigoProducto);
    }

    @Override
    public void llenarCmbManejaInventario(EnumSiNo[] datos) {
        UtilidadesComboBox.llenarComboBox(getCmbManejaInventario(),datos);
    }

    @Override
    public void llenarCmbGenerarCodigoBarras(EnumSiNo[] datos) {
        UtilidadesComboBox.llenarComboBox(getCmbGenerarCodigoBarras(),datos);
    }

    @Override
    public void llenarCmbTipoProducto(TipoProductoEnum[] tipoProductoList) {
        UtilidadesComboBox.llenarComboBox(getCmbTipoProducto(),tipoProductoList);
    }

    @Override
    public void llenarCmbCategoriaProducto(List<CategoriaProducto> catProdList) {
        UtilidadesComboBox.llenarComboBox(getCmbCategoriaProducto(),catProdList);
    }

    @Override
    public void llenarComboIva(List<ImpuestoDetalle> impuestos) {
        UtilidadesComboBox.llenarComboBox(getComboIva(),impuestos);
    }

    @Override
    public void llenarComboIce(List<ImpuestoDetalle> impuestos) {
        UtilidadesComboBox.llenarComboBox(getComboIce(),impuestos);
    }

    @Override
    public void llenarComboIrbpnr(List<ImpuestoDetalle> impuestos) {
        UtilidadesComboBox.llenarComboBox(getComboIrbpnr(),impuestos);
    }

    @Override
    public void seleccionarComboIva(ImpuestoDetalle impuesto) {
        getComboIva().setSelectedItem(impuesto);
    }

    @Override
    public void llenarCmbGarantia(EnumSiNo[] datos) {
        UtilidadesComboBox.llenarComboBox(getCmbGarantia(),datos);
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
        
    }
}
