/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.OrdenTrabajoDetalleFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.OrdenTrabajoDetalleServiceIf;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public class OrdenTrabajoDetalleService extends ServiceAbstract<OrdenTrabajoDetalle, OrdenTrabajoDetalleFacade> implements OrdenTrabajoDetalleServiceIf
{
    public OrdenTrabajoDetalleService() throws RemoteException {
        super(OrdenTrabajoDetalleFacade.class);
    }
    
    public List<OrdenTrabajoDetalle> buscarPorOrdenTrabajo(OrdenTrabajo ordenTrabajo)throws ServicioCodefacException, java.rmi.RemoteException
    {
        Map<String, Object> parametroMap = new HashMap<String, Object>();
        parametroMap.put("ordenTrabajo", ordenTrabajo);
        //parametroMap.put("departamento", departamento);
        return getFacade().findByMap(parametroMap);
    }
   
}
