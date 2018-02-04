/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.entity.ProductoEnsamble;
import ec.com.codesoft.codefaclite.servidor.facade.ProductoEnsambleFacade;

/**
 *
 * @author Carlos
 */
public class ProductoEnsambleService extends ServiceAbstract<ProductoEnsamble,ProductoEnsambleFacade>{

    public ProductoEnsambleService() {
        super(ProductoEnsambleFacade.class);
    }
    
}
