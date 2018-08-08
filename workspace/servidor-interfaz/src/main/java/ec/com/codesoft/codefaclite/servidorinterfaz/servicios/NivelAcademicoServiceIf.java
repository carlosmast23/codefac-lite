/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.NivelAcademico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author CodesoftDesarrollo
 */
public interface NivelAcademicoServiceIf extends ServiceAbstractIf<NivelAcademico>{
    public List<NivelAcademico> obtenerTodosActivosPorPeriodo(Periodo periodo) throws RemoteException;
    public void eliminarNivelAcademico(NivelAcademico n) throws RemoteException,ServicioCodefacException;
}
