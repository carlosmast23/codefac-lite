/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.other;

import ec.com.codesoft.codefaclite.controlador.comprobantes.ComprobanteElectronicoAbstract;
import ec.com.codesoft.codefaclite.controlador.session.SessionCodefacInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfazComunicacionPanel;
import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteEnum;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.factura.DetalleFacturaComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general.ImpuestoComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general.TotalImpuesto;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.notacredito.DetalleNotaCreditoComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.notacredito.InformacionNotaCredito;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.notacredito.NotaCreditoComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.util.ComprobantesElectronicosUtil;
import ec.com.codesoft.codefaclite.servidor.entity.FacturaDetalle;
import ec.com.codesoft.codefaclite.servidor.entity.ImpuestoDetalle;
import ec.com.codesoft.codefaclite.servidor.entity.NotaCredito;
import ec.com.codesoft.codefaclite.servidor.entity.NotaCreditoDetalle;
import ec.com.codesoft.codefaclite.servidor.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidor.entity.SriIdentificacion;
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
public class NotaCreditoElectronico extends ComprobanteElectronicoAbstract<NotaCreditoComprobante>{
    private NotaCredito notaCredito;
    private Map<String,String> mapInfoAdicional;
    private List<String> correosAdicionales;
    
    
    public NotaCreditoElectronico(SessionCodefacInterface session, InterfazComunicacionPanel interfazPadre) {
        super(session, interfazPadre);
    }

    @Override
    public String getCodigoComprobante() {
        return ComprobanteEnum.NOTA_CREDITO.getCodigo();
    }

    @Override
    public String getSecuencial() {
       // String secuencial= this.session.getParametrosCodefac().get(ParametroCodefac.SECUENCIAL_NOTA_CREDITO).getValor();
       String secuencial= notaCredito.getSecuencial().toString();       
       return UtilidadesTextos.llenarCarateresIzquierda(secuencial,9,"0");
    }

    @Override
    public Map<String, String> getMapAdicional() {
        return mapInfoAdicional;
    }

    @Override
    public List<String> getCorreos() {
        List<String> correos=new ArrayList<String>();
        if(notaCredito!=null && notaCredito.getCliente()!=null)
            correos.add(notaCredito.getCliente().getCorreoElectronico());
        
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
        return correos;
    }

