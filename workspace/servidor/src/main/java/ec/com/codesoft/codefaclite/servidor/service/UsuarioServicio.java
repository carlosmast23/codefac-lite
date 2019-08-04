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
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoLicenciaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ModoSistemaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.UsuarioServicioIf;
import ec.com.codesoft.codefaclite.utilidades.seguridad.UtilidadesHash;
import ec.com.codesoft.codefaclite.ws.codefac.test.service.WebServiceCodefac;
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

    private static final Logger LOG = Logger.getLogger(UsuarioServicio.class.getName());
    
    UsuarioFacade usuarioFacade=new UsuarioFacade();
    PerfilUsuarioFacade perfilUsuarioFacade=new PerfilUsuarioFacade();
    PerfilFacade perfilFacade=new PerfilFacade();
    

    public UsuarioServicio() throws RemoteException {
        super(UsuarioFacade.class);
        this.usuarioFacade=new UsuarioFacade();
    }    
    
    
    public Usuario login(String nick,String clave)
    {
        //String clave=new String(getTxtClave().getPassword());
        //String usuarioTxt=getTxtUsuario().getText();
        Usuario usuario=null;
        
        if(!nick.equals("") && !clave.equals(""))
        {
            /**
             * Validacion para verificar si no es un usuario root es decir para soporte
             */
            if(nick.toLowerCase().indexOf("root")>=0) //Si contiene la palabra root asumo que es de soporte
            {
                //Consultar el usuario root
                Map<String, Object> mapParametros = new HashMap<String, Object>();
                mapParametros.put("nick", Usuario.SUPER_USUARIO);
                Usuario usuarioRoot = null; //variable para consultar la variable root
                try {
                    UsuarioServicio usuarioServicio=new UsuarioServicio();
                    usuarioRoot = usuarioServicio.obtenerPorMap(mapParametros).get(0);//obtiene el usuario root de la base de datos 
                    usuarioRoot.isRoot = true;
                } catch (RemoteException ex) {
                    Logger.getLogger(UsuarioServicio.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ServicioCodefacException ex) {
                    Logger.getLogger(UsuarioServicio.class.getName()).log(Level.SEVERE, null, ex);
                }

                
                if(ParametrosSistemaCodefac.MODO.equals(ModoSistemaEnum.PRODUCCION)) //Cuando esta en modo produccion para el root consulto desde los web services
                {
                    if(WebServiceCodefac.getVerificarSoporte(nick, clave))
                    {                            
                            usuario=usuarioRoot;
                            LOG.log(Level.INFO, "Ingresando con el usuario root: "+nick);
                            //setVisible(false);
                    }
                    else
                    {
                        LOG.log(Level.WARNING, "Error al ingresar con el usuario: " + nick);                        
                    }
                }
                else
                {
                    //Seteo directemente una clave independiente de lo que este grabado en la base, ademas como solo es para modo desarrollo no afecta para el desarrollo
                    if(clave.equals("1234")) //Todo: ver si se setea esta variable de forma global aunque no hay necesidad
                    {
                        usuario=usuarioRoot;
                        LOG.log(Level.INFO, "Ingresando con el usuario root en produccion: " + nick);
                        //setVisible(false);
                    }
                    else
                    {
                        //DialogoCodefac.mensaje("Error Login","Datos Incorrectos para root en modo desarrollo",DialogoCodefac.MENSAJE_INCORRECTO);
                    }

                }
                    
            }
            else //Validacion para usuarios normales que no son root
            {            
                //usuario=usuarioServicio.login(usuarioTxt,clave);
                usuario=verificarCredencialesUsuario(nick, clave);
                if(usuario!=null)
                {
                    LOG.log(Level.INFO, "Ingresando con el usuario: "+nick);
                    //setVisible(false);
                }
                else
                {
                    LOG.log(Level.WARNING, "Error al ingresar con el usuario: "+nick);
                    //DialogoCodefac.mensaje("Error Login","Datos Incorrectos",DialogoCodefac.MENSAJE_INCORRECTO);
                }
            }

        }
        /*else
        {
            //DialogoCodefac.mensaje("Advertencia Login","Ingrese todos los campos",DialogoCodefac.MENSAJE_ADVERTENCIA);
        }*/
        
        return usuario;
        
    }
    
    private Usuario verificarCredencialesUsuario(String nick,String clave)
    {
         Map<String,Object> mapParametros=new HashMap<String,Object>();
        mapParametros.put("nick",nick);
        List<Usuario> usuarios=usuarioFacade.findByMap(mapParametros);
        
        if(usuarios.size()>0)
        {
            if(UtilidadesHash.verificarHashBcrypt(clave,usuarios.get(0).getClave()))
            {
                return usuarios.get(0);
            }
        }
        
        return null;
    }
    
    public void eliminar(Usuario entity) throws java.rmi.RemoteException {
        EntityTransaction transaccion=getTransaccion();
        transaccion.begin();
        entity.setEstado(GeneralEnumEstado.ELIMINADO.getEstado());
        entityManager.merge(entity);
        transaccion.commit();
    }
    
    public Usuario cambiarClave(Usuario usuario,String claveAnterior,String claveNueva) throws java.rmi.RemoteException, ServicioCodefacException
    {
        //TODO: Complentar la validacion completa validando con el usuario guardado y el nuevo usuario para ver si los datos coinciden
        //Actualizo las referencia del nuevo objecto a editar
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() {
                String claveHash=UtilidadesHash.generarHashBcrypt(claveNueva);
                usuario.setClave(claveHash);
                entityManager.merge(usuario);
                entityManager.flush();
            }
        });
        //usuario.setClave("Modificando clave");
        System.out.println(usuario);
        return usuario;

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
        
        ///Funcionalidad que permite eliminar perfiles que fueron eliminados
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
        
        //Funcionalidad que permite agregar nuevos perfiles agregados
        if(entity.getPerfilesUsuario()!=null)
        {
            for (PerfilUsuario perfilUsuario : entity.getPerfilesUsuario()) {

                //Si en el objeto origina no tiene el perfil lo agrego
                if(!usuarioOriginal.getPerfilesUsuario().contains(perfilUsuario))
                {
                    //Elimino de la persistencia
                    entityManager.persist(perfilUsuario);
                }

            }
        }
        
        //Actualizo el objeto editado
        entityManager.merge(entity);
        //Actualizo las referencia del nuevo objecto a editar
        //entity.setClave(UtilidadesHash.generarHashBcrypt(entity.getClave()));
        //entityManager.merge(entity);
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
            entity.setClave(UtilidadesHash.generarHashBcrypt(entity.getClave())); //Cifrar la clave para que no puede ser legible 
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
    
    @Deprecated //Todo este metodo ya esta definido en guardar
    public void grabarUsuario(Usuario usuario,String nombrePerfil) throws ServicioCodefacException
    {
        EntityTransaction transaccion=this.getTransaccion();
        try {
            
            transaccion.begin();
            usuario.setClave(UtilidadesHash.generarHashBcrypt(usuario.getClave()));
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
    
    public Usuario consultarUsuarioActivoPorEmpresa(String nick,Empresa empresa) throws ServicioCodefacException,java.rmi.RemoteException
    {
        Map<String, Object> mapParametros = new HashMap<String, Object>();        
        mapParametros = new HashMap<String, Object>();
        mapParametros.put("nick", "root");
        //mapParametros.put("empresa",empresa); TODO: Terminar de implementar esta funcionalidad
        mapParametros.put("estado",GeneralEnumEstado.ACTIVO.getEstado());
        //UsuarioServicioIf usuarioServiceIf = ServiceFactory.getFactory().getUsuarioServicioIf();
        List<Usuario> usuarios=getFacade().findByMap(mapParametros);
        if(usuarios.size()>0)
        {
            return usuarios.get(0);
        }
        return null;
    }
}
