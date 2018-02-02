/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.entity;

import java.math.BigDecimal;
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
@Table(name = "KARDEX_DETALLE")
public class KardexDetalle {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "DOCUMENTO_AFECTA_ID")
    private Long documenoAfectaId;
    
    @Column(name = "CODIGO_DOCUMENTO")
    private String codigoDocumento;
    
    /**
     * Variable para saber si el ingreso es positivo o negativo en el cardex
     */
    @Column(name = "TIPO_MOVIMIENTO")
    private String tipoMovimiento;
    
    @Column(name = "CANTIDAD")
    private Integer cantidad;
    
    @Column(name = "PRECIO_UNITARIO")
    private BigDecimal precioUnitario;
    
    @Column(name = "PRECIO_TOTAL")
    private BigDecimal precioTotal;
    

    @JoinColumn(name = "KARDEX_ID")
    @ManyToOne
    private Kardex kardex;

    public KardexDetalle() {
    }
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDocumenoAfectaId() {
        return documenoAfectaId;
    }

    public void setDocumenoAfectaId(Long documenoAfectaId) {
        this.documenoAfectaId = documenoAfectaId;
    }

    public String getCodigoDocumento() {
        return codigoDocumento;
    }

    public void setCodigoDocumento(String codigoDocumento) {
        this.codigoDocumento = codigoDocumento;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public BigDecimal getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(BigDecimal precioTotal) {
        this.precioTotal = precioTotal;
    }

    public Kardex getKardex() {
        return kardex;
    }

    public void setKardex(Kardex kardex) {
        this.kardex = kardex;
    }
    
    
}
