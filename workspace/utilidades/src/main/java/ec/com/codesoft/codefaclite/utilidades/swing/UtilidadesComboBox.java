/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.utilidades.swing;

import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.event.EventListenerList;

/**
 *
 * @author Carlos
 */
public class UtilidadesComboBox {
    
    public static void seleccionarItemPorCriterio(JComboBox comboBox,Object valor,CriterioCompararComboEnum criterio)
    {
        for (int i = 0; i < comboBox.getItemCount(); i++) 
        {
            Object puntoEmision= comboBox.getItemAt(i);
            Object comparador=criterio.objectoComparador(puntoEmision);
            if(comparador!=null)
            {
                if(comparador.equals(valor))
                {
                    comboBox.setSelectedIndex(i);
                    break;
                }
            }
        }
    }
    

    public static void crearCabeceraComboBox(JComboBox comboBox, String titulo) {
        //comboBox.setRenderer(new MyComboBoxRenderer(titulo));
        //comboBox.setSelectedIndex(-1);
        comboBox.setEditable(true);
        comboBox.setSelectedItem("Seleccione : ");
        comboBox.setEditable(false);

    }
    
    public static void eliminarTodosLosListener(JComboBox comboBox) 
    {
        // Obtenemos un array de ActionListener del JComboBox        
        ActionListener[] listeners = comboBox.getActionListeners();

        // Quitamos todos los ActionListener del JComboBox
        for (ActionListener listener : listeners) {
            comboBox.removeActionListener(listener);
        }
        
        ItemListener[] itemListenerList=comboBox.getItemListeners();
        for (ItemListener itemListener : itemListenerList) 
        {
            comboBox.removeItemListener(itemListener);
        }
        
    }

    public static void agregarActionListeners(JComboBox comboBox, ActionListener[] listeners) {
        // Agregamos cada ActionListener del arreglo al JComboBox
        for (ActionListener listener : listeners) 
        {
            comboBox.addActionListener(listener);
        }
    }
    
    public static void agregarItemListeners(JComboBox comboBox, ItemListener[] listeners) {
        // Agregamos cada ActionListener del arreglo al JComboBox
        for (ItemListener listener : listeners) 
        {
            comboBox.addItemListener(listener);
        }
    }
    
    public static void llenarComboBox(JComboBox comboBox,Object[] datos)
    {
        comboBox.removeAllItems();
        for (Object object : datos) 
        {
            comboBox.addItem(object);
        }
    }
    
    public static void llenarComboBox(JComboBox comboBox,List datos)
    {
        llenarComboBox(comboBox, datos, Boolean.FALSE);
    }
    
    public static void llenarComboBox(JComboBox comboBox,List datos,Boolean defectoNull)
    {
        llenarComboBox(comboBox, datos, Boolean.TRUE,defectoNull);
    }
    
    public static void llenarComboBox(JComboBox comboBox,List datos,Boolean limpiar,Boolean defectoNull)
    {
        if(limpiar)
        {
            comboBox.removeAllItems();
        }
        
        if(defectoNull)
        {
            comboBox.addItem(null);
        }
        
        for (Object object : datos) 
        {
            comboBox.addItem(object);
        }
    }
    
    /**
     * Metodo que me va permitir ejecutar un conjunto de acciones sobre el modelo del combo pero sin ejecutar listener de forma imnecesario
     * @param comboBox
     * @param procesoIf 
     */
    public static void ejecutarProcesoSinListener(JComboBox comboBox,ProcesoComboBoxIf procesoIf)
    {
        
        ActionListener[] listerTmp=comboBox.getActionListeners();        
        ItemListener[] itemListerTmp=comboBox.getItemListeners();
        UtilidadesComboBox.eliminarTodosLosListener(comboBox);
        procesoIf.proceso();
        UtilidadesComboBox.agregarActionListeners(comboBox, listerTmp);
        UtilidadesComboBox.agregarItemListeners(comboBox, itemListerTmp);
    }
    
    public interface CriterioCompararComboEnum<T>
    {
        public abstract Object objectoComparador(T objeto );
    }
    
    public interface ProcesoComboBoxIf
    {
        public void proceso();
    }
    
}
