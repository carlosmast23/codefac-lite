/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author CARLOS_CODESOFT
 */
@Entity
@Table(name = "VEHICULO")
public class Vehiculo extends ObjetoMantenimiento
{
    /**
     * El VIN es el codigo del vehiculo a nivel internacional
     */
   /* @Column(name = "VIN")
    private Long vin;
    
    @Column(name = "MODELO")
    private String modelo;

    public Vehiculo() {
    }

    public Long getVin() {
        return vin;
    }

    public void setVin(Long vin) {
        this.vin = vin;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }*/

    
}
