/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Estudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
 ;
import java.util.List;

/**
 *
 * @author CodesoftDesarrollo
 */
public interface EstudianteServiceIf extends ServiceAbstractIf<Estudiante>{
    public List<Estudiante> estudianteSinMatriculaPorPeriodo(Periodo periodo);   
    public List<Estudiante> estudianteNuevosSinMatricula();    
    public void eliminarEstudiante(Estudiante e) throws ServicioCodefacException;
    public Estudiante buscarPorCedulayEstado(String cedula,GeneralEnumEstado estado) throws ServicioCodefacException;
    public List<Estudiante> obtenerEstudiantesActivos();    
}
