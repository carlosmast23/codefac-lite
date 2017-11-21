/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.entity.Impuesto;
import ec.com.codesoft.codefaclite.servidor.entity.ImpuestoDetalle;
import ec.com.codesoft.codefaclite.servidor.facade.ImpuestoDetalleFacade;
import ec.com.codesoft.codefaclite.servidor.facade.ImpuestoFacade;
import java.util.List;

/**
 *
 * @author PC
 */
public class ImpuestoService 
{
    private ImpuestoFacade impuestoFacade;
    private ImpuestoDetalleFacade impuestoDetalleFacade;

    public ImpuestoService() 
    {
        impuestoFacade = new ImpuestoFacade();
        impuestoDetalleFacade=new ImpuestoDetalleFacade();
    }
    
    public void grabar(Impuesto i)
    {
        impuestoFacade.create(i);
    }
    
    public void editar(Impuesto i)
    {
        impuestoFacade.edit(i);
    }
    
    public void eliminar(Impuesto i)
    {
        impuestoFacade.remove(i);
    }
    
    public Impuesto obtenerImpuestoPorCodigo(String nombre)
    {
        return impuestoFacade.getByName(nombre);
    }
    
    public List<Impuesto> obtenerTodos()
    {
        return impuestoFacade.findAll();
    }
    
    public List<ImpuestoDetalle> obtenerDetalle()
    {
        return impuestoDetalleFacade.findAll();
    }
}
