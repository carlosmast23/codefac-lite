/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Bodega;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CategoriaProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Kardex;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.KardexDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author Carlos
 */
public class KardexFacade extends AbstractFacade<Kardex> {

    public KardexFacade() {
        super(Kardex.class);
    }

    public List<Object[]> obtenerConsultaSaldoAnterior(Date fechaCorte, Producto producto, Bodega bodega) {
        //KardexDetalle kd;
        //kd.getKardex().getBodega();
        //kd.getKardex().getProducto();
        //kd.getPrecioTotal();
        String queryString = "SELECT kd.codigoTipoDocumento, SUM(kd.cantidad),SUM(kd.precioUnitario),SUM(kd.precioTotal) "
                + "FROM KardexDetalle kd "
                + "WHERE kd.fechaIngreso<?1 and kd.kardex.bodega=?2 and kd.kardex.producto=?3 "
                + "group by kd.codigoTipoDocumento ";

        Query query = getEntityManager().createQuery(queryString);
        query.setParameter(1, fechaCorte);
        query.setParameter(2, bodega);
        query.setParameter(3, producto);

        return query.getResultList();

    }

    public List<KardexDetalle> obtenerConsultaPorFechaFacade(Date fechaInicial, Date fechaFinal, Producto producto, Bodega bodega, Integer cantidadMovimientos) {
        try {
            //KardexDetalle kd;

            //kd.getFechaIngreso();
            String queryString = "SELECT kd FROM KardexDetalle kd WHERE kd.kardex.bodega=?3 and kd.kardex.producto=?4 ";

            if (fechaInicial != null) {
                queryString += " and kd.fechaIngreso>=?1 ";
            }

            if (fechaFinal != null) {
                queryString += " and kd.fechaIngreso<=?2 ";
            }

            //Agregar orden y un limite de la consulta
            //queryString+=" order by kd.id desc ";
            System.out.println(queryString);
            Query query = getEntityManager().createQuery(queryString);

            //if (cantidadMovimientos != null) {
            //    query.setMaxResults(cantidadMovimientos);
            //}
            query.setParameter(3, bodega);
            query.setParameter(4, producto);

            if (fechaInicial != null) {
                query.setParameter(1, fechaInicial);
            }

            if (fechaFinal != null) {
                query.setParameter(2, fechaFinal);
            }

            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Object[]> consultarStockMinimoFacade(Bodega bodega,CategoriaProducto categoria) throws java.rmi.RemoteException {

        String whereBodega="";
        if(bodega!=null)
        {
            whereBodega=" and k.bodega=?1 ";
        }
        
        String whereCategoria="";
        if(categoria!=null)
        {
            whereCategoria=" and k.producto.catalogoProducto.categoriaProducto=?2 ";
        }
        
        String queryString = "SELECT k.producto,max(k.stock) FROM Kardex k WHERE 1=1 "+whereBodega+whereCategoria+" group by k.producto having max(k.stock)<=k.producto.cantidadMinima  ";
        Query query = getEntityManager().createQuery(queryString);
        
        if(bodega!=null)
        {
            query.setParameter(1,bodega);
        }
        
        if(categoria!=null)
        {
            query.setParameter(2,categoria);
        }
        //query.setParameter(1,producto);
        return query.getResultList();

    }

    /**
     * TODO: Falta filtrar por empresa cuando sea vacia la bodega
     * @param bodega
     * @return
     * @throws java.rmi.RemoteException 
     */
    public List<Object[]> consultarStockFacade(Bodega bodega,CategoriaProducto categoria) throws java.rmi.RemoteException {
        
        //Kardex k;
        //k.getProducto().getCatalogoProducto().getCategoriaProducto();
        String whereBodega="";
        if(bodega!=null)
        {
            whereBodega=" and k.bodega=?1 ";
        }
        
        String whereCategoria="";
        if(categoria!=null)
        {
            whereCategoria=" and k.producto.catalogoProducto.categoriaProducto=?2 ";
        }
        
        //Talvez agregar condicion para buscar solo por kardex activos
        String queryString = "SELECT k.producto,k.stock FROM Kardex k WHERE 1=1 "+whereBodega+whereCategoria;
        Query query = getEntityManager().createQuery(queryString);
        
        if(bodega!=null)
        {
            query.setParameter(1,bodega);
        }        
        
        if(categoria!=null)
        {
            query.setParameter(2,categoria);
        }
        
        return query.getResultList();

    }

}
