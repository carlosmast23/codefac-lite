/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.entity.Persona;
import ec.com.codesoft.codefaclite.servidor.entity.enumerados.ClienteEnumEstado;
import ec.com.codesoft.codefaclite.servidor.facade.AbstractFacade;
import ec.com.codesoft.codefaclite.servidor.facade.PersonaFacade;
import java.util.List;

/**
 *
 * @author PC
 */
public class PersonaService extends ServiceAbstract<PersonaFacade>
{
    private PersonaFacade personaFacade;

    public PersonaService() 
    {
        super(PersonaFacade.class);
        this.personaFacade=new PersonaFacade();
    }

    public void grabar(Persona p)
    {
        personaFacade.create(p);
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
