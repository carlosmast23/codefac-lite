/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public abstract class ServiceController {
    
    /**
     * Puerto mediante el cual nos vamos a comunicar con el servidor por RMI
     */
    public static final int PUERTO_SERVIDOR=1099;
    public String ipServidor="192.168.1.3";
    public static ServiceController serviceController;
    
    /**
     * Obtiene la instancia del controlador actual
     * @return 
     */
    public static ServiceController getController()
    {
        return serviceController;
    }
    
        /**
     * Metodos que devuelven los servicios     *      * 
     */
    public ProductoServiceIf getProductoServiceIf(){return (ProductoServiceIf) getRecursosRMI(ProductoServiceIf.class);};
    public PersonaServiceIf getPersonaServiceIf(){return (PersonaServiceIf) getRecursosRMI(PersonaServiceIf.class);};
    public ParametroCodefacServiceIf getParametroCodefacServiceIf(){return (ParametroCodefacServiceIf) getRecursosRMI(ParametroCodefacServiceIf.class);};
    public UtilidadesServiceIf getUtilidadesServiceIf(){return (UtilidadesServiceIf) getRecursosRMI(UtilidadesServiceIf.class);};
    
    
    /**
     * Crea una nueva instancia el controlados para manejar por el cliente
     * @param ipServidor 
     */
    public static void newController(String ipServidor)
    {
        serviceController=new ServiceController(ipServidor) {};
    }
    
    
    private Map<Class,Remote> mapRecursosRMI;

    private ServiceController(String ipServidor) 
    {
        this.ipServidor=ipServidor;
        this.mapRecursosRMI=new HashMap<Class,Remote>();
    }
    
    
    /**
     * Metodo que permite crear u obtener el servicio atravez de RMI
     * @param clase
     * @return 
     */
    public Remote getRecursosRMI(Class clase)
    {
        Remote remote= mapRecursosRMI.get(clase);
        
        if(remote==null)
        {
            try {
                Registry registro= LocateRegistry.getRegistry(ipServidor,PUERTO_SERVIDOR);    
                remote= registro.lookup("rmi://"+ipServidor+":"+PUERTO_SERVIDOR+"/"+clase.getSimpleName());
                mapRecursosRMI.put(clase,remote);
                
                
            } catch (RemoteException ex) {
                Logger.getLogger(ServiceController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NotBoundException ex) {
                Logger.getLogger(ServiceController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return remote;
    }
    
     
    
    
}
