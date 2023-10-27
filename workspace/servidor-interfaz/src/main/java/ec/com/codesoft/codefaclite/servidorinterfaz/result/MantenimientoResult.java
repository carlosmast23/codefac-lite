/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.result;

import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoNoConformidadEnum;
import ec.com.codesoft.codefaclite.utilidades.list.UtilidadesLista;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadVarios;
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
    
    private static Long idContador=0l;
    public Long idTemp;
    
    public String modelo;
    public String marca;
    public String color;
    public String vin;
    public String estado;
    public String fechaIngreso;
    public String fechaSalida;
    public String ubicacion;
    
    //Listado de los detalles adicionales
    public String enderezada;
    public String pintura;
    public String pulida;
    public String faltante;
    public String electromecanico;
    public String comentario;
    public String taller;
            
    
    private String tareaTitulo;
    private String tareaDescripcion;
    private String duracionDias;
    private String duracionHoras;
    
    private String noConformidad;
    private String parteVehiculo;
    
    private String fechaInicioProceso;
    private String fechaFinProceso;
    private Integer horasProceso;
    
    
    private List<DetalleTareaResult> tareaLista;

    public MantenimientoResult() 
    {
        this.idTemp=idContador++;
    }
    
    
    public void asignarNuevoId()
    {
        this.idTemp=idContador++;
    }
    
    
    
    public void agregarTarea(DetalleTareaResult detalle)
    {
        if(tareaLista==null)
        {
            tareaLista=new ArrayList<DetalleTareaResult>();            
        }
        tareaLista.add(detalle);
    }
    
    public static List<MantenimientoResult> convertirDataReporte(List<MantenimientoResult> datosList,Boolean noConformidades)
    {
        System.out.println("convertirDataReporte ...");  
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
                    
                    MantenimientoResult detalleTmp=new MantenimientoResult();
                    //UtilidadVarios.copiarObjetos(detalle, detalleTmp);
                    //MantenimientoResult detalleTmp = (MantenimientoResult) detalle.clone();
                    UtilidadVarios.copiarObjetos(detalle, detalleTmp);
                    detalleTmp.setTareaTitulo(tareaResult.titulo);
                    detalleTmp.setTareaDescripcion(tareaResult.descripcion);
                    detalleTmp.setDuracionDias(tareaResult.horas + "");
                    detalleTmp.setFechaInicioProceso(tareaResult.fechaInicio);
                    detalleTmp.setFechaFinProceso(tareaResult.fechaFin);
                    detalleTmp.setHorasProceso(tareaResult.horasProceso);
                    Integer duracionDiasTarea = (int) (tareaResult.horasProceso / 24);
                    detalleTmp.setDuracionDias(duracionDiasTarea+"");
                    if (noConformidades) {
                        System.out.println("Consultando NO CONFORMIDADES ...");
                        List<InformeDetalleResult> informeList = tareaResult.detalleList;
                        if (informeList != null) {
                            for (InformeDetalleResult informe : informeList) {
                                MantenimientoResult detalleTmp2=new MantenimientoResult();
                                UtilidadVarios.copiarObjetos(detalleTmp, detalleTmp2);
                                
                                //MantenimientoResult detalleTmp = (MantenimientoResult) detalle.clone();
                                //detalleTmp.setTareaTitulo(tareaResult.titulo);
                                //detalleTmp.setTareaDescripcion(tareaResult.descripcion);
                                //detalleTmp.setDuracionDias(duracionDiasTarea + "");
                                detalleTmp2.setNoConformidad(informe.noConformidad);
                                detalleTmp2.setParteVehiculo(informe.parteVehiculo);
                                resultadoList.add(detalleTmp2);
                            }
                        }
                    }
                    else
                    {
                        resultadoList.add(detalleTmp);
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

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(String fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public String getDuracionDias() {
        return duracionDias;
    }

    public void setDuracionDias(String duracionDias) {
        this.duracionDias = duracionDias;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
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

    public String getPulida() {
        return pulida;
    }

    public void setPulida(String pulida) {
        this.pulida = pulida;
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

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getTaller() {
        return taller;
    }
    
    public String obtenerTallerNombre()
    {
        TipoNoConformidadEnum noConformidadEnum= TipoNoConformidadEnum.buscarPorCodigo(noConformidad);
        if(noConformidadEnum!=null)
        {
            return noConformidadEnum.getNombre();
        }
        return "";
    }

    public void setTaller(String taller) {
        this.taller = taller;
    }

    public String getNoConformidad() {
        return noConformidad;
    }

    public void setNoConformidad(String noConformidad) {
        this.noConformidad = noConformidad;
    }

    public String getParteVehiculo() {
        return parteVehiculo;
    }

    public void setParteVehiculo(String parteVehiculo) {
        this.parteVehiculo = parteVehiculo;
    }

    public String getFechaInicioProceso() {
        return fechaInicioProceso;
    }

    public void setFechaInicioProceso(String fechaInicioProceso) {
        this.fechaInicioProceso = fechaInicioProceso;
    }

    public String getFechaFinProceso() {
        return fechaFinProceso;
    }

    public void setFechaFinProceso(String fechaFinProceso) {
        this.fechaFinProceso = fechaFinProceso;
    }

    public Integer getHorasProceso() {
        return horasProceso;
    }

    public void setHorasProceso(Integer horasProceso) {
        this.horasProceso = horasProceso;
    }

    public String getDuracionHoras() {
        return duracionHoras;
    }

    public void setDuracionHoras(String duracionHoras) {
        this.duracionHoras = duracionHoras;
    }

    public Long getIdTemp() {
        return idTemp;
    }

    public void setIdTemp(Long idTemp) {
        this.idTemp = idTemp;
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
    
    public String getDetalleTareasStr()
    {
        /*return UtilidadesLista.castListToString(tareaLista,",",new UtilidadesLista.CastListInterface<DetalleTareaResult>() {
            @Override
            public String getString(DetalleTareaResult dato) {
                if(dato!=null)
                {
                    return dato.titulo;
                }
                return "";
            }
        });*/
        return "";
    }
    
    public static class DetalleTareaResult implements Serializable
    {
        public String titulo;
        public String descripcion;
        public Integer horas;
        
        public String fechaInicio;
        public String fechaFin;
        public Integer horasProceso;
        
        public List<InformeDetalleResult> detalleList;

        public DetalleTareaResult(String titulo, String descripcion,Integer horas,String fechaInicio,String fechaFin,Integer horasProceso) {
            this.titulo = titulo;
            this.descripcion = descripcion;
            this.horas=horas;
            this.fechaInicio=fechaInicio;
            this.fechaFin=fechaFin;
            this.horasProceso=horasProceso;
        }
        
        public void agregarInformeDetalle(InformeDetalleResult detalle)
        {
            if(detalleList==null)
            {
                this.detalleList=new ArrayList<InformeDetalleResult>();                
            }
            
            detalleList.add(detalle);
        }
        
        
    }
    
    public static class InformeDetalleResult implements Serializable
    {
        public String noConformidad;
        public String parteVehiculo;

        public InformeDetalleResult(String noConformidad, String parteVehiculo) {
            this.noConformidad = noConformidad;
            this.parteVehiculo = parteVehiculo;
        }
        
        
    }
        
}
