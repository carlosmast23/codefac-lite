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
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general.InformacionTributaria;
import ec.com.codesoft.codefaclite.servidor.entity.Factura;
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
        //setear todos los campos exepto la informacion tributaria que sa la iplementa sola
        return facturaComprobante;
    }



}
