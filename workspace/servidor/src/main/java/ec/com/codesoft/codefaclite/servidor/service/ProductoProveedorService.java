/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.entity.ProductoProveedor;
import ec.com.codesoft.codefaclite.servidor.facade.ProductoProveedorFacade;

/**
 *
 * @author Carlos
 */
public class ProductoProveedorService extends ServiceAbstract<ProductoProveedor,ProductoProveedorFacade>{
    
    public ProductoProveedorService() {
        super(ProductoProveedorFacade.class);
    }
    
}
