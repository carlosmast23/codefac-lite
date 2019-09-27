/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PersonaEstablecimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author Carlos
 */
public class FacturaFacade extends AbstractFacade<Factura> {

    //aca consuultas con la BBDD
//compara objetos no strings
    public FacturaFacade() {
        super(Factura.class);
    }

    public List<Factura> lista(PersonaEstablecimiento persona, Date fi, Date ff, ComprobanteEntity.ComprobanteEnumEstado estadoEnum,Boolean consultarReferidos,Persona referido,Boolean agrupadoReferido,PuntoEmision puntoEmision,Empresa empresa,DocumentoEnum documentoEnum) {
        //Factura factura;
        //factura.getSucursal();
        //factura.getCodigoDocumentoEnum();
        String cliente = "", fecha = "", estadoFactura = "",filtrarReferidos="",ordenarAgrupado="";
        if (persona != null) {
            //cliente = "u.cliente=?1";
            cliente = "u.sucursal=?1";
        } else {
            cliente = "1=1";
        }
        
        if (fi == null && ff != null) {
            fecha = " AND u.fechaEmision <= ?3";
        } else if (fi != null && ff == null) {
            fecha = " AND u.fechaEmision >= ?2";
        } else if (fi == null && ff == null) {
            fecha = "";
        } else {
            fecha = " AND (u.fechaEmision BETWEEN ?2 AND ?3)";
        }
        
        if (estadoEnum!= null) {
            //Si la peticion es por todos sri entonces tengo que setear 2 valores
            if(ComprobanteEntity.ComprobanteEnumEstado.TODOS_SRI.equals(estadoEnum))
            {
                estadoFactura = " AND ( u.estado=?10 or u.estado=?11 ) ";
            }
            else
            {                
                estadoFactura = " AND u.estado=?4";
            }
        }
        
        if(agrupadoReferido)
        {
            ordenarAgrupado=" u.referido ,";
        }
        
        if(consultarReferidos)
        {
            filtrarReferidos=" AND u.referido IS NOT NULL ";
            if(referido!=null)
            {            
                filtrarReferidos+=" AND u.referido=?5 ";
            }
        }
        
        String filtroPuntoEmision="";
        if(puntoEmision!=null)
        {
            filtroPuntoEmision=" AND u.puntoEmision =?12 ";
        }
        //Factura f;
        //f.getPuntoEmision()

        try {
            String queryString = "SELECT u FROM Factura u WHERE u.empresa=?7 and u.codigoDocumento=?6 and  " + cliente + fecha + estadoFactura +filtrarReferidos+filtroPuntoEmision+" ORDER BY"+ ordenarAgrupado+" u.secuencial+0 asc";
            Query query = getEntityManager().createQuery(queryString);
            //System.err.println("QUERY--->"+query.toString());
            if (persona != null) {
                query.setParameter(1, persona);
            }
            if (fi != null) {
                query.setParameter(2, fi);
            }
            if (ff != null) {
                query.setParameter(3, ff);
            }
            if (estadoEnum != null) {
                if(ComprobanteEntity.ComprobanteEnumEstado.TODOS_SRI.equals(estadoEnum))
                {
                    query.setParameter(10,ComprobanteEntity.ComprobanteEnumEstado.AUTORIZADO.getEstado());
                    query.setParameter(11,ComprobanteEntity.ComprobanteEnumEstado.ELIMINADO_SRI.getEstado());
                }else
                {
                    query.setParameter(4, estadoEnum.getEstado());
                }
            }
            
            if (consultarReferidos) 
            {
                if (referido != null) 
                {
                    query.setParameter(5, referido);
                }
            }
            
            //query.setParameter(6,DocumentoEnum.FACTURA.getCodigo());
            query.setParameter(6,documentoEnum.getCodigo());
            query.setParameter(7,empresa);
            
            if (puntoEmision != null) {
                query.setParameter(12,puntoEmision.getPuntoEmision()); //TODO: Grabo este valor porque de esta manera se esta grababndo en la factura
            }
            
            
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Factura> getFacturaEnable() {
        try {
            
            String queryString = "SELECT u FROM Factura u WHERE u.estado<>?1 AND u.estadoNotaCredito<>?2 AND u.estado<>?3";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter(1, ComprobanteEntity.ComprobanteEnumEstado.ELIMINADO.getEstado());
            query.setParameter(2, Factura.EstadoNotaCreditoEnum.ANULADO_PARCIAL.getEstado());
            query.setParameter(3, ComprobanteEntity.ComprobanteEnumEstado.SIN_AUTORIZAR.getEstado());
            return (List<Factura>) query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Factura> queryDialog(String param, int limiteMinimo, int limiteMaximo) {
        String queryString = "SELECT u FROM Factura u WHERE u.estado<>?1 AND u.estado<>?2 AND u.estado<>?3 ";
        queryString += "AND ( f.cliente.razonSocial like ?1 )";

        Query query = getEntityManager().createQuery(queryString);
        query.setParameter(1, param);
        query.setMaxResults(limiteMaximo);
        query.setFirstResult(limiteMinimo);
        return query.getResultList();

    }
    
      public Long getSecuencialProforma(Empresa empresa) {
        try {
            //Factura f;
            //f.getEmpresa();
            String queryString="SELECT MAX(CAST (F.SECUENCIAL AS BIGINT)) FROM FACTURA F WHERE F.EMPRESA_ID=?2 AND F.CODIGO_DOCUMENTO=?1"; //TODO: Por el momento dejo una consulta nativa porque tengo un problema al evaluar el secuencial que en la base de datos esta como string pero esta mapeado como entero y al hacer casting en jpql el compilador se confunde
            //String queryString = "SELECT max( CAST(u.secuencial CHAR(64))  ) FROM Factura u WHERE  u.codigoDocumento=?1";
            Query query = getEntityManager().createNativeQuery(queryString);
            query.setParameter(1, DocumentoEnum.PROFORMA.getCodigo());
            query.setParameter(2,empresa.getId());
            System.out.println("QueryStringNative:"+queryString);
            return (Long) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
      
      public List<Factura> consultarProformasReporteFacade(Persona cliente,Date fechaInicial,Date fechaFinal,Empresa empresa,GeneralEnumEstado estado) 
      {
          Factura factura;
          //factura.getCodigoDocumento()
          //factura.getEstado();
          //factura.getFechaEmision();
          //factura.getCliente()
          String queryString="SELECT F FROM Factura F WHERE F.empresa=?6 and F.codigoDocumento=?1 ";
          if(fechaInicial!=null)
          {
              queryString+=" AND F.fechaEmision>= ?2";
          }
          
          if(fechaFinal!=null)
          {
              queryString+=" AND F.fechaEmision<= ?3";
          }
          
          if(cliente!=null)
          {
              queryString+=" AND F.cliente= ?4";
          }
          
          if(estado!=null)
          {
              queryString+=" AND F.estado=?5 ";
          }
                    
          //String queryString="SELECT F FROM FACTURA F WHERE F.CODIGO_DOCUMENTO=?1 AND F.cliente=?2";
          Query query=getEntityManager().createQuery(queryString);
          query.setParameter(1,DocumentoEnum.PROFORMA.getCodigo());
          
          if(fechaInicial!=null)
          {
              query.setParameter(2,fechaInicial);
          }
          
          if (fechaFinal != null) {
              query.setParameter(3,fechaFinal);
          }
          
          if (cliente != null) {
              query.setParameter(4,cliente);
          }
          
          if (estado != null) {
              query.setParameter(5, estado.getEstado());
          }
          
          query.setParameter(6,empresa);
          return query.getResultList();
          
      }
      
      
      public Factura buscarPorPremimpresoYEstadoFacade(Integer secuencial,BigDecimal puntoEstablecimiento,Integer puntoEmision,ComprobanteEntity.ComprobanteEnumEstado estadoEnum)
      {
          /*Factura f;
          f.getSecuencial();
          f.getPuntoEmision();
          f.getPuntoEstablecimiento();
          f.getEstadoEnum();*/
          
          String queryString="SELECT F FROM Factura F WHERE F.secuencial=?1 AND F.puntoEmision=?2 AND F.puntoEstablecimiento=?3 AND F.estado=?4 ";
          
          Query query=getEntityManager().createQuery(queryString);
          
          query.setParameter(1,secuencial);
          query.setParameter(2,puntoEmision);
          query.setParameter(3,puntoEstablecimiento);
          query.setParameter(4,estadoEnum.getEstado());
          try
          {
              return (Factura) query.getSingleResult();
          }
          catch(NoResultException nre)
          {
              return null;
          }
      }
    //public Long obtenerSecuencialPresupuestos
}
