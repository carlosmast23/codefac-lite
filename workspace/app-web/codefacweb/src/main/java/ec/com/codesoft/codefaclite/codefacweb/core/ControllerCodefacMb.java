/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.core;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Carlos
 */
@ManagedBean
@ViewScoped
public class ControllerCodefacMb implements Serializable{
    private String indiceTabSecundario;
    private GeneralAbstractMb generalAbstractMb;

    @PostConstruct
    public void init()
    {
        indiceTabSecundario="2";
    }
    
    /**
     * Metodo superior que contrala la forma como van a grabar
     */
    public void save()
    {
        //Metodo save desde el controlador
        System.out.println("Metodo save desde el controlador");
        generalAbstractMb.grabar();
    }
    
    public String getIndiceTabSecundario() {
        return indiceTabSecundario;
    }

    public void setIndiceTabSecundario(String indiceTabSecundario) {
        this.indiceTabSecundario = indiceTabSecundario;
    }
    
    public void mostrarAyuda()
    {
        System.out.println("Cambiando indice panel");
        indiceTabSecundario="1";
    }

    public GeneralAbstractMb getGeneralAbstractMb() {
        return generalAbstractMb;
    }

    public void setGeneralAbstractMb(GeneralAbstractMb generalAbstractMb) {
        this.generalAbstractMb = generalAbstractMb;
    }
    
    public void agregarVista(GeneralAbstractMb vista)
    {
        generalAbstractMb=vista;
        System.out.println("iniciando el metodo que setea la vista");
    }
    
    
}
