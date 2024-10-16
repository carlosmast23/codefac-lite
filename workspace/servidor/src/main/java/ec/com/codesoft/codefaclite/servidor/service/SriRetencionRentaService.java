/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.SriRetencionRentaFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencionRenta;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SriRetencionRentaServiceIf;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Carlos
 */
public class SriRetencionRentaService extends ServiceAbstract<SriRetencionRenta,SriRetencionRentaFacade> implements SriRetencionRentaServiceIf{

    public SriRetencionRentaService() throws RemoteException {
        super(SriRetencionRentaFacade.class);
    }
    
    public List<SriRetencionRenta> obtenerTodosOrdenadoPorCodigo() throws RemoteException
    {
        return getFacade().obtenerTodosOrdenadoPorCodigoFacade();
    }
    
}
