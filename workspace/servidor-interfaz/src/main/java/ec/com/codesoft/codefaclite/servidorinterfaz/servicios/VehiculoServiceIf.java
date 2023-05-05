/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Vehiculo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
//import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SegmentoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author DellWin10
 */
public interface VehiculoServiceIf extends ServiceAbstractIf<Vehiculo> {
    
    public Vehiculo grabar(Vehiculo entity,Empresa empresa,Usuario usuarioCreacion) throws ServicioCodefacException, RemoteException ;
    public Vehiculo editar(Vehiculo entity,Empresa empresa,Usuario usuarioCreacion) throws ServicioCodefacException, RemoteException ;
    public void eliminar(Vehiculo entity) throws ServicioCodefacException, RemoteException ;    
    public List<Vehiculo> obtenerActivosPorEmpresa(Empresa empresa) throws ServicioCodefacException, RemoteException;
    public Vehiculo buscarPorNombre(Empresa empresa,String nombre) throws ServicioCodefacException,java.rmi.RemoteException;
    public void grabarSinTransaccion(Vehiculo entity,Empresa empresa,Usuario usuarioCreacion) throws ServicioCodefacException, RemoteException ;
    public List<Vehiculo> buscarPorPropietario(Empresa empresa,Persona propietario) throws ServicioCodefacException,java.rmi.RemoteException;
}
