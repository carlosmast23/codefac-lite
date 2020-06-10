/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vistas.core.components;

import ec.com.codesoft.codefaclite.controlador.vistas.core.TextFieldBinding;
import ec.com.codesoft.codefaclite.utilidades.swing.UtilidadesComboBox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JTextField;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class ComboBoxBindingImp extends ComponentBindingAbstract<JComboBox,ComboBoxBinding>{

    private ComponentBindingIf source=new ComponentBindingIf<List,ComboBoxBinding>() {
        @Override
        public void getAccion(String nombrePropiedadControlador) {
            
        }

        @Override
        public void setAccion(List value) {
            UtilidadesComboBox.llenarComboBox(getComponente(), value);
        }

        @Override
        public String getNombrePropiedadControlador(ComboBoxBinding componente) {
            return componente.source();
        }
       
            
    };
    
    
    private ComponentBindingIf valueSelect=new ComponentBindingIf<Object,ComboBoxBinding>() {
        @Override
        public void getAccion(String nombrePropiedadControlador) {
            getComponente().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setValoresAlControlador(getComponente().getSelectedItem(), nombrePropiedadControlador);
                }
            });
        }

        @Override
        public void setAccion(Object value) {
            //UtilidadesComboBox.llenarComboBox(getComponente(), value);
        }

        @Override
        public String getNombrePropiedadControlador(ComboBoxBinding componente) {
            return componente.valueSelect();
        }
       
            
    };
    
    @Override
    public void  getAccionesComponente(List<ComponentBindingIf> lista) {
        lista.add(source);
        lista.add(valueSelect);
    }

    @Override
    public Class<JComboBox> getClassComponente() {
        return JComboBox.class;
    }

    
    
}
