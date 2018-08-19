/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.mb;

import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.CatalogoProductoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ProductoServiceIf;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Carlos
 */
@ManagedBean
@ViewScoped
public class ConsultarProductosMb implements Serializable{
    
    private List<Producto> productos;
    
    @PostConstruct
    public void init()  
    {
        try {
            ProductoServiceIf catalogosService=ServiceFactory.getFactory().getProductoServiceIf();
            productos=catalogosService.obtenerTodos();
        } catch (RemoteException ex) {
            Logger.getLogger(ConsultarProductosMb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    
    
    
}
