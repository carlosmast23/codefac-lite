/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empleado;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ObjetoMantenimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Presupuesto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PresupuestoDetalleActividad;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import jakarta.persistence.Query;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public class PresupuestoFacade extends AbstractFacade<Presupuesto> {

    public PresupuestoFacade() {
        super(Presupuesto.class);
    }

    public List<OrdenTrabajoDetalle> listarOrdenTrabajo(OrdenTrabajo ordenTrabajo) {
        String queryString = " Select DISTINCT (otd) From Presupuesto p INNER JOIN p.ordenTrabajoDetalle otd where otd.ordenTrabajo = ?1";
        Query query = getEntityManager().createQuery(queryString);
        query.setParameter(1, ordenTrabajo);
        return query.getResultList();
    }
    
    public List<Presupuesto> buscarPorOrdenTrabajoFacade(OrdenTrabajo ordenTrabajo) {
        String queryString = " Select DISTINCT (p) From Presupuesto p INNER JOIN p.ordenTrabajoDetalle otd where otd.ordenTrabajo = ?1  ";
        Query query = getEntityManager().createQuery(queryString);
        query.setParameter(1, ordenTrabajo);
        return query.getResultList();
    }

    public List<Presupuesto> consultarPresupuestos(Date fechaInicial, Date fechaFinal, Persona cliente,String codigoObjetoMantenimiento, Presupuesto.EstadoEnum estadoEnum) 
    {
        //Presupuesto presupuesto;
        //presupuesto.getOrdenTrabajoDetalle().getOrdenTrabajo().getObjetoMantenimiento().getCodigo();
        
        //presupuesto.getEstado();        
        String queryString = " Select DISTINCT p From Presupuesto p where 1=1 ";

        if (fechaInicial != null) {
            queryString += " AND p.fechaPresupuesto>=?1";
        }

        if (fechaFinal != null) {
            queryString += " AND p.fechaPresupuesto<=?2";
        }

        if (cliente != null) {
            queryString += " AND p.persona=?3";
        }

        if (estadoEnum != null) {
            queryString += " AND p.estado=?4";
        }
        
        if(!UtilidadesTextos.verificarNullOVacio(codigoObjetoMantenimiento))
        {
            queryString+=" AND p.ordenTrabajoDetalle.ordenTrabajo.objetoMantenimiento.codigo=?5 "; 
        }

        Query query = getEntityManager().createQuery(queryString);

        if (fechaInicial != null) {
            query.setParameter(1, fechaInicial);
        }

        if (fechaFinal != null) {
            query.setParameter(2, fechaFinal);
        }

        if (cliente != null) {
            query.setParameter(3, cliente);
        }

        if (estadoEnum != null) {
            query.setParameter(4, estadoEnum.getLetra());
        }
        
        if(!UtilidadesTextos.verificarNullOVacio(codigoObjetoMantenimiento))
        {
            query.setParameter(5,codigoObjetoMantenimiento);
        }
        return query.getResultList();
    }
    
    public List<PresupuestoDetalleActividad> consultarActividades(Date fechaInicial, Date fechaFinal, Persona cliente,Usuario usuario,String codigoObjetoMantenimiento, Presupuesto.EstadoEnum estadoEnum) 
    {
        //PresupuestoDetalleActividad p;
        //p.getUsuario()
        //p.getPresupuestoDetalle().getPresupuesto();
        //presupuestoDetalleActividad.get
        //Presupuesto presupuesto;
        //presupuesto.getOrdenTrabajoDetalle().getOrdenTrabajo().getObjetoMantenimiento().getCodigo();
        
        //presupuesto.getEstado();        
        String queryString = " Select DISTINCT p From PresupuestoDetalleActividad p where 1=1 ";

        if (fechaInicial != null) {
            queryString += " AND p.presupuestoDetalle.presupuesto.fechaPresupuesto>=?1";
        }

        if (fechaFinal != null) {
            queryString += " AND p.presupuestoDetalle.presupuesto.fechaPresupuesto<=?2";
        }

        if (cliente != null) {
            queryString += " AND p.presupuestoDetalle.presupuesto.persona=?3";
        }

        if (estadoEnum != null) {
            queryString += " AND p.presupuestoDetalle.presupuesto.estado=?4";
        }
        
        if(usuario!=null)
        {
            queryString+=" AND p.usuario=?5";
        }
        
        if(!UtilidadesTextos.verificarNullOVacio(codigoObjetoMantenimiento))
        {
            queryString+=" AND p.presupuestoDetalle.presupuesto.ordenTrabajoDetalle.ordenTrabajo.objetoMantenimiento.codigo=?5 "; 
        }

        Query query = getEntityManager().createQuery(queryString);

        if (fechaInicial != null) {
            query.setParameter(1, fechaInicial);
        }

        if (fechaFinal != null) {
            query.setParameter(2, fechaFinal);
        }

        if (cliente != null) {
            query.setParameter(3, cliente);
        }

        if (estadoEnum != null) {
            query.setParameter(4, estadoEnum.getLetra());
        }
        
        if(usuario!=null)
        {
            query.setParameter(5,usuario);
        }
        
        if(!UtilidadesTextos.verificarNullOVacio(codigoObjetoMantenimiento))
        {
            query.setParameter(5,codigoObjetoMantenimiento);
        }
        return query.getResultList();
    }
    
    public List<PresupuestoDetalleActividad> consultarActividadesPorEmpleado(Empleado empleado,Boolean actividadesPendientes)
    {
        //Presupuesto p;
        //p.getEstadoEnum()
        PresupuestoDetalleActividad actividad;
        //Sactividad.getTerminado()
        //actividad.getPresupuestoDetalle().getPresupuesto().getEstado();
        //actividad.getPresupuestoDetalle().getEmpleado();
        String whereActividadesPendiente="";
        if(actividadesPendientes!=null)
        {
            whereActividadesPendiente=" AND p.terminado = ?3 ";
        }
        
        String whereEmpleado="";
        if(empleado!=null)
        {
            whereEmpleado=" AND p.presupuestoDetalle.empleado=?1";
        }
        
        String queryString = " Select DISTINCT p FROM PresupuestoDetalleActividad p WHERE p.presupuestoDetalle.presupuesto.estado<>?2 "+whereActividadesPendiente+whereEmpleado;
        
        Query query = getEntityManager().createQuery(queryString);
        
        if(empleado!=null)
        {
            query.setParameter(1,empleado);
        }
        
        query.setParameter(2,Presupuesto.EstadoEnum.FACTURADO.getLetra());
        
        if(actividadesPendientes!=null)
        {
            query.setParameter(3,EnumSiNo.NO.getLetra());
        }
        
        return query.getResultList();
    }
    
    public Presupuesto consultarUltimaOTporObjectoMantenimientoFacade(ObjetoMantenimiento objetoMantenimiento) throws ServicioCodefacException, RemoteException
    {
        //Presupuesto p;
        //p.getFechaCreacion()
        //p.getOrdenTrabajoDetalle().getOrdenTrabajo().getObjetoMantenimiento()
        //OrdenTrabajo ot;
        //ot.getFechaIngreso();
        //ot.getEstado();
        //ot.getObjetoMantenimiento()
        String queryStr="SELECT u FROM Presupuesto u WHERE u.ordenTrabajoDetalle.ordenTrabajo.objetoMantenimiento = ?1 AND u.estado<> ?2 ORDER BY u.fechaCreacion desc ";
        Query query = getEntityManager().createQuery(queryStr);
        
        query.setParameter(1, objetoMantenimiento);
        query.setParameter(2, OrdenTrabajo.EstadoEnum.ELIMINADO.getEstado());
        
        query.setMaxResults(1);
        
        List<Presupuesto> resultadoList= query.getResultList();
        if(resultadoList.size()>0)
        {
            return resultadoList.get(0);
        }
        return null;
    }
    
}
