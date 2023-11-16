/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.ProductoComponenteFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ProductoComponente;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ProductoComponenteServiceIf;
import java.rmi.RemoteException;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class ProductoComponenteService extends ServiceAbstract<ProductoComponente, ProductoComponenteFacade> implements ProductoComponenteServiceIf{

    public ProductoComponenteService() throws RemoteException 
    {
        super(ProductoComponenteFacade.class);
    }
    
    
    
}
