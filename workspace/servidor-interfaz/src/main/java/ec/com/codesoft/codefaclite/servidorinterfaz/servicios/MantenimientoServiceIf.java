/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empleado;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Mantenimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.MantenimientoTareaDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.MarcaProducto;
//import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SegmentoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.result.MantenimientoResult;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

/**
 *
 * @author DellWin10
 */
public interface MantenimientoServiceIf extends ServiceAbstractIf<Mantenimiento>{    
    public Mantenimiento grabar(Mantenimiento mesa,Empresa empresa,Usuario usuarioCreacion) throws ServicioCodefacException, RemoteException;
    public Mantenimiento editar(Mantenimiento entity,Empresa empresa,Usuario usuarioCreacion) throws ServicioCodefacException, RemoteException;    
    public List<Mantenimiento> obtenerTodosActivos(Empresa empresa)  throws ServicioCodefacException, RemoteException;
    public int grabarPorLote(List<Mantenimiento> mantenimientoList,Empresa empresa,Usuario usuarioCreacion)  throws ServicioCodefacException, RemoteException;
    public List<Mantenimiento> obtenerPendientes(Empresa empresa) throws ServicioCodefacException, RemoteException ;
    public List<MantenimientoResult> consultarMantenimiento(Date fechaInicio, Date fechaFin,Mantenimiento.MantenimientoEnum estadoEnum ,MarcaProducto marca,Mantenimiento.UbicacionEnum ubicacionEnum,Boolean eliminados) throws ServicioCodefacException, RemoteException;
    public List<Mantenimiento> obtenerPendientesClasificarUbicacion(Empresa empresa) throws ServicioCodefacException, RemoteException;
    public void editarLote(List<Mantenimiento> mantenimientoList,Usuario usuarioEditar) throws ServicioCodefacException, RemoteException;
    public List<Mantenimiento> obtenerPendientesPorVin(Empresa empresa,String vin) throws ServicioCodefacException, RemoteException;
    public void iniciarTarea(MantenimientoTareaDetalle tarea,Empleado empleado) throws ServicioCodefacException, RemoteException;
    public void finalizarTarea(MantenimientoTareaDetalle tarea, Empleado empleado) throws ServicioCodefacException, RemoteException;
    
}
