/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.callback.ClienteInterfaceComprobante;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.ComprobanteDataFactura;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.ComprobanteDataInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Carlos
 */
public interface ComprobanteServiceIf extends Remote {

    public void procesarComprobante(ComprobanteDataInterface comprobanteData, Usuario usuario) throws RemoteException;

    public void registerForCallback(ClienteInterfaceComprobante callbackClientObject) throws java.rmi.RemoteException;

    public void unregisterForCallback(ClienteInterfaceComprobante callbackClientObject) throws java.rmi.RemoteException;
}
