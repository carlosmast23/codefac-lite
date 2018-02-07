/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Bodega;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import java.rmi.Remote;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public interface BodegaServiceIf extends ServiceAbstractIf<Bodega>
{
    public void grabar(Bodega b) throws ServicioCodefacException,java.rmi.RemoteException;
    public void editar(Bodega b) throws java.rmi.RemoteException;
    public void eliminar(Bodega b) throws java.rmi.RemoteException;
}
