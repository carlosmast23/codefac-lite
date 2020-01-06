/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vista.factura;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.NotaCredito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.NotaCreditoAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.NotaCreditoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Carlos
 */
public class NotaCreditoModelControlador {
    
    public void setearDatosFacturaEnNotaCredito(Factura factura,NotaCredito notaCredito)
    {
        notaCredito.setFactura(factura);
        notaCredito.setNumDocModificado(factura.getPreimpreso());
        setearDatosProveedor(factura.getCliente(),notaCredito);
        cargarDatosNotaCredito(notaCredito);
        cargarDatosAdicionales(notaCredito);
        //cargarTablaDatosAdicionales();
        //mostrarDatosNotaCredito();
    }
    
    private void cargarDatosNotaCredito(NotaCredito notaCredito) {
        
        /**
         * Setear datos de la factura a la nota de credito
         */
        notaCredito.setTotal(notaCredito.getFactura().getTotal());
        notaCredito.setValorIvaDoce(notaCredito.getFactura().getIva());
        notaCredito.setSubtotalCero(notaCredito.getFactura().getSubtotalSinImpuestos());
        notaCredito.setSubtotalDoce(notaCredito.getFactura().getSubtotalImpuestos());
        notaCredito.setCliente(notaCredito.getFactura().getCliente());
        notaCredito.setDescuentoImpuestos(notaCredito.getFactura().getDescuentoImpuestos());
        notaCredito.setDescuentoSinImpuestos(notaCredito.getFactura().getDescuentoSinImpuestos());
        
        /**
         * CargarDetallesNotaCredito
         */
        List<FacturaDetalle> detallesFactura = notaCredito.getFactura().getDetalles();
        if(notaCredito.getDetalles()!=null)
            notaCredito.getDetalles().clear();
        
        for (FacturaDetalle facturaDetalle : detallesFactura) {
            NotaCreditoDetalle notaDetalle = new NotaCreditoDetalle();
            notaDetalle.setCantidad(facturaDetalle.getCantidad());
            notaDetalle.setDescripcion(facturaDetalle.getDescripcion());
            //System.out.println(facturaDetalle.getDescuento());
            notaDetalle.setDescuento(facturaDetalle.getDescuento());
            notaDetalle.setIva(facturaDetalle.getIva());
            notaDetalle.setPrecioUnitario(facturaDetalle.getPrecioUnitario());
            notaDetalle.setReferenciaId(facturaDetalle.getReferenciaId());
            notaDetalle.setTipoReferencia(facturaDetalle.getTipoDocumento());
            notaDetalle.setTotal(facturaDetalle.getTotal());
            notaDetalle.setValorIce(facturaDetalle.getValorIce());
            notaDetalle.setIvaPorcentaje(facturaDetalle.getIvaPorcentaje());

            notaCredito.addDetalle(notaDetalle);
        }
        
      
        
    }

    
    public void setearDatosProveedor(Persona proveedor,NotaCredito notaCredito)
    {
        notaCredito.setCliente(proveedor);
        notaCredito.setTelefono(proveedor.getEstablecimientos().get(0).getTelefonoConvencional());
        notaCredito.setDireccion(proveedor.getEstablecimientos().get(0).getDireccion());
        notaCredito.setRazonSocial(proveedor.getRazonSocial());
    }
    
    private void cargarDatosAdicionales(NotaCredito notaCredito)
    {
        //Si vuelve a escoger otra factura se borran los datos adicionales
         if(notaCredito.getDatosAdicionales()!=null)
            notaCredito.getDatosAdicionales().clear();
        
         List<FacturaAdicional> datosAdicional=notaCredito.getFactura().getDatosAdicionales();
         if(datosAdicional!=null)
         {
             List<NotaCreditoAdicional> datosAdicionalNotaCredito=new ArrayList<NotaCreditoAdicional>();
             for (FacturaAdicional facturaDetalle : datosAdicional) {
                 NotaCreditoAdicional notaCreditoAdicional=new NotaCreditoAdicional();
                 notaCreditoAdicional.setCampo(facturaDetalle.getCampo());
                 notaCreditoAdicional.setNotaCredito(notaCredito);
                 notaCreditoAdicional.setNumero(facturaDetalle.getNumero());
                 notaCreditoAdicional.setTipo(facturaDetalle.getTipo());
                 notaCreditoAdicional.setValor(facturaDetalle.getValor());
                 datosAdicionalNotaCredito.add(notaCreditoAdicional);
             }
             notaCredito.setDatosAdicionales(datosAdicionalNotaCredito);
         }

    }
}
