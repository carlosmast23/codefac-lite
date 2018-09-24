/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import com.sun.mail.handlers.multipart_mixed;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Bodega;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Compra;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CompraDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Kardex;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.KardexDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ProductoEnsamble;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidor.facade.AbstractFacade;
import ec.com.codesoft.codefaclite.servidor.facade.KardexFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.auxiliar.KardexDetalleTmp;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.KardexServiceIf;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author Carlos
 */
public class KardexService extends ServiceAbstract<Kardex,KardexFacade> implements KardexServiceIf
{
    
    /**
     * Entidad de control para manejar transacciones con la base de datos
     */
    private EntityManager em;
    
    public KardexService() throws RemoteException {
        super(KardexFacade.class);
        em=AbstractFacade.entityManager;
    }
    
    /**
     * Metodo que permite buscar el kardex por bodega y el el producto
     * @return 
     */
    
    public Kardex buscarKardexPorProductoyBodega(Bodega bodega,Producto producto) throws java.rmi.RemoteException
    {
        Map<String,Object> mapParametros=new HashMap<String,Object>();
        mapParametros.put("bodega",bodega);
        mapParametros.put("producto",producto);
        List<Kardex> listaKardex=obtenerPorMap(mapParametros);
        
        if(listaKardex!=null && listaKardex.size()>0)
        {
            return listaKardex.get(0);
        }
        
        return null;
    }
    

    public void IngresoEgresoInventarioEnsamble(Bodega bodega, Producto productoEnsamble,Integer cantidad,List<Kardex> componentesKardex, boolean ingreso) throws java.rmi.RemoteException,ServicioCodefacException
    {
        EntityTransaction et= entityManager.getTransaction();
        try
        {
            et.begin();
            //Buscar el Kardex del ensamble o crear
            Map<String,Object> parametrosMap=new HashMap<String,Object>();
            parametrosMap.put("bodega",bodega);
            parametrosMap.put("producto",productoEnsamble);
            List<Kardex> kardexList=obtenerPorMap(parametrosMap);
            
            //Obtener o crear el kardex si no existe
            Kardex kardex=null;
            if(kardexList!=null && kardexList.size()>0)
            {
                kardex=kardex=kardexList.get(0);                
            }
            else
            {
                kardex=new Kardex();
                kardex.setBodega(bodega);
                kardex.setFechaCreacion(UtilidadesFecha.getFechaHoy());
                kardex.setFechaModificacion(UtilidadesFecha.getFechaHoy());
                kardex.setPrecioPromedio(BigDecimal.ZERO);
                kardex.setPrecioTotal(BigDecimal.ZERO);
                kardex.setPrecioUltimo(BigDecimal.ZERO);
                kardex.setProducto(productoEnsamble);
                kardex.setStock(0);
                kardex.setReserva(0);
                entityManager.persist(kardex);
            }
            
            //Actualizar los detalles de los componentes del kardex                        
            for (Kardex kardexComponente : componentesKardex) {
                entityManager.merge(kardexComponente);  
                
            }
            
            //Calcular el valor del ensamble
            BigDecimal costoIndividualEnsamble=BigDecimal.ZERO;
            List<ProductoEnsamble> listaComponentes=kardex.getProducto().getDetallesEnsamble();
            for (ProductoEnsamble componenteEmsamble : listaComponentes) {
                parametrosMap = new HashMap<String, Object>();
                parametrosMap.put("bodega", bodega);
                parametrosMap.put("producto", componenteEmsamble.getComponenteEnsamble());
                kardexList = obtenerPorMap(parametrosMap);
                
                costoIndividualEnsamble=costoIndividualEnsamble.add(new BigDecimal(componenteEmsamble.getCantidad().toString()).multiply(kardexList.get(0).getPrecioUltimo()));
            }
            
            ///Actualizar los totales del emsamble
            kardex.setPrecioPromedio(kardex.getPrecioPromedio().add(costoIndividualEnsamble).divide(new BigDecimal("2"),2,RoundingMode.HALF_UP));
            kardex.setPrecioTotal(kardex.getPrecioTotal().add(costoIndividualEnsamble));
            kardex.setPrecioUltimo(costoIndividualEnsamble);
            
            //Agregar o descontar el stock cuando se ingrese o salga mercaderia
            if(ingreso)
                kardex.setStock(kardex.getStock()+cantidad );
            else
                kardex.setStock(kardex.getStock()-cantidad );
            
            //Crear el registro del kardex detalle 
            KardexDetalle kardexDetalle=new KardexDetalle();
            
            //Agregar o descontar el stock cuando se ingrese o salga mercaderia
            if(ingreso)
                kardexDetalle.setCodigoTipoDocumento(TipoDocumentoEnum.ENSAMBLE_INGRESO.getCodigo());
            else
                kardexDetalle.setCodigoTipoDocumento(TipoDocumentoEnum.ENSAMBLE_EGRESO.getCodigo());
                
            kardexDetalle.setCantidad(cantidad);
            kardexDetalle.setPrecioTotal(costoIndividualEnsamble.multiply(new BigDecimal(cantidad.toString())));
            kardexDetalle.setPrecioUnitario(costoIndividualEnsamble);
            kardexDetalle.setReferenciaDocumentoId(null);
            
            kardex.addDetalleKardex(kardexDetalle);
            entityManager.merge(kardex);
            
            et.commit();
        }catch(Exception e)
        {
            e.printStackTrace();
            et.rollback();
            throw  new ServicioCodefacException("Error al grabar el inventario");     
            
        }
    }
    
