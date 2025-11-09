/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.AbstractFacade;
import ec.com.codesoft.codefaclite.servidor.facade.PresupuestoDetalleFacade;
import ec.com.codesoft.codefaclite.servidor.util.ExcepcionDataBaseEnum;
import ec.com.codesoft.codefaclite.servidor.util.UtilidadesExcepciones;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Presupuesto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PresupuestoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PresupuestoDetalleServiceIf;
import jakarta.persistence.EntityManager;
import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public class PresupuestoDetalleService extends ServiceAbstract<PresupuestoDetalle, PresupuestoDetalleFacade> implements PresupuestoDetalleServiceIf
{
    private PresupuestoDetalleFacade presupuestoDetalleFacade;

    public PresupuestoDetalleService() throws RemoteException 
    {
        super(PresupuestoDetalleFacade.class);
        this.presupuestoDetalleFacade = new PresupuestoDetalleFacade();        
    }
    
    public PresupuestoDetalle grabar(PresupuestoDetalle pd) throws ServicioCodefacException
    {
        return (PresupuestoDetalle) ejecutarTransaccionConResultado(new MetodoInterfaceTransaccionResultado() {
            @Override
            public Object transaccion(EntityManager entityManager) throws ServicioCodefacException, RemoteException {
                entityManager.persist(pd);
                //entityManager.commit();
                return pd;
            }
        });
        
       
    }
    

    
    public void eliminar(PresupuestoDetalle p)
    {
        try {
            ejecutarTransaccion(new MetodoInterfaceTransaccion() {
                @Override
                public void transaccion(EntityManager entityManager) throws ServicioCodefacException, RemoteException {

                    //personaFacade.remove(p);
                    p.setEstado(GeneralEnumEstado.ELIMINADO.getEstado());
                    entityManager.merge(p);
                }
            });
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(SriIdentificacionService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
       
    public List<PresupuestoDetalle> buscarPorPresupuesto(Presupuesto presupuesto,EntityManager em) throws ServicioCodefacException, RemoteException
    {

        //EntityManager entityManager=AbstractFacade.nuevoEntityManager();
        Map<String,Object> mapParametros=new HashMap<String, Object>();
        mapParametros.put("presupuesto", presupuesto);
        return this.obtenerPorMap(mapParametros,em);
    }
    
    
}
