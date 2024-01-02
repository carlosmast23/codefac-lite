/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade.pos;

import ec.com.codesoft.codefaclite.servidor.facade.AbstractFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.Caja;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.CajaSession;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CajaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CajaSessionEnum;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

/**
 *
 * @author Robert
 */
public class CajaSesionFacade extends AbstractFacade<CajaSession> {
    
    public CajaSesionFacade() {
        super(CajaSession.class);
    }
    
    public CajaSession obtenerUltimaCajaSession(Caja caja) 
    {
        try
        {
            String stringQuery = "Select cs from CajaSession cs where cs.caja = ?1 and cs.estadoCierreCaja = ?2 order by cs.id desc";

            Query query = getEntityManager().createQuery(stringQuery);
            query.setParameter(1, caja);
            query.setParameter(2, CajaSessionEnum.FINALIZADO.getEstado());
            query.setMaxResults(1);

            return (CajaSession) query.getSingleResult();
            /*List<CajaSession> resultadoLista= query.getResultList();
            
            if(resultadoLista.size()>0)
            {
                return resultadoLista.get(0);
            }*/
        } 
        catch (NoResultException e) 
        {
           return null;
        }
        
        //return null;
    }
    
    public List<CajaSession> obtenerCajaSessionPorCajaUsuarioYFecha(Caja caja, Usuario usuario, Date fechaInicio, Date fechaFin,CajaEnum estado)
    {
        try
        {
            Timestamp fechaInicioTimeStamp = UtilidadesFecha.castDateSqlToTimeStampSql(fechaInicio);
            Timestamp fechaFinTimeStamp = UtilidadesFecha.castDateSqlToTimeStampSql(UtilidadesFecha.agregarTiempoFinalDia(fechaFin));
            
            String cajaStr="";
            if(caja!=null)
            {
                cajaStr=" and cs.caja = ?1 ";
            }
            
            String usuarioStr="";
            if(usuario!=null)
            {
                usuarioStr=" and cs.usuario = ?2 ";
            }

            String estadoStr = "";
            if (estado != null) {
                estadoStr = " and cs.estadoCierreCaja= ?7 ";
            }
            
            String stringQuery = "Select cs from CajaSession cs where 1=1  "+cajaStr+usuarioStr+estadoStr;
            String queryStringFecha="";
            
            if(fechaInicio!=null)
            {
                queryStringFecha+=" AND  cs.fechaHoraApertura>=?4 ";
            }

            if(fechaFin!=null)
            {
                queryStringFecha+=" AND  cs.fechaHoraCierre<=?5 ";
            }
            


            stringQuery += queryStringFecha+" ORDER BY cs.fechaHoraApertura DESC";
            Query query = getEntityManager().createQuery(stringQuery);
                        
            //query.setParameter(3, CajaSessionEnum.FINALIZADO.getEstado());
            
            if(caja!=null)
            {
                query.setParameter(1, caja);
            }
            
            if(usuario!=null)
            {
                query.setParameter(2, usuario);
            }

            if(fechaInicio!=null)
            {
                query.setParameter(4, fechaInicioTimeStamp);
            }

            if(fechaFin!=null)
            {
                query.setParameter(5, fechaFinTimeStamp);
            }
            
            if(estado!=null)
            {
                query.setParameter(7, estado.getEstado());
            }

            return query.getResultList();
            
        }catch (NoResultException e) {
            return null;
        }
    }
    
    public List<CajaSession> obtenerCajaSessionPorPuntoEmisionYUsuarioFacade(Integer puntoEmision, Usuario usuario) 
    {
        //TODO: Mejorar esta parte por que fallaba el metodo cuando tenia 2 objetos para comparar y el uno tenia un valor de null
        List<CajaSession> resultado = obtenerCajaSessionPorPuntoEmisionYUsuarioFacadeGeneral(puntoEmision, usuario,1);
        List<CajaSession> resultadoTmp = obtenerCajaSessionPorPuntoEmisionYUsuarioFacadeGeneral(puntoEmision, usuario,2);
        resultado.addAll(resultadoTmp);
        return resultado;
    }
    
    public List<CajaSession> obtenerCajaSessionPorPuntoEmisionYUsuarioFacadeGeneral(Integer puntoEmision, Usuario usuario,Integer numeroPuntoEmision) 
    {
        //CajaSession u;
        //u.getCaja().getPuntoEmision().pun
        
        String wherePuntoEmision="";
        if(puntoEmision!=null)
        {
            wherePuntoEmision=" AND u.caja.puntoEmision?#.puntoEmision = ?3";
        }
        
        String consultaGeneral="SELECT u FROM CajaSession u WHERE u.usuario = ?1 AND u.estadoCierreCaja = ?2 "+wherePuntoEmision+"  ORDER BY u.fechaHoraCierre desc";
        
        if(numeroPuntoEmision==1)
        {
            consultaGeneral = consultaGeneral.replace("?#","");
        }
        else if(numeroPuntoEmision==2)
        {
            consultaGeneral = consultaGeneral.replace("?#","2");
        }
        
        Query query = getEntityManager().createQuery(consultaGeneral);
        query.setParameter(1, usuario);
        query.setParameter(2, CajaSessionEnum.ACTIVO.getEstado());
        
        if(puntoEmision!=null)
        {
            query.setParameter(3, puntoEmision);
        }

        List<CajaSession> resultadoLista = query.getResultList();

        return resultadoLista;
               
    }
    
}
