
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.utilidades.seguridad.UtilidadesEncriptar;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        try {
            //String datosEncriptado=UtilidadesEncriptar.encriptar("2020-07-13",ParametrosSistemaCodefac.LLAVE_ENCRIPTAR);
            //System.out.println(datosEncriptado);
            //System.out.println(UtilidadesEncriptar.desencriptar("1zP71DO+Pj+jLHF+c4sAdQ==", ParametrosSistemaCodefac.LLAVE_ENCRIPTAR));
            String datosSinEncriptar=UtilidadesEncriptar.desencriptar("JJqLqIj4MuvzWU+DD7I6Aw==", ParametrosSistemaCodefac.LLAVE_ENCRIPTAR);
            System.out.println(datosSinEncriptar);
        } catch (Exception ex) {
            Logger.getLogger(TestEncriptar.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
}
