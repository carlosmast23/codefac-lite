/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.utilidades.xml;

import ec.com.codesoft.codefaclite.servidorinterfaz.ats.jaxb.AtsJaxb;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 *
 * @author Carlos
 */
public abstract class UtilidadesXml {
    
    public static String convertirDocumentToString(String path)
    {
        
        try {
            //XMLOutputter xmlOutput = new XMLOutputter();
            //xmlOutput.setFormat(Format.getPrettyFormat());
            //xmlOutput.output(document, new FileWriter("factura_fisica.jrxml"));
            //SAXBuilder builder = new SAXBuilder();
            //File xmlFile = new File(path);
            //InputStream inputStream=new FileInputStream(xmlFile);
            String content = new String ( Files.readAllBytes( Paths.get(path) ) );
            return content;
            //return UtilidadesTextos.getStringFromInputStream(inputStream);
            
            //Document document = (Document) builder.build(xmlFile);
            //String documento = xmlOutput.outputString(document);
            

            //byte[] byteText = documento.toString().getBytes(Charset.forName("UTF-8"));
            //return new String(byteText,"UTF-8");
            //return documento;
        /*} catch (JDOMException ex) {
            ex.printStackTrace();
            Logger.getLogger(UtilidadesXml.class.getName()).log(Level.SEVERE, null, ex);            */
        } catch (IOException ex) {
            ex.printStackTrace();
            Logger.getLogger(UtilidadesXml.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        return "";
    }
    
    /**
     * Este metodo unicamente sirve si esta trabajando con jaxb para guardar en un archivo fisico
     * @return 
     */
    public static void convertirObjetoXmlEnArchivo(Object objecto ,File file)
    {
        try {
            JAXBContext contexto = JAXBContext.newInstance(AtsJaxb.class);
            Marshaller marshaller = contexto.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            //marshaller.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, "libro.xsd");
            //StringWriter sw = new StringWriter();
            //marshaller.marshal(libro, System.out);
            //File file = new File( "tmp/ejemplo.xml" );
            marshaller.marshal(objecto, file);
        } catch (JAXBException ex) {
            Logger.getLogger(UtilidadesXml.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
