/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos;

import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteEnum;
import ec.com.codesoft.codefaclite.facturacionelectronica.evento.ListenerComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general.ImpuestoComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general.TotalImpuesto;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.notacredito.DetalleNotaCreditoComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.notacredito.InformacionNotaCredito;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.notacredito.NotaCreditoComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.util.ComprobantesElectronicosUtil;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ImpuestoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.NotaCredito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.NotaCreditoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriIdentificacion;
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
public class ComprobanteDataNotaCredito implements ComprobanteDataInterface,Serializable {
    private NotaCredito notaCredito;
    private Map<String,String> mapInfoAdicional;
    private List<String> correosAdicionales;
    private ListenerComprobanteElectronico listener;
    
    public ComprobanteDataNotaCredito(NotaCredito notaCredito) {
        this.notaCredito=notaCredito;
    }

    @Override
    public String getCodigoComprobante() {
        return ComprobanteEnum.NOTA_CREDITO.getCodigo();
    }

    @Override
    public String getSecuencial() {
       // String secuencial= this.session.getParametrosCodefac().get(ParametroCodefac.SECUENCIAL_NOTA_CREDITO).getValor();
       String secuencial= notaCredito.getSecuencial().toString();       
       return UtilidadesTextos.llenarCarateresIzquierda(secuencial,9,"0");
    }

    @Override
    public Map<String, String> getMapAdicional() {
        return mapInfoAdicional;
    }

    @Override
    public List<String> getCorreos() {
        List<String> correos=new ArrayList<String>();
        if(notaCredito!=null && notaCredito.getCliente()!=null)
            correos.add(notaCredito.getCliente().getCorreoElectronico());
        
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
        return correos;
    }

