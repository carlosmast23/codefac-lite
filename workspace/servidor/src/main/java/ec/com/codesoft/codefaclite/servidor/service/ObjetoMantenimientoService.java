/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.ObjetoMantenimientoFacade;
//import ec.com.codesoft.codefaclite.servidor.facade.SegmentoProductoFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Lote;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ObjetoMantenimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
//import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SegmentoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CrudEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ObjetoMantenimientoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SegmentoProductoServiceIf;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DellWin10
 */
public class ObjetoMantenimientoService extends ServiceAbstract<ObjetoMantenimiento, ObjetoMantenimientoFacade> implements  ObjetoMantenimientoServiceIf{

    public ObjetoMantenimientoService() throws RemoteException {
        super(ObjetoMantenimientoFacade.class);
    }
    
    private void setearDatosGrabar(ObjetoMantenimiento entity,Empresa empresa,CrudEnum crudEnum)
    {
        entity.setEmpresa(empresa);
    }
    
    private void validarGrabar(ObjetoMantenimiento segmento,CrudEnum crudEnum) throws ServicioCodefacException
    {           
        if(segmento.getEmpresa()==null)
        {
            throw new ServicioCodefacException("No se puede grabar sin una Empresa");
        }

        
    }
    
    private void validar(ObjetoMantenimiento objetoMantenimiento,CrudEnum crudEnum) throws RemoteException, ServicioCodefacException
    {
                validarEdicionCampo(objetoMantenimiento.getEmpresa(), crudEnum, new ValidarEdicionCampoIf<ObjetoMantenimiento>() {
            @Override
            public Object getId() {
                return objetoMantenimiento.getId();
            }

            @Override
            public Boolean compararCampos(ObjetoMantenimiento dato) {
                return objetoMantenimiento.getCodigo().equals(dato.getCodigo());
            }
        });
    }
    
    
    @Override
    public ObjetoMantenimiento grabar(ObjetoMantenimiento entity,Empresa empresa,Usuario usuarioCreacion) throws ServicioCodefacException, RemoteException {
        
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                validar(entity, CrudEnum.CREAR);
                grabarSinTransaccion(entity, empresa, usuarioCreacion);
                
            }
        });
        return entity;
    }
    
    public void grabarSinTransaccion(ObjetoMantenimiento entity,Empresa empresa,Usuario usuarioCreacion) throws ServicioCodefacException, RemoteException 
    {
        entity.setEstadoEnum(GeneralEnumEstado.ACTIVO);

        setDatosAuditoria(entity, usuarioCreacion, CrudEnum.CREAR);
        setearDatosGrabar(entity, empresa, CrudEnum.CREAR);
        validarGrabar(entity, CrudEnum.CREAR);
        entityManager.persist(entity);
    }
    
    @Override
    public ObjetoMantenimiento editar(ObjetoMantenimiento entity,Empresa empresa,Usuario usuarioCreacion) throws ServicioCodefacException, RemoteException {
        
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {                                
                setDatosAuditoria(entity,usuarioCreacion,CrudEnum.EDITAR);
                setearDatosGrabar(entity, empresa,CrudEnum.EDITAR);
                editarSinTransaccion(entity);
            }
        });
        return entity;
    }
    
    public void editarSinTransaccion(ObjetoMantenimiento entity) throws ServicioCodefacException, RemoteException 
    {
        validarGrabar(entity, CrudEnum.EDITAR);
        entityManager.merge(entity);
    }
    
    @Override
    public void eliminar(ObjetoMantenimiento entity) throws ServicioCodefacException, RemoteException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                //TODO: Agregar validacion para solo eliminar los lotes si no tiene ningun saldo disponible
                entity.setEstadoEnum(GeneralEnumEstado.ELIMINADO);
                entityManager.merge(entity);
            }
        });
    }
    
    public List<ObjetoMantenimiento> obtenerActivosPorEmpresa(Empresa empresa) throws ServicioCodefacException, RemoteException
    {
        return (List<ObjetoMantenimiento>) ejecutarConsulta(new MetodoInterfaceConsulta() {
            @Override
            public Object consulta() throws ServicioCodefacException, RemoteException {
                Map<String,Object> mapParameros=new HashMap<String, Object>();
                mapParameros.put("empresa", empresa);
                mapParameros.put("estado", GeneralEnumEstado.ACTIVO.getEstado());
                return getFacade().findByMap(mapParameros);
            }
        } );
    }
    
    public ObjetoMantenimiento buscarPorNombre(Empresa empresa,String nombre) throws ServicioCodefacException,java.rmi.RemoteException
    {
        Map<String,Object> mapParametros=new HashMap<String,Object>();
        mapParametros.put("estado",GeneralEnumEstado.ACTIVO.getEstado());
        mapParametros.put("empresa", empresa);
        mapParametros.put("nombre",nombre);
        List<ObjetoMantenimiento> resultados=getFacade().findByMap(mapParametros);
        if(resultados.size()>0)
        {
            return resultados.get(0);
        }
        return null;
    }
    
    public List<ObjetoMantenimiento> buscarPorPropietario(Empresa empresa,Persona propietario) throws ServicioCodefacException,java.rmi.RemoteException
    {
        Map<String,Object> mapParametros=new HashMap<String,Object>();
        mapParametros.put("estado",GeneralEnumEstado.ACTIVO.getEstado());
        mapParametros.put("empresa", empresa);
        mapParametros.put("propietario",propietario);
        List<ObjetoMantenimiento> resultados=getFacade().findByMap(mapParametros);
        
        return resultados;
    }
}
