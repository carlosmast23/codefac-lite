/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.transporte.callback;

import ec.com.codesoft.codefaclite.controlador.comprobantes.MonitorComprobanteData;
import ec.com.codesoft.codefaclite.controlador.comprobantes.MonitorComprobanteModel;
import ec.com.codesoft.codefaclite.facturacionelectronica.ClaveAcceso;
import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteElectronicoService;
import ec.com.codesoft.codefaclite.facturacionelectronica.exception.ComprobanteElectronicoException;
import ec.com.codesoft.codefaclite.servidorinterfaz.callback.ClienteInterfaceComprobante;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.GuiaRemision;
import ec.com.codesoft.codefaclite.transporte.model.GuiaRemisionModel;
import ec.com.codesoft.codefaclite.utilidades.rmi.UtilidadesRmi;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author Carlos
 */
public class GuiaRemisionImplComprobante extends UnicastRemoteObject implements ClienteInterfaceComprobante {


    private GuiaRemisionModel guiaRemisionModel;
    private MonitorComprobanteData monitorData;
    private GuiaRemision guiaRemision;

    public GuiaRemisionImplComprobante(GuiaRemisionModel guiaRemisionModel, GuiaRemision guiaRemision) throws RemoteException {
        this.guiaRemisionModel = guiaRemisionModel;
        this.guiaRemision = guiaRemision;
    }

    @Override
    public void termino(byte[] byteJasperPrint) throws RemoteException {

        try {
            
            JasperPrint jasperPrint = (JasperPrint) UtilidadesRmi.deserializar(byteJasperPrint);
            monitorData.getBarraProgreso().setForeground(Color.GREEN);
            monitorData.getBtnAbrir().setEnabled(true);
            monitorData.getBtnCerrar().setEnabled(true);
            monitorData.getBtnAbrir().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    guiaRemisionModel.panelPadre.crearReportePantalla(jasperPrint, guiaRemision.getPreimpreso());
                }
            });
        } catch (IOException ex) {
            Logger.getLogger(GuiaRemisionImplComprobante.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GuiaRemisionImplComprobante.class.getName()).log(Level.SEVERE, null, ex);
        } 

    }

    @Override
    public void iniciado() {
        monitorData = MonitorComprobanteModel.getInstance().agregarComprobante();
        monitorData.getLblPreimpreso().setText(guiaRemision.getPreimpreso() + " ");
        monitorData.getBtnAbrir().setEnabled(false);
        monitorData.getBtnReporte().setEnabled(false);
        monitorData.getBtnCerrar().setEnabled(false);
        monitorData.getBarraProgreso().setString(guiaRemision.getPreimpreso());
        monitorData.getBarraProgreso().setStringPainted(true);
        MonitorComprobanteModel.getInstance().mostrar();
        
    }

    @Override
    public void procesando(int etapa, ClaveAcceso clave) throws RemoteException {
        if (etapa == ComprobanteElectronicoService.ETAPA_GENERAR) {
            monitorData.getBarraProgreso().setValue(20);
            guiaRemision.setClaveAcceso(clave.clave);
        }

        if (etapa == ComprobanteElectronicoService.ETAPA_PRE_VALIDAR) {
            monitorData.getBarraProgreso().setValue(30);
        }

        if (etapa == ComprobanteElectronicoService.ETAPA_FIRMAR) {
            monitorData.getBarraProgreso().setValue(50);
        }
        
        if (etapa == ComprobanteElectronicoService.ETAPA_RIDE) {
            monitorData.getBarraProgreso().setValue(65);
            guiaRemision.setEstado(ComprobanteEntity.ComprobanteEnumEstado.AUTORIZADO.getEstado());
            
            monitorData.getBtnAbrir().setEnabled(true);
            monitorData.getBtnAbrir().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    generarReportePdf(clave.clave);
                }
            });
        }

        if (etapa == ComprobanteElectronicoService.ETAPA_ENVIO_COMPROBANTE) {
            monitorData.getBarraProgreso().setValue(80);
            guiaRemision.setEstado(ComprobanteEntity.ComprobanteEnumEstado.AUTORIZADO.getEstado());
        }

        if (etapa == ComprobanteElectronicoService.ETAPA_ENVIAR) {
            monitorData.getBarraProgreso().setValue(90);
        }

        if (etapa == ComprobanteElectronicoService.ETAPA_AUTORIZAR) {
            monitorData.getBarraProgreso().setValue(100);
            guiaRemision.setEstado(ComprobanteEntity.ComprobanteEnumEstado.AUTORIZADO.getEstado());
        }

    }

    @Override
    public void error(ComprobanteElectronicoException cee,String claveAcceso) throws RemoteException {
        try {
            byte[] resporteSerializado = ServiceFactory.getFactory().getComprobanteServiceIf().getReporteComprobante(claveAcceso);
            JasperPrint jasperPrint = (JasperPrint) UtilidadesRmi.deserializar(resporteSerializado);
            monitorData.getBtnReporte().setEnabled(true);
            monitorData.getBtnCerrar().setEnabled(true);
            monitorData.getBtnReporte().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(null, "Etapa: " + cee.getEtapa() + "\n" + cee.getMessage());
                    monitorData.getBtnAbrir().addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            guiaRemisionModel.panelPadre.crearReportePantalla(jasperPrint, guiaRemision.getPreimpreso());
                            //JasperPrint print = facturaElectronica.getServicio().getPrintJasper();
                            //panelPadre.crearReportePantalla(print, facturaProcesando.getPreimpreso());
                        }
                    });
                }
            });

            if (cee.getTipoError().equals(ComprobanteElectronicoException.ERROR_ENVIO_CLIENTE)) {
                monitorData.getBtnAbrir().setEnabled(true);
                monitorData.getBarraProgreso().setForeground(Color.YELLOW);
            } else {
                monitorData.getBarraProgreso().setForeground(Color.ORANGE);
            }

            //servicio.editar(facturaProcesando);
        } catch (RemoteException ex) {
            Logger.getLogger(GuiaRemisionImplComprobante.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GuiaRemisionImplComprobante.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GuiaRemisionImplComprobante.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void generarReportePdf(String clave) {
        try {

                byte[] bytes = ServiceFactory.getFactory().getComprobanteServiceIf().getReporteComprobante(clave);
                JasperPrint jasperPrint = (JasperPrint) UtilidadesRmi.deserializar(bytes);
                guiaRemisionModel.panelPadre.crearReportePantalla(jasperPrint, clave);
            } catch (RemoteException ex) {
            Logger.getLogger(GuiaRemisionImplComprobante.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GuiaRemisionImplComprobante.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GuiaRemisionImplComprobante.class.getName()).log(Level.SEVERE, null, ex);
        }

    }    
}
