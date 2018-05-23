/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.compra.model;

import ec.com.codesoft.codefaclite.compra.busqueda.CompraBusqueda;
import ec.com.codesoft.codefaclite.compra.busqueda.OrdenCompraBusqueda;
import ec.com.codesoft.codefaclite.compra.panel.OrdenCompraPanel;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.DialogInterfacePanel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ObserverUpdateInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.crm.busqueda.ProductoBusquedaDialogo;
import ec.com.codesoft.codefaclite.crm.busqueda.ProveedorBusquedaDialogo;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Compra;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CompraDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ProductoProveedor;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencionIva;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencionRenta;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.compra.OrdenCompra;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.compra.OrdenCompraDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.OperadorNegocioEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.VentanaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.CompraServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.OrdenCompraServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ProductoProveedorServiceIf;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Carlos
 */
public class OrdenCompraModel extends OrdenCompraPanel{
    
    private ProductoProveedor productoProveedor;
    /**
     * Referencia de la orden de compra para guardar
     */
    private OrdenCompra ordenCompra;
    
    /**
     * Producto seleccionado para agregar o editar
     */
    private Producto productoSeleccionado;
    
    /**
     * Modelo para las tablas
     */
    private DefaultTableModel modeloTablaDetallesCompra;
        
    private BigDecimal subtotal12;
    
