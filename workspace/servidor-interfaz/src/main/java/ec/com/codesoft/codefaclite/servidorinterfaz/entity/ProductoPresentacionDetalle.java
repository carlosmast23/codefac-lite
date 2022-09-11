/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author CARLOS_CODESOFT
 */
@Entity
@Table(name = "PRODUCTO_PRESENTACION_DETALLE")
public class ProductoPresentacionDetalle implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "CANTIDAD")
    private BigDecimal cantidad;
    
    @Transient
    private BigDecimal pvpTmp;
    
    @JoinColumn(name = "PRESENTACION_PRODUCTO_ID")
    private PresentacionProducto presentacionProducto;
    
    @JoinColumn(name = "PRODUCTO_ORIGINAL_ID")
    private Producto productoOriginal;
    
    @JoinColumn(name = "PRODUCTO_EMPAQUETADO_ID")
    private Producto productoEmpaquetado;
    
    @Column(name = "TIPO")
    private String tipo;
    

    public ProductoPresentacionDetalle() 
    {
        
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public Producto getProductoOriginal() {
        return productoOriginal;
    }

    public void setProductoOriginal(Producto productoOriginal) {
        this.productoOriginal = productoOriginal;
    }

    public Producto getProductoEmpaquetado() {
        return productoEmpaquetado;
    }

    public void setProductoEmpaquetado(Producto productoEmpaquetado) {
        this.productoEmpaquetado = productoEmpaquetado;
    }

    public PresentacionProducto getPresentacionProducto() {
        return presentacionProducto;
    }

    public void setPresentacionProducto(PresentacionProducto presentacionProducto) {
        this.presentacionProducto = presentacionProducto;
    }

    public BigDecimal getPvpTmp() {
        return pvpTmp;
    }

    public void setPvpTmp(BigDecimal pvpTmp) {
        this.pvpTmp = pvpTmp;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public TipoPresentacionEnum getTipoEnum() {
        return TipoPresentacionEnum.buscarPorLetra(tipo);
    }

    public void setTipoEnum(TipoPresentacionEnum tipoEnum) {
        this.tipo = tipoEnum.letra;
    }

    
    
    public enum TipoPresentacionEnum
    {
        ORIGINAL("O","Original"),
        ADICIONAL("A","Adicional");
        
        private String letra;
        private String nombre;

        private TipoPresentacionEnum(String letra, String nombre) {
            this.letra = letra;
            this.nombre = nombre;
        }
        
        public String getLetra() {
            return letra;
        }


        public String getNombre() {
            return nombre;
        }
        
        public static TipoPresentacionEnum buscarPorLetra(String letra)
        {
            for (TipoPresentacionEnum presentacion : TipoPresentacionEnum.values()) 
            {
                if(presentacion.getLetra().equals(letra))
                {
                    return presentacion ;
                }
            }
            return null;
        }
        
        @Override
        public String toString() {
            return nombre;
        }

        
    }

   
    
    
}
