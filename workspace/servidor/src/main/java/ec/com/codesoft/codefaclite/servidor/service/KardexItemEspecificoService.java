/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.KardexItemEspecifico;
import ec.com.codesoft.codefaclite.servidor.facade.KardexItemEspecificoFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.KardexItemEspecificoServiceIf;
import java.rmi.RemoteException;

/**
 *
 * @author Carlos
 */
public class KardexItemEspecificoService extends ServiceAbstract<KardexItemEspecifico, KardexItemEspecificoFacade> implements  KardexItemEspecificoServiceIf
{
    
    public KardexItemEspecificoService() throws RemoteException {
        super(KardexItemEspecificoFacade.class);
    } 
}
