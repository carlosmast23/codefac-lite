/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service.gestionAcademica;

import ec.com.codesoft.codefaclite.servidor.facade.gestionAcademica.EstudianteInscritoFacade;
import ec.com.codesoft.codefaclite.servidor.service.ServiceAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Estudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.EstudianteInscrito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.NivelAcademico;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.EstudianteInscritoServiceIf;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityTransaction;

/**
 *
 * @author Carlos
 */
public class EstudianteInscritoService extends ServiceAbstract<EstudianteInscrito, EstudianteInscritoFacade> implements EstudianteInscritoServiceIf {

    EstudianteInscritoFacade estudianteInscritoFacade;

    public EstudianteInscritoService() throws RemoteException {
        super(EstudianteInscritoFacade.class);
        this.estudianteInscritoFacade = new EstudianteInscritoFacade();

    }

    /**
     * Permite grabar un conjunto de estudiantes para matricular
     *
     * @param mapEstudiantes
     * @throws RemoteException
     */
    public void matricularEstudiantesByMap(Map<NivelAcademico, List<Estudiante>> mapEstudiantes) throws RemoteException {
        EntityTransaction transaccion = getTransaccion();
        transaccion.begin();
        for (Map.Entry<NivelAcademico, List<Estudiante>> entry : mapEstudiantes.entrySet()) {
            NivelAcademico nivelAcademico = entry.getKey();
            List<Estudiante> lista = entry.getValue();

            for (Estudiante estudiante : lista) {
                EstudianteInscrito matricula = new EstudianteInscrito();
                matricula.setEstudiante(estudiante);
                matricula.setNivelAcademico(nivelAcademico);
                entityManager.persist(matricula);
            }

        }

        transaccion.commit();
    }

    @Override
    public List<EstudianteInscrito> obtenerEstudiantesInscritos(NivelAcademico nivel) throws RemoteException {
        return estudianteInscritoFacade.lista(nivel);
    }

}
