/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.compra.model;

import ec.com.codesoft.codefaclite.compra.panel.CompraPanel;
import java.awt.Color;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.DialogInterfacePanel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ObserverUpdateInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.compra.busqueda.CompraBusqueda;
import ec.com.codesoft.codefaclite.crm.busqueda.ProductoBusquedaDialogo;
import ec.com.codesoft.codefaclite.crm.busqueda.ProductoProveedorBusquedaDialogo;
import ec.com.codesoft.codefaclite.crm.busqueda.ProveedorBusquedaDialogo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Compra;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CompraDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ProductoProveedor;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoFacturacionEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.CompraServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ProductoProveedorServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencionIva;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencionRenta;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.OperadorNegocioEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.VentanaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.EmpresaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SriRetencionIvaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SriRetencionRentaServiceIf;
import ec.com.codesoft.ejemplo.utilidades.fecha.UtilidadesFecha;
import es.mityc.firmaJava.libreria.utilidades.Utilidades;
import es.mityc.firmaJava.ocsp.config.ServidorOcsp;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.text.Document;

/**
 *
 * @author Carlos
 */
public class CompraModel extends CompraPanel{

    /**
     * Referencia donde se va a almacenar la compra gestionado
     */
    private Empresa empresa;
    private Compra compra;
    private Producto productoSeleccionado;
    private Persona proveedor;
    private ProductoProveedor productoProveedor;
    private DefaultTableModel modeloTablaDetallesCompra;
    private BigDecimal subtotal12;
    private BigDecimal subtotal0;
    private Boolean bandera;
    private int filaDP;
    private Boolean banderaIngresoDetallesCompra;
    private Compra.RetencionEnumCompras estadoRetencion;
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        iniciarCombos();
        agregarListenerBotones();
        crearVariables();
        initModelTablaDetalleCompra();
        getCmbFechaCompra().setDate(new java.util.Date());
        desbloquearIngresoDetalleProducto();
        this.bandera = false;
        this.banderaIngresoDetallesCompra = false;
        bloquearDesbloquearBotones(true);
        setearVariblesIniciales();
        getTxtFPreimpreso().setText("001001000");
        try {
            mostrarVentanaRetenciones();
        } catch (RemoteException ex) {
            Logger.getLogger(CompraModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        Boolean respuesta = DialogoCodefac.dialogoPregunta("Alerta", "Si desea continuar se perderan los datos sin guardar?", DialogoCodefac.MENSAJE_ADVERTENCIA);
        if (!respuesta) {
            throw new ExcepcionCodefacLite("Cancelacion usuario");
        }
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        try {
            CompraServiceIf servicio=ServiceFactory.getFactory().getCompraServiceIf();
            setearValores();
            servicio.grabarCompra(compra);
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
        String preimpreso = "";
        compra.setClaveAcceso("");
        DocumentoEnum documentoEnum= (DocumentoEnum) getCmbDocumento().getSelectedItem();
        compra.setCodigoDocumento(documentoEnum.getCodigo());
        compra.setDireccion("");
        compra.setEstado("a"); //TODO: cambiar el estado de las ordenes de compra
        compra.setFechaCreacion(UtilidadesFecha.getFechaHoy());
        compra.setFechaFactura(new Date(getCmbFechaCompra().getDate().getTime()));
        compra.setIdentificacion("");
        compra.setProveedor(proveedor);
        
//        compra.setPuntoEmision(getTxtPuntoEmision().getText());
//        compra.setPuntoEstablecimiento(getTxtEstablecimiento().getText());
//        compra.setSecuencial(Integer.parseInt(getTxtSecuencial().getText()));
        compra.setPuntoEmision(getTxtFPreimpreso().getText().substring(0,2));
        compra.setPuntoEstablecimiento(getTxtFPreimpreso().getText().substring(4,6));
        compra.setSecuencial(Integer.parseInt(getTxtFPreimpreso().getText().substring(8, 10)));
        
        compra.setRazonSocial("");
        compra.setTelefono("");
        compra.setTipoFacturacion(""); //TODO: Establecer el metodo de facturacion manual y electronica
        compra.setInventarioIngreso(EnumSiNo.NO.getLetra());
        
        //Seteando el tipo de documento 
        TipoDocumentoEnum tipoDocumentoEnum= (TipoDocumentoEnum) getCmbTipoDocumento().getSelectedItem();
        compra.setCodigoTipoDocumento(tipoDocumentoEnum.getCodigo());
        
        estadoRetencion = Compra.RetencionEnumCompras.NO_EMITIDO;
        compra.setEstadoRetencion(estadoRetencion.getEstado());
    }

    @Override
    public void editar() throws ExcepcionCodefacLite {
        
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite {
        
    }

    @Override
    public void imprimir() {
        
    }

    @Override
    public void actualizar() throws ExcepcionCodefacLite {
        
    }

    @Override
    public void buscar() throws ExcepcionCodefacLite {
        CompraBusqueda compraBusqueda = new CompraBusqueda();
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(compraBusqueda);
        buscarDialogoModel.setVisible(true);
        Compra compra = (Compra)buscarDialogoModel.getResultado();
        if(compra != null)
        {
            this.compra = compra;
            String identificacion = this.compra.getProveedor().getIdentificacion();
            String nombre = this.compra.getProveedor().getRazonSocial();
            getTxtProveedor().setText(identificacion+" - "+nombre);
            //Observacion
            this.getTxtObservacion().setText("Por Defecto");
            //Preimpreso
            String preimpreso = "00"+this.compra.getPuntoEstablecimiento() +"00"+ this.compra.getPuntoEmision();
            String secuencial = ""+this.compra.getSecuencial();
            preimpreso += establecerSecuencial(secuencial);

            this.getTxtFPreimpreso().setText(preimpreso);
            //Autorizacion
            this.getTxtAutorizacion().setText("Por Defecto"); 
            //Fecha
            this.getCmbFechaCompra().setDate(this.compra.getFechaFactura());
            //Descuentos
            this.getTxtDescuentoImpuestos().setText(this.compra.getDescuentoImpuestos()+"");
            this.getTxtDescuentoSinImpuestos().setText(this.compra.getDescuentoSinImpuestos()+"");
            //Valores a mostrar del subtotal
            this.subtotal0 = new BigDecimal(BigInteger.ZERO) ;
            this.subtotal0 = this.subtotal0.add(this.compra.getDescuentoSinImpuestos()).add(this.compra.getSubtotalSinImpuestos());      
            this.subtotal12 = new BigDecimal(BigInteger.ZERO) ;
            this.subtotal12 = this.subtotal12.add(this.compra.getDescuentoImpuestos()).add(this.compra.getSubtotalImpuestos());
            this.getLblSubtotalImpuesto().setText(this.subtotal12 + "");
            this.getLblSubtotalSinImpuesto().setText(this.subtotal0 + "");
            //actualizarDatosMostrarVentana();
            mostrarDatosTabla();
            mostrarDatosTotales();
            desbloquearIngresoDetalleProducto();
        }
        
    }
    
    public String establecerSecuencial(String secuencial)
    {
        switch(secuencial.length())
        {
            case 1:
                secuencial = "00"+this.compra.getSecuencial();
            break;
            case 2:
                secuencial = "0"+this.compra.getSecuencial();
            break;
            case 3:
                secuencial = ""+this.compra.getSecuencial();
            break;
        }
        return  secuencial;
    }
    
    @Override
    public void limpiar() {
        getTxtCantidadItem().setText("1");
        getTxtOrdenCompra().setText("");
        getTxtProveedor().setText("");
        getTxtObservacion().setText("");
        getTxtFPreimpreso().setText("001001");
        getTxtAutorizacion().setText("");
        //Limpiar Detalles de Producto
        getTxtProductoItem().setText("");
        getTxtDescripcionItem().setText("");
        getTxtPrecionUnitarioItem().setText("");
        getTxtCantidadItem().setText("");
        //Limpiar Tabla de Detalles Producto
        initModelTablaDetalleCompra();
        //Limpiar totales
        getLblSubtotalImpuesto().setText("0.00");
        getLblSubtotalSinImpuesto().setText("0.00");
        getTxtDescuentoImpuestos().setText("0.00");
        getTxtDescuentoSinImpuestos().setText("0.00");
        getLblSubtotalImpuestos().setText("0.00");
        getLblIva().setText("0.00");
        getLblTotal().setText("0.00");
        getCmbFechaCompra().setDate(new java.util.Date());
        this.compra = new Compra();
        this.compra.setDetalles(new ArrayList<CompraDetalle>());
        bloquearIngresoDetalleProducto();    
    }

    @Override
    public String getNombre() {
        return "Compra";
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

    private void iniciarCombos() {
        //Agregar los documentos del sri
        getCmbDocumento().removeAllItems();
        List<DocumentoEnum> documentos= DocumentoEnum.obtenerDocumentoPorModulo(ModuloEnum.COMPRAS);
        for (DocumentoEnum documento : documentos) {
            getCmbDocumento().addItem(documento);
        }
        
        //Agregar los tipos de documentos disponibles
        getCmbTipoDocumento().removeAllItems();
        List<TipoDocumentoEnum> tipoDocumentos= TipoDocumentoEnum.obtenerTipoDocumentoPorModulo(ModuloEnum.COMPRAS);
        for (TipoDocumentoEnum tipoDocumento : tipoDocumentos) {
            getCmbTipoDocumento().addItem(tipoDocumento);
        }
        
        //Agregar los valores para ver si se cobra el iva o no
        EnumSiNo enumSiNo=(EnumSiNo) getCmbCobraIva().getSelectedItem();
        for (EnumSiNo enumerador : EnumSiNo.values()) {
            getCmbCobraIva().addItem(enumerador);
        }
        
        //Agregar los tipos de retencion Iva
        getCmbRetencionIva().removeAllItems();
        SriRetencionIvaServiceIf sriRetencionIvaService = ServiceFactory.getFactory().getSriRetencionIvaServiceIf();
        try{
            List<SriRetencionIva> tipoRetencionesIva = sriRetencionIvaService.obtenerTodos();
            for (SriRetencionIva tipoRetencione : tipoRetencionesIva) {
                getCmbRetencionIva().addItem(tipoRetencione);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        //Agregar los tipos de retencion Renta
        getCmbRetencionRenta().removeAllItems();
        SriRetencionRentaServiceIf sriRetencionRentaService = ServiceFactory.getFactory().getSriRetencionRentaServiceIf();
        try{
            List<SriRetencionRenta> tipoRetencionesRenta = sriRetencionRentaService.obtenerTodos();
            for (SriRetencionRenta sriRetencionRenta : tipoRetencionesRenta) {
                getCmbRetencionRenta().addItem(sriRetencionRenta);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private void agregarListenerBotones() {
        
        getBtnCrearProducto().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ObserverUpdateInterface observer = new ObserverUpdateInterface<Producto>() {
                    @Override
                    public void updateInterface(Producto entity) {
                        if (entity != null) {
                            String identificacion = proveedor.getIdentificacion();
                            String nombre = proveedor.getRazonSocial();
                            getTxtProveedor().setText(identificacion + " - " + nombre);
                            desbloquearIngresoDetalleProducto();
                        }
                    }
                };

                panelPadre.crearDialogoCodefac(observer, DialogInterfacePanel.PRODUCTO_PANEL, false);
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
                        proveedor=entity;
                        compra.setProveedor(entity);
                        if (compra.getProveedor() != null) {
                            String identificacion = proveedor.getIdentificacion();
                            String nombre = proveedor.getRazonSocial();
                            getTxtProveedor().setText(identificacion + " - " + nombre);
                            desbloquearIngresoDetalleProducto();
                        }
                    }
                }, VentanaEnum.CLIENTE, false,paramPostConstruct);
         
                //panelPadre.crearVentanaCodefac(VentanaEnum.CLIENTE,true,paramPostConstruct);
                
            }
        });
        
        getBtnProveedorBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProveedorBusquedaDialogo buscarBusquedaDialogo = new ProveedorBusquedaDialogo();
                BuscarDialogoModel buscarDialogo = new BuscarDialogoModel(buscarBusquedaDialogo);
                buscarDialogo.setVisible(true);
                proveedor = (Persona) buscarDialogo.getResultado();
                if (proveedor != null) {
                    String identificacion=proveedor.getIdentificacion();
                    String nombre =proveedor.getRazonSocial();
                    getTxtProveedor().setText(identificacion+" - "+nombre);
                    desbloquearIngresoDetalleProducto();
                }
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
                bloquearDesbloquearBotones(true);
            }
        });
        
        getBtnAgregarItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /**
                 * Seteo el valor que se ingresa en el costo para actualizar el valor del producto para ese proveedor
                 */
                agregarDetallesCompra(null);

            }
        });
        
        getTxtDescuentoImpuestos().addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                BigDecimal descuento = new BigDecimal(getTxtDescuentoImpuestos().getText());
                if(descuento.compareTo(subtotal12) == 1)
                {
                    DialogoCodefac.mensaje("Descuento", "El descuento no puede ser mayor que el subtotal con impuesto", DialogoCodefac.MENSAJE_ADVERTENCIA);
                    getTxtDescuentoImpuestos().setText(compra.getDescuentoImpuestos()+"");
                }
                else
                {
                    calcularDescuento(1,descuento);
                } 
            }

            @Override
            public void focusGained(FocusEvent e) {
                
            }
        });
        
        getTxtDescuentoSinImpuestos().addFocusListener(new FocusAdapter() {
            
            @Override
            public void focusLost(FocusEvent e)
            {
                BigDecimal descuento = new BigDecimal(getTxtDescuentoSinImpuestos().getText());
                if(descuento.compareTo(subtotal0) == 1)
                {
                    DialogoCodefac.mensaje("Descuento", "El descuento no puede ser mayor que el subtotal sin impuesto", DialogoCodefac.MENSAJE_ADVERTENCIA);
                    getTxtDescuentoSinImpuestos().setText(compra.getDescuentoSinImpuestos()+"");
                }
                else
                {
                    calcularDescuento(2,descuento);
                }
            }
            
            @Override
            public void focusGained(FocusEvent e)
            {
                
            }
            
        });
        
        getTblDetalleProductos().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt)
            {
                filaDP = getTblDetalleProductos().getSelectedRow();
                bandera = true;
                CompraDetalle compraDetalle = (CompraDetalle) compra.getDetalles().get(filaDP);
                getTxtProductoItem().setText(compraDetalle.getDescripcion());
                getTxtDescripcionItem().setText(compraDetalle.getDescripcion());
                getTxtCantidadItem().setText(compraDetalle.getCantidad()+"");
                getTxtPrecionUnitarioItem().setText(compraDetalle.getPrecioUnitario()+"");
                //compraDetalle.setPrecioUnitario
                compraDetalle.getPrecioUnitario();
                System.out.println("---------------->" + compraDetalle.getPrecioUnitario());
                bloquearDesbloquearBotones(false);
                //----------------------------------------------------------------------
                productoSeleccionado = compraDetalle.getProductoProveedor().getProducto();
                verificarExistenciadeProductoProveedor();
                
            }
        });
        
        getBtnEditarItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(bandera)
                {
                    bandera = false;
                    filaDP = getTblDetalleProductos().getSelectedRow();
                    CompraDetalle compraDetalle = compra.getDetalles().get(filaDP);
                    agregarDetallesCompra(compraDetalle);
                    calcularDescuento(1, new BigDecimal(getTxtDescuentoImpuestos().getText()));
                    calcularDescuento(2, new BigDecimal(getTxtDescuentoSinImpuestos().getText()));
                }
            }
        });
        
        getBtnEliminarItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(bandera)
                {
                    bandera = false;
                    System.out.println("Fila numero: " +filaDP );
                    System.out.println("Compra: "+compra.getDetalles().toString());
                    modeloTablaDetallesCompra.removeRow(filaDP);
                    compra.getDetalles().remove(filaDP);                   
                    actualizarDatosMostrarVentana();
                }
            }
        });
                
    }
    
    private void verificarExistenciadeProductoProveedor()
    {
        if(productoSeleccionado!=null)
                {
                    try {
                        //Buscar si existe el producto vinculado con un proveedor
                        ProductoProveedorServiceIf serviceProductoProveedor = ServiceFactory.getFactory().getProductoProveedorServiceIf();
                        Map<String, Object> mapParametros = new HashMap<String, Object>();
                        mapParametros.put("producto", productoSeleccionado);
                        mapParametros.put("proveedor", proveedor);
                        List<ProductoProveedor> resultados = serviceProductoProveedor.obtenerPorMap(mapParametros);
                        if (resultados != null && resultados.size() > 0) {
                            productoProveedor = resultados.get(0); //Si existe el proveedor solo seteo la variale
                            getTxtPrecionUnitarioItem().setText(productoProveedor.getCosto()+"");
                            EnumSiNo enumSiNo=EnumSiNo.getEnumByLetra(productoProveedor.getConIva());
                            getCmbCobraIva().setSelectedItem(enumSiNo);
                        }
                        else
                        {//Cuando no existe crea un nuevo producto proveedor
                            productoProveedor=new ProductoProveedor(); //Si no existe el item lo creo para posteriormente cuando grabe persistir con la base de datos
                            productoProveedor.setDescripcion("");
                            productoProveedor.setEstado("a");
                            productoProveedor.setProducto(productoSeleccionado);
                            productoProveedor.setProveedor(proveedor);
                            getTxtPrecionUnitarioItem().setText("0"); //Seteo con el valor de 0 porque no existe el costo grabado
                            getCmbCobraIva().setSelectedItem(EnumSiNo.SI); //Seteo por defecto el valor de SI cuando no existe en la base de datos
                        }
                        
                        getTxtProductoItem().setText(productoSeleccionado.getNombre());
                        getTxtDescripcionItem().setText(productoSeleccionado.getNombre());
                    } catch (RemoteException ex) {
                        Logger.getLogger(CompraModel.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ServicioCodefacException ex) {
                        Logger.getLogger(CompraModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
    }
    
    private void actualizarTotales()
    {
        List<CompraDetalle> detalles= compra.getDetalles();
        
        //Encerado Valores       
        compra.setIva(BigDecimal.ZERO);
        compra.setSubtotalImpuestos(BigDecimal.ZERO);
        compra.setSubtotalSinImpuestos(BigDecimal.ZERO);
        compra.setTotal(BigDecimal.ZERO);
        //compra.setDescuentoSinImpuestos(BigDecimal.ZERO);
        compra.setDescuentoSinImpuestos(compra.getDescuentoSinImpuestos().setScale(2,RoundingMode.HALF_UP));
        //compra.setDescuentoImpuestos(BigDecimal.ZERO);
        compra.setDescuentoImpuestos(compra.getDescuentoImpuestos().setScale(2,RoundingMode.HALF_UP));
        //LLamo metodos para calcular valores totales
        calcularSubtotal0(detalles);
        calcularSubtotal12(detalles);
        calcularIva12();
        calcularValorTotal();

    }
    
    public void calcularSubtotal0(List<CompraDetalle> detalles)
    {
        for(CompraDetalle detalle : detalles)
        {
            if(detalle.getProductoProveedor().getConIva().equals("n"))
            {
                this.compra.setSubtotalSinImpuestos(this.compra.getSubtotalSinImpuestos().add(detalle.getTotal()));
            }
        }
        this.compra.setSubtotalSinImpuestos(this.compra.getSubtotalSinImpuestos().setScale(2,RoundingMode.HALF_UP));
        this.subtotal0 = this.compra.getSubtotalSinImpuestos();
    }
    
    public void calcularSubtotal12(List<CompraDetalle> detalles)
    {
        for(CompraDetalle detalle : detalles)
        {
            if(detalle.getProductoProveedor().getConIva().equals("s"))
            {
                this.compra.setSubtotalImpuestos(this.compra.getSubtotalImpuestos().add(detalle.getTotal()));
            }
        }
        this.compra.setSubtotalImpuestos(this.compra.getSubtotalImpuestos().setScale(2,RoundingMode.HALF_UP));
        this.subtotal12 = this.compra.getSubtotalImpuestos();
    }
    
    public void calcularIva12()
    {
        this.compra.setIva(this.compra.getSubtotalImpuestos().multiply(new BigDecimal("0.120")));
        this.compra.setIva(this.compra.getIva().setScale(2,RoundingMode.HALF_UP));
    }
    
    public void calcularSubtotalSinImpuesto()
    {
        this.compra.setSubtotalSinImpuestos(this.subtotal0.subtract(this.compra.getDescuentoSinImpuestos()));
        this.compra.setSubtotalSinImpuestos(this.compra.getSubtotalSinImpuestos().setScale(2,RoundingMode.HALF_UP));
    }
    
    public void calcularSubtotalImpuesto()
    {
        this.compra.setSubtotalImpuestos(this.subtotal12.subtract(this.compra.getDescuentoImpuestos()));
        this.compra.setSubtotalImpuestos(this.compra.getSubtotalImpuestos().setScale(2,RoundingMode.HALF_UP));
    }
    
    public void calcularValorTotal()
    {    
        this.compra.setTotal(this.compra.getSubtotalImpuestos().add(this.compra.getSubtotalSinImpuestos().add(this.compra.getIva())));
    }
    
    /**
     * Actualiza los datos de la tabla segun los datos grabados en los detalles de la tabla
     * de compras
     */
    private void mostrarDatosTabla()
    {
        String[] titulo={"Cantidad","Descripción","ValorRetIVA","ValorRetRent","Valor Unitario","Valor Total"};
        this.modeloTablaDetallesCompra = new DefaultTableModel(titulo,0);
        List<CompraDetalle> detalles= compra.getDetalles();
        for (CompraDetalle detalle : detalles) {
            Vector<String> fila=new Vector<String>();
            fila.add(detalle.getCantidad()+"");
            fila.add(detalle.getDescripcion()+"");
            fila.add(detalle.getValorSriRetencionIVA()+"");
            fila.add(detalle.getValorSriRetencionRenta()+"");
            fila.add(detalle.getPrecioUnitario()+"");
            fila.add(detalle.getSubtotal()+"");
            this.modeloTablaDetallesCompra.addRow(fila);
        }
        
        getTblDetalleProductos().setModel(this.modeloTablaDetallesCompra);
        
    }
    
    private void mostrarDatosTotales()
    {
        getLblSubtotalSinImpuesto().setText(this.subtotal0 + "");
        getLblSubtotalImpuesto().setText(this.subtotal12 + "");
        getTxtDescuentoImpuestos().setText(compra.getDescuentoImpuestos()+"");
        getTxtDescuentoSinImpuestos().setText(compra.getDescuentoSinImpuestos()+"");
        getLblSubtotalImpuestos().setText(compra.getSubtotalImpuestos()+"");
        getLblSubtotalSinImpuestos().setText(compra.getSubtotalSinImpuestos()+"");
        getLblIva().setText(compra.getIva()+"");
        getLblTotal().setText(compra.getTotal()+"");
    }

    private void crearVariables() {
        this.compra = new Compra();
        this.empresa = new Empresa();
    }
    
    private void initModelTablaDetalleCompra() {
        Vector<String> titulo = new Vector<>();
        titulo.add("Cantidad");
        titulo.add("Descripción");
        titulo.add("Valor Ret. Iva.");
        titulo.add("Valor Ret. Rent.");
        titulo.add("Valor Unitario");
        titulo.add("Valor Total");
        
        this.modeloTablaDetallesCompra = new DefaultTableModel(titulo, 0);
        //this.modeloTablaDetallesProductos.isCellEditable
        getTblDetalleProductos().setModel(modeloTablaDetallesCompra);
    }
    
    private void desbloquearIngresoDetalleProducto()
    {
        getTxtDescripcionItem().setEnabled(true);
        getTxtProductoItem().setEnabled(true);
        getTxtCantidadItem().setEnabled(true);
        getTxtPrecionUnitarioItem().setEnabled(true);
        getCmbCobraIva().setEnabled(true);
    }
    
    private void bloquearIngresoDetalleProducto()
    {
        getTxtDescripcionItem().setEnabled(false);
        getTxtProductoItem().setEnabled(false);
        getTxtCantidadItem().setEnabled(false);
        getTxtPrecionUnitarioItem().setEnabled(false);
        getCmbCobraIva().setEnabled(false);
    }
    
    private void limpiarCampos()
    {
        getTxtDescripcionItem().setText("");
        getTxtPrecionUnitarioItem().setText("");
        getTxtProductoItem().setText("");
        getTxtCantidadItem().setText("");
        
    }
    
    private void bloquearDesbloquearBotones(Boolean b)
    {
        getBtnAgregarItem().setEnabled(b);
        getBtnEditarItem().setEnabled(!b);
        getBtnEliminarItem().setEnabled(!b);
    }
   
    private void agregarDetallesCompra(CompraDetalle compraDetalle)
    {
        Boolean agregar = true;
        
        if(compraDetalle != null){
            agregar = false;
        }
        else{
            compraDetalle = new CompraDetalle();
        }
        
        if (!panelPadre.validarPorGrupo("detalles")) {
            return;
        }
        
        if(verificarCamposValidados())
        {
            BigDecimal costo=new BigDecimal(getTxtPrecionUnitarioItem().getText());
            productoProveedor.setCosto(costo);
            EnumSiNo enumSiNo= (EnumSiNo) getCmbCobraIva().getSelectedItem();
            productoProveedor.setConIva(enumSiNo.getLetra());
            //Seteo los valores de los detalles e la compra
            compraDetalle.setCantidad(Integer.parseInt(getTxtCantidadItem().getText()));
            BigDecimal precioUnitario = new BigDecimal(getTxtPrecionUnitarioItem().getText()); 
            compraDetalle.setPrecioUnitario(precioUnitario.setScale(2,BigDecimal.ROUND_HALF_UP));
            compraDetalle.setCompra(compra);
            compraDetalle.setDescripcion(getTxtDescripcionItem().getText());
            compraDetalle.setDescuento(BigDecimal.ZERO);
            if(productoProveedor.getConIva().equals("s"))
            {
                compraDetalle.setIva(compraDetalle.calcularValorIva());
            }
            else
            {
                compraDetalle.setIva(BigDecimal.ZERO);
            }
            
            SriRetencionIva sriRetencionIva = (SriRetencionIva) getCmbRetencionIva().getSelectedItem();
            SriRetencionRenta sriRetencionRenta = (SriRetencionRenta) getCmbRetencionRenta().getSelectedItem();
            
            compraDetalle.setSriRetencionIva(sriRetencionIva);
            compraDetalle.setSriRetencionRenta(sriRetencionRenta);
            
            compraDetalle.setProductoProveedor(productoProveedor);
            compraDetalle.setTotal(compraDetalle.getSubtotal());
            
            BigDecimal valorRetencionIVA = compraDetalle.getIva().multiply(new BigDecimal(sriRetencionIva.getPorcentaje()+"")).divide(new BigDecimal("100"));
            BigDecimal valorRetencionRenta = compraDetalle.getTotal().multiply(new BigDecimal(sriRetencionRenta.getPorcentaje()+"")).divide(new BigDecimal("100"));
            
            
            compraDetalle.setValorSriRetencionIVA(valorRetencionIVA.setScale(2,BigDecimal.ROUND_HALF_UP));
            compraDetalle.setValorSriRetencionRenta(valorRetencionRenta.setScale(2,BigDecimal.ROUND_HALF_UP));
            
            BigDecimal valorTotalRetencion = valorRetencionIVA.add(valorRetencionRenta);
            
            //compraDetalle.setTotal(compraDetalle.getTotal().subtract(valorTotalRetencion));
            
            compraDetalle.setValorIce(BigDecimal.ZERO);

            if(agregar)
            {
                compra.addDetalle(compraDetalle);
                compra.setDescuentoImpuestos(new BigDecimal(getTxtDescuentoImpuestos().getText()));
                compra.setDescuentoSinImpuestos(new BigDecimal(getTxtDescuentoSinImpuestos().getText()));
            }
            
            actualizarDatosMostrarVentana();
        }
     
    }
    
    private void actualizarDatosMostrarVentana()
    {
        actualizarTotales();
        mostrarDatosTabla();
        mostrarDatosTotales();
        limpiarCampos();
    }
    
    private boolean verificarCamposValidados()
    {
        boolean b = true;
        List<JTextField> camposValidar = new ArrayList<JTextField>();
            camposValidar.add(getTxtCantidadItem());
            camposValidar.add(getTxtPrecionUnitarioItem());
            camposValidar.add(getTxtDescripcionItem());
            camposValidar.add(getTxtProductoItem());
            for(JTextField campo : camposValidar)
            {
                System.out.println("Color: -->" + campo.getBackground());
                if(!campo.getBackground().equals(Color.WHITE))
                {
                    b = false;
                }
            }
        return b;
    }
    
    public void calcularDescuento(int opc, BigDecimal descuento)
    {
        switch(opc)
        {
            case 1:
                compra.setDescuentoImpuestos(descuento.setScale(2, RoundingMode.HALF_UP));
                calcularSubtotalImpuesto();
                break;
            case 2:
                compra.setDescuentoSinImpuestos(descuento.setScale(2,RoundingMode.HALF_UP));
                calcularSubtotalSinImpuesto();
                break;
        }
        
        calcularIva12();
        calcularValorTotal();
        mostrarDatosTotales();
    }
    
    public void mostrarVentanaRetenciones() throws RemoteException
    {
        EmpresaServiceIf empresaService = ServiceFactory.getFactory().getEmpresaServiceIf();
        List<Empresa> listadoEmpresas = empresaService.obtenerTodos();
        this.empresa = listadoEmpresas.get(0);
        if(this.empresa.getObligadoLlevarContabilidad().equals(Empresa.SI_LLEVA_CONTABILIDAD))
        {
            this.getPanelRetencion().setVisible(true);
        }
        else
        {
            this.getPanelRetencion().setVisible(false);
        }
    }

    private void setearVariblesIniciales() {
        
        //Seleccionar el tipo de documento configurado por defecto para la compra
        ParametroCodefac parametroCodefac=session.getParametrosCodefac().get(ParametroCodefac.DEFECTO_TIPO_DOCUMENTO_COMPRA);
        TipoDocumentoEnum tipoDocumentoEnumDefault=TipoDocumentoEnum.obtenerTipoDocumentoPorCodigo(parametroCodefac.getValor());
        getCmbTipoDocumento().setSelectedItem(tipoDocumentoEnumDefault);
    }
}