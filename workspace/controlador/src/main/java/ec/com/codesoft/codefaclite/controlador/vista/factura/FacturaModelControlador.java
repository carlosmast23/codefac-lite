/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vista.factura;

import ec.com.codesoft.codefaclite.controlador.core.swing.InterfazComunicacionPanel;
import ec.com.codesoft.codefaclite.controlador.core.swing.ReporteCodefac;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.corecodefaclite.enumerador.OrientacionReporteEnum;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.ComprobanteDataFactura;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.ComprobanteDataInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.ComprobanteDataLiquidacionCompra;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Bodega;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FormaPago;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Presupuesto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriFormaPago;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroEstudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ConfiguracionImpresoraEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.FormatoHojaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefacInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.BodegaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ComprobanteServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.KardexServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ProductoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.ParametroUtilidades;
import ec.com.codesoft.codefaclite.utilidades.reporte.UtilidadesJasper;
import ec.com.codesoft.codefaclite.utilidades.rmi.UtilidadesRmi;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadIva;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesImpuestos;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Carlos
 */
public class FacturaModelControlador extends FacturaNotaCreditoModelControladorAbstract{
    
    private TipoDocumentoEnum tipoDocumentoEnumSeleccionado;
    
    private List<TipoDocumentoEnum> tipoDocumentoList;
    
    private FacturaModelControlador.FacturaModelInterface interfaz;
    
        /**
     * Variable que almacena la forma de pago por defecto cuando no se selecciona ninguna
     */
    private SriFormaPago formaPagoDefecto;
    
    private SriFormaPago formaPagoConCartera;
    

    public FacturaModelControlador(SessionCodefacInterface session,FacturaModelInterface interfaz,MensajeVistaInterface mensajeVista) {
        super(mensajeVista);
        this.session=session;
        this.interfaz=interfaz;
        iniciarValores();
    }
    
