/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.entity.Producto;
import ec.com.codesoft.codefaclite.servidor.facade.ProductoFacade;
import java.util.List;

/**
 *
 * @author PC
 */
public class ProductoService 
{
    private ProductoFacade productoFacade;
    
    public ProductoService()
    {
        this.productoFacade = new ProductoFacade();
    }
        
    public void grabar(Producto p)
    {
        productoFacade.create(p);
    }
    
    public void editar(Producto p)
    {
        productoFacade.edit(p);
    }
    
    public void eliminar(Producto p)
    {
        productoFacade.remove(p);
    }
    
    public List<Producto> buscar()
    {
        return productoFacade.findAll();
    }
    
            
    

}
