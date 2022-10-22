/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.utilidades.archivos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author CARLOS_CODESOFT
 */
public abstract class UtilidadesDirectorios {
    public static String buscarDirectorio()
    {
        JFileChooser f = new JFileChooser();
        f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        f.showSaveDialog(null);
        if (f.getSelectedFile() != null) {
            return f.getSelectedFile().getPath();
        }
        return null;
    }
    
    public static File crearArchivoEnDirectorio(String nombreDefecto)
    {
        JFileChooser f = new JFileChooser();
        f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);        
        f.showSaveDialog(null);
        
        
        if (f.getSelectedFile() != null) 
        {
            return new File(f.getSelectedFile()+"/"+nombreDefecto);
        }
        return null;        
    }
    
    public static File buscarArchivo(String nombreExtension ,String[] filtros)
    {
        JFileChooser f = new JFileChooser();
        f.setFileFilter(new FileNameExtensionFilter(nombreExtension,filtros));  
        //f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);        
        int seleccion=f.showDialog(null,"Abrir");
        if(seleccion==JFileChooser.APPROVE_OPTION)
        {
            return f.getSelectedFile();
        }
        return null;
    }
    
    public static void grabarObjectoArchivo(File archivo,Object objetoGrabar)
    {
        try {
            //File fileOriginal=UtilidadesDirectorios.crearArchivoEnDirectorio("disenio.codefac");
            FileOutputStream archivoNuevo=new FileOutputStream(archivo);
            ObjectOutputStream output = new ObjectOutputStream(archivoNuevo);
            if (output!=null)
            {
                //ComprobanteFisicoDisenio disenio=(ComprobanteFisicoDisenio) getCmbDocumento().getSelectedItem();
                output.writeObject(objetoGrabar);
                output.close();
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(UtilidadesDirectorios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UtilidadesDirectorios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static Object leerObjectoArchivo(File archivo)
    {
        //File archivo=UtilidadesDirectorios.buscarArchivo("Diseño Codefac",new String[]{"codefac"});
        if(archivo!=null)
        {
            FileInputStream fileInputStream = null;
            try {
                fileInputStream = new FileInputStream(archivo);
                //ObjectOutputStream output = new ObjectOutputStream(fileOutputStream);
                ObjectInputStream input = new ObjectInputStream (fileInputStream);
                //ComprobanteFisicoDisenio comprobanteFisicoDisenio = (ComprobanteFisicoDisenio) input.readObject();
                //cargarDatos(comprobanteFisicoDisenio);
                return input.readObject();
                } catch (FileNotFoundException ex) {
                Logger.getLogger(UtilidadesDirectorios.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(UtilidadesDirectorios.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(UtilidadesDirectorios.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
        return null;
    }
}
