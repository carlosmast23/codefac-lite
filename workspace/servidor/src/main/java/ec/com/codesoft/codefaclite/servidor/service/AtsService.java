/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

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
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.MesEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.SriEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.AtsServiceIf;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import java.io.Serializable;
import java.math.BigDecimal;
import java.rmi.RemoteException;
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
        ats.setNumEstabRuc(numeroSucursal);
        ats.setRazonSocial(empresa.getRazonSocial());
        ats.setTipoIDInformante("R"); //Todo: Ver que opciones existen para ese campo
        
        
        java.sql.Date fechaInicial=new java.sql.Date(UtilidadesFecha.getPrimerDiaMes(anio,mes.getNumero()-1).getTime());
        java.sql.Date fechaFinal=new java.sql.Date(UtilidadesFecha.getUltimoDiaMes(anio,mes.getNumero()-1).getTime());
        
        /**
         * ===================> COMPRAS <==========================
         */
        if(comprasBool)
        {
            List<CompraAts> compras=consultarComprasAts(fechaInicial, fechaFinal);
            ats.setCompras(compras);
        }
        
        /**
         * ===================> VENTAS <==========================
         */
        if(ventasBool)
        {
            List<VentaAts> ventas=consultarVentasAts(fechaInicial, fechaFinal);
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
            List<AnuladoAts> anulados=consultarAnuladosAts(fechaInicial, fechaFinal);
            ats.setAnuladosAts(anulados);
        }
        
        return ats;
        
    }
    
    public List<AnuladoAts> consultarAnuladosAts(java.sql.Date fechaInicial,java.sql.Date fechaFinal) throws  RemoteException,ServicioCodefacException
    {
        List<AnuladoAts> anuladosAts=new ArrayList<AnuladoAts>();
        NotaCreditoService notaCreditoService=new NotaCreditoService();
        List<NotaCredito> notasCredito=notaCreditoService.obtenerNotasReporte(null, fechaInicial, fechaFinal, GeneralEnumEstado.ACTIVO.getEstado());
        
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
   
    
    public List<CompraAts> consultarComprasAts(java.sql.Date fechaInicial,java.sql.Date fechaFinal) throws  RemoteException,ServicioCodefacException
    {
        List<CompraAts> comprasAts=new ArrayList<CompraAts>();
        CompraService compraService=new CompraService();
        List<Compra> compras=compraService.obtenerCompraReporte(null, fechaInicial, fechaFinal,null,null,GeneralEnumEstado.ACTIVO);
        
        for (Compra compra : compras) {
            CompraAts compraAts=new CompraAts();
            
            String identificacion=(compra.getIdentificacion()!=null && !compra.getIdentificacion().isEmpty() )?compra.getIdentificacion():compra.getProveedor().getIdentificacion();
                    
            
            compraAts.setCodSustento("10"); //TODO:TABLA 5 consultar para integrar todas las clasificaciones de las compras
            String codigoSri=getCodigoSri(compra);        
            compraAts.setTpIdProv(codigoSri);
            compraAts.setIdProv(identificacion);
            compraAts.setTipoComprobante("19");//Todo: Por el momento queda regitra ese codigo pero existen otro que toca preguntar para ver como funciona
            compraAts.setParteRel("SI");
            SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
            compraAts.setFechaRegistro(dateFormat.format(compra.getFechaCreacion()));
            compraAts.setEstablecimiento(UtilidadesTextos.llenarCarateresIzquierda(compra.getPuntoEstablecimiento(),3,"0"));
            compraAts.setPuntoEmision(UtilidadesTextos.llenarCarateresIzquierda(compra.getPuntoEmision(),3,"0"));
            compraAts.setSecuencial(compra.getSecuencial().toString());
            compraAts.setFechaEmision(dateFormat.format(compra.getFechaFactura()));
            
            String autorizacion=(compra.getAutorizacion()!=null && !compra.getAutorizacion().isEmpty())?compra.getAutorizacion():"0000000000";
            compraAts.setAutorizacion(autorizacion); //todo: revisar si para las comptas es lo mismo la clave de autorizacion y la autorizacion
            compraAts.setBaseNoGraIva(BigDecimal.ZERO);
            compraAts.setBaseImponible(compra.getSubtotalSinImpuestos());
            compraAts.setBaseImpGrav(compra.getSubtotalImpuestos());
            compraAts.setBaseImpExe(BigDecimal.ZERO);//TODO: Revisar cuando se aplica este campo , el manula dice que son Base imponible exenta de IVA
            compraAts.setMontoIce(BigDecimal.ZERO);
            compraAts.setMontoIva(compra.getIva());
            
            ///=======> DATOS DE LAS RETENCIONES <============///
            compraAts.setValRetBien10(BigDecimal.ZERO); //TODO:completar
            compraAts.setValRetServ20(BigDecimal.ZERO); //TODO:completar
            compraAts.setValorRetBienes(BigDecimal.ZERO); //TODO:completar
            compraAts.setValRetServ50(BigDecimal.ZERO); //TODO:completar
            compraAts.setValorRetServicios(BigDecimal.ZERO); //TODO:completar
            compraAts.setValRetServ100(BigDecimal.ZERO); //TODO:completar
            
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
    
    public List<VentaAts> consultarVentasAts(java.sql.Date fechaInicial,java.sql.Date fechaFinal) throws  RemoteException,ServicioCodefacException
    {
        FacturacionService facturacionService=new FacturacionService();
        List<Factura> facturas=facturacionService.obtenerFacturasReporte(null,fechaInicial,fechaFinal,ComprobanteEntity.ComprobanteEnumEstado.AUTORIZADO.getEstado(),false,null,false);
        
        Map<String,VentaAts> mapVentas=new HashMap<String,VentaAts>();
        
        for (Factura factura : facturas) 
        {
            VentaAts ventaAts=mapVentas.get(factura.getIdentificacion());
            if(ventaAts==null)
            { //Cuando no existe el dato en el map lo creo
                ventaAts=new VentaAts();
                
                String codigoSri=getCodigoSri(factura);                        
                ventaAts.setTpIdCliente(codigoSri);//Consultar el tipo de cliente
                ventaAts.setIdCliente(factura.getIdentificacion());
                
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
