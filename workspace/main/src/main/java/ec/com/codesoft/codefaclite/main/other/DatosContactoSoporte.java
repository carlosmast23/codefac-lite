/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.other;

import ec.com.codesoft.codefaclite.main.archivos.ArchivoConfiguracionesCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;

/**
 * Datos de contacto de soporte (telefono, correo, web) que se muestran en toda la aplicacion.
 * Nunca se deben usar directamente las constantes de ParametrosSistemaCodefac (quedan congeladas en el .jar
 * al compilar); en su lugar usar estos metodos, que devuelven el valor cacheado localmente si
 * ActualizarVersionCodefac ya lo descargo y actualizo en un arranque anterior, o el valor compilado por
 * defecto como respaldo si todavia no hay nada cacheado (instalacion nueva, sin internet, etc).
 */
public class DatosContactoSoporte {

    public static String obtenerTelefonoSoporte() {
        String valor = ArchivoConfiguracionesCodefac.getInstance().obtenerValor(ArchivoConfiguracionesCodefac.CAMPO_TELEFONO_SOPORTE);
        return !UtilidadesTextos.verificarNullOVacio(valor) ? valor : ParametrosSistemaCodefac.NUMERO_SOPORTE;
    }

    public static String obtenerCorreoSoporte() {
        String valor = ArchivoConfiguracionesCodefac.getInstance().obtenerValor(ArchivoConfiguracionesCodefac.CAMPO_CORREO_SOPORTE);
        return !UtilidadesTextos.verificarNullOVacio(valor) ? valor : ParametrosSistemaCodefac.CORREO_SOPORTE;
    }

    public static String obtenerWebSoporte() {
        String valor = ArchivoConfiguracionesCodefac.getInstance().obtenerValor(ArchivoConfiguracionesCodefac.CAMPO_WEB_SOPORTE);
        return !UtilidadesTextos.verificarNullOVacio(valor) ? valor : ParametrosSistemaCodefac.WEB_SOPORTE;
    }
}
