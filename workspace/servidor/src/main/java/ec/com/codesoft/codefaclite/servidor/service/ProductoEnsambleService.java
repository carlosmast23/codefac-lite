/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ProductoEnsamble;
import ec.com.codesoft.codefaclite.servidor.facade.ProductoEnsambleFacade;
import java.rmi.RemoteException;

/**
 *
 * @author Carlos
 */
public class ProductoEnsambleService extends ServiceAbstract<ProductoEnsamble,ProductoEnsambleFacade>{

    public ProductoEnsambleService() throws RemoteException {
        super(ProductoEnsambleFacade.class);
    }
    
}
