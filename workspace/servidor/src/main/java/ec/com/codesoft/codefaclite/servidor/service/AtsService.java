/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidorinterfaz.ats.jaxb.AirAts;
import ec.com.codesoft.codefaclite.servidorinterfaz.ats.jaxb.AnuladoAts;
import ec.com.codesoft.codefaclite.servidorinterfaz.ats.jaxb.AtsJaxb;
import ec.com.codesoft.codefaclite.servidorinterfaz.ats.jaxb.CompraAts;
import ec.com.codesoft.codefaclite.servidorinterfaz.ats.jaxb.FormaDePagoAts;
import ec.com.codesoft.codefaclite.servidorinterfaz.ats.jaxb.PagoExteriorAts;
import ec.com.codesoft.codefaclite.servidorinterfaz.ats.jaxb.VentaAts;
import ec.com.codesoft.codefaclite.servidorinterfaz.ats.jaxb.VentasEstablecimientoAts;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Compra;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FormaPago;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.NotaCredito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.RetencionDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencion;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.MesEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.SriEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.sri.SriSustentoComprobanteEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.AtsServiceIf;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Carlos
 */
public class AtsService extends UnicastRemoteObject implements Serializable,AtsServiceIf {

    public AtsService() throws RemoteException {
        super(Registry.REGISTRY_PORT);
    }
    
    private String formatearMes(Integer mes)
    {
        if(mes.toString().length()==1)
        {
            return "0"+mes;
        }
        return mes.toString();
    }
    
    public AtsJaxb consultarAts(Integer anio, MesEnum mes,Empresa empresa,String numeroSucursal,boolean  comprasBool, boolean  ventasBool,boolean anuladosBool) throws  RemoteException,ServicioCodefacException
    {
        AtsJaxb ats=new AtsJaxb();
        ats.setAnio(anio);
        ats.setCodigoOperativo("IVA"); //Todo: Por el momento dejo en IVA como en el ejemplo del SRI
        ats.setIdInformante(empresa.getIdentificacion());
        ats.setMes(formatearMes(mes.getNumero()));
        //Todo: Es el numero de establecimientos activos que voy a realizar el ats
        SucursalService sucursalService=new SucursalService();
        List<Sucursal> sucursales=sucursalService.consultarActivosPorEmpresa(empresa);
        //ats.setNumEstabRuc(UtilidadesTextos.llenarCarateresIzquierda(sucursales.size()+"",3,"0")); 
        ats.setNumEstabRuc("001");  //Todo: por el momento dejo seteado
        ats.setRazonSocial(empresa.getRazonSocial());
        ats.setTipoIDInformante("R"); //Todo: Ver que opciones existen para ese campo
        
        
        java.sql.Date fechaInicial=new java.sql.Date(UtilidadesFecha.getPrimerDiaMes(anio,mes.getNumero()-1).getTime());
        java.sql.Date fechaFinal=new java.sql.Date(UtilidadesFecha.getUltimoDiaMes(anio,mes.getNumero()-1).getTime());
        
        /**
         * ===================> COMPRAS <==========================
         */
        if(comprasBool)
        {
            List<CompraAts> compras=consultarComprasAts(fechaInicial, fechaFinal,empresa);
            ats.setCompras(compras);
        }
        
        /**
         * ===================> VENTAS <==========================
         */
        if(ventasBool)
        {
            List<VentaAts> ventas=consultarVentasAts(fechaInicial, fechaFinal,empresa);
            ats.setVentas(ventas);
            ats.calcularTotalVentas();
            
            /**
             * ======================> TOTALES POR ESTABLECIMIENTO <===============================
             */
            //TODO: Analizar esta parte que esta diseñada solo para una sucursal
            VentasEstablecimientoAts ventaEstablecimientoAts = new VentasEstablecimientoAts();
            ventaEstablecimientoAts.setCodEstab(numeroSucursal);
            ventaEstablecimientoAts.setIvaComp(BigDecimal.ZERO); //Solo aplicaba para cuando era iva de compensacion por el terremoto
            ventaEstablecimientoAts.setVentasEstab(ats.getTotalVentas());

            List<VentasEstablecimientoAts> establecimientos = new ArrayList<VentasEstablecimientoAts>();
            establecimientos.add(ventaEstablecimientoAts);

            ats.setVentasEstablecimiento(establecimientos);
        }
        
        /**
         * ===================> ANULADOS <==========================
         */
        if(anuladosBool)
        {
            List<AnuladoAts> anulados=consultarAnuladosAts(fechaInicial, fechaFinal,empresa);
            ats.setAnuladosAts(anulados);
        }
        
        return ats;
        
    }
    
