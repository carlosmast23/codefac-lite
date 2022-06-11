/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import java.util.List;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public interface OrdenTrabajoDetalleServiceIf extends ServiceAbstractIf<OrdenTrabajoDetalle> 
{
    public List<OrdenTrabajoDetalle> buscarPorOrdenTrabajo(OrdenTrabajo ordenTrabajo)throws ServicioCodefacException, java.rmi.RemoteException;
}
