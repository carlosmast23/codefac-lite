/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.utilidades;

import com.healthmarketscience.rmiio.RemoteInputStream;
import com.healthmarketscience.rmiio.RemoteInputStreamClient;
import com.healthmarketscience.rmiio.SimpleRemoteInputStream;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.directorio.DirectorioCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RecursosServiceIf;
import ec.com.codesoft.codefaclite.utilidades.imagen.UtilidadImagen;
import ec.com.codesoft.codefaclite.utilidades.rmi.UtilidadesRmi;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author CARLOS_CODESOFT
 *///TODO: Terminar de implementar la misma logica para los productos
public abstract class UtilidadesImagenesCodefac {
    
    public static JFileChooser crearFileChooserImagenes()
    {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setDialogTitle("Elegir archivo");
        jFileChooser.setFileFilter(new FileNameExtensionFilter("Logo Imagen", "png", "jpg","jpeg","bmp","gif"));  
        return jFileChooser;
    }
    
    public static void moverArchivo(Path origen,Empresa empresa) {
        try {
            //Verifica que solo cuando exista un origen y destino exista se copien los datos
            if (origen == null) {
                return;
            }
            
            SimpleRemoteInputStream istream = new SimpleRemoteInputStream(
                    new FileInputStream(origen.toFile()));
            
            ServiceFactory.getFactory().getRecursosServiceIf().uploadFileServer(DirectorioCodefac.IMAGENES, istream,origen.getFileName().toString(),empresa);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(UtilidadesImagenesCodefac.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(UtilidadesImagenesCodefac.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    public static Path buscarImagen()
    {
        JFileChooser jFileChooser= crearFileChooserImagenes();
        int seleccion = jFileChooser.showDialog(null, "Abrir");
        switch (seleccion) {
            case JFileChooser.APPROVE_OPTION:
                File archivoSeleccionado=jFileChooser.getSelectedFile();
                return archivoSeleccionado.toPath();
                
            case JFileChooser.CANCEL_OPTION:

                break;
            case JFileChooser.ERROR_OPTION:

                break;
        }
        return null;
    }
    
    public static ImageIcon buscarImagenServidor(Empresa empresa,String nombreArchivo)
    {
            RemoteInputStream risImagen =null;
            if(!UtilidadesTextos.verificarNullOVacio(nombreArchivo))
            {
            try {
                RecursosServiceIf service = ServiceFactory.getFactory().getRecursosServiceIf();
                byte[] imagenSerializada = service.obtenerRecurso(empresa, DirectorioCodefac.IMAGENES, nombreArchivo);
                risImagen = (RemoteInputStream) UtilidadesRmi.deserializar(imagenSerializada);                
            } catch (IOException ex) {
                Logger.getLogger(UtilidadesImagenesCodefac.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(UtilidadesImagenesCodefac.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
            //ImageIcon imageIcon=null;
            InputStream inputStream =null;
            if (risImagen != null) {
            try {
                inputStream = RemoteInputStreamClient.wrap(risImagen);
                //imageIcon=UtilidadImagen.castInputStreamToImageIcon(inputStream);                
            } catch (IOException ex) {
                Logger.getLogger(UtilidadesImagenesCodefac.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
            else
            {
                inputStream = RecursoCodefac.IMAGENES_GENERAL.getResourceInputStream(ParametrosSistemaCodefac.ComprobantesElectronicos.LOGO_SIN_FOTO);
                //imageIcon=UtilidadImagen.castInputStreamToImageIcon(inputStream);
            }
            
            BufferedImage imagenTmp= UtilidadImagen.resizeImage(inputStream, 200, 200);
            ImageIcon imageIcon=new ImageIcon(imagenTmp);
            return imageIcon;
    }
    
}
