/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Perfil;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PerfilUsuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ConstrainViolationExceptionSQL;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidor.facade.PerfilFacade;
import ec.com.codesoft.codefaclite.servidor.facade.PerfilUsuarioFacade;
import ec.com.codesoft.codefaclite.servidor.facade.UsuarioFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.UsuarioServicioIf;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.persistence.exceptions.DatabaseException;

/**
 *
 * @author Carlos
 */
public class UsuarioServicio extends ServiceAbstract<Usuario,UsuarioFacade> implements UsuarioServicioIf{
    UsuarioFacade usuarioFacade=new UsuarioFacade();
    PerfilUsuarioFacade perfilUsuarioFacade=new PerfilUsuarioFacade();
    PerfilFacade perfilFacade=new PerfilFacade();
    

    public UsuarioServicio() throws RemoteException {
        super(UsuarioFacade.class);
        this.usuarioFacade=new UsuarioFacade();
    }    
    
    
    public Usuario login(String nick,String clave)
    {
        Usuario usuario=usuarioFacade.login(nick, clave);
        return usuario;
    }
    
    public void grabarUsuario(Usuario usuario,String nombrePerfil) throws ServicioCodefacException
    {
        try {
            this.usuarioFacade.create(usuario);            
            Map<String,Object> parametros=new HashMap<String, Object>();
            parametros.put("nombre",nombrePerfil);
            List<Perfil> perfilesList= this.perfilFacade.findByMap(parametros);
            Perfil perfil=null;
            
            if(perfilesList.size()>0)
            {
                perfil=perfilesList.get(0);
            }
            else
            {
               throw new ServicioCodefacException("No existe el perfil para guardar");
            }
            
            PerfilUsuario perfilUsuario=new PerfilUsuario();
            
            
            perfilUsuario.setUsuario(usuario);
            perfilUsuario.setPerfil(perfil);
            perfilUsuario.setFechaCreacion(new java.sql.Date(new Date().getTime()));
            this.perfilUsuarioFacade.create(perfilUsuario);
                    
        } catch (ConstrainViolationExceptionSQL ex) {
            Logger.getLogger(UsuarioServicio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DatabaseException ex) {
            Logger.getLogger(UsuarioServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
