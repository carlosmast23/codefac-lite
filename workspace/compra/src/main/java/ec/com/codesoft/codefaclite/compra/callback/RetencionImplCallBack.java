/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.compra.callback;

import ec.com.codesoft.codefaclite.controlador.aplicacion.ControladorCodefacInterface;
import ec.com.codesoft.codefaclite.controlador.comprobantes.MonitorComprobanteData;
import ec.com.codesoft.codefaclite.controlador.comprobantes.MonitorComprobanteModel;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.facturacionelectronica.AlertaComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.ClaveAcceso;
import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteElectronicoService;
import ec.com.codesoft.codefaclite.facturacionelectronica.exception.ComprobanteElectronicoException;
import ec.com.codesoft.codefaclite.servidorinterfaz.callback.ClienteInterfaceComprobante;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Retencion;
import ec.com.codesoft.codefaclite.utilidades.rmi.UtilidadesRmi;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.Serializable;
 ;
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
public class RetencionImplCallBack   implements ClienteInterfaceComprobante,Serializable{

    private MonitorComprobanteData monitorData;
    private Retencion retencion;
    private transient ControladorCodefacInterface formulario;

    public RetencionImplCallBack(Retencion retencion, ControladorCodefacInterface formulario)    {
        this.retencion = retencion;
        this.formulario = formulario;
    }

    
    @Override
    public void termino(byte[] byteJasperPrint,List<AlertaComprobanteElectronico> alertas)    {
        
        try {
            
            JasperPrint jasperPrint = (JasperPrint) UtilidadesRmi.deserializar(byteJasperPrint);
            monitorData.getBarraProgreso().setForeground(Color.GREEN);
            monitorData.getBtnAbrir().setEnabled(true);
            monitorData.getBtnCerrar().setEnabled(true);

            // Agregado mensaje de advertencia cuando se factura en el modo offline
            /*
            if(facturacionOffline)
            {
                monitorData.getBtnReporte().setEnabled(true);
                monitorData.getBtnReporte().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        DialogoCodefac.mensaje("Advertencia","Recuerde Autorizar el comprobante en el SRI, \n Si esta sin internet mandar por correo el RIDE", DialogoCodefac.MENSAJE_ADVERTENCIA);
                    }
                });
            }*/
            formulario.panelPadre.actualizarNotificacionesCodefac();
            
        } catch (IOException ex) {
            Logger.getLogger(RetencionImplCallBack.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RetencionImplCallBack.class.getName()).log(Level.SEVERE, null, ex);
        } 
    
    }

    @Override
    public void iniciado()    {
        monitorData = MonitorComprobanteModel.getInstance().agregarComprobante();
        monitorData.getLblPreimpreso().setText(retencion.getPreimpreso() + " ");
        monitorData.getBtnAbrir().setEnabled(false);
        monitorData.getBtnReporte().setEnabled(false);
        monitorData.getBtnCerrar().setEnabled(false);
        monitorData.getBarraProgreso().setString(retencion.getPreimpreso());
        monitorData.getBarraProgreso().setStringPainted(true);
        MonitorComprobanteModel.getInstance().mostrar();
    }

    @Override
    public void procesando(int etapa, ClaveAcceso clave)    {
        
        if (etapa == ComprobanteElectronicoService.ETAPA_GENERAR) {
            monitorData.getBarraProgreso().setValue(20);
            retencion.setClaveAcceso(clave.clave);
        }

        if (etapa == ComprobanteElectronicoService.ETAPA_PRE_VALIDAR) {
            monitorData.getBarraProgreso().setValue(30);
        }

        if (etapa == ComprobanteElectronicoService.ETAPA_FIRMAR) {
            monitorData.getBarraProgreso().setValue(50);
        }
        
        if (etapa == ComprobanteElectronicoService.ETAPA_RIDE) {
            monitorData.getBarraProgreso().setValue(65);
            retencion.setEstado(ComprobanteEntity.ComprobanteEnumEstado.AUTORIZADO.getEstado());
                        
            //En esta etapa ya se habilita la opcion de imprimir el ride porque ya esta generado
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
            retencion.setEstado(ComprobanteEntity.ComprobanteEnumEstado.AUTORIZADO.getEstado());            
        }

        if (etapa == ComprobanteElectronicoService.ETAPA_ENVIAR) {
            monitorData.getBarraProgreso().setValue(90);
        }

        if (etapa == ComprobanteElectronicoService.ETAPA_AUTORIZAR) {
            monitorData.getBarraProgreso().setValue(100);
            retencion.setEstado(ComprobanteEntity.ComprobanteEnumEstado.AUTORIZADO.getEstado());
        }
        
    }

    @Override
    public void error(ComprobanteElectronicoException cee, String claveAcceso)    {
        monitorData.getBtnReporte().setEnabled(true);
        monitorData.getBtnCerrar().setEnabled(true);
        monitorData.getBtnReporte().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Etapa: " + cee.getEtapa() + "\n" + cee.getMessage());
                /*
                monitorData.getBtnAbrir().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                facturacionModel.panelPadre.crearReportePantalla(jasperPrint, facturaProcesando.getPreimpreso());
                
                }
                });*/
            }
        });
        if (cee.getTipoError().equals(ComprobanteElectronicoException.ERROR_ENVIO_CLIENTE)) {
            monitorData.getBtnAbrir().setEnabled(true);
            monitorData.getBarraProgreso().setForeground(Color.YELLOW);
        } else {
            monitorData.getBarraProgreso().setForeground(Color.ORANGE);
        }
        formulario.panelPadre.actualizarNotificacionesCodefac();
    }
    
    private void generarReportePdf(String clave) {
        try {
            byte[] bytes = ServiceFactory.getFactory().getComprobanteServiceIf().getReporteComprobante(clave,retencion.getEmpresa());
            JasperPrint jasperPrint = (JasperPrint) UtilidadesRmi.deserializar(bytes);
            formulario.panelPadre.crearReportePantalla(jasperPrint, clave);
            //facturacionModel.panelPadre.crearReportePantalla(jasperPrint, facturaProcesando.getPreimpreso());
        }catch (IOException ex) {
            Logger.getLogger(RetencionImplCallBack.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RetencionImplCallBack.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
     
}
