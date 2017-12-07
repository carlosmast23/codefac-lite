/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.entity.NotaCredito;
import ec.com.codesoft.codefaclite.servidor.facade.NotaCreditoFacade;

/**
 *
 * @author Carlos
 */
public class NotaCreditoService {
    private NotaCreditoFacade notaCreditoFacade;

    public NotaCreditoService() {
        this.notaCreditoFacade = new NotaCreditoFacade();
    }
    
    public void grabar(NotaCredito notaCredito)
    {
        notaCreditoFacade.create(notaCredito);
    }
    
    public void editar(NotaCredito notaCredito)
    {
        notaCreditoFacade.edit(notaCredito);
    }
    
}
