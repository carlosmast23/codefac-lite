/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos;

import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteEnum;
import ec.com.codesoft.codefaclite.facturacionelectronica.evento.ListenerComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.factura.DetalleFacturaComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.factura.FacturaComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.factura.FormaPagoComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.factura.InformacionFactura;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general.ImpuestoComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general.TotalImpuesto;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.util.ComprobantesElectronicosUtil;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FormaPago;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ImpuestoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriIdentificacion;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroEstudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoReferenciaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ProductoServiceIf;
import ec.com.codesoft.ejemplo.utilidades.texto.UtilidadesTextos;
import java.io.Serializable;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class ComprobanteDataFactura implements ComprobanteDataInterface,Serializable{
    
    private Factura factura;
    private Map<String,String> mapInfoAdicional;
    private List<String> correosAdicionales;   
    private ListenerComprobanteElectronico listener;
    private Integer secuencial;
    //private List<FormaPago> formaPagos;

    public ComprobanteDataFactura(Factura factura) {
        String secuencialStr = factura.getSecuencial().toString();
        secuencialStr=UtilidadesTextos.llenarCarateresIzquierda(secuencialStr, 9, "0");
        secuencial=Integer.parseInt(secuencialStr);
        
        this.factura = factura;
    }

    public ComprobanteDataFactura(Factura factura, Integer secuencial) {
        this.factura = factura;
        this.secuencial = secuencial;
    }
    
    
    

    @Override
    public String getCodigoComprobante() {
        return ComprobanteEnum.FACTURA.getCodigo();
    }
    
        @Override
    public String getSecuencial() {
        return UtilidadesTextos.llenarCarateresIzquierda(factura.getSecuencial()+"",9,"0");
    }

    @Override
    public FacturaComprobante getComprobante() {
        FacturaComprobante facturaComprobante=new FacturaComprobante();
        
        InformacionFactura informacionFactura=new InformacionFactura();
        
        informacionFactura.setFechaEmision(ComprobantesElectronicosUtil.dateToString(factura.getFechaFactura()));
        
        if(factura.getCliente().getSriTipoIdentificacion().getCodigo().equals(SriIdentificacion.CEDULA_IDENTIFICACION))
            informacionFactura.setIdentificacionComprador(factura.getCliente().getIdentificacion());
        else
            informacionFactura.setIdentificacionComprador(UtilidadesTextos.llenarCarateresDerecha(factura.getCliente().getIdentificacion(), 13, "0"));
            
        informacionFactura.setImporteTotal(factura.getTotal());
        //Falta manejar este campo al momento de guardar
        informacionFactura.setRazonSocialComprador(factura.getCliente().getRazonSocial());
        //informacionFactura.setRazonSocialComprador(factura.getCliente().getRazonSocial());
        informacionFactura.setTipoIdentificacionComprador(factura.getCliente().getSriTipoIdentificacion().getCodigo());
        
        BigDecimal descuentoTotal=factura.getDescuentoImpuestos().add(factura.getDescuentoSinImpuestos());
        informacionFactura.setTotalDescuento(descuentoTotal);
        
         BigDecimal subtotalTotalSinDescuentos=factura.getSubtotalImpuestos().add(factura.getSubtotalSinImpuestos());
        //Esta variable se refiere al subtotal antes de impuesto y menos los descuentos       
        informacionFactura.setTotalSinImpuestos(subtotalTotalSinDescuentos.subtract(descuentoTotal));
        /**
         * Aqui hay que setear los valores de la base de datos
         */
        informacionFactura.setObligadoContabilidad("NO");
        //informacionFactura.setTotalImpuestos(totalImpuestos);
        
        /**
         * Grabar las formas de pago si la variable exise y es distinto de vacio
         */
        if(factura.getFormaPagos()!=null && factura.getFormaPagos().size()>0)
        {
            List<FormaPagoComprobante> formaPagosFactura=new ArrayList<FormaPagoComprobante>();
            for (FormaPago formaPago : factura.getFormaPagos()) {
               FormaPagoComprobante formaPagoComprobante=new FormaPagoComprobante();
               formaPagoComprobante.setFormaPago(formaPago.getSriFormaPago().getCodigo());
               formaPagoComprobante.setPlazo(new BigDecimal(formaPago.getPlazo()+""));
               formaPagoComprobante.setTotal(formaPago.getTotal());
               formaPagoComprobante.setUnidadTiempo(formaPago.getUnidadTiempo());
               formaPagosFactura.add(formaPagoComprobante);
            }
            informacionFactura.setFormaPagos(formaPagosFactura);
        }
        
        
        /**
         * Total con impuestos
         */
        Map<ImpuestoDetalle,TotalImpuesto> mapTotalImpuestos=new HashMap<ImpuestoDetalle,TotalImpuesto>();
        
        
        /**
         * Informacion de los detalles
         */
        List<DetalleFacturaComprobante> detallesComprobante=new ArrayList<DetalleFacturaComprobante>();
        List<FacturaDetalle> detallesFactura= factura.getDetalles();
        
        for (FacturaDetalle facturaDetalle : detallesFactura) {
            try {
                DetalleFacturaComprobante detalle=new DetalleFacturaComprobante();
                
                CatalogoProducto catalogoProducto=null;
                if(facturaDetalle.getTipoDocumento()!=null && facturaDetalle.getTipoDocumentoEnum().equals(TipoDocumentoEnum.ACADEMICO))
                {
                    RubroEstudiante rubroEstudiante=ServiceFactory.getFactory().getRubroEstudianteServiceIf().buscarPorId(facturaDetalle.getReferenciaId());
                    catalogoProducto=rubroEstudiante.getRubroNivel().getCatalogoProducto();
                    detalle.setCodigoPrincipal(rubroEstudiante.getId()+"");
                }
                else
                {
                    Producto producto=ServiceFactory.getFactory().getProductoServiceIf().buscarPorId(facturaDetalle.getReferenciaId());
                    catalogoProducto=producto.getCatalogoProducto();
                    detalle.setCodigoPrincipal(producto.getCodigoPersonalizado());
                }
                
                //detalle.setCodigoPrincipal(producto.getCodigoPersonalizado());
                detalle.setCantidad(facturaDetalle.getCantidad());
                detalle.setDescripcion(facturaDetalle.getDescripcion());
                //Establecer el descuento en el aplicativo
                detalle.setDescuento(facturaDetalle.getDescuento());
                detalle.setPrecioTotalSinImpuesto(facturaDetalle.getTotal());
                detalle.setPrecioUnitario(facturaDetalle.getPrecioUnitario());
                
                
                //facturaDetalle.getProducto().get
                
                /**
                 * Agregado impuesto que se cobran a cada detalle individual
                 */
                List<ImpuestoComprobante> listaComprobantes=new ArrayList<ImpuestoComprobante>();
                
                ImpuestoComprobante impuesto=new ImpuestoComprobante();
                impuesto.setCodigo(catalogoProducto.getIva().getImpuesto().getCodigoSri());
                impuesto.setCodigoPorcentaje(catalogoProducto.getIva().getCodigo()+"");
                impuesto.setTarifa(new BigDecimal(catalogoProducto.getIva().getTarifa()+""));
                impuesto.setBaseImponible(facturaDetalle.getTotal());
                impuesto.setValor(facturaDetalle.getIva());
                
                /**
                 * Verificar valores para el total de impuesto
                 */
                if(mapTotalImpuestos.get(catalogoProducto.getIva())==null)
                {
                    TotalImpuesto totalImpuesto=new TotalImpuesto();
                    totalImpuesto.setBaseImponible(impuesto.getBaseImponible());
                    totalImpuesto.setCodigo(impuesto.getCodigo());
                    totalImpuesto.setCodigoPorcentaje(impuesto.getCodigoPorcentaje());
                    totalImpuesto.setValor(impuesto.getValor());
                    //totalImpuesto.setDescuentoAdicional(detalle.getDescuento().toString());
                    mapTotalImpuestos.put(catalogoProducto.getIva(), totalImpuesto);
                }
                else
                {
                    TotalImpuesto totalImpuesto=mapTotalImpuestos.get(catalogoProducto.getIva());
                    totalImpuesto.setBaseImponible(totalImpuesto.getBaseImponible().add(impuesto.getBaseImponible()));
                    totalImpuesto.setValor(totalImpuesto.getValor().add(impuesto.getValor()));
                    //totalImpuesto.setDescuentoAdicional(detalle.getDescuento().toString());
                    mapTotalImpuestos.put(catalogoProducto.getIva(), totalImpuesto);
                    
                }
                
                //-------------> FIN <----------------
                listaComprobantes.add(impuesto);
                
                detalle.setImpuestos(listaComprobantes);
                
                detallesComprobante.add(detalle);
            } catch (RemoteException ex) {
                Logger.getLogger(ComprobanteDataFactura.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        facturaComprobante.setDetalles(detallesComprobante);
        facturaComprobante.setInformacionFactura(informacionFactura);
        
        /**
         * Crear los impuestos totales
         */
        List<TotalImpuesto> totalImpuestos = new ArrayList<TotalImpuesto>();
        for (Map.Entry<ImpuestoDetalle, TotalImpuesto> entry : mapTotalImpuestos.entrySet()) {
            ImpuestoDetalle key = entry.getKey();
            TotalImpuesto value = entry.getValue();
            totalImpuestos.add(value);
        }
        facturaComprobante.getInformacionFactura().setTotalImpuestos(totalImpuestos);
        
        /**
         * Informacion adicional
         */
        facturaComprobante.setCorreos(getCorreos());
                
                
        return facturaComprobante;
    }

    @Override
    public Map<String, String> getMapAdicional() {
        return mapInfoAdicional;
    }

    public void setMapInfoAdicional(Map<String, String> mapInfoAdicional) {
        this.mapInfoAdicional = mapInfoAdicional;
    }

    @Override
    public List<String> getCorreos() {
        /*
        List<String> correos=new ArrayList<String>();
        if(factura!=null && factura.getCliente()!=null)
            correos.add(factura.getCliente().getCorreoElectronico());
        
        //Agregar correos adicionales , solo si estan seteados los valores de los correos       
        if(this.correosAdicionales!=null)
        {
            for (String correo : this.correosAdicionales) {
                if(!correos.contains(correo))
                {
                    correos.add(correo);
                }
            }
        }
        
        */
        return new ArrayList<String>(); //TODO: Verificar si se deben usar estos campos porque ya se envian los correos desde la informacion adicional
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;        
    }

    public List<String> getCorreosAdicionales() {
        return correosAdicionales;
    }

    public void setCorreosAdicionales(List<String> correosAdicionales) {
        this.correosAdicionales = correosAdicionales;
    }

    @Override
    public ListenerComprobanteElectronico getListenerComprobanteElectronico() {
        return listener;
    }

    public void setListener(ListenerComprobanteElectronico listener) {
        this.listener = listener;
    }

    @Override
    public Long getComprobanteId() {
        return this.factura.getId();
    }
   
    
    
}
