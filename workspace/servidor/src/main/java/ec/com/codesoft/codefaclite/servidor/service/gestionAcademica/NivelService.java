/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service.gestionAcademica;

import ec.com.codesoft.codefaclite.servidor.facade.gestionAcademica.NivelFacade;
import ec.com.codesoft.codefaclite.servidor.service.ServiceAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Nivel;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ConstrainViolationExceptionSQL;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.PeriodoEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.NivelServiceIf;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.persistence.exceptions.DatabaseException;

/**
 *
 * @author Carlos
 */
public class NivelService extends ServiceAbstract<Nivel, NivelFacade> implements NivelServiceIf {

    private NivelFacade nivelFacade;

    public NivelService() throws RemoteException {
        super(NivelFacade.class);
        this.nivelFacade = new NivelFacade();
    }
    
    public Nivel grabar(Nivel n) throws ServicioCodefacException {
        try {
            nivelFacade.create(n);
        } catch (ConstrainViolationExceptionSQL ex) {
            Logger.getLogger(AulaService.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioCodefacException("La clave principal ya existe en el sistema");
        } catch (DatabaseException ex) {
            Logger.getLogger(AulaService.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioCodefacException("Error con la base de datos aulas");
        }
        return n;
    }

    public void editar(Nivel n) {
        nivelFacade.edit(n);
    }

    public void eliminar(Nivel n) {
        n.setEstado(PeriodoEnumEstado.ELIMINADO.getEstado());
        nivelFacade.edit(n);
    }

}
