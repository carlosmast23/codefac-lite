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
 ;
import java.util.HashMap;
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

    public ImpuestoDetalleService()   
    {
        super(ImpuestoDetalleFacade.class);
        impuestoDetalleFacade= new ImpuestoDetalleFacade();
    }
    /*
    public ImpuestoDetalle grabar(ImpuestoDetalle i) throws ServicioCodefacException   
    {
        try {
            impuestoDetalleFacade.create(i);
        } catch (ConstrainViolationExceptionSQL ex) {
            Logger.getLogger(ImpuestoDetalleService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DatabaseException ex) {
            Logger.getLogger(ImpuestoDetalleService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return i;
    } */   
    
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
    
    public ImpuestoDetalle buscarPorTarifa(Integer tarifa) throws ServicioCodefacException   
    {
        List<ImpuestoDetalle> resultados=(List<ImpuestoDetalle>) ejecutarConsulta(new MetodoInterfaceConsulta() {
            @Override
            public Object consulta() throws ServicioCodefacException   {
                Map<String,Object> mapParametros=new HashMap<String, Object>();
                mapParametros.put("tarifa",tarifa);
                return getFacade().findByMap(mapParametros);
            }
        });
        
        if(resultados.size()>0)
        {
            return resultados.get(0);
        }
        return null;
    }

}
