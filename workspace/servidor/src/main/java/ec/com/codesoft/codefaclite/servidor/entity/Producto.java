/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
@Entity
@Table(name = "PRODUCTO")
@XmlRootElement
public class Producto implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column (name = "ID_PRODUCTO")
    private Long  idProducto;
    @Column(name = "CODIGO_PERSONALIZADO")
    private String codigoPersonalizado;
    @Column(name = "CODIGO_EAN")
    private String codigoEAN;
    @Column(name = "CODIGO_UPC")
    private String codigoUPC;
    
    @Column(name = "TIPO_PRODUCTO")
    private String tipoProducto;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "VALOR_UNITARIO")
    private BigDecimal valorUnitario;
    @Column(name = "ESTADO")
    private String estado;
    
    //@Column(name = "IVA")
    @JoinColumn(name = "IVA_ID")
    @ManyToOne
    private ImpuestoDetalle iva;
    
    @JoinColumn(name = "ICE_ID")
    @ManyToOne
    //@Column(name = "ICE")
    private ImpuestoDetalle ice;
    
    @JoinColumn(name = "IRBPNR_ID")
    @ManyToOne
    //@Column(name = "IRBPNR")
    private ImpuestoDetalle irbpnr;

    public Producto() {
    }
    
    

    public ImpuestoDetalle getIva() {
        return iva;
    }

    public void setIva(ImpuestoDetalle iva) {
        this.iva = iva;
    }

    public ImpuestoDetalle getIce() {
        return ice;
    }

    public void setIce(ImpuestoDetalle ice) {
        this.ice = ice;
    }

    public static final Producto getDEFECTO() {
        Producto p = new Producto();
        return new Producto();
    }

    public ImpuestoDetalle getIrbpnr() {
        return irbpnr;
    }

    public void setIrbpnr(ImpuestoDetalle irbpnr) {
        this.irbpnr = irbpnr;
    }

    
    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    
    public String getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(String tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCodigoPersonalizado() {
        return codigoPersonalizado;
    }

    public void setCodigoPersonalizado(String codigoPersonalizado) {
        this.codigoPersonalizado = codigoPersonalizado;
    }

    public String getCodigoEAN() {
        return codigoEAN;
    }

    public void setCodigoEAN(String codigoEAN) {
        this.codigoEAN = codigoEAN;
    }

    public String getCodigoUPC() {
        return codigoUPC;
    }

    public void setCodigoUPC(String codigoUPC) {
        this.codigoUPC = codigoUPC;
    }

    
    
    
    
    
    
}
