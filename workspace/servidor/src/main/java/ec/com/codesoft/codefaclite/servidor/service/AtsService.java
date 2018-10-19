/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidorinterfaz.ats.jaxb.AtsJaxb;
import ec.com.codesoft.codefaclite.servidorinterfaz.ats.jaxb.FormaDePagoAts;
import ec.com.codesoft.codefaclite.servidorinterfaz.ats.jaxb.VentaAts;
import ec.com.codesoft.codefaclite.servidorinterfaz.ats.jaxb.VentasEstablecimientoAts;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FormaPago;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.MesEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.AtsServiceIf;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import java.io.Serializable;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Date;
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
    
    public AtsJaxb consultarAts(Integer anio, MesEnum mes,Empresa empresa,String numeroSucursal) throws  RemoteException,ServicioCodefacException
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
        
        List<VentaAts> ventas=consultarVentasAts(fechaInicial, fechaFinal);
        ats.setVentas(ventas);
        ats.calcularTotalVentas();
        
                
        //======================> Armar los subtotales de ventas por establecimient <===============================///
        //TODO: Analizar esta parte que esta diseñada solo para una sucursal
        VentasEstablecimientoAts ventaEstablecimientoAts=new VentasEstablecimientoAts();
        ventaEstablecimientoAts.setCodEstab(numeroSucursal);
        ventaEstablecimientoAts.setIvaComp(BigDecimal.ZERO); //Solo aplicaba para cuando era iva de compensacion por el terremoto
        ventaEstablecimientoAts.setVentasEstab(ats.getTotalVentas());
        
        List<VentasEstablecimientoAts> establecimientos=new ArrayList<VentasEstablecimientoAts>();
        establecimientos.add(ventaEstablecimientoAts);
        
        ats.setVentasEstablecimiento(establecimientos);
        
        return ats;
        
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
                
                if(!ventaAts.getTpIdCliente().equals(Persona.TipoIdentificacionEnum.CLIENTE_FINAL.getCodigoSriVenta()))
                {
                    ventaAts.setParteRelVtas("SI");
                }
                
                ventaAts.setTipoComprobante("18");
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
    
}
