/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.mb.crm;

import com.healthmarketscience.rmiio.RemoteInputStream;
import com.healthmarketscience.rmiio.RemoteInputStreamClient;
import ec.com.codesoft.codefaclite.controlador.utilidades.UtilidadesImagenesCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.directorio.DirectorioCodefac;
import ec.com.codesoft.codefaclite.utilidades.imagen.UtilidadImagen;
import ec.com.codesoft.codefaclite.utilidades.rmi.UtilidadesRmi;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.swing.ImageIcon;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author CARLOS_CODESOFT
 */
@ManagedBean
@ApplicationScoped
public class ImagenMb implements Serializable{
    
    public StreamedContent getImage() throws IOException {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            
            if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
                // So, we're rendering the HTML. Return a stub StreamedContent so that it will generate right URL.
                return new DefaultStreamedContent();
            }
            
            //String studentId = context.getExternalContext().getRequestParameterMap().get("empresaId");
            //Cargar los datos de la imagen
            Empresa empresa= ServiceFactory.getFactory().getEmpresaServiceIf().buscarPorIdentificacion("1704579372001");
            byte[] imagenSerializada = ServiceFactory.getFactory().getRecursosServiceIf().obtenerRecurso(empresa, DirectorioCodefac.IMAGENES, "logo.png");
            //byte[] imagenSerializada = service.obtenerRecurso(sucursal.getEmpresa(), DirectorioCodefac.IMAGENES, nombreImagen);
            RemoteInputStream risImagen = (RemoteInputStream) UtilidadesRmi.deserializar(imagenSerializada);
            if (risImagen != null) {
                InputStream imagenEjemploTmp = RemoteInputStreamClient.wrap(risImagen);
                //BufferedImage tmp1= UtilidadImagen.castInputStreamToBufferedImage(imagenEjemploTmp);
                StreamedContent imagenEjemplo= new DefaultStreamedContent(imagenEjemploTmp);
                return imagenEjemplo;
                //DefaultStreamedContent(imagenEjemploTmp, new MimetypesFileTypeMap().getContentType(imagenEjemploTmp));
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ImagenMb.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
   
    public StreamedContent getImageProducto() throws IOException 
    {
        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the HTML. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        }
        
        //String productoId=context.getExternalContext().getRequestParameterMap().get("productoId");
        String imagenPath=context.getExternalContext().getRequestParameterMap().get("imagenPath");
        String empresaId=context.getExternalContext().getRequestParameterMap().get("empresaId"); 
        
        //Producto producto=ServiceFactory.getFactory().getProductoServiceIf().buscarPorId(Long.parseLong(productoId));
        Empresa empresa=ServiceFactory.getFactory().getEmpresaServiceIf().buscarPorId(Long.parseLong(empresaId));
        
        //String imagenPath=producto.getImagen();
        InputStream inputStream= UtilidadesImagenesCodefac.buscarImagenServidorInputStream(empresa, imagenPath);
        StreamedContent imagenEjemplo= new DefaultStreamedContent(inputStream);
        return imagenEjemplo;
    }
    
}
