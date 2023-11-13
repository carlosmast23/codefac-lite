/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

/**
 *
 * @author CARLOS_CODESOFT
 */
@Entity
@Table(name = "PRODUCTO_PRESENTACION_DETALLE")
public class ProductoPresentacionDetalle implements Serializable{
    
    private static Long ID_TEMPORAL=-1l;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "CANTIDAD")
    private BigDecimal cantidad;
    
    @Transient
    private BigDecimal pvpTmp;
    
    @Transient
    private String codigoTmp;
    
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
        id=ID_TEMPORAL--;
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

    public String getCodigoTmp() {
        return codigoTmp;
    }

    public void setCodigoTmp(String codigoTmp) {
        this.codigoTmp = codigoTmp;
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

    /*@Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.id);
        return hash;
    }*/

    /*@Override
    public int hashCode() {
        int hash = 3;
        if(this.id==null)
        {
            hash = 83 * hash + Objects.hashCode(this.cantidad);
            hash = 83 * hash + Objects.hashCode(this.pvpTmp);
            hash = 83 * hash + Objects.hashCode(this.presentacionProducto);
            hash = 83 * hash + Objects.hashCode(this.tipo);
        }
        else
        {
            hash = 83 * hash + Objects.hashCode(this.id);
        }
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
                
        final ProductoPresentacionDetalle other = (ProductoPresentacionDetalle) obj;
        
        if(this.id==null && other.id==null)
        {
            int hashCode=hashCode();
            int hashCodeTmp=other.hashCode();
            if(hashCode!=hashCodeTmp)
            {
                return false;
            }
        }
        else
        {
            //Comparar cuando tiene null en el id del comparados               
            if (!Objects.equals(this.id, other.id)) {
                return false;
            }
        }
        return true;
    }*/

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.id);
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
        final ProductoPresentacionDetalle other = (ProductoPresentacionDetalle) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    //////////////////////////////////////////////////////////////////////////////
    ///                 METODOS PERSONALIZADOS
    //////////////////////////////////////////////////////////////////////////////
    
    /**
     * Metodo para calcular los valores de las presentaciones en funcion de los precios de la presentacion original
     */
    public void calcularPreciosEmpaquesDesdeOriginal()
    {
        
        if (productoOriginal.getValorUnitario() != null) {
            productoEmpaquetado.setValorUnitario(productoOriginal.getValorUnitario().multiply(cantidad));
        }
        
        if (productoOriginal.getPrecioDistribuidor() != null) {
            productoEmpaquetado.setPrecioDistribuidor(productoOriginal.getPrecioDistribuidor().multiply(cantidad));
        }

        if (productoOriginal.getPrecioTarjeta() != null) {
            productoEmpaquetado.setPrecioTarjeta(productoOriginal.getPrecioTarjeta().multiply(cantidad));
        }

        if (productoOriginal.getPvp4() != null) {
            productoEmpaquetado.setPvp4(productoOriginal.getPvp4().multiply(cantidad));
        }

        if (productoOriginal.getPvp5() != null) {
            productoEmpaquetado.setPvp5(productoOriginal.getPvp5().multiply(cantidad));
        }

        if (productoOriginal.getPvp6() != null) {
            productoEmpaquetado.setPvp6(productoOriginal.getPvp6().multiply(cantidad));
        }
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
