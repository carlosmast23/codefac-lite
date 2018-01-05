/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.entity.AccesoDirecto;
import ec.com.codesoft.codefaclite.servidor.facade.AccesoDirectoFacade;

/**
 *
 * @author Carlos
 */
public class AccesoDirectoService extends ServiceAbstract<AccesoDirecto,AccesoDirectoFacade>{
    
    public AccesoDirectoService() {
        super(AccesoDirectoFacade.class);
    }
    
}
