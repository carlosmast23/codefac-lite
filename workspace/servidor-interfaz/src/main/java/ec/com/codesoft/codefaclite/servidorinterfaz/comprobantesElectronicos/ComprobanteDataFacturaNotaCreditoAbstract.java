/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos;

import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.InformacionComprobanteAbstract;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general.ImpuestoComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general.TotalImpuesto;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.util.ComprobantesElectronicosUtil;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteVentaNotaCreditoAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.DetalleFacturaNotaCeditoAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ImpuestoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Presupuesto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriIdentificacion;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroEstudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.respuesta.ReferenciaDetalleFacturaRespuesta;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SriIdentificacionServiceIf;
import ec.com.codesoft.codefaclite.utilidades.list.UtilidadesMap;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import ec.com.codesoft.codefaclite.utilidades.validadores.UtilidadValidador;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CARLOS_CODESOFT
 */
public abstract class ComprobanteDataFacturaNotaCreditoAbstract implements ComprobanteDataInterface ,Serializable{
 
    
    public void llenarInformacionComprobante(InfoComprobanteInterface informacionComprobante,ComprobanteVentaNotaCreditoAbstract comprobante)
    {
        informacionComprobante.setFechaEmision(ComprobantesElectronicosUtil.dateToString(new java.sql.Date(comprobante.getFechaEmision().getTime())));
        //informacionComprobante.setDireccion(); //TODO: Este campo no son iguales en factura y nota de credito
        informacionComprobante.setDirEstablecimiento(UtilidadValidador.normalizarTexto(comprobante.getDireccionEstablecimiento()));
        
        //SriIdentificacionServiceIf servicioSri = ServiceFactory.getFactory().getSriIdentificacionServiceIf();
        SriIdentificacion sriIdentificacion = getSriIdentificacion(comprobante);
        /*try {
            sriIdentificacion = servicioSri.obtenerPorTransaccionEIdentificacion(comprobante.getCliente().getTipoIdentificacionEnum(), SriIdentificacion.tipoTransaccionEnum.VENTA);
        } catch (RemoteException ex) {
            Logger.getLogger(ComprobanteDataFacturaNotaCreditoAbstract.class.getName()).log(Level.SEVERE, null, ex);
        }*/

        if (sriIdentificacion != null && sriIdentificacion.getCodigo().equals(SriIdentificacion.CEDULA_IDENTIFICACION)) 
        {
            informacionComprobante.setIdentificacion(comprobante.getCliente().getIdentificacion());
        } 
        else if(sriIdentificacion != null && sriIdentificacion.getCodigo().equals(SriIdentificacion.RUC_IDENTIFICACION)) 
        {
            //TODO: Revisar si esta parte tiene sentido que complete los ceros
            informacionComprobante.setIdentificacion(UtilidadesTextos.llenarCarateresDerecha(comprobante.getCliente().getIdentificacion(), 13, "0"));
        }
        else
        {
            informacionComprobante.setIdentificacion(comprobante.getCliente().getIdentificacion());
        }
        //informacionComprobante.setIdentificacion(comprobante.getIdentificacion());
        
        //informacionComprobante.setImporteTotal(BigDecimal.ONE); //NOTA:Solo tiene en factura
        informacionComprobante.setObligadoContabilidad(comprobante.getObligadoLlevarContabilidad());
        //informacionComprobante.setRazonSocial(comprobante.getCliente().getRazonSocial()); /TODO: NO se puede generaliar porque solo tiene la factura
        informacionComprobante.setTipoIdentificacion(getSriIdentificacion(comprobante).getCodigo());
        
        if(comprobante.getContribuyenteEspecial()!=null && !comprobante.getContribuyenteEspecial().trim().isEmpty())
        {
            informacionComprobante.setContribuyenteEspecial(comprobante.getContribuyenteEspecial());
        }
        //informacionComprobante.setTotalDescuento(BigDecimal.ONE); TODO: No se puede generalizar
        //informacionComprobante.setTotalSinImpuestos(BigDecimal.ONE);
        //informacionComprobante.setTotalImpuestos(list);
        
        
    }
    
