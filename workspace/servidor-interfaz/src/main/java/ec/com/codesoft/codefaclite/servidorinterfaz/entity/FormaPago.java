/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import java.io.Serializable;
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
@Table(name = "FACTURA_FORMA_PAGO")
public class FormaPago implements Serializable {
    
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    
    @Column(name = "TOTAL")
    private BigDecimal total;
    @Column(name = "PLAZO")
    private Integer plazo;
    @Column(name = "UNIDAD_TIEMPO")
    private String unidadTiempo;    
    
    @JoinColumn(name = "SRI_FORMA_PAGO_ID")
    @ManyToOne
    private SriFormaPago sriFormaPago;
    
    @JoinColumn(name = "FACTURA_ID")
    @ManyToOne
    private Factura factura;

    public FormaPago() {
    }
    
    
    public Long getId() {
        return id;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public Integer getPlazo() {
        return plazo;
    }

    public String getUnidadTiempo() {
        return unidadTiempo;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public void setPlazo(Integer plazo) {
        this.plazo = plazo;
    }

    public void setUnidadTiempo(String unidadTiempo) {
        this.unidadTiempo = unidadTiempo;
    }

    public SriFormaPago getSriFormaPago() {
        return sriFormaPago;
    }

    public void setSriFormaPago(SriFormaPago sriFormaPago) {
        this.sriFormaPago = sriFormaPago;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    //Metodos perosonalizadas
    public enum UnidadTiempoEnum
    {
        NINGUNO("ninguno"),
        DIAS("Dias"),
        MESES("Meses"),
        ANOS("Años");

        private UnidadTiempoEnum(String nombre) {
            this.nombre = nombre;
        }
        
        private String nombre;

        public String getNombre() {
            return nombre;
        }
        
        
    }
    
}
