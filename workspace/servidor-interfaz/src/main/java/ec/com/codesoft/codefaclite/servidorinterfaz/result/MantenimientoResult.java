/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.result;

import java.io.Serializable;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class MantenimientoResult implements Serializable{
    
    public String modelo;
    public String color;
    public String vin;
    public String estado;
    public String fechaIngreso;
    
    public String enderezada;
    public String pintura;
    public String vidrios;
    public String faltante;
    public String electromecanico;
    public String otros;

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getEnderezada() {
        return enderezada;
    }

    public void setEnderezada(String enderezada) {
        this.enderezada = enderezada;
    }

    public String getPintura() {
        return pintura;
    }

    public void setPintura(String pintura) {
        this.pintura = pintura;
    }

    public String getVidrios() {
        return vidrios;
    }

    public void setVidrios(String vidrios) {
        this.vidrios = vidrios;
    }

    public String getFaltante() {
        return faltante;
    }

    public void setFaltante(String faltante) {
        this.faltante = faltante;
    }

    public String getElectromecanico() {
        return electromecanico;
    }

    public void setElectromecanico(String electromecanico) {
        this.electromecanico = electromecanico;
    }

    public String getOtros() {
        return otros;
    }

    public void setOtros(String otros) {
        this.otros = otros;
    }
    
    
        
}
