/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.PermisoVentanaFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PermisoVentana;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PermisoVentanaServiceIf;
import java.rmi.RemoteException;

/**
 *
 * @author Carlos
 */
public class PermisoVentanaService extends ServiceAbstract<PermisoVentana,PermisoVentanaFacade> implements PermisoVentanaServiceIf{

    public PermisoVentanaService() throws RemoteException {
        super(PermisoVentanaFacade.class);
    }
    
    
    
}