    @Override
    public NotaCreditoComprobante getComprobante() {
        NotaCreditoComprobante notaCreditoComprobante=new NotaCreditoComprobante();
        InformacionNotaCredito info=new InformacionNotaCredito();
        info.setCodDocModificado(ComprobanteEnum.FACTURA.getCodigo());
        
        //Revisar este dato porque solo se debe poner cuando sea contribuyente especial
        //info.setContribuyenteEspecial(claveAcceso);
        
        info.setDirEstablecimiento(notaCredito.getCliente().getDireccion());
        info.setFechaEmision(ComprobantesElectronicosUtil.dateToString(notaCredito.getFechaNotaCredito()));
        info.setFechaEmisionDocSustento(ComprobantesElectronicosUtil.dateToString(notaCredito.getFactura().getFechaFactura()));
        
        if(notaCredito.getCliente().getTipoIdentificacion().equals(SriIdentificacion.CEDULA_IDENTIFICACION))
            info.setIdentificacionComprador(notaCredito.getCliente().getIdentificacion());
        else
            info.setIdentificacionComprador(UtilidadesTextos.llenarCarateresDerecha(notaCredito.getCliente().getIdentificacion(), 13, "0"));
        /**
         * Verificar que valor no mas acepta
         */
        info.setMoneda("DOLAR");
        
        info.setMotivo(notaCredito.getRazonModificado());
        info.setNumDocModificado(notaCredito.getFactura().getPreimpreso());
        info.setObligadoContabilidad("NO");
        info.setRazonSocialComprador(notaCredito.getCliente().getRazonSocial());
        
        /**
         * Consultar para que sirve el rise
         */
        //info.setRise(claveAcceso);
        
        info.setTipoIdentificacionComprador(notaCredito.getCliente().getTipoIdentificacion());
        //info.setTotalImpuestos(totalImpuestos);
        info.setTotalSinImpuestos(notaCredito.getSubtotalDoce().add(notaCredito.getSubtotalCero()));
        info.setValorModificacion(notaCredito.getTotal());
        
        
        Map<ImpuestoDetalle,TotalImpuesto> mapTotalImpuestos=new HashMap<ImpuestoDetalle,TotalImpuesto>();
        
                /**
         * Informacion de los detalles
         */
        List<DetalleNotaCreditoComprobante> detallesComprobante=new ArrayList<DetalleNotaCreditoComprobante>();
        List<NotaCreditoDetalle> detallesNotaCredito= notaCredito.getDetalles();
        
        for (NotaCreditoDetalle detalleNotaCredito : detallesNotaCredito) {
            DetalleNotaCreditoComprobante detalle=new DetalleNotaCreditoComprobante();

            detalle.setCodigoInterno(detalleNotaCredito.getProducto().getCodigoPrincipal());
            detalle.setCantidad(detalleNotaCredito.getCantidad());
            detalle.setDescripcion(detalleNotaCredito.getDescripcion());
            //Establecer el descuento en el aplicativo
            detalle.setDescuento(BigDecimal.ZERO);
            detalle.setPrecioTotalSinImpuesto(detalleNotaCredito.getTotal());
            detalle.setPrecioUnitario(detalleNotaCredito.getPrecioUnitario());  
            
            
            //facturaDetalle.getProducto().get
            
            /**
             * Agregado impuesto que se cobran a cada detalle individual
             */
            List<ImpuestoComprobante> listaComprobantes=new ArrayList<ImpuestoComprobante>();
            
            ImpuestoComprobante impuesto=new ImpuestoComprobante();
            impuesto.setCodigo(detalleNotaCredito.getProducto().getIva().getImpuesto().getCodigoSri());
            impuesto.setCodigoPorcentaje(detalleNotaCredito.getProducto().getIva().getCodigo()+"");
            impuesto.setTarifa(new BigDecimal(detalleNotaCredito.getProducto().getIva().getTarifa()+""));
            impuesto.setBaseImponible(detalleNotaCredito.getTotal());
            impuesto.setValor(detalleNotaCredito.getIva());
            
            /**
             * Verificar valores para el total de impuesto
             */
            if(mapTotalImpuestos.get(detalleNotaCredito.getProducto().getIva())==null)
            {
                TotalImpuesto totalImpuesto=new TotalImpuesto();
                totalImpuesto.setBaseImponible(impuesto.getBaseImponible());
                totalImpuesto.setCodigo(impuesto.getCodigo());
                totalImpuesto.setCodigoPorcentaje(impuesto.getCodigoPorcentaje());
                totalImpuesto.setValor(impuesto.getValor());
                mapTotalImpuestos.put(detalleNotaCredito.getProducto().getIva(), totalImpuesto);
            }
            else
            {
                TotalImpuesto totalImpuesto=mapTotalImpuestos.get(detalleNotaCredito.getProducto().getIva());
                totalImpuesto.setBaseImponible(totalImpuesto.getBaseImponible().add(impuesto.getBaseImponible()));
                totalImpuesto.setValor(totalImpuesto.getValor().add(impuesto.getValor()));
                mapTotalImpuestos.put(detalleNotaCredito.getProducto().getIva(), totalImpuesto);
                
            }
            
            //-------------> FIN <----------------
            listaComprobantes.add(impuesto);
            
            detalle.setImpuestos(listaComprobantes);
            
            detallesComprobante.add(detalle);
        }
        
        notaCreditoComprobante.setDetalles(detallesComprobante);
        notaCreditoComprobante.setInfoNotaCredito(info);
        
                /**
         * Crear los impuestos totales
         */
        List<TotalImpuesto> totalImpuestos = new ArrayList<TotalImpuesto>();
        for (Map.Entry<ImpuestoDetalle, TotalImpuesto> entry : mapTotalImpuestos.entrySet()) {
            ImpuestoDetalle key = entry.getKey();
            TotalImpuesto value = entry.getValue();
            totalImpuestos.add(value);
        }
        notaCreditoComprobante.getInfoNotaCredito().setTotalImpuestos(totalImpuestos);
        
        return notaCreditoComprobante;
    }

    public NotaCredito getNotaCredito() {
        return notaCredito;
    }

    public void setNotaCredito(NotaCredito notaCredito) {
        this.notaCredito = notaCredito;
    }

    public Map<String, String> getMapInfoAdicional() {
        return mapInfoAdicional;
    }

    public void setMapInfoAdicional(Map<String, String> mapInfoAdicional) {
        this.mapInfoAdicional = mapInfoAdicional;
    }

    public List<String> getCorreosAdicionales() {
        return correosAdicionales;
    }

    public void setCorreosAdicionales(List<String> correosAdicionales) {
        this.correosAdicionales = correosAdicionales;
    }
    
    
    
}
