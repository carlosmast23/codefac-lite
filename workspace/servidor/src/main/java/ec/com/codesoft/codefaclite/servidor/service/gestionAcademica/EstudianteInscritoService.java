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
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroEstudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.EstudianteInscritoServiceIf;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import java.rmi.RemoteException;
import java.util.HashMap;
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
    
    public Long obtenerTamanioPorEstudiante(Estudiante estudiante)
    {
        return getFacade().obtenerTamanioEstudiatesInscritosPorEstudiante(estudiante);
    }
    
    public Long obtenerTamanioEstudiatesInscritosPorCurso(NivelAcademico nivelAcademico) throws RemoteException
    {
        return getFacade().obtenerTamanioEstudiatesInscritosPorCurso(nivelAcademico);
    }
    
    public EstudianteInscrito matricularEstudiante(EstudianteInscrito estudianteInscrito,RubroEstudiante rubroMatricula) throws RemoteException
    {
        EntityTransaction transaccion = getTransaccion();
        transaccion.begin();
        entityManager.persist(estudianteInscrito);
        estudianteInscrito=entityManager.merge(estudianteInscrito);
        rubroMatricula.setEstudianteInscrito(estudianteInscrito);
        rubroMatricula.setFechaGenerado(UtilidadesFecha.getFechaHoy());
        entityManager.persist(rubroMatricula);
        transaccion.commit();
        return estudianteInscrito;
    }
    
    public void eliminarEstudiantesInscrito(List<EstudianteInscrito> estudiantesEliminar) throws RemoteException
    {
        EntityTransaction transaccion = getTransaccion();        
        transaccion.begin();
        
        for (EstudianteInscrito estudianteInscrito : estudiantesEliminar) 
        {
            estudianteInscrito=entityManager.merge(estudianteInscrito);
            estudianteInscrito.setEstado(GeneralEnumEstado.ELIMINADO.getEstado());            
        }
        
        transaccion.commit();
        
    }
    
    public void matriculaEstudianteByList(List<EstudianteInscrito> estudiantesPorMatricular) throws RemoteException
    {
        EntityTransaction transaccion = getTransaccion();
        transaccion.begin();
        
        for (EstudianteInscrito estudianteInscrito : estudiantesPorMatricular) {
            if(estudianteInscrito.getId()==null)
            {
                estudianteInscrito=entityManager.merge(estudianteInscrito);
                estudianteInscrito.setEstado(GeneralEnumEstado.ACTIVO.getEstado());
                entityManager.persist(estudianteInscrito);
            }
            else
            {
                entityManager.merge(estudianteInscrito);            
            }
        }        
        transaccion.commit();
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
    public List<EstudianteInscrito> obtenerEstudiantesInscritos(NivelAcademico nivel,Periodo periodo) throws RemoteException {
        return estudianteInscritoFacade.obtenerEstudiantesInscritos(nivel,periodo);
    }

    @Override
    public List<EstudianteInscrito> obtenerEstudiantesInscritosPorPeriodo(Periodo periodo) throws RemoteException {
        Map<String,Object> mapParametros=new HashMap<String, Object>();
        mapParametros.put("nivelAcademico.periodo",periodo);
        mapParametros.put("estado",GeneralEnumEstado.ACTIVO.getEstado());
        return getFacade().findByMap(mapParametros);
    }
    
    @Override
    public List<EstudianteInscrito> obtenerEstudiantesInscritosPorPeriodoYEstudiante(Periodo periodo,Estudiante estudiante) throws RemoteException {
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("nivelAcademico.periodo", periodo);
        mapParametros.put("estudiante", estudiante);
        mapParametros.put("estado", GeneralEnumEstado.ACTIVO.getEstado());
        return getFacade().findByMap(mapParametros);
    }
}
