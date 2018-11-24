/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.SucursalFacade;
import ec.com.codesoft.codefaclite.servidor.facade.UsuarioFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SucursalServiceIf;
import java.io.Serializable;
import java.rmi.RemoteException;

/**
 *
 * @author Carlos
 */
public class SucursalService extends ServiceAbstract<Sucursal, SucursalFacade> implements SucursalServiceIf{

    public SucursalService() throws RemoteException {
        super(SucursalFacade.class);
    }
    
}