    /**
     * Calcular los impuestos por cada detalle de la factura y por el total
     * @param mapTotalImpuestos
     * @param comprobanteDetalle
     * @return 
     */
    public List<ImpuestoComprobante> calcularImpuestos(DetalleFacturaNotaCeditoAbstract comprobanteDetalle) {
        ReferenciaDetalleFacturaRespuesta respuesta;
        try {
            //Busco la FACTURA O NOTA DE CREDITO
            //TODO: Corregir esta parte por que cuando modifico un producto el iva , se va a modificar los reportes de la factura y eso debe ser datos fijos
            respuesta = ServiceFactory.getFactory().getFacturacionServiceIf().obtenerReferenciaDetalleFactura(comprobanteDetalle.getTipoDocumentoEnum(), comprobanteDetalle.getReferenciaId());
            if(respuesta!=null)
            {
                CatalogoProducto catalogoProducto = respuesta.catalogoProducto;            
                
                List<ImpuestoComprobante> listaComprobantes = crearImpuestoDetalles(
                        catalogoProducto.getIce(),
                        comprobanteDetalle.getIvaPorcentaje(),
                        comprobanteDetalle.totalSinImpuestosConIce(), 
                        comprobanteDetalle.recalcularIva(), 
                        comprobanteDetalle.getTotal(),
                        comprobanteDetalle.getValorIce()
                );
                return listaComprobantes;
            }
            
        } catch (RemoteException ex) {
            Logger.getLogger(ComprobanteDataFacturaNotaCreditoAbstract.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ComprobanteDataFacturaNotaCreditoAbstract.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static List<ImpuestoComprobante> crearImpuestoDetalles(ImpuestoDetalle iceImpuesto,Integer tarifaIva,BigDecimal totalSinImpuestosConIce,BigDecimal ivaRecalculado,BigDecimal total,BigDecimal valorIce)
    {
        //TODO: SOLUCION TEMPORAL PARA SOLUCIONAR EL TEMA DEL IVA, por que como ahora no depende el producto del iva, el mismo producto puede tener iva 12 o iva cero
        ImpuestoDetalle impuestoIva=null;
        if(ivaRecalculado.compareTo(BigDecimal.ZERO)==0)
        {
            try {
                impuestoIva=ServiceFactory.getFactory().getImpuestoDetalleServiceIf().buscarPorTarifa(0);
            } catch (ServicioCodefacException ex) {
                Logger.getLogger(ComprobanteDataFacturaNotaCreditoAbstract.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RemoteException ex) {
                Logger.getLogger(ComprobanteDataFacturaNotaCreditoAbstract.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else //Em este caso busco el IVA Activo
        {
            try 
            {
                //Integer ivaDefecto=Integer.parseInt(ParametrosSistemaCodefac.IVA_DEFECTO);
                //Integer ivaDefecto=catalogoProducto.getIva().getTarifa();
                impuestoIva=ServiceFactory.getFactory().getImpuestoDetalleServiceIf().buscarPorTarifa(tarifaIva);
            } catch (ServicioCodefacException ex) {
                Logger.getLogger(ComprobanteDataFacturaNotaCreditoAbstract.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RemoteException ex) {
                Logger.getLogger(ComprobanteDataFacturaNotaCreditoAbstract.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        List<ImpuestoComprobante> listaComprobantes = new ArrayList<ImpuestoComprobante>();
        ImpuestoComprobante impuesto = new ImpuestoComprobante();
        impuesto.setCodigo(impuestoIva.getImpuesto().getCodigoSri());
        impuesto.setCodigoPorcentaje(impuestoIva.getCodigo() + "");
        impuesto.setTarifa(new BigDecimal(impuestoIva.getTarifa() + ""));
        impuesto.setBaseImponible(totalSinImpuestosConIce);
        
        //Obtengo nuevamente el iva calculado por que necesito todos los decimales para tener el valor exacto y en la base de datos esta grabado solo con 2 decimales y eso puede generar problemas
        //TODO: Analizar si tengo que mandar este dato del valor redondeado o del valor origina
        impuesto.setValor(ivaRecalculado);
        System.out.println("valor: " + impuesto.getValor());
        
        /**
         * Redondedo los impuestos despues de hacer los calculos por que el Sri
         * solo acepta con 2 decimales
         */
        impuesto.setValor(impuesto.getValor().setScale(2, RoundingMode.HALF_UP));
        impuesto.setBaseImponible(impuesto.getBaseImponible().setScale(2, RoundingMode.HALF_UP));

        listaComprobantes.add(impuesto);
        
        /**
         * Agregando el valor del ICE
         */
        if (iceImpuesto != null) {
            ImpuestoComprobante impuestoIce = new ImpuestoComprobante();
            impuestoIce.setCodigo(iceImpuesto.getImpuesto().getCodigoSri());
            impuestoIce.setCodigoPorcentaje(iceImpuesto.getCodigo() + "");
            impuestoIce.setTarifa(new BigDecimal(iceImpuesto.getPorcentaje() + ""));
            impuestoIce.setBaseImponible(total.setScale(2, RoundingMode.HALF_UP));
            impuestoIce.setValor(valorIce.setScale(2, RoundingMode.HALF_UP));
            //sumarizarTotalesImpuestos(mapTotalImpuestos, catalogoProducto.getIce(), impuestoIce);
            listaComprobantes.add(impuestoIce);

        }
        
        return listaComprobantes;
        
    }
   
   
    
    public SriIdentificacion getSriIdentificacion(ComprobanteVentaNotaCreditoAbstract comprobante)
    {
        SriIdentificacionServiceIf servicioSri = ServiceFactory.getFactory().getSriIdentificacionServiceIf();
        SriIdentificacion sriIdentificacion = null;
        try {
            sriIdentificacion = servicioSri.obtenerPorTransaccionEIdentificacion(comprobante.getCliente().getTipoIdentificacionEnum(), SriIdentificacion.tipoTransaccionEnum.VENTA);
            return sriIdentificacion;
        } catch (RemoteException ex) {
            Logger.getLogger(ComprobanteDataFacturaNotaCreditoAbstract.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    protected void redondearImpuestosTotales(List<TotalImpuesto> totalesList)
    {
        for (TotalImpuesto totalImpuesto : totalesList) {
            totalImpuesto.setBaseImponible(totalImpuesto.getBaseImponible().setScale(2,BigDecimal.ROUND_HALF_UP));
            totalImpuesto.setValor(totalImpuesto.getValor().setScale(2,BigDecimal.ROUND_HALF_UP));
            
        }
        
    }
    
    //TODO:Mejorar esta parte para que funcione para cualaquier iva
    public List<TotalImpuesto> crearImpuestosTotales(ComprobanteVentaNotaCreditoAbstract comprobante)
    {
        List<TotalImpuesto> totalImpuestos = new ArrayList<TotalImpuesto>();
        try {
            //Consultar una lista de todos los impuestos disponibles
            Map<Integer,ImpuestoDetalle> mapImpuestoDetalle=ServiceFactory.getFactory().getImpuestoDetalleServiceIf().obtenerTodosMap();
            
            //Buscar los tipos de impuestos disponibles en los detalles para los totales
            Boolean impuestoDoce=false;
            Boolean impuestoCero=false;
            Boolean impuestoOcho=false;
            
            List<DetalleFacturaNotaCeditoAbstract> detalles= comprobante.getDetallesComprobante();
            for (DetalleFacturaNotaCeditoAbstract detalle : detalles) 
            {
                if(detalle.getCatalogoProducto()!=null)
                {
                    if(detalle.getCatalogoProducto().getIva().getTarifa()==12)
                    {
                        impuestoDoce=true;
                    }
                    
                    if(detalle.getCatalogoProducto().getIva().getTarifa()==8)
                    {
                        impuestoOcho=true;
                    }
                    
                    if(detalle.getCatalogoProducto().getIva().getTarifa()==0)
                    {
                        impuestoCero=true;
                    }
                }
                else
                {
                    if(detalle.getIvaPorcentaje()==12)
                    {
                        impuestoDoce=true;
                    }
                    
                    if(detalle.getIvaPorcentaje()==8)
                    {
                        impuestoOcho=true;
                    }
                    
                    if(detalle.getIvaPorcentaje()==0)
                    {
                        impuestoCero=true;
                    }
                }
                
                
            }
            
            //Crear el IMPUESTO DEL IVA_CERO cuando exista esa clasificacion
            
            //if(comprobante.getSubtotalSinImpuestos().compareTo(BigDecimal.ZERO)>0)
            if(impuestoCero)
            {
                ImpuestoDetalle impuestoDetalleIvaCero=mapImpuestoDetalle.get(ImpuestoDetalle.CODIGO_IVA_CERO);
                TotalImpuesto totalImpuestoIva=new TotalImpuesto();
                totalImpuestoIva.setBaseImponible(comprobante.getSubtotalSinImpuestosMenosDescuento());
                totalImpuestoIva.setCodigo(impuestoDetalleIvaCero.getImpuesto().getCodigoSri());
                totalImpuestoIva.setCodigoPorcentaje(impuestoDetalleIvaCero.getCodigo()+"");
                //totalImpuestoIva.setDescuentoAdicional(descuentoAdicional);
                if(!comprobante.getCodigoDocumentoEnum().equals(DocumentoEnum.NOTA_CREDITO))
                {
                    totalImpuestoIva.setTarifa(new BigDecimal(impuestoDetalleIvaCero.getTarifa()+""));
                }
                
                totalImpuestoIva.setValor(BigDecimal.ZERO);
                totalImpuestos.add(totalImpuestoIva);
            }
            
           
            //Crear el IMPUESTO DEL IVA cuando exista
            //if(comprobante.getIva().compareTo(BigDecimal.ZERO)>0)
            if(impuestoDoce || impuestoOcho)
            {   
                ImpuestoDetalle impuestoDetalleIva=null;
                if(impuestoDoce)
                {
                    impuestoDetalleIva=mapImpuestoDetalle.get(ImpuestoDetalle.CODIGO_IVA_DOCE);
                    
                }else if(impuestoOcho)
                {
                    impuestoDetalleIva=mapImpuestoDetalle.get(ImpuestoDetalle.CODIGO_IVA_OCHO);
                    
                }
                
                
                TotalImpuesto totalImpuestoIva=new TotalImpuesto();
                totalImpuestoIva.setBaseImponible(comprobante.getSubtotalImpuestosMenosDescuento());
                totalImpuestoIva.setCodigo(impuestoDetalleIva.getImpuesto().getCodigoSri());
                totalImpuestoIva.setCodigoPorcentaje(impuestoDetalleIva.getCodigo()+"");
                //totalImpuestoIva.setDescuentoAdicional(descuentoAdicional);
                if(!comprobante.getCodigoDocumentoEnum().equals(DocumentoEnum.NOTA_CREDITO))
                {
                    totalImpuestoIva.setTarifa(new BigDecimal(impuestoDetalleIva.getTarifa()+""));
                }
                
                totalImpuestoIva.setValor(comprobante.getIva());
                totalImpuestos.add(totalImpuestoIva);
            }
            
            //Crear el IMPUESTO DEL ICE cuando exista
            if(comprobante.getIce().compareTo(BigDecimal.ZERO)>0)
            {
                Map<ImpuestoDetalle,List<DetalleFacturaNotaCeditoAbstract>> mapResultado=comprobante.obtenerIceMap();
                
                for (Map.Entry<ImpuestoDetalle, List<DetalleFacturaNotaCeditoAbstract>> entry : mapResultado.entrySet()) {
                    ImpuestoDetalle impuestoDetalle = entry.getKey();
                    List<DetalleFacturaNotaCeditoAbstract> detalleList = entry.getValue();
                    
                    TotalImpuesto totalImpuesto=generarTotalImpuestoIce(impuestoDetalle, detalleList);
                    totalImpuestos.add(totalImpuesto);
                    
                }                
            }
            
            
        } catch (RemoteException ex) {
            Logger.getLogger(ComprobanteDataFacturaNotaCreditoAbstract.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ComprobanteDataFacturaNotaCreditoAbstract.class.getName()).log(Level.SEVERE, null, ex);
        }
        redondearImpuestosTotales(totalImpuestos);
        return totalImpuestos;
    } 
    
    private TotalImpuesto generarTotalImpuestoIce(ImpuestoDetalle impuestoDetalle,List<DetalleFacturaNotaCeditoAbstract> detalleList)
    {
        BigDecimal valorTotal=BigDecimal.ZERO;
        //TODO: Verificar que no necesariamente puede ser la base imponible el subtotal , parece que el subtotal es incluido el iva
        BigDecimal baseImponible=BigDecimal.ZERO;
        for (DetalleFacturaNotaCeditoAbstract detalle : detalleList) 
        {
            valorTotal=valorTotal.add(detalle.getValorIce());
            baseImponible=baseImponible.add(detalle.getCalcularTotalDetalleConTodosDecimales());
        }
        
        TotalImpuesto totalImpuestoIva = new TotalImpuesto();
        totalImpuestoIva.setBaseImponible(baseImponible);
        totalImpuestoIva.setCodigo(impuestoDetalle.getImpuesto().getCodigoSri());
        totalImpuestoIva.setCodigoPorcentaje(impuestoDetalle.getCodigo()+"");
        //totalImpuestoIva.setDescuentoAdicional(descuentoAdicional);
        totalImpuestoIva.setTarifa(impuestoDetalle.getPorcentaje());
        totalImpuestoIva.setValor(valorTotal);
        return totalImpuestoIva;
    }
    
    public interface InfoComprobanteInterface
    {
        public void setFechaEmision(String fechaEmision);
        public void setDirEstablecimiento(String dirEstablecimiento);
        public void setIdentificacion(String identificacion);
        public void setObligadoContabilidad(String obligadoContabilidad);
        public void setContribuyenteEspecial(String contribuyenteEspecial);
        public void setTipoIdentificacion(String tipoIdentificacion);
        
        
        
    }
}
