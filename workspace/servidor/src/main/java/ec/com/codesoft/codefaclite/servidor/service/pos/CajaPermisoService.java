/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service.pos;

import ec.com.codesoft.codefaclite.servidor.facade.pos.CajaPermisoFacade;
import ec.com.codesoft.codefaclite.servidor.service.ServiceAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.CajaPermiso;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.pos.CajaPermisoServiceIf;
import java.rmi.RemoteException;

/**
 *
 * @author Robert
 */
public class CajaPermisoService extends ServiceAbstract<CajaPermiso, CajaPermisoFacade> implements CajaPermisoServiceIf
{

    public CajaPermisoService() throws RemoteException {
        super(CajaPermisoFacade.class);
    }
    
}
