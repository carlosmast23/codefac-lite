/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.mb;

import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author CARLOS_CODESOFT
 */
@ManagedBean
@ViewScoped
public class PaginaInformativaMb implements Serializable{
    
    private List<Producto> listaProductosMasVendidos;
    
    @PostConstruct
    public void init()
    {
        try {
            listaProductosMasVendidos=ServiceFactory.getFactory().getProductoServiceIf().obtenerTodos();
        } catch (RemoteException ex) {
            Logger.getLogger(PaginaInformativaMb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Producto> getListaProductosMasVendidos() {
        return listaProductosMasVendidos;
    }

    public void setListaProductosMasVendidos(List<Producto> listaProductosMasVendidos) {
        this.listaProductosMasVendidos = listaProductosMasVendidos;
    }
    
    
}
