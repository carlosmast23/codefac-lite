/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.FacturaFacade;
import ec.com.codesoft.codefaclite.servidor.facade.OrdenTrabajoDetalleFacade;
import ec.com.codesoft.codefaclite.servidor.facade.OrdenTrabajoFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Departamento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empleado;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ObjetoMantenimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.compra.OrdenCompraDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ConstrainViolationExceptionSQL;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.OrdenTrabajoServiceIf;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityTransaction;
import org.eclipse.persistence.exceptions.DatabaseException;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public class OrdenTrabajoService extends ServiceAbstract<OrdenTrabajo, OrdenTrabajoFacade> implements OrdenTrabajoServiceIf {

    OrdenTrabajoFacade ordenTrabajoFacade;

    public OrdenTrabajoService() throws RemoteException {
        super(OrdenTrabajoFacade.class);
        this.ordenTrabajoFacade = new OrdenTrabajoFacade();
    }

    @Override
    public List<OrdenTrabajo> consultaDialogo(String param, int limiteMinimo, int limiteMaximo) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabarOrdenTrabajo(OrdenTrabajo ordenTrabajo) throws ServicioCodefacException, RemoteException {
        try {
            entityManager.getTransaction().begin(); //Inicio de la transaccion
            entityManager.persist(ordenTrabajo);
            entityManager.getTransaction().commit();
        } catch (Exception exc) {
            exc.addSuppressed(exc);
        }
    }

    @Override
    public List<OrdenTrabajo> obtenerTodos() {
        return ordenTrabajoFacade.findAll();
    }

    @Override
    public void editar(OrdenTrabajo ordenTrabajo) {
        ordenTrabajoFacade.edit(ordenTrabajo);
    }

    @Override
    public void eliminar(OrdenTrabajo ordenTrabajo) throws ServicioCodefacException, java.rmi.RemoteException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                ordenTrabajo.setEstadoEnum(OrdenTrabajo.EstadoEnum.ELIMINADO);
                entityManager.merge(ordenTrabajo);
            }
        });

    }

    @Override
    public List<OrdenTrabajoDetalle> consultarReporte(Date fechaInicial, Date fechaFinal, Departamento departamento, Empleado empleado, OrdenTrabajoDetalle.EstadoEnum estado) throws RemoteException {
        return getFacade().consultaReporteFacade(fechaInicial, fechaFinal, departamento, empleado, estado);
    }

    @Override
    public OrdenTrabajo grabar(OrdenTrabajo ordenTrabajo) throws ServicioCodefacException, java.rmi.RemoteException {

        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                OrdenTrabajo.EstadoEnum estadoEnum = OrdenTrabajo.EstadoEnum.GENERADO;
                ordenTrabajo.setEstadoEnum(estadoEnum);

                for (OrdenTrabajoDetalle ordenTrabajoDetalle : ordenTrabajo.getDetalles()) {
                    /**
                     * Agregar estado por defecto a detalle orden trabajo
                     */
                    ordenTrabajoDetalle.setEstadoEnum(OrdenTrabajoDetalle.EstadoEnum.RECIBIDO);
                }
                entityManager.persist(ordenTrabajo);

                SmsService smsService = new SmsService();
                for (OrdenTrabajoDetalle detalle : ordenTrabajo.getDetalles()) {
                    if (detalle.getEmpleado() != null) {
                        if (detalle.getEmpleado().getTelefonoCelular() != null && !detalle.getEmpleado().getTelefonoCelular().equals("")) {
                            try
                            {
                                smsService.enviarMensaje(detalle.getEmpleado().getTelefonoCelular(), "Nueva orden " + ordenTrabajo.getId() + "," + detalle.getTitulo() + ", Cliente:" + ordenTrabajo.getCliente().getNombreSimple());
                            }catch(ServicioCodefacException se)
                            {
                                se.printStackTrace();
                            }
                        }

                    }
                }

            }
           
        });
        return ordenTrabajo;
    }
    
    /**
     * Metodo que me permite establecer el estado de la orden dea trabajo segun el estado de los detalles
     * @param ordenTrabajo 
     */
    public void actualizarEstadoSinTransaccion(OrdenTrabajo ordenTrabajo)
    {
        //OrdenTrabajo.EstadoEnum estadoEnum=OrdenTrabajo.EstadoEnum.GENERADO;
        
        for (OrdenTrabajoDetalle detalleOrdenTrabajo : ordenTrabajo.getDetalles()) 
        {
            if(detalleOrdenTrabajo.getEstadoEnum().equals(OrdenTrabajoDetalle.EstadoEnum.RECIBIDO) || detalleOrdenTrabajo.getEstadoEnum().equals(OrdenTrabajoDetalle.EstadoEnum.PRESUPUESTADO))
            {
                ordenTrabajo.setEstadoEnum(OrdenTrabajo.EstadoEnum.GENERADO);
                entityManager.merge(ordenTrabajo);
                return ;
            }
        }
        ordenTrabajo.setEstadoEnum(OrdenTrabajo.EstadoEnum.FINALIZADO);
        entityManager.merge(ordenTrabajo);
        
    }
    
    public List<OrdenTrabajoDetalle> filtrarDetallesPorEstadoYEmpleado(OrdenTrabajoDetalle.EstadoEnum estadoEnum,Empleado empleado)throws ServicioCodefacException, java.rmi.RemoteException 
    {
        Map<String,Object> mapParametros=new HashMap<String,Object>();
        mapParametros.put("estado",estadoEnum.getLetra());
        //mapParametros.put("ordenTrabajo.", empresa);
        mapParametros.put("empleado",empleado);
        
        OrdenTrabajoDetalleFacade facade=new OrdenTrabajoDetalleFacade();
        List<OrdenTrabajoDetalle> resultados=facade.findByMap(mapParametros);        
        return resultados;
    }
    
    public void terminarDetallesOrdenesTrabajo(Empleado empleado,List<OrdenTrabajoDetalle> detalles) throws ServicioCodefacException, java.rmi.RemoteException 
    {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() 
        {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException 
            {                
                for (OrdenTrabajoDetalle detalle : detalles) 
                {
                    detalle.setEstadoEnum(OrdenTrabajoDetalle.EstadoEnum.TERMINADO);
                    entityManager.merge(detalle);
                    entityManager.flush();
                }
                
                if(detalles.size()>0)
                {
                    verificarTerminarOrdenTrabajoSinTransaccion(detalles.get(0).getOrdenTrabajo());
                }
            }
        });
    }
    
    public void verificarTerminarOrdenTrabajoSinTransaccion(OrdenTrabajo ordenTrabajo) throws ServicioCodefacException, RemoteException
    {       
        Boolean terminadoTodo=true;
        List<OrdenTrabajoDetalle> detalles= ServiceFactory.getFactory().getOrdenDetalleTrabajoServiceIf().buscarPorOrdenTrabajo(ordenTrabajo);
        for (OrdenTrabajoDetalle detalle : detalles) {
            //Si tiene cualquier estado diferente de TERMINADO o ANULADO significa que aun no esta terminado
            if(!detalle.getEstadoEnum().equals(OrdenTrabajoDetalle.EstadoEnum.TERMINADO) && !detalle.getEstadoEnum().equals(OrdenTrabajoDetalle.EstadoEnum.ANULADO))
            {
                terminadoTodo=false;
                break;
            }
        }
        
        if(terminadoTodo)
        {
            ordenTrabajo.setEstadoEnum(OrdenTrabajo.EstadoEnum.FINALIZADO);        
            entityManager.merge(ordenTrabajo);
        }
    }
}
