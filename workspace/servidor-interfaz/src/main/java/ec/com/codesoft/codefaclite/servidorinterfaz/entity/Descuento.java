/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author CARLOS_CODESOFT
 */
@Entity
@Table(name = "DESCUENTO")
public class Descuento extends EntityAbstract<GeneralEnumEstado>{
    
    @Column(name = "NOMBRE")
    private String nombre;
    
    @Column(name = "DESCRIPCION")
    private String descripcion;
    
    @Column(name = "FECHA_INICIO")
    private Date fechaInicio;
    
    @Column(name = "FECHA_FIN")
    private Date fechaFin;
    
    @Column(name = "ALCANCE")
    private String alcance;
    
    @JoinColumn(name = "EMPRESA_ID")
    protected Empresa empresa;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "descuento",fetch = FetchType.EAGER)
    private List<DescuentoProductoDetalle> productoList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "descuento",fetch = FetchType.EAGER)
    private List<DescuentoCondicionPrecio> condicionPrecioList;
    
    
    
    
    /////////////////////////////////////////////////
    ///             METODOS GET AND SET
    ////////////////////////////////////////////////

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getAlcance() {
        return alcance;
    }

    public void setAlcance(String alcance) {
        this.alcance = alcance;
    }
    
    public AlcanceEnum getAlcanceEnum() {
        return AlcanceEnum.getEnum(alcance);
    }

    public void setAlcanceEnum(AlcanceEnum alcanceEnum) 
    {
        if(alcanceEnum!=null)
        {
            this.alcance=alcanceEnum.letra;
        }
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public List<DescuentoProductoDetalle> getProductoList() {
        return productoList;
    }

    public void setProductoList(List<DescuentoProductoDetalle> productoList) {
        this.productoList = productoList;
    }

    public List<DescuentoCondicionPrecio> getCondicionPrecioList() {
        return condicionPrecioList;
    }

    public void setCondicionPrecioList(List<DescuentoCondicionPrecio> condicionPrecioList) {
        this.condicionPrecioList = condicionPrecioList;
    }
    
    
    
    ////////////////////////////////////////////////////
    ///     CODIGO PERSONALIZADO
    ////////////////////////////////////////////////////
    
    public void agregarProductoPorLote(List<Producto> productoList)
    {
        if(productoList!=null)
        {
            for (Producto producto : productoList) 
            {
                agregarProducto(producto);
            }
        }
    }
    
    public void agregarProducto(Producto producto)
    {
        if(productoList==null)
        {
            this.productoList=new ArrayList<DescuentoProductoDetalle>();            
        }
        
        DescuentoProductoDetalle descuentoProductoDetalle=new DescuentoProductoDetalle(this, producto);
        
        this.productoList.add(descuentoProductoDetalle);
    }
    
    public void agregarCondicionPrecio(DescuentoCondicionPrecio condicionPrecio)
    {
        if(condicionPrecioList==null)
        {
            this.condicionPrecioList=new ArrayList<DescuentoCondicionPrecio >();            
        }
        
        //DescuentoCondicionPrecio descuentoCondicionPrecio=new DescuentoCondicionPrecio(this);
        condicionPrecio.setDescuento(this);
        this.condicionPrecioList.add(condicionPrecio);
    }
    
    /**
     * Este enum me va a permitir defecinir el alcance del descuento por ejemplo a nivel de todos los productos o a nivel de una cantidad de productos especificos
     */
    public enum AlcanceEnum
    {
        GLOBAL("Global","g"),
        SEGMENTADO("Segmentado","s");
        
        private String nombre;
        private String letra;

        private AlcanceEnum(String nombre, String letra) {
            this.nombre = nombre;
            this.letra = letra;
        }

        public String getNombre() {
            return nombre;
        }

        public String getLetra() {
            return letra;
        }
        
        public static AlcanceEnum getEnum(String letra)
        {
            for (AlcanceEnum value : AlcanceEnum.values()) 
            {
                if(value.getLetra().equals(letra))
                {
                    return value;
                }
            }
            return null;
        }
        
    }
    
    
    
}
