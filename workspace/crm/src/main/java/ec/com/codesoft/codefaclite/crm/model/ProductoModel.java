/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.crm.model;

import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ProductoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.interfaces.ControladorVistaIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.controlador.vista.crm.ProductoModelControlador;
import ec.com.codesoft.codefaclite.controlador.vista.factura.ModelControladorAbstract;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.DialogInterfacePanel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;import java.util.Map;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.controlador.vista.crm.ProductoModelControlador.IvaOpcionEnum;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ObserverUpdateInterface;
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
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.MarcaProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PresentacionProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ProductoPresentacionDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.OperadorNegocioEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.VentanaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ModoSistemaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PresentacionProductoServiceIf;
import ec.com.codesoft.codefaclite.utilidades.swing.UtilidadesComboBox;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author PC
 */
public class ProductoModel extends ProductoForm implements DialogInterfacePanel<Producto> , InterfazPostConstructPanel,ProductoModelControlador.SwingIf ,ControladorVistaIf {
    
    private static final Integer COLUMNA_ENSAMBLE_OBJECTO=0;
    
    //TODO: Mejorar esta parte para ver si tengo una referencia absoluta
    private ProductoModel formularioInstancia=this;

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
    private ProductoEnsamble productoEnsambleEditar;
    
    private ProductoModelControlador controlador;
    

