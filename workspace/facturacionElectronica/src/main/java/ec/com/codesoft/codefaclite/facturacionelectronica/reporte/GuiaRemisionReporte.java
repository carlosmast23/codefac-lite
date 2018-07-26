/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.reporte;

import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.guiaRetencion.GuiaRemisionComprobante;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Carlos
 */
public class GuiaRemisionReporte extends ComprobanteElectronicoReporte {

    private GuiaRemisionComprobante comprobante;
    
    public GuiaRemisionReporte(ComprobanteElectronico comprobante) {
        super(comprobante);
        this.comprobante = (GuiaRemisionComprobante) comprobante;
    }

    @Override
    public List<Object> getDetalles() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected Map<String, Object> getMapTotales() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected List getListFormasPago() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected Map<String, Object> getMapInfoCliente() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
