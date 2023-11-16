/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.codefaclite.servidorinterfaz.controller.EstadoEntidadIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

/**
 *
 * @author CARLOS_CODESOFT
 */
@Entity
@Table(name = "PRODUCTO_COMPONENTE")
public class ProductoComponente extends EntityAbstract<GeneralEnumEstado>
{

    public ProductoComponente() 
    {
        
    }
        
    @Column(name = "NOMBRE")
    private String nombre;
    
    @Column(name = "ESTADO")
    private String estado;
    
    @JoinColumn(name = "PRODUCTO_ID")
    private Producto producto;


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    
    

    @Override
    public String toString() {
        return nombre;
    }
    
    
    
    public enum MesaEstadoEnum implements EstadoEntidadIf
    {
        LIBRE("L","Libre"),
        ELIMINADO("E","Eliminado"),
        OCUPADO("O","Ocupado");

        private MesaEstadoEnum(String letra, String nombre) {
            this.letra = letra;
            this.nombre = nombre;
        }
        
        private String letra;
        private String nombre;

        public String getLetra() {
            return letra;
        }

        public String getNombre() {
            return nombre;
        }
        
        public static MesaEstadoEnum getEnum(String letra) {
        for (MesaEstadoEnum enumerador : MesaEstadoEnum.values()) {
            if (enumerador.letra.equals(letra)) {
                return enumerador;
            }
        }
        return null;
    }
        
        
    }
    
        
}
