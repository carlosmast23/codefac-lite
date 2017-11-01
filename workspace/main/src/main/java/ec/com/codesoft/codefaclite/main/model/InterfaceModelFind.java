/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.model;

import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author Carlos
 */
public interface InterfaceModelFind<T>
{
    public abstract Vector<String> getColumnas();
    public abstract ArrayList<T> getConsulta();
    public abstract void agregarObjeto(T t,Vector dato);
    public abstract Boolean buscarObjeto(T t,Object valor);
    
}
