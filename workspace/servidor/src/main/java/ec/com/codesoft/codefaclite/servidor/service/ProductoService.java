/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.entity.Producto;
import ec.com.codesoft.codefaclite.servidor.entity.enumerados.ProductoEnumEstado;
import ec.com.codesoft.codefaclite.servidor.excepciones.ConstrainViolationExceptionSQL;
import ec.com.codesoft.codefaclite.servidor.facade.ProductoFacade;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.persistence.exceptions.DatabaseException;

/**
 *
 * @author PC
 */
public class ProductoService extends ServiceAbstract<Producto,ProductoFacade>
{
    private ProductoFacade productoFacade;
    
    public ProductoService()
    {
        super(ProductoFacade.class);
        this.productoFacade = new ProductoFacade();
    }
        
    public void grabar(Producto p)
    {
        try {
            productoFacade.create(p);
        } catch (ConstrainViolationExceptionSQL ex) {
            Logger.getLogger(ProductoService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DatabaseException ex) {
            Logger.getLogger(ProductoService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void editar(Producto p)
    {
        productoFacade.edit(p);
    }
    
    public void eliminar(Producto p)
    {
        p.setEstado(ProductoEnumEstado.ELIMINADO.getEstado());
        productoFacade.edit(p);
    }
    
    public List<Producto> buscar()
    {
        return productoFacade.findAll();
    }
    
            
    

}
