/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.comprobante.reporte;

import java.io.Serializable;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class PresentacionPrecioData implements Serializable{
    private String nombre;
    private String costo;
    private String pvp;
    private String stock;

    public PresentacionPrecioData(String nombre, String costo, String pvp,String stock) {
        this.nombre = nombre;
        this.costo = costo;
        this.pvp = pvp;
        this.stock=stock;
    }

    public PresentacionPrecioData() {
    }
    
    

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCosto() {
        return costo;
    }

    public void setCosto(String costo) {
        this.costo = costo;
    }

    public String getPvp() {
        return pvp;
    }

    public void setPvp(String pvp) {
        this.pvp = pvp;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }
    
    
    
    
    
}
