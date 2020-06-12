/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vistas.core.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class ButtonBindingImp extends ComponentBindingAbstract<JButton,ButtonBinding> {

    private ComponentBindingIf actionListener=new ComponentBindingIf<Object,ButtonBinding>() {
        @Override
        public void getAccion(String nombreMetodo) {
            getComponente().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ejecutarMetodoControlador(nombreMetodo);
                }
            });
        }

        @Override
        public void setAccion(Object value) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public String getNombrePropiedadControlador(ButtonBinding componente) {
            return componente.actionListener();
        }
    };
    
    @Override
    public void getAccionesComponente(List<ComponentBindingIf> lista) {
        lista.add(actionListener);
    }

    @Override
    public Class<JButton> getClassComponente() {
        return JButton.class;
    }
    
}
