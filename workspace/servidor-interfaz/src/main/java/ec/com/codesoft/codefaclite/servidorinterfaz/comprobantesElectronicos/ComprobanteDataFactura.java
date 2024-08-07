/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos;

import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteEnum;
import ec.com.codesoft.codefaclite.facturacionelectronica.evento.ListenerComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronicoFacturaAndLiquidacionAbstract;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.InformacionComprobanteAbstract;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.factura.DetalleFacturaComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.factura.DetalleImpuestoReembolsoComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.factura.FacturaComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.factura.FormaPagoComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.factura.InformacionFactura;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.factura.ReembolsoDetalleComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general.InformacionTributaria;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.liquidacionCompra.InformacionLiquidacionCompra;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.liquidacionCompra.LiquidacionCompraComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.util.ComprobantesElectronicosUtil;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FormaPago;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ReembolsoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.RembolsoImpuestoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.ParametroUtilidades;
import ec.com.codesoft.codefaclite.utilidades.formato.ComprobantesUtilidades;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import ec.com.codesoft.codefaclite.utilidades.validadores.UtilidadValidador;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Carlos
 */
public class ComprobanteDataFactura extends ComprobanteDataFacturaNotaCreditoAbstract{

    private Factura factura;
    private Map<String, String> mapInfoAdicional;
    private List<String> correosAdicionales;
    private ListenerComprobanteElectronico listener;
    private Integer secuencial;
    //private List<FormaPago> formaPagos;

    public ComprobanteDataFactura(Factura factura) 
    {
        String secuencialStr=""; //Por defecto si no tiene secuencial dejo vacio
        if(factura.getSecuencial()!=null)
        {
            secuencialStr = factura.getSecuencial().toString();
        }
        
        secuencialStr = UtilidadesTextos.llenarCarateresIzquierda(secuencialStr, 9, "0");
        secuencial = Integer.parseInt(secuencialStr);

        this.factura = factura;
    }

    public ComprobanteDataFactura(Factura factura, Integer secuencial) {
        this.factura = factura;
        this.secuencial = secuencial;
    }

    @Override
    public String getCodigoComprobante() {
        if(factura.getCodigoDocumentoEnum().equals(DocumentoEnum.FACTURA))
        {
            return ComprobanteEnum.FACTURA.getCodigo();
        }else if(factura.getCodigoDocumentoEnum().equals(DocumentoEnum.LIQUIDACION_COMPRA))
        {
            return ComprobanteEnum.LIQUIDACION_COMPRA.getCodigo();
        }else if(factura.getCodigoDocumentoEnum().equals(DocumentoEnum.NOTA_VENTA_INTERNA))
        {
            return ComprobanteEnum.NOTA_VENTA_INTERNA.getCodigo();
        }
        return null;
    }

    @Override
    public String getSecuencial() {
        return UtilidadesTextos.llenarCarateresIzquierda(factura.getSecuencial() + "", 9, "0");
    }

    @Override
    public InformacionTributaria getInformacionTributaria() {
        return null;
    }
    
    public class InfoComprobante implements InfoComprobanteInterface{
        
        private InformacionComprobanteAbstract info;

        public InfoComprobante(InformacionComprobanteAbstract info) {
            this.info = info;
        }

        @Override
        public void setFechaEmision(String fechaEmision) {
            this.info.setFechaEmision(fechaEmision);
        }

        @Override
        public void setDirEstablecimiento(String dirEstablecimiento) {
            this.info.setDirEstablecimiento(dirEstablecimiento);
        }

        @Override
        public void setIdentificacion(String identificacion) {
            this.info.setIdentificacion(identificacion);
        }

        @Override
        public void setObligadoContabilidad(String obligadoContabilidad) {
            this.info.setObligadoContabilidad(obligadoContabilidad);
        }

        @Override
        public void setTipoIdentificacion(String tipoIdentificacion) {
            this.info.setTipoIdentificacion(tipoIdentificacion);
        }

