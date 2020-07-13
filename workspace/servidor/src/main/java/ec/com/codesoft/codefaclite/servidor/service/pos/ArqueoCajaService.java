/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service.pos;

import ec.com.codesoft.codefaclite.servidor.facade.pos.ArqueoCajaFacade;
import ec.com.codesoft.codefaclite.servidor.service.ServiceAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.ArqueoCaja;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.pos.ArqueoCajaServiceIf;
import java.rmi.RemoteException;

/**
 *
 * @author Robert
 */
public class ArqueoCajaService extends ServiceAbstract<ArqueoCaja, ArqueoCajaFacade> implements ArqueoCajaServiceIf
{

    public ArqueoCajaService() throws RemoteException {
        super(ArqueoCajaFacade.class);
    }
    
}
