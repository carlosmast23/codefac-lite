/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencionIva;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Carlos
 */
public interface SriRetencionIvaServiceIf extends ServiceAbstractIf<SriRetencionIva> {
    
    public List<SriRetencionIva> obtenerTodosOrdenadoPorCodigo() throws RemoteException;
}
