/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.VehiculoFacade;
//import ec.com.codesoft.codefaclite.servidor.facade.SegmentoProductoFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Lote;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Vehiculo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
//import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SegmentoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CrudEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.VehiculoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SegmentoProductoServiceIf;
import ec.com.codesoft.codefaclite.utilidades.list.UtilidadesMap;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import es.mityc.firmaJava.libreria.utilidades.Utilidades;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DellWin10
 */
public class VehiculoService extends ServiceAbstract<Vehiculo, VehiculoFacade> implements  VehiculoServiceIf{

    public VehiculoService() throws RemoteException {
        super(VehiculoFacade.class);
    }
    
    private void setearDatosGrabar(Vehiculo entity,Empresa empresa,CrudEnum crudEnum)
    {
        entity.setEmpresa(empresa);
    }
    
    private void validarGrabar(Vehiculo segmento,CrudEnum crudEnum) throws ServicioCodefacException
    {           
        if(segmento.getEmpresa()==null)
        {
            throw new ServicioCodefacException("No se puede grabar sin una Empresa");
        }

        
    }
    
    private void validarDatoRepetidoObjectoMantenimiento(Vehiculo objetoMantenimiento) throws ServicioCodefacException, RemoteException
    {
        //Verificar que no pueda ingresar datos duplicados
        Map<String,Object> mapParametro=new HashMap<String, Object>();
        mapParametro.put("empresa",objetoMantenimiento.getEmpresa());
        mapParametro.put("estado",GeneralEnumEstado.ACTIVO.getEstado());
        mapParametro.put("codigo",objetoMantenimiento.getCodigo());
        validarDatoRepetido(mapParametro);
    }
    
    private void validar(Vehiculo objetoMantenimiento,CrudEnum crudEnum) throws RemoteException, ServicioCodefacException
    {
       validarDatoRepetidoObjectoMantenimiento(objetoMantenimiento);
       
        //Verificar que al momento de editar no pueda editar los códigos de los datos
        validarEdicionCampo(objetoMantenimiento.getEmpresa(), crudEnum, new ValidarEdicionCampoIf<Vehiculo>() {
            @Override
            public Object getId() {
                return objetoMantenimiento.getId();
            }

            @Override
            public Boolean compararCampos(Vehiculo dato) {
                return objetoMantenimiento.getCodigo().equals(dato.getCodigo());
            }
        });
    }
    
    
    @Override
    public Vehiculo grabar(Vehiculo entity,Empresa empresa,Usuario usuarioCreacion) throws ServicioCodefacException, RemoteException {
        
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                validar(entity, CrudEnum.CREAR);
                grabarSinTransaccion(entity, empresa, usuarioCreacion);
                
            }
        });
        return entity;
    }
    
    public void grabarSinTransaccion(Vehiculo entity,Empresa empresa,Usuario usuarioCreacion) throws ServicioCodefacException, RemoteException 
    {
        entity.setEstadoEnum(GeneralEnumEstado.ACTIVO);

        setDatosAuditoria(entity, usuarioCreacion, CrudEnum.CREAR);
        setearDatosGrabar(entity, empresa, CrudEnum.CREAR);
        validarGrabar(entity, CrudEnum.CREAR);
        entityManager.persist(entity);
    }
    
    @Override
    public Vehiculo editar(Vehiculo entity,Empresa empresa,Usuario usuarioCreacion) throws ServicioCodefacException, RemoteException {
        
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
    
    public void editarSinTransaccion(Vehiculo entity) throws ServicioCodefacException, RemoteException 
    {
        validarGrabar(entity, CrudEnum.EDITAR);
        entityManager.merge(entity);
    }
    
    @Override
    public void eliminar(Vehiculo entity) throws ServicioCodefacException, RemoteException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                //TODO: Agregar validacion para solo eliminar los lotes si no tiene ningun saldo disponible
                entity.setEstadoEnum(GeneralEnumEstado.ELIMINADO);
                entityManager.merge(entity);
            }
        });
    }
    
    public List<Vehiculo> obtenerActivosPorEmpresa(Empresa empresa) throws ServicioCodefacException, RemoteException
    {
        return (List<Vehiculo>) ejecutarConsulta(new MetodoInterfaceConsulta() {
            @Override
            public Object consulta() throws ServicioCodefacException, RemoteException {
                Map<String,Object> mapParameros=new HashMap<String, Object>();
                mapParameros.put("empresa", empresa);
                mapParameros.put("estado", GeneralEnumEstado.ACTIVO.getEstado());
                return getFacade().findByMap(mapParameros);
            }
        } );
    }
    
    public Vehiculo buscarPorNombre(Empresa empresa,String nombre) throws ServicioCodefacException,java.rmi.RemoteException
    {
        Map<String,Object> mapParametros=new HashMap<String,Object>();
        mapParametros.put("estado",GeneralEnumEstado.ACTIVO.getEstado());
        mapParametros.put("empresa", empresa);
        mapParametros.put("nombre",nombre);
        List<Vehiculo> resultados=getFacade().findByMap(mapParametros);
        if(resultados.size()>0)
        {
            return resultados.get(0);
        }
        return null;
    }
    
    public List<Vehiculo> buscarPorPropietario(Empresa empresa,Persona propietario) throws ServicioCodefacException,java.rmi.RemoteException
    {
        Map<String,Object> mapParametros=new HashMap<String,Object>();
        mapParametros.put("estado",GeneralEnumEstado.ACTIVO.getEstado());
        mapParametros.put("empresa", empresa);
        mapParametros.put("propietario",propietario);
        List<Vehiculo> resultados=getFacade().findByMap(mapParametros);
        
        return resultados;
    }
}
