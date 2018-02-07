/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriIdentificacion;
import ec.com.codesoft.codefaclite.servidor.facade.SriFormaPagoFacade;
import ec.com.codesoft.codefaclite.servidor.facade.SriIdentificacionFacade;
import java.rmi.RemoteException;

/**
 *
 * @author Carlos
 */
public class SriIdentificacionService extends ServiceAbstract<SriIdentificacion,SriIdentificacionFacade>{
    
    public SriIdentificacionService() throws RemoteException {
        super(SriIdentificacionFacade.class);
    }
    
    
}
