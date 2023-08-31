/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.TallerFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Taller;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.TallerTarea;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.TallerServiceIf;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class TallerService extends ServiceAbstract<Taller,TallerFacade> implements TallerServiceIf {

    public TallerService() throws RemoteException {
        super(TallerFacade.class);
    }   

    @Override
    public Taller grabar(Taller entity) throws ServicioCodefacException, RemoteException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                
                validar(entity);
                
                entity.setEstadoEnum(GeneralEnumEstado.ACTIVO);
                entityManager.persist(entity);
                
            }
        });
        return entity;
    }

    @Override
    public void editar(Taller entity) throws ServicioCodefacException, RemoteException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                validar(entity);
                
                //entity.setEstadoEnum(GeneralEnumEstado.ACTIVO);
                entityManager.merge(entity);
            }
        });
    }

    @Override
    public void eliminar(Taller entity) throws ServicioCodefacException, RemoteException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                entity.setEstadoEnum(GeneralEnumEstado.ELIMINADO);
                entityManager.merge(entity);
            }
        });
        
    }
    
        
    
    private void validar(Taller taller)throws ServicioCodefacException, RemoteException
    {
        if(taller.getNombre().isEmpty())
        {
            throw new ServicioCodefacException("No se puede grabar sin nombre");
        }
        
    
    }
    
    public List<Taller> obtenerActivos() throws ServicioCodefacException, RemoteException
    {
        Map<String,Object> mapParametros=new HashMap<String, Object>();
        mapParametros.put("estado",GeneralEnumEstado.ACTIVO.getEstado());
        return getFacade().findByMap(mapParametros);
        
    }
    
    public List<TallerTarea> obtenerTareasPorTaller(Taller taller) throws ServicioCodefacException, RemoteException
    {        
        return getFacade().obtenerTareasPorTallerFacade(taller);
    }
    
}
