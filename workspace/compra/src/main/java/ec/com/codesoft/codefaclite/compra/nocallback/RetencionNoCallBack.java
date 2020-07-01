/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.compra.nocallback;

import ec.com.codesoft.codefaclite.compra.callback.RetencionImplCallBack;
import ec.com.codesoft.codefaclite.compra.model.RetencionModel;
import ec.com.codesoft.codefaclite.controlador.aplicacion.ControladorCodefacInterface;
import ec.com.codesoft.codefaclite.controlador.comprobantes.ComprobanteRespuestaNoCallBack;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.utilidades.rmi.UtilidadesRmi;
import java.io.IOException;
 ;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author Carlos
 */
public class RetencionNoCallBack extends ComprobanteRespuestaNoCallBack{

    private RetencionModel retencionModel;
    public RetencionNoCallBack(ComprobanteEntity comprobante, RetencionModel panel) {
        super(comprobante, panel);
        this.retencionModel=panel;
    }

    
    
    @Override
    public void imprimirComprobante() {
        generarReportePdf(comprobante.getClaveAcceso());
    }
    
    private void generarReportePdf(String clave) {
        try {
            byte[] bytes = ServiceFactory.getFactory().getComprobanteServiceIf().getReporteComprobante(clave,comprobante.getEmpresa());
            JasperPrint jasperPrint = (JasperPrint) UtilidadesRmi.deserializar(bytes);
            retencionModel.panelPadre.crearReportePantalla(jasperPrint, clave);
            //facturacionModel.panelPadre.crearReportePantalla(jasperPrint, facturaProcesando.getPreimpreso());
        }catch (IOException ex) {
            Logger.getLogger(RetencionImplCallBack.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RetencionImplCallBack.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
}
