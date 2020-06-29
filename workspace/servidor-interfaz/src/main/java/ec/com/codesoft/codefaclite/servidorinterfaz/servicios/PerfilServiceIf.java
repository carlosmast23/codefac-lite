/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Perfil;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.respuesta.MenuCodefacRespuesta;
 ;
import java.util.List;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public interface PerfilServiceIf extends ServiceAbstractIf<Perfil>
{
        public List<Perfil> obtenerPerfilesPorUsuario(Usuario usuario)   ;
        public MenuCodefacRespuesta construirMenuPermisosUsuario(SessionCodefac sessionCodefac) throws    ServicioCodefacException;
}
