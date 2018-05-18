/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ClienteEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ConstrainViolationExceptionSQL;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidor.facade.AbstractFacade;
import ec.com.codesoft.codefaclite.servidor.facade.PersonaFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PersonaServiceIf;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityTransaction;
import org.eclipse.persistence.exceptions.DatabaseException;

/**
 *
 * @author PC
 */
public class PersonaService extends ServiceAbstract<Persona,PersonaFacade> implements PersonaServiceIf
{
    private PersonaFacade personaFacade;

    public PersonaService() throws RemoteException 
    {
        super(PersonaFacade.class);
        this.personaFacade=new PersonaFacade();
    }

    public Persona grabar(Persona p) throws ServicioCodefacException,java.rmi.RemoteException
    {
        EntityTransaction transaccion=getTransaccion();
        transaccion.begin();
        try {
            entityManager.persist(p);
            //personaFacade.create(p);
            transaccion.commit();
            
        //} catch (ConstrainViolationExceptionSQL ex) {
        //    Logger.getLogger(PersonaService.class.getName()).log(Level.SEVERE, null, ex);
        //    throw new ServicioCodefacException(ex.getMessage());
        } catch (DatabaseException ex) {
            transaccion.rollback();
            Logger.getLogger(PersonaService.class.getName()).log(Level.SEVERE, null, ex);
            throw  new ServicioCodefacException("Error sql");
        }
        return p;
    }
    
    public void editar(Persona p)
    {
        personaFacade.edit(p);
    }
    
    public void eliminar(Persona p)
    {
        //personaFacade.remove(p);
        p.setEstado(ClienteEnumEstado.ELIMINADO.getEstado());
        editar(p);
    }
    
    public List<Persona> buscar()
    {
        return personaFacade.findAll();
    }
            
    
}
