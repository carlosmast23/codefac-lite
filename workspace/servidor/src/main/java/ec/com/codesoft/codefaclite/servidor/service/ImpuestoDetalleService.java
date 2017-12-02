/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.entity.Impuesto;
import ec.com.codesoft.codefaclite.servidor.entity.ImpuestoDetalle;
import ec.com.codesoft.codefaclite.servidor.facade.ImpuestoDetalleFacade;
import java.util.List;
import java.util.Map;

/**
 *
 * @author PC
 */
public class ImpuestoDetalleService 
{
    private ImpuestoDetalleFacade impuestoDetalleFacade;

    public ImpuestoDetalleService() 
    {
        impuestoDetalleFacade= new ImpuestoDetalleFacade();
    }
    
    public void grabar(ImpuestoDetalle i)
    {
        impuestoDetalleFacade.create(i);
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
