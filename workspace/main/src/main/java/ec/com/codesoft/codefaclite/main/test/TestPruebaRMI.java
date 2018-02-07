/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.test;

import ec.com.codesoft.codefaclite.servidor.facade.AbstractFacade;
import ec.com.codesoft.codefaclite.servidor.service.PersonaService;
import ec.com.codesoft.codefaclite.servidor.service.ProductoService;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.PersistenciaDuplicadaException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PersonaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ProductoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ServiceController;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ServiceControllerServer;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.PersistenceException;

/**
 *
 * @author Carlos
 */
public class TestPruebaRMI {
    public static void main(String[] args) {
        try {
            //try {
            AbstractFacade.cargarEntityManager();
            //PersonaServiceIf servicioIf;
            
            Map<Class,Class> mapRecursos=new HashMap<Class, Class>();
            
            mapRecursos.put(ProductoService.class,ProductoServiceIf.class);
            mapRecursos.put(PersonaService.class,PersonaServiceIf.class);
            
            ServiceControllerServer.cargarRecursos(mapRecursos);
            System.out.println("servidor iniciado");
            /*
            String host=InetAddress.getLocalHost().getHostAddress();
            System.out.println(host);
            Registry registro=LocateRegistry.createRegistry(1099);
            registro.rebind("rmi://"+host+":1099/RMIBD",new PersonaService());
            System.out.println("Servidor Activo");
            } catch (PersistenceException ex) {
            Logger.getLogger(TestPruebaRMI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (PersistenciaDuplicadaException ex) {
            Logger.getLogger(TestPruebaRMI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RemoteException ex) {
            Logger.getLogger(TestPruebaRMI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnknownHostException ex) {
            Logger.getLogger(TestPruebaRMI.class.getName()).log(Level.SEVERE, null, ex);
            }*/
        } catch (PersistenceException ex) {
            Logger.getLogger(TestPruebaRMI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PersistenciaDuplicadaException ex) {
            Logger.getLogger(TestPruebaRMI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
