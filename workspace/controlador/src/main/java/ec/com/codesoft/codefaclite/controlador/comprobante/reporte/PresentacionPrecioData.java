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

    public PresentacionPrecioData(String nombre, String costo, String pvp) {
        this.nombre = nombre;
        this.costo = costo;
        this.pvp = pvp;
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
    
    
    
}
