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
    
    public static Map<Class,Class> routingMap=new HashMap<Class, Class>();
    static
    {
        routingMap.put(TextFieldBinding.class,TextFieldBindingImp.class);
        routingMap.put(ComboBoxBinding.class,ComboBoxBindingImp.class);
        routingMap.put(ButtonBinding.class,ButtonBindingImp.class);        
    }
    
}