    public List<AnuladoAts> consultarAnuladosAts(java.sql.Date fechaInicial,java.sql.Date fechaFinal,Empresa empresa) throws  RemoteException,ServicioCodefacException
    {
        List<AnuladoAts> anuladosAts=new ArrayList<AnuladoAts>();
        NotaCreditoService notaCreditoService=new NotaCreditoService();
        List<NotaCredito> notasCredito=notaCreditoService.obtenerNotasReporte(null, fechaInicial, fechaFinal,ComprobanteEntity.ComprobanteEnumEstado.AUTORIZADO,empresa);
        
        for (NotaCredito notaCredito : notasCredito) {
            AnuladoAts anuladoAts=new AnuladoAts();
            
            anuladoAts.setTipoComprobante("18"); //Todo: por defecto solo anulo el tipo 18 que supuestamente corresponde documentos autorizados electronicamente
            String preimpreso[]=notaCredito.getNumDocModificado().split("-");
            anuladoAts.setEstablecimiento(preimpreso[0]);
            anuladoAts.setPuntoEmision(preimpreso[1]);
            anuladoAts.setSecuencialInicio(Integer.parseInt(preimpreso[2]));
            anuladoAts.setSecuencialFin(Integer.parseInt(preimpreso[2]));
            anuladoAts.setAutorizacion(notaCredito.getClaveAcceso()); //Todo: Verifica si este dato es el de la nota de credito o la factura que elimina , pero si son algunas no tiene sentido que sea el de la factura
            anuladosAts.add(anuladoAts);
        }
        return anuladosAts;
    }
   
    
    public List<CompraAts> consultarComprasAts(java.sql.Date fechaInicial,java.sql.Date fechaFinal,Empresa empresa) throws  RemoteException,ServicioCodefacException
    {
        SriRetencionService sriRetencionService=new SriRetencionService();
        SriRetencion sriRetencionIva=sriRetencionService.consultarPorNombre(SriRetencion.NOMBRE_RETENCION_IVA);//Variable que necesito para las retenciones
        SriRetencion sriRetencionRenta=sriRetencionService.consultarPorNombre(SriRetencion.NOMBRE_RETENCION_RENTA);//Variable que necesito para las retenciones
        
        List<CompraAts> comprasAts=new ArrayList<CompraAts>();
        CompraService compraService=new CompraService();
        List<Compra> compras=compraService.obtenerCompraReporte(null, fechaInicial, fechaFinal,null,null,GeneralEnumEstado.ACTIVO,empresa);
        
        for (Compra compra : compras) {
            CompraAts compraAts=new CompraAts();
            
            String identificacion=(compra.getIdentificacion()!=null && !compra.getIdentificacion().isEmpty() )?compra.getIdentificacion():compra.getProveedor().getIdentificacion();
                    
            if(compra.getCodigoSustentoSri()==null)
            {
                if(compra.getDetalles().get(0).getCodigoSustentoSriEnum()==null) //Si tampoco en el detalle tiene un dato definido lo pongo como null 
                {
                    compraAts.setCodSustento(SriSustentoComprobanteEnum.CREDITO_TRIBUTARIO_IVA.getCodigo()); //TODO: Por defecto dejo este valor para tener retrocompatiblidad con datos anteriores
                }
                else
                {
                    //TODO: Esta parte toca revisar porque solo estoy seleccionado por el momento el primer item para obtener ekl codigo de sustento tributario
                    //TODO: Pero lo correcto es si tiene distintos valores por cada detalle hacer varios registros agrupando los similares
                    compraAts.setCodSustento((compra.getDetalles().get(0).getCodigoSustentoSri()));
                }
            }else
            {
                compraAts.setCodSustento(compra.getCodigoSustentoSri());
            }
            
            String codigoSri=getCodigoSri(compra);        
            compraAts.setTpIdProv(codigoSri);
            compraAts.setIdProv(identificacion);
            
            if(compra.getCodigoComprobanteSri()==null)
            {
                compraAts.setTipoComprobante(DocumentoEnum.FACTURA.getCodigoSri());
            }else
            {
                compraAts.setTipoComprobante(compra.getCodigoComprobanteSri());
            }
            
            compraAts.setParteRel("SI"); //Todo: Me parece que esta parte toca implementar cuando es cliente final
            SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
            
            //Fecha de registro contable del comprobante de venta
            //compraAts.setFechaRegistro(dateFormat.format(compra.getFechaCreacion())); //Todo: este dato por defecto voy a dejar como fecha de registro la fecha de la compra
            compraAts.setFechaRegistro(dateFormat.format(compra.getFechaFactura())); //Todo: este dato por defecto voy a dejar como fecha de registro la fecha de la compra
            compraAts.setEstablecimiento(UtilidadesTextos.llenarCarateresIzquierda(compra.getPuntoEstablecimiento().toString(),3,"0"));
            compraAts.setPuntoEmision(UtilidadesTextos.llenarCarateresIzquierda(compra.getPuntoEmision().toString(),3,"0"));
            compraAts.setSecuencial(compra.getSecuencial().toString());
            compraAts.setFechaEmision(dateFormat.format(compra.getFechaFactura()));
            
            String autorizacion=(compra.getAutorizacion()!=null && !compra.getAutorizacion().isEmpty())?compra.getAutorizacion():"0000000000";
            compraAts.setAutorizacion(autorizacion.trim()); //todo: En caso de que los comprobantes con código 11, 19 y 20 no posean numeración, así como en convenios de débito y recaudación deberán completar sus datos con nueves (9999999999)
            compraAts.setBaseNoGraIva(BigDecimal.ZERO);
            compraAts.setBaseImponible(compra.getSubtotalSinImpuestos());
            compraAts.setBaseImpGrav(compra.getSubtotalImpuestos());
            compraAts.setBaseImpExe(BigDecimal.ZERO);//TODO: Revisar cuando se aplica este campo , el manula dice que son Base imponible exenta de IVA
            compraAts.setMontoIce(BigDecimal.ZERO);
            compraAts.setMontoIva(compra.getIva());
            
            Map<BigDecimal,BigDecimal> mapRetenciones=consultarRetencionesIva(compra,sriRetencionIva);
            ///=======> DATOS DE LAS RETENCIONES <============///
            compraAts.setValRetBien10(obtenerValorMapRetenciones(mapRetenciones,10)); //10% TODO:completar
            compraAts.setValRetServ20(obtenerValorMapRetenciones(mapRetenciones,20)); //20% TODO:completar
            compraAts.setValorRetBienes(obtenerValorMapRetenciones(mapRetenciones,30)); //30% TODO:completar 
            compraAts.setValRetServ50(obtenerValorMapRetenciones(mapRetenciones,50)); //50% TODO:completar
            compraAts.setValorRetServicios(obtenerValorMapRetenciones(mapRetenciones,70));//70% //TODO:completar
            compraAts.setValRetServ100(obtenerValorMapRetenciones(mapRetenciones,100)); //100% TODO:completar
            
            //========> COMPRAS DE REEMBOLSO <=================//
            compraAts.setTotbasesImpReemb(BigDecimal.ZERO); //TODO: Esto queda pendiente de programar
            //TODO: Falta programar para pagos en el exterior
            
            //========> PAGO EXTERIOR <========================//
            PagoExteriorAts pagoExteriorAts=new PagoExteriorAts();
            pagoExteriorAts.setPagoLocExt("01"); //Todo:por el momento dejo seteado solo para personas locales , el codigo para personas del exterior es 02
            pagoExteriorAts.setPaisEfecPagoGen("NA");
            pagoExteriorAts.setPaisEfecPago("NA");
            pagoExteriorAts.setAplicConvDobTrib("NA");
            pagoExteriorAts.setPagExtSujRetNorLeg("NA");
            compraAts.setPagoExteriorAts(pagoExteriorAts);
            
            
             //Agregar solo formas de pago que no esten ya registrados en el cliente //Solo deben aparecer las formas de pago cuando la base imponible es superior a 1000 dolares
            List<FormaDePagoAts> formasPago = new ArrayList<FormaDePagoAts>();
            FormaDePagoAts formaPago=new FormaDePagoAts();
            formaPago.setFormaPago("01"); //Todo: Por defecto queda setear pago en efectivo(Sin utuizacion del sistema financiero)
            formasPago.add(formaPago);
            
            
            List<RetencionDetalle> retencionesRenta=consultarRetencionesRenta(compra, sriRetencionRenta);
            List<AirAts> retencionesAts=new ArrayList<AirAts>();
            for (RetencionDetalle retencionRenta : retencionesRenta) {
                AirAts retencionRentaAts=new AirAts();
                retencionRentaAts.setBaseImpAir(retencionRenta.getBaseImponible().setScale(2,BigDecimal.ROUND_UP));
                retencionRentaAts.setCodRetAir(retencionRenta.getCodigoRetencionSri());
                retencionRentaAts.setPorcentajeAir(retencionRenta.getPorcentajeRetener().setScale(2,BigDecimal.ROUND_UP));
                retencionRentaAts.setValRetAir(retencionRenta.getValorRetenido().setScale(2,BigDecimal.ROUND_UP));
                //retencionesAts.add(retencionRentaAts);
                agregarAirAts(retencionesAts,retencionRentaAts);
            }
            compraAts.setDetalleAir(retencionesAts);
            
            //solo agregar las formas de pago cuando la base imponible superio los 1000
            //la suma de las BASES IMPONIBLES y los MONTOS de IVA e ICE exceden los USD. 1000.00.
            if(compraAts.getBaseImpGrav()
                    .add(compraAts.getBaseImpExe())
                    .add(compraAts.getBaseImponible())
                    .add(compraAts.getBaseNoGraIva())
                    .add(compraAts.getMontoIva())
                    .add(compraAts.getMontoIce())
                    .compareTo(new BigDecimal("1000"))>0)
            {
                compraAts.setFormasDePago(formasPago);
            }
            
            //TODO Falta completar los detalles de los impuestos a la renta
            
            //compraAts.setEstabRetencion1("");
            //compraAts.setPtoEmiRetencion1("");
            //compraAts.setSecRetencion1(codigoSri); //Secuecual de la retencion
            //compraAts.setAutRetencion1("");
            //compraAts.setFechaEmiRet1("");
            comprasAts.add(compraAts);
        }
        
        return comprasAts;
        
    }
    
