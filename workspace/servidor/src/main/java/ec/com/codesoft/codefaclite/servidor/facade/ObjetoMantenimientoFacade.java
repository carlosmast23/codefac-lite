/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.com.codesoft.codefaclite.servidor.facade;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ObjetoMantenimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SegmentoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.util.List;
import jakarta.persistence.Query;

/**
 *
 * @author DellWin10
 */
public class ObjetoMantenimientoFacade extends AbstractFacade<ObjetoMantenimiento>{
    
    public ObjetoMantenimientoFacade() {
        super(ObjetoMantenimiento.class);
    }
    
    public List<ObjetoMantenimiento> buscarPorVINFacade(Empresa empresa,String vin) throws ServicioCodefacException,java.rmi.RemoteException
    {
        //ObjetoMantenimiento o;
        //o.getEstado();
        //o.getVin();
        
        String queryStr="SELECT u FROM ObjetoMantenimiento u WHERE u.vin LIKE ?1 AND u.estado=?2 ";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter(1,"%"+vin+"%");
        query.setParameter(2,GeneralEnumEstado.ACTIVO.getLetra());
        return query.getResultList();
    }
    
}
