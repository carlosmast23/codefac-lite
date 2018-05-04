/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos;

import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteEnum;
import ec.com.codesoft.codefaclite.facturacionelectronica.evento.ListenerComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.retencion.DetalleRetencionComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.retencion.InformacionRetencion;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.retencion.RetencionComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.util.ComprobantesElectronicosUtil;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Retencion;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.RetencionDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriIdentificacion;
import ec.com.codesoft.ejemplo.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.ejemplo.utilidades.texto.UtilidadesTextos;
import ec.com.codesoft.ejemplo.utilidades.validadores.UtilidadValidador;
import es.mityc.firmaJava.libreria.utilidades.UtilidadFechas;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Carlos
 */
public class ComprobanteDataRetencion implements ComprobanteDataInterface,Serializable {
    
    private Retencion retencion;
    private Map<String,String> mapInfoAdicional;
    private List<String> correosAdicionales;
    private ListenerComprobanteElectronico listener;

    public ComprobanteDataRetencion(Retencion retencion) {
        this.retencion = retencion;
    }
    
    
    @Override
    public String getCodigoComprobante() {
        return ComprobanteEnum.COMPROBANTE_RETENCION.getCodigo();
    }

    @Override
    public String getSecuencial() {
        String secuencial = retencion.getSecuencial().toString();
        return UtilidadesTextos.llenarCarateresIzquierda(secuencial, 9, "0");
    }

    @Override
    public Map<String, String> getMapAdicional() {
        for (Map.Entry<String, String> entry : mapInfoAdicional.entrySet()) {
            String key = entry.getKey();
            String value = UtilidadValidador.normalizarTexto(entry.getValue());
            mapInfoAdicional.put(key, value);
        }
        return mapInfoAdicional;
    }

    @Override
    public List<String> getCorreos() {
        List<String> correos=new ArrayList<String>();
        if(retencion!=null && retencion.getProveedor()!=null)
            correos.add(retencion.getProveedor().getCorreoElectronico());
        
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
    public ComprobanteElectronico getComprobante() {
        RetencionComprobante retencionComprobante=new RetencionComprobante();
        InformacionRetencion info=new InformacionRetencion();
        
        //Revisar que codigo debe ir aqui , aunque en el SRI dice que es opcional
        info.setContribuyenteEspecial("123");
        info.setDirEstablecimiento(UtilidadValidador.normalizarTexto(retencion.getProveedor().getDireccion()));
        info.setFechaEmision(ComprobantesElectronicosUtil.dateToString(retencion.getFechaEmision()));
        
        if(retencion.getProveedor().getSriTipoIdentificacion().getCodigo().equals(SriIdentificacion.CEDULA_IDENTIFICACION))
            info.setIdentificacionSujetoRetenido(retencion.getProveedor().getIdentificacion());
        else
            info.setIdentificacionSujetoRetenido(UtilidadesTextos.llenarCarateresDerecha(retencion.getProveedor().getIdentificacion(), 13, "0"));
        /**
         * Verificar que valor no mas acepta
         */
        info.setObligadoContabilidad(retencion.getProveedor().getObligadoLlevarContabilidadEnum().getNombre().toUpperCase());
        info.setPeriodoFiscal(retencion.getPeriodoFiscal());
        info.setRazonSocialSujetoRetenido(retencion.getProveedor().getRazonSocial());
        
        //Todo: Revisar este caso porque en los clientes coincide pero para las proveedores ya no coincide el codigo de tipo de identifiacion
        info.setTipoIdentificacionSujetoRetenido(retencion.getProveedor().getSriTipoIdentificacion().getCodigo());

        /**
         * Llenar los detalles de las retenciones
         */
        List<DetalleRetencionComprobante> detalles=new ArrayList<DetalleRetencionComprobante>();
        
        for (RetencionDetalle detalle : retencion.getDetalles()) {
            
            DetalleRetencionComprobante detalleComprobante=new DetalleRetencionComprobante();
            
            detalleComprobante.setCodigo(detalle.getCodigoSri());
            detalleComprobante.setCodigoRetencion(detalle.getCodigoRetencionSri());
            
            detalleComprobante.setBaseImponible(detalle.getBaseImponible());
            detalleComprobante.setPorcentajeRetener(detalle.getPorcentajeRetener());
            detalleComprobante.setValorRetenido(detalle.getValorRetenido());
            
            //Todo: por el momento solo guardo el 001 porque solo se emiten retenciones de facturas, pero este campo deberia guardarse para los ats supongo
            detalleComprobante.setCodDocSustento("01");
            detalleComprobante.setNumDocSustento(retencion.getPreimpreso().replace("-",""));
            detalleComprobante.setFechaEmisionDocSustento(UtilidadesFecha.formatDate(retencion.getCompra().getFechaFactura(),"dd/MM/yyyy"));
            
            detalles.add(detalleComprobante);                                
        }

        retencionComprobante.setInfoRetencion(info);
        retencionComprobante.setDetalles(detalles);
        
        return retencionComprobante;
    }

    @Override
    public ListenerComprobanteElectronico getListenerComprobanteElectronico() {
        return listener;
    }

    @Override
    public Long getComprobanteId() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
