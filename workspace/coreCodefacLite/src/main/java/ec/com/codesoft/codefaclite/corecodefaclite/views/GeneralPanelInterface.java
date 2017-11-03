/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.corecodefaclite.views;

import java.util.Map;
import javax.swing.JPanel;

/**
 *
 * @author Carlos
 */
public abstract class GeneralPanelInterface extends javax.swing.JInternalFrame
{
    public static final int BOTON_GRABAR = 1;
    public static final int BOTON_EDITAR = 2;
    public static final int BOTON_ELIMINAR = 3;
    public static final int BOTON_IMPRIMIR = 4;
    
    public InterfazComunicacionPanel panelPadre;
    
    public static final String ESTADO_GRABAR="G";
    
    public String Estado;
    public abstract void grabar();
    public abstract void editar();
    public abstract void eliminar();
    public abstract void imprimir();
    public abstract String getNombre();
    
    public abstract Map<Integer,Boolean> permisosFormulario();
    
    
}
