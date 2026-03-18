/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.exception;

/**
 *
 * @author Carlos
 */
public class ComprobanteElectronicoException extends Exception {
    
    public static final int ERROR_COMPROBANTE=1;
    public static final int ERROR_ENVIO_CLIENTE=2;
    
    /*
    Estado para saber que ya no debo enviar porque fue rechazado por el Sri
    */
    public static final int RECHAZADO=3;
    
    private String etapa;
    private Integer tipoError;
    private String claveAcceso;
    /**
     * Variable que me permite identificar en que carpeta se quedo pendiente para luego eliminar si sucede un error
     */
    private String carpetaComprobante;
    
    public ComprobanteElectronicoException(ComprobanteElectronicoException cee)
    {
        super(cee.getMessage());
        this.etapa=cee.etapa;
        this.tipoError=cee.tipoError;
    }
    
    public ComprobanteElectronicoException(String msg,String etapa,int error) 
    {
        super(msg);
        this.etapa=etapa;
        this.tipoError=error;
    }
    
    public ComprobanteElectronicoException(String msg,String etapa,int error,String carpetaComprobante) 
    {
        super(msg);
        this.etapa=etapa;
        this.tipoError=error;
        this.carpetaComprobante=carpetaComprobante;
    }
    
    

    public String getEtapa() {
        return etapa;
    }

    public Integer getTipoError() {
        return tipoError;
    }
    
    public String obtenerErrorFormato()
    {
        return"Etapa: " + getEtapa() + "\n" + getMessage();
    }

    public String getCarpetaComprobante() {
        return carpetaComprobante;
    }

    public void setCarpetaComprobante(String carpetaComprobante) {
        this.carpetaComprobante = carpetaComprobante;
    }

    public String getClaveAcceso() {
        return claveAcceso;
    }

    public void setClaveAcceso(String claveAcceso) {
        this.claveAcceso = claveAcceso;
    }
    
    

    
    
}