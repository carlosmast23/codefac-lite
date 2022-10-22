/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.GarantiaFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Garantia;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Lote;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CrudEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.GarantiaServiceIf;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import java.rmi.RemoteException;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class GarantiaService extends ServiceAbstract<Garantia,GarantiaFacade> implements  GarantiaServiceIf
{

    public GarantiaService() throws RemoteException 
    {
        super(GarantiaFacade.class);
    }
    
    private void validarGrabar(Garantia garantia,CrudEnum crudEnum) throws ServicioCodefacException
    {           
                
        if(UtilidadesTextos.verificarNullOVacio(garantia.getDescripcionIngreso()))
        {
            throw new ServicioCodefacException("Debe ingresar una descripción de ingreso");
        }
        
    }
    
    public void editarSinTransaccion(Garantia entity) throws ServicioCodefacException, RemoteException 
    {
        validarGrabar(entity, CrudEnum.EDITAR);
        entityManager.merge(entity);
    }
    
    @Override
    public Garantia grabar(Garantia entity, Empresa empresa, Usuario usuarioCreacion) throws ServicioCodefacException, RemoteException {

        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                entity.setEstadoEnum(GeneralEnumEstado.ACTIVO);

                setDatosAuditoria(entity, usuarioCreacion, CrudEnum.CREAR);
                //setearDatosGrabar(entity, empresa, CrudEnum.CREAR);
                validarGrabar(entity, CrudEnum.CREAR);
                entityManager.persist(entity);

            }
        });
        return entity;
    }
    
    @Override
    public Garantia editar(Garantia entity, Empresa empresa, Usuario usuarioCreacion) throws ServicioCodefacException, RemoteException
    {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                setDatosAuditoria(entity, usuarioCreacion, CrudEnum.EDITAR);
                //setearDatosGrabar(entity, empresa, CrudEnum.EDITAR);
                editarSinTransaccion(entity);
            }
        });
        return entity;
    }
    
}
