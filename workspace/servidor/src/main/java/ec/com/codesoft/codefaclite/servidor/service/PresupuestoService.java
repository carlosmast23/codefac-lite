/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.PresupuestoDetalleFacade;
import ec.com.codesoft.codefaclite.servidor.facade.PresupuestoFacade;
import ec.com.codesoft.codefaclite.servidor.util.ExcepcionDataBaseEnum;
import ec.com.codesoft.codefaclite.servidor.util.UtilidadesExcepciones;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empleado;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Presupuesto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PresupuestoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PresupuestoDetalleActividad;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ProductoActividad;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoProductoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PresupuestoServiceIf;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                if (entity.getTotalVenta() == null || entity.getTotalVenta().compareTo(BigDecimal.ZERO) == 0) 
                {
                    throw new ServicioCodefacException("Error al grabar, el total del presupuesto no puede ser 0");
                }
                
                entity.getOrdenTrabajoDetalle().setEstado(OrdenTrabajoDetalle.EstadoEnum.PRESUPUESTADO.getLetra());

                /**
                 * Recorro todos los detalles para verificar si existe todos los
                 * productos proveedor o los grabo o los edito con los nuevos
                 * valores
                 */
                if (entity.getPresupuestoDetalles() != null) {
                    for (PresupuestoDetalle presupuestoDetalle : entity.getPresupuestoDetalles()) {
                        if (presupuestoDetalle.getProductoProveedor().getId() == null) {
                            entityManager.persist(presupuestoDetalle.getProductoProveedor());
                        } else {
                            entityManager.merge(presupuestoDetalle.getProductoProveedor());
                        }
                        entityManager.flush();
                        crearActividadesPresupuesto(presupuestoDetalle);
                    }
                }
                
                ServiceFactory.getFactory().getKardexServiceIf().grabarProductosReservadosSinTransaccion(entity);
                
                entityManager.persist(entity);
                //entity=entityManager.merge(entity);
                
            }
        });
        
        return entity;
    }
    
    private void crearActividadesPresupuesto(PresupuestoDetalle presupuestoDetalle)throws ServicioCodefacException
    {
        Producto producto=presupuestoDetalle.getProducto();
        if(producto.getTipoProductoEnum().equals(TipoProductoEnum.SERVICIO))
        {
            List<PresupuestoDetalleActividad> productoActividadList=new ArrayList<PresupuestoDetalleActividad>();
            for (ProductoActividad actividad : producto.getActividadList()) 
            {
                
                PresupuestoDetalleActividad actividadPresupuesto=new PresupuestoDetalleActividad();
                //presupuestoDetalle=entityManager.merge(presupuestoDetalle);
                //actividadPresupuesto.setPresupuestoDetalle(presupuestoDetalle);
                actividadPresupuesto.setProductoActividad(actividad);
                actividadPresupuesto.setTerminado(EnumSiNo.NO);
                entityManager.persist(actividadPresupuesto);
                //entityManager.flush();
                
                actividadPresupuesto.setPresupuestoDetalle(presupuestoDetalle);
                entityManager.merge(actividadPresupuesto);
                //entityManager.flush();
                
                productoActividadList.add(actividadPresupuesto);
            }
           
            //Agregar toda la lista al presupuesto detalle
             presupuestoDetalle.setActividadList(productoActividadList);
           entityManager.merge(presupuestoDetalle);
            
        }
    }
    
    public void editar(Presupuesto p)
    {
        presupuestoFacade.edit(p);
    }
    
    public void eliminar(Presupuesto p) throws ServicioCodefacException,RemoteException
    {
        if(p.getEstadoEnum().equals(Presupuesto.EstadoEnum.FACTURADO))
        {
            throw new ServicioCodefacException("No se puede eliminar un presupuesto facturado");
        }
        
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                p.setEstadoEnum(Presupuesto.EstadoEnum.ANULADO);
                entityManager.merge(p);
            }
        });
        
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
    
    public List<Presupuesto> consultarPorOrdenTrabajo(OrdenTrabajo ordenTrabajo) throws ServicioCodefacException,RemoteException
    {
        List<Presupuesto> presupuestoList=getFacade().buscarPorOrdenTrabajoFacade(ordenTrabajo);
             
        return presupuestoList;
    }
    
    public List<PresupuestoDetalleActividad> consultarActividadPresupuesto(Empleado empleado) throws ServicioCodefacException,RemoteException
    {
        return getFacade().consultarActividadesPorEmpleado(empleado,null);
    }
    
    public List<PresupuestoDetalleActividad> consultarActividadesPendientesPresupuesto(Empleado empleado) throws ServicioCodefacException,RemoteException
    {
        return getFacade().consultarActividadesPorEmpleado(empleado,true);
    }
    
    
    public void actualizarActividadesPresupuestos(List<PresupuestoDetalleActividad> actividadList) throws ServicioCodefacException,RemoteException
    {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                for (PresupuestoDetalleActividad actividad : actividadList) 
                {
                    entityManager.merge(actividad);
                }
            }
        });
    }
    
}
