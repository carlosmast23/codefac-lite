/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.core;

import ec.com.codesoft.codefaclite.codefacweb.mb.facturacion.BarraProgreso;
import ec.com.codesoft.codefaclite.codefacweb.mb.sistema.UtilidadesWeb;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefac;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
    
    //private Integer porcentaje=0;
    private Boolean actualizarMonitor=false;
    /**
     * Entidad que se va a encargar de tener fisicamente los datos de la session
     */
    private SessionCodefac session;
    
    public static final String LOGOUT_PAGE_REDIRECT =
      "/login.xhtml?faces-redirect=true";
    
    public List<BarraProgreso> barraProgresoList;

    @PostConstruct
    public void init() {
        this.barraProgresoList=new ArrayList<BarraProgreso>();
        //this.barraProgresoList.add(new BarraProgreso(90));
        //this.barraProgresoList.add(new BarraProgreso(70));
        //ejemplo = "hola a todos";
    }

    public SessionCodefac getSession() {
        return session;
    }

    public void setSession(SessionCodefac session) {
        this.session = session;
        this.actualizarMonitor=false;
    }

    /**
     * Variable para saber si el usuario esta logeado en el sistema
     * @return 
     */
    public boolean isLoggedIn() {
        return session != null;
    }
    
    /**
     * Terminar la session del usuario
     * @return 
     */
    public String logout()
    {
        System.out.println("salir de la session");
        session=null;
        return LOGOUT_PAGE_REDIRECT;
    }
      
    /*public Integer getProgreso()
    {
        return porcentaje;
    }
    
    public void setProgreso(Integer porcentaje)
    {
        this.porcentaje=porcentaje;
    }*/
    
    public void ejemploContador()
    {
        //this.actualizarMonitor=true;
        System.out.println("Actualizando monitor");
    }
    
    public void ejemploActivar()
    {
        
        //this.actualizarMonitor=!this.actualizarMonitor;
        System.out.println("Activando o apagando monitor");
    }

    public Boolean getActualizarMonitor() {
        return actualizarMonitor;
    }

    public void setActualizarMonitor(Boolean actualizarMonitor) {
        this.actualizarMonitor = actualizarMonitor;
        //this.actualizarMonitor=false;
    }

    public List<BarraProgreso> getBarraProgresoList() {
        return barraProgresoList;
    }

    public void setBarraProgresoList(List<BarraProgreso> barraProgresoList) {
        this.barraProgresoList = barraProgresoList;
    }
    
    
    
    
}
