/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.reporte;

import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteEnum;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.notacredito.DetalleNotaCreditoComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.notacredito.NotaCreditoComprobante;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Carlos
 */
public class NotaCreditoReporte extends ComprobanteElectronicoReporte{
    
    private NotaCreditoComprobante comprobante;

    public NotaCreditoReporte(ComprobanteElectronico comprobante) {
        super(comprobante);
        this.comprobante = (NotaCreditoComprobante) comprobante;
    }
    
    @Override
    public Map<String,Object> getMapInfoCliente()
    {
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("cliente_nombres",comprobante.getInfoNotaCredito().getRazonSocialComprador());
        map.put("cliente_identificacion",comprobante.getInfoNotaCredito().getIdentificacionComprador());
        map.put("fecha_emision", comprobante.getInfoNotaCredito().getFechaEmision());
        map.put("obligado_contabilidad",comprobante.getInfoNotaCredito().getObligadoContabilidad());
        return map;
    }
    
    @Override
    public Map<String,Object> getMapTotales()
    {
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("subtotal_cero","0");
        map.put("subtotal",comprobante.getInfoNotaCredito().getTotalSinImpuestos().toString());
        map.put("descuento","0");
        map.put("iva",comprobante.getInfoNotaCredito().getValorModificacion().subtract(comprobante.getInfoNotaCredito().getTotalSinImpuestos())+"");
        map.put("total",comprobante.getInfoNotaCredito().getValorModificacion()+"");
        /**
         * Falta setear el iva que se esta usando en el sistema
         */
        map.put("iva_porcentaje","12");
        
        ComprobanteEnum enumerador=ComprobanteEnum.getEnumByCodigo(comprobante.getInfoNotaCredito().getCodDocModificado());
        map.put("comprobanteModificado",enumerador.getNombre()+": "+comprobante.getInfoNotaCredito().getNumDocModificado());
        map.put("razonModificado",comprobante.getInfoNotaCredito().getMotivo());
        map.put("fechaDocumentoModificado",comprobante.getInfoNotaCredito().getFechaEmisionDocSustento());
        
        return map;
    }
    
    @Override
    public List<Object> getDetalles()
    {
        List<Object> detalles=new ArrayList<Object>();
        List<DetalleNotaCreditoComprobante> detalleComprobante=comprobante.getDetalles();
        for (DetalleNotaCreditoComprobante detalleFacturaComprobante : detalleComprobante) {
            DetalleReporteData data=new DetalleReporteData();
            data.setCantidad(detalleFacturaComprobante.getCantidad()+"");
            data.setCodigo(detalleFacturaComprobante.getCodigoInterno());
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
        return null;
    }

   
}