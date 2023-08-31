/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Taller;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.TallerTarea;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Zona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author CARLOS_CODESOFT
 */
public interface TallerServiceIf extends ServiceAbstractIf<Taller>{
    public List<Taller> obtenerActivos() throws ServicioCodefacException, RemoteException;
    public List<TallerTarea> obtenerTareasPorTaller(Taller taller) throws ServicioCodefacException, RemoteException;
}
