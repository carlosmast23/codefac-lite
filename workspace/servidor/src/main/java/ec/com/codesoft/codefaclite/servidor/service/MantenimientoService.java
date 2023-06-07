/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.MantenimientoFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Lote;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Mantenimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.MantenimientoTareaDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CrudEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.result.MantenimientoResult;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.MantenimientoServiceIf;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import es.mityc.firmaJava.libreria.utilidades.UtilidadFechas;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class MantenimientoService extends ServiceAbstract<Mantenimiento, MantenimientoFacade> implements MantenimientoServiceIf{

    public MantenimientoService() throws RemoteException {
        super(MantenimientoFacade.class);
    }
    
    public void grabarPorLote(List<Mantenimiento> mantenimientoList,Empresa empresa,Usuario usuarioCreacion)  throws ServicioCodefacException, RemoteException 
    {
        for (Mantenimiento mantenimiento : mantenimientoList) 
        {
            grabar(mantenimiento, empresa, usuarioCreacion);
        }
        
    }
    
    public List<Mantenimiento> obtenerPendientes(Empresa empresa) throws ServicioCodefacException, RemoteException 
    {
        return (List<Mantenimiento>) ejecutarConsulta(new MetodoInterfaceConsulta() {
            @Override
            public Object consulta() throws ServicioCodefacException, RemoteException {
                Map<String,Object> mapParametros=new HashMap<String,Object>();
                mapParametros.put("estado", Mantenimiento.MantenimientoEnum.INGRESADO.getLetra());
                mapParametros.put("ubicacion", Mantenimiento.UbicacionEnum.TALLER.getLetra());
                return getFacade().findByMap(mapParametros);
            }
        });
        
    }
    
    public List<Mantenimiento> obtenerPendientesClasificarUbicacion(Empresa empresa) throws ServicioCodefacException, RemoteException 
    {
        return getFacade().obtenerPendientesClasificarUbicacionFacade(empresa);
        
    }
    
    
    //TODO: Terminar de programar para que salgan todos los estados menos el eliminado
    public List<Mantenimiento> obtenerTodosActivos(Empresa empresa)  throws ServicioCodefacException, RemoteException 
    {
        //Mantenimiento mesa;
        
        //mesa.getEstadoEnum();
        Map<String,Object> mapParametros=new HashMap<String, Object>();
        mapParametros.put("estado", Mantenimiento.MantenimientoEnum.INGRESADO.getLetra());
        return getFacade().findByMap(mapParametros);
    }
    
    private void validarGrabar(Mantenimiento entidad,CrudEnum crudEnum) throws ServicioCodefacException
    {           
        /*if(UtilidadesTextos.verificarNullOVacio(entidad.get))
        {
            throw new ServicioCodefacException("No se puede grabar una mesa sin un nombre");
        }*/
        
    }
    
    public Boolean verificarMantenimientoActivo(Mantenimiento objeto) throws ServicioCodefacException, RemoteException 
    {
        //objeto.getUbicacion()
        String vin=objeto.getVehiculo().getVin();
        Map<String,Object> mapConsulta=new HashMap<String,Object>();
        mapConsulta.put("vehiculo.vin",vin);
        mapConsulta.put("estado", Mantenimiento.MantenimientoEnum.INGRESADO.getLetra());
        mapConsulta.put("ubicacion", Mantenimiento.UbicacionEnum.TALLER.getLetra());
        
        List resultadoList= getFacade().findByMap(mapConsulta);
        if(resultadoList.size()>0)
        {
            return true;
        }
        
        return false;
    }
    
    private void validarArchivoMantenimiento(Mantenimiento objeto) throws ServicioCodefacException, RemoteException 
    {
        //TODO: Verificar que el vehiculo previamente no este ingresado y estado pendiente
        if(verificarMantenimientoActivo(objeto))
        {
            throw new ServicioCodefacException("No se puede ingresar el vehiculo con placa : "+objeto.getVehiculo().getVin()+" porque ya fue ingresado previamente y sigue en mantenimiento");
        }
        
    }
    
    @Override
    public Mantenimiento grabar(Mantenimiento objeto,Empresa empresa,Usuario usuarioCreacion) throws ServicioCodefacException, RemoteException {
        
        validarArchivoMantenimiento(objeto);
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {                
                objeto.setEstadoEnum(Mantenimiento.MantenimientoEnum.INGRESADO);
                setDatosAuditoria(objeto,usuarioCreacion,CrudEnum.CREAR);
                objeto.setFechaIngreso(UtilidadesFecha.getFechaHoyTimeStamp());
                //setearDatosGrabar(mesa, empresa,CrudEnum.CREAR);
                validarGrabar(objeto, CrudEnum.CREAR);
                entityManager.persist(objeto.getVehiculo());
                entityManager.persist(objeto);
                
            }
        });
        return objeto;
    }
    
    public void editarLote(List<Mantenimiento> mantenimientoList,Usuario usuarioEditar) throws ServicioCodefacException, RemoteException
    {
        for (Mantenimiento mantenimiento : mantenimientoList) 
        {            
            editar(mantenimiento, mantenimiento.getVehiculo().getEmpresa(), usuarioEditar);
            System.out.println("Editando Vin: "+mantenimiento.getVehiculo().getVin());
        }
    }
    
    @Override
    public Mantenimiento editar(Mantenimiento entity,Empresa empresa,Usuario usuarioCreacion) throws ServicioCodefacException, RemoteException {
        
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
    
    public List<MantenimientoResult> consultarMantenimiento(Date fechaInicio, Date fechaFin,Boolean eliminados) throws ServicioCodefacException, RemoteException
    {
        return (List<MantenimientoResult>) ejecutarConsulta(new MetodoInterfaceConsulta() {
            @Override
            public Object consulta() throws ServicioCodefacException, RemoteException 
            {  
                return convertirDatos(getFacade().consultarMantenimientoFacade(fechaInicio, fechaFin));
            }
        });
    }
    
    private List<MantenimientoResult> convertirDatos(List<Mantenimiento> datos)
    {
        List<MantenimientoResult> resultadoList=new ArrayList<MantenimientoResult>();
        for (Mantenimiento dato : datos) {
            try {
                Mantenimiento.MantenimientoEnum estadoEnum=dato.getEstadoEnum();
                
                MantenimientoResult mantenimientoResult=new MantenimientoResult();
                mantenimientoResult.modelo=dato.getVehiculo().getModelo();
                mantenimientoResult.color=dato.getVehiculo().getColor();
                mantenimientoResult.vin=dato.getVehiculo().getVin();
                mantenimientoResult.estado=(estadoEnum!=null)?estadoEnum.getNombre():"";
                mantenimientoResult.fechaIngreso=dato.getFechaIngreso()+"";
                mantenimientoResult.fechaSalida=(dato.getFechaSalida()!=null)?dato.getFechaSalida()+"":"";
                mantenimientoResult.ubicacion=(dato.getUbicacionEnum()!=null)?dato.getUbicacionEnum().getNombre():"";
                
                String duracionDiasStr="";
                if(dato.getFechaSalida()!=null)
                {
                    duracionDiasStr=UtilidadesFecha.obtenerDistanciaDias(dato.getFechaIngreso(), dato.getFechaSalida())+"";
                }
                else
                {
                    duracionDiasStr=UtilidadesFecha.obtenerDistanciaDias(dato.getFechaIngreso(), UtilidadesFecha.getFechaHoraHoy())+"";
                }
                mantenimientoResult.setDuracionDias(duracionDiasStr);
                
                //Falta implemtar el resto de los procesos
                MantenimientoTareaDetalleService tareaService=new MantenimientoTareaDetalleService();
                List<MantenimientoTareaDetalle> detalleList= tareaService.buscarPorMantenimiento(dato);
                for (MantenimientoTareaDetalle mantenimientoTareaDetalle : detalleList) 
                {
                    MantenimientoResult.DetalleTareaResult detalleResult= new MantenimientoResult.DetalleTareaResult(mantenimientoTareaDetalle.getTarea().getNombre(),mantenimientoTareaDetalle.getObservacion());
                    
                    mantenimientoResult.agregarTarea(detalleResult);
                }
                resultadoList.add(mantenimientoResult);
            } catch (RemoteException ex) {
                Logger.getLogger(MantenimientoService.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ServicioCodefacException ex) {
                Logger.getLogger(MantenimientoService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return resultadoList;
    }
    
    @Override
    public void eliminar(Mantenimiento entity) throws ServicioCodefacException, RemoteException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException 
            {
                entity.setEstadoEnum(Mantenimiento.MantenimientoEnum.ELIMINADO);
                //TODO: Agregar validacion para solo eliminar los lotes si no tiene ningun saldo disponible
                entityManager.merge(entity);
            }
        });
    }

    public void editarSinTransaccion(Mantenimiento entity) throws ServicioCodefacException, RemoteException 
    {
        validarGrabar(entity, CrudEnum.EDITAR);
        entityManager.merge(entity);
    }
}
