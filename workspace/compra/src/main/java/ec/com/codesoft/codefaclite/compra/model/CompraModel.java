/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.compra.model;

import ec.com.codesoft.codefaclite.compra.busqueda.CompraBusquedaDialogo;
import ec.com.codesoft.codefaclite.compra.panel.CompraPanel;
import java.awt.Color;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ObserverUpdateInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.compra.busqueda.OrdenCompraBusqueda;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.FacturaBusqueda;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.LoteBusqueda;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ProductoBusquedaDialogoFactory;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ProveedorBusquedaDialogo;
import static ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface.ESTADO_EDITAR;
import static ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface.ESTADO_GRABAR;
import ec.com.codesoft.codefaclite.controlador.model.AuditoriaInformacionModel;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteElectronicoService;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Compra;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CompraDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ProductoProveedor;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.CompraServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ProductoProveedorServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CompraFacturaReembolso;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ImpuestoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Lote;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PersonaEstablecimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PresentacionProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencionIva;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencionRenta;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.compra.OrdenCompra;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.compra.OrdenCompraDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CrudEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.OperadorNegocioEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoProductoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.VentanaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.sri.SriSustentoComprobanteEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.servidorinterfaz.parameros.CarteraParametro;
import ec.com.codesoft.codefaclite.servidorinterfaz.reportData.ProductoPrecioDataTable;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.EmpresaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SriRetencionIvaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SriRetencionRentaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.ParametroUtilidades;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.rmi.UtilidadesRmi;
import ec.com.codesoft.codefaclite.utilidades.swing.UtilidadesComboBox;
import ec.com.codesoft.codefaclite.utilidades.swing.UtilidadesFormularios;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesNumeros;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesSistema;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesSwing;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesSwingX;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author Carlos
 */
public class CompraModel extends CompraPanel{
    
    private static final int INDICE_OBJ_TABLA_REEMBOLSO=0;

    /**
     * Referencia donde se va a almacenar la compra gestionado
     */
    //private Empresa empresa;tblDetalleProductos
    private Compra compra;
    private Producto productoSeleccionado;
    private Lote loteSeleccionado;
    //private Persona proveedor;
    private ProductoProveedor productoProveedor;
    private DefaultTableModel modeloTablaDetallesCompra;
    private DefaultTableModel modeloTablaCompraReembolso;
    private Boolean banderaEdicion;
    //private int filaDP;
    private Boolean banderaIngresoDetallesCompra;
    private Compra.RetencionEnumCompras estadoRetencion;

