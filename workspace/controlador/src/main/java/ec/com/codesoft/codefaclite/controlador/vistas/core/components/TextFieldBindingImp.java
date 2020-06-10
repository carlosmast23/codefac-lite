/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vistas.core.components;

import ec.com.codesoft.codefaclite.controlador.vistas.core.TextFieldBinding;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTextField;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class TextFieldBindingImp extends ComponentBindingAbstract<JTextField,TextFieldBinding>{

    

    public ComponentBindingIf value=new ComponentBindingIf<String,TextFieldBinding>() 
    {
        @Override
        public void getAccion(String nombrePropiedadControlador) {
            getComponente().addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {}

                @Override
                public void focusLost(FocusEvent e) {
                    setValoresAlControlador(getComponente().getText(),nombrePropiedadControlador);
                }
            });
            
        }

        @Override
        public void setAccion(String value) {
            getComponente().setText(value);
        }

        @Override
        public String getNombrePropiedadControlador(TextFieldBinding anotacion) {
            return anotacion.value();
        }
        
    };
    
    
    
    public ComponentBindingIf editable=new ComponentBindingIf<Boolean,TextFieldBinding>() {
        @Override
        public void getAccion(String nombrePropiedadControlador) {
            getComponente().addPropertyChangeListener("editable",new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    setValoresAlControlador(getComponente().isEditable(),nombrePropiedadControlador);
                }
            });
        }

        @Override
        public void setAccion(Boolean value) {
            getComponente().setEditable(value);
        }

        @Override
        public String getNombrePropiedadControlador(TextFieldBinding componente) {
            return componente.editable();
        }
            
    };
    
    
    public ComponentBindingIf actionListener=new ComponentBindingIf<Boolean,TextFieldBinding>() {
        @Override
        public void getAccion(String nombreMetodo) {
            getComponente().addPropertyChangeListener("editable",new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    ejecutarMetodoControlador(nombreMetodo);
                }
            });
        }

        @Override
        public void setAccion(Boolean value) {
            getComponente().setEditable(value);
        }

        @Override
        public String getNombrePropiedadControlador(TextFieldBinding componente) {
            return componente.actionListener();
        }
            
    }; 
    
    
    @Override
    public void getAccionesComponente(List<ComponentBindingIf> lista) {        
        lista.add(value);
        lista.add(editable);
        lista.add(actionListener);
    }

    @Override
    public Class<JTextField> getClassComponente() {
        return JTextField.class;
    }

    
}
