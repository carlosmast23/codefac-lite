/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import java.rmi.Remote;
import java.util.List;

/**
 *
 * @author Carlos
 */
public interface ProductoServiceIf extends Remote {

        
    public void grabar(Producto p) throws ServicioCodefacException, java.rmi.RemoteException;
    
    public void editar(Producto p) throws java.rmi.RemoteException;
    
    public void eliminar(Producto p) throws java.rmi.RemoteException;
    
    public List<Producto> buscar() throws java.rmi.RemoteException;
}
