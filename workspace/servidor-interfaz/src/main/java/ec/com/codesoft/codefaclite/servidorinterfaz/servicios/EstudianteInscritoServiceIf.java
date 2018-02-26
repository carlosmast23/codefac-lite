/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Estudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.EstudianteInscrito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.NivelAcademico;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Carlos
 */
public interface EstudianteInscritoServiceIf extends ServiceAbstractIf<EstudianteInscrito>{
    public void matricularEstudiantesByMap(Map<NivelAcademico,List<Estudiante>> mapEstudiantes) throws RemoteException;
}
