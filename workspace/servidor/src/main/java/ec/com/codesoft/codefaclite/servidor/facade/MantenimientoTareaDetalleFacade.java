/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empleado;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Mantenimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.MantenimientoTareaDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Mesa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import java.rmi.RemoteException;
import java.util.List;
import jakarta.persistence.Query;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class MantenimientoTareaDetalleFacade extends AbstractFacade<MantenimientoTareaDetalle>{

    public MantenimientoTareaDetalleFacade() 
    {
        super(MantenimientoTareaDetalle.class);
    }
    
    public List<MantenimientoTareaDetalle> obtenerTareasPendientesPorEmpleadoFacade(Empleado empleado) throws ServicioCodefacException, RemoteException 
    {
        //MantenimientoTareaDetalle mtd;
        //mtd.getMantenimiento().getFechaIngreso();
        
        String queryStr=" SELECT mtd FROM MantenimientoTareaDetalle mtd WHERE mtd.mantenimiento.estado<>?3 AND mtd.mantenimiento.estado<>?4 ORDER BY  mtd.mantenimiento.prioridad DESC, mtd.mantenimiento.fechaIngreso";
        Query query = getEntityManager().createQuery(queryStr);
        //query.setParameter(1, empleado);
        //query.setParameter(2, MantenimientoTareaDetalle.EstadoEnum.INICIADO.getLetra());
        query.setParameter(3, Mantenimiento.MantenimientoEnum.ELIMINADO.getLetra());
        query.setParameter(4, Mantenimiento.MantenimientoEnum.TERMINADO.getLetra());
        //query.setParameter(5, Mantenimiento.UbicacionEnum.TALLER.getLetra());
        return query.getResultList();
    }
    
    
}
