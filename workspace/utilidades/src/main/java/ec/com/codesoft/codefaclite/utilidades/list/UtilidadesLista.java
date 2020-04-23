/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.utilidades.list;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Carlos
 */
public abstract class UtilidadesLista {
    public static String castListToString(List lista,String caracter)
    {
        if(lista==null || lista.size()==0)
        {
            return "";
        }
        
        return StringUtils.join(lista,caracter);
    }
    
    public static String castListToString(List lista,String caracter,CastListInterface interfaz)
    {
        List<String> datos=new ArrayList<String>();
        for (Object object : lista) {
            String textoNuevo=interfaz.getString(object);
            datos.add(textoNuevo);
        }
        return StringUtils.join(datos,caracter);
    }
    
    public interface CastListInterface<T>
    {
        public String getString(T dato);
    }
}
