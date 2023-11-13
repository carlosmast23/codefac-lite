/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Departamento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empleado;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ObjetoMantenimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import jakarta.persistence.Query;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public class OrdenTrabajoFacade extends AbstractFacade<OrdenTrabajo>
{
    
    public OrdenTrabajoFacade()
    {
        super(OrdenTrabajo.class);
    }
    
    public List<OrdenTrabajoDetalle> consultaReporteFacade(Date fechaInicial, Date fechaFinal,Departamento  departamento,Empleado empleado,ObjetoMantenimiento objetoMantenimiento ,OrdenTrabajoDetalle.EstadoEnum estado)
    {
        //OrdenTrabajoDetalle otd;
        //otd.getOrdenTrabajo().getObjetoMantenimiento()
        //otd.getEmpleado();
        //otd.getDepartamento();
        //otd.getOrdenTrabajo();
        //ot.get
       // ot.getFechaIngreso();        
        String queryStr="SELECT u FROM OrdenTrabajoDetalle u WHERE 1=1 ";
        
        if (fechaInicial != null) 
        {
            queryStr = queryStr + " AND u.ordenTrabajo.fechaIngreso>=?1 ";
        }

        if (fechaFinal != null) 
        {
            queryStr = queryStr + " AND u.ordenTrabajo.fechaIngreso<=?2 ";
        }   
        
        if(departamento!=null)
        {
            queryStr = queryStr + " AND u.departamento=?3 ";
        }
        
        if(empleado!=null)
        {
            queryStr = queryStr + " AND u.empleado=?4 ";
        }
        
        if(estado!=null)
        {
            queryStr=queryStr+"AND u.estado=?5 ";
        }
        
        if(objetoMantenimiento!=null)
        {
            queryStr=queryStr+" AND u.ordenTrabajo.objetoMantenimiento=?6 ";
        }
        
        
        Query query = getEntityManager().createQuery(queryStr);
        
        
        if(fechaInicial!=null)
            query.setParameter(1,fechaInicial);
        
        if(fechaFinal!=null)
            query.setParameter(2,fechaFinal);
        
        if (departamento != null)
            query.setParameter(3, departamento);
        
        if (empleado != null) {
            query.setParameter(4, empleado);
        }
        
        if (estado != null) {
            query.setParameter(5, estado.getLetra());
        }
        
        if(objetoMantenimiento!=null)
        {
            query.setParameter(6,objetoMantenimiento);
        }
        
        return query.getResultList();
    }
    
    public OrdenTrabajo consultarUltimaOTporObjectoMantenimientoFacade(ObjetoMantenimiento objetoMantenimiento) throws ServicioCodefacException, RemoteException
    {
        //OrdenTrabajo ot;
        //ot.getFechaIngreso();
        //ot.getEstado();
        //ot.getObjetoMantenimiento()
        String queryStr="SELECT u FROM OrdenTrabajo u WHERE u.objetoMantenimiento = ?1 AND u.estado<> ?2 ORDER BY u.fechaIngreso desc ";
        Query query = getEntityManager().createQuery(queryStr);
        
        query.setParameter(1, objetoMantenimiento);
        query.setParameter(2, OrdenTrabajo.EstadoEnum.ELIMINADO.getEstado());
        
        query.setMaxResults(1);
        
        List<OrdenTrabajo> resultadoList= query.getResultList();
        if(resultadoList.size()>0)
        {
            return resultadoList.get(0);
        }
        return null;
    }
    
}
