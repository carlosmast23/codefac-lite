/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service.pos;

import ec.com.codesoft.codefaclite.servidor.facade.pos.IngresoCajaFacade;
import ec.com.codesoft.codefaclite.servidor.service.MetodoInterfaceTransaccion;
import ec.com.codesoft.codefaclite.servidor.service.MetodoInterfaceTransaccionResultado;
import ec.com.codesoft.codefaclite.servidor.service.ServiceAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.Caja;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.CajaSession;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.IngresoCaja;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ServiceAbstractIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.pos.IngresoCajaServiceIf;
import jakarta.persistence.EntityManager;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Robert
 */
public class IngresoCajaService extends ServiceAbstract<IngresoCaja, IngresoCajaFacade> implements IngresoCajaServiceIf{

    public IngresoCajaService() throws RemoteException {
        super(IngresoCajaFacade.class);
    }
    
    @Override
    public IngresoCaja grabar(IngresoCaja entity) throws ServicioCodefacException, RemoteException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion(EntityManager entityManager) throws ServicioCodefacException, RemoteException {
                entityManager.persist(entity);
            }
        });
        return entity;
    }

    
    public void eliminarPorId(Long id) throws ServicioCodefacException, RemoteException {
        
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion(EntityManager entityManager) throws ServicioCodefacException, RemoteException {
                IngresoCaja ingresoCaja=getFacade().find(id,entityManager);
                eliminar(ingresoCaja);
            }
        });
        
        
    }
    
    
    
    public List<IngresoCaja> consultarPorCajaSession(CajaSession cajaSession) throws ServicioCodefacException, RemoteException 
    {
        return (List<IngresoCaja>) ejecutarTransaccionConResultado(new MetodoInterfaceTransaccionResultado() {
            @Override
            public Object transaccion(EntityManager entityManager) throws ServicioCodefacException, RemoteException {
                return getFacade().consultarPorCajaSession(cajaSession,entityManager);
            }
        });
        
    }
    
}
