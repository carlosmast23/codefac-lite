/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.entity.Emisor;
import ec.com.codesoft.codefaclite.servidor.facade.EmisorFacade;
import java.util.List;

/**
 *
 * @author PC
 */
public class EmisorService 
{
    private EmisorFacade emisorFacade;
    
    public EmisorService() 
    {
        this.emisorFacade = new EmisorFacade();
    }
    
    public void grabar(Emisor p)
    {
        emisorFacade.create(p);
    }
    
    public void editar(Emisor p)
    {
        emisorFacade.edit(p);
    }
    
    public void eliminar(Emisor p)
    {
        emisorFacade.remove(p);
    }
    
    public List<Emisor> buscar()
    {
        return emisorFacade.findAll();
    }  
}
