/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CategoriaProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ImpuestoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoProductoEnum;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
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
@Table(name = "CATALOGO_PRODUCTO")
public class CatalogoProducto implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "NOMBRE")
    private String nombre;
    
    @Column(name = "TIPO_PRODUCTO")
    private String tipoProducto;
    
    @JoinColumn(name = "IVA_ID")
    @ManyToOne
    private ImpuestoDetalle iva;

    @JoinColumn(name = "ICE_ID")
    @ManyToOne
    private ImpuestoDetalle ice;

    @JoinColumn(name = "IRBPNR_ID")
    @ManyToOne
    private ImpuestoDetalle irbpnr;
    
    @JoinColumn(name = "CATEGORIA_ID")
    @ManyToOne
    private CategoriaProducto categoriaProducto;

    public CatalogoProducto() {
    }
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(String tipoProducto) {
        this.tipoProducto = tipoProducto;
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

    public ImpuestoDetalle getIrbpnr() {
        return irbpnr;
    }

    public void setIrbpnr(ImpuestoDetalle irbpnr) {
        this.irbpnr = irbpnr;
    }

    public CategoriaProducto getCategoriaProducto() {
        return categoriaProducto;
    }

    public void setCategoriaProducto(CategoriaProducto categoriaProducto) {
        this.categoriaProducto = categoriaProducto;
    }
    
    // Metodos adicionales

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.id);
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
        final CatalogoProducto other = (CatalogoProducto) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombre;
    }
    
    
    public TipoProductoEnum getTipoProductoEnum() {
        return TipoProductoEnum.getEnumByLetra(tipoProducto);
    }
    
    
    

}
