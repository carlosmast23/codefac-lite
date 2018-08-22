/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.gestionacademica.callback;

import ec.com.codesoft.codefaclite.controlador.comprobantes.MonitorComprobanteData;
import ec.com.codesoft.codefaclite.controlador.comprobantes.MonitorComprobanteModel;
import ec.com.codesoft.codefaclite.servidorinterfaz.callback.EnvioMensajesCallBackInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.callback.EnvioMensajesCallBackInterface;
import java.awt.Color;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *Clase que me permite comunicar con el servidor para saber la cantidad de mensajes enviadas
 * @author Carlos
 */
public class EnvioMensajesCallBack extends UnicastRemoteObject implements EnvioMensajesCallBackInterface{
    
    private MonitorComprobanteData monitorData;

    public EnvioMensajesCallBack() throws RemoteException {
        monitorData = MonitorComprobanteModel.getInstance().agregarComprobante();
        MonitorComprobanteModel.getInstance().mostrar();
    }
    
    
    
    public void procesando(Integer porcentaje) throws RemoteException
    {
        monitorData.getBarraProgreso().setValue(porcentaje);
        
        if(porcentaje.equals(100))
        {
            monitorData.getBarraProgreso().setForeground(Color.green);
        }
        else
        {
            monitorData.getBarraProgreso().setBackground(Color.GREEN);
        }
    }
}
