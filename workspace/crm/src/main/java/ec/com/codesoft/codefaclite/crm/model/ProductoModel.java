/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.crm.model;

import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ProductoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.interfaces.ControladorVistaIf;
import ec.com.codesoft.codefaclite.controlador.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.controlador.vista.crm.ProductoModelControlador;
import ec.com.codesoft.codefaclite.controlador.vista.factura.ModelControladorAbstract;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.DialogInterfacePanel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.controlador.vista.crm.ProductoModelControlador.IvaOpcionEnum;
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
import java.util.ArrayList;
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
public class ProductoModel extends ProductoForm implements DialogInterfacePanel<Producto> , InterfazPostConstructPanel,ProductoModelControlador.SwingIf ,ControladorVistaIf {

    //private EnumSiNo imprimirBarrasSeleccionado;
        
    /////////////////////////////////////////////    
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
        /*try {
            setearValoresProducto(controlador.producto);
            controlador.producto=productoService.grabar(controlador.producto);
            DialogoCodefac.mensaje("Datos correctos", "El Producto se guardo correctamente", DialogoCodefac.MENSAJE_CORRECTO);
        } catch (ServicioCodefacException ex) {
            DialogoCodefac.mensaje("Error", ex.getMessage(), DialogoCodefac.MENSAJE_INCORRECTO);
            throw new ExcepcionCodefacLite("Error al grabar");
        } catch (RemoteException ex) {
            Logger.getLogger(ProductoModel.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public void editar() throws ExcepcionCodefacLite {
        /*try {
            
            setearValoresProducto(controlador.producto);
            productoService.editarProducto(controlador.producto);
            DialogoCodefac.mensaje("Datos correctos", "El producto se edito correctamente", DialogoCodefac.MENSAJE_CORRECTO);
        } catch (RemoteException ex) {
            Logger.getLogger(ProductoModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ProductoModel.class.getName()).log(Level.SEVERE, null, ex);
            DialogoCodefac.mensaje("Datos correctos", ex.getMessage(), DialogoCodefac.MENSAJE_INCORRECTO);
            throw new ExcepcionCodefacLite(ex.getMessage());
        }*/
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setearValoresProducto(Producto producto) {
        producto.setCodigoEAN(getTxtCodigoEAN().getText());
        producto.setCodigoUPC(getTxtCodigoUPC().getText());
        //producto.setEstadoEnum(GeneralEnumEstado.ACTIVO);

          
        //BigDecimal valorUnitario=BigDecimal.ZERO;
        //valorUnitario = new BigDecimal(getTextValorUnitario().getText());

        //Si el valor esta incluido el iva calculo el valor sin iva
        /*if(controlador.getIvaOpcionSeleccionado().equals(IvaOpcionEnum.CON_IVA))
        {            
            BigDecimal ivaDefecto=new BigDecimal(session.getParametrosCodefac().get(ParametroCodefac.IVA_DEFECTO).getValor());
            BigDecimal ivaTmp=ivaDefecto.divide(new BigDecimal("100"),2,BigDecimal.ROUND_HALF_UP).add(BigDecimal.ONE);            
            valorUnitario=valorUnitario.divide(ivaTmp,3,BigDecimal.ROUND_HALF_UP);
            
        }
        producto.setValorUnitario(valorUnitario);*/
        /**
         * Setear valores adicionales
         */
        producto.setUbicacion(getTxtUbicacion().getText());
        //producto.setGarantia(((EnumSiNo) getCmbGarantia().getSelectedItem()).getLetra());
        producto.setCantidadMinima(Integer.parseInt(getTxtCantidadMinima().getText()));
        producto.setPrecioDistribuidor(new BigDecimal(getTxtPrecioDistribuidor().getText()));
        producto.setPrecioTarjeta(new BigDecimal(getTxtPrecioTarjeta().getText()));
        producto.setPrecioSinSubsidio(new BigDecimal(getTxtPrecio1SinSubsidio().getText()));
        
        producto.setPvp4(new BigDecimal(getTxtPV4().getText()));
        producto.setPvp5(new BigDecimal(getTxtPV5().getText()));
        producto.setPvp6(new BigDecimal(getTxtPV6().getText()));
        
        //producto.setStockInicial(Long.parseLong(getTxtStockInicial().getText()));
        producto.setMarca(getTxtMarca().getText());
        producto.setImagen(getTxtImagenProducto().getText());

        //catProducto= (CategoriaProducto) getCmbCategoriaProducto().getSelectedItem();
        //producto.setCategoriaProducto(catProducto);
        producto.setCaracteristicas(getTxtCaracteristica().getText());
        producto.setObservaciones(getTxtObservaciones().getText());
        
        //TipoProductoEnum tipoProductoEnum=(TipoProductoEnum) getCmbTipoProducto().getSelectedItem();
        //producto.setTipoProductoCodigo(tipoProductoEnum.getLetra());
        /**
         * AGREGAR LOS DATOS ADICIONALES DEL CATALOGO PRODUCTO
         */
        //CatalogoProducto catalogoProducto=crearCatalogoProducto();
        //producto.setCatalogoProducto(catalogoProducto);
        
        
        EnumSiNo enumSiNo=EnumSiNo.getEnumByBoolean(getChkTransportarGuiaRemision().isSelected());        
        producto.setTransportarEnGuiaRemisionEnum(enumSiNo);
        
        enumSiNo=enumSiNo.getEnumByBoolean(getChkOcultarDetalleVenta().isSelected());        
        producto.setOcultarDetalleVentaEnum(enumSiNo);
        
        producto.setEmpresa(session.getEmpresa());
        

    }
    
    