    private BigDecimal subtotal0;

    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        listener();
        iniciarVariables();
        initModelTablaDetalleCompra();
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        try {
            OrdenCompraServiceIf servicio=ServiceFactory.getFactory().getOrdenCompraServiceIf();
            setearValores();
            servicio.grabar(ordenCompra);
            DialogoCodefac.mensaje("Correcto","La compra fue guardada correctamente",DialogoCodefac.MENSAJE_CORRECTO);
        } catch (ServicioCodefacException ex) {
            DialogoCodefac.mensaje("Incorrecto","No se puede gurdar la compra",DialogoCodefac.MENSAJE_INCORRECTO);
            Logger.getLogger(CompraModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(CompraModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void setearValores()
    {
        //DocumentoEnum documentoEnum= (DocumentoEnum) getCmbDocumento().getSelectedItem();
        //ordenCompra.setCodigoDocumento(documentoEnum.getCodigo());
        ordenCompra.setEstado("a"); //TODO: cambiar el estado de las ordenes de compra
        ordenCompra.setFechaCreacion(UtilidadesFecha.getFechaHoy());
        ordenCompra.setFechaIngreso(new Date(getCmbFechaIngreso().getDate().getTime()));
        //ordenCompra.setProveedor(proveedor);
                
        //Seteando el tipo de documento 
        TipoDocumentoEnum tipoDocumentoEnum= (TipoDocumentoEnum) getCmbTipoDocumento().getSelectedItem();
        ordenCompra.setCodigoTipoDocumento(tipoDocumentoEnum.getCodigo());
        

    }

    @Override
    public void editar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void imprimir() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buscar() throws ExcepcionCodefacLite {
        OrdenCompraBusqueda ordenCompraBusqueda = new OrdenCompraBusqueda();
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(ordenCompraBusqueda);
        buscarDialogoModel.setVisible(true);
        OrdenCompra ordenCompra = (OrdenCompra)buscarDialogoModel.getResultado();
        if(ordenCompra != null)
        {
            this.ordenCompra = ordenCompra;
            String identificacion = this.ordenCompra.getProveedor().getIdentificacion();
            String nombre = this.ordenCompra.getProveedor().getRazonSocial();
            getTxtProveedor().setText(identificacion+" - "+nombre);
            //Observacion
            this.getTxtObservacion().setText("Por Defecto");

            //Fecha
            this.getCmbFechaIngreso().setDate(this.ordenCompra.getFechaIngreso());
            //Descuentos
            this.getTxtDescuentoImpuestos().setText(this.ordenCompra.getDescuentoImpuestos()+"");
            this.getTxtDescuentoSinImpuestos().setText(this.ordenCompra.getDescuentoSinImpuestos()+"");
            //Valores a mostrar del subtotal
            this.subtotal0 = new BigDecimal(BigInteger.ZERO) ;
            this.subtotal0 = this.subtotal0.add(this.ordenCompra.getDescuentoSinImpuestos()).add(this.ordenCompra.getSubtotalSinImpuestos());      
            this.subtotal12 = new BigDecimal(BigInteger.ZERO) ;
            this.subtotal12 = this.subtotal12.add(this.ordenCompra.getDescuentoImpuestos()).add(this.ordenCompra.getSubtotalImpuestos());
            this.getLblSubtotalImpuesto().setText(this.subtotal12 + "");
            this.getLblSubtotalSinImpuesto().setText(this.subtotal0 + "");
            //actualizarDatosMostrarVentana();
            mostrarDatosTotales();
        }
    }

    @Override
    public void limpiar() {
        
        getTxtCantidadItem().setText("1");
        getTxtOrdenCompra().setText("");
        getTxtProveedor().setText("");
        getTxtObservacion().setText("");
        //Limpiar Detalles de Producto
        getTxtDescripcionItem().setText("");
        getTxtPrecionUnitarioItem().setText("");
        getTxtCantidadItem().setText("");

        //Limpiar totales
        getLblSubtotalImpuesto().setText("0.00");
        getLblSubtotalSinImpuesto().setText("0.00");
        getTxtDescuentoImpuestos().setText("0.00");
        getTxtDescuentoSinImpuestos().setText("0.00");
        getLblSubtotalImpuestos().setText("0.00");
        getLblIva().setText("0.00");
        getLblTotal().setText("0.00");
        getCmbFechaIngreso().setDate(new java.util.Date());
        
        ordenCompra=new OrdenCompra();        

        
    }

//    @Override
    public String getNombre() {
        return "Orden de Compra";
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
    
    private void iniciarVariables() {
        //Agregar los tipos de documentos disponibles
        getCmbTipoDocumento().removeAllItems();
        List<TipoDocumentoEnum> tipoDocumentos = TipoDocumentoEnum.obtenerTipoDocumentoPorModulo(ModuloEnum.COMPRAS);
        for (TipoDocumentoEnum tipoDocumento : tipoDocumentos) 
        {
            getCmbTipoDocumento().addItem(tipoDocumento);
        }
        
        //Agregar los valores para ver si se cobra el iva o no
        EnumSiNo enumSiNo = (EnumSiNo) getCmbCobraIva().getSelectedItem();
        for (EnumSiNo enumerador : EnumSiNo.values()) {
            getCmbCobraIva().addItem(enumerador);
        }
        
    }

    private void listener() {
        listenerBotones();
    }

    private void listenerBotones() {
        
        getBtnAgregarItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /**
                 * Seteo el valor que se ingresa en el costo para actualizar el
                 * valor del producto para ese proveedor
                 */
                agregarDetallesCompra(null);

            }
        });
        
        getBtnEditarItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //if (bandera) {
                    //bandera = false;
                    int fila = getTblDetalleProductos().getSelectedRow();
                    OrdenCompraDetalle ordenCompraDetalle = ordenCompra.getDetalles().get(fila);
                    agregarDetallesCompra(ordenCompraDetalle);
                    
                    calcularDescuento(1, new BigDecimal(getTxtDescuentoImpuestos().getText()));
                    calcularDescuento(2, new BigDecimal(getTxtDescuentoSinImpuestos().getText()));
                
            }
        });
        
        getBtnEliminarItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //if (bandera) {
                //    bandera = false;
                    int fila = getTblDetalleProductos().getSelectedRow();
                    System.out.println("Compra: " + ordenCompra.getDetalles().toString());
                    modeloTablaDetallesCompra.removeRow(fila);
                    ordenCompra.getDetalles().remove(fila);
                    actualizarDatosMostrarVentana();
                //}
            }
        });

        getBtnProveedorBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProveedorBusquedaDialogo buscarBusquedaDialogo = new ProveedorBusquedaDialogo();
                BuscarDialogoModel buscarDialogo = new BuscarDialogoModel(buscarBusquedaDialogo);
                buscarDialogo.setVisible(true);
                Persona proveedor = (Persona) buscarDialogo.getResultado();
                if (proveedor != null) {
                    String identificacion = proveedor.getIdentificacion();
                    String nombre = proveedor.getRazonSocial();
                    getTxtProveedor().setText(identificacion + " - " + nombre);
                    //desbloquearIngresoDetalleProducto();
                }
            }
        });
        
        
        getBtnAgregarProveedor().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] paramPostConstruct = new Object[1];
                paramPostConstruct[0] = OperadorNegocioEnum.PROVEEDOR;
                
                panelPadre.crearDialogoCodefac(new ObserverUpdateInterface<Persona>() {
                    @Override
                    public void updateInterface(Persona entity) {
                        
                        if (entity != null) {
                            ordenCompra.setProveedor(entity);
                            String identificacion = entity.getIdentificacion();
                            String nombre = entity.getRazonSocial();
                            getTxtProveedor().setText(identificacion + " - " + nombre);
                            //desbloquearIngresoDetalleProducto();
                        }
                    }
                }, VentanaEnum.CLIENTE, false,paramPostConstruct);
         
                //panelPadre.crearVentanaCodefac(VentanaEnum.CLIENTE,true,paramPostConstruct);
                
            }
        });
        
        
        getBtnBuscarProductoProveedor().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductoBusquedaDialogo buscarBusquedaDialogo = new ProductoBusquedaDialogo();
                BuscarDialogoModel buscarDialogo = new BuscarDialogoModel(buscarBusquedaDialogo);
                buscarDialogo.setVisible(true);
                productoSeleccionado = (Producto) buscarDialogo.getResultado();
                verificarExistenciadeProductoProveedor();
            }
        });
        
        
        getBtnCrearProducto().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ObserverUpdateInterface observer = new ObserverUpdateInterface<Producto>() {
                    @Override
                    public void updateInterface(Producto entity) {
                        if (entity != null) {
                            productoSeleccionado=entity;
                        }
                    }
                };

                panelPadre.crearDialogoCodefac(observer, DialogInterfacePanel.PRODUCTO_PANEL, false);
            }
        });
    }
    
    private void verificarExistenciadeProductoProveedor()
    {
        //Setear las varibales por defecto
        getTxtCantidadItem().setText("1");
        
        if (productoSeleccionado != null) {
            try {
                //Buscar si existe el producto vinculado con un proveedor
                ProductoProveedorServiceIf serviceProductoProveedor = ServiceFactory.getFactory().getProductoProveedorServiceIf();
                Map<String, Object> mapParametros = new HashMap<String, Object>();
                mapParametros.put("producto", productoSeleccionado);
                mapParametros.put("proveedor", ordenCompra.getProveedor());
                List<ProductoProveedor> resultados = serviceProductoProveedor.obtenerPorMap(mapParametros);
                if (resultados != null && resultados.size() > 0) {
                    productoProveedor = resultados.get(0); //Si existe el proveedor solo seteo la variale
                    getTxtPrecionUnitarioItem().setText(productoProveedor.getCosto() + "");
                    EnumSiNo enumSiNo = EnumSiNo.getEnumByLetra(productoProveedor.getConIva());
                    getCmbCobraIva().setSelectedItem(enumSiNo);
                } else {//Cuando no existe crea un nuevo producto proveedor
                    productoProveedor = new ProductoProveedor(); //Si no existe el item lo creo para posteriormente cuando grabe persistir con la base de datos
                    productoProveedor.setDescripcion("");
                    productoProveedor.setEstado("a");
                    productoProveedor.setProducto(productoSeleccionado);
                    productoProveedor.setProveedor(ordenCompra.getProveedor());
                    getTxtPrecionUnitarioItem().setText("0"); //Seteo con el valor de 0 porque no existe el costo grabado
                    getCmbCobraIva().setSelectedItem(EnumSiNo.SI); //Seteo por defecto el valor de SI cuando no existe en la base de datos
                }

                getTxtDescripcionItem().setText(productoSeleccionado.getNombre());
            } catch (RemoteException ex) {
                Logger.getLogger(CompraModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ServicioCodefacException ex) {
                Logger.getLogger(CompraModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void agregarDetallesCompra(OrdenCompraDetalle ordenCompraDetalle)
    {
        Boolean agregar = true;
        
        if(ordenCompraDetalle != null){
            agregar = false;
        }
        else{
            ordenCompraDetalle = new OrdenCompraDetalle();
        }
        
        if (!panelPadre.validarPorGrupo("detalles")) {
            return;
        }
        
        if (verificarCamposValidados()) {
            BigDecimal costo = new BigDecimal(getTxtPrecionUnitarioItem().getText());
            productoProveedor.setCosto(costo);
            EnumSiNo enumSiNo = (EnumSiNo) getCmbCobraIva().getSelectedItem();
            productoProveedor.setConIva(enumSiNo.getLetra());
            //Seteo los valores de los detalles e la compra
            ordenCompraDetalle.setCantidad(Integer.parseInt(getTxtCantidadItem().getText()));
            BigDecimal precioUnitario = new BigDecimal(getTxtPrecionUnitarioItem().getText());
            ordenCompraDetalle.setPrecioUnitario(precioUnitario.setScale(2, BigDecimal.ROUND_HALF_UP));
            ordenCompraDetalle.setOrdenCompra(ordenCompra);
            ordenCompraDetalle.setDescripcion(getTxtDescripcionItem().getText());
            ordenCompraDetalle.setDescuento(BigDecimal.ZERO);
            if (productoProveedor.getConIva().equals("s")) {
                ordenCompraDetalle.setIva(ordenCompraDetalle.calcularValorIva());
            } else {
                ordenCompraDetalle.setIva(BigDecimal.ZERO);
            }

            ordenCompraDetalle.setProductoProveedor(productoProveedor);
            ordenCompraDetalle.setTotal(ordenCompraDetalle.getSubtotal());


  
            //compraDetalle.setTotal(compraDetalle.getTotal().subtract(valorTotalRetencion));
            ordenCompraDetalle.setValorIce(BigDecimal.ZERO);

            if (agregar) {
                ordenCompra.addDetalle(ordenCompraDetalle);
                ordenCompra.setDescuentoImpuestos(new BigDecimal(getTxtDescuentoImpuestos().getText()));
                ordenCompra.setDescuentoSinImpuestos(new BigDecimal(getTxtDescuentoSinImpuestos().getText()));
            }

            actualizarDatosMostrarVentana();
        }
        
    }
    
    private void limpiarCampos() {
        getTxtDescripcionItem().setText("");
        getTxtPrecionUnitarioItem().setText("");
        getTxtCantidadItem().setText("");

    }
    
    private void mostrarDatosTotales() {
        getLblSubtotalSinImpuesto().setText(this.subtotal0 + "");
        getLblSubtotalImpuesto().setText(this.subtotal12 + "");
        getTxtDescuentoImpuestos().setText(ordenCompra.getDescuentoImpuestos() + "");
        getTxtDescuentoSinImpuestos().setText(ordenCompra.getDescuentoSinImpuestos() + "");
        getLblSubtotalImpuestos().setText(ordenCompra.getSubtotalImpuestos() + "");
        getLblSubtotalSinImpuestos().setText(ordenCompra.getSubtotalSinImpuestos() + "");
        getLblIva().setText(ordenCompra.getIva() + "");
        getLblTotal().setText(ordenCompra.getTotal() + "");
    }
    
        /**
     * Actualiza los datos de la tabla segun los datos grabados en los detalles de la tabla
     * de compras
     */
    private void mostrarDatosTabla()
    {
        String[] titulo={"Cantidad","Descripción","Valor Unitario","Valor Total"};
        this.modeloTablaDetallesCompra = new DefaultTableModel(titulo,0);
        List<OrdenCompraDetalle> detalles= ordenCompra.getDetalles();
        for (OrdenCompraDetalle detalle : detalles) {
            Vector<String> fila=new Vector<String>();
            fila.add(detalle.getCantidad()+"");
            fila.add(detalle.getDescripcion()+"");
            fila.add(detalle.getPrecioUnitario()+"");
            fila.add(detalle.getSubtotal()+"");
            this.modeloTablaDetallesCompra.addRow(fila);
        }
        
        getTblDetalleProductos().setModel(this.modeloTablaDetallesCompra);
    }
    
    private void actualizarDatosMostrarVentana() {
        actualizarTotales();
        mostrarDatosTabla();
   
        mostrarDatosTotales();
        limpiarCampos();
    }
    
    private void actualizarTotales() {
        List<OrdenCompraDetalle> detalles = ordenCompra.getDetalles();

        //Encerado Valores       
        ordenCompra.setIva(BigDecimal.ZERO);
        ordenCompra.setSubtotalImpuestos(BigDecimal.ZERO);
        ordenCompra.setSubtotalSinImpuestos(BigDecimal.ZERO);
        ordenCompra.setTotal(BigDecimal.ZERO);
        //compra.setDescuentoSinImpuestos(BigDecimal.ZERO);
        ordenCompra.setDescuentoSinImpuestos(ordenCompra.getDescuentoSinImpuestos().setScale(2, RoundingMode.HALF_UP));
        //compra.setDescuentoImpuestos(BigDecimal.ZERO);
        ordenCompra.setDescuentoImpuestos(ordenCompra.getDescuentoImpuestos().setScale(2, RoundingMode.HALF_UP));
        //LLamo metodos para calcular valores totales
        calcularSubtotal0(detalles);
        calcularSubtotal12(detalles);
        calcularIva12();
        calcularValorTotal();

    }

    private boolean verificarCamposValidados() {
        boolean b = true;
        List<JTextField> camposValidar = new ArrayList<JTextField>();
        camposValidar.add(getTxtCantidadItem());
        camposValidar.add(getTxtPrecionUnitarioItem());
        camposValidar.add(getTxtDescripcionItem());
        for (JTextField campo : camposValidar) {
            System.out.println("Color: -->" + campo.getBackground());
            if (!campo.getBackground().equals(Color.WHITE)) {
                b = false;
            }
        }
        return b;
    }
    
    public void calcularSubtotal0(List<OrdenCompraDetalle> detalles) {
        for (OrdenCompraDetalle detalle : detalles) {
            if (detalle.getProductoProveedor().getConIva().equals("n")) {
                this.ordenCompra.setSubtotalSinImpuestos(this.ordenCompra.getSubtotalSinImpuestos().add(detalle.getTotal()));
            }
        }
        this.ordenCompra.setSubtotalSinImpuestos(this.ordenCompra.getSubtotalSinImpuestos().setScale(2, RoundingMode.HALF_UP));
        this.subtotal0 = this.ordenCompra.getSubtotalSinImpuestos();
    }

    public void calcularSubtotal12(List<OrdenCompraDetalle> detalles) {
        for (OrdenCompraDetalle detalle : detalles) {
            if (detalle.getProductoProveedor().getConIva().equals("s")) {
                this.ordenCompra.setSubtotalImpuestos(this.ordenCompra.getSubtotalImpuestos().add(detalle.getTotal()));
            }
        }
        this.ordenCompra.setSubtotalImpuestos(this.ordenCompra.getSubtotalImpuestos().setScale(2, RoundingMode.HALF_UP));
        this.subtotal12 = this.ordenCompra.getSubtotalImpuestos();
    }

    public void calcularIva12() {
        this.ordenCompra.setIva(this.ordenCompra.getSubtotalImpuestos().multiply(new BigDecimal("0.120")));
        this.ordenCompra.setIva(this.ordenCompra.getIva().setScale(2, RoundingMode.HALF_UP));
    }

    public void calcularSubtotalSinImpuesto() {
        this.ordenCompra.setSubtotalSinImpuestos(this.subtotal0.subtract(this.ordenCompra.getDescuentoSinImpuestos()));
        this.ordenCompra.setSubtotalSinImpuestos(this.ordenCompra.getSubtotalSinImpuestos().setScale(2, RoundingMode.HALF_UP));
    }

    public void calcularSubtotalImpuesto() {
        this.ordenCompra.setSubtotalImpuestos(this.subtotal12.subtract(this.ordenCompra.getDescuentoImpuestos()));
        this.ordenCompra.setSubtotalImpuestos(this.ordenCompra.getSubtotalImpuestos().setScale(2, RoundingMode.HALF_UP));
    }

    public void calcularValorTotal() {
        this.ordenCompra.setTotal(this.ordenCompra.getSubtotalImpuestos().add(this.ordenCompra.getSubtotalSinImpuestos().add(this.ordenCompra.getIva())));
    }
    
    private void initModelTablaDetalleCompra() {
        Vector<String> titulo = new Vector<>();
        titulo.add("Cantidad");
        titulo.add("Descripción");
        titulo.add("Valor Unitario");
        titulo.add("Valor Total");

        this.modeloTablaDetallesCompra = new DefaultTableModel(titulo, 0);
        //this.modeloTablaDetallesProductos.isCellEditable
        getTblDetalleProductos().setModel(modeloTablaDetallesCompra);
    }
    
    public void calcularDescuento(int opc, BigDecimal descuento)
    {
        switch(opc)
        {
            case 1:
                ordenCompra.setDescuentoImpuestos(descuento.setScale(2, RoundingMode.HALF_UP));
                calcularSubtotalImpuesto();
                break;
            case 2:
                ordenCompra.setDescuentoSinImpuestos(descuento.setScale(2,RoundingMode.HALF_UP));
                calcularSubtotalSinImpuesto();
                break;
        }
        
        calcularIva12();
        calcularValorTotal();
        mostrarDatosTotales();
    }
    
    

    
}
