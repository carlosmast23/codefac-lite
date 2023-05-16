/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.MantenimientoTareaDetalleFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empleado;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Lote;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Mantenimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.MantenimientoTareaDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CrudEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.MantenimientoTareaDetalleServiceIf;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import es.mityc.firmaJava.libreria.utilidades.Utilidades;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class MantenimientoTareaDetalleService extends ServiceAbstract<MantenimientoTareaDetalle, MantenimientoTareaDetalleFacade> implements MantenimientoTareaDetalleServiceIf{

    public MantenimientoTareaDetalleService() throws RemoteException {
        super(MantenimientoTareaDetalleFacade.class);
    }
    
    public void finalizarTarea(MantenimientoTareaDetalle tareaDetalle,Boolean terminarMantenimiento) throws ServicioCodefacException, RemoteException 
    {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                tareaDetalle.setEstadoEnum(MantenimientoTareaDetalle.EstadoEnum.FINALIZADO);
                tareaDetalle.setFechaFin(UtilidadesFecha.getFechaHoyTimeStamp());
                
                if(terminarMantenimiento)
                {
                    tareaDetalle.getMantenimiento().setEstadoEnum(Mantenimiento.MantenimientoEnum.TERMINADO);
                    tareaDetalle.setFechaFin(UtilidadesFecha.getFechaHoyTimeStamp());
                    entityManager.merge(tareaDetalle.getMantenimiento());
                }
                
                entityManager.merge(tareaDetalle);
            }
        });
    }
    
    public List<MantenimientoTareaDetalle> obtenerTareasPendientesPorEmpleado(Empleado empleado) throws ServicioCodefacException, RemoteException 
    {
        Map<String,Object> mapParametros=new HashMap<String, Object>();
        mapParametros.put("operador", empleado);
        mapParametros.put("estado",MantenimientoTareaDetalle.EstadoEnum.INICIADO.getLetra());
        return getFacade().findByMap(mapParametros);
    }
    
    
    //TODO: Terminar de programar para que salgan todos los estados menos el eliminado
    public List<MantenimientoTareaDetalle> obtenerTodosActivos(Empresa empresa)  throws ServicioCodefacException, RemoteException 
    {
        //MantenimientoTareaDetalle mesa;
        
        //mesa.getEstadoEnum();
        Map<String,Object> mapParametros=new HashMap<String, Object>();
        mapParametros.put("estado", MantenimientoTareaDetalle.EstadoEnum.GENERADO);
        return getFacade().findByMap(mapParametros);
    }
    
    private void validarGrabar(MantenimientoTareaDetalle entidad,CrudEnum crudEnum) throws ServicioCodefacException
    {           
        /*if(UtilidadesTextos.verificarNullOVacio(entidad.get))
        {
            throw new ServicioCodefacException("No se puede grabar una mesa sin un nombre");
        }*/
        
    }
    
    @Override
    public MantenimientoTareaDetalle grabar(MantenimientoTareaDetalle tareaDetalle,Empresa empresa,Usuario usuarioCreacion) throws ServicioCodefacException, RemoteException {
        
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                tareaDetalle.setEstadoEnum(MantenimientoTareaDetalle.EstadoEnum.INICIADO);
                tareaDetalle.setFechaInicio(UtilidadesFecha.getFechaHoyTimeStamp());
                
                setDatosAuditoria(tareaDetalle,usuarioCreacion,CrudEnum.CREAR);
                //setearDatosGrabar(mesa, empresa,CrudEnum.CREAR);
                validarGrabar(tareaDetalle, CrudEnum.CREAR);
                entityManager.persist(tareaDetalle);
                
            }
        });
        return tareaDetalle;
    }
    
    @Override
    public MantenimientoTareaDetalle editar(MantenimientoTareaDetalle entity,Empresa empresa,Usuario usuarioCreacion) throws ServicioCodefacException, RemoteException {
        
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {                                
                setDatosAuditoria(entity,usuarioCreacion,CrudEnum.EDITAR);
                //setearDatosGrabar(entity, empresa,CrudEnum.EDITAR);
                editarSinTransaccion(entity);
            }
        });
        return entity;
    }
    
    @Override
    public void eliminar(MantenimientoTareaDetalle entity) throws ServicioCodefacException, RemoteException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException 
            {
                //TODO: Agregar validacion para solo eliminar los lotes si no tiene ningun saldo disponible
                entityManager.merge(entity);
            }
        });
    }

    public void editarSinTransaccion(MantenimientoTareaDetalle entity) throws ServicioCodefacException, RemoteException 
    {
        validarGrabar(entity, CrudEnum.EDITAR);
        entityManager.merge(entity);
    }
}
