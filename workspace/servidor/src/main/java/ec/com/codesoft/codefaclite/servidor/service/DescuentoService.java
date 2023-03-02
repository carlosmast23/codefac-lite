/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.DescuentoFacade;
import ec.com.codesoft.codefaclite.servidor.facade.LoteFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
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
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.DescuentoCondicionPrecioServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.DescuentoSeviceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.LoteSeviceIf;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import java.math.BigDecimal;
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
        List<DescuentoCondicionPrecio> descuentoCondicionPrecioList=entity.getCondicionPrecioList();
        List<DescuentoProductoDetalle> descuentoProductoDetalleList=entity.getProductoList();
        validarGrabar(entity, CrudEnum.EDITAR);
        
        for (DescuentoCondicionPrecio condicion : descuentoCondicionPrecioList) 
        {
            //Si el datoya existe simplemente lo que tengo que hacer es editar
            if(condicion.getId()!=null && condicion.getId()>0)
            {
                entityManager.merge(condicion);
            }
            else
            {
                condicion.setId(null);
                entityManager.persist(condicion);
            }
        }
        
        for (DescuentoProductoDetalle descuento : descuentoProductoDetalleList) 
        {
            if(descuento.getId()!=null && descuento.getId()>0)
            {
                entityManager.merge(descuento);
            }
            else
            {
                descuento.setId(null);
                entityManager.persist(descuento);
            }
            
        }
        
        eliminarDetallesDescuentoCondicion(entity);
        eliminarDetallesProducto(entity);
        entityManager.flush();
               
                
        entityManager.merge(entity);
        
        
        
    }
    
    private void eliminarDetallesDescuentoCondicion(Descuento entity) throws RemoteException, ServicioCodefacException
    {
        DescuentoCondicionPrecioServiceIf  descuentoCondicionPrecioServiceIf= ServiceFactory.getFactory().getDescuentoCondicionPrecioServiceIf();
        List<DescuentoCondicionPrecio> originalList= descuentoCondicionPrecioServiceIf.consultarPorDescuento(entity);
        //List<DescuentoCondicionPrecio> eliminadoList=new ArrayList<DescuentoCondicionPrecio>();        
        for (DescuentoCondicionPrecio condicion : originalList) {
            
            //Si el dato original no esta en la otra lista significa que fue eliminado
            if(!entity.getCondicionPrecioList().contains(condicion))
            {
                //eliminadoList.add(condicion);
                //condicion=descuentoCondicionPrecioServiceIf.buscarPorId(condicion.getId());
                condicion=entityManager.merge(condicion);
                entityManager.flush();
                entityManager.remove(condicion);
            }
        }
        
    }
    
    private void eliminarDetallesProducto(Descuento entity) throws RemoteException, ServicioCodefacException
    {
        List<DescuentoProductoDetalle> originalList= ServiceFactory.getFactory().getDescuentoProductoDetalleServiceIf().consultarPorDescuento(entity);
        
        for (DescuentoProductoDetalle condicion : originalList) 
        {
            //Si el dato original no esta en la otra lista significa que fue eliminado
            if(!entity.getProductoList().contains(condicion))
            {
                //eliminadoList.add(condicion);
                condicion=entityManager.merge(condicion);
                entityManager.flush();
                //condicion=ServiceFactory.getFactory().getDescuentoProductoDetalleServiceIf().buscarPorId(condicion.getId());
                //entityManager.flush();
                entityManager.remove(condicion);
            }
        }
        
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
    
    public List<BigDecimal> consultarDescuentosPorProducto(Producto producto,Integer numeroPrecio) throws ServicioCodefacException, RemoteException
    {
        List<DescuentoProductoDetalle> detalleDescuento= getFacade().consultarDescuentosPorProductoFacade(producto);
        
        List<BigDecimal> descuentoList=new ArrayList<BigDecimal>();
        for (DescuentoProductoDetalle descuentoProductoDetalle : detalleDescuento) {
            
            //OPTIMIZAR ESTA PARTE PARA HACER DIRECTO EN LA CONSULTA
            List<DescuentoCondicionPrecio> condicionList= descuentoProductoDetalle.getDescuento().getCondicionPrecioList();
            for (DescuentoCondicionPrecio condicion : condicionList) 
            {
                if(condicion.getNumeroPrecio().equals(numeroPrecio))
                {
                    descuentoList.add(condicion.getPorcentajeDescuento());
                }
            }
            
        }
        
        return descuentoList;
        
    }
 
    
    
}
