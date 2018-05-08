/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.RetencionFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Retencion;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.RetencionDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.FacturaEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoFacturacionEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RetencionServiceIf;
import java.rmi.RemoteException;
import javax.persistence.EntityTransaction;

/**
 *
 * @author Carlos
 */
public class RetencionService extends ServiceAbstract<Retencion,RetencionFacade> implements RetencionServiceIf{

    public RetencionService() throws RemoteException {
        super(RetencionFacade.class);
    }

    
    public Retencion grabar(Retencion entity) throws ServicioCodefacException, RemoteException {
        
        EntityTransaction transaction=getTransaccion();
        transaction.begin();
        
        ParametroCodefacService parametroService=new ParametroCodefacService();
        ParametroCodefac parametro =null;
        if(parametroService.getParametroByNombre(ParametroCodefac.TIPO_FACTURACION).valor.equals(TipoFacturacionEnumEstado.ELECTRONICA.getLetra()))
            {
                //factura.setTipoFacturacion(TipoFacturacionEnumEstado.ELECTRONICA.getLetra());
                parametro = parametroService.getParametroByNombre(ParametroCodefac.SECUENCIAL_RETENCION);
            }
            else
            {
                //Estableciendo estado de facturacion manual
                entity.setEstado(FacturaEnumEstado.FACTURADO.getEstado());                
                parametro = parametroService.getParametroByNombre(ParametroCodefac.SECUENCIAL_RETENCION_FISICA);
            }
        
        for (RetencionDetalle retencionDetalle : entity.getDetalles()) {
            entityManager.persist(retencionDetalle);
        }
        
        entityManager.persist(entity);        
        
        //Aumentar el secuencial para facturar
        parametro.valor = (Integer.parseInt(parametro.valor) + 1) + "";
        entityManager.merge(parametro);
        
        transaction.commit();
        return entity;
    }
    
    
    
}
