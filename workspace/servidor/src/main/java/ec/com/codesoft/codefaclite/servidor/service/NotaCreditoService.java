/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.NotaCredito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ConstrainViolationExceptionSQL;
import ec.com.codesoft.codefaclite.servidor.facade.NotaCreditoDetalleFacade;
import ec.com.codesoft.codefaclite.servidor.facade.NotaCreditoFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.NotaCreditoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroEstudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.FacturaEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.NotaCreditoServiceIf;
import ec.com.codesoft.ejemplo.utilidades.texto.UtilidadesTextos;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityTransaction;
import org.eclipse.persistence.exceptions.DatabaseException;

/**
 *
 * @author PC
 */
public class NotaCreditoService extends ServiceAbstract<NotaCredito,NotaCreditoFacade> implements NotaCreditoServiceIf
{

    NotaCreditoFacade notaCreditoFacade;
    NotaCreditoDetalleFacade notaCreditoDetalleFacade;
    ParametroCodefacService parametroCodefacService;

    public NotaCreditoService() throws RemoteException {
        super(NotaCreditoFacade.class);
        this.notaCreditoFacade = new NotaCreditoFacade();
        this.notaCreditoDetalleFacade = new NotaCreditoDetalleFacade();
        parametroCodefacService = new ParametroCodefacService();
    }

    public NotaCredito grabar(NotaCredito notaCredito) {        
        try {
            EntityTransaction transaccion=getTransaccion();
            transaccion.begin();
           
            entityManager.persist(notaCredito);
            //notaCreditoFacade.create(notaCredito);
            /**
             * Aumentar el codigo de la numeracion en los parametros
             */
            ParametroCodefac parametro = parametroCodefacService.getParametroByNombre(ParametroCodefac.SECUENCIAL_NOTA_CREDITO);
            parametro.valor = (Integer.parseInt(parametro.valor) + 1) + "";
            entityManager.persist(parametro);
            
            /**
             * Actualizar la logica de cada modulo dependiendo del tipo de documento de cada detalle
             */
            
            for (NotaCreditoDetalle detalle : notaCredito.getDetalles()) {
                if(detalle.getTipoDocumentoEnum().equals(TipoDocumentoEnum.ACADEMICO))
                {
                    RubroEstudiante rubroEstudiante=ServiceFactory.getFactory().getRubroEstudianteServiceIf().buscarPorId(detalle.getReferenciaId());
                    rubroEstudiante.setEstadoFactura(RubroEstudiante.FacturacionEstadoEnum.SIN_FACTURAR.getLetra());
                    rubroEstudiante.setSaldo(rubroEstudiante.getSaldo().add(detalle.getTotal()));
                    
                    entityManager.merge(rubroEstudiante);
                }
                else
                {
                    //TODO: Falta implementar la logica para los otros modulos  
                }
            }
            
            /**
             * Actualizar el estado de la nota de credito de la factura dependiendo del tipo anuluacion parcial o total
             */
            if (notaCredito.getTotal().compareTo(notaCredito.getFactura().getTotal()) < 0) 
            {
                notaCredito.getFactura().setEstadoNotaCredito(Factura.EstadoNotaCreditoEnum.ANULADO_PARCIAL.getEstado());
            } 
            else 
            {
                notaCredito.getFactura().setEstadoNotaCredito(Factura.EstadoNotaCreditoEnum.ANULADO_TOTAL.getEstado());
            }

            //Actualizar la referencia de la factura con el nuevo estado
            entityManager.merge(notaCredito.getFactura());
            
            
            transaccion.commit();

        } catch (DatabaseException ex) {
            Logger.getLogger(NotaCreditoService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(NotaCreditoService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return notaCredito;
    }
    
    public String getPreimpresoSiguiente() {
        try {
            Integer secuencialSiguiente = Integer.parseInt(parametroCodefacService.getParametroByNombre(ParametroCodefac.SECUENCIAL_NOTA_CREDITO).valor);
            String secuencial = UtilidadesTextos.llenarCarateresIzquierda(secuencialSiguiente.toString(), 8, "0");
            String establecimiento = parametroCodefacService.getParametroByNombre(ParametroCodefac.ESTABLECIMIENTO).valor;
            String puntoEmision = parametroCodefacService.getParametroByNombre(ParametroCodefac.PUNTO_EMISION).valor;
            return puntoEmision + "-" + establecimiento + "-" + secuencial;
        } catch (RemoteException ex) {
            Logger.getLogger(NotaCreditoService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public void editar(NotaCredito notaCredito) {
        notaCreditoFacade.edit(notaCredito);
    }

    public List<NotaCredito> obtenerTodos() {
        return notaCreditoFacade.findAll();
    }

    public List<NotaCredito> obtenerNotasReporte(Persona persona, Date fi, Date ff) {
        return notaCreditoFacade.lista(persona, fi, ff);
    }
}
