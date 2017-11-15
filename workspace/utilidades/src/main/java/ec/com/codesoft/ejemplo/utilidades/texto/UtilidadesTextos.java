/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.ejemplo.utilidades.texto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author Carlos
 */
public abstract class UtilidadesTextos {
    public static String getStringURLFile(InputStream input)
    {   
        String text = "";
        try {
            InputStreamReader reader = new InputStreamReader(input);
            BufferedReader br = new BufferedReader(reader);
            String line = null;        
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                text += line;
            }
        } catch (IOException ex) {
            Logger.getLogger(UtilidadesTextos.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
        return text;
    }
    
    public static String llenarCarateresDerecha(String texto,Integer cantidad,String caracter)
    {
        return StringUtils.rightPad(texto, cantidad, caracter);
    }
    
    public static String llenarCarateresIzquierda(String texto,Integer cantidad,String caracter)
    {
        return StringUtils.leftPad(texto, cantidad, caracter);
    }
    
    public static String calcularModulo11(String clave) {
        int digitoVerificador = 0;
        int[] secuenciaMultiplicadores = {2, 3, 4, 5, 6, 7};
        int indiceMultiplicador = 0;
        int longitudClave = clave.length();
        int suma = 0;
        for (int i = longitudClave - 1; i >= 0; i--) {
            int c = Integer.parseInt(String.valueOf(clave.charAt(i)));
            suma += (c * (secuenciaMultiplicadores[indiceMultiplicador]));
            if (indiceMultiplicador == 5) {
                indiceMultiplicador = -1;
            }
            indiceMultiplicador++;
        }
        digitoVerificador = 11 - (suma % 11);
        if (digitoVerificador == 11) {
            digitoVerificador = 0;
        } else if (digitoVerificador == 10) {
            digitoVerificador = 1;
        }
        return new Integer(digitoVerificador).toString();
    }
    
    public static String castVectorToString(Vector<String> datos)
    {
        String cadena="";
        for (String dato : datos) 
        {
            cadena+=dato;
        }
        return cadena;
    }
}