    private void agregarAirAts(List<AirAts> retencionesAts,AirAts retencionRentaAts)
    {
        if(retencionesAts!=null)
        {
            for (AirAts retencionesAt : retencionesAts) {
                //Si ya existe mismos codigos de la retencion duplicados solo los agrupo
                if(retencionesAt.getCodRetAir().equals(retencionRentaAts.getCodRetAir()) && retencionesAt.getPorcentajeAir().equals(retencionRentaAts.getPorcentajeAir()))
                {
                    retencionesAt.setBaseImpAir(retencionesAt.getBaseImpAir().add(retencionRentaAts.getBaseImpAir()));
                    retencionesAt.setValRetAir(retencionesAt.getValRetAir().add(retencionRentaAts.getValRetAir()));
                    return;
                }
            }
        }
        //Si no encuentra un item con el mismo codigo los agrego
        retencionesAts.add(retencionRentaAts);
    }
    
    
    private List<RetencionDetalle> consultarRetencionesRenta(Compra compra,SriRetencion sriRetencion) throws RemoteException
    {
        RetencionService retencionService=new RetencionService();
        return retencionService.obtenerRetencionesRentaPorCompra(compra,sriRetencion);
    }
    
    private BigDecimal obtenerValorMapRetenciones(Map<BigDecimal,BigDecimal> mapRetenciones,Integer porcentaje)
    {
        BigDecimal porcentajeRetener=new BigDecimal(porcentaje);
        BigDecimal valorRetencion=BigDecimal.ZERO;
        
        if(mapRetenciones.get(porcentajeRetener)!=null)
        {
            return mapRetenciones.get(porcentajeRetener);
        }
        return valorRetencion;
    }
    