    private void iniciarValores()
    {
        try {
            //Obtener la forma de pago
            formaPagoDefecto=ServiceFactory.getFactory().getSriServiceIf().obtenerFormarPagoDefecto();
            formaPagoConCartera=ServiceFactory.getFactory().getSriServiceIf().obtenerFormarPagoConCartera();
        } catch (RemoteException ex) {
            Logger.getLogger(FacturaModelControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public static ComprobanteDataInterface obtenerComprobanteData(Factura factura)
    {
        if(factura.getCodigoDocumentoEnum().equals(DocumentoEnum.FACTURA))
        {
            ComprobanteDataFactura comprobanteData = new ComprobanteDataFactura(factura);
            comprobanteData.setMapInfoAdicional(factura.getMapAdicional());
            return comprobanteData;
        } else if(factura.getCodigoDocumentoEnum().equals(DocumentoEnum.LIQUIDACION_COMPRA))
        {
            ComprobanteDataLiquidacionCompra comprobanteData=new ComprobanteDataLiquidacionCompra(factura);
            comprobanteData.setMapInfoAdicional(factura.getMapAdicional());
            return comprobanteData;
        } else if(factura.getCodigoDocumentoEnum().equals(DocumentoEnum.NOTA_VENTA_INTERNA))
        {
            ComprobanteDataFactura comprobanteData = new ComprobanteDataFactura(factura);
            comprobanteData.setMapInfoAdicional(factura.getMapAdicional());
            return comprobanteData;
        }
        return null;
    }
        
    
    public List<DocumentoEnum>  buscarDocumentosFactura()
    {
        List<DocumentoEnum> tiposDocumento=null;
        //cuando la factura es electronica
        String letraTipoEmision=session.getParametrosCodefac().get(ParametroCodefac.TIPO_FACTURACION).valor;
        if(letraTipoEmision.equals(ComprobanteEntity.TipoEmisionEnum.ELECTRONICA.getLetra()))
        {
            ComprobanteEntity.TipoEmisionEnum tipoEmisionEnum=ComprobanteEntity.TipoEmisionEnum.getEnumByLetra(letraTipoEmision);
            
            tiposDocumento=DocumentoEnum.obtenerPorDocumentosElectronicos(ModuloCodefacEnum.FACTURACION,tipoEmisionEnum);
            
            try {
                if(ParametroUtilidades.comparar(session.getEmpresa(),ParametroCodefac.ACTIVAR_NOTA_VENTA,EnumSiNo.SI))
                {
                    tiposDocumento.add(DocumentoEnum.NOTA_VENTA_INTERNA); //Todo ver si utilizar este documento para grabar o crearme otros 
                }
            } catch (RemoteException ex) {
                Logger.getLogger(FacturaModelControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
            /*
            ParametroCodefac paramCodefacNotaVenta=session.getParametrosCodefac().get(ParametroCodefac.ACTIVAR_NOTA_VENTA);
                    
            if(paramCodefacNotaVenta!=null)
            {
                EnumSiNo enumSino=EnumSiNo.getEnumByLetra(paramCodefacNotaVenta.getValor());
                if(enumSino.equals(EnumSiNo.SI))
                {
                    tiposDocumento.add(DocumentoEnum.NOTA_VENTA_INTERNA); //Todo ver si utilizar este documento para grabar o crearme otros 
                }
            }*/
        }
        else //Cuando la factura es fisica
        {
            tiposDocumento=DocumentoEnum.obtenerPorDocumentosFisico(ModuloCodefacEnum.FACTURACION);
        }
        
        return tiposDocumento;
    }
    
    public void agregarProductoVista(Producto productoSeleccionado) {
        if (productoSeleccionado == null) {
            return;
        }
        verificarProductoConNotaVentaInterna(productoSeleccionado);
        //this.productoSeleccionado=productoSeleccionado;
        interfaz.setProductoSeleccionado(productoSeleccionado);
        
        //cargarPrecios(productoSeleccionado);
        interfaz.cargarPrecios(productoSeleccionado);
        
        String descripcion=productoSeleccionado.getNombre();
        descripcion+=(productoSeleccionado.getCaracteristicas()!=null)?" "+productoSeleccionado.getCaracteristicas():"";
        descripcion=descripcion.replace("\n"," ");
        
        
        FacturaDetalle facturaDetalle=crearFacturaDetalle(
                productoSeleccionado.getValorUnitario(), 
                productoSeleccionado.getPrecioSinSubsidio(),
                descripcion, 
                productoSeleccionado.getCodigoPersonalizado(), 
                productoSeleccionado.getCatalogoProducto(), 
                productoSeleccionado.getIdProducto(), 
                interfaz.obtenerTipoDocumentoSeleccionado(),
                BigDecimal.ZERO);
        
        //interfaz.setearValoresProducto(productoSeleccionado.getValorUnitario(),descripcion,productoSeleccionado.getCodigoPersonalizado(),productoSeleccionado.getCatalogoProducto());
        interfaz.setFacturaDetalleSeleccionado(facturaDetalle);
        setearValoresProducto(facturaDetalle);
    }
    
    public FacturaDetalle crearFacturaDetalle(
            BigDecimal valorUnitario,
            BigDecimal valorSinSubsidio,
            String descripcion,
            String codigo,
            CatalogoProducto catalogoProducto,
            Long referenciaId,            
            TipoDocumentoEnum tipoDocumentoReferencia,
            BigDecimal descuento)
    {
        FacturaDetalle facturaDetalle=new FacturaDetalle();
        facturaDetalle.setCantidad(BigDecimal.ONE);
        facturaDetalle.setDescripcion(descripcion);
        facturaDetalle.setDescuento(descuento);
        facturaDetalle.setPrecioUnitario(valorUnitario);
        facturaDetalle.setReferenciaId(referenciaId);
        facturaDetalle.setCodigoPrincipal(codigo);
        facturaDetalle.setTipoDocumentoEnum(tipoDocumentoReferencia);
        facturaDetalle.setPrecioSinSubsidio(valorSinSubsidio);
        
        if(catalogoProducto!=null)
        {
            if(catalogoProducto.getIce()!=null)
            {
                facturaDetalle.setIcePorcentaje(catalogoProducto.getIce().getPorcentaje());                
            }
            
            if(catalogoProducto.getIva()!=null)
            {
                facturaDetalle.setIvaPorcentaje(catalogoProducto.getIva().getTarifa());
            }
        }
        else
        {
            mostrarMensaje(new CodefacMsj("Advertencia","El producto no tiene catalago",DialogoCodefac.MENSAJE_ADVERTENCIA));            
        }
        
        return facturaDetalle;
    }
    
        
    /**
     * Este metodo sirve para que cuando algun producto que vaya a ingresarse a la factura y no tiene que llevar iva cambiar las propiedades
     * @param producto 
     */
    private void verificarProductoConNotaVentaInterna(Producto producto)
    {
        DocumentoEnum documentoEnum=interfaz.obtenerDocumentoSeleccionado() ;
        BigDecimal valorUnitario=producto.getValorUnitario();
        if(documentoEnum.equals(DocumentoEnum.NOTA_VENTA_INTERNA))
        {
            /**
             * Si el producto tiene ice calculo el nuevo subtotal
             */
            CatalogoProducto catalogoProducto=producto.getCatalogoProducto();
            if(catalogoProducto.getIce()!=null && catalogoProducto.getIce().getPorcentaje()!=null)
            {
                BigDecimal porcentajeIce = (catalogoProducto.getIce() != null) ? catalogoProducto.getIce().getPorcentaje() : null;
                valorUnitario = UtilidadIva.calcularValorConIce(
                        porcentajeIce,
                        valorUnitario).setScale(5,ParametrosSistemaCodefac.REDONDEO_POR_DEFECTO);
                
                catalogoProducto.setIce(null);//Pongo el null para que posteriormente no realice este calculo

            }
            
            //Si el producto es distinto de 0 convierto a producto sin iva y cambio el costo
            if(catalogoProducto.getIva().getTarifa()!=0)
            {
                producto.getCatalogoProducto().getIva().setTarifa(0);
                producto.getCatalogoProducto().getIva().setPorcentaje(BigDecimal.ZERO);
                
                BigDecimal nuevoValorUnitario=UtilidadesImpuestos.agregarValorIva(session.obtenerIvaActual(),valorUnitario);
                producto.setValorUnitario(nuevoValorUnitario);
            }
        }
    }
    
    public  void setearValoresProducto(FacturaDetalle facturaDetalle) {
        interfaz.cargarDatosDetalleVista(
                facturaDetalle.getPrecioUnitario(),
                facturaDetalle.getDescuento(),
                facturaDetalle.getDescripcion(),
                facturaDetalle.getCodigoPrincipal());
        
        if(facturaDetalle.getIvaPorcentaje()==0)
        {
            //Carga el dato por defecto cuando no se puede ingresar productos que contengan iva
            //getCmbIva().setSelectedItem(EnumSiNo.NO);
            //getCmbIva().setEnabled(false);
            interfaz.habilitarComboIva(false);
            interfaz.setComboIva(EnumSiNo.NO);
        }
        else
        {
            try 
            {
                //getCmbIva().setEnabled(true);
                interfaz.habilitarComboIva(true);
                //TODO: Ver alguna forma de cargar por defecto el precio guardado en la base de datos
                if(ParametroUtilidades.comparar(session.getEmpresa(), ParametroCodefac.CARGAR_PRODUCTO_IVA_FACTURA, EnumSiNo.SI))
                {
                    //getCmbIva().setSelectedItem(EnumSiNo.SI);
                    interfaz.setComboIva(EnumSiNo.SI);
                    BigDecimal porcentajeIce=(facturaDetalle.getIcePorcentaje()!=null)?facturaDetalle.getIcePorcentaje():null;
                    BigDecimal valorConIva=UtilidadIva.calcularValorConIvaIncluido(
                            session.obtenerIvaActualDecimal(),
                            porcentajeIce,
                            facturaDetalle.getPrecioUnitario());
                    //getTxtValorUnitario().setText(valorConIva.toString());
                    interfaz.setTxtValorUnitario(valorConIva.toString());
                }
                else
                {
                    //getCmbIva().setSelectedItem(EnumSiNo.NO);
                    interfaz.setComboIva(EnumSiNo.NO);
                }
                
            } catch (RemoteException ex) {
                Logger.getLogger(FacturaModelControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
           
            
        }
    }
    
    
    /**
     * @author Carlos
     * Validacion de la la logica dependiendo el modulo
     * @return 
     */
    private boolean validacionPersonalizadaPorModulos(FacturaDetalle facturaDetalle,DocumentoEnum documentoEnum) {
        
        if(facturaDetalle==null)
        {
            mostrarMensaje(new CodefacMsj("Advertencia","Por favor seleccionar un producto para agregar al detalle de la factura",DialogoCodefac.MENSAJE_ADVERTENCIA));
            //DialogoCodefac.mensaje("Por favor seleccionar un producto para agregar al detalle de la factura",DialogoCodefac.MENSAJE_ADVERTENCIA);
            return false;
        }
        
        TipoDocumentoEnum tipoDocEnum=facturaDetalle.getTipoDocumentoEnum();
        BigDecimal cantidad = facturaDetalle.getCantidad();
        BigDecimal valorUnitario = facturaDetalle.getPrecioUnitario();

        switch(tipoDocEnum)
        {
            case ACADEMICO:
                //TODO: Analizar para el caso que tenga descuento
                if (interfaz.obtenerRubroSeleccionado().obtenerSaldoIncluidoDescuento().compareTo(cantidad.multiply(valorUnitario)) == -1) {
                    DialogoCodefac.mensaje("Validación", "El Total no puede exceder del valor " + interfaz.obtenerRubroSeleccionado().getSaldo() + " del rubro", DialogoCodefac.MENSAJE_ADVERTENCIA);
                    return false;
                }
                break;
                
            case PRESUPUESTOS:
                /*if (presupuestoSeleccionado.getTotalVenta().compareTo(cantidad.multiply(valorUnitario)) == -1) {
                    DialogoCodefac.mensaje("Validación", "El Total no puede exceder del valor " + rubroSeleccionado.getSaldo() + " del rubro", DialogoCodefac.MENSAJE_ADVERTENCIA);
                    return false;
                }*/
                break;
                
            case INVENTARIO:
                return validarAgregarInventario(facturaDetalle,documentoEnum); //Metodo que se encarga de validar el inventario
                
        }
        
        return true;
    }
    
    private boolean validarAgregarInventario(FacturaDetalle facturaDetalle,DocumentoEnum documentoEnum)
    {
        try {
            
            if(ParametroUtilidades.comparar(session.getEmpresa(), ParametroCodefac.FACTURAR_INVENTARIO_NEGATIVO, EnumSiNo.NO))
            {
                try {                    
                    //Verifico si el producto es inventario y esta activo la opción de construir ensamble en la venta porque en ese caso
                    //tampoco debe validar el inventario en la vista para el ensamble
                    if(ParametroUtilidades.comparar(session.getEmpresa(), ParametroCodefac.CONSTRUIR_ENSAMBLES_FACTURAR, EnumSiNo.SI))
                    {
                         //Si tengo que construir el ensamble no valido en la vista porque puede tener stock insuficiente pero despues de construir si puede generar
                        return true;
                    }
                    
                    //Solo hago la validacion del inventario cuando es un producto diferente de proforma , por que para ese tema no deberia importar
                    if(!documentoEnum.equals(DocumentoEnum.PROFORMA))
                    {                    
                        boolean verifadorStock = verificarExistenciaStockProducto(facturaDetalle);
                        //Verificar si agrego los datos al fomurlaro cuando no existe inventario
                        if (!verifadorStock) {
                            mostrarMensaje(new CodefacMsj("No existe stock para el producto", CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
                            //DialogoCodefac.mensaje("Advertencia", "No existe stock para el producto", DialogoCodefac.MENSAJE_ADVERTENCIA);
                            return false;
                        } else {                        
                            return true;
                        }
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(FacturaModelControlador.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ServicioCodefacException ex) {
                    Logger.getLogger(FacturaModelControlador.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        } catch (RemoteException ex) {
            Logger.getLogger(FacturaModelControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Por defecto si no tiene nada seleccionado si permito agregar el inventario
        return true;
        
    }
    
    /**
     * 
     * @return
     * @throws RemoteException
     * @throws ServicioCodefacException 
     * @author Trebor
     */
    public boolean verificarExistenciaStockProducto(FacturaDetalle facturaDetalle) throws RemoteException, ServicioCodefacException
    {
        boolean verificadorStock;
        KardexServiceIf serviceKardex = ServiceFactory.getFactory().getKardexServiceIf();
        //Bodega activa de venta
        BodegaServiceIf serviceBodega = ServiceFactory.getFactory().getBodegaServiceIf();
        Bodega bodegaVenta = serviceBodega.obtenerBodegaVenta(session.getSucursal());
        //Verifica si existe stock para el producto seleccionado
        ProductoServiceIf productoServiceIf=ServiceFactory.getFactory().getProductoServiceIf();
        Producto producto=productoServiceIf.buscarPorId(facturaDetalle.getReferenciaId());
        
        //verificadorStock = serviceKardex.obtenerSiNoExisteStockProducto(bodegaVenta,interfaz.obtenerProductoSeleccionado(), facturaDetalle.getCantidad().intValue());
        verificadorStock = serviceKardex.obtenerSiNoExisteStockProducto(bodegaVenta,producto, facturaDetalle.getCantidad());
        return verificadorStock;
    }
    
    /**
     * TODO: VER SIS ESTE METODO SE PUEDE UNIR CON EL DE ABAJO PORQUE EISTE 2 SIMILARES !IMPORTANTE!
     * @param facturaDetalle
     * @return 
     */
    public boolean agregarDetallesFactura(FacturaDetalle facturaDetalle,DocumentoEnum documentoEnum) throws ServicioCodefacException {

        //Validacion de los datos ingresados para ver si puedo agregar al detalle
        if (!interfaz.validarIngresoDetalle()) {
            int filaSeleccionada=interfaz.filaSeleccionadaTablaDetalle();
            interfaz.cargarDatosDetalles(); //Si no se pudo editar vuelvo a cargar los detalles si se modifico desde la tabla para que quede la forma original
            interfaz.seleccionarFilaTablaDetalle(filaSeleccionada);
            return false;
        }

        facturaDetalle.setCantidad(new BigDecimal(interfaz.obtenerTxtCantidad()));    
        //Validacion personalizada dependiendo de la logica de cada tipo de documento
        if (!validacionPersonalizadaPorModulos(facturaDetalle,documentoEnum)) {
                return false;
        }
            
        //Agregar el tipo de documento seleccionado
        facturaDetalle.setTipoDocumentoEnum(tipoDocumentoEnumSeleccionado);
        
       
        //facturaDetalle.setCantidad(new BigDecimal(interfaz.obtenerTxtCantidad()));
        facturaDetalle.setDescripcion(interfaz.obtenerTxtDescripcion());
        //Calcula los valores dependiendo del iva para tener el valor unitario
        BigDecimal valorTotalUnitario = new BigDecimal(interfaz.obtenerTxtValorUnitario());
        EnumSiNo incluidoIvaSiNo=interfaz.obtenerComboIva();
        //session.
        BigDecimal ivaDefecto=new BigDecimal(session.getParametrosCodefac().get(ParametroCodefac.IVA_DEFECTO).getValor());
        
        if(incluidoIvaSiNo.equals(EnumSiNo.SI))
        {
            BigDecimal porcentajeIce=(facturaDetalle.getIcePorcentaje()!=null)?facturaDetalle.getIcePorcentaje():null;
            valorTotalUnitario=UtilidadIva.calcularValorUnitario(
                    session.obtenerIvaActualDecimal(),
                    porcentajeIce,
                    valorTotalUnitario);
        }
        facturaDetalle.setPrecioUnitario(valorTotalUnitario);
        /**
         * ===========> CALCULA LOS VALORES DEL DESCUENTO <=============
         */
        BigDecimal descuento;
        if (!interfaz.obtenerCheckPorcentajeSeleccion()) { //Cuando no es porcentaje el valor se setea directo
            if (!interfaz.obtenerTxtDescuento().equals("")) {
                descuento = new BigDecimal(this.interfaz.obtenerTxtDescuento());
                //Si esta seleccionada la opcion asumo que el descuento se esta aplicando incluido iva
                if(incluidoIvaSiNo.equals(EnumSiNo.SI))
                {
                    descuento=UtilidadesImpuestos.quitarValorIva(ivaDefecto,descuento,6);
                }
                
            } else {
                descuento = BigDecimal.ZERO;
            }
            
            //Redonde a 2 decimales porque en el Sri no permite con mas decimales
            facturaDetalle.setDescuento(descuento.setScale(2,BigDecimal.ROUND_HALF_UP));
            //facturaDetalle.setDescuento(descuento);
        } else { //Cuando es porcentaje se calcula primero el valor en procentaje
            if (!interfaz.obtenerTxtDescuento().isEmpty()) {
                BigDecimal porcentajeDescuento = new BigDecimal(interfaz.obtenerTxtDescuento());
                facturaDetalle.calcularDescuentoConPorcentaje(porcentajeDescuento, incluidoIvaSiNo, ivaDefecto);                
            }
        }
        
        
          /***
         * Validar que tenga seleccionado un porcentaje de iva para continuar}
         * TODO: Talvez este codigo debe ir en una sección de validaciones pero
         * por el momento le dejo en esta parte
         * @Author 
         */
        if(facturaDetalle.getIvaPorcentaje()==null)
        {
            mostrarMensaje(new CodefacMsj("Advertencia","El producto no tiene definido un porcentaje de Iva",DialogoCodefac.MENSAJE_ADVERTENCIA));
            return false;
        }
        
        calcularTotalesDetalles(facturaDetalle);
        /**
         * ========> VALIDACION QUE EL VALOR UNITARIO MENOS DESCUENTO NO SEA NEGATIVO <=============
         * TODO: Ver si este codigo es correcto poner al final o se debe agrupar todos las validaciones en un bloque
         * para tener mas organiado porque en la parte superior de este metodo existen mas validaciones
         */
        if (facturaDetalle.getCantidad().multiply(facturaDetalle.getPrecioUnitario()).compareTo(facturaDetalle.getDescuento()) >= 0) {
            
            //Solo agregar si no esta en modo edicion
            if (!interfaz.getModoEdicionDetalle()) {
                interfaz.obtenerFactura().addDetalle(facturaDetalle);
            }
            
            interfaz.cargarDatosDetalles();            
            cargarTotales();
            limpiarDetalleFactura();
        } else {
            mostrarMensaje(new CodefacMsj("Alerta", "El valor de Descuento excede, el valor de PrecioTotal del Producto", DialogoCodefac.MENSAJE_ADVERTENCIA));
            //DialogoCodefac.mensaje("Alerta", "El valor de Descuento excede, el valor de PrecioTotal del Producto", DialogoCodefac.MENSAJE_ADVERTENCIA);
            //limpiarDetalleFactura();
            return false;
        }
        
        
        interfaz.setModoEdicionDetalle(false); //Por defecto pongo este valor en falso porque aunque este editando o agregando pongo terminar modo edicion
        return true; //si pasa todas las validaciones asumo que se edito correctamente

        
        
    }
    
    public void calcularTotalesDetalles(FacturaDetalle facturaDetalle)
    {
        //Calular el total despues del descuento porque necesito esa valor para grabar
        
        facturaDetalle.calcularTotalDetalle();
        /**
         * Revisar este calculo del iva para no calcular 2 veces al mostrar
         */
        facturaDetalle.setIvaPorcentaje(facturaDetalle.getIvaPorcentaje());
        if(facturaDetalle.getIcePorcentaje()!=null)
        {
            facturaDetalle.calcularValorIce(facturaDetalle.getIcePorcentaje());
        }
        
             
        facturaDetalle.calculaIva();
    }
    
    public void cargarTotales() {
        Factura factura=interfaz.obtenerFactura();
        //if(estadoFormulario.equals(ESTADO_GRABAR))
        //{
        factura.calcularTotalesDesdeDetalles();
        //}
        /**
         * Setear los componentes graficos despues de los calculos
         */
        interfaz.cargarTotalesVista();
        
        //Verifico que solo exista una forma de pago y si cumple ese requesito actualizo el valor de la forma de pago
        /*if(factura.getFormaPagos()!=null)
        {
            if(factura.getFormaPagos().size()==0)
            {
                a
            }else if (factura.getFormaPagos().size() == 1) {
                FormaPago formaPago = factura.getFormaPagos().get(0);
                formaPago.setTotal(factura.getTotal());            

            }else if( factura.getFormaPagos().size()>1)
            {
                //Si tengo varias formas de pago solo tengo que dejar una forma de pago
               FormaPago formaPago=factura.getFormaPagos().get(0);
               formaPago.setTotal(factura.getTotal());
               factura.getFormaPagos().clear();
               factura.addFormaPago(formaPago);   
               
            }
        }*/
        
        this.cargarFormaPago();
        //agregarFormaPagoConCartera();
        interfaz.cargarFormasPagoTabla();

    }
    
    
    public void cargarFormaPago()
    {
        Factura factura=interfaz.obtenerFactura();
                
        //TODO: Caso cuando no exista ningun valor por pagar lipio las formas de pago
        if(factura.getTotal().compareTo(BigDecimal.ZERO)==0)
        {
            if(factura.getFormaPagos()!=null)
            {
                factura.getFormaPagos().clear();
            }
            return;
        }
        
        if(factura.getFormaPagos()==null || factura.getFormaPagos().size()==0)
        {
            if(factura.getCliente()!=null)
            {
                FormaPago formaPago=new FormaPago();
                formaPago.setPlazo(0);
                
                if(factura.getCliente().getSriFormaPago()==null)
                    formaPago.setSriFormaPago(formaPagoDefecto);
                else
                    formaPago.setSriFormaPago(factura.getCliente().getSriFormaPago());
                    
                formaPago.setTotal(factura.getTotal());
                formaPago.setUnidadTiempo(FormaPago.UnidadTiempoEnum.NINGUNO.getNombre());

                factura.addFormaPago(formaPago);
                agregarFormaPagoConCartera();                
                interfaz.cargarFormasPagoTabla();
            }
        }
        else
        {
            //TODO: Si exsiten varias formas de pago transformo en una sola forma de pago 
            //TODO: buscar una manera que esto luego funcione cuando tengo varias formas de pago asignado y tomar en cuenta la cartera
            if(factura.getFormaPagos().size()>1)
            {
                FormaPago formPagoDefecto=factura.buscarFormaPagoDistintaDeCartera();
                factura.getFormaPagos().clear();
                factura.addFormaPago(formPagoDefecto);
            }
            
            //Si ya existe un dato ingresado solo edita el dato si se cambia de representante
            if (factura.getFormaPagos().size() == 1)
            {
                FormaPago formaPago = factura.getFormaPagos().get(0);
                //TODO: Optimizar para que se cambie la forma de pago solo si es un cliente distinto
                if (factura.getCliente().getSriFormaPago() != null) 
                {
                    formaPago.setSriFormaPago(factura.getCliente().getSriFormaPago());
                } 
                else //Si no esta grabado una forma de pago en el cliente asigno a forma de pago por defecto de las configuraciones
                {
                    if (formaPagoDefecto != null) 
                    {
                        formaPago.setSriFormaPago(formaPagoDefecto);
                    }
                }
                formaPago.setTotal(factura.getTotal());
                agregarFormaPagoConCartera();
                interfaz.cargarFormasPagoTabla();
            }
        }
    }
    
    //TODO: Ver si este metodo lo podemos unir con la parte web
    public void agregarFormaPagoConCartera()
    {        
        //Primero verificar que esta activo el tema de manejar con cartera
        try {            
            if(!ParametroUtilidades.comparar(session.getEmpresa(),ParametroCodefac.ACTIVAR_CARTERA,EnumSiNo.SI))
            {
                return;
            }            
        } catch (RemoteException ex) {
            Logger.getLogger(FacturaModelControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Factura factura=interfaz.obtenerFactura();
        
        //NOTA: Si la factura no tiene nada que pagar no agrego nada
        if(factura.getTotal().compareTo(BigDecimal.ZERO)==0)
        {
            return;
        }
        
        //Si no esta activado el tema de pago con cartera continua
        if(!interfaz.isPagoConCartera())
        {
            return;
        }
        
        try {                        
            BigDecimal saldoDisponibleCartera=ServiceFactory.getFactory().getCarteraServiceIf().obtenerSaldoDisponibleCruzar(factura.getCliente(),session.getEmpresa());
            
            //Si el cliente no tiene saldo en cartera entonces ya no continua con el proceso
            if(saldoDisponibleCartera==null || saldoDisponibleCartera.compareTo(BigDecimal.ZERO)==0)
            {
                return;
            }
            
            //BigDecimal totalFormasPagoOtros=factura.getTotalFormasPagoSinCartera();
            //BigDecimal totalFormaPagoCartera=factura.getTotalFormasPagoCartera();
            
            
            BigDecimal valorFaltaPorPagarDistintoCartera=factura.getTotal().subtract(saldoDisponibleCartera);
            
            //Si el valor que se puede pagar es negativo significa que puedo pagar el total solo con cartera
            if(valorFaltaPorPagarDistintoCartera.compareTo(BigDecimal.ZERO)<=0)
            {
                factura.getFormaPagos().clear(); //Quito todas las formas de pago preExistentes
                FormaPago formaPagoCartera=new FormaPago(factura.getTotal(), formaPagoConCartera);
                factura.addFormaPago(formaPagoCartera);
            }
            else //Si el valor a pagar es menor que el total hago cuadrar las 2 cuentas disponibles
            {                
                FormaPago formaPagoDefecto=factura.buscarFormaPagoDistintaDeCartera();
                formaPagoDefecto.setTotal(valorFaltaPorPagarDistintoCartera);                
                FormaPago formaPagoCartera=new FormaPago(saldoDisponibleCartera, formaPagoConCartera);
                
                factura.getFormaPagos().clear(); //Quito todas las formas de pago para en este paso cuadrar
                factura.getFormaPagos().add(formaPagoDefecto);
                factura.getFormaPagos().add(formaPagoCartera);
            }
                        
            
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(FacturaModelControlador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(FacturaModelControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Limpiar las variables y los campos de de la vista de la parte de detalle
     */
    public void limpiarDetalleFactura() {
        
        TipoDocumentoEnum tipoDocumentoEnum=interfaz.obtenerTipoDocumentoSeleccionado();
        
        //TODO: REVISAR PORQUE ME TOCA HACER ESTA VALIDACION
        if(tipoDocumentoEnum==null)
        {
            tipoDocumentoEnum=tipoDocumentoEnum.LIBRE;
        }
        
        /**
         * TODO: Revisar esta parte que originalemente seteaba con null directamente , si no funciona toca crear metodos para setear las variable
         * FALTA AGREGAR LIMPIAR PARA EL RESTO DE VARIABLES
         */
        interfaz.setProductoSeleccionado(null);

        interfaz.limpiarIngresoDetalleVista();
        
        interfaz.focoTxtCodigoDetalle();
        
        //Desctivar los diferentes precios si el producto fue agregado correctamente
        //getCmbPreciosVenta().removeAllItems();
        interfaz.limpiarComboPrecioVenta();
    }

    @Override
    public ComprobanteAdicional crearComprobanteAdicional(String correo, ComprobanteAdicional.Tipo tipoCorreo, ComprobanteAdicional.CampoDefectoEnum campoDefecto) {
        return new FacturaAdicional(correo, FacturaAdicional.Tipo.TIPO_CORREO, ComprobanteAdicional.CampoDefectoEnum.CORREO);
    }

    public SriFormaPago getFormaPagoDefecto() {
        return formaPagoDefecto;
    }

    public void setFormaPagoDefecto(SriFormaPago formaPagoDefecto) {
        this.formaPagoDefecto = formaPagoDefecto;
    }

    public SriFormaPago getFormaPagoConCartera() {
        return formaPagoConCartera;
    }

    public void setFormaPagoConCartera(SriFormaPago formaPagoConCartera) {
        this.formaPagoConCartera = formaPagoConCartera;
    }

    public TipoDocumentoEnum getTipoDocumentoEnumSeleccionado() {
        return tipoDocumentoEnumSeleccionado;
    }

    public void setTipoDocumentoEnumSeleccionado(TipoDocumentoEnum tipoDocumentoEnumSeleccionado) {
        this.tipoDocumentoEnumSeleccionado = tipoDocumentoEnumSeleccionado;
    }

    public List<TipoDocumentoEnum> getTipoDocumentoList() {
        return tipoDocumentoList;
    }

    public void setTipoDocumentoList(List<TipoDocumentoEnum> tipoDocumentoList) {
        this.tipoDocumentoList = tipoDocumentoList;
    }
    
    
    

    public void iniciar() {
        try {
            tipoDocumentoList= TipoDocumentoEnum.obtenerTipoDocumentoPorModulo(ModuloCodefacEnum.FACTURACION,session.getModulos());
            
            if(tipoDocumentoList.size()==0)
            {
                mostrarMensaje(new CodefacMsj("No tiene disponible ningun modo para facturar", CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
            }
            
            //Seleccionar el tipo de documento configurado por defecto
            TipoDocumentoEnum tipoDocumentoEnumDefault=ParametroUtilidades.obtenerValorBaseDatos(session.getEmpresa(),ParametroCodefac.DEFECTO_TIPO_DOCUMENTO_FACTURA,TipoDocumentoEnum.ACADEMICO);
            tipoDocumentoEnumSeleccionado=TipoDocumentoEnum.LIBRE;
            if(tipoDocumentoEnumDefault!=null)
            {
                tipoDocumentoEnumSeleccionado=tipoDocumentoEnumDefault;
            }
            
            /*ParametroCodefac parametroCodefac=session.getParametrosCodefac().get(ParametroCodefac.DEFECTO_TIPO_DOCUMENTO_FACTURA);
            if(parametroCodefac!=null)
            {
            TipoDocumentoEnum tipoDocumentoEnumDefault=TipoDocumentoEnum.obtenerTipoDocumentoPorCodigo(parametroCodefac.getValor());
            getCmbTipoDocumento().setSelectedItem(tipoDocumentoEnumDefault);
            }*/
            
        } catch (RemoteException ex) {
            Logger.getLogger(FacturaModelControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    /**
     * TODO: Falta organizar mejor este codigo
     * @param facturaProcesando
     * @param nombre
     * @param activarConfiguracionesImpresion
     * @param session
     * @param panelPadre 
     */
    public static void imprimirComprobanteVenta(Factura facturaProcesando,String nombre,Boolean activarConfiguracionesImpresion,SessionCodefacInterface session,InterfazComunicacionPanel panelPadre) 
    {
        TipoReporteEnum tipoReporteEnum=ParametroUtilidades.obtenerValorParametroEnum(session.getEmpresa(),ParametroCodefac.REPORTE_DEFECTO_VENTA, TipoReporteEnum.A2);
        
        if(tipoReporteEnum!=null && tipoReporteEnum.equals(tipoReporteEnum.A4) && facturaProcesando.getCodigoDocumentoEnum().equals(DocumentoEnum.NOTA_VENTA_INTERNA))
        {
            try {
                ComprobanteDataInterface dataFactura= obtenerComprobanteData(facturaProcesando);
                ComprobanteServiceIf comprobanteService=ServiceFactory.getFactory().getComprobanteServiceIf();
                byte[] byteReporte=comprobanteService.getReporteComprobanteComprobante(dataFactura, session.getUsuario(),facturaProcesando.getPreimpreso());
                JasperPrint jasperPrint=(JasperPrint) UtilidadesRmi.deserializar(byteReporte);
                panelPadre.crearReportePantalla(jasperPrint,facturaProcesando.getPreimpreso());
                
                //ComprobanteDataInterface dataFactura= obtenerComprobanteData(facturaProcesando);
                //comprobanteService.generarRideComprobanteNoLegal(facturaProcesando,dataFactura, session.getUsuario());
                
            } catch (RemoteException ex) {
                Logger.getLogger(FacturaModelControlador.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(FacturaModelControlador.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(FacturaModelControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
            return;
        }

        List<ComprobanteVentaData> dataReporte = getDetalleDataReporte(facturaProcesando);        
        Map<String, Object> mapParametros =getMapParametrosReporte(facturaProcesando);
        
        String nombreReporte="comprobante_venta.jrxml";
        
        
        //TODO: Ver si esta parte se puede mejorar para imprimir
        ParametroCodefac parametroCodefac = session.getParametrosCodefac().get(ParametroCodefac.IMPRESORA_TICKETS_VENTAS);
        FormatoHojaEnum formatoEnum=FormatoHojaEnum.A5;
        
        if (parametroCodefac !=null) 
        {
            if(parametroCodefac.getValor()!=null)
            {
                EnumSiNo enumSiNo=EnumSiNo.getEnumByLetra(parametroCodefac.getValor());
                if(enumSiNo!=null && enumSiNo.getBool())
                {
                    formatoEnum=FormatoHojaEnum.TICKET;
                    //nombreReporte = RecursoCodefac.JASPER_FACTURACION.getResourceInputStream("comprobante_venta_ticket.jrxml");
                    nombreReporte = "comprobante_venta_ticket.jrxml";
                    //TODO:Terminar de implementar para los demas comprobantes
                    
                    if(tipoReporteEnum!=null)
                    {
                        nombreReporte=tipoReporteEnum.getReporteJasperNombre();
                    }
                    
                }
            }
        }
        
        ConfiguracionImpresoraEnum configuracion=null;        
        if(activarConfiguracionesImpresion)
        {
            configuracion=obtenerConfiguracionImpresora(session);
        }
        //ReporteCodefac.generarReporteInternalFramePlantilla(parametro, mapParametros, dataReporte, this.panelPadre, "Comprobante de Venta ", OrientacionReporteEnum.VERTICAL,formatoEnum);
        ReporteCodefac.generarReporteInternalFramePlantilla(RecursoCodefac.JASPER_FACTURACION,nombreReporte, mapParametros, dataReporte, panelPadre, nombre, OrientacionReporteEnum.VERTICAL,formatoEnum,configuracion);

    }
    
    /**
     * TODO: Metodo temporal para separar por lote e individual pero toca unir 
     * @param facturas
     * @param nombre
     * @param activarConfiguracionesImpresion
     * @param session
     * @param panelPadre 
     */
    public static void imprimirComprobanteVentaLote(List<Factura> facturas,String nombre,Boolean activarConfiguracionesImpresion,SessionCodefacInterface session,InterfazComunicacionPanel panelPadre)
    {
        List<JasperPrint> jasperList=new ArrayList<JasperPrint>();
        for (Factura factura : facturas) 
        {
            Factura facturaProcesando=factura;
            List<ComprobanteVentaData> dataReporte = getDetalleDataReporte(facturaProcesando);
        
            Map<String, Object> mapParametros =getMapParametrosReporte(facturaProcesando);
            //InputStream path = RecursoCodefac.JASPER_FACTURACION.getResourceInputStream("comprobante_venta.jrxml");
            String nombreReporte="comprobante_venta.jrxml";


            //TODO: Ver si esta parte se puede mejorar para imprimir
            ParametroCodefac parametroCodefac = session.getParametrosCodefac().get(ParametroCodefac.IMPRESORA_TICKETS_VENTAS);
            FormatoHojaEnum formatoEnum=FormatoHojaEnum.A5;

            if (parametroCodefac !=null) 
            {
                if(parametroCodefac.getValor()!=null)
                {
                    EnumSiNo enumSiNo=EnumSiNo.getEnumByLetra(parametroCodefac.getValor());
                    if(enumSiNo!=null && enumSiNo.getBool())
                    {
                        formatoEnum=FormatoHojaEnum.TICKET;
                        //nombreReporte = RecursoCodefac.JASPER_FACTURACION.getResourceInputStream("comprobante_venta_ticket.jrxml");
                        nombreReporte = "comprobante_venta_ticket.jrxml";
                        //TODO:Terminar de implementar para los demas comprobantes
                        TipoReporteEnum tipoReporteEnum=ParametroUtilidades.obtenerValorParametroEnum(session.getEmpresa(),ParametroCodefac.REPORTE_DEFECTO_VENTA, TipoReporteEnum.A2);
                        if(tipoReporteEnum!=null)
                        {
                            nombreReporte=tipoReporteEnum.getReporteJasperNombre();
                        }

                    }
                }
            }

            ConfiguracionImpresoraEnum configuracion=null;        
            if(activarConfiguracionesImpresion)
            {
                configuracion=obtenerConfiguracionImpresora(session);
            }
            //ReporteCodefac.generarReporteInternalFramePlantilla(parametro, mapParametros, dataReporte, this.panelPadre, "Comprobante de Venta ", OrientacionReporteEnum.VERTICAL,formatoEnum);
            
            InputStream reporte=RecursoCodefac.JASPER_FACTURACION.getResourceInputStream(nombreReporte);
            JasperPrint reporteNuevo=ReporteCodefac.generarReporteInternalFramePlantillaReturn(RecursoCodefac.JASPER_FACTURACION,nombreReporte, mapParametros, dataReporte, panelPadre, nombre, OrientacionReporteEnum.VERTICAL,formatoEnum,configuracion);
            
            //JasperPrint reporteNuevo=ReporteCodefac.generarReporteInternalFrameJasperPrint(reporte, mapParametros, dataReporte, panelPadre, nombreReporte, configuracion);
            jasperList.add(reporteNuevo);
        }
        
        generarReporteUnificadoJasper(jasperList);

    }
    
    public static void generarReporteUnificadoJasper(List<JasperPrint> jasperList)
    {
        JasperPrint reporteUnido=null;
        for (JasperPrint jasperPrint : jasperList) {
            if (reporteUnido == null) {
                reporteUnido = jasperPrint;
            } else {
                List pages = jasperPrint.getPages();
                for (int j = 0; j < pages.size(); j++) {
                    JRPrintPage nuevasPaginas = (JRPrintPage) pages.get(j);
                    reporteUnido.addPage(nuevasPaginas);
                }
                //reporteUnido.addPage(page);
            }
        }
        JasperViewer.viewReport(reporteUnido,false);
        
   
    }
        
    public static ConfiguracionImpresoraEnum obtenerConfiguracionImpresora(SessionCodefacInterface session)
    {
        return ParametroUtilidades.<ConfiguracionImpresoraEnum>obtenerValorParametroEnum(session.getEmpresa(),ParametroCodefac.CONFIGURACION_IMPRESORA_FACTURA,ConfiguracionImpresoraEnum.NINGUNA);
        //return null;
    }

        
    public static Map<String,Object> getMapParametrosReporte(Factura facturaProcesando)
    {
        //map de los parametros faltantes
            Map<String,Object> mapParametros=new HashMap<String, Object>();
            mapParametros.put("codigo", facturaProcesando.getPreimpreso());
            mapParametros.put("cedula", facturaProcesando.getIdentificacion());
            mapParametros.put("cliente", facturaProcesando.getRazonSocial());
            mapParametros.put("direccion", facturaProcesando.getDireccion());
            mapParametros.put("telefonos", facturaProcesando.getTelefono());
            mapParametros.put("fechaIngreso", facturaProcesando.getFechaEmision().toString());
            mapParametros.put("subtotal", facturaProcesando.getSubtotalImpuestos().add(facturaProcesando.getSubtotalSinImpuestos()).toString());
            mapParametros.put("iva", facturaProcesando.getIva().toString());
            mapParametros.put("ice", facturaProcesando.getIce().toString());
            mapParametros.put("total", facturaProcesando.getTotal().toString());
            mapParametros.put("autorizacion", facturaProcesando.getClaveAcceso());
            
            return mapParametros;
            
    }
        
    public static List<ComprobanteVentaData> getDetalleDataReporte(Factura facturaProcesando)
    {
        List<ComprobanteVentaData> dataReporte = new ArrayList<ComprobanteVentaData>();

        for (FacturaDetalle detalle : facturaProcesando.getDetalles()) {
            
            ComprobanteVentaData data = new ComprobanteVentaData();
            data.setCantidad(detalle.getCantidad().toString());
            data.setCodigo(detalle.getCodigoPrincipal());
            data.setNombre(detalle.getDescripcion().toString());
            data.setPrecioUnitario(detalle.getPrecioUnitario().toString());
            data.setTotal(detalle.getTotal().toString());
            
            //Datos adicionales para las proformas
            data.setDescuento(detalle.getDescuento().toString());
            data.setDescripcion(detalle.getDescripcion());

            dataReporte.add(data);
        }
        return dataReporte;
    }

    ////////////////////////////////////////////////////////////////////////////
    ///                 CLASES E INTERFACES ADICIONALES
    ////////////////////////////////////////////////////////////////////////////
    
    
    public interface FacturaModelInterface
    {
        public DocumentoEnum obtenerDocumentoSeleccionado();
        
        public TipoDocumentoEnum obtenerTipoDocumentoSeleccionado();
        
        public Producto obtenerProductoSeleccionado();
        public Presupuesto obtenerPresupuestoSeleccionado();
        public RubroEstudiante obtenerRubroSeleccionado();
        
        /**
         * Metodo que me permite establecer o seleccionar el producto seleccionado
         */
        public void setProductoSeleccionado(Producto producto);
        
        /*
        Metodo que permite establecer como cargar los precios en la vista
        */
        public void cargarPrecios(Producto producto);
        
        /**
         * 
         * @param valorUnitario
         * @param descripcion
         * @param codigo
         * @param catologoProducto 
         */
        public void setearValoresProducto(BigDecimal valorUnitario,String descripcion,String codigo,CatalogoProducto catologoProducto);
        
        /**
         * Metodo que me permite cargar los detalle que necesito para mostrar en la vista los datos del cliente
         */
        public void cargarDatosDetalleVista(BigDecimal valorUnitario,BigDecimal descuento,String descripcion,String codigo);
        
        public void habilitarComboIva(Boolean opcion);
        public void setComboIva(EnumSiNo enumSiNo);
        public void setTxtValorUnitario(String valorUnitario);
        public String obtenerTxtDescuento();
        public String obtenerTxtCantidad();
        public String obtenerTxtDescripcion();
        public String obtenerTxtValorUnitario();
        public EnumSiNo obtenerComboIva();
        public Factura obtenerFactura();
        public Boolean obtenerCheckPorcentajeSeleccion();
        
        public void limpiarComboPrecioVenta();
        public void focoTxtCodigoDetalle();
        public void setearCantidadTxt(String cantidad);
        public void setearDescripcionTxt(String descripcion);
        public void setearValorUnitarioTxt(String valorUnitario);
        public void setearDescuentoTxt(String descuento);
        public void setearCodigoDetalleTxt(String codigoDetalle);
        
        public void cargarTotalesVista();
        public void cargarFormasPagoTabla();
        public void cargarDatosDetalles();
        public Boolean validarIngresoDetalle();
        public Integer filaSeleccionadaTablaDetalle();
        public void seleccionarFilaTablaDetalle(int filaSeleccionada);
        public void setFacturaDetalleSeleccionado(FacturaDetalle facturaDetalle);
        public Boolean getModoEdicionDetalle();
        public void setModoEdicionDetalle(Boolean modoEdicionDetalle);
        public void limpiarIngresoDetalleVista();
        public Boolean isPagoConCartera();
        
        
    }
    
    public enum TipoReporteEnum implements ParametroUtilidades.ComparadorInterface
    {
        A4("A4",""),
        A2("A2",""),
        POS_80("POS 80","comprobante_venta_ticket.jrxml"),
        POS_50("POS 50","comprobante_venta_ticket_50.jrxml");

        private TipoReporteEnum(String nombre,String reporteJasperNombre) {
            this.nombre = nombre;
            this.reporteJasperNombre=reporteJasperNombre;
        }
            
        private String nombre;
        private String reporteJasperNombre;

        public String getNombre() {
            return nombre;
        }

        public String getReporteJasperNombre() {
            return reporteJasperNombre;
        }
        
        public static TipoReporteEnum findByName(String nombre)
        {
            for (TipoReporteEnum enumerador : TipoReporteEnum.values()) {
                if(enumerador.getNombre().equals(nombre))
                {
                    return enumerador;
                }
            }
            return null;
        }

        @Override
        public Object consultarParametro(String nombreParametro) {
            return TipoReporteEnum.findByName(nombreParametro);
        }
        
    }
    
}
