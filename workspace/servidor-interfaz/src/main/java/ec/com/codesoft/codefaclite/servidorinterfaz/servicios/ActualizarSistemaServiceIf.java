/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ActualizarSistema;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Carlos
 */
public interface ActualizarSistemaServiceIf extends ServiceAbstractIf<ActualizarSistema>
{ 
    public List<ActualizarSistema> obtenerCambiosPendientes() throws RemoteException;
    public Boolean cambiosIvaQuinceIngresados() throws RemoteException,ServicioCodefacException;
}
