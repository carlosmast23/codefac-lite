/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import static ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ServiceController.PUERTO_SERVIDOR;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RemoteServer;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class ServiceControllerServer {
    
    public static final int PUERTO_SERVIDOR=1099;
       /**
     * Permite cargar todos los recursos que van a usar en el sistema mediante el protocolo RMI
     * @param mapRecursos envia un map con la clase de la clase de la interfaz y la que implementa
     */
    public static void cargarRecursos(Map<Class,Class> mapRecursos)
    {
        try {
            Registry registro=LocateRegistry.createRegistry(PUERTO_SERVIDOR);
            String host=InetAddress.getLocalHost().getHostAddress();
            
            for (Map.Entry<Class, Class> entry : mapRecursos.entrySet()) {
                Class claseImplementacion=entry.getKey();
                System.out.println("Cargando RMI: "+claseImplementacion.getName());
                UnicastRemoteObject remoteObject=(UnicastRemoteObject) claseImplementacion.newInstance();
                
                Class claseInterfaz=entry.getValue();
                
                String nombreRecurso=claseInterfaz.getSimpleName();
                
                //Lanza el recurso para que este disponible por la red
                registro.rebind("rmi://"+host+":"+PUERTO_SERVIDOR+"/"+nombreRecurso,remoteObject);               
                
            }
            
        } catch (RemoteException ex) {
            Logger.getLogger(ServiceController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(ServiceController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ServiceController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(ServiceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
