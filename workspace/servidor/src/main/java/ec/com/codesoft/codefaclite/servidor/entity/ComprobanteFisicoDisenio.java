/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.entity;

import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "ROOT.COMPROBANTE_FISICO_DISENIO")
public class ComprobanteFisicoDisenio {
    
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column (name = "NOMBRE")
    private String nombre;
    
    @Column (name = "ANCHO")
    private int ancho;
    
    @Column (name = "ALTO")
    private int alto;
    
    @Column (name = "CODIGO_DOCUMENTO")
    private String codigoDocumento;

    @OneToMany(cascade = {CascadeType.ALL,CascadeType.DETACH}, mappedBy = "documento")
    private List<BandaComprobante> secciones;
    
    public ComprobanteFisicoDisenio() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAncho() {
        return ancho;
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    public int getAlto() {
        return alto;
    }

    public void setAlto(int alto) {
        this.alto = alto;
    }

    public String getCodigoDocumento() {
        return codigoDocumento;
    }

    public void setCodigoDocumento(String codigoDocumento) {
        this.codigoDocumento = codigoDocumento;
    }
    
    

    public List<BandaComprobante> getSecciones() {
        return secciones;
    }
    
    

    @Override
    public String toString() {
        return nombre;
    }
    
    
    
}
