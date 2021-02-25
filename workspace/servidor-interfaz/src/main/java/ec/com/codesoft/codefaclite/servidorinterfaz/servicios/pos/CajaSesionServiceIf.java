/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios.pos;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.Caja;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.CajaSession;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ServiceAbstractIf;
import java.rmi.RemoteException;

/**
 *
 * @author Robert
 */
public interface CajaSesionServiceIf extends ServiceAbstractIf<CajaSession>
{
    public boolean buscarSiCajaTieneSessionActiva(Caja caja) throws RemoteException;
    public CajaSession obtenerUltimaCajaSession(Caja caja) throws RemoteException;
    public CajaSession obtenerCajaSessionPorPuntoEmisionYUsuario(Integer puntoEmision, Usuario usuario) throws RemoteException;
    
}
