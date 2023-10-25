/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.MantenimientoFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empleado;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Lote;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Mantenimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.MantenimientoInformeDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.MantenimientoTareaDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.MarcaProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ObjetoMantenimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Taller;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.TareaMantenimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CrudEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.result.MantenimientoResult;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.MantenimientoServiceIf;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import es.mityc.firmaJava.libreria.utilidades.UtilidadFechas;
import java.rmi.RemoteException;
import java.sql.Timestamp;
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
    
    public int grabarPorLote(List<Mantenimiento> mantenimientoList,Empresa empresa,Usuario usuarioCreacion)  throws ServicioCodefacException, RemoteException 
    {
        Integer totalDatosGrabados=0;
        for (Mantenimiento mantenimiento : mantenimientoList) 
        {
            ObjetoMantenimiento objetoMantenimiento = mantenimiento.getVehiculo();
            objetoMantenimiento.setEstadoEnum(GeneralEnumEstado.ACTIVO);
            
            //Verificar que no existan datos repetidos Antes de grabar
            Map<String,Object> mapParametros=new HashMap<String,Object>();
            mapParametros.put("vin", mantenimiento.getVehiculo().getVin());
            mapParametros.put("estado",GeneralEnumEstado.ACTIVO.getLetra());
            
            ObjetoMantenimientoService objetoMantenimientoService=new ObjetoMantenimientoService();
            List<ObjetoMantenimiento> resultadoList= objetoMantenimientoService.obtenerPorMap(mapParametros);
            if(resultadoList.size()>0)
            {
                System.out.println("DATO repetido con VIN: "+mantenimiento.getVehiculo().getVin());
                continue;
            }
            
            
            ejecutarTransaccion(new MetodoInterfaceTransaccion() {
                @Override
                public void transaccion() throws ServicioCodefacException, RemoteException {
                    entityManager.persist(objetoMantenimiento);
                    
                }
            });
            totalDatosGrabados++;
            //grabar(mantenimiento, empresa, usuarioCreacion);
        }
        return totalDatosGrabados;
    }
    
    public List<Mantenimiento> obtenerPendientesPorVin(Empresa empresa,String vin) throws ServicioCodefacException, RemoteException 
    {
        //Mantenimiento m;
        //m.getVehiculo().getVin()
        return (List<Mantenimiento>) ejecutarConsulta(new MetodoInterfaceConsulta() {
            @Override
            public Object consulta() throws ServicioCodefacException, RemoteException {
                Map<String, Object> mapParametros = new HashMap<String, Object>();
                mapParametros.put("estado", Mantenimiento.MantenimientoEnum.INGRESADO.getLetra());
                mapParametros.put("vehiculo.vin", vin);
                //mapParametros.put("ubicacion", Mantenimiento.UbicacionEnum.TALLER.getLetra());
                return getFacade().findByMap(mapParametros);
            }
        });
    }
    
    public List<Mantenimiento> obtenerPendientes(Empresa empresa) throws ServicioCodefacException, RemoteException 
    {
        return (List<Mantenimiento>) ejecutarConsulta(new MetodoInterfaceConsulta() {
            @Override
            public Object consulta() throws ServicioCodefacException, RemoteException {
                Map<String,Object> mapParametros=new HashMap<String,Object>();
                mapParametros.put("estado", Mantenimiento.MantenimientoEnum.INGRESADO.getLetra());
                //mapParametros.put("ubicacion", Mantenimiento.UbicacionEnum.TALLER.getLetra());
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
                //objeto.setEstadoEnum(Mantenimiento.MantenimientoEnum.INGRESADO);
                objeto.getVehiculo().setEmpresa(empresa);
                //objeto.setEstado("A");
                setDatosAuditoria(objeto,usuarioCreacion,CrudEnum.CREAR);
                objeto.setFechaIngreso(UtilidadesFecha.getFechaHoyTimeStamp());
                //setearDatosGrabar(mesa, empresa,CrudEnum.CREAR);
                validarGrabar(objeto, CrudEnum.CREAR);
                
                List<MantenimientoTareaDetalle> detalleList=objeto.getTareaList();
                objeto.setTareaList(null);
                
                entityManager.persist(objeto.getVehiculo());
                entityManager.persist(objeto);
                entityManager.flush();
                
                //Grabar primero los detalles
                if(detalleList!=null)
                {
                    for (MantenimientoTareaDetalle mantenimientoTareaDetalle : detalleList) 
                    {
                        mantenimientoTareaDetalle.setMantenimiento(objeto);
                        entityManager.persist(objeto);

                    }                
                }
                
                objeto.setTareaList(detalleList);
                entityManager.merge(objeto);
                
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
    
    public void iniciarTarea(MantenimientoTareaDetalle tarea,Empleado empleado) throws ServicioCodefacException, RemoteException
    {
        tarea.setOperador(empleado);
        tarea.setFechaInicio(UtilidadesFecha.getFechaHoyTimeStamp());
        tarea.setEstadoEnum(MantenimientoTareaDetalle.EstadoEnum.INICIADO);
        
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                entityManager.merge(tarea);
            }
        });
        
    }
    
    public void finalizarTarea(MantenimientoTareaDetalle tarea, Empleado empleado) throws ServicioCodefacException, RemoteException 
    {
        //Solo puede finalizar el mismo empleado que inicio la tarea
        if(!tarea.getOperador().equals(empleado))
        {
            throw new ServicioCodefacException("Solo puede finalizar la tarea el usuario: "+tarea.getOperador());
        }
        
        tarea.setFechaFin(UtilidadesFecha.getFechaHoyTimeStamp());
        tarea.setEstadoEnum(MantenimientoTareaDetalle.EstadoEnum.FINALIZADO);
        
        //Verificar si todas las tareas estan FINALIZADAS entonces cambio tambien el mantenimiento
        List<MantenimientoTareaDetalle> tareaList=tarea.getMantenimiento().getTareaList();
        Boolean finalizarMantenimiento=true;
        for (MantenimientoTareaDetalle mantenimientoTareaDetalle : tareaList) {
            if(!tarea.equals(mantenimientoTareaDetalle))
            {
                if(!MantenimientoTareaDetalle.EstadoEnum.FINALIZADO.getNombre().equals(mantenimientoTareaDetalle.getEstadoNombre()))
                {
                    finalizarMantenimiento=false;
                    break;
                }
            }
        }
        
        if(finalizarMantenimiento)
        {
            tarea.getMantenimiento().setEstadoEnum(Mantenimiento.MantenimientoEnum.TERMINADO);
            tarea.getMantenimiento().setFechaSalida(UtilidadesFecha.getFechaHoyTimeStamp());
        }

        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                entityManager.merge(tarea.getMantenimiento());
                entityManager.merge(tarea);                
            }
        });
    }
    
    public List<MantenimientoResult> consultarMantenimiento(Date fechaInicio, Date fechaFin,Boolean fechaFinExacto,Taller taller,Mantenimiento.MantenimientoEnum estadoEnum ,MarcaProducto marca,Mantenimiento.UbicacionEnum ubicacionEnum,Boolean eliminados,TareaMantenimiento tareaMantenimiento) throws ServicioCodefacException, RemoteException
    {
        return (List<MantenimientoResult>) ejecutarConsulta(new MetodoInterfaceConsulta() {
            @Override
            public Object consulta() throws ServicioCodefacException, RemoteException 
            {  
                return convertirDatos(getFacade().consultarMantenimientoFacade(fechaInicio, fechaFin,fechaFinExacto,taller,estadoEnum,marca,ubicacionEnum));
            }
        });
    }
    
    public void terminarMantenimiento(Mantenimiento mantenimiento) throws ServicioCodefacException, RemoteException
    {               
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                mantenimiento.setEstadoEnum(Mantenimiento.MantenimientoEnum.TERMINADO);
                mantenimiento.setFechaSalida(UtilidadesFecha.getFechaHoyTimeStamp());
                
                //Cambiar de estado a todas las tareas
                for (MantenimientoTareaDetalle detalle : mantenimiento.getTareaList()) {
                    detalle.setEstadoEnum(MantenimientoTareaDetalle.EstadoEnum.FINALIZADO);
                    detalle.setFechaFin(UtilidadesFecha.getFechaHoyTimeStamp());
                    //if(MantenimientoTareaDetalle.EstadoEnum. detalle.getEstadoEnum().FINALIZADO)
                    entityManager.merge(detalle);
                }                
                entityManager.merge(mantenimiento);

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
                mantenimientoResult.marca=dato.getVehiculo().getMarcaNombre();
                mantenimientoResult.color=dato.getVehiculo().getColor();
                mantenimientoResult.vin=dato.getVehiculo().getVin();
                mantenimientoResult.estado=(estadoEnum!=null)?estadoEnum.getNombre():"";
                mantenimientoResult.fechaIngreso=dato.getFechaIngreso()+"";
                mantenimientoResult.fechaSalida=(dato.getFechaSalida()!=null)?dato.getFechaSalida()+"":"";
                mantenimientoResult.ubicacion=(dato.getUbicacionEnum()!=null)?dato.getUbicacionEnum().getNombre():"";
                mantenimientoResult.taller=dato.getTaller().getNombre();
                
                String duracionDiasStr="";
                String duracionhorasStr="";
                if(dato.getFechaSalida()!=null)
                {
                    duracionDiasStr=UtilidadesFecha.obtenerDistanciaDias(dato.getFechaIngreso(), dato.getFechaSalida())+"";                    
                    duracionhorasStr=UtilidadesFecha.obtenerDistanciaHorasDate(dato.getFechaIngreso(),dato.getFechaSalida())+"";
                }
                else
                {
                    duracionDiasStr=UtilidadesFecha.obtenerDistanciaDias(dato.getFechaIngreso(), UtilidadesFecha.getFechaHoraHoy())+"";
                    duracionhorasStr=UtilidadesFecha.obtenerDistanciaHorasDate(dato.getFechaIngreso(),UtilidadesFecha.getFechaHoraHoy())+"";
                }
                
                mantenimientoResult.setDuracionDias(duracionDiasStr);                
                mantenimientoResult.setDuracionHoras(duracionhorasStr);
                
                //Falta implemtar el resto de los procesos
                MantenimientoTareaDetalleService tareaService=new MantenimientoTareaDetalleService();
                List<MantenimientoTareaDetalle> detalleList= tareaService.buscarPorMantenimiento(dato);
                for (MantenimientoTareaDetalle detalleMantenimiento : detalleList) 
                {
                    if(detalleMantenimiento==null || detalleMantenimiento.getTallerTarea()==null || detalleMantenimiento.getTallerTarea().getTareaMantenimiento()==null)
                    {
                        Logger.getLogger(MantenimientoService.class.getName()).log(Level.WARNING,"Revisar MantenimientoTareaDetalle: Id: "+detalleMantenimiento.getId());
                        continue;
                    }
                    
                    String fechaInicio="";
                    if(detalleMantenimiento.getFechaInicio()!=null)
                    {
                        fechaInicio=ParametrosSistemaCodefac.FORMATO_ESTANDAR_FECHA_HORA.format(detalleMantenimiento.getFechaInicio());                        
                    }
                    
                    String fechaFin="";
                    if(detalleMantenimiento.getFechaFin()!=null)
                    {
                        fechaFin=ParametrosSistemaCodefac.FORMATO_ESTANDAR_FECHA_HORA.format(detalleMantenimiento.getFechaFin());                        
                    }
                    
                    //Agregar las horas que se ha demorado una tarea
                    Timestamp tiempoFinal=detalleMantenimiento.getFechaFin();
                    if(tiempoFinal==null)
                    {
                        tiempoFinal=UtilidadesFecha.getFechaHoyTimeStamp();
                    }
                    
                    Timestamp tiempoInicial=detalleMantenimiento.getFechaInicio();
                    if(tiempoInicial==null)
                    {
                        tiempoInicial=UtilidadesFecha.getFechaHoyTimeStamp();
                    }
                    
                    Integer horasProceso=UtilidadesFecha.calcularDiferenciaEnHoras(tiempoInicial, tiempoFinal);
                    
                    MantenimientoResult.DetalleTareaResult tareaDetalle=new MantenimientoResult.DetalleTareaResult(detalleMantenimiento.getTallerTarea().getTareaMantenimiento().getNombre(), "0",detalleMantenimiento.obtenerHorasTarea(),fechaInicio,fechaFin,horasProceso);
                    
                    //Agregar las detalles del Informa
                    List<MantenimientoInformeDetalle> informeList=detalleMantenimiento.getInformeList();
                    for (MantenimientoInformeDetalle informe : informeList) 
                    {
                        MantenimientoResult.InformeDetalleResult informeResult=new MantenimientoResult.InformeDetalleResult(informe.getCodigoTipoNoConformidad(),informe.getParteVehiculo());
                        tareaDetalle.agregarInformeDetalle(informeResult);
                    }
                    
                    mantenimientoResult.agregarTarea(tareaDetalle);
                    
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
