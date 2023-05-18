/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.result;

import ec.com.codesoft.codefaclite.utilidades.list.UtilidadesLista;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class MantenimientoResult implements Serializable,Cloneable{
    
    public String modelo;
    public String color;
    public String vin;
    public String estado;
    public String fechaIngreso;
    
    private String tareaTitulo;
    private String tareaDescripcion;
    
    private List<DetalleTareaResult> tareaLista;
    
    
    public void agregarTarea(DetalleTareaResult detalle)
    {
        if(tareaLista==null)
        {
            tareaLista=new ArrayList<DetalleTareaResult>();            
        }
        tareaLista.add(detalle);
    }
    
    public static List<MantenimientoResult> convertirDataReporte(List<MantenimientoResult> datosList)
    {
        List<MantenimientoResult> resultadoList=new ArrayList<MantenimientoResult>();
        
        for (MantenimientoResult detalle : datosList) 
        {
            if(UtilidadesLista.verificarListaVaciaONull(detalle.getTareaLista()))
            {
                resultadoList.add(detalle);
            }
            else
            {
                for (DetalleTareaResult tareaResult : detalle.getTareaLista()) 
                {
                    try {
                        MantenimientoResult detalleTmp= (MantenimientoResult) detalle.clone();
                        detalleTmp.setTareaTitulo(tareaResult.titulo);
                        detalleTmp.setTareaDescripcion(tareaResult.descripcion);
                        resultadoList.add(detalleTmp);
                    } catch (CloneNotSupportedException ex) {
                        Logger.getLogger(MantenimientoResult.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
            }
        }
        return resultadoList;
    }
    
  /*  public String enderezada;
    public String pintura;
    public String vidrios;
    public String faltante;
    public String electromecanico;
    public String otros;*/

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

    public String getTareaDescripcion() {
        return tareaDescripcion;
    }

    public void setTareaDescripcion(String tareaDescripcion) {
        this.tareaDescripcion = tareaDescripcion;
    }

    public String getTareaTitulo() {
        return tareaTitulo;
    }

    public void setTareaTitulo(String tareaTitulo) {
        this.tareaTitulo = tareaTitulo;
    }

    public List<DetalleTareaResult> getTareaLista() {
        return tareaLista;
    }

    public void setTareaLista(List<DetalleTareaResult> tareaLista) {
        this.tareaLista = tareaLista;
    }
    
    
    

    /*public String getEnderezada() {
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
    }*/
    
    public static class DetalleTareaResult implements Serializable
    {
        public String titulo;
        public String descripcion;

        public DetalleTareaResult(String titulo, String descripcion) {
            this.titulo = titulo;
            this.descripcion = descripcion;
        }
        
        
    }
        
}