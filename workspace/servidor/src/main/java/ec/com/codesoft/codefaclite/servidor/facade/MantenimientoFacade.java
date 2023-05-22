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
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import java.rmi.RemoteException;
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
    
}
