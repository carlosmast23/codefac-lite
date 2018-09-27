/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service.transporte;

import ec.com.codesoft.codefaclite.servidor.facade.transporte.DestinatarioGuiaRemisionFacade;
import ec.com.codesoft.codefaclite.servidor.service.ServiceAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.DestinatarioGuiaRemision;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.transporte.DestinatarioGuiaRemisionServiceIf;
import java.rmi.RemoteException;

/**
 *
 * @author Carlos
 */
public class DestinatarioGuiaRemisionService extends ServiceAbstract<DestinatarioGuiaRemision,DestinatarioGuiaRemisionFacade> implements DestinatarioGuiaRemisionServiceIf{
    
    public DestinatarioGuiaRemisionService() throws RemoteException
    {
        super(DestinatarioGuiaRemisionFacade.class);        
    }
}