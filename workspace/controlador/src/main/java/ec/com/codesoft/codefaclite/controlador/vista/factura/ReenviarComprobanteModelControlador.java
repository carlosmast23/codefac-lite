/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vista.factura;

import ec.com.codesoft.codefaclite.controlador.vista.factura.ReenviarComprobanteModelControlador.InterfazModelControlador;
import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteElectronicoService;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefacInterface;
import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class ReenviarComprobanteModelControlador extends ModelControladorAbstract<InterfazModelControlador> {

    public ReenviarComprobanteModelControlador(MensajeVistaInterface mensajeVista, SessionCodefacInterface session, InterfazModelControlador interfaz) {
        super(mensajeVista, session, interfaz);
    }

    
    public void iniciar()
    {
        cargarComprobantes();
    }
    
    private void cargarComprobantes()
    {
        try {
            //Carlos todos los comprobantes pendientes de enviar de la carpeta "sin autorizar y no enviados"
            List<ComprobanteElectronico> comprobantesPendientesAutorizar=ServiceFactory.getFactory().getComprobanteServiceIf().getComprobantesObjectByFolder(ComprobanteElectronicoService.CARPETA_GENERADOS,session.getEmpresa());
            interfaz.cargarVistaTabla(comprobantesPendientesAutorizar);                      
            
            
        } catch (RemoteException ex) {
            Logger.getLogger(ReenviarComprobanteModelControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
   

    public interface InterfazModelControlador
    {
        public void cargarVistaTabla(List<ComprobanteElectronico> comprobantes);
    }
    
}
