/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vista.factura;

import com.healthmarketscience.rmiio.RemoteInputStreamClient;
import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.controlador.core.swing.InterfazComunicacionPanel;
import ec.com.codesoft.codefaclite.controlador.core.swing.ReporteCodefac;
import ec.com.codesoft.codefaclite.controlador.core.swing.ReporteCodefac.ImpresionAutomaticaEnum;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.corecodefaclite.enumerador.OrientacionReporteEnum;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general.InformacionAdicional;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.ComprobanteDataFactura;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.ComprobanteDataInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.ComprobanteDataLiquidacionCompra;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FormaPago;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Kardex;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.KardexItemEspecifico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Lote;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Mesa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ObjetoMantenimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PersonaEstablecimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Presupuesto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PresupuestoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ProductoPresentacionDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriFormaPago;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Estudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroEstudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ConfiguracionImpresoraEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DatosAdicionalesComprobanteEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EstadoFormEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.FormatoHojaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModoProcesarEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoProductoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefacInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.reportData.InformacionAdicionalData;
import ec.com.codesoft.codefaclite.servidorinterfaz.respuesta.ReferenciaDetalleFacturaRespuesta;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ComprobanteServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.KardexServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ProductoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RecursosServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.ParametroUtilidades;
import ec.com.codesoft.codefaclite.utilidades.reporte.UtilidadesJasper;
import ec.com.codesoft.codefaclite.utilidades.rmi.UtilidadesRmi;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadIva;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesImpuestos;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesPorcentajes;
import es.mityc.firmaJava.libreria.utilidades.Utilidades;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Carlos
 */
public class FacturaModelControlador extends FacturaNotaCreditoModelControladorAbstract{
    
    private TipoDocumentoEnum tipoDocumentoEnumSeleccionado;
    
    private List<TipoDocumentoEnum> tipoDocumentoList;
    