        @Override
        public void setContribuyenteEspecial(String contribuyenteEspecial) {
            this.info.setContribuyenteEspecial(contribuyenteEspecial);
        }
    
            
    }
            

    @Override
    public ComprobanteElectronico getComprobante() {
                
        ComprobanteElectronicoFacturaAndLiquidacionAbstract comprobante = null;

        InformacionComprobanteAbstract informacionComprobante = null;
        if(factura.getCodigoDocumentoEnum().equals(DocumentoEnum.FACTURA) || factura.getCodigoDocumentoEnum().equals(DocumentoEnum.NOTA_VENTA_INTERNA))
        {  
            informacionComprobante = new InformacionFactura();
            comprobante=new FacturaComprobante();
        }else
        {
            informacionComprobante=new InformacionLiquidacionCompra();
            comprobante=new LiquidacionCompraComprobante();
        }
        
        llenarInformacionComprobante(new InfoComprobante(informacionComprobante) , factura);
        
        //Esta Direccion se refiere a la direccion del comprador
        String direccionComprador=factura.getDireccion();
        if(direccionComprador==null)
        {
            if(factura.getSucursal().getDireccion()!=null && !factura.getSucursal().getDireccion().equals(""))
            {
                direccionComprador=factura.getSucursal().getDireccion();                
            }
        }
        informacionComprobante.setDireccion(direccionComprador);
        
 
        //Falta manejar este campo al momento de guardar
        String razonSocial=factura.getRazonSocial();
        
        if(razonSocial==null)
        {
            if(factura.getCliente()!=null)
            {
                razonSocial=factura.getCliente().getRazonSocial();
            }
        }
        
        informacionComprobante.setRazonSocial(UtilidadValidador.normalizarTextoFacturacionElectronica(razonSocial));
        
       
        informacionComprobante.setImporteTotal(factura.getTotal());

        BigDecimal descuentoTotal = factura.getDescuentoImpuestos().add(factura.getDescuentoSinImpuestos());
        informacionComprobante.setTotalDescuento(descuentoTotal);

        BigDecimal subtotalTotalSinDescuentos = factura.getSubtotalImpuestos().add(factura.getSubtotalSinImpuestos());
        //Esta variable se refiere al subtotal antes de impuesto y menos los descuentos       
        informacionComprobante.setTotalSinImpuestos(subtotalTotalSinDescuentos.subtract(descuentoTotal));
        /**
         * Aqui hay que setear los valores de la base de datos
         */
        //informacionComprobante.setObligadoContabilidad(factura.getObligadoLlevarContabilidad()); //TODO: Revisar esta parte porque debe cambiar dependiendo el cliente
        
        /**
         * Grabar las formas de pago si la variable exise y es distinto de vacio
         */
        if (factura.getFormaPagos() != null && factura.getFormaPagos().size() > 0) {
            List<FormaPagoComprobante> formaPagosFactura = new ArrayList<FormaPagoComprobante>();
            for (FormaPago formaPago : factura.getFormaPagos()) {
                //Si la forma de pago no esta bien creada no la tomo en cuenta luego
                if(formaPago.getSriFormaPago()==null)
                {
                    continue;
                }
                
                FormaPagoComprobante formaPagoComprobante = new FormaPagoComprobante();
                formaPagoComprobante.setFormaPago(formaPago.getSriFormaPago().getCodigo());
                formaPagoComprobante.setPlazo(new BigDecimal(formaPago.getPlazo() + ""));
                formaPagoComprobante.setTotal(formaPago.getTotal());
                formaPagoComprobante.setUnidadTiempo(formaPago.getUnidadTiempo());
                formaPagoComprobante.setNombreTmp(formaPago.getSriFormaPago().getNombre());
                formaPagosFactura.add(formaPagoComprobante);
            }
            informacionComprobante.setFormaPagos(formaPagosFactura);
            //infORMA.setFormaPagos(formaPagosFactura);
        }

        /**
         * Total con impuestos la clave es el codigo del impuesto
         */
        //Map<Integer, TotalImpuesto> mapTotalImpuestos = new HashMap<Integer, TotalImpuesto>();

        /**
         * Informacion de los detalles
         */
        List<DetalleFacturaComprobante> detallesComprobante = new ArrayList<DetalleFacturaComprobante>();
        List<FacturaDetalle> detallesFactura = factura.getDetallesOrdenados();

        for (FacturaDetalle facturaDetalle : detallesFactura) {
            DetalleFacturaComprobante detalle = new DetalleFacturaComprobante();
            //ReferenciaDetalleFacturaRespuesta respuesta= ServiceFactory.getFactory().getFacturacionServiceIf().obtenerReferenciaDetalleFactura(facturaDetalle.getTipoDocumentoEnum(),facturaDetalle.getReferenciaId());
            
            //Esta opcion me permite ocultar el codigo principal de los productos por ejemplo para que no puedan encontrar los clientes en otros proveedores
            if(ParametroUtilidades.comparar(factura.getEmpresa(),ParametroCodefac.IMPRIMIR_CODIGO_INTERNO_PRODUCTO, EnumSiNo.SI))
            {
                detalle.setCodigoPrincipal(facturaDetalle.getReferenciaId()+"");
            }
            else
            {
                //detalle.setCodigoPrincipal(respuesta.obtenerCodigoPrincipal()+"");
                detalle.setCodigoPrincipal(facturaDetalle.getCodigoPrincipal());
            }
            
            //Agregar el codigo secundario
            if(!UtilidadesTextos.verificarNullOVacio(facturaDetalle.getCodigoSecundario()))
            {
                detalle.setCodigoAuxiliar(facturaDetalle.getCodigoSecundario());
            }
            
            detalle.setCantidad(facturaDetalle.getCantidad());
            
            if(!UtilidadesTextos.verificarNullOVacio(facturaDetalle.getPresentacionCodigo()))
            {
                detalle.setUnidadMedida(facturaDetalle.getPresentacionCodigo());
            }
            else
            {
                detalle.setUnidadMedida(null);
            }
            
            /*
            *   UTF-8 Validad caracteres no imprimibles Á y Í
            */
            detalle.setDescripcion(facturaDetalle.getDescripcion()); //Supuestamente ya no tengo que validar porque esta validad en el ingreso de los detalles
            //Establecer el descuento en el aplicativo
            detalle.setDescuento((facturaDetalle.getDescuento()!=null)?facturaDetalle.getDescuento():BigDecimal.ZERO);
            detalle.setPrecioTotalSinImpuesto(facturaDetalle.getTotal());
            //Todo: redondear valor porque en los comprobantes electronicos no me permite enviar con mas de 2 decimales aunque en los archivos xsd si permite
            //detalle.setPrecioUnitario(facturaDetalle.getPrecioUnitario().setScale(2, RoundingMode.HALF_UP));
            
            Integer decimalesRedondear=ParametroUtilidades.obtenerValorBaseDatos(factura.getEmpresa(),ParametroCodefac.NUMERO_DECIMALES_RIDE,new ParametroUtilidades.ComparadorInterface() {
                @Override
                public Object consultarParametro(String nombreParametro) {
                    return Integer.parseInt(nombreParametro);
                }
            });
            if(decimalesRedondear==null)
            {
                decimalesRedondear=2;
            }
            detalle.setPrecioUnitario(facturaDetalle.getPrecioUnitario().setScale(decimalesRedondear,BigDecimal.ROUND_HALF_UP));
            //detalle.setDescuento(detalle.getDescuento().setScale(2,RoundingMode.HALF_UP));
            detalle.setDescuento(detalle.getDescuento());
            /**
             * Agregado impuesto que se cobran a cada detalle individual
             */
            //List<ImpuestoComprobante> listaComprobantes = new ArrayList<ImpuestoComprobante>();
            
            //calcularImpuestos(mapTotalImpuestos, facturaDetalle);
            detalle.setImpuestos(calcularImpuestos(facturaDetalle));
            /**
             * Agregar valor del subsidio si existe o es mayor que cero
             */
            if(facturaDetalle.getPrecioSinSubsidio()!=null && facturaDetalle.getPrecioSinSubsidio().compareTo(BigDecimal.ZERO)!=0)
            {
                detalle.setPrecioSinSubsidio(facturaDetalle.getPrecioSinSubsidio());
            }
            detallesComprobante.add(detalle);
        }
        /**
         * Agregar el valor del Subsidio al xml en el caso de que exista
         */
        BigDecimal totalSubsidio=factura.calcularSubsidio();
        if(totalSubsidio.compareTo(BigDecimal.ZERO)>0)
        {
            InformacionFactura comprobanteFactura=(InformacionFactura)informacionComprobante;
            comprobanteFactura.setTotalSubsidio(totalSubsidio);
        }

        //comprobante.set
        comprobante.setDetalles(detallesComprobante);
        comprobante.setInformacionComprobante(informacionComprobante);

        /**
         * Crear los impuestos totales
         */
       
        //comprobante.getInformacionComprobante().setTotalImpuestos(crearImpuestosTotales(mapTotalImpuestos));
        //comprobante.getInformacionFactura().setTotalImpuestos(totalImpuestos);
        comprobante.getInformacionComprobante().setTotalImpuestos(crearImpuestosTotales(factura));

        /**
         * Informacion adicional
         */
        comprobante.setCorreos(getCorreos());
        
        /**
         * Agregar los datos cuando es una FACTURA DE REEMBOLSO
         */
        agregarDatosReembolso(comprobante);
        

        return comprobante;
    }
    
