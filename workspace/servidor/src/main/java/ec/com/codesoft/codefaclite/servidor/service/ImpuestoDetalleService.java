/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Impuesto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ImpuestoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ConstrainViolationExceptionSQL;
import ec.com.codesoft.codefaclite.servidor.facade.ImpuestoDetalleFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ImpuestoDetalleServiceIf;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.persistence.exceptions.DatabaseException;

/**
 *
 * @author PC
 */
public class ImpuestoDetalleService extends ServiceAbstract<ImpuestoDetalle,ImpuestoDetalleFacade> implements ImpuestoDetalleServiceIf
{
    private ImpuestoDetalleFacade impuestoDetalleFacade;

    public ImpuestoDetalleService() throws java.rmi.RemoteException
    {
        super(ImpuestoDetalleFacade.class);
        impuestoDetalleFacade= new ImpuestoDetalleFacade();
    }
    
    public void grabar(ImpuestoDetalle i) throws ServicioCodefacException,java.rmi.RemoteException
    {
        try {
            impuestoDetalleFacade.create(i);
        } catch (ConstrainViolationExceptionSQL ex) {
            Logger.getLogger(ImpuestoDetalleService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DatabaseException ex) {
            Logger.getLogger(ImpuestoDetalleService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    public void eliminar(ImpuestoDetalle i) throws java.rmi.RemoteException
    {
        impuestoDetalleFacade.remove(i);
    }
    
    public List<ImpuestoDetalle> buscarImpuestoDetallePorMap(Map<String,Object> map) throws java.rmi.RemoteException
    {
        return impuestoDetalleFacade.findByMap(map);        
    }
    
    public List<ImpuestoDetalle> obtenerIvaVigente() throws java.rmi.RemoteException
    {
        return impuestoDetalleFacade.getImpuestoVigenteByName(Impuesto.IVA);
    }

}
