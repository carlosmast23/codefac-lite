/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.reporte;

import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronicoFacturaAndLiquidacionAbstract;
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
public class FacturaElectronicaReporte extends FacturaLiquidacionCompraAbstractReport{
    private FacturaComprobante faturaComprobante;

    public FacturaElectronicaReporte(FacturaComprobante faturaComprobante) 
    {
        super(faturaComprobante);
        this.faturaComprobante = faturaComprobante;
    }

    @Override
    public Map<String, Object> getMapInfoCliente() {
        Map<String,Object> map=super.getMapInfoCliente(); //To change body of generated methods, choose Tools | Templates.
        map.put("cliente_nombres",faturaComprobante.getInformacionFactura().getRazonSocialComprador());
        map.put("direccion_comprador",faturaComprobante.getInformacionFactura().getDireccionComprador());
        map.put("cliente_identificacion",faturaComprobante.getInformacionFactura().getIdentificacionComprador());
        return map;
    }

    
    
    
   
}
