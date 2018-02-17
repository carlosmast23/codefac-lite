/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service.gestionAcademica;

import ec.com.codesoft.codefaclite.servidor.facade.gestionAcademica.AulaFacade;
import ec.com.codesoft.codefaclite.servidor.service.ServiceAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Aula;
import java.rmi.RemoteException;

/**
 *
 * @author Carlos
 */
public class AulaService extends ServiceAbstract<Aula,AulaFacade>{

    public AulaService() throws RemoteException {
        super(AulaFacade.class);
    }
    
}
