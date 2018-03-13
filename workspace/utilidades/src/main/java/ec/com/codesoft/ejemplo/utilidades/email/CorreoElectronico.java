/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.ejemplo.utilidades.email;

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
    PropiedadesCorreoEnum propiedadCorreo;

    public CorreoElectronico(String usuario, String clave, String mensaje, List<String> to, String subject) {
        this.usuario = usuario;
        this.clave = clave;
        this.mensaje = mensaje;
        this.to = to;
        this.subject = subject;
        this.pathFiles=new HashMap<>();
        this.propiedadCorreo=obtenenerPropiedad(usuario);
    }
    
    public CorreoElectronico(String usuario, String clave, String mensaje, List<String> to, String subject,Map<String,String> pathFiles) {
        this.usuario = usuario;
        this.clave = clave;
        this.mensaje = mensaje;
        this.to = to;
        this.subject = subject;
        this.pathFiles=pathFiles;
        this.propiedadCorreo=obtenenerPropiedad(usuario);
    }

    public void sendMail() throws AuthenticationFailedException,MessagingException,SmtpNoExisteException{
        
        //Verificar si existe un servidor smtp registrado
        if(propiedadCorreo==null)
            throw new SmtpNoExisteException("No existe servidor smtp");
        
        Properties props = new Properties();
        
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host",propiedadCorreo.getHost());
        props.put("mail.smtp.port",propiedadCorreo.getPort() );
        props.put("mail.transport.protocol", "smtp");
        //props.put("mail.smtp.from", bounceAddr);

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
        
        
        
         Transport.send(message);

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

    /**
     * Obtiene las propiedades de configuracion segun el dominio del email
     * @param email
     * @return 
     */
    public PropiedadesCorreoEnum obtenenerPropiedad(String email)
    {
        String emailFormat=email.toLowerCase();
        if(emailFormat.indexOf("@gmail")>=0)
        {
            return PropiedadesCorreoEnum.GMAIL;
        }
        else
        {
            if(email.indexOf("@hotmail")>=0)
            {
                return PropiedadesCorreoEnum.HOTMAIL;
            }
            else
            {
                if(email.indexOf("@outlook")>=0)
                {
                    return PropiedadesCorreoEnum.HOTMAIL;
                }
                else
                {
                    if(email.indexOf("@yahoo")>=0)
                    {
                        return PropiedadesCorreoEnum.YAHOO;
                    }
                
                }
            }
        }
        
        return null;
    }
    

}
