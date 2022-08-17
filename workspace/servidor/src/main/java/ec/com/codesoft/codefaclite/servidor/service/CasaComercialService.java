/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.CasaComercialFacade;
import ec.com.codesoft.codefaclite.servidor.facade.MarcaProductoFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CasaComercial;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.MarcaProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.CasaComercialServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.MarcaProductoServiceIf;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class CasaComercialService extends ServiceAbstract<CasaComercial,CasaComercialFacade> implements CasaComercialServiceIf{

    public CasaComercialService() throws RemoteException {
        super(CasaComercialFacade.class);
    }
    
    private void validarGrabar(CasaComercial casaComercial) throws ServicioCodefacException, RemoteException
    {
        if(casaComercial.getEmpresa()==null)
        {
            throw new ServicioCodefacException("Error Validación: No se puede grabar sin registrar una empresa");
        }
        
    }

    @Override
    public CasaComercial grabar(CasaComercial casaComercial) throws ServicioCodefacException, RemoteException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                //validarGrabar(marcaProducto);
                //marcaProducto.setEstadoEnum(GeneralEnumEstado.ACTIVO);
                //entityManager.persist(marcaProducto);
                grabarSinTransaccion(casaComercial);
            }
        });
        return casaComercial;
    }
    
    

    
    
    public CasaComercial grabarSinTransaccion(CasaComercial casaComercial) throws ServicioCodefacException, RemoteException {
        validarGrabar(casaComercial);
        casaComercial.setEstadoEnum(GeneralEnumEstado.ACTIVO);
        entityManager.persist(casaComercial);
        return casaComercial;
    }

    @Override
    public void editar(CasaComercial casaComercial) throws ServicioCodefacException, RemoteException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                validarGrabar(casaComercial);
                entityManager.merge(casaComercial);
            }
        });
    }

    @Override
    public void eliminar(CasaComercial casaComercial) throws ServicioCodefacException, RemoteException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                casaComercial.setEstadoEnum(GeneralEnumEstado.ELIMINADO);
                entityManager.merge(casaComercial);
            }
        });
    }

    
    public List<CasaComercial> obtenerActivosPorEmpresa(Empresa empresa) throws ServicioCodefacException, RemoteException
    {
        return (List<CasaComercial>) ejecutarConsulta(new MetodoInterfaceConsulta() {
            @Override
            public Object consulta() throws ServicioCodefacException, RemoteException {
                Map<String,Object> mapParameros=new HashMap<String, Object>();
                mapParameros.put("empresa", empresa);
                mapParameros.put("estado", GeneralEnumEstado.ACTIVO.getEstado());
                return getFacade().findByMap(mapParameros);
            }
        } );
    }
    
    public CasaComercial buscarPorNombre(Empresa empresa,String nombre) throws ServicioCodefacException,java.rmi.RemoteException
    {
        Map<String,Object> mapParametros=new HashMap<String,Object>();
        mapParametros.put("estado",GeneralEnumEstado.ACTIVO.getEstado());
        mapParametros.put("empresa", empresa);
        mapParametros.put("nombre",nombre);
        List<CasaComercial> resultados=getFacade().findByMap(mapParametros);
        if(resultados.size()>0)
        {
            return resultados.get(0);
        }
        return null;
    }
    
    
    
}
