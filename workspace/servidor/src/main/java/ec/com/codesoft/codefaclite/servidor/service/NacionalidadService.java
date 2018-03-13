/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.NacionalidadFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Nacionalidad;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.NacionalidadServiceIf;
import java.rmi.RemoteException;

/**
 *
 * @author CodesoftDesarrollo
 */
public class NacionalidadService extends ServiceAbstract<Nacionalidad, NacionalidadFacade> implements NacionalidadServiceIf {

    NacionalidadFacade nacionalidadFacade;

    public NacionalidadService() throws RemoteException {
        super(NacionalidadFacade.class);
        nacionalidadFacade = new NacionalidadFacade();
    }

}
