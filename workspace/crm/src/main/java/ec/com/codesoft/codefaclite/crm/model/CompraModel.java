/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.crm.model;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.crm.busqueda.ProductoBusquedaDialogo;
import ec.com.codesoft.codefaclite.crm.busqueda.ProductoProveedorBusquedaDialogo;
import ec.com.codesoft.codefaclite.crm.busqueda.ProveedorBusquedaDialogo;
import ec.com.codesoft.codefaclite.crm.panel.CompraPanel;
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
import ec.com.codesoft.ejemplo.utilidades.fecha.UtilidadesFecha;
import es.mityc.firmaJava.libreria.utilidades.Utilidades;
import es.mityc.firmaJava.ocsp.config.ServidorOcsp;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private Compra compra;
    private Producto productoSeleccionado;
    private Persona proveedor;
    private ProductoProveedor productoProveedor;
    private DefaultTableModel modeloTablaDetallesCompra;
    
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        iniciarCombos();
        agregarListenerBotones();
        crearVariables();
        initModelTablaDetalleCompra();
        getCmbFechaCompra().setDate(new java.util.Date());
        desbloquearIngresoDetalleProducto();
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
        compra.setClaveAcceso("");
        DocumentoEnum documentoEnum= (DocumentoEnum) getCmbDocumento().getSelectedItem();
        compra.setCodigoDocumento(documentoEnum.getCodigo());
        compra.setDireccion("");
        compra.setEstado("a"); //TODO: cambiar el estado de las ordenes de compra
        compra.setFechaCreacion(UtilidadesFecha.getFechaHoy());
        compra.setFechaFactura(new Date(getCmbFechaCompra().getDate().getTime()));
        compra.setIdentificacion("");
        compra.setProveedor(proveedor);
        
        compra.setPuntoEmision(getTxtPuntoEmision().getText());
        compra.setPuntoEstablecimiento(getTxtEstablecimiento().getText());
        compra.setSecuencial(Integer.parseInt(getTxtSecuencial().getText()));
        
        compra.setRazonSocial("");
        compra.setTelefono("");
        compra.setTipoFacturacion(""); //TODO: Establecer el metodo de facturacion manual y electronica
        compra.setInventarioIngreso(EnumSiNo.NO.getLetra());
        
        //Seteando el tipo de documento 
        TipoDocumentoEnum tipoDocumentoEnum= (TipoDocumentoEnum) getCmbTipoDocumento().getSelectedItem();
        compra.setCodigoTipoDocumento(tipoDocumentoEnum.getCodigo());
 
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
        
    }

    @Override
    public void limpiar() {
        getTxtCantidadItem().setText("1");
        getTxtOrdenCompra().setText("");
        getTxtProveedor().setText("");
        getTxtObservacion().setText("");
        getTxtSecuencial().setText("");
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
        getLblIva().setText("0.00");
        getLblTotal().setText("0.00");
        getCmbFechaCompra().setDate(new java.util.Date());
        //Bloquear Campos Detalles producto
        bloquearIngresoDetalleProducto();
        
    }

    @Override
    public String getNombre() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        
    }

    private void agregarListenerBotones() {
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
        });
        
        getBtnAgregarItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /**
                 * Seteo el valor que se ingresa en el costo para actualizar el valor del producto para ese proveedor
                 */
                BigDecimal costo=new BigDecimal(getTxtPrecionUnitarioItem().getText());
                productoProveedor.setCosto(costo);
                
                EnumSiNo enumSiNo= (EnumSiNo) getCmbCobraIva().getSelectedItem();
                productoProveedor.setConIva(enumSiNo.getLetra());
                
                //Seteo los valores de los detalles e la compra
                CompraDetalle compraDetalle=new CompraDetalle();
                compraDetalle.setCantidad(Integer.parseInt(getTxtCantidadItem().getText()));
                compraDetalle.setPrecioUnitario(new BigDecimal(getTxtPrecionUnitarioItem().getText()));
                compraDetalle.setCompra(compra);
                compraDetalle.setDescripcion(getTxtDescripcionItem().getText());
                compraDetalle.setDescuento(BigDecimal.ZERO);
                compraDetalle.setIva(compraDetalle.calcularValorIva());
                compraDetalle.setProductoProveedor(productoProveedor);
                compraDetalle.setTotal(compraDetalle.calcularTotal());
                compraDetalle.setValorIce(BigDecimal.ZERO);
                compra.addDetalle(compraDetalle);
                
                /**
                 * Cargar los otros valores como el descuento
                 */
                compra.setDescuentoImpuestos(new BigDecimal(getTxtDescuentoImpuestos().getText()));
                compra.setDescuentoSinImpuestos(new BigDecimal(getTxtDescuentoSinImpuestos().getText()));
                actualizarTotales();
                mostrarDatosTabla();
                mostrarDatosTotales();
            }
        });
    }
    
    private void actualizarTotales()
    {
        List<CompraDetalle> detalles= compra.getDetalles();
        
        //Encerado Valores       
        compra.setIva(BigDecimal.ZERO);
        compra.setSubtotalImpuestos(BigDecimal.ZERO);
        compra.setSubtotalSinImpuestos(BigDecimal.ZERO);
        compra.setTotal(BigDecimal.ZERO);
                
        for (CompraDetalle detalle : detalles) {
             compra.setIva(compra.getIva().add(detalle.getIva()));
             compra.setSubtotalImpuestos(compra.getSubtotalImpuestos());
             compra.setSubtotalSinImpuestos(compra.getSubtotalSinImpuestos());
             compra.setTotal(compra.getTotal().add(detalle.getTotal()));
        }
    }
    
    /**
     * Actualiza los datos de la tabla segun los datos grabados en los detalles de la tabla
     * de compras
     */
    private void mostrarDatosTabla()
    {
        String[] titulo={"Cantidad","Descripción","Valor Unitario","Valor Total"};
        DefaultTableModel modeloTablaCompra=new DefaultTableModel(titulo,0);
        
        List<CompraDetalle> detalles= compra.getDetalles();
        for (CompraDetalle detalle : detalles) {
            Vector<String> fila=new Vector<String>();
            fila.add(detalle.getCantidad()+"");
            fila.add(detalle.getDescripcion()+"");
            fila.add(detalle.getPrecioUnitario()+"");
            fila.add(detalle.getSubtotal()+"");
            modeloTablaCompra.addRow(fila);
        }
        
        getTblDetalleProductos().setModel(modeloTablaCompra);
        
    }
    
    private void mostrarDatosTotales()
    {
        getLblIva().setText(compra.getIva()+"");
        getLblSubtotalImpuesto().setText(compra.getSubtotalImpuestos()+"");
        getLblSubtotalSinImpuesto().setText(compra.getSubtotalSinImpuestos()+"");
        getLblTotal().setText(compra.getTotal()+"");        
    }

    private void crearVariables() {
        this.compra=new Compra();        
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
    
    private void desbloquearIngresoDetalleProducto()
    {
        getTxtDescripcionItem().enable(true);
        getTxtProductoItem().enable(true);
        getTxtCantidadItem().enable(true);
        getTxtPrecionUnitarioItem().enable(true);
    }
    
    private void bloquearIngresoDetalleProducto()
    {
        getTxtDescripcionItem().enable(false);
        getTxtProductoItem().enable(false);
        getTxtCantidadItem().enable(false);
        getTxtPrecionUnitarioItem().enable(false);
    }
    
}