    @Override
    public void eliminar() throws ExcepcionCodefacLite {
        //
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
        /*ProductoBusquedaDialogo productoBusquedaDialogo = new ProductoBusquedaDialogo(session.getEmpresa());
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(productoBusquedaDialogo);
        buscarDialogoModel.setVisible(true);
        Producto productoTmp = (Producto) buscarDialogoModel.getResultado();

        if (productoTmp == null) {
            throw new ExcepcionCodefacLite("Excepcion lanzada desde buscar producto vacio");
        }
        else
        {
            controlador.producto=productoTmp;
            cargarProducto();
        }*/
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    

    @Override
    public void limpiar() {
        //controlador.producto=new Producto();
        //controlador.producto.setGenerarCodigoBarrasEnum(EnumSiNo.NO);
        //this.producto = new Producto();

        initModelTablaDatosEnsamble();
        setearValoresIniciales();

        //getComboIce().setEditable(true);
        //getComboIce().setSelectedItem("Seleccione : ");

        //getComboIrbpnr().setEditable(true);
        //getComboIrbpnr().setSelectedItem("Seleccione: ");
        
        //getCmbIvaOpcionPrecioVentaPublico().setSelectedItem(IvaOpcionEnum.SIN_IVA);
        
        //getCmbGenerarCodigoBarras().setSelectedItem(EnumSiNo.NO);
        //producto.setGenerarCodigoBarras(EnumSiNo.NO);
        //imprimirBarrasSeleccionado=EnumSiNo.NO;
        
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
            controlador.grabar();
            return controlador.producto;
        } catch (ExcepcionCodefacLite ex) {
            Logger.getLogger(ProductoModel.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } catch (RemoteException ex) {
            Logger.getLogger(ProductoModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void iniciar() {
        controlador=new ProductoModelControlador(DialogoCodefac.intefaceMensaje, session,this,ModelControladorAbstract.TipoVista.ESCRITORIO);
        
        //controlador.producto=new Producto();
        //controlador.producto.setGenerarCodigoBarrasEnum(EnumSiNo.NO);
        //Iniciar cmbLleva inventario
        
        listenerComboBox();
        listenerBotones();
        
        //mapDatosIngresadosDefault.put(getTextValorUnitario(),"0");
        mapDatosIngresadosDefault.put(getTxtCantidadEnsamble(),"0");
        mapDatosIngresadosDefault.put(getTxtCantidadEnsamble(),"0");
        
        mapDatosIngresadosDefault.put(getTxtCantidadMinima(),"0");
        mapDatosIngresadosDefault.put(getTxtPrecioDistribuidor(),"0");
        mapDatosIngresadosDefault.put(getTxtPrecioTarjeta(),"0");
        mapDatosIngresadosDefault.put(getTxtPV4(),"0");
        mapDatosIngresadosDefault.put(getTxtPV5(),"0");
        mapDatosIngresadosDefault.put(getTxtPV6(),"0");
        //mapDatosIngresadosDefault.put(getTxtStockInicial(),"0");
        
        productoService = ServiceFactory.getFactory().getProductoServiceIf();
        impuestoService = ServiceFactory.getFactory().getImpuestoServiceIf();
        impuestoDetalleService = ServiceFactory.getFactory().getImpuestoDetalleServiceIf();
        catProdService = ServiceFactory.getFactory().getCategoriaProductoServiceIf();
        iniciarCombosBox();
        
        /*getBtnTemporal().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogoCodefac.mensaje(new CodefacMsj("Enum:"+controlador.getProducto().getManejarInventarioEnum(), CodefacMsj.TipoMensajeEnum.CORRECTO));
            }
        });*/
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
        //TODO: Terminar de implementar este listener
        /*getCmbManejaInventario().addActionListener(new ActionListener() {
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
                controlador.producto.addProductoEnsamble(componenteEnsamble);
                actualizarTablaEnsamble();
            }
        });

    }

    private void actualizarTablaEnsamble() {
        String[] titulo = {"Cantidad", "Nombre", "Precio Venta"};
        DefaultTableModel tableModel = new DefaultTableModel(titulo, 0);

        if(controlador.producto.getDetallesEnsamble()!=null)
        {
            for (ProductoEnsamble productoEnsamble : controlador.producto.getDetallesEnsamble()) {
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
        getTxtCantidadEnsamble().setText("0");
        getTxtCantidadMinima().setText("0");
        getTxtPrecioDistribuidor().setText("0");
        getTxtPrecioTarjeta().setText("0");
        getTxtPrecio1SinSubsidio().setText("0");
        getTxtPV4().setText("0");
        getTxtPV5().setText("0");
        getTxtPV6().setText("0");
        //getTxtStockInicial().setText("0");
    }
    
    private void initModelTablaDatosEnsamble()
    {
        String[] titulo = {"Cantidad","Nombre","Precio Venta"};
        DefaultTableModel tableModel = new DefaultTableModel(titulo, 0);
        getTblDatosEnsamble().setModel(tableModel);
    }

    @Override
    public InterfaceModelFind obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        controlador.producto=(Producto) entidad;
        //getTxtCodigoPersonalizado().setText(controlador.producto.getCodigoPersonalizado());
        getTxtCodigoEAN().setText(controlador.producto.getCodigoEAN());
        getTxtCodigoUPC().setText(controlador.producto.getCodigoUPC());
        //getTextNombre().setText(controlador.producto.getNombre());
        //getTextValorUnitario().setText(controlador.producto.getValorUnitario().toString());
        //getCmbIvaOpcionPrecioVentaPublico().setSelectedItem(IvaOpcionEnum.SIN_IVA); //Seleccciona esta opcion porque por defecto los precios en la base de datos estan grabados sin Iva


        /**
         * Cargar datos adicionales
         */
        getTxtUbicacion().setText((controlador.producto.getUbicacion() != null) ? controlador.producto.getUbicacion() : "");
        //getCmbGarantia().setSelectedItem(EnumSiNo.getEnumByLetra(controlador.producto.getGarantia()));
        //get().setText((producto.getUbicacion()!=null)?producto.getUbicacion():"");
        getTxtCantidadMinima().setText((controlador.producto.getCantidadMinima() != null) ? controlador.producto.getCantidadMinima() + "" : "");
        getTxtPrecioDistribuidor().setText((controlador.producto.getPrecioDistribuidor() != null) ? controlador.producto.getPrecioDistribuidor() + "" : "");
        getTxtPrecioTarjeta().setText((controlador.producto.getPrecioTarjeta() != null) ? controlador.producto.getPrecioTarjeta() + "" : "");
        //getTxtStockInicial().setText((producto.getStockInicial() != null) ? producto.getStockInicial() + "" : "");
        getTxtMarca().setText((controlador.producto.getMarca() != null) ? controlador.producto.getMarca() + "" : "");
        getTxtImagenProducto().setText((controlador.producto.getImagen() != null) ? controlador.producto.getImagen() + "" : "");
        //getCmbCategoriaProducto().setSelectedItem(producto.getCatalogoProducto().getCategoriaProducto());
        getTxtCaracteristica().setText((controlador.producto.getCaracteristicas() != null) ? controlador.producto.getCaracteristicas() + "" : "");
        getTxtObservaciones().setText((controlador.producto.getObservaciones() != null) ? controlador.producto.getObservaciones() + "" : "");
        //getCmbTipoProducto().setSelectedItem(controlador.producto.getTipoProductoEnum());
        getTxtPV4().setText((controlador.producto.getPvp4()!= null) ? controlador.producto.getPvp4() + "" : "0");
        getTxtPV5().setText((controlador.producto.getPvp5()!= null) ? controlador.producto.getPvp5() + "" : "0");
        getTxtPV6().setText((controlador.producto.getPvp6()!= null) ? controlador.producto.getPvp6() + "" : "0");
        /**
         * Cargar datos de la entidad catalogo producto
         */
        //getCmbCategoriaProducto().setSelectedItem(controlador.producto.getCatalogoProducto().getCategoriaProducto());
        //getComboIva().setSelectedItem(controlador.producto.getCatalogoProducto().getIva());
        //getComboIrbpnr().setSelectedItem(controlador.producto.getCatalogoProducto().getIrbpnr());
        //getComboIce().setSelectedItem(controlador.producto.getCatalogoProducto().getIce()); 
        getTxtPrecio1SinSubsidio().setText((controlador.producto.getPrecioSinSubsidio()!=null)?controlador.producto.getPrecioSinSubsidio().toString():"0");
        
        //Setear la opcion de inventario y si no esta escogida ninguna opcion de si maneja inventario por defecte seteo en no
        //String letraInventario=(controlador.producto.getManejarInventario()!=null)?controlador.producto.getManejarInventario():EnumSiNo.NO.getLetra();        
        //EnumSiNo enumInventario=EnumSiNo.getEnumByLetra(letraInventario);        
        //getCmbManejaInventario().setSelectedItem(enumInventario);
        
        //Cargar si desea generar el codigo de los productos
        //String letraGenerarCodBarras=(controlador.producto.getGenerarCodigoBarras()!=null)?controlador.producto.getGenerarCodigoBarras():EnumSiNo.NO.getLetra();        
        //EnumSiNo enumGenerarCodigoBarras=EnumSiNo.getEnumByLetra(letraGenerarCodBarras);        
        //imprimirBarrasSeleccionado=enumGenerarCodigoBarras;
        //getCmbGenerarCodigoBarras().setSelectedItem(enumGenerarCodigoBarras);
        
        
        getChkTransportarGuiaRemision().setSelected(controlador.producto.getTransportarEnGuiaRemisionEnum().getBool());
        getChkOcultarDetalleVenta().setSelected(controlador.producto.getOcultarDetalleVentaEnum().getBool());
        
        actualizarTablaEnsamble();
    }

    @Override
    public void postConstructorExterno(Object[] parametros) {
      
        EnumSiNo enumSiNo=(EnumSiNo) parametros[0];
        //getCmbManejaInventario().setSelectedItem(enumSiNo);
        
        //TODO: Mejorar esta parte porque solo va a funcionar para el codigo personalizado y si quieren manejar algunos codigos no funciona
        String codigoProducto=(String) parametros[1];
        getTxtCodigoPersonalizado().setText(codigoProducto);
    }

   
    /*@Override
    public void llenarCmbGenerarCodigoBarras(EnumSiNo[] datos) {
        UtilidadesComboBox.llenarComboBox(getCmbGenerarCodigoBarras(),datos);
    }*/

   

    /*@Override
    public void llenarCmbCategoriaProducto(List<CategoriaProducto> catProdList) {
        UtilidadesComboBox.llenarComboBox(getCmbCategoriaProducto(),catProdList);
    }*/

    /*@Override
    public void llenarComboIva(List<ImpuestoDetalle> impuestos) {
        UtilidadesComboBox.llenarComboBox(getComboIva(),impuestos);
    }*/

    /*@Override
    public void llenarComboIce(List<ImpuestoDetalle> impuestos) {
        UtilidadesComboBox.llenarComboBox(getComboIce(),impuestos);
    }*/

    /*@Override
    public void llenarComboIrbpnr(List<ImpuestoDetalle> impuestos) {
        UtilidadesComboBox.llenarComboBox(getComboIrbpnr(),impuestos);
    }*/

    /*@Override
    public void seleccionarComboIva(ImpuestoDetalle impuesto) {
        getComboIva().setSelectedItem(impuesto);
    }*/

    /*@Override
    public void llenarCmbGarantia(EnumSiNo[] datos) {
        UtilidadesComboBox.llenarComboBox(getCmbGarantia(),datos);
    }*/
    
    //==================== METODOS GET AND SET ==============================//
    

    /*public EnumSiNo getImprimirBarrasSeleccionado() {
        return imprimirBarrasSeleccionado;
    }

    public void setImprimirBarrasSeleccionado(EnumSiNo imprimirBarrasSeleccionado) {
        this.imprimirBarrasSeleccionado = imprimirBarrasSeleccionado;
    }*/

    @Override
    public ModelControladorAbstract getControladorVista() {
        return controlador;
    }

    public ProductoModelControlador getControlador() {
        return controlador;
    }

    public void setControlador(ProductoModelControlador controlador) {
        this.controlador = controlador;
    }
    
    
    
    
}
