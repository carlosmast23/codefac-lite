/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service.gestionAcademica;

import ec.com.codesoft.codefaclite.servidor.facade.gestionAcademica.RubroPlantillaFacade;
import ec.com.codesoft.codefaclite.servidor.service.ServiceAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroPlantilla;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.gestionacademica.RubroPlantillaServiceIf;
import java.rmi.RemoteException;

/**
 *
 * @author Carlos
 */
public class RubroPlantillaService extends ServiceAbstract<RubroPlantilla,RubroPlantillaFacade> implements RubroPlantillaServiceIf{

    public RubroPlantillaService() throws RemoteException {
        super(RubroPlantillaFacade.class);
    }
    
    
}
