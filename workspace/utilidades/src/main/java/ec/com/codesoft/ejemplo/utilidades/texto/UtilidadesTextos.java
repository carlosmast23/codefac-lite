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
import java.util.logging.Level;
import java.util.logging.Logger;

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
}
