/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vista.factura;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefacInterface;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Carlos
 */
public class FacturaModelControlador {
    
    public SessionCodefacInterface session;

    public FacturaModelControlador(SessionCodefacInterface session) {
        this.session=session;
    }
    
    
    public Map<String,Object> getMapParametrosReporte(Factura facturaProcesando)
    {
        //map de los parametros faltantes
            Map<String,Object> mapParametros=new HashMap<String, Object>();
            mapParametros.put("codigo", facturaProcesando.getPreimpreso());
            mapParametros.put("cedula", facturaProcesando.getIdentificacion());
            mapParametros.put("cliente", facturaProcesando.getRazonSocial());
            mapParametros.put("direccion", facturaProcesando.getDireccion());
            mapParametros.put("telefonos", facturaProcesando.getTelefono());
            mapParametros.put("fechaIngreso", facturaProcesando.getFechaEmision().toString());
            mapParametros.put("subtotal", facturaProcesando.getSubtotalImpuestos().add(facturaProcesando.getSubtotalSinImpuestos()).toString());
            mapParametros.put("iva", facturaProcesando.getIva().toString());
            mapParametros.put("ice", facturaProcesando.getIce().toString());
            mapParametros.put("total", facturaProcesando.getTotal().toString());
            mapParametros.put("autorizacion", facturaProcesando.getClaveAcceso());
            
            
            return mapParametros;
            
    }
    
    public List<ComprobanteVentaData> getDetalleDataReporte(Factura facturaProcesando)
    {
        List<ComprobanteVentaData> dataReporte = new ArrayList<ComprobanteVentaData>();

        for (FacturaDetalle detalle : facturaProcesando.getDetalles()) {

            ComprobanteVentaData data = new ComprobanteVentaData();
            data.setCantidad(detalle.getCantidad().toString());
            data.setCodigo(detalle.getId().toString());
            data.setNombre(detalle.getDescripcion().toString());
            data.setPrecioUnitario(detalle.getPrecioUnitario().toString());
            data.setTotal(detalle.getTotal().toString());
            
            //Datos adicionales para las proformas
            data.setDescuento(detalle.getDescuento().toString());
            data.setDescripcion(detalle.getDescripcion());

            dataReporte.add(data);
        }
        return dataReporte;
    }
    
    
    public List<DocumentoEnum>  buscarDocumentosFactura()
    {
        List<DocumentoEnum> tiposDocumento=null;
        //cuando la factura es electronica
        String letraTipoEmision=session.getParametrosCodefac().get(ParametroCodefac.TIPO_FACTURACION).valor;
        if(letraTipoEmision.equals(ComprobanteEntity.TipoEmisionEnum.ELECTRONICA.getLetra()))
        {
            ComprobanteEntity.TipoEmisionEnum tipoEmisionEnum=ComprobanteEntity.TipoEmisionEnum.getEnumByLetra(letraTipoEmision);
            
            tiposDocumento=DocumentoEnum.obtenerPorDocumentosElectronicos(ModuloCodefacEnum.FACTURACION,tipoEmisionEnum);
            
            ParametroCodefac paramCodefacNotaVenta=session.getParametrosCodefac().get(ParametroCodefac.ACTIVAR_NOTA_VENTA);
                    
            if(paramCodefacNotaVenta!=null)
            {
                EnumSiNo enumSino=EnumSiNo.getEnumByLetra(paramCodefacNotaVenta.getValor());
                if(enumSino.equals(EnumSiNo.SI))
                {
                    tiposDocumento.add(DocumentoEnum.NOTA_VENTA_INTERNA); //Todo ver si utilizar este documento para grabar o crearme otros 
                }
            }
        }
        else //Cuando la factura es fisica
        {
            tiposDocumento=DocumentoEnum.obtenerPorDocumentosFisico(ModuloCodefacEnum.FACTURACION);
        }
        
        return tiposDocumento;
    }
    
}
