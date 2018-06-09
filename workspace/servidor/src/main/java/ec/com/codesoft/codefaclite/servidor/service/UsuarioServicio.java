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
import ec.com.codesoft.codefaclite.servidor.util.ExcepcionDataBaseEnum;
import ec.com.codesoft.codefaclite.servidor.util.UtilidadesExcepciones;
import ec.com.codesoft.codefaclite.servidor.util.UtilidadesServidor;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoLicenciaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.UsuarioServicioIf;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Entity;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
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
    
    public void eliminar(Usuario entity) throws java.rmi.RemoteException {
        EntityTransaction transaccion=getTransaccion();
        transaccion.begin();
        entity.setEstado(GeneralEnumEstado.ELIMINADO.getEstado());
        entityManager.merge(entity);
        transaccion.commit();
    }
    
    
    
    public void editar(Usuario entity) throws ServicioCodefacException
    {
                
        EntityTransaction transaccion=getTransaccion();
        transaccion.begin();
        
        Usuario usuarioOriginal=getFacade().find(entity.getNick());
        
        //Verificar que no sea el usuario root el quieren editar
        if(usuarioOriginal.getNick().equals(Usuario.SUPER_USUARIO))
        {
            transaccion.rollback();
            throw new ServicioCodefacException("El usuario root no se puede editar");
            
        }
        
        if(usuarioOriginal.getPerfilesUsuario()!=null)
        {
            for (PerfilUsuario perfilUsuario : usuarioOriginal.getPerfilesUsuario()) {

                //Si en el nuevo objeto que mando a editar no contiene el perfil usuario lo elimino
                if(!entity.getPerfilesUsuario().contains(perfilUsuario))
                {
                    //Elimino de la persistencia
                    entityManager.remove(perfilUsuario);
                }

            }
        }
        
        //Actualizo las referencia del nuevo objecto a editar
        entityManager.merge(entity);
        transaccion.commit();
        
    }
    
    public Usuario grabar(Usuario entity) throws ServicioCodefacException,java.rmi.RemoteException
    {
        EntityTransaction transaccion = getTransaccion();
        try {
            
            //Si la licencia es gratis restringir que solo pueda tener 1 solo usuario
            if(UtilidadesServidor.tipoLicenciaEnum.equals(TipoLicenciaEnum.GRATIS))
            {
                Map<String,Object> mapParametros=new HashMap<String, Object>();
                mapParametros.put("estado", GeneralEnumEstado.ACTIVO.getEstado());
                List<Usuario> usuariosActivos= obtenerPorMap(mapParametros);
                if(usuariosActivos.size()>0)
                {
                    throw new ServicioCodefacException("En la licencia gratuita solo puede crear 1 usuario \n Si desea mas usuarios necesita una licencia PREMIUN");
                }
            }
            
            transaccion.begin();
            entityManager.persist(entity);
            transaccion.commit();
            return entity;
        } 
        catch (PersistenceException ex) 
        {
            if (transaccion.isActive()) {
                transaccion.rollback();
            }

            ExcepcionDataBaseEnum excepcionEnum = UtilidadesExcepciones.analizarExcepcionDataBase(ex);
            Logger.getLogger(PersonaService.class.getName()).log(Level.SEVERE, null, ex);
            if (excepcionEnum.equals(ExcepcionDataBaseEnum.CLAVE_DUPLICADO)) {
                throw new ServicioCodefacException(ExcepcionDataBaseEnum.CLAVE_DUPLICADO.getMensaje());
            } else {
                throw new ServicioCodefacException(ExcepcionDataBaseEnum.DESCONOCIDO.getMensaje());
            }
        }        
        
    
    }
    
    public void grabarUsuario(Usuario usuario,String nombrePerfil) throws ServicioCodefacException
    {
        EntityTransaction transaccion=this.getTransaccion();
        try {
            
            transaccion.begin();
            
            entityManager.persist(usuario);            
            Map<String,Object> parametros=new HashMap<String, Object>();
            parametros.put("nombre",nombrePerfil);
            List<Perfil> perfilesList= this.perfilFacade.findByMap(parametros);
            Perfil perfil=null;
            
            if(perfilesList.size()>0)
            {
                perfil=perfilesList.get(0);                
                
            }
            
            PerfilUsuario perfilUsuario=new PerfilUsuario();
            perfilUsuario.setUsuario(usuario);
            perfilUsuario.setPerfil(perfil);
            perfilUsuario.setFechaCreacion(new java.sql.Date(new Date().getTime()));
            
            entityManager.persist(perfilUsuario);
            
            //Actualizar la referencia del usuario
            usuario.addPerfilUsuario(perfilUsuario);
            entityManager.merge(perfil);
            
            //this.perfilUsuarioFacade.create(perfilUsuario);
            transaccion.commit();
                    
        } catch (PersistenceException ex) {
            
            //verifica que la transaccion esta activa para hacer un rollback
            //Nota: Algunas veces el commit automaticamente hace un rollback es decir no es necesario hacer rollback y la sesion ya no esta activa
            if(transaccion.isActive())
            {
                transaccion.rollback();
            }
            
            ExcepcionDataBaseEnum excepcionEnum=UtilidadesExcepciones.analizarExcepcionDataBase(ex);
            Logger.getLogger(UsuarioServicio.class.getName()).log(Level.SEVERE, null, ex);
            if(excepcionEnum.equals(ExcepcionDataBaseEnum.CLAVE_DUPLICADO))
            {
                throw new ServicioCodefacException(ExcepcionDataBaseEnum.CLAVE_DUPLICADO.getMensaje());
            }
            else
            {
                throw new ServicioCodefacException(ExcepcionDataBaseEnum.DESCONOCIDO.getMensaje());
            }            
            //throw  new ServicioCodefacException("Error sql desconocido");
        
        } catch (DatabaseException ex) {
            Logger.getLogger(UsuarioServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
