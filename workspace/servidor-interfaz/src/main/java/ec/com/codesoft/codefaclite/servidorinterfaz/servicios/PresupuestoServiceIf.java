/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empleado;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ObjetoMantenimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Presupuesto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PresupuestoDetalleActividad;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.reportData.ActividadPresupuestoData;
import ec.com.codesoft.codefaclite.servidorinterfaz.reportData.ReportDataAbstract;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public interface PresupuestoServiceIf  extends ServiceAbstractIf<Presupuesto> {
    
   public List<OrdenTrabajoDetalle> listarOrdenesTrabajo(OrdenTrabajo ordenTrabajo) throws ServicioCodefacException,java.rmi.RemoteException; 
   public List<Presupuesto> consultarPresupuestos(Date fechaInicial, Date fechaFinal,Persona cliente,String codigoObjetoMantenimiento,Presupuesto.EstadoEnum estadoEnum) throws ServicioCodefacException,java.rmi.RemoteException;
   public List<Presupuesto> consultarPorOrdenTrabajo(OrdenTrabajo ordenTrabajo) throws ServicioCodefacException,RemoteException;
   public List<PresupuestoDetalleActividad> consultarActividadPresupuesto(Empleado empleado) throws ServicioCodefacException,RemoteException;
   public void actualizarActividadesPresupuestos(List<PresupuestoDetalleActividad> actividadList,Usuario usuarioSeleccionado,Usuario usuario) throws ServicioCodefacException,RemoteException;
   public List<PresupuestoDetalleActividad> consultarActividadesPendientesPresupuesto(Empleado empleado) throws ServicioCodefacException,RemoteException;
   public Presupuesto consultarUltimaPorObjectoMantenimiento(ObjetoMantenimiento objetoMantenimiento) throws ServicioCodefacException, RemoteException;
   public Presupuesto grabar(Presupuesto entity,Boolean enviarCorreo) throws RemoteException,ServicioCodefacException;
   public void editar(Presupuesto p,Boolean enviarCorreo) throws RemoteException, ServicioCodefacException;
   public ReportDataAbstract<ActividadPresupuestoData> consultarActividadesPresupuesto(Date fechaInicial, Date fechaFinal,Persona cliente,String codigoObjetoMantenimiento,Presupuesto.EstadoEnum estadoEnum) throws ServicioCodefacException, RemoteException;
   
}
