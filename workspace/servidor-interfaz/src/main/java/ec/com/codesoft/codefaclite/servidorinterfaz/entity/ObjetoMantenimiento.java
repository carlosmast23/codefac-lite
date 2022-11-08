/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoObjetoMantenimientoEnum;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "OBJETO_MANTENIMIENTO")
public class ObjetoMantenimiento extends EntityAbstract<GeneralEnumEstado>{

    @Column(name = "CODIGO")
    private String codigo;
    
    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "DESCRIPCION")
    private String descripcion;
    
    @Column(name = "TIPO")
    private String tipo;
    
    @JoinColumn(name = "EMPRESA_ID")
    private Empresa empresa;
    
    @JoinColumn(name = "PROPIETARIO_ID")
    private Persona propietario;
    
    @JoinColumn(name = "MARCA_ID")
    private MarcaProducto marca;

    @Column(name = "TIPO_COMBUSTIBLE")
    private String tipoCombustible;
    
    @Column(name = "KILOMETRAJE")
    private Integer kilometraje;
    

    public ObjetoMantenimiento() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
    
    public GeneralEnumEstado getEstadoEnum() {
        return GeneralEnumEstado.getEnum(estado);
    }

    public void setEstadoEnum(GeneralEnumEstado estadoEnum) {
        this.estado = estadoEnum.getEstado();
    }

    public ObjetoMantenimiento(String codigo) {
        this.codigo = codigo;
    }

    public Persona getPropietario() {
        return propietario;
    }

    public void setPropietario(Persona propietario) {
        this.propietario = propietario;
    }
    
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public MarcaProducto getMarca() {
        return marca;
    }

    public void setMarca(MarcaProducto marca) {
        this.marca = marca;
    }

    public String getTipoCombustible() {
        return tipoCombustible;
    }

    public void setTipoCombustible(String tipoCombustible) {
        this.tipoCombustible = tipoCombustible;
    }

    public Integer getKilometraje() {
        return kilometraje;
    }

    public void setKilometraje(Integer kilometraje) {
        this.kilometraje = kilometraje;
    }
 
    
    
    
    ////////////////////////////////////////////////
    ///         METODOS PERSONALIZADOS
    ////////////////////////////////////////////////

    public TipoObjetoMantenimientoEnum getTipoEnum() {
        return TipoObjetoMantenimientoEnum.getEnumByLetra(tipo);
    }

    public void setTipoEnum(TipoObjetoMantenimientoEnum tipoEnum) {
        if(tipoEnum!=null)
        {
            this.tipo = tipoEnum.getLetra();
        }
    }
    
    

    @Override
    public String toString() {
        return "["+codigo+"] "+ nombre;
    }

    
    

}
