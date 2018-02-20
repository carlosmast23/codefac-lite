/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service.gestionAcademica;

import ec.com.codesoft.codefaclite.servidor.facade.gestionAcademica.PeriodoFacade;
import ec.com.codesoft.codefaclite.servidor.service.ServiceAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PeriodoServiceIf;
import java.rmi.RemoteException;

/**
 *
 * @author CodesoftDesarrollo
 */
public class PeriodoService extends ServiceAbstract<Periodo, PeriodoFacade> implements PeriodoServiceIf {

    private PeriodoFacade periodoFacade;

    public PeriodoService() throws RemoteException {
        super(PeriodoFacade.class);
        this.periodoFacade = new PeriodoFacade();
    }
}
