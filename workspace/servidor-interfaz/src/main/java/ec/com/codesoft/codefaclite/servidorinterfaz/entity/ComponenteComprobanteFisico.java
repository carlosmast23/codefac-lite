/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "COMPONENTE_COMPROBANTE_FISICO")
public class ComponenteComprobanteFisico implements Serializable{

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "UUID")
    private String uuid;

    @Column(name = "X")
    private int x;

    @Column(name = "Y")
    private int y;

    @Column(name = "ANCHO")
    private int ancho;

    @Column(name = "ALTO")
    private int alto;

    @Column(name = "TAMANIO_LETRA")
    private int tamanioLetra;

    @Column(name = "NEGRITA")
    private String negrita;
    
    @Column(name = "OCULTO")
    private String oculto;
    
    @JoinColumn(name = "BANDA_COMPROBANTE_ID")
    @ManyToOne
    private BandaComprobante seccion;

    public ComponenteComprobanteFisico() {
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
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

    public String getNegrita() {
        return negrita;
    }

    public void setNegrita(String negrita) {
        this.negrita = negrita;
    }

    public int getTamanioLetra() {
        return tamanioLetra;
    }

    public void setTamanioLetra(int tamanioLetra) {
        this.tamanioLetra = tamanioLetra;
    }

    public String getOculto() {
        return oculto;
    }

    public void setOculto(String oculto) {
        this.oculto = oculto;
    }
    
    
    @Override
    public String toString() {
        return nombre;
    }

    

}
