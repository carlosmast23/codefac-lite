/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.entity.Empresa;
import ec.com.codesoft.codefaclite.servidor.excepciones.ConstrainViolationExceptionSQL;
import ec.com.codesoft.codefaclite.servidor.facade.EmpresaFacade;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.persistence.exceptions.DatabaseException;

/**
 *
 * @author PC
 */
public class EmpresaService 
{
    private EmpresaFacade empresaFacade;
    
    public EmpresaService() 
    {
        this.empresaFacade = new EmpresaFacade();
    }
    
    public void grabar(Empresa p)
    {
        try {
            empresaFacade.create(p);
        } catch (DatabaseException ex) {
            Logger.getLogger(EmpresaService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ConstrainViolationExceptionSQL ex) {
            Logger.getLogger(EmpresaService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void editar(Empresa p)
    {
        empresaFacade.edit(p);
    }
    
    public void eliminar(Empresa p)
    {
        empresaFacade.remove(p);
    }
    
    public List<Empresa> buscar()
    {
        return empresaFacade.findAll();
    } 
        
}
