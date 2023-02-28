/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.DescuentoFacade;
import ec.com.codesoft.codefaclite.servidor.facade.LoteFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Bodega;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Descuento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.DescuentoCondicionPrecio;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.DescuentoProductoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Lote;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CrudEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.reportData.FechaCaducidadData;
import ec.com.codesoft.codefaclite.servidorinterfaz.reportData.ReportDataAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.reportData.ReporteFechaCaducidadReport;
import ec.com.codesoft.codefaclite.servidorinterfaz.result.FechaCaducidadResult;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.DescuentoSeviceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.LoteSeviceIf;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import java.math.RoundingMode;
import java.rmi.RemoteException;
import java.sql.Date;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DellWin10
 */
public class DescuentoService extends ServiceAbstract<Descuento,DescuentoFacade> implements DescuentoSeviceIf{

    public DescuentoService() throws RemoteException {
        super(DescuentoFacade.class);
    }
    
    private void setearDatosDefecto(Descuento descuento,Empresa empresa)
    {
        descuento.setEmpresa(empresa);
    }
    
    private void validarGrabar(Descuento descuento,CrudEnum crudEnum) throws ServicioCodefacException
    {           
        
        if(UtilidadesTextos.verificarNullOVacio(descuento.getNombre()))
        {
            throw new ServicioCodefacException("Debe ingresar un nombre de descuento para grabar");
        }
        
        if(descuento.getEmpresa()==null)
        {
            throw new ServicioCodefacException("No se puede GRABAR un descuento sin asignar una empresa");
        }
        
        /*if(descuento.getEmpresa()==null)
        {
            throw new ServicioCodefacException("No se puede grabar sin una Empresa");
        }
        
        if(UtilidadesTextos.verificarNullOVacio(descuento.getCodigo()))
        {
            throw new ServicioCodefacException("Debe ingresar un código de lote para grabar");
        }*/
        
    }

    @Override
    public Descuento grabar(Descuento entity,Empresa empresa,Usuario usuarioCreacion) throws ServicioCodefacException, RemoteException {
        
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                setearDatosDefecto(entity, empresa);
                entity.setEstadoEnum(GeneralEnumEstado.ACTIVO);
                
                setDatosAuditoria(entity,usuarioCreacion,CrudEnum.CREAR);
                //setearDatosGrabar(entity, empresa,CrudEnum.CREAR);
                validarGrabar(entity, CrudEnum.CREAR);
                                
                
                List<DescuentoProductoDetalle> productoList= entity.getProductoList();
                entity.setProductoList(null);
                
                List<DescuentoCondicionPrecio> condicionList= entity.getCondicionPrecioList();
                entity.setCondicionPrecioList(null);
                
                entityManager.persist(entity);
                
                //Grabar los detalles de la entidad
                
                for (DescuentoProductoDetalle productoDetalle : productoList) 
                {
                    if(productoDetalle.getId()!=null && productoDetalle.getId()<0)
                    {
                        productoDetalle.setId(null);
                    }
                    
                    productoDetalle.setDescuento(entity);
                    entityManager.persist(productoDetalle);
                    
                }
                
                
                for (DescuentoCondicionPrecio condicion : condicionList) 
                {
                    if (condicion.getId() != null && condicion.getId() < 0) {
                        condicion.setId(null);
                    }

                    condicion.setDescuento(entity);
                    entityManager.persist(condicion);
                }
                
                entity.setProductoList(productoList);
                entity.setCondicionPrecioList(condicionList);
                
                entityManager.merge(entity);
                
                
            }
        });
        return entity;
    }
    
    
    
    
    
    @Override
    public Descuento editar(Descuento entity,Empresa empresa,Usuario usuarioCreacion) throws ServicioCodefacException, RemoteException {
        
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
    
    public void editarSinTransaccion(Descuento entity) throws ServicioCodefacException, RemoteException 
    {
        validarGrabar(entity, CrudEnum.EDITAR);
        entityManager.merge(entity);
    }
    
    
    @Override
    public void eliminar(Descuento entity) throws ServicioCodefacException, RemoteException {
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