    private void agregarDatosReembolso(ComprobanteElectronicoFacturaAndLiquidacionAbstract comprobanteData)
    {
        //Si es una liquidacion de compra no tiene que hacer ese proceso
        if(comprobanteData instanceof LiquidacionCompraComprobante)
        {
            return;
        }
        
        FacturaComprobante facturaComprobante=(FacturaComprobante) comprobanteData;
        if(factura.getReembolsoList()!=null)
        {
            for (ReembolsoDetalle reembolsoDetalle : factura.getReembolsoList()) 
            {
                ReembolsoDetalleComprobante rembolsoDetalleData=new ReembolsoDetalleComprobante();
                rembolsoDetalleData.setTipoIdentificacionProveedorReembolso("04");
                rembolsoDetalleData.setIdentificacionProveedorReembolso(reembolsoDetalle.getProveedor().getIdentificacion());
                rembolsoDetalleData.setCodPaisPagoProveedorReembolso("000");
                rembolsoDetalleData.setTipoProveedorReembolso("01");
                rembolsoDetalleData.setCodDocReembolso("01");
                rembolsoDetalleData.setEstabDocReembolso(ComprobantesUtilidades.formatoEstablecimiento(reembolsoDetalle.getPuntoEstablecimiento()+""));
                rembolsoDetalleData.setPtoEmiDocReembolso(ComprobantesUtilidades.formatoEstablecimiento(reembolsoDetalle.getPuntoEmision()+""));
                String secuencialFormat=UtilidadesTextos.llenarCarateresIzquierda(reembolsoDetalle.getSecuencial()+"",9,"0");
                rembolsoDetalleData.setSecuencialDocReembolso(secuencialFormat);
                
                String fechaEmisionStr=ComprobantesElectronicosUtil.dateToString(new java.sql.Date(reembolsoDetalle.getFechaEmision().getTime()));
                
                rembolsoDetalleData.setFechaEmisionDocReembolso(fechaEmisionStr);
                rembolsoDetalleData.setNumeroautorizacionDocReemb(reembolsoDetalle.getNumeroAutorizacion());
                
                
                /// Agregar los detalle de los impuesto de los REEMBOLSOS                
                for (RembolsoImpuestoDetalle impuestoDetalle : reembolsoDetalle.getDetalleList()) 
                {
                    DetalleImpuestoReembolsoComprobante impuestoDetalleData=new DetalleImpuestoReembolsoComprobante();
                    impuestoDetalleData.setCodigo("2"); //Por defecto por el momento solo hago que permita enviar productos con IVA
                    
                    //TODO: Por el momento solo queda configurado para poder hacer reembolso del IVA, solo hago esto por la premura del tiempo
                    //SOLUCION: Guardar este dato desde impuestoDetalle para no tener que estar haciendo validaciones extra
                    if(impuestoDetalle.getPorcentajeIva()==0)
                    {
                        impuestoDetalleData.setCodigoPorcentaje("0");
                        impuestoDetalleData.setTarifa("0");
                        
                    }else if(impuestoDetalle.getPorcentajeIva()==12)
                    {
                        impuestoDetalleData.setCodigoPorcentaje("2");
                        impuestoDetalleData.setTarifa("12");
                    }
                    
                    BigDecimal baseImponibleReembolso=impuestoDetalle.getBaseImponible().setScale(2, RoundingMode.HALF_UP);
                    impuestoDetalleData.setBaseImponibleReembolso(baseImponibleReembolso+"");
                    impuestoDetalleData.setImpuestoReembolso(baseImponibleReembolso+"");
                    rembolsoDetalleData.agregarDetalleImpuesto(impuestoDetalleData);
                } 
                
                //Agregar el reembolso al detalle de la factura del comprobante
                facturaComprobante.agregarDetalleRembolso(rembolsoDetalleData);
            }
            
        }
    }

    
    @Override
    public Map<String, String> getMapAdicional() {
        //Validar el tipo de texto para quitar carcteres especiales
        for (Map.Entry<String, String> entry : mapInfoAdicional.entrySet()) {
            String key = entry.getKey();
            String value = UtilidadValidador.normalizarTextoCorreo(entry.getValue());
            mapInfoAdicional.put(key, value);
        }
        return mapInfoAdicional;
    }

