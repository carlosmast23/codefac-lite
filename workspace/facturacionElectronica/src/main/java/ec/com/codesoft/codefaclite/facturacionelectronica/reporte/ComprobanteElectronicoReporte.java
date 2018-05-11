/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.reporte;

import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteElectronicoService;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general.InformacionAdicional;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Carlos
 */
public abstract class ComprobanteElectronicoReporte 
{
    public ComprobanteElectronico comprobante;
    //public InputStream imagenEmpresa;
    
    public abstract List<Object> getDetalles();
    protected abstract Map<String,Object> getMapTotales();
    protected abstract List getListFormasPago();
    protected abstract Map<String,Object> getMapInfoCliente();

    public ComprobanteElectronicoReporte(ComprobanteElectronico comprobante) {
        this.comprobante = comprobante;
    }
    
    
    
    protected Map<String,Object> getMapInfoTributaria()
    {
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("ruc",comprobante.getInformacionTributaria().getRuc());
        String secuencial=comprobante.getInformacionTributaria().getPuntoEmision()+"-"+comprobante.getInformacionTributaria().getEstablecimiento()+"-"+comprobante.getInformacionTributaria().getSecuencial();
        map.put("secuencial",secuencial);
        map.put("autorizacion",comprobante.getInformacionTributaria().getClaveAcceso());
        //map.put("estado","");
        map.put("fecha_hora_autorizacion","");
        String codAmbiente=comprobante.getInformacionTributaria().getAmbiente();
        if(codAmbiente.equals(ComprobanteElectronicoService.CODIGO_SRI_MODO_PRODUCCION.toString()))
             map.put("ambiente","producción");
        else
            map.put("ambiente",ComprobanteElectronicoService.MODO_PRUEBAS);
        
        return map;
    }
    
    protected Map<String, Object> getMapInfoEmpresa() {
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("razon_social",comprobante.getInformacionTributaria().getRazonSocial());
        map.put("nombre_legal",comprobante.getInformacionTributaria().getNombreComercial());
        map.put("direccion",comprobante.getInformacionTributaria().getDirecionMatriz());
        //Cambiar por la direccion de las sucursales cuando exista
        map.put("direccion_sucursal",comprobante.getInformacionTributaria().getDirecionMatriz());
        map.put("contribuyente_especial","");
        map.put("obligado_contabilidad","");
        return map;
    }
    
    public List<InformacionAdicional> getListInfoAdifional()
    {
        return comprobante.getInformacionAdicional();
    }
    

    public Map<String,Object> getMapReporte()
    {
        //try {
            Map<String,Object> map=new HashMap<String,Object>();
            map.putAll(getMapInfoTributaria());
            map.putAll(getMapInfoEmpresa());
            map.putAll(getMapTotales());
            map.putAll(getMapInfoCliente());
            
            //map.put("imagen_logo",(imagenEmpresa!=null)?IOUtils.toBufferedInputStream(imagenEmpresa):null);
            map.put("formaPagoList", getListFormasPago());
            map.put("informacionAdicionalList", getListInfoAdifional());
            return map;
        //} catch (IOException ex) {
        //    Logger.getLogger(ComprobanteElectronicoReporte.class.getName()).log(Level.SEVERE, null, ex);
        //}
        //return null;
    }
    
}
