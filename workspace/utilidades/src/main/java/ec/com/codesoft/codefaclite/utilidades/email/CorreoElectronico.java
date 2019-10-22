/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.utilidades.email;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.AuthenticationFailedException;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author Carlos
 */
public class CorreoElectronico {

    private String usuario;
    private String clave;

    private String mensaje;
    private List<String> to;
    private String subject;
    private Map<String,String> pathFiles;
    private PropiedadCorreo propiedadCorreo;

    public CorreoElectronico(String usuario, String clave, String mensaje, List<String> to, String subject,PropiedadCorreo propiedadCorreo) {
        this.usuario = usuario;
        this.clave = clave;
        this.mensaje = mensaje;
        this.to = to;
        this.subject = subject;
        this.pathFiles=new HashMap<>();
        setearPropiedad(propiedadCorreo);
    }
    
    public CorreoElectronico(String usuario, String clave, String mensaje, List<String> to, String subject,Map<String,String> pathFiles,PropiedadCorreo propiedadCorreo) {
        this.usuario = usuario;
        this.clave = clave;
        this.mensaje = mensaje;
        this.to = to;
        this.subject = subject;
        this.pathFiles=pathFiles;
        setearPropiedad(propiedadCorreo);
    }
    
    public void setearPropiedad(PropiedadCorreo propiedadCorreo)
    {
        if(propiedadCorreo==null)
        {
            //Si no existe las propiedades trata de buscar las propiedades por defecto
            PropiedadCorreo propiedadPorDefecto=PropiedadCorreo.obtenerPropiedadesPorDefecto(this.usuario);
            if(propiedadPorDefecto!=null)
            {
                this.propiedadCorreo=propiedadPorDefecto;
            }
            
        }
        else
        {
            this.propiedadCorreo=propiedadCorreo;
        }
    }

    public void sendMail() throws AuthenticationFailedException,MessagingException,SmtpNoExisteException{
        
        //Verificar si existe un servidor smtp registrado
        if(propiedadCorreo==null)
            throw new SmtpNoExisteException("No existe servidor smtp");
        
        Properties props = new Properties();
        
        
        //props.put("mail.smtp.from", bounceAddr);
        if(propiedadCorreo.getPort().toString().equals("465"))
        {            
            props.put("mail.smtps.host", propiedadCorreo.getHost());
            props.put("mail.smtps.auth", "true");
            props.put("mail.transport.protocol", "smtps");
        }
        else
        {
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host",propiedadCorreo.getHost());
            props.put("mail.smtp.port",propiedadCorreo.getPort().toString());
            props.put("mail.transport.protocol", "smtp");
        }
        
        System.out.println("====>PROPIEDADES EMAIL <===========");
        System.out.println(props);

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(usuario, clave);
            }
        });
        
        //session.setDebug(true);

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(usuario));
            
            /**
             * Agregar varios destinatarios
             */
            
            String correosStr=String.join(",",to);
            System.out.println(correosStr);
            
            //InternetAddress.parse(correosStr);
            message.setRecipients(Message.RecipientType.TO,
                            InternetAddress.parse(correosStr));
            //TODO: Analizar si depues necesito separa para enviar con cc o directo al usuario
            /*
            for(int i=0;i<to.size();i++)
            {
                if(i==0)
                {
                    message.setRecipients(Message.RecipientType.TO,
                            InternetAddress.parse(to.get(i)));
                    System.out.println("Correo 1:"+to.get(i));
                }
                else
                {
                    InternetAddress[] address=InternetAddress.parse(to.get(i));
                    message.setRecipient(Message.RecipientType.CC,address[0]);
                    //message.setRecipients(Message.RecipientType.CC,
                    //    InternetAddress.parse(to.get(i)));
                    System.out.println("Correo "+(i+1)+":"+to.get(i));
                }
            }*/
            
            
            message.setSubject(subject);
            //message.setText(mensaje);
            
            
            
        // Create the message part
         //BodyPart messageBodyPart = new MimeBodyPart();

         // Now set the actual message
         //messageBodyPart.setText(mensaje);
            
         // Create a multipar message
         Multipart multipart = new MimeMultipart();

         // Set text message part
         //multipart.addBodyPart(messageBodyPart);

         // Part two is attachment
        for (Map.Entry<String, String> entry : pathFiles.entrySet())
        {
             BodyPart messageBodyPart = new MimeBodyPart();
             //String filename = "E:\\ejemplo3.xml";
             DataSource source = new FileDataSource(entry.getValue());
             messageBodyPart.setDataHandler(new DataHandler(source));
             messageBodyPart.setFileName(entry.getKey());
             multipart.addBodyPart(messageBodyPart);
        }

         
         //multipart.addBodyPart(messageBodyPart);
        
         /**
          * Contenido del texto html
          */
         MimeBodyPart htmlPart = new MimeBodyPart();
         htmlPart.setContent(mensaje, "text/html; charset=utf-8");
         

         multipart.addBodyPart(htmlPart);

         
        //message.setContent(multipart);
        message.setContent(multipart);
        
        
        
        //Transport.send(message);
         
        Transport transport = session.getTransport();
        
        transport.connect
          (propiedadCorreo.getHost(), propiedadCorreo.getPort(), usuario, clave);

        transport.sendMessage(message,
            message.getRecipients(Message.RecipientType.TO));
        transport.close();

        } catch (AuthenticationFailedException  e) {
            throw e;
        } catch (MessagingException ex) {
            throw ex;
        }
    }
    
    public Map<String, String> getPathFiles() {
        return pathFiles;
    }

    public void setPathFiles(Map<String, String> pathFiles) {
        this.pathFiles = pathFiles;
    }

    
    

}
