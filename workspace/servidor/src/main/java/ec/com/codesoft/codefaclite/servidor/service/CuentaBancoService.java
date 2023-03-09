/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.CuentaBancoFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Lote;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.banco.CuentaBanco;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CrudEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.CuentaBancoServiceIf;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import java.rmi.RemoteException;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class CuentaBancoService extends ServiceAbstract<CuentaBanco,CuentaBancoFacade> implements CuentaBancoServiceIf { 

    public CuentaBancoService() throws RemoteException {
        super(CuentaBancoFacade.class);
    }
    
    
    
    private void validarGrabar(CuentaBanco entity,CrudEnum crudEnum) throws ServicioCodefacException
    {           
        /*if(entity.getEmpresa()==null)
        {
            throw new ServicioCodefacException("No se puede grabar sin una Empresa");
        }*/
        
        /*if(UtilidadesTextos.verificarNullOVacio(entity.getCodigo()))
        {
            throw new ServicioCodefacException("Debe ingresar un código de lote para grabar");
        }*/
        
    }   
    
    
    private void setearDatosGrabar(CuentaBanco entity,Empresa empresa,CrudEnum crudEnum)
    {
        //entity.setEmpresa(empresa);
    }
    
    @Override
    public CuentaBanco grabar(CuentaBanco entity,Empresa empresa,Usuario usuarioCreacion) throws ServicioCodefacException, RemoteException {
        
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                entity.setEstadoEnum(GeneralEnumEstado.ACTIVO);
                
                setDatosAuditoria(entity,usuarioCreacion,CrudEnum.CREAR);
                setearDatosGrabar(entity, empresa,CrudEnum.CREAR);
                validarGrabar(entity, CrudEnum.CREAR);
                entityManager.persist(entity);
                
            }
        });
        return entity;
    }
    
    
    @Override
    public CuentaBanco editar(CuentaBanco entity,Empresa empresa,Usuario usuarioCreacion) throws ServicioCodefacException, RemoteException {
        
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
    
    public void editarSinTransaccion(CuentaBanco entity) throws ServicioCodefacException, RemoteException 
    {
        validarGrabar(entity, CrudEnum.EDITAR);
        entityManager.merge(entity);
    }
    
    @Override
    public void eliminar(CuentaBanco entity) throws ServicioCodefacException, RemoteException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                //TODO: Agregar validacion para solo eliminar los lotes si no tiene ningun saldo disponible
                entity.setEstadoEnum(GeneralEnumEstado.ELIMINADO);
                entityManager.merge(entity);
            }
        });
    }

    
    
    
}
