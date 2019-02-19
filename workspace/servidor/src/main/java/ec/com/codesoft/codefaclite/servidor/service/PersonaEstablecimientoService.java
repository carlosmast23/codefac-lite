/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.PersonaEstablecimientoFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PersonaEstablecimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PersonaEstablecimientoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PersonaServiceIf;
import java.rmi.RemoteException;

/**
 *
 * @author Carlos
 */
public class PersonaEstablecimientoService extends ServiceAbstract<PersonaEstablecimiento,PersonaEstablecimientoFacade> implements PersonaEstablecimientoServiceIf {

    public PersonaEstablecimientoService() throws RemoteException {
        super(PersonaEstablecimientoFacade.class);
    }
    
}
