/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.com.codesoft.codefaclite.servidor.facade;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Bodega;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Descuento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.DescuentoProductoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Lote;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoConsultaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.result.FechaCaducidadResult;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.list.UtilidadesLista;
import jakarta.persistence.EntityManager;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Query;

/**
 *
 * @author DellWin10
 */
public class DescuentoFacade extends AbstractFacade<Descuento>{

    public DescuentoFacade() {
        super(Descuento.class);
    }
    
    public List<DescuentoProductoDetalle> consultarDescuentosPorProductoFacade(Producto producto,EntityManager em)
    {
        ///DescuentoProductoDetalle dpd;        
        
        //dpd.getDescuento().getEstado();
        //dpd.getDescuento().getFechaFin();
        String queryStr=" SELECT d FROM DescuentoProductoDetalle d WHERE d.producto=?1 AND d.descuento.estado=?2 AND ?3>=d.descuento.fechaInicio AND ?3<=d.descuento.fechaFin ";
        Query query=em.createQuery(queryStr);
        query.setParameter(1, producto);
        query.setParameter(2, GeneralEnumEstado.ACTIVO.getEstado());
        query.setParameter(3, UtilidadesFecha.getFechaHoraHoy());
        return query.getResultList();
    }
    
    public List<DescuentoProductoDetalle> consultarDescuentosPorProductoIdFacade(Long productoId,EntityManager em)
    {
        ///DescuentoProductoDetalle dpd;        
        //Producto p;
        //p.getIdProducto();
        //dpd.getDescuento().getEstado();
        //dpd.getDescuento().getFechaFin();
        String queryStr=" SELECT d FROM DescuentoProductoDetalle d WHERE d.producto.idProducto=?1 AND d.descuento.estado=?2 AND ?3>=d.descuento.fechaInicio AND ?3<=d.descuento.fechaFin ";
        Query query=em.createQuery(queryStr);
        query.setParameter(1, productoId);
        query.setParameter(2, GeneralEnumEstado.ACTIVO.getEstado());
        query.setParameter(3, UtilidadesFecha.getFechaHoraHoy());
        return query.getResultList();
    }
   
    
    
}
