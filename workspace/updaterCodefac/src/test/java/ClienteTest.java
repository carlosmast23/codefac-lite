
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
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
public class ClienteTest {
    public static void main(String[] args) {
        try {
            String pid="416";
            
            List<String> comando = Arrays.asList(
                    "tasklist ",
                    "/fi",
                    "\"pid eq "+pid+" \"");
            //Ejecuta el comando como si estuviera directamente ejecutando en el cmd del sistema
            ProcessBuilder pb = new ProcessBuilder(comando);
            Process proceso=pb.start();
            InputStream is = proceso.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            
            String line;
            String cadena="";
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                cadena+=line;
            }
            
            cadena=cadena.toLowerCase();
            if(cadena.indexOf(pid)>=0  && cadena.indexOf("pid")>=0)
            {
                System.out.println("Proceso Vivo");
            }
            else
            {
                System.out.println("Proceso muerto");
            }
            
        } catch (IOException ex) {
            Logger.getLogger(ClienteTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
