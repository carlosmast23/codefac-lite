/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CategoriaProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import java.rmi.Remote;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public interface CategoriaProductoServiceIf extends Remote
{
    public void grabar(CategoriaProducto c) throws ServicioCodefacException,java.rmi.RemoteException;
    public void editar(CategoriaProducto c) throws java.rmi.RemoteException;
    public void eliminar(CategoriaProducto c) throws java.rmi.RemoteException;
}
