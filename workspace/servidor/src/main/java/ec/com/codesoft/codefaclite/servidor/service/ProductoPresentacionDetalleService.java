/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.ProductoPresentacionDetalleFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ProductoPresentacionDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ProductoPresentacionDetalleServiceIf;
import jakarta.persistence.EntityManager;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DellWin10
 */
public class ProductoPresentacionDetalleService extends ServiceAbstract<ProductoPresentacionDetalle, ProductoPresentacionDetalleFacade> implements ProductoPresentacionDetalleServiceIf {

    public ProductoPresentacionDetalleService() throws RemoteException {
        super(ProductoPresentacionDetalleFacade.class);
    }
    
    public List<ProductoPresentacionDetalle> buscarPorProducto(Producto producto) throws ServicioCodefacException, RemoteException {
        
        return (List<ProductoPresentacionDetalle>) ejecutarTransaccionConResultado(new MetodoInterfaceTransaccionResultado() 
        {
            @Override
            public Object transaccion(EntityManager entityManager) throws ServicioCodefacException, RemoteException {
                //ProductoPresentacionDetalle d;
                //d.getProductoOriginal();
                Map<String, Object> mapParametros = new HashMap<String, Object>();
                mapParametros.put("productoOriginal", producto);
                return getFacade().findByMap(mapParametros,entityManager);
            }
        });
        
    }
    
    //TODO: Este metodo esta causando conflicto cuando se consulta muchas veces, se pone el sistema exageradamente lento
    @Deprecated
    public ProductoPresentacionDetalle buscarPorProductoEmpaquetado(Producto productoEmpaquetado) throws ServicioCodefacException, RemoteException 
    {
        return (ProductoPresentacionDetalle) ejecutarTransaccionConResultado(new MetodoInterfaceTransaccionResultado() {
            @Override
            public Object transaccion(EntityManager entityManager) throws ServicioCodefacException, RemoteException {
                Map<String, Object> mapParametros = new HashMap<String, Object>();
                //mapParametros.put("productoEmpaquetado", productoEmpaquetado);
                mapParametros.put("productoEmpaquetado.idProducto", productoEmpaquetado.getIdProducto());
                mapParametros.put("productoOriginal.estado", GeneralEnumEstado.ACTIVO.getLetra());
                List<ProductoPresentacionDetalle> detalles = getFacade().findByMap(mapParametros,entityManager);
                if (detalles.size() > 0) {
                    return detalles.get(0);
                }
                return null;
            }
        });
        
    }
    
}
