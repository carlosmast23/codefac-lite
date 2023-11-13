/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade;

import ec.com.codesoft.codefaclite.servidor.service.MantenimientoService;
import ec.com.codesoft.codefaclite.servidor.service.MetodoInterfaceConsulta;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Mantenimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.MarcaProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Mesa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Taller;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.result.MantenimientoResult;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.persistence.Query;
import jakarta.persistence.TemporalType;

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
    
    public List<Mantenimiento> consultarMantenimientoFacade(Date fechaInicio, Date fechaFin,Boolean fechaFinExacto,Taller taller,Mantenimiento.MantenimientoEnum estadoEnum,MarcaProducto marca,Mantenimiento.UbicacionEnum ubicacionEnum) throws ServicioCodefacException, RemoteException
    {
        //Mantenimiento m;
        //m.getTaller().getNombre();
        //m.getEstado()
        //m.getFechaSalida();
        
        String fechaIngresoStr="";
        if(fechaInicio!=null)
        {
            fechaIngresoStr=" AND m.fechaIngreso>=?1  ";
        }
        
        String fechaFinStr="";
        if(fechaFin!=null)
        {
            fechaFin=UtilidadesFecha.agregarTiempoFinalDia(UtilidadesFecha.castDateUtilToSql(fechaFin));
            if(fechaFinExacto!=null && fechaFinExacto)
            {
                fechaFinStr=" AND m.fechaSalida>=?21 and m.fechaSalida<=?22  ";
                //fechaFinStr=" AND FUNC('TRUNC',m.fechaSalida)=FUNC('TRUNC',?2)  ";
            }
            else
            {
                fechaFinStr=" AND m.fechaIngreso<=?2  ";
            }
        }
        
        String estado="";
        if(estadoEnum!=null)
        {
            estado=" AND m.estado=?3 ";
        }
        else
        {
            //Si quiere ver todos los estados por defecto, no muestro los que estan eliminados
            //estado=" AND m.estado<>"+Mantenimiento.MantenimientoEnum.ELIMINADO.getLetra();
            estado=" AND m.estado<>?3";
        }
        
        String marcaStr="";
        if(marca!=null)
        {
            marcaStr=" AND m.marca=?4 ";
        }
        
        String ubicacionEnumStr="";
        if(ubicacionEnum!=null)
        {
            ubicacionEnumStr=" AND m.ubicacion=?5";
        }
        
        String tallerStr="";
        if(taller!=null)
        {
            tallerStr=" AND m.taller=?6 "; 
        }
        else
        {
            tallerStr=" AND m.taller.nombre<>?66 "; 
        }
        
        String queryStr = " SELECT m FROM Mantenimiento m WHERE 1=1 "+fechaIngresoStr+fechaFinStr+estado+marcaStr+ubicacionEnumStr+tallerStr;
        Logger.getLogger(MantenimientoService.class.getName()).log(Level.INFO, queryStr);
        Query query = getEntityManager().createQuery(queryStr);
        
        
        //query.setParameter(3,Mantenimiento.MantenimientoEnum.ELIMINADO.getLetra());
        
        if(fechaInicio!=null)
        {
            query.setParameter(1,fechaInicio);
        }
        
        if(fechaFin!=null)
        {
            if(fechaFinExacto!=null && fechaFinExacto)
            {
                Date FechaFinInicio= UtilidadesFecha.eliminarHorasFecha(fechaFin);
                Date FechaFinFinal=UtilidadesFecha.agregarTiempoFinalDia(UtilidadesFecha.castDateUtilToSql(fechaFin));
                query.setParameter(21,FechaFinInicio);
                query.setParameter(22,FechaFinFinal);
            }
            else
            {
                query.setParameter(2,fechaFin);
            }
        }
        
        if(estadoEnum!=null)
        {
            query.setParameter(3,estadoEnum.getLetra());
        }
        else
        {
            query.setParameter(3,Mantenimiento.MantenimientoEnum.ELIMINADO.getLetra());
        }
        
        if(marca!=null)
        {
            query.setParameter(4,marca);
        }
        
        if(ubicacionEnum!=null)
        {
            query.setParameter(5, ubicacionEnum.getLetra());
        }
        
        if(taller!=null)
        {
            query.setParameter(6, taller);
        }
        else
        {
            query.setParameter(66, "Taller Mecánico");
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
