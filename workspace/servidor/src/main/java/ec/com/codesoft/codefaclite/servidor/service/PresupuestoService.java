/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.PresupuestoFacade;
import ec.com.codesoft.codefaclite.servidor.util.ExcepcionDataBaseEnum;
import ec.com.codesoft.codefaclite.servidor.util.UtilidadesExcepciones;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Presupuesto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PresupuestoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PresupuestoServiceIf;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public class PresupuestoService extends ServiceAbstract<Presupuesto, PresupuestoFacade> implements PresupuestoServiceIf
{
    private PresupuestoFacade presupuestoFacade;

    public PresupuestoService() throws RemoteException 
    {
        super(PresupuestoFacade.class);
        this.presupuestoFacade = new PresupuestoFacade();
    }
    
    public Presupuesto grabar(Presupuesto entity) throws ServicioCodefacException
    {
        
        EntityTransaction transaccion=entityManager.getTransaction();
        try {
            transaccion.begin();
            /**
             * Cambiar el estado del detalle de la Orden de trabajo
             */
            entity.getOrdenTrabajoDetalle().setEstado(OrdenTrabajoDetalle.EstadoEnum.PRESUPUESTADO.getLetra());
            
            /**
             * Recorro todos los detalles para verificar si existe todos los productos proveedor o los grabo o los edito con los nuevos valores
             */ 
            for (PresupuestoDetalle presupuestoDetalle : entity.getPresupuestoDetalles()) {
                if (presupuestoDetalle.getProductoProveedor().getId() == null) {
                    entityManager.persist(presupuestoDetalle.getProductoProveedor());
                } else {
                    entityManager.merge(presupuestoDetalle.getProductoProveedor());
                }
            }

            entityManager.persist(entity);
            transaccion.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaccion.rollback();
            throw new ServicioCodefacException("Error al grabar la compra");
        }
        return entity;
    }
    
    public void editar(Presupuesto p)
    {
        presupuestoFacade.edit(p);
    }
    
    public void eliminar(Presupuesto p)
    {
        p.setEstado(GeneralEnumEstado.ELIMINADO.getEstado());
        editar(p);
    }
    
    public List<Presupuesto> buscar()
    {
        return presupuestoFacade.findAll();
    }
    
    public List<OrdenTrabajoDetalle> listarOrdenesTrabajo(OrdenTrabajo ordenTrabajo)
    {
        return presupuestoFacade.listarOrdenTrabajo(ordenTrabajo);
    }
    
    public List<Presupuesto> consultarPresupuestos(Date fechaInicial, Date fechaFinal,Persona cliente,Presupuesto.EstadoEnum estadoEnum) throws ServicioCodefacException,java.rmi.RemoteException
    {
        return getFacade().consultarPresupuestos(fechaInicial, fechaFinal, cliente, estadoEnum);
    }
    
    
    
}
