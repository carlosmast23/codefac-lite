/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.gestionAcademica.EstudianteInscritoFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.EstudianteInscrito;
import java.rmi.RemoteException;

/**
 *
 * @author Carlos
 */
public class EstudianteInscritoService extends ServiceAbstract<EstudianteInscrito, EstudianteInscritoFacade>
{    
    public EstudianteInscritoService() throws RemoteException {
        super(EstudianteInscritoFacade.class);
    }
    
}
