/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.ejemplo.utilidades.list;

import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Carlos
 */
public abstract class UtilidadesLista {
    public static String castListToString(List lista,String caracter)
    {
        return StringUtils.join(lista,caracter);
    }
}
