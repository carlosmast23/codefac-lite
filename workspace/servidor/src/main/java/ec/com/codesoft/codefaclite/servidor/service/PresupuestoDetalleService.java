/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.PresupuestoDetalleFacade;
import ec.com.codesoft.codefaclite.servidor.util.ExcepcionDataBaseEnum;
import ec.com.codesoft.codefaclite.servidor.util.UtilidadesExcepciones;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Presupuesto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PresupuestoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PresupuestoDetalleServiceIf;
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
        EntityTransaction transaccion = getTransaccion();
        transaccion.begin();
        try {
            entityManager.persist(pd);
            transaccion.commit();
        } catch (PersistenceException ex)
        {
            if(transaccion.isActive())
            {
                transaccion.rollback();
            }
            
            ExcepcionDataBaseEnum excepcionEnum=UtilidadesExcepciones.analizarExcepcionDataBase(ex);
            Logger.getLogger(PersonaService.class.getName()).log(Level.SEVERE, null, ex);
            if(excepcionEnum.equals(ExcepcionDataBaseEnum.CLAVE_DUPLICADO))
            {
                throw new ServicioCodefacException(ExcepcionDataBaseEnum.CLAVE_DUPLICADO.getMensaje());
            }
            else
            {
                throw new ServicioCodefacException(ExcepcionDataBaseEnum.DESCONOCIDO.getMensaje());
            }            
        }
        return pd;
    }
    
    public void editar(PresupuestoDetalle p)
    {
        presupuestoDetalleFacade.edit(p);
    }
    
    public void eliminar(PresupuestoDetalle p)
    {
        //personaFacade.remove(p);
        p.setEstado(GeneralEnumEstado.ELIMINADO.getEstado());
        editar(p);
    }
    
    public List<PresupuestoDetalle> buscar()
    {
        return presupuestoDetalleFacade.findAll();
    }
    
    public List<PresupuestoDetalle> buscarPorPresupuesto(Presupuesto presupuesto) throws ServicioCodefacException, RemoteException
    {
        /*PresupuestoDetalle pd;
        pd.getPresupuesto()*/
        Map<String,Object> mapParametros=new HashMap<String, Object>();
        mapParametros.put("Presupuesto", presupuesto);
        return this.obtenerPorMap(mapParametros);
    }
    
    
}
