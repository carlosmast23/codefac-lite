/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.entity;

import ec.com.codesoft.codefaclite.servidor.entity.enumerados.TipoDocumentoEnum;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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

    /**
     * ID del documento que hacer e
     */
    @Column(name = "REFERENCIA_DOCUMENTO_ID")
    private Long referenciaDocumentoId;
    
    
    /**
     * Variable para saber si el ingreso es positivo o negativo en el cardex
     */
    @Column(name = "CODIGO_TIPO_DOCUMENTO")
    private String codigoTipoDocumento;
    
    @Column(name = "CANTIDAD")
    private Integer cantidad;
    
    @Column(name = "PRECIO_UNITARIO")
    private BigDecimal precioUnitario;
    
    @Column(name = "PRECIO_TOTAL")
    private BigDecimal precioTotal;
    

    @JoinColumn(name = "KARDEX_ID")
    @ManyToOne
    private Kardex kardex;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "kardexDetalle")
    private List<KardexItemEspecifico> detallesEspecificos;
    

    public KardexDetalle() {
    }
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReferenciaDocumentoId() {
        return referenciaDocumentoId;
    }

    public void setReferenciaDocumentoId(Long referenciaDocumentoId) {
        this.referenciaDocumentoId = referenciaDocumentoId;
    }


    public String getCodigoTipoDocumento() {
        return codigoTipoDocumento;
    }

    public void setCodigoTipoDocumento(String codigoTipoDocumento) {
        this.codigoTipoDocumento = codigoTipoDocumento;
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

    public List<KardexItemEspecifico> getDetallesEspecificos() {
        return detallesEspecificos;
    }

    public void setDetallesEspecificos(List<KardexItemEspecifico> detallesEspecificos) {
        this.detallesEspecificos = detallesEspecificos;
    }
    
    
    
        /**
     * Aregando datos adicionales de los item de los kardex
     */
    public void addDetalle(KardexItemEspecifico detalle)
    {
        if(this.detallesEspecificos==null)
        {
            this.detallesEspecificos=new ArrayList<KardexItemEspecifico>();
        }
        detalle.setKardexDetalle(this);
        this.detallesEspecificos.add(detalle);
        
    }
    
    public TipoDocumentoEnum getCodigoTipoDocumentoEnum()
    {
        return TipoDocumentoEnum.obtenerTipoDocumentoPorCodigo(codigoTipoDocumento);
    }
    
}
