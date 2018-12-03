/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.PuntoVentaFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoVenta;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PuntoVentaServiceIf;
import java.rmi.RemoteException;

/**
 *
 * @author Carlos
 */
public class PuntoVentaService extends ServiceAbstract<PuntoVenta,PuntoVentaFacade> implements PuntoVentaServiceIf
{
    public PuntoVentaService() throws RemoteException {
        super(PuntoVentaFacade.class);
    }

    @Override
    public PuntoVenta grabar(PuntoVenta entity) throws ServicioCodefacException, RemoteException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                entity.setEstado(GeneralEnumEstado.ACTIVO.getEstado());
                entityManager.persist(entity);
            }
        });
        return entity;
    }
    
    
    
}
