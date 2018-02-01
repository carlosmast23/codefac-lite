/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.model;

import ec.com.codesoft.codefaclite.controlador.aplicacion.ControladorCodefacInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * @author Carlos
 */
public class MenuControlador 
{
   private JMenuItem menuItem;
   private Class ventana;
   private boolean maximizado;
   private Object instance;

    public MenuControlador(JMenuItem menuItem, Class ventana) {
        this.menuItem = menuItem;
        this.ventana = ventana;
        this.maximizado=true;
        
    }
    
    /**
     * Devuelve una instancia segun la clase grabada
     * @return 
     */
    public Object getInstance()
    {
       try {
           
           if(instance==null)
               instance=this.ventana.getConstructor().newInstance();
           
           return instance;
           
       } catch (NoSuchMethodException ex) {
           Logger.getLogger(MenuControlador.class.getName()).log(Level.SEVERE, null, ex);
       } catch (SecurityException ex) {
           Logger.getLogger(MenuControlador.class.getName()).log(Level.SEVERE, null, ex);
       } catch (InstantiationException ex) {
           Logger.getLogger(MenuControlador.class.getName()).log(Level.SEVERE, null, ex);
       } catch (IllegalAccessException ex) {
           Logger.getLogger(MenuControlador.class.getName()).log(Level.SEVERE, null, ex);
       } catch (IllegalArgumentException ex) {
           Logger.getLogger(MenuControlador.class.getName()).log(Level.SEVERE, null, ex);
       } catch (InvocationTargetException ex) {
           Logger.getLogger(MenuControlador.class.getName()).log(Level.SEVERE, null, ex);
       }
       return null;
    }
    
    public Object createNewInstance()
    {
       try {
           instance=this.ventana.getConstructor().newInstance();
       } catch (InstantiationException ex) {
           Logger.getLogger(MenuControlador.class.getName()).log(Level.SEVERE, null, ex);
       } catch (IllegalAccessException ex) {
           Logger.getLogger(MenuControlador.class.getName()).log(Level.SEVERE, null, ex);
       } catch (IllegalArgumentException ex) {
           Logger.getLogger(MenuControlador.class.getName()).log(Level.SEVERE, null, ex);
       } catch (InvocationTargetException ex) {
           Logger.getLogger(MenuControlador.class.getName()).log(Level.SEVERE, null, ex);
       } catch (NoSuchMethodException ex) {
           Logger.getLogger(MenuControlador.class.getName()).log(Level.SEVERE, null, ex);
       } catch (SecurityException ex) {
           Logger.getLogger(MenuControlador.class.getName()).log(Level.SEVERE, null, ex);
       }
       return instance;
    }

    public MenuControlador(JMenuItem menuItem, Class ventana, boolean maximizado) {
        this.menuItem = menuItem;
        this.ventana = ventana;
        this.maximizado = maximizado;
    }
    
    

    public JMenuItem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(JMenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public Class getVentana() {
        return ventana;
    }

    public void setVentana(Class ventana) {
        this.ventana = ventana;
    }

    public boolean isMaximizado() {
        return maximizado;
    }

    public void setMaximizado(boolean maximizado) {
        this.maximizado = maximizado;
    }

    
   
   
}
