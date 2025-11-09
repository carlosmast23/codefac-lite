/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.SriRetencionIvaFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencionIva;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SriRetencionIvaServiceIf;
import jakarta.persistence.EntityManager;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class SriRetencionIvaService extends ServiceAbstract<SriRetencionIva,SriRetencionIvaFacade> implements SriRetencionIvaServiceIf{

    public SriRetencionIvaService() throws RemoteException {
        super(SriRetencionIvaFacade.class);
    }
    
    public List<SriRetencionIva> obtenerTodosOrdenadoPorCodigo() throws RemoteException
    {
        try {
            return (List<SriRetencionIva>) ejecutarTransaccionConResultado(new MetodoInterfaceTransaccionResultado() {
                @Override
                public Object transaccion(EntityManager entityManager) throws ServicioCodefacException, RemoteException {
                    return getFacade().obtenerTodosOrdenadoPorCodigoFacade(entityManager);
                }
            });
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(SriRetencionIvaService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return new ArrayList();
    }

    @Override
    public SriRetencionIva grabar(SriRetencionIva entity) throws ServicioCodefacException, RemoteException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion(EntityManager entityManager) throws ServicioCodefacException, RemoteException {
                entityManager.persist(entity);
            }
        });
        return entity;
    }

    @Override
    public void editar(SriRetencionIva entity) throws ServicioCodefacException, RemoteException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion(EntityManager entityManager) throws ServicioCodefacException, RemoteException {
                entityManager.merge(entity);
            }
        });
    }
    
    
    
    
    
}
