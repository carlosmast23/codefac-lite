/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.PuntoEmisionFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PuntoEmisionServiceIf;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class PuntoEmisionService extends ServiceAbstract<PuntoEmision,PuntoEmisionFacade> implements PuntoEmisionServiceIf
{
    public PuntoEmisionService() throws RemoteException {
        super(PuntoEmisionFacade.class);
    }

    @Override
    public PuntoEmision grabar(PuntoEmision entity) throws ServicioCodefacException, RemoteException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                entity.setEstado(GeneralEnumEstado.ACTIVO.getEstado());
                entityManager.persist(entity);
            }
        });
        return entity;
    }
    
    @Override
    public List<PuntoEmision> obtenerActivosPorSucursal(Sucursal sucursal) throws ServicioCodefacException, RemoteException {
       // ejecutarTransaccion(new MetodoInterfaceTransaccion() {
       PuntoEmision pv;
       //pv.getS
        Map<String,Object> mapParametros=new HashMap<String,Object>();
        mapParametros.put("sucursal",sucursal);
        mapParametros.put("estado",GeneralEnumEstado.ACTIVO.getEstado());
        return getFacade().findByMap(mapParametros);
            
    }

    @Override
    public void eliminar(PuntoEmision entity) throws RemoteException {
        try {
            ejecutarTransaccion(new MetodoInterfaceTransaccion() {
                @Override
                public void transaccion() throws ServicioCodefacException, RemoteException {
                    entity.setEstado(GeneralEnumEstado.ELIMINADO.getEstado());
                    entityManager.merge(entity);
                }
            });
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(PuntoEmisionService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @Override
    public PuntoEmision obtenerPorCodigo(Integer codigo) throws ServicioCodefacException, RemoteException {
       Map<String,Object> mapParametros=new HashMap<String,Object>();
      
       mapParametros.put("estado",GeneralEnumEstado.ACTIVO.getEstado());
       mapParametros.put("puntoEmision",codigo);
       List<PuntoEmision> resultados=getFacade().findByMap(mapParametros);
       if(resultados.size()>0)
       {
            return resultados.get(0);
       }
       return null;
    }
    
    
}