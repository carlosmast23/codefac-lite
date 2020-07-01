/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos;

import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.InformacionComprobanteAbstract;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general.ImpuestoComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general.TotalImpuesto;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.util.ComprobantesElectronicosUtil;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteVentaNotaCreditoAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.DetalleFacturaNotaCeditoAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ImpuestoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Presupuesto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriIdentificacion;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroEstudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.respuesta.ReferenciaDetalleFacturaRespuesta;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SriIdentificacionServiceIf;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import ec.com.codesoft.codefaclite.utilidades.validadores.UtilidadValidador;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
 ;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CARLOS_CODESOFT
 */
public abstract class ComprobanteDataFacturaNotaCreditoAbstract implements ComprobanteDataInterface ,Serializable{
 
    
    public void llenarInformacionComprobante(InfoComprobanteInterface informacionComprobante,ComprobanteVentaNotaCreditoAbstract comprobante)
    {
        informacionComprobante.setFechaEmision(ComprobantesElectronicosUtil.dateToString(new java.sql.Date(comprobante.getFechaEmision().getTime())));
        //informacionComprobante.setDireccion(); //TODO: Este campo no son iguales en factura y nota de credito
        informacionComprobante.setDirEstablecimiento(UtilidadValidador.normalizarTexto(comprobante.getDireccionEstablecimiento()));
        
        //SriIdentificacionServiceIf servicioSri = ServiceFactory.getFactory().getSriIdentificacionServiceIf();
        SriIdentificacion sriIdentificacion = getSriIdentificacion(comprobante);
        /*try {
            sriIdentificacion = servicioSri.obtenerPorTransaccionEIdentificacion(comprobante.getCliente().getTipoIdentificacionEnum(), SriIdentificacion.tipoTransaccionEnum.VENTA);
        } catch (Exception ex) {
            Logger.getLogger(ComprobanteDataFacturaNotaCreditoAbstract.class.getName()).log(Level.SEVERE, null, ex);
        }*/

        if (sriIdentificacion != null && sriIdentificacion.getCodigo().equals(SriIdentificacion.CEDULA_IDENTIFICACION)) {
            informacionComprobante.setIdentificacion(comprobante.getCliente().getIdentificacion());
        } else {
            informacionComprobante.setIdentificacion(UtilidadesTextos.llenarCarateresDerecha(comprobante.getCliente().getIdentificacion(), 13, "0"));
        }
        //informacionComprobante.setIdentificacion(comprobante.getIdentificacion());
        
        //informacionComprobante.setImporteTotal(BigDecimal.ONE); //NOTA:Solo tiene en factura
        informacionComprobante.setObligadoContabilidad(comprobante.getObligadoLlevarContabilidad());
        //informacionComprobante.setRazonSocial(comprobante.getCliente().getRazonSocial()); /TODO: NO se puede generaliar porque solo tiene la factura
        informacionComprobante.setTipoIdentificacion(getSriIdentificacion(comprobante).getCodigo());
        //informacionComprobante.setTotalDescuento(BigDecimal.ONE); TODO: No se puede generalizar
        //informacionComprobante.setTotalSinImpuestos(BigDecimal.ONE);
        //informacionComprobante.setTotalImpuestos(list);
        
        
    }
    
