/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.CompraFacturaReembolsoFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CompraFacturaReembolso;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.CompraFacturaReembolsoServiceIf;
import java.rmi.RemoteException;

/**
 *
 * @author DellWin10
 */
public class CompraFacturaReembolsoService extends ServiceAbstract<CompraFacturaReembolso, CompraFacturaReembolsoFacade> implements CompraFacturaReembolsoServiceIf{

    public CompraFacturaReembolsoService() throws RemoteException {
        super(CompraFacturaReembolsoFacade.class);
    }
    
    
}
