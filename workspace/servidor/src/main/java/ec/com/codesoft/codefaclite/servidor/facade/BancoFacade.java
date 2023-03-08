/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.banco.Banco;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class BancoFacade extends AbstractFacade<Banco>{

    public BancoFacade() 
    {
        super(Banco.class);
    }
    
}
