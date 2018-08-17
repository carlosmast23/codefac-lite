/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.mb;

import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Carlos
 */
@ManagedBean
@ViewScoped
public class ConfiguracionInicialMb implements Serializable{
    
    
    private String ipServidor;
    
    public String conectarServidor()
    {
        //ServiceFactory.newController(ipServidor);
        
        FacesContext context = FacesContext.getCurrentInstance();         
        context.addMessage(null, new FacesMessage("Correcto",  "El servidor fue conectado correctamente") );
        return "menu";
    }

    public String getIpServidor() {
        return ipServidor;
    }

    public void setIpServidor(String ipServidor) {
        this.ipServidor = ipServidor;
    }
    
    
}
