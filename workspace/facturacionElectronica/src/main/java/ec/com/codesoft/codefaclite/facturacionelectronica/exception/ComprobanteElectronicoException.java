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
    
    private String etapa;
    
    public ComprobanteElectronicoException(String msg,String etapa) 
    {
        super(msg);
        this.etapa=etapa;
    }

    public String getEtapa() {
        return etapa;
    }

    
    
}