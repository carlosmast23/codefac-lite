/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.entity.SriFormaPago;
import ec.com.codesoft.codefaclite.servidor.entity.SriIdentificacion;
import ec.com.codesoft.codefaclite.servidor.facade.SriFormaPagoFacade;
import ec.com.codesoft.codefaclite.servidor.facade.SriIdentificacionFacade;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author Carlos
 */
public class SriService {
    private SriFormaPagoFacade sriFormaPagoFacade;
    private SriIdentificacionFacade sriIdentificacionFacade;

    public SriService() {
        this.sriFormaPagoFacade = new SriFormaPagoFacade();
         this.sriIdentificacionFacade = new SriIdentificacionFacade();
    }
    
    public List<SriFormaPago> obtenerFormasPagoActivo()
    {
        java.util.Date fechaActual=new java.util.Date();
        
        return sriFormaPagoFacade.getFormaPagoByDate(new Date(fechaActual.getTime()));
    }
    
    /**
     * Obtiene el tipo de identificacion segun el tipo de proveedor
     * Los valor por defecto se encuentro en la tabla SriIdentificacion
     * SriIdentificacion.CLIENTE
     * SriIdentificacion.PROVEEDOR
     * @param tipo
     * @return 
     */
    public List<SriIdentificacion> obtenerIdentificaciones(String tipo)
    {

        return sriIdentificacionFacade.getSriIdentificacionByTipoTransaccion(tipo);
    }
    
}
