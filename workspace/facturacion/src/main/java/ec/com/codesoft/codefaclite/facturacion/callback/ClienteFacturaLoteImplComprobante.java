/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.callback;

import ec.com.codesoft.codefaclite.controlador.aplicacion.ControladorCodefacInterface;
import ec.com.codesoft.codefaclite.controlador.comprobantes.MonitorComprobanteData;
import ec.com.codesoft.codefaclite.controlador.comprobantes.MonitorComprobanteModel;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.facturacion.model.FacturacionModel;
import ec.com.codesoft.codefaclite.facturacion.model.ResultadoLoteAcademicoModel;
import ec.com.codesoft.codefaclite.facturacionelectronica.ClaveAcceso;
import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteElectronicoService;
import ec.com.codesoft.codefaclite.facturacionelectronica.evento.ListenerComprobanteElectronicoLote;
import ec.com.codesoft.codefaclite.facturacionelectronica.exception.ComprobanteElectronicoException;
import ec.com.codesoft.codefaclite.servidorinterfaz.callback.ClienteInterfaceComprobante;
import ec.com.codesoft.codefaclite.servidorinterfaz.callback.ClienteInterfaceComprobanteLote;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.ComprobanteData;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.FacturaEnumEstado;
import ec.com.codesoft.ejemplo.utilidades.rmi.UtilidadesRmi;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author Carlos
 */
public class ClienteFacturaLoteImplComprobante extends UnicastRemoteObject implements ClienteInterfaceComprobanteLote {

    private MonitorComprobanteData monitorData;
    private ControladorCodefacInterface controlador;

    public ClienteFacturaLoteImplComprobante(ControladorCodefacInterface controlador) throws RemoteException {
        this.controlador=controlador;
    }
    
    
    @Override
    public void iniciado() {        
        monitorData = MonitorComprobanteModel.getInstance().agregarComprobante();
        monitorData.getLblPreimpreso().setText("Factura Lote");
        monitorData.getBtnAbrir().setEnabled(false);
        monitorData.getBtnReporte().setEnabled(false);
        monitorData.getBtnCerrar().setEnabled(false);
        monitorData.getBarraProgreso().setString("Factura Lote");
        monitorData.getBarraProgreso().setStringPainted(true);
        MonitorComprobanteModel.getInstance().mostrar();
        
    }

    @Override
    public void procesando(int etapa) {
        if (etapa == ComprobanteElectronicoService.ETAPA_GENERAR) {
            monitorData.getBarraProgreso().setValue(20);
        }

        if (etapa == ComprobanteElectronicoService.ETAPA_PRE_VALIDAR) {
            monitorData.getBarraProgreso().setValue(30);
        }

        if (etapa == ComprobanteElectronicoService.ETAPA_FIRMAR) {
            monitorData.getBarraProgreso().setValue(50);
        }

        if (etapa == ComprobanteElectronicoService.ETAPA_ENVIAR) {
            monitorData.getBarraProgreso().setValue(70);
        }

        if (etapa == ComprobanteElectronicoService.ETAPA_AUTORIZAR) {
            monitorData.getBarraProgreso().setValue(90);
        }

        if (etapa == ComprobanteElectronicoService.ETAPA_RIDE) {
            monitorData.getBarraProgreso().setValue(95);
        }

        if (etapa == ComprobanteElectronicoService.ETAPA_ENVIO_COMPROBANTE) {
            monitorData.getBarraProgreso().setValue(100);
        }
    }

    @Override
    public void error() {
        monitorData.getBarraProgreso().setForeground(Color.GREEN);
        monitorData.getBtnAbrir().setEnabled(true);
        monitorData.getBtnCerrar().setEnabled(true);
        monitorData.getBtnAbrir().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //facturacionModel.panelPadre.crearReportePantalla(jasperPrint, facturaProcesando.getPreimpreso());
            }
        });

    }

    @Override
    public void termino(List<ComprobanteData> comprobantes) {
            monitorData.getBarraProgreso().setForeground(Color.GREEN);
            monitorData.getBtnAbrir().setEnabled(true);
            monitorData.getBtnCerrar().setEnabled(true);
            
            monitorData.getBtnAbrir().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ResultadoLoteAcademicoModel resultadoLote=new ResultadoLoteAcademicoModel(comprobantes);
                    controlador.panelPadre.crearVentanaCodefac(resultadoLote, true);
                    
                    //facturacionModel.panelPadre.crearReportePantalla(jasperPrint, facturaProcesando.getPreimpreso());
                }
            });
    }


}
