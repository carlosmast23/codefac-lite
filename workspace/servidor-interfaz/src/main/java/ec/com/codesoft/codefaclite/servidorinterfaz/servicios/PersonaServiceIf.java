/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import java.rmi.Remote;
import java.util.List;

/**
 *
 * @author Carlos
 */
public interface PersonaServiceIf extends ServiceAbstractIf<Persona>{
    
    public Persona grabar(Persona p) throws ServicioCodefacException,java.rmi.RemoteException;
    
    public void editar(Persona p) throws java.rmi.RemoteException;
    
    public void eliminar(Persona p) throws java.rmi.RemoteException;
    
    public List<Persona> buscar() throws java.rmi.RemoteException;
}
