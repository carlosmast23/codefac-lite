/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
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
@Table(name = "RUBRO_ESTUDIANTE")
@XmlRootElement
public class RubroEstudiante implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "VALOR")    
    private BigDecimal valor;
    
    @Column(name = "SALDO")
    private BigDecimal saldo;
    
    @Column(name = "ESTADO_FACTURA")
    private String estadoFactura;
    
    @JoinColumn(name = "RUBRO_NIVEL_ID")
    @ManyToOne
    private RubrosNivel rubroNivel;
    
    @JoinColumn(name = "ESTUDIANTE_INSCRITO_ID")
    @ManyToOne
    private EstudianteInscrito estudianteInscrito;

    public RubroEstudiante() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RubrosNivel getRubroNivel() {
        return rubroNivel;
    }

    public void setRubroNivel(RubrosNivel rubroNivel) {
        this.rubroNivel = rubroNivel;
    }

    public EstudianteInscrito getEstudianteInscrito() {
        return estudianteInscrito;
    }

    public void setEstudianteInscrito(EstudianteInscrito estudianteInscrito) {
        this.estudianteInscrito = estudianteInscrito;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public String getEstadoFactura() {
        return estadoFactura;
    }

    public void setEstadoFactura(String estadoFactura) {
        this.estadoFactura = estadoFactura;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
    
        
    public FacturacionEstadoEnum getEstadoFacturaEnum() {
        return FacturacionEstadoEnum.buscarPorLetra(estadoFactura);       
    } 

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.id);
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
        final RubroEstudiante other = (RubroEstudiante) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    public enum FacturacionEstadoEnum {
        FACTURADO("f"),
        SIN_FACTURAR("s"),
        FACTURA_PARCIAL("p");

        private FacturacionEstadoEnum(String letra) {
            this.letra = letra;
        }

        private String letra;

        public static FacturacionEstadoEnum buscarPorLetra(String letra) {
            for (FacturacionEstadoEnum facturacionEstadoEnum : FacturacionEstadoEnum.values()) {
                if (facturacionEstadoEnum.equals(letra)) {
                    return facturacionEstadoEnum;
                }
            }
            return null;
        }

        public String getLetra() {
            return letra;
        }
        
        
    }
    
}
