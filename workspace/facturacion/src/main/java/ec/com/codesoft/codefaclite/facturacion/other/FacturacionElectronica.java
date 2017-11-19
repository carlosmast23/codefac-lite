/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.other;

import ec.com.codesoft.codefaclite.controlador.comprobantes.ComprobanteElectronicoAbstract;
import ec.com.codesoft.codefaclite.controlador.session.SessionCodefacInterface;
import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteElectronicoService;
import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteEnum;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.factura.FacturaComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.factura.InformacionFactura;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general.InformacionTributaria;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general.TotalImpuesto;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.util.ComprobantesElectronicosUtil;
import ec.com.codesoft.codefaclite.servidor.entity.Factura;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Carlos
 */
public class FacturacionElectronica extends ComprobanteElectronicoAbstract<FacturaComprobante>{

    private Factura factura;
    
    public FacturacionElectronica(SessionCodefacInterface session) {
        super(session);
    }

    public FacturacionElectronica(Factura factura, SessionCodefacInterface session) {
        super(session);
        this.factura = factura;
    }
    
    

    @Override
    public String getCodigoComprobante() {
        return ComprobanteEnum.FACTURA.getCodigo();
    }
    
        @Override
    public String getSecuencial() {
       return "01";
    }

    @Override
    public FacturaComprobante getComprobante() {
        FacturaComprobante facturaComprobante=new FacturaComprobante();
        
        InformacionFactura informacionFactura=new InformacionFactura();
        
        informacionFactura.setFechaEmision(ComprobantesElectronicosUtil.dateToString(factura.getFechaEmision()));
        informacionFactura.setIdentificacionComprador(factura.getCliente().getIdentificacion());
        informacionFactura.setImporteTotal(BigDecimal.ZERO);
        informacionFactura.setRazonSocialComprador(factura.getCliente().getRazonSocial());
        informacionFactura.setTipoIdentificacionComprador(factura.getCliente().getIdentificacion());
        informacionFactura.setTotalDescuento(BigDecimal.ZERO);
        informacionFactura.setTotalSinImpuestos(BigDecimal.ZERO);
        
        List<TotalImpuesto> totalImpuestos=new ArrayList<TotalImpuesto>();
        informacionFactura.setTotalImpuestos(totalImpuestos);
        
        return facturaComprobante;
    }



}
