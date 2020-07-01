/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.SucursalFacade;
import ec.com.codesoft.codefaclite.servidor.facade.UsuarioFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SucursalServiceIf;
import java.io.Serializable;
 ;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class SucursalService extends ServiceAbstract<Sucursal, SucursalFacade> implements SucursalServiceIf{

    public SucursalService()    {
        super(SucursalFacade.class);
    }

    @Override
    public Sucursal grabar(Sucursal entity) throws ServicioCodefacException   {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException   {
                entity.setEstado(GeneralEnumEstado.ACTIVO.getEstado());
                entityManager.persist(entity);
            }
        });
        return entity;
    }
    
    @Override
    public List<Sucursal> consultarActivosPorEmpresa(Empresa empresa)  throws ServicioCodefacException  
    {
        //Sucursal sucursal;
        //sucursal.getEmpresa();
        //sucursal.getEstado();
        Map<String,Object> mapParametros=new HashMap<String,Object>();
        mapParametros.put("empresa", empresa);
        mapParametros.put("estado", GeneralEnumEstado.ACTIVO.getEstado());
        return getFacade().findByMap(mapParametros);        
    }

    @Override
    public void eliminar(Sucursal entity)    {
        try {
            ejecutarTransaccion(new MetodoInterfaceTransaccion() {
                @Override
                public void transaccion() throws ServicioCodefacException   {
                    entity.setEstado(GeneralEnumEstado.ELIMINADO.getEstado());
                    entityManager.merge(entity);
                }
            });
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(SucursalService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public Sucursal obtenerPorCodigo(Integer codigo) throws ServicioCodefacException   {
       Map<String,Object> mapParametros=new HashMap<String,Object>();
       //Sucursal sucursal;
       //sucursal.getCodigoSucursal()
       mapParametros.put("estado",GeneralEnumEstado.ACTIVO.getEstado());
       mapParametros.put("codigoSucursal",codigo);
       List<Sucursal> sucursales=getFacade().findByMap(mapParametros);
       if(sucursales.size()>0)
       {
            return sucursales.get(0);
       }
       return null;
    }
    
    public Sucursal obtenerMatrizPorSucursal(Empresa empresa) throws ServicioCodefacException  
    {
       Map<String,Object> mapParametros=new HashMap<String,Object>();
       //Sucursal sucursal;
       //sucursal.getT
       mapParametros.put("estado",GeneralEnumEstado.ACTIVO.getEstado());
       mapParametros.put("tipo",Sucursal.TipoSucursalEnum.MATRIZ.getCodigo());
       List<Sucursal> sucursales=getFacade().findByMap(mapParametros);
       
       if(sucursales.size()>0)
       {
           return sucursales.get(0);
       }
       return null;
    }
    
    
}
