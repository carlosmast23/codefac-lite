/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.ejemplo.utilidades.varios;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public abstract class UtilidadVarios {
    public static void abrirArchivo(String path)
    {
        try {
            File file = new File(path);
            Desktop.getDesktop().open(file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static String getStringHtmltoUrl(InputStream input)
    {
        try {
            InputStreamReader reader = new InputStreamReader(input);
            BufferedReader br = new BufferedReader(reader);
            String line = null;
            String htmlText = "";
            while ((line = br.readLine()) != null) {
                htmlText += line;
            }
            return htmlText;
        } catch (IOException ex) {
            Logger.getLogger(UtilidadVarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
}
