/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ConstrainViolationExceptionSQL;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidor.facade.AbstractFacade;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import org.eclipse.persistence.exceptions.DatabaseException;

/**
 *
 * @author Carlos
 */
public abstract class ServiceAbstract<Entity,Facade> extends UnicastRemoteObject
{
    private AbstractFacade<Entity> facade;
    protected EntityManager entityManager;

    public ServiceAbstract() throws RemoteException {
        this.entityManager=AbstractFacade.entityManager;
    }
    
    /**
     * Obtiene un transaccion para trabar con el entity manager
     */
    public EntityTransaction getTransaccion()
    {
        return entityManager.getTransaction();
    }
 
    public ServiceAbstract(Class<Facade> clase) throws java.rmi.RemoteException
    {
        try {
            this.facade =(AbstractFacade<Entity>) clase.newInstance();
            this.entityManager=AbstractFacade.entityManager;
        } catch (InstantiationException ex) {
            Logger.getLogger(ServiceAbstract.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ServiceAbstract.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //private T t;
    public List<Entity> obtenerTodos()
    {
        return this.facade.findAll();
    }
    
    public Entity grabar(Entity entity) throws ServicioCodefacException,java.rmi.RemoteException
    {
        try {
            this.facade.create(entity);            

        } catch (ConstrainViolationExceptionSQL ex) {
            Logger.getLogger(ServiceAbstract.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DatabaseException ex) {
            Logger.getLogger(ServiceAbstract.class.getName()).log(Level.SEVERE, null, ex);
        }
        return entity;
    }
    
    public void editar(Entity entity)
    {
        this.facade.edit(entity);
    }
    
    public void eliminar(Entity entity) throws java.rmi.RemoteException
    {
        this.facade.remove(entity);
    }
    
    public List<Entity> obtenerPorMap(Map<String,Object> parametros)
    {
        return this.facade.findByMap(parametros);
    }
    


    /**
     * Metodo que se encarga de desasoriar una entidad gestionada para poder hacer acciones
     * sobre el objecto pero que no se reflejen en la persistencia con la base de datoss
     */    
    public static void desasociarEntidadPersistencia(Object entidad)
    {
        AbstractFacade.detachEntity(entidad);
    }
    
    /**
     * Metodo recursivo que se encarga de desasoriar una entidad gestionada para poder
     * hacer acciones sobre el objecto pero que no se reflejen en la
     * persistencia con la base de datoss
     */   
    public static void desasociarEntidadRecursivo(Object entidad)
    {
        AbstractFacade.detachRecursive(entidad);
    }
    
}
