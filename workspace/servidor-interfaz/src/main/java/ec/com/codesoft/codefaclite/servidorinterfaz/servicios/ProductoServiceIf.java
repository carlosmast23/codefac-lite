/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Carlos
 */
public interface ProductoServiceIf extends ServiceAbstractIf<Producto> {

       
    public Producto grabar(Producto p) throws ServicioCodefacException, java.rmi.RemoteException;
    
    public void editarProducto(Producto p) throws java.rmi.RemoteException,ServicioCodefacException;
    
    public void eliminar(Producto p) throws java.rmi.RemoteException;
    
    public List<Producto> buscar() throws java.rmi.RemoteException;
    
    public Producto buscarPorNombreyEstado(String nombre,GeneralEnumEstado estadoEnum) throws RemoteException;
    
    public Producto buscarProductoActivoPorCodigo(String codigo) throws RemoteException;
}
