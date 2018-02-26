/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service.gestionAcademica;

import ec.com.codesoft.codefaclite.servidor.facade.gestionAcademica.RubrosNivelFacade;
import ec.com.codesoft.codefaclite.servidor.service.ServiceAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubrosNivel;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RubrosNivelServiceIf;
import java.rmi.RemoteException;

/**
 *
 * @author Carlos
 */
public class RubrosNivelService extends ServiceAbstract<RubrosNivel,RubrosNivelFacade> implements RubrosNivelServiceIf{

    public RubrosNivelService() throws RemoteException {
        super(RubrosNivelFacade.class);
    }
    
}
