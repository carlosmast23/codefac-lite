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
import java.util.List;

/**
 *
 * @author Carlos
 */
public abstract class ComprobanteElectronicoAbstract <T extends ComprobanteElectronico> {
    private SessionCodefacInterface session;
    private ComprobanteElectronicoService servicio;
    
    public abstract String getCodigoComprobante();
    public abstract String getSecuencial();
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
        infoTributaria.setAmbiente(session.getParametrosCodefac().get(ParametroCodefac.MODO_FACTURACION).valor);
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
        servicio = new ComprobanteElectronicoService(
                session.getParametrosCodefac().get(ParametroCodefac.DIRECTORIO_RECURSOS).valor,
                session.getParametrosCodefac().get(ParametroCodefac.NOMBRE_FIRMA_ELECTRONICA).valor,
                session.getParametrosCodefac().get(ParametroCodefac.CLAVE_FIRMA_ELECTRONICA).valor,
                session.getParametrosCodefac().get(ParametroCodefac.MODO_FACTURACION).valor,
                comprobante);
        
        servicio.procesarComprobante();
        
    }
    
    
    
}
