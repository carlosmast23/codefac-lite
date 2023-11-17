/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.ProductoComponenteFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ProductoComponente;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Zona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ProductoComponenteServiceIf;
import java.rmi.RemoteException;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class ProductoComponenteService extends ServiceAbstract<ProductoComponente, ProductoComponenteFacade> implements ProductoComponenteServiceIf{

    public ProductoComponenteService() throws RemoteException 
    {
        super(ProductoComponenteFacade.class);
    }
    
    
    @Override
    public ProductoComponente grabar(ProductoComponente entity) throws ServicioCodefacException, RemoteException {
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
    public void editar(ProductoComponente entity) throws ServicioCodefacException, RemoteException {
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
    public void eliminar(ProductoComponente entity) throws ServicioCodefacException, RemoteException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                entity.setEstadoEnum(GeneralEnumEstado.ELIMINADO);
                entityManager.merge(entity);
            }
        });

    }

    
 
    private void validar(ProductoComponente productoComponente) throws ServicioCodefacException, RemoteException 
    {
        if (productoComponente.getNombre().isEmpty()) {
            throw new ServicioCodefacException("No se puede grabar sin nombre");
        }

    }
    
}
