/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.SriRetencionRentaFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencionRenta;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SriRetencionRentaServiceIf;
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
public class SriRetencionRentaService extends ServiceAbstract<SriRetencionRenta,SriRetencionRentaFacade> implements SriRetencionRentaServiceIf{

    public SriRetencionRentaService() throws RemoteException {
        super(SriRetencionRentaFacade.class);
    }
    
    public List<SriRetencionRenta> obtenerTodosOrdenadoPorCodigo() throws RemoteException
    {
        try {
            return (List<SriRetencionRenta>) ejecutarTransaccionConResultado(new MetodoInterfaceTransaccionResultado() {
                @Override
                public Object transaccion(EntityManager entityManager) throws ServicioCodefacException, RemoteException {
                    return getFacade().obtenerTodosOrdenadoPorCodigoFacade(entityManager);
                }
            });
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(SriRetencionRentaService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return new ArrayList();
                
    }

    @Override
    public void editar(SriRetencionRenta entity) throws ServicioCodefacException, RemoteException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion(EntityManager entityManager) throws ServicioCodefacException, RemoteException {
                entityManager.merge(entity);
            }
        });
    }
    
    
}
