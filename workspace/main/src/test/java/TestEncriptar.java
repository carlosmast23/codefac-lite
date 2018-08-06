
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.utilidades.seguridad.UtilidadesEncriptar;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Carlos
 */
public class TestEncriptar {
    public static void main(String[] args) {
        String datosEncriptado=UtilidadesEncriptar.encriptar("1702mgwk",ParametrosSistemaCodefac.LLAVE_ENCRIPTAR);
        System.out.println(datosEncriptado); 
   }
}
