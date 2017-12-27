/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.entity.Usuario;
import ec.com.codesoft.codefaclite.servidor.facade.UsuarioFacade;

/**
 *
 * @author Carlos
 */
public class UsuarioServicio extends ServiceAbstract<Usuario,UsuarioFacade>{
    UsuarioFacade usuarioFacade=new UsuarioFacade();

    public UsuarioServicio() {
        super(UsuarioFacade.class);
        this.usuarioFacade=new UsuarioFacade();
    }    
    
    
    public Usuario login(String nick,String clave)
    {
        Usuario usuario=usuarioFacade.login(nick, clave);
        return usuario;
    }
}
