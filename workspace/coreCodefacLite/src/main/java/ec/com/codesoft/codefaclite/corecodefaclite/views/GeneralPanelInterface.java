/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.corecodefaclite.views;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ObserverUpdateInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.validation.ConsolaGeneral;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import org.bouncycastle.crypto.tls.SessionParameters;

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
    public boolean modoDialogo=false;
    /**
     * Fomulario propietari
     */
    public ObserverUpdateInterface formOwner;
    
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
    
    /**
     * Metodo que establece si deseas controlar el ciclo de vida de la pantalla
     * o que se controle automaticamente.
     * Ejemplo: Despues de guardar se limpian automaticamentes los datos
     */
    public Boolean cicloVida=true;
    
    
    /**
     * Variable que permite grabar los componentes que estaban desactivos
     * para cuando se vuelvan a activar el formulario conserver el estado
     * de los que estaban desactivados antes de desactivar todo
     */
    private List<Component> componentesTemporales;
    
    /**
     * Metodo para establecer un icono por defecto cuando este cargando la pantalla
     */
    
        
    public void estadoCargando()
    {
            componentesTemporales=new ArrayList<Component>();
            /*
            java.awt.Toolkit toolkit = java.awt.Toolkit.getDefaultToolkit();
            InputStream is=RecursoCodefac.IMAGENES_ICONOS.getResourceInputStream("gif/cargando.gif");
            Image image = ImageIO.read(is);
            ImageIcon imageIcon=new ImageIcon(image);
            int width=imageIcon.getIconWidth()/2;
            int height=imageIcon.getIconHeight()/2;
            Cursor cursor=toolkit.createCustomCursor(image, new Point(0,0),"");*/
            
            setEnableRec(this,false);
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            //this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            //toolkit.createCustomCursor(image , new Point(width,height), "");

    }
    
    private void setEnableRec(Component container, boolean enable){
        
        if(!container.equals(this)) //para que no desactive el panel padre
        {

            if (!enable) {
                if (!container.isEnabled()) {
                    componentesTemporales.add(container);
                }
                container.setEnabled(enable);
            }
            else
            {
                if(!componentesTemporales.contains(container))
                    container.setEnabled(enable);
            }
        }
        


    try {
        Component[] components= ((Container) container).getComponents();
        for (int i = 0; i < components.length; i++) {
           
            setEnableRec(components[i], enable);
        }
    } catch (ClassCastException e) {

    }
}

    public void estadoNormal()
    {
        setEnableRec(this,true);
        this.setCursor(Cursor.getDefaultCursor());
        //this.setVisible(true);
    }
    
    
    
}
