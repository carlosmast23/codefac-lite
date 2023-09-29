/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empleado;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Mantenimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.MantenimientoInformeDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.MantenimientoTareaDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.MantenimientoTareaDetalle;
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
public interface MantenimientoTareaDetalleServiceIf extends ServiceAbstractIf<MantenimientoTareaDetalle>{    
    public MantenimientoTareaDetalle grabar(MantenimientoTareaDetalle mesa,Empresa empresa,Usuario usuarioCreacion) throws ServicioCodefacException, RemoteException;
    public MantenimientoTareaDetalle editar(MantenimientoTareaDetalle entity,Empresa empresa,Usuario usuarioCreacion) throws ServicioCodefacException, RemoteException;    
    public List<MantenimientoTareaDetalle> obtenerTodosActivos(Empresa empresa)  throws ServicioCodefacException, RemoteException;
    public List<MantenimientoTareaDetalle> obtenerTareasPendientesPorEmpleado(Empleado empleado) throws ServicioCodefacException, RemoteException;
    public void finalizarTarea(MantenimientoTareaDetalle tareaDetalle,Boolean terminarMantenimiento) throws ServicioCodefacException, RemoteException;
    public List<MantenimientoTareaDetalle> obtenerTareasPendientesPorUsuario(Usuario usuario) throws ServicioCodefacException, RemoteException ;
    public List<MantenimientoTareaDetalle> buscarPorMantenimiento(Mantenimiento mantenimiento) throws ServicioCodefacException, RemoteException ;
    public void grabarInformeDetalle(MantenimientoInformeDetalle detalle,MantenimientoTareaDetalle tareaDetalle) throws ServicioCodefacException, RemoteException;    
    public void eliminarInformeDetalle(MantenimientoInformeDetalle detalle,MantenimientoTareaDetalle tareaDetalle) throws ServicioCodefacException, RemoteException;
}
