/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Estudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.EstudianteInscrito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.NivelAcademico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroEstudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
 ;
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
    public List<EstudianteInscrito> obtenerEstudiantesInscritosPorPeriodoYEstudiante(Periodo periodo,Estudiante estudiante)    ;
    /**
     * Metodo que permite registrar un estudiante y crea el rubro de matricula para que pueda facturar posteriormente
     */
    public EstudianteInscrito matricularEstudiante(EstudianteInscrito estudianteInscrito,RubroEstudiante rubroMatricula) throws   ServicioCodefacException;
    
    public void matriculaEstudianteByList(List<EstudianteInscrito> estudiantesPorMatricular);    
    
    public void matricularEstudiantesByMap(Map<NivelAcademico,List<Estudiante>> mapEstudiantes);    
    
    public List<EstudianteInscrito> obtenerEstudiantesInscritos(NivelAcademico nivel,Periodo periodo)   ;
    
    public List<EstudianteInscrito> buscarPorNivelAcademico(Periodo periodo,NivelAcademico nivel) throws ServicioCodefacException ;
    
    public List<EstudianteInscrito> obtenerEstudiantesInscritosPorPeriodo(Periodo periodo);    
    
    public void eliminarEstudiantesInscrito(List<EstudianteInscrito> estudiantesEliminar);    
    
    public Long obtenerTamanioEstudiatesInscritosPorCurso(NivelAcademico nivelAcademico);    
    
    public List<Object[]> consultarRepresentanteConEstudiantesYCursos();    
    
    public EstudianteInscrito obtenerPorEstudianteYNivelYEstado(Estudiante estudiante,NivelAcademico nivel,GeneralEnumEstado estado );    
    
    public EstudianteInscrito buscarEstudianteMatriculadoPeriodoActivo(Estudiante estudiante) throws ServicioCodefacException ;
    
}