    public void setMapInfoAdicional(Map<String, String> mapInfoAdicional) {
        this.mapInfoAdicional = mapInfoAdicional;
    }

    @Override
    public List<String> getCorreos() {
        /*
        List<String> correos=new ArrayList<String>();
        if(factura!=null && factura.getCliente()!=null)
            correos.add(factura.getCliente().getCorreoElectronico());
        
        //Agregar correos adicionales , solo si estan seteados los valores de los correos       
        if(this.correosAdicionales!=null)
        {
            for (String correo : this.correosAdicionales) {
                if(!correos.contains(correo))
                {
                    correos.add(correo);
                }
            }
        }
        
         */
        return new ArrayList<String>(); //TODO: Verificar si se deben usar estos campos porque ya se envian los correos desde la informacion adicional
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public List<String> getCorreosAdicionales() {
        return correosAdicionales;
    }

    public void setCorreosAdicionales(List<String> correosAdicionales) {
        this.correosAdicionales = correosAdicionales;
    }

    @Override
    public ListenerComprobanteElectronico getListenerComprobanteElectronico() {
        return listener;
    }

    public void setListener(ListenerComprobanteElectronico listener) {
        this.listener = listener;
    }

    @Override
    public Long getComprobanteId() {
        return this.factura.getId();
    }

    @Override
    public String getPuntoEmision() {
        return this.factura.getPuntoEmision().toString();
    }

    @Override
    public String getEstablecimiento() {
        return this.factura.getPuntoEstablecimiento().toString();
    }

    @Override
    public Empresa getEmpresa() {
        return factura.getEmpresa();
    }

    @Override
    public String getDireccionMatriz() {
        return factura.getDireccionMatriz();
    }

}
