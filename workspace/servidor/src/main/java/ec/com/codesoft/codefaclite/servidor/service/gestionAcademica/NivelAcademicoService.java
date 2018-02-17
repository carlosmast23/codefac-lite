/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service.gestionAcademica;

import ec.com.codesoft.codefaclite.servidor.facade.gestionAcademica.NivelAcademicoFacade;
import ec.com.codesoft.codefaclite.servidor.service.ServiceAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.NivelAcademico;
import java.rmi.RemoteException;

/**
 *
 * @author Carlos
 */
public class NivelAcademicoService extends ServiceAbstract<NivelAcademico,NivelAcademicoFacade> 
{
    public NivelAcademicoService() throws RemoteException {
        super(NivelAcademicoFacade.class);
    }
    
}
