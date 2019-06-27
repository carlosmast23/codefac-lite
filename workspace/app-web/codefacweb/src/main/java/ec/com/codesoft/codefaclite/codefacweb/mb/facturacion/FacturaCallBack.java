/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.mb.facturacion;

import ec.com.codesoft.codefaclite.codefacweb.core.SessionMb;
import ec.com.codesoft.codefaclite.facturacionelectronica.AlertaComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.ClaveAcceso;
import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteElectronicoService;
import ec.com.codesoft.codefaclite.facturacionelectronica.exception.ComprobanteElectronicoException;
import ec.com.codesoft.codefaclite.servidorinterfaz.callback.ClienteInterfaceComprobante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 *
 * @author Carlos
 */
public class FacturaCallBack extends UnicastRemoteObject implements ClienteInterfaceComprobante {
    
    private SessionMb sessionMb;

    public FacturaCallBack(SessionMb sessionMb) throws RemoteException {
        this.sessionMb.setProgreso(0);
    }
    

    public void termino(byte[] byteJasperPrint, List<AlertaComprobanteElectronico> alertas) throws RemoteException {
        this.sessionMb.setProgreso(100);
    }

    public void iniciado() throws RemoteException {
        this.sessionMb.setProgreso(0);
    }

    public void procesando(int etapa, ClaveAcceso clave) throws RemoteException {
        if (etapa == ComprobanteElectronicoService.ETAPA_GENERAR) {
            this.sessionMb.setProgreso(20);
            //monitorData.getBarraProgreso().setValue(20);
           
        }

        if (etapa == ComprobanteElectronicoService.ETAPA_PRE_VALIDAR) {
            this.sessionMb.setProgreso(30);
            //monitorData.getBarraProgreso().setValue(30);
        }

        if (etapa == ComprobanteElectronicoService.ETAPA_FIRMAR) {
            this.sessionMb.setProgreso(50);
            //monitorData.getBarraProgreso().setValue(50);
        }
        
        if (etapa == ComprobanteElectronicoService.ETAPA_RIDE) {
            this.sessionMb.setProgreso(65);
            //monitorData.getBarraProgreso().setValue(65);
            
            
        }
        
        if (etapa == ComprobanteElectronicoService.ETAPA_ENVIO_COMPROBANTE) {
            this.sessionMb.setProgreso(80);
            //monitorData.getBarraProgreso().setValue(80);
            
        }

        if (etapa == ComprobanteElectronicoService.ETAPA_ENVIAR) {
            this.sessionMb.setProgreso(90);
            //monitorData.getBarraProgreso().setValue(90);
        }

        if (etapa == ComprobanteElectronicoService.ETAPA_AUTORIZAR) {
            this.sessionMb.setProgreso(100);
            //monitorData.getBarraProgreso().setValue(100);
           
        }
    }

    public void error(ComprobanteElectronicoException cee, String claveAcceso) throws RemoteException {
        System.out.println("error ...");
    }
    
}
