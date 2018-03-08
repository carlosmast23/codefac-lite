/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Carlos
 */
public class ComprobanteData implements Serializable{
    private String estado;
    private String numeroAutorizacion;
    private String fechaAutorizacion;
    private String ambiente;
    private String comprobante;
    private List<ComprobanteMensaje> mensajes;

    public ComprobanteData() {
    }
    
    

    public ComprobanteData(String estado, String numeroAutorizacion, String fechaAutorizacion, String ambiente, String comprobante, List<ComprobanteMensaje> mensajes) {
        this.estado = estado;
        this.numeroAutorizacion = numeroAutorizacion;
        this.fechaAutorizacion = fechaAutorizacion;
        this.ambiente = ambiente;
        this.comprobante = comprobante;
        this.mensajes = mensajes;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNumeroAutorizacion() {
        return numeroAutorizacion;
    }

    public void setNumeroAutorizacion(String numeroAutorizacion) {
        this.numeroAutorizacion = numeroAutorizacion;
    }

    public String getFechaAutorizacion() {
        return fechaAutorizacion;
    }

    public void setFechaAutorizacion(String fechaAutorizacion) {
        this.fechaAutorizacion = fechaAutorizacion;
    }

    public String getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(String ambiente) {
        this.ambiente = ambiente;
    }

    public String getComprobante() {
        return comprobante;
    }

    public void setComprobante(String comprobante) {
        this.comprobante = comprobante;
    }

    public List<ComprobanteMensaje> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<ComprobanteMensaje> mensajes) {
        this.mensajes = mensajes;
    }
    
    
    
}
