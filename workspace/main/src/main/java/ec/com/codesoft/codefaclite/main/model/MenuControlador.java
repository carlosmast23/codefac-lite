/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.model;

import ec.com.codesoft.codefaclite.controlador.aplicacion.ControladorCodefacInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CategoriaMenuEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloCodefacEnum;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * @author Carlos
 */
public class MenuControlador {

    private Class<GeneralPanelInterface> ventana;
    private boolean maximizado;
    private Object instance;

    private ModuloCodefacEnum modulo;
    private CategoriaMenuEnum categoriaMenu;

    private JMenuItem jmenuItem;

    /**
     * Lista de permisos para los modulos permitidos Nota: Si no se setea ningun
     * modulo es permitido para todos los modulos
     */
    private ModuloCodefacEnum[] modulosPermitidos;

    public MenuControlador(Class<GeneralPanelInterface> ventana, ModuloCodefacEnum modulo, CategoriaMenuEnum categoriaMenu) {
        this.ventana = ventana;
        this.modulo = modulo;
        this.categoriaMenu = categoriaMenu;
        this.modulosPermitidos = modulosPermitidos;
    }

    public MenuControlador(Class ventana, ModuloCodefacEnum modulo, CategoriaMenuEnum categoriaMenu, boolean maximizado) {
        this.ventana = ventana;
        this.modulo = modulo;
        this.categoriaMenu = categoriaMenu;
        this.maximizado = maximizado;
    }

    public MenuControlador(Class ventana, ModuloCodefacEnum modulo, CategoriaMenuEnum categoriaMenu, boolean maximizado, ModuloCodefacEnum[] modulosPermitidos) {
        this.ventana = ventana;
        this.modulo = modulo;
        this.categoriaMenu = categoriaMenu;
        this.maximizado = maximizado;
        this.modulosPermitidos = modulosPermitidos;
    }

    /**
     * Devuelve una instancia segun la clase grabada
     *
     * @return
     */
    public GeneralPanelInterface getInstance() {
        try {

            if (instance == null) {
                instance = this.ventana.getConstructor().newInstance();
            }

            return (GeneralPanelInterface) instance;

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

    public Object createNewInstance() {
        try {
            instance = this.ventana.getConstructor().newInstance();
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

    public Boolean verificarPermisoModulo(ModuloCodefacEnum moduloVerificar) {
        
        if(modulo.equals(moduloVerificar))
        {
            return true;
        }

        return false;
    }

    public Boolean verificarPermisoModuloAdicional(Map<ModuloCodefacEnum, Boolean> modulos) {
        //Si no existe ningun dato en modulo permitidos asumo que tiene acceso para todos los modulos

        for (Map.Entry<ModuloCodefacEnum, Boolean> entry : modulos.entrySet()) {
            ModuloCodefacEnum moduloSistema = entry.getKey();
            Boolean value = entry.getValue();

            if (value) //Verificar solo para los modulos activos
            {
                //Verifico si indirectamente otro modulo necesita de esta pantalla
                for (ModuloCodefacEnum modulosPermitido : modulosPermitidos) {
                    if (moduloSistema.equals(modulosPermitido)) {
                        return true;
                    }
                }

            }

        }
        return false;
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

    public ModuloCodefacEnum[] getModulosPermitidos() {
        return modulosPermitidos;
    }

    public void setModulosPermitidos(ModuloCodefacEnum[] modulosPermitidos) {
        this.modulosPermitidos = modulosPermitidos;
    }

    public ModuloCodefacEnum getModulo() {
        return modulo;
    }

    public void setModulo(ModuloCodefacEnum modulo) {
        this.modulo = modulo;
    }

    public CategoriaMenuEnum getCategoriaMenu() {
        return categoriaMenu;
    }

    public void setCategoriaMenu(CategoriaMenuEnum categoriaMenu) {
        this.categoriaMenu = categoriaMenu;
    }

    public JMenuItem getJmenuItem() {
        return jmenuItem;
    }

    public void setJmenuItem(JMenuItem jmenuItem) {
        this.jmenuItem = jmenuItem;
    }

}
