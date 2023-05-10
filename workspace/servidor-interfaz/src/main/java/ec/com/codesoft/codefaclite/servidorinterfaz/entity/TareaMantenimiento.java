/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author CARLOS_CODESOFT
 */

@Entity
@Table(name = "TAREA_MANTENIMIENTO")
public class TareaMantenimiento extends EntityAbstract<GeneralEnumEstado>
{
    @Column(name = "NOMBRE")
    private String nombre;
    
    @Column(name = "ORDEN")
    private Integer Orden;
    
    @Column(name = "TIPO")
    private String tipo;

    public TareaMantenimiento() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getOrden() {
        return Orden;
    }

    public void setOrden(Integer Orden) {
        this.Orden = Orden;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    /////////////////////////////////////////////////////////////
    ///             DATOS ADICIONALES
    /////////////////////////////////////////////////////////////
    
    public enum TipoEnum
    {
        
        /**
         * Este proceso me va a identificar cuando es uno normal del proceso
         */
        NORMAL("n","Normal"),
        
        /**
         * Este proceso me sirve para identificar cuando se asigna una novedad
         */
        NOVEDAD("v","Novedad");
        
        private String letra;
        private String nombre;

        private TipoEnum(String letra, String nombre) {
            this.letra = letra;
            this.nombre = nombre;
        }

        public String getLetra() {
            return letra;
        }

        public String getNombre() {
            return nombre;
        }
        
        
        
    }
}
