/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade;

import ec.com.codesoft.codefaclite.servidor.service.MetodoInterfaceConsulta;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Mantenimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Mesa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.result.MantenimientoResult;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Query;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class MantenimientoFacade extends AbstractFacade<Mantenimiento>{

    public MantenimientoFacade() {
        super(Mantenimiento.class);
    }
    
    public List<Mantenimiento> obtenerPendientesClasificarUbicacionFacade(Empresa empresa) throws ServicioCodefacException, RemoteException 
    {
        //Mantenimiento mantenimiento;
        //mantenimiento.getUbicacion();
        String queryStr = " SELECT m FROM Mantenimiento m WHERE M.ubicacion IS NULL ";
        Query query = getEntityManager().createQuery(queryStr);
        
        return query.getResultList();
    }
    
    public List<Mantenimiento> consultarMantenimientoFacade(Date fechaInicio, Date fechaFin) throws ServicioCodefacException, RemoteException
    {
        //Mantenimiento m;
        //m.getFechaSalida();
        
        String fechaIngresoStr="";
        if(fechaInicio!=null)
        {
            fechaIngresoStr=" AND m.fechaIngreso>=?1  ";
        }
        
        String fechaFinStr="";
        if(fechaFin!=null)
        {
            fechaFinStr=" AND m.fechaIngreso<=?2  ";
        }
        
        String queryStr = " SELECT m FROM Mantenimiento m WHERE 1=1 AND m.estado<>?3 "+fechaIngresoStr+fechaFinStr;
        Query query = getEntityManager().createQuery(queryStr);
        
        
        query.setParameter(3,Mantenimiento.MantenimientoEnum.ELIMINADO.getLetra());
        
        if(fechaInicio!=null)
        {
            query.setParameter(1,fechaInicio);
        }
        
        if(fechaFin!=null)
        {
            query.setParameter(2,fechaFin);
        }
        
        return query.getResultList();
    }
    
    public Boolean verificarMantenimientoActivoFacade(Mantenimiento objeto) throws ServicioCodefacException, RemoteException 
    {
        /*objeto.getEstado()
        /*String queryStr = " SELECT m FROM Mantenimiento m WHERE m.vehiculo.vin=?1 AND m.estado=?2 AND  ";*/
        return true;
        
    }
}
