/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.DescuentoAcademico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Estudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.EstudianteInscrito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroEstudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroPlantilla;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroPlantillaMes;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubrosNivel;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.AplicarDescuentoAcademicoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.MesEnum;
 ;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author Carlos
 */
public interface RubroEstudianteServiceIf extends ServiceAbstractIf<RubroEstudiante> {
    
    /**
     * Obtiene los rubros de matricula por el estudiante inscrito y que este activo
     * @param estudianteInscrito
     * @return
     * @   
     */
    public List<RubroEstudiante> obtenerRubroMatriculaPorEstudianteInscrito(EstudianteInscrito estudianteInscrito);    

    public List<RubroEstudiante> obtenerRubrosActivosPorEstudiantesInscrito(EstudianteInscrito estudianteInscrito);   
    
    public List<RubroEstudiante> obtenerRubrosEstudiantesPorRubros(List<RubrosNivel> rubros);    
    
    public RubroPlantilla crearRubroEstudiantesDesdePlantila(RubroPlantilla rubroPlantilla, MesEnum mesEnum, String nombreRubroMes,Integer anio,DescuentoAcademico descuentoAcademico,AplicarDescuentoAcademicoEnum aplicarDescuentoEnum) throws   ServicioCodefacException;

    public void crearRubrosEstudiantes(List<EstudianteInscrito> estudiantes, RubrosNivel rubroNivel);    

    public void crearRubrosEstudiantes(List<RubroEstudiante> rubrosEstudiantes);    
    
    public void eliminarRubrosEstudiantes(List<RubroEstudiante> rubrosEstudiantes);    
            

    public List<RubroEstudiante> obtenerDeudasEstudiante(Estudiante estudiante,Periodo periodo)   ;

    public List<Object[]> obtenerRubroPeriodoGrupo(Periodo periodo,Date fechaInicio,Date fechaFin);    

    public List<RubroEstudiante> buscarRubrosMes(EstudianteInscrito est,Periodo periodo, CatalogoProducto catalogoProducto, List<RubroPlantillaMes> meses)   ;
    
    public Long contarRubrosEstudiantePorRubroNivel(RubrosNivel rubroNivel);    
    
    public void actualizarRubrosEstudiante(List<RubroEstudiante> rubroEstudiantes);    
    
    public void eliminarMesRubroPlantilla(RubroPlantillaMes rubroPlantillaMes) throws    ServicioCodefacException;
    
    public List<RubroEstudiante> consultarPorEstudianteInscritoSinFacturar(EstudianteInscrito estudianteInscrito);    
    
    public List<RubroEstudiante> buscarPorEstudianteInscritoYRubroNivel(EstudianteInscrito estudianteInscrito,RubrosNivel rubroNivel) throws ServicioCodefacException  ;
    
    public List<RubroEstudiante> buscarPorEstudianteInscritoYRubroNivelActivos(EstudianteInscrito estudianteInscrito, RubrosNivel rubroNivel) throws ServicioCodefacException  ;
    
}
