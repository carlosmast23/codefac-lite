/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Compra;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CompraDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoConsultaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

/**
 *
 * @author Carlos
 */
public class CompraFacade extends AbstractFacade<Compra>{
    
    public CompraFacade() {
        super(Compra.class);
    }
    
    public BigDecimal obtenerCompraReporteTotalValor(Persona proveedor, Date fechaInicial, Date fechaFin, DocumentoEnum documentoEnum, TipoDocumentoEnum tipoDocumentoEnum,GeneralEnumEstado estadoEnum,Empresa empresa)
    {
        return (BigDecimal) obtenerCompraReporteAbstract(proveedor, fechaInicial, fechaFin, documentoEnum, tipoDocumentoEnum, estadoEnum, empresa, TipoConsultaEnum.VALOR_TOTAL).getSingleResult();
    }
    
    public Query obtenerCompraReporteAbstract(Persona proveedor, Date fechaInicial, Date fechaFin, DocumentoEnum documentoEnum, TipoDocumentoEnum tipoDocumentoEnum,GeneralEnumEstado estadoEnum,Empresa empresa,TipoConsultaEnum tipoConsultaEnum)
    {        
        String cliente = "";
        String fecha = "";
        String documento = "";
        String tipoDocumento = "";
        String estadoEnumQuery= "";
        
        if (proveedor != null) {
            cliente = "u.proveedor=?1";
        } else {
            cliente = "1=1";
        }
        
        if (fechaInicial == null && fechaFin != null) {
            fecha = " AND u.fechaFactura <= ?3";
        } else if (fechaInicial != null && fechaFin == null) {
            fecha = " AND u.fechaFactura <= ?2";
        } else if (fechaInicial == null && fechaFin == null) {
            fecha = "";
        } else {
            fecha = " AND (u.fechaFactura BETWEEN ?2 AND ?3)";
        }
        
        if (documentoEnum != null) {
            documento = " AND u.codigoDocumento=?4";
        }
        
        if(tipoDocumentoEnum != null)
        {
            tipoDocumento = " AND u.codigoTipoDocumento=?5";
        }
        
        if(estadoEnum!=null)
        {
            estadoEnumQuery=" AND u.estado=?6 ";
        }
        
        String selectStr="SELECT u ";
        if(tipoConsultaEnum.equals(TipoConsultaEnum.TAMANIO))
        {
            selectStr="SELECT COUNT(1) ";
        }
        else if(tipoConsultaEnum.equals(TipoConsultaEnum.VALOR_TOTAL))
        {
            selectStr="SELECT SUM(u.total) ";
        }
        
        try {
            String queryString = selectStr+ " FROM Compra u WHERE u.empresa=?7 and " + cliente + fecha + documento + tipoDocumento+estadoEnumQuery;
            System.out.println("Script: "+queryString);
            Query query = getEntityManager().createQuery(queryString);
            if (proveedor != null) 
            {
                query.setParameter(1, proveedor);
            }
            if (fechaInicial != null) 
            {
                query.setParameter(2, fechaInicial);
            }
            if (fechaFin != null) 
            {
                query.setParameter(3, fechaFin);
            }
            if (documentoEnum != null) 
            {
                //System.out.println("---->>>>>>" + documentoEnum.getCodigo());
                query.setParameter(4, documentoEnum.getCodigo());
                
            }
            if(tipoDocumentoEnum != null)
            {
                query.setParameter(5, tipoDocumentoEnum.getCodigo());
            }
            
            if(estadoEnum!=null)
            {
                query.setParameter(6,estadoEnum.getEstado());
            }
            
            query.setParameter(7,empresa);
            
            return query;
        } catch (NoResultException e) {
            return null;
        }    
    }
    
