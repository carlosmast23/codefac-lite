/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.corecodefaclite.views;

import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
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
    public ConsolaGeneral consola;
    
    /**
     * Variables para corregir bugs de los internal frames
     */
    public boolean sinAcciones=true;
    public boolean formularioCerrando=false;
    
    public static final String ESTADO_GRABAR="G";
    public static final String ESTADO_EDITAR="E";
    
    public String estadoFormulario;
    public abstract void grabar() throws ExcepcionCodefacLite;
    public abstract void editar() throws ExcepcionCodefacLite;
    public abstract void eliminar();
    public abstract void imprimir();
    public abstract void actualizar();
    
    /**
     * Metodo que se ejecuta despues de presionar el boton de buscar     * 
     * Es recomendable usar este metodo solo para buscar datos de la propia clase
     */
    public abstract void buscar() throws ExcepcionCodefacLite;
    
    /**
     * Este metodo se ejecutara despues de grabar o borrar los datos
     * Se deben programar solo metodos personalizados que no se puedan
     * programar con las etiquetas de validacion.
     * Ejemplo:
     *  - Seleccionar ele valor por defecto de un combo box
     *  - Limpiar una tabla de valor agregados
     * 
     * Nota: Este campo tambien sirve para limpiar las variables que se usaron
     * para dejar en blanco los valores
     */
    public abstract void limpiar();
    
    /**
     * Nombre del formulario 
     * @return nombre del formulario
     */
    public abstract String getNombre();
    
    /**
     * URL de la ayuda que se mostrara en la pantalla auxiliar cuando presionesn
     * el boton de ayuda
     * @return Link de la pagina que contiene las ayudas
     */
    public abstract String getURLAyuda();
    
    /**
     * Habilitar los botones que van a estar disponibles para la pantalla
     * Ejemplo:
    * <pre>
    * <code>
    * Map<Integer,Boolean> permisos=new HashMap<Integer,Boolean>();
    * permisos.put(GeneralPanelInterface.BOTON_NUEVO,true);
    * return permisos;
    * </code>
    * </pre>
     * @return 
     */
    public abstract Map<Integer,Boolean> permisosFormulario();
    
    
}
