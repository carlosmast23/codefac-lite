/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.RetencionFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Compra;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Retencion;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.RetencionDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencion;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencionIva;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencionRenta;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RetencionServiceIf;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityTransaction;

/**
 *
 * @author Carlos
 */
public class RetencionService extends ServiceAbstract<Retencion, RetencionFacade> implements RetencionServiceIf {

    RetencionFacade retencionFacade;

    public RetencionService() throws RemoteException {
        super(RetencionFacade.class);
        retencionFacade = new RetencionFacade();
    }

    public Retencion grabar(Retencion entity) throws ServicioCodefacException, RemoteException {
        
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                
                //Verificar si es una retencion libre o tiene una referencia
                if(entity.getTipoDocumentoEnum().equals(TipoDocumentoEnum.LIBRE))
                {
                    entity.setCompra(null); //Si es libre es decir que no guarda referencia y por tal motivo elimino la compra porque solo debe estar enviado una temporal
                }
                else
                {
                    //Actualizar la referencia de la compra para saber que la retencion ya fue enviada y no cargar de nuevo
                    Compra compra=entity.getCompra();
                    compra.setEstadoRetencion(Compra.RetencionEnumCompras.EMITIDO.getEstado());
                    entityManager.merge(compra);
                }
                
                ParametroCodefacService parametroService = new ParametroCodefacService();
                ParametroCodefac parametro = null;
                if (parametroService.getParametroByNombre(ParametroCodefac.TIPO_FACTURACION).valor.equals(ComprobanteEntity.TipoEmisionEnum.ELECTRONICA.getLetra())) {
                    //factura.setTipoFacturacion(TipoFacturacionEnumEstado.ELECTRONICA.getLetra());
                    parametro = parametroService.getParametroByNombre(ParametroCodefac.SECUENCIAL_RETENCION);
                } else {
                    //Estableciendo estado de facturacion manual
                    entity.setEstado(ComprobanteEntity.ComprobanteEnumEstado.AUTORIZADO.getEstado());
                    parametro = parametroService.getParametroByNombre(ParametroCodefac.SECUENCIAL_RETENCION_FISICA);
                }

                for (RetencionDetalle retencionDetalle : entity.getDetalles()) {
                    entityManager.persist(retencionDetalle);
                }

                entityManager.persist(entity);

                //Aumentar el secuencial para facturar
                parametro.valor = (Integer.parseInt(parametro.valor) + 1) + "";
                entityManager.merge(parametro);
                
                

            }
        });

        //EntityTransaction transaction = getTransaccion();
        //transaction.begin();        
        //transaction.commit();
        return entity;
    }

    @Override
    public void eliminar(Retencion entity) throws RemoteException {
        try {
            ejecutarTransaccion(new MetodoInterfaceTransaccion() {
                @Override
                public void transaccion() throws ServicioCodefacException, RemoteException {
                    entity.setEstado(GeneralEnumEstado.ELIMINADO.getEstado());
                    entityManager.merge(entity);
                }
            });
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(RetencionService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public List<RetencionDetalle> obtenerRetencionesReportes(Persona persona, Date fi, Date ff, SriRetencionIva iva, SriRetencionRenta renta, SriRetencion sriRetencion) throws RemoteException {
        //return retencionFacade.lista(persona, fi, ff, iva, renta,tipo);
        return retencionFacade.obtenerRetencionesReportesFacade(persona, fi, ff, iva, renta,sriRetencion);
    }
    public List<Object[]> obtenerRetencionesCodigo(Persona persona, Date fi, Date ff, SriRetencionIva iva, SriRetencionRenta renta,String tipo) {
        return retencionFacade.retencionesCodigo(persona, fi, ff, iva, renta, tipo);
    }

    /*
    @Override
    public List<RetencionDetalle> obtenerRetencionesReportes(Persona persona, Date fi, Date ff, SriRetencionIva iva, SriRetencionRenta renta, String tipo) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/


}
