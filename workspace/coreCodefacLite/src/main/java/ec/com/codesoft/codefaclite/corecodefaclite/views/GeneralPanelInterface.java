/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.corecodefaclite.views;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
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
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;
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
    
    /**
     * Rererencia que va a almacenar cual es el dialogo principal  de la aplicacion para interfacturar con los campos de busqueda
     */
   // private BuscarDialogoModel dialogoBusqueda;
    
    public InterfazComunicacionPanel panelPadre;
    public ConsolaGeneral consola;
    
    /**
     * Variables para corregir bugs de los internal frames
     */
    public boolean sinAcciones=false;
    public boolean formularioCerrando=false;
    public boolean modoDialogo=false;
    
    /**
     * Evento propietario que contiene el metodo para poder utilizar dialogos
     */
    public ObserverUpdateInterface formOwner;
    
    /**
     * Variable que setea cual fue el formulario padre para devolver el focus
     * //TODO: Ver alguna forma de optimizar esta parte
     */
    public GeneralPanelInterface formOwnerFocus;
    
    public static final String ESTADO_GRABAR="G";
    public static final String ESTADO_EDITAR="E";
    
    /**
     * Variable que contiene el estado actual del formulario para que el desarrollado pueda consultar
     */
    public String estadoFormulario;
    
    /**
     * Lista que me permite grabar componentes que se desean excluir para no tomar en cuenta para la validacion de no salir si existen datos ingresados 
     */
    public List<JComponent> listaExclusionComponentes;
    
    /**
     * Variable para controlar si desea activar o desactivar esta validacion de cuando existen datos ingresados
     * Esto deberia desactivarse por ejemplo para reportes
     */
    public Boolean validacionDatosIngresados;
    
    /**
     * Variable de utilidad solo para facilitar el trabajo para obtener la referencia al objecto actual desde una clase interna o anonima
     */
    public GeneralPanelInterface formularioActual;
    
    /**
     * Map que me permite tener almacenados si algunos campos tienen valores por defecto para la validacion de datos ingresados
     */
    public Map<JComponent,Object> mapDatosIngresadosDefault;

    public GeneralPanelInterface() {
        this.formularioActual=this;
        this.listaExclusionComponentes=new ArrayList<JComponent>();
        this.mapDatosIngresadosDefault=new HashMap<JComponent,Object>();
        this.validacionDatosIngresados=true;
        
    }
    
    
    /**
     * Este metodo tiene el objetivo de funcionar despues de construir el objeto para interactuar con las variables inyectadas en el formulario
     * por ejemplo con las variables de session , o con funcionalidades del formulario padre
     */
    public abstract void iniciar() throws ExcepcionCodefacLite,RemoteException;
    /**
     * Metodo que se debe implementar si se desea controlar el proceso del boton nuevo
     */
    public abstract void nuevo() throws ExcepcionCodefacLite,RemoteException;
    public abstract void grabar() throws ExcepcionCodefacLite,RemoteException;
    public abstract void editar() throws ExcepcionCodefacLite,RemoteException;
    public abstract void eliminar() throws ExcepcionCodefacLite,RemoteException;
    public abstract void imprimir() throws ExcepcionCodefacLite,RemoteException;
    public abstract void actualizar() throws ExcepcionCodefacLite,RemoteException;
    
    /**
     * Metodo que se ejecuta despues de presionar el boton de buscar     * 
     * Es recomendable usar este metodo solo para buscar datos de la propia clase
     */
    public void buscar() throws ExcepcionCodefacLite,RemoteException
    {
        //Esto lo pongo de esta manera porque Codefac construye automaticamente el metodo buscar desde el controlador
        //y ya no ahy necesidad de implementar, pero si en ultimo caso el usuario tiene la necesidad de escribir su
        //metodo personalizado de busqueda puede sobrescribir este metodo 
        throw new UnsupportedOperationException("Metodo no implementado"); //To change body of generated methods, choose Tools | Templates.
    }
    
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
    //public abstract String getNombre();
    
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
     * Implementa una lista de los permisos de esa pantalla para cada tipo de perfil
     * existentes , si la ventana no implementa ningun metodo o recibe null , automaticamente
     * habilita la ventana para todos los tipos de perfiles
     * @return 
     */
    public abstract List<String> getPerfilesPermisos();
    
    /**
     * Obtiene el dialogo que se va a usar para consultar los datos de
     * formulario
     *
     * @return
     */
    public abstract BuscarDialogoModel obtenerDialogoBusqueda();

    /**
     * Metodo donde se establece como se van a cargar los datos en pantalla
     * despues que se abra el dialogo
     *
     * @param entidad
     */
    public abstract void cargarDatosPantalla(Object entidad);
    
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

            setEnableRec(this,false);
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));


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

     /**
     * Metodo que me permite saber si existe datos ingresados y no existe nongun dato y puedo salir sin grabar
     * @return 
     */
    public boolean salirSinGrabar() {
        //Esta validacion solo debe funcionar en el estado de grabar y si esta activa la opcion de validar
        if(estadoFormulario!=ESTADO_GRABAR || !validacionDatosIngresados)
        {
            return true;
        }
        
        //TODO: Ver si se puede validar para otros componentes como combox
        Field[] campos = getClass().getSuperclass().getDeclaredFields();
        for (Field campo : campos) {
            
            if (campo.getType().equals(JTextField.class) || campo.getType().equals(JTextArea.class)) {
                try {
                    campo.setAccessible(true);
                    Object campoSeleccionado= campo.get(this);
                    
                    //Verificar si el campo no se encuentra dentro de la lista de exclusiones
                    if(listaExclusionComponentes.contains(campoSeleccionado))
                    {
                        continue;
                    }
                    
                    JTextComponent campoTexto = (JTextComponent) campoSeleccionado;
                    Object valorDefecto=mapDatosIngresadosDefault.get(campoTexto);
                    String valorCampo=campoTexto.getText().trim();
                    
                    //Verifico si tiene ingresado cualquier valor que no sea por defecto significa que cambio el valor
                    if(valorDefecto!=null)
                    {
                        if (!valorDefecto.toString().equals(valorCampo) ) {
                            if(!valorCampo.equals(""))
                            {
                                return false;
                            }
                        }
                    }
                    else
                    {
                        if (!valorCampo.equals("")) {
                            return false;
                        }
                    }


                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(GeneralPanelInterface.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(GeneralPanelInterface.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return true;

    }    
    
    
}
