/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.crm.model;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.DialogInterfacePanel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ObserverUpdateInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.crm.busqueda.ProductoBusquedaDialogo;
import ec.com.codesoft.codefaclite.crm.panel.ProductoForm;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CategoriaProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Impuesto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ImpuestoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ProductoEnsamble;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ProductoEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoProductoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.CategoriaProductoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ImpuestoDetalleServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ImpuestoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ProductoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.VentanaEnum;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.BigInteger;
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
public class ProductoModel extends ProductoForm implements DialogInterfacePanel<Producto> {

    private Producto producto;
    private Impuesto impuesto;
    private CategoriaProducto catProducto;

    private ProductoServiceIf productoService;
    private ImpuestoServiceIf impuestoService;
    private ImpuestoDetalleServiceIf impuestoDetalleService;
    private CategoriaProductoServiceIf catProdService;
    private BigDecimal d;

    /*
    Referencia sobre el producto seleccionado para el ensamble
     */
    private Producto productoEnsamble;

    public ProductoModel() {
        listenerComboBox();
        listenerBotones();
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
            /*
            producto.setCodigoPersonalizado(getTxtCodigoPersonalizado().getText());
            producto.setCodigoEAN(getTxtCodigoEAN().getText());
            producto.setCodigoUPC(getTxtCodigoUPC().getText());
            if(getComboTipoProducto().getSelectedItem().equals("Bien"))
            {
            producto.setTipoProducto("B");
            }
            else
            {
            producto.setTipoProducto("S");
            }
            
            producto.setNombre(getTextNombre().getText());
            d = new BigDecimal(getTextValorUnitario().getText());
            producto.setValorUnitario(d);
            */
            setearValoresProducto(producto);
            productoService.editar(producto);
            DialogoCodefac.mensaje("Datos correctos", "El producto se edito correctamente", DialogoCodefac.MENSAJE_CORRECTO);
        } catch (RemoteException ex) {
            Logger.getLogger(ProductoModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setearValoresProducto(Producto producto) {
        producto.setCodigoPersonalizado(getTxtCodigoPersonalizado().getText());
        producto.setCodigoEAN(getTxtCodigoEAN().getText());
        producto.setCodigoUPC(getTxtCodigoUPC().getText());
        producto.setEstado(ProductoEnumEstado.ACTIVO.getEstado());

        ///CatalogoProducto catalogoProducto=(CatalogoProducto) getCmbCatalogoProducto().getSelectedItem();
        //producto.setCatalogoProducto(catalogoProducto);
        //TipoProductoEnum tipoProductoEnum = (TipoProductoEnum) getComboTipoProducto().getSelectedItem();
        //producto.getCatalogoProducto().setTipoProducto(tipoProductoEnum.getLetra());
        
        

        producto.setNombre(getTextNombre().getText());
        d = new BigDecimal(getTextValorUnitario().getText());

        producto.setValorUnitario(d);
        /**
         * Setear valores adicionales
         */
        producto.setUbicacion(getTxtUbicacion().getText());
        producto.setGarantia(((EnumSiNo) getCmbGarantia().getSelectedItem()).getLetra());
        producto.setCantidadMinima(Integer.parseInt(getTxtCantidadMinima().getText()));
        producto.setPrecioDistribuidor(new BigDecimal(getTxtPrecioDistribuidor().getText()));
        producto.setPrecioTarjeta(new BigDecimal(getTxtPrecioTarjeta().getText()));
        producto.setStockInicial(Long.parseLong(getTxtStockInicial().getText()));
        producto.setMarca(getTxtMarca().getText());
        producto.setImagen(getTxtImagenProducto().getText());

        //catProducto= (CategoriaProducto) getCmbCategoriaProducto().getSelectedItem();
        //producto.setCategoriaProducto(catProducto);
        producto.setCaracteristicas(getTxtCaracteristica().getText());
        producto.setObservaciones(getTxtObservaciones().getText());
        
        /**
         * AGREGAR LOS DATOS ADICIONALES DEL CATALOGO PRODUCTO
         */
        CatalogoProducto catalogoProducto=crearCatalogoProducto();
        producto.setCatalogoProducto(catalogoProducto);

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
        catalogoProducto.setTipoProducto(tipoProductoEnum.getLetra());
        return catalogoProducto;
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite {
        if (estadoFormulario.equals(GeneralPanelInterface.ESTADO_EDITAR)) {
            try {
                Boolean respuesta = DialogoCodefac.dialogoPregunta("Alerta", "Estas seguro que desea eliminar el producto?", DialogoCodefac.MENSAJE_ADVERTENCIA);
                if (!respuesta) {
                    throw new ExcepcionCodefacLite("Cancelacion usuario");
                }
                productoService.eliminar(producto);
                DialogoCodefac.mensaje("Datos correctos", "El producto se elimino correctamente", DialogoCodefac.MENSAJE_CORRECTO);
            } catch (RemoteException ex) {
                Logger.getLogger(ProductoModel.class.getName()).log(Level.SEVERE, null, ex);
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
        ProductoBusquedaDialogo productoBusquedaDialogo = new ProductoBusquedaDialogo();
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(productoBusquedaDialogo);
        buscarDialogoModel.setVisible(true);
        producto = (Producto) buscarDialogoModel.getResultado();

        if (producto == null) {
            throw new ExcepcionCodefacLite("Excepcion lanzada desde buscar producto vacio");
        }

        getTxtCodigoPersonalizado().setText(producto.getCodigoPersonalizado());
        getTxtCodigoEAN().setText(producto.getCodigoEAN());
        getTxtCodigoUPC().setText(producto.getCodigoUPC());
        getTextNombre().setText(producto.getNombre());
        getTextValorUnitario().setText(producto.getValorUnitario().toString());

        //getComboIva().setSelectedItem(producto.getCatalogoProducto().getIva());

        //getComboTipoProducto().setSelectedItem(TipoProductoEnum.getEnumByLetra(producto.getCatalogoProducto().getTipoProducto()));

        /**
         * Cargar datos adicionales
         */
        getTxtUbicacion().setText((producto.getUbicacion() != null) ? producto.getUbicacion() : "");
        getCmbGarantia().setSelectedItem(EnumSiNo.getEnumByLetra(producto.getGarantia()));
        //get().setText((producto.getUbicacion()!=null)?producto.getUbicacion():"");
        getTxtCantidadMinima().setText((producto.getCantidadMinima() != null) ? producto.getCantidadMinima() + "" : "");
        getTxtPrecioDistribuidor().setText((producto.getPrecioDistribuidor() != null) ? producto.getPrecioDistribuidor() + "" : "");
        getTxtPrecioTarjeta().setText((producto.getPrecioTarjeta() != null) ? producto.getPrecioTarjeta() + "" : "");
        getTxtStockInicial().setText((producto.getStockInicial() != null) ? producto.getStockInicial() + "" : "");
        getTxtMarca().setText((producto.getMarca() != null) ? producto.getMarca() + "" : "");
        getTxtImagenProducto().setText((producto.getImagen() != null) ? producto.getImagen() + "" : "");
        //getCmbCategoriaProducto().setSelectedItem(producto.getCatalogoProducto().getCategoriaProducto());
        getTxtCaracteristica().setText((producto.getCaracteristicas() != null) ? producto.getCaracteristicas() + "" : "");
        getTxtObservaciones().setText((producto.getObservaciones() != null) ? producto.getObservaciones() + "" : "");
        
        /**
         * Cargar datos de la entidad catalogo producto
         */
        getCmbCategoriaProducto().setSelectedItem(producto.getCatalogoProducto().getCategoriaProducto());
        getComboIva().setSelectedItem(producto.getCatalogoProducto().getIva());
        getComboIrbpnr().setSelectedItem(producto.getCatalogoProducto().getIrbpnr());
        getComboIce().setSelectedItem(producto.getCatalogoProducto().getIce()); 

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


    }

    @Override
    public String getNombre() {
        return "Producto";
    }

    @Override
    public String getURLAyuda() {
        return "http://www.cf.codesoft-ec.com/ayuda#eproductos";
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
    public Producto getResult() {
        try {
            grabar();
            return producto;
        } catch (ExcepcionCodefacLite ex) {
            Logger.getLogger(ProductoModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void iniciar() {
        productoService = ServiceFactory.getFactory().getProductoServiceIf();
        impuestoService = ServiceFactory.getFactory().getImpuestoServiceIf();
        impuestoDetalleService = ServiceFactory.getFactory().getImpuestoDetalleServiceIf();
        catProdService = ServiceFactory.getFactory().getCategoriaProductoServiceIf();
        iniciarCombosBox();
    }

    @Override
    public void nuevo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void iniciarCombosBox() {
            
        try {
            //Agregar combo de garantia
            getCmbGarantia().removeAllItems();
            EnumSiNo[] garantias = EnumSiNo.values();
            for (EnumSiNo garantia : garantias) {
                getCmbGarantia().addItem(garantia);
            }
            
            //Agregar las categorias disponibles
            getCmbCategoriaProducto().removeAllItems();
            CategoriaProductoServiceIf catProdService=ServiceFactory.getFactory().getCategoriaProductoServiceIf();
            List<CategoriaProducto> catProdList = catProdService.obtenerTodos();
            for (CategoriaProducto cat : catProdList) {
                getCmbCategoriaProducto().addItem(cat);
            }
            
             
            getComboIva().removeAllItems();
            getComboIce().removeAllItems();
            getComboIrbpnr().removeAllItems();
            
            ImpuestoServiceIf impuestoService=ServiceFactory.getFactory().getImpuestoServiceIf();
            ImpuestoDetalleServiceIf impuestoDetalleService=ServiceFactory.getFactory().getImpuestoDetalleServiceIf();
            
            List<ImpuestoDetalle> impuestoDetalleList = impuestoDetalleService.obtenerIvaVigente();
            ImpuestoDetalle impuestoDefault = null;
            String ivaDefecto = session.getParametrosCodefac().get(ParametroCodefac.IVA_DEFECTO).valor;
            for (ImpuestoDetalle impuesto : impuestoDetalleList) {
                if (impuesto.getTarifa().toString().equals(ivaDefecto)) {
                    impuestoDefault = impuesto;
                }
                getComboIva().addItem(impuesto);
            }
            getComboIva().setSelectedItem(impuestoDefault);
            
            Impuesto ice = impuestoService.obtenerImpuestoPorCodigo(Impuesto.ICE);
            for (ImpuestoDetalle impuesto : ice.getDetalleImpuestos()) {
                getComboIce().addItem(impuesto);
            }
            getComboIce().setEditable(true);
            getComboIce().setSelectedItem("Seleccione : ");
            
            Impuesto irbpnr = impuestoService.obtenerImpuestoPorCodigo(Impuesto.IRBPNR);
            for (ImpuestoDetalle impuesto : irbpnr.getDetalleImpuestos()) {
                getComboIrbpnr().addItem(impuesto);
            }
            getComboIrbpnr().setEditable(true);
            getComboIrbpnr().setSelectedItem("Seleccione: ");
        } catch (RemoteException ex) {
            Logger.getLogger(ProductoModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void listenerComboBox() {

        
        /*
        getComboTipoProducto().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TipoProductoEnum tipoPorductoEnum = (TipoProductoEnum) getComboTipoProducto().getSelectedItem();
                seleccionarTipoProducto(tipoPorductoEnum);
            }
        });*/

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
                ProductoBusquedaDialogo productoBusquedaDialogo = new ProductoBusquedaDialogo();
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

        for (ProductoEnsamble productoEnsamble : producto.getDetallesEnsamble()) {
            Vector<String> fila = new Vector<String>();
            fila.add(productoEnsamble.getCantidad() + "");
            fila.add(productoEnsamble.getComponenteEnsamble().getNombre());
            fila.add(productoEnsamble.getComponenteEnsamble().getValorUnitario() + "");
            tableModel.addRow(fila);
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
        getTxtStockInicial().setText("0");
    }
    
    private void initModelTablaDatosEnsamble()
    {
        String[] titulo = {"Cantidad","Nombre","Precio Venta"};
        DefaultTableModel tableModel = new DefaultTableModel(titulo, 0);
        getTblDatosEnsamble().setModel(tableModel);
    }
}
