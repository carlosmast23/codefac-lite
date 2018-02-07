/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Bodega;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.BodegaEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ConstrainViolationExceptionSQL;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidor.facade.BodegaFacade;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.persistence.exceptions.DatabaseException;

/**
 *
 * @author CodesoftDesarrollo
 */
public class BodegaService extends ServiceAbstract<Bodega, BodegaFacade> {

    private BodegaFacade bodegaFacade;

    public BodegaService() throws RemoteException {
        super(BodegaFacade.class);
        this.bodegaFacade = new BodegaFacade();
    }

    public void grabar(Bodega b) throws ServicioCodefacException {
        try {
            bodegaFacade.create(b);
        } catch (ConstrainViolationExceptionSQL ex) {
            Logger.getLogger(BodegaService.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioCodefacException("La clave principal ya existe en el sistema");
        } catch (DatabaseException ex) {
            Logger.getLogger(BodegaService.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioCodefacException("Error con la base de datos");
        }
    }

    public void editar(Bodega b) {
        bodegaFacade.edit(b);
    }

    public void eliminar(Bodega b) {
        b.setEstado(BodegaEnumEstado.ELIMINADO.getEstado());
        bodegaFacade.edit(b);
    }

}
