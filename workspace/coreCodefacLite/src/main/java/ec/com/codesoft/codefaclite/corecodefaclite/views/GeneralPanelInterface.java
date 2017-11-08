/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.corecodefaclite.views;

import ec.com.codesoft.codefaclite.corecodefaclite.validation.ConsolaGeneral;
import java.util.Map;
import javax.swing.JPanel;

/**
 *
 * @author Carlos
 */
public abstract class GeneralPanelInterface extends javax.swing.JInternalFrame
{
    public static final int BOTON_GRABAR = 1;
    public static final int BOTON_ELIMINAR = 2;
    public static final int BOTON_IMPRIMIR = 3;
    public static final int BOTON_AYUDA = 4;
    public static final int BOTON_NUEVO = 5;
    public static final int BOTON_REFRESCAR = 6;
    public static final int BOTON_BUSCAR = 7;
    
    public InterfazComunicacionPanel panelPadre;
    public ConsolaGeneral consola=new ConsolaGeneral();
    
    public static final String ESTADO_GRABAR="G";
    public static final String ESTADO_EDITAR="E";
    
    public String estadoFormulario;
    public abstract void grabar();
    public abstract void editar();
    public abstract void eliminar();
    public abstract void imprimir();
    public abstract void actualizar();
    public abstract void buscar();
    public abstract String getNombre();
    public abstract String getURLAyuda();
    
    public abstract Map<Integer,Boolean> permisosFormulario();
    
    
}