    private List<Mesa> mesaList;
    
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
            /**
             * TODO: Filtrar luego solo las mesas que esten activas
             */
            mesaList=ServiceFactory.getFactory().getMesaSeviceIf().obtenerTodosActivos(session.getEmpresa());
        } catch (RemoteException ex) {
            Logger.getLogger(FacturaModelControlador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(FacturaModelControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * TODO: Ver si esta parte no debe manejar el cliente si no directamente en el servidor para todos los casos
     * @param factura
     * @return 
     */
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
        //String letraTipoEmision=session.getParametrosCodefac().get(ParametroCodefac.TIPO_FACTURACION).valor;
        //if(letraTipoEmision.equals(ComprobanteEntity.TipoEmisionEnum.ELECTRONICA.getLetra()))
        //{
        //TODO: Por el momento dejo esta 
            ComprobanteEntity.TipoEmisionEnum tipoEmisionEnum=ComprobanteEntity.TipoEmisionEnum.getEnumByLetra(ComprobanteEntity.TipoEmisionEnum.ELECTRONICA.getLetra());
            
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
        //}
        //else //Cuando la factura es fisica
        //{
        //    tiposDocumento=DocumentoEnum.obtenerPorDocumentosFisico(ModuloCodefacEnum.FACTURACION);
        //}
        
        return tiposDocumento;
    }
    
    private boolean verificarExistePresupuestoAgregado(Presupuesto presupuesto)
    {
        Factura factura= interfaz.obtenerFactura();
        for (FacturaDetalle facturaDetalle : factura.getDetalles()) 
        {
            //Verificar solo los que son de tipo academico
            if(facturaDetalle.getTipoDocumentoEnum().equals(TipoDocumentoEnum.PRESUPUESTOS))
            {
                try {
                    Presupuesto presupuestoTmp=ServiceFactory.getFactory().getPresupuestoServiceIf().buscarPorId(facturaDetalle.getReferenciaId());
                    
                    if(presupuestoTmp!=null)
                    {
                        if(presupuestoTmp.equals(presupuesto))
                        {
                            return true;
                        }
                    }                    
                    
                } catch (RemoteException ex) {
                    Logger.getLogger(FacturaModelControlador.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return false;
    }
    
    //TODO: Ver alguna forma de unificar con el metodo de agregarProductoVista
    @Deprecated
    public FacturaDetalle agregarPresupuestoVista(Presupuesto presupuestoTmp,Boolean agregarAutomaticamente)
    {
        if (presupuestoTmp != null) {

            if(verificarExistePresupuestoAgregado(presupuestoTmp))
            {
                mostrarMensaje(new CodefacMsj("EL presupuesto ya esta agregado, no se puede agregar nuevamente", CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
                //DialogoCodefac.mensaje("Advertencia","EL presupuesto ya esta agregado, no se puede agregar nuevamente",DialogoCodefac.MENSAJE_ADVERTENCIA);
                return null;
            }
            
            //presupuestoSeleccionado=presupuestoTmp;
            interfaz.setPresupuestoSeleccionado(presupuestoTmp);
            //EnumSiNo.SI
            
            String objetoVehiculoTxt="";
            String kilometrajeTxt="";
            ObjetoMantenimiento objetoMantenimiento=presupuestoTmp.getOrdenTrabajoDetalle().getOrdenTrabajo().getObjetoMantenimiento();
            
            if(objetoMantenimiento!=null)
            {
                objetoVehiculoTxt= " "+objetoMantenimiento.toString()+" ";                
                kilometrajeTxt=objetoMantenimiento.getKilometraje()+"";
            }
            
            
            String descripcion="P "+presupuestoTmp.getId()+objetoMantenimiento+" OT"+presupuestoTmp.getOrdenTrabajoDetalle().getOrdenTrabajo().getId()+"  "+presupuestoTmp.getDescripcion();
            FacturaDetalle facturaDetalle=crearFacturaDetalle(
                    BigDecimal.ONE,
                    presupuestoTmp.calcularValorServicio(), 
                    null, //No tiene valor del subsidio
                    descripcion, 
                    presupuestoTmp.getId().toString(), 
                    presupuestoTmp.getCatalogoProducto(), 
                    presupuestoTmp.getId(),
                    null,
                    null,
                    EnumSiNo.NO,
                    getTipoDocumentoEnumSeleccionado(),
                    BigDecimal.ZERO);
            
            
            
            this.interfaz.setFacturaDetalleSeleccionado(facturaDetalle);
            setearValoresProducto(facturaDetalle);
            if(agregarAutomaticamente)
            {
                try {
                    //DocumentoEnum documentoSeleccionado=(DocumentoEnum) getCmbDocumento().getSelectedItem();
                    DocumentoEnum documentoSeleccionado=interfaz.obtenerDocumentoSeleccionado();
                    agregarDetallesFactura(facturaDetalle,null, documentoSeleccionado, null,null,null);
                    
                    //Cargar los productos adicionales para la factura
                    for (PresupuestoDetalle presupuestoDetalle : presupuestoTmp.getPresupuestoDetalles()) 
                    {
                        Producto productoSeleccionado=presupuestoDetalle.getProducto();
                        
                        //TODO: Ver si puedo unir esta parte con el metodo que ya existe para poder agregar los detalles unitarios
                        facturaDetalle = crearFacturaDetalle
                        (
                                presupuestoDetalle.getCantidad(),
                                //productoSeleccionado.getValorUnitario(),
                                presupuestoDetalle.getPrecioCompra(),
                                productoSeleccionado.getPrecioSinSubsidio(),
                                productoSeleccionado.getNombre(),
                                productoSeleccionado.getCodigoPersonalizado(),
                                productoSeleccionado.getCatalogoProducto(),
                                productoSeleccionado.getIdProducto(),
                                null, //Verificar este tema cuando vaya a trabar con varios LOTES
                                null,
                                presupuestoDetalle.getReservadoEnum(),
                                TipoDocumentoEnum.INVENTARIO,
                                BigDecimal.ZERO
                        );
                        
                        Kardex kardexSeleccionado= interfaz.obtenerKardexDesdeProducto(productoSeleccionado);
                        
                        if(kardexSeleccionado!=null)
                        {
                            facturaDetalle.setKardexId(kardexSeleccionado.getId());
                        }
                        
                        this.setTipoDocumentoEnumSeleccionado(TipoDocumentoEnum.INVENTARIO);
                        this.interfaz.setFacturaDetalleSeleccionado(facturaDetalle);
                        setearValoresProducto(facturaDetalle);
                        agregarDetallesFactura(facturaDetalle,null,documentoSeleccionado, null,facturaDetalle.getReservadoEnum(),facturaDetalle.getCantidad());
                    }
                    
                } catch (ServicioCodefacException ex) {
                    DialogoCodefac.mensaje(new CodefacMsj(ex.getMessage(), CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
                    Logger.getLogger(FacturaModelControlador.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            //controlador.setearValoresProducto(presupuestoSeleccionado.getTotalVenta(),descripcion,presupuestoSeleccionado.getId().toString(),presupuestoSeleccionado.getCatalogoProducto());
            for (PersonaEstablecimiento establecimiento : presupuestoTmp.getPersona().getEstablecimientos()) {
                interfaz.cargarCliente(establecimiento);
                break;
            }
            
            if(objetoMantenimiento!=null)
            {
                Factura factura=interfaz.obtenerFactura();
                factura.addDatoAdicional(new FacturaAdicional(DatosAdicionalesComprobanteEnum.VEHICULO.getNombre(), objetoVehiculoTxt,ComprobanteAdicional.Tipo.TIPO_OTRO));
                factura.addDatoAdicional(new FacturaAdicional(DatosAdicionalesComprobanteEnum.KILOMETRAJE.getNombre(), kilometrajeTxt,ComprobanteAdicional.Tipo.TIPO_OTRO));
                factura.addDatoAdicional(new FacturaAdicional(DatosAdicionalesComprobanteEnum.ORDEN_TRABAJO.getNombre(), presupuestoTmp.getOrdenTrabajoDetalle().getOrdenTrabajo().getId()+"",ComprobanteAdicional.Tipo.TIPO_OTRO));                
                
                //Factura facturaTmp= interfaz.obtenerFactura();
                //System.out.println(facturaTmp.getDatosAdicionales().size());
            }
            
            return facturaDetalle;            
        }
        return null;
    }
    
    public List<BigDecimal> consultarDescuentoPorProducto(Producto producto,Integer numeroPrecio)
    {
        try {
            return ServiceFactory.getFactory().getDescuentoSeviceIf().consultarDescuentosPorProducto(producto, numeroPrecio);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(FacturaModelControlador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(FacturaModelControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList<BigDecimal>();
    }
    
    
    
    /**
     * TODO: Ver si en este mismo metodo se puede hacer para tener seleccionado el kardex del producto para no tener en el modelo de la vista
     * @param productoSeleccionado
     * @param lote
     * @param stock 
     */
    public void agregarProductoVista(Producto productoSeleccionado,Lote lote,KardexItemEspecifico itemEspecifico,BigDecimal stock,BigDecimal costo,java.sql.Date fechaCaducidad) {
        if (productoSeleccionado == null) {
            return;
        }
        verificarProductoConNotaVentaInterna(productoSeleccionado);
        //this.productoSeleccionado=productoSeleccionado;
        interfaz.setProductoSeleccionado(productoSeleccionado);
        
        //cargarPrecios(productoSeleccionado);
        interfaz.cargarPrecios(productoSeleccionado);
        interfaz.cargarPreciosPorcentaje(consultarDescuentoPorProducto(productoSeleccionado,1));
        interfaz.cargarPresentaciones(productoSeleccionado);
        if(stock!=null)
        {
            stock=stock.setScale(2, RoundingMode.HALF_UP);
        }
        interfaz.cargarEtiquetaStock(stock);
        
        //cargar datos del costo
        interfaz.setearCostoDetalleTxt("");
        if(costo!=null)
        {
            try {
                if(ParametroUtilidades.comparar(session.getEmpresa(),ParametroCodefac.MOSTRAR_COSTO_CON_IVA, EnumSiNo.SI))
                {
                    //String tarifaStr=ParametrosSistemaCodefac.IVA_DEFECTO;
                    //String tarifaStr=ParametrosSistemaCodefac.IVA_DEFECTO;
                    costo= UtilidadesImpuestos.agregarValorIva(ParametrosSistemaCodefac.obtenerIvaDefecto(), costo);
                    interfaz.setearCostoDetalleTxt(costo+"");
                }
                else
                {
                    interfaz.setearCostoDetalleTxt(costo+"");
                }
            } catch (RemoteException ex) {
                Logger.getLogger(FacturaModelControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        interfaz.setearFechaCaducidadTxt("");
        if(fechaCaducidad!=null)
        {
            interfaz.setearFechaCaducidadTxt(fechaCaducidad+"");
        }
        
        
        String descripcion=productoSeleccionado.getNombre();
        descripcion+=(productoSeleccionado.getCaracteristicas()!=null)?" "+productoSeleccionado.getCaracteristicas():"";
        //descripcion=descripcion.replace("\n"," ");
        
        FacturaDetalle facturaDetalle=crearFacturaDetalle(
                BigDecimal.ONE,
                productoSeleccionado.getValorUnitario(), 
                productoSeleccionado.getPrecioSinSubsidio(),
                descripcion, 
                productoSeleccionado.getCodigoPersonalizado(), 
                productoSeleccionado.getCatalogoProducto(), 
                productoSeleccionado.getIdProducto(), 
                (lote!=null)?lote:null,
                itemEspecifico,
                EnumSiNo.NO,
                interfaz.obtenerTipoDocumentoSeleccionado(),
                BigDecimal.ZERO);
        
        //interfaz.setearValoresProducto(productoSeleccionado.getValorUnitario(),descripcion,productoSeleccionado.getCodigoPersonalizado(),productoSeleccionado.getCatalogoProducto());
        interfaz.setFacturaDetalleSeleccionado(facturaDetalle);
        setearValoresProducto(facturaDetalle);
    }
    
    public FacturaDetalle crearFacturaDetalle(
            BigDecimal cantidad,
            BigDecimal valorUnitario,
            BigDecimal valorSinSubsidio,
            String descripcion,
            String codigo,
            CatalogoProducto catalogoProducto,
            Long referenciaId,  
            Lote lote,
            KardexItemEspecifico kardexItemEspecifico,
            EnumSiNo reservaEnumSiNo,
            TipoDocumentoEnum tipoDocumentoReferencia,
            BigDecimal descuento)
    {
        FacturaDetalle facturaDetalle=new FacturaDetalle();
        facturaDetalle.setCantidad(cantidad);
        facturaDetalle.setDescripcion(descripcion);
        facturaDetalle.setDescuento(descuento);
        facturaDetalle.setPrecioUnitario(valorUnitario);
        facturaDetalle.setReferenciaId(referenciaId);
        facturaDetalle.setLote(lote);
        facturaDetalle.setKardexItemEspecifico(kardexItemEspecifico);
        facturaDetalle.setCatalogoProducto(catalogoProducto);
        facturaDetalle.setCodigoPrincipal(codigo);
        facturaDetalle.setTipoDocumentoEnum(tipoDocumentoReferencia);
        facturaDetalle.setPrecioSinSubsidio(valorSinSubsidio);
        facturaDetalle.setReservadoEnum(reservaEnumSiNo);
        
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
    
    public FacturaDetalle actualizarFacturaDetalleCatalogo(FacturaDetalle facturaDetalle)
    {
        CatalogoProducto catalogoProducto=facturaDetalle.getCatalogoProducto();
        
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
    public void verificarProductoConNotaVentaInterna(Producto producto)
    {
        /*DocumentoEnum documentoEnum=interfaz.obtenerDocumentoSeleccionado() ;
        BigDecimal valorUnitario=producto.getValorUnitario();
        
        Boolean agregarIva=false;
        EnumSiNo agregarIvaSiNo=ParametroUtilidades.obtenerValorParametroEnum(session.getEmpresa(),ParametroCodefac.NOTA_VENTA_INTERNA_IVA, EnumSiNo.SI);
        if(agregarIvaSiNo!=null)
        {
            agregarIva=agregarIvaSiNo.getBool();
        }
        
        if(documentoEnum.equals(DocumentoEnum.NOTA_VENTA_INTERNA) && !agregarIva)        
        {*/
            /**
             * Si el producto tiene ice calculo el nuevo subtotal
             */
        /*    CatalogoProducto catalogoProducto=producto.getCatalogoProducto();
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
        }*/
        SetearDatosNVI interfazSetearDatos=new SetearDatosNVI() {
            @Override
            public void setValores(BigDecimal valorUnitario, BigDecimal iva) {
                producto.setValorUnitario(valorUnitario);
            }
        };
        verificarProductoConNotaVentaInternaGenerico(producto.getCatalogoProducto(), producto.getValorUnitario(),interfazSetearDatos);
        //producto.setValorUnitario(valorUnitario);
    
    }
    public void verificarFacturaConNotaVentaInterna(Factura factura)
    {
        for (FacturaDetalle detalle : factura.getDetalles()) 
        {
            verificarFacturaDetalleConNotaVentaInterna(detalle);
        }
        factura.calcularTotalesDesdeDetalles();
    }
    
    public void verificarFacturaDetalleConNotaVentaInterna(FacturaDetalle facturaDetalle)
    {
        SetearDatosNVI interfazSetearDatos=new SetearDatosNVI() {
            @Override
            public void setValores(BigDecimal valorUnitario, BigDecimal iva) {
                facturaDetalle.setPrecioUnitario(valorUnitario);
                facturaDetalle.setIva(iva);
                facturaDetalle.setIvaPorcentaje(0);
            }
        };
        verificarProductoConNotaVentaInternaGenerico(facturaDetalle.getCatalogoProducto(), facturaDetalle.getPrecioUnitario(),interfazSetearDatos);
        //facturaDetalle.setPrecioUnitario(valorUnitario);
        facturaDetalle.calcularTotalesDetallesFactura();
    }
    
    private void verificarProductoConNotaVentaInternaGenerico(CatalogoProducto catalogoProducto,BigDecimal valorUnitario,SetearDatosNVI setearDatosNVI)
    {
        DocumentoEnum documentoEnum=interfaz.obtenerDocumentoSeleccionado() ;
        //BigDecimal valorUnitario=producto.getValorUnitario();
        
        Boolean agregarIva=false;
        EnumSiNo agregarIvaSiNo=ParametroUtilidades.obtenerValorParametroEnum(session.getEmpresa(),ParametroCodefac.NOTA_VENTA_INTERNA_IVA, EnumSiNo.SI);
        if(agregarIvaSiNo!=null)
        {
            agregarIva=agregarIvaSiNo.getBool();
        }
        
        if(documentoEnum.equals(DocumentoEnum.NOTA_VENTA_INTERNA) && !agregarIva)        
        {
            /**
             * Si el producto tiene ice calculo el nuevo subtotal
             */
            //CatalogoProducto catalogoProducto=producto.getCatalogoProducto();
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
                try {
                    catalogoProducto.getIva().setTarifa(0);
                    catalogoProducto.getIva().setPorcentaje(BigDecimal.ZERO);
                    //producto.getCatalogoProducto().getIva().setTarifa(0);
                    //producto.getCatalogoProducto().getIva().setPorcentaje(BigDecimal.ZERO);
                    
                    BigDecimal nuevoValorUnitario=UtilidadesImpuestos.agregarValorIva(session.obtenerIvaActual(),valorUnitario);
                    //Por defecto si no tiene configurada ninguna opcion asume que el valor de la nota de venta interna debe ser con el valor
                    if(ParametroUtilidades.comparar(session.getEmpresa(),ParametroCodefac.NVI_TOTAL_CON_IVA,EnumSiNo.NO))
                    {
                        nuevoValorUnitario=valorUnitario;
                    }                    
                    //producto.setValorUnitario(nuevoValorUnitario);
                    if(setearDatosNVI!=null)
                    {
                        setearDatosNVI.setValores(nuevoValorUnitario,BigDecimal.ZERO);
                        
                    }
                    //return nuevoValorUnitario;
                } catch (RemoteException ex) {
                    Logger.getLogger(FacturaModelControlador.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        //return valorUnitario;
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
            //getCmbIva().setEnabled(true);
            cargarPrecioUnitario(facturaDetalle.getIcePorcentaje(),facturaDetalle.getPrecioUnitario());
            //facturaDetalle.getPrecioUnitario()
           
            
        }
    }
    
    public void cargarPrecioUnitario(BigDecimal icePorcentaje,BigDecimal precioUnitario)
    {
        try {
            interfaz.habilitarComboIva(true);
            
            //TODO: Ver alguna forma de cargar por defecto el precio guardado en la base de datos
            if (ParametroUtilidades.comparar(session.getEmpresa(), ParametroCodefac.CARGAR_PRODUCTO_IVA_FACTURA, EnumSiNo.SI)) {
                //getCmbIva().setSelectedItem(EnumSiNo.SI);
                interfaz.setComboIva(EnumSiNo.SI);
                //BigDecimal porcentajeIce = (facturaDetalle.getIcePorcentaje() != null) ? facturaDetalle.getIcePorcentaje() : null;
                BigDecimal porcentajeIce = (icePorcentaje != null) ? icePorcentaje : null;
                BigDecimal valorConIva = UtilidadIva.calcularValorConIvaIncluido(
                        session.obtenerIvaActualDecimal(),
                        porcentajeIce,
                        precioUnitario);
                //getTxtValorUnitario().setText(valorConIva.toString());
                interfaz.setTxtValorUnitario(valorConIva.toString());
            } else {
                //getCmbIva().setSelectedItem(EnumSiNo.NO);
                interfaz.setComboIva(EnumSiNo.NO);
                interfaz.setTxtValorUnitario(precioUnitario.toString());
            }
        } catch (RemoteException ex) {
            Logger.getLogger(FacturaModelControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    /**
     * @author Carlos
     * Validacion de la la logica dependiendo el modulo
     * @return 
     */
    private boolean validacionPersonalizadaPorModulos(FacturaDetalle facturaDetalle,Kardex kardex,DocumentoEnum documentoEnum) {
        
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
                return validarAgregarInventario(facturaDetalle,kardex,documentoEnum); //Metodo que se encarga de validar el inventario
                
        }
        
        return true;
    }
    
    private boolean validarAgregarInventario(FacturaDetalle facturaDetalle,Kardex kardex,DocumentoEnum documentoEnum)
    {
        try {
            
            if(ParametroUtilidades.comparar(session.getEmpresa(), ParametroCodefac.FACTURAR_INVENTARIO_NEGATIVO, EnumSiNo.NO))
            {
                try {                    
                    //Verifico si el producto es inventario y esta activo la opción de construir ensamble en la venta porque en ese caso
                    //tampoco debe validar el inventario en la vista para el ensamble
                    //Validacion para saber si se debe validar en la vista o no los productos cuando vienen desde inventario
                    ReferenciaDetalleFacturaRespuesta referenciaDetalle=ServiceFactory.getFactory().getFacturacionServiceIf().obtenerReferenciaDetalleFactura(facturaDetalle.getTipoDocumentoEnum(),facturaDetalle.getReferenciaId());
                    if(referenciaDetalle.tipoDocumentoEnum.equals(TipoDocumentoEnum.INVENTARIO))
                    {
                        Producto producto=(Producto) referenciaDetalle.objecto;
                        if(producto.getTipoProductoEnum().equals(TipoProductoEnum.EMSAMBLE) && ParametroUtilidades.comparar(session.getEmpresa(), ParametroCodefac.CONSTRUIR_ENSAMBLES_FACTURAR, EnumSiNo.SI))
                        {
                             //Si tengo que construir el ensamble no valido en la vista porque puede tener stock insuficiente pero despues de construir si puede generar
                            return true;
                        }
                    }
                    
                    //Solo hago la validacion del inventario cuando es un producto diferente de proforma , por que para ese tema no deberia importar
                    if(!documentoEnum.equals(DocumentoEnum.PROFORMA))
                    {                    
                        boolean verifadorStock = verificarExistenciaStockProducto(facturaDetalle,kardex);
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
    public boolean verificarExistenciaStockProducto(FacturaDetalle facturaDetalle,Kardex kardex) throws RemoteException, ServicioCodefacException
    {
        boolean verificadorStock;
        KardexServiceIf serviceKardex = ServiceFactory.getFactory().getKardexServiceIf();
        //Bodega activa de venta
        //BodegaServiceIf serviceBodega = ServiceFactory.getFactory().getBodegaServiceIf();
        //Bodega bodegaVenta = serviceBodega.obtenerBodegaVenta(session.getSucursal());
        //Verifica si existe stock para el producto seleccionado
        ProductoServiceIf productoServiceIf=ServiceFactory.getFactory().getProductoServiceIf();
        Producto producto=productoServiceIf.buscarPorId(facturaDetalle.getReferenciaId());
        BigDecimal cantidadPresentacion=BigDecimal.ONE;
        ProductoPresentacionDetalle presentacionDetalle=  producto.buscarPresentacionDetalleProducto();
        if(presentacionDetalle!=null)
        {
            cantidadPresentacion=presentacionDetalle.getCantidad();
        }
        
        //verificadorStock = serviceKardex.obtenerSiNoExisteStockProducto(bodegaVenta,interfaz.obtenerProductoSeleccionado(), facturaDetalle.getCantidad().intValue());
        //verificadorStock = serviceKardex.obtenerSiNoExisteStockProducto(bodegaVenta,producto, facturaDetalle.getCantidad());
        if(facturaDetalle.getCantidad().multiply(cantidadPresentacion).compareTo(kardex.getStock())<=0)
        {
            verificadorStock=true;
        }
        else
        {
            verificadorStock=false;
        }
        
        return verificadorStock;
    }
    
    public void agregarDetallesFacturaValidacion(FacturaDetalle facturaDetalle,BigDecimal precioVentaOriginal) throws ServicioCodefacException
    {
        try {
            if(precioVentaOriginal!=null)
            {
                if(ParametroUtilidades.comparar(session.getEmpresa(),ParametroCodefac.MODIFICAR_PRECIO_MENOR,EnumSiNo.NO))
                {
                    if(facturaDetalle.getPrecioUnitario().compareTo(precioVentaOriginal)<0)
                    {
                        throw new ServicioCodefacException("No se puede ingresar un valor menor al precio de venta");
                    }
                }
            }
        } catch (RemoteException ex) {
            Logger.getLogger(FacturaModelControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * TODO: VER SIS ESTE METODO SE PUEDE UNIR CON EL DE ABAJO PORQUE EISTE 2 SIMILARES !IMPORTANTE!
     * @param facturaDetalle
     * @return 
     */
    public boolean agregarDetallesFactura(FacturaDetalle facturaDetalle,BigDecimal precioVentaOriginal,DocumentoEnum documentoEnum,Kardex kardex,EnumSiNo reservaEnum,BigDecimal cantidad) throws ServicioCodefacException {

        
        agregarDetallesFacturaValidacion(facturaDetalle, precioVentaOriginal);
        
        //Validacion de los datos ingresados para ver si puedo agregar al detalle
        if (!interfaz.validarIngresoDetalle()) 
        {
            int filaSeleccionada=interfaz.filaSeleccionadaTablaDetalle();
            interfaz.cargarDatosDetalles(); //Si no se pudo editar vuelvo a cargar los detalles si se modifico desde la tabla para que quede la forma original
            interfaz.seleccionarFilaTablaDetalle(filaSeleccionada);
            return false;
        }
        
        //TODO: Por el momento cuando no encuentra un kardex intento buscar, pero parece que este paso esta redundado
        //if(kardex==null)
        //{
        //    interfaz.obtenerKardexDesdeProducto(facturaDetalle.get);
        //}
        
        //Cuando tiene seleccionado una kardex mando el valor del lote
        Lote lote=null;
        if(kardex!=null)
        {
            lote=kardex.getLote();
        }
        
        
        facturaDetalle.setLote((lote!=null)?lote:null);
        
        //Solo leer la cantidad de la pantalla si no estan enviando una cantidad
        if(cantidad==null)
        {
            facturaDetalle.setCantidad(new BigDecimal(interfaz.obtenerTxtCantidad()));   
        }
        else
        {
            facturaDetalle.setCantidad(cantidad);
        }
        //facturaDetalle.setReservadoEnum(EnumSiNo.getEnumByBoolean(interfaz.obtenerChkReservado()));
        facturaDetalle.setReservadoEnum(reservaEnum);
        //facturaDetalle.setKardexId(interfaz.obtenerKardexId());
        //facturaDetalle.setKardexId((kardex!=null)?kardex.getId():null);
        if(kardex!=null)
        {
            facturaDetalle.setKardexId(kardex.getId());
        }
        //Validacion personalizada dependiendo de la logica de cada tipo de documento
        if (!validacionPersonalizadaPorModulos(facturaDetalle,kardex,documentoEnum)) {
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
        ParametroCodefac parametroIva=session.getParametrosCodefac().get(ParametroCodefac.IVA_DEFECTO);
        if(parametroIva==null)
        {
            throw new ServicioCodefacException("Falta configurar el IVA por DEFECTO");
        }
        
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
        
        validacionDescuentoMenorQueElCosto(facturaDetalle, kardex);
        
        //calcularTotalesDetalles(facturaDetalle);
        facturaDetalle.calcularTotalesDetallesFactura();
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
    
    private void validacionDescuentoMenorQueElCosto(FacturaDetalle facturaDetalle,Kardex kardex) throws ServicioCodefacException
    {
        try {
            if(ParametroUtilidades.comparar(session.getEmpresa(),ParametroCodefac.PERMITIR_DESCUENTO_MENOR_COSTO,EnumSiNo.NO))
            {
                if(kardex!=null)
                {
                    BigDecimal ultimoCosto=kardex.getPrecioUltimo();
                    if(ultimoCosto!=null && ultimoCosto.compareTo(BigDecimal.ZERO)!=0 )
                    {

                        BigDecimal subtotalMenosDescuento = facturaDetalle.getSubtotalRestadoDescuentos();
                        System.out.println(subtotalMenosDescuento + " < " + ultimoCosto);
                        
                        //Si tiene configurada la opcion de margen minimo de venta, le aumento el porcentaje minimo al ultimo costo antes de comparar
                        String margenMinimoStr=ParametroUtilidades.obtenerValorParametro(session.getEmpresa(),ParametroCodefac.MARGEN_MINIMO_DESCUENTO_VENTA);
                        if(!UtilidadesTextos.verificarNullOVacio(margenMinimoStr))
                        {
                            BigDecimal margenMinimo=new BigDecimal(margenMinimoStr);
                            BigDecimal valorAdicional=UtilidadesPorcentajes.calcularPorcentaje(margenMinimo,ultimoCosto);                            
                            ultimoCosto=ultimoCosto.add(valorAdicional);
                        }
                        
                        if (subtotalMenosDescuento.compareTo(ultimoCosto) < 0) {
                            throw new ServicioCodefacException("El precio no puede ser menor que " + ultimoCosto);
                        }
                    }
                }

            }            
            
        } catch (RemoteException ex) {
            Logger.getLogger(FacturaModelControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
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
       
        //Solo activar el calculo de las formas de pago cuando aun no tiene grabado una factura
        if(factura.getId()==null)
        {
            this.cargarFormaPago();
        }
        //agregarFormaPagoConCartera();
        interfaz.cargarFormasPagoTabla();

    }
    
    /**
     * TODO: Cambiar el nombre de la funcion , por que es confuso por que este metodo permite MODIFICAR LA FORMA DE PAGO
     * Este metodo solo sirve cuando estoy haciendo alguna modificacion en la factura para que se ponga de forma automatico las formas de pago, no se deberia usar para cuando se quiere solo consultar la factura
     */
    public void cargarFormaPago()
    {
        Factura factura=interfaz.obtenerFactura();
                
        //Si no existe NINGUN VALOR limpio todas las formas de pago
        if(factura.getTotal().compareTo(BigDecimal.ZERO)==0)
        {
            if(factura.getFormaPagos()!=null)
            {
                factura.getFormaPagos().clear();
            }
            return;
        }
        
        //EDITA o CREA la forma de pago
        if(factura.getFormaPagos()==null || factura.getFormaPagos().size()==0)
        {
            
            FormaPago formaPago = new FormaPago();
            formaPago.setPlazo(0);
            formaPago.setTotal(factura.getTotal());
            formaPago.setUnidadTiempo(FormaPago.UnidadTiempoEnum.NINGUNO.getNombre());

            //Si no existe una forma de cliente asignada , agregao la FORMA DE PAGO DEFECTO del sistema
            if (factura.getCliente()==null || factura.getCliente().getSriFormaPago() == null) 
            {
                formaPago.setSriFormaPago(formaPagoDefecto);
            } 
            else 
            {
                formaPago.setSriFormaPago(factura.getCliente().getSriFormaPago());
            }
            
            factura.addFormaPago(formaPago);
            agregarFormaPagoConCartera();
            //Actualizar la forma de pago en las vistas
            interfaz.cargarFormasPagoTabla();            
        }
        else
        {
            //Caso cuando tiene una sola forma de pago
            if (factura.getFormaPagos().size() == 1)
            {
                FormaPago formaPago = factura.getFormaPagos().get(0);
                formaPago.setTotal(factura.getTotal());
                
                //TODO: Optimizar para que se cambie la forma de pago solo si es un cliente distinto
                if (factura.getCliente()!=null && factura.getCliente().getSriFormaPago() != null) 
                {
                    //Cambia de forma de pago si cambia de cliente
                    formaPago.setSriFormaPago(factura.getCliente().getSriFormaPago());
                } 
                else //Si no esta grabado una forma de pago en el cliente asigno a forma de pago por defecto de las configuraciones
                {
                    if (formaPagoDefecto != null) 
                    {
                        formaPago.setSriFormaPago(formaPagoDefecto);
                    }
                }
                
                agregarFormaPagoConCartera();
                interfaz.cargarFormasPagoTabla();
            }
            else if(factura.getFormaPagos().size()>1)
            {
                //TODO: Si exsiten varias formas de pago transformo en una sola forma de pago 
                //TODO: buscar una manera que esto luego funcione cuando tengo varias formas de pago asignado y tomar en cuenta la cartera
                FormaPago formPagoDefecto=factura.buscarFormaPagoDistintaDeCartera();
                factura.getFormaPagos().clear();
                factura.addFormaPago(formPagoDefecto);
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
            
            BigDecimal saldoDisponibleCartera=ServiceFactory.getFactory().getCarteraServiceIf().obtenerSaldoDisponibleCruzar(factura.getCliente(),session.getEmpresa(),interfaz.getEStudiante());
            
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
        interfaz.cargarEtiquetaStock(BigDecimal.ZERO);
        interfaz.limpiarComboPresentaciones();
    }

    @Override
    public ComprobanteAdicional crearComprobanteAdicional(String correo, ComprobanteAdicional.Tipo tipoCorreo, ComprobanteAdicional.CampoDefectoEnum campoDefecto) {
        return new FacturaAdicional(correo, tipoCorreo, ComprobanteAdicional.CampoDefectoEnum.CORREO);
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

    public List<Mesa> getMesaList() {
        return mesaList;
    }

    public void setMesaList(List<Mesa> mesaList) {
        this.mesaList = mesaList;
    }
    
    
    

    public void iniciar() {
        try {
            tipoDocumentoList= TipoDocumentoEnum.obtenerTipoDocumentoPorModulo(ModuloCodefacEnum.FACTURACION,session.getModulos());
            
            if(tipoDocumentoList.size()==0)
            {
                mostrarMensaje(new CodefacMsj("No tiene disponible ningun modo para facturar", CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
            }
            
            //Quitar documentos que no se necesitan mostrar
            //TODO: Revisar por que estaba saliendo este dato adicional
            tipoDocumentoList.remove(TipoDocumentoEnum.VENTA);
            
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
    public static void imprimirComprobanteVenta(Factura facturaProcesando,String nombre,Boolean activarConfiguracionesImpresion,SessionCodefacInterface session,InterfazComunicacionPanel panelPadre,GeneralPanelInterface.EstadoFormularioEnum formEnum) 
    {
        //Revisar el nombre del comproante si tiene un alias
        if(facturaProcesando.getCodigoDocumentoEnum().equals(DocumentoEnum.NOTA_VENTA_INTERNA))
        {
            String aliasNotaVenta=ParametroUtilidades.obtenerValorParametro(session.getEmpresa(),ParametroCodefac.AliasNombresDocumentos.NOTA_VENTA_INTERNA_ALIAS);
            if(!UtilidadesTextos.verificarNullOVacio(aliasNotaVenta))
            {
                nombre=aliasNotaVenta;
            }
        }
        
        FormatoReporteEnum tipoReporteEnum=ParametroUtilidades.obtenerValorParametroEnum(session.getEmpresa(),ParametroCodefac.REPORTE_DEFECTO_VENTA, FormatoReporteEnum.A2);
        
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
        FormatoHojaEnum formatoEnum=FormatoHojaEnum.A5;
        
        //OrientacionReporteEnum
        //TODO: Mejorarar esta parte para solo cargar en horizontal
        String parametroOrientacion = ParametroUtilidades.obtenerValorParametro(session.getEmpresa(), ParametroCodefac.REPORTE_ORIENTACION_VENTA);
        if(!UtilidadesTextos.verificarNullOVacio(parametroOrientacion))
        {
            OrientacionReporteEnum orientacionEnum=OrientacionReporteEnum.buscarPorLetra(parametroOrientacion);
            if(orientacionEnum!=null && orientacionEnum.equals(OrientacionReporteEnum.HORIZONTAL))
            {
                nombreReporte="comprobante_venta_A5_horizontal.jrxml";
                formatoEnum=FormatoHojaEnum.A4;
            }
        }
        
        //TODO: Ver si esta parte se puede mejorar para imprimir
        ParametroCodefac parametroCodefac = session.getParametrosCodefac().get(ParametroCodefac.IMPRESORA_TICKETS_VENTAS);
        
        
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
        
        //Imprimir solo cuando esta grabar la primera de forma automaticamente
        ImpresionAutomaticaEnum impresionAutomaticaEnum=null;
        if(formEnum.equals(GeneralPanelInterface.EstadoFormularioEnum.GRABAR))
        {
            impresionAutomaticaEnum=ImpresionAutomaticaEnum.VENTA;
        }
        //ReporteCodefac.generarReporteInternalFramePlantilla(parametro, mapParametros, dataReporte, this.panelPadre, "Comprobante de Venta ", OrientacionReporteEnum.VERTICAL,formatoEnum);
        ReporteCodefac.generarReporteInternalFramePlantilla(RecursoCodefac.JASPER_FACTURACION,nombreReporte, mapParametros, dataReporte, panelPadre, nombre, OrientacionReporteEnum.VERTICAL,formatoEnum,configuracion,impresionAutomaticaEnum);

    }
    
    /**
     * Actualmente el metodo solo sirve para agregar productos
     * @param codigoDetalle
     * @param tipoDocumentoEnum
     * @param empresa
     * @return 
     */
    public static Producto listenerBuscarProducto(String codigoDetalle,TipoDocumentoEnum tipoDocumentoEnum,Empresa empresa)
    {
        //Solo validar si existe datos ingresados en el combo
        if (codigoDetalle.trim().equals("")) {
            return null;
        }
        
        
            try {
                //TipoDocumentoEnum tipoDocumentoEnum = controlador.getTipoDocumentoEnumSeleccionado();

                switch (tipoDocumentoEnum) {
                    case ACADEMICO:
                        //agregarRubroAcademico();
                        break;
                    case PRESUPUESTOS:
                        //agregarPresupuesto();
                        break;
                    case INVENTARIO:
                    case LIBRE:

                        //Map<String,Object> mapParametros=new HashMap<String,Object>();
                        //mapParametros.put("codigoPersonalizado", getTxtCodigoDetalle().getText()); //TODO: VER COMO MANEJAR TODOS LOS TIPOS DE CODIGO, VER UNA OPCION DE PARAMETRIZAR POR QUE CODIGO SE QUIERE TRABAJAR
                        //List<Producto> productos=ServiceFactory.getFactory().getProductoServiceIf().buscarProductoActivoPorCodigo(getTxtCodigoDetalle().getText(),session.getEmpresa());
                        Producto producto = ServiceFactory.getFactory().getProductoServiceIf().buscarProductoActivoPorCodigo(codigoDetalle, empresa);
                        return producto;

                }
            } catch (RemoteException ex) {
                Logger.getLogger(FacturaModelControlador.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ServicioCodefacException ex) {
                Logger.getLogger(FacturaModelControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        return null;
    
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
                        FormatoReporteEnum tipoReporteEnum=ParametroUtilidades.obtenerValorParametroEnum(session.getEmpresa(),ParametroCodefac.REPORTE_DEFECTO_VENTA, FormatoReporteEnum.A2);
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
            mapParametros.put("descuento", facturaProcesando.getDescuentoImpuestos().add(facturaProcesando.getDescuentoSinImpuestos())+"");
            mapParametros.put("mesero", facturaProcesando.getUsuario().getNick());
            mapParametros.put("mesa",(facturaProcesando.getMesa()!=null)?facturaProcesando.getMesa().toString():"");
            
            String leyendaAdicional= ParametroUtilidades.obtenerValorParametro(facturaProcesando.getEmpresa(),ParametroCodefac.LEYENDA_ADICIONAL_COMPROBANTE);
            if(!UtilidadesTextos.verificarNullOVacio(leyendaAdicional))
            {
                mapParametros.put("leyenda_documento",leyendaAdicional);
            }
            
            //Agregar datos adicionales para imprimir en el ticket
            List<InformacionAdicional> datoAdicionalList=new ArrayList<InformacionAdicional>();
            
            for (FacturaAdicional facturaAdicional : facturaProcesando.getDatosAdicionalesComprobante()) {
            InformacionAdicional informacionAdicional = new InformacionAdicional();
            informacionAdicional.setNombre(facturaAdicional.getCampo());
            informacionAdicional.setValor(facturaAdicional.getValor());

            datoAdicionalList.add(informacionAdicional);
            }

            //Agregar datos adicionales para facturas electrónicas
            agregarDatosFacturacionElectronica(facturaProcesando, datoAdicionalList);
           
            
            mapParametros.put("informacionAdicionalList", datoAdicionalList);
            
        try 
        {
            //Agregar el jasper del reporte adicional en le map
            RecursosServiceIf service= ServiceFactory.getFactory().getRecursosServiceIf();
            InputStream inputStream;
            inputStream = RemoteInputStreamClient.wrap(service.getResourceInputStream(RecursoCodefac.JASPER_COMPROBANTES_ELECTRONICOS,"datos_adicional_ticket.jrxml"));
            
            JasperReport reportDatosAdicionales = JasperCompileManager.compileReport(inputStream);
            mapParametros.put("pl_url_datos_adicionales",reportDatosAdicionales);
            
        } catch (RemoteException ex) {
            Logger.getLogger(FacturaModelControlador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FacturaModelControlador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            Logger.getLogger(FacturaModelControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            return mapParametros;
            
    }
    
    private static void agregarDatosFacturacionElectronica(Factura factura,List<InformacionAdicional> datoAdicionalList)
    {
        //Si el documento es una nota de venta no tengo en cuenta los datos para las facturas
        if(factura.getCodigoDocumentoEnum().equals(DocumentoEnum.NOTA_VENTA_INTERNA))
        {
            return ;
        }
        
        if(factura.getTipoFacturacionEnum()!=null && factura.getTipoFacturacionEnum().equals(ComprobanteEntity.TipoEmisionEnum.ELECTRONICA))
        {                       
            InformacionAdicional infoAdicional=new InformacionAdicional("Clave de Acceso",factura.getClaveAcceso());
            datoAdicionalList.add(0,infoAdicional);
            
            String modoFacturacion= ParametroUtilidades.obtenerValorParametro(factura.getEmpresa(),ParametroCodefac.MODO_FACTURACION);            
            infoAdicional=new InformacionAdicional("Ambiente",modoFacturacion);
            datoAdicionalList.add(0,infoAdicional);
            

        }
    }
    
    private static List<InformacionAdicionalData> obtenerDatosAdicionales(Factura factura)
    {
        
        List<InformacionAdicionalData> datosAdicionalesData=new ArrayList<InformacionAdicionalData>();
        if(factura.getDatosAdicionales()!=null)
        {          
            for (FacturaAdicional datoAdicional : factura.getDatosAdicionales()) 
            {
                InformacionAdicionalData data=new InformacionAdicionalData();
                data.setNombre(datoAdicional.getCampo());
                data.setValor(datoAdicional.getValor());
                datosAdicionalesData.add(data);
            }
        }
        return datosAdicionalesData;
    }
    
    public static Map<String, Object> getMapParametrosReporteProforma(Factura facturaProcesando) 
    {
        Map<String, Object> mapParametros= FacturaModelControlador.getMapParametrosReporte(facturaProcesando); //To change body of generated methods, choose Tools | Templates.
        //mapParametros.put("estado",factura.getEnumEstadoProforma().getNombre());    
        ComprobanteEntity.ComprobanteEnumEstado estadoEnum= facturaProcesando.getEstadoEnum();
        mapParametros.put("estado",estadoEnum.getNombre());        
        //subtotal_cero
        //Datos adicionales para las proformas
        mapParametros.put("telefono", facturaProcesando.getTelefono());
        mapParametros.put("direccion", facturaProcesando.getDireccion());
        mapParametros.put("secuencial", facturaProcesando.getSecuencial().toString());
        mapParametros.put("cliente_nombres", facturaProcesando.getRazonSocial());
        mapParametros.put("cliente_identificacion", facturaProcesando.getIdentificacion());
        mapParametros.put("fecha_emision", facturaProcesando.getFechaEmisionFormat());
        mapParametros.put("subtotal_cero",facturaProcesando.getSubtotalSinImpuestos().toString());
        mapParametros.put("descuento",facturaProcesando.getDescuentoImpuestos().add(facturaProcesando.getDescuentoSinImpuestos()).toString());
        String porcentajeIva="";
        if(facturaProcesando.getIvaSriId()!=null)
        {
            porcentajeIva=facturaProcesando.getIvaSriId().getPorcentaje().setScale(2).toString();
        }
        
        mapParametros.put("iva_porcentaje",porcentajeIva);
        //mapParametros.put("iva_porcentaje",session.obtenerIvaActual().toString());        
        mapParametros.put("informacionAdicionalList",obtenerDatosAdicionales(facturaProcesando));

        try {
            //TODO: Optimizar guardando en un memoria de cache
            RecursosServiceIf service= ServiceFactory.getFactory().getRecursosServiceIf();
            InputStream inputStream = RemoteInputStreamClient.wrap(service.getResourceInputStream(RecursoCodefac.JASPER_COMPROBANTES_ELECTRONICOS,"datos_adicionalesA4.jrxml"));
            JasperReport reportDatosAdicionales = JasperCompileManager.compileReport(inputStream);
            mapParametros.put("SUBREPORT_INFO_OTRO",reportDatosAdicionales);
            
            inputStream = RemoteInputStreamClient.wrap(service.getResourceInputStream(RecursoCodefac.JASPER_COMPROBANTES_ELECTRONICOS,"datos_adicionales.jrxml"));
            reportDatosAdicionales = JasperCompileManager.compileReport(inputStream);
            mapParametros.put("SUBREPORT_INFO_ADICIONAL",reportDatosAdicionales);
        } catch (RemoteException ex) {
            Logger.getLogger(FacturaModelControlador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FacturaModelControlador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            Logger.getLogger(FacturaModelControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //SUBREPORT_INFO_ADICIONAL
        
        // mapParametros.put("estado",facturaProcesando.getEstadoEnum());
        return mapParametros;
    }
    
    //TODO: poner en otra parte como utilidades
    public static Integer obtenerDecimalesRedondeo(Empresa empresa)
    {
        try {
            return ParametroUtilidades.obtenerValorBaseDatos(empresa
                    , ParametroCodefac.NUMERO_DECIMALES_RIDE, new ParametroUtilidades.ComparadorInterface() {
                @Override
                public Object consultarParametro(String nombreParametro) {
                    return Integer.parseInt(nombreParametro);
                }
            });
        } catch (RemoteException ex) {
            Logger.getLogger(FacturaModelControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    //TODO: poner en otra parte como utilidades
    public static Integer obtenerCantidadProducto(Empresa empresa)
    {
        try {
            return ParametroUtilidades.obtenerValorBaseDatos(empresa
                    , ParametroCodefac.NUMERO_DECIMAL_PRODUCTO, new ParametroUtilidades.ComparadorInterface() {
                @Override
                public Object consultarParametro(String nombreParametro) {
                    return Integer.parseInt(nombreParametro);
                }
            });
        } catch (RemoteException ex) {
            Logger.getLogger(FacturaModelControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
        
    public static List<ComprobanteVentaData> getDetalleDataReporte(Factura facturaProcesando)
    {
        
        List<ComprobanteVentaData> dataReporte = new ArrayList<ComprobanteVentaData>();
        Integer redondedoDecimalesPrecios=obtenerDecimalesRedondeo(facturaProcesando.getEmpresa());
        for (FacturaDetalle detalle : facturaProcesando.getDetalles()) {
            
            ComprobanteVentaData data = new ComprobanteVentaData();
            data.setCantidad(detalle.getCantidad().setScale(2, RoundingMode.HALF_UP).toString());
            data.setCodigo(detalle.getCodigoPrincipal());
            data.setNombre(detalle.getDescripcion().toString());
            data.setIva(detalle.getIvaPorcentaje()+"");
            
            BigDecimal precioUnitario=detalle.getPrecioUnitario();
            BigDecimal total=detalle.getTotal();
            
            //Redondeando decimales cuando este configurado ese modo
            if(redondedoDecimalesPrecios!=null)
            {
                precioUnitario=precioUnitario.setScale(redondedoDecimalesPrecios,BigDecimal.ROUND_UP);
                total=total.setScale(redondedoDecimalesPrecios,BigDecimal.ROUND_UP);
            }                        
            
            data.setPrecioUnitario(precioUnitario.toString());
            data.setTotal(total.toString());
            
            //Datos adicionales para las proformas
            data.setDescuento(detalle.getDescuento().toString());
            data.setDescripcion(detalle.getDescripcion());

            dataReporte.add(data);
        }
        return dataReporte;
    }
    
    public static JasperPrint getReporteTicket(Factura factura,SessionCodefac sessionCodefac)
    {
        Map<String, Object> mapParametros = getMapParametrosReporte(factura);
        List<ComprobanteVentaData> dataReporte = getDetalleDataReporte(factura);
        InputStream path = RecursoCodefac.JASPER_FACTURACION.getResourceInputStream("comprobante_venta_ticket.jrxml");
        
        if(factura.getCodigoDocumentoEnum().equals(DocumentoEnum.COMANDA))
        {
            path=RecursoCodefac.JASPER_FACTURACION.getResourceInputStream("comanda_ticket_40.jrxml");
        }
        
        String nombreReporte = factura.getCodigoDocumentoEnum().getNombre();
        if(factura.getCodigoDocumentoEnum().equals(DocumentoEnum.FACTURA))
        {
            nombreReporte="Nota de Venta";
        }
        JasperPrint jasperPrint = ReporteCodefac.construirReporte(path, mapParametros, dataReporte, sessionCodefac,nombreReporte, OrientacionReporteEnum.VERTICAL, FormatoHojaEnum.TICKET);
        return jasperPrint;
        //UtilidadesReporteWeb.generarReporteHojaNuevaPdf(jasperPrint,factura.getPreimpreso()+".pdf");
    }
    
    public static JasperPrint getReporteJasperProforma(Factura proforma,FacturaModelControlador.FormatoReporteEnum formatoEnum)
    {
        //Formato A4 por defecto para el reporte de proformas
        String nombreReporte="proforma.jrxml";
        FormatoHojaEnum formatoHoja=FormatoHojaEnum.A4;
        
        if(formatoEnum.equals(FacturaModelControlador.FormatoReporteEnum.A4))
        {
            nombreReporte="proforma.jrxml";
            formatoHoja=FormatoHojaEnum.A4;
        }
        else if(formatoEnum.equals(FacturaModelControlador.FormatoReporteEnum.A5))
        {
            nombreReporte="proformaA5.jrxml";
            formatoHoja=FormatoHojaEnum.A5;
        }
        else if (formatoEnum.equals(FacturaModelControlador.FormatoReporteEnum.A6)) 
        {
            nombreReporte = "proformaA6.jrxml";
            formatoHoja = FormatoHojaEnum.A6;
        }
        
        
        List<ComprobanteVentaData> dataReporte = getDetalleDataReporte(proforma);

        //map de los parametros faltantes
        Map<String, Object> mapParametros = getMapParametrosReporteProforma(proforma);
        
        //ReporteCodefac.generarReporteInternalFramePlantillaReturn(RecursoCodefac.JASPER_COMPRA, nombre, mapParametros, dataReporte, panelPadre, tituloReporte, OrientacionReporteEnum.HORIZONTAL, FormatoHojaEnum.TICKET, ConfiguracionImpresoraEnum.SELECCIONAR_IMPRESORA)        
        return ReporteCodefac.generarReporteInternalFramePlantillaReturn(
                proforma.getSucursalEmpresa(),
                proforma.getUsuario(),
                RecursoCodefac.JASPER_FACTURACION,
                nombreReporte,
                mapParametros, 
                dataReporte,
                "Proforma", 
                OrientacionReporteEnum.VERTICAL, 
                formatoHoja,
                ConfiguracionImpresoraEnum.NINGUNA,
                false
                );
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
        
        public void cargarPreciosPorcentaje(List<BigDecimal> descuentos );
        
        public void cargarPresentaciones(Producto producto);
        
        public void cargarEtiquetaStock(BigDecimal stock);
        
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
        public Boolean obtenerChkReservado();
        public EnumSiNo obtenerComboIva();
        public Factura obtenerFactura();
        public Boolean obtenerCheckPorcentajeSeleccion();
        public Long obtenerKardexId();
        
        public void limpiarComboPrecioVenta();
        public void limpiarComboPresentaciones();
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
        public Estudiante getEStudiante();
        
        //Cargar datos del costo y de la fecha de caducidad
        public void setearCostoDetalleTxt(String cantidad);
        public void setearFechaCaducidadTxt(String fechaCaducidad);
        
        public void cargarCliente(PersonaEstablecimiento cliente);
        public void setPresupuestoSeleccionado(Presupuesto presupuestoSeleccionado);
        public Kardex obtenerKardexDesdeProducto(Producto producto);
        
    }
    
    public enum FormatoReporteEnum implements ParametroUtilidades.ComparadorInterface
    {
        A4("A4",""),
        A2("A2","comprobante_venta_ticket.jrxml"),
        A5("A5","comprobante_venta_ticket.jrxml"),
        A6("A6","comprobante_venta_ticket.jrxml"),
        POS_80("POS 80","comprobante_venta_ticket.jrxml"),
        POS_50("POS 50","comprobante_venta_ticket_50.jrxml"),
        POS_40("POS 40","comprobante_venta_ticket_40.jrxml"),
        POS_30("POS 30","comprobante_venta_ticket_30.jrxml")
        ;

        private FormatoReporteEnum(String nombre,String reporteJasperNombre) {
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
        
        public static FormatoReporteEnum findByName(String nombre)
        {
            for (FormatoReporteEnum enumerador : FormatoReporteEnum.values()) {
                if(enumerador.getNombre().equals(nombre))
                {
                    return enumerador;
                }
            }
            return null;
        }

        @Override
        public Object consultarParametro(String nombreParametro) {
            return FormatoReporteEnum.findByName(nombreParametro);
        }
        
    }
    
    public interface SetearDatosNVI
    {
        public void setValores(BigDecimal valorUnitario,BigDecimal iva);
    }

    
}
