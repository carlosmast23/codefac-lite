/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.comprobantes;

import ec.com.codesoft.codefaclite.controlador.session.SessionCodefacInterface;
import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteElectronicoService;
import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteEnum;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general.InformacionAdicional;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general.InformacionTributaria;
import ec.com.codesoft.codefaclite.servidor.entity.ParametroCodefac;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Carlos
 */
public abstract class ComprobanteElectronicoAbstract <T extends ComprobanteElectronico> {
    private SessionCodefacInterface session;
    private ComprobanteElectronicoService servicio;
    
    public abstract String getCodigoComprobante();
    public abstract String getSecuencial();
    public abstract Map<String,String> getMapAdicional();
    //public abstract List<InformacionAdicional> getInformacionAdicional();
    /**
     * Implementar el modelo del comprobante exeptuando
     * los valores de informacion tributaria que se implementa solo
     * @return 
     */
    public abstract T getComprobante();

    public ComprobanteElectronicoAbstract(SessionCodefacInterface session) {
        this.session = session;
    }
    
    public InformacionTributaria getInfoInformacionTributaria()
    {
        InformacionTributaria infoTributaria=new InformacionTributaria();
        if(session.getParametrosCodefac().get(ParametroCodefac.MODO_FACTURACION).valor.equals(ComprobanteElectronicoService.MODO_PRODUCCION))
        {
           infoTributaria.setAmbiente(ComprobanteElectronicoService.CODIGO_SRI_MODO_PRODUCCION+"");
        }
        else
        {
           infoTributaria.setAmbiente(ComprobanteElectronicoService.CODIGO_SRI_MODO_PRUEBAS+"");
        }

        infoTributaria.setClaveAcceso("");
        infoTributaria.setCodigoDocumento(getCodigoComprobante());
        infoTributaria.setDirecionMatriz(session.getEmpresa().getDireccion());
        infoTributaria.setEstablecimiento("001");
        infoTributaria.setNombreComercial(session.getEmpresa().getNombreLegal());
        infoTributaria.setPuntoEmision("001");
        infoTributaria.setRazonSocial(session.getEmpresa().getRazonSocial());
        infoTributaria.setRuc(session.getEmpresa().getIdentificacion());
        infoTributaria.setSecuencial(getSecuencial());
        infoTributaria.setTipoEmision(ComprobanteElectronico.MODO_FACTURACION_NORMAL);
        return infoTributaria;
    }
    
    public void procesarComprobante()
    {
        /**
         * Crear el servicio para facturar
         */
        T comprobante=getComprobante();
        comprobante.setInformacionTributaria(getInfoInformacionTributaria());
        comprobante.setInformacionAdicional(getInformacionAdicional());
        servicio = new ComprobanteElectronicoService(
                session.getParametrosCodefac().get(ParametroCodefac.DIRECTORIO_RECURSOS).valor,
                session.getParametrosCodefac().get(ParametroCodefac.NOMBRE_FIRMA_ELECTRONICA).valor,
                session.getParametrosCodefac().get(ParametroCodefac.CLAVE_FIRMA_ELECTRONICA).valor,
                session.getParametrosCodefac().get(ParametroCodefac.MODO_FACTURACION).valor,
                comprobante);
        
       
        /**
         * Setear variables de configuracion de los comprobantes electronicos
         */
        String modoFacturacion=session.getParametrosCodefac().get(ParametroCodefac.MODO_FACTURACION).valor;
        servicio.setModoFacturacion(modoFacturacion);
        
        if(ComprobanteElectronicoService.MODO_PRODUCCION.equals(modoFacturacion))
        {
            String autorizacion=session.getParametrosCodefac().get(ParametroCodefac.SRI_WS_AUTORIZACION).valor;
            servicio.setUriAutorizacion(autorizacion);
            
            String recepcion=session.getParametrosCodefac().get(ParametroCodefac.SRI_WS_RECEPCION).valor;
            servicio.setUriRecepcion(recepcion);
            
        }
        else
        {
            String autorizacion = session.getParametrosCodefac().get(ParametroCodefac.SRI_WS_AUTORIZACION_PRUEBA).valor;
            servicio.setUriAutorizacion(autorizacion);

            String recepcion = session.getParametrosCodefac().get(ParametroCodefac.SRI_WS_RECEPCION_PRUEBA).valor;
            servicio.setUriRecepcion(recepcion);
        }
        
        servicio.procesarComprobante();
        
    }
    
    private List<InformacionAdicional> getInformacionAdicional()
    {
        List<InformacionAdicional> listaInfoAdicional = new ArrayList<InformacionAdicional>();
        
        Map<String,String> map=getMapAdicional();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();            
            InformacionAdicional info=new InformacionAdicional();
            info.setNombre(key);
            info.setValor(value);
            listaInfoAdicional.add(info);
        }
        return listaInfoAdicional;        
    }
    
    
    
}
