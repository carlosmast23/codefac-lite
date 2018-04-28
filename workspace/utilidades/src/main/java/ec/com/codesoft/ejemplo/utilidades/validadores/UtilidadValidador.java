/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.ejemplo.utilidades.validadores;

/**
 *
 * @author Carlos
 */
public abstract class UtilidadValidador {
    
    public static String normalizarTexto(String s) {
        // Cadena de caracteres original a sustituir.
        String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ[]{}^\\-¿";
        // Cadena de caracteres ASCII que reemplazarán los originales.
        String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC()()    ";
        String output = s;
        for (int i = 0; i < original.length(); i++) {
            // Reemplazamos los caracteres especiales.
            output = output.replace(original.charAt(i), ascii.charAt(i));
        }

        output = output.replaceAll("\r\n", " ").replaceAll("\r", " ").replaceAll("\n", " ");
        String regex = "[^0-9A-Za-z.,;:_()&= ]";
        return output.replaceAll(regex, "");
    }
    
}
