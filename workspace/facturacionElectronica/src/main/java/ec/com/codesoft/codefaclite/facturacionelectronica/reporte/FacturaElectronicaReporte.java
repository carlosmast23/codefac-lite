/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.reporte;

import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.factura.DetalleFacturaComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.factura.FacturaComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.factura.FormaPagoComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general.TotalImpuesto;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Carlos
 */
public class FacturaElectronicaReporte extends ComprobanteElectronicoReporte{
    
    private FacturaComprobante facturaComprobante;
    private Map<String,String> mapCodeAndNameFormaPago;

    public FacturaElectronicaReporte(ComprobanteElectronico comprobante) {
        super(comprobante);
        this.facturaComprobante = (FacturaComprobante) comprobante;
    }
    
    
    
    @Override
    public Map<String,Object> getMapInfoCliente()
    {
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("cliente_nombres",facturaComprobante.getInformacionFactura().getRazonSocialComprador());
        map.put("cliente_identificacion",facturaComprobante.getInformacionFactura().getIdentificacionComprador());
        map.put("fecha_emision", facturaComprobante.getInformacionFactura().getFechaEmision());
        map.put("obligado_contabilidad",facturaComprobante.getInformacionFactura().getObligadoContabilidad());
        return map;
    }
    
    @Override
    public Map<String,Object> getMapTotales()
    {
        /**
         * Calcular los valores de los subtotales 0 y con impuestos         * 
         */
        List<TotalImpuesto> impuestos=facturaComprobante.getInformacionFactura().getTotalImpuestos();
        BigDecimal subTotalCero=BigDecimal.ZERO;
        BigDecimal subTotalImpuesto=BigDecimal.ZERO;
        BigDecimal iva=BigDecimal.ZERO;
        
        for (TotalImpuesto impuesto : impuestos) {
            if(impuesto.getValor().compareTo(BigDecimal.ZERO)==0)
            {
                //subTotalCero=subTotalCero.add(impuesto.getBaseImponible().add(new BigDecimal(impuesto.getDescuentoAdicional())));
                subTotalCero=subTotalCero.add(impuesto.getBaseImponible());
                
            }
            else
            {
                //subTotalImpuesto=subTotalImpuesto.add(impuesto.getBaseImponible().add(new BigDecimal(impuesto.getDescuentoAdicional())));
                subTotalImpuesto=subTotalImpuesto.add(impuesto.getBaseImponible());
                iva=iva.add(impuesto.getValor());
            }
        }
        
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("subtotal_cero",subTotalCero.toString());
        map.put("subtotal",subTotalImpuesto.toString());
        map.put("descuento",facturaComprobante.getInformacionFactura().getTotalDescuento().toString());
        
        map.put("iva",iva.toString());
        map.put("total",facturaComprobante.getInformacionFactura().getImporteTotal()+"");
        /**
         * Falta setear el iva que se esta usando en el sistema
         */
        map.put("iva_porcentaje","12");
        return map;
    }
    
    @Override
    public List<Object> getDetalles()
    {
        List<Object> detalles=new ArrayList<Object>();
        List<DetalleFacturaComprobante> detalleComprobante=facturaComprobante.getDetalles();
        for (DetalleFacturaComprobante detalleFacturaComprobante : detalleComprobante) {
            DetalleReporteData data=new DetalleReporteData();
            data.setCantidad(detalleFacturaComprobante.getCantidad()+"");
            data.setCodigo(detalleFacturaComprobante.getCodigoPrincipal());
            data.setDescripcion(detalleFacturaComprobante.getDescripcion());
            data.setDescuento(detalleFacturaComprobante.getDescuento()+"");
            data.setPrecio_total(detalleFacturaComprobante.getPrecioTotalSinImpuesto()+"");
            data.setPrecio_unitario(detalleFacturaComprobante.getPrecioUnitario()+"");
            detalles.add(data);
        }
        
        return detalles;
    }

    @Override
    public List getListFormasPago() {
        List<FormaPagoComprobante> formasPago=facturaComprobante.getInformacionFactura().getFormaPagos();
        List<FormaPagoData> formaPagosData=new ArrayList<FormaPagoData>();
        if(formasPago!=null)
        {
            for (FormaPagoComprobante formaPagoComprobante : formasPago) {
                FormaPagoData formaPagoData=new FormaPagoData();
                if(mapCodeAndNameFormaPago!=null)
                {
                    String nombreFormaPago=mapCodeAndNameFormaPago.get(formaPagoComprobante.getFormaPago());
                    formaPagoData.setNombre((nombreFormaPago!=null)?nombreFormaPago:"Sin Nombre");
                }
                else
                {
                    formaPagoData.setNombre("Sin Nombre");
                }
                formaPagoData.setValor(formaPagoComprobante.getTotal().toString());            
                formaPagosData.add(formaPagoData);
            }
        }
        return formaPagosData;
    }

    public void setMapCodeAndNameFormaPago(Map<String, String> mapCodeAndNameFormaPago) {
        this.mapCodeAndNameFormaPago = mapCodeAndNameFormaPago;
    }
    
    
    
    

   
}