    public List<Compra> obtenerCompraReporte(Persona proveedor, Date fechaInicial, Date fechaFin, DocumentoEnum documentoEnum, TipoDocumentoEnum tipoDocumentoEnum,GeneralEnumEstado estadoEnum,Empresa empresa)
    {        
        return obtenerCompraReporteAbstract(proveedor, fechaInicial, fechaFin, documentoEnum, tipoDocumentoEnum, estadoEnum, empresa, TipoConsultaEnum.DATOS).getResultList();
        /*String cliente = "";
        String fecha = "";
        String documento = "";
        String tipoDocumento = "";
        String estadoEnumQuery= "";
        
        if (proveedor != null) {
            cliente = "u.proveedor=?1";
        } else {
            cliente = "1=1";
        }
        
        if (fechaInicial == null && fechaFin != null) {
            fecha = " AND u.fechaFactura <= ?3";
        } else if (fechaInicial != null && fechaFin == null) {
            fecha = " AND u.fechaFactura <= ?2";
        } else if (fechaInicial == null && fechaFin == null) {
            fecha = "";
        } else {
            fecha = " AND (u.fechaFactura BETWEEN ?2 AND ?3)";
        }
        
        if (documentoEnum != null) {
            documento = " AND u.codigoDocumento=?4";
        }
        
        if(tipoDocumentoEnum != null)
        {
            tipoDocumento = " AND u.codigoTipoDocumento=?5";
        }
        
        if(estadoEnum!=null)
        {
            estadoEnumQuery=" AND u.estado=?6 ";
        }
        
        try {
            String queryString = "SELECT u FROM Compra u WHERE u.empresa=?7 and " + cliente + fecha + documento + tipoDocumento+estadoEnumQuery;
            System.out.println("Script: "+queryString);
            Query query = getEntityManager().createQuery(queryString);
            if (proveedor != null) 
            {
                query.setParameter(1, proveedor);
            }
            if (fechaInicial != null) 
            {
                query.setParameter(2, fechaInicial);
            }
            if (fechaFin != null) 
            {
                query.setParameter(3, fechaFin);
            }
            if (documentoEnum != null) 
            {
                //System.out.println("---->>>>>>" + documentoEnum.getCodigo());
                query.setParameter(4, documentoEnum.getCodigo());
                
            }
            if(tipoDocumentoEnum != null)
            {
                query.setParameter(5, tipoDocumentoEnum.getCodigo());
            }
            
            if(estadoEnum!=null)
            {
                query.setParameter(6,estadoEnum.getEstado());
            }
            
            query.setParameter(7,empresa);
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }    */
    }
    
     public List<Compra> getCompraRetencionDisenable() {
        try {
            
            String queryString = "SELECT u FROM Compra u WHERE u.estadoRetencion=?1";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter(1, Compra.RetencionEnumCompras.NO_EMITIDO.getEstado());
            return (List<Compra>) query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
     
    public Boolean verificarCompraRepetida(Compra compra) {
        try {
            /*Compra compra;
            compra.getProveedor();
            compra.getSecuencial();
            compra.getPuntoEmision();
            compra.getPuntoEstablecimiento();*/
            
            String queryString = "SELECT count(u.id) FROM Compra u WHERE u.proveedor=?1 and u.secuencial=?2 and u.puntoEmision=?3 and u.puntoEstablecimiento=?4 and u.estado<>?5 ";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter(1,compra.getProveedor());
            query.setParameter(2,compra.getSecuencial());
            query.setParameter(3,compra.getPuntoEmision());
            query.setParameter(4,compra.getPuntoEstablecimiento());
            query.setParameter(5,GeneralEnumEstado.ELIMINADO.getEstado());
            Long cantidad= (Long) query.getSingleResult();
            if(cantidad>0)
                return true;
            else
                return false;
            
        } catch (NoResultException e) {
            return null;
        }

    }
    
    /**
     * TODO: Falta tomar en cuenta el estado para tener resultados mas exactos
     * @param puntoEmision
     * @param puntoEstablecimiento
     * @return 
     */
    public Integer obtenerMaximoCodigoPorDocumento(Integer puntoEmision,BigDecimal puntoEstablecimiento,DocumentoEnum documentoEnum,Empresa empresa)
    {   
        //Compra compra;
        //compra.getEmpresa();
        //compra.getPuntoEmision()
        //c.getEstado();
        //c.getPuntoEmision();
        //c.getPuntoEstablecimiento();
        //c.getCodigoDocumento();
        //c.getSecuencial()
        
        String queryString="SELECT MAX( CAST (c.secuencial AS BIGINT )) FROM Compra c WHERE c.puntoEmision=?1 AND c.puntoEstablecimiento=?2 AND c.codigoDocumento=?3 AND c.empresa=?4 ";
        Query query = getEntityManager().createQuery(queryString);
        query.setParameter(1,puntoEmision);
        query.setParameter(2,puntoEstablecimiento);
        query.setParameter(3,documentoEnum.getCodigo());
        query.setParameter(4,empresa);
        
        Long secuencialMaximo=(Long) query.getSingleResult();
        if(secuencialMaximo!=null)
        {
            return secuencialMaximo.intValue()+1;
        }        
        return 1;        
    }
    
    public List<Producto> obtenerProductosActualizarPrecios(Compra compra) throws ServicioCodefacException,java.rmi.RemoteException
    {
        //Compra compra;
        //compra.getDetalles().
        //CompraDetalle compradetalle;
        //compradetalle.getProductoProveedor().getProducto();
        
        String queryString="SELECT cd.productoProveedor.producto FROM Compra u LEFT JOIN u.detalles cd  WHERE u=?1 AND cd.productoProveedor.producto.actualizarPrecio=?2 ";
        Query query = getEntityManager().createQuery(queryString);
        query.setParameter(1,compra);
        query.setParameter(2,EnumSiNo.SI.getLetra());
        return query.getResultList();
    }
    
}
