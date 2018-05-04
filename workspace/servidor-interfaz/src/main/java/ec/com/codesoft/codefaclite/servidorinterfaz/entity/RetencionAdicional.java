/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "RETENCION_ADICIONAL")
@XmlRootElement
public class RetencionAdicional implements Serializable{
    
 /**
     * Nombre de los campos para grabar correos
     */
    public static final String NOMBRE_CORREO="correo";
    
    
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    
    
    @Column(name = "CAMPO")
    private String campo;
    
    @Column(name = "VALOR")
    private String valor;
    
    @Column(name = "TIPO")
    private String tipo;
    
    /**
     * Tipo de dato que se utiliza para numerar tipo de datos de un mismo tipo por ejemplo
     * los correo, correo 1 , correo 2 , correo 3
     */
    @Column(name = "NUMERO")
    private Integer numero;
    
    @JoinColumn(name = "RETENCION_ID")
    @ManyToOne
    private Retencion retencion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Retencion getRetencion() {
        return retencion;
    }

    public void setRetencion(Retencion retencion) {
        this.retencion = retencion;
    }

    

    public enum Tipo
    {
        TIPO_CORREO("c","correo"),
        TIPO_OTRO("o","otro");

        private Tipo(String letra, String nombre) {
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
        
        
    }
    
}
