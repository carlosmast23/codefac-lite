/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service.cartera;

import ec.com.codesoft.codefaclite.servidor.facade.cartera.CarteraCruceFacade;
import ec.com.codesoft.codefaclite.servidor.service.ServiceAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.CarteraCruce;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.cartera.CarteraCruceServiceIf;
import java.rmi.RemoteException;

/**
 *
 * @author Carlos
 */
public class CarteraCruceService extends ServiceAbstract<CarteraCruce,CarteraCruceFacade> implements CarteraCruceServiceIf
{

    public CarteraCruceService() throws RemoteException {
        super(CarteraCruceFacade.class);
    }
    
}