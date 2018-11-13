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
import ec.com.codesoft.codefaclite.servidor.util.ExcepcionDataBaseEnum;
import ec.com.codesoft.codefaclite.servidor.util.UtilidadesExcepciones;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.OperadorNegocioEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PersonaServiceIf;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
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
        } catch (PersistenceException ex) {
            
            //verifica que la transaccion esta activa para hacer un rollback
            //Nota: Algunas veces el commit automaticamente hace un rollback es decir no es necesario hacer rollback y la sesion ya no esta activa
            if(transaccion.isActive())
            {
                transaccion.rollback();
            }
            
            ExcepcionDataBaseEnum excepcionEnum=UtilidadesExcepciones.analizarExcepcionDataBase(ex);
            Logger.getLogger(PersonaService.class.getName()).log(Level.SEVERE, null, ex);
            if(excepcionEnum.equals(ExcepcionDataBaseEnum.CLAVE_DUPLICADO))
            {
                throw new ServicioCodefacException(ExcepcionDataBaseEnum.CLAVE_DUPLICADO.getMensaje());
            }
            else
            {
                throw new ServicioCodefacException(ExcepcionDataBaseEnum.DESCONOCIDO.getMensaje());
            }            
            //throw  new ServicioCodefacException("Error sql desconocido");
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
    
    @Override
    public List<Persona> buscarPorTipo(OperadorNegocioEnum tipoEnum) throws java.rmi.RemoteException
    {
        return getFacade().buscarPorTipoFacade(tipoEnum);
    }
            
    
}
