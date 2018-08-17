/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.mb;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;


/**
 *
 * @author Carlos
 */
@ManagedBean(name="textoBean")
@ViewScoped
public class TextoBean implements Serializable {

    @ManagedProperty("titulo")
    private String titulo;
    
    @PostConstruct
    public void init() {
        titulo = "ESTE ESTA BIEN ";
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
}
