/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service.gestionAcademica;

import ec.com.codesoft.codefaclite.servidor.facade.gestionAcademica.EstudianteFacade;
import ec.com.codesoft.codefaclite.servidor.service.ServiceAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Estudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.NivelAcademico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.EstudianteServiceIf;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Carlos
 */
public class EstudianteService extends ServiceAbstract<Estudiante,EstudianteFacade> implements EstudianteServiceIf{

    EstudianteFacade estudianteFacade;
    
    public EstudianteService() throws RemoteException {
        super(EstudianteFacade.class);
        estudianteFacade=new EstudianteFacade();
    }
    
    public List<Estudiante> estudianteSinMatriculaPorPeriodo(Periodo periodo)throws RemoteException
    {
        return estudianteFacade.getEstudiantesSinMatricula(periodo);
    }
    
    public List<Estudiante> estudianteNuevosSinMatricula() throws RemoteException
    {
        return estudianteFacade.getEstudiantesNuevos();
    }
    
    
}
