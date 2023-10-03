/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ObjetoMantenimiento;
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
public interface ObjetoMantenimientoServiceIf extends ServiceAbstractIf<ObjetoMantenimiento> {
    
    public ObjetoMantenimiento grabar(ObjetoMantenimiento entity,Empresa empresa,Usuario usuarioCreacion) throws ServicioCodefacException, RemoteException ;
    public ObjetoMantenimiento editar(ObjetoMantenimiento entity,Empresa empresa,Usuario usuarioCreacion) throws ServicioCodefacException, RemoteException ;
    public void eliminar(ObjetoMantenimiento entity) throws ServicioCodefacException, RemoteException ;    
    public List<ObjetoMantenimiento> obtenerActivosPorEmpresa(Empresa empresa) throws ServicioCodefacException, RemoteException;
    public ObjetoMantenimiento buscarPorNombre(Empresa empresa,String nombre) throws ServicioCodefacException,java.rmi.RemoteException;
    public void grabarSinTransaccion(ObjetoMantenimiento entity,Empresa empresa,Usuario usuarioCreacion) throws ServicioCodefacException, RemoteException ;
    public List<ObjetoMantenimiento> buscarPorPropietario(Empresa empresa,Persona propietario) throws ServicioCodefacException,java.rmi.RemoteException;
    public ObjetoMantenimiento buscarPorVIN(Empresa empresa,String vin) throws ServicioCodefacException,java.rmi.RemoteException;
}
