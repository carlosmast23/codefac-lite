/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.NotaCredito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import java.rmi.Remote;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public interface NotaCreditoServiceIf extends ServiceAbstractIf<NotaCredito>
{
    public void grabar(NotaCredito notaCredito) throws ServicioCodefacException,java.rmi.RemoteException;;
    public String getPreimpresoSiguiente() throws java.rmi.RemoteException;;
    public void editar(NotaCredito notaCredito) throws java.rmi.RemoteException;;
    public List<NotaCredito> obtenerTodos() throws java.rmi.RemoteException;;
    public List<NotaCredito> obtenerNotasReporte(Persona persona, Date fi, Date ff) throws java.rmi.RemoteException;;
}
