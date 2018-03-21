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
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroPlantilla;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubrosNivel;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.MesEnum;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Carlos
 */
public interface RubroEstudianteServiceIf extends ServiceAbstractIf<RubroEstudiante> {

    
    public List<RubroEstudiante> obtenerRubrosEstudiantesPorRubros(List<RubrosNivel> rubros) throws RemoteException;
    
    public RubroPlantilla crearRubroEstudiantesDesdePlantila(RubroPlantilla rubroPlantilla,MesEnum mesEnum,String nombreRubroMes) throws RemoteException;

    public void crearRubrosEstudiantes(List<EstudianteInscrito> estudiantes, RubrosNivel rubroNivel) throws RemoteException;

    public void crearRubrosEstudiantes(List<RubroEstudiante> rubrosEstudiantes) throws RemoteException;

    public List<RubroEstudiante> obtenerDeudasEstudiante(Estudiante estudiante) throws java.rmi.RemoteException;

    public List<Object[]> obtenerRubroPeriodoGrupo(Periodo periodo) throws java.rmi.RemoteException;
    public List<NivelAcademico> obtenerRubroPeriodo(Periodo periodo) throws java.rmi.RemoteException;
    
    public List<RubrosNivel> obtenerRubroNivel(NivelAcademico nivel) throws java.rmi.RemoteException;
    
    public List<RubroEstudiante> obtenerRubro(NivelAcademico nivel,RubrosNivel rubro) throws java.rmi.RemoteException;

}
