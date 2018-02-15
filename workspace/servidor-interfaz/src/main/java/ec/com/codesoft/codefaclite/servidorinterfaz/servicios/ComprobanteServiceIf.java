/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.callback.ClienteInterfaceComprobante;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.ComprobanteDataFactura;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.ComprobanteDataInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.NotaCredito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import java.rmi.Remote;
import java.rmi.RemoteException;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author Carlos
 */
public interface ComprobanteServiceIf extends Remote {
    
    public byte[] getReporteComprobante(String claveAcceso) throws RemoteException;

    public void procesarComprobante(ComprobanteDataInterface comprobanteData,Factura factura, Usuario usuario,ClienteInterfaceComprobante callbackClientObject) throws RemoteException;
    
    public void procesarComprobanteNotaCredito(ComprobanteDataInterface comprobanteData,NotaCredito notaCredito,Usuario usuario,ClienteInterfaceComprobante callbackClientObject) throws RemoteException;

    public void registerForCallback(ClienteInterfaceComprobante callbackClientObject) throws java.rmi.RemoteException;

    public void unregisterForCallback(ClienteInterfaceComprobante callbackClientObject) throws java.rmi.RemoteException;
}
