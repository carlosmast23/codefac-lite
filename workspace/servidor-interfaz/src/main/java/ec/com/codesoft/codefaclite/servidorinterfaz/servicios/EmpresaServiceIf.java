/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ConstrainViolationExceptionSQL;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.persistence.exceptions.DatabaseException;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public interface EmpresaServiceIf extends ServiceAbstractIf<Empresa>
{
    public Empresa grabar(Empresa p) throws ServicioCodefacException,java.rmi.RemoteException;
    //public void editar(Empresa p) throws java.rmi.RemoteException;
    public void eliminar(Empresa p) throws java.rmi.RemoteException;   
    public List<Empresa> buscar() throws java.rmi.RemoteException;
    public Empresa buscarPorIdentificacion(String identificacion) throws RemoteException;
    public List<Empresa> obtenerTodosActivos() throws RemoteException;
}
