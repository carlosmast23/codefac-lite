/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade.transporte;

import ec.com.codesoft.codefaclite.servidor.facade.AbstractFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.GuiaRemision;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Carlos
 */
public class GuiaRemisionFacade extends AbstractFacade<GuiaRemision>{
    
    public GuiaRemisionFacade() {
        super(GuiaRemision.class);
    }
    
    public List<GuiaRemision> obtenerConsultaFacade(Date fechaInicial,Date fechaFinal,ComprobanteEntity.ComprobanteEnumEstado estado) throws ServicioCodefacException, RemoteException    
    {
        GuiaRemision guia;
        //guia.getFechaEmision()
        //guia.getEs
        //guia.getFechaIniciaTransporte();
        String queryString="select u from GuiaRemision u where 1=1 ";
        
        if(fechaInicial!=null)
        {
            queryString+=" AND u.fechaEmision>=?1 ";
        }
        
        if(fechaFinal!=null)
        {
            queryString+="AND u.fechaEmision<=?2 ";
        }
        
        if(estado!=null)
        {
            if(estado.equals(ComprobanteEntity.ComprobanteEnumEstado.TODOS_SRI))
            {
                queryString+=" AND (u.estado=?6 or u.estado=?7 )" ;
            }
            else
            {
                queryString+=" AND u.estado=?5 " ;
            }
            
        }
        
        /**
         * ===================> SETEAR VALORES <=====================
         */
        
        Query query = getEntityManager().createQuery(queryString);

        if (fechaInicial != null) {
            query.setParameter(1,fechaInicial);
        }

        if (fechaFinal != null) {
            query.setParameter(2,fechaFinal);
        }
        
        if(estado!=null)
        {
            if(estado.equals(ComprobanteEntity.ComprobanteEnumEstado.TODOS_SRI))
            {
                query.setParameter(6,ComprobanteEntity.ComprobanteEnumEstado.AUTORIZADO.getEstado());
                query.setParameter(7,ComprobanteEntity.ComprobanteEnumEstado.ELIMINADO_SRI.getEstado());
            }else
            {
                query.setParameter(5,estado.getEstado());
            }            
        }
        
        return query.getResultList();        
    }
    
}
