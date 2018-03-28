/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Estudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.EstudianteInscrito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.NivelAcademico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroEstudiante;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Carlos
 */
public interface EstudianteInscritoServiceIf extends ServiceAbstractIf<EstudianteInscrito>{
    
    /**
     * Obtiene los estudiantes inscritos que pertenescan a ese Estudiante y a el periodo
     */
    public List<EstudianteInscrito> obtenerEstudiantesInscritosPorPeriodoYEstudiante(Periodo periodo,Estudiante estudiante) throws RemoteException ;
    /**
     * Metodo que permite registrar un estudiante y crea el rubro de matricula para que pueda facturar posteriormente
     */
    public void matricularEstudiante(EstudianteInscrito estudianteInscrito,RubroEstudiante rubroMatricula) throws RemoteException;
    
    public void matriculaEstudianteByList(List<EstudianteInscrito> estudiantesPorMatricular) throws RemoteException;
    
    public void matricularEstudiantesByMap(Map<NivelAcademico,List<Estudiante>> mapEstudiantes) throws RemoteException;
    
    public List<EstudianteInscrito> obtenerEstudiantesInscritos(NivelAcademico nivel) throws java.rmi.RemoteException;
    
    public List<EstudianteInscrito> obtenerEstudiantesInscritosPorPeriodo(Periodo periodo) throws RemoteException;
    
    public void eliminarEstudiantesInscrito(List<EstudianteInscrito> estudiantesEliminar) throws RemoteException;

}
