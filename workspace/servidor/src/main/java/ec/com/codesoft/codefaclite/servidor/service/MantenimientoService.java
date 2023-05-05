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
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.MantenimientoServiceIf;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import java.rmi.RemoteException;
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
    
    //TODO: Terminar de programar para que salgan todos los estados menos el eliminado
    public List<Mantenimiento> obtenerTodosActivos(Empresa empresa)  throws ServicioCodefacException, RemoteException 
    {
        //Mantenimiento mesa;
        
        //mesa.getEstadoEnum();
        Map<String,Object> mapParametros=new HashMap<String, Object>();
        mapParametros.put("estado", Mantenimiento.MantenimientoEnum.INGRESADO);
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
    public Mantenimiento grabar(Mantenimiento mesa,Empresa empresa,Usuario usuarioCreacion) throws ServicioCodefacException, RemoteException {
        
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                mesa.setEstadoEnum(Mantenimiento.MantenimientoEnum.INGRESADO);
                setDatosAuditoria(mesa,usuarioCreacion,CrudEnum.CREAR);
                //setearDatosGrabar(mesa, empresa,CrudEnum.CREAR);
                validarGrabar(mesa, CrudEnum.CREAR);
                entityManager.persist(mesa);
                
            }
        });
        return mesa;
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
