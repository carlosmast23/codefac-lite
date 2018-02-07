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
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ImpuestoDetalleServiceIf;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.persistence.exceptions.DatabaseException;

/**
 *
 * @author PC
 */
public class ImpuestoDetalleService implements ImpuestoDetalleServiceIf
{
    private ImpuestoDetalleFacade impuestoDetalleFacade;

    public ImpuestoDetalleService() 
    {
        impuestoDetalleFacade= new ImpuestoDetalleFacade();
    }
    
    public void grabar(ImpuestoDetalle i)
    {
        try {
            impuestoDetalleFacade.create(i);
        } catch (ConstrainViolationExceptionSQL ex) {
            Logger.getLogger(ImpuestoDetalleService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DatabaseException ex) {
            Logger.getLogger(ImpuestoDetalleService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void editar(ImpuestoDetalle i)
    {
        impuestoDetalleFacade.edit(i);
    }
    
    public void eliminar(ImpuestoDetalle i)
    {
        impuestoDetalleFacade.remove(i);
    }
    
    public List<ImpuestoDetalle> buscarImpuestoDetallePorMap(Map<String,Object> map)
    {
        return impuestoDetalleFacade.findByMap(map);        
    }
    
    public List<ImpuestoDetalle> obtenerIvaVigente()
    {
        return impuestoDetalleFacade.getImpuestoVigenteByName(Impuesto.IVA);
    }
}
