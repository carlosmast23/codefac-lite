/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.utilidades.varios;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author CARLOS_CODESOFT
 */
public abstract class UtilidadesSwing {
    
    public static JFileChooser getJFileChooserPreBuild(String title,String nombreExtension,String[] filter)
    {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setDialogTitle(title);
        jFileChooser.setFileFilter(new FileNameExtensionFilter(nombreExtension,filter));  
        return jFileChooser;
    }
    
}
