/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.PresentacionProductoFacade;
import ec.com.codesoft.codefaclite.servidor.facade.PresentacionProductoFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PresentacionProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PresentacionProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PresentacionProductoServiceIf;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class PresentacionProductoService extends ServiceAbstract<PresentacionProducto,PresentacionProductoFacade> implements PresentacionProductoServiceIf{

    public PresentacionProductoService() throws RemoteException {
        super(PresentacionProductoFacade.class);
    }
    
    private void validarGrabar(PresentacionProducto presentacionProducto) throws ServicioCodefacException, RemoteException
    {
        
        if(UtilidadesTextos.verificarNullOVacio(presentacionProducto.getNombre()))
        {
            throw new ServicioCodefacException("Error Validación: No se puede grabar sin ingresar un código");
        }
        
        if(presentacionProducto.getNombre().length()<=10)
        {
            throw new ServicioCodefacException("Error Validación: las presentaciones no puede tener un tamaño superior a 10 caracteres");
        }
        
        if(presentacionProducto.getEmpresa()==null)
        {
            throw new ServicioCodefacException("Error Validación: No se puede grabar sin registrar una empresa");
        }
        
    }

    @Override
    public PresentacionProducto grabar(PresentacionProducto presentacionProducto) throws ServicioCodefacException, RemoteException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                //validarGrabar(presentacionProducto);
                //presentacionProducto.setEstadoEnum(GeneralEnumEstado.ACTIVO);
                //entityManager.persist(presentacionProducto);
                grabarSinTransaccion(presentacionProducto);
            }
        });
        return presentacionProducto;
    }
    
    
    public PresentacionProducto grabarSinTransaccion(PresentacionProducto presentacionProducto) throws ServicioCodefacException, RemoteException {
        validarGrabar(presentacionProducto);
        presentacionProducto.setEstadoEnum(GeneralEnumEstado.ACTIVO);
        entityManager.persist(presentacionProducto);
        return presentacionProducto;
    }

    @Override
    public void editar(PresentacionProducto presentacionProducto) throws ServicioCodefacException, RemoteException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                validarGrabar(presentacionProducto);
                entityManager.merge(presentacionProducto);
            }
        });
    }

    @Override
    public void eliminar(PresentacionProducto presentacionProducto) throws ServicioCodefacException, RemoteException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                presentacionProducto.setEstadoEnum(GeneralEnumEstado.ELIMINADO);
                entityManager.merge(presentacionProducto);
            }
        });
    }
    
    public List<PresentacionProducto> obtenerActivosPorEmpresa(Empresa empresa) throws ServicioCodefacException, RemoteException
    {
        return (List<PresentacionProducto>) ejecutarConsulta(new MetodoInterfaceConsulta() {
            @Override
            public Object consulta() throws ServicioCodefacException, RemoteException {
                Map<String,Object> mapParameros=new HashMap<String, Object>();
                mapParameros.put("empresa", empresa);
                mapParameros.put("estado", GeneralEnumEstado.ACTIVO.getEstado());
                return getFacade().findByMap(mapParameros);
            }
        } );
    }
    
    public PresentacionProducto buscarPorNombre(Empresa empresa,String nombre) throws ServicioCodefacException,java.rmi.RemoteException
    {
        Map<String,Object> mapParametros=new HashMap<String,Object>();
        mapParametros.put("estado",GeneralEnumEstado.ACTIVO.getEstado());
        mapParametros.put("empresa", empresa);
        mapParametros.put("nombre",nombre);
        List<PresentacionProducto> resultados=getFacade().findByMap(mapParametros);
        if(resultados.size()>0)
        {
            return resultados.get(0);
        }
        return null;
    }
    
    
    
}
