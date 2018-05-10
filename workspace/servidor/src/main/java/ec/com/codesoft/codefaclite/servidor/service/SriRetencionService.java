/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.SriRetencionFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencion;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SriRetencionServiceIf;
import java.rmi.RemoteException;

/**
 *
 * @author Carlos
 */
public class SriRetencionService extends ServiceAbstract<SriRetencion,SriRetencionFacade> implements SriRetencionServiceIf{

    public SriRetencionService() throws RemoteException {
        super(SriRetencionFacade.class);
    }
    
}
