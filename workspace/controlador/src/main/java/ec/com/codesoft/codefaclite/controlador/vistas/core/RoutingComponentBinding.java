/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vistas.core;

import ec.com.codesoft.codefaclite.controlador.vistas.core.components.ButtonBinding;
import ec.com.codesoft.codefaclite.controlador.vistas.core.components.ButtonBindingImp;
import ec.com.codesoft.codefaclite.controlador.vistas.core.components.ComboBoxBinding;
import ec.com.codesoft.codefaclite.controlador.vistas.core.components.ComboBoxBindingImp;
import ec.com.codesoft.codefaclite.controlador.vistas.core.components.ComponentBindingAbstract;
import ec.com.codesoft.codefaclite.controlador.vistas.core.components.TextFieldBindingImp;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class RoutingComponentBinding {
    
    public static Map<Class,ComponentBindingAbstract> routingMap=new HashMap<Class, ComponentBindingAbstract>();
    static
    {
        routingMap.put(TextFieldBinding.class,new TextFieldBindingImp());
        routingMap.put(ComboBoxBinding.class,new ComboBoxBindingImp());
        routingMap.put(ButtonBinding.class,new ButtonBindingImp());        
    }
    
}
