/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.core;

import ec.com.codesoft.codefaclite.controlador.session.SessionCodefac;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Carlos
 */
@ManagedBean
@SessionScoped
public class SessionMb extends SessionCodefac{
 
    private String ejemplo;
    
    @PostConstruct
    public void init()
    {
        ejemplo="hola a todos";
    }

    public String getEjemplo() {
        return ejemplo;
    }

    public void setEjemplo(String ejemplo) {
        this.ejemplo = ejemplo;
    }
    
    
}
