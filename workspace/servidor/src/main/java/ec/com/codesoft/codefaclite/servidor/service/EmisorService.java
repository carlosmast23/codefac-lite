/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.entity.Emisor;
import ec.com.codesoft.codefaclite.servidor.excepciones.ConstrainViolationExceptionSQL;
import ec.com.codesoft.codefaclite.servidor.facade.EmisorFacade;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.persistence.exceptions.DatabaseException;

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
        try {
            emisorFacade.create(p);
        } catch (ConstrainViolationExceptionSQL ex) {
            Logger.getLogger(EmisorService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DatabaseException ex) {
            Logger.getLogger(EmisorService.class.getName()).log(Level.SEVERE, null, ex);
        }
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
