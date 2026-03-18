/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.tareasProgramadas;

import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteElectronicoService;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.utilidades.list.UtilidadesLista;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class ComprobacionComprobantesAutorizadosTarea implements Runnable{

    //private Empresa empresa;
    public ComprobacionComprobantesAutorizadosTarea() 
    {
        //this.empresa=empresa;
    }

    @Override
    public void run() {
        
        comprobacion();
        Logger.getLogger(RespaldoProgramadoTarea.class.getName()).log(Level.INFO,"Metodo revisar si existen comprobantes pendientes");
        
    }
    
    private void comprobacion()
    {
        try {
            Integer comprobantesPendientes=ServiceFactory.getFactory().getComprobanteServiceIf().obtenerTotalComprobantesSinTerminarProcesarTodos();
            if(comprobantesPendientes>0)
            {
                ServiceFactory.getFactory().getComprobanteServiceIf().procesarSinAutorizarYEnviadosPendientesTodos();
                Logger.getLogger(ComprobacionComprobantesAutorizadosTarea.class.getName()).log(Level.SEVERE,"Procesando intentando autorizar facturas pendientes");        
            }
            
        } catch (RemoteException ex) {
            Logger.getLogger(ComprobacionComprobantesAutorizadosTarea.class.getName()).log(Level.SEVERE, null, ex);        
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ComprobacionComprobantesAutorizadosTarea.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
