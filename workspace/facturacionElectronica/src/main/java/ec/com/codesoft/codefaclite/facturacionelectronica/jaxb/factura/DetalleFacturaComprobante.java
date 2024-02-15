/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.factura;

import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.DetalleComprobanteAbstract;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general.ImpuestoComprobante;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Carlos
 */
@XmlType(propOrder = {
    "codigoPrincipal",
    "descripcion",
    "unidadMedida",
    "cantidad",
    "precioUnitario",
    "precioSinSubsidio",
    "descuento",
    "precioTotalSinImpuesto",
    "impuestos"})
public class DetalleFacturaComprobante extends DetalleComprobanteAbstract {

    private String descripcion;
    
    private String unidadMedida;
    //private BigDecimal cantidad;
    //private BigDecimal precioUnitario;
    //private BigDecimal descuento;
    //Precio Unitario*Cantidad-Descuento
    private BigDecimal precioTotalSinImpuesto;

    private BigDecimal precioSinSubsidio;

    private String codigoPrincipal;

    //@XmlElementWrapper(name = "formasDePago")
    //@XmlElement(name = "formaPago")
    private List<ImpuestoComprobante> impuestos;

    public DetalleFacturaComprobante() {
    }

    @XmlElement(name = "descripcion")
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlElement(name = "unidadMedida")
    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }
    

    @XmlElement(name = "precioTotalSinImpuesto")
    public BigDecimal getPrecioTotalSinImpuesto() {
        return precioTotalSinImpuesto;
    }

    public void setPrecioTotalSinImpuesto(BigDecimal precioTotalSinImpuesto) {
        this.precioTotalSinImpuesto = precioTotalSinImpuesto;
    }

    @XmlElementWrapper(name = "impuestos")
    @XmlElement(name = "impuesto")
    public List<ImpuestoComprobante> getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(List<ImpuestoComprobante> impuestos) {
        this.impuestos = impuestos;
    }

    @XmlElement(name = "codigoPrincipal")
    public String getCodigoPrincipal() {
        return codigoPrincipal;
    }

    public void setCodigoPrincipal(String codigoPrincipal) {
        this.codigoPrincipal = codigoPrincipal;
    }

    @XmlElement(name = "precioSinSubsidio")
    public BigDecimal getPrecioSinSubsidio() {
        return precioSinSubsidio;
    }

    public void setPrecioSinSubsidio(BigDecimal precioSinSubsidio) {
        this.precioSinSubsidio = precioSinSubsidio;
    }
    
    public ImpuestoComprobante obtenerIvaDetalle()
    {
        if(impuestos!=null)
        {
            for (ImpuestoComprobante impuesto : impuestos) 
            {
                if(impuesto.getCodigo().equals("2"))
                {
                    return impuesto;
                }
            }
        }
        return null;
    }
    
    @Deprecated 
    public BigDecimal obtenerIvaPorcentaje()
    {
        //BigDecimal resultado=BigDecimal.ZERO;
        for (ImpuestoComprobante impuesto : impuestos) 
        {
            //TODO: Parametrizar de mejor manera pero por el momento asumo que el codigo 3 es para el ICE
            if(impuesto.getCodigo().equals("2"))
            {
                return impuesto.getTarifa();
            }
        }
        return BigDecimal.ZERO;
    }
    
    @Deprecated
    public BigDecimal obtenerIvaValor() {
        //BigDecimal resultado = BigDecimal.ZERO;
        for (ImpuestoComprobante impuesto : impuestos) {
            //TODO: Parametrizar de mejor manera pero por el momento asumo que el codigo 3 es para el ICE
            if (impuesto.getCodigo().equals("2")) 
            {
                return  impuesto.getValor();
            }
        }
        return BigDecimal.ZERO;
    }
    
    @Deprecated //TODO: Mejorar esta parte
    public BigDecimal obtenerIce()
    {       
        BigDecimal resultado=BigDecimal.ZERO;
        for (ImpuestoComprobante impuesto : impuestos) 
        {
            //TODO: Parametrizar de mejor manera pero por el momento asumo que el codigo 3 es para el ICE
            if(impuesto.getCodigo().equals("3"))
            {
                resultado=impuesto.getValor();
            }
        }
        return resultado;
    }
    
    @Deprecated //TODO: Mejorar esta parte
    public BigDecimal obtenerIcePorcentaje()
    {       
        BigDecimal resultado=BigDecimal.ZERO;
        for (ImpuestoComprobante impuesto : impuestos) 
        {
            //TODO: Parametrizar de mejor manera pero por el momento asumo que el codigo 3 es para el ICE
            if(impuesto.getCodigo().equals("3"))
            {
                resultado=impuesto.getTarifa();
            }
        }
        return resultado;
    }
    
    @Deprecated //TODO: Mejorar esta parte
    public BigDecimal obtenerIRBPNRPorcentaje()
    {       
        BigDecimal resultado=BigDecimal.ZERO;
        for (ImpuestoComprobante impuesto : impuestos) 
        {
            //TODO: Parametrizar de mejor manera pero por el momento asumo que el codigo 5 es para el IRBPNR
            if(impuesto.getCodigo().equals("5"))
            {
                resultado=impuesto.getValor();
            }
        }
        return resultado;
    }

    /**
     * Metodos Adicionales
     */
    public BigDecimal calcularSubsidioDetalle() {
        
        if(getPrecioSinSubsidio()==null)
        {
            return BigDecimal.ZERO;
        }
        
        BigDecimal subsidioTotal=BigDecimal.ZERO;
        for (ImpuestoComprobante impuesto : getImpuestos()) {

            if (impuesto.getCodigo().equals("2"))//TODO: Parametriza esta valor por el momento e codigo 1 significa IVA
            {
                if (impuesto.getValor().compareTo(BigDecimal.ZERO) == 0) {
                    subsidioTotal = subsidioTotal.add(getPrecioSinSubsidio().multiply(getCantidad()).subtract(getPrecioTotalSinImpuesto())).setScale(2,BigDecimal.ROUND_HALF_UP);

                } else {
                    BigDecimal constanteImpuesto=impuesto.getTarifa().divide(new BigDecimal("100"),2,BigDecimal.ROUND_HALF_UP).add(BigDecimal.ONE);
                    BigDecimal subsidioTotalTmp=constanteImpuesto.multiply(getPrecioSinSubsidio());
                    subsidioTotalTmp=subsidioTotalTmp.multiply(getCantidad());
                    //subsidioTotal = subsidioTotalTmp.setScale(2, BigDecimal.ROUND_HALF_UP);
                    
                    //Descontar el valor del valor pagado menos el subsidio añadiendo el iva del producto
                    BigDecimal precioTotal=getPrecioTotalSinImpuesto();
                    precioTotal=precioTotal.multiply(constanteImpuesto);
                    subsidioTotal=subsidioTotalTmp.subtract(precioTotal).setScale(2, BigDecimal.ROUND_HALF_UP);

                }
            } // Solo me interesa este codigo y no el que diga ICE
        }
        return subsidioTotal;
    }

}
