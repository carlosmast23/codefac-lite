/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade.cartera;

import ec.com.codesoft.codefaclite.servidor.facade.AbstractFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.Cartera;
import java.sql.Date;
import java.util.List;
import javax.persistence.NoResultException;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.CarteraCruce;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.CarteraDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CategoriaMenuEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoCategoriaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Carlos
 */
public class CarteraFacade extends AbstractFacade<Cartera>
{    
    public CarteraFacade() 
    {
        super(Cartera.class);
    }
      
    public List<Cartera> getCarteraSaldoCero(Persona persona, Date fi, Date ff,DocumentoCategoriaEnum categoriaMenuEnum,Cartera.TipoCarteraEnum tipoCartera,Boolean carteraConSaldo)
    {
        String cliente = "", fecha = "", saldo = "";
        if (persona != null) {
            cliente = "c.persona=?1";
        } else {
            cliente = "1=1";
        }
        if (fi == null && ff != null) {
            fecha = " AND c.fechaEmision <= ?3";
        } else if (fi != null && ff == null) {
            fecha = " AND c.fechaEmision >= ?2";
        } else if (fi == null && ff == null) {
            fecha = "";
        } else {
            fecha = " AND (c.fechaEmision BETWEEN ?2 AND ?3)";
        }
        
        /*Cartera cartera;
        DocumentoCategoriaEnum doc;
        cartera.getCarteraDocumentoEnum().getCategoria();*/
        
        if(carteraConSaldo)
            saldo = " AND c.saldo>0";    
        
        /*Cartera cartera=new Cartera();
        cartera.getEstado();*/
        
        String whereDocumentos=obtenerDocumentosDesdeCategoriaDocumento(categoriaMenuEnum,"c.codigoDocumento");
        /*Cartera c;
        c.getPuntoEmision();
        c.getPuntoEstablecimiento();
        c.getSecuencial();*/
        
        try {
            String queryString = "SELECT c FROM Cartera c WHERE " + cliente + fecha + saldo +" AND ("+whereDocumentos+") AND c.tipoCartera=?4 AND c.estado=?5  ORDER BY CAST(c.puntoEmision AS BIGINT) ,CAST(c.puntoEstablecimiento AS BIGINT) ,CAST(c.secuencial AS BIGINT)  asc";
            Query query = getEntityManager().createQuery(queryString);
            if (persona != null) {
                query.setParameter(1, persona);
            }
            if (fi != null) {
                query.setParameter(2, fi);
            }
            if (ff != null) {
                query.setParameter(3, ff);
            }
            
            if(tipoCartera!=null)
            {
                query.setParameter(4,tipoCartera.getLetra());
            }
            
            query.setParameter(5,GeneralEnumEstado.ACTIVO.getEstado());
            
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    private String obtenerDocumentosDesdeCategoriaDocumento(DocumentoCategoriaEnum documentoCategoria,String alias)
    {
        List<DocumentoEnum> documentos=DocumentoEnum.obtenerPorCategoria(documentoCategoria);
        String whereDocumento=" ";
        Boolean primeraIteracion=true;
        for (DocumentoEnum documento: documentos) {
            if(primeraIteracion)
            {                
                primeraIteracion=false;
            }else
            {
                whereDocumento+=" OR ";
            }
            whereDocumento+=alias+"='"+documento.getCodigo()+"' " ;
        }
        return whereDocumento;
        //DocumentoCategoriaEnum documentoCategoria
    }
    
    public List<CarteraCruce> getMovimientoCartera(Persona persona)
    {
        //CarteraCruce cc;
        //c.getCarteraAfectada().getPersona();
        
        String queryString = "SELECT u FROM CarteraCruce u WHERE u.carteraAfectada.persona=?1 ";
        Query query = getEntityManager().createQuery(queryString);
        query.setParameter(1, persona);
        return query.getResultList();
        
    }
    
    /**
     * Metodo que obtiene los valores cruzados para un documento de la cartera que le afecta cruces como por ejemplo facturas
     * @param cartera
     * @return 
     */
    public BigDecimal obtenerValorCruceCarteraAfecta(Cartera cartera)
    {
        CarteraCruce carteraCruce;

        String queryString = "SELECT SUM(u.valor) FROM CarteraCruce u WHERE u.carteraAfectada=?1 ";
        Query query = getEntityManager().createQuery(queryString);
        query.setParameter(1, cartera);
        Number sumatoria=(Number) query.getSingleResult();
        return new BigDecimal(sumatoria.toString());

    }
    
    
    /**
     * Metodo que obtiene los valores cruzados desde una cartera detalle
     * @param carteraDetalle
     * @return 
     */
    public BigDecimal obtenerValorCruceCarteraDetalle(CarteraDetalle  carteraDetalle)
    {
        //carteraDetalle.get
        //carteraCruce.get        
        String queryString = "SELECT SUM(u.valor) FROM CarteraCruce u WHERE u.carteraDetalle=?1 ";
        Query query = getEntityManager().createQuery(queryString);
        query.setParameter(1, carteraDetalle);
        Number sumatoria=(Number) query.getSingleResult();
        return new BigDecimal(sumatoria.toString());

    }
    
     /**
     * Metodo que obtiene los valores cruzados para un documento de la cartera que afecta cruces como por ejemplo abonos
     * @param cartera
     * @return 
     */
    public BigDecimal obtenerValorCruceCarteraAfectados(Cartera cartera)
    {
        String queryString = "SELECT SUM(u.valor) FROM CarteraCruce u WHERE u.carteraDetalle.cartera=?1 ";
        Query query = getEntityManager().createQuery(queryString);
        query.setParameter(1, cartera);
        Number sumatoria=(Number) query.getSingleResult();
        return new BigDecimal(sumatoria.toString());
    }
    
    public List<Cartera> obtenerCarteraPorCobrarFacade(Persona cliente,Empresa empresa)
    {
        /*Cartera c;
        c.getSaldo();*/
        String queryString = "SELECT c FROM Cartera c WHERE c.persona=?1 and c.estado=?2 and c.sucursal.empresa=?3 and c.saldo>0  ";
        Query query=getEntityManager().createQuery(queryString);
        query.setParameter(1,cliente);
        query.setParameter(2,GeneralEnumEstado.ACTIVO.getEstado());
        query.setParameter(3,empresa);
        return query.getResultList();
    }
    
    public BigDecimal obtenerSaldoDisponibleCruzarFacade(Persona cliente,Empresa empresa)
    {
        String queryString = "SELECT SUM(c.saldo) FROM Cartera c WHERE c.persona=?1 and c.estado=?2 and c.sucursal.empresa=?3 and c.saldo>0  ";
        Query query=getEntityManager().createQuery(queryString);
        query.setParameter(1,cliente);
        query.setParameter(2,GeneralEnumEstado.ACTIVO.getEstado());
        query.setParameter(3,empresa);
        return (BigDecimal) query.getSingleResult();
    }
    
}
