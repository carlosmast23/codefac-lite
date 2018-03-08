/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service.gestionAcademica;

import ec.com.codesoft.codefaclite.servidor.facade.gestionAcademica.RubroEstudianteFacade;
import ec.com.codesoft.codefaclite.servidor.service.ServiceAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.EstudianteInscrito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.NivelAcademico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroEstudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubrosNivel;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RubroEstudianteServiceIf;
import java.rmi.RemoteException;
import java.util.List;
import javax.persistence.EntityTransaction;

/**
 *
 * @author Carlos
 */
public class RubroEstudianteService extends ServiceAbstract<RubroEstudiante, RubroEstudianteFacade> implements RubroEstudianteServiceIf {

    RubroEstudianteFacade rubroEstudianteFacade;

    public RubroEstudianteService() throws RemoteException {
        super(RubroEstudianteFacade.class);
        rubroEstudianteFacade= new RubroEstudianteFacade();
    }

    public void crearRubrosEstudiantes(List<EstudianteInscrito> estudiantes, RubrosNivel rubroNivel) throws RemoteException {
        EntityTransaction transaccion = getTransaccion();
        transaccion.begin();
        for (EstudianteInscrito estudiante : estudiantes) {
            RubroEstudiante rubroEstudiante = new RubroEstudiante();
            rubroEstudiante.setEstudianteInscrito(estudiante);
            rubroEstudiante.setRubroNivel(rubroNivel);

            entityManager.persist(rubroEstudiante);
        }
        entityManager.flush();
        transaccion.commit();

    }

    @Override
    public List<RubroEstudiante> obtenerDeudasEstudiante(NivelAcademico nivel) throws RemoteException {
        return rubroEstudianteFacade.obtenerDeudasEstudiante(nivel);
    }

}
