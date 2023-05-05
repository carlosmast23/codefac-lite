/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import java.io.Serializable;
import java.sql.Timestamp;
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
 * @author CARLOS_CODESOFT
 */
@Entity
@Table(name = "MANTENIMIENTO")
public class Mantenimiento extends EntityAbstract<Mantenimiento.MantenimientoEnum>{

    public Mantenimiento() {
    }
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "COMENTARIO")
    private String comentario;
    
    @JoinColumn(name = "SUPERVISOR_ID")
    private Empleado supervisor;
    
    @Column(name = "FECHA_INGRESO")
    private Timestamp fechaIngreso; 
    
    @Column(name = "FECHA_SALIDA")
    private Timestamp fechaSalida;
    
    
    
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
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
        final Mantenimiento other = (Mantenimiento) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    
    
    public enum MantenimientoEnum 
    {
        INGRESADO("I","Ingresado"),
        FACTURADO("F","Facturado"),
        ELIMINADO("E","Eliminado");

        private MantenimientoEnum(String letra, String nombre) {
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
        
        public static MantenimientoEnum getEnum(String letra) {
        for (MantenimientoEnum enumerador : MantenimientoEnum.values()) {
            if (enumerador.letra.equals(letra)) {
                return enumerador;
            }
        }
        return null;
    }
        
        
    }
    
        
}
