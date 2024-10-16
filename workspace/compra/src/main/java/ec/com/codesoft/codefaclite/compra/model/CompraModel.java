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
import ec.com.codesoft.codefaclite.compra.busqueda.OrdenCompraBusqueda;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ProductoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ProveedorBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.crm.busqueda.ProductoProveedorBusquedaDialogo;
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
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PersonaEstablecimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencionIva;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencionRenta;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.compra.OrdenCompra;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.compra.OrdenCompraDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.OperadorNegocioEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.VentanaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.sri.SriSustentoComprobanteEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.EmpresaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ProductoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SriRetencionIvaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SriRetencionRentaServiceIf;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.swing.UtilidadesFormularios;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesSwingX;
import es.mityc.firmaJava.libreria.utilidades.Utilidades;
import es.mityc.firmaJava.ocsp.config.ServidorOcsp;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
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
    //private Empresa empresa;
    private Compra compra;
    private Producto productoSeleccionado;
    //private Persona proveedor;
    private ProductoProveedor productoProveedor;
    private DefaultTableModel modeloTablaDetallesCompra;
    private Boolean bandera;
    private int filaDP;
    private Boolean banderaIngresoDetallesCompra;
    private Compra.RetencionEnumCompras estadoRetencion;

    public CompraModel() {
        super.validacionDatosIngresados=false;
    }
    
    
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        inicarComponentesGraficos();
        iniciarCombos();
        agregarListerCombos();
        agregarListenerBotones();
        agregarListenerTextoBox();
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
        this.bandera = false;
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
            servicio.grabarCompra(compra);
            DialogoCodefac.mensaje("Correcto","La compra fue guardada correctamente",DialogoCodefac.MENSAJE_CORRECTO);
        } catch (ServicioCodefacException ex) {
            DialogoCodefac.mensaje("Incorrecto","No se puede gurdar la compra",DialogoCodefac.MENSAJE_INCORRECTO);
            Logger.getLogger(CompraModel.class.getName()).log(Level.SEVERE, null, ex);
            throw new ExcepcionCodefacLite(ex.getMessage());
        } catch (RemoteException ex) {
            Logger.getLogger(CompraModel.class.getName()).log(Level.SEVERE, null, ex);
            throw new ExcepcionCodefacLite(ex.getMessage());
        }
    }
    
    private void setearValores()
    {
        Persona.TipoIdentificacionEnum tipoIdentificacionEnum=compra.getProveedor().getTipoIdentificacionEnum();
        String codigoSri=tipoIdentificacionEnum.getCodigoSriCompra();
        compra.setTipoIdentificacionCodigoSri(codigoSri); //TODO: Ver si esta variable se debe grabar en el servidor
        
        String preimpreso = "";
        compra.setClaveAcceso("");
        DocumentoEnum documentoEnum= (DocumentoEnum) getCmbDocumento().getSelectedItem();
        compra.setEmpresa(session.getEmpresa());
        compra.setCodigoDocumento(documentoEnum.getCodigo());      
        compra.setEstado(GeneralEnumEstado.ACTIVO.getEstado()); //TODO: cambiar el estado de las ordenes de compra
        compra.setFechaCreacion(UtilidadesFecha.getFechaHoy());
        compra.setFechaFactura(new Date(getCmbFechaCompra().getDate().getTime()));
        
        compra.setIdentificacion(compra.getProveedor().getIdentificacion());
        compra.setRazonSocial(compra.getProveedor().getRazonSocial());
        compra.setTelefono(compra.getProveedor().getEstablecimientos().get(0).getTelefonoCelular()); //Todo: ver si se modifica para trabajar por sucursales
        compra.setDireccion(compra.getProveedor().getEstablecimientos().get(0).getDireccion());//Todo: ver si se modifica para trabajar por sucursales
        //compra.setProveedor(proveedor);
        
//        compra.setPuntoEmision(getTxtPuntoEmision().getText());
//        compra.setPuntoEstablecimiento(getTxtEstablecimiento().getText());
//        compra.setSecuencial(Integer.parseInt(getTcmbTipoDocumentoxtSecuencial().getText()));
        //compra.setPuntoEmision(getTxtFPreimpreso().getText().substring(0,3));
        //compra.setPuntoEstablecimiento(getTxtFPreimpreso().getText().substring(4,7));
        //compra.setSecuencial(Integer.parseInt(getTxtFPreimpreso().getText().substring(8, 17)));
        compra.setPuntoEmision(getTxtPuntoEmisionCompra().getText());
        compra.setPuntoEstablecimiento(getTxtEstablecimientoCompra().getText());
        compra.setSecuencial(Integer.parseInt(getTxtSecuencialCompra().getText()));
        
        compra.setTipoFacturacion(""); //TODO: Establecer el metodo de facturacion manual y electronica
        compra.setInventarioIngreso(EnumSiNo.NO.getLetra());
        compra.setObservacion(getTxtObservacion().getText());
        compra.setAutorizacion(getTxtAutorizacion().getText().trim());
        
        //Seteando el tipo de documento 
        TipoDocumentoEnum tipoDocumentoEnum= (TipoDocumentoEnum) getCmbTipoDocumento().getSelectedItem();
        compra.setCodigoTipoDocumento(tipoDocumentoEnum.getCodigo());
        
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
        
        
        compra.setEstadoRetencion(estadoRetencion.getEstado());
        
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
        
    }

    @Override
    public void actualizar() throws ExcepcionCodefacLite {
        
    }

    @Override
    public void buscar() throws ExcepcionCodefacLite {
        CompraBusqueda compraBusqueda = new CompraBusqueda(session.getEmpresa());
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(compraBusqueda);
        buscarDialogoModel.setVisible(true);
        Compra compra = (Compra)buscarDialogoModel.getResultado();
        if(compra != null)
        {
            this.compra=compra;
            cargarDatosCompra();
        }else{
            throw new ExcepcionCodefacLite("Excepcion lanzada desde buscar compra vacio");
        }
        
        
    }
    
    private void cargarDatosCompra()
    {
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
        this.getLblSubtotalImpuestoSinDescuento().setText(compra.getSubtotalImpuestosSinDescuentos().toString());
        this.getLblSubtotalSinImpuestoSinDescuento().setText(compra.getSubtotalSinImpuestosSinDescuentos().toString());
        
        this.getCmbDocumento().setSelectedItem(DocumentoEnum.obtenerDocumentoPorCodigo(compra.getCodigoDocumento()));
        this.getCmbSustentoComprobante().setSelectedItem(compra.getCodigoSustentoSriEnum());
        
        //Cargar la orden de compra si existe referencia
        if(this.compra.getOrdenCompra()!=null)
        {
            this.getTxtOrdenCompra().setText(this.compra.getOrdenCompra().getId()+"-"+this.compra.getOrdenCompra().getObservacion());
        }
        
        //actualizarDatosMostrarVentana();
        if (session.getEmpresa().getObligadoLlevarContabilidad().equals(Empresa.SI_LLEVA_CONTABILIDAD)) {
            mostrarDatosTabla();
        } else {
            mostrarDatosTablaSinRetencion();
        }
        
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
        getTxtCantidadItem().setText("");
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
        //getCmbDocumento().setSelectedIndex(0);
        cargarCatalogoRetencionesDefecto();
        
        getRdbEmisionFisica().setSelected(true);
        
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
        
        //Agregar los tipos de documentos disponibles
        getCmbTipoDocumento().removeAllItems();
        getCmbTipoDocumento().addItem(TipoDocumentoEnum.COMPRA);
        getCmbTipoDocumento().addItem(TipoDocumentoEnum.COMPRA_INVENTARIO);
        getCmbTipoDocumento().addItem(TipoDocumentoEnum.COMPRA_SERVICIOS);
        
        //Iniciar componentes de generar o no retencion
        getCmbEmitirRetencion().removeAllItems();
        getCmbEmitirRetencion().addItem(EnumSiNo.SI);
        getCmbEmitirRetencion().addItem(EnumSiNo.NO);
        
        
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
        compra.setUsuarioId(ordenCompra.getUsuarioId());
        
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
        cargarDatosCompra();
        
    }

    private void agregarListenerBotones() {
        
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
                            String identificacion = compra.getProveedor().getIdentificacion();
                            String nombre = compra.getProveedor().getRazonSocial();
                            getTxtProveedor().setText(identificacion + " - " + nombre);
                            desbloquearIngresoDetalleProducto();
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
                }
            }
        });
        
        getBtnBuscarProductoProveedor().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductoBusquedaDialogo buscarBusquedaDialogo = new ProductoBusquedaDialogo(session.getEmpresa());
                BuscarDialogoModel buscarDialogo = new BuscarDialogoModel(buscarBusquedaDialogo);
                buscarDialogo.setVisible(true);
                productoSeleccionado = (Producto) buscarDialogo.getResultado();
                verificarExistenciadeProductoProveedor();
                bloquearDesbloquearBotones(true);
                getTxtCantidadItem().requestFocus();
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
        
        getTblDetalleProductos().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt)
            {
                filaDP = getTblDetalleProductos().getSelectedRow();
                bandera = true;
                if(filaDP>=0)
                {
                    CompraDetalle compraDetalle = (CompraDetalle) compra.getDetalles().get(filaDP); //TODO: Revisar si esta forma es la mas optima
                    getTxtProductoItem().setText(compraDetalle.getProductoProveedor().getProducto().getCodigoPersonalizado());
                    verificarExistenciadeProductoProveedor();
                    getTxtDescripcionItem().setText(compraDetalle.getDescripcion());
                    getTxtCantidadItem().setText(compraDetalle.getCantidad()+"");
                    getTxtPrecionUnitarioItem().setText(compraDetalle.getPrecioUnitario()+"");
                    getCmbRetencionIva().setSelectedItem(compraDetalle.getSriRetencionIva());
                    getCmbRetencionRenta().setSelectedItem(compraDetalle.getSriRetencionRenta());
                    //compraDetalle.setPrecioUnitario
                    compraDetalle.getPrecioUnitario();
                    bloquearDesbloquearBotones(false);
                    //----------------------------------------------------------------------
                    productoSeleccionado = compraDetalle.getProductoProveedor().getProducto();
                    
                    
                    getCmbSustentoComprobante().setSelectedItem(compraDetalle.getCodigoSustentoSriEnum());
                }
                
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
                    modeloTablaDetallesCompra.removeRow(filaDP);
                    compra.getDetalles().remove(filaDP);                   
                    actualizarDatosMostrarVentana();
                }
            }
        });
                
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
        if(productoSeleccionado!=null)
                {
                    try {
                        //Buscar si existe el producto vinculado con un proveedor
                        ProductoProveedorServiceIf serviceProductoProveedor = ServiceFactory.getFactory().getProductoProveedorServiceIf();
                        //Map<String, Object> mapParametros = new HashMap<String, Object>();
                        //mapParametros.put("producto", productoSeleccionado);
                        //mapParametros.put("proveedor", compra.getProveedor());
                        List<ProductoProveedor> resultados = serviceProductoProveedor.buscarProductoCompraActivo(productoSeleccionado,compra);
                        
                        if (resultados != null && resultados.size() > 0) {
                            productoProveedor = resultados.get(0); //Si existe el proveedor solo seteo la variale
                            getTxtPrecionUnitarioItem().setText(productoProveedor.getCosto()+"");
                            //EnumSiNo enumSiNo=EnumSiNo.getEnumByLetra(productoProveedor.getConIva());
                        }
                        else
                        {//Cuando no existe crea un nuevo producto proveedor
                            productoProveedor=new ProductoProveedor(); //Si no existe el item lo creo para posteriormente cuando grabe persistir con la base de datos
                            productoProveedor.setDescripcion("");
                            productoProveedor.setEstado("a");
                            productoProveedor.setProducto(productoSeleccionado);
                            productoProveedor.setProveedor(compra.getProveedor());
                            getTxtPrecionUnitarioItem().setText("0"); //Seteo con el valor de 0 porque no existe el costo grabado
                        }
                        
                        getTxtProductoItem().setText(productoSeleccionado.getCodigoPersonalizado());
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
        compra.calcularTotales(session.obtenerIvaActualDecimal());
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
    
    private void mostrarDatosTablaSinRetencion()
    {
        String[] titulo={"Cantidad","Descripción","Valor Unitario","Valor Total"};
        this.modeloTablaDetallesCompra = new DefaultTableModel(titulo,0);
        List<CompraDetalle> detalles= compra.getDetalles();
        for (CompraDetalle detalle : detalles) {
            Vector<String> fila=new Vector<String>();
            fila.add(detalle.getCantidad()+"");
            fila.add(detalle.getDescripcion()+"");
            fila.add(detalle.getPrecioUnitario()+"");
            fila.add(detalle.getSubtotal()+"");
            this.modeloTablaDetallesCompra.addRow(fila);
        }
        
        getTblDetalleProductos().setModel(this.modeloTablaDetallesCompra);
    }
    
    
    
    private void mostrarDatosTotales()
    {
        getLblSubtotalSinImpuestoSinDescuento().setText(compra.getSubtotalSinImpuestosSinDescuentos().toString());
        getLblSubtotalImpuestoSinDescuento().setText(compra.getSubtotalImpuestosSinDescuentos().toString());
        getTxtDescuentoImpuestos().setText(compra.getDescuentoImpuestos()+"");
        getTxtDescuentoSinImpuestos().setText(compra.getDescuentoSinImpuestos()+"");
        getLblSubtotalImpuestos().setText(compra.getSubtotalImpuestos()+"");
        getLblSubtotalSinImpuestos().setText(compra.getSubtotalSinImpuestos()+"");
        getLblIva().setText(compra.getIva()+"");
        getLblTotal().setText(compra.getTotal()+"");
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
        
    }
    
    private void bloquearIngresoDetalleProducto()
    {
        getTxtDescripcionItem().setEnabled(false);
        getTxtProductoItem().setEnabled(false);
        getTxtCantidadItem().setEnabled(false);
        getTxtPrecionUnitarioItem().setEnabled(false);
        
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
            //EnumSiNo enumSiNo= (EnumSiNo) getCmbCobraIva().getSelectedItem();
            //productoProveedor.setConIva(enumSiNo.getLetra());
            
            //Seteo los valores de los detalles e la compra
            compraDetalle.setCantidad(new BigDecimal(getTxtCantidadItem().getText()));
            BigDecimal precioUnitario = new BigDecimal(getTxtPrecionUnitarioItem().getText()); 
            //compraDetalle.setPrecioUnitario(precioUnitario.setScale(2,BigDecimal.ROUND_HALF_UP));
            compraDetalle.setPrecioUnitario(precioUnitario ); //TODO: Ver si es necesario escalar los valores o este proceso lo debe hacer el usuario
            compraDetalle.setCompra(compra);
            compraDetalle.setDescripcion(getTxtDescripcionItem().getText());
            compraDetalle.setDescuento(BigDecimal.ZERO);
            if(productoProveedor.getProducto().getCatalogoProducto().getIva().getPorcentaje().compareTo(BigDecimal.ZERO)==0)
            {
                compraDetalle.setIva(BigDecimal.ZERO);                
            }
            else
            {
                compraDetalle.setIva(compraDetalle.calcularValorIva());
            }
            
            SriRetencionIva sriRetencionIva = (SriRetencionIva) getCmbRetencionIva().getSelectedItem();
            SriRetencionRenta sriRetencionRenta = (SriRetencionRenta) getCmbRetencionRenta().getSelectedItem();
            
            compraDetalle.setSriRetencionIva(sriRetencionIva);
            compraDetalle.setSriRetencionRenta(sriRetencionRenta);
            
            compraDetalle.setProductoProveedor(productoProveedor);
            //compraDetalle.setTotal(compraDetalle.getSubtotal());
            compraDetalle.calcularSubtotalSinIva();
            
            BigDecimal valorRetencionIVA = compraDetalle.getIva().multiply(new BigDecimal(sriRetencionIva.getPorcentaje()+"")).divide(new BigDecimal("100"));
            BigDecimal valorRetencionRenta = compraDetalle.getTotal().multiply(new BigDecimal(sriRetencionRenta.getPorcentaje()+"")).divide(new BigDecimal("100"));
            
            
            compraDetalle.setValorSriRetencionIVA(valorRetencionIVA.setScale(2,BigDecimal.ROUND_HALF_UP));
            compraDetalle.setValorSriRetencionRenta(valorRetencionRenta.setScale(2,BigDecimal.ROUND_HALF_UP));
            
            BigDecimal valorTotalRetencion = valorRetencionIVA.add(valorRetencionRenta);
            
            //compraDetalle.setTotal(compraDetalle.getTotal().subtract(valorTotalRetencion));
            
            compraDetalle.setValorIce(BigDecimal.ZERO);
            
            
            compraDetalle.setCodigoSustentoSriEnum((SriSustentoComprobanteEnum)getCmbSustentoComprobante().getSelectedItem());

            if(agregar)
            {
                compra.addDetalle(compraDetalle);              
            }
            getTxtProductoItem().requestFocus(); //Despues de agregar setear nuevamente en el campo para ingresar otro codigo
            actualizarDatosMostrarVentana();
            getCmbSustentoComprobante().setSelectedIndex(0); //Selecionar el primer sustento despues de agregar
        }
     
    }
    
    private void actualizarDatosMostrarVentana()
    {
        actualizarTotales();
        if(session.getEmpresa().getObligadoLlevarContabilidad().equals(Empresa.SI_LLEVA_CONTABILIDAD)){
            mostrarDatosTabla();
        }
        else{
            mostrarDatosTablaSinRetencion();
        }
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
                if(precioUnitario.compareTo(BigDecimal.ZERO)<=0)
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
        
        if(empresa.getObligadoLlevarContabilidad().equals(Empresa.SI_LLEVA_CONTABILIDAD))
        {
            this.getPanelRetencion().setVisible(true);
            /*getCmbSustentoComprobante().setVisible(true);
            getLblSustentoSri().setVisible(true);*/
        }
        else
        {
            this.getPanelRetencion().setVisible(false);
            /*getCmbSustentoComprobante().setVisible(false);
            getLblSustentoSri().setVisible(false);*/
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
    public BuscarDialogoModel obtenerDialogoBusqueda() {
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
        
        getTxtCantidadItem().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) 
                {
                    agregarDetallesCompra(null);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        getTxtProductoItem().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                 if (e.getKeyCode() == KeyEvent.VK_ENTER) 
                 {
                     try {
                         //Solo validar si existe datos ingresados en el combo
                         if (getTxtProductoItem().getText().trim().equals("")) {
                             return;
                         }
                         
                         ProductoServiceIf productoServiceIf=ServiceFactory.getFactory().getProductoServiceIf();
                         Producto productoSeleccionadoTmp=productoServiceIf.buscarProductoActivoPorCodigo(getTxtProductoItem().getText(),session.getEmpresa());
                         if(productoSeleccionadoTmp!=null)
                         {
                            productoSeleccionado=productoSeleccionadoTmp;
                            verificarExistenciadeProductoProveedor();
                            bloquearDesbloquearBotones(true);
                            getTxtCantidadItem().requestFocus();
                         }
                     } catch (RemoteException ex) {
                         Logger.getLogger(CompraModel.class.getName()).log(Level.SEVERE, null, ex);
                     } catch (ServicioCodefacException ex) {
                         Logger.getLogger(CompraModel.class.getName()).log(Level.SEVERE, null, ex);
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
}
