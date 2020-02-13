/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vista.factura;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.mensajes.CodefacMsj;
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
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroEstudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefacInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.BodegaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.KardexServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ProductoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.ParametroUtilidades;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadIva;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesImpuestos;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class FacturaModelControlador extends FacturaNotaCreditoModelControladorAbstract{
    
    private FacturaModelControlador.FacturaModelInterface interfaz;

    public FacturaModelControlador(SessionCodefacInterface session,FacturaModelInterface interfaz,MensajeVistaInterface mensajeVista) {
        super(mensajeVista);
        this.session=session;
        this.interfaz=interfaz;
    }
    
    
    public Map<String,Object> getMapParametrosReporte(Factura facturaProcesando)
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
    
    public List<ComprobanteVentaData> getDetalleDataReporte(Factura facturaProcesando)
    {
        List<ComprobanteVentaData> dataReporte = new ArrayList<ComprobanteVentaData>();

        for (FacturaDetalle detalle : facturaProcesando.getDetalles()) {

            ComprobanteVentaData data = new ComprobanteVentaData();
            data.setCantidad(detalle.getCantidad().toString());
            data.setCodigo(detalle.getId().toString());
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
                descripcion, 
                productoSeleccionado.getCodigoPersonalizado(), 
                productoSeleccionado.getCatalogoProducto(), 
                productoSeleccionado.getIdProducto(), 
                interfaz.obtenerTipoDocumentoSeleccionado());
        
        //interfaz.setearValoresProducto(productoSeleccionado.getValorUnitario(),descripcion,productoSeleccionado.getCodigoPersonalizado(),productoSeleccionado.getCatalogoProducto());
        interfaz.setFacturaDetalleSeleccionado(facturaDetalle);
        setearValoresProducto(facturaDetalle);
    }
    
    public FacturaDetalle crearFacturaDetalle(BigDecimal valorUnitario,String descripcion,String codigo,CatalogoProducto catalogoProducto,Long referenciaId,TipoDocumentoEnum tipoDocumentoReferencia)
    {
        FacturaDetalle facturaDetalle=new FacturaDetalle();
        facturaDetalle.setCantidad(BigDecimal.ONE);
        facturaDetalle.setDescripcion(descripcion);
        facturaDetalle.setDescuento(BigDecimal.ZERO);
        facturaDetalle.setPrecioUnitario(valorUnitario);
        facturaDetalle.setReferenciaId(referenciaId);
        facturaDetalle.setCodigoPrincipal(codigo);
        facturaDetalle.setTipoDocumentoEnum(tipoDocumentoReferencia);
        
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
    private boolean validacionPersonalizadaPorModulos(FacturaDetalle facturaDetalle) {
        TipoDocumentoEnum tipoDocEnum=facturaDetalle.getTipoDocumentoEnum();
        BigDecimal cantidad = facturaDetalle.getCantidad();
        BigDecimal valorUnitario = facturaDetalle.getPrecioUnitario();

        switch(tipoDocEnum)
        {
            case ACADEMICO:
                //TODO: Analizar para el caso que tenga descuento
                if (interfaz.obtenerRubroSeleccionado().getSaldo().compareTo(cantidad.multiply(valorUnitario)) == -1) {
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
                return validarAgregarInventario(facturaDetalle); //Metodo que se encarga de validar el inventario
                
        }
        
        return true;
    }
    
    private boolean validarAgregarInventario(FacturaDetalle facturaDetalle)
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
                    
                    
                    boolean verifadorStock = verificarExistenciaStockProducto(facturaDetalle);
                    //Verificar si agrego los datos al fomurlaro cuando no existe inventario
                    if (!verifadorStock) {
                        mostrarMensaje(new CodefacMsj("Advertencia", "No existe stock para el producto", DialogoCodefac.MENSAJE_ADVERTENCIA));
                        //DialogoCodefac.mensaje("Advertencia", "No existe stock para el producto", DialogoCodefac.MENSAJE_ADVERTENCIA);
                        return false;
                    } else {                        
                        return true;
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
        verificadorStock = serviceKardex.obtenerSiNoExisteStockProducto(bodegaVenta,producto, facturaDetalle.getCantidad().intValue());
        return verificadorStock;
    }
    
    /**
     * TODO: VER SIS ESTE METODO SE PUEDE UNIR CON EL DE ABAJO PORQUE EISTE 2 SIMILARES !IMPORTANTE!
     * @param facturaDetalle
     * @return 
     */
    public boolean agregarDetallesFactura(FacturaDetalle facturaDetalle) throws ServicioCodefacException {
        //boolean agregar = true;

        //Verifica si manda un detalle existe solo se modifica
        //if (facturaDetalle != null) {
        //    agregar = false;
        //} else {
        //    facturaDetalle = new FacturaDetalle();
        //}

        //Validacion de los datos ingresados para ver si puedo agregar al detalle
        if (!interfaz.validarIngresoDetalle()) {
            //int filaSeleccionada=getTblDetalleFactura().getSelectedRow();
            int filaSeleccionada=interfaz.filaSeleccionadaTablaDetalle();
            interfaz.cargarDatosDetalles(); //Si no se pudo editar vuelvo a cargar los detalles si se modifico desde la tabla para que quede la forma original
            //getTblDetalleFactura().setRowSelectionInterval(filaSeleccionada,filaSeleccionada);
            interfaz.seleccionarFilaTablaDetalle(filaSeleccionada);
            return false;
        }

            
        //Validacion personalizada dependiendo de la logica de cada tipo de documento
        if (!validacionPersonalizadaPorModulos(facturaDetalle)) {
                return false;
        }
            
            
        //Variable del producto para verificar otros datos como el iva
        //CatalogoProducto catalogoProducto=null;
        /*try {
            if(facturaDetalle.get)
            ReferenciaDetalleFacturaRespuesta respuesta=ServiceFactory.getFactory().getFacturacionServiceIf().obtenerReferenciaDetalleFactura(facturaDetalle.getTipoDocumentoEnum(),facturaDetalle.getReferenciaId());
            catalogoProducto=respuesta.catalogoProducto;
        } catch (RemoteException ex) {
            Logger.getLogger(FacturaModelControlador.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
        //Seleccionar la referencia dependiendo del tipo de documento
        //TipoDocumentoEnum tipoDocumentoEnum=interfaz.obtenerTipoDocumentoSeleccionado();
        //facturaDetalle.setTipoDocumento(tipoDocumentoEnum.getCodigo());s
        //Obtengo el catalogo producto dependiendo el documento para poder saber las caracteristicas del producto
        /*switch (tipoDocumentoEnum)
        {
            case ACADEMICO:
                facturaDetalle.setReferenciaId(interfaz.obtenerRubroSeleccionado().getId());
                catalogoProducto = interfaz.obtenerRubroSeleccionado().getRubroNivel().getCatalogoProducto();
                break;
                
            case PRESUPUESTOS:
                facturaDetalle.setReferenciaId(interfaz.obtenerPresupuestoSeleccionado().getId());
                catalogoProducto=interfaz.obtenerPresupuestoSeleccionado().getCatalogoProducto();
                break;
                
                //Para invetario o para libre es la misma logica
            case INVENTARIO: case LIBRE: 
                facturaDetalle.setReferenciaId(interfaz.obtenerProductoSeleccionado().getIdProducto());
                catalogoProducto =interfaz.obtenerProductoSeleccionado().getCatalogoProducto();
                //catalogoProducto = ServiceFactory.getFactory().getProductoServiceIf().buscarPorId(facturaDetalle.getReferenciaId()).getCatalogoProducto();
                break;
        }*/
        
        //Advertecia cuando el item a facturar no tiene asignando un catalogo producto que es importante porque es de donde obtiene los valore
        /*if(catalogoProducto==null)
        {
            DialogoCodefac.mensaje("Advertencia","No esta definido el Catalogo Producto ,donde se especifica los impuestos para facturar ",DialogoCodefac.MENSAJE_INCORRECTO);
            return false;
        }*/
        facturaDetalle.setCantidad(new BigDecimal(interfaz.obtenerTxtCantidad()));
        facturaDetalle.setDescripcion(interfaz.obtenerTxtDescripcion());
        //Calcula los valores dependiendo del iva para tener el valor unitario
        BigDecimal valorTotalUnitario = new BigDecimal(interfaz.obtenerTxtValorUnitario());
        EnumSiNo incluidoIvaSiNo=interfaz.obtenerComboIva();
        //session.
        BigDecimal ivaDefecto=new BigDecimal(session.getParametrosCodefac().get(ParametroCodefac.IVA_DEFECTO).getValor());
        
        if(incluidoIvaSiNo.equals(EnumSiNo.SI))
        {
            //BigDecimal ivaTmp=ivaDefecto.divide(new BigDecimal("100"),2,BigDecimal.ROUND_HALF_UP).add(BigDecimal.ONE);
            //valorTotalUnitario=valorTotalUnitario.divide(ivaTmp,6,BigDecimal.ROUND_HALF_UP); //Redondeando con 4 decimales ya no genera problema con el centavo aveces
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
        //if (!getCheckPorcentaje().isSelected()) { //Cuando no es porcentaje el valor se setea directo
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
            //facturaDetalle.setDescuento(descuento.setScale(2,BigDecimal.ROUND_HALF_UP));
            facturaDetalle.setDescuento(descuento);
        } else { //Cuando es porcentaje se calcula primero el valor en procentaje
            if (!interfaz.obtenerTxtDescuento().isEmpty()) {
                BigDecimal porcentajeDescuento = new BigDecimal(interfaz.obtenerTxtDescuento());
                facturaDetalle.calcularDescuentoConPorcentaje(porcentajeDescuento, incluidoIvaSiNo, ivaDefecto);                
            }
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
        if (factura.getFormaPagos()!=null && factura.getFormaPagos().size() == 1) {
            FormaPago formaPago = factura.getFormaPagos().get(0);
            formaPago.setTotal(factura.getTotal());
            interfaz.cargarFormasPagoTabla();
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
        //PRE presupuestoSeleccionado=null;
        //interfazrubroEstudiante=null;
           
        
        //Limpio los datos en la pantalla
        /*interfaz.setearCantidadTxt("1");
        interfaz.setearDescripcionTxt("");
        interfaz.setearValorUnitarioTxt("");
        interfaz.setearDescuentoTxt("0");
        interfaz.setearCodigoDetalleTxt("");*/
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
        public void cargarDatosDetalleVista(BigDecimal valorUnitario,String descripcion,String codigo);
        
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
        
    }
    
}