    private Map<BigDecimal,BigDecimal> consultarRetencionesIva(Compra compra,SriRetencion sriRetencion) throws RemoteException
    {
        RetencionService retencionService=new RetencionService();
        List<Object[]> retencionesLista=retencionService.obtenerRetencionesIvaPorCompra(compra,sriRetencion);
        Map<BigDecimal,BigDecimal> mapValoresRetenciones=new HashMap<BigDecimal, BigDecimal>();
        
        for (Object[] objects : retencionesLista) {
            BigDecimal porcentaje=(BigDecimal) objects[0];
            BigDecimal valorRetener=(BigDecimal) objects[1];
            mapValoresRetenciones.put(porcentaje.setScale(0), valorRetener.setScale(2,BigDecimal.ROUND_UP));            
        }
        return mapValoresRetenciones;
        
    }
    
    public List<VentaAts> consultarVentasAts(java.sql.Date fechaInicial,java.sql.Date fechaFinal,Empresa empresa) throws  RemoteException,ServicioCodefacException
    {
        FacturacionService facturacionService=new FacturacionService();
        /**
         * TODO: Verificar si en esta consulta no se traen las notas de venta interna porque no son documentos validos
         * Pero si deberia trear otros documentos que nos sean facturas tener pendiente
         */
        List<Factura> facturas=facturacionService.obtenerFacturasReporte(null,fechaInicial,fechaFinal,ComprobanteEntity.ComprobanteEnumEstado.AUTORIZADO,false,null,false,null,empresa,DocumentoEnum.FACTURA,null);
        
        Map<String,VentaAts> mapVentas=new HashMap<String,VentaAts>();
        
        for (Factura factura : facturas) 
        {
            VentaAts ventaAts=mapVentas.get(factura.getIdentificacion());
            if(ventaAts==null)
            { //Cuando no existe el dato en el map lo creo
                ventaAts=new VentaAts();
                
                String codigoSri=getCodigoSri(factura);                        
                ventaAts.setTpIdCliente(codigoSri);//Consultar el tipo de cliente
                
                ///Agrego esta validacion para tener retrocompatibilidad porque antes no se grababan la identificacion en la factura y si ese es el caso genero con la referencia guardada
                String identificacion=factura.getIdentificacion();
                if(identificacion==null || identificacion.isEmpty())
                {
                    identificacion=factura.getCliente().getIdentificacion();
                }
                
                ventaAts.setIdCliente(identificacion);
                
                //Este campo solo se incluye cuando el cliente es diferente del cliente final
                if(!ventaAts.getTpIdCliente().equals(Persona.TipoIdentificacionEnum.CLIENTE_FINAL.getCodigoSriVenta()))
                {
                    ventaAts.setParteRelVtas("SI");
                }
                
                //Este cmapo solo debe aparecer cuando el cliente extranjero
                if(ventaAts.getTpIdCliente().equals(Persona.TipoIdentificacionEnum.PASAPORTE.getCodigoSriVenta()))
                {
                    ventaAts.setTipoCliente(SriEnum.TipoIdentificacion.PERSONA_NATURAL.codigo); //TODO: por el momento dejeo seteado que todos son personas naturales los estranjeros
                    ventaAts.setDenoCli(factura.getRazonSocial());
                }
                
                ventaAts.setTipoComprobante("18"); //TODO: Revisar en la tabla 4 cuando sea otro tipo de documento
                ventaAts.setTipoEmision((factura.getTipoFacturacionEnum()!=null)?factura.getTipoFacturacionEnum().getCodigoSri():ComprobanteEntity.TipoEmisionEnum.ELECTRONICA.getCodigoSri()); //Todo: Si no tiene tipo asignado por algun motivo le dejo con electronica
                //Valores para los calculos
                ventaAts.setNumeroComprobantes(1);
                ventaAts.setBaseNoGraIva(BigDecimal.ZERO); //Este valor debe ser para productos que no grabar , Ejemplo la venta de bienes inmuebles: oficinas, terrenos, locales
                ventaAts.setBaseImponible(factura.getSubtotalSinImpuestos());
                ventaAts.setBaseImpGrav(factura.getSubtotalImpuestos());
                ventaAts.setMontoIva(factura.getIva());
                ventaAts.setMontoIce(BigDecimal.ZERO); // TODO: Este valor no estoy grabando para obtener el subtotal
                ventaAts.setValorRetIva(BigDecimal.ZERO); //TODO: Este dato aun no tento porque viene de la cartera
                ventaAts.setValorRetRenta(BigDecimal.ZERO); //TODO: Este dato aun no tengo porque viene de la cartera
                ventaAts.setFormasDePago(getFormasPago(factura)); //La primera setea la primera forma de pago

                mapVentas.put(factura.getIdentificacion(),ventaAts);
            }
            else
            {//Si existe solo consulto y edito los valores
                
                
                ventaAts.setNumeroComprobantes(ventaAts.getNumeroComprobantes()+1);
                ventaAts.setBaseNoGraIva(BigDecimal.ZERO); //Este valor debe ser para productos que no grabar , Ejemplo la venta de bienes inmuebles: oficinas, terrenos, locales
                ventaAts.setBaseImponible(ventaAts.getBaseImponible().add(factura.getSubtotalSinImpuestos()));
                ventaAts.setBaseImpGrav(ventaAts.getBaseImpGrav().add(factura.getSubtotalImpuestos()));
                ventaAts.setMontoIva(ventaAts.getMontoIva().add(factura.getIva()));
                ventaAts.setMontoIce(BigDecimal.ZERO); // TODO: Este valor no estoy grabando para obtener el subtotal
                ventaAts.setValorRetIva(BigDecimal.ZERO); //TODO: Este dato aun no tento porque viene de la cartera
                ventaAts.setValorRetRenta(BigDecimal.ZERO); //TODO: Este dato aun no tengo porque viene de la cartera
                
                //Agregar solo formas de pago que no esten ya registrados en el cliente
                List<FormaDePagoAts> formasPagoOriginal=getFormasPago(factura);
                List<FormaDePagoAts> formasPagoAcumulado=unirFormasPago(ventaAts.getFormasDePago(),formasPagoOriginal);
                
                ventaAts.setFormasDePago(formasPagoAcumulado);
                
            }
        }
        
        ////====================> Reconstruir los Valores de las ventas en una Lista <============================///
        List<VentaAts> ventasAts=new ArrayList<VentaAts>();
        for (Map.Entry<String, VentaAts> entry : mapVentas.entrySet()) {
            //String key = entry.getKey();
            VentaAts value = entry.getValue();
            ventasAts.add(value);
        }
        
        return ventasAts;

        
    }
    
    
    private List<FormaDePagoAts> unirFormasPago(List<FormaDePagoAts> acumulados, List<FormaDePagoAts> nuevos)
    {
        for (FormaDePagoAts nuevo : nuevos) {
            if(!acumulados.contains(nuevo))
            {
                acumulados.add(nuevo);
            }
        }
        return acumulados;
    }
    
