/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service.pos;

import ec.com.codesoft.codefaclite.servidor.facade.pos.CajaSesionFacade;
import ec.com.codesoft.codefaclite.servidor.service.ServiceAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.CajaSession;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.pos.CajaSesionServiceIf;
import java.rmi.RemoteException;

/**
 *
 * @author Robert
 */
public class CajaSesionService extends ServiceAbstract<CajaSession, CajaSesionFacade> implements CajaSesionServiceIf
{

    public CajaSesionService() throws RemoteException {
        super(CajaSesionFacade.class);
    }
    
    
}