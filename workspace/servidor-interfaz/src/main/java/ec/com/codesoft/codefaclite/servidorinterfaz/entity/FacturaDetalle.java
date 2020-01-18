/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import com.sun.imageio.plugins.common.BogusColorSpace;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.utilidades.validadores.UtilidadBigDecimal;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import javax.inject.Singleton;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "FACTURA_DETALLE")
public class FacturaDetalle implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //@Column(name = "PRODUCTO_ID")
    //private Long productoId;
    @Column(name = "CANTIDAD")
    private BigDecimal cantidad;
    @Column(name = "PRECIO_UNITARIO")
    private BigDecimal precioUnitario;
    @Column(name = "DESCUENTO")
    private BigDecimal descuento;
    @Column(name = "VALOR_ICE")
    private BigDecimal valorIce;

    @Column(name = "DESCRIPCION", length = 150)
    private String descripcion;

    /**
     * El total del detalle corresonde a la siguiente formular
     * Total=cantidad*valorUnitario-descuento
     */
    @Column(name = "TOTAL")
    private BigDecimal total;
    @Column(name = "IVA")
    private BigDecimal iva;

    @Column(name = "IVA_PORCENTAJE")
    private Integer ivaPorcentaje;

    @Column(name = "ICE_PORCENTAJE")
    private BigDecimal icePorcentaje;

    @JoinColumn(name = "FACTURA_ID")
    @ManyToOne(optional = false)
    private Factura factura;

    @Column(name = "REFERENCIA_ID")
    private Long referenciaId;

    @Column(name = "TIPO_REFERENCIA")
    private String tipoDocumento;

    public FacturaDetalle() {
        valorIce=BigDecimal.ZERO;
        icePorcentaje=BigDecimal.ZERO;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public BigDecimal getValorIce() {
        return valorIce;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public void setValorIce(BigDecimal valorIce) {
        this.valorIce = valorIce;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public Long getReferenciaId() {
        return referenciaId;
    }

    public void setReferenciaId(Long referenciaId) {
        this.referenciaId = referenciaId;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public Integer getIvaPorcentaje() {
        return ivaPorcentaje;
    }

    public void setIvaPorcentaje(Integer ivaPorcentaje) {
        this.ivaPorcentaje = ivaPorcentaje;
    }

    public BigDecimal getIcePorcentaje() {
        return icePorcentaje;
    }

    public void setIcePorcentaje(BigDecimal icePorcentaje) {
        this.icePorcentaje = icePorcentaje;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FacturaDetalle other = (FacturaDetalle) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    /**
     * @deprecated Porque deberia trabajar directamente con el porcentjae como en el metodo de abajo
     * ====================> METODOS PERSONALIZADOS <===================== *
     */
    public void calcularValorIce(BigDecimal porcentajeIce) {
        if(porcentajeIce.compareTo(BigDecimal.ZERO)>0)
        {
            this.icePorcentaje=porcentajeIce;
            this.valorIce=UtilidadBigDecimal.calcularValorPorcentaje(porcentajeIce, getSubtotalSinDescuentos(),5).setScale(2, RoundingMode.HALF_UP);        
        }
    }
    
    public void calcularValorIce() 
    {
        calcularValorIce(icePorcentaje);
    }

    
    public void calcularTotalDetalle() {
        BigDecimal setTotal = getCantidad().multiply(getPrecioUnitario()).subtract(getDescuento());
        total = setTotal.setScale(2, BigDecimal.ROUND_HALF_UP);
    }
    
    public BigDecimal totalSinImpuestosConRise()
    {
        return total.add(valorIce);
    }

    public void calculaIva() {
        //Verifico que el valor del ice no cea null para no tener errores
        //TODO: Mejorar esta parte de setear el valor del Ice
        if(valorIce==null)
        {
            valorIce=BigDecimal.ZERO;
        }
        
        BigDecimal valorIvaDecimal = new BigDecimal(ivaPorcentaje.toString()).divide(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP);
        iva = getTotal().add(valorIce).multiply(valorIvaDecimal).setScale(2, BigDecimal.ROUND_HALF_UP); //Para calcular el iva tomo en cuenta el vlaor del Ice
    }

    public TipoDocumentoEnum getTipoDocumentoEnum() {
        return TipoDocumentoEnum.obtenerTipoDocumentoPorCodigo(this.tipoDocumento);
    }

    public void setTipoDocumentoEnum(TipoDocumentoEnum tipoDocumentoEnum) {
        this.tipoDocumento = tipoDocumentoEnum.getCodigo();
    }

    /**
     * Metodos personalizados
     *
     * @return
     */
    public BigDecimal getSubtotalSinDescuentos() {
        return precioUnitario.multiply(cantidad);
    }

    /**
     * Metodos que devuelve el subtotal restado impuestos
     *
     * @return
     */
    public BigDecimal getSubtotalRestadoDescuentos() {
        return precioUnitario.multiply(cantidad).subtract(descuento);
    }

}
