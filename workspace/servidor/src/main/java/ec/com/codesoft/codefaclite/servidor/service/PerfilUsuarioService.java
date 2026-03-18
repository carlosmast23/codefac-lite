
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.PerfilUsuarioFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Perfil;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PerfilUsuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PerfilUsuarioServiceIf;
import jakarta.persistence.EntityManager;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class PerfilUsuarioService extends ServiceAbstract<PerfilUsuario,PerfilUsuarioFacade> implements PerfilUsuarioServiceIf{

    public PerfilUsuarioService() throws RemoteException {
        super(PerfilUsuarioFacade.class);
    }

    @Override
    public List<PerfilUsuario> buscarPorPerfil(Perfil perfil) throws RemoteException {        
        try {
            return (List<PerfilUsuario>)ejecutarTransaccionConResultado(new MetodoInterfaceTransaccionResultado() {
                @Override
                public Object transaccion(EntityManager entityManager) throws ServicioCodefacException, RemoteException {
                    Map<String, Object> mapParametros = new HashMap<String, Object>();
                    mapParametros.put("perfil", perfil);
                    return getFacade().findByMap(mapParametros,entityManager);
                }
            });
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(PerfilUsuarioService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @Override
    public List<PerfilUsuario> buscarPorUsuario(Usuario usuario) throws RemoteException {        
        try {
            return (List<PerfilUsuario>)ejecutarTransaccionConResultado(new MetodoInterfaceTransaccionResultado() {
                @Override
                public Object transaccion(EntityManager entityManager) throws ServicioCodefacException, RemoteException {
                    Map<String, Object> mapParametros = new HashMap<String, Object>();
                    mapParametros.put("usuarioId", usuario.getId());
                    return getFacade().findByMap(mapParametros,entityManager);
                }
            });
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(PerfilUsuarioService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
