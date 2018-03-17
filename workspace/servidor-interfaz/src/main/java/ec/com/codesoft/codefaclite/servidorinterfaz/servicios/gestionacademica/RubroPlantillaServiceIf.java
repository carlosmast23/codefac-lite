/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios.gestionacademica;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroPlantilla;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ServiceAbstractIf;
import java.rmi.RemoteException;

/**
 *
 * @author Carlos
 */
public interface RubroPlantillaServiceIf extends ServiceAbstractIf<RubroPlantilla>{
    public void grabarConDetalles(RubroPlantilla rubroPlantilla) throws RemoteException;
    public void editarConDetalles(RubroPlantilla entity) throws java.rmi.RemoteException;
            
}
