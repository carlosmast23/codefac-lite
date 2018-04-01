
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.PerfilUsuarioFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PerfilUsuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PerfilUsuarioServiceIf;
import java.rmi.RemoteException;

/**
 *
 * @author Carlos
 */
public class PerfilUsuarioService extends ServiceAbstract<PerfilUsuario,PerfilUsuarioFacade> implements PerfilUsuarioServiceIf{

    public PerfilUsuarioService() throws RemoteException {
        super(PerfilUsuarioFacade.class);
    }
    
    
}
