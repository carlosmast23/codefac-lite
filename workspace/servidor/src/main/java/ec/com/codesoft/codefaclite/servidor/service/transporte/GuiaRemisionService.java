/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service.transporte;

import ec.com.codesoft.codefaclite.servidor.facade.transporte.GuiaRemisionFacade;
import ec.com.codesoft.codefaclite.servidor.service.ServiceAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.GuiaRemision;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.transporte.GuiaRemisionServiceIf;
import java.rmi.RemoteException;

/**
 *
 * @author Carlos
 */
public class GuiaRemisionService extends ServiceAbstract<GuiaRemision,GuiaRemisionFacade> implements GuiaRemisionServiceIf{

    public GuiaRemisionService() throws RemoteException {
        super(GuiaRemisionFacade.class);
    }
    
    
    
}
