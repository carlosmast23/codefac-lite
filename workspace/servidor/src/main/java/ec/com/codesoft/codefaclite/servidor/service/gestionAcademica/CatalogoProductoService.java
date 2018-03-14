/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service.gestionAcademica;

import ec.com.codesoft.codefaclite.servidor.facade.gestionAcademica.CatalogoProductoFacade;
import ec.com.codesoft.codefaclite.servidor.service.ServiceAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.CatalogoProductoServiceIf;
import java.rmi.RemoteException;

/**
 *
 * @author Carlos
 */
public class CatalogoProductoService extends ServiceAbstract<CatalogoProducto,CatalogoProductoFacade> implements CatalogoProductoServiceIf{

    public CatalogoProductoService() throws RemoteException {
        super(CatalogoProductoFacade.class);
    }
    
}
