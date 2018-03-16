/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico;

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
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "ESTUDIANTE_INSCRITO")
@XmlRootElement
public class EstudianteInscrito implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "SALDO")
    private BigDecimal saldo;
    
    @Column(name = "ESTADO_FACTURA")
    private String estadoFactura;
    
    @JoinColumn(name = "ESTUDIANTE_ID")
    @ManyToOne
    private Estudiante estudiante;
    
    @JoinColumn(name = "NIVEL_ACADEMICO_ID")
    @ManyToOne
    private NivelAcademico nivelAcademico;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public NivelAcademico getNivelAcademico() {
        return nivelAcademico;
    }

    public void setNivelAcademico(NivelAcademico nivelAcademico) {
        this.nivelAcademico = nivelAcademico;
    }

    public String getEstadoFactura() {
        return estadoFactura;
    }

    public void setEstadoFactura(String estadoFactura) {
        this.estadoFactura = estadoFactura;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }
    
    

    @Override
    public String toString() {
        return estudiante.getNombres()+" "+estudiante.getApellidos();
    }
    
    //Metodos personalizados
    
    
    public FacturacionEstadoEnum getEstadoFacturaEnum() {
        return FacturacionEstadoEnum.buscarPorLetra(estadoFactura);       
    } 
    
    public enum FacturacionEstadoEnum
    {
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
    }
    
    
}
