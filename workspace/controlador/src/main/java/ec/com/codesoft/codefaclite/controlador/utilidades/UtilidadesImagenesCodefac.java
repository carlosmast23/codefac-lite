/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.utilidades;

import com.healthmarketscience.rmiio.RemoteInputStream;
import com.healthmarketscience.rmiio.RemoteInputStreamClient;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.directorio.DirectorioCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RecursosServiceIf;
import ec.com.codesoft.codefaclite.utilidades.imagen.UtilidadImagen;
import ec.com.codesoft.codefaclite.utilidades.rmi.UtilidadesRmi;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 *
 * @author CARLOS_CODESOFT
 */
public abstract class UtilidadesImagenesCodefac {
    
    public static ImageIcon buscarImagenServidor(Empresa empresa,String nombreArchivo)
    {
            RemoteInputStream risImagen =null;
            if(empresa.getImagenLogoPath()!=null)
            {
            try {
                RecursosServiceIf service = ServiceFactory.getFactory().getRecursosServiceIf();
                byte[] imagenSerializada = service.obtenerRecurso(empresa, DirectorioCodefac.IMAGENES, empresa.getImagenLogoPath());
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
