/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.entity.Bodega;
import ec.com.codesoft.codefaclite.servidor.entity.Compra;
import ec.com.codesoft.codefaclite.servidor.entity.CompraDetalle;
import ec.com.codesoft.codefaclite.servidor.entity.Kardex;
import ec.com.codesoft.codefaclite.servidor.entity.KardexDetalle;
import ec.com.codesoft.codefaclite.servidor.entity.Producto;
import ec.com.codesoft.codefaclite.servidor.entity.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidor.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidor.facade.AbstractFacade;
import ec.com.codesoft.codefaclite.servidor.facade.KardexFacade;
import ec.com.codesoft.ejemplo.utilidades.fecha.UtilidadesFecha;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author Carlos
 */
public class KardexService extends ServiceAbstract<Kardex,KardexFacade>{
    
    /**
     * Entidad de control para manejar transacciones con la base de datos
     */
    private EntityManager em;
    
    public KardexService() {
        super(KardexFacade.class);
        em=AbstractFacade.entityManager;
    }
    
    /**
     * Metodo para el ingreso de nuevos productos del kardex
     * @param detalles 
     */
    public void ingresarInventario(Map<KardexDetalle,CompraDetalle> detalles,Bodega bodega) throws ServicioCodefacException
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
                compra.setInventarioIngreso(EnumSiNo.SI.getLetra());

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
