/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.entity.Impuesto;
import ec.com.codesoft.codefaclite.servidor.facade.ImpuestoFacade;

/**
 *
 * @author PC
 */
public class ImpuestoService 
{
    private ImpuestoFacade impuestoFacade;

    public ImpuestoService() 
    {
        impuestoFacade = new ImpuestoFacade();
    }
    
    public void grabar(Impuesto i)
    {
        impuestoFacade.create(i);
    }
    
    public void editar(Impuesto i)
    {
        impuestoFacade.edit(i);
    }
    
    public void eliminar(Impuesto i)
    {
        impuestoFacade.remove(i);
    }
}
