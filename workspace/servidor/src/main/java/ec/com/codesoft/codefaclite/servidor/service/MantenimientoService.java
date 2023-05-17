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
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CrudEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.result.MantenimientoResult;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.MantenimientoServiceIf;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                return getFacade().findByMap(mapParametros);
            }
        });
        
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
    
    @Override
    public Mantenimiento grabar(Mantenimiento objeto,Empresa empresa,Usuario usuarioCreacion) throws ServicioCodefacException, RemoteException {
        
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {                
                objeto.setEstadoEnum(Mantenimiento.MantenimientoEnum.INGRESADO);
                setDatosAuditoria(objeto,usuarioCreacion,CrudEnum.CREAR);
                //setearDatosGrabar(mesa, empresa,CrudEnum.CREAR);
                validarGrabar(objeto, CrudEnum.CREAR);
                entityManager.persist(objeto.getVehiculo());
                entityManager.persist(objeto);
                
            }
        });
        return objeto;
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
    
    public List<MantenimientoResult> consultarMantenimiento(Date fechaInicio, Date fechaFin) throws ServicioCodefacException, RemoteException
    {
        return (List<MantenimientoResult>) ejecutarConsulta(new MetodoInterfaceConsulta() {
            @Override
            public Object consulta() throws ServicioCodefacException, RemoteException 
            {                
                List<Mantenimiento> resultado=getFacade().findByMap(new HashMap<String, Object>());
                return convertirDatos(resultado);
            }
        });
    }
    
    private List<MantenimientoResult> convertirDatos(List<Mantenimiento> datos)
    {
        List<MantenimientoResult> resultadoList=new ArrayList<MantenimientoResult>();
        for (Mantenimiento dato : datos) {
            Mantenimiento.MantenimientoEnum estadoEnum=dato.getEstadoEnum();
            
            MantenimientoResult mantenimientoResult=new MantenimientoResult();
            mantenimientoResult.modelo=dato.getVehiculo().getModelo();
            mantenimientoResult.color=dato.getVehiculo().getColor();
            mantenimientoResult.vin=dato.getVehiculo().getVin();
            mantenimientoResult.estado=(estadoEnum!=null)?estadoEnum.getNombre():"";
            mantenimientoResult.fechaIngreso=dato.getFechaIngreso()+"";
            
            //Falta implemtar el resto de los procesos
            
            
            resultadoList.add(mantenimientoResult);
        }
        
        return resultadoList;
    }
    
    @Override
    public void eliminar(Mantenimiento entity) throws ServicioCodefacException, RemoteException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException 
            {
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