    public CompraModel() 
    {
        super.validacionDatosIngresados=false;
    }
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        inicarComponentesGraficos();
        iniciarCombos();
        agregarListerCombos();
        agregarListenerBotones();
        agregarListenerTextoBox();
        agregarListenerPopUps();
        crearVariables();
        if(session.getEmpresa() != null){
            if(session.getEmpresa().getObligadoLlevarContabilidad().equalsIgnoreCase(Empresa.SI_LLEVA_CONTABILIDAD))
            {
                initModelTablaDetalleCompra();
            }else{
                initModelTablaDetalleCompraSinRetencion();
            }
        }else{
            DialogoCodefac.mensaje("Advertencia", "No existe información de empresa", DialogoCodefac.MENSAJE_ADVERTENCIA);
        }
        getCmbFechaCompra().setDate(new java.util.Date());
        desbloquearIngresoDetalleProducto();
        this.banderaEdicion = false;
        this.banderaIngresoDetallesCompra = false;
        bloquearDesbloquearBotones(true);
        setearVariblesIniciales();
        //getTxtFPreimpreso().setText("001001000");
        listenerTexts();
        try {
            mostrarVentanaRetenciones();
        } catch (RemoteException ex) {
            Logger.getLogger(CompraModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        agregarListenerPopUp();
    }
    
    private void listenerModificarPrecioVenta(Producto productoSeleccionado,BigDecimal precioCompra)
    {
        List<ProductoPrecioDataTable> productoList=new ArrayList<ProductoPrecioDataTable>();
        
        ProductoPrecioDataTable productoDataTable=new ProductoPrecioDataTable(productoSeleccionado, precioCompra);
        
        productoList.add(productoDataTable);
        
        Object[] parametros = {productoList};
        
        panelPadre.crearDialogoCodefac(new ObserverUpdateInterface<Object>() {
            @Override
            public void updateInterface(Object entity) {
                
            }
        }, VentanaEnum.UTILIDAD_PRECIO, true,parametros, this);
        
    }

    private void agregarListenerPopUps()
    {
        JPopupMenu jPopupMenu = new JPopupMenu();
        JMenuItem jMenuItemDatoAdicional = new JMenuItem("Modificar Precios de Venta");
        
        jMenuItemDatoAdicional.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada=getTblDetalleProductos().getSelectedRow();
                if(filaSeleccionada>=0)
                {
                    CompraDetalle compraDetalle = (CompraDetalle) getTblDetalleProductos().getValueAt(filaSeleccionada, 0);
                    BigDecimal costo=compraDetalle.getCostoUnitario();
                    
                    if(costo==null || costo.compareTo(BigDecimal.ZERO)==0)
                    {
                        costo=compraDetalle.getPrecioUnitario();
                    }
                    
                    listenerModificarPrecioVenta(compraDetalle.getProductoProveedor().getProducto(),costo);
                }
                
            }
        });
        
        jPopupMenu.add(jMenuItemDatoAdicional);
        getTblDetalleProductos().setComponentPopupMenu(jPopupMenu);
    }
    
    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        
        /*if(compra != null && compra.getDetalles().size()>0 || compra.getProveedor() != null || compra.getOrdenCompra() != null)
        {
            Boolean respuesta = DialogoCodefac.dialogoPregunta("Alerta", "Si desea continuar se perderan los datos sin guardar?", DialogoCodefac.MENSAJE_ADVERTENCIA);
            if (!respuesta) {
                throw new ExcepcionCodefacLite("Cancelacion usuario");
            }
        }*/
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        try {
            
            if(!validarDatosGrabar())
            {
                throw new ExcepcionCodefacLite("Error de validación");
            }
            
            CompraServiceIf servicio=ServiceFactory.getFactory().getCompraServiceIf();
            setearValores();
            CarteraParametro carteraParametro=new CarteraParametro(
                    Boolean.TRUE, 
                    (Integer)getTxtDiasCredito().getValue());      
            
            carteraParametro.pagarConCaja=getChkPagarCaja().isSelected();
            
            servicio.grabarCompra(compra,carteraParametro);
            DialogoCodefac.mensaje("Correcto","La compra fue guardada correctamente",DialogoCodefac.MENSAJE_CORRECTO);
        } catch (ServicioCodefacException ex) {
            DialogoCodefac.mensaje("Error al grabar la compra",ex.getMessage(),DialogoCodefac.MENSAJE_INCORRECTO);
            Logger.getLogger(CompraModel.class.getName()).log(Level.SEVERE, null, ex);
            throw new ExcepcionCodefacLite(ex.getMessage());
        } catch (RemoteException ex) {
            Logger.getLogger(CompraModel.class.getName()).log(Level.SEVERE, null, ex);
            throw new ExcepcionCodefacLite(ex.getMessage());
        }
    }
    
    private void setearValores() throws ServicioCodefacException
    {
        Persona.TipoIdentificacionEnum tipoIdentificacionEnum=compra.getProveedor().getTipoIdentificacionEnum();
        if(tipoIdentificacionEnum==null)
        {
            throw new ServicioCodefacException("El proveedor no tiene definido un Tipo de identificación");
        }
        
        String codigoSri=tipoIdentificacionEnum.getCodigoSriCompra();
        compra.setTipoIdentificacionCodigoSri(codigoSri); //TODO: Ver si esta variable se debe grabar en el servidor
        
        String preimpreso = "";
        compra.setClaveAcceso("");
        DocumentoEnum documentoEnum= (DocumentoEnum) getCmbDocumento().getSelectedItem();
        compra.setEmpresa(session.getEmpresa());
        compra.setCodigoDocumento(documentoEnum.getCodigo());      
        compra.setEstado(GeneralEnumEstado.ACTIVO.getEstado()); //TODO: cambiar el estado de las ordenes de compra; El estado deberia ponerse por defecto en el servicio
        compra.setFechaCreacion(UtilidadesFecha.getFechaHoy());
        compra.setFechaFactura(new Date(getCmbFechaCompra().getDate().getTime()));
        compra.setUsuario(session.getUsuario());
        
        compra.setIdentificacion(compra.getProveedor().getIdentificacion());
        compra.setRazonSocial(compra.getProveedor().getRazonSocial());
        compra.setTelefono(compra.getProveedor().getEstablecimientos().get(0).getTelefonoCelular()); //Todo: ver si se modifica para trabajar por sucursales
        compra.setDireccion(compra.getProveedor().getEstablecimientos().get(0).getDireccion());//Todo: ver si se modifica para trabajar por sucursales
       
        compra.setPuntoEmision(UtilidadesNumeros.castStringToInteger(getTxtPuntoEmisionCompra().getText()));
        compra.setPuntoEstablecimiento(UtilidadesNumeros.castStringToBigDecimal(getTxtEstablecimientoCompra().getText()));
        compra.setSecuencial(UtilidadesNumeros.castStringToInteger(getTxtSecuencialCompra().getText()));
        
        compra.setTipoFacturacion(""); //TODO: Establecer el metodo de facturacion manual y electronica
        //compra.setInventarioIngreso(EnumSiNo.NO.getLetra());
        compra.setObservacion(getTxtObservacion().getText());
        compra.setAutorizacion(getTxtAutorizacion().getText().trim());
        
        //Seteando el tipo de documento 
        TipoDocumentoEnum tipoDocumentoEnum= (TipoDocumentoEnum) getCmbTipoDocumento().getSelectedItem();
        compra.setCodigoTipoDocumento(tipoDocumentoEnum.getCodigo());
        compra.setSucursalEmpresa(session.getSucursal());
        
        /*if(session.getEmpresa().getObligadoLlevarContabilidad().equals(Empresa.SI_LLEVA_CONTABILIDAD)){
            estadoRetencion = Compra.RetencionEnumCompras.NO_EMITIDO;
        }else{
            estadoRetencion = Compra.RetencionEnumCompras.SIN_CONTABILIDAD;
        }*/
        if(((EnumSiNo)getCmbEmitirRetencion().getSelectedItem()).equals(EnumSiNo.SI))
        {
            estadoRetencion=Compra.RetencionEnumCompras.NO_EMITIDO;
        }else
        {
            estadoRetencion=Compra.RetencionEnumCompras.SIN_CONTABILIDAD;
        }
        
        if(estadoFormulario.equals(ESTADO_GRABAR))
        {
            compra.setEstadoRetencion(estadoRetencion.getEstado());
        }
        
        compra.setCodigoComprobanteSriEnum(documentoEnum);
        //compra.setCodigoSustentoSriEnum((SriSustentoComprobanteEnum) getCmbSustentoComprobante().getSelectedItem());
        //Setear el tipo de emision de la factura
        if(getRdbEmisionElectronica().isSelected())
        {
            compra.setTipoFacturacion(ComprobanteEntity.TipoEmisionEnum.ELECTRONICA);
        }else if(getRdbEmisionFisica().isSelected())
        {
            compra.setTipoFacturacion(ComprobanteEntity.TipoEmisionEnum.NORMAL);
        }
    }

    @Override
    public void editar() throws ExcepcionCodefacLite {
        try {
            if (!validarDatosGrabar()) {
                throw new ExcepcionCodefacLite("Error de validación");
            }
            setearValores();            
            
            //Verificar que el producto aun no este ingresado en el inventario o si no le debe pedir clave de autorizacion
            if (EnumSiNo.SI.equals(compra.getInventarioIngresoEnum())) 
            {
                //Hacer una verificacion basica con los totales solo para verificar que no fueron modificados los totales significa en teria que los detalles son iguales
                Compra compraOriginal=ServiceFactory.getFactory().getCompraServiceIf().buscarPorId(compra.getId());                        
                BigDecimal diferencia=compraOriginal.getTotal().subtract(compra.getTotal()).abs();
                //Si la compra tiene una diferencia hasta 2 ctv entonces si permite grabar
                if(new BigDecimal("3").compareTo(diferencia)<=0)
                {

                    String claveIngresada = DialogoCodefac.mensajeTextoIngreso(MensajeCodefacSistema.IngresoInformacion.INGRESO_CLAVE_CODEFAC);
                    if (!UtilidadesSistema.verificarClaveSoporte(claveIngresada)) 
                    {
                        DialogoCodefac.mensaje(MensajeCodefacSistema.IngresoInformacion.MENSAJE_CLAVE_INCORRECTA);
                        throw new ExcepcionCodefacLite(MensajeCodefacSistema.IngresoInformacion.MENSAJE_CLAVE_INCORRECTA.mensaje);
                    }
                }
            }

            
            ServiceFactory.getFactory().getCompraServiceIf().editarCompra(compra);
            DialogoCodefac.mensaje(MensajeCodefacSistema.AccionesFormulario.EDITADO);
            
        } catch (ServicioCodefacException ex) {
            DialogoCodefac.dialogoPregunta("Error al grabar",ex.getMessage(),DialogoCodefac.MENSAJE_INCORRECTO);
            throw new ExcepcionCodefacLite(ex.getMessage());
        } catch (RemoteException ex) {
            Logger.getLogger(CompraModel.class.getName()).log(Level.SEVERE, null, ex);
            throw new ExcepcionCodefacLite(ex.getMessage());
        }
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite, RemoteException {
        if(estadoFormulario==ESTADO_EDITAR)
        {
            try {
                Boolean confirmarEliminar=DialogoCodefac.dialogoPregunta(MensajeCodefacSistema.Preguntas.GUARDADO);
                if(confirmarEliminar)
                {
                    ServiceFactory.getFactory().getCompraServiceIf().eliminarCompra(compra);
                    DialogoCodefac.mensaje(MensajeCodefacSistema.AccionesFormulario.ELIMINADO_CORRECTAMENTE); 
                }
                else
                {
                    throw new ExcepcionCodefacLite("Cancelado eliminar");
                }
                
            } catch (ServicioCodefacException ex) {
                Logger.getLogger(CompraModel.class.getName()).log(Level.SEVERE, null, ex);
                DialogoCodefac.mensaje("Error",ex.getMessage(),DialogoCodefac.MENSAJE_INCORRECTO);
            }
        }
        else
        {
            DialogoCodefac.mensaje(MensajeCodefacSistema.AccionesFormulario.NO_PERMITE_ELIMINAR);
            throw new ExcepcionCodefacLite("No se puede eliminar");
        }
    }

    @Override
    public void imprimir() {
        try {
            
            byte[] byteReporte = ServiceFactory.getFactory().getComprobanteServiceIf().getReporteComprobante(compra, compra.getClaveAcceso(), session.getEmpresa());
            JasperPrint jasperPrint = (JasperPrint) UtilidadesRmi.deserializar(byteReporte);
            
            panelPadre.crearReportePantalla(jasperPrint, compra.getPreimpreso());
        } catch (IOException ex) {
            Logger.getLogger(CompraModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CompraModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void actualizar() throws ExcepcionCodefacLite {
        
    }

    @Override
    public void buscar() throws ExcepcionCodefacLite {
        CompraBusquedaDialogo compraBusqueda = new CompraBusquedaDialogo(session.getSucursal());
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(compraBusqueda);
        buscarDialogoModel.setVisible(true);
        Compra compra = (Compra)buscarDialogoModel.getResultado();
        if(compra != null)
        {            
            cargarDatosCompra(compra);
        }else{
            throw new ExcepcionCodefacLite("Excepcion lanzada desde buscar compra vacio");
        }
        
        
    }
    
    private void cargarDatosCompra(Compra compra)
    {
        this.compra=compra;
        //this.compra = compra;
        String identificacion = this.compra.getProveedor().getIdentificacion();
        String nombre = this.compra.getProveedor().getRazonSocial();
        getTxtProveedor().setText(identificacion + " - " + nombre);
        //Observacion
        this.getTxtObservacion().setText(this.compra.getObservacion());
        
        getTxtEstablecimientoCompra().setText(this.compra.getPuntoEstablecimientoFormat());
        getTxtPuntoEmisionCompra().setText(this.compra.getPuntoEmisionFormat());
        getTxtSecuencialCompra().setText(this.compra.getSecuencialFormat());
        
        //this.getTxtFPreimpreso().setText(preimpreso);
        //Autorizacion
        this.getTxtAutorizacion().setText(this.compra.getAutorizacion());
        //Fecha
        this.getCmbFechaCompra().setDate(this.compra.getFechaFactura());
        
        //Descuentos
        this.getTxtDescuentoImpuestos().setText(this.compra.getDescuentoImpuestos() + "");
        this.getTxtDescuentoSinImpuestos().setText(this.compra.getDescuentoSinImpuestos() + "");
        //Valores a mostrar del subtotal
        this.getLblSubtotalImpuestoSinDescuento().setText(compra.getSubtotalImpuestosSinDescuentos().setScale(3, RoundingMode.HALF_UP)+"");
        this.getLblSubtotalSinImpuestoSinDescuento().setText(compra.getSubtotalSinImpuestosSinDescuentos().toString());
        
        this.getCmbDocumento().setSelectedItem(DocumentoEnum.obtenerDocumentoPorCodigo(compra.getCodigoDocumento()));
        //this.getCmbSustentoComprobante().setSelectedItem(compra.getCodigoSustentoSriEnum());
        getCmbSustentoComprobante().setSelectedItem(0);
        this.getCmbTipoDocumento().setSelectedItem(compra.getCodigoTipoDocumentoEnum());
        
        
        
        
        //Cargar la orden de compra si existe referencia
        if(this.compra.getOrdenCompra()!=null)
        {
            this.getTxtOrdenCompra().setText(this.compra.getOrdenCompra().getId()+"-"+this.compra.getOrdenCompra().getObservacion());
        }
        
        //actualizarDatosMostrarVentana();
        //if (session.getEmpresa().getObligadoLlevarContabilidad().equals(Empresa.SI_LLEVA_CONTABILIDAD)) {
            mostrarDatosTabla();
        //} else {
        //    mostrarDatosTablaSinRetencion();
        //}
        
        //Cargar el tipo de emision de las compras
        if(compra.getTipoFacturacionEnum()!=null)
        {
            if(compra.getTipoFacturacionEnum().equals(ComprobanteEntity.TipoEmisionEnum.ELECTRONICA))
            {
                getRdbEmisionElectronica().setSelected(true);
            }
            else if(compra.getTipoFacturacionEnum().equals(ComprobanteEntity.TipoEmisionEnum.NORMAL))
            { 
                getRdbEmisionFisica().setSelected(true);
            }
        }
        else
        {
            getRdbEmisionFisica().setSelected(true); //Cuando no tiene seleccionado ningun dato asumo que es fisica
        }
        
        if(compra.getEstadoRetencionEnum().equals(estadoRetencion.SIN_CONTABILIDAD))
        {
            getCmbEmitirRetencion().setSelectedItem(EnumSiNo.NO);
        }
        else
        {
            getCmbEmitirRetencion().setSelectedItem(EnumSiNo.SI);
        }
        
        mostrarDatosTotales();
        desbloquearIngresoDetalleProducto();
        mostrarDatosFacturasReembolso();
        
        //getCmbRetencionIva().setSelectedItem(compra.get);
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
        //getTxtFPreimpreso().setText("001001000000000");
        getTxtEstablecimientoCompra().setText("");
        getTxtPuntoEmisionCompra().setText("");
        getTxtSecuencialCompra().setText("");
        
        getTxtAutorizacion().setText("");
        //Limpiar Detalles de Producto
        getTxtProductoItem().setText("");
        getTxtDescripcionItem().setText("");
        getTxtPrecionUnitarioItem().setText("");
        getTxtCostoItem().setText("");
        getTxtDescuentoItem().setText("0");
        getTxtCostoItem().setText("");
        getTxtCantidadItem().setText("");
        getTxtLoteNombre().setText("");
        getCmbPresentacionProducto().removeAllItems();
        //Limpiar Tabla de Detalles Producto 
        if(session.getEmpresa().getObligadoLlevarContabilidad().equalsIgnoreCase(Empresa.SI_LLEVA_CONTABILIDAD))
        {
            initModelTablaDetalleCompra();
        }else{
            initModelTablaDetalleCompraSinRetencion();
        }
        //Limpiar totales
        getLblSubtotalImpuestoSinDescuento().setText("0.00");
        getLblSubtotalSinImpuestoSinDescuento().setText("0.00");
        getTxtDescuentoImpuestos().setText("0.00");
        getTxtDescuentoSinImpuestos().setText("0.00");
        getLblSubtotalImpuestos().setText("0.00");
        getLblIva().setText("0.00");
        getLblTotal().setText("0.00");
        getCmbFechaCompra().setDate(new java.util.Date());
        this.compra = new Compra();
        this.compra.setDetalles(new ArrayList<CompraDetalle>());
        bloquearIngresoDetalleProducto();   
        
       
        //getCmbSustentoComprobante().setSelectedIndex(0);
        cargarCatalogoRetencionesDefecto();
        
        getRdbEmisionFisica().setSelected(true);
        
        EnumSiNo habilitarRetensiones=null;
        habilitarRetensiones = ParametroUtilidades.obtenerValorParametroEnum(session.getEmpresa(),ParametroCodefac.HABILITAR_RETENCION_COMPRAS,EnumSiNo.SI);
        
        if(habilitarRetensiones!=null)
        {
            this.getPanelRetencion().setVisible(habilitarRetensiones.getBool());
            this.getCmbEmitirRetencion().setSelectedItem(habilitarRetensiones);
        }
        else
        {
            //Seleccionar la opcion de enviar o no retenciones
            if(session.getEmpresa().getObligadoLlevarContabilidad().equals(Empresa.SI_LLEVA_CONTABILIDAD))
            {
                getCmbEmitirRetencion().setSelectedItem(EnumSiNo.SI);
            }
            else
            {
                getCmbEmitirRetencion().setSelectedItem(EnumSiNo.NO);
            }
        }
        
        getTxtDiasCredito().setValue(0);
        getCmbSustentoComprobante().setSelectedIndex(0); //Selecionar el primer sustento despues de agregar
        mostrarDatosFacturasReembolso();
    }
    
    private void cargarCatalogoRetencionesDefecto()
    {
        try {
            ParametroCodefac parametroIva= ServiceFactory.getFactory().getParametroCodefacServiceIf().getParametroByNombre(ParametroCodefac.VALOR_DEFECTO_RETENCION_IVA,session.getEmpresa());
            if(parametroIva!=null && parametroIva.getValor()!=null && !parametroIva.getValor().isEmpty())
            {
                SriRetencionIva sriRetencionIva= ServiceFactory.getFactory().getSriRetencionIvaServiceIf().buscarPorId(Long.parseLong(parametroIva.getValor()));
                getCmbRetencionIva().setSelectedItem(sriRetencionIva);
            }
            
            ParametroCodefac parametroRenta= ServiceFactory.getFactory().getParametroCodefacServiceIf().getParametroByNombre(ParametroCodefac.VALOR_DEFECTO_RETENCION_RENTA,session.getEmpresa());
            if(parametroRenta!=null && parametroRenta.getValor()!=null && !parametroRenta.getValor().isEmpty())
            {
                SriRetencionRenta sriRetencionRenta= ServiceFactory.getFactory().getSriRetencionRentaServiceIf().buscarPorId(Long.parseLong(parametroRenta.getValor()));
                getCmbRetencionRenta().setSelectedItem(sriRetencionRenta);
            }
            

        } catch (RemoteException ex) {
            Logger.getLogger(CompraModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    @Override
    public String getNombre() {
        return "Compra";
    }

    @Override
    public String getURLAyuda() {
        return "https://docs.google.com/document/d/e/2PACX-1vRxHiHd5vpEu1In25BKtCXigpl4m1phGAZwNR7Rh2Jm-Xqe7ffQpivlYJsMAWHFBS0BOnYxj4dpUi7H/pub?embedded=true#h.f6pwytgicjgq";
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

    private void iniciarCombos()  {
        
        try{
            
            getCmbSustentoComprobante().removeAllItems();
            SriSustentoComprobanteEnum[] sustentosComprobanteList=SriSustentoComprobanteEnum.values();
            for (SriSustentoComprobanteEnum sriSustentoComprobanteEnum : sustentosComprobanteList) {
                getCmbSustentoComprobante().addItem(sriSustentoComprobanteEnum);
            }
            
            //Agregar los documentos del sri
            getCmbDocumento().removeAllItems();
            List<DocumentoEnum> documentos= DocumentoEnum.obtenerDocumentoPorModulo(ModuloCodefacEnum.COMPRA);
            for (DocumentoEnum documento : documentos) {
                getCmbDocumento().addItem(documento);
            }
            //Quitar documento de compras que no deben estar como la liquidacion de compras
            //getCmbDocumento().removeItem(DocumentoEnum.LIQUIDACION_COMPRA);
            
            //Agregar los tipos de documentos disponibles
            getCmbTipoDocumento().removeAllItems();
            getCmbTipoDocumento().addItem(TipoDocumentoEnum.COMPRA);
            getCmbTipoDocumento().addItem(TipoDocumentoEnum.COMPRA_INVENTARIO);
            getCmbTipoDocumento().addItem(TipoDocumentoEnum.COMPRA_SERVICIOS);
            
            //Iniciar componentes de generar o no retencion
            getCmbEmitirRetencion().removeAllItems();
            getCmbEmitirRetencion().addItem(EnumSiNo.SI);
            getCmbEmitirRetencion().addItem(EnumSiNo.NO);
            
            getCmbIvaDescuento().removeAllItems();
            getCmbIvaDescuento().addItem(EnumSiNo.SI);
            getCmbIvaDescuento().addItem(EnumSiNo.NO);       
            
            List<ImpuestoDetalle> impuestoDetalleList = ServiceFactory.getFactory().getImpuestoDetalleServiceIf().obtenerIvaVigente();
            getCmbIvaDetalle().removeAllItems();
            for (ImpuestoDetalle impuestoDetalle : impuestoDetalleList)
            {
                getCmbIvaDetalle().addItem(impuestoDetalle.getTarifa());
                
            }
            
            
            //Agregar los tipos de retencion Iva
            getCmbRetencionIva().removeAllItems();
            SriRetencionIvaServiceIf sriRetencionIvaService = ServiceFactory.getFactory().getSriRetencionIvaServiceIf();
            try{
                List<SriRetencionIva> tipoRetencionesIva = sriRetencionIvaService.obtenerTodosOrdenadoPorCodigo();
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
                List<SriRetencionRenta> tipoRetencionesRenta = sriRetencionRentaService.obtenerTodosOrdenadoPorCodigo();
                for (SriRetencionRenta sriRetencionRenta : tipoRetencionesRenta) {
                    getCmbRetencionRenta().addItem(sriRetencionRenta);
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        catch(RemoteException ex)
        {
            Logger.getLogger(CompraModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void cargarCompraDesdeOrdenCompra()
    {
        OrdenCompra ordenCompra=compra.getOrdenCompra();
        
        compra=new Compra();
        compra.setFechaFactura(UtilidadesFecha.getFechaHoy());
        compra.setObservacion(ordenCompra.getObservacion());
        compra.setProveedor(ordenCompra.getProveedor());
        compra.setCodigoTipoDocumento(ordenCompra.getCodigoTipoDocumento());
        
        compra.setDescuentoImpuestos(ordenCompra.getDescuentoImpuestos());
        compra.setDescuentoSinImpuestos(ordenCompra.getDescuentoSinImpuestos());
        compra.setEmpresa(ordenCompra.getEmpresa());
        compra.setIva(ordenCompra.getIva());
        compra.setIvaSriId(ordenCompra.getIvaSriId());
        compra.setSubtotalImpuestos(ordenCompra.getSubtotalImpuestos());
        compra.setSubtotalSinImpuestos(ordenCompra.getSubtotalSinImpuestos());
        compra.setTotal(ordenCompra.getTotal());
        compra.setUsuario(null);//Terminar de setar el usuario
        
        //Cargar los detalles
        for (OrdenCompraDetalle detalleOrden : ordenCompra.getDetalles()) {
            CompraDetalle compraDetalle=new CompraDetalle();
            compraDetalle.setCantidad(new BigDecimal(detalleOrden.getCantidad())); //TODO: Verificar que este campo 
            compraDetalle.setDescripcion(detalleOrden.getDescripcion());
            compraDetalle.setDescuento(detalleOrden.getDescuento());
            compraDetalle.setIva(detalleOrden.getIva());
            compraDetalle.setPrecioUnitario(detalleOrden.getPrecioUnitario()); //Todo: Segun el proceso normal se deben crear otros datos para relacionar el producto con el precio y sacar una lista de precios
            compraDetalle.setProductoProveedor(detalleOrden.getProductoProveedor());
            compraDetalle.setTotal(detalleOrden.getTotal());
            compraDetalle.setValorIce(detalleOrden.getValorIce());       
            compra.addDetalle(compraDetalle);
        }
        
        //Cargar los datos en la vista
        cargarDatosCompra(this.compra);
        
    }
    
    private ActionListener listenerRetencionAplicarTodo=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            //aplicarRetencionDetalle
            
            for (CompraDetalle detalle : compra.getDetalles()) 
            {
                aplicarRetencionDetalle(detalle);
            }
            actualizarDatosMostrarVentana();
        }
    };
    
    private void abrirDialogoCompraXml(Compra compra)
    {
        Object[] parametros = {compra};
        panelPadre.crearDialogoCodefac(observerCompraXml, VentanaEnum.COMPRA_XML, true, parametros, formularioActual);
    }
    
    private ActionListener listenerDescargarXmlInternet=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                System.out.println("descargando factura");
                compra=ServiceFactory.getFactory().getCompraServiceIf().obtenerCompraDesdeClaveDeAcceso(getTxtAutorizacion().getText(), session.getEmpresa());               
                abrirDialogoCompraXml(compra);
            } catch (RemoteException ex) {
                Logger.getLogger(CompraModel.class.getName()).log(Level.SEVERE, null, ex);
                 DialogoCodefac.mensaje(new CodefacMsj(ex.getMessage(), CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
            } catch (ServicioCodefacException ex) {
                Logger.getLogger(CompraModel.class.getName()).log(Level.SEVERE, null, ex);
                DialogoCodefac.mensaje(new CodefacMsj(ex.getMessage(), CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
            }
            
        }
    };
    
    private ActionListener listenerDescargarPdfInternet=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                System.out.println("descargando factura");
                ComprobanteElectronico comprobanteElectronico=ServiceFactory.getFactory().getCompraServiceIf().obtenerComprobanteElectronicoConClaveAcceso(getTxtAutorizacion().getText(), session.getEmpresa());
                if(comprobanteElectronico==null)
                {
                    DialogoCodefac.mensaje(new CodefacMsj("No se puede encontrar el comprobante", CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
                    return ;
                }
                
                byte[] byteReporte=ServiceFactory.getFactory().getComprobanteServiceIf().getReporteComprobante(comprobanteElectronico, null, comprobanteElectronico.getInformacionTributaria().getClaveAcceso(),session.getEmpresa());
                JasperPrint jasperPrint = (JasperPrint) UtilidadesRmi.deserializar(byteReporte);

                panelPadre.crearReportePantalla(jasperPrint, comprobanteElectronico.getInformacionTributaria().getPreimpreso());
                
            } catch (RemoteException ex) {
                Logger.getLogger(CompraModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ServicioCodefacException ex) {
                Logger.getLogger(CompraModel.class.getName()).log(Level.SEVERE, null, ex);
                DialogoCodefac.mensaje(new CodefacMsj(ex.getMessage(), CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
            } catch (IOException ex) {
                Logger.getLogger(CompraModel.class.getName()).log(Level.SEVERE, null, ex);
                DialogoCodefac.mensaje(new CodefacMsj(ex.getMessage(), CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(CompraModel.class.getName()).log(Level.SEVERE, null, ex);
                DialogoCodefac.mensaje(new CodefacMsj(ex.getMessage(), CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
            }
            
        }
    };
    
    private ActionListener listenerDescuentoGlobal=new ActionListener() 
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            String descuentoStr=getTxtDescuentoGlobal().getText();            
            BigDecimal descuentoLeido=new BigDecimal(descuentoStr);            
            compra.aplicarDescuento(descuentoLeido, getChkPorcentajeDescuentoGlobal().isSelected(),(EnumSiNo) getCmbIvaDescuento().getSelectedItem());
            actualizarDatosMostrarVentana();
            
        }
    };

    private void agregarListenerBotones() {
        
        getBtnAplicarDescuentoGlobal().addActionListener(listenerDescuentoGlobal);
        
        getBtnDescargarPdfInternet().addActionListener(listenerDescargarPdfInternet);
        
        getBtnDescargarXmlInternet().addActionListener(listenerDescargarXmlInternet);
        
        getBtnRetencionAplicarTodo().addActionListener(listenerRetencionAplicarTodo);
        
        getBtnAgregarReembolso().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] parametros={};
                
                panelPadre.crearDialogoCodefac(new ObserverUpdateInterface<CompraFacturaReembolso>() {
                    @Override
                    public void updateInterface(CompraFacturaReembolso entity) {
                        CompraFacturaReembolso reembolso = entity;
                        if (reembolso != null) {
                            compra.addFacturaReembolso(reembolso);
                            mostrarDatosFacturasReembolso();
                        }
                    }
                }, VentanaEnum.FACTURA_REEMBOLSO, false, parametros, formularioActual);
                
            }
        });
        
        getBtnBuscarFacturaReembolso().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                //buscar();
                FacturaBusqueda facturaBusqueda = null;
                //TODO: Unificar este metood con el de consultar la factura para tener en un solo lugar
                ParametroCodefac siNofiltrarFacturaPorUsuario = session.getParametrosCodefac().get(ParametroCodefac.FILTRAR_FACTURAS_POR_USUARIO);
                EnumSiNo enumSiNo = EnumSiNo.getEnumByLetra((siNofiltrarFacturaPorUsuario != null) ? siNofiltrarFacturaPorUsuario.getValor() : null);
                if (enumSiNo != null && enumSiNo.equals(EnumSiNo.SI)) {
                    facturaBusqueda = new FacturaBusqueda(session.getSucursal(), session.getUsuario());
                } else {
                    facturaBusqueda = new FacturaBusqueda(session.getSucursal());
                }

                BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(facturaBusqueda);
                buscarDialogoModel.setVisible(true);

                Factura facturaTmp = (Factura) buscarDialogoModel.getResultado();

                if (facturaTmp != null) {
                    //compra.add
                    compra.addFacturaReembolso(facturaTmp);
                    mostrarDatosFacturasReembolso();
                } else {
                    //throw new ExcepcionCodefacLite("cancelar ejecucion");
                }
                
                
            }
        }
        );
        
        getBtnOrdenCompraBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OrdenCompraBusqueda ordenCompraBusqueda = new OrdenCompraBusqueda();
                BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(ordenCompraBusqueda);
                buscarDialogoModel.setVisible(true);
                OrdenCompra ordenCompra = (OrdenCompra) buscarDialogoModel.getResultado();
                if (ordenCompra != null) {
                    compra.setOrdenCompra(ordenCompra);
                    getTxtOrdenCompra().setText(ordenCompra.getId()+"-"+ordenCompra.getObservacion());
                    cargarCompraDesdeOrdenCompra();
                }
            }
        });
        
        getBtnCrearProducto().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ObserverUpdateInterface observer = new ObserverUpdateInterface<Producto>() {
                    @Override
                    public void updateInterface(Producto entity) {
                        if (entity != null) {
                            /*String identificacion = compra.getProveedor().getIdentificacion();
                            String nombre = compra.getProveedor().getRazonSocial();
                            desbloquearIngresoDetalleProducto();*/
                            cargarProductoVistaAgregar(entity,CrudEnum.CREAR);
                        }
                    }
                };

                panelPadre.crearDialogoCodefac(observer, VentanaEnum.PRODUCTO, false,formularioActual);
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
                        Persona proveedor=entity;
                        compra.setProveedor(entity);
                        if (compra.getProveedor() != null) {
                            String identificacion = proveedor.getIdentificacion();
                            String nombre = proveedor.getRazonSocial();
                            getTxtProveedor().setText(identificacion + " - " + nombre);
                            desbloquearIngresoDetalleProducto();
                        }
                    }
                }, VentanaEnum.CLIENTE, false,paramPostConstruct,formularioActual);
         
                //panelPadre.crearVentanaCodefac(VentanaEnum.CLIENTE,true,paramPostConstruct);
                
            }
        });
        
        getBtnProveedorBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProveedorBusquedaDialogo buscarBusquedaDialogo = new ProveedorBusquedaDialogo(session.getEmpresa());
                BuscarDialogoModel buscarDialogo = new BuscarDialogoModel(buscarBusquedaDialogo);
                buscarDialogo.setVisible(true);
                Persona proveedor = ((PersonaEstablecimiento) buscarDialogo.getResultado()).getPersona();
                compra.setProveedor(proveedor);
                if (proveedor != null) {
                    String identificacion=proveedor.getIdentificacion();
                    String nombre =proveedor.getRazonSocial();
                    getTxtProveedor().setText(identificacion+" - "+nombre);
                    desbloquearIngresoDetalleProducto();
                    getTxtProductoItem().requestFocus();
                    
                    //Cargar los días de crédito en el caso que tenga creado
                    Integer diasCredito=0;
                    if(proveedor.getDiasCreditoProveedor()!=null)
                    {
                        diasCredito=proveedor.getDiasCreditoProveedor();
                    }
                    getTxtDiasCredito().setValue(diasCredito);
                    
                }
            }
        });
        
        getBtnBuscarProductoProveedor().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // ProductoBusquedaDialogo buscarBusquedaDialogo = new ProductoBusquedaDialogo(session.getEmpresa(),false,true);
                    //BuscarDialogoModel buscarDialogo = new BuscarDialogoModel(buscarBusquedaDialogo);
                    //buscarDialogo.setVisible(true);
                    //Producto productoTmp = (Producto) buscarDialogo.getResultado();
                    ProductoBusquedaDialogoFactory busquedaFactory=new ProductoBusquedaDialogoFactory(session.getSucursal(), ProductoBusquedaDialogoFactory.ResultadoEnum.PRODUCTO);
                    Producto productoTmp = (Producto) busquedaFactory.ejecutarDialogo();
                    productoTmp= ServiceFactory.getFactory().getProductoServiceIf().buscarProductoDefectoCompras(productoTmp);
                    cargarProductoVistaAgregar(productoTmp,CrudEnum.CREAR);
                } catch (ServicioCodefacException ex) {
                    Logger.getLogger(CompraModel.class.getName()).log(Level.SEVERE, null, ex);
                    new CodefacMsj(ex.getMessage(), CodefacMsj.TipoMensajeEnum.CORRECTO);
                } catch (RemoteException ex) {
                    Logger.getLogger(CompraModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        getBtnAgregarItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    /**
                     * Seteo el valor que se ingresa en el costo para actualizar el valor del producto para ese proveedor
                     */
                    agregarDetalleCompraConDatosVista(null);
                } catch (ExcepcionCodefacLite ex) {
                    Logger.getLogger(CompraModel.class.getName()).log(Level.SEVERE, null, ex);
                    DialogoCodefac.mensaje(new CodefacMsj(ex.getMessage(), CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
                }

            }
        });
        
        getTxtDescuentoImpuestos().addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                calcularDescuentoConImpuestosVista();
            }

            @Override
            public void focusGained(FocusEvent e) {
                
            }
        });
        
        getTxtDescuentoSinImpuestos().addFocusListener(new FocusAdapter() {
            
            @Override
            public void focusLost(FocusEvent e)
            {
                calcularDescuentoSinImpuestosVista();
            }
            
            @Override
            public void focusGained(FocusEvent e)
            {
                
            }
            
        });
        
        getTblDetalleProductos().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int filaDP = getTblDetalleProductos().getSelectedRow();
                banderaEdicion = true;
                if(filaDP>=0)
                {
                    
                    CompraDetalle compraDetalle = (CompraDetalle) compra.getDetalles().get(filaDP); //TODO: Revisar si esta forma es la mas optima
                    System.out.println("Seleccionado producto original vinculado: "+compraDetalle.getProductoProveedor().getProducto().getNombre() );
                    getTxtProductoItem().setText(compraDetalle.getProductoProveedor().getProducto().getCodigoPersonalizado());
                    verificarExistenciadeProductoProveedor();
                    getTxtDescripcionItem().setText(compraDetalle.getDescripcion());
                    seleccionarComboTipoIva(compraDetalle.getIvaPorcentaje());
                    getTxtCostoItem().setText((compraDetalle.getCostoUnitario()!=null)?compraDetalle.getCostoUnitario()+"":"");
                    getTxtCantidadItem().setText(compraDetalle.getCantidad()+"");
                    getTxtPrecionUnitarioItem().setText(compraDetalle.getPrecioUnitario()+"");
                    getTxtDescuentoItem().setText(compraDetalle.getDescuento()+"");
                    //getTxtCostoItem().setText(compraDetalle.getCo);
                    getCmbRetencionIva().setSelectedItem(compraDetalle.getSriRetencionIva());
                    getCmbRetencionRenta().setSelectedItem(compraDetalle.getSriRetencionRenta());
                    getCmbIvaDetalle().setSelectedItem(compraDetalle.getIvaPorcentaje());
                    
                    cargarPresentaciones(compraDetalle.getProductoProveedor().getProducto());
                    //getCmbPresentacionProducto().setSelectedItem(compraDetalle.getProductoProveedor().getProducto().buscarPresentacionOriginal());
                    getCmbPresentacionProducto().setSelectedItem(compraDetalle.getProductoProveedor().getProducto().buscarPresentacionProducto());
                    
                    String loteCodigo="";
                    if(compraDetalle.getLote()!=null)
                    {
                        loteCodigo=compraDetalle.getLote().getCodigo();
                    }
                    getTxtLoteNombre().setText(loteCodigo);
                    //compraDetalle.setPrecioUnitario
                    compraDetalle.getPrecioUnitario();
                    bloquearDesbloquearBotones(false);
                    //----------------------------------------------------------------------
                    productoSeleccionado = compraDetalle.getProductoProveedor().getProducto();
                    loteSeleccionado=compraDetalle.getLote();
                    
                    
                    getCmbSustentoComprobante().setSelectedItem(compraDetalle.getCodigoSustentoSriEnum());
                }
            }
        });
        
        
        getBtnEditarItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(banderaEdicion)
                {
                    try {
                        banderaEdicion = false;
                        int filaDP = getTblDetalleProductos().getSelectedRow();
                        filaDP = getTblDetalleProductos().getSelectedRow();
                        CompraDetalle compraDetalle = compra.getDetalles().get(filaDP);
                        agregarDetalleCompraConDatosVista(compraDetalle);
                        //calcularDescuento(1, new BigDecimal(getTxtDescuentoImpuestos().getText()));
                        //calcularDescuento(2, new BigDecimal(getTxtDescuentoSinImpuestos().getText()));
                        bloquearDesbloquearBotones(true);
                    } catch (ExcepcionCodefacLite ex) {
                        Logger.getLogger(CompraModel.class.getName()).log(Level.SEVERE, null, ex);
                        DialogoCodefac.mensaje(new CodefacMsj(ex.getMessage(), CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
                    }
                }
            }
        });
        
        getBtnEliminarItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(banderaEdicion)
                {
                    int filaDP = getTblDetalleProductos().getSelectedRow();
                    if(filaDP>=0)
                    {
                        banderaEdicion = false;
                        modeloTablaDetallesCompra.removeRow(filaDP);
                        compra.getDetalles().remove(filaDP);                   
                        actualizarDatosMostrarVentana();
                    }
                }
            }
        });
        
        getBtnCargarXml().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                listenerBtnCargarCompraXml();
            }
        });
        
        getCmbPresentacionProducto().addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                PresentacionProducto presentacion=(PresentacionProducto) getCmbPresentacionProducto().getSelectedItem();
                
                if(presentacion!=null && productoSeleccionado!=null)
                {
                    //Obtengo la nueva presentacion para trabajar con los nuevos datos seleccionados
                    Producto productoTmp= productoSeleccionado.buscarProductoPorPresentacion(presentacion);
                    //Si la presentacion es igual al mismo producto entonces no hago nada mas
                    if(productoTmp==null || productoTmp.equals(productoSeleccionado))
                    {
                        if(productoTmp==null)
                        {
                            Logger.getLogger(CompraModel.class.getName()).log(Level.SEVERE,"No existe la presentacion :"+presentacion.getNombre()+" para el producto: "+productoSeleccionado.getNombre());
                        }
                        return ;
                    }
                    
                    productoSeleccionado=productoTmp;
                    
                    //TODO: Arreglo temporal pero ver como mejorar esta parte porque da problemas al momento de cambiar de presentacion al momento de editar                    
                    CrudEnum crudEnum=CrudEnum.EDITAR;
                    if(getBtnAgregarItem().isEnabled())
                    {
                        crudEnum=CrudEnum.CREAR;
                    }
                    
                    cargarProductoVistaAgregar(productoTmp,crudEnum);
                    
                }
            }
        });
        
        getBtnBuscarLote().addActionListener(listenerBuscarLote);
        getBtnCrearLote().addActionListener(listenerCrearLote);
        
        getBtnAuditoria().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                if(estadoFormulario.equals(ESTADO_EDITAR))
                {
                    AuditoriaInformacionModel auditoriaModel=new AuditoriaInformacionModel(compra.getFechaCreacion()+"",compra.getFechaUltimaEdicion()+"",compra.getUsuario()+"",compra.getUsuarioUltimaEdicion()+"");
                    auditoriaModel.setVisible(true);
                    
                }
            }
        });
                
    }
    
    private void cargarProductoVistaAgregar(Producto productoTmp,CrudEnum crudEnum)
    {
        agregarProductoVista(productoTmp,crudEnum);

        //Cuando seleccione otro producto por seguridad debo limpiar el lote para que seleccione de nuevo
        //TODO:Mejorar esta parte para tener desde un mismo lugar donde limpiar los datos
        if(crudEnum.equals(CrudEnum.CREAR))
        {
            loteSeleccionado = null;
            getTxtLoteNombre().setText("");
        }
        cargarPresentaciones(productoTmp);
    }
    
    public void cargarPresentaciones(Producto producto)
    {
        UtilidadesComboBox.ejecutarProcesoSinListener(getCmbPresentacionProducto(),new UtilidadesComboBox.ProcesoComboBoxIf() {
            @Override
            public void proceso() {
                getCmbPresentacionProducto().removeAllItems();
                if(producto!=null && producto.getPresentacionList()!=null)
                {
                    //Por defecto agrego la presentacion del mismo producto                                
                    List<PresentacionProducto> presentacionList=producto.obtenerPresentacionesList();

                    for (PresentacionProducto detallePresentacion : presentacionList) 
                    {
                        getCmbPresentacionProducto().addItem(detallePresentacion);
                    }

                    //Volver a seleccionar la presentacion correcta en el caso que existe el producto
                    if(productoSeleccionado!=null)
                    {
                        //getCmbPresentacionProducto().setSelectedItem(productoSeleccionado.buscarPresentacionOriginal());
                        getCmbPresentacionProducto().setSelectedItem(productoSeleccionado.buscarPresentacionProducto());
                    }
                }
            }
        });        
    }
    
    private ActionListener listenerCrearLote=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            
            Object[] paramPostConstruct = new Object[1];
            Producto productoCrearLote= productoSeleccionado.buscarProductoEmpaquePrincipal();
            paramPostConstruct[0] = productoCrearLote;
            
            ObserverUpdateInterface observerCreate=new ObserverUpdateInterface<Lote>() 
            {
                @Override
                public void updateInterface(Lote entity) {
                    if(entity!=null)
                    {
                        loteSeleccionado= (Lote) entity;
                        cargarDatosPantallaLote();
                    }

                }
            };
            panelPadre.crearDialogoCodefac(observerCreate, VentanaEnum.LOTE, false,paramPostConstruct,formularioActual);        
        }
    };
    
    private ActionListener listenerBuscarLote=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            LoteBusqueda busqueda=new LoteBusqueda(session.getEmpresa(),productoSeleccionado.buscarProductoEmpaquePrincipal());
            BuscarDialogoModel buscarDialogo = new BuscarDialogoModel(busqueda);            
            buscarDialogo.setVisible(true);

            if (buscarDialogo.getResultado() != null) 
            {
                loteSeleccionado= (Lote) buscarDialogo.getResultado();
            }
            
            cargarDatosPantallaLote();
        }
    };
    
    private void cargarDatosPantallaLote()
    {
        if(loteSeleccionado!=null)
        {
            getTxtLoteNombre().setText(loteSeleccionado.getCodigo());
        }
    }
    
    private void agregarDetalleCompraConDatosVista(CompraDetalle compraDetalle) throws ExcepcionCodefacLite
    {

        BigDecimal costo = null;
        String costoStr=getTxtCostoItem().getText();
        
        if(!UtilidadesTextos.verificarNullOVacio(costoStr))
        {
            costo=new BigDecimal(costoStr);
        }
        
        BigDecimal cantidad =  null;        
        try
        {
            cantidad =  new BigDecimal(getTxtCantidadItem().getText());
        }catch(java.lang.NumberFormatException nfe)
        {
            throw new ExcepcionCodefacLite("Error al ingresar un formato de número invalido para la CANTIDAD");
        }
                
        BigDecimal precioUnitario = null;
        try
        {
            precioUnitario = new BigDecimal(getTxtPrecionUnitarioItem().getText());
        }catch(java.lang.NumberFormatException nfe)
        {
            throw new ExcepcionCodefacLite("Error al ingresar un formato de número invalido para el PRECIO UNITARIO");
        }
        
        
        BigDecimal descuento=null;
        try
        {
            descuento = new BigDecimal(getTxtDescuentoItem().getText());
        }catch(java.lang.NumberFormatException nfe)
        {
            throw new ExcepcionCodefacLite("Error al ingresar un formato de número invalido para el DESCUENTO");
        }
        
        //compraDetalle.setDescripcion(getTxtDescripcionItem().getText());
        Integer porcentajeIva=(Integer) getCmbIvaDetalle().getSelectedItem();
        
        //validar el ingreso en la vista
        if (!panelPadre.validarPorGrupo("detalles")) {
            return;
        }
        
        //TODO:Verificar por que existen 2 validaciones para la vista
        if(verificarCamposValidados())
        {
            agregarDetallesCompra(compraDetalle,loteSeleccionado,productoProveedor ,costo, cantidad, precioUnitario,descuento,getTxtDescripcionItem().getText(),porcentajeIva);
        }
        
    }
    
    private void listenerBtnCargarCompraXml()
    {
        String[] filtros={"xml", "XML"};
        JFileChooser jFileChooser=UtilidadesSwing.getJFileChooserPreBuild("Elegir Compra Xml", "Compra Xml", filtros);
        int seleccion=jFileChooser.showDialog(null,"Abrir");
        //Si devuelve una respuesta ejecuto el metodo para grabar
        if(seleccion==JFileChooser.APPROVE_OPTION)
        {
            try {
                File archivoSeleccionado=jFileChooser.getSelectedFile();
                //SimpleRemoteInputStream istream = new SimpleRemoteInputStream(
                //        new FileInputStream(archivoSeleccionado));
                
                ComprobanteElectronico comprobanteElectronico=ComprobanteElectronicoService.obtenerComprobanteDataDesdeXml(archivoSeleccionado);
                
                Compra compra=ServiceFactory.getFactory().getCompraServiceIf().obtenerCompraDesdeXml(comprobanteElectronico,session.getEmpresa());
                
                //Object[] parametros={compra};
                //panelPadre.crearDialogoCodefac(observerCompraXml, VentanaEnum.COMPRA_XML, true, parametros, formularioActual);
                abrirDialogoCompraXml(compra);
                        
                //cargarDatosCompra(compra);
                
            } catch (RemoteException ex) {
                Logger.getLogger(CompraModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ServicioCodefacException ex) {
                DialogoCodefac.mensaje(new CodefacMsj(ex.getMessage(), CodefacMsj.TipoMensajeEnum.ERROR));
                Logger.getLogger(CompraModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private ObserverUpdateInterface observerCompraXml=new ObserverUpdateInterface() {
        @Override
        public void updateInterface(Object entity) {
            if(entity!=null)
            {
                compra=(Compra) entity;                
                //Agregar todos los detalles de la forma estandar en la compra para ir haciendo los calculos necesarios de la pantalla de COMPRA
                List<CompraDetalle> detallesTemporal=compra.getDetalles();
                //compra.setDetalles(null);
                //compra.setDetalles(null);
                for (CompraDetalle compraDetalle : detallesTemporal) 
                {
                    //TODO:Mejorar esta parte para no pasar los mismos datos                    
                    agregarDetallesCompra(compraDetalle,loteSeleccionado,compraDetalle.getProductoProveedor() ,compraDetalle.getPrecioUnitario(), compraDetalle.getCantidad(), compraDetalle.getPrecioUnitario(),compraDetalle.getDescuento() ,compraDetalle.getDescripcion(),compraDetalle.getIvaPorcentaje());
                    
                }
                
                compra.calcularTotales(session.obtenerIvaActualDecimal());
                cargarDatosCompra(compra);
            }
        }
    };
    
    private void agregarProductoVista(Producto producto,CrudEnum crudEnum)
    {
        productoSeleccionado = producto;
        verificarExistenciadeProductoProveedor();
        if(crudEnum.equals(CrudEnum.CREAR))
        {
            bloquearDesbloquearBotones(true);
        }
        else
        {
            bloquearDesbloquearBotones(false);
        }
        
        getTxtCantidadItem().requestFocus();
    }
    
    private void calcularDescuentoConImpuestosVista()
    {
        BigDecimal descuento = new BigDecimal(getTxtDescuentoImpuestos().getText());
        if (descuento.compareTo(compra.getSubtotalImpuestosSinDescuentos()) == 1) {
            DialogoCodefac.mensaje("Descuento", "El descuento no puede ser mayor que el subtotal con impuesto", DialogoCodefac.MENSAJE_ADVERTENCIA);
            getTxtDescuentoImpuestos().setText(compra.getDescuentoImpuestos() + "");
        } else {
            calcularDescuento(1, descuento);
        }
    }
    
    private void calcularDescuentoSinImpuestosVista()
    {
        BigDecimal descuento = new BigDecimal(getTxtDescuentoSinImpuestos().getText());
        if (descuento.compareTo(compra.getSubtotalSinImpuestosSinDescuentos()) == 1) {
            DialogoCodefac.mensaje("Descuento", "El descuento no puede ser mayor que el subtotal con impuesto", DialogoCodefac.MENSAJE_ADVERTENCIA);
            getTxtDescuentoSinImpuestos().setText(compra.getDescuentoSinImpuestos()+ "");
        } else {
            calcularDescuento(2, descuento);
        }
    }
    
    private void calcularIceImpuesto()
    {
        BigDecimal ice = new BigDecimal(getTxtIce().getText());
        compra.setIce(ice.setScale(2, RoundingMode.HALF_UP));
        actualizarTotales();
        mostrarDatosTotales();
    }
    
    private void verificarExistenciadeProductoProveedor()
    {
        if (productoSeleccionado != null) 
        {

            
            try 
            {
                //Buscar si existe el producto vinculado con un proveedor
                ProductoProveedorServiceIf serviceProductoProveedor = ServiceFactory.getFactory().getProductoProveedorServiceIf();
                //Map<String, Object> mapParametros = new HashMap<String, Object>();
                //mapParametros.put("producto", productoSeleccionado);
                //mapParametros.put("proveedor", compra.getProveedor());
                List<ProductoProveedor> resultados = serviceProductoProveedor.buscarProductoCompraActivo(productoSeleccionado, compra);

                if (resultados != null && resultados.size() > 0) {
                    productoProveedor = resultados.get(0); //Si existe el proveedor solo seteo la variale
                    
                    if(productoProveedor.getCosto()!=null && productoProveedor.getCosto().compareTo(BigDecimal.ZERO)>0)
                    {
                        getTxtPrecionUnitarioItem().setText(productoProveedor.getCosto() + "");
                    }
                    
                    //EnumSiNo enumSiNo=EnumSiNo.getEnumByLetra(productoProveedor.getConIva());
                } else {//Cuando no existe crea un nuevo producto proveedor
                    productoProveedor = new ProductoProveedor(); //Si no existe el item lo creo para posteriormente cuando grabe persistir con la base de datos
                    productoProveedor.setDescripcion("");
                    productoProveedor.setEstado("a");
                    productoProveedor.setProducto(productoSeleccionado);
                    productoProveedor.setProveedor(compra.getProveedor());
                    //Si se esta seleccionado un empaque por el momento no hago nada
                    
                    if (!productoSeleccionado.getTipoProductoEnum().equals(TipoProductoEnum.EMPAQUE)) {
                        getTxtPrecionUnitarioItem().setText("0"); //Seteo con el valor de 0 porque no existe el costo grabado
                    }
                    
                }

                getTxtProductoItem().setText(productoSeleccionado.getCodigoPersonalizado());
                getTxtDescripcionItem().setText(productoSeleccionado.getNombre());
                
                //Solo cambiar o cargar el iva cuando no este en modo de edicion, caso contrario se queda el mimos
                //Este artificio lo dejo de esta manera porque estaba dando problemas con las presentaciones
                if(!banderaEdicion)
                {
                    seleccionarComboTipoIva(productoSeleccionado.getCatalogoProducto().getIva().getTarifa());
                }
            } catch (RemoteException ex) {
                Logger.getLogger(CompraModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ServicioCodefacException ex) {
                Logger.getLogger(CompraModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void seleccionarComboTipoIva(Integer porcentajeIva)
    {
        getCmbIvaDetalle().setSelectedItem(porcentajeIva);
    }
    
    private void actualizarTotales()
    {
        //compra.calcularTotales(session.obtenerIvaActualDecimal());
        compra.calcularTotalesDesdeDetalles();
    }
    
       
    
        
    /**
     * Actualiza los datos de la tabla segun los datos grabados en los detalles de la tabla
     * de compras
     */
    private void mostrarDatosTabla()
    {
        String[] titulo={
            "",
            "Código",
            "Cantidad",
            "Unidad",
            "Descripción",
            "lote",
            "Desc",
            "Val Unit",
            "Val+IVA",
            "Ice",
            "Utilidad",            
            "RetIVA",
            "RetRent",
            "Total"
        };
        
        this.modeloTablaDetallesCompra = new DefaultTableModel(titulo,0);
        List<CompraDetalle> detalles= compra.getDetalles();
        for (CompraDetalle detalle : detalles) {
            
            String loteCodigo="";
            if(detalle.getLote()!=null)
            {
                loteCodigo=detalle.getLote().getCodigo();
            }
            
            Vector<Object> fila=new Vector<Object>();
            Producto producto=detalle.getProductoProveedor().getProducto();
            PresentacionProducto presentacionProducto=producto.buscarPresentacionProducto();
            String presentacionProductoNombre="";
            if(presentacionProducto!=null)
            {
                presentacionProductoNombre=presentacionProducto.getNombre();
            }
            
            fila.add(detalle);
            fila.add(producto.getCodigoPersonalizado());
            fila.add(detalle.getCantidad().setScale(3, RoundingMode.HALF_UP)+"");
            fila.add(presentacionProductoNombre);
            fila.add(detalle.getDescripcion()+"");
            fila.add(loteCodigo);
            fila.add(detalle.getDescuento()+"");
            fila.add(detalle.getPrecioUnitario().setScale(4, RoundingMode.HALF_UP)+"");
            fila.add(detalle.obtenerPrecioUnitarioConIva().setScale(2, RoundingMode.HALF_UP));
            fila.add(detalle.getValorIce());
            fila.add(detalle.calcularUtilidadRedondeada()+"");
            fila.add((detalle.getValorSriRetencionIVA()!=null)?detalle.getValorSriRetencionIVA().setScale(3, RoundingMode.HALF_UP)+"":"");
            fila.add((detalle.getValorSriRetencionRenta()!=null)?detalle.getValorSriRetencionRenta().setScale(3,RoundingMode.HALF_UP)+"":"");            
            fila.add(detalle.getSubtotal().setScale(2, RoundingMode.HALF_UP)+"");
            this.modeloTablaDetallesCompra.addRow(fila);
            
            System.out.println(producto.getCodigoPersonalizado()+";"+detalle.getCantidad()+";"+detalle.getPrecioUnitario()+";"+detalle.getDescuento());
        }
                
        getTblDetalleProductos().setModel(this.modeloTablaDetallesCompra);
        UtilidadesTablas.ocultarColumna(getTblDetalleProductos(),0);
        
        EnumSiNo emitirRetencion=(EnumSiNo) getCmbEmitirRetencion().getSelectedItem();
        ArrayList<Integer> miArrayList = new ArrayList<>(Arrays.asList(0,120,50,50,250,80,50,50,50,50,0));
        if(emitirRetencion.getBool())
        {
            miArrayList.addAll(Arrays.asList(50,50,70));
        }
        else
        {
            miArrayList.addAll(Arrays.asList(0,0,70));
        }
            
        UtilidadesTablas.cambiarTamanioColumnas(getTblDetalleProductos(),miArrayList.toArray(new Integer[0]));
        //UtilidadesTablas.cambiarTamanioColumnas(getTblDetalleProductos(),new Integer[]{0,120,50,50,250,80,50,50,50,50,0,0,70});
        
    }
    
    private void mostrarDatosFacturasReembolso()
    {        
        String[] titulo={"","# Factura","Identificación","Base 0%","Base 12%","IVA"};
        modeloTablaCompraReembolso=new DefaultTableModel(titulo,0);
        
        DefaultTableModel modeloTabla=UtilidadesTablas.crearModeloTabla(titulo,new Class[]{Object.class,String.class,String.class},new Boolean[]{false,false,false});
        
        if(compra!=null && compra.getFacturaReembolsoList()!=null)
        {
            for(CompraFacturaReembolso detalle : compra.getFacturaReembolsoList())
            {
                Vector<Object> fila=new Vector<Object>();
                fila.add(detalle);
                fila.add(detalle.getSecuencialReemb()+"");
                fila.add(detalle.getIdProvReemb());
                fila.add(detalle.getBaseImponibleReemb());
                fila.add(detalle.getBaseImpGravReemb());
                fila.add(detalle.getMontoIvaRemb());
                modeloTablaCompraReembolso.addRow(fila);
            }
        }
                
        getTblFacturaReembolso().setModel(modeloTablaCompraReembolso);
        UtilidadesTablas.ocultarColumna(getTblFacturaReembolso(),0);  
    }
    
    
    
    
    private void mostrarDatosTotales()
    {
        getLblSubtotalSinImpuestoSinDescuento().setText(compra.getSubtotalSinImpuestosSinDescuentos().toString());
        getLblSubtotalImpuestoSinDescuento().setText(compra.getSubtotalImpuestosSinDescuentos().setScale(3,RoundingMode.HALF_UP)+"");
        getTxtDescuentoImpuestos().setText(compra.getDescuentoImpuestos()+"");
        getTxtDescuentoSinImpuestos().setText(compra.getDescuentoSinImpuestos()+"");
        getLblSubtotalImpuestos().setText(compra.getSubtotalImpuestos().setScale(3, RoundingMode.HALF_UP)+"");
        getLblSubtotalSinImpuestos().setText(compra.getSubtotalSinImpuestos()+"");
        getLblIva().setText(compra.getIva()+"");
        getTxtIce().setText(compra.getIce()+"");
        getTxtIrbpnr().setText(compra.getIrbpnr()+"");
        getLblTotal().setText(compra.getTotal()+"");
        
        getLblTotalIvaRet().setText(compra.calcularTotalRentencionIva()+"");
        getLblTotalRentaRet().setText(compra.calcularTotalRentencionRenta()+"");
    }

    private void crearVariables() {
        this.compra = new Compra();
        //this.empresa = new Empresa();
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
    
    private void initModelTablaDetalleCompraSinRetencion() {
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
        getTxtDescripcionItem().setEnabled(true);
        getTxtProductoItem().setEnabled(true);
        getTxtCantidadItem().setEnabled(true);
        getTxtPrecionUnitarioItem().setEnabled(true);
        getTxtCostoItem().setEnabled(true);
        getTxtDescuentoItem().setEnabled(true);
        
    }
    
    private void bloquearIngresoDetalleProducto()
    {
        getTxtDescripcionItem().setEnabled(false);
        getTxtProductoItem().setEnabled(false);
        getTxtCantidadItem().setEnabled(false);
        getTxtPrecionUnitarioItem().setEnabled(false);
        getTxtCostoItem().setEnabled(false);
        getTxtDescuentoItem().setEnabled(false);
    }
    
    private void limpiarCampos()
    {
        getTxtDescripcionItem().setText("");
        getTxtPrecionUnitarioItem().setText("");
        getTxtCostoItem().setText("");
        getTxtDescuentoItem().setText("0");
        getTxtProductoItem().setText("");
        getTxtCantidadItem().setText("");
        getTxtLoteNombre().setText("");
        getCmbPresentacionProducto().removeAllItems();
        
    }
    
    private void bloquearDesbloquearBotones(Boolean b)
    {
        getBtnAgregarItem().setEnabled(b);
        getBtnEditarItem().setEnabled(!b);
        getBtnEliminarItem().setEnabled(!b);
    }
    
    private void aplicarRetencionDetalle(CompraDetalle compraDetalle)
    {
        SriRetencionIva sriRetencionIva = (SriRetencionIva) getCmbRetencionIva().getSelectedItem();
        SriRetencionRenta sriRetencionRenta = (SriRetencionRenta) getCmbRetencionRenta().getSelectedItem();
        
        compraDetalle.setSriRetencionIva(sriRetencionIva);
        compraDetalle.setSriRetencionRenta(sriRetencionRenta);
        
        BigDecimal valorRetencionIVA = compraDetalle.getIva().multiply(new BigDecimal(sriRetencionIva.getPorcentaje()+"")).divide(new BigDecimal("100"));
        BigDecimal valorRetencionRenta = compraDetalle.getTotal().multiply(new BigDecimal(sriRetencionRenta.getPorcentaje()+"")).divide(new BigDecimal("100"));
            
        compraDetalle.setValorSriRetencionIVA(valorRetencionIVA.setScale(5,BigDecimal.ROUND_HALF_UP));
        compraDetalle.setValorSriRetencionRenta(valorRetencionRenta.setScale(5,BigDecimal.ROUND_HALF_UP));

        
    }
   
    //TODO: Pasar esta logica de agregar un producto a la entidad de compra para poder usar desde otras partes por ejemplo de la capa del servidor
    private void agregarDetallesCompra(CompraDetalle compraDetalle,Lote lote,ProductoProveedor productoProveedor,BigDecimal costo,BigDecimal cantidadItem,BigDecimal precioUnitario,BigDecimal descuento,String descripcion,Integer porcentajeIva)
    {
        Boolean agregar = true;
        
        if(compraDetalle != null){
            agregar = false;
        }
        else{
            compraDetalle = new CompraDetalle();
        }
            
            if(costo==null)
            {
                productoProveedor.setCosto(precioUnitario);
                //costo=precioUnitario;
            }
            else
            {
                productoProveedor.setCosto(costo);
            }
            
            
            compraDetalle.setCostoUnitario(costo);
            compraDetalle.setCantidad(cantidadItem);
            
            compraDetalle.setIvaPorcentaje(porcentajeIva);
            
            
            //BigDecimal precioUnitario = new BigDecimal(getTxtPrecionUnitarioItem().getText()); 
            //compraDetalle.setPrecioUnitario(precioUnitario.setScale(2,BigDecimal.ROUND_HALF_UP));
            compraDetalle.setPrecioUnitario(precioUnitario ); //TODO: Ver si es necesario escalar los valores o este proceso lo debe hacer el usuario
            compraDetalle.setCompra(compra);
            compraDetalle.setDescripcion(descripcion);
            //compraDetalle.setDescripcion(getTxtDescripcionItem().getText());
            compraDetalle.setDescuento(descuento);
            compraDetalle.setLote(lote);
            /*if(productoProveedor.getProducto().getCatalogoProducto().getIva().getPorcentaje().compareTo(BigDecimal.ZERO)==0)
            {
                compraDetalle.setIva(BigDecimal.ZERO);                
            }
            else
            {
                compraDetalle.setIva(compraDetalle.calcularValorIva());
            }*/
            
            compraDetalle.setIva(compraDetalle.calcularValorIva());
            
            //SriRetencionIva sriRetencionIva = (SriRetencionIva) getCmbRetencionIva().getSelectedItem();
            //SriRetencionRenta sriRetencionRenta = (SriRetencionRenta) getCmbRetencionRenta().getSelectedItem();
            
            //compraDetalle.setSriRetencionIva(sriRetencionIva);
            //compraDetalle.setSriRetencionRenta(sriRetencionRenta);
            
            compraDetalle.setProductoProveedor(productoProveedor);
            //compraDetalle.setTotal(compraDetalle.getSubtotal());
            compraDetalle.calcularSubtotalSinIva();
            
            
            aplicarRetencionDetalle(compraDetalle);
            
            //BigDecimal valorRetencionIVA = compraDetalle.getIva().multiply(new BigDecimal(sriRetencionIva.getPorcentaje()+"")).divide(new BigDecimal("100"));
            //BigDecimal valorRetencionRenta = compraDetalle.getTotal().multiply(new BigDecimal(sriRetencionRenta.getPorcentaje()+"")).divide(new BigDecimal("100"));
            
            
            //compraDetalle.setValorSriRetencionIVA(valorRetencionIVA.setScale(2,BigDecimal.ROUND_HALF_UP));
            //compraDetalle.setValorSriRetencionRenta(valorRetencionRenta.setScale(2,BigDecimal.ROUND_HALF_UP));
            
            //BigDecimal valorTotalRetencion = valorRetencionIVA.add(valorRetencionRenta);
            
            //compraDetalle.setTotal(compraDetalle.getTotal().subtract(valorTotalRetencion));
            
            //compraDetalle.setValorIce(BigDecimal.ZERO);
            
            
            compraDetalle.setCodigoSustentoSriEnum((SriSustentoComprobanteEnum)getCmbSustentoComprobante().getSelectedItem());

            if(agregar)
            {
                compra.addDetalle(compraDetalle);              
            }
            getTxtProductoItem().requestFocus(); //Despues de agregar setear nuevamente en el campo para ingresar otro codigo
            actualizarDatosMostrarVentana();
            getCmbSustentoComprobante().setSelectedIndex(0); //Selecionar el primer sustento despues de agregar
        
     
    }
    
    private void actualizarDatosMostrarVentana()
    {
        actualizarTotales();
        //if(session.getEmpresa().getObligadoLlevarContabilidad().equals(Empresa.SI_LLEVA_CONTABILIDAD)){
            mostrarDatosTabla();
        //}
        //else{
        //    mostrarDatosTablaSinRetencion();
        //}
        mostrarDatosTotales();
        limpiarCampos();
    }
    
    private boolean verificarCamposValidados()
    {
        //boolean b = true;
        List<JTextField> camposValidar = new ArrayList<JTextField>();
            camposValidar.add(getTxtCantidadItem());
            camposValidar.add(getTxtPrecionUnitarioItem());
            camposValidar.add(getTxtDescripcionItem());
            camposValidar.add(getTxtProductoItem());
            for(JTextField campo : camposValidar)
            {
                //System.out.println("Color: -->" + campo.getBackground());
                if(!campo.getBackground().equals(Color.WHITE))
                {
                    return  false;
                }
            }
            
        if(estadoFormulario.equals(ESTADO_GRABAR))
        {
            String precioUnitarioTxt=getTxtPrecionUnitarioItem().getText();
            if(precioUnitarioTxt.isEmpty())
            {
               DialogoCodefac.mensaje("Advertencia","El precio unitario no puede ser vacio",DialogoCodefac.MENSAJE_ADVERTENCIA);
               return false;
            }
            else
            {
                BigDecimal precioUnitario=new BigDecimal(precioUnitarioTxt);
                if(precioUnitario.compareTo(BigDecimal.ZERO)<0)
                {
                    DialogoCodefac.mensaje("Advertencia","El precio unitario debe ser mayor que 0",DialogoCodefac.MENSAJE_ADVERTENCIA);
                    return false;
                }
            }
            
            
            
        }
            
        return true;
    }
    
    /*
    La opcion 1: es para cuando tiene impuestos , la opcion 2: es para sin impuestos
    */
    public void calcularDescuento(int opc, BigDecimal descuento)
    {
        switch(opc)
        {
            case 1:
                compra.setDescuentoImpuestos(descuento.setScale(2, RoundingMode.HALF_UP));
                compra.calcularTotales(session.obtenerIvaActualDecimal());
                break;
            case 2:
                compra.setDescuentoSinImpuestos(descuento.setScale(2,RoundingMode.HALF_UP));
                compra.calcularTotales(session.obtenerIvaActualDecimal());
                break;
        }
        
        mostrarDatosTotales();
    }
    
    public void mostrarVentanaRetenciones() throws RemoteException
    {
        EmpresaServiceIf empresaService = ServiceFactory.getFactory().getEmpresaServiceIf();
        List<Empresa> listadoEmpresas = empresaService.obtenerTodos();
        Empresa empresa = session.getEmpresa();
        
        EnumSiNo habilitarRetensiones=ParametroUtilidades.obtenerValorParametroEnum(session.getEmpresa(),ParametroCodefac.HABILITAR_RETENCION_COMPRAS,EnumSiNo.SI);
        
        if(habilitarRetensiones!=null)
        {
            this.getPanelRetencion().setVisible(habilitarRetensiones.getBool());
            this.getCmbEmitirRetencion().setSelectedItem(habilitarRetensiones);
        }
        else
        {
            if(empresa.getObligadoLlevarContabilidad().equals(Empresa.SI_LLEVA_CONTABILIDAD))
            {
                this.getPanelRetencion().setVisible(true);
                this.getCmbEmitirRetencion().setSelectedItem(EnumSiNo.SI);
            }
            else
            {
                this.getPanelRetencion().setVisible(false);
                this.getCmbEmitirRetencion().setSelectedItem(EnumSiNo.NO);
            }
        }
    }

    private void setearVariblesIniciales() {
        
        //Seleccionar el tipo de documento configurado por defecto para la compra
        ParametroCodefac parametroCodefac=session.getParametrosCodefac().get(ParametroCodefac.DEFECTO_TIPO_DOCUMENTO_COMPRA);
        if(parametroCodefac!=null)
        {
            TipoDocumentoEnum tipoDocumentoEnumDefault=TipoDocumentoEnum.obtenerTipoDocumentoPorCodigo(parametroCodefac.getValor());
            getCmbTipoDocumento().setSelectedItem(tipoDocumentoEnumDefault);
        }
    }

    @Override
    public InterfaceModelFind obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private boolean validarDatosGrabar() {
        
        if(compra.getDetalles()==null || compra.getDetalles().size()==0)
        {
            DialogoCodefac.mensaje("Error Validación","Ingrese detalles para poder grabar",DialogoCodefac.MENSAJE_ADVERTENCIA);
            return false;
        }
        
        if(getCmbDocumento().getSelectedItem()==null)
        {
            DialogoCodefac.mensaje(new CodefacMsj("No se puede grabar sin seleccionar un documento", CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
            return false;
        }
        
        if(getCmbTipoDocumento().getSelectedItem()==null)
        {
            DialogoCodefac.mensaje(new CodefacMsj("No se puede grabar sin seleccionar un tipo de documento", CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
            return false;
        }
        
        
        //La validacion de los secuenciales solo debe funcionar cuando no es un documento interno por que puede ser que no tenga datos de secuenciales
        /*DocumentoEnum documentoEnum=(DocumentoEnum) getCmbDocumento().getSelectedItem();
        if(documentoEnum.equals(DocumentoEnum.NOTA_VENTA_INTERNA))
        {
            try
            {
                if((new BigDecimal(getTxtEstablecimientoCompra().getText())).compareTo(BigDecimal.ZERO)==0)
                {
                    DialogoCodefac.mensaje("Formato error","El establecimiento no puede ser 0",DialogoCodefac.MENSAJE_ADVERTENCIA);
                    return false;
                }
                if((new BigDecimal(getTxtPuntoEmisionCompra().getText())).compareTo(BigDecimal.ZERO)==0)
                {
                    DialogoCodefac.mensaje("Formato error","EL punto de emisión no puede ser 0",DialogoCodefac.MENSAJE_ADVERTENCIA);
                    return false;
                }
                if((new BigDecimal(getTxtSecuencialCompra().getText())).compareTo(BigDecimal.ZERO)==0)
                {
                    DialogoCodefac.mensaje("Formato error","El secuencial no puede ser 0",DialogoCodefac.MENSAJE_ADVERTENCIA);
                    return false;
                }
            } catch(java.lang.NumberFormatException nfe)
            {
                DialogoCodefac.mensaje("Formato error","El secuencial solo pueden ser numeros",DialogoCodefac.MENSAJE_ADVERTENCIA);
                return false;
            }

            if(!(getTxtEstablecimientoCompra().getText().length()==3 && getTxtPuntoEmisionCompra().getText().length()==3 && getTxtSecuencialCompra().getText().length()==9))
            {
                DialogoCodefac.mensaje("Formato error","Revise el formato del secuencial de la compra que es incorrecto",DialogoCodefac.MENSAJE_ADVERTENCIA);
                return false;
            }
        }*/
        
       return true;

    }
    
    private void listenerTexts() {
        UtilidadesFormularios.bloquerLimiteIngresoCampoTexto(getTxtEstablecimientoCompra(),3);
        UtilidadesFormularios.bloquerLimiteIngresoCampoTexto(getTxtPuntoEmisionCompra(),3);
        UtilidadesFormularios.bloquerLimiteIngresoCampoTexto(getTxtSecuencialCompra(),9);

        UtilidadesFormularios.llenarAutomaticamenteCamposTexto(getTxtEstablecimientoCompra(),3);
        UtilidadesFormularios.llenarAutomaticamenteCamposTexto(getTxtPuntoEmisionCompra(),3);
        UtilidadesFormularios.llenarAutomaticamenteCamposTexto(getTxtSecuencialCompra(),9);
        
    }

    private void inicarComponentesGraficos() {
        UtilidadesSwingX.placeHolder("Código Producto", getTxtProductoItem());
        UtilidadesSwingX.placeHolder("Descripción", getTxtDescripcionItem());
        //getTxtProductoItem().
    }

    private void agregarListenerTextoBox() {
        
        getTxtProductoItem().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) 
                {
                    try {
                        Producto producto = ServiceFactory.getFactory().getProductoServiceIf().buscarProductoActivoPorCodigo(getTxtProductoItem().getText(),session.getEmpresa());
                        
                        if (producto != null) 
                        { 
                            agregarProductoVista(producto,CrudEnum.CREAR);
                        }
                        
                    } catch (ServicioCodefacException ex) {
                        Logger.getLogger(CompraModel.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (RemoteException ex) {
                        Logger.getLogger(CompraModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        
        getTxtCantidadItem().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) 
                {
                    try {
                        agregarDetalleCompraConDatosVista(null);
                    } catch (ExcepcionCodefacLite ex) {
                        Logger.getLogger(CompraModel.class.getName()).log(Level.SEVERE, null, ex);
                        DialogoCodefac.mensaje(new CodefacMsj(ex.getMessage(), CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });
                
        
        getTxtDescuentoImpuestos().addKeyListener(new  KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) 
                {
                    calcularDescuentoConImpuestosVista();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        getTxtDescuentoSinImpuestos().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) 
                {
                    calcularDescuentoSinImpuestosVista();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        getTxtIce().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) 
                {
                    calcularIceImpuesto();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });
    }

    private void agregarListerCombos() {
        getCmbEmitirRetencion().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EnumSiNo enumSiNo=(EnumSiNo) getCmbEmitirRetencion().getSelectedItem();
                if(enumSiNo.equals(EnumSiNo.SI))
                {
                    getCmbRetencionIva().setEnabled(true);
                    getCmbRetencionRenta().setEnabled(true);
                }else if(enumSiNo.equals(EnumSiNo.NO))
                {
                    getCmbRetencionIva().setEnabled(false);
                    getCmbRetencionRenta().setEnabled(false);
                }
            }
        });
    }

    private void agregarListenerPopUp() {
        JPopupMenu jPopupMenu=new JPopupMenu();
        JMenuItem jMenuItemDatoAdicional=new JMenuItem("Eliminar");
        jPopupMenu.add(jMenuItemDatoAdicional);
        jMenuItemDatoAdicional.addActionListener(listenerEliminarReembolsoPopUp);
        getTblFacturaReembolso().setComponentPopupMenu(jPopupMenu);
    }
    
    private ActionListener listenerEliminarReembolsoPopUp=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            int indiceFilaSeleccionada=getTblFacturaReembolso().getSelectedRow();
            if(indiceFilaSeleccionada>=0)
            {
                CompraFacturaReembolso compraFacturaReembolso=(CompraFacturaReembolso) getTblFacturaReembolso().getValueAt(indiceFilaSeleccionada,INDICE_OBJ_TABLA_REEMBOLSO);
                compra.quitarFacturaReembolso(compraFacturaReembolso);
                mostrarDatosFacturasReembolso();

            }
        }
    };
    
    
}