    public void ingresarInventario(KardexDetalle detalle) throws java.rmi.RemoteException,ServicioCodefacException
    {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                grabarKardexDetallSinTransaccion(detalle);
            }
        });
    }
    
    public void ingresarInventario(List<KardexDetalle> detalles) throws java.rmi.RemoteException,ServicioCodefacException
    {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                
                for (KardexDetalle detalle : detalles) {
                    grabarKardexDetallSinTransaccion(detalle);
                    
                }
                
            }
        });
    }
    
    
    private void grabarKardexDetallSinTransaccion(KardexDetalle detalle) throws RemoteException, RemoteException
    {
        //Buscar si ya existe el kardex o si no existe los creamos
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("bodega", detalle.getKardex().getBodega());
        map.put("producto", detalle.getKardex().getProducto());

        List<Kardex> kardexList = obtenerPorMap(map);

        Kardex kardex = null;
        if (kardexList == null | kardexList.size() == 0) {
            //Si no existe completo los datos para crear el kardex
            kardex = detalle.getKardex();
            //kardex.setBodega(bodega);
            kardex.setFechaCreacion(UtilidadesFecha.getFechaHoy());
            kardex.setFechaModificacion(UtilidadesFecha.getFechaHoy());
            kardex.setPrecioPromedio(BigDecimal.ZERO);
            kardex.setPrecioTotal(BigDecimal.ZERO);
            kardex.setPrecioUltimo(BigDecimal.ZERO);
            //kardex.setProducto(value.getProductoProveedor().getProducto());
            kardex.setStock(0);
            kardex.setReserva(0);
            em.persist(kardex); //grabando la persistencia
        } else {
            //Si existe el kardex solo creo
            kardex = kardexList.get(0);
        }

        detalle.setKardex(kardex);
        kardex.addDetalleKardex(detalle);
        em.persist(detalle);
        //em.persist(kardexDetalle); //grabando el kardex detalle

        //Esto va a depender del tipo de flujo del inventario es decir para saber si la suma o resta pero por defecto
        // el metodo es solo de ingreso asi que no considero el otro caso
        kardex.setStock(kardex.getStock() + detalle.getCantidad());
        kardex.setPrecioPromedio(kardex.getPrecioPromedio().add(detalle.getPrecioUnitario()).divide(new BigDecimal("2"), 2, RoundingMode.HALF_UP));
        kardex.setPrecioTotal(kardex.getPrecioTotal().add(detalle.getPrecioTotal()));
        kardex.setPrecioUltimo(detalle.getPrecioUnitario());
        kardex = em.merge(kardex);

        //Actualizar la compra de referencia para saber que ya fue ingresada
        switch (detalle.getCodigoTipoDocumentoEnum()) {
            case COMPRA_INVENTARIO:
                //Actualizar referencia de la compra para que sepa que ya fue ingresado la mercaderia
                //TODO:Mejorar esta parte porque esta grabando muchas veces lo mismo cuando hay varios detalles apuntando a la misma compra
                CompraService compraService = new CompraService();
                Compra compra = compraService.buscarPorId(detalle.getReferenciaDocumentoId());
                compra.setInventarioIngreso(EnumSiNo.SI.getLetra());
                em.merge(compra);
                break;

        }
    }
    
    /**
     * TODO: Ver si no voy a usar este metodo borrar
     * Metodo para el ingreso de nuevos productos del kardex
     * @param detalles 
     * @deprecated 
     */
    public void ingresarInventario(Map<KardexDetalle,CompraDetalle> detalles,Bodega bodega) throws java.rmi.RemoteException,ServicioCodefacException
    {
        EntityTransaction transaction=em.getTransaction();
        try
        {            
            transaction.begin();
            Compra compra=null; //referencia para almacenar la compra ingresada a inventario

            for (Map.Entry<KardexDetalle, CompraDetalle> entry : detalles.entrySet()) {

                KardexDetalle kardexDetalle = entry.getKey();
                CompraDetalle value = entry.getValue();
                compra=value.getCompra();
                Producto producto=value.getProductoProveedor().getProducto();

                //Verificar si existe el karde o lo crea;
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("bodega", bodega);
                map.put("producto",producto);
                List<Kardex> kardexList=obtenerPorMap(map);

                Kardex kardex=null;
                if(kardexList==null | kardexList.size()==0)
                {
                    //Creando el kardex cuando no existe en la base de datos
                    kardex=new Kardex();
                    kardex.setBodega(bodega);
                    kardex.setFechaCreacion(UtilidadesFecha.getFechaHoy());
                    kardex.setFechaModificacion(UtilidadesFecha.getFechaHoy());
                    kardex.setPrecioPromedio(BigDecimal.ZERO);
                    kardex.setPrecioTotal(BigDecimal.ZERO);
                    kardex.setPrecioUltimo(BigDecimal.ZERO);
                    kardex.setProducto(value.getProductoProveedor().getProducto());
                    kardex.setStock(0);
                    kardex.setReserva(0);
                    em.persist(kardex); //grabando la persistencia
                }            
                else
                {
                    //Si existe el kardex solo creo
                    kardex=kardexList.get(0);
                }

                /**
                 * Grabar los detalles de los kardes y actualizar los valores en el kardex
                 */
                kardexDetalle.setKardex(kardex);
                kardex.addDetalleKardex(kardexDetalle);
                //em.persist(kardexDetalle); //grabando el kardex detalle

                //Esto va a depender del tipo de flujo del inventario es decir para saber si la suma o resta pero por defecto
                // el metodo es solo de ingreso asi que no considero el otro caso
                kardex.setStock(kardex.getStock()+kardexDetalle.getCantidad());
                kardex.setPrecioPromedio(kardex.getPrecioPromedio().add(kardexDetalle.getPrecioUnitario()).divide(new BigDecimal("2"),2,RoundingMode.HALF_UP));
                kardex.setPrecioTotal(kardex.getPrecioTotal().add(kardexDetalle.getPrecioTotal()));
                kardex.setPrecioUltimo(kardexDetalle.getPrecioUnitario());
                kardex=em.merge(kardex);
            }
            
            if(compra!=null)
            {
                compra.setInventarioIngreso(EnumSiNo.SI.getLetra());
                em.merge(compra);
            }

            transaction.commit(); //si todo sale bien ejecuto en la base de datos
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
            transaction.rollback();
            throw  new ServicioCodefacException("Error al grabar el inventario");            
        } 
    
    }
    
}

