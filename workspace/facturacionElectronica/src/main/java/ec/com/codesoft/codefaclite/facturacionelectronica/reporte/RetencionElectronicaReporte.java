/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.reporte;

import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.retencion.DetalleRetencionComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.retencion.DocumentoSustento;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.retencion.RetencionComprobante;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Carlos
 */
public class RetencionElectronicaReporte extends ComprobanteElectronicoReporte{
    
    private RetencionComprobante retencionComprobante;
    private Map<String,String> mapCodeAndNameTipoRetecion;        
    private Map<String,String> mapCodeAndNameTipoDocumento;

    public RetencionElectronicaReporte(ComprobanteElectronico retencionComprobante) {
        super(retencionComprobante);
        this.retencionComprobante = (RetencionComprobante) retencionComprobante;
    }
    

    @Override
    public List<Object> getDetalles() {
        List<Object> detalles=new ArrayList<Object>();
        for (DocumentoSustento documentoSustento : retencionComprobante.getDocsSustento()) {
            
            for (DetalleRetencionComprobante detalleComprobante : documentoSustento.getRetenciones()) 
            {            
                DetalleRetencionReporteData reporteData=new DetalleRetencionReporteData();

                reporteData.setComprobante(mapCodeAndNameTipoDocumento.get(documentoSustento.getCodDocSustento()));
                reporteData.setNumero(documentoSustento.getNumDocSustento());
                reporteData.setFechaEmision(documentoSustento.getFechaEmisionDocSustento());
                reporteData.setEjercicioFiscal(retencionComprobante.getInfoRetencion().getPeriodoFiscal());
                reporteData.setBaseImponible(detalleComprobante.getBaseImponible().toString());
                reporteData.setImpuesto(mapCodeAndNameTipoRetecion.get(detalleComprobante.getCodigo()));
                reporteData.setCodigo(detalleComprobante.getCodigoRetencion());
                reporteData.setPorcentajeRetencion(detalleComprobante.getPorcentajeRetener().toString());
                reporteData.setValorRetenido(detalleComprobante.getValorRetenido().toString());

                detalles.add(reporteData);
            }
            
        }        
        return detalles;
    }

    @Override
    protected Map<String, Object> getMapTotales() {
        return new HashMap<String,Object>();
    }

    @Override
    protected List getListFormasPago() {
        return new ArrayList<Object>();
    }

    @Override
    protected Map<String, Object> getMapInfoCliente() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("cliente_nombres", retencionComprobante.getInfoRetencion().getRazonSocialSujetoRetenido());
        map.put("cliente_identificacion", retencionComprobante.getInfoRetencion().getIdentificacionSujetoRetenido());
        map.put("fecha_emision", retencionComprobante.getInfoRetencion().getFechaEmision());
        map.put("obligado_contabilidad", retencionComprobante.getInfoRetencion().getObligadoContabilidad());
        return map;
    }

    public Map<String, String> getMapCodeAndNameTipoRetecion() {
        return mapCodeAndNameTipoRetecion;
    }

    public void setMapCodeAndNameTipoRetecion(Map<String, String> mapCodeAndNameTipoRetecion) {
        this.mapCodeAndNameTipoRetecion = mapCodeAndNameTipoRetecion;
    }

    public Map<String, String> getMapCodeAndNameTipoDocumento() {
        return mapCodeAndNameTipoDocumento;
    }

    public void setMapCodeAndNameTipoDocumento(Map<String, String> mapCodeAndNameTipoDocumento) {
        this.mapCodeAndNameTipoDocumento = mapCodeAndNameTipoDocumento;
    }
    
    
    
}
