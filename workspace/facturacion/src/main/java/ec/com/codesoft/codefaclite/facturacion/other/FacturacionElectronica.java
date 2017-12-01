/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.other;

import ec.com.codesoft.codefaclite.controlador.comprobantes.ComprobanteElectronicoAbstract;
import ec.com.codesoft.codefaclite.controlador.session.SessionCodefacInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfazComunicacionPanel;
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
import ec.com.codesoft.codefaclite.servidor.entity.ImpuestoDetalle;
import ec.com.codesoft.codefaclite.servidor.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidor.entity.SriIdentificacion;
import ec.com.codesoft.ejemplo.utilidades.email.CorreoElectronico;
import ec.com.codesoft.ejemplo.utilidades.texto.UtilidadesTextos;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Carlos
 */
public class FacturacionElectronica extends ComprobanteElectronicoAbstract<FacturaComprobante> {

    private Factura factura;
    private Map<String,String> mapInfoAdicional;
    
    public FacturacionElectronica(SessionCodefacInterface session) {
        super(session);
    }

    public FacturacionElectronica(Factura factura, SessionCodefacInterface session, InterfazComunicacionPanel interfazPadre) {
        super(session, interfazPadre);
        this.factura = factura;
    }

    
    
    
    

    @Override
    public String getCodigoComprobante() {
        return ComprobanteEnum.FACTURA.getCodigo();
    }
    
        @Override
    public String getSecuencial() {
       String secuencial= this.session.getParametrosCodefac().get(ParametroCodefac.SECUENCIAL_FACTURA).getValor();
       return UtilidadesTextos.llenarCarateresIzquierda(secuencial,9,"0");
    }

    @Override
    public FacturaComprobante getComprobante() {
        FacturaComprobante facturaComprobante=new FacturaComprobante();
        
        InformacionFactura informacionFactura=new InformacionFactura();
        
        informacionFactura.setFechaEmision(ComprobantesElectronicosUtil.dateToString(factura.getFechaFactura()));
        
        if(factura.getCliente().getTipoIdentificacion().equals(SriIdentificacion.CEDULA_IDENTIFICACION))
            informacionFactura.setIdentificacionComprador(factura.getCliente().getIdentificacion());
        else
            informacionFactura.setIdentificacionComprador(UtilidadesTextos.llenarCarateresDerecha(factura.getCliente().getIdentificacion(), 13, "0"));
            
        informacionFactura.setImporteTotal(factura.getTotal());
        //Falta manejar este campo al momento de guardar
        informacionFactura.setRazonSocialComprador(factura.getCliente().getRazonSocial());
        //informacionFactura.setRazonSocialComprador(factura.getCliente().getRazonSocial());
        informacionFactura.setTipoIdentificacionComprador(factura.getCliente().getTipoIdentificacion());
        informacionFactura.setTotalDescuento(BigDecimal.ZERO);
        informacionFactura.setTotalSinImpuestos(factura.getSubtotalDoce().add(factura.getSubtotalCero()));
        
        //informacionFactura.setTotalImpuestos(totalImpuestos);
        
        /**
         * Total con impuestos
         */
        Map<ImpuestoDetalle,TotalImpuesto> mapTotalImpuestos=new HashMap<ImpuestoDetalle,TotalImpuesto>();
        
        
        /**
         * Informacion de los detalles
         */
        List<DetalleFacturaComprobante> detallesComprobante=new ArrayList<DetalleFacturaComprobante>();
        List<FacturaDetalle> detallesFactura= factura.getDetalles();
        
        for (FacturaDetalle facturaDetalle : detallesFactura) {
            DetalleFacturaComprobante detalle=new DetalleFacturaComprobante();
            detalle.setCantidad(facturaDetalle.getCantidad());
            detalle.setDescripcion(facturaDetalle.getDescripcion());
            //Establecer el descuento en el aplicativo
            detalle.setDescuento(BigDecimal.ZERO);
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
            impuesto.setTarifa(new BigDecimal(facturaDetalle.getProducto().getIva().getTarifa()+""));
            impuesto.setBaseImponible(facturaDetalle.getTotal());
            impuesto.setValor(facturaDetalle.getIva());
            
            /**
             * Verificar valores para el total de impuesto
             */
            if(mapTotalImpuestos.get(facturaDetalle.getProducto().getIva())==null)
            {
                TotalImpuesto totalImpuesto=new TotalImpuesto();
                totalImpuesto.setBaseImponible(impuesto.getBaseImponible());
                totalImpuesto.setCodigo(impuesto.getCodigo());
                totalImpuesto.setCodigoPorcentaje(impuesto.getCodigoPorcentaje());
                totalImpuesto.setValor(impuesto.getValor());
                mapTotalImpuestos.put(facturaDetalle.getProducto().getIva(), totalImpuesto);
            }
            else
            {
                TotalImpuesto totalImpuesto=mapTotalImpuestos.get(facturaDetalle.getProducto().getIva());
                totalImpuesto.setBaseImponible(totalImpuesto.getBaseImponible().add(impuesto.getBaseImponible()));
                totalImpuesto.setValor(totalImpuesto.getValor().add(impuesto.getValor()));
                mapTotalImpuestos.put(facturaDetalle.getProducto().getIva(), totalImpuesto);
                
            }
            
            //-------------> FIN <----------------
            listaComprobantes.add(impuesto);
            
            detalle.setImpuestos(listaComprobantes);
            
            detallesComprobante.add(detalle);
        }
        
        facturaComprobante.setDetalles(detallesComprobante);
        facturaComprobante.setInformacionFactura(informacionFactura);
        
        /**
         * Crear los impuestos totales
         */
        List<TotalImpuesto> totalImpuestos = new ArrayList<TotalImpuesto>();
        for (Map.Entry<ImpuestoDetalle, TotalImpuesto> entry : mapTotalImpuestos.entrySet()) {
            ImpuestoDetalle key = entry.getKey();
            TotalImpuesto value = entry.getValue();
            totalImpuestos.add(value);
        }
        facturaComprobante.getInformacionFactura().setTotalImpuestos(totalImpuestos);
        
        /**
         * Informacion adicional
         */
        
        
        return facturaComprobante;
    }

    @Override
    public Map<String, String> getMapAdicional() {
        return mapInfoAdicional;
    }

    public void setMapInfoAdicional(Map<String, String> mapInfoAdicional) {
        this.mapInfoAdicional = mapInfoAdicional;
    }

    @Override
    public List<String> getCorreos() {
        List<String> correos=new ArrayList<String>();
        correos.add(factura.getCliente().getCorreoElectronico());
        return correos;
    }
    
    



}
