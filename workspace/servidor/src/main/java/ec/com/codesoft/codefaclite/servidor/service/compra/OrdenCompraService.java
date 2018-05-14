/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service.compra;

import ec.com.codesoft.codefaclite.servidor.facade.compra.OrdenCompraFacade;
import ec.com.codesoft.codefaclite.servidor.service.ServiceAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.compra.OrdenCompra;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.OrdenCompraServiceIf;
import java.rmi.RemoteException;

/**
 *
 * @author Carlos
 */
public class OrdenCompraService extends ServiceAbstract<OrdenCompra, OrdenCompraFacade> implements OrdenCompraServiceIf{

    public OrdenCompraService() throws RemoteException {
        super(OrdenCompraFacade.class);
    }
    
}
