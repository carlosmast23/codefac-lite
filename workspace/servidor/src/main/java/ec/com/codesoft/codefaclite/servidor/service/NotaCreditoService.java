/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.entity.NotaCredito;
import ec.com.codesoft.codefaclite.servidor.entity.Persona;
import ec.com.codesoft.codefaclite.servidor.excepciones.ConstrainViolationExceptionSQL;
import ec.com.codesoft.codefaclite.servidor.facade.NotaCreditoDetalleFacade;
import ec.com.codesoft.codefaclite.servidor.facade.NotaCreditoFacade;
import java.sql.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.persistence.exceptions.DatabaseException;

/**
 *
 * @author PC
 */
public class NotaCreditoService extends ServiceAbstract<NotaCredito,NotaCreditoFacade>{

    NotaCreditoFacade notaCreditoFacade;
    NotaCreditoDetalleFacade notaCreditoDetalleFacade;
    ParametroCodefacService parametroCodefacService;

    public NotaCreditoService() {
        super(NotaCreditoFacade.class);
        this.notaCreditoFacade = new NotaCreditoFacade();
        this.notaCreditoDetalleFacade = new NotaCreditoDetalleFacade();
        parametroCodefacService = new ParametroCodefacService();
    }

    public void grabar(NotaCredito notaCredito) {
        try {
            notaCreditoFacade.create(notaCredito);
        } catch (ConstrainViolationExceptionSQL ex) {
            Logger.getLogger(NotaCreditoService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DatabaseException ex) {
            Logger.getLogger(NotaCreditoService.class.getName()).log(Level.SEVERE, null, ex);
        }
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
