/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.utilidades.validadores.UtilidadBigDecimal;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author CARLOS_CODESOFT
 */
@MappedSuperclass
public class DetalleFacturaNotaCeditoAbstract implements Serializable {

    @Column(name = "CANTIDAD")
    private BigDecimal cantidad;
    @Column(name = "PRECIO_UNITARIO")
    private BigDecimal precioUnitario;
    @Column(name = "DESCUENTO")
    private BigDecimal descuento;
    @Column(name = "VALOR_ICE")
    private BigDecimal valorIce;

    @Column(name = "TOTAL")
    private BigDecimal total;
    @Column(name = "IVA")
    private BigDecimal iva;

    @Column(name = "IVA_PORCENTAJE")
    private Integer ivaPorcentaje;

    @Column(name = "DESCRIPCION", length = 150)
    private String descripcion;

    @Column(name = "REFERENCIA_ID")
    private Long referenciaId;

    @Column(name = "TIPO_REFERENCIA")
    private String tipoDocumento;
    
    @Column(name = "CODIGO_PRINCIPAL")
    private String codigoPrincipal;
    /**
     * El total del detalle corresonde a la siguiente formular
     * Total=cantidad*valorUnitario-descuento
     */

    @Column(name = "ICE_PORCENTAJE")
    private BigDecimal icePorcentaje;

    public DetalleFacturaNotaCeditoAbstract() {
        this.valorIce = BigDecimal.ZERO;
        this.icePorcentaje = BigDecimal.ZERO;
    }

    /**
     * ======================================================== METODOS
     * PERSONALIZADOS ========================================================
     */
    public void calcularValorIce(BigDecimal porcentajeIce) {
        if (porcentajeIce.compareTo(BigDecimal.ZERO) > 0) {
            this.icePorcentaje = porcentajeIce;
            this.valorIce = UtilidadBigDecimal.calcularValorPorcentaje(porcentajeIce, getSubtotalSinDescuentos(), 5).setScale(5, RoundingMode.HALF_UP);
        }
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public BigDecimal getValorIce() {
        return valorIce;
    }

    public void setValorIce(BigDecimal valorIce) {
        this.valorIce = valorIce;
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

    public Integer getIvaPorcentaje() {
        return ivaPorcentaje;
    }

    public void setIvaPorcentaje(Integer ivaPorcentaje) {
        this.ivaPorcentaje = ivaPorcentaje;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getIcePorcentaje() {
        return icePorcentaje;
    }

    public void setIcePorcentaje(BigDecimal icePorcentaje) {
        this.icePorcentaje = icePorcentaje;
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
    
    

    public void calculaIva() {
        //Verifico que el valor del ice no cea null para no tener errores
        //TODO: Mejorar esta parte de setear el valor del Ice
        if (valorIce == null) {
            valorIce = BigDecimal.ZERO;
        }

        BigDecimal valorIvaDecimal = new BigDecimal(ivaPorcentaje.toString()).divide(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP);
        iva = getTotal().add(valorIce).multiply(valorIvaDecimal).setScale(2, BigDecimal.ROUND_HALF_UP); //Para calcular el iva tomo en cuenta el vlaor del Ice
    }

    public void calcularValorIce() {
        calcularValorIce(icePorcentaje);
    }

    public void calcularTotalDetalle() {
        BigDecimal setTotal = getCantidad().multiply(getPrecioUnitario()).subtract(getDescuento());
        total = setTotal.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public BigDecimal totalSinImpuestosConIce() {
        return total.add(valorIce);
    }

    public BigDecimal calcularTotalFinal() {
        return this.total.add(iva).add(this.valorIce);
    }

    public String getCodigoPrincipal() {
        return codigoPrincipal;
    }

    public void setCodigoPrincipal(String codigoPrincipal) {
        this.codigoPrincipal = codigoPrincipal;
    }
    
    

}
