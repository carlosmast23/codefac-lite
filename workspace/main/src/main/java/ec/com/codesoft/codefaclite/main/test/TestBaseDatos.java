/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.test;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteFisicoDisenio;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.PersistenciaDuplicadaException;
import ec.com.codesoft.codefaclite.servidor.facade.AbstractFacade;
import ec.com.codesoft.codefaclite.servidor.service.ComprobanteFisicoDisenioService;
import ec.com.codesoft.codefaclite.servidor.service.OrdenTrabajoService;
import ec.com.codesoft.codefaclite.servidor.service.PersonaService;
import ec.com.codesoft.codefaclite.servidor.service.PresupuestoService;
import ec.com.codesoft.codefaclite.servidor.service.ServiceAbstract;
import ec.com.codesoft.codefaclite.servidor.service.UsuarioServicio;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PersonaServiceIf;
import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

/**
 *
 * @author Carlos
 */
public class TestBaseDatos {
    public static void main(String[] args) {
        try {
            AbstractFacade.usuarioDb = "root";
            AbstractFacade.claveDb = "123";
            AbstractFacade.cargarEntityManager();
            
            EntityManager em= AbstractFacade.entityManager;
            EntityTransaction et= em.getTransaction();
            
            
//            ComprobanteFisicoDisenioService servicio = new ComprobanteFisicoDisenioService();
//            ComprobanteFisicoDisenio c2=servicio.obtenerTodos().get(0);
//            AbstractFacade.detachRecursive(c2);
            
//            PersonaService servicio = new PersonaService();
//            List <Persona> personas = servicio.obtenerTodos();
//            for (Persona persona : personas) {
//                System.out.println(" - " + persona);
//            }
            
            PersonaServiceIf service = ServiceFactory.getFactory().getPersonaServiceIf();
            for (Persona persona : service.obtenerTodos()) {
                System.out.println(persona);
            }
            em.close();
            System.exit(0);
            
        } catch (PersistenceException ex) {
            Logger.getLogger(TestBaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PersistenciaDuplicadaException ex) {
            Logger.getLogger(TestBaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(TestBaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