    private List<FormaDePagoAts> getFormasPago(Factura factura)
    {
        List<FormaDePagoAts> formasPagoAts=new ArrayList<FormaDePagoAts>();
                
        List<FormaPago> formasPago=factura.getFormaPagos();
        for (FormaPago formaPago : formasPago) 
        {
            FormaDePagoAts formaPagoAts=new FormaDePagoAts();
            formaPagoAts.setFormaPago(formaPago.getSriFormaPago().getCodigo());
            formasPagoAts.add(formaPagoAts);
            //formaPagoAts.setFormaPago("");
        }
        return formasPagoAts;
    }
    
    /**
     * Funcion para consulta el tipo de identificacion 
     * @param factura
     * @return 
     */
    private String getCodigoSri(Factura factura)
    {
        if(factura.getTipoIdentificacionCodigoSri()!=null)
        {
            return factura.getTipoIdentificacionCodigoSri();
        }
        else
        {
            return factura.getCliente().getTipoIdentificacionEnum().getCodigoSriVenta();
        }
    }
    
    /**
     * Funcion para consulta el tipo de identificacion 
     * @param factura
     * @return 
     */
    private String getCodigoSri(Compra factura)
    {
        if(factura.getTipoIdentificacionCodigoSri()!=null)
        {
            return factura.getTipoIdentificacionCodigoSri();
        }
        else
        {
            return factura.getProveedor().getTipoIdentificacionEnum().getCodigoSriCompra();
        }
    }
    
}
