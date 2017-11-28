/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.ejemplo.utilidades.varios;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

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
}
