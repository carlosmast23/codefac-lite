/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.MantenimientoFacade;
import ec.com.codesoft.codefaclite.servidor.facade.MantenimientoTareaDetalleFacade;
import ec.com.codesoft.codefaclite.servidor.facade.TallerTareaFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empleado;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Mantenimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.MantenimientoTareaDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.TallerTarea;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.MantenimientoTareaDetalleServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.TallerTareaServiceIf;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class TallerTareaService extends ServiceAbstract<TallerTarea, TallerTareaFacade> implements TallerTareaServiceIf {

    public TallerTareaService() throws RemoteException {
        super(TallerTareaFacade.class);
    }
    
}
