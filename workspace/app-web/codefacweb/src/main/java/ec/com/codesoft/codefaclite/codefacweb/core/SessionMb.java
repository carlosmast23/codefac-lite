/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.core;

import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefac;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Carlos
 */
@ManagedBean
@SessionScoped
public class SessionMb implements Serializable{

    /**
     * Entidad que se va a encargar de tener fisicamente los datos de la session
     */
    private SessionCodefac session;

    @PostConstruct
    public void init() {
        //ejemplo = "hola a todos";
    }

    public SessionCodefac getSession() {
        return session;
    }

    public void setSession(SessionCodefac session) {
        this.session = session;
    }

    
}