    @Override
    public NotaCreditoComprobante getComprobante() {
        NotaCreditoComprobante notaCreditoComprobante=new NotaCreditoComprobante();
        InformacionNotaCredito info=new InformacionNotaCredito();
        info.setCodDocModificado(ComprobanteEnum.FACTURA.getCodigo());
        
        //Revisar este dato porque solo se debe poner cuando sea contribuyente especial
        //info.setContribuyenteEspecial(claveAcceso);
        
        info.setDirEstablecimiento(notaCredito.getCliente().getDireccion());
        info.setFechaEmision(ComprobantesElectronicosUtil.dateToString(notaCredito.getFechaNotaCredito()));
        info.setFechaEmisionDocSustento(ComprobantesElectronicosUtil.dateToString(notaCredito.getFactura().getFechaFactura()));
        
        if(notaCredito.getCliente().getTipoIdentificacion().equals(SriIdentificacion.CEDULA_IDENTIFICACION))
            info.setIdentificacionComprador(notaCredito.getCliente().getIdentificacion());
        else
            info.setIdentificacionComprador(UtilidadesTextos.llenarCarateresDerecha(notaCredito.getCliente().getIdentificacion(), 13, "0"));
        /**
         * Verificar que valor no mas acepta
         */
        info.setMoneda("DOLAR");
        
        info.setMotivo(notaCredito.getRazonModificado());
        info.setNumDocModificado(notaCredito.getFactura().getPreimpreso());
        info.setObligadoContabilidad("NO");
        info.setRazonSocialComprador(notaCredito.getCliente().getRazonSocial());
        
        /**
         * Consultar para que sirve el rise
         */
        //info.setRise(claveAcceso);
        
        info.setTipoIdentificacionComprador(notaCredito.getCliente().getTipoIdentificacion());
        //info.setTotalImpuestos(totalImpuestos);
        info.setTotalSinImpuestos(notaCredito.getSubtotalDoce().add(notaCredito.getSubtotalCero()));
        info.setValorModificacion(notaCredito.getTotal());
        
        
        Map<ImpuestoDetalle,TotalImpuesto> mapTotalImpuestos=new HashMap<ImpuestoDetalle,TotalImpuesto>();
        
                /**
         * Informacion de los detalles
         */
        List<DetalleNotaCreditoComprobante> detallesComprobante=new ArrayList<DetalleNotaCreditoComprobante>();
        List<NotaCreditoDetalle> detallesNotaCredito= notaCredito.getDetalles();
        
        for (NotaCreditoDetalle detalleNotaCredito : detallesNotaCredito) {
            try {
                Producto producto=ServiceFactory.getFactory().getProductoServiceIf().buscarPorId(detalleNotaCredito.getReferenciaId());
                DetalleNotaCreditoComprobante detalle=new DetalleNotaCreditoComprobante();
                
                detalle.setCodigoInterno(producto.getCodigoPersonalizado());
                detalle.setCantidad(detalleNotaCredito.getCantidad());
                detalle.setDescripcion(detalleNotaCredito.getDescripcion());
                //Establecer el descuento en el aplicativo
                detalle.setDescuento(BigDecimal.ZERO);
                detalle.setPrecioTotalSinImpuesto(detalleNotaCredito.getTotal());
                detalle.setPrecioUnitario(detalleNotaCredito.getPrecioUnitario());
                
                
                //facturaDetalle.getProducto().get
                
                /**
                 * Agregado impuesto que se cobran a cada detalle individual
                 */
                List<ImpuestoComprobante> listaComprobantes=new ArrayList<ImpuestoComprobante>();
                
                ImpuestoComprobante impuesto=new ImpuestoComprobante();
                impuesto.setCodigo(producto.getIva().getImpuesto().getCodigoSri());
                impuesto.setCodigoPorcentaje(producto.getIva().getCodigo()+"");
                impuesto.setTarifa(new BigDecimal(producto.getIva().getTarifa()+""));
                impuesto.setBaseImponible(detalleNotaCredito.getTotal());
                impuesto.setValor(detalleNotaCredito.getIva());
                
                /**
                 * Verificar valores para el total de impuesto
                 */
                if(mapTotalImpuestos.get(producto.getIva())==null)
                {
                    TotalImpuesto totalImpuesto=new TotalImpuesto();
                    totalImpuesto.setBaseImponible(impuesto.getBaseImponible());
                    totalImpuesto.setCodigo(impuesto.getCodigo());
                    totalImpuesto.setCodigoPorcentaje(impuesto.getCodigoPorcentaje());
                    totalImpuesto.setValor(impuesto.getValor());
                    mapTotalImpuestos.put(producto.getIva(), totalImpuesto);
                }
                else
                {
                    TotalImpuesto totalImpuesto=mapTotalImpuestos.get(producto.getIva());
                    totalImpuesto.setBaseImponible(totalImpuesto.getBaseImponible().add(impuesto.getBaseImponible()));
                    totalImpuesto.setValor(totalImpuesto.getValor().add(impuesto.getValor()));
                    mapTotalImpuestos.put(producto.getIva(), totalImpuesto);
                    
                }
                
                //-------------> FIN <----------------
                listaComprobantes.add(impuesto);
                
                detalle.setImpuestos(listaComprobantes);
                
                detallesComprobante.add(detalle);
            } catch (RemoteException ex) {
                Logger.getLogger(ComprobanteDataNotaCredito.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        notaCreditoComprobante.setDetalles(detallesComprobante);
        notaCreditoComprobante.setInfoNotaCredito(info);
        
                /**
         * Crear los impuestos totales
         */
        List<TotalImpuesto> totalImpuestos = new ArrayList<TotalImpuesto>();
        for (Map.Entry<ImpuestoDetalle, TotalImpuesto> entry : mapTotalImpuestos.entrySet()) {
            ImpuestoDetalle key = entry.getKey();
            TotalImpuesto value = entry.getValue();
            totalImpuestos.add(value);
        }
        notaCreditoComprobante.getInfoNotaCredito().setTotalImpuestos(totalImpuestos);
        
        notaCreditoComprobante.setCorreos(getCorreos());
        
        return notaCreditoComprobante;
    }

    public NotaCredito getNotaCredito() {
        return notaCredito;
    }

    public void setNotaCredito(NotaCredito notaCredito) {
        this.notaCredito = notaCredito;
    }

    public Map<String, String> getMapInfoAdicional() {
        return mapInfoAdicional;
    }

    public void setMapInfoAdicional(Map<String, String> mapInfoAdicional) {
        this.mapInfoAdicional = mapInfoAdicional;
    }

    public List<String> getCorreosAdicionales() {
        return correosAdicionales;
    }

    public void setCorreosAdicionales(List<String> correosAdicionales) {
        this.correosAdicionales = correosAdicionales;
    }

    public ListenerComprobanteElectronico getListener() {
        return listener;
    }

    public void setListener(ListenerComprobanteElectronico listener) {
        this.listener = listener;
    }
    
    

    @Override
    public ListenerComprobanteElectronico getListenerComprobanteElectronico() {
        return listener;
    }

    @Override
    public Long getComprobanteId() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
