/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Perfil;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidor.facade.PerfilFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PermisoVentana;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.rmi.RemoteException;
import java.util.List;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PerfilServiceIf;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityTransaction;

/**
 *
 * @author Carlos
 */
public class PerfilService extends ServiceAbstract<Perfil,PerfilFacade> implements PerfilServiceIf{

    private PerfilFacade perfilFacade;
    public PerfilService() throws RemoteException 
    {
        super(PerfilFacade.class);
        this.perfilFacade=new PerfilFacade();
    }
    
    
    public Perfil grabar(Perfil entity) throws ServicioCodefacException,java.rmi.RemoteException
    {
        EntityTransaction transaction=getTransaccion();
        transaction.begin();
        entity.setEstado(GeneralEnumEstado.ACTIVO.getEstado());
        entityManager.persist(entity);
        transaction.commit();
        return entity;
    }
    
    public void eliminar(Perfil entity) throws java.rmi.RemoteException
    {
        EntityTransaction transaction=getTransaccion();
        transaction.begin();        
        entity.setEstado(GeneralEnumEstado.ELIMINADO.getEstado());
        entityManager.merge(entity);
        transaction.commit();
    }
    
    public void editar(Perfil entity)
    {
        try {
            EntityTransaction transaction=getTransaccion();
            transaction.begin();
            
            Perfil perfilSinModificar = buscarPorId(entity.getId());
            for (PermisoVentana permisoVentana : perfilSinModificar.getVentanasPermisos()) {
                //Comprabar si algun objeto fue eliminado
                if (!entity.getVentanasPermisos().contains(permisoVentana)) {
                    entityManager.remove(permisoVentana);
                }
            }            
            
            entityManager.merge(entity);
            transaction.commit();
        } catch (RemoteException ex) {
            Logger.getLogger(PerfilService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Perfil> obtenerPerfilesPorUsuario(Usuario usuario)
    {
        return this.perfilFacade.getPerfilesByUsuario(usuario);
    }
    
}
