/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.entity.NotaCredito;
import ec.com.codesoft.codefaclite.servidor.facade.NotaCreditoDetalleFacade;
import ec.com.codesoft.codefaclite.servidor.facade.NotaCreditoFacade;
import java.util.List;

/**
 *
 * @author PC
 */
public class NotaCreditoService 
{
    NotaCreditoFacade notaCreditoFacade;
    NotaCreditoDetalleFacade notaCreditoDetalleFacade;
    ParametroCodefacService parametroCodefacService;
    
    public NotaCreditoService()
    {
        this.notaCreditoFacade = new NotaCreditoFacade();
        this.notaCreditoDetalleFacade = new NotaCreditoDetalleFacade();
        parametroCodefacService = new ParametroCodefacService();
    }
    
    public void grabar(NotaCredito notaCredito)
    {
        notaCreditoFacade.create(notaCredito);
    }
    
    public void editar(NotaCredito notaCredito)
    {
        notaCreditoFacade.edit(notaCredito);
    }        
    
    public List<NotaCredito> obtenerTodos()
    {
        return notaCreditoFacade.findAll();
    }
     
}
