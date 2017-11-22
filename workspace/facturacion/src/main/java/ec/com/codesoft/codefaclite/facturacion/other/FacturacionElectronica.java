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
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.factura.DetalleFacturaComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.factura.FacturaComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.factura.InformacionFactura;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general.ImpuestoComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general.InformacionTributaria;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general.TotalImpuesto;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.util.ComprobantesElectronicosUtil;
import ec.com.codesoft.codefaclite.servidor.entity.Factura;
import ec.com.codesoft.codefaclite.servidor.entity.FacturaDetalle;
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
    private Map<String,String> mapInfoAdicional;
    
    public FacturacionElectronica(SessionCodefacInterface session) {
        super(session);
    }

    public FacturacionElectronica(Factura factura, SessionCodefacInterface session) {
        super(session);
        this.factura = factura;
    }

    public FacturacionElectronica(Factura factura, Map<String, String> mapInfoAdicional, SessionCodefacInterface session) {
        super(session);
        this.factura = factura;
        this.mapInfoAdicional = mapInfoAdicional;
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
        
        /**
         * Informacion de los detalles
         */
        List<DetalleFacturaComprobante> detallesComprobante=new ArrayList<DetalleFacturaComprobante>();
        List<FacturaDetalle> detallesFactura= factura.getDetalles();
        
        for (FacturaDetalle facturaDetalle : detallesFactura) {
            DetalleFacturaComprobante detalle=new DetalleFacturaComprobante();
            detalle.setCantidad(facturaDetalle.getCantidad());
            detalle.setDescripcion(facturaDetalle.getDescripcion());
            detalle.setDescuento(facturaDetalle.getDescuento());
            detalle.setPrecioTotalSinImpuesto(facturaDetalle.getTotal());
            detalle.setPrecioUnitario(facturaDetalle.getPrecioUnitario());
            
            
            //facturaDetalle.getProducto().get
            
            /**
             * Agregado impuesto que se cobran a cada detalle individual
             */
            List<ImpuestoComprobante> listaComprobantes=new ArrayList<ImpuestoComprobante>();
            
            ImpuestoComprobante impuesto=new ImpuestoComprobante();
            impuesto.setCodigo(facturaDetalle.getProducto().getIva().getImpuesto().getCodigoSri());
            impuesto.setCodigoPorcentaje(facturaDetalle.getProducto().getIva().getCodigo()+"");
            impuesto.setTarifa(facturaDetalle.getProducto().getIva().getTarifa());
            impuesto.setBaseImponible(facturaDetalle.getTotal());
            impuesto.setValor(facturaDetalle.getIva());
            
            listaComprobantes.add(impuesto);
            
            detalle.setComprobantes(listaComprobantes);
            
            detallesComprobante.add(detalle);
        }
        
        facturaComprobante.setDetalles(detallesComprobante);
        
        /**
         * Informacion adicional
         */
        
        
        return facturaComprobante;
    }

    @Override
    public Map<String, String> getMapAdicional() {
        return mapInfoAdicional;
    }



}
