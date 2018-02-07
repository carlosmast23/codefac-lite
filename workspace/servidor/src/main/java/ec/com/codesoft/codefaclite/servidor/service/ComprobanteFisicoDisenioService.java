/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteFisicoDisenio;
import ec.com.codesoft.codefaclite.servidor.facade.ComprobanteFisicoDisenioFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ComprobanteFisicoDisenioServiceIf;
import java.rmi.RemoteException;

/**
 *
 * @author Carlos
 */
public class ComprobanteFisicoDisenioService extends ServiceAbstract<ComprobanteFisicoDisenio,ComprobanteFisicoDisenioFacade> implements  ComprobanteFisicoDisenioServiceIf{
    
    public ComprobanteFisicoDisenioService() throws RemoteException {
        super(ComprobanteFisicoDisenioFacade.class);
    }
    
}
