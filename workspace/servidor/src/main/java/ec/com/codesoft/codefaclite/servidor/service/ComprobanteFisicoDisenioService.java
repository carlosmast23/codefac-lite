/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.entity.ComprobanteFisicoDisenio;
import ec.com.codesoft.codefaclite.servidor.facade.ComprobanteFisicoDisenioFacade;

/**
 *
 * @author Carlos
 */
public class ComprobanteFisicoDisenioService extends ServiceAbstract<ComprobanteFisicoDisenio,ComprobanteFisicoDisenioFacade>{
    
    public ComprobanteFisicoDisenioService() {
        super(ComprobanteFisicoDisenioFacade.class);
    }
    
}
