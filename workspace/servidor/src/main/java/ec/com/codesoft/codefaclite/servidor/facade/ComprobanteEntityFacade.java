/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Bodega;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.NotaCredito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Retencion;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.GuiaRemision;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Carlos
 */          
public class ComprobanteEntityFacade extends AbstractFacade<ComprobanteEntity> {

    public ComprobanteEntityFacade() {
        super(ComprobanteEntity.class);
    }
    
    
    public List<ComprobanteEntity> validarSecuencialRepetidoComprobanteFacade(Empresa empresa,DocumentoEnum documentoEnum,BigDecimal puntoEstablecimiento,Integer puntoEmision,Integer Secuencial)
    {

        String nombreTabla="";
        switch(documentoEnum)
        {
            case FACTURA:
                nombreTabla="Factura";
                break;

            case NOTA_VENTA:
                nombreTabla="Factura";
                break;
                
            case NOTA_VENTA_INTERNA:
                nombreTabla="Factura";
                break;


            case RETENCIONES:
                nombreTabla="Retencion";
                break;

            case NOTA_CREDITO:
                 nombreTabla="NotaCredito";
                break;

            case GUIA_REMISION:
                 nombreTabla="GuiaRemision";
                break;
        }
        
        /*Factura f;
        //f.getEmpresa();
        f.getPuntoEstablecimiento();
        f.getPuntoEmision();
        f.getSecuencial();*/
        
        String queryString = "SELECT f FROM "+nombreTabla+" f WHERE f.estado<>?1 AND f.empresa=?2 AND f.puntoEstablecimiento=?3 AND f.puntoEmision=?4 AND f.secuencial=?5";
        Query query = getEntityManager().createQuery(queryString);
        
        query.setParameter(1, ComprobanteEntity.ComprobanteEnumEstado.ELIMINADO.getEstado()); //TODO: Buscar cualquier 
        query.setParameter(2, empresa);
        query.setParameter(3, puntoEstablecimiento);
        query.setParameter(4, puntoEmision);
        query.setParameter(5, Secuencial);
        return query.getResultList();
        //return (Usuario) query.getSingleResult();
        
    }
}
