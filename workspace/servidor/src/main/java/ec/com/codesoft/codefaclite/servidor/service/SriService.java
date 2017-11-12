/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.entity.SriFormaPago;
import ec.com.codesoft.codefaclite.servidor.facade.SriFormaPagoFacade;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author Carlos
 */
public class SriService {
    private SriFormaPagoFacade sriFormaPagoFacade;

    public SriService() {
        this.sriFormaPagoFacade = new SriFormaPagoFacade();
    }
    
    public List<SriFormaPago> obtenerFormasPagoActivo()
    {
        java.util.Date fechaActual=new java.util.Date();
        
        return sriFormaPagoFacade.getFormaPagoByDate(new Date(fechaActual.getTime()));
    }
    
}
