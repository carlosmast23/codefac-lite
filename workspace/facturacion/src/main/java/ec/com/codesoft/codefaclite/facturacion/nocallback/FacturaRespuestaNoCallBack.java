/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.nocallback;

import ec.com.codesoft.codefaclite.controlador.comprobantes.MonitorComprobanteData;
import ec.com.codesoft.codefaclite.controlador.comprobantes.MonitorComprobanteModel;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.facturacion.callback.ClienteFacturaImplComprobante;
import ec.com.codesoft.codefaclite.facturacion.model.FacturacionModel;
import ec.com.codesoft.codefaclite.facturacionelectronica.AlertaComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.ClaveAcceso;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.utilidades.rmi.UtilidadesRmi;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author Carlos
 */
public class FacturaRespuestaNoCallBack implements Runnable{

    private static final Logger LOG = Logger.getLogger(FacturaRespuestaNoCallBack.class.getName());
    
    
    //Esta variable identifica cuanto tiempo esperar la autorizacion del documento expresado en segundos
    private static final Integer TIEMPO_ESPERA=10;
    
    private FacturacionModel facturacionModel;
    private Factura factura;
    private MonitorComprobanteData monitorData;

    public FacturaRespuestaNoCallBack(FacturacionModel facturacionModel, Factura factura) {
        this.facturacionModel = facturacionModel;
        this.factura = factura;
    }

    /**
     * Metodo prinicipal que se encarga de revisar 
     */
    public void iniciar()
    {
        new Thread(this).start();
    }
    
    @Override
    public void run() {
        
        LOG.log(Level.INFO,"Iniciado nocallback :"+factura.getPreimpreso());
        iniciado(); //Estado inicial que esta procesando la factura
        for (int i = 0; i < TIEMPO_ESPERA; i++) 
        {            
            try {
                Thread.sleep(2000);
                this.factura = ServiceFactory.getFactory().getFacturacionServiceIf().buscarPorId(factura.getId());

                if (factura.getFechaAutorizacionSri() != null) {
                    terminado();
                    LOG.log(Level.INFO,"Factura Autorizada :"+factura.getPreimpreso());
                    return;
                } else if (factura.getClaveAcceso() != null) {
                    generadoRide();
                    LOG.log(Level.INFO,"Factura generado Ride :"+factura.getPreimpreso());
                }

            } catch (InterruptedException ex) {
                Logger.getLogger(FacturaRespuestaNoCallBack.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RemoteException ex) {
                Logger.getLogger(FacturaRespuestaNoCallBack.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        LOG.log(Level.INFO,"Tiempo de espera superado para factura :"+factura.getPreimpreso());
        
        
    }
    
    private void iniciado() {
        monitorData = MonitorComprobanteModel.getInstance().agregarComprobante();
        monitorData.getLblPreimpreso().setText(factura.getPreimpreso() + " ");
        monitorData.getBtnAbrir().setEnabled(false);
        monitorData.getBtnReporte().setEnabled(false);
        monitorData.getBtnCerrar().setEnabled(false);
        monitorData.getBarraProgreso().setString(factura.getPreimpreso());
        monitorData.getBarraProgreso().setStringPainted(true);
        MonitorComprobanteModel.getInstance().mostrar();
    }
    
    private void generadoRide()
    {
        monitorData.getBarraProgreso().setForeground(Color.YELLOW);
        monitorData.getBarraProgreso().setValue(75);
        
        monitorData.getBtnAbrir().setEnabled(true);
            monitorData.getBtnAbrir().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(verificarImprimirComprobanteVenta())
                    {
                        facturacionModel.imprimirComprobanteVenta(factura);
                        //imprimirComprobanteVenta();
                    }
                    else
                    {
                        ClaveAcceso claveAcceso=new ClaveAcceso(factura.getClaveAcceso());
                        generarReportePdf(claveAcceso.clave);
                    }
                }
            });
        
    }
    
    private void terminado()
    {
        try {
            byte[] bytes = ServiceFactory.getFactory().getComprobanteServiceIf().getReporteComprobante(factura.getClaveAcceso());
            JasperPrint jasperPrint = (JasperPrint) UtilidadesRmi.deserializar(bytes);
            monitorData.getBarraProgreso().setForeground(Color.GREEN);            
            monitorData.getBarraProgreso().setValue(100);
            monitorData.getBtnCerrar().setEnabled(true);
            
            monitorData.getBtnAbrir().setEnabled(true);
            monitorData.getBtnAbrir().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(verificarImprimirComprobanteVenta())
                    {
                        facturacionModel.imprimirComprobanteVenta(factura);
                        //imprimirComprobanteVenta();
                    }
                    else
                    {
                        ClaveAcceso claveAcceso=new ClaveAcceso(factura.getClaveAcceso());
                        generarReportePdf(claveAcceso.clave);
                    }
                }
            });
                       
            
        } catch (IOException ex) {
            Logger.getLogger(ClienteFacturaImplComprobante.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClienteFacturaImplComprobante.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    private void generarReportePdf(String clave) {
        try {
            
            if(verificarImprimirComprobanteVenta())
            {
                facturacionModel.imprimirComprobanteVenta(factura); //TODO:Verificar si este metodo no funciona
            }
            else
            {            
                byte[] bytes = ServiceFactory.getFactory().getComprobanteServiceIf().getReporteComprobante(clave);
                JasperPrint jasperPrint = (JasperPrint) UtilidadesRmi.deserializar(bytes);
                facturacionModel.panelPadre.crearReportePantalla(jasperPrint, clave);
            }
            //facturacionModel.panelPadre.crearReportePantalla(jasperPrint, facturaProcesando.getPreimpreso());
        } catch (RemoteException ex) {
            Logger.getLogger(ClienteFacturaImplComprobante.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClienteFacturaImplComprobante.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClienteFacturaImplComprobante.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    private boolean verificarImprimirComprobanteVenta()
    {
        ParametroCodefac parametroCodefac = facturacionModel.session.getParametrosCodefac().get(ParametroCodefac.COMPROBANTE_VENTA_ACTIVAR);
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
