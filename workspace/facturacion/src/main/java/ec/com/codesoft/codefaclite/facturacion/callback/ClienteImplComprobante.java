/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.callback;

import ec.com.codesoft.codefaclite.controlador.comprobantes.MonitorComprobanteData;
import ec.com.codesoft.codefaclite.controlador.comprobantes.MonitorComprobanteModel;
import ec.com.codesoft.codefaclite.facturacion.model.FacturacionModel;
import ec.com.codesoft.codefaclite.facturacionelectronica.ClaveAcceso;
import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteElectronicoService;
import ec.com.codesoft.codefaclite.facturacionelectronica.exception.ComprobanteElectronicoException;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.servidorinterfaz.callback.ClienteInterfaceComprobante;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.ComprobanteDataFactura;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.FacturaEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.FacturacionServiceIf;
import ec.com.codesoft.ejemplo.utilidades.rmi.UtilidadesRmi;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
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
public class ClienteImplComprobante extends UnicastRemoteObject implements ClienteInterfaceComprobante {

    private FacturacionModel facturacionModel;
    private MonitorComprobanteData monitorData;
    private FacturacionServiceIf servicio;
    private Factura facturaProcesando;

    public ClienteImplComprobante(FacturacionModel facturacionModel, MonitorComprobanteData monitorData, FacturacionServiceIf servicio, Factura facturaProcesando) throws RemoteException {
        this.facturacionModel = facturacionModel;
        this.monitorData = monitorData;
        this.servicio = servicio;
        this.facturaProcesando = facturaProcesando;
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

                    facturacionModel.panelPadre.crearReportePantalla(jasperPrint, facturaProcesando.getPreimpreso());
                }
            });
        } catch (IOException ex) {
            Logger.getLogger(ClienteImplComprobante.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClienteImplComprobante.class.getName()).log(Level.SEVERE, null, ex);
        } 

    }

    @Override
    public void iniciado() {
        monitorData = MonitorComprobanteModel.getInstance().agregarComprobante();
        monitorData.getLblPreimpreso().setText(facturaProcesando.getPreimpreso() + " ");
        monitorData.getBtnAbrir().setEnabled(false);
        monitorData.getBtnReporte().setEnabled(false);
        monitorData.getBtnCerrar().setEnabled(false);
        monitorData.getBarraProgreso().setString(facturaProcesando.getPreimpreso());
        monitorData.getBarraProgreso().setStringPainted(true);
        MonitorComprobanteModel.getInstance().mostrar();

        facturaProcesando.setEstado(FacturaEnumEstado.SIN_AUTORIZAR.getEstado());
    }

    @Override
    public void procesando(int etapa, ClaveAcceso clave) throws RemoteException {
        if (etapa == ComprobanteElectronicoService.ETAPA_GENERAR) {
            monitorData.getBarraProgreso().setValue(20);
            facturaProcesando.setClaveAcceso(clave.clave);
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
            facturaProcesando.setEstado(FacturaEnumEstado.FACTURADO.getEstado());
        }

        if (etapa == ComprobanteElectronicoService.ETAPA_RIDE) {
            monitorData.getBarraProgreso().setValue(95);
            facturaProcesando.setEstado(FacturaEnumEstado.FACTURADO.getEstado());
        }

        if (etapa == ComprobanteElectronicoService.ETAPA_ENVIO_COMPROBANTE) {
            monitorData.getBarraProgreso().setValue(100);
            facturaProcesando.setEstado(FacturaEnumEstado.FACTURADO.getEstado());
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
                            facturacionModel.panelPadre.crearReportePantalla(jasperPrint, facturaProcesando.getPreimpreso());
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

            servicio.editar(facturaProcesando);
        } catch (RemoteException ex) {
            Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClienteImplComprobante.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClienteImplComprobante.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
