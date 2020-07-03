/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.transporte.nocallback;

import ec.com.codesoft.codefaclite.controlador.aplicacion.ControladorCodefacInterface;
import ec.com.codesoft.codefaclite.controlador.comprobantes.ComprobanteRespuestaNoCallBack;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.GuiaRemision;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.transporte.callback.GuiaRemisionImplComprobante;
import ec.com.codesoft.codefaclite.transporte.model.GuiaRemisionModel;
import ec.com.codesoft.codefaclite.utilidades.rmi.UtilidadesRmi;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author Carlos
 */
public class GuiaRemisionNoCallBack extends ComprobanteRespuestaNoCallBack {

    private GuiaRemisionModel guiaRemisionModel;

    public GuiaRemisionNoCallBack(GuiaRemision guiaRemision, GuiaRemisionModel panel) {
        super(guiaRemision, panel);
        this.guiaRemisionModel = panel;
    }

    @Override
    public void imprimirComprobante() {
        generarReportePdf(comprobante.getClaveAcceso());
    }

    private void generarReportePdf(String clave) {

        try {

            if (verificarImprimirComprobante()) {
                guiaRemisionModel.imprimirComprobanteGuiaRemision((GuiaRemision) comprobante); //TODO:Verificar si este metodo no funciona
            } else {
                byte[] bytes = ServiceFactory.getFactory().getComprobanteServiceIf().getReporteComprobante(clave,comprobante.getEmpresa());
                JasperPrint jasperPrint = (JasperPrint) UtilidadesRmi.deserializar(bytes);
                guiaRemisionModel.panelPadre.crearReportePantalla(jasperPrint, clave);
            }
            //facturacionModel.panelPadre.crearReportePantalla(jasperPrint, facturaProcesando.getPreimpreso());
        } catch (RemoteException ex) {
            Logger.getLogger(GuiaRemisionImplComprobante.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GuiaRemisionImplComprobante.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GuiaRemisionImplComprobante.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private boolean verificarImprimirComprobante() {
        ParametroCodefac parametroCodefac = guiaRemisionModel.session.getParametrosCodefac().get(ParametroCodefac.COMPROBANTE_GUIA_REMISION_ACTIVAR);
        if (parametroCodefac == null) {
            //Si no esta tiene ningun dato por defecto no habilito la opcion de comprobante de venta
            return false;
        } else {
            if (EnumSiNo.getEnumByLetra(parametroCodefac.getValor()).equals(EnumSiNo.NO)) {
                //Si esta marcado la opcion no entonce no genero la opcion de imprimir el comprobante de venta
                return false;
            }
        }
        return true;
    }

}
