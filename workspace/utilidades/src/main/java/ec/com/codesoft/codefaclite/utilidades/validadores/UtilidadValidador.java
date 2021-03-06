/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.utilidades.validadores;

/**
 *
 * @author Carlos
 */
public abstract class UtilidadValidador {
    
    
    public static String normalizarTexto(String s) {
        
        //Si la variable es nula devolver en blanco
        if(s==null)
            return "";
        
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
    
    /**
     * Funcion que me permite quitar caracteres especiales de una cadenas obiando simbolos como @ y .
     * @param s
     * @return 
     */
    public static String normalizarTextoCorreo(String s) {

        //()<>@,;:"[]ç%&
        // Cadena de caracteres original a sustituir.
        //String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ[]{}^\\-¿";
        String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
        // Cadena de caracteres ASCII que reemplazarán los originales.
        String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
        String output = s;
        for (int i = 0; i < original.length(); i++) {
            // Reemplazamos los caracteres especiales.
            output = output.replace(original.charAt(i), ascii.charAt(i));
        }
        return output;
    }
    
    /**
     * Funcion que me permite reemplazar caracteres no imprimibles en UTF-8
     */
    public static String normalizarDescripcionDetalleFacura(String s)
    {
        //String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ[]{}^\\-¿";
        String original = "ÁÍ";
        // Cadena de caracteres ASCII que reemplazarán los originales.
        String ascii = "ÀÌ";
        String output = s;
        for (int i = 0; i < original.length(); i++) {
            // Reemplazamos los caracteres especiales.
            output = output.replace(original.charAt(i), ascii.charAt(i));
        }
        return output;
    }
    
}
