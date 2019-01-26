/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.actualizacion;

import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.NotaCredito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Retencion;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.GuiaRemision;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class ActualizacionSistemaUtil {
    
    public static void ejecutarNuevoMetodoEstatico()
    {
        System.out.println("Metodo ejecutado estatico");
    }
    
    private static void actualizarComprobante(ComprobanteEntity comprobante)
    {
        if(comprobante.getFechaAutorizacionSri()==null)
        {
            
        }
    }
    
    public static void actualizaComprobantesElectronicos()
    {
        List<ComprobanteEntity> listaComprobantesActualizar=new ArrayList<ComprobanteEntity>();
        
        try {
            List<Factura> facturas=ServiceFactory.getFactory().getFacturacionServiceIf().obtenerTodos();
            listaComprobantesActualizar.addAll(facturas);
            
            List<NotaCredito> notasCredito=ServiceFactory.getFactory().getNotaCreditoServiceIf().obtenerTodos();
            listaComprobantesActualizar.addAll(notasCredito);
            
            List<Retencion> retenciones = ServiceFactory.getFactory().getRetencionServiceIf().obtenerTodos();
            listaComprobantesActualizar.addAll(retenciones);

            List<GuiaRemision> guiasRemision = ServiceFactory.getFactory().getGuiaRemisionServiceIf().obtenerTodos();
            listaComprobantesActualizar.addAll(guiasRemision);
            
            
            ServiceFactory.getFactory().getComprobanteServiceIf().actualizarComprobanteDatos(listaComprobantesActualizar);
            
            
        } catch (RemoteException ex) {
            Logger.getLogger(ActualizacionSistemaUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ActualizacionSistemaUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
