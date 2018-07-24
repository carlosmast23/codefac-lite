/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriIdentificacion;
import ec.com.codesoft.codefaclite.servidor.facade.SriFormaPagoFacade;
import ec.com.codesoft.codefaclite.servidor.facade.SriIdentificacionFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SriIdentificacionServiceIf;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Carlos
 */
public class SriIdentificacionService extends ServiceAbstract<SriIdentificacion,SriIdentificacionFacade> implements  SriIdentificacionServiceIf
{
    
    public SriIdentificacionService() throws RemoteException {
        super(SriIdentificacionFacade.class);
    }
    
    /**
     * 
     * @param tipoIdentificacion
     * @param tipoTransaccion
     * @return 
     */
    public SriIdentificacion obtenerPorTransaccionEIdentificacion(Persona.TipoIdentificacionEnum tipoIdentificacion,SriIdentificacion.tipoTransaccionEnum tipoTransaccion) throws java.rmi.RemoteException
    {
        Map<String,Object> mapParametros=new HashMap<String, Object>();
        mapParametros.put("tipoIdentificacion",tipoIdentificacion.getLetra());
        mapParametros.put("tipoTransaccion",tipoTransaccion.getNombre());
        List<SriIdentificacion> sriIdentficacionList=getFacade().findByMap(mapParametros);
        if(sriIdentficacionList!=null && sriIdentficacionList.size()>0)
        {
            return sriIdentficacionList.get(0);
        }
        return null;
    }
    
    
}
