/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service.pos;

import ec.com.codesoft.codefaclite.servidor.facade.pos.TurnoAsignadoFacade;
import ec.com.codesoft.codefaclite.servidor.service.ServiceAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.TurnoAsignado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.pos.TurnoAsignadoServiceIf;
import java.rmi.RemoteException;

/**
 *
 * @author Robert
 */
public class TurnoAsignadoService extends ServiceAbstract<TurnoAsignado, TurnoAsignadoFacade> implements TurnoAsignadoServiceIf
{

    public TurnoAsignadoService() throws RemoteException {
        super(TurnoAsignadoFacade.class);
    }
    
}
