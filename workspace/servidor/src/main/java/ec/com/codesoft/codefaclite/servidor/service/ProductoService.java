/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ProductoEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoProductoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ConstrainViolationExceptionSQL;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidor.facade.ProductoFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ProductoServiceIf;
import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityTransaction;
import org.eclipse.persistence.exceptions.DatabaseException;

/**
 *
 * @author PC
 */
public class ProductoService extends ServiceAbstract<Producto,ProductoFacade> implements ProductoServiceIf
{
    private ProductoFacade productoFacade;
    
    public ProductoService() throws RemoteException
    {
        super(ProductoFacade.class);
        this.productoFacade = new ProductoFacade();
    }
    

        
    public Producto grabar(Producto p) throws ServicioCodefacException
    {
        EntityTransaction transactions= entityManager.getTransaction();
        transactions.begin();
        try {
            
            //Si el catalogo producto no esta creado primero crea la entidad
            CatalogoProducto catalogoProducto=p.getCatalogoProducto();                        
            if(catalogoProducto.getId()==null)
            {
                entityManager.persist(catalogoProducto);                        
            }
            
            //Si no son ensables remover datos para no tener incoherencias
            if(!TipoProductoEnum.EMSAMBLE.getLetra().equals(p.getCatalogoProducto().getTipoProducto()))
            {          
                if(p.getDetallesEnsamble()!=null)
                {
                    p.getDetallesEnsamble().clear();
                }
            }
            
            entityManager.persist(p);
            transactions.commit();
            
        //} catch (ConstrainViolationExceptionSQL ex) {
        //    Logger.getLogger(ProductoService.class.getName()).log(Level.SEVERE, null, ex);
        //    throw new ServicioCodefacException("La clave principal ya existe en el sistema");
        } catch (DatabaseException ex) {
            transactions.rollback();
            Logger.getLogger(ProductoService.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioCodefacException("Error con la base de datos");
        }
        return p;
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
