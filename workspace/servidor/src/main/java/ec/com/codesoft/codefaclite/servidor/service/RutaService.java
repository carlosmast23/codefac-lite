/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.RutaFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Ruta;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RutaServiceIf;
import java.rmi.RemoteException;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class RutaService extends ServiceAbstract<Ruta,RutaFacade> implements RutaServiceIf{

    public RutaService() throws RemoteException {
        super(RutaFacade.class);
    }

    @Override
    public Ruta grabar(Ruta entity) throws ServicioCodefacException, RemoteException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                validacionGrabar(entity);
                entity.setEstadoEnum(GeneralEnumEstado.ACTIVO);
                entityManager.persist(entity);
            }
        });
        return entity;
    }

    @Override
    public void editar(Ruta entity) throws ServicioCodefacException, RemoteException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                validacionGrabar(entity);
                entityManager.merge(entity);
            }
        });
    }

    @Override
    public void eliminar(Ruta entity) throws ServicioCodefacException, RemoteException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                entity.setEstadoEnum(GeneralEnumEstado.ELIMINADO);
                entityManager.merge(entity);
            }
        });
    }
    
    
    
    
    private void validacionGrabar(Ruta ruta) throws ServicioCodefacException, RemoteException
    {
        if(ruta.getCodigo()==null || ruta.getCodigo().trim().isEmpty())
        {
            throw new ServicioCodefacException("El código de la ruta no puede estar vacio");
        }
        
        if(ruta.getNombre()==null || ruta.getNombre().trim().isEmpty())
        {
            throw new ServicioCodefacException("El nombre de la ruta no puede estar vacio");
        }
        
        if(ruta.getDetallesActivos().size()==0)
        {
            throw new ServicioCodefacException("Las rutas no se pueden grabar con detalles vacios");
        }
        
        if(ruta.getEmpresa()==null)
        {
            throw new ServicioCodefacException("No se puede grabar sin asignar una empresa");
        }
    }
    
    
    
}