    public ProductoModel() {
                
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {

        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public void editar() throws ExcepcionCodefacLite {

        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setearValoresProducto(Producto producto) {
        producto.setCodigoEAN(getTxtCodigoEAN().getText());
        producto.setCodigoUPC(getTxtCodigoUPC().getText());
        //producto.setEstadoEnum(GeneralEnumEstado.ACTIVO);

          

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
        //producto.setMarca(getTxtMarca().getText());
        producto.setImagen(getTxtImagenProducto().getText());

        //catProducto= (CategoriaProducto) getCmbCategoriaProducto().getSelectedItem();
        //producto.setCategoriaProducto(catProducto);
        producto.setCaracteristicas(getTxtCaracteristica().getText());
        producto.setObservaciones(getTxtObservaciones().getText());
        producto.setAplicacionProducto(getTxtAplicacionProducto().getText());
        
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

        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    

    @Override
    public void limpiar() {

        initModelTablaDatosEnsamble();
        setearValoresIniciales();


        getChkTransportarGuiaRemision().setEnabled(true);
        getChkTransportarGuiaRemision().setSelected(true);
        
        getChkOcultarDetalleVenta().setEnabled(true);
        getChkGenerarCodigoAutomatico().setSelected(false);
        actualizarTablaEmpaques();
        
        
        verificarVisibleBotonEditarPresentacion();
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
            if(estadoFormularioEnum.equals(EstadoFormularioEnum.GRABAR))
            {
                controlador.grabar();
            }
            else if(estadoFormularioEnum.equals(EstadoFormularioEnum.EDITAR))
            {
                controlador.editar();
            }
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
        listenerTablas();
        
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
            
        try {
            controlador.iniciarCombosBox();
            
            //Cambiar esta parte al controlador
            PresentacionProductoServiceIf presentacionService = ServiceFactory.getFactory().getPresentacionProductoServiceIf();
            List<PresentacionProducto> presentacionProductosList=presentacionService.obtenerActivosPorEmpresa(session.getEmpresa());
            UtilidadesComboBox.llenarComboBox(getCmbPresentacionEmpaquetado(),presentacionProductosList);
            UtilidadesComboBox.llenarComboBox(getCmbTipoPresentacion(), ProductoPresentacionDetalle.TipoPresentacionEnum.values());
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ProductoModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(ProductoModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void listenerComboBox() {
       
        getCmbTipoPresentacion().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                getPnlPresentacionAdicional().setVisible(false);
                ProductoPresentacionDetalle.TipoPresentacionEnum tipoPresentacion= (ProductoPresentacionDetalle.TipoPresentacionEnum) getCmbTipoPresentacion().getSelectedItem();
                if(tipoPresentacion!=null && tipoPresentacion.equals(ProductoPresentacionDetalle.TipoPresentacionEnum.ADICIONAL))
                {
                    getPnlPresentacionAdicional().setVisible(true);
                }
            }
        });
        //TODO: Mejorar esta parte para que funcione con el controlador
        /*getCmbGenerarCodigoBarras().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EnumSiNo enumSiNo=(EnumSiNo) getCmbGenerarCodigoBarras().getSelectedItem();
                if(enumSiNo!=null && enumSiNo.equals(EnumSiNo.SI))
                {
                    getChkGenerarCodigoAutomatico().setSelected(true);
                    //getTxtCodigoPersonalizado().setText("");
                    //getTxtCodigoPersonalizado().setEnabled(false);
                    controlador.setGenerarCodigoAutomatico(true);
                }
            }
        });*/
        
        //getCmbGenerarCodigoBarras().getSelectedItem();
         

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
    
    private void listenerTablas()
    {
        getTblDatosEnsamble().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaSeleccionada=getTblDatosEnsamble().getSelectedRow();
                if(filaSeleccionada>=0)
                {
                    productoEnsambleEditar=(ProductoEnsamble) getTblDatosEnsamble().getValueAt(filaSeleccionada,COLUMNA_ENSAMBLE_OBJECTO);
                    productoEnsamble=productoEnsambleEditar.getComponenteEnsamble();                   
                    cargarComponenteProductoEnsambleVista(productoEnsambleEditar.getComponenteEnsamble(),productoEnsambleEditar.getCantidad());
                    //actualizarTablaEnsamble();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
        
    }
    
    private void cargarComponenteProductoEnsambleVista(Producto producto,BigDecimal cantidad)
    {        
        if(producto!=null)
        {
            getTxtProductoEnsamble().setText(productoEnsamble.getNombre());
            getTxtCantidadEnsamble().setText(cantidad+"");
        }
        else
        {
            getTxtProductoEnsamble().setText("");
            getTxtCantidadEnsamble().setText("");
        }
        
    }
    
    public ProductoPresentacionDetalle obtenerPresentacionEmpaqueVista()
    {   
        
        PresentacionProducto presentacionEmpaquetado=(PresentacionProducto) getCmbPresentacionEmpaquetado().getSelectedItem();
        ProductoPresentacionDetalle.TipoPresentacionEnum tipoEnum= (ProductoPresentacionDetalle.TipoPresentacionEnum) getCmbTipoPresentacion().getSelectedItem();
        
        ProductoPresentacionDetalle detallePresentacion=new ProductoPresentacionDetalle();
        if(tipoEnum.equals(ProductoPresentacionDetalle.TipoPresentacionEnum.ORIGINAL))
        {
            detallePresentacion.setCantidad(BigDecimal.ONE);
            detallePresentacion.setPvpTmp(BigDecimal.ZERO);
        }
        else
        {
            BigDecimal cantidad = new BigDecimal(getTxtCantidadEmpaquetado().getText());

            BigDecimal pvp = null;
            if (!UtilidadesTextos.verificarNullOVacio(getTxtPrecioEmpaquetado().getText())) {
                pvp = new BigDecimal(getTxtPrecioEmpaquetado().getText());
            }
            
            detallePresentacion.setCantidad(cantidad);
            detallePresentacion.setPvpTmp(pvp);
        }

        detallePresentacion.setTipoEnum(tipoEnum);
        detallePresentacion.setPresentacionProducto(presentacionEmpaquetado);
        detallePresentacion.setProductoOriginal(controlador.getProducto());
        
        return detallePresentacion;
        
    }

    private void listenerBotones() 
    {
        
        getBtnCrearPresentacion().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelPadre.crearDialogoCodefac(new ObserverUpdateInterface<PresentacionProducto>() {
                    @Override
                    public void updateInterface(PresentacionProducto entity) 
                    {
                        getCmbPresentacionEmpaquetado().addItem(entity);
                        getCmbPresentacionEmpaquetado().setSelectedItem(entity);
                    }
                }, VentanaEnum.PRESENTACION_PRODUCTO, false, formularioActual);
            }
        });
        
        getBtnCrearMarca().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                //controlador.cargarMarcasActivas();
                //actualizarBindingCompontValues();
                
                panelPadre.crearDialogoCodefac(new ObserverUpdateInterface<MarcaProducto>() {
                    @Override
                    public void updateInterface(MarcaProducto entity) {
                        controlador.cargarMarcasActivas();
                        //controlador.producto.setMarcaProducto(entity);
                        //actualizarBindingCompontValues();
                        getCmbMarca().setSelectedItem(entity);
                        actualizarBindingComponent(true, true);
                        //controlador.getMarcaProductoList().add(entity);
                        //controlador.getProducto().setMarcaProducto(entity);
                        
                        //actualizarBindingComponent(true, true);
                    }
                }, VentanaEnum.MARCA_PRODUCTO, false, formularioActual);
            }
        });
        
        getBtnEliminarEmpaque().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                int filaTabla=getTblEmpaquetado().getSelectedRow();
                ProductoPresentacionDetalle productoEmpaque =(ProductoPresentacionDetalle) getTblEmpaquetado().getValueAt(filaTabla, 0);
                if(productoEmpaque!=null)
                {
                    controlador.producto.eliminarPresentacionProducto(productoEmpaque);
                    actualizarTablaEmpaques();
                }
            }
        });
        
        getBtnEditarEmpaque().addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                if(controlador!=null && controlador.producto!=null)
                {                
                    ProductoPresentacionDetalle productoEmpaque =(ProductoPresentacionDetalle) getTblEmpaquetado().getValueAt(getTblEmpaquetado().getSelectedRow(), 0);
                    if(productoEmpaque==null)
                    {
                        return;
                    }
                    
                    ObserverUpdateInterface observer = new ObserverUpdateInterface<Producto>() 
                    {
                        @Override
                        public void updateInterface(Producto entity) 
                        {
                            //Actualizar los datos al momento de recibir
                        }
                    };

                    Object[] parametroProductos={null,null,null,null,null};
                    panelPadre.crearDialogoCodefac(observer, VentanaEnum.PRODUCTO, false,productoEmpaque.getProductoEmpaquetado() ,parametroProductos,formularioInstancia);
                
                }
            }
        });
        
        getBtnBuscarProductoEnsamble().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductoBusquedaDialogo productoBusquedaDialogo = new ProductoBusquedaDialogo(session.getEmpresa());
                BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(productoBusquedaDialogo);
                buscarDialogoModel.setVisible(true);
                productoEnsamble = (Producto) buscarDialogoModel.getResultado();

                if (productoEnsamble != null) {
                    cargarComponenteProductoEnsambleVista(productoEnsamble, BigDecimal.ONE);
                    //getTxtProductoEnsamble().setText(productoEnsamble.getNombre());
                }
            }
        });
        
        getBtnAgregarEmpaque().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductoPresentacionDetalle presentacionDetalle=obtenerPresentacionEmpaqueVista();
                controlador.getProducto().addPresentacion(presentacionDetalle);
                actualizarTablaEmpaques();
                limpiarPresentacionVista();
            }
        });
        
        getBtnAgregarEnsamble().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductoEnsamble componenteEnsamble = new ProductoEnsamble();
                componenteEnsamble.setCantidad(new BigDecimal(getTxtCantidadEnsamble().getText()));
                componenteEnsamble.setComponenteEnsamble(productoEnsamble);
                controlador.producto.addProductoEnsamble(componenteEnsamble);
                actualizarTablaEnsamble();
                //DialogoCodefac.mensaje(new CodefacMs);
            }
        });
        
        getBtnEditarEnsamble().addActionListener(listenerEditarEnsamble);
        getBtnEliminarEnsamble().addActionListener(listenerEliminarEnsamble);

    }
    
    private void limpiarPresentacionVista()
    {
        getTxtCantidadEmpaquetado().setText("0");
        getTxtPrecioEmpaquetado().setText("");
    }
    
    private ActionListener listenerEliminarEnsamble=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(productoEnsambleEditar!=null)
            {
                controlador.producto.quitarProductoEnsamble(productoEnsambleEditar);
                cargarComponenteProductoEnsambleVista(null, BigDecimal.ONE);
                actualizarTablaEnsamble();
            }
        }
    };
    
    private ActionListener listenerEditarEnsamble=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            //int filaSeleccionada=getTblDatosEnsamble().getSelectedRowCount();
            if(productoEnsambleEditar!=null)
            {
                //ProductoEnsamble productoEnsamble=(ProductoEnsamble) getTblDatosEnsamble().getValueAt(filaSeleccionada,COLUMNA_ENSAMBLE_OBJECTO);                
                productoEnsambleEditar.setCantidad(new BigDecimal(getTxtCantidadEnsamble().getText()));
                actualizarTablaEnsamble();
                productoEnsambleEditar=null;
                cargarComponenteProductoEnsambleVista(null, BigDecimal.ONE);
            }
            else
            {
                
                //DialogoCodefac.mensaje(new CodefacMs);
            }
       }
    };
    
    private void actualizarTablaEmpaques()
    {
        String[] titulo = {"","Cantidad", "Tipo", "Presentacion"};
        UtilidadesTablas.crearModeloTabla(titulo,new Class[]{Object.class,String.class,String.class,String.class});
        DefaultTableModel tableModel = new DefaultTableModel(titulo, 0);
        
        if(controlador.producto!=null && controlador.producto.getPresentacionList()!=null)
        {
            for (ProductoPresentacionDetalle detalle : controlador.producto.getPresentacionList()) {
                Vector<Object> fila = new Vector<Object>();
                fila.add(detalle);
                fila.add(detalle.getCantidad());
                fila.add(detalle.getTipoEnum());
                fila.add((detalle.getPresentacionProducto()!=null)?detalle.getPresentacionProducto().getNombre():"");   
                tableModel.addRow(fila);
            }
        }        
        getTblEmpaquetado().setModel(tableModel);
        UtilidadesTablas.definirTamanioColumnas(getTblEmpaquetado(), new Integer[]{0});
    }

    private void actualizarTablaEnsamble() {
        String[] titulo = {"","Cantidad", "Nombre", "Precio Venta"};
        UtilidadesTablas.crearModeloTabla(titulo,new Class[]{Object.class,String.class,String.class,String.class});
        DefaultTableModel tableModel = new DefaultTableModel(titulo, 0);

        if(controlador.producto.getDetallesEnsamble()!=null)
        {
            for (ProductoEnsamble productoEnsamble : controlador.producto.getDetallesEnsamble()) {
                Vector<Object> fila = new Vector<Object>();
                fila.add(productoEnsamble);
                fila.add(productoEnsamble.getCantidad() + "");
                if(productoEnsamble.getComponenteEnsamble()!=null)
                {
                    fila.add(productoEnsamble.getComponenteEnsamble().getNombre());
                    fila.add(productoEnsamble.getComponenteEnsamble().getValorUnitario() + "");
                }
                else
                {
                    fila.add("");
                    fila.add("");
                }
                tableModel.addRow(fila);
            }
        }
        getTblDatosEnsamble().setModel(tableModel);
        UtilidadesTablas.definirTamanioColumnas(getTblDatosEnsamble(), new Integer[]{0});
    }
    
    private void setearValoresIniciales()
    {
        getTxtAplicacionProducto().setText("");
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
        getTxtCodigoEAN().setText(controlador.producto.getCodigoEAN());
        getTxtCodigoUPC().setText(controlador.producto.getCodigoUPC());


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
        //getTxtMarca().setText((controlador.producto.getMarca() != null) ? controlador.producto.getMarca() + "" : "");
        getTxtImagenProducto().setText((controlador.producto.getImagen() != null) ? controlador.producto.getImagen() + "" : "");
        //getCmbCategoriaProducto().setSelectedItem(producto.getCatalogoProducto().getCategoriaProducto());
        getTxtCaracteristica().setText((controlador.producto.getCaracteristicas() != null) ? controlador.producto.getCaracteristicas() + "" : "");
        getTxtObservaciones().setText((controlador.producto.getObservaciones() != null) ? controlador.producto.getObservaciones() + "" : "");
        //getCmbTipoProducto().setSelectedItem(controlador.producto.getTipoProductoEnum());
        getTxtPV4().setText((controlador.producto.getPvp4()!= null) ? controlador.producto.getPvp4() + "" : "0");
        getTxtPV5().setText((controlador.producto.getPvp5()!= null) ? controlador.producto.getPvp5() + "" : "0");
        getTxtPV6().setText((controlador.producto.getPvp6()!= null) ? controlador.producto.getPvp6() + "" : "0");
        getTxtAplicacionProducto().setText(controlador.producto.getAplicacionProducto());
        getTxtPrecio1SinSubsidio().setText((controlador.producto.getPrecioSinSubsidio()!=null)?controlador.producto.getPrecioSinSubsidio().toString():"0");
        
        
        getChkTransportarGuiaRemision().setSelected(controlador.producto.getTransportarEnGuiaRemisionEnum().getBool());
        getChkOcultarDetalleVenta().setSelected(controlador.producto.getOcultarDetalleVentaEnum().getBool());
        
        actualizarTablaEnsamble();
        actualizarTablaEmpaques();
         verificarVisibleBotonEditarPresentacion();
        
    }
    
    public void verificarVisibleBotonEditarPresentacion()
    {
        getBtnEditarEmpaque().setVisible(false);
        if (getEstadoFormularioEnum() != null && getEstadoFormularioEnum().equals(EstadoFormularioEnum.EDITAR)) 
        {
            getBtnEditarEmpaque().setVisible(true);
        }
    }

    /**
     * LOS datos del constructor externo son
     * [0] EnumSiNO
     * [1] Codigo producto
     * [2] Nombre producto
     * [3] Pvp sin iva
     * [4] Impuesto del Iva
     * @param parametros 
     */
    @Override
    public void postConstructorExterno(Object[] parametros) {
      
        //EnumSiNo enumSiNo=(EnumSiNo) parametros[0];
        //getCmbManejaInventario().setSelectedItem(enumSiNo);
        
        //TODO: Mejorar esta parte porque solo va a funcionar para el codigo personalizado y si quieren manejar algunos codigos no funciona
        if(parametros[1]!=null)
        {
            String codigoProducto=(String) parametros[1];
            getTxtCodigoPersonalizado().setText(codigoProducto);
        }
        
        if(parametros[2]!=null)
        {
            String nombreProducto=(String) parametros[2];
            getTextNombre().setText(nombreProducto);
        }
        
        if(parametros[3]!=null)
        {
            BigDecimal pvpPrecio=(BigDecimal) parametros[3];
            getTextValorUnitario().setText(pvpPrecio+"");
        }
        
        if(parametros[4]!=null)
        {
            ImpuestoDetalle impuestoDetalleIva=(ImpuestoDetalle) parametros[4];
            controlador.setIvaSeleccionado(impuestoDetalleIva);
        }
        
        /*if(parametros[5]!=null)
        {
            EstadoFormularioEnum estadoEnum = (EstadoFormularioEnum) parametros[5];
            if(estadoEnum.equals(estadoFormularioEnum.EDITAR))
            {
                //utilizar el nuevo estado
                this.estadoFormularioEnum=estadoEnum;                
            }
        }
        
        if(parametros[6]!=null)
        {
            Producto producto=(Producto) parametros[6];
            cargarDatosPantalla(producto);
        }*/
        
        
        actualizarBindingCompontValues();
    }

  
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

    /*@Override
    public void setearChkGenerarCodAutomatico(Boolean valor) {
        getChkGenerarCodigoAutomatico().setSelected(valor);
    }*/
    
    
    
    
}
