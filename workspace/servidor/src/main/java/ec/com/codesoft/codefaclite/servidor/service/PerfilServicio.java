/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Perfil;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidor.facade.PerfilFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PerfilServicioIf;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Carlos
 */
public class PerfilServicio extends ServiceAbstract<Perfil,PerfilFacade> implements PerfilServicioIf{

    private PerfilFacade perfilFacade;
    public PerfilServicio() throws RemoteException 
    {
        super(PerfilFacade.class);
        this.perfilFacade=new PerfilFacade();
    }
    
    public List<Perfil> obtenerPerfilesPorUsuario(Usuario usuario)
    {
        return this.perfilFacade.getPerfilesByUsuario(usuario);
    }
    
}
