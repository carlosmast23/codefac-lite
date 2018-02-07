/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ProductoProveedor;
import ec.com.codesoft.codefaclite.servidor.facade.ProductoProveedorFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ProductoProveedorServiceIf;
import java.rmi.RemoteException;

/**
 *
 * @author Carlos
 */
public class ProductoProveedorService extends ServiceAbstract<ProductoProveedor,ProductoProveedorFacade> implements  ProductoProveedorServiceIf
{
    
    public ProductoProveedorService() throws RemoteException {
        super(ProductoProveedorFacade.class);
    }
    
}
