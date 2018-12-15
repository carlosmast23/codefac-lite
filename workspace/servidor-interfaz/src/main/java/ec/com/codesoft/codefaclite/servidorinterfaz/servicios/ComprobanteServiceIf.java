/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import com.healthmarketscience.rmiio.RemoteInputStream;
import ec.com.codesoft.codefaclite.facturacionelectronica.AlertaComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.servidorinterfaz.callback.ClienteInterfaceComprobante;
import ec.com.codesoft.codefaclite.servidorinterfaz.callback.ClienteInterfaceComprobanteLote;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.ComprobanteDataFactura;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.ComprobanteDataInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.NotaCredito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author Carlos
 */
public interface ComprobanteServiceIf extends Remote {
    
    public void autorizarComprobante(ComprobanteEntity comprobanteElectronica) throws RemoteException,ServicioCodefacException;
    
    public boolean procesarComprobantesLotePendiente(Integer etapaInicial,Integer etapaLimite,List<String> clavesAcceso,String ruc,ClienteInterfaceComprobanteLote callbackClientObject) throws RemoteException;
    
    public void procesarComprobanteOffline(ComprobanteDataInterface comprobanteData,Factura factura,Usuario usuario,ClienteInterfaceComprobante callbackClientObject) throws RemoteException ;
    
    //public Integer obtenerSecuencialFacturaYAvanzar() throws RemoteException;
    
    public boolean verificarCredencialesFirma(String claveFirma) throws RemoteException;

    public boolean procesarComprobantesPendiente(Integer etapaInicial,Integer etapaLimite,String claveAcceso, List<String> correos,ClienteInterfaceComprobante callbackClientObject) throws RemoteException;
    
    public List<AlertaComprobanteElectronico> procesarComprobantesPendienteSinCallBack(Integer etapaInicial,Integer etapaLimite,String claveAcceso, List<String> correos) throws RemoteException,ServicioCodefacException;
    
    public List<ComprobanteElectronico> getComprobantesObjectByFolder(String carpetaConfiguracion) throws RemoteException;
    
    public byte[] getReporteComprobante(String claveAcceso) throws RemoteException;

    public void procesarComprobante(ComprobanteDataInterface comprobanteData,ComprobanteEntity comprobante, Usuario usuario,ClienteInterfaceComprobante callbackClientObject) throws RemoteException;

    public void registerForCallback(ClienteInterfaceComprobante callbackClientObject) throws java.rmi.RemoteException;

    public void unregisterForCallback(ClienteInterfaceComprobante callbackClientObject) throws java.rmi.RemoteException;
    
    public void procesarComprobanteLote(List<ComprobanteDataInterface> comprobantesData,Usuario usuario,String ruc,ClienteInterfaceComprobanteLote callbackClientObject) throws RemoteException;
    
    public boolean verificarDisponibilidadSri() throws RemoteException;
    
    public RemoteInputStream obtenerXmlFirmadoComprobante(Empresa empresa,String claveAcceso) throws RemoteException, ServicioCodefacException;
    
}
