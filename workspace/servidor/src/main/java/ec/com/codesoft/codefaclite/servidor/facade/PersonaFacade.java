/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade;

import ec.com.codesoft.codefaclite.servidor.entity.Persona;
//import ec.com.codesoft.codefaclite.servidor.entity.Persona;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Carlos
 */
public class PersonaFacade extends AbstractFacade<Persona>
{
    //@PersistenceContext(unitName = "persistenceUnitCodefac")
    //private EntityManager em;

    public PersonaFacade() 
    {
        super(Persona.class);
    }    
    
    /*
    @Override
    protected EntityManager getEntityManager() {
        //EntityManagerFactory factory=Persistence.createEntityManagerFactory("pu_ejemplo");
        //em= factory.createEntityManager();
        return em;
    }*/
    
    public int addNumbers(int numberA, int numberB) {
        return numberA - numberB;
    }

    
}
