/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ConstrainViolationExceptionSQL;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidor.facade.AbstractFacade;
import ec.com.codesoft.codefaclite.servidor.util.ExcepcionDataBaseEnum;
import ec.com.codesoft.codefaclite.servidor.util.UtilidadesExcepciones;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import org.eclipse.persistence.exceptions.DatabaseException;

/**
 *
 * @author Carlos
 */
public abstract class ServiceAbstract<Entity,Facade> extends UnicastRemoteObject implements Serializable
{

    private static final Logger LOG = Logger.getLogger(ServiceAbstract.class.getName());
    
    
    protected AbstractFacade<Entity> facade;
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
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                entityManager.persist(entity);
            }
        });
        return entity;
    }
   
    public Entity buscarPorId(Object primaryKey) throws java.rmi.RemoteException
    {
        return this.facade.find(primaryKey);
    }
    
    public void editar(Entity entity) throws ServicioCodefacException,java.rmi.RemoteException
    {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                entityManager.merge(entity);
            }
        });
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
     * Metodo Que me permite ejecutar un conjunto de procesos en el jpa como un proceso
     * @param interfaz
     * @throws PersistenceException 
     */
    protected void ejecutarTransaccion(MetodoInterfaceTransaccion interfaz) throws ServicioCodefacException
   {
        EntityTransaction transaccion = entityManager.getTransaction();
        try {            
            transaccion.begin();
            interfaz.transaccion();
            transaccion.commit();
        }catch (RemoteException ex) { //Hacer un RoolBack cuando no exista comunicacion con el servidor
            if (transaccion.isActive()) {
                transaccion.rollback();
            }
            ex.printStackTrace();
            throw new ServicioCodefacException("Error de conexión con el servidor");
        }catch (ServicioCodefacException ex) { //Hacer un RoolBack cuando sea un error personalizado
            if (transaccion.isActive()) {
                transaccion.rollback();
            }
            ex.printStackTrace();
            throw ex;
        }catch (PersistenceException ex) { //Hacer un RoolBack cuando es un error relacionado con la persistencia
            ex.printStackTrace();
            //verifica que la transaccion esta activa para hacer un rollback
            //Nota: Algunas veces el commit automaticamente hace un rollback es decir no es necesario hacer rollback y la sesion ya no esta activa
            if(transaccion.isActive())
            {
                transaccion.rollback();
            }
            
            ExcepcionDataBaseEnum excepcionEnum=UtilidadesExcepciones.analizarExcepcionDataBase(ex);
            //Logger.getLogger(PersonaService.class.getName()).log(Level.SEVERE, null, ex);
            if(excepcionEnum.equals(ExcepcionDataBaseEnum.CLAVE_DUPLICADO))
            {
                throw new ServicioCodefacException(ExcepcionDataBaseEnum.CLAVE_DUPLICADO.getMensaje());
            }
            else
            {
                throw new ServicioCodefacException(ExcepcionDataBaseEnum.DESCONOCIDO.getMensaje()+"\n Causa: "+ ex.getMessage());
            }  
            
            
        } catch(Exception e) //Hacer un RollBack si se produce cualquier error
        {
             if (transaccion.isActive()) {
                transaccion.rollback();
            }
            e.printStackTrace();
            LOG.log(Level.SEVERE,e.getMessage()); //Todo: Mejorar esta parte porque deberia imprimir toda la pila de error y ademas deberia poder comunicar el error a la capa superior
            throw new ServicioCodefacException(e.getMessage());
            //throw e;
        }
    }

    protected Facade getFacade()
    {
        return (Facade) this.facade;
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