    public List<ImpuestoComprobante> calcularImpuestos(Map<Integer, TotalImpuesto> mapTotalImpuestos, DetalleFacturaNotaCeditoAbstract comprobanteDetalle) {
        ReferenciaDetalleFacturaRespuesta respuesta;
        try {
            respuesta = ServiceFactory.getFactory().getFacturacionServiceIf().obtenerReferenciaDetalleFactura(comprobanteDetalle.getTipoDocumentoEnum(), comprobanteDetalle.getReferenciaId());
            CatalogoProducto catalogoProducto = respuesta.catalogoProducto;

            List<ImpuestoComprobante> listaComprobantes = new ArrayList<ImpuestoComprobante>();
            ImpuestoComprobante impuesto = new ImpuestoComprobante();
            impuesto.setCodigo(catalogoProducto.getIva().getImpuesto().getCodigoSri());
            impuesto.setCodigoPorcentaje(catalogoProducto.getIva().getCodigo() + "");
            impuesto.setTarifa(new BigDecimal(catalogoProducto.getIva().getTarifa() + ""));
            impuesto.setBaseImponible(comprobanteDetalle.totalSinImpuestosConIce().setScale(2, RoundingMode.HALF_UP));
            impuesto.setValor(comprobanteDetalle.getIva().setScale(2, RoundingMode.HALF_UP));

            /**
             * Verificar valores para el total de impuesto
             */
            sumarizarTotalesImpuestos(mapTotalImpuestos, catalogoProducto.getIva(), impuesto);
            listaComprobantes.add(impuesto);

            /**
             * Agregando el valor del ICE
             */
            if (catalogoProducto.getIce() != null) {
                ImpuestoComprobante impuestoIce = new ImpuestoComprobante();
                impuestoIce.setCodigo(catalogoProducto.getIce().getImpuesto().getCodigoSri());
                impuestoIce.setCodigoPorcentaje(catalogoProducto.getIce().getCodigo() + "");
                impuestoIce.setTarifa(new BigDecimal(catalogoProducto.getIce().getPorcentaje() + ""));
                impuestoIce.setBaseImponible(comprobanteDetalle.getTotal().setScale(2, RoundingMode.HALF_UP));
                impuestoIce.setValor(comprobanteDetalle.getValorIce().setScale(2, RoundingMode.HALF_UP));
                sumarizarTotalesImpuestos(mapTotalImpuestos, catalogoProducto.getIce(), impuestoIce);
                listaComprobantes.add(impuestoIce);

            }
            return listaComprobantes;
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ComprobanteDataFacturaNotaCreditoAbstract.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private void sumarizarTotalesImpuestos(Map<Integer, TotalImpuesto> mapTotalImpuestos, ImpuestoDetalle impuestoDetalle, ImpuestoComprobante impuesto) {
        /**
         * Verificar valores para el total de impuesto
         */
                
        if (mapTotalImpuestos.get(impuestoDetalle.getCodigo()) == null ) {
            TotalImpuesto totalImpuesto = new TotalImpuesto();
            totalImpuesto.setBaseImponible(impuesto.getBaseImponible());
            totalImpuesto.setCodigo(impuesto.getCodigo());
            totalImpuesto.setCodigoPorcentaje(impuesto.getCodigoPorcentaje());
            totalImpuesto.setValor(impuesto.getValor());
            //totalImpuesto.setDescuentoAdicional(detalle.getDescuento().toString());
            mapTotalImpuestos.put(impuestoDetalle.getCodigo(), totalImpuesto);
        } else {
            TotalImpuesto totalImpuesto = mapTotalImpuestos.get(impuestoDetalle.getCodigo());
            totalImpuesto.setBaseImponible(totalImpuesto.getBaseImponible().add(impuesto.getBaseImponible()));
            totalImpuesto.setValor(totalImpuesto.getValor().add(impuesto.getValor()));
            //totalImpuesto.setDescuentoAdicional(detalle.getDescuento().toString());
            mapTotalImpuestos.put(impuestoDetalle.getCodigo(), totalImpuesto);

        }
    }
    
    public List<TotalImpuesto> crearImpuestosTotales(Map<Integer, TotalImpuesto> mapTotalImpuestos)
    {
        List<TotalImpuesto> totalImpuestos = new ArrayList<TotalImpuesto>();
        for (Map.Entry<Integer, TotalImpuesto> entry : mapTotalImpuestos.entrySet()) {
            Integer key = entry.getKey();
            TotalImpuesto value = entry.getValue();
            totalImpuestos.add(value);
        }
        return totalImpuestos;
    }
    
    public SriIdentificacion getSriIdentificacion(ComprobanteVentaNotaCreditoAbstract comprobante)
    {
        SriIdentificacionServiceIf servicioSri = ServiceFactory.getFactory().getSriIdentificacionServiceIf();
        SriIdentificacion sriIdentificacion = null;
        try {
            sriIdentificacion = servicioSri.obtenerPorTransaccionEIdentificacion(comprobante.getCliente().getTipoIdentificacionEnum(), SriIdentificacion.tipoTransaccionEnum.VENTA);
            return sriIdentificacion;
        } catch (Exception ex) {
            Logger.getLogger(ComprobanteDataFacturaNotaCreditoAbstract.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public interface InfoComprobanteInterface
    {
        public void setFechaEmision(String fechaEmision);
        public void setDirEstablecimiento(String dirEstablecimiento);
        public void setIdentificacion(String identificacion);
        public void setObligadoContabilidad(String obligadoContabilidad);
        public void setTipoIdentificacion(String tipoIdentificacion);
        
        
        
    }
}
