/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.KardexDetalle;
import ec.com.codesoft.codefaclite.servidor.facade.KardexDetalleFacade;
import java.rmi.RemoteException;

/**
 *
 * @author Carlos
 */
public class KardexDetalleService extends ServiceAbstract<KardexDetalle, KardexDetalleFacade>{
    
    public KardexDetalleService() throws RemoteException {
        super(KardexDetalleFacade.class);
    }
    
}
